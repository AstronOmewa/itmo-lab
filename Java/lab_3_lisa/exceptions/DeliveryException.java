package exceptions;

/**
 * Unchecked exception - ошибка доставки
 */
public class DeliveryException extends RuntimeException {

    private final String itemName;

    public DeliveryException(String message, String itemName) {
        super(message);
        this.itemName = itemName;
    }

    public DeliveryException(String itemName) {
        super("Не удалось доставить: " + itemName);
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    @Override
    public String getMessage() {
        return "Ошибка доставки: " + super.getMessage();
    }
}
