############################################### LRU Cache ############################################


class Node:
    """
    Node class representing each element in the doubly linked list.
    Each node stores the key, value, and references to the previous and next nodes.
    """
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.prev = None
        self.next = None


class LRUCache:
    """
    LRUCache class implementing the Least Recently Used (LRU) caching mechanism.
    It uses a HashMap (dictionary) for fast access and a doubly linked list for
    maintaining the order of usage (most recent to least recent).
    """
    
    def __init__(self, capacity: int):
        """
        Initializes the cache with a given capacity. Also creates dummy head and tail
        nodes for ease of list manipulation.
        
        :param capacity: The maximum number of items the cache can hold.
        """
        self.capacity = capacity
        self.cache = {}  # HashMap to store key-node pairs for O(1) access.
        
        # Dummy head and tail nodes to simplify list operations.
        self.head = Node(0, 0)
        self.tail = Node(0, 0)
        self.head.next = self.tail
        self.tail.prev = self.head

    def remove(self, node: Node):
        """
        Removes a node from the doubly linked list.
        
        :param node: The node to be removed.
        """
        prev_node = node.prev
        next_node = node.next
        prev_node.next = next_node
        next_node.prev = prev_node

    def add_to_front(self, node: Node):
        """
        Adds a node to the front (right after the head) of the doubly linked list.
        
        :param node: The node to be added.
        """
        first_node = self.head.next
        self.head.next = node
        node.prev = self.head
        node.next = first_node
        first_node.prev = node

    def get(self, key: int) -> int:
        """
        Returns the value of the given key if it exists in the cache. Moves the
        node corresponding to the key to the front (most recently used position).
        
        :param key: The key to retrieve from the cache.
        :return: The value associated with the key or -1 if the key is not found.
        """
        if key in self.cache:
            node = self.cache[key]
            self.remove(node)
            self.add_to_front(node)
            return node.value
        return -1

    def put(self, key: int, value: int) -> None:
        """
        Inserts a key-value pair into the cache. If the key already exists, updates
        its value and moves it to the front (most recently used position). If the
        cache exceeds capacity, evicts the least recently used item (node before tail).
        
        :param key: The key to insert/update.
        :param value: The value to associate with the key.
        """
        if key in self.cache:
            # Update the value and move the node to the front.
            node = self.cache[key]
            self.remove(node)
            node.value = value
            self.add_to_front(node)
        else:
            if len(self.cache) >= self.capacity:
                # Remove the least recently used node (before the tail).
                lru_node = self.tail.prev
                self.remove(lru_node)
                del self.cache[lru_node.key]
            
            # Insert the new node.
            new_node = Node(key, value)
            self.add_to_front(new_node)
            self.cache[key] = new_node

            
            
# cache = LRUCache(2)
# cache.put(1, 1)   # Cache is {1=1}
# cache.put(2, 2)   # Cache is {1=1, 2=2}
# print(cache.get(1)) # Returns 1 and moves 1 to the front
# cache.put(3, 3)   # Evicts key 2 and inserts 3. Cache is {1=1, 3=3}
# print(cache.get(2)) # Returns -1 (key 2 is evicted)





















############################################### Weather alerts ###########################################


from abc import ABC, abstractmethod
from typing import List
import random
import time

# WeatherAlert class
class WeatherAlert:
    def __init__(self, alert_id: str, alert_type: str, severity: str, location: str, timestamp: str):
        self.alert_id = alert_id
        self.alert_type = alert_type
        self.severity = severity
        self.location = location
        self.timestamp = timestamp

    def __str__(self):
        return f"Alert ID: {self.alert_id}, Type: {self.alert_type}, Severity: {self.severity}, Location: {self.location}, Time: {self.timestamp}"

# UserSubscription class
class UserSubscription:
    def __init__(self, user_id: str, location: str, mediums: List[str]):
        self.user_id = user_id
        self.location = location
        self.mediums = mediums
        self.is_active = True

    def update_preferences(self, mediums: List[str]):
        self.mediums = mediums

    def deactivate_subscription(self):
        self.is_active = False

# NotificationStrategy interface (abstract class in Python)
class NotificationStrategy(ABC):
    @abstractmethod
    def send_notification(self, alert: WeatherAlert, subscription: UserSubscription):
        pass

