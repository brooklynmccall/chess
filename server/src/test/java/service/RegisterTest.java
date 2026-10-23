package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();

    static final RegisterService REGISTER_SERVICE = new RegisterService(userDAO, authDAO);

    @Test
    void testRegisterUsersSuccess() throws AlreadyTakenException, BadRequestException {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        REGISTER_SERVICE.register(harryRequest);
        REGISTER_SERVICE.register(hermioneRequest);
        REGISTER_SERVICE.register(ronRequest);

        assertNotNull(userDAO.getUser("hpotter"));
        assertNotNull(userDAO.getUser("hgranger"));
        assertNotNull(userDAO.getUser("rweasley"));
        assertNull(userDAO.getUser("dmalfoy"));

    }

    @Test
    void testRegisterUsersFailure() {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        assertThrows(AlreadyTakenException.class, () -> REGISTER_SERVICE.register(harryRequest));
        assertThrows(AlreadyTakenException.class, () -> REGISTER_SERVICE.register(hermioneRequest));
        assertThrows(AlreadyTakenException.class, () -> REGISTER_SERVICE.register(ronRequest));

    }

}
