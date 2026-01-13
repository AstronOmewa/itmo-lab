package exceptions;

/**
 * Checked exception - недостаточно ресурсов (материалов)
 */
public class InsufficientResourcesException extends Exception {

    private final String missingResource;

    public InsufficientResourcesException(String message, String missingResource) {
        super(message);
        this.missingResource = missingResource;
    }

    public InsufficientResourcesException(String missingResource) {
        super("Недостаточно ресурсов: отсутствует '" + missingResource + "'");
        this.missingResource = missingResource;
    }

    public String getMissingResource() {
        return missingResource;
    }

    @Override
    public String getMessage() {
        return "Ошибка: " + super.getMessage();
    }
}
