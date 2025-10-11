import ru.ifmo.se.pokemon.*;

public final class FlashCannon extends SpecialMove{
    private boolean succeed = false;
    public FlashCannon(){
        super(Type.STEEL, 80, 100);
    }
    @Override 
    public void applyOppEffects(Pokemon p){
        Effect e = new Effect().chance(0.1).stat(Stat.SPECIAL_DEFENSE, -1);
        p.addEffect(e);
        if(e.success()){
            this.succeed = true;
        }
    }
    @Override
    public String describe(){

        return succeed?"применил Flash Cannon (неудача)":"применил Flash Cannon (нанесен урон)";
    }
}