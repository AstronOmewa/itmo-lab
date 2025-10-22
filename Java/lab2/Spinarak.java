import pokemon.Attack;
import ru.ifmo.se.pokemon.*;

public class Spinarak extends Pokemon{
    public Spinarak(String name, int level){
        super(name, level);
        setStats(
            40.0,
            60.0,
            40.0,
            40.0,
            40.0,
            30.0
        );
        setType(Type.BUG);
        addMove(new Attack.Absorb());
        addMove(new Attack.PinMissile());
        addMove(new Attack.XScissor());
    } 

}