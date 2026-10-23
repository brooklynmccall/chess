package server;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import model.GameData;
import model.UserData;
import service.*;
import service.request.*;
import service.result.*;

import java.util.Map;

public class Server {

    private final Javalin javalin;

    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    private final ClearService clearService;
    private final RegisterService registerService;
    private final LoginService loginService;
    private final LogoutService logoutService;
    private final ListGamesService listGamesService;
    private final CreateGameService createGameService;
    private final JoinGameService joinGameService;

    public Server() {
        this.userDAO = new MemoryUserDAO();
        this.authDAO = new MemoryAuthDAO();
        this.gameDAO = new MemoryGameDAO();

        clearService = new ClearService(userDAO, authDAO, gameDAO);
        registerService = new RegisterService(userDAO, authDAO);
        loginService = new LoginService(userDAO, authDAO);
        logoutService = new LogoutService(authDAO);
        listGamesService = new ListGamesService(authDAO, gameDAO);
        createGameService = new CreateGameService(authDAO, gameDAO);
        joinGameService = new JoinGameService(authDAO, gameDAO);

        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.
        javalin.delete("/db", this::clear);
        javalin.post("/user", this::register);
        javalin.post("/session", this::login);
        javalin.delete("/session", this::logout);
        javalin.get("/game", this::listGames);
        javalin.post("/game", this::createGame);
        javalin.put("/game", this::joinGame);
        javalin.exception(DataAccessException.class, this::dataAccessExceptionHandler);
        javalin.exception(AlreadyTakenException.class, this::alreadyTakenExceptionHandler);
        javalin.exception(BadRequestException.class, this::badRequestExceptionHandler);

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    private void dataAccessExceptionHandler(DataAccessException ex, Context ctx) {
        ctx.status(401);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

    private void alreadyTakenExceptionHandler(AlreadyTakenException ex, Context ctx) {
        ctx.status(403);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

    private void badRequestExceptionHandler(BadRequestException ex, Context ctx) {
        ctx.status(400);
        ctx.result(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

    private void clear(Context ctx) {
        clearService.clear();
        ctx.status(200);
        ctx.result(new Gson().toJson(Map.of()));
    }

    private void register(Context ctx) throws AlreadyTakenException, BadRequestException {
        RegisterRequest request = new Gson().fromJson(ctx.body(), RegisterRequest.class);
        RegisterResult result = registerService.register(request);
        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }

    private void login(Context ctx) throws DataAccessException, AlreadyTakenException, BadRequestException {
        LoginRequest request = new Gson().fromJson(ctx.body(), LoginRequest.class);
        LoginResult result = loginService.login(request);
        ctx.status(200);
        ctx.result(new Gson().toJson(result));
    }

    private void logout(Context ctx) throws DataAccessException {
        String authToken = new Gson().fromJson(ctx.body(), String.class);
        logoutService.logout(authToken);
        ctx.status(200);
        ctx.result(new Gson().toJson(Map.of()));
    }

    private void listGames(Context ctx) throws DataAccessException {
        String authToken = new Gson().fromJson(ctx.body(), String.class);
        GameData[] games = listGamesService.listGames(authToken);
        ctx.status(200);
        ctx.result(new Gson().toJson(games));
    }

    private void createGame(Context ctx) throws DataAccessException, BadRequestException {
        CreateGameRequest request = new Gson().fromJson(ctx.body(), CreateGameRequest.class);
        int gameID = createGameService.createGame(request);
        ctx.status(200);
        ctx.result(new Gson().toJson(gameID));
    }

    private void joinGame(Context ctx) throws DataAccessException, AlreadyTakenException, BadRequestException {
        JoinGameRequest request = new Gson().fromJson(ctx.body(), JoinGameRequest.class);
        joinGameService.joinGame(request);
        ctx.status(200);
        ctx.result(new Gson().toJson(Map.of()));
    }
}
