package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Универсальный класс для событий
 * События происходят в мире и могут влиять на состояние объектов
 */
public class Event {
    private final String name;
    private final String description;
    private final List<Object> participants;
    private final LocalDateTime timestamp;
    private final EventType type;

    public enum EventType {
        DISCOVERY,     // Открытие/нахождение
        INTERACTION,   // Взаимодействие
        STATE_CHANGE,  // Изменение состояния
        CREATION,      // Создание объекта
        DESTRUCTION    // Уничтожение объекта
    }

    public Event(String name, String description, EventType type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.participants = new ArrayList<>();
        this.timestamp = LocalDateTime.now();
    }

    public Event withParticipant(Object participant) {
        this.participants.add(participant);
        return this;
    }

    public Event withParticipants(List<Object> participants) {
        this.participants.addAll(participants);
        return this;
    }

    public void occur() {
        System.out.println(getEventLog());
    }

    private String getEventLog() {
        StringBuilder sb = new StringBuilder();
        sb.append("СОБЫТИЕ: ").append(name);
        if (!description.isEmpty()) {
            sb.append(" - ").append(description);
        }
        sb.append("\n   Участники: ");
        for (int i = 0; i < participants.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(participants.get(i).toString());
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public EventType getType() {
        return type;
    }

    public List<Object> getParticipants() {
        return new ArrayList<>(participants);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(name, event.name) &&
               Objects.equals(timestamp, event.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, timestamp);
    }

    @Override
    public String toString() {
        return String.format("Event[%s: %s]", type, name);
    }
}
