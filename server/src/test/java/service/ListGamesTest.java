package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.request.CreateGameRequest;
import service.request.RegisterRequest;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class ListGamesTest {
    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();
    static GameDAO gameDAO = new MemoryGameDAO();

    static final ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);
    static final RegisterService registerService = new RegisterService(userDAO, authDAO);
    static final CreateGameService createGameService = new CreateGameService(authDAO, gameDAO);
    static final ListGamesService listGamesService = new ListGamesService(authDAO, gameDAO);

    @Test
    void testListGamesSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterResult harryResult = registerService.register(harryRequest);

        String authToken = harryResult.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        createGameService.createGame(gameRequest);

        assertNotNull(listGamesService.listGames(authToken));
    }

    @Test
    void testListGamesFailure() throws DataAccessException, AlreadyTakenException, BadRequestException {
        clearService.clear();

        RegisterRequest dracoRequest = new RegisterRequest("dmalfoy", "pottermustdie", "draco.malfoy@hogwarts.edu");
        RegisterResult draco = registerService.register(dracoRequest);

        String authToken = draco.authToken();

        assert(listGamesService.listGames(authToken).length == 0);
        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");

        createGameService.createGame(gameRequest);
        assertThrows(DataAccessException.class, () -> listGamesService.listGames("Alohamora"));
    }
}
