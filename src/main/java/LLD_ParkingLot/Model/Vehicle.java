package LLD_ParkingLot.Model;

public abstract class Vehicle {
    private VehicleType type;
    private String registrationNumber;
    private String color;

    public Vehicle(VehicleType type, String registrationNumber, String color) {
        this.type = type;
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public VehicleType getType() {
        return type;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getColor() {
        return color;
    }
}
