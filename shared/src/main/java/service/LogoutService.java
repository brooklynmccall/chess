package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import service.request.LoginRequest;
import service.request.LogoutRequest;
import service.result.LoginResult;

public class LogoutService {

    private final AuthDAO authDAO;

    public LogoutService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }

    public void logout(LogoutRequest logoutRequest) throws DataAccessException {
        boolean success = authDAO.deleteAuth(logoutRequest.authToken());
        if (success == false) {
            throw new DataAccessException("Error: unauthorized");
        }

    }

}
