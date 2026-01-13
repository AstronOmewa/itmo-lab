package models;

/**
 * Record для описания свойств материалов
 */
public record MaterialInfo(String name, double weight, boolean isRare, String description) {

    public MaterialInfo {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название материала не может быть пустым");
        }
        if (weight <= 0) {
            throw new IllegalArgumentException("Вес должен быть положительным числом");
        }
    }

    @Override
    public String toString() {
        String rarity = isRare ? " (редкий)" : "";
        return String.format("%s (%.2f кг)%s: %s", name, weight, rarity, description);
    }
}
