"""
UserCurrency model implementation
Represents the relationship between users and their subscribed currencies
"""
from typing import Optional
import uuid


class UserCurrency:
    """
    Model representing the many-to-many relationship between users and currencies

    Attributes:
        _id (str): Unique relationship identifier
        _user_id (str): User ID
        _currency_id (str): Currency ID
        _notification_threshold (float): Threshold for price change notifications
    """

    def __init__(self, user_id: str, currency_id: str,
                 notification_threshold: Optional[float] = None,
                 relationship_id: Optional[str] = None):
        """
        Initialize UserCurrency relationship

        Args:
            user_id: User ID
            currency_id: Currency ID
            notification_threshold: Optional threshold for notifications
            relationship_id: Optional unique ID (generated if not provided)

        Raises:
            ValueError: If user_id or currency_id is empty
        """
        self._id = relationship_id or str(uuid.uuid4())
        self.user_id = user_id
        self.currency_id = currency_id
        self.notification_threshold = notification_threshold

    @property
    def id(self) -> str:
        """Get relationship ID"""
        return self._id

    @property
    def user_id(self) -> str:
        """Get user ID"""
        return self._user_id

    @user_id.setter
    def user_id(self, value: str) -> None:
        """
        Set user ID

        Args:
            value: User ID

        Raises:
            ValueError: If value is empty or not a string
        """
        if not isinstance(value, str):
            raise ValueError("User ID must be a string")
        if not value.strip():
            raise ValueError("User ID cannot be empty")
        self._user_id = value.strip()

    @property
    def currency_id(self) -> str:
        """Get currency ID"""
        return self._currency_id

    @currency_id.setter
    def currency_id(self, value: str) -> None:
        """
        Set currency ID

        Args:
            value: Currency ID

        Raises:
            ValueError: If value is empty or not a string
        """
        if not isinstance(value, str):
            raise ValueError("Currency ID must be a string")
        if not value.strip():
            raise ValueError("Currency ID cannot be empty")
        self._currency_id = value.strip()

    @property
    def notification_threshold(self) -> Optional[float]:
        """Get notification threshold"""
        return self._notification_threshold

    @notification_threshold.setter
    def notification_threshold(self, value: Optional[float]) -> None:
        """
        Set notification threshold

        Args:
            value: Threshold value or None

        Raises:
            ValueError: If value is negative or not a number
        """
        if value is not None:
            if not isinstance(value, (int, float)):
                raise ValueError("Notification threshold must be a number")
            if value < 0:
                raise ValueError("Notification threshold cannot be negative")
            self._notification_threshold = float(value)
        else:
            self._notification_threshold = None

    def __str__(self) -> str:
        """String representation of relationship"""
        return f"UserCurrency: {self._user_id[:8]}... -> {self._currency_id}"

    def __repr__(self) -> str:
        """Detailed string representation"""
        return (f"UserCurrency(id='{self._id}', user_id='{self._user_id}', "
                f"currency_id='{self._currency_id}', threshold={self._notification_threshold})")