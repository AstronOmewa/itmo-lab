package models;

import java.util.Objects;

/**
 * Абстрактный класс Физический Объект
 */
public abstract class PhysicalObject {
    private String name;
    private String purpose;
    private MaterialInfo material;

    public PhysicalObject(String name, String purpose, MaterialInfo material) {
        this.name = name;
        this.purpose = purpose;
        this.material = material;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }

    public MaterialInfo getMaterial() {
        return material;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
        System.out.println("Название объекта изменено на: " + name);
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
        System.out.println("Предназначение объекта '" + name + "' изменено на: " + purpose);
    }

    public void setMaterial(MaterialInfo material) {
        this.material = material;
        System.out.println("Материал объекта '" + name + "' изменен на: " + material);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalObject that = (PhysicalObject) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s (предназначение: %s, материал: %s)",
            name, purpose, material);
    }
}
