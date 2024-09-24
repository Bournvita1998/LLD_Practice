package LLD_OfferManagementSystem;

import java.util.Map;

public class Offer {
    private String id;
    private String type; // Combo, Bundle, Buy X Get Y
    private Map<String, String> details;

    // Constructors, getters, and setters
    public Offer(String id, String type, Map<String, String> details) {
        this.id = id;
        this.type = type;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getDetails() {
        return details;
    }
}
