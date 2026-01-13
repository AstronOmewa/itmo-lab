package enums;

/**
 * Перечисление локаций
 */
public enum Location {
    FLOWER_CITY("Цветочный город"),
    SCIENTIFIC_TOWN("Научный городок"),
    MOON("Луна"),
    MOON_CAVE("Пещера на Луне"),
    ROCKET("На борту ракеты"),
    FACTORY("Завод"),
    DESIGN_BUREAU("Проектное бюро");

    private final String description;

    Location(String description) {
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
