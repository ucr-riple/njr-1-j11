/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 *
 * @author Irakiza Krak
 */
public interface ChestChangeState {
    public void setState(ChestStatelike state);
    public ChestStatelike getState();
    public ChestStatelike getClosedChest();
    public ChestStatelike getOpenedChest();
}
