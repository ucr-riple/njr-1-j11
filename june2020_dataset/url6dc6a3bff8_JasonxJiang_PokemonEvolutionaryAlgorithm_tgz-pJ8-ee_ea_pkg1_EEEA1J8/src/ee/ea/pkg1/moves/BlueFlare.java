/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.SpecifiedSpecial;
import ee.ea.pkg1.types.Fire;

/**
 *
 * @author Jason
 */
public class BlueFlare extends SpecifiedSpecial{
    public BlueFlare() {
        basepower = 130;
        accuracy = 80;
        type = "Fire";
        primary = new Fire();
        name = "blue flare";
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
