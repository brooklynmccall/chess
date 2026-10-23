package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.request.CreateGameRequest;
import service.request.RegisterRequest;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGameTest {

    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();
    static GameDAO gameDAO = new MemoryGameDAO();

    static final RegisterService registerService = new RegisterService(userDAO, authDAO);
    static final CreateGameService createGameService = new CreateGameService(authDAO, gameDAO);

    @Test
    void testCreateGameSuccess() throws DataAccessException, AlreadyTakenException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterResult harryResult = registerService.register(harryRequest);

        String authToken = harryResult.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        int gameID = createGameService.createGame(gameRequest);

        assertNotNull(gameDAO.getGame(gameID));

    }

    @Test
    void testCreateGameFail() throws DataAccessException {
        CreateGameRequest gameRequest = new CreateGameRequest("This is my magic authToken.", "Wizard's Chess");

        assertThrows(DataAccessException.class, () -> createGameService.createGame(gameRequest));

    }
}
