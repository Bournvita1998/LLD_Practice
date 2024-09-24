package LLD_Twitter;

import java.util.*;

public class Twitter {
    private Map<String, User> users;

    public Twitter() {
        users = new HashMap<>();
    }

    public User createUser(String userName) {
        if (users.containsKey(userName)) {
            System.out.println("User already exists!");
            return users.get(userName);
        }
        User newUser = new User(userName);
        users.put(userName, newUser);
        return newUser;
    }

    public User getUser(String userName) {
        return users.get(userName);
    }
}

