package enums;

public enum Time {
    ATTHEENDOF(0,"в конце концов"),
    INTHEEND(1,"в итоге"),
    VERYLATETONIGHT(2, "очень поздно той ночью"),
    NEXTDAY(3, "на следующий день"),
    FORALONGTIME(4, "в течение долгого времени"),;


    private final int index;
    private final String time;

    Time(int index, String time){
        this.index = index;
        this.time = time;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public String getTime() {
        return this.time;
    }
}
