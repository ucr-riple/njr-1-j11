/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.types;

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
public class Grass extends Type{
    public Grass()
    {
        name = "Grass";
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
        effectiveTypes = new Type[3];
        effectiveTypes[0] = ground;
        effectiveTypes[1] = rock;
        effectiveTypes[2] = water;
        resistantTypes = new Type[7];
        resistantTypes[0] = bug;
        resistantTypes[1] = dragon;
        resistantTypes[2] = steel;
        resistantTypes[3] = fire;
        resistantTypes[4] = flying;
        resistantTypes[5] = grass;
        resistantTypes[6] = poison;
        immuneTypes = new Type [0];
    }
    
    public Grass(int something)
    {
        nothing= something;
        name = "Grass";
    }
}
