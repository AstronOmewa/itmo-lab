package enums;

/**
 * Перечисление цветов волос
 */
public enum HairColor {
    BLACK("Черные"),
    CHESTNUT("Каштановые"),
    BLONDE("Русые"),
    LIGHT("Светлые"),
    GRAY("Седые"),
    RED("Рыжие");

    private final String description;

    HairColor(String description) {
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
