import ru.ifmo.se.pokemon.*;

public class Absorb extends SpecialMove{

    public Absorb() {
        super(Type.GRASS, 20, 100);

    }
    @Override
    protected void applySelfEffects(Pokemon p){
        Effect e = new Effect().stat(Stat.HP, -3);
    }
    @Override 
    public String describe(){
        return "применил Absorb (восстановлено 50% здоровья, нанесен урон)";
    }
}