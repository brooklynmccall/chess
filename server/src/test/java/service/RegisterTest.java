package service;

import dataaccess.*;
import org.junit.jupiter.api.Test;
import service.request.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {

    static UserDAO userDAO = new MemoryUserDAO();
    static AuthDAO authDAO = new MemoryAuthDAO();

    static final RegisterService registerService = new RegisterService(userDAO, authDAO);

    @Test
    void testRegisterUsersSuccess() {
        RegisterRequest harryRequest = new RegisterRequest("hpotter", "jameslilyharry", "harry.potter@hogwarts.edu");
        RegisterRequest hermioneRequest = new RegisterRequest("hgranger", "spew4ever", "hermione.granger@hogwarts.edu");
        RegisterRequest ronRequest = new RegisterRequest("rweasley", "chudleycannonsRgr8", "ron.weasley@hogwarts.edu");

        registerService.register(harryRequest);
        registerService.register(hermioneRequest);
        registerService.register(ronRequest);

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

        assertThrows(AlreadyTakenException.class, () -> registerService.register(harryRequest));
        assertThrows(AlreadyTakenException.class, () -> registerService.register(hermioneRequest));
        assertThrows(AlreadyTakenException.class, () -> registerService.register(ronRequest));

    }

}
