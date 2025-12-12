package models;

import enums.Places;
import exceptions.InventoryFullException;
import java.util.ArrayList;

public abstract class Human extends Entity {

    protected Inventory inventory;
    protected float distractionLevel = 1f;
    protected static ArrayList<Human> allHumans = new ArrayList<>(); // Статический список всех людей

    public Human(String name) {
        super(name);
        this.inventory = new Inventory();
        allHumans.add(this);
    }

    public Human(String name, Inventory inv) {
        super(name);
        this.inventory = inv;
        allHumans.add(this);
    }

    public abstract Event showInventory();

    public EventSequence checkWardrobe() {
        EventSequence reaction = new EventSequence();

        // Сначала проверяем, есть ли у нас чужие вещи
        boolean hasWrongItems = false;
        Item myLostItem = null;

        for (Inventory.InventoryCell cell : this.inventory.getInventory()) {
            Item item = cell.getItem();

            // Если предмет не наш
            if (!item.getOwner().getName().equals(this.name)) {
                hasWrongItems = true;
                String itemType = (item instanceof Cloth) ? ((Cloth)item).getName() : item.getName();
                Event findWrongItem = new Event("обнаружил, что у него осталась " + itemType + " (" + item.getOwner().getName() + ")")
                    .addSubject(this)
                    .addObject(item);
                reaction.addEvent(findWrongItem);
            }
            // Ищем свою пропавшую вещь (если она каким-то образом вернулась)
            else if (item.getOwner().getName().equals(this.name)) {
                myLostItem = item;
            }
        }

        // Если есть чужие вещи, значит, наша пропала
        if (hasWrongItems) {
            Event realizeOwnLoss = new Event("понял, что его собственная вещь пропала")
                .addSubject(this);
            reaction.addEvent(realizeOwnLoss);

            // Вспоминаем о важных вещах, которые были в пропавшей одежде
            rememberImportantItemsLost(reaction);
        }

        return reaction;
    }

    private void rememberImportantItemsLost(EventSequence reaction) {
        // Универсальный метод поиска важных вещей, принадлежащих этому персонажу
        // В идеальной системе здесь была бы проверка всех предметов в мире

        // Для текущей реализации - просто проверяем известные важные предметы
        checkForSpecificImportantItems(reaction);
    }

    private void checkForSpecificImportantItems(EventSequence reaction) {
        // Ищем все важные предметы у всех персонажей в мире
        findAllImportantItems(reaction);
    }

    private void findAllImportantItems(EventSequence reaction) {
        // Ищем все важные предметы у всех персонажей
        for (Human human : allHumans) {
            // Проверяем только предметы с инвентарем (одежду), а не основной инвентарь персонажа
            findImportantItemsInClothesInventory(human.getInventory(), reaction, "");
        }
    }

    private void findImportantItemsInClothesInventory(Inventory inventory, EventSequence reaction, String containerInfo) {
        for (Inventory.InventoryCell cell : inventory.getInventory()) {
            Item item = cell.getItem();
            Places place = cell.place();
            String itemInfo = containerInfo.isEmpty() ? "в " + place.getPlace() : containerInfo + " -> в " + place.getPlace() + " " + item.getName();

            // Если это одежда, рекурсивно проверяем её инвентарь
            if (item.getInventory() != null) {
                try {
                    if (!item.getInventory().getInventory().isEmpty()) {
                        // Передаем владельца одежды вместе с поиском
                        String ownerOfCloth = getCurrentOwnerName(inventory, this);
                        findImportantItemsInInventory(item.getInventory(), reaction, itemInfo, ownerOfCloth);
                    }
                } catch (UnsupportedOperationException e) {
                    // У предмета нет инвентаря
                }
            }
        }
    }

    private void findImportantItemsInInventory(Inventory inventory, EventSequence reaction, String containerInfo) {
        findImportantItemsInInventory(inventory, reaction, containerInfo, null);
    }

