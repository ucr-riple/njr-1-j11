/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.SpecifiedSpecial;
import ee.ea.pkg1.types.Dragon;

/**
 *
 * @author Jason
 */
public class DracoMeteor extends SpecifiedSpecial{
    public DracoMeteor() {
        basepower = 140;
        accuracy = 90;
        primary = new Dragon();
        name = "draco meteor";

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
