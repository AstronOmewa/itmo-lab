package models;

import exceptions.ClothingMiswearException;
import interfaces.Wearable;
import java.util.ArrayList;

public abstract class Cloth extends Item implements Wearable {

    protected Human whoWears;

    public Cloth(Human whoWears, String name) {
        super(whoWears, name);
    }

    public abstract Event wear(Human whoWears) throws ClothingMiswearException;

    @Override
    public abstract Event wear() throws ClothingMiswearException;

    public abstract Event maintainAppearance();

    public Event checkWhoWears(){
        ArrayList<Entity> actor = new ArrayList<>();
        actor.add(this.whoWears);
        Event observe = new Event(String.format(" была взята вещь <владелец %s>", this.owner.getName()), actor, new Time(null), this);
        return observe;
    }

    @Override
    public abstract Event changeState(String newState);

    public Human getWhoWears() {
        return whoWears;
    }
    @Override
    public String toString() {
        return "Cloth{"
                + "name='" + name + '\''
                + ", owner=" + (owner != null ? owner.getName() : "null")
                + ", whoWears=" + (whoWears != null ? whoWears.getName() : "null")
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
    public Inventory getInventory() {
        Inventory inv = new Inventory();
        return inv;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (whoWears != null ? whoWears.hashCode() : 0);
        return result;
    }
}
