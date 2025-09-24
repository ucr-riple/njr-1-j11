/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.types.Electric;
import ee.ea.pkg1.SpecifiedSpecial;

/**
 *
 * @author Jason
 */
public class Thunder extends SpecifiedSpecial{
    public Thunder() {
        basepower = 120;
        accuracy = 70;
        primary = new Electric();
        name = "thunder";

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
