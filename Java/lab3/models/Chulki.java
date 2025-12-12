package models;

import exceptions.ClothingMiswearException;
import interfaces.Missarangeable;
import java.util.ArrayList;
import java.util.Arrays;

public class Chulki extends Cloth implements Missarangeable {

    private boolean isCorrectlyArranged = true;

    public Chulki(Human whoWears, String name) {
        super(whoWears, name);

    }

    @Override
    public Event wear(Human whoWears) throws ClothingMiswearException {
        this.whoWears = whoWears;
        Event e = new Event();
        if (Math.random() < 0.45) {
            e = missarrange();
        }
        if (!isCorrectlyArranged) {
            throw new ClothingMiswearException("Чулки", "неправильно расположены");
        }
        return new Event("одел правильно", new ArrayList<Entity>(Arrays.asList(this.whoWears)), new Time(-1, 0, 0), this);
    }

    @Override
    public Event wear() throws ClothingMiswearException {
        return wear(this.whoWears);
    }

    @Override
    public Event missarrange() throws ClothingMiswearException {
        isCorrectlyArranged = false;
        throw new ClothingMiswearException("Чулки", "перепутаны");
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
        return "Chulki{"
                + "name='" + name + '\''
                + ", owner=" + (owner != null ? owner.getName() : "null")
                + ", whoWears=" + (whoWears != null ? whoWears.getName() : "null")
                + ", isCorrectlyArranged=" + isCorrectlyArranged
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (whoWears != null ? whoWears.hashCode() : 0);
        result = 31 * result + (isCorrectlyArranged ? 1 : 0);
        return result;
    }
}
