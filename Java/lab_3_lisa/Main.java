import models.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Лабораторная работа №3: "Незнайка на Луне"
 *
 * Использует ТОЛЬКО универсальные методы базовых классов.
 * Никаких plot-specific методов в персонажах!
 */
public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        // Персонажи
        Znaika znaika = new Znaika();
        Fuchsia fuchsia = new Fuchsia();
        Seledochka seledochka = new Seledochka();

        // Объекты
        Rocket rocket = new Rocket("Ракета");
        Lunit lunit = new Lunit();

        // Сцена 1: Идея Знайки
       znaika.think(
            "последняя ступень ракеты должна была иметь двоякое управление: " +
            "управление для полетов в условиях тяжести и управление для полетов в состоянии невесомости"
        );

        znaika.think(
            "по прибытии на Луну они обнаружат в какой-нибудь из пещер залежи лунита. " +
            "Обладая же хоть небольшим кусочком лунита, нетрудно будет соорудить прибор невесомости"
        );

        // Сцена 2: Создание эскизов
        fuchsia.perform("создание", "составила эскиз")
            .withTarget(rocket)
            .execute();

        seledochka.perform("создание", "составила эскиз")
            .withTarget(rocket)
            .execute();

        // Сцена 3: Отправка в Научный городок
        fuchsia.perform("перемещение", "отправилась в Научный городок").execute();
        fuchsia.moveTo(enums.Location.SCIENTIFIC_TOWN);

        seledochka.perform("перемещение", "отправилась в Научный городок").execute();
        seledochka.moveTo(enums.Location.SCIENTIFIC_TOWN);

        // Сцена 4: Инженеры
        ArrayList<String> engineers = new ArrayList<>();
        engineers.add("инженер Винтик");
        engineers.add("инженер Шпунтик");
        engineers.add("конструктор Тюбик");

        Action coordAction = znaika.perform("координация", "координирует работу инженеров");
        for (String engineer : engineers) {
            coordAction.withTarget(engineer);
        }
        coordAction.execute();

        // Сцена 5: Чертежи
        ArrayList<Blueprint> blueprints = new ArrayList<>();
        blueprints.add(new Blueprint("чертеж отливок", "отливки"));
        blueprints.add(new Blueprint("чертеж поковок", "поковки"));
        blueprints.add(new Blueprint("чертеж штамповок", "штамповки"));
        blueprints.add(new Blueprint("чертеж аппаратуры управления", "аппаратура"));

        for (Blueprint bp : blueprints) {
            bp.approve();
            znaika.perform("утверждение", "утверждает")
                .withTarget(bp)
                .execute();
            rocket.addBlueprint(bp);
        }

        // Сцена 6: Вариативность - поиск лунита
        boolean lunitFound = random.nextBoolean();

        if (lunitFound) {
            // С лунитом
            Event discovery = new Event("Открытие", "Обнаружен лунит", Event.EventType.DISCOVERY)
                .withParticipant("экспедиция");
            discovery.occur();

            lunit.discover(enums.Location.MOON_CAVE);
            Lunit.WeightlessnessDevice device = lunit.createDevice();
            device.activate();
            rocket.setControlSystem(Rocket.ControlSystem.DUAL);
            rocket.setLunitDevice(device);

            try {
                rocket.launch();
            } catch (exceptions.InsufficientResourcesException e) {
                System.out.println(e.getMessage());
            }

        } else {
            // Без лунита
            rocket.setControlSystem(Rocket.ControlSystem.GRAVITY);

            try {
                rocket.launch();
            } catch (exceptions.InsufficientResourcesException e) {
                rocket.launchStandardMode();
            } catch (IllegalStateException e) {
                rocket.launchStandardMode();
            }
        }
    }
}
