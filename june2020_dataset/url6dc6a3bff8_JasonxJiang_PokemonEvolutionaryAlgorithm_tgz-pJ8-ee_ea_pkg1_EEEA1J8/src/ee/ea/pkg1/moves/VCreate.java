/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.types.Fire;
import ee.ea.pkg1.SpecifiedPhysical;

/**
 *
 * @author Jason
 */
public class VCreate extends SpecifiedPhysical{
    public VCreate() {
        basepower = 180;
        accuracy = 95;
        primary = new Fire();
        name = "V-create";

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
