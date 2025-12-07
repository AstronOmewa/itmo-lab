from handler import logger
import logging
from typing import Tuple, Union

# Настройка логгера с разными уровнями
file_logger = logging.getLogger("quadratic_file")

# Проверяем, есть ли уже обработчики, чтобы избежать дублирования
if not file_logger.handlers:
    # Обработчик для записи в файл
    file_handler = logging.FileHandler("quadratic.log", mode="w", encoding="utf-8")
    formatter = logging.Formatter("%(asctime)s - %(levelname)s - %(message)s")
    file_handler.setFormatter(formatter)

    # Обработчик для вывода в консоль
    console_handler = logging.StreamHandler()
    console_handler.setFormatter(formatter)

    file_logger.addHandler(file_handler)
    file_logger.addHandler(console_handler)
    file_logger.setLevel(logging.DEBUG)

@logger(handle=file_logger)
def solve_quadratic(a: float, b: float, c: float) -> Union[Tuple[float, float], None, str]:
    """
    Решает квадратное уравнение ax^2 + bx + c = 0

    Args:
        a: Коэффициент при x^2
        b: Коэффициент при x
        c: Свободный член

    Returns:
        Кортеж с двумя корнями, кортеж (x_0, x_0) для одного корня x_0, или строка с сообщением об ошибке

    Raises:
        TypeError: Если коэффициенты не являются числами
        ArithmeticError: Если a=b=0 (не квадратное уравнение)
    """
    if not (isinstance(a, (int, float)) and isinstance(b, (int, float)) and isinstance(c, (int, float))):
        raise TypeError(f"Coefficients must be numbers, but got (a: {type(a)}, b: {type(b)}, c: {type(c)})")

    if a == b == 0:
        raise ArithmeticError("a and b cannot both be zero (critical error)")

    # Логируем INFO для начала вычислений
    file_logger.info(f"Starting quadratic equation: {a}x^2 + {b}x + {c} = 0")

    discriminant = b**2 - 4*a*c
    file_logger.info(f"Discriminant D = {discriminant}")

    if discriminant < 0:
        # Логируем WARNING для отрицательного дискриминанта
        file_logger.warning(f"Discriminant is negative ({discriminant} < 0), no real roots")
        return f"No real roots (D={discriminant} < 0)"

    sqrt_d = discriminant ** 0.5

    if discriminant == 0:
        # Логируем INFO для одного корня
        root = -b / (2*a)
        file_logger.info(f"One root found: x = {root}")
        return (root, root)
    else:
        # Логируем INFO для двух корней
        root1 = (-b + sqrt_d) / (2*a)
        root2 = (-b - sqrt_d) / (2*a)
        file_logger.info(f"Two roots found: x1 = {root1}, x2 = {root2}")
        return (root1, root2)
