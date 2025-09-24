/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.moves;

import ee.ea.pkg1.SpecifiedSpecial;
import ee.ea.pkg1.types.Ice;

/**
 *
 * @author Jason
 */
public class Blizzard extends SpecifiedSpecial{
    public Blizzard() {
        basepower = 120;
        accuracy = 70;
        primary = new Ice();
        name = "blizzard";

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
