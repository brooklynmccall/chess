package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.request.CreateGameRequest;
import service.request.RegisterRequest;
import service.result.ListGamesResult;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class ListGamesTest {
    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();
    static GameDAO gameDAO = new MemoryGameDAO();

    static final ClearService ClearService = new ClearService(userDAO, authDAO, gameDAO);
    static final RegisterService RegisterService = new RegisterService(userDAO, authDAO);
    static final CreateGameService CreateGameService = new CreateGameService(authDAO, gameDAO);
    static final ListGamesService ListGamesService = new ListGamesService(authDAO, gameDAO);

    @Test
    void testListGamesSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterResult harryResult = RegisterService.register(harryRequest);

        String authToken = harryResult.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        CreateGameService.createGame(gameRequest);
        ListGamesResult games = ListGamesService.listGames(authToken);

        assertNotNull(games.games());
    }

    @Test
    void testListGamesFailure() throws DataAccessException, AlreadyTakenException, BadRequestException {
        ClearService.clear();

        RegisterRequest dracoRequest = new RegisterRequest("dmalfoy", "pottermustdie", "draco.malfoy@hogwarts.edu");
        RegisterResult draco = RegisterService.register(dracoRequest);

        String authToken = draco.authToken();
        ListGamesResult games = ListGamesService.listGames(authToken);

        assert(games.games().length == 0);
        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");

        CreateGameService.createGame(gameRequest);
        assertThrows(DataAccessException.class, () -> ListGamesService.listGames("Alohamora"));
    }
}
