package models;

import java.util.ArrayList;

public class Event {

    public ArrayList<Entity> subject;
    private String event;
    private Time when;
    private Entity object;

    public Event(String eventType, ArrayList<Entity> subject, Time when, Entity object) {
        this.subject = subject;
        this.event = eventType;
        this.when = when;
        this.object = object;
    }

    public Event(String eventType, ArrayList<Entity> subject, Time when) {
        this.subject = subject;
        this.event = eventType;
        this.when = when;
        this.object = null;
    }

    public Event(String eventType, ArrayList<Entity> subject) {
        this.subject = subject;
        this.event = eventType;
        this.when = null;
        this.object = null;
    }

    public Event(String eventType) {
        this.event = eventType;
        this.subject = new ArrayList<>();
        this.when = null;
        this.object = null;
    }

    public Event() {
        this.event = null;
        this.subject = new ArrayList<>();
        this.when = null;
        this.object = null;
    }

    public Event addSubject(Entity h) {
        if (this.subject == null) {
            this.subject = new ArrayList<>();
        }
        this.subject.add(h);
        return new Event(this.event, this.subject, this.when, this.object);
    }

    public Event addSubject(ArrayList<Entity> h) {
        if (this.subject == null) {
            this.subject = new ArrayList<>();
        }
        this.subject.addAll(h);
        return new Event(this.event, this.subject, this.when, this.object);
    }

    public Event addTime(Time when) {
        this.when = when;
        return new Event(this.event, this.subject, this.when, this.object);
    }

    public Event addObject(Entity object) {
        this.object = object;
        return new Event(this.event, this.subject, this.when, this.object);
    }

    public String getString() {
        return event;
    }

    public Time getTime() {
        return when;
    }

    public String happen() {
        StringBuilder output = new StringBuilder();

        // Время
        if (when != null) {
            output.append("[").append(when.getString()).append("] ");
        }

        // Субъекты (кто выполняет действие)
        if (subject != null && !subject.isEmpty()) {
            for (int i = 0; i < subject.size(); i++) {
                Entity entity = subject.get(i);
                if (entity != null) {
                    output.append(entity.getName());
                    if (i < subject.size() - 1) {
                        output.append(" и ");
                    }
                }
            }
            output.append(" ");
        }

        // Описание события
        if (event != null) {
            output.append(event);
        }

        // Объект события (над кем или над чем происходит действие)
        if (object != null) {
            output.append(" ").append(object.getName());
            try {
                if (!object.getInventory().isEmpty()) {
                    output.append(" (инвентарь/содержит: ");
                    output.append(object.getInventory().show());
                    output.append(")");
                }
            } catch (UnsupportedOperationException e) {
                // У объекта нет инвентаря
            }
            // output.append(object.getInventory());
        }

        System.out.println(output.toString());
        return output.toString();
    }

    @Override
    public String toString() {
        return "Event{"
                + "event=" + (event != null ? event : "null")
                + ", subject=" + subject.toString()
                + ", when=" + (when != null ? when.getString() : "null")
                + ", object=" + (object != null ? object.getName() : "null")
                + '}';
    }
}
