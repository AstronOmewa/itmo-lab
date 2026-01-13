import enums.Location;
import models.*;

/**
 * Демонстрация расширяемости новой архитектуры
 */
public class Demo {
    public static void main(String[] args) {

        Znaika znaika = new Znaika();
        Fuchsia fuchsia = new Fuchsia();

        // ОДНИ И ТЕ ЖЕ МЕТОДЫ для разных персонажей!
        znaika.think("о ракете", "научная идея");
        fuchsia.think("о чертежах", "конструкторская идея");

        znaika.proposeIdea("установить двойное управление", "ракетостроение");
        fuchsia.proposeIdea("использовать титан", "материаловедение");

        System.out.println();

        Rocket rocket = new Rocket("Лунная ракета");
        Blueprint blueprint = new Blueprint("Чертеж двигателя", "Двигатель");

        // ОДИН И ТОТ ЖЕ метод perform() с разными целями
        znaika.perform("разработка", "разрабатывает чертёж")
            .withTarget(blueprint)
            .execute();

        fuchsia.perform("создание", "создаёт эскиз")
            .withTarget(rocket)
            .execute();

        // Множественные цели
        znaika.perform("проверка", "проверяет документы")
            .withTarget(rocket)
            .withTarget(blueprint)
            .execute();

        System.out.println();

        Human neznaika = new Human("Незнайка", enums.Gender.MALE, 15, 40.0, enums.HairColor.LIGHT) {};

        // Сразу можем использовать все универсальные методы:
        neznaika.think("о приключениях");
        neznaika.proposeIdea("полететь на Луну", "приключение");
        neznaika.perform("путешествие", "отправляется в путь")
            .withTarget("ракета")
            .execute();
        neznaika.moveTo(Location.MOON);

        System.out.println();

        Thought thought1 = znaika.think("об использовании лунита", "техническая идея");
        System.out.println("Создана мысль: " + thought1);
        System.out.println("   Категория: " + thought1.getCategory());
        System.out.println("   Автор: " + thought1.getAuthor().getName());
        System.out.println("   Поделена: " + thought1.isShared());

        znaika.propose(thought1);
        System.out.println("   После propose: " + thought1.isShared());

        System.out.println();

        Event discovery = new Event("Открытие", "Найден лунит!", Event.EventType.DISCOVERY)
            .withParticipant(znaika)
            .withParticipant(fuchsia)
            .withParticipant("экспедиция");
        discovery.occur();

        System.out.println();

        Human donut = new Human("Пончик", enums.Gender.MALE, 16, 48.0, enums.HairColor.CHESTNUT) {};

        // Пончик может делать всё то же, что и Знайка - без наследования!
        donut.think("о еде");
        donut.perform("едание", "ест пончик")
            .withTarget("вкусный пончик")
            .execute();

        // И даже участвовать в ракетной программе!
        donut.proposeIdea("установить на ракету холодильник для еды", "важное дело");
        donut.study("космонавтику");

    }
}
