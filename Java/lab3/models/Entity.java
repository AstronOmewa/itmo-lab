package models;

public abstract class Entity {

    protected String name;
    protected Inventory inventory;

    public Entity(String name) {
        this.name = name;
        this.inventory = null;
    }

    abstract public String getName();

    public Inventory getInventory() {
        return inventory;
    }
}
