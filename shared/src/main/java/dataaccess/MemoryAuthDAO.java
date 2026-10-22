package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    private final Map<String, AuthData> auths = new HashMap<>();

    @Override
    public AuthData getAuth(String authToken) {
        return auths.get(authToken);
    }

    @Override
    public AuthData createAuth(String username) {
        String authToken = generateToken();
        AuthData auth = new AuthData(authToken, username);
        auths.put(authToken, auth);
        return auth;
    }

    @Override
    public boolean deleteAuth(String authToken) {
        AuthData auth = auths.remove(authToken);
        if (auth == null) {
            return false;
        }
        return true;
    }

    public void clearAuths() {
        auths.clear();
    }

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
