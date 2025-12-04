package exceptions;

/**
 * Проверяемое исключение, возникающее при потере предмета
 */
public class ItemLostException extends Exception {
    private String itemName;
    private String previousOwner;

    public ItemLostException(String itemName, String previousOwner) {
        super(String.format("Предмет '%s' потерян! Предыдущий владелец: %s", itemName, previousOwner));
        this.itemName = itemName;
        this.previousOwner = previousOwner;
    }

    public ItemLostException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public String getItemName() {
        return itemName;
    }

    public String getPreviousOwner() {
        return previousOwner;
    }
}
