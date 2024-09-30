
############################################ Book My show ###############################################




from abc import ABC, abstractmethod
from threading import Lock

# Data model for Seats
class Seat:
    def __init__(self, seat_id: int):
        self.seat_id = seat_id
        self.is_booked = False

    def book_seat(self):
        self.is_booked = True

    def unbook_seat(self):
        self.is_booked = False


# Movie show details
class MovieShow:
    def __init__(self, movie_name: str, show_time: str, available_seats: list[Seat]):
        self.movie_name = movie_name
        self.show_time = show_time
        self.available_seats = available_seats

    def get_available_seats(self):
        return [seat for seat in self.available_seats if not seat.is_booked]


# Theater class to manage multiple movie shows
class Theater:
    def __init__(self, theater_name: str, shows: list[MovieShow]):
        self.theater_name = theater_name
        self.shows = shows

    def get_movie_show(self, movie_name: str, show_time: str):
        for show in self.shows:
            if show.movie_name == movie_name and show.show_time == show_time:
                return show
        return None


# Booking service class following SRP for managing bookings
class BookingService:
    def __init__(self):
        self.lock = Lock()

    def book_seats(self, user: "User", theater: Theater, movie_name: str, show_time: str, seat_ids: list[int]):
        show = theater.get_movie_show(movie_name, show_time)
        if not show:
            raise ValueError("Invalid show details")

        available_seats = show.get_available_seats()
        selected_seats = [seat for seat in available_seats if seat.seat_id in seat_ids]

        if len(selected_seats) < len(seat_ids):
            raise ValueError("Some seats are already booked")

        # Handling concurrency with a lock
        with self.lock:
            for seat in selected_seats:
                seat.book_seat()

            print(f"Booking successful for user {user.name}. Seats: {[seat.seat_id for seat in selected_seats]}")


# User class
class User:
    def __init__(self, user_id: int, name: str):
        self.user_id = user_id
        self.name = name


# Payment service interface for testability
class PaymentService(ABC):
    @abstractmethod
    def process_payment(self, user: User, amount: float):
        pass


# Simple implementation of PaymentService
class DummyPaymentService(PaymentService):
    def process_payment(self, user: User, amount: float):
        print(f"Processing payment of ${amount} for user {user.name}")
        return True  # Simulate a successful payment


# Example usage

# Seats and shows setup
seats = [Seat(i) for i in range(1, 11)]  # 10 seats
show1 = MovieShow("Movie A", "12:00 PM", seats)
show2 = MovieShow("Movie B", "03:00 PM", seats)
theater = Theater("Awesome Theater", [show1, show2])

# Users and Booking service
user1 = User(1, "Alice")
user2 = User(2, "Bob")

booking_service = BookingService()
payment_service = DummyPaymentService()

# Booking tickets
try:
    booking_service.book_seats(user1, theater, "Movie A", "12:00 PM", [1, 2, 3])
    payment_service.process_payment(user1, 30.0)  # Assuming 10 per ticket
except ValueError as e:
    print(f"Booking failed: {e}")