package LLD_ParkingLot;

import LLD_ParkingLot.Model.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = null;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("exit")) {
                break;
            }
            String[] parts = command.split(" ");
            String action = parts[0];

            switch (action) {
                case "create_parking_lot":
                    String parkingLotId = parts[1];
                    int numFloors = Integer.parseInt(parts[2]);
                    int numSlotsPerFloor = Integer.parseInt(parts[3]);
                    parkingLot = new ParkingLot(parkingLotId, numFloors, numSlotsPerFloor);
                    System.out.println("Created parking lot with " + numFloors + " floors and " + numSlotsPerFloor + " slots per floor");
                    break;
                case "park_vehicle":
                    VehicleType vehicleType = VehicleType.valueOf(parts[1]);
                    String regNo = parts[2];
                    String color = parts[3];
                    Vehicle vehicle;
                    switch (vehicleType) {
                        case CAR:
                            vehicle = new Car(regNo, color);
                            break;
                        case BIKE:
                            vehicle = new Bike(regNo, color);
                            break;
                        case TRUCK:
                            vehicle = new Truck(regNo, color);
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown vehicle type");
                    }
                    String ticketId = parkingLot.findAndPark(vehicle);
                    if (ticketId.equals("Parking Lot Full")) {
                        System.out.println("Parking Lot Full");
                    } else {
                        System.out.println("Parked vehicle. Ticket ID: " + ticketId);
                    }
                    break;
                case "unpark_vehicle":
                    String ticket = parts[1];
                    Vehicle unparkedVehicle = parkingLot.unpark(ticket);
                    if (unparkedVehicle == null) {
                        System.out.println("Invalid Ticket");
                    } else {
                        System.out.println("Unparked vehicle with Registration Number: " + unparkedVehicle.getRegistrationNumber() + " and Color: " + unparkedVehicle.getColor());
                    }
                    break;
                case "display":
                    String displayType = parts[1];
                    vehicleType = VehicleType.valueOf(parts[2]);
                    switch (displayType) {
                        case "free_count":
                            System.out.println(parkingLot.displayFreeCount(vehicleType));
                            break;
                        case "free_slots":
                            System.out.println(parkingLot.displayFreeSlots(vehicleType));
                            break;
                        case "occupied_slots":
                            System.out.println(parkingLot.displayOccupiedSlots(vehicleType));
                            break;
                    }
                    break;
            }
        }

        scanner.close();
    }
}
