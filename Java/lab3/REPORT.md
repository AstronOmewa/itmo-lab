# Отчёт: Java Lab 3 - OOP Model of Literary Text

## 1. Введение

Проект Lab 3 реализует объектно-ориентированную модель русского литературного текста "История про Шутило" с использованием принципов проектирования ООП, паттернов проектирования и архитектурных подходов.

Основная цель: создать архитектуру, которая моделирует события (Events), их последовательности (EventSequences) и полную историю (Story), позволяя описать сложные сценарии взаимодействия персонажей с предметами.

## 2. Архитектура

### 2.1 Иерархия Story → EventSequence → Event

Архитектура построена на трёхуровневой иерархии:

```
Story
  └─ EventSequence 1
      ├─ Event 1
      ├─ Event 2
      └─ Event 3
  └─ EventSequence 2
      ├─ Event 1
      └─ Event 2
```

- **Story**: Контейнер для всех последовательностей событий. Метод `tell()` выполняет все последовательности.
- **EventSequence**: Логическая группа событий с условиями, временем и объектами. Метод `simulate()` исполняет все события.
- **Event**: Отдельное событие с субъектами (люди), типом события, временем и объектом. Метод `happen()` выводит описание события.

### 2.2 Ключевые компоненты

#### Персонажи (Characters)
- **Human** (abstract): Базовый класс для всех персонажей
  - `name`: Имя персонажа
  - `inventory`: Инвентарь предметов
  - Методы: `addToInventory()`, `rmFromInventory()`

- **Shutilo**: Главный персонаж (спешит и путает вещи)
- **Korzhik**: Вспомогательный персонаж
- **Swisstulkin**: Вспомогательный персонаж

#### Предметы (Items)
- **Item** (abstract): Базовый класс для всех предметов
  - `owner`: Владелец предмета
  - `name`: Название предмета
  - `changeState(String)`: Изменить состояние предмета

- **Cloth** (abstract): Одежда
  - `whoWears`: Кто надел предмет одежды
  - `wear(Human)`: Надеть одежду на человека
  - `maintainAppearance()`: Следить за внешним видом

- **Tie**: Галстук с поддержкой надевания наизнанку
- **Pants**: Брюки с поддержкой неправильного надевания
- **Chulki**: Чулки с поддержкой неправильного расположения
- **Jacket**: Куртка с карманами (Inventory)

- **Car**: Автомобиль
- **DriverLicense**: Водительское удостоверение
- **FlowerPot**: Горшок с цветком (может разбиться)

#### Хранилища
- **Inventory**: Контейнер для предметов (max 5 объектов)
  - `InventoryCell`: Запись предмета с местоположением

## 3. Обработка Исключений

### 3.1 Custom Exceptions

#### ClothingMiswearException (checked)
Выбрасывается при неправильном надевании одежды:
```java
throw new ClothingMiswearException("Галстук", "надет наизнанку");
```

#### ItemLostException (checked)
Выбрасывается при потере предмета:
```java
throw new ItemLostException("Водительское удостоверение", "Шутило");
```

#### InventoryFullException (unchecked)
Выбрасывается при переполнении инвентаря:
```java
throw new InventoryFullException("Инвентарь переполнен", 5);
```

### 3.2 Обработка в Main

В Main.java исключения обрабатываются через try-catch блоки, которые преобразуют исключения в fallback Events:

```java
try {
    Event tieWearEvent = tie.wear(shutilo);
    sequence2.addEvent(tieWearEvent);
} catch (ClothingMiswearException e) {
    Event misdressEvent = new Event(EventType.WENTWRONG).addSubject(shutilo);
    sequence2.addEvent(misdressEvent);
}
```

## 4. Реализация OOP принципов

### 4.1 Инкапсуляция

Все поля классов использованы с правильными модификаторами доступа:
- `private`: Приватные поля (не должны быть доступны извне)
- `protected`: Защищённые поля (доступны наследникам)
- `public`: Публичные поля (только Event.subject для удобства)

### 4.2 Наследование

Классы образуют правильную иерархию:
- `Human` ← `Shutilo`, `Korzhik`, `Swisstulkin`
- `Item` ← `Cloth` ← `Tie`, `Pants`, `Chulki`, `Jacket`
- `Item` ← `Car`, `DriverLicense`

### 4.3 Полиморфизм

Переопределены методы в наследниках:
- `wear()` в каждом классе одежды с специфичной логикой
- `changeState()` в каждом классе предмета
- `showInventory()` в каждом персонаже

### 4.4 equals(), hashCode(), toString()

Все 13 классов моделей реализуют:
- **equals()**: Использует hashCode() для сравнения
- **hashCode()**: Учитывает ВСЕ значимые поля (множитель 31)
- **toString()**: Выводит все свойства объекта

Пример (Pants.java):
```java
@Override
public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (owner != null ? owner.hashCode() : 0);
    result = 31 * result + (whoWears != null ? whoWears.hashCode() : 0);
    result = 31 * result + (rightLegInPants ? 1 : 0);
    return result;
}
```

