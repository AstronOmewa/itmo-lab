import math
from typing import Union, Callable
from timeit import timeit
from math import *
from DivergentException import DivergentException

# итерация 1
def integrate(f: Callable, a: Union[float, int], b: Union[float, int], *, n_iter : int=1000, bunches: int = 10):
    """

    ## Численное интегрирование методом Дарбу
    с проверкой сходимости по нескольким разбиениям.

    f : Callable[[float], float]
        Интегрируемая функция.
    a, b : float | int
        Левая и правая границы интегрирования.
    n_iter : int
        Количество разбиений базовой сетки интегрирования.
    bunches : int
        Количество последовательных сгущений сетки для проверки сходимости.

    # Возвращает
    ----------
    float :
        Приближённое значение определённого интеграла.

    # Исключения
    ----------
    DivergentException :
        Если интеграл расходится или функция даёт ошибку деления на ноль.

    # Примеры (doctest)
    -----------------
    >>> round(integrate(math.cos, 0, math.pi/2, n_iter=100), 5)
    0.98343

    >>> round(integrate(lambda x: x*x, 0, 1, n_iter=1000), 5)
    0.32341

    - аннотировать переменные
    - написать тесты для функции (2 штуки тут)
    - + тесты с помощью Unittest
    >>> round(integrate(math.cos, 0, math.pi / 2, n_iter=100), 5)
    1.00783
    - замерить время вычисления функции (timeit), записать время
    вычисления
    """

    uppersums = []
    lowersums = []


    for nbunch in range(ceil(bunches)+1, 0, -1):
        uppersum = lowersum = 0
        step = (b-a)/n_iter*bunches
        for i in range(1,ceil(n_iter/bunches*nbunch)):
            try:
                lowersum, uppersum = lowersum + f(a+step*(i-1))*step, uppersum + f(a+step*(i))*step
                
            except ZeroDivisionError:
                raise DivergentException("Интеграл расходится (деление на ноль)")
        uppersums.append(uppersum)
        lowersums.append(lowersum)
    
    diff = [abs(u - l) for u, l in zip(uppersums, lowersums)]
    # print(uppersums[i], lowersums[i], diff[-5:])
    
    if sum(diff)>len(diff)*max(diff):
        raise DivergentException("Интеграл расходится")
    else:
        return sqrt(lowersums[-1]*uppersums[-1])

print(round(integrate(math.cos, 0, math.pi/2, n_iter=100), 5))
print(round(integrate(lambda x: x*x, 0, 1, n_iter=1000), 5))