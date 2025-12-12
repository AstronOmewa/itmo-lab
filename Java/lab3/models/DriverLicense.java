package models;

import exceptions.ItemLostException;

public class DriverLicense extends Item {

    private Car vehicle;
    private boolean isLost = false;

    public DriverLicense(Car vehicle) {
        super(vehicle.getOwner(), "удостоверение на право вождения автомобиля", true);
        this.vehicle = vehicle;
    }

    public Car getVehicle() {
        return this.vehicle;
    }

    private void setVehicle(Car vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isLost() {
        return isLost;
    }

    public void markAsLost() throws ItemLostException {
        isLost = true;
        throw new ItemLostException("Водительское удостоверение", owner != null ? owner.getName() : "неизвестно");
    }

    @Override
    public Event changeState(String newCar) {
        Car c = new Car(newCar, this.vehicle.getOwner());
        setVehicle(c);
        return new Event();
    }

    @Override
    public String toString() {
        return "DriverLicense{"
                + "name='" + name + '\''
                + ", vehicle=" + (vehicle != null ? vehicle.getName() : "null")
                + ", owner=" + (owner != null ? owner.getName() : "null")
                + ", isLost=" + isLost
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
        result = 31 * result + (vehicle != null ? vehicle.hashCode() : 0);
        result = 31 * result + (isLost ? 1 : 0);
        return result;
    }

    @Override
    public Inventory getInventory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("У водительского удостоверения нет инвентаря.");
    }
}
