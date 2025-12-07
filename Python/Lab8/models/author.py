"""
Author model implementation
Represents the application author information
"""
from typing import Optional


class Author:
    """
    Model representing the author of the application

    Attributes:
        _name (str): Author's name
        _group (str): Author's study group
    """

    def __init__(self, name: str, group: str):
        """
        Initialize Author with name and group

        Args:
            name: Author's name (must be non-empty string)
            group: Study group (must be non-empty string)

        Raises:
            ValueError: If name or group is empty or not a string
        """
        self.name = name
        self.group = group

    @property
    def name(self) -> str:
        """Get author's name"""
        return self._name

    @name.setter
    def name(self, value: str) -> None:
        """
        Set author's name

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
    def group(self) -> str:
        """Get author's group"""
        return self._group

    @group.setter
    def group(self, value: str) -> None:
        """
        Set author's group

        Args:
            value: New group value

        Raises:
            ValueError: If value is empty or not a string
        """
        if not isinstance(value, str):
            raise ValueError("Group must be a string")
        if not value.strip():
            raise ValueError("Group cannot be empty")
        self._group = value.strip()

    def __str__(self) -> str:
        """String representation of author"""
        return f"{self.name} ({self.group})"

    def __repr__(self) -> str:
        """Detailed string representation"""
        return f"Author(name='{self.name}', group='{self.group}')"