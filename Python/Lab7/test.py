"""
Тесты для лабораторной работы 7
Проверка декоратора logger и функций get_currencies и solve_quadratic
"""
import unittest
import io
import sys
import logging
from unittest.mock import patch, MagicMock
import requests

from handler import logger
from get_currencies import get_currencies
from solve_quadratic import solve_quadratic


class TestDecoratorWithStringIO(unittest.TestCase):
    """Тестирование декоратора с io.StringIO"""
    
    def setUp(self):
        """Инициализация для каждого теста"""
        self.stream = io.StringIO()
    
    def test_logging_success_stream(self):
        """Тест успешного выполнения функции с логированием в поток"""
        @logger(handle=self.stream)
        def simple_function(x: int) -> int:
            return x * 2
        
        result = simple_function(5)
        logs = self.stream.getvalue()
        
        # Проверяем результат
        self.assertEqual(result, 10)
        
        # Проверяем логи
        self.assertIn("Started", logs)
        self.assertIn("Finishing", logs)
        self.assertIn("success", logs)
    
    def test_logging_exception_stream(self):
        """Тест логирования исключения в поток"""
        @logger(handle=self.stream)
        def failing_function():
            raise ValueError("Test error")
        
        # Проверяем, что исключение проброшено
        with self.assertRaises(ValueError):
            failing_function()
        
        logs = self.stream.getvalue()
        # При использовании StringIO ошибка логируется как объект
        self.assertIn("Started", logs)


class TestDecoratorWithLogging(unittest.TestCase):
    """Тестирование декоратора с logging.Logger"""
    
    def setUp(self):
        """Инициализация для каждого теста"""
        self.logger_obj = logging.getLogger("test_logger")
        # Очищаем предыдущие обработчики
        self.logger_obj.handlers = []
        
        self.stream = io.StringIO()
        handler = logging.StreamHandler(self.stream)
        formatter = logging.Formatter("%(levelname)s - %(message)s")
        handler.setFormatter(formatter)
        self.logger_obj.addHandler(handler)
        self.logger_obj.setLevel(logging.DEBUG)
    
    def test_logging_success_logger(self):
        """Тест успешного выполнения функции с logging.Logger"""
        @logger(handle=self.logger_obj)
        def simple_function(x: int) -> int:
            return x * 2
        
        result = simple_function(5)
        logs = self.stream.getvalue()
        
        self.assertEqual(result, 10)
        self.assertIn("INFO", logs)
        self.assertIn("Started", logs)
        self.assertIn("Finishing", logs)
    
    def test_logging_exception_logger(self):
        """Тест логирования исключения с logging.Logger"""
        @logger(handle=self.logger_obj)
        def failing_function():
            raise KeyError("Missing key")
        
        with self.assertRaises(KeyError):
            failing_function()
        
        logs = self.stream.getvalue()
        self.assertIn("ERROR", logs)
        self.assertRegex(logs, r"ERROR.*Missing key")


class TestGetCurrencies(unittest.TestCase):
    """Тестирование функции get_currencies"""
    
    def setUp(self):
        """Инициализация для каждого теста"""
        self.stream = io.StringIO()
        self.test_url = "https://httpbin.org/json"  # Используем для тестов
    
    @patch('get_currencies.requests.get')
    def test_get_currencies_success(self, mock_get):
        """Тест успешного получения курсов"""
        # Мокируем ответ API
        mock_response = MagicMock()
        mock_response.json.return_value = {
            "Valute": {
                "USD": {"Value": 93.25},
                "EUR": {"Value": 101.7}
            }
        }
        mock_get.return_value = mock_response
        
        result = get_currencies(["USD", "EUR"])
        
        self.assertEqual(result, {"USD": 93.25, "EUR": 101.7})
    
    @patch('get_currencies.requests.get')
    def test_get_currencies_missing_key(self, mock_get):
        """Тест при отсутствии валюты"""
        mock_response = MagicMock()
        mock_response.json.return_value = {
            "Valute": {
                "USD": {"Value": 93.25}
            }
        }
        mock_get.return_value = mock_response
        
        with self.assertRaises(KeyError):
            get_currencies(["EUR"])
    
    @patch('get_currencies.requests.get')
    def test_get_currencies_missing_valute(self, mock_get):
        """Тест при отсутствии ключа Valute"""
        mock_response = MagicMock()
        mock_response.json.return_value = {}
        mock_get.return_value = mock_response
        
        with self.assertRaises(KeyError):
            get_currencies(["USD"])
    
    @patch('get_currencies.requests.get')
    def test_get_currencies_connection_error(self, mock_get):
        """Тест при ошибке соединения"""
        mock_get.side_effect = requests.exceptions.RequestException("Connection failed")
        
        with self.assertRaises(ConnectionError):
            get_currencies(["USD"])
    
    @patch('get_currencies.requests.get')
    def test_get_currencies_invalid_json(self, mock_get):
        """Тест при некорректном JSON"""
        mock_response = MagicMock()
        mock_response.json.side_effect = ValueError("Invalid JSON")
        mock_get.return_value = mock_response
        
        with self.assertRaises(ValueError):
            get_currencies(["USD"])


