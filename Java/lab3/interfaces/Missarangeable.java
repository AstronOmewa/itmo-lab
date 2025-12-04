package interfaces;

import exceptions.ClothingMiswearException;
import models.Event;

public interface Missarangeable {
    public Event missarrange() throws ClothingMiswearException;
}
