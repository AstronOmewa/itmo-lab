"""
Tests for currencies API
"""
import unittest
from unittest.mock import patch, Mock
from utils.currencies_api import get_currencies


class TestCurrenciesAPI(unittest.TestCase):
    """Test cases for currencies API"""

    @patch('utils.currencies_api.requests.get')
    def test_get_currencies_success(self, mock_get):
        """Test successful currency fetching"""
        # Mock response
        mock_response = Mock()
        mock_response.raise_for_status.return_value = None
        mock_response.json.return_value = {
            "Date": "2024-12-07T00:00:00",
            "Valute": {
                "R01235": {
                    "NumCode": "840",
                    "CharCode": "USD",
                    "Nominal": 1,
                    "Name": "Доллар США",
                    "Value": 76.0937,
                    "Previous": 75.8421
                },
                "R01239": {
                    "NumCode": "978",
                    "CharCode": "EUR",
                    "Nominal": 1,
                    "Name": "Евро",
                    "Value": 88.7028,
                    "Previous": 88.1234
                }
            }
        }
        mock_get.return_value = mock_response

        result = get_currencies(['USD', 'EUR'])

        self.assertIn('USD', result)
        self.assertIn('EUR', result)
        self.assertEqual(result['USD']['char_code'], 'USD')
        self.assertEqual(result['USD']['value'], 76.0937)
        self.assertEqual(result['EUR']['char_code'], 'EUR')
        self.assertEqual(result['EUR']['value'], 88.7028)

    @patch('utils.currencies_api.requests.get')
    def test_get_currencies_connection_error(self, mock_get):
        """Test connection error handling"""
        mock_get.side_effect = Exception("Connection failed")

        with self.assertRaises(ConnectionError):
            get_currencies(['USD'])

    @patch('utils.currencies_api.requests.get')
    def test_get_currencies_invalid_json(self, mock_get):
        """Test invalid JSON response"""
        mock_response = Mock()
        mock_response.raise_for_status.return_value = None
        mock_response.json.side_effect = ValueError("Invalid JSON")
        mock_get.return_value = mock_response

        with self.assertRaises(ValueError):
            get_currencies(['USD'])

    @patch('utils.currencies_api.requests.get')
    def test_get_currencies_missing_valute(self, mock_get):
        """Test response missing Valute key"""
        mock_response = Mock()
        mock_response.raise_for_status.return_value = None
        mock_response.json.return_value = {"Date": "2024-12-07"}
        mock_get.return_value = mock_response

        with self.assertRaises(KeyError):
            get_currencies(['USD'])

    @patch('utils.currencies_api.requests.get')
    def test_get_currencies_currency_not_found(self, mock_get):
        """Test currency not in response"""
        mock_response = Mock()
        mock_response.raise_for_status.return_value = None
        mock_response.json.return_value = {
            "Date": "2024-12-07",
            "Valute": {
                "R01235": {
                    "NumCode": "840",
                    "CharCode": "USD",
                    "Nominal": 1,
                    "Name": "Доллар США",
                    "Value": 76.0937,
                    "Previous": 75.8421
                }
            }
        }
        mock_get.return_value = mock_response

        result = get_currencies(['USD', 'EUR'])  # EUR is not in response

        self.assertIn('USD', result)
        self.assertNotIn('EUR', result)

    @patch('utils.currencies_api.requests.get')
    def test_get_all_currencies(self, mock_get):
        """Test fetching all currencies when no specific codes provided"""
        mock_response = Mock()
        mock_response.raise_for_status.return_value = None
        mock_response.json.return_value = {
            "Date": "2024-12-07",
            "Valute": {
                "R01235": {
                    "NumCode": "840",
                    "CharCode": "USD",
                    "Nominal": 1,
                    "Name": "Доллар США",
                    "Value": 76.0937,
                    "Previous": 75.8421
                }
            }
        }
        mock_get.return_value = mock_response

        result = get_currencies()  # No currency codes specified

        self.assertIn('USD', result)
        self.assertEqual(len(result), 1)


if __name__ == '__main__':
    unittest.main()