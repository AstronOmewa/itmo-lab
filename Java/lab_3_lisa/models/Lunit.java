package models;

import enums.Location;
import java.util.Objects;

/**
 * Класс Лунит - редкий материал, влияющий на гравитацию
 */
public class Lunit extends PhysicalObject {

    private boolean isDiscovered;
    private Location discoveryLocation;
    private double gravitationalEffect;

    public Lunit() {
        super("Лунит", "создание прибора невесомости",
              new MaterialInfo("Лунит", 1.5, true, "редкий лунный минерал с антигравитационными свойствами"));
        this.isDiscovered = false;
        this.gravitationalEffect = 0.0;
    }

    public void discover(Location location) {
        this.isDiscovered = true;
        this.discoveryLocation = location;
        this.gravitationalEffect = 1.0;
        System.out.println("🌟 В " + location.getDescription() + " обнаружены залежи лунита!");
        System.out.println("   Теперь можно соорудить прибор невесомости, что облегчит полеты вокруг Луны");
    }

    public WeightlessnessDevice createDevice() {
        if (!isDiscovered) {
            throw new IllegalStateException("Лунит еще не обнаружен! Нельзя создать прибор.");
        }
        System.out.println("⚡ Из лунита создан прибор невесомости!");
        return new WeightlessnessDevice(this);
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public Location getDiscoveryLocation() {
        return discoveryLocation;
    }

    public double getGravitationalEffect() {
        return gravitationalEffect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return o instanceof Lunit;
    }

    @Override
    public int hashCode() {
        return Objects.hash("Лунит");
    }

    @Override
    public String toString() {
        if (isDiscovered) {
            return "Лунит (обнаружен в " + discoveryLocation.getDescription() +
                   ", эффект: " + gravitationalEffect + ")";
        }
        return "Лунит (не обнаружен)";
    }

    /**
     * Внутренний класс Прибор Невесомости
     */
    public static class WeightlessnessDevice extends PhysicalObject {
        private Lunit sourceLunit;
        private boolean isActive;

        public WeightlessnessDevice(Lunit lunit) {
            super("Прибор невесомости", "обеспечение невесомости",
                  new MaterialInfo("Лунит", 1.5, true, "активный антигравитационный элемент"));
            this.sourceLunit = lunit;
            this.isActive = false;
        }

        public void activate() {
            this.isActive = true;
            System.out.println("✨ Прибор невесомости активирован! Гравитация нейтрализована.");
        }

        public void deactivate() {
            this.isActive = false;
            System.out.println("Прибор невесомости деактивирован. Гравитация восстановлена.");
        }

        public boolean isActive() {
            return isActive;
        }
    }
}
