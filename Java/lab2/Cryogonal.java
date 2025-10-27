
import ru.ifmo.se.pokemon.*;

public final class Cryogonal extends Pokemon{

    public Cryogonal(String name, int level){       
        super(name, level);
        setStats(
            80.0,
            50.0,
            50.0,
            95.0,
            135.0,
            105.0
        );
        setType(Type.ICE);
        addMove(new Attack.Sharpen());
        addMove(new Attack.Rest());
        addMove(new Attack.Recover());
        addMove(new Attack.FlashCannon());
    }


}