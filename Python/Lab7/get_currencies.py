"""
Получение курсов валют от ЦБ РФ
"""
import requests
import logging
from typing import List, Dict
from handler import logger

# Настройка файлового логирования
file_logger = logging.getLogger("currency_file")

# Проверяем, есть ли уже обработчики, чтобы избежать дублирования
if not file_logger.handlers:
    # Обработчик для записи в файл
    file_handler = logging.FileHandler("currency.log", mode="w", encoding="utf-8")
    formatter = logging.Formatter("%(asctime)s - %(levelname)s - %(message)s")
    file_handler.setFormatter(formatter)

    # Обработчик для вывода в консоль
    console_handler = logging.StreamHandler()
    console_handler.setFormatter(formatter)

    file_logger.addHandler(file_handler)
    file_logger.addHandler(console_handler)
    file_logger.setLevel(logging.INFO)


@logger(handle=file_logger)
def get_currencies(
    currency_codes: List[str],
    url: str = "https://www.cbr-xml-daily.ru/daily_json.js"
) -> Dict[str, float]:
    """
    Получает курсы валют из API ЦБ РФ
    
    Args:
        currency_codes: Список кодов валют (например, ['USD', 'EUR'])
        url: URL для получения данных
        
    Returns:
        Словарь вида {'USD': 93.25, 'EUR': 101.7}
        
    Raises:
        ConnectionError: Если API недоступен
        ValueError: Если JSON некорректный
        KeyError: Если отсутствует ключ 'Valute' или валюта в данных
        TypeError: Если значение курса имеет неверный тип
    """
    try:
        # Делаем запрос к API
        response = requests.get(url, timeout=5)
        response.raise_for_status()
    except requests.exceptions.RequestException as e:
        raise ConnectionError(f"Failed to connect to {url}: {e}")
    
    try:
        # Парсим JSON
        data = response.json()
    except ValueError as e:
        raise ValueError(f"Invalid JSON response: {e}")
    
    # Проверяем наличие ключа 'Valute'
    if "Valute" not in data:
        raise KeyError("'Valute' key not found in response")
    
    valute = data["Valute"]
    result = {}
    
    # Извлекаем курсы для каждой валюты
    for code in currency_codes:
        if code not in valute:
            raise KeyError(f"Currency '{code}' not found in data")
        
        value = valute[code]["Value"]
        
        # Проверяем тип значения
        if not isinstance(value, (int, float)):
            raise TypeError(f"Currency value for '{code}' has invalid type: {type(value)}")
        
        result[code] = float(value)
    
    return result
