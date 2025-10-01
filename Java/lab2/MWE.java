import ru.ifmo.se.pokemon.*;

public class MWE{
    public static void main(String[] args) {
        Battle b = new Battle();
        Cryogonal c = new Cryogonal("Cryogonal", 1);
        Ariados a = new Ariados("Ariados", 1);
        Spinarak s = new Spinarak("Spinarak", 1);
        b.addAlly(c);
        b.addAlly(a);
        b.addFoe(s);
        
        b.go();
    }
}