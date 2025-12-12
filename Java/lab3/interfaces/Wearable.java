package interfaces;
import exceptions.ClothingMiswearException;
import models.Event;
import models.Human;

public interface Wearable {
    public Event wear() throws ClothingMiswearException;
    public Event wear(Human h) throws ClothingMiswearException;
}