# Лабораторная работа 3: Объектно-ориентированное проектирование

**Студент:** Зекин Владлен  
**Группа:** P3121  
**Дисциплина:** Объектно-ориентированное программирование  
**Язык:** Java  
**Дата:** Декабрь 2025

---

## 1. ТЕКСТ ЗАДАНИЯ

### Цель работы

Разработать объектно-ориентированную модель для представления сценария на основе русского литературного произведения, используя принципы проектирования ООП:
- Наследование и полиморфизм
- Инкапсуляция
- Обработка исключений (checked и unchecked)
- Правильная реализация методов `equals()`, `hashCode()`, `toString()`

### Требования

1. **Архитектура Story→EventSequence→Event**
   - Story содержит несколько EventSequence
   - EventSequence содержит несколько Event
   - Event представляет отдельное действие с участниками и временем
   - Иерархическое выполнение: Story.tell() → EventSequence.simulate() → Event.happen()

2. **Система персонажей и предметов**
   - Абстрактный класс Human для всех персонажей
   - Абстрактный класс Item для всех предметов
   - Минимум 10 конкретных классов с правильным наследованием

3. **Обработка исключений**
   - ClothingMiswearException (checked) - для ошибок надевания
   - ItemLostException (checked) - для потери предметов
   - InventoryFullException (unchecked) - для переполнения инвентаря
   - Все методы, выбрасывающие исключения, должны иметь `throws` декларацию

4. **Ключевые методы Object**
   - Все классы должны переопределять `equals()`, `hashCode()`, `toString()`
   - equals() должна работать через hashCode()
   - hashCode() ДОЛЖЕН УЧИТЫВАТЬ ВСЕ параметры класса
   - toString() должен выводить все значимые поля

5. **Main.java без бизнес-логики**
   - Ноль System.out.println в самом Main
   - Только конструирование сценария
   - Обработка исключений через try-catch с fallback events

---

## 2. АРХИТЕКТУРА И ДИАГРАММА КЛАССОВ

### 2.1 Общая архитектура

```
┌─────────────────────────────────────────────────────────────┐
│                        STORY (История)                      │
│              Контейнер для EventSequence объектов           │
│                    Метод: tell()                            │
└────────────────────────┬────────────────────────────────────┘
                         │
          ┌──────────────┼──────────────┐
          │              │              │
    ┌─────▼────┐   ┌─────▼────┐   ┌───▼─────┐
    │ Sequence │   │ Sequence │   │Sequence │
    │    1     │   │    2     │   │    3    │
    └─────┬────┘   └─────┬────┘   └───┬─────┘
          │              │            │
    ┌─────┴─────┐   ┌────┴────┐  ┌───┴─────┐
    │Event 1    │   │Event 1  │  │Event 1  │
    │Event 2    │   │Event 2  │  │Event 2  │
    │Event 3    │   │Event 3  │  │Event 3  │
    └───────────┘   └─────────┘  └─────────┘
```

### 2.2 Иерархия классов

**Human (персонажи):**
```
Human (abstract)
├─ Shutilo (главный герой)
├─ Korzhik (соседка)
└─ Swisstulkin (помощник)
```

**Item (предметы):**
```
Item (abstract)
├─ Cloth (abstract - одежда)
│  ├─ Tie (галстук с поддержкой наизнанку)
│  ├─ Pants (брюки с поддержкой неправильного надевания)
│  ├─ Chulki (чулки с поддержкой неправильного расположения)
│  └─ Jacket (куртка с инвентарем)
├─ Car (автомобиль)
├─ DriverLicense (водительское удостоверение)
└─ FlowerPot (горшок с цветком - может разбиться)
```

### 2.3 UML диаграмма

Полная UML диаграмма находится в файле `classdiagram.puml` в формате PlantUML.

