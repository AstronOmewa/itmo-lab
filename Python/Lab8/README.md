# Лабораторная работа 8: Клиент-серверное приложение

Простое веб-приложение для отслеживания курсов валют с архитектурой MVC.

## Описание

Приложение позволяет:
- Просматривать актуальные курсы валют ЦБ РФ
- Управлять пользователями
- Подписываться на отслеживание изменений курсов валют

## Архитектура

### MVC (Model-View-Controller)

- **Models** - классы предметной области в папке `models/`
- **Views** - HTML шаблоны Jinja2 в папке `templates/`
- **Controller** - HTTP сервер с маршрутизацией в `myapp.py`

### Структура проекта

```
Lab8/
├── models/                 # Модели данных
│   ├── __init__.py
│   ├── author.py          # Модель автора
│   ├── app.py             # Модель приложения
│   ├── user.py            # Модель пользователя
│   ├── currency.py        # Модель валюты
│   └── user_currency.py   # Связь пользователя и валюты
├── templates/              # HTML шаблоны
│   ├── index.html         # Главная страница
│   ├── users.html         # Список пользователей
│   ├── user_detail.html   # Детали пользователя
│   ├── currencies.html    # Курсы валют
│   └── author.html        # Информация об авторе
├── utils/                  # Утилиты
│   └── currencies_api.py  # API для получения курсов
├── tests/                  # Тесты
│   ├── __init__.py
│   ├── test_models.py
│   └── test_currencies_api.py
├── static/                 # Статические файлы (CSS, JS)
├── myapp.py               # Основной файл приложения
├── requirements.txt       # Зависимости
└── README.md             # Этот файл
```

## Установка и запуск

1. Установите зависимости:
```bash
pip install -r requirements.txt
```

2. Запустите сервер:
```bash
python myapp.py
```

3. Откройте в браузере:
```
http://localhost:8000
```

## Доступные маршруты

- `/` - Главная страница
- `/users` - Список пользователей
- `/user?id=<user_id>` - Детали пользователя
- `/currencies` - Курсы валют
- `/author` - Информация об авторе

## API эндпоинты

- `GET /api/users` - Получить список пользователей
- `POST /api/users` - Создать нового пользователя
- `GET /api/currencies` - Получить курсы валют
- `POST /api/subscriptions` - Создать подписку

## Тестирование

Запуск тестов:
```bash
python -m pytest tests/
```

или
```bash
python -m unittest tests.test_models
python -m unittest tests.test_currencies_api
```

## Автор

Зекин В.К., группа P3121

## Версия

1.0.0