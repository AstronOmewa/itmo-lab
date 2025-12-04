package interfaces;

import models.Item;

public interface Storable {
    public void store(Item item) throws Exception;
}
