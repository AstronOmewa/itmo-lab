package models;

import enums.Gender;
import enums.HairColor;
import enums.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Абстрактный класс Человек (Коротышка)
 * Универсальная база для любых персонажей
 */
public abstract class Human {
    private final String name;
    private final Gender gender;
    private final int age;
    private final double height;
    private final HairColor hairColor;
    private Location currentLocation;
    private final List<Thought> thoughts;
    private final List<Action> actions;
    private static ArrayList<Human> allHumans = new ArrayList<>();

    public Human(String name, Gender gender, int age, double height, HairColor hairColor) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.hairColor = hairColor;
        this.currentLocation = Location.FLOWER_CITY;
        this.thoughts = new ArrayList<>();
        this.actions = new ArrayList<>();
        allHumans.add(this);
    }

    // ========== Универсальные методы ==========

    /**
     * Подумать о чем-то (создает Thought)
     */
    public Thought think(String content) {
        return think(content, "размышление");
    }

    /**
     * Подумать о чем-то в конкретной категории
     */
    public Thought think(String content, String category) {
        Thought thought = new Thought(content, this, category);
        thoughts.add(thought);
        return thought;
    }

    /**
     * Предложить мысль другим
     */
    public void propose(Thought thought) {
        thought.markAsShared();
        System.out.println(name + " предложил: '" + thought.getContent() + "'");
    }

    /**
     * Создать мысль и сразу предложить её
     */
    public Thought proposeIdea(String content, String category) {
        Thought thought = think(content, category);
        propose(thought);
        return thought;
    }

    /**
     * Выполнить действие
     */
    public Action perform(String actionName, String description) {
        Action action = new Action(actionName, description, this);
        actions.add(action);
        return action;
    }

    /**
     * Изучить что-то
     */
    public void study(String subject) {
        System.out.println(name + " изучает: " + subject);
    }

    /**
     * Переместиться в другую локацию
     */
    public void moveTo(Location destination) {
        System.out.println(name + " переместился из " + currentLocation.getDescription() +
                         " в " + destination.getDescription());
        this.currentLocation = destination;
    }

    // ========== Getters ==========

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public List<Thought> getThoughts() {
        return new ArrayList<>(thoughts);
    }

    public List<Action> getActions() {
        return new ArrayList<>(actions);
    }

    public static ArrayList<Human> getAllHumans() {
        return new ArrayList<>(allHumans);
    }

    // ========== Setters ==========

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    // ========== Object methods ==========

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return Objects.equals(name, human.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s (%d лет, %.1f см, %s, %s волосы)",
            name, age, height, gender.getDescription(), hairColor.getDescription());
    }
}
