package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import service.request.CreateGameRequest;
import service.result.CreateGameResult;

public class CreateGameService {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public CreateGameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest) throws DataAccessException {
        AuthData auth = authDAO.getAuth(createGameRequest.authToken());
        if (auth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        GameData game = gameDAO.createGame(createGameRequest.gameName());

        return new CreateGameResult(game.gameID());
    }

}
