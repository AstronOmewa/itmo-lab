package enums;

public enum Places{

    LEFTPOCKET(0,"левый карман"),
    RIGHTPOCKET(1,"правый карман"),
    DEFAULT(2, "ячейка инвентаря по умолчанию");

    private final int index;
    private final String place;

    Places(int index, String place){
        this.index = index;
        this.place = place;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String getPlace() {
        return this.place;
    }
}
