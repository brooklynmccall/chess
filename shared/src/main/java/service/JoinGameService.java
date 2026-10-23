package service;

import chess.ChessGame;
import dataaccess.*;
import model.AuthData;
import model.GameData;
import service.request.JoinGameRequest;

public class JoinGameService {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public JoinGameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public void joinGame(JoinGameRequest joinGameRequest) throws DataAccessException, AlreadyTakenException {
        AuthData auth = authDAO.getAuth(joinGameRequest.authToken());
        if (auth == null) {
            throw new DataAccessException("Error: unauthorized");
        }

        String username = auth.username();
        GameData game = gameDAO.getGame(joinGameRequest.gameID());
        if (game == null) {
            throw new DataAccessException("Error: nonexistent game");
        }

        if (joinGameRequest.color() == ChessGame.TeamColor.WHITE) {
            if (game.whiteUsername() != null) {
                throw new AlreadyTakenException("Error: color already taken");
            } else {
                GameData newGame = new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game());
                gameDAO.updateGame(game.gameID(), newGame);
            }
        }

    }

}
