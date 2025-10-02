from typing import Literal

def guess_number(num_guessed: int, num_list: list[int], guessing_way: Literal["binary" , "slow"] ) -> tuple:
    """Вернёт кортеж из 2 значений: загаданное число и количество потраченных на угадывание числа попыток.
    
    Ключевые аргументы:\\
    num_guessed -- загаданное число\\
    num_list -- список, в котором будет вестись поиск\\
    guessing_way -- способ угадывания. "binary" -- алгоритм двоичного поиска на отсортированном массиве, "slow" -- медленный перебор.
    """
    
    def slow_guessing():
        """Вернёт кортеж из 2 значений: загаданное число и количество потраченных на угадывание числа попыток медленным способом (полный перебор).
        """
        nonlocal num_list
        guesses = 1
        for el in num_list:
            if el == num_guessed: return (el, guesses)
            guesses += 1
    
    def binary_search():
        """Вернёт кортеж из 2 значений: загаданное число и количество потраченных на угадывание числа попыток бинарным поиском."""
        nonlocal num_list
        num_list.sort()
        # print(num_list)
        left, right = 0, len(num_list)-1
        mid = (left+right)//2
        guesses = 1
        while right - left != 0:
            # print(mid)
            if num_list[mid] < num_guessed:
                left = mid + 1
            elif num_list[mid] > num_guessed:
                right = mid - 1
            else:
                return (num_list[mid], guesses)

            mid = (right+left)//2
            
            guesses += 1
        return (num_list[mid], guesses)
        
    if guessing_way not in ['binary', 'slow']:
        raise ValueError(f'Параметр guessing_way может принимать только значения из списка ["binary","slow"], указано {guessing_way}')
    if any(type(el)!=int for el in num_list): # Долго что-то работает
        el = [(type(el),num_list.index(el)) for el in num_list if type(el)!=int ][0]
        raise TypeError(f'В списке чисел найден элемент постороннего типа ({el[0]}) на позиции {el[1]}')
    if guessing_way == "slow":
        return slow_guessing()
    if guessing_way == "binary":
        return binary_search()
        
print(guess_number(7, list(range(1,2^5+1)), "binary"))