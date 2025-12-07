"""
Currency model implementation
Represents a currency with exchange rate information
"""
from typing import Optional


class Currency:
    """
    Model representing a currency with exchange rate information

    Attributes:
        _id (str): Unique currency identifier
        _num_code (str): Numeric code of currency
        _char_code (str): Character code of currency
        _name (str): Full name of currency
        _value (float): Exchange rate value
        _nominal (int): Nominal value
    """

    def __init__(self, num_code: str, char_code: str, name: str,
                 value: float, nominal: int = 1, currency_id: Optional[str] = None):
        """
        Initialize Currency with required fields

        Args:
            num_code: Numeric currency code (e.g., "840")
            char_code: Character currency code (e.g., "USD")
            name: Full currency name
            value: Exchange rate value
            nominal: Nominal value (default: 1)
            currency_id: Optional unique ID (generated if not provided)

        Raises:
            ValueError: If any parameter is invalid
        """
        self._id = currency_id or f"{char_code}_{num_code}"
        self.num_code = num_code
        self.char_code = char_code
        self.name = name
        self.value = value
        self.nominal = nominal

    @property
    def id(self) -> str:
        """Get currency ID"""
        return self._id

    @property
    def num_code(self) -> str:
        """Get numeric code"""
        return self._num_code

    @num_code.setter
    def num_code(self, value: str) -> None:
        """
        Set numeric code

        Args:
            value: Numeric code (must be digits only)

        Raises:
            ValueError: If value is empty or not digits
        """
        if not isinstance(value, str):
            raise ValueError("Numeric code must be a string")
        if not value.strip() or not value.strip().isdigit():
            raise ValueError("Numeric code must contain only digits")
        self._num_code = value.strip()

    @property
    def char_code(self) -> str:
        """Get character code"""
        return self._char_code

    @char_code.setter
    def char_code(self, value: str) -> None:
        """
        Set character code

        Args:
            value: Character code (3 letters)

        Raises:
            ValueError: If value is not 3 letters
        """
        if not isinstance(value, str):
            raise ValueError("Character code must be a string")
        if len(value.strip()) != 3 or not value.strip().isalpha():
            raise ValueError("Character code must be exactly 3 letters")
        self._char_code = value.strip().upper()

    @property
    def name(self) -> str:
        """Get currency name"""
        return self._name

    @name.setter
    def name(self, value: str) -> None:
        """
        Set currency name

        Args:
            value: Currency name

        Raises:
            ValueError: If value is empty or not a string
        """
        if not isinstance(value, str):
            raise ValueError("Name must be a string")
        if not value.strip():
            raise ValueError("Name cannot be empty")
        self._name = value.strip()

    @property
    def value(self) -> float:
        """Get exchange rate value"""
        return self._value

    @value.setter
    def value(self, value: float) -> None:
        """
        Set exchange rate value

        Args:
            value: New exchange rate value

        Raises:
            ValueError: If value is negative or not a number
        """
        if not isinstance(value, (int, float)):
            raise ValueError("Value must be a number")
        if value < 0:
            raise ValueError("Value cannot be negative")
        self._value = float(value)

    @property
    def nominal(self) -> int:
        """Get nominal value"""
        return self._nominal

    @nominal.setter
    def nominal(self, value: int) -> None:
        """
        Set nominal value

        Args:
            value: Nominal value

        Raises:
            ValueError: If value is not a positive integer
        """
        if not isinstance(value, int):
            raise ValueError("Nominal must be an integer")
        if value <= 0:
            raise ValueError("Nominal must be positive")
        self._nominal = value

    def get_rate_per_unit(self) -> float:
        """
        Get exchange rate per 1 unit of currency

        Returns:
            Rate per 1 unit
        """
        return self._value / self._nominal

    def __str__(self) -> str:
        """String representation of currency"""
        return f"{self.char_code} - {self.name} ({self.value}/{self.nominal})"

    def __repr__(self) -> str:
        """Detailed string representation"""
        return (f"Currency(id='{self._id}', char_code='{self.char_code}', "
                f"name='{self.name}', value={self.value}, nominal={self.nominal})")