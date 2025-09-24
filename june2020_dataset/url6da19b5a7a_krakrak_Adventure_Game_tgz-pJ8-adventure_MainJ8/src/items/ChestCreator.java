/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/**
 *
 * @author Irakiza Krak
 */
public class ChestCreator {
   public static Chest createChest(){
       return new ChestStateContext();
   }
}
