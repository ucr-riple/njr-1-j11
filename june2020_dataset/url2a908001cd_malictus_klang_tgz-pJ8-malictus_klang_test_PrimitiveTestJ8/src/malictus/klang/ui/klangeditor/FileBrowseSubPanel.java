/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import malictus.klang.ui.*;
import malictus.klang.*;

/**
 * This panel represents a browse panel - it contains a label, text field and browse button.
 * @author Jim Halliday
 */
public class FileBrowseSubPanel extends JPanel {
	
	KlangLabel lblFile;
	KlangTextField txtfFile;
	JButton btnFile;
	//static so all dialogs can share previously viewed location
	static JFileChooser choose = new JFileChooser();
	
	/**
	 * Initiate the browse panel
	 * @param lbl the text to be placed before the text field
	 */
	public FileBrowseSubPanel(String lbl) {
		super();
		FlowLayout f1 = new FlowLayout();
		f1.setAlignment(FlowLayout.LEFT);
		this.setLayout(f1);
		lblFile = new KlangLabel(lbl);
		txtfFile = new KlangTextField("", 475);
		btnFile = new JButton(KlangConstants.KLANGEDITOR_BROWSEPANEL_FILEBTN);
		btnFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				browseForFile();
			}
		});
		this.add(lblFile);
		this.add(txtfFile);
		this.add(btnFile);
	}
	
	/**
	 * Bring up a JFileChooser for choosing a file to write data to
	 */
	private void browseForFile() {
		int response = choose.showSaveDialog(this);
		if (response != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File x = choose.getSelectedFile();
		this.txtfFile.setText(x.getPath());
	}
	
	/**
	 * Turn off all UI components
	 */
	public void disableInterface() {
		this.lblFile.setEnabled(false);
		this.btnFile.setEnabled(false);
		this.txtfFile.setEnabled(false);
	}
	
	/**
	 * Turn on all UI components
	 */
	public void enableInterface() {
		this.lblFile.setEnabled(true);
		this.btnFile.setEnabled(true);
		this.txtfFile.setEnabled(true);
	}
	
	/**
	 * Retrieval method for file name.
	 * @return whatever text is in the text file box; may be blank or not a real path.
	 */
	public String getSelectedFilename() {
		return this.txtfFile.getText();
	}

}