# Concrete Strategy classes for different notification methods
class SmsNotificationStrategy(NotificationStrategy):
    def send_notification(self, alert: WeatherAlert, subscription: UserSubscription):
        print(f"Sending SMS to {subscription.user_id} for {alert}")

class EmailNotificationStrategy(NotificationStrategy):
    def send_notification(self, alert: WeatherAlert, subscription: UserSubscription):
        print(f"Sending Email to {subscription.user_id} for {alert}")

class PushNotificationStrategy(NotificationStrategy):
    def send_notification(self, alert: WeatherAlert, subscription: UserSubscription):
        print(f"Sending Push Notification to {subscription.user_id} for {alert}")

# AlertDispatcher class that uses strategies to dispatch alerts
class AlertDispatcher:
    def __init__(self):
        self.strategies = {
            "SMS": SmsNotificationStrategy(),
            "EMAIL": EmailNotificationStrategy(),
            "PUSH": PushNotificationStrategy()
        }

    def dispatch_alert(self, alert: WeatherAlert, subscription: UserSubscription):
        for medium in subscription.mediums:
            strategy = self.strategies.get(medium)
            if strategy:
                strategy.send_notification(alert, subscription)

# SubscriptionService class to manage user subscriptions
class SubscriptionService:
    def __init__(self):
        self.subscriptions = []

    def add_subscription(self, subscription: UserSubscription):
        self.subscriptions.append(subscription)

    def get_subscriptions_by_location(self, location: str):
        return [sub for sub in self.subscriptions if sub.location == location and sub.is_active]

# AlertHandler class to process weather alerts and dispatch them
class AlertHandler:
    def __init__(self, subscription_service: SubscriptionService, alert_dispatcher: AlertDispatcher):
        self.subscription_service = subscription_service
        self.alert_dispatcher = alert_dispatcher

    def process_weather_alert(self, alert: WeatherAlert):
        print(f"Processing alert: {alert}")
        subscriptions = self.subscription_service.get_subscriptions_by_location(alert.location)
        for subscription in subscriptions:
            self.alert_dispatcher.dispatch_alert(alert, subscription)

# Simulate the system with example alerts and users
# if __name__ == "__main__":
#     # Initialize services
#     subscription_service = SubscriptionService()
#     alert_dispatcher = AlertDispatcher()
#     alert_handler = AlertHandler(subscription_service, alert_dispatcher)

#     # Add some user subscriptions
#     user1 = UserSubscription(user_id="user1", location="New York", mediums=["SMS", "EMAIL"])
#     user2 = UserSubscription(user_id="user2", location="Los Angeles", mediums=["PUSH", "EMAIL"])
#     user3 = UserSubscription(user_id="user3", location="New York", mediums=["PUSH", "SMS"])

#     subscription_service.add_subscription(user1)
#     subscription_service.add_subscription(user2)
#     subscription_service.add_subscription(user3)

#     # Simulate incoming weather alerts
#     alerts = [
#         WeatherAlert(alert_id="A1", alert_type="Storm", severity="Severe", location="New York", timestamp="2024-09-22 14:00"),
#         WeatherAlert(alert_id="A2", alert_type="Heatwave", severity="Moderate", location="Los Angeles", timestamp="2024-09-22 15:00")
#     ]

#     # Process each alert
#     for alert in alerts:
#         alert_handler.process_weather_alert(alert)
#         time.sleep(1)  # Simulating a delay between alerts





        
        
        
        
        
        
        
        

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
############################################### News notification ########################################
        
        
        
        
        
import threading
from abc import ABC, abstractmethod
from typing import List, Dict

# --- NewsArticle class ---
class NewsArticle:
    def __init__(self, title: str, content: str):
        self.title = title
        self.content = content

# --- Observer pattern (SOLID - Dependency Inversion) ---
class Subscriber(ABC):
    @abstractmethod
    def notify(self, article: NewsArticle):
        pass

class EmailSubscriber(Subscriber):
    def __init__(self, email: str):
        self.email = email

    def notify(self, article: NewsArticle):
        print(f"Email sent to {self.email}: New article titled '{article.title}'")

