package LLD_Uber.Ride;

public class Route {
    private Location startLocation;
    private Location endLocation;

    public Route(Location startLocation, Location endLocation) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    // Getters and setters
}