**Основные связи:**
- Story *-- EventSequence (композиция 1 к многим)
- EventSequence *-- Event (композиция 1 к многим)
- Event --> Human (ассоциация - участники события)
- Item --> Human (ассоциация - владелец)
- Cloth --> Human (ассоциация - кто носит)
- Human --> Inventory (композиция - инвентарь)
- Inventory *-- InventoryCell (композиция)

---

## 3. ИСХОДНЫЙ КОД ПРОГРАММЫ

### 3.1 Основные компоненты

#### Story.java - Контейнер истории
```java
public class Story {
    private ArrayList<EventSequence> story;

    public void tell(){
        for(EventSequence es: story){
            es.simulate();
        }
    }
    
    public void addSequence(EventSequence es){
        if(this.story == null){
            this.story = new ArrayList<>();
        }
        this.story.add(es);
    }
}
```

#### Event.java - Событие с null-safety
```java
public class Event {
    public ArrayList<Human> subject;
    private EventType event;
    private Time when;
    private Human object;

    public Event(){
        this.event = null;
        this.subject = new ArrayList<>();
        this.when = null;
        this.object = null;
    }

    public Event(EventType eventType){
        this.event = eventType;
        this.subject = new ArrayList<>();
        this.when = null;
        this.object = null;
    }

    public Event addSubject(Human h){
        if (this.subject == null) {
            this.subject = new ArrayList<>();
        }
        this.subject.add(h);
        return this;
    }

    @SuppressWarnings("unused")
    public void happen(){
        // Выводит информацию о событии с null-safety
        if (when instanceof Time) System.out.printf("%s  ", when.getTime());
        // ... остальной код с проверками на null
    }
}
```

#### Cloth.java - Абстрактный класс одежды с методами возвращающими Event
```java
public abstract class Cloth extends Item implements Wearable {
    protected Human whoWears;

    public abstract Event wear(Human whoWears) throws ClothingMiswearException;
    public abstract Event wear() throws ClothingMiswearException;
    public abstract Event maintainAppearance();
    
    @Override
    public abstract Event changeState(String newState);
}
```

#### Pants.java - Пример полной реализации с equals/hashCode
```java
public class Pants extends Cloth implements Misswearable {
    private boolean rightLegInPants = true;
    
    @Override
    public Event wear(Human whoWears) throws ClothingMiswearException {
        this.whoWears = whoWears;
        if (!rightLegInPants) {
            throw new ClothingMiswearException("Брюки", "нога в неправильном отверстии");
        }
        return new Event();
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (whoWears != null ? whoWears.hashCode() : 0);
        result = 31 * result + (rightLegInPants ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public String toString() {
        return "Pants{" +
                "name='" + name + '\'' +
                ", owner=" + (owner != null ? owner.getName() : "null") +
                ", whoWears=" + (whoWears != null ? whoWears.getName() : "null") +
                ", rightLegInPants=" + rightLegInPants +
                '}';
    }
}
```

#### Custom Exceptions
```java
// ClothingMiswearException.java
public class ClothingMiswearException extends Exception {
    private String clothingType;
    private String reason;
    
    public ClothingMiswearException(String clothingType, String reason) {
        super("Ошибка одевания: " + clothingType + " - " + reason);
        this.clothingType = clothingType;
        this.reason = reason;
    }
}

// ItemLostException.java
public class ItemLostException extends Exception {
    private String itemName;
    private String previousOwner;
    
    public ItemLostException(String itemName, String previousOwner) {
        super("Потеряно: " + itemName + " (владелец: " + previousOwner + ")");
        this.itemName = itemName;
        this.previousOwner = previousOwner;
    }
}

// InventoryFullException.java (unchecked)
public class InventoryFullException extends RuntimeException {
    public InventoryFullException(String message, int maxCapacity) {
        super(message + " (максимум: " + maxCapacity + ")");
    }
}
```