class SMSSubscriber(Subscriber):
    def __init__(self, phone_number: str):
        self.phone_number = phone_number

    def notify(self, article: NewsArticle):
        print(f"SMS sent to {self.phone_number}: New article titled '{article.title}'")

# --- Publisher class (SOLID - Open/Closed Principle) ---
class NewsPublisher:
    def __init__(self):
        self.subscribers: List[Subscriber] = []
        self.articles: List[NewsArticle] = []

    def add_subscriber(self, subscriber: Subscriber):
        self.subscribers.append(subscriber)

    def remove_subscriber(self, subscriber: Subscriber):
        self.subscribers.remove(subscriber)

    def notify_subscribers(self, article: NewsArticle):
        for subscriber in self.subscribers:
            subscriber.notify(article)

    def publish_article(self, title: str, content: str):
        article = NewsArticle(title, content)
        self.articles.append(article)
        print(f"Article published: {article.title}")
        self.notify_subscribers(article)

# --- Singleton pattern for shared notification system ---
class NotificationSystem:
    _instance = None
    _lock = threading.Lock()

    def __new__(cls):
        with cls._lock:
            if cls._instance is None:
                cls._instance = super().__new__(cls)
                cls._instance.publisher = NewsPublisher()
            return cls._instance

# --- Testing concurrency with threading ---
def simulate_article_publishing(system: NotificationSystem, article_data: List[Dict[str, str]]):
    for data in article_data:
        system.publisher.publish_article(data["title"], data["content"])

# if __name__ == "__main__":
#     # Create notification system (singleton)
#     system = NotificationSystem()

#     # Subscribers
#     email_subscriber = EmailSubscriber("john.doe@example.com")
#     sms_subscriber = SMSSubscriber("+123456789")

#     # Register subscribers
#     system.publisher.add_subscriber(email_subscriber)
#     system.publisher.add_subscriber(sms_subscriber)

#     # Simulate article publishing with threads (Concurrency handling)
#     article_data_1 = [{"title": "News 1", "content": "Content 1"}, {"title": "News 2", "content": "Content 2"}]
#     article_data_2 = [{"title": "News 3", "content": "Content 3"}, {"title": "News 4", "content": "Content 4"}]

#     thread1 = threading.Thread(target=simulate_article_publishing, args=(system, article_data_1))
#     thread2 = threading.Thread(target=simulate_article_publishing, args=(system, article_data_2))

#     thread1.start()
#     thread2.start()

#     thread1.join()
#     thread2.join()


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
############################################### Coffee maker #############################################

    
    
    
    
    
    
from abc import ABC, abstractmethod

# Coffee Beans Class - A simple class to hold the amount of coffee beans
class CoffeeBeans:
    def __init__(self, quantity_in_grams: int):
        self.quantity_in_grams = quantity_in_grams

# Water Class - Similar to coffee beans, holds water level in milliliters
class Water:
    def __init__(self, quantity_in_milliliters: int):
        self.quantity_in_milliliters = quantity_in_milliliters

# Grinder class for grinding coffee beans (modular component)
class Grinder:
    def grind(self, beans: CoffeeBeans) -> str:
        if beans.quantity_in_grams > 0:
            return "Ground coffee ready."
        else:
            raise Exception("Not enough coffee beans.")

# Water Heater Class - Heats up the water for brewing
class WaterHeater:
    def heat(self, water: Water) -> str:
        if water.quantity_in_milliliters > 0:
            return "Water heated."
        else:
            raise Exception("Not enough water.")

# Abstract base class for Coffee Types (Liskov Substitution)
class Coffee(ABC):
    @abstractmethod
    def brew(self) -> str:
        pass

# Espresso Coffee Class - implements Coffee
class Espresso(Coffee):
    def brew(self) -> str:
        return "Brewing a rich Espresso."

# Cappuccino Coffee Class - implements Coffee
class Cappuccino(Coffee):
    def brew(self) -> str:
        return "Brewing a frothy Cappuccino."

