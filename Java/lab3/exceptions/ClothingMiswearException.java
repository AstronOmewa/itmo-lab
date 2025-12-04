package exceptions;

/**
 * Проверяемое исключение, возникающее при неправильном надевании одежды
 */
public class ClothingMiswearException extends Exception {
    private String clothingType;
    private String reason;

    public ClothingMiswearException(String clothingType, String reason) {
        super(String.format("Неправильно надета одежда: %s - %s", clothingType, reason));
        this.clothingType = clothingType;
        this.reason = reason;
    }

    public ClothingMiswearException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public String getClothingType() {
        return clothingType;
    }

    public String getReason() {
        return reason;
    }
}
