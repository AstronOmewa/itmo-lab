#!/usr/bin/env python3
"""
Демонстрация работы декоратора logger и различных функций
"""
import sys
import io
import logging
from datetime import datetime

# Импортируем наши модули
from handler import logger
from get_currencies import get_currencies
from solve_quadratic import solve_quadratic


def demo_stdout_logging():
    """Демонстрация логирования в stdout"""
    print("=== Демонстрация логирования в stdout ===\n")

    @logger
    def add_numbers(a, b):
        return a + b

    @logger
    def divide_numbers(a, b):
        return a / b

    # Успешный вызов
    result = add_numbers(5, 3)
    print(f"Результат: {result}\n")

    # Вызов с ошибкой
    try:
        divide_numbers(10, 0)
    except ZeroDivisionError:
        print("Ошибка поймана, лог записан\n")


def demo_stringio_logging():
    """Демонстрация логирования в StringIO"""
    print("=== Демонстрация логирования в StringIO ===\n")

    stream = io.StringIO()

    @logger(handle=stream)
    def multiply(x, y):
        return x * y

    result = multiply(7, 8)
    logs = stream.getvalue()

    print(f"Результат: {result}")
    print("Логи из StringIO:")
    print(logs)


def demo_logging_module():
    """Демонстрация логирования через модуль logging"""
    print("=== Демонстрация логирования через logging ===\n")

    # Создаем логгер
    log = logging.getLogger("demo_logger")
    log.handlers = []  # Очищаем предыдущие обработчики

    # Добавляем обработчик для консоли
    console_handler = logging.StreamHandler(sys.stdout)
    formatter = logging.Formatter("%(levelname)s: %(message)s")
    console_handler.setFormatter(formatter)
    log.addHandler(console_handler)
    log.setLevel(logging.INFO)

    @logger(handle=log)
    def power(base, exponent):
        return base ** exponent

    result = power(2, 10)
    print(f"Результат: {result}")


def demo_currencies():
    """Демонстрация работы get_currencies"""
    print("\n=== Демонстрация get_currencies ===\n")

    try:
        # Получаем курсы USD и EUR
        rates = get_currencies(['USD', 'EUR'])
        print("Курсы валют успешно получены:")
        for currency, rate in rates.items():
            print(f"  {currency}: {rate} руб.")
    except Exception as e:
        print(f"Ошибка при получении курсов: {e}")
        print("(Лог ошибки записан в файл currency.log)")


def demo_quadratic():
    """Демонстрация solve_quadratic с разными уровнями логирования"""
    print("\n=== Демонстрация solve_quadratic ===\n")

    test_cases = [
        (1, -5, 6, "Два корня"),
        (1, -2, 1, "Один корень"),
        (1, 0, 1, "Нет действительных корней"),
        ("abc", 2, 1, "Неверный тип данных"),
        (0, 0, 1, "Критическая ошибка (a=b=0)")
    ]

    for a, b, c, description in test_cases:
        print(f"Тест: {description}")
        print(f"Уравнение: {a}x^2 + {b}x + {c} = 0")

        try:
            result = solve_quadratic(a, b, c)
            if isinstance(result, str):
                print(f"Результат: {result}")
            else:
                print(f"Корни: x₁ = {result[0]:.2f}, x₂ = {result[1]:.2f}")
        except Exception as e:
            print(f"Исключение: {type(e).__name__}: {e}")

        print()


def demo_file_logging():
    """Демонстрация файлового логирования"""
    print("=== Демонстрация файлового логирования ===\n")

    # Создаем файловый логгер
    file_log = logging.getLogger("demo_file")
    file_log.handlers = []

    file_handler = logging.FileHandler("demo.log", mode="w", encoding="utf-8")
    formatter = logging.Formatter("%(asctime)s - %(name)s - %(levelname)s - %(message)s")
    file_handler.setFormatter(formatter)
    file_log.addHandler(file_handler)
    file_log.setLevel(logging.DEBUG)

    @logger(handle=file_log)
    def factorial(n):
        if n < 0:
            raise ValueError("Факториал отрицательного числа не определен")
        if n == 0:
            return 1
        result = 1
        for i in range(1, n + 1):
            result *= i
        return result

    # Тестируем функцию
    print("Вычисляем факториалы...")
    factorial(5)
    factorial(0)

    try:
        factorial(-3)
    except ValueError:
        pass

    print("Логи записаны в файл demo.log")

    # Показываем содержимое лог-файла
    with open("demo.log", "r", encoding="utf-8") as f:
        print("\nСодержимое лог-файла:")
        print(f.read())


if __name__ == "__main__":
    print(f"Лабораторная работа 7 - Демонстрация")
    print(f"Дата и время: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print("=" * 50)

    demo_stdout_logging()
    demo_stringio_logging()
    demo_logging_module()
    demo_currencies()
    demo_quadratic()
    demo_file_logging()

    print("=" * 50)
    print("Демонстрация завершена!")
    print("\nСозданные файлы логов:")
    print("- currency.log (логи get_currencies)")
    print("- quadratic.log (логи solve_quadratic)")
    print("- demo.log (демонстрация файлового логирования)")