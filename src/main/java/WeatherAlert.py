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
