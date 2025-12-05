package enums;

public record Time(int h, int m, int s){
    public Time(int h, int m, int s){
        this.h = Math.clamp(h, 0, 24);
        this.m = Math.clamp(m, 0, 60);
        this.s = Math.clamp(s, 0, 60);
    }

    public String getDescription(){
        return switch(h){
            case 0 -> new String("Поздняя ночь");
            case 1 -> "Поздняя ночь";
            case 2 -> "Поздняя ночь";
            case 3 -> "Поздняя ночь";
            case 4 -> "Поздняя ночь";
            case 5 -> "П";
        }
    }
}