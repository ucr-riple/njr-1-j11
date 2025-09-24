/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;

/**
 * Main method and initialization for the klang editor application
 * @author Jim Halliday
 */
public class KlangEditorMain {
	
	/**
	 * The Klang Editor application's main method.
	 *
	 * @param args not currently used
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception err) {
			JOptionPane.showMessageDialog(null, "Error setting look and feel.");
		}
		new KlangEditor();
	}

}
