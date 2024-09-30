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

