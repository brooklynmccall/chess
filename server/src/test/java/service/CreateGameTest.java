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

    static final RegisterService REGISTER_SERVICE = new RegisterService(userDAO, authDAO);
    static final CreateGameService CREATE_GAME_SERVICE = new CreateGameService(authDAO, gameDAO);

    @Test
    void testCreateGameSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterResult harryResult = REGISTER_SERVICE.register(harryRequest);

        String authToken = harryResult.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        int gameID = CREATE_GAME_SERVICE.createGame(gameRequest);

        assertNotNull(gameDAO.getGame(gameID));

    }

    @Test
    void testCreateGameFail() throws DataAccessException {
        CreateGameRequest gameRequest = new CreateGameRequest("This is my magic authToken.", "Wizard's Chess");

        assertThrows(DataAccessException.class, () -> CREATE_GAME_SERVICE.createGame(gameRequest));

    }
}
