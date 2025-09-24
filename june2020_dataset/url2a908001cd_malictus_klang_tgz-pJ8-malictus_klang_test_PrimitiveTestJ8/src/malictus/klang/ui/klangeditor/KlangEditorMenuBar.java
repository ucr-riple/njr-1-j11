/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import malictus.klang.KlangConstants;

/**
 * This class represents the KlangEditor menu bar.
 * @author Jim Halliday
 */
public class KlangEditorMenuBar extends JMenuBar {

	private KlangEditor parent;
	private JMenu menuFile = null;
	private JMenuItem menuiOpenFile = null;
	private JMenuItem menuiCloseFile = null;
	private JMenuItem menuiQuit = null;
	private JMenu menuOptions = null;
	private JCheckBoxMenuItem menuiOptionsReadOnly = null;
	private JRadioButtonMenuItem menuiOptionsDec = null;
	private JRadioButtonMenuItem menuiOptionsHex = null;
	private JRadioButtonMenuItem menuiOptionsAutoText = null;
	private JRadioButtonMenuItem menuiOptionsManualText = null;
	private JMenu menuAbout = null;
	private JMenuItem menuiAboutAbout = null;
	
	/**
	 * Init the Klang Editor menu bar.
	 * @param parent the window that this menu belongs to
	 */
	public KlangEditorMenuBar(KlangEditor parent) {
		super();
		this.parent = parent;
		menuiQuit = new JMenuItem(KlangConstants.KLANGEDITOR_MENU_QUIT, KeyEvent.VK_F4);
		menuiQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		menuiQuit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doQuit();
			}
		});
		menuiCloseFile = new JMenuItem(KlangConstants.KLANGEDITOR_MENU_CLOSE, KeyEvent.VK_W);
		menuiCloseFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		menuiCloseFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doClose();
			}
		});
		menuiCloseFile.setEnabled(false);
		menuiOpenFile = new JMenuItem(KlangConstants.KLANGEDITOR_MENU_OPEN, KeyEvent.VK_O);
		menuiOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuiOpenFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doOpen();
			}
		});
		menuiOptionsReadOnly = new JCheckBoxMenuItem(KlangConstants.KLANGEDITOR_MENU_READONLY);
		menuiOptionsReadOnly.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doReadOnlySelected();
			}
		});
		menuiOptionsDec = new JRadioButtonMenuItem(KlangConstants.KLANGEDITOR_MENU_DEC);
		menuiOptionsDec.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doHexDecToggle();
			}
		});
		menuiOptionsHex = new JRadioButtonMenuItem(KlangConstants.KLANGEDITOR_MENU_HEX);
		menuiOptionsHex.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doHexDecToggle();
			}
		});
		ButtonGroup bg = new ButtonGroup();
		bg.add(menuiOptionsDec);
		bg.add(menuiOptionsHex);
		//set defaults
		menuiOptionsDec.setSelected(true);
		menuiOptionsReadOnly.setSelected(false);
		//tell parent about default settings as well
		doHexDecToggle();
		doReadOnlySelected();
		
		menuiOptionsAutoText = new JRadioButtonMenuItem(KlangConstants.KLANGEDITOR_MENU_AUTOTEXT);
		menuiOptionsAutoText.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doTextToggle();
			}
		});
		menuiOptionsManualText = new JRadioButtonMenuItem(KlangConstants.KLANGEDITOR_MENU_MANUALTEXT);
		menuiOptionsManualText.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doTextToggle();
			}
		});
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(menuiOptionsAutoText);
		bg2.add(menuiOptionsManualText);
		//set defaults
		menuiOptionsAutoText.setSelected(true);
		menuiOptionsManualText.setSelected(false);
		//tell parent about default settings as well
		doTextToggle();
		
		
		menuiAboutAbout = new JMenuItem(KlangConstants.KLANGEDITOR_MENU_CREDITS, KeyEvent.VK_B);
		menuiAboutAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
		menuiAboutAbout.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doAbout();
			}
		});
		menuFile = new JMenu(KlangConstants.KLANGEDITOR_MENU_FILE);
		menuFile.setMnemonic(KeyEvent.VK_F);
		menuFile.add(menuiOpenFile);
		menuFile.add(menuiCloseFile);
		menuFile.addSeparator();
		menuFile.add(menuiQuit);
		menuOptions = new JMenu(KlangConstants.KLANGEDITOR_MENU_OPTIONS);
		menuOptions.add(menuiOptionsReadOnly);
		menuOptions.addSeparator();
		menuOptions.add(menuiOptionsDec);
		menuOptions.add(menuiOptionsHex);
		menuOptions.addSeparator();
		menuOptions.add(menuiOptionsAutoText);
		menuOptions.add(menuiOptionsManualText);
		menuAbout = new JMenu(KlangConstants.KLANGEDITOR_MENU_ABOUT);
		menuAbout.setMnemonic(KeyEvent.VK_A);
		menuAbout.add(menuiAboutAbout);
		this.add(menuFile);	
		this.add(menuOptions);	
		this.add(menuAbout);		
	}
	
	/**
	 * Toggle menus to reflect a file being open or closed
	 * @param newVal true if a file is currently open, false otherwise
	 */
	protected void setFileIsOpen(boolean newVal) {
		if (newVal == true) {
			this.menuiCloseFile.setEnabled(true);
		} else {
			this.menuiCloseFile.setEnabled(false);
		}
	}
	
	/**
	 * Handle quit menu option
	 */
	private void doQuit() {
		parent.doQuit();
	}
	
	/**
	 * Handle open menu option
	 */
	private void doOpen() {
		parent.openFile();
	}
	
	/**
	 * Handle close menu option
	 */
	private void doClose() {
		parent.closeFile();
	}
	
	/**
	 * Toggle read only option to match menu
	 */
	private void doReadOnlySelected() {
		parent.setReadOnlyOption(this.menuiOptionsReadOnly.isSelected());
	}
	
	/**
	 * Toggle hex/dec selection option to match menu
	 */
	private void doHexDecToggle() {
		parent.setHexMode(this.menuiOptionsHex.isSelected());
	}
	
	/**
	 * Toggle text manual/automatic selection option to match menu
	 */
	private void doTextToggle() {
		parent.setTextMode(this.menuiOptionsManualText.isSelected());
	}
	
	/**
	 * Handle display about dialog
	 */
	private void doAbout() {
		JOptionPane.showMessageDialog(this, KlangConstants.KLANGEDITOR_ABOUT_TEXT, KlangConstants.KLANGEDITOR_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
	
}
