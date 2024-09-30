###############################################  Multilevel Rate limiter ############################################




import time
from collections import defaultdict

class RateLimiter:
    def __init__(self, rate, capacity):
        """
        Initialize the rate limiter with a specific rate (tokens per second) and capacity (maximum tokens).
        """
        self.rate = rate
        self.capacity = capacity
        self.tokens = capacity
        self.last_checked = time.time()

    def allow_request(self):
        """
        Returns True if the request is allowed, False otherwise.
        """
        now = time.time()
        elapsed = now - self.last_checked
        self.last_checked = now

        # Replenish tokens based on elapsed time
        self.tokens += elapsed * self.rate
        if self.tokens > self.capacity:
            self.tokens = self.capacity

        if self.tokens >= 1:
            self.tokens -= 1
            return True
        else:
            return False

class MultiLevelRateLimiter:
    def __init__(self, service_rate, resource_rate, user_rate):
        """
        Initialize rate limiters for service, resource, and user levels.
        """
        self.service_limiter = RateLimiter(service_rate[0], service_rate[1])
        self.resource_limiters = defaultdict(lambda: RateLimiter(resource_rate[0], resource_rate[1]))
        self.user_limiters = defaultdict(lambda: RateLimiter(user_rate[0], user_rate[1]))

    def allow_request(self, user_id, resource_id):
        """
        Check if a request is allowed based on service-level, resource-level, and user-level limits.
        """
        # Check service-level limit
        if not self.service_limiter.allow_request():
            print(f"Service limit exceeded.")
            return False

        # Check resource-level limit
        resource_limiter = self.resource_limiters[resource_id]
        if not resource_limiter.allow_request():
            print(f"Resource {resource_id} limit exceeded.")
            return False

        # Check user-level limit
        user_limiter = self.user_limiters[user_id]
        if not user_limiter.allow_request():
            print(f"User {user_id} limit exceeded.")
            return False

        # All limits passed
        print(f"Request allowed for user {user_id} on resource {resource_id}.")
        return True

# Testing the multi-level rate limiter

if __name__ == "__main__":
    # Initialize rate limits
    # Service-level: 5 requests per second
    # Resource-level: 3 requests per second for each resource
    # User-level: 2 requests per second per user
    limiter = MultiLevelRateLimiter(service_rate=(5, 5), resource_rate=(3, 3), user_rate=(2, 2))

    user_id = "user_1"
    resource_id = "resource_1"

    # Simulating requests
    for i in range(10):
        if limiter.allow_request(user_id, resource_id):
            print(f"Request {i + 1}: Allowed")
        else:
            print(f"Request {i + 1}: Denied")
        time.sleep(0.3)  # Simulate a delay between requests
