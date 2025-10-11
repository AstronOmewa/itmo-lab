from guessr import *
from test import *
import unittest

def main():
    """Ввод значений с клавиатуры для формирования
    списка, по которому мы ищем искомое число и
    искомого числа
    (опционально) предложить пользователю сформировать
    список вручную с клавиатуры

    __вызов функции guess-number с параметрами: __
      - искомое число (target)
      - список, по-которому идем
      - тип поиска (последовательный, бинарный)

    __вывод результатов на экран__
    :return:
    """
    
    target = int(input('Введите target: '))
    start_range = int(input('Введите начало диапазона: '))
    end_range = int(input('Введите конец диапазона: '))
    d = list(range(start_range, end_range + 1))

    res = guess_number(target, d, type_='bin')
    print(f'{res}')

if __name__=="__main__":
    main()
    unittest.main(verbosity=2)