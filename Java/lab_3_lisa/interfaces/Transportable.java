package interfaces;

import models.Blueprint;
import exceptions.DeliveryException;

/**
 * Интерфейс для объектов, которые могут транспортировать чертежи
 */
public interface Transportable {

    /**
     * Транспортировать чертеж
     * @param blueprint чертеж для транспортировки
     * @throws DeliveryException если транспортировка не удалась
     */
    void transport(Blueprint blueprint) throws DeliveryException;

    /**
     * Проверить, доступен ли объект для транспортировки
     * @return true, если доступен
     */
    boolean isAvailableForTransport();
}
