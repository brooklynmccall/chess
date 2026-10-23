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

    static final ClearService ClearService = new ClearService(userDAO, authDAO, gameDAO);
    static final RegisterService RegisterService = new RegisterService(userDAO, authDAO);
    static final CreateGameService CreateGameService = new CreateGameService(authDAO, gameDAO);

    void clear() {
        ClearService.clear();
    }

    @Test
    void clearUsersSuccess() throws AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        RegisterService.register(harryRequest);
        RegisterService.register(hermioneRequest);
        RegisterService.register(ronRequest);

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

        RegisterResult harryResult = RegisterService.register(harryRequest);
        RegisterResult hermioneResult = RegisterService.register(hermioneRequest);
        RegisterResult ronResult = RegisterService.register(ronRequest);

        clear();

        assertNull(authDAO.getAuth(harryResult.authToken()));
        assertNull(authDAO.getAuth(hermioneResult.authToken()));
        assertNull(authDAO.getAuth(ronResult.authToken()));

    }

    @Test
    void clearGamesSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest dracoRequest = new RegisterRequest("dmalfoy", "pottermustdie", "draco.malfoy@hogwarts.edu");
        RegisterResult draco = RegisterService.register(dracoRequest);
        String authToken = draco.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        int gameID = CreateGameService.createGame(gameRequest);

        clear();
        assertNull(gameDAO.getGame(gameID));

    }

}