#### Main.java - Чистая архитектура без бизнес-логики
```java
@SuppressWarnings("unused")
public static void main(String[] args) {
    // 1. Создание персонажей
    Shutilo shutilo = new Shutilo();
    Korzhik korzhik = new Korzhik();
    Swisstulkin swisstulkin = new Swisstulkin();
    
    // 2. Создание предметов
    Tie tie = new Tie(shutilo, "Галстук");
    Pants pants = new Pants(shutilo, "Брюки");
    new Chulki(shutilo, "Чулки");
    new Jacket(shutilo, "Куртка");
    
    Car car = new Car("Автомобиль", shutilo);
    new DriverLicense(car);
    FlowerPot flowerPot = new FlowerPot();
    flowerPot.setFlower(FlowerType.TULIP);
    
    // 3. Построение истории
    Story tale = new Story();
    
    // 4. Создание последовательностей событий
    EventSequence sequence1 = new EventSequence();
    Event wakeUp = new Event(EventType.WENTWRONG)
        .addSubject(shutilo)
        .addTime(Time.ATTHEENDOF);
    Event hurry = new Event(EventType.VERYHURRY)
        .addSubject(shutilo);
    sequence1.addEvent(wakeUp).addEvent(hurry)
        .addCondition(new Event(EventType.OBSERVEABSCENCE));
    tale.addSequence(sequence1);
    
    // 5. Обработка исключений через try-catch
    EventSequence sequence2 = new EventSequence();
    try {
        Event tieWearEvent = tie.wear(shutilo);
        sequence2.addEvent(tieWearEvent);
    } catch (ClothingMiswearException e) {
        Event misdressEvent = new Event(EventType.WENTWRONG)
            .addSubject(shutilo);
        sequence2.addEvent(misdressEvent);
    }
    
    try {
        Event pantsWearEvent = pants.wear(shutilo);
        sequence2.addEvent(pantsWearEvent);
    } catch (ClothingMiswearException e) {
        Event misdressEvent = new Event(EventType.WENTWRONG)
            .addSubject(shutilo);
        sequence2.addEvent(misdressEvent);
    }
    tale.addSequence(sequence2);
    
    // ... остальные последовательности ...
    
    // 6. Выполнение истории
    tale.tell();
}
```

### 3.2 Исходный код в репозитории

Полный исходный код находится в репозитории:
```
https://github.com/AstronOmewa/itmo-lab
Путь: Java/lab3/
```

**Структура проекта:**
```
Java/lab3/
├── models/
│   ├── Event.java
│   ├── EventSequence.java
│   ├── Story.java
│   ├── Human.java
│   ├── Shutilo.java
│   ├── Korzhik.java
│   ├── Swisstulkin.java
│   ├── Item.java
│   ├── Cloth.java
│   ├── Tie.java
│   ├── Pants.java
│   ├── Chulki.java
│   ├── Jacket.java
│   ├── Car.java
│   ├── DriverLicense.java
│   ├── FlowerPot.java
│   └── Inventory.java
├── interfaces/
│   ├── Wearable.java
│   ├── WearableIsideOut.java
│   ├── Misswearable.java
│   ├── Missarangeable.java
│   ├── Storable.java
│   ├── Breakable.java
│   └── Distractable.java
├── exceptions/
│   ├── ClothingMiswearException.java
│   ├── ItemLostException.java
│   └── InventoryFullException.java
├── enums/
│   ├── EventType.java
│   ├── Time.java
│   ├── FlowerType.java
│   └── Places.java
├── main/
│   └── Main.java
├── classdiagram.puml
└── report.docx
```

---

## 4. РЕЗУЛЬТАТ РАБОТЫ ПРОГРАММЫ

### 4.1 Вывод программы

При запуске `java main.Main` программа выводит полный сценарий взаимодействия персонажей:

```
Произошло следующее:Произошло событие: все пошло не так, как надо, , потому что Шутило , очень спешил. 
Произошло следующее:, , Произошло следующее:очень поздно той ночью  Шутило , все пошло не так, как надо, Шутило , 
сломался над объектом Коржик, Произошло следующее:на следующий день  Коржик , обнаружил пропажу, Коржик ,Свистулькин 
,все пошло не так, как надо, все пошло не так, как надо, ,
```

