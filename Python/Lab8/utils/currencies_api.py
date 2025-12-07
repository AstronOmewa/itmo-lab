"""
Currency API utilities
Contains the get_currencies function for fetching exchange rates
"""
import requests
import logging
from typing import List, Dict, Optional
from datetime import datetime

# Configure logging
logger = logging.getLogger("currencies_api")


def get_currencies(
    currency_codes: Optional[List[str]] = None,
    url: str = "https://www.cbr-xml-daily.ru/daily_json.js"
) -> Dict[str, Dict[str, any]]:
    """
    Fetches currency exchange rates from Central Bank of Russia API

    Args:
        currency_codes: List of currency codes to fetch (e.g., ['USD', 'EUR'])
                       If None, fetches all currencies
        url: URL for fetching data (default: CBR daily rates)

    Returns:
        Dictionary with currency data:
        {
            'USD': {
                'num_code': '840',
                'char_code': 'USD',
                'nominal': 1,
                'name': 'Доллар США',
                'value': 76.0937,
                'previous': 75.8421
            },
            ...
        }

    Raises:
        ConnectionError: If API is unavailable
        ValueError: If JSON is invalid
        KeyError: If 'Valute' key is missing or currency not found
        TypeError: If currency value has invalid type
    """
    try:
        logger.info(f"Fetching currencies from {url}")
        response = requests.get(url, timeout=5)
        response.raise_for_status()
    except requests.exceptions.RequestException as e:
        logger.error(f"Failed to connect to {url}: {e}")
        raise ConnectionError(f"Failed to connect to {url}: {e}")

    try:
        data = response.json()
    except ValueError as e:
        logger.error(f"Invalid JSON response: {e}")
        raise ValueError(f"Invalid JSON response: {e}")

    if "Valute" not in data:
        logger.error("'Valute' key not found in response")
        raise KeyError("'Valute' key not found in response")

    valute = data["Valute"]
    result = {}

    # Determine which currencies to fetch
    codes_to_fetch = currency_codes if currency_codes else list(valute.keys())

    for code in codes_to_fetch:
        # Handle both numeric code and char code
        currency_key = None
        if code in valute:
            currency_key = code
        else:
            # Search by char code
            for key, value in valute.items():
                if value.get('CharCode') == code:
                    currency_key = key
                    break

        if not currency_key:
            logger.warning(f"Currency '{code}' not found in data")
            continue

        currency_data = valute[currency_key]

        # Validate and extract data
        try:
            value = float(currency_data.get("Value", 0))
            nominal = int(currency_data.get("Nominal", 1))
            previous = float(currency_data.get("Previous", value))

            if not isinstance(value, (int, float)):
                raise TypeError(f"Currency value for '{code}' has invalid type")

            result[code] = {
                'num_code': str(currency_data.get('NumCode', '')),
                'char_code': str(currency_data.get('CharCode', code)),
                'nominal': nominal,
                'name': str(currency_data.get('Name', code)),
                'value': value,
                'previous': previous,
                'date': data.get('Date', datetime.now().strftime('%Y-%m-%dT%H:%M:%S'))
            }

        except (ValueError, TypeError) as e:
            logger.error(f"Error processing currency '{code}': {e}")
            raise TypeError(f"Currency value for '{code}' has invalid type: {e}")

    logger.info(f"Successfully fetched {len(result)} currencies")
    return result


def get_currency_history(
    currency_code: str,
    days: int = 90
) -> Dict[str, float]:
    """
    Get historical exchange rates for a currency
    Note: This is a simplified implementation
    In production, you would use a proper historical data API

    Args:
        currency_code: Currency code (e.g., 'USD')
        days: Number of days of history to fetch

    Returns:
        Dictionary with dates as keys and rates as values
    """
    # This is a placeholder implementation
    # In a real application, you would fetch from a historical data API
    logger.warning("Currency history is a placeholder implementation")

    from datetime import datetime, timedelta
    import random

    history = {}
    base_date = datetime.now()

    # Get current rate
    try:
        current_data = get_currencies([currency_code])
        current_rate = current_data[currency_code]['value']
    except:
        current_rate = 75.0  # Default fallback

    # Generate mock historical data
    for i in range(days):
        date = (base_date - timedelta(days=i)).strftime('%Y-%m-%d')
        # Add some random variation
        variation = random.uniform(-0.05, 0.05)
        history[date] = current_rate * (1 + variation)

    return history