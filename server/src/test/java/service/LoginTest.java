package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.request.LoginRequest;
import service.request.RegisterRequest;
import service.result.LoginResult;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();

    static final RegisterService registerService = new RegisterService(userDAO, authDAO);
    static final LoginService loginService = new LoginService(userDAO, authDAO);
    static final LogoutService logoutService = new LogoutService(authDAO);

    @Test
    void testLogoutUsersSuccess() throws DataAccessException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        RegisterResult harry = registerService.register(harryRequest);
        RegisterResult hermione = registerService.register(hermioneRequest);
        RegisterResult ron = registerService.register(ronRequest);

        logoutService.logout(harry.authToken());
        logoutService.logout(hermione.authToken());
        logoutService.logout(ron.authToken());

        LoginResult harryLogin = loginService.login(new LoginRequest("hpotter", "jameslilyharry"));
        LoginResult hermioneLogin = loginService.login(new LoginRequest("hgranger", "spew4ever"));
        LoginResult ronLogin = loginService.login(new LoginRequest("rweasley", "chudleycannonsRgr8"));

        assertNotNull(authDAO.getAuth(harryLogin.authToken()));
        assertNotNull(authDAO.getAuth(hermioneLogin.authToken()));
        assertNotNull(authDAO.getAuth(ronLogin.authToken()));
    }

    @Test
    void testLogoutUsersFailure() throws DataAccessException {
        RegisterRequest dracoRequest = new RegisterRequest("dmalfoy", "pottermustdie", "draco.malfoy@hogwarts.edu");
        RegisterResult draco = registerService.register(dracoRequest);

        logoutService.logout(draco.authToken());

        assertThrows(DataAccessException.class, () -> loginService.login(new LoginRequest("dmalfoy", "ilovevoldy")));
        assertNull(authDAO.getAuth(draco.authToken()));

        assertThrows(DataAccessException.class, () -> loginService.login(new LoginRequest("llovegood", "thequibbler")));

    }

}
