package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {

    private final Map<String, UserData> users = new HashMap<>();

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public UserData createUser(String username, String password, String email) {
        UserData user = new UserData(username, password, email);
        users.put(username, user);
        return user;
    }

    @Override
    public void clearUsers() {
        users.clear();
    }
}
