/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.SpecifiedSpecial;
import ee.ea.pkg1.types.Flying;

/**
 *
 * @author Jason
 */
public class AirSlash extends SpecifiedSpecial{
    public AirSlash() {
        basepower = 75;
        accuracy = 95;
        primary = new Flying();
        name = "air slash";

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
