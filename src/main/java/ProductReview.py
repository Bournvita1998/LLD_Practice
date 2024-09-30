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

