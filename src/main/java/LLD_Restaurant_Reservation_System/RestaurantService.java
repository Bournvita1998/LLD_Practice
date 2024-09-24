package LLD_Restaurant_Reservation_System;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantService {
    private Map<Integer, Restaurant> restaurantMap = new HashMap<>();

    public Restaurant createOrUpdateRestaurant(int id, String name, String location, List<Table> tables, Map<String, String> openingHours) {
        Restaurant restaurant = new Restaurant(id, name, location, tables, openingHours);
        restaurantMap.put(id, restaurant);
        return restaurant;
    }

    public Restaurant getRestaurantById(int id) {
        return restaurantMap.get(id);
    }
}

