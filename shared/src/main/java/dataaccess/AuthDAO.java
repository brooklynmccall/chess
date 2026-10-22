package dataaccess;

import model.AuthData;

public interface AuthDAO {

    AuthData getAuth (String authToken);
    AuthData createAuth (String username);
    boolean deleteAuth (String authToken);
    void clearAuths ();

}
