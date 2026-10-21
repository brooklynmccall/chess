package service;

import dataaccess.AlreadyTakenException;
import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;
import service.request.RegisterRequest;
import service.result.RegisterResult;

public class RegisterService {

    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public RegisterService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException {
        UserData user = userDAO.getUser(registerRequest.username());
        if (user != null) {
            throw new AlreadyTakenException("Error: username already taken");
        }
        AuthData auth = authDAO.createAuth(user.username());

        return new RegisterResult(user.username(), auth.authToken());
    }

}
