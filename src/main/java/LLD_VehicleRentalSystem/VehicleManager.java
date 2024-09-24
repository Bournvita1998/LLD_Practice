package LLD_VehicleRentalSystem;

import java.util.HashMap;
import java.util.Map;

public class VehicleManager {
    private Map<String, Vehicle> vehicles;

    public VehicleManager() {
        vehicles = new HashMap<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.put(vehicle.getVehicleId(), vehicle);
    }

    public Vehicle getVehicle(String vehicleId) {
        return vehicles.get(vehicleId);
    }

    public boolean isVehicleAvailable(String vehicleId) {
        Vehicle vehicle = vehicles.get(vehicleId);
        return vehicle != null && vehicle.isAvailable();
    }

    public void updateVehicleAvailability(String vehicleId, boolean isAvailable) {
        Vehicle vehicle = vehicles.get(vehicleId);
        if (vehicle != null) {
            vehicle.setAvailable(isAvailable);
        }
    }
}

