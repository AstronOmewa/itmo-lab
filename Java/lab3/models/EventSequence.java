package models;

import enums.Time;
import java.util.ArrayList;

public class EventSequence {

    private ArrayList<Event> sequence;
    private Event condition;
    private Time when;
    private Human object;

    public EventSequence(ArrayList<Event> sequence, Event condition, Time when, Human object) {
        this.sequence = sequence;
        this.condition = condition;
        this.when = when;
        this.object = object;
    }

    public EventSequence(ArrayList<Event> sequence, Event condition, Time when) {
        this.sequence = sequence;
        this.condition = condition;
        this.when = when;
    }

    public EventSequence(ArrayList<Event> sequence, Event condition) {
        this.sequence = sequence;
        this.condition = condition;
    }

    public EventSequence(ArrayList<Event> sequence) {
        this.sequence = sequence;
    }

    public EventSequence() {
    }

    public EventSequence addEvent(Event event) {
        if (this.sequence == null) {
            this.sequence = new ArrayList<>();
        }
        sequence.add(event);
        return new EventSequence(this.sequence, this.condition, this.when, this.object);
    }

    public EventSequence addCondition(Event condition) {
        this.condition = condition;
        return new EventSequence(this.sequence, this.condition, this.when, this.object);
    }

    public EventSequence addTime(Time when) {
        this.when = when;
        return new EventSequence(this.sequence, this.condition, this.when, this.object);
    }

    public EventSequence addObject(Human object) {
        this.object = object;
        return new EventSequence(this.sequence, this.condition, this.when, this.object);
    }

    public void simulate() {
        System.out.println("=== Выполнение последовательности событий ===");
        for (int i = 0; i < sequence.size(); i++) {
            System.out.print("[" + (i + 1) + "] ");
            Event e = sequence.get(i);
            e.happen();
        }
        if (condition instanceof Event) {
            System.out.print("[Это произошло потому, что] ");
            condition.happen();
        }
        System.out.println();
    }

    @Override
    public String toString() {
        if (sequence == null || sequence.isEmpty()) {
            return "(нет событий)";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("EventSequence{");
            sb.append("sequence=");
            for (Event e : sequence) {
                sb.append(e.toString()).append("; ");
            }

            if (condition != null) {
                sb.append(", condition=").append(condition);
            }
            if (when != null) {
                sb.append(", when=").append(when);
            }
            if (object != null) {
                sb.append(", object=").append(object);
            }
            sb.append('}');
            return sb.toString();
        }
    }
}
