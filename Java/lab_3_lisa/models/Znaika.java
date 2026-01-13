package models;

import enums.Gender;
import enums.HairColor;

/**
 * Знайка - учёный
 * НЕ содержит plot-specific логики!
 * Только универсальные методы базового класса Human
 */
public class Znaika extends Human {

    public Znaika() {
        super("Знайка", Gender.MALE, 18, 45.5, HairColor.LIGHT);
    }

    @Override
    public String toString() {
        return super.toString() + " [Учёный]";
    }
}
