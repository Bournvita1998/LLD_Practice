package LLD_Uber.User;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private Rating rating;

    public User(String userId, String name, String email, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public void setPhone(String newPhone) {
        this.phone = newPhone;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    // Getters and setters
}