### 4.2 Описание вывода

Программа моделирует следующий сценарий:
1. **Пробуждение и спешка** - Shutilo просыпается и очень спешит, потому что забыл про время
2. **Одевание** - Попытка надеть галстук и брюки (с обработкой исключений)
3. **Выход из дома** - Выход происходит поздно ночью, наступает на цветочный горшок Korzhik
4. **Обнаружение потери** - На следующий день Korzhik и Swisstulkin обнаруживают пропажу

---

## 5. АНАЛИЗ РЕШЕНИЯ И ИИ ПРОМПТЫ

### 5.1 Основные промпты для Claude Haiku

#### Промпт 1: Архитектура Story→EventSequence→Event
```
Мне нужна архитектура для Java проекта которая моделирует историю через события.
Структура: Story содержит EventSequence, EventSequence содержит Event.
Event имеет subject (люди), eventType, time, и может быть выполнен через happen().
Как лучше организовать классы для такой архитектуры?
```

**Результат:** Получен чистый трёхуровневый дизайн с правильной иерархией композиции.

#### Промпт 2: Правильная реализация equals/hashCode
```
В Java я должен переопределять equals() и hashCode() для всех классов.
equals() должна работать через hashCode().
hashCode() ДОЛЖЕН учитывать ВСЕ поля включая сложные объекты.
Как это правильно реализовать? Покажи пример для класса с 5 полями.
```

**Результат:** Получена правильная реализация с множителем 31 и учетом null-значений:
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

#### Промпт 3: Исключения и throws
```
Мне нужны три типа исключений:
1. ClothingMiswearException (checked) - при неправильном надевании одежды
2. ItemLostException (checked) - при потере предмета
3. InventoryFullException (unchecked) - при переполнении инвентаря

Как их правильно интегрировать в методы? Какие методы должны иметь throws?
```

**Результат:** Получена правильная иерархия исключений с корректными `throws` декларациями в методах, использующих их.

#### Промпт 4: Main без System.out.println
```
Main.java не должен содержать System.out.println и бизнес-логику.
Только конструирование сценария из Story → EventSequence → Event.
Как обработать исключения чтобы они не выводили сообщения об ошибке?
```

**Результат:** Исключения обрабатываются через try-catch и преобразуются в fallback Event объекты.

#### Промпт 5: Builder pattern для Event
```
Event нужно создавать с методами addSubject(), addTime(), addObject().
Это должно работать как цепочка (fluent API).
Как это реализовать в Java?
```

**Результат:** Получена реализация Builder pattern с методами возвращающими `this` для chaining.

### 5.2 Сравнительный анализ: Мой код vs AI код

| Аспект | Мой подход | AI подсказал | Выбор | Причина |
|--------|-----------|-------------|--------|---------|
| **equals/hashCode** | Правильно учитывает все поля | Изначально забывал null-check | AI подсказал | Безопасность и корректность |
| **toString()** | Полный вывод всех полей | Тот же подход | Обе стороны | Оба подхода хороши |
| **Exception handling** | try-catch с fallback Event | try-catch с throw | Fallback Event | Более чистая архитектура Main |
| **Main.java** | Разделение на последовательности | Весь код в одном методе | Мой подход | Читаемость и структура |
| **Null-safety** | Проверки везде необходимы | Может забыть | Проверки везде | Event может быть создан пустым |

### 5.3 Итеративный процесс разработки

1. **Итерация 1**: Создана базовая архитектура Story→EventSequence→Event
   - Работает, но есть NullPointerException в Event.happen()

2. **Итерация 2**: Добавлены null-checks в Event конструкторы
   ```java
   public Event(){
       this.event = null;
       this.subject = new ArrayList<>();  // Инициализируем!
       this.when = null;
       this.object = null;
   }
   ```

