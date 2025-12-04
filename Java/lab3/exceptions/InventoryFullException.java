package exceptions;

/**
 * Unchecked исключение (RuntimeException), возникающее когда инвентарь переполнен
 */
public class InventoryFullException extends RuntimeException {
    private int maxCapacity;

    public InventoryFullException(String message, int maxCapacity) {
        super(message);
        this.maxCapacity = maxCapacity;
    }

    public InventoryFullException(String message) {
        super(message);
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