# Coffee Machine Class - Composed of multiple components
class CoffeeMaker:
    def __init__(self, grinder: Grinder, water_heater: WaterHeater):
        self.grinder = grinder
        self.water_heater = water_heater
        self.clean_state = True  # Initially clean

    def add_water(self, water: Water):
        self.water = water

    def add_coffee_beans(self, beans: CoffeeBeans):
        self.beans = beans

    def brew_coffee(self, coffee: Coffee):
        if not self.clean_state:
            raise Exception("Machine needs to be cleaned before next brew.")
        
        # Grind coffee beans
        print(self.grinder.grind(self.beans))

        # Heat water
        print(self.water_heater.heat(self.water))

        # Brew coffee
        print(coffee.brew())
        print("Coffee is ready!")
        
        # Mark as dirty
        self.clean_state = False

    def clean_machine(self):
        print("Cleaning the coffee maker...")
        self.clean_state = True

# Factory Pattern for creating coffee objects
class CoffeeFactory:
    @staticmethod
    def create_coffee(coffee_type: str) -> Coffee:
        if coffee_type == 'espresso':
            return Espresso()
        elif coffee_type == 'cappuccino':
            return Cappuccino()
        else:
            raise ValueError(f"Unknown coffee type: {coffee_type}")

# Main function to simulate the usage
# if __name__ == "__main__":
#     # Create the necessary components
#     grinder = Grinder()
#     water_heater = WaterHeater()

#     # Create the coffee maker machine
#     coffee_maker = CoffeeMaker(grinder, water_heater)

#     # Add water and coffee beans
#     water = Water(200)  # 200 ml water
#     beans = CoffeeBeans(20)  # 20 grams of beans
#     coffee_maker.add_water(water)
#     coffee_maker.add_coffee_beans(beans)

#     # Brew espresso
#     coffee = CoffeeFactory.create_coffee("espresso")
#     coffee_maker.brew_coffee(coffee)

#     # Clean the machine
#     coffee_maker.clean_machine()

#     # Brew cappuccino
#     coffee = CoffeeFactory.create_coffee("cappuccino")
#     coffee_maker.brew_coffee(coffee)

    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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

    

    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
########################################## Wallet Service ################################################

    
    
    
from abc import ABC, abstractmethod
from threading import Lock

# User entity (Single Responsibility)
class User:
    def __init__(self, user_id: str, name: str):
        self.user_id = user_id
        self.name = name


# Wallet Interface for future extensibility (Open/Closed principle)
class WalletInterface(ABC):
    @abstractmethod
    def deposit(self, amount: float):
        pass

    @abstractmethod
    def withdraw(self, amount: float):
        pass

    @abstractmethod
    def transfer(self, amount: float, to_wallet: 'Wallet'):
        pass

    @abstractmethod
    def get_balance(self) -> float:
        pass


# Concrete Wallet class (Liskov Substitution)
class Wallet(WalletInterface):
    def __init__(self, user: User):
        self.user = user
        self.balance = 0.0
        self.transaction_history = []
        self._lock = Lock()  # To handle concurrency
        
    def deposit(self, amount: float):
        if amount <= 0:
            raise ValueError("Deposit amount must be positive.")
        # Only lock the part of the code modifying the balance
        with self._lock:
            self.balance += amount
        # Append outside the lock to avoid lock contention
        self.transaction_history.append(f"Deposited {amount}")
    
    def withdraw(self, amount: float):
        if amount <= 0:
            raise ValueError("Withdrawal amount must be positive.")
        with self._lock:
            if self.balance < amount:
                raise ValueError("Insufficient balance.")
            self.balance -= amount
        self.transaction_history.append(f"Withdrew {amount}")
    
    def transfer(self, amount: float, to_wallet: 'Wallet'):
        if not isinstance(to_wallet, Wallet):
            raise ValueError("Invalid wallet for transfer.")
        if amount <= 0:
            raise ValueError("Transfer amount must be positive.")
        
        # Lock both wallets to avoid race conditions, but acquire locks in the same order
        # to avoid deadlocks. Always lock the current wallet first.
        with self._lock:
            if self.balance < amount:
                raise ValueError("Insufficient balance.")
            self.balance -= amount
        with to_wallet._lock:
            to_wallet.balance += amount

        # Log the transactions outside the locks to avoid delays
        self.transaction_history.append(f"Transferred {amount} to {to_wallet.user.name}")
        to_wallet.transaction_history.append(f"Received {amount} from {self.user.name}")
    
    def get_balance(self) -> float:
        with self._lock:
            return self.balance

    def get_transaction_history(self):
        return self.transaction_history


