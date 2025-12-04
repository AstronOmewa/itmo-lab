package interfaces;
import exceptions.ClothingMiswearException;
import models.Event;

public interface Wearable {
    public Event wear() throws ClothingMiswearException;
}