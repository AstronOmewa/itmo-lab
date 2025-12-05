package models;

import enums.FlowerType;
import interfaces.Breakable;
import interfaces.Storable;

public class FlowerPot extends Item implements Storable, Breakable {
    private Inventory flowers;
    private boolean isBroken = false;
    public String name;
    public FlowerPot(){
        super(null, "горшок");
    }
    public FlowerPot(FlowerType flower){
        super(null, "горшок с "+flower.getName().toLowerCase());
        this.flowers = new Inventory();
        this.flowers.addItem(new Flower(flower));
    }
    @Override
    public Event breakItem() {
        isBroken = true;
        return new Event("разбился вдребезги").addObject(this);
    }

    public boolean isBroken() {
        return isBroken;
    }

    public Inventory getFlowers() {
        return flowers;
    }

    public void addFlower(FlowerType flower) {
        this.flowers.addItem(new Flower(flower));
    }
    @Override
    public Inventory getInventory() {
        return flowers;
    }
    @Override
    public void store(Item item) throws Exception {
        
    }

    @Override
    public String toString() {
        return "FlowerPot{" +
                "flower=" + flowers.toString() +
                ", isBroken=" + isBroken +
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
        int result = flowers != null ? flowers.hashCode() : 0;
        result = 31 * result + (isBroken ? 1 : 0);
        return result;
    }

    @Override
    public Event changeState(String newState) {
        throw new UnsupportedOperationException("Не поддерживается.");
    }
}
