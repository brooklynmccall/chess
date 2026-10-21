package service;

import dataaccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {

    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();
    static GameDAO gameDAO = new MemoryGameDAO();

    static final ClearService clearService = new ClearService(userDAO, authDAO, gameDAO);
    static final RegisterService registerService = new RegisterService(userDAO, authDAO);
    static final CreateGameService createGameService = new CreateGameService(authDAO, gameDAO);

    @BeforeEach
    void clear() {
        clearService.clear();
    }

    @Test
    void registerUser() {
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterResult hermioneResult = registerService.register(hermioneRequest);

        assertNotNull(userDAO.getUser("hgranger"));
        assertNull(userDAO.getUser("hpotter"));
    }



}
