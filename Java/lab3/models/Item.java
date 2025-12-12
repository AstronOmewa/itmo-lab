package models;

public abstract class Item extends Entity {

    protected Human owner;
    protected boolean important = false;

    public Item(Human owner, String name) {
        super(name);
        this.owner = owner;
    }

    public Item(Human owner, String name, boolean important) {
        super(name);
        this.owner = owner;
        this.important = important;
    }

    public abstract Event changeState(String newState);

    public Human getOwner() {
        return owner;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public abstract Inventory getInventory();

    public boolean isImportant() {
        return important;
    }

    @Override
    public String toString() {
        return "Item{"
                + "name='" + name + '\''
                + ", owner=" + (owner != null ? owner.getName() : "null")
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
        return result;
    }
}
