package models;

import exceptions.InventoryFullException;

public abstract class Human extends Entity {
    protected Inventory inventory;

    public Human(String name){
        super(name);
        this.inventory = new Inventory();
    }

    public Human(String name, Inventory inv){
        super(name);
        this.inventory = inv;
    }

    public abstract void showInventory();
    public Event addToInventory(Item it) throws InventoryFullException {
        Event e = new Event();
        if (inventory != null) {
            inventory.addItem(it);
        }
        // System.out.println("Добавлена вещь: "+it.toString());
        return e;
    }

    public Event rmFromInventory(Item it){
        Event e = new Event();
        if (inventory != null) {
            inventory.rmItem(it);
        }
        return e;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", inventory=" + (inventory != null ? "present" : "null") +
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
        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        return result;
    }
}