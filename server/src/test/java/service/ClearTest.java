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

    static final ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);
    static final RegisterService registerService = new RegisterService(userDAO, authDAO);
    static final CreateGameService createGameService = new CreateGameService(authDAO, gameDAO);

    void clear() {
        clearService.clear();
    }

    @Test
    void clearUsersSuccess() {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        registerService.register(harryRequest);
        registerService.register(hermioneRequest);
        registerService.register(ronRequest);

        clear();

        assertNull(userDAO.getUser("hpotter"));
        assertNull(userDAO.getUser("hgranger"));
        assertNull(userDAO.getUser("rweasley"));

    }

    @Test
    void clearAuthSuccess() {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        RegisterResult harryResult = registerService.register(harryRequest);
        RegisterResult hermioneResult = registerService.register(hermioneRequest);
        RegisterResult ronResult = registerService.register(ronRequest);

        clear();

        assertNull(authDAO.getAuth(harryResult.authToken()));
        assertNull(authDAO.getAuth(hermioneResult.authToken()));
        assertNull(authDAO.getAuth(ronResult.authToken()));

    }

    @Test
    void clearGamesSuccess() throws DataAccessException {
        RegisterRequest dracoRequest = new RegisterRequest("dmalfoy", "pottermustdie", "draco.malfoy@hogwarts.edu");
        RegisterResult draco = registerService.register(dracoRequest);
        String authToken = draco.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        int gameID = createGameService.createGame(gameRequest);

        clear();
        assertNull(gameDAO.getGame(gameID));

    }

}
