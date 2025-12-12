package models;

import enums.GlobalObjectProperties;
import interfaces.Breakable;
import interfaces.Distractable;
import interfaces.Wearable;
import java.util.ArrayList;

public class Shutilo extends Human implements Distractable {

    public Shutilo() {
        super("Шутило");
    }

    public EventSequence veryHurry() {
        Event hurry = new Event("очень спешил").addSubject(this);
        Event consequence;
        EventSequence es = new EventSequence();

        for (Inventory.InventoryCell icell : this.inventory.getInventory()) {
                Item item = icell.getItem();
                if (item instanceof Wearable) {
                    try {
                        Event wear = ((Wearable) item).wear(this);
                        es.addEvent(wear);
                    } catch (Exception e) {
                        this.distractionLevel += 1;
                        ArrayList<Entity> actors = new ArrayList<Entity>();
                        actors.add(this);
                        es.addEvent(new Event(e.getMessage(), actors));
                    }
                }
            }

        if(this.distractionLevel >= 3){
            consequence = new Event("все пошло не так, как надо").addSubject(this);
        }else{
            consequence = new Event("все шло, как было задумано").addSubject(this);
        }

        es.addEvent(consequence);
        es.addCondition(hurry);
        return es;
    }

    public Event jumpOverRoom() {
        double chance = 0.1 + Math.random();
        if (chance < 0.7) {
            this.distractionLevel += 1;
        }
        Event jumpOverRoom = new Event("пошел по комнате").addSubject(this);
        for (Inventory.InventoryCell icell : this.inventory.getInventory()) {
                Item item = icell.getItem();
                if (item instanceof Pants && !((Pants)item).rightLegInPants) {
                    jumpOverRoom = new Event("скакал по всей комнате, так как неправильно одел штаны").addSubject(this);
                }
            }
        return jumpOverRoom;
    }

    public EventSequence goOut(Cloth cloth) {
        EventSequence goOut = new EventSequence();
        goOut.addEvent(new Event("ушел, взяв следующие вещи:").addSubject(this));
        goOut.addEvent(this.showInventory());
        return goOut;
    }

    public EventSequence collideWith(Breakable brItem) {
        EventSequence es = new EventSequence();
        if (distractionLevel >= 3) {
            es.addEvent(brItem.breakItem());
        } else {
            es.addEvent(new Event("был ловок и ничего не сломал"));
        }
        es.addCondition(this.jumpOverRoom());
        return es;
    }

    @Override
    public Event distract() {
        ArrayList<Entity> subjectList = new ArrayList<Entity>();
        subjectList.add(this);
        Event e = new Event("был достаточно " + GlobalObjectProperties.CONCENTRATED.getProp() + " (его distractionLevel = "+this.distractionLevel +"[<3])", subjectList);
        if (distractionLevel >=3) {
            e = new Event("был " + GlobalObjectProperties.DISTRACTED.getProp()+ " (его distractionLevel = "+this.distractionLevel +"[>=3])", subjectList);
        }
        return e;
    }

    @Override
    public Event showInventory() {
        String eventString = "";

         for (Inventory.InventoryCell icell : this.inventory.getInventory()) {
                Item item = icell.getItem();
                if (item instanceof Cloth) {
                    eventString += item.getName() + " (владелец " + ((Cloth) item).getOwner().getName() + ")\n";
                }
            }


        return new Event(String.format("%s", eventString));
    }

    @Override
    public String toString() {
        return "Shutilo{"
                + "name='" + name + '\''
                + ", inventory=" + (inventory != null ? "present" : "null")
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
        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        return result;
    }
}
