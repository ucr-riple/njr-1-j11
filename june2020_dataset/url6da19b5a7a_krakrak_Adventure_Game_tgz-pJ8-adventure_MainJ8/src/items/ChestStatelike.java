/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 *
 * @author Irakiza Krak
 */
public interface ChestStatelike{
    
    /**
     * Opeining a chest
     * @param ccs
     * @param name
     * @param descr
     * @param ShortC
     */
    public void openChest(ChestChangeState ccs, final String name, final String descr, final String ShortC);
    public void closeChest(ChestChangeState ccs, final String name, final String descr, final String shortC);
}
