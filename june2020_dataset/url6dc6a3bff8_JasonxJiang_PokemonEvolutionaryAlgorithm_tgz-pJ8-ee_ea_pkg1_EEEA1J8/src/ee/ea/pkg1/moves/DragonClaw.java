/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.types.Dragon;
import ee.ea.pkg1.SpecifiedPhysical;

/**
 *
 * @author Jason
 */
public class DragonClaw extends SpecifiedPhysical{
    
    public DragonClaw()
    {
        basepower = 80;
        accuracy = 100;
        primary = new Dragon();
        name = "dragon claw";
    }
    
    public int recoilDamage(int damageDealt) {
        return 0;
    }

    public int statBoost() {
        return 0;
    }

    public int statDrop() {
        return 0;
    }

    public int recoverHealth() {
        return 0;
    }

    public double typeModifier() {
        return 0;
    }
    
}
