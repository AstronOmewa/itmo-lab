"""
Декоратор logger для логирования вызовов функций
Поддерживает логирование в sys.stdout, файлоподобные объекты и logging.Logger
"""
import sys
from functools import wraps
import logging


def logger(func=None, *, handle=sys.stdout):
    """
    Параметризуемый декоратор для логирования вызовов функций.
    
    Args:
        func: Функция для декорирования (или None при использовании параметров)
        handle: Объект для логирования (sys.stdout, файл, logging.Logger)
        
    Returns:
        Декорированная функция
        
    Examples:
        @logger
        def my_func():
            pass
        
        @logger(handle=sys.stdout)
        def my_func():
            pass
            
        @logger(handle=some_logger)
        def my_func():
            pass
    """
    if func is None:
        return lambda f: logger(f, handle=handle)
    
    if isinstance(handle, logging.Logger):
        @wraps(func)
        def wrapper(*args, **kwargs):
            handle.info(f"Started logging: {func.__name__}({args}, {kwargs})")
            try:
                res = func(*args, **kwargs)
                handle.info(f"Finishing logging: {func.__name__} returned {res}")
            except Exception as e:
                handle.error(f"{type(e).__name__}: {e}")
                raise
            return res
        return wrapper
    else:
        @wraps(func)
        def wrapper(*args, **kwargs):
            handle.write(f"Started logging: {func.__name__}({args}, {kwargs})\n")
            try:
                res = func(*args, **kwargs)
                handle.write(f"Finishing logging: {func.__name__} returned {res}\n")
            except Exception as e:
                handle.write(f"{type(e).__name__}: {e}\n")
                raise
            return res
        return wrapper