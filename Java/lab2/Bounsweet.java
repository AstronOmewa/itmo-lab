import pokemon.Attack;
import ru.ifmo.se.pokemon.*;

public class Bounsweet extends Pokemon{
    public Bounsweet(String name, int level){
        super(name, level);
        setStats(
            42.0,
            30.0,
            38.0,
            30.0,
            38.0,
            32.0
        );
        setType(Type.GRASS);
        addMove(new Attack.TeeterDance());
        addMove(new Attack.DoubleTeam());
    }
}
