package interfaces;
import exceptions.ClothingMiswearException;
import models.Event;

public interface WearableIsideOut extends Wearable{
    public Event wearInsideOut() throws ClothingMiswearException;
}
