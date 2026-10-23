package service;

import dataaccess.AuthDAO;
import dataaccess.BadRequestException;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import service.request.LoginRequest;
import service.result.LoginResult;

public class LoginService {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public LoginService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException, BadRequestException {
        if (loginRequest.username() == ""
                || loginRequest.username() == null
                || loginRequest.password() == ""
                || loginRequest.password() == null) {
            throw new BadRequestException("Error: bad request");
        }
        UserData user = userDAO.getUser(loginRequest.username());
        if (user == null || !loginRequest.password().equals(user.password())) {
            throw new DataAccessException("Error: unauthorized");
        }
        AuthData auth = authDAO.createAuth(user.username());

        return new LoginResult(user.username(), auth.authToken());
    }
}
