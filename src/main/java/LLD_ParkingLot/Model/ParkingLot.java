package LLD_ParkingLot.Model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private String parkingLotId;
    private List<Floor> floors;

    public ParkingLot(String parkingLotId, int numFloors, int numSlotsPerFloor) {
        this.parkingLotId = parkingLotId;
        this.floors = new ArrayList<>();
        for (int i = 0; i < numFloors; i++) {
            floors.add(new Floor(i + 1, numSlotsPerFloor));
        }
    }

    public String findAndPark(Vehicle vehicle) {
        for (Floor floor : floors) {
            Slot slot = floor.findFreeSlot(vehicle.getType());
            if (slot != null) {
                slot.parkVehicle(vehicle);
                return parkingLotId + "_" + floor.getFloorNumber() + "_" + slot.getSlotNumber();
            }
        }
        return "Parking Lot Full";
    }

    public Vehicle unpark(String ticketId) {
        String[] parts = ticketId.split("_");
        int floorNumber = Integer.parseInt(parts[1]);
        int slotNumber = Integer.parseInt(parts[2]);
        Floor floor = floors.get(floorNumber - 1);
        Slot slot = floor.getSlots().get(slotNumber - 1);
        if (slot != null && !slot.isFree()) {
            Vehicle vehicle = slot.getVehicle();
            slot.unparkVehicle();
            return vehicle;
        }
        return null;
    }

    public String displayFreeCount(VehicleType vehicleType) {
        StringBuilder sb = new StringBuilder();
        for (Floor floor : floors) {
            int freeSlots = floor.getFreeSlots(vehicleType).size();
            sb.append("No. of free slots for ").append(vehicleType.name()).append(" on Floor ")
                    .append(floor.getFloorNumber()).append(": ").append(freeSlots).append("\n");
        }
        return sb.toString().trim();
    }

    public String displayFreeSlots(VehicleType vehicleType) {
        StringBuilder sb = new StringBuilder();
        for (Floor floor : floors) {
            List<Integer> freeSlots = floor.getFreeSlots(vehicleType);
            sb.append("Free slots for ").append(vehicleType.name()).append(" on Floor ")
                    .append(floor.getFloorNumber()).append(": ")
                    .append(String.join(",", freeSlots.stream().map(String::valueOf).toArray(String[]::new)))
                    .append("\n");
        }
        return sb.toString().trim();
    }

    public String displayOccupiedSlots(VehicleType vehicleType) {
        StringBuilder sb = new StringBuilder();
        for (Floor floor : floors) {
            List<Integer> occupiedSlots = floor.getOccupiedSlots(vehicleType);
            sb.append("Occupied slots for ").append(vehicleType.name()).append(" on Floor ")
                    .append(floor.getFloorNumber()).append(": ")
                    .append(String.join(",", occupiedSlots.stream().map(String::valueOf).toArray(String[]::new)))
                    .append("\n");
        }
        return sb.toString().trim();
    }
}
