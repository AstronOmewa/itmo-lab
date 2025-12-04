package models;

import exceptions.ClothingMiswearException;
import interfaces.WearableIsideOut;

public class Tie extends Cloth implements WearableIsideOut {
    private boolean isInsideOut = false;
    
    public Tie(Human whoWears, String name) {
        super(whoWears, name);
    }
    
    @Override
    public Event wear(Human whoWears) throws ClothingMiswearException {
        this.whoWears = whoWears;
        if(Math.random() < 0.5){
            this.wearInsideOut();
        }
        if (this.isInsideOut) {
            throw new ClothingMiswearException("Галстук", "надет наизнанку");
        }
        return new Event();
    }

    @Override
    public Event wearInsideOut() throws ClothingMiswearException{
        this.isInsideOut = true;
        this.wear(this.whoWears);
        return new Event();
    }

    @Override
    public Event maintainAppearance() {
        return new Event();
    }

    @Override
    public Event wear() throws ClothingMiswearException {
        return wear(this.whoWears);
    }

    @Override
    public Event changeState(String newState) {
        return new Event();
    }

    @Override
    public String toString() {
        return "Tie{" +
                "name='" + name + '\'' +
                ", owner=" + (owner != null ? owner.getName() : "null") +
                ", whoWears=" + (whoWears != null ? whoWears.getName() : "null") +
                ", isInsideOut=" + isInsideOut +
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
        result = 31 * result + (isInsideOut ? 1 : 0);
        return result;
    }
}
