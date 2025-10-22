import pokemon.Attack;

public final class Tsareena extends Steenee{
    public Tsareena(String name, int level){
        super(name, level);
        setStats(
            72.0,
            120.0,
            98.0,
            50.0,
            98.0,
            72.0
        );
        addMove(new Attack.DazzlingGleam());
    }
}
