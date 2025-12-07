"""
Демонстрация логирования в реальном времени
"""
from get_currencies import get_currencies
from solve_quadratic import solve_quadratic

def main():
    print("ДЕМОНСТРАЦИЯ ЛОГИРОВАНИЯ В РЕАЛЬНОМ ВРЕМЕНИ")
    print("=" * 50)

    print("\n1. Вызов solve_quadratic с двумя корнями:")
    result = solve_quadratic(1.0, -5.0, 6.0)
    print(f"   Результат: {result}")

    print("\n2. Вызов solve_quadratic с отрицательным дискриминантом:")
    result = solve_quadratic(1.0, 0.0, 1.0)
    print(f"   Результат: {result}")

    print("\n3. Вызов solve_quadratic с неверными типами:")
    try:
        result = solve_quadratic("abc", 2.0, 1.0)
    except TypeError as e:
        print(f"   Исключение: {e}")

    print("\n4. Попытка получения курсов валют:")
    try:
        result = get_currencies(['USD', 'EUR'])
        print(f"   Результат: {result}")
    except ConnectionError as e:
        print(f"   Ошибка подключения: {e}")
    except Exception as e:
        print(f"   Ошибка: {e}")

if __name__ == "__main__":
    main()