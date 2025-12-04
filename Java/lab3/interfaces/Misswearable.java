package interfaces;

import exceptions.ClothingMiswearException;
import models.Event;

public interface Misswearable extends Wearable{
    public Event misswear() throws ClothingMiswearException;
}