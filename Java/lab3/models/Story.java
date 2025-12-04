package models;

import java.util.ArrayList;

public class Story {
    public  ArrayList<EventSequence> story;

    public void tell(){
        for(EventSequence es: story){
            es.simulate();
        }
    }
    
    public void addSequence(EventSequence es){
        if(this.story == null){
            this.story = new ArrayList<>();
        }
        // System.out.println("Добавлена последовательность: " + es.toString());
        this.story.add(es);
    }
}
