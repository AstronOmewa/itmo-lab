"""
Демонстрация работы декоратора logger с различными типами логирования
"""
import sys, os
import io
import logging
from handler import logger
from get_currencies import get_currencies
from solve_quadratic import solve_quadratic
os.chdir(os.path.dirname(os.path.abspath(__file__)))

def demo_logger_with_stdout():
    """Демонстрация логирования в sys.stdout"""
    print("\n" + "="*60)
    print("1. ДЕМОНСТРАЦИЯ ЛОГИРОВАНИЯ В sys.stdout")
    print("="*60 + "\n")
    
    @logger(handle=sys.stdout)
    def simple_calc(x, y):
        return x + y
    
    result = simple_calc(5, 3)
    print(f"Результат: {result}\n")


def demo_logger_with_stringio():
    """Демонстрация логирования в io.StringIO"""
    print("="*60)
    print("2. ДЕМОНСТРАЦИЯ ЛОГИРОВАНИЯ В io.StringIO")
    print("="*60 + "\n")
    
    stream = io.StringIO()
    
    @logger(handle=stream)
    def multiply(x, y):
        return x * y
    
    result = multiply(4, 7)
    logs = stream.getvalue()
    
    print(f"Результат: {result}")
    print(f"Логи:\n{logs}\n")


def demo_logger_with_exception():
    """Демонстрация логирования исключений"""
    print("="*60)
    print("3. ДЕМОНСТРАЦИЯ ЛОГИРОВАНИЯ ИСКЛЮЧЕНИЙ")
    print("="*60 + "\n")
    
    stream = io.StringIO()
    
    @logger(handle=stream)
    def divide(x, y):
        return x / y
    
    print("Попытка деления на ноль:")
    try:
        divide(10, 0)
    except ZeroDivisionError as e:
        print(f"Поймано исключение: {e}")
    
    logs = stream.getvalue()
    print(f"\nЛоги:\n{logs}\n")


def demo_logger_with_logging():
    """Демонстрация логирования через logging.Logger"""
    print("="*60)
    print("4. ДЕМОНСТРАЦИЯ ЛОГИРОВАНИЯ ЧЕРЕЗ logging.Logger")
    print("="*60 + "\n")
    
    # Создаем логгер
    logger_obj = logging.getLogger("demo_logger")
    logger_obj.handlers = []  # Очищаем старые обработчики
    
    # Настраиваем вывод в консоль
    handler = logging.StreamHandler(sys.stdout)
    formatter = logging.Formatter("%(levelname)-8s - %(message)s")
    handler.setFormatter(formatter)
    logger_obj.addHandler(handler)
    logger_obj.setLevel(logging.INFO)
    
    @logger(handle=logger_obj)
    def power(x, exp):
        return x ** exp
    
    result = power(2, 5)
    print(f"\nРезультат: {result}\n")


def demo_get_currencies():
    """Демонстрация функции get_currencies"""
    print("="*60)
    print("5. ДЕМОНСТРАЦИЯ get_currencies")
    print("="*60 + "\n")
    
    try:
        print("Получение курсов USD и EUR...")
        result = get_currencies(['USD', 'EUR'])

        print(f"Результат: {result}\n")
    except ConnectionError as e:
        print(f"Ошибка подключения: {e}\n")
    except Exception as e:
        print(f"Ошибка: {type(e).__name__}: {e}\n")


def demo_solve_quadratic():
    """Демонстрация функции solve_quadratic"""
    print("="*60)
    print("6. ДЕМОНСТРАЦИЯ solve_quadratic")
    print("="*60 + "\n")
    
    # Пример 1: Два корня
    print("Пример 1: x^2 - 5x + 6 = 0")
    try:
        result = solve_quadratic(1.0, -5.0, 6.0)
        print(f"Корни: {result}\n")
    except Exception as e:
        print(f"Ошибка: {e}\n")
    
    # Пример 2: Отрицательный дискриминант
    print("Пример 2: x^2 + 1 = 0 (D < 0)")
    try:
        result = solve_quadratic(1.0, 0.0, 1.0)
        if isinstance(result, ValueError):
            print(f"Результат: {result}\n")
    except Exception as e:
        print(f"Ошибка: {e}\n")
    
    # Пример 3: Ошибка типа
    print("Пример 3: Некорректный тип коэффициента")
    try:
        result = solve_quadratic("abc", 2.0, 1.0)
        print(f"Корни: {result}\n")
    except TypeError as e:
        print(f"Ошибка типа: {e}\n")
    
    # Пример 4: Вырожденное уравнение
    print("Пример 4: a=b=0 (вырожденное уравнение)")
    try:
        result = solve_quadratic(0.0, 0.0, 1.0)
        print(f"Результат: {result}\n")
    except ArithmeticError as e:
        print(f"Арифметическая ошибка: {e}\n")


def main():
    
    demo_logger_with_stdout()
    demo_logger_with_stringio()
    demo_logger_with_exception()
    demo_logger_with_logging()
    demo_get_currencies()
    demo_solve_quadratic()
    
    print("Логи сохранены в файлы:")
    print("- currency.log (логи get_currencies)")
    print("- quadratic.log (логи solve_quadratic)\n")


if __name__ == "__main__":
    main()
