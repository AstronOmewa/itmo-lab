"""
Tests for models
"""
import unittest
import uuid
from models import Author, App, User, Currency, UserCurrency


class TestAuthor(unittest.TestCase):
    """Test cases for Author model"""

    def test_author_creation(self):
        """Test successful author creation"""
        author = Author("Иван Иванов", "P3120")
        self.assertEqual(author.name, "Иван Иванов")
        self.assertEqual(author.group, "P3120")

    def test_author_name_validation(self):
        """Test author name validation"""
        # Empty name
        with self.assertRaises(ValueError):
            Author("", "P3120")

        # Non-string name
        with self.assertRaises(ValueError):
            Author(123, "P3120")

        # Name with whitespace
        with self.assertRaises(ValueError):
            Author("   ", "P3120")

    def test_author_group_validation(self):
        """Test author group validation"""
        # Empty group
        with self.assertRaises(ValueError):
            Author("Иван Иванов", "")

        # Non-string group
        with self.assertRaises(ValueError):
            Author("Иван Иванов", 123)


class TestApp(unittest.TestCase):
    """Test cases for App model"""

    def setUp(self):
        """Set up test fixtures"""
        self.author = Author("Тест Автор", "P3121")

    def test_app_creation(self):
        """Test successful app creation"""
        app = App("TestApp", "1.0.0", self.author)
        self.assertEqual(app.name, "TestApp")
        self.assertEqual(app.version, "1.0.0")
        self.assertEqual(app.author, self.author)

    def test_app_name_validation(self):
        """Test app name validation"""
        with self.assertRaises(ValueError):
            App("", "1.0.0", self.author)

    def test_app_version_validation(self):
        """Test app version validation"""
        with self.assertRaises(ValueError):
            App("TestApp", "", self.author)

    def test_app_author_validation(self):
        """Test app author validation"""
        with self.assertRaises(TypeError):
            App("TestApp", "1.0.0", "not_an_author")


class TestUser(unittest.TestCase):
    """Test cases for User model"""

    def test_user_creation(self):
        """Test successful user creation"""
        user = User("Тестовый пользователь")
        self.assertEqual(user.name, "Тестовый пользователь")
        self.assertIsNotNone(user.id)
        self.assertEqual(len(user.subscriptions), 0)

    def test_user_with_id(self):
        """Test user creation with specific ID"""
        test_id = str(uuid.uuid4())
        user = User("Тест", test_id)
        self.assertEqual(user.id, test_id)

    def test_user_name_validation(self):
        """Test user name validation"""
        with self.assertRaises(ValueError):
            User("")

    def test_user_subscriptions(self):
        """Test user subscriptions management"""
        user = User("Тест")
        subscription = UserCurrency(user.id, "USD")
        user.add_subscription(subscription)

        self.assertEqual(len(user.subscriptions), 1)
        self.assertIn("USD", user.subscriptions)
        self.assertEqual(user.get_subscription("USD"), subscription)

        # Remove subscription
        user.remove_subscription("USD")
        self.assertEqual(len(user.subscriptions), 0)
        self.assertIsNone(user.get_subscription("USD"))


class TestCurrency(unittest.TestCase):
    """Test cases for Currency model"""

    def test_currency_creation(self):
        """Test successful currency creation"""
        currency = Currency("840", "USD", "Доллар США", 75.50, 1)
        self.assertEqual(currency.num_code, "840")
        self.assertEqual(currency.char_code, "USD")
        self.assertEqual(currency.name, "Доллар США")
        self.assertEqual(currency.value, 75.50)
        self.assertEqual(currency.nominal, 1)

    def test_currency_with_nominal(self):
        """Test currency with nominal > 1"""
        currency = Currency("360", "IDR", "Индонезийская рупия", 48.6178, 10000)
        self.assertEqual(currency.get_rate_per_unit(), 48.6178 / 10000)

    def test_currency_code_validation(self):
        """Test currency code validation"""
        # Invalid numeric code
        with self.assertRaises(ValueError):
            Currency("ABC", "USD", "Доллар", 75.50)

        # Invalid character code
        with self.assertRaises(ValueError):
            Currency("840", "US", "Доллар", 75.50)

    def test_currency_value_validation(self):
        """Test currency value validation"""
        # Negative value
        with self.assertRaises(ValueError):
            Currency("840", "USD", "Доллар", -75.50)

        # Non-numeric value
        with self.assertRaises(ValueError):
            Currency("840", "USD", "Доллар", "invalid")

    def test_currency_nominal_validation(self):
        """Test currency nominal validation"""
        # Zero nominal
        with self.assertRaises(ValueError):
            Currency("840", "USD", "Доллар", 75.50, 0)

        # Negative nominal
        with self.assertRaises(ValueError):
            Currency("840", "USD", "Доллар", 75.50, -1)

        # Non-integer nominal
        with self.assertRaises(ValueError):
            Currency("840", "USD", "Доллар", 75.50, 1.5)


class TestUserCurrency(unittest.TestCase):
    """Test cases for UserCurrency model"""

    def test_user_currency_creation(self):
        """Test successful user currency creation"""
        user_id = str(uuid.uuid4())
        subscription = UserCurrency(user_id, "USD")
        self.assertEqual(subscription.user_id, user_id)
        self.assertEqual(subscription.currency_id, "USD")
        self.assertIsNotNone(subscription.id)
        self.assertIsNone(subscription.notification_threshold)

    def test_user_currency_with_threshold(self):
        """Test user currency with notification threshold"""
        subscription = UserCurrency("user123", "EUR", 1.5)
        self.assertEqual(subscription.notification_threshold, 1.5)

    def test_user_currency_validation(self):
        """Test user currency validation"""
        # Empty user_id
        with self.assertRaises(ValueError):
            UserCurrency("", "USD")

        # Empty currency_id
        with self.assertRaises(ValueError):
            UserCurrency("user123", "")

        # Invalid threshold
        with self.assertRaises(ValueError):
            UserCurrency("user123", "USD", -1.0)


if __name__ == '__main__':
    unittest.main()