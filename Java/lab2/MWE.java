import ru.ifmo.se.pokemon.*;

public class MWE{
    public static void main(String[] args) {
        Battle b = new Battle();
        Cryogonal cryogonal = new Cryogonal("Cryogonal", 1);
        Ariados ariados = new Ariados("Ariados", 1);
        Spinarak spinarak = new Spinarak("Spinarak", 1);
        Steenee steenee = new Steenee("Steenee",1);
        Bounsweet bounsweet = new Bounsweet("Bounsweet", 1);
        Tsareena tsareena = new Tsareena("Tsareena", 1);
        
        b.addAlly(cryogonal);
        b.addAlly(spinarak);
        b.addAlly(tsareena);

        b.addFoe(ariados);
        b.addFoe(steenee);
        b.addFoe(bounsweet);
        
        b.go();
    }
}