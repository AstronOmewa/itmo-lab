from typing import Literal

def guess_number(num_guessed: int, num_list: list[int], type_: Literal["bin" , "seq"] ) -> tuple[int, int| None]:
    """Вернёт кортеж из 2 значений: загаданное число и количество потраченных на угадывание числа попыток.
    
    Ключевые аргументы:\\
    num_guessed -- загаданное число\\
    num_list -- список, в котором будет вестись поиск\\
    type_ -- способ угадывания. "bin" -- алгоритм двоичного поиска на отсортированном массиве,
    "seq" -- медленный перебор.
    """
        
    if type_ not in ['bin', 'seq']:
        raise ValueError(f'Параметр type_ может принимать только значения из списка ["bin","seq"], указано {type_}')
    if not all(type(el)==int for el in num_list): # Долго что-то работает
        el = [(type(el),num_list.index(el)) for el in num_list if type(el)!=int ][0]
        raise TypeError(f'В списке чисел найден элемент постороннего типа ({el[0]}) на позиции {el[1]}')
    if num_guessed not in num_list:
        raise ValueError(f"Given value {num_guessed} is not in list")
    
    
    def slow_guessing():
        """Вернёт кортеж из 2 значений: загаданное число и количество потраченных на угадывание
        числа попыток медленным способом (полный перебор).
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
        # print(num_list) # DEBUG
        left, right = 0, len(num_list)-1
        mid = (left+right)//2
        guesses = 1
        while right - left != 0:
            # print(mid) # DEBUG
            if num_list[mid] < num_guessed:
                left = mid + 1
            elif num_list[mid] > num_guessed:
                right = mid - 1
            else:
                return (num_list[mid], guesses)

            mid = (right+left)//2
            
            guesses += 1
        return (num_list[mid], guesses)
    
    
    if type_ == "seq":
        return slow_guessing()
    if type_ == "bin":
        return binary_search()

