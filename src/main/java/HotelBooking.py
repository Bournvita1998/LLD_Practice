
############################################ Hotel Booking ##############################################




from datetime import date, timedelta
from threading import Lock
from typing import List

# Data Model for Room
class Room:
    def __init__(self, room_number: int, room_type: str):
        self.room_number = room_number
        self.room_type = room_type
        self.is_available = True

    def __repr__(self):
        return f"Room({self.room_number}, {self.room_type})"

# Data Model for Booking
class Booking:
    def __init__(self, room: Room, customer_name: str, start_date: date, end_date: date):
        self.room = room
        self.customer_name = customer_name
        self.start_date = start_date
        self.end_date = end_date

    def __repr__(self):
        return f"Booking({self.room}, {self.customer_name}, {self.start_date} - {self.end_date})"

# Handles the Hotel Logic
class Hotel:
    def __init__(self, name: str, rooms: List[Room]):
        self.name = name
        self.rooms = rooms
        self.lock = Lock()  # To handle concurrency

    def check_availability(self, room_type: str, start_date: date, end_date: date) -> Room:
        with self.lock:
            for room in self.rooms:
                if room.room_type == room_type and room.is_available:
                    return room
        return None

    def mark_room_unavailable(self, room: Room):
        room.is_available = False

    def mark_room_available(self, room: Room):
        room.is_available = True

# Manages the booking process and applies business rules
class BookingManager:
    def __init__(self, hotel: Hotel):
        self.hotel = hotel
        self.bookings: List[Booking] = []

    def create_booking(self, customer_name: str, room_type: str, start_date: date, end_date: date) -> Booking:
        room = self.hotel.check_availability(room_type, start_date, end_date)

        if room is None:
            raise Exception("No rooms available for the selected dates.")

        # Once a room is found, mark it as unavailable
        self.hotel.mark_room_unavailable(room)

        # Create a booking
        booking = Booking(room, customer_name, start_date, end_date)
        self.bookings.append(booking)

        return booking

    def cancel_booking(self, booking: Booking):
        self.bookings.remove(booking)
        self.hotel.mark_room_available(booking.room)

    def list_bookings(self):
        return self.bookings

# --- Testing the functionality ---
if __name__ == "__main__":
    # Sample hotel rooms
    rooms = [Room(101, "Single"), Room(102, "Double"), Room(103, "Suite")]
    hotel = Hotel("Grand Hotel", rooms)

    # Booking manager for handling room reservations
    booking_manager = BookingManager(hotel)

    # Simulate a booking
    try:
        booking1 = booking_manager.create_booking("John Doe", "Single", date.today(), date.today() + timedelta(days=2))
        print(f"Booking created: {booking1}")

        # Attempt to book the same room - Should raise an exception due to unavailability
        booking2 = booking_manager.create_booking("Jane Doe", "Single", date.today(), date.today() + timedelta(days=2))
        print(f"Booking created: {booking2}")
    except Exception as e:
        print(f"Booking failed: {e}")

    # List bookings
    print("All bookings:", booking_manager.list_bookings())
