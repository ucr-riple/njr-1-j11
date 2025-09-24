/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.types;

import ee.ea.pkg1.types.Ghost;
import ee.ea.pkg1.types.Ground;
import ee.ea.pkg1.types.Grass;
import ee.ea.pkg1.types.Normal;
import ee.ea.pkg1.types.Ice;
import ee.ea.pkg1.types.Psychic;
import ee.ea.pkg1.types.Rock;
import ee.ea.pkg1.types.Poison;
import ee.ea.pkg1.types.Steel;
import ee.ea.pkg1.types.Water;

/**
 *
 * @author Chins
 */
public class Bug extends Type{
    
    public Bug()
    {
        name = "Bug";
        Normal normal = new Normal(0);
        Fighting fighting = new Fighting(0);
        Flying flying = new Flying(0);
        Poison poison = new Poison(0);
        Ground ground = new Ground(0);
        Rock rock = new Rock(0);
        Bug bug = new Bug(0);
        Steel steel = new Steel(0);
        Fire fire = new Fire(0);
        Water water = new Water(0);
        Grass grass = new Grass(0);
        Electric electric = new Electric(0);
        Psychic psychic = new Psychic(0);
        Ice ice = new Ice(0);
        Dragon dragon = new Dragon(0);
        Dark dark = new Dark(0);
        Ghost ghost = new Ghost(0);
        effectiveTypes = new Type [3];
        effectiveTypes[0] = dark;
        effectiveTypes[1] = psychic;
        effectiveTypes[2] = grass;
        resistantTypes = new Type [6];
        resistantTypes[0] = fighting;
        resistantTypes[1] = fire;
        resistantTypes[2] = flying;
        resistantTypes[3] = ghost;
        resistantTypes[4] = poison;
        resistantTypes[5] = steel;
        immuneTypes = new Type[0]; 
        
    }
    
    public Bug(int something)
    {
        nothing = something;
        name = "Bug";
        
    }
}
