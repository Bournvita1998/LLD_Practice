package LLD_Uber.User;

public class Driver extends User {
    private String licenseNumber;
    private String carDetails;

    public Driver(String userId, String name, String email, String phone, String licenseNumber, String carDetails) {
        super(userId, name, email, phone);
        this.licenseNumber = licenseNumber;
        this.carDetails = carDetails;
    }

    // Getters and setters
}
