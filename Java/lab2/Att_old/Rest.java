import ru.ifmo.se.pokemon.*;

public final class Rest extends StatusMove{
    public Rest(){
        super(Type.PSYCHIC, 0, 100);
        
    }
    @Override
    protected void applySelfEffects(Pokemon p){
        Effect.sleep(p);
    }
    @Override
    public String describe(){
        return "применил Rest";
    }
}