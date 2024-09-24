package LLD_TravelAgentServices;

public class Itinerary {
    private String itineraryId;
    private String description;
    private String location;
    private String date;

    public Itinerary(String itineraryId, String description, String location, String date) {
        this.itineraryId = itineraryId;
        this.description = description;
        this.location = location;
        this.date = date;
    }

    public String getItineraryId() {
        return itineraryId;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}

