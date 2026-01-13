package enums;

/**
 * Перечисление полов
 */
public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String description;

    Gender(String description) {
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
