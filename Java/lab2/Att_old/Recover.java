import ru.ifmo.se.pokemon.*;

public final class Recover extends StatusMove{
    public Recover(){
        super(Type.NORMAL, 0, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon p){
        Effect e = new Effect().turns(1).stat(Stat.HP, 10);
    }
    @Override
    public String describe(){
        return "применил Rest (восполнил HP)";
    }
}