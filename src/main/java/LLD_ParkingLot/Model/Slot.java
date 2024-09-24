package LLD_ParkingLot.Model;

public abstract class Slot {
    private SlotType type;
    private int slotNumber;
    private boolean isFree;
    private Vehicle vehicle;

    public Slot(SlotType type, int slotNumber) {
        this.type = type;
        this.slotNumber = slotNumber;
        this.isFree = true;
        this.vehicle = null;
    }

    public SlotType getType() {
        return type;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean isFree() {
        return isFree;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.isFree = false;
        this.vehicle = vehicle;
    }

    public void unparkVehicle() {
        this.isFree = true;
        this.vehicle = null;
    }
}
