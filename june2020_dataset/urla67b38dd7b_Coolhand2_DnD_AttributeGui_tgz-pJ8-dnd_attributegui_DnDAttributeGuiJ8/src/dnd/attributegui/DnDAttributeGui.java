/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dnd.attributegui;

import dnd.attributegui.ui.MainInterface;
import java.awt.EventQueue;

/**
 *
 * @author Mike
 */
public class DnDAttributeGui {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainInterface mainInterface = new MainInterface(new Character());
                mainInterface.setVisible(true);
            }
        });
    }
}
