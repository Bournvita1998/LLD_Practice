package LLD_Elevator;

import java.util.*;

public class ElevatorSystem {
    private List<Elevator> elevators;

    public ElevatorSystem(int numElevators) {
        elevators = new ArrayList<>();
        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(i));
        }
    }

    public void requestElevator(int sourceFloor, int destinationFloor) {
        Elevator closestElevator = findClosestElevator(sourceFloor);
        if (closestElevator != null) {
            closestElevator.addRequest(new ElevatorRequest(sourceFloor, destinationFloor));
        }
    }

    private Elevator findClosestElevator(int sourceFloor) {
        Elevator closestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - sourceFloor);
            if (distance < minDistance) {
                minDistance = distance;
                closestElevator = elevator;
            }
        }
        return closestElevator;
    }
}