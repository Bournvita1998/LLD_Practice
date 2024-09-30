################################################# Cab Ride ##############################################






import threading
import time

# User Class
class User:
    def __init__(self, user_id: int, name: str):
        self.user_id = user_id
        self.name = name

# Cab Class
class Cab:
    def __init__(self, cab_id: int, cab_number: str, driver_name: str, is_available: bool = True):
        self.cab_id = cab_id
        self.cab_number = cab_number
        self.driver_name = driver_name
        self.is_available = is_available

    def assign_to_booking(self):
        """Mark cab as unavailable once assigned to a booking."""
        self.is_available = False

    def release(self):
        """Release cab after the ride is completed or booking is canceled."""
        self.is_available = True

# Ride Class
class Ride:
    def __init__(self, ride_id: int, user: User, cab: Cab, source: str, destination: str):
        self.ride_id = ride_id
        self.user = user
        self.cab = cab
        self.source = source
        self.destination = destination
        self.is_active = True
        self.booking_time = time.time()

    def cancel(self):
        """Cancels the ride."""
        self.is_active = False
        self.cab.release()
        print(f"Ride {self.ride_id} has been canceled.")

# RideManager Class (Singleton)
class RideManager:
    _instance = None
    _lock = threading.Lock()

    def __new__(cls):
        if cls._instance is None:
            with cls._lock:
                if cls._instance is None:
                    cls._instance = super(RideManager, cls).__new__(cls)
                    cls._instance._rides = []
        return cls._instance

    def create_ride(self, user: User, cab: Cab, source: str, destination: str) -> Ride:
        ride_id = len(self._rides) + 1
        ride = Ride(ride_id, user, cab, source, destination)
        self._rides.append(ride)
        cab.assign_to_booking()
        print(f"Ride {ride_id} created for user {user.name}.")
        return ride

    def get_ride(self, ride_id: int) -> Ride:
        for ride in self._rides:
            if ride.ride_id == ride_id:
                return ride
        return None

    def cancel_ride(self, ride_id: int):
        ride = self.get_ride(ride_id)
        if ride and ride.is_active:
            ride.cancel()
        else:
            print(f"Ride {ride_id} cannot be canceled or does not exist.")

# CabManager Class (Singleton)
class CabManager:
    _instance = None
    _lock = threading.Lock()

    def __new__(cls):
        if cls._instance is None:
            with cls._lock:
                if cls._instance is None:
                    cls._instance = super(CabManager, cls).__new__(cls)
                    cls._instance._cabs = []
        return cls._instance

    def add_cab(self, cab: Cab):
        """Add a new cab to the system."""
        self._cabs.append(cab)

    def get_available_cab(self) -> Cab:
        """Get an available cab for booking."""
        for cab in self._cabs:
            if cab.is_available:
                return cab
        return None

# Main Execution
if __name__ == "__main__":
    # Initialize Cab Manager
    cab_manager = CabManager()

    # Add some cabs
    cab1 = Cab(1, "CAB123", "John")
    cab2 = Cab(2, "CAB456", "Mike")
    cab_manager.add_cab(cab1)
    cab_manager.add_cab(cab2)

    # Initialize Ride Manager
    ride_manager = RideManager()

    # Create a user
    user = User(1, "Alice")

    # Get an available cab
    available_cab = cab_manager.get_available_cab()
    if available_cab:
        # Book a ride
        ride = ride_manager.create_ride(user, available_cab, "Location A", "Location B")
    else:
        print("No cabs available at the moment.")

    # Get the ride details
    ride_details = ride_manager.get_ride(1)
    if ride_details:
        print(f"Ride ID: {ride_details.ride_id}, Driver: {ride_details.cab.driver_name}")

    # Cancel the ride
    ride_manager.cancel_ride(1)


