package LLD_Restaurant_Reservation_System;

import java.util.List;
import java.util.Map;

public class Restaurant {
    private int id;
    private String name;
    private String location;
    private List<Table> tables;
    private Map<String, String> openingHours; // Key: Day, Value: "start-end"

    // Constructor, Getters, and Setters
    public Restaurant(int id, String name, String location, List<Table> tables, Map<String, String> openingHours) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.tables = tables;
        this.openingHours = openingHours;
    }

    public List<Table> getTables() {
        return tables;
    }

    // Getters and Setters
}

