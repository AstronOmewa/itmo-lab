# Лабораторная работа 7: Декораторы и логирование

## Описание

Лабораторная работа посвящена изучению параметризуемых декораторов в Python, обработке исключений при работе с внешними API и организации логирования.

## Основные компоненты

### 1. `handler.py` - Декоратор logger

Параметризуемый декоратор для логирования вызовов функций. Поддерживает три способа логирования:

#### Сигнатура:
```python
def logger(func=None, *, handle=sys.stdout):
    """Декоратор для логирования функций"""
```

#### Способы использования:

**1.1. Логирование в sys.stdout (по умолчанию)**
```python
@logger
def my_function():
    pass
```

**1.2. Логирование в файлоподобный объект (io.StringIO)**
```python
stream = io.StringIO()
@logger(handle=stream)
def my_function():
    pass
```

**1.3. Логирование через logging.Logger**
```python
log = logging.getLogger("my_logger")
@logger(handle=log)
def my_function():
    pass
```

#### Обязанности декоратора:

- При **успешном выполнении** логирует:
  - Уровень **INFO**: старт вызова с аргументами
  - Уровень **INFO**: завершение с результатом

- При **возникновении исключения** логирует:
  - Уровень **ERROR**: текст и тип исключения
  - Затем **повторно выбрасывает исключение**

- Использует `functools.wraps` для сохранения сигнатуры функции

### 2. `get_currencies.py` - Функция получения курсов валют

Функция получает текущие курсы валют из API ЦБ РФ.

#### Сигнатура:
```python
def get_currencies(
    currency_codes: List[str],
    url: str = "https://www.cbr-xml-daily.ru/daily_json.js"
) -> Dict[str, float]:
```

#### Примеры использования:

**Получение одной валюты:**
```python
result = get_currencies(['USD'])
# {'USD': 93.25}
```

**Получение нескольких валют:**
```python
result = get_currencies(['USD', 'EUR', 'GBP'])
# {'USD': 93.25, 'EUR': 101.7, 'GBP': 116.45}
```

#### Выбрасываемые исключения:

| Ситуация | Исключение |
|----------|-----------|
| API недоступен | `ConnectionError` |
| Некорректный JSON | `ValueError` |
| Нет ключа "Valute" | `KeyError` |
| Валюта отсутствует в данных | `KeyError` |
| Курс имеет неверный тип | `TypeError` |

#### Логирование:

Функция оборачивается декоратором `@logger(handle=file_logger)` для логирования в файл `currency.log`.

### 3. `solve_quadratic.py` - Решение квадратного уравнения

Решает квадратное уравнение вида ax² + bx + c = 0.

#### Сигнатура:
```python
def solve_quadratic(
    a: float,
    b: float,
    c: float
) -> Union[Tuple[float, float], ValueError]:
```

#### Примеры использования:

**Два различных корня:**
```python
result = solve_quadratic(1.0, -5.0, 6.0)
# (3.0, 2.0)
```

**Один двойной корень:**
```python
result = solve_quadratic(1.0, -2.0, 1.0)
# (1.0, 1.0)
```

**Нет действительных корней (D < 0):**
```python
result = solve_quadratic(1.0, 0.0, 1.0)
# ValueError("Discriminant is negative (D<0), no real roots")
```

#### Выбрасываемые исключения:

| Ситуация | Исключение |
|----------|-----------|
| Коэффициент не число | `TypeError` |
| a = b = 0 | `ArithmeticError` |
| D < 0 | `ValueError` (возвращается) |

#### Логирование:

Функция оборачивается декоратором `@logger(handle=file_logger)` для логирования в файл `quadratic.log`.

### 4. `test.py` - Модульные тесты

Полный набор тестов для проверки функциональности декоратора, функций и обработки исключений.

#### Классы тестов:

**TestDecoratorWithStringIO** - Тесты декоратора с io.StringIO
- `test_logging_success_stream` - Успешное выполнение функции
- `test_logging_exception_stream` - Логирование исключений

