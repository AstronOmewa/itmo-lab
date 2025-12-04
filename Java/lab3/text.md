Он очень спешил, и поэтому у него все получалось не так, как надо. Галстук он надел наизнанку, чулки перепутал; правая нога у него никак не хотела пролезть в штанину, поэтому он долго скакал по всей комнате на левой ноге, под конец налетел на цветочный горшок с маргаритками и разбил его вдребезги. Кончилось тем, что он напялил на себя по рассеянности куртку Коржика и ушел в ней. В ту ночь Шутило и Коржик легли спать совсем поздно, и только на следующее утро Коржик обнаружил пропажу своей куртки. Правда, у него осталась куртка Свистулькина, но беда была в том, что вместе с курткой пропало Коржиково удостоверение на право вождения автомобиля, которое лежало у него в боковом кармане.

================
// Объявляем объекты
Shutilo = Shutilo()
Korzhik = Korzhik() 
Swisstulkin = Swisstulkin()

// Предметы
korzhikCar = Car(owner=Korzhik)
korzhikLicense = DriverLicense(vehicle=korzhikCar)
korzhikJacket = Jacket(owner=Korzhik)
swisstulkinJacket = Jacket(owner=Swisstulkin)

// Добавляем права в карман куртки Коржика
inventoryCell = InventoryCell(item=korzhikLicense, place=Place.LEFTPOCKET)
korzhikJacket.inventory = Inventory(cells=[inventoryCell])

// Одежда Шутило
shutiloTie = Tie(owner=Shutilo)
shutiloChulki = Chulki(owner=Shutilo)  
shutiloPants = Pants(owner=Shutilo)

// Цветочный горшок
daisyFlowerPot = FlowerPot(flower=FlowerType.DAISY)

// Куртка Свистулькина у Коржика
Korzhik.addToInventory(swisstulkinJacket)

// События

    Shutilo.veryHurry() // == EventSequence()
.

EventSequence(
    shutiloTie.wearInsideOut(),
    shutiloChulki.missarrange(),
    Shutilo.jumpOverRoom(),
    Shutilo.collideWith(daisyFlowerPot),
    daisyFlowerPot.break(),
    condition = shutiloPants.misswear()
).

EventSequence(
    korzhikJacket.wear(whoWears=Shutilo),
    Shutilo.goOut(),
    condition = Shutilo.distract()
).

EventSequence(
    Event(
        subject = [Shutilo, Korzhik],
        eventType = EventType.FALLASLEEP,
        when = Time.VERYLATEATTHATNOGHT
    )
).

EventSequence(
    Korzhik.addToInventory(Jacket(owner=Swisstulkin)),
    Korzhik.showInventory(),
    Event(
        subject = Korzhik,
        eventType = EventType.OBSERVEABSCENCE,
        object = korzhikJacket,
        when = Time.NEXTDAY
    ),
    condition = Event(
        subject = [Shutilo, Korzhik],
        eventType = EventType.FALLASLEEP,
        when = Time.VERYLATEATTHATNOGHT
    )
).
