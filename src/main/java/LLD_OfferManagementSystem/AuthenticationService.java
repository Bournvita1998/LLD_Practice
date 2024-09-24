package LLD_OfferManagementSystem;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private Map<String, User> users;

    public AuthenticationService() {
        this.users = new HashMap<>();
    }

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    public User authenticate(String userId) {
        return users.get(userId);
    }
}
