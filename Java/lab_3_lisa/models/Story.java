package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для управления сценариями (Story/Director pattern)
 * Вся plot-specific логика находится здесь, а не в классах персонажей!
 */
public class Story {

    private final String title;
    private final List<Human> characters;
    private final List<Event> events;
    private int currentChapter;

    public Story(String title) {
        this.title = title;
        this.characters = new ArrayList<>();
        this.events = new ArrayList<>();
        this.currentChapter = 0;
    }

    public Story addCharacter(Human character) {
        characters.add(character);
        return this;
    }

    public Story addEvent(Event event) {
        events.add(event);
        return this;
    }

    public Chapter chapter(int number, String name) {
        this.currentChapter = number;
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ГЛАВА " + number + ": " + name);
        System.out.println("=".repeat(50));
        return new Chapter(this, number, name);
    }

    public String getTitle() {
        return title;
    }

    public List<Human> getCharacters() {
        return new ArrayList<>(characters);
    }

    /**
     * Вложенный класс для управления главой истории
     */
    public static class Chapter {
        private final Story story;
        private final int number;
        private final String name;
        private final List<Action> actions;

        public Chapter(Story story, int number, String name) {
            this.story = story;
            this.number = number;
            this.name = name;
            this.actions = new ArrayList<>();
        }

        public Chapter action(Action action) {
            action.execute();
            actions.add(action);
            return this;
        }

        public Chapter event(Event event) {
            event.occur();
            story.addEvent(event);
            return this;
        }

        public Chapter thought(Thought thought) {
            System.out.println(thought);
            return this;
        }

        public Chapter pause(String message) {
            System.out.println("\n" + message);
            return this;
        }

        public Chapter end() {
            System.out.println("→ Конец главы " + number);
            return this;
        }
    }
}
