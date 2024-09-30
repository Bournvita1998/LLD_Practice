
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

