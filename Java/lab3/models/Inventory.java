package models;

import enums.Places;
import exceptions.InventoryFullException;
import interfaces.Storable;
import java.util.ArrayList;

public class Inventory implements Storable {

    public static record InventoryCell(Item item, Places place) {

        public InventoryCell(Item item, Places place) {
            this.item = item;
            this.place = place;
        }

        public Item getItem() {
            return this.item;
        }
    }

    private final ArrayList<InventoryCell> inventory;
    private static final int MAX_CAPACITY = 5;
    private int storedCells = 0;

    public Inventory() {
        this.inventory = new ArrayList<>();
    }

    public ArrayList<InventoryCell> getInventory() {
        return inventory;
    }

    public String show() {
        String result = "";
        for (InventoryCell cell : inventory) {
            result += cell.item().getName() + " в " + cell.place().getPlace() + "; ";
        }
        return result;
    }

    public void addItem(Item item) throws InventoryFullException {
        if (inventory.size() >= MAX_CAPACITY) {
            throw new InventoryFullException("Инвентарь переполнен", MAX_CAPACITY);
        }
        storedCells += 1;
        this.inventory.add(new InventoryCell(item, Places.DEFAULT));
    }

    public void addItem(Item item, Places place) throws InventoryFullException {
        if (inventory.size() >= MAX_CAPACITY) {
            throw new InventoryFullException("Инвентарь переполнен", MAX_CAPACITY);
        }
        storedCells += 1;
        this.inventory.add(new InventoryCell(item, place));
    }

    public void rmItem(Item it) {
        storedCells -= 1;
        inventory.removeIf(cell -> cell.item() == it);
    }

    public boolean isEmpty(){
        return (storedCells==0);
    }

    public void swapItems(Item it1, Item it2) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).item() == it1) {
                inventory.set(i, new InventoryCell(it2, inventory.get(i).place()));
                return;
            }
        }
    }

    @Override
    public void store(Item item) throws Exception {
        if (inventory.size() >= MAX_CAPACITY) {
            throw new InventoryFullException("Инвентарь переполнен", MAX_CAPACITY);
        }
        for (InventoryCell cell : inventory) {
            if (cell.item() == item) {
                throw new Exception("Предмет уже находится в инвентаре");
            }
        }
        this.inventory.add(new InventoryCell(item, Places.DEFAULT));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Inventory{");
        for (InventoryCell cell : inventory) {
            sb.append("\n  Item: ").append(cell.item().toString())
                    .append(", Place: ").append(cell.place().toString());
        }
        sb.append("\n}");
        return sb.toString();
    }

    @Override
    public int hashCode() {

        int result = inventory != null ? inventory.hashCode() : 0;
        result = 31 * result + MAX_CAPACITY;
        return result;
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
}