# Service layer for Wallet management
class WalletService:
    def __init__(self):
        # In-memory storage of users and wallets
        self.wallets = {}

    def create_wallet(self, user: User):
        if user.user_id in self.wallets:
            raise ValueError("User already has a wallet.")
        wallet = Wallet(user)
        self.wallets[user.user_id] = wallet

    def get_wallet(self, user_id: str) -> Wallet:
        if user_id not in self.wallets:
            raise ValueError("Wallet not found for this user.")
        return self.wallets[user_id]

    def deposit_to_wallet(self, user_id: str, amount: float):
        wallet = self.get_wallet(user_id)
        wallet.deposit(amount)

    def withdraw_from_wallet(self, user_id: str, amount: float):
        wallet = self.get_wallet(user_id)
        wallet.withdraw(amount)

    def transfer_between_wallets(self, from_user_id: str, to_user_id: str, amount: float):
        from_wallet = self.get_wallet(from_user_id)
        to_wallet = self.get_wallet(to_user_id)
        from_wallet.transfer(amount, to_wallet)

    def get_wallet_balance(self, user_id: str) -> float:
        wallet = self.get_wallet(user_id)
        return wallet.get_balance()

    def get_user_transaction_history(self, user_id: str):
        wallet = self.get_wallet(user_id)
        return wallet.get_transaction_history()


# Testing the Wallet System directly in code
if __name__ == '__main__':
    wallet_service = WalletService()

    # Create two users
    user1 = User(user_id="1", name="Alice")
    user2 = User(user_id="2", name="Bob")

    # Create wallets for both users
    wallet_service.create_wallet(user1)
    wallet_service.create_wallet(user2)

    # Deposit to Alice's wallet
    print("Depositing 100 to Alice's wallet...")
    wallet_service.deposit_to_wallet(user1.user_id, 100.0)
    print(f"Alice's balance: {wallet_service.get_wallet_balance(user1.user_id)}")

    # Withdraw from Alice's wallet
    print("\nWithdrawing 40 from Alice's wallet...")
    wallet_service.withdraw_from_wallet(user1.user_id, 40.0)
    print(f"Alice's balance: {wallet_service.get_wallet_balance(user1.user_id)}")

    # Transfer from Alice to Bob
    print("\nTransferring 50 from Alice to Bob...")
    wallet_service.transfer_between_wallets(user1.user_id, user2.user_id, 50.0)
    print(f"Alice's balance: {wallet_service.get_wallet_balance(user1.user_id)}")
    print(f"Bob's balance: {wallet_service.get_wallet_balance(user2.user_id)}")

    # View transaction histories
    print("\nAlice's transaction history:")
    for transaction in wallet_service.get_user_transaction_history(user1.user_id):
        print(transaction)

    print("\nBob's transaction history:")
    for transaction in wallet_service.get_user_transaction_history(user2.user_id):
        print(transaction)

    # Attempting an invalid operation (withdrawing too much)
    print("\nAttempting to withdraw 100 from Alice's wallet (should fail)...")
    try:
        wallet_service.withdraw_from_wallet(user1.user_id, 100.0)
    except ValueError as e:
        print(f"Error: {e}")

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    




    
    
    
    
    
    
    
    
    
########################################## Product Review ###############################################

    
    
    
    
from threading import Lock
from typing import List, Dict

class User:
    def __init__(self, user_id: int, name: str):
        self.user_id = user_id
        self.name = name


class Product:
    def __init__(self, product_id: int, name: str):
        self.product_id = product_id
        self.name = name


class Review:
    def __init__(self, user: User, product: Product, rating: int, comment: str):
        self.user_id = user.user_id
        self.product_id = product.product_id
        self.rating = rating
        self.comment = comment

    def __repr__(self):
        return f"Review(user={self.user_id}, product={self.product_id}, rating={self.rating}, comment='{self.comment}')"


