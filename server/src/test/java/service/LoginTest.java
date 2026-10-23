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

    static final RegisterService REGISTER_SERVICE = new RegisterService(userDAO, authDAO);
    static final LoginService LOGIN_SERVICE = new LoginService(userDAO, authDAO);
    static final LogoutService LOGOUT_SERVICE = new LogoutService(authDAO);

    @Test
    void testLogoutUsersSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        RegisterResult harry = REGISTER_SERVICE.register(harryRequest);
        RegisterResult hermione = REGISTER_SERVICE.register(hermioneRequest);
        RegisterResult ron = REGISTER_SERVICE.register(ronRequest);

        LOGOUT_SERVICE.logout(harry.authToken());
        LOGOUT_SERVICE.logout(hermione.authToken());
        LOGOUT_SERVICE.logout(ron.authToken());

        LoginResult harryLogin = LOGIN_SERVICE.login(new LoginRequest("hpotter", "jameslilyharry"));
        LoginResult hermioneLogin = LOGIN_SERVICE.login(new LoginRequest("hgranger", "spew4ever"));
        LoginResult ronLogin = LOGIN_SERVICE.login(new LoginRequest("rweasley", "chudleycannonsRgr8"));

        assertNotNull(authDAO.getAuth(harryLogin.authToken()));
        assertNotNull(authDAO.getAuth(hermioneLogin.authToken()));
        assertNotNull(authDAO.getAuth(ronLogin.authToken()));
    }

    @Test
    void testLogoutUsersFailure() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest dracoRequest = new RegisterRequest("dmalfoy", "pottermustdie", "draco.malfoy@hogwarts.edu");
        RegisterResult draco = REGISTER_SERVICE.register(dracoRequest);

        LOGOUT_SERVICE.logout(draco.authToken());

        assertThrows(DataAccessException.class, () -> LOGIN_SERVICE.login(new LoginRequest("dmalfoy", "ilovevoldy")));
        assertNull(authDAO.getAuth(draco.authToken()));

        assertThrows(DataAccessException.class, () -> LOGIN_SERVICE.login(new LoginRequest("llovegood", "thequibbler")));

    }

}
