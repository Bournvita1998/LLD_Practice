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


