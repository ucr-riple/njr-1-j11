/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.attributegui.generators;

/**
 *
 * @author mike
 */
public class NormalSpread extends BaseGenerator {

    @Override
    public String getName() {
        return "Standard Array";
    }

    @Override
    public int[] getAttributes() {
        int[] local = {16, 14, 14, 11, 10, 10};
        return local;
    }
}
