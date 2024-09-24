package LLD_Elevator;

public class Main {
    public static void main(String[] args) {
        ElevatorSystem elevatorSystem = new ElevatorSystem(4);

        elevatorSystem.requestElevator(0, 5);
        elevatorSystem.requestElevator(2, 10);
        elevatorSystem.requestElevator(7, 20);
        elevatorSystem.requestElevator(15, 25);
    }
}
