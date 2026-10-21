package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

public class ClearTest {
    static final ClearService service = new ClearService(new MemoryUserDAO(), new MemoryAuthDAO(), new MemoryGameDAO());


}
