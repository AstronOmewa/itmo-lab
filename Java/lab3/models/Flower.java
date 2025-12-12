package models;

import enums.FlowerType;

public class Flower extends Item {

    private FlowerType flowerType;

    public Flower(FlowerType flowerType) {
        super(null, flowerType.toString());
        this.flowerType = flowerType;
    }

    public FlowerType getFlowerType() {
        return flowerType;
    }

    public String getName() {
        return flowerType.getName();
    }

    @Override
    public Event changeState(String newState) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Не поддерживается");
    }

    @Override
    public Inventory getInventory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("У цветка нет инвентаря");
    }

}
