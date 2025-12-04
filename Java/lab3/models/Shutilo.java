package models;

import enums.EventType;
import interfaces.Breakable;
import interfaces.Distractable;

public class Shutilo extends Human implements Distractable {
    
    public Shutilo(){
        super("Шутило");
    }


    public EventSequence veryHurry() {
        Event hurry = new Event(EventType.VERYHURRY).addSubject(this);
        Event fail = new Event(EventType.WENTWRONG);
        EventSequence es = new EventSequence().addEvent(fail).addCondition(hurry);
        return es;
    }

    public Event jumpOverRoom() {
        Event jumpOverRoom = new Event(EventType.JUMPOVERROOM).addSubject(this);

        return jumpOverRoom;
    }

    public Event goOut(Cloth cloth) {
        Event goOut = new Event(EventType.GOOUT).addSubject(this).addObject(cloth);

        return goOut;
    }

    public Event collideWith(Breakable brItem) {
        Event e = brItem.breakItem();
        return e;
    }

    @Override
    public Event distract() {
        return new Event();
    }

    @Override
    public void showInventory() {
        System.out.printf("Инвентарь <Шутило>: %s\n", inventory.toString());
    }

    @Override
    public String toString() {
        return "Shutilo{" +
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
