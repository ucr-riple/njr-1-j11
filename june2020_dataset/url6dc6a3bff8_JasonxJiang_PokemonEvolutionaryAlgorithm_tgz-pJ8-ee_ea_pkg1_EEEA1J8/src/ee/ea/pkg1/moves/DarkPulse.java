/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.types.Dark;
import ee.ea.pkg1.SpecifiedSpecial;

/**
 *
 * @author Jason
 */
public class DarkPulse extends SpecifiedSpecial {
    
    public DarkPulse()
    {
        accuracy = 80;
        basepower = 100;
        primary = new Dark();
        name = "dark pulse";
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
