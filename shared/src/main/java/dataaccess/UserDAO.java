package dataaccess;

import model.UserData;

public interface UserDAO {

    UserData readUser (String username);
    void createUser (String username, String password, String email);
    void deleteUser (String username);

}
