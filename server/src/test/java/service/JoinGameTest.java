package service;

import chess.ChessGame;
import dataaccess.*;
import model.GameData;
import org.junit.jupiter.api.Test;
import service.request.CreateGameRequest;
import service.request.JoinGameRequest;
import service.request.RegisterRequest;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JoinGameTest {
    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();
    static GameDAO gameDAO = new MemoryGameDAO();

    static final ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);
    static final RegisterService registerService = new RegisterService(userDAO, authDAO);
    static final CreateGameService createGameService = new CreateGameService(authDAO, gameDAO);
    static final JoinGameService joinGameService = new JoinGameService(authDAO, gameDAO);

    @Test
    void testJoinGameSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterResult harryResult = registerService.register(harryRequest);
        String authToken = harryResult.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        int gameID = createGameService.createGame(gameRequest);

        assertDoesNotThrow(() -> joinGameService.joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.WHITE, gameID)));
        GameData actual = gameDAO.getGame(gameID);
        GameData expected = new GameData(gameID, "hpotter", null, "Wizard's Chess", actual.game());

        assert(expected.equals(actual));

    }

    @Test
    void testJoinGameFailure() throws DataAccessException, AlreadyTakenException, BadRequestException {
        clearService.clear();
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterResult harryResult = registerService.register(harryRequest);
        String harryAuthToken = harryResult.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(harryAuthToken, "Wizard's Chess");
        int gameID = createGameService.createGame(gameRequest);
        joinGameService.joinGame(new JoinGameRequest(harryAuthToken, ChessGame.TeamColor.WHITE, gameID));

        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterResult hermioneResult = registerService.register(hermioneRequest);
        String hermioneAuthToken = hermioneResult.authToken();

        assertThrows(AlreadyTakenException.class, () -> joinGameService.joinGame(new JoinGameRequest(hermioneAuthToken, ChessGame.TeamColor.WHITE, gameID)));
        GameData actual = gameDAO.getGame(gameID);
        GameData expected = new GameData(gameID, "hpotter", null, "Wizard's Chess", actual.game());
        assert(expected.equals(actual));

    }

}
