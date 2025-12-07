# Отчет по ЛР 7  
Зекин В.К., P3121
## Декораторы и логирование

---

### Реализация декоратора `logger`

Декоратор автоматически определяет тип обработчика и использует соответствующий метод логирования.

```python
def logger(func=None, *, handle=sys.stdout):
    """
    Параметризуемый декоратор для логирования вызовов функций.

    Поддерживает три типа логирования:
    1. sys.stdout (по умолчанию)
    2. Любой файлоподобный объект (StringIO, File)
    3. logging.Logger (модуль logging)
    """
    if func is None:
        return lambda f: logger(f, handle=handle)

    if isinstance(handle, logging.Logger):
        # Логирование через модуль logging
        @wraps(func)
        def wrapper(*args, **kwargs):
            handle.info(f"Calling {func.__name__} with args={args}, kwargs={kwargs}")
            try:
                res = func(*args, **kwargs)
                handle.info(f"{func.__name__} returned {res}")
            except Exception as e:
                handle.error(f"{type(e).__name__}: {e}")
                raise
            return res
        return wrapper
    else:
        # Логирование через метод write()
        @wraps(func)
        def wrapper(*args, **kwargs):
            handle.write(f"Calling {func.__name__} with args={args}, kwargs={kwargs}\n")
            try:
                res = func(*args, **kwargs)
                handle.write(f"{func.__name__} returned {res}\n")
            except Exception as e:
                handle.write(f"ERROR: {type(e).__name__}: {e}\n")
                raise
            return res
        return wrapper
```

### Исходный код get_currencies (без логирования)

```py
def get_currencies(
    currency_codes: List[str],
    url: str = "https://www.cbr-xml-daily.ru/daily_json.js"
) -> Dict[str, float]:
    """
    Получает курсы валют из API ЦБ РФ
    
    Args:
        currency_codes: Список кодов валют (например, ['USD', 'EUR'])
        url: URL для получения данных
        
    Returns:
        Словарь вида {'USD': 93.25, 'EUR': 101.7}
        
    Raises:
        ConnectionError: Если API недоступен
        ValueError: Если JSON некорректный
        KeyError: Если отсутствует ключ 'Valute' или валюта в данных
        TypeError: Если значение курса имеет неверный тип
    """
    try:
        # Делаем запрос к API
        response = requests.get(url, timeout=5)
        response.raise_for_status()
    except requests.exceptions.RequestException as e:
        raise ConnectionError(f"Failed to connect to {url}: {e}")
    
    try:
        # Парсим JSON
        data = response.json()
    except ValueError as e:
        raise ValueError(f"Invalid JSON response: {e}")
    
    # Проверяем наличие ключа 'Valute'
    if "Valute" not in data:
        raise KeyError("'Valute' key not found in response")
    
    valute = data["Valute"]
    result = {}
    
    # Извлекаем курсы для каждой валюты
    for code in currency_codes:
        if code not in valute:
            raise KeyError(f"Currency '{code}' not found in data")
        
        value = valute[code]["Value"]
        
        # Проверяем тип значения
        if not isinstance(value, (int, float)):
            raise TypeError(f"Currency value for '{code}' has invalid type: {type(value)}")
        
        result[code] = float(value)
    
    return result
```

### Демонстрационный пример (квадратное уравнение).

![Демонстрационный пример (квадратное уравнение).](image.png)

### Скриншоты / фрагменты логов.

**currency.log:**
```
2025-12-07 18:51:34,123 - INFO - Calling get_currencies with args=(['USD', 'EUR'],), kwargs={}
2025-12-07 18:51:34,456 - INFO - get_currencies returned {'USD': 76.0937, 'EUR': 88.7028}
```

**quadratic.log:**
```
2025-12-07 18:51:34,456 - INFO - Calling solve_quadratic with args=(1.0, -5.0, 6.0), kwargs={}
2025-12-07 18:51:34,457 - INFO - Starting quadratic equation: 1.0x^2 + -5.0x + 6.0 = 0
2025-12-07 18:51:34,457 - INFO - Discriminant D = 1.0
2025-12-07 18:51:34,457 - INFO - Two roots found: x1 = 3.0, x2 = 2.0
2025-12-07 18:51:34,457 - INFO - solve_quadratic returned (3.0, 2.0)

2025-12-07 18:51:34,457 - INFO - Calling solve_quadratic with args=(1.0, 0.0, 1.0), kwargs={}
2025-12-07 18:51:34,458 - INFO - Starting quadratic equation: 1.0x^2 + 0.0x + 1.0 = 0
2025-12-07 18:51:34,458 - INFO - Discriminant D = -4.0
2025-12-07 18:51:34,458 - WARNING - Discriminant is negative (-4.0 < 0), no real roots
2025-12-07 18:51:34,458 - INFO - solve_quadratic returned No real roots (D=-4.0 < 0)

2025-12-07 18:51:34,458 - INFO - Calling solve_quadratic with args=('abc', 2.0, 1.0), kwargs={}
2025-12-07 18:51:34,458 - ERROR - TypeError: Coefficients must be numbers, but got (a: <class 'str'>, b: <class 'float'>, c: <class 'float'>)

2025-12-07 18:51:34,458 - INFO - Calling solve_quadratic with args=(0.0, 0.0, 1.0), kwargs={}
2025-12-07 18:51:34,459 - ERROR - ArithmeticError: a and b cannot both be zero (critical error)
```

### Выводы

Были реализованы:

1. **Параметризуемый декоратор `logger`** - универсальный инструмент для логирования вызовов функций, поддерживающий различные типы обработчиков (sys.stdout, файлоподобные объекты, logging.Logger)

2. **Функция `get_currencies`** - реализует получение курсов валют из API ЦБ РФ с полной обработкой исключений:
   - `ConnectionError` - при недоступности API
   - `ValueError` - при некорректном JSON
   - `KeyError` - при отсутствии необходимых данных
   - `TypeError` - при неверном типе значений

3. **Функция `solve_quadratic`** - решает квадратные уравнения с различными уровнями логирования:
   - `INFO` - для обычных операций
   - `WARNING` - для особых случаев (отрицательный дискриминант)
   - `ERROR` - для критических ошибок (неверные типы данных)

4. **Файловое логирование** - реализовано сохранение логов в файлы `currency.log` и `quadratic.log` с использованием модуля logging

