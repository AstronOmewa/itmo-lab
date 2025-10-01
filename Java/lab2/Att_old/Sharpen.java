import ru.ifmo.se.pokemon.*;

public final class Sharpen extends StatusMove{
    public Sharpen(){
        super(Type.NORMAL, 0, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.ATTACK, 2);
    }
    @Override
    public String describe(){
        return "применил Sharpen (увеличил атаку)";
    }
}