**TestDecoratorWithLogging** - Тесты декоратора с logging.Logger
- `test_logging_success_logger` - Успешное выполнение
- `test_logging_exception_logger` - Логирование исключений

**TestGetCurrencies** - Тесты функции get_currencies
- `test_get_currencies_success` - Успешное получение курсов
- `test_get_currencies_missing_key` - Отсутствие валюты
- `test_get_currencies_missing_valute` - Отсутствие ключа Valute
- `test_get_currencies_connection_error` - Ошибка соединения
- `test_get_currencies_invalid_json` - Некорректный JSON

**TestSolveQuadratic** - Тесты функции solve_quadratic
- `test_two_roots` - Два корня
- `test_one_root` - Один корень
- `test_negative_discriminant` - Отрицательный дискриминант
- `test_invalid_coefficients_type` - Неверный тип
- `test_both_zero` - a = b = 0

**TestDecoratorWithFileLogging** - Тесты файлового логирования

**TestDecoratorWithStdout** - Тесты с sys.stdout

#### Запуск тестов:

```bash
python -m pytest test.py -v
# или
python -m unittest test.py -v
```

### 5. `lab7.py` - Демонстрационная программа

Демонстрирует все компоненты лабораторной работы:

1. Логирование в sys.stdout
2. Логирование в io.StringIO
3. Логирование исключений
4. Логирование через logging.Logger
5. Использование get_currencies
6. Использование solve_quadratic

#### Запуск:

```bash
python lab7.py
```

## Файлы логирования

При запуске программы создаются логи в файлы:

- **`currency.log`** - Логи функции get_currencies
- **`quadratic.log`** - Логи функции solve_quadratic

Формат логов:
```
2024-11-30 15:30:45,123 - INFO - Started logging: get_currencies((['USD'],), {})
2024-11-30 15:30:46,456 - INFO - Finishing logging: get_currencies returned {'USD': 93.25}
```

## Ответы на контрольные вопросы

### Вопрос: Как логировать ошибки внутри функции, если логирование вынесено в декоратор?

**Ответ:**

Есть несколько подходов:

**1. Передать логгер как параметр функции:**
```python
def get_currencies(currency_codes: List[str], logger=None) -> Dict[str, float]:
    if logger:
        logger.warning(f"Checking currency {code}")
    # ...
```

**2. Использовать глобальный логгер:**
```python
# get_currencies.py
import logging
internal_logger = logging.getLogger("internal")

def get_currencies(currency_codes: List[str]):
    internal_logger.debug("Processing currencies...")  # Внутреннее логирование
    # ...
```

**3. Комбинированный подход с декоратором и внутренним логгером:**
```python
@logger(handle=file_logger)
def get_currencies(currency_codes: List[str]):
    internal_logger = logging.getLogger("get_currencies.internal")
    internal_logger.debug("Starting processing...")
    # Декоратор логирует начало/конец и исключения
    # Функция логирует внутренние события
```

В нашей реализации используется **третий подход**: декоратор отвечает за логирование вызовов и исключений, а функция содержит только бизнес-логику без логирования.

## Требования

- Python 3.8+
- requests (для get_currencies)
- unittest (встроенная библиотека)
- logging (встроенная библиотека)

## Примеры вывода

### Успешное выполнение:
```
INFO - Started logging: solve_quadratic((1.0, -5.0, 6.0), {})
INFO - Finishing logging: solve_quadratic returned (3.0, 2.0)
```

### Ошибка типа:
```
INFO - Started logging: solve_quadratic(('abc', 2.0, 1.0), {})
ERROR - TypeError: Coefficients must be numbers, but got (a: <class 'str'>, b: <class 'float'>, c: <class 'float'>)
```

### Ошибка подключения:
```
INFO - Started logging: get_currencies((['USD'],), {})
ERROR - ConnectionError: Failed to connect to https://www.cbr-xml-daily.ru/daily_json.js: ...
```