package models;

public class Car extends Item {

    private boolean isWorking = true;

    public Car(String name, Human owner) {
        super(owner, name);
    }

    public boolean isWorking() {
        return isWorking;
    }

    @Override
    public Event changeState(String newName) {
        this.name = newName;
        return new Event();
    }

    public void breakCar() {
        isWorking = false;
    }

    @Override
    public String toString() {
        return "Car{"
                + "name='" + name + '\''
                + ", owner=" + (owner != null ? owner.getName() : "null")
                + ", isWorking=" + isWorking
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
        result = 31 * result + (isWorking ? 1 : 0);
        return result;
    }

    @Override
    public Inventory getInventory() {
        throw new UnsupportedOperationException("Еще не поддерживается.");
    }
}
