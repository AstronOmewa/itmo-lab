package models;

public class Korzhik extends Human {
    
    
    public Korzhik() {
        super("Коржик");
    }

    @Override
    public void showInventory(){
        System.out.printf("Инвентарь <Коржик>: %s\n", inventory.toString());
    }

    @Override
    public String toString() {
        return "Korzhik{" +
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
