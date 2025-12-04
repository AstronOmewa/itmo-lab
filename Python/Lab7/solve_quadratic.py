from handler import logger
import logging
from typing import Tuple, Union

file_logger = logging.getLogger("quadratic_file")
file_handler = logging.FileHandler("quadratic.log", mode="w", encoding="utf-8")
formatter = logging.Formatter("%(asctime)s - %(levelname)s - %(message)s")
file_handler.setFormatter(formatter)

file_logger.addHandler(file_handler)
file_logger.setLevel(logging.INFO)

@logger(handle=file_logger)
def solve_quadratic(a: float, b: float, c: float) -> Union[Tuple[float, float], ValueError]:
    """
    Решает квадратное уравнение ax^2 + bx + c = 0
    
    Args:
        a: Коэффициент при x^2
        b: Коэффициент при x
        c: Свободный член
        
    Returns:
        Кортеж с двумя корнями или ValueError при D<0
        
    Raises:
        TypeError: Если коэффициенты не являются числами
        ArithmeticError: Если a=b=0 (не квадратное уравнение)
        ValueError: Если дискриминант отрицательный (нет действительных корней)
    """
    if not (isinstance(a, (int, float)) and isinstance(b, (int, float)) and isinstance(c, (int, float))):
        raise TypeError(f"Coefficients must be numbers, but got (a: {type(a)}, b: {type(b)}, c: {type(c)})")
    
    if a == b == 0:
        raise ArithmeticError("a and b cannot both be zero")
    
    discriminant = b**2 - 4*a*c
    
    if discriminant < 0:
        return ValueError("Discriminant is negative (D<0), no real roots")
    
    sqrt_d = discriminant ** 0.5
    root1 = (-b + sqrt_d) / (2*a)
    root2 = (-b - sqrt_d) / (2*a)
    
    return (root1, root2)