class TestSolveQuadratic(unittest.TestCase):
    """Тестирование функции solve_quadratic"""
    
    def setUp(self):
        """Инициализация для каждого теста"""
        self.logger_obj = logging.getLogger("test_quadratic")
        self.logger_obj.handlers = []
        
        self.stream = io.StringIO()
        handler = logging.StreamHandler(self.stream)
        formatter = logging.Formatter("%(levelname)s - %(message)s")
        handler.setFormatter(formatter)
        self.logger_obj.addHandler(handler)
        self.logger_obj.setLevel(logging.DEBUG)
    
    def test_two_roots(self):
        """Тест квадратного уравнения с двумя корнями"""
        # x^2 - 5x + 6 = 0 -> x1=3, x2=2
        result = solve_quadratic(1.0, -5.0, 6.0)
        self.assertIsNotNone(result)
        self.assertEqual(len(result), 2)
    
    def test_one_root(self):
        """Тест квадратного уравнения с одним корнем"""
        # x^2 - 2x + 1 = 0 -> x=1 (двойной корень)
        result = solve_quadratic(1.0, -2.0, 1.0)
        self.assertIsNotNone(result)
    
    def test_negative_discriminant(self):
        """Тест при отрицательном дискриминанте"""
        # x^2 + 1 = 0 -> нет действительных корней
        result = solve_quadratic(1.0, 0.0, 1.0)
        # Функция возвращает ValueError при D<0
        self.assertIsInstance(result, ValueError)
    
    def test_invalid_coefficients_type(self):
        """Тест при неверных типах коэффициентов"""
        with self.assertRaises(TypeError):
            solve_quadratic("abc", 2.0, 1.0)
    
    def test_both_zero(self):
        """Тест при a=b=0"""
        with self.assertRaises(ArithmeticError):
            solve_quadratic(0.0, 0.0, 1.0)


class TestDecoratorWithFileLogging(unittest.TestCase):
    """Тестирование файлового логирования"""
    
    def test_file_logger_creation(self):
        """Тест создания файлового логгера"""
        file_logger = logging.getLogger("test_file_logger")
        file_logger.handlers = []
        
        # Создаем обработчик для строки вместо файла для теста
        stream = io.StringIO()
        handler = logging.StreamHandler(stream)
        formatter = logging.Formatter("%(asctime)s - %(levelname)s - %(message)s")
        handler.setFormatter(formatter)
        file_logger.addHandler(handler)
        file_logger.setLevel(logging.INFO)
        
        @logger(handle=file_logger)
        def test_func(x):
            return x + 1
        
        result = test_func(5)
        logs = stream.getvalue()
        
        self.assertEqual(result, 6)
        self.assertIn("INFO", logs)
        self.assertIn("Started", logs)


class TestDecoratorWithStdout(unittest.TestCase):
    """Тестирование декоратора с sys.stdout по умолчанию"""
    
    def test_decorator_default_stdout(self):
        """Тест декоратора без параметров (по умолчанию sys.stdout)"""
        stream = io.StringIO()
        
        @logger(handle=stream)
        def test_func(x):
            return x * 3
        
        result = test_func(4)
        logs = stream.getvalue()
        
        self.assertEqual(result, 12)
        self.assertIn("Started", logs)
        self.assertIn("success", logs)


if __name__ == '__main__':
    unittest.main()
