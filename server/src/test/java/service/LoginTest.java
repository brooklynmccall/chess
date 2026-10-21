package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;

public class LoginTest {

    static final LoginService service = new LoginService(new MemoryUserDAO(), new MemoryAuthDAO());



}
