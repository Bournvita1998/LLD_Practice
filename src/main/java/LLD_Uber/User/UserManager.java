package LLD_Uber.User;

import java.util.UUID;

public class UserManager {
    public static User createUser(String name, String email, String phone) {
        String userId = generateId();
        return new User(userId, name, email, phone);
    }

    public static Driver createDriver(String name, String email, String phone, String licenseNumber, String carDetails) {
        String driverId = generateId();
        return new Driver(driverId, name, email, phone, licenseNumber, carDetails);
    }

    public static void updateProfile(User user, String newName, String newEmail, String newPhone) {
        if (newName != null) {
            user.setName(newName);
        }
        if (newEmail != null) {
            user.setEmail(newEmail);
        }
        if (newPhone != null) {
            user.setPhone(newPhone);
        }
    }

    public static void addRating(String userId, String driverId, int score, String review) {
        Rating rating = new Rating(userId, driverId, score, review);
        // Save rating to database
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }
}