    private void findImportantItemsInInventory(Inventory inventory, EventSequence reaction, String containerInfo, String clothesOwner) {
        for (Inventory.InventoryCell cell : inventory.getInventory()) {
            Item item = cell.getItem();
            Places place = cell.place();

            // Если это важная вещь
            if (item.isImportant()) {
                String itemOwner = item.getOwner().getName();
                String myName = this.name;

                // Если это наша важная вещь
                if (itemOwner.equals(myName)) {
                    // Формируем правильный путь: только место, без контейнера
                    String location;
                    switch (place) {
                        case LEFTPOCKET:
                            location = "в левом кармане";
                            break;
                        case RIGHTPOCKET:
                            location = "в правом кармане";
                            break;
                        default:
                            location = "в " + place.getPlace();
                            break;
                    }

                    // Используем владельца одежды, если он известен, иначе ищем стандартным способом
                    String currentOwner = clothesOwner != null ? clothesOwner : getCurrentOwnerName(inventory, this);

                    // Добавляем информацию о типе контейнера, если он есть
                    String containerType = "";
                    if (!containerInfo.isEmpty() && containerInfo.contains("->")) {
                        String[] parts = containerInfo.split(" -> ");
                        for (String part : parts) {
                            if (part.contains("куртка")) {
                                containerType = " в " + part.trim();
                                break;
                            }
                        }
                    }

                    Event foundImportantItem = new Event("с ужасом обнаружил, что его " + item.getName() +
                        " находится " + location + " у " + currentOwner + containerType)
                        .addSubject(this);
                    reaction.addEvent(foundImportantItem);
                }
            }

            // Рекурсивно проверяем содержимое предмета
            try {
                if (item.getInventory() != null && !item.getInventory().getInventory().isEmpty()) {
                    // Передаем информацию о предмете в качестве контейнера
                    String newContainerInfo = containerInfo.isEmpty() ? "в " + item.getName() : containerInfo + " -> в " + item.getName();
                    findImportantItemsInInventory(item.getInventory(), reaction, newContainerInfo, clothesOwner);
                }
            } catch (UnsupportedOperationException e) {
                // У предмета нет инвентаря (например, у водительского удостоверения)
            }
        }
    }

    private String getCurrentOwnerName(Inventory inventory, Human searchContext) {
        // Сначала проверяем, не это ли инвентарь самого ищущего
        if (searchContext.getInventory() == inventory) {
            return searchContext.getName();
        }

        // Ищем владельца инвентаря
        for (Human human : allHumans) {
            if (human.getInventory() == inventory) {
                return human.getName();
            }
        }
        return "неизвестно";
    }

    
    
    public EventSequence wakeUp(Time t, ArrayList<Event> esToDoAfterSleep){
        EventSequence es = new EventSequence();
        es.addEvent(new Event("проснулся в ").addTime(t));
        for(Event e: esToDoAfterSleep){
            es.addEvent(e);
        }
        return es;
    }
    public EventSequence wakeUp(Time t){
        EventSequence es = new EventSequence();
        es.addEvent(new Event("проснулся в ").addTime(t));
        es.addEvent(new Event("и не делал ничего до вечера"));
        return es;
    }

    public Event addToInventory(Item it) throws InventoryFullException {
        Event e = new Event();
        if (inventory != null) {
            inventory.addItem(it);
        }
        // System.out.println("Добавлена вещь: "+it.toString());
        return e;
    }

    public Event addToInventory(Item it, Places place) throws InventoryFullException {
        Event e = new Event();
        if (inventory != null) {
            inventory.addItem(it, place);
        }
        // System.out.println("Добавлена вещь: "+it.toString());
        return e;
    }

    public Event rmFromInventory(Item it) {
        Event e = new Event();
        if (inventory != null) {
            inventory.rmItem(it);
        }
        return e;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "Human{"
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
