import uuid
from threading import Lock
from datetime import datetime

# Helper function to send notifications
def send_notification(booking, user):
    print(f"Notification: Booking confirmed for {user.name} - Vehicle {booking.vehicle.model_name} from {booking.start_time} to {booking.end_time}")

# User class to represent the user entity
class User:
    def __init__(self, user_id, name):
        self.user_id = user_id
        self.name = name

# Vehicle class with the schedule variable
class Vehicle:
    def __init__(self, vehicle_id, model_name, location):
        self.vehicle_id = vehicle_id
        self.model_name = model_name
        self.location = location
        self.schedule = []  # List to store booking schedules as tuples (start_time, end_time)
        self.lock = Lock()

    def is_available(self, start_time, end_time):
        """ Check if the vehicle is available for the given time slot """
        for booked_start, booked_end in self.schedule:
            if not (end_time <= booked_start or start_time >= booked_end):
                return False  # Overlap found, vehicle not available
        return True  # No overlap, vehicle is available

    def add_booking(self, start_time, end_time):
        """ Add a new booking to the vehicle's schedule """
        self.schedule.append((start_time, end_time))

# Booking class to represent a booking entity
class Booking:
    def __init__(self, vehicle, user, start_time, end_time):
        self.booking_id = str(uuid.uuid4())  # Generate a unique UUID for the booking ID
        self.vehicle = vehicle
        self.user = user
        self.start_time = start_time
        self.end_time = end_time
        self.status = 'pending'

    def confirm_booking(self):
        self.status = 'confirmed'

# Payment class to represent a payment
class Payment:
    def __init__(self, amount, user, booking):
        self.payment_id = str(uuid.uuid4())  # Generate unique UUID for payment
        self.amount = amount
        self.user = user
        self.booking = booking
        self.status = 'pending'

    def process_payment(self):
        """ Simulate payment processing """
        print(f"Processing payment of {self.amount} for user {self.user.name} for booking {self.booking.booking_id}")
        # Simulate payment success (you can add more complex logic or integrate an external payment gateway here)
        self.status = 'success'
        print(f"Payment successful for booking {self.booking.booking_id}")
        return True

# Function to book a vehicle
def book_vehicle(vehicle, user, start_time, end_time, payment_amount):
    with vehicle.lock:
        """ Try to book a vehicle for a user within a given time slot """
        if vehicle.is_available(start_time, end_time):
            booking = Booking(vehicle, user, start_time, end_time)

            # Create a payment instance
            payment = Payment(payment_amount, user, booking)

            # Process the payment before confirming the booking
            if payment.process_payment():
                booking.confirm_booking()

                # Add the new booking time slot to the vehicle's schedule
                vehicle.add_booking(start_time, end_time)

                # Send notification to the user
                send_notification(booking, user)
                return booking
            else:
                raise Exception("Payment failed.")
        else:
            raise Exception("Vehicle is already booked for the selected time.")

# Sample usage
if __name__ == "__main__":
    # Create a vehicle and a user
    vehicle = Vehicle(vehicle_id="123", model_name="Sedan", location="Bangalore")
    user = User(user_id="abc", name="John Doe")

    # Define booking times
    start_time_1 = datetime(2024, 10, 1, 9, 0)
    end_time_1 = datetime(2024, 10, 1, 12, 0)

    start_time_2 = datetime(2024, 10, 1, 12, 30)
    end_time_2 = datetime(2024, 10, 1, 15, 0)

    # Payment amounts for the bookings
    payment_amount_1 = 1000  # Assume 1000 units of currency
    payment_amount_2 = 1500  # Assume 1500 units of currency

    # Attempt to book the vehicle
    try:
        booking1 = book_vehicle(vehicle, user, start_time_1, end_time_1, payment_amount_1)
        print(f"Booking 1 confirmed: {booking1.booking_id}")
    except Exception as e:
        print(str(e))

    try:
        booking2 = book_vehicle(vehicle, user, start_time_2, end_time_2, payment_amount_2)
        print(f"Booking 2 confirmed: {booking2.booking_id}")
    except Exception as e:
        print(str(e))

    # Print the schedule for the vehicle
    print(f"Vehicle Schedule: {vehicle.schedule}")
