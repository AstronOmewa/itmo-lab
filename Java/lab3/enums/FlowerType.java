package enums;

public enum FlowerType {
    DAISY(0, "маргаритка"),
    TULIP(1, "тюльпан"),
    ROSE(2, "роза");

    private final int index;
    private final String flower;

    FlowerType(int index, String flower){
        this.index = index;
        this.flower = flower;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String getName() {
        return this.flower;
    }
}
