package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemoryGameDAO implements GameDAO {

    private final Map<Integer, GameData> games = new HashMap<>();
    private int nextGameID = 1;

    @Override
    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    @Override
    public GameData createGame(String gameName) {
        GameData game = new GameData(nextGameID, null, null, gameName, new ChessGame());
        nextGameID++;
        games.put(nextGameID, game);
        return game;
    }

    @Override
    public GameData[] listGames() {
        return games.values().toArray(new GameData[0]);
    }

    @Override
    public GameData updateGame(int gameID, GameData newData) {
        games.replace(gameID, newData);
        return games.get(gameID);
    }

    @Override
    public void clearGames() {
        games.clear();
    }
}
