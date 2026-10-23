package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.request.CreateGameRequest;
import service.request.RegisterRequest;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {

    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();
    static GameDAO gameDAO = new MemoryGameDAO();

    static final ClearService CLEAR_SERVICE = new ClearService(userDAO, authDAO, gameDAO);
    static final RegisterService REGISTER_SERVICE = new RegisterService(userDAO, authDAO);
    static final CreateGameService CREATE_GAME_SERVICE = new CreateGameService(authDAO, gameDAO);

    void clear() {
        CLEAR_SERVICE.clear();
    }

    @Test
    void clearUsersSuccess() throws AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        REGISTER_SERVICE.register(harryRequest);
        REGISTER_SERVICE.register(hermioneRequest);
        REGISTER_SERVICE.register(ronRequest);

        clear();

        assertNull(userDAO.getUser("hpotter"));
        assertNull(userDAO.getUser("hgranger"));
        assertNull(userDAO.getUser("rweasley"));

    }

    @Test
    void clearAuthSuccess() throws AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        RegisterResult harryResult = REGISTER_SERVICE.register(harryRequest);
        RegisterResult hermioneResult = REGISTER_SERVICE.register(hermioneRequest);
        RegisterResult ronResult = REGISTER_SERVICE.register(ronRequest);

        clear();

        assertNull(authDAO.getAuth(harryResult.authToken()));
        assertNull(authDAO.getAuth(hermioneResult.authToken()));
        assertNull(authDAO.getAuth(ronResult.authToken()));

    }

    @Test
    void clearGamesSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest dracoRequest = new RegisterRequest("dmalfoy", "pottermustdie", "draco.malfoy@hogwarts.edu");
        RegisterResult draco = REGISTER_SERVICE.register(dracoRequest);
        String authToken = draco.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        int gameID = CREATE_GAME_SERVICE.createGame(gameRequest);

        clear();
        assertNull(gameDAO.getGame(gameID));

    }

}
