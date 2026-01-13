package models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Универсальный класс для мыслей и идей
 * Расширяемый: может быть любая мысль, идея, предложение
 */
public class Thought {
    private final String content;
    private final Human author;
    private final LocalDateTime createdAt;
    private final String category;
    private boolean isShared;

    public Thought(String content, Human author, String category) {
        this.content = content;
        this.author = author;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        this.isShared = false;
    }

    public String getContent() {
        return content;
    }

    public Human getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isShared() {
        return isShared;
    }

    public void markAsShared() {
        this.isShared = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thought thought = (Thought) o;
        return Objects.equals(content, thought.content) &&
               Objects.equals(author, thought.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, author);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: '%s'", category, author.getName(), content);
    }
}
