package models;

import enums.Location;
import interfaces.Transportable;
import exceptions.InsufficientResourcesException;
import exceptions.DeliveryException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Класс Ракета
 */
public class Rocket extends PhysicalObject implements Transportable {

    public enum ControlSystem {
        GRAVITY("управление для полетов в условиях тяжести"),
        ZERO_GRAVITY("управление для полетов в состоянии невесомости"),
        DUAL("двойное управление (тяжесть + невесомость)");

        private final String description;

        ControlSystem(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    private ControlSystem controlSystem;
    private ArrayList<Blueprint> blueprints;
    private boolean isLaunched;
    private Lunit.WeightlessnessDevice weightlessnessDevice;
    private boolean hasLunit;

    public Rocket(String name) {
        super(name, "космический корабль для полета на Луну",
              new MaterialInfo("Металл", 5000.0, false, "прочный космический сплав"));
        this.controlSystem = null;
        this.blueprints = new ArrayList<>();
        this.isLaunched = false;
        this.hasLunit = false;
    }

    public void setControlSystem(ControlSystem system) {
        this.controlSystem = system;
        System.out.println("На ракете '" + getName() + "' установлено: " + system.getDescription());
    }

    public void addBlueprint(Blueprint blueprint) {
        blueprints.add(blueprint);
        System.out.println("Чертеж '" + blueprint.getName() + "' добавлен к ракете '" + getName() + "'");
    }

    public void setLunitDevice(Lunit.WeightlessnessDevice device) {
        this.weightlessnessDevice = device;
        this.hasLunit = true;
        System.out.println("На ракету '" + getName() + "' установлен прибор невесомости!");
    }

    public void launch() throws InsufficientResourcesException {
        // Проверка наличия чертежей (unchecked exception через IllegalStateException)
        if (blueprints.isEmpty()) {
            throw new IllegalStateException("🚫 Ракета '" + getName() +
                                          "' не может быть запущена без чертежей!") {
                @Override
                public String getMessage() {
                    return "КРИТИЧЕСКАЯ ОШИБКА: " + super.getMessage();
                }
            };
        }

        // Проверка наличия лунита (checked exception)
        if (!hasLunit) {
            throw new InsufficientResourcesException(
                "Ракета '" + getName() + "' не может быть запущена. " +
                "Отсутствует прибор невесомости (лунит не найден). " +
                "Полет пройдет в штатном режиме.",
                "Лунит"
            );
        }

        this.isLaunched = true;
        System.out.println("🚀 Ракета '" + getName() + "' успешно запущена!");
        System.out.println("   Система управления: " + controlSystem.getDescription());
        if (hasLunit) {
            System.out.println("   Прибор невесомости: активирован");
        }
    }

    /**
     * Альтернативный запуск без лунита (в штатном режиме)
     */
    public void launchStandardMode() {
        if (blueprints.isEmpty()) {
            throw new IllegalStateException("Ракета '" + getName() +
                                          "' не может быть запущена без чертежей!") {
                @Override
                public String getMessage() {
                    return "КРИТИЧЕСКАЯ ОШИБКА: " + super.getMessage();
                }
            };
        }

        this.isLaunched = true;
        System.out.println("🚀 Ракета '" + getName() + "' запущена в штатном режиме");
        System.out.println("   Полет проходит в условиях тяжести");
        System.out.println("   Система управления: " +
                          (controlSystem != null ? controlSystem.getDescription() : "Не установлена"));
    }

    @Override
    public void transport(Blueprint blueprint) throws DeliveryException {
        if (!blueprint.isApproved()) {
            throw new DeliveryException("Чертеж не утвержден!", blueprint.getName());
        }
        addBlueprint(blueprint);
        System.out.println("Чертеж '" + blueprint.getName() + "' транспортирован к ракете '" + getName() + "'");
    }

    @Override
    public boolean isAvailableForTransport() {
        return !isLaunched;
    }

    public ControlSystem getControlSystem() {
        return controlSystem;
    }

    public ArrayList<Blueprint> getBlueprints() {
        return new ArrayList<>(blueprints);
    }

    public boolean isLaunched() {
        return isLaunched;
    }

    public boolean hasLunit() {
        return hasLunit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rocket rocket = (Rocket) o;
        return Objects.equals(getName(), rocket.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Ракета '" + getName() + "' (" +
               "чертежей: " + blueprints.size() + ", " +
               "управление: " + (controlSystem != null ? controlSystem.getDescription() : "не установлено") + ", " +
               "лунит: " + (hasLunit ? "есть" : "нет") + ")";
    }
}
