package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;
import service.result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest {

    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();

    static final RegisterService REGISTER_SERVICE = new RegisterService(userDAO, authDAO);
    static final LogoutService LOGOUT_SERVICE = new LogoutService(authDAO);

    @Test
    void testLogoutUsersSuccess() throws DataAccessException, AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        RegisterResult harry = REGISTER_SERVICE.register(harryRequest);
        RegisterResult hermione = REGISTER_SERVICE.register(hermioneRequest);
        RegisterResult ron = REGISTER_SERVICE.register(ronRequest);

        assertNotNull(authDAO.getAuth(harry.authToken()));
        assertNotNull(authDAO.getAuth(hermione.authToken()));
        assertNotNull(authDAO.getAuth(ron.authToken()));

        assertDoesNotThrow(() -> LOGOUT_SERVICE.logout(harry.authToken()));
        assertDoesNotThrow(() -> LOGOUT_SERVICE.logout(hermione.authToken()));
        assertDoesNotThrow(() -> LOGOUT_SERVICE.logout(ron.authToken()));

        assertNull(authDAO.getAuth(harry.authToken()));
        assertNull(authDAO.getAuth(hermione.authToken()));
        assertNull(authDAO.getAuth(ron.authToken()));
    }

    @Test
    void testLogoutUsersFailure() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> LOGOUT_SERVICE.logout("Alohamora"));
        assertThrows(DataAccessException.class, () -> LOGOUT_SERVICE.logout("Wingardium leviosa"));
        assertThrows(DataAccessException.class, () -> LOGOUT_SERVICE.logout("Expelliarmus"));
    }

}
