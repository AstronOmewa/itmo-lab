import enums.*;
import java.util.ArrayList;
import models.*;

public class Main {
    public static void main(String[] args) {
        // Создание персонажей
        Shutilo shutilo = new Shutilo();
        Korzhik korzhik = new Korzhik();
        Swisstulkin swisstulkin = new Swisstulkin();

        // Создание предметов
        Jacket korzhikJacket = new Jacket(korzhik, "куртка");
        Car korzhikCar = new Car("автомобиль", korzhik);
        DriverLicense korzhikDriverLicense = new DriverLicense(korzhikCar);
        Pants pants = new Pants(shutilo, "штаны");
        Tie tie = new Tie(shutilo, "галстук");
        Chulki chulki = new Chulki(shutilo, "чулки");
        FlowerPot flowerPot = new FlowerPot(FlowerType.DAISY);
        flowerPot.addFlower(FlowerType.DAISY);
        try {
            korzhikJacket.store(korzhikDriverLicense);
        } catch (Exception e) {

        }
        
        // Построение истории (Story) из последовательностей событий (EventSequence)
        Story tale = new Story();
        
        // === Событие 1: Шутило спешит и одевается неправильно ===
        EventSequence seq1 = shutilo.veryHurry();
        
        // Событие 1.1: Шутило надевает галстук неправильно и штаны с трудом
        EventSequence seq11 = new EventSequence();
        
        try {
            Event wearTie = tie.wearInsideOut()
                .addSubject(shutilo);
            seq11.addEvent(wearTie);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            Event wearPants = pants.misswear()
                .addSubject(shutilo);
            seq11.addEvent(wearPants);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            Event missarrange = chulki.missarrange();
            seq11.addEvent(missarrange);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        Event jumpOverRoom = shutilo.jumpOverRoom().addTime(Time.FORALONGTIME);

        seq11 = seq11.addEvent(jumpOverRoom);
        
        tale.addSequence(seq1);
        tale.addSequence(seq11);
        
        
        // === Событие 2: Шутило берет куртку Коржика и выходит ===
        EventSequence seq2 = new EventSequence();

        shutilo.addToInventory(korzhikJacket);
        try {
            korzhikJacket.wear();
        } catch (Exception e) {

        }
        shutilo.showInventory();
        
        Event breakFlower = shutilo.collideWith(flowerPot);
        seq2 = seq2.addEvent(breakFlower).addCondition(jumpOverRoom);
        
        seq2 = seq2.addEvent(shutilo.goOut(korzhikJacket));
        tale.addSequence(seq2);
        
        // === Событие 3: Шутило и Коржик спят ===
        EventSequence seq3 = new EventSequence();
        
        ArrayList<Entity> sleepers = new ArrayList<>();
        sleepers.add(shutilo);
        sleepers.add(korzhik);
        Event sleep = new Event(EventType.FALLASLEAP)
            .addTime(Time.VERYLATETONIGHT);
        sleep.subject = sleepers;
        seq3.addEvent(sleep);
        
        tale.addSequence(seq3);
        
        // // === Событие 4: Коржик обнаруживает пропажу куртки ===
        // EventSequence seq4 = new EventSequence();
        
        // Event discover = new Event(EventType.OBSERVEABSCENCE)
        //     .addSubject(korzhik)
        //     .addTime(Time.NEXTDAY);
        // seq4.addEvent(discover);
        
        // tale.addSequence(seq4);
        
        // Выполнение истории
//         for (var es : tale.story) {
//     System.out.println("SEQ = " + es);
// }
        tale.tell();
    }
}