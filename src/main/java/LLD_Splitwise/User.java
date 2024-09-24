package LLD_Splitwise;

public class User {
    private String userId;
    private String name;
    private String email;
    private String mobile;

    public User(String userId, String name, String email, String mobile) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" + "userId='" + userId + '\'' + ", name='" + name + '\'' + '}';
    }
}