## 5. Архитектура Main.java

### 5.1 Принцип Design

Main.java содержит ТОЛЬКО конструирование сценария, без:
- ❌ System.out.println (выводу информации)
- ❌ Бизнес-логики (условных операторов, случайных выборов)
- ❌ Явной обработки ошибок (вывода ошибок в консоль)

### 5.2 Структура Main.java

```java
public class Main {
    public static void main(String[] args) {
        // 1. Создание персонажей
        Shutilo shutilo = new Shutilo();
        Korzhik korzhik = new Korzhik();
        
        // 2. Создание предметов
        Tie tie = new Tie(shutilo, "Галстук");
        Car car = new Car("Автомобиль", shutilo);
        
        // 3. Построение истории
        Story tale = new Story();
        
        // 4. Создание последовательностей с событиями
        EventSequence sequence1 = new EventSequence();
        Event wakeUp = new Event(EventType.WENTWRONG).addSubject(shutilo);
        sequence1.addEvent(wakeUp);
        tale.addSequence(sequence1);
        
        // 5. Обработка исключений через fallback events
        try {
            Event tieWearEvent = tie.wear(shutilo);
            sequence2.addEvent(tieWearEvent);
        } catch (ClothingMiswearException e) {
            Event fallbackEvent = new Event(EventType.WENTWRONG).addSubject(shutilo);
            sequence2.addEvent(fallbackEvent);
        }
        
        // 6. Выполнение истории (весь вывод происходит здесь)
        tale.tell();
    }
}
```

## 6. Примеры использования

### 6.1 Создание простого события

```java
Event event = new Event(EventType.WENTWRONG)
    .addSubject(shutilo)
    .addTime(Time.ATTHEENDOF)
    .addObject(korzhik);
```

### 6.2 Создание последовательности

```java
EventSequence sequence = new EventSequence();
sequence.addEvent(event1);
sequence.addEvent(event2);
sequence.addCondition(conditionEvent);
sequence.addTime(Time.NEXTDAY);
```

### 6.3 Создание истории и выполнение

```java
Story story = new Story();
story.addSequence(sequence1);
story.addSequence(sequence2);
story.tell(); // Выполняет все события
```

## 7. Обработка Checked Exceptions

Все методы, которые могут выбросить checked exceptions, содержат:
- `throws ClothingMiswearException` для методов одежды
- `throws ItemLostException` для методов потери предметов
- `throws InventoryFullException` для методов управления инвентарём

Пример:
```java
@Override
public Event wear(Human whoWears) throws ClothingMiswearException {
    if (!isCorrectlyArranged) {
        throw new ClothingMiswearException("Чулки", "неправильно расположены");
    }
    return new Event();
}
```

## 8. Интерфейсы и Contract Programming

### 8.1 Используемые интерфейсы

- **Wearable**: Предметы, которые можно надеть
- **WearableInsideOut**: Предметы, которые можно надеть наизнанку
- **Misswearable**: Предметы, которые можно надеть неправильно
- **Missarangeable**: Предметы, которые можно расположить неправильно
- **Storable**: Предметы, которые можно хранить
- **Breakable**: Предметы, которые можно сломать
- **Distractable**: Персонажи, которые отвлекаются

### 8.2 Множественное наследование интерфейсов

```java
public class Jacket extends Cloth implements Storable {
    // Наследует Cloth (которая implements Wearable)
    // Дополнительно implements Storable
}
```

## 9. Паттерны проектирования

### 9.1 Builder Pattern (метод chaining)

```java
Event event = new Event(EventType.WENTWRONG)
    .addSubject(shutilo)
    .addTime(Time.NEXTDAY)
    .addObject(korzhik);
```

### 9.2 Template Method Pattern

`EventSequence.simulate()` использует template method для выполнения цепи событий.

### 9.3 Decorator Pattern

`Inventory` декорирует `Item` по расположению в `InventoryCell`.

## 10. Результаты

### 10.1 Компиляция

✅ Все файлы успешно компилируются без ошибок

### 10.2 Архитектурные требования

✅ Story содержит EventSequence  
✅ EventSequence содержит Event  
✅ Event.happen() выполняет вывод события  
✅ Main не содержит println  
✅ Main не содержит бизнес-логики  
✅ Main только конструирует сценарий  

### 10.3 OOP требования

✅ Все 13 классов имеют правильные equals/hashCode/toString  
✅ Все методы с исключениями содержат throws  
✅ Правильное наследование и полиморфизм  
✅ Инкапсуляция с private/protected/public  

## 11. Заключение

Проект успешно реализует объектно-ориентированную архитектуру для моделирования сложных сценариев с использованием:

- Трёхуровневой иерархии (Story → EventSequence → Event)
- Custom exceptions для различных ошибочных ситуаций
- Правильного применения ООП принципов
- Чистой архитектуры Main.java без бизнес-логики
- Полного использования функций Java (интерфейсы, наследование, полиморфизм)

Код готов к дальнейшему расширению и добавлению новых типов событий, персонажей и предметов.
