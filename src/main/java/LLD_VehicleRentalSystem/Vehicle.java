package LLD_VehicleRentalSystem;

public class Vehicle {
    private String vehicleId;
    private String make;
    private String model;
    private String type; // e.g., Car, Truck, Bike
    private boolean isAvailable;
    private double rentalPricePerDay;

    public Vehicle(String vehicleId, String make, String model, String type, boolean isAvailable, double rentalPricePerDay) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.type = type;
        this.isAvailable = isAvailable;
        this.rentalPricePerDay = rentalPricePerDay;
    }

    // Constructors, getters, and setters

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public double getRentalPricePerDay() {
        return rentalPricePerDay;
    }
}

