package dataaccess;

import chess.ChessGame;
import model.GameData;

public interface GameDAO {

    GameData getGame (int gameID);
    GameData createGame (String gameName);
    GameData[] listGames ();
    GameData updateGame(int gameID, GameData newData);
    void clearGames ();
}
