package LLD_Elevator;

import java.util.*;

class Elevator {
    private int id;
    private int currentFloor;
    private Direction direction;
    private PriorityQueue<ElevatorRequest> requests;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0; // Start from ground floor
        this.direction = Direction.IDLE;
        this.requests = new PriorityQueue<>(Comparator.comparingInt(ElevatorRequest::getDestinationFloor));
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public void addRequest(ElevatorRequest request) {
        requests.offer(request);
        moveElevator();
    }

    private void moveElevator() {
        while (!requests.isEmpty()) {
            ElevatorRequest request = requests.poll();
            if (request.getSourceFloor() != currentFloor) {
                moveToFloor(request.getSourceFloor());
            }
            moveToFloor(request.getDestinationFloor());
        }
        direction = Direction.IDLE;
    }

    private void moveToFloor(int floor) {
        if (currentFloor < floor) {
            direction = Direction.UP;
        } else if (currentFloor > floor) {
            direction = Direction.DOWN;
        }
        System.out.println("Elevator " + id + " moving from floor " + currentFloor + " to floor " + floor);
        currentFloor = floor;
        direction = Direction.IDLE;
    }
}
