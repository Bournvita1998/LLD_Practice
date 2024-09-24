package LLD_Restaurant_Reservation_System;

public class Table {
    private int tableId;
    private int capacity;

    // Constructor, Getters, and Setters
    public Table(int tableId, int capacity) {
        this.tableId = tableId;
        this.capacity = capacity;
    }

    public int getTableId() {
        return tableId;
    }

    public int getCapacity() {
        return capacity;
    }
}

