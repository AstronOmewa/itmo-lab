package models;

import exceptions.ClothingMiswearException;
import interfaces.Misswearable;

public class Pants extends Cloth implements Misswearable {
    private boolean rightLegInPants = true;
    
    public Pants(Human whoWears, String name) {
        super(whoWears, name);
    }
    
    @Override
    public Event wear(Human whoWears) throws ClothingMiswearException {
        this.whoWears = whoWears;
        if (!rightLegInPants) {
            throw new ClothingMiswearException("Брюки", "нога в неправильном отверстии");
        }
        return new Event();
    }

    @Override
    public Event wear() throws ClothingMiswearException {
        return wear(this.whoWears);
    }

    @Override
    public Event misswear() throws ClothingMiswearException {
        rightLegInPants = false;
        throw new ClothingMiswearException("Брюки", "не хотели налезать");
    }

    @Override
    public Event maintainAppearance() {
        return new Event();
    }

    @Override
    public Event changeState(String newState) {
        return new Event();
    }

    @Override
    public String toString() {
        return "Pants{" +
                "name='" + name + '\'' +
                ", owner=" + (owner != null ? owner.getName() : "null") +
                ", whoWears=" + (whoWears != null ? whoWears.getName() : "null") +
                ", rightLegInPants=" + rightLegInPants +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (whoWears != null ? whoWears.hashCode() : 0);
        result = 31 * result + (rightLegInPants ? 1 : 0);
        return result;
    }
}
