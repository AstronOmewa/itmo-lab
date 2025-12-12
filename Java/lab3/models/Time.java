package models;

public class Time {

    private int h;
    private int m;
    private int s;
    private String duration;

    public Time(int h, int m, int s) {
        this.h = Math.clamp(h, 0, 23);
        this.m = Math.clamp(m, 0, 59);
        this.s = Math.clamp(s, 0, 59);
        this.duration = null;
    }

    public Time(String duration) {
        this.duration = duration;
        this.h = 0;
        this.m = 0;
        this.s = 0;
    }

    private Time update(int s) {
        int totalSeconds = this.s + s;
        int extraMinutes = totalSeconds / 60;
        int s1 = totalSeconds % 60;

        int totalMinutes = this.m + extraMinutes;
        int extraHours = totalMinutes / 60;
        int m = totalMinutes % 60;

        int h = (this.h + extraHours) % 24;

        return new Time(h,m,s1);
    }

    public Time elapse(int s) {
        return update(s);
    }

    public Time elapse(int m, int s) {
        return update(s + m * 60);
    }

    public Time elapse(int h, int m, int s) {
        return update(s + m * 60 + h * 3600);
    }

    public String getString() {
        if (duration != null) {
            return duration;
        }
        return switch (h) {
            case 0, 1, 2, 3 ->
                "Поздняя ночь";
            case 4, 5, 6 ->
                "Раннее утро";
            case 7, 8, 9, 10, 11 ->
                "Утро";
            case 12, 13, 14, 15 ->
                "Обед";
            case 16, 17 ->
                "День";
            case 18, 19 ->
                "Вечер";
            case 20, 21, 22, 23 ->
                "Ночь";
            default ->
                "какое-то время";
        };
    }
}
