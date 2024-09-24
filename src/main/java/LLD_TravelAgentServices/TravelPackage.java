package LLD_TravelAgentServices;

import java.util.ArrayList;
import java.util.List;

public class TravelPackage {
    private String packageId;
    private String packageName;
    private List<Itinerary> itineraries;
    private List<Passenger> passengers;

    public TravelPackage(String packageId, String packageName) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.itineraries = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    public void addItinerary(Itinerary itinerary) {
        itineraries.add(itinerary);
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public String getPackageId() {
        return packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }
}

