from typing import Literal

def guess_number(num_guessed: int, num_list: list[int], guessing_way: Literal["binary" , "slow"] ) -> tuple:
    """Вернёт кортеж из 2 значений: загаданное число и количество потраченных на угадывание числа попыток.
    
    Ключевые аргументы:\\
    num_guessed -- загаданное число\\
    num_list -- список, в котором будет вестись поиск\\
    guessing_way -- способ угадывания. "binary" -- алгоритм двоичного поиска на отсортированном массиве, "slow" -- медленный перебор.
    """
    if guessing_way not in ['binary', 'slow']:
        raise ValueError(f'Параметр guessing_way может принимать только значения из списка ["binary","slow"], указано {guessing_way}')
    if any(type(el)!=int for el in num_list):
        el = [(type(el),num_list.index(el)) for el in num_list if type(el)!=int ][0]
        raise TypeError(f'В списке чисел найден элемент постороннего типа ({el[0]}) на посзиции {el[1]}')
    if guessing_way == "slow":
        pass
        

guess_number(0,[0,"",1],"binary")