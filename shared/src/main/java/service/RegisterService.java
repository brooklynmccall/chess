package service;

import dataaccess.AlreadyTakenException;
import dataaccess.AuthDAO;
import dataaccess.BadRequestException;
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

    public RegisterResult register(RegisterRequest registerRequest) throws AlreadyTakenException, BadRequestException {
        if (registerRequest.username() == ""
                || registerRequest.username() == null
                || registerRequest.password() == ""
                || registerRequest.password() == null
                || registerRequest.email() == ""
                || registerRequest.email() == null) {
            throw new BadRequestException("Error: bad request");
        }
        UserData user = userDAO.getUser(registerRequest.username());
        if (user != null) {
            throw new AlreadyTakenException("Error: username already taken");
        }
        userDAO.createUser(registerRequest.username(), registerRequest.password(), registerRequest.email());
        AuthData auth = authDAO.createAuth(registerRequest.username());

        return new RegisterResult(registerRequest.username(), auth.authToken());
    }

}
