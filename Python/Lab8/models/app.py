"""
App model implementation
Represents the application metadata
"""
from typing import Optional
from .author import Author


class App:
    """
    Model representing the application metadata

    Attributes:
        _name (str): Application name
        _version (str): Application version
        _author (Author): Application author
    """

    def __init__(self, name: str, version: str, author: Author):
        """
        Initialize App with name, version and author

        Args:
            name: Application name (must be non-empty string)
            version: Application version (must follow semantic versioning)
            author: Author object

        Raises:
            ValueError: If name or version is invalid
            TypeError: If author is not an Author instance
        """
        self.name = name
        self.version = version
        self.author = author

    @property
    def name(self) -> str:
        """Get application name"""
        return self._name

    @name.setter
    def name(self, value: str) -> None:
        """
        Set application name

        Args:
            value: New application name

        Raises:
            ValueError: If value is empty or not a string
        """
        if not isinstance(value, str):
            raise ValueError("Name must be a string")
        if not value.strip():
            raise ValueError("Name cannot be empty")
        self._name = value.strip()

    @property
    def version(self) -> str:
        """Get application version"""
        return self._version

    @version.setter
    def version(self, value: str) -> None:
        """
        Set application version

        Args:
            value: New version (should follow semantic versioning)

        Raises:
            ValueError: If value is empty or not a string
        """
        if not isinstance(value, str):
            raise ValueError("Version must be a string")
        if not value.strip():
            raise ValueError("Version cannot be empty")
        self._version = value.strip()

    @property
    def author(self) -> Author:
        """Get application author"""
        return self._author

    @author.setter
    def author(self, value: Author) -> None:
        """
        Set application author

        Args:
            value: New author object

        Raises:
            TypeError: If value is not an Author instance
        """
        if not isinstance(value, Author):
            raise TypeError("Author must be an instance of Author class")
        self._author = value

    def __str__(self) -> str:
        """String representation of app"""
        return f"{self.name} v{self.version} by {self.author}"

    def __repr__(self) -> str:
        """Detailed string representation"""
        return f"App(name='{self.name}', version='{self.version}', author={repr(self.author)})"