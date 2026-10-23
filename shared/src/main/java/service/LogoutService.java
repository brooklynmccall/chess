package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;

public class LogoutService {

    private final AuthDAO authDAO;

    public LogoutService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public void logout(String authToken) throws DataAccessException {
        boolean success = authDAO.deleteAuth(authToken);
        if (success == false || authToken == null) {
            throw new DataAccessException("Error: unauthorized");
        }

    }

}
