package models;

import enums.Gender;
import enums.HairColor;

/**
 * Селедочка - конструктор
 * НЕ содержит plot-specific логики!
 * Только универсальные методы базового класса Human
 */
public class Seledochka extends Human {

    public Seledochka() {
        super("Селедочка", Gender.FEMALE, 16, 41.5, HairColor.BLONDE);
    }

    @Override
    public String toString() {
        return super.toString() + " [Конструктор]";
    }
}
