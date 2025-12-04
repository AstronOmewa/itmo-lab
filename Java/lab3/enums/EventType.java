package enums;

public enum EventType{
    WENTWRONG(0, "все пошло не так, как надо"),
    ITEMBREAK(1, "сломался"),
    FALLASLEAP(2, "уснул"),
    OBSERVEABSCENCE(3, "обнаружил пропажу"),
    VERYHURRY(4, "очень спешил"),
    WEARINSIDEOUT(5, "одел наизнанку"),
    JUMPOVERROOM(6, "прыгал по комнате"),
    GOOUT(7, "ушел"),
    DISTRACTED(8, "был рассеян");

    private final int index;
    private final String description;

    EventType(int index, String description){
        this.index = index;
        this.description = description;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String getEventType() {
        return description;
    }
}
