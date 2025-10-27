
import ru.ifmo.se.pokemon.*;

public final class Ariados extends Spinarak{
    public Ariados(String name, int level){
        super(name, level);
        setStats(
            70.0,
            90.0,
            70.0,
            60.0,
            70.0,
            40.0
        );
        setType(Type.BUG, Type.POISON);
        addMove(new Attack.SwordsDance());
    }

}