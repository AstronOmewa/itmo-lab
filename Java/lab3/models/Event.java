package models;

import enums.EventType;
import enums.Time;
import java.util.ArrayList;

public class Event {
    public ArrayList<Entity> subject;
    private EventType event;
    private Time when;
    private Entity object;

    public Event(EventType eventType, ArrayList<Entity> subject, Time when, Entity object){
        this.subject = subject;
        this.event = eventType;
        this.when = when;
        this.object = object;
    }

    public Event(EventType eventType, ArrayList<Entity> subject, Time when){
        this.subject = subject;
        this.event = eventType;
        this.when = when;
        this.object = null;
    }

    public Event(EventType eventType, ArrayList<Entity> subject){
        this.subject = subject;
        this.event = eventType;
        this.when = null;
        this.object = null;
    }

    public Event(EventType eventType){
        this.event = eventType;
        this.subject = new ArrayList<>();
        this.when = null;
        this.object = null;
    }

    public Event(){
        this.event = null;
        this.subject = new ArrayList<>();
        this.when = null;
        this.object = null;
    }

    public Event addSubject(Entity h){
        if (this.subject == null) {
            this.subject = new ArrayList<>();
        }
        this.subject.add(h);
        return new Event(this.event, this.subject, this.when, this.object);
    }

    public Event addTime(Time when){
        this.when = when;
        return new Event(this.event, this.subject, this.when, this.object);
    }

    public Event addObject(Entity object){
        this.object = object;
        return new Event(this.event, this.subject, this.when, this.object);
    }

    public EventType getEventType() {
        return event;
    }

    public Time getTime() {
        return when;
    }

   public void happen() {
    StringBuilder output = new StringBuilder();



    // Время
    if (when != null) {
        output.append("[").append(when.getTime()).append("] ");
    }

    // Субъекты (кто выполняет действие)
    if (subject != null && !subject.isEmpty()) {
        for (int i = 0; i < subject.size(); i++) {
            output.append(subject.get(i).getName());
            if (i < subject.size() - 1) output.append(" и ");
            
        }
        output.append(" ");
    }

    // Описание события
    if (event != null) {
        output.append(event.getEventType());
    }

    // Объект события (над кем или над чем происходит действие)
    if (object != null) {
        output.append(" ").append(object.getName());
        if(object.getInventory() != null){
            output.append(" (инвентарь/содержит: ");
            output.append(object.getInventory().show());
            output.append(")");
        }
        output.append(object.getInventory());
    }

    System.out.println(output.toString());
    }
    @Override
    public String toString() {
        return "Event{" +
                "event=" + (event != null ? event.getEventType() : "null") +
                ", subject=" + subject +
                ", when=" + (when != null ? when.getTime() : "null") +
                ", object=" + (object != null ? object.getName() : "null") +
                '}';
    }
}
