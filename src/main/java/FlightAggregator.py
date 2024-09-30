
########################################## Flight Aggregator #############################################



from abc import ABC, abstractmethod
from typing import List

# Step 1: Define the Flight Entity
class Flight:
    def __init__(self, flight_number: str, source: str, destination: str, departure_time: str, price: float):
        self.flight_number = flight_number
        self.source = source
        self.destination = destination
        self.departure_time = departure_time
        self.price = price

    def __str__(self):
        return f"Flight {self.flight_number} from {self.source} to {self.destination}, departs at {self.departure_time}, costs ${self.price}"

# Step 2: Define an abstract base class (interface) for Airline APIs
class AirlineAPI(ABC):
    @abstractmethod
    def get_flights(self, search_criteria) -> List[Flight]:
        pass

# Step 3: Implement concrete classes for different Airline APIs
class AirlineOneAPI(AirlineAPI):
    def get_flights(self, search_criteria) -> List[Flight]:
        # Mocked data
        return [
            Flight("A1-101", search_criteria.source, search_criteria.destination, "10:00 AM", 200),
            Flight("A1-102", search_criteria.source, search_criteria.destination, "3:00 PM", 250),
        ]

class AirlineTwoAPI(AirlineAPI):
    def get_flights(self, search_criteria) -> List[Flight]:
        # Mocked data
        return [
            Flight("A2-201", search_criteria.source, search_criteria.destination, "11:00 AM", 180),
            Flight("A2-202", search_criteria.source, search_criteria.destination, "4:00 PM", 230),
        ]

# Step 4: Define the Search Criteria entity
class FlightSearchCriteria:
    def __init__(self, source: str, destination: str, date: str):
        self.source = source
        self.destination = destination
        self.date = date

# Step 5: Flight Aggregator class
class FlightAggregator:
    def __init__(self, airlines: List[AirlineAPI]):
        self.airlines = airlines

    def search_flights(self, search_criteria: FlightSearchCriteria) -> List[Flight]:
        results = []
        for airline in self.airlines:
            results.extend(airline.get_flights(search_criteria))
        return sorted(results, key=lambda x: x.price)  # Sort by price for simplicity

# Step 6: Usage
def main():
    # Create the search criteria
    search_criteria = FlightSearchCriteria(source="NYC", destination="LAX", date="2023-10-01")

    # Create instances of airline APIs
    airline_one = AirlineOneAPI()
    airline_two = AirlineTwoAPI()

    # Create the aggregator and pass the airline APIs
    aggregator = FlightAggregator(airlines=[airline_one, airline_two])

    # Search for flights
    flights = aggregator.search_flights(search_criteria)

    # Print the search results
    for flight in flights:
        print(flight)

# if __name__ == "__main__":
#     main()

