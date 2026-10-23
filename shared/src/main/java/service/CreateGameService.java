package service;

import dataaccess.AuthDAO;
import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.AuthData;
import model.GameData;
import service.request.CreateGameRequest;

public class CreateGameService {

    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public CreateGameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public int createGame(CreateGameRequest createGameRequest) throws DataAccessException, BadRequestException {
        if (createGameRequest.authToken() == ""
                || createGameRequest.authToken() == null
                || createGameRequest.gameName() == ""
                || createGameRequest.gameName() == null) {
            throw new BadRequestException("Error: bad request");
        }
        AuthData auth = authDAO.getAuth(createGameRequest.authToken());
        if (auth == null) {
            throw new DataAccessException("Error: unauthorized");
        }
        GameData game = gameDAO.createGame(createGameRequest.gameName());

        return game.gameID();
    }

}
