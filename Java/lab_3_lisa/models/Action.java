package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Универсальный класс для действий
 * Расширяемый: любое действие с любыми объектами
 */
public class Action {
    private final String name;
    private final String description;
    private final Human performer;
    private final List<Object> targets;
    private boolean isCompleted;
    private final long timestamp;

    public Action(String name, String description, Human performer) {
        this.name = name;
        this.description = description;
        this.performer = performer;
        this.targets = new ArrayList<>();
        this.isCompleted = false;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Добавить цель действия (цепочка методов)
     */
    public Action withTarget(Object target) {
        this.targets.add(target);
        return this;
    }

    /**
     * Добавить несколько целей
     */
    public Action withTargets(List<Object> targets) {
        this.targets.addAll(targets);
        return this;
    }

    /**
     * Выполнить действие
     */
    public Action execute() {
        this.isCompleted = true;
        System.out.println(this.getExecutionLog());
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Human getPerformer() {
        return performer;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public List<Object> getTargets() {
        return new ArrayList<>(targets);
    }

    /**
     * Формирует лог выполнения действия
     */
    private String getExecutionLog() {
        StringBuilder sb = new StringBuilder();
        sb.append("✓ ").append(performer.getName()).append(" ").append(description);

        if (!targets.isEmpty()) {
            sb.append(" [");
            for (int i = 0; i < targets.size(); i++) {
                if (i > 0) sb.append(", ");
                Object target = targets.get(i);
                // Упрощенное представление объекта
                sb.append(target.toString());
            }
            sb.append("]");
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return timestamp == action.timestamp &&
               Objects.equals(performer, action.performer) &&
               Objects.equals(name, action.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, performer, name);
    }

    @Override
    public String toString() {
        return String.format("Action[%s] by %s", name, performer.getName());
    }
}
