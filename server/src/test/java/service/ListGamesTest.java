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

    static final ClearService CLEAR_SERVICE = new ClearService(userDAO, authDAO, gameDAO);
    static final RegisterService REGISTER_SERVICE = new RegisterService(userDAO, authDAO);
    static final CreateGameService CREATE_GAME_SERVICE = new CreateGameService(authDAO, gameDAO);
    static final ListGamesService LIST_GAMES_SERVICE = new ListGamesService(authDAO, gameDAO);

    @Test
    void testListGamesSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterResult harryResult = REGISTER_SERVICE.register(harryRequest);

        String authToken = harryResult.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        CREATE_GAME_SERVICE.createGame(gameRequest);
        ListGamesResult games = LIST_GAMES_SERVICE.listGames(authToken);

        assertNotNull(games.games());
    }

    @Test
    void testListGamesFailure() throws DataAccessException, AlreadyTakenException, BadRequestException {
        CLEAR_SERVICE.clear();

        RegisterRequest dracoRequest = new RegisterRequest("dmalfoy", "pottermustdie", "draco.malfoy@hogwarts.edu");
        RegisterResult draco = REGISTER_SERVICE.register(dracoRequest);

        String authToken = draco.authToken();
        ListGamesResult games = LIST_GAMES_SERVICE.listGames(authToken);

        assert(games.games().length == 0);
        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");

        CREATE_GAME_SERVICE.createGame(gameRequest);
        assertThrows(DataAccessException.class, () -> LIST_GAMES_SERVICE.listGames("Alohamora"));
    }
}
