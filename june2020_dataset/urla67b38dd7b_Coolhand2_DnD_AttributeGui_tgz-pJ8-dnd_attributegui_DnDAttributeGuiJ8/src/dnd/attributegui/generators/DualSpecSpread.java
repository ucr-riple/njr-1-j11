/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.attributegui.generators;

/**
 *
 * @author mike
 */
public class DualSpecSpread extends BaseGenerator {

    @Override
    public String getName() {
        return "Dual-Specialized Array";
    }

    @Override
    public int[] getAttributes() {
        int[] local = {16, 16, 12, 11, 11, 8};
        return local;
    }
}
