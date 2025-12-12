package models;

import exceptions.ClothingMiswearException;
import interfaces.Storable;
import java.util.ArrayList;
import java.util.Arrays;

public class Jacket extends Cloth implements Storable {

    private final Inventory inventory;

    public Jacket(Human whoWears, String name) {
        super(whoWears, name);
        this.inventory = new Inventory();
    }

    @Override
    public Event wear(Human whoWears) throws ClothingMiswearException {
        this.whoWears = whoWears;
        return new Event("надел", new ArrayList<Entity>(Arrays.asList(this.whoWears)), new Time(-1, 0, 0), this);
    }

    @Override
    public Event wear() throws ClothingMiswearException {
        return wear(this.whoWears);
    }

    @Override
    public void store(Item item) throws Exception {
        inventory.store(item);
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
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "Jacket{"
                + "name='" + name + '\''
                + ", owner=" + (owner != null ? owner.getName() : "null")
                + ", whoWears=" + (whoWears != null ? whoWears.getName() : "null")
                + ", inventory=" + (inventory != null ? "present" : "null")
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
        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        return result;
    }
}
