/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.attributegui.generators;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author mike
 */
public class PowerRoll extends BaseGenerator {

    @Override
    public String getName() {
        return "Power Roll";
    }

    @Override
    public int[] getAttributes() {
        Random r = new Random();
        int[] local = {0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 6; i++) {
            int[] rolls = {0, 0, 0, 0};
            for (int k = 0; k < 4; k++) {
                int roll = 1;
                do {
                    roll = r.nextInt(6) + 1;
                } while (roll == 1);
                rolls[k] = roll;
            }
            Arrays.sort(rolls);
            int sum;
            if (rolls[0] == 6) {
                sum = 19;
            } else {
                sum = rolls[1] + rolls[2] + rolls[3];
            }
            local[i] = sum;
        }
        return local;
    }
}
