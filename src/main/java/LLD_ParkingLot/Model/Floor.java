package LLD_ParkingLot.Model;

import java.util.ArrayList;
import java.util.List;

public class Floor {
    private int floorNumber;
    private List<Slot> slots;

    public Floor(int floorNumber, int numSlots) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
        for (int i = 0; i < numSlots; i++) {
            if (i == 0) {
                slots.add(new TruckSlot(i + 1));
            } else if (i == 1 || i == 2) {
                slots.add(new BikeSlot(i + 1));
            } else {
                slots.add(new CarSlot(i + 1));
            }
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public Slot findFreeSlot(VehicleType vehicleType) {
        for (Slot slot : slots) {
            if (slot.getType().name().equals(vehicleType.name()) && slot.isFree()) {
                return slot;
            }
        }
        return null;
    }

    public List<Integer> getFreeSlots(VehicleType vehicleType) {
        List<Integer> freeSlots = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getType().name().equals(vehicleType.name()) && slot.isFree()) {
                freeSlots.add(slot.getSlotNumber());
            }
        }
        return freeSlots;
    }

    public List<Integer> getOccupiedSlots(VehicleType vehicleType) {
        List<Integer> occupiedSlots = new ArrayList<>();
        for (Slot slot : slots) {
            if (slot.getType().name().equals(vehicleType.name()) && !slot.isFree()) {
                occupiedSlots.add(slot.getSlotNumber());
            }
        }
        return occupiedSlots;
    }
}