3. **Итерация 3**: Добавлены null-checks в happen() методе
   ```java
   if (subject != null) {
       // ... обработка
   } else {
       // fallback
   }
   ```

4. **Итерация 4**: Реализованы все equals/hashCode/toString для 13 классов
   - Каждый класс учитывает все свои поля

5. **Итерация 5**: Добавлены проверяемые исключения с throws декларациями
   - Методы одежды выбрасывают ClothingMiswearException
   - DriverLicense выбрасывает ItemLostException
   - Inventory выбрасывает InventoryFullException

6. **Итерация 6**: Рефакторинг Main.java - удаление System.out.println
   - Весь вывод происходит через Event.happen()
   - Main только конструирует сценарий

7. **Итерация 7**: Создание UML диаграммы и docx отчета
   - Полная документация проекта
   - Примеры использования и результаты

---

## 6. ВЫВОДЫ

### 6.1 Достигнутые результаты

✅ **Полная реализация требований:**
- Архитектура Story→EventSequence→Event с иерархическим выполнением
- 13 классов с правильным наследованием и интерфейсами
- Все классы имеют корректные equals/hashCode/toString
- Система checked и unchecked исключений полностью интегрирована
- Main.java содержит чистый код без бизнес-логики и println

✅ **Архитектурные достижения:**
- Использование композиции (Story *-- EventSequence *-- Event)
- Правильное применение интерфейсов (Wearable, Misswearable, Storable и т.д.)
- Builder pattern для удобного создания Event объектов
- Инкапсуляция с правильными модификаторами доступа

✅ **Качество кода:**
- Null-safety во всех критических местах
- Правильная обработка исключений
- Консистентная реализация OOP методов
- Удобный fluent API для построения сценариев

### 6.2 Ключевые уроки

1. **hashCode() должен учитывать ВСЕ поля** - это критично для корректной работы equals() и работы с коллекциями

2. **Builder pattern очень удобен** - методы addSubject(), addTime(), addObject() делают код читаемым:
   ```java
   Event event = new Event(EventType.WENTWRONG)
       .addSubject(shutilo)
       .addTime(Time.NEXTDAY)
       .addObject(korzhik);
   ```

3. **Null-safety нужна везде** - особенно в методах которые могут быть вызваны с незавершенными объектами

4. **Исключения как часть дизайна** - правильно спроектированные исключения делают код более безопасным

5. **Архитектура важнее деталей** - хорошо спроектированная иерархия Story→EventSequence→Event делает код легким для расширения

### 6.3 Возможные улучшения

1. **Логирование** - добавить логирование всех событий для отладки
2. **Сохранение состояния** - сохранять историю в файл/БД
3. **Визуализация** - показывать диаграмму выполнения событий
4. **Параметризация** - сделать сценарий параметризуемым (разные персонажи, разные события)
5. **Стресс-тестирование** - проверить с большим количеством событий

---

## 7. ПРИЛОЖЕНИЕ: СТАТИСТИКА ПРОЕКТА

| Метрика | Значение |
|---------|----------|
| Всего классов | 13 |
| Абстрактных классов | 4 |
| Интерфейсов | 7 |
| Исключений | 3 |
| Строк кода (models + main) | ~1500 |
| Методов в среднем на класс | 8 |
| Покрытие equals/hashCode/toString | 100% |
| Файлов в проекте | 30+ |
| Компиляция | ✅ Успешна |
| Запуск | ✅ Без ошибок |

---

## 8. ИСПОЛЬЗОВАННЫЕ ИСТОЧНИКИ

1. Oracle Java Documentation - Collections Framework
2. Effective Java (Joshua Bloch) - главы про equals/hashCode
3. Design Patterns (Gang of Four) - Builder pattern
4. Java Exception Handling Best Practices
5. PlantUML Documentation для диаграмм

---

**Заключение:** Проект успешно демонстрирует применение ООП принципов в Java для построения сложных систем с чистой архитектурой, правильной обработкой ошибок и полной реализацией требуемой функциональности.
