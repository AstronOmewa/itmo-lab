import pokemon.Attack;
import ru.ifmo.se.pokemon.*;

public class Steenee extends Bounsweet{
    public Steenee(String name, int level){
        super(name, level);
        setStats(
        52.0,
        40.0,
        48.0,
        40.0,
        48.0,
        62.0
        );
        addMove(new Attack.PlayNice());
    }
}
