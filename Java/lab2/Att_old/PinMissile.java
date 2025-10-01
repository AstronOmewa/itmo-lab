import ru.ifmo.se.pokemon.*;
import java.lang.Math.*;

public class PinMissile extends PhysicalMove{
    public PinMissile(){
        Random generator = new Random();
        if(generator.nextDouble(0,1)<0.325){
            super(Type.BUG, 50, 95);
        }
        else if(0.325<=generator.nextDouble(0,1)<0.65){
            super(Type.BUG, 50, 95);
        }
        else if(0.5<=generator.nextDouble)
    }
}