class ReviewStorage:
    def __init__(self):
        self.reviews: Dict[int, List[Review]] = {}
        self.locks: Dict[int, Lock] = {}  # Locks per product

    def add_review(self, review: Review):
        if review.product_id not in self.locks:
            self.locks[review.product_id] = Lock()

        with self.locks[review.product_id]:
            if review.product_id not in self.reviews:
                self.reviews[review.product_id] = []
            self.reviews[review.product_id].append(review)

    def get_reviews(self, product_id: int) -> List[Review]:
        return self.reviews.get(product_id, [])

    def find_user_review(self, user_id: int, product_id: int) -> Review:
        product_reviews = self.reviews.get(product_id, [])
        for review in product_reviews:
            if review.user_id == user_id:
                return review
        return None

    def update_review(self, review: Review):
        with self.locks[review.product_id]:
            product_reviews = self.reviews.get(review.product_id, [])
            for i, r in enumerate(product_reviews):
                if r.user_id == review.user_id:
                    product_reviews[i] = review  # Update the review

    def delete_review(self, user_id: int, product_id: int):
        with self.locks[product_id]:
            self.reviews[product_id] = [
                review for review in self.reviews.get(product_id, []) if review.user_id != user_id
            ]


class ReviewManager:
    def __init__(self, storage: ReviewStorage):
        self.storage = storage

    def create_review(self, user: User, product: Product, rating: int, comment: str) -> str:
        # Check if user already has a review for this product
        existing_review = self.storage.find_user_review(user.user_id, product.product_id)
        if existing_review:
            return "User has already reviewed this product."

        # Create new review
        review = Review(user, product, rating, comment)
        self.storage.add_review(review)
        return "Review created successfully."

    def update_review(self, user: User, product: Product, rating: int, comment: str) -> str:
        # Check if user has a review to update
        existing_review = self.storage.find_user_review(user.user_id, product.product_id)
        if not existing_review:
            return "No review found to update."

        # Update review
        updated_review = Review(user, product, rating, comment)
        self.storage.update_review(updated_review)
        return "Review updated successfully."

    def delete_review(self, user: User, product: Product) -> str:
        # Check if user has a review to delete
        existing_review = self.storage.find_user_review(user.user_id, product.product_id)
        if not existing_review:
            return "No review found to delete."

        # Delete review
        self.storage.delete_review(user.user_id, product.product_id)
        return "Review deleted successfully."

    def get_reviews(self, product: Product) -> List[Review]:
        # Retrieve all reviews for a product
        return self.storage.get_reviews(product.product_id)

    def get_average_rating(self, product: Product) -> float:
        # Retrieve all reviews for a product
        reviews = self.get_reviews(product)

        if not reviews:
            return 0.0  # No reviews, return 0 as average rating

        # Calculate the average rating
        total_rating = sum(review.rating for review in reviews)
        return total_rating / len(reviews)



# main without Average Rating
# if __name__ == "__main__":
#     user1 = User(1, "John Doe")
#     product1 = Product(101, "Smartphone")

#     storage = ReviewStorage()
#     review_manager = ReviewManager(storage)

#     # Create a review
#     print(review_manager.create_review(user1, product1, 5, "Great product!"))

#     # Try to create another review (should fail due to limit)
#     print(review_manager.create_review(user1, product1, 4, "Updated review"))

#     # Update the review
#     print(review_manager.update_review(user1, product1, 4, "Still a good product"))

#     # Get all reviews for the product
#     print(review_manager.get_reviews(product1))

#     # Delete the review
#     print(review_manager.delete_review(user1, product1))

#     # Get all reviews after deletion
#     print(review_manager.get_reviews(product1))
    

# main for Average Rating
if __name__ == "__main__":
    user1 = User(1, "John Doe")
    user2 = User(2, "Jane Smith")
    product1 = Product(101, "Smartphone")

    storage = ReviewStorage()
    review_manager = ReviewManager(storage)

    # Create reviews from two different users
    print(review_manager.create_review(user1, product1, 5, "Great product!"))
    print(review_manager.create_review(user2, product1, 3, "It's okay"))

    # Get all reviews
    print("All reviews:", review_manager.get_reviews(product1))

    # Calculate and print the average rating
    print("Average rating for product:", review_manager.get_average_rating(product1))  # Should output 4.0

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
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

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    







