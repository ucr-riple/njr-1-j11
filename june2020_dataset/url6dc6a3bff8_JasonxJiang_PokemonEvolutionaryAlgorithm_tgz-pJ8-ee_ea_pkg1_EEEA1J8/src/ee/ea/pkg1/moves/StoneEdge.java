/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.SpecifiedPhysical;
import ee.ea.pkg1.types.Rock;

/**
 *
 * @author Jason
 */
public class StoneEdge extends SpecifiedPhysical{
    public StoneEdge() {
        basepower = 80;
        accuracy = 90;
        primary = new Rock();
        name = "stone edge";

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
