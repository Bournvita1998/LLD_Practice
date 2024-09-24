package LLD_Elevator;

import java.util.HashSet;
import java.util.Set;

class Floor {
    private int floorNumber;
    private Set<Integer> elevators;

    public Floor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.elevators = new HashSet<>();
    }

    public void addElevator(int elevatorId) {
        elevators.add(elevatorId);
    }

    public Set<Integer> getElevators() {
        return elevators;
    }
}
