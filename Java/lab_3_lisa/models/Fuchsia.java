package models;

import enums.Gender;
import enums.HairColor;

/**
 * Фуксия - конструктор
 * НЕ содержит plot-specific логики!
 * Только универсальные методы базового класса Human
 */
public class Fuchsia extends Human {

    public Fuchsia() {
        super("Фуксия", Gender.FEMALE, 16, 42.0, HairColor.BLACK);
    }

    @Override
    public String toString() {
        return super.toString() + " [Конструктор]";
    }
}