#################################################### Zepto ##############################################


from typing import List, Dict
import threading
import uuid
from datetime import datetime, timedelta

# ----- Models -----

class User:
    def __init__(self, user_id: str, name: str, address: str):
        self.user_id = user_id
        self.name = name
        self.address = address


class Product:
    def __init__(self, product_id: str, name: str, price: float, quantity: int):
        self.product_id = product_id
        self.name = name
        self.price = price
        self.quantity = quantity


class Order:
    def __init__(self, user: User, products: List[Product]):
        self.order_id = str(uuid.uuid4())
        self.user = user
        self.products = products
        self.total_amount = sum([product.price for product in products])
        self.timestamp = datetime.now()

    def __str__(self):
        return f"Order(id={self.order_id}, user={self.user.name}, total={self.total_amount})"


class DeliverySlot:
    def __init__(self, slot_time: datetime):
        self.slot_time = slot_time
        self.is_available = True
        self.lock = threading.Lock()

    def book_slot(self) -> bool:
        with self.lock:
            if self.is_available:
                self.is_available = False
                return True
            return False


class Inventory:
    _instance = None

    def __init__(self):
        if not Inventory._instance:
            self.products: Dict[str, Product] = {}

    @classmethod
    def get_instance(cls):
        if cls._instance is None:
            cls._instance = Inventory()
        return cls._instance

    def add_product(self, product: Product):
        self.products[product.product_id] = product

    def update_product_quantity(self, product_id: str, quantity: int) -> bool:
        product = self.products.get(product_id)
        if product and product.quantity >= quantity:
            product.quantity -= quantity
            return True
        return False

    def get_available_products(self) -> List[Product]:
        return [product for product in self.products.values() if product.quantity > 0]


# ----- Services -----

class BookingService:
    def __init__(self):
        self.slots: List[DeliverySlot] = self.generate_slots()

    def generate_slots(self) -> List[DeliverySlot]:
        current_time = datetime.now()
        slots = []
        for i in range(10):  # 10 slots for 10-min delivery intervals
            slot_time = current_time + timedelta(minutes=(i * 10))
            slots.append(DeliverySlot(slot_time))
        return slots

    def find_available_slot(self) -> DeliverySlot:
        for slot in self.slots:
            if slot.is_available:
                return slot
        return None

    def book_order(self, user: User, products: List[Product]) -> str:
        inventory = Inventory.get_instance()
        # Check availability of all products
        for product in products:
            if not inventory.update_product_quantity(product.product_id, 1):
                return f"Product {product.name} is out of stock."

        # Find and book delivery slot
        slot = self.find_available_slot()
        if not slot or not slot.book_slot():
            return "No delivery slots available."

        # Create and return the order
        order = Order(user, products)
        self.notify_user(order)
        return f"Order booked successfully: {order}"

    def notify_user(self, order: Order):
        NotificationService.notify(order.user, f"Your order {order.order_id} is confirmed!")

class Payment:
    @staticmethod
    def process_payment(order: Order) -> bool:
        # Mock payment process
        return True


class NotificationService:
    @staticmethod
    def notify(user: User, message: str):
        print(f"Notification sent to {user.name}: {message}")


# ----- Example Usage -----

def main():
    user = User("U001", "Alice", "123 Street, City")
    
    product1 = Product("P001", "Apple", 1.0, 50)
    product2 = Product("P002", "Banana", 0.5, 100)
    product3 = Product("P003", "Orange", 0.8, 0)  # Out of stock
    
    # Add products to inventory
    inventory = Inventory.get_instance()
    inventory.add_product(product1)
    inventory.add_product(product2)
    inventory.add_product(product3)
    
    # Get available products
    available_products = inventory.get_available_products()
    print("Available Products:")
    for product in available_products:
        print(f"{product.name} - ${product.price} (Quantity: {product.quantity})")
    
    # Create booking service
    booking_service = BookingService()
    
    # User attempts to book an order
    result = booking_service.book_order(user, [product1, product2])
    print(result)


if __name__ == "__main__":
    main()










