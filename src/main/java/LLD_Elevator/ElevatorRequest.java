package LLD_Elevator;

public class ElevatorRequest {
    private int sourceFloor;
    private int destinationFloor;

    public ElevatorRequest(int sourceFloor, int destinationFloor) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}
