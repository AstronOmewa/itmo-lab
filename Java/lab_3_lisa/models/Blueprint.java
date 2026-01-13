package models;

import enums.Location;
import interfaces.Transportable;
import exceptions.DeliveryException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Класс Чертеж
 */
public class Blueprint extends PhysicalObject implements Transportable {

    private String factoryName;
    private boolean isDelivered;
    private boolean isApproved;

    public Blueprint(String name, String description) {
        super(name, "чертеж для изготовления узлов ракеты",
              new MaterialInfo("бумага", 0.05, false, "чертежная бумага"));
        this.isDelivered = false;
        this.isApproved = false;
    }

    public void approve() {
        this.isApproved = true;
        System.out.println("Чертеж '" + getName() + "' утвержден");
    }

    @Override
    public void transport(Blueprint blueprint) throws DeliveryException {
        if (!isApproved) {
            throw new DeliveryException("Чертеж не утвержден! Нельзя доставить неутвержденный чертеж.",
                                        blueprint.getName());
        }
        this.isDelivered = true;
        System.out.println("Чертеж '" + getName() + "' доставлен на завод '" + factoryName + "'");
    }

    @Override
    public boolean isAvailableForTransport() {
        return isApproved;
    }

    public void setFactory(String factoryName) {
        this.factoryName = factoryName;
        System.out.println("Чертеж '" + getName() + "' направлен на завод '" + factoryName + "'");
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public boolean isApproved() {
        return isApproved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blueprint blueprint = (Blueprint) o;
        return Objects.equals(getName(), blueprint.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Чертеж '" + getName() + "' (" +
               (isApproved ? "утвержден" : "не утвержден") + ", " +
               (isDelivered ? "доставлен" : "не доставлен") + ")";
    }
}
