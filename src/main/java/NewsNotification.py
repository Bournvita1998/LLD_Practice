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