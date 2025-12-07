"""
User model implementation
Represents a user in the system
"""
from typing import Optional, Dict, Any
import uuid
from .user_currency import UserCurrency


class User:
    """
    Model representing a user in the system

    Attributes:
        _id (str): Unique user identifier
        _name (str): User's name
    """

    def __init__(self, name: str, user_id: Optional[str] = None):
        """
        Initialize User with name and optional ID

        Args:
            name: User's name (must be non-empty string)
            user_id: Optional user ID (generated if not provided)

        Raises:
            ValueError: If name is empty or not a string
        """
        self._id = user_id or str(uuid.uuid4())
        self.name = name
        self._subscriptions: Dict[str, UserCurrency] = {}

    @property
    def id(self) -> str:
        """Get user ID"""
        return self._id

    @property
    def name(self) -> str:
        """Get user's name"""
        return self._name

    @name.setter
    def name(self, value: str) -> None:
        """
        Set user's name

        Args:
            value: New name value

        Raises:
            ValueError: If value is empty or not a string
        """
        if not isinstance(value, str):
            raise ValueError("Name must be a string")
        if not value.strip():
            raise ValueError("Name cannot be empty")
        self._name = value.strip()

    @property
    def subscriptions(self) -> Dict[str, UserCurrency]:
        """Get user's currency subscriptions"""
        return self._subscriptions.copy()

    def add_subscription(self, subscription: UserCurrency) -> None:
        """
        Add a currency subscription

        Args:
            subscription: UserCurrency object to add

        Raises:
            TypeError: If subscription is not a UserCurrency instance
            ValueError: If user_id doesn't match this user's ID
        """
        if not isinstance(subscription, UserCurrency):
            raise TypeError("Subscription must be a UserCurrency instance")
        if subscription.user_id != self._id:
            raise ValueError("Subscription user_id doesn't match this user")
        self._subscriptions[subscription.currency_id] = subscription

    def remove_subscription(self, currency_id: str) -> None:
        """
        Remove a currency subscription

        Args:
            currency_id: Currency ID to remove
        """
        if currency_id in self._subscriptions:
            del self._subscriptions[currency_id]

    def get_subscription(self, currency_id: str) -> Optional[UserCurrency]:
        """
        Get a specific subscription

        Args:
            currency_id: Currency ID

        Returns:
            UserCurrency object or None if not found
        """
        return self._subscriptions.get(currency_id)

    def __str__(self) -> str:
        """String representation of user"""
        return f"User: {self.name} (ID: {self._id[:8]}...)"

    def __repr__(self) -> str:
        """Detailed string representation"""
        return f"User(id='{self._id}', name='{self.name}', subscriptions={len(self._subscriptions)})"