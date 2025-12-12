import enums.*;
import java.util.ArrayList;
import models.*;

public class Main {
    public static void main(String[] args) {
        // Время событий (с начала)
        Time t = new Time(20,00,00);

        // Создание персонажей
        Shutilo shutilo = new Shutilo();
        Korzhik korzhik = new Korzhik();
        Swisstulkin swisstulkin = new Swisstulkin();

        // Создание предметов
        Jacket korzhikJacket = new Jacket(korzhik, "куртка");
        Jacket swisstulkinJacket = new Jacket(swisstulkin, "куртка");
        Car korzhikCar = new Car("автомобиль", korzhik);
        DriverLicense korzhikDriverLicense = new DriverLicense(korzhikCar);
        Pants pants = new Pants(shutilo, "штаны");
        Tie tie = new Tie(shutilo, "галстук");
        Chulki chulki = new Chulki(shutilo, "чулки");
        FlowerPot flowerPot = new FlowerPot(FlowerType.DAISY);


        // инвентаризация
        flowerPot.addFlower(FlowerType.DAISY);
        shutilo.addToInventory(tie);
        shutilo.addToInventory(chulki);
        shutilo.addToInventory(pants);

        try {
            korzhikJacket.getInventory().addItem(korzhikDriverLicense, Places.LEFTPOCKET);
        } catch (Exception e) {

        }

       
        
        // Построение истории (Story) из последовательностей событий (EventSequence)
        Story tale = new Story();
        
        // === Событие 1: Шутило спешит и одевается неправильно (возможно, а может и правильно) ===
        EventSequence seq1 = shutilo.veryHurry().addTime(t);
        
        // Событие 1.1: Шутило надевает галстук неправильно и штаны с трудом
        EventSequence seq11 = new EventSequence();

        Event jumpOverRoom = shutilo.jumpOverRoom().addTime(new Time("в течение долгого времени"));

        seq11.addEvent(jumpOverRoom);
        seq11 = seq11.addCondition(shutilo.distract());

        tale.addSequence(seq1);
        tale.addSequence(seq11);
        
        
        // === Событие 2: Шутило берет куртку Коржика и выходит ===

        // Шутило берет куртку Коржика
        shutilo.addToInventory(korzhikJacket, Places.LEFTPOCKET);
        try {
            korzhikJacket.wear(shutilo);
        } catch (Exception e) {

        }

        EventSequence breakFlower = shutilo.collideWith(flowerPot).addCondition(shutilo.distract());
        tale.addSequence(breakFlower);
        EventSequence goOut = shutilo.goOut(korzhikJacket).addCondition(shutilo.distract()).addTime(t);
        tale.addSequence(goOut);

        // Шутило ушел, оставив куртку Свистулькина Коржику
        korzhik.addToInventory(swisstulkinJacket);
        
        // === Событие 3: Шутило и Коржик спят ===
        EventSequence seq3 = new EventSequence();
        
        ArrayList<Entity> sleepers = new ArrayList<>();
        sleepers.add(shutilo);
        sleepers.add(korzhik);
        Event sleep = new Event("уснули").addTime(new Time(1,0,0)).addSubject(sleepers);
        seq3 = seq3.addEvent(sleep);
        Time t1 = t.elapse(10,0,0);
        // System.out.println(t.getString());
        Event discover = korzhikJacket.checkWhoWears();
        discover.addTime(t1);
        seq3.addEvent(discover);
        tale.addSequence(seq3);
        
        // === Событие 4: Коржик проверяет свою одежду ===
        EventSequence seq4 = korzhik.checkWardrobe().addTime(t1);
        tale.addSequence(seq4);

        // Выполнение истории
        tale.tell();
    }
}