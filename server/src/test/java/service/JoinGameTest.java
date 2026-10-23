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

    static final ClearService ClearService = new ClearService(userDAO, authDAO, gameDAO);
    static final RegisterService RegisterService = new RegisterService(userDAO, authDAO);
    static final CreateGameService CreateGameService = new CreateGameService(authDAO, gameDAO);
    static final JoinGameService JoinGameService = new JoinGameService(authDAO, gameDAO);

    @Test
    void testJoinGameSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterResult harryResult = RegisterService.register(harryRequest);
        String authToken = harryResult.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(authToken, "Wizard's Chess");
        int gameID = CreateGameService.createGame(gameRequest);

        assertDoesNotThrow(() -> JoinGameService.joinGame(new JoinGameRequest(authToken, ChessGame.TeamColor.WHITE, gameID)));
        GameData actual = gameDAO.getGame(gameID);
        GameData expected = new GameData(gameID, "hpotter", null, "Wizard's Chess", actual.game());

        assert(expected.equals(actual));

    }

    @Test
    void testJoinGameFailure() throws DataAccessException, AlreadyTakenException, BadRequestException {
        ClearService.clear();
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterResult harryResult = RegisterService.register(harryRequest);
        String harryAuthToken = harryResult.authToken();

        CreateGameRequest gameRequest = new CreateGameRequest(harryAuthToken, "Wizard's Chess");
        int gameID = CreateGameService.createGame(gameRequest);
        JoinGameService.joinGame(new JoinGameRequest(harryAuthToken, ChessGame.TeamColor.WHITE, gameID));

        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterResult hermioneResult = RegisterService.register(hermioneRequest);
        String hermioneAuthToken = hermioneResult.authToken();

        assertThrows(AlreadyTakenException.class, () ->
                JoinGameService.joinGame(new JoinGameRequest(hermioneAuthToken, ChessGame.TeamColor.WHITE, gameID)));
        GameData actual = gameDAO.getGame(gameID);
        GameData expected = new GameData(gameID, "hpotter", null, "Wizard's Chess", actual.game());
        assert(expected.equals(actual));

    }

}
