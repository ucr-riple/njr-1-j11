/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import malictus.klang.KlangConstants;
import malictus.klang.KlangUtil;

/**
 * A TextEncodingDialog is a dialog box that lets the editor choose a text encoding.
 * @author Jim Halliday
 */
public class TextEncodingDialog extends JDialog {
	
	// Used when reopening and resaving plain text files, so that the user doesn't 
	// have to keep telling the program what encoding to use
	public static String currentEncoding = "";

	JPanel pnlContent;
	JPanel pnlEncoding;
	JPanel pnlOK;
	
	JButton btnOK;
	KlangLabel lblEnc;
	JComboBox combEncoding;
	
	/**
	 * Create the text encoding dialog.
	 * @param chunkName the chunk name that is being referenced, or null if no chunk name is applicable
	 */
	public TextEncodingDialog(String chunkName) {
		super();
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle(KlangConstants.KLANG_TEXTENC_DIALOG_TITLE);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		pnlContent = new JPanel();
		pnlContent.setBorder( new EmptyBorder(11,11,11,11) );
		pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
		
		combEncoding = new JComboBox();
		combEncoding.setEditable(true);
		combEncoding.addItem(KlangConstants.KLANG_TEXTENC_DIALOG_NONE);
		Vector<String> encs = KlangUtil.getCommonTextEncodings();
		int counter = 0;
		while (counter < encs.size()) {
			combEncoding.addItem(encs.get(counter));
			counter = counter + 1;
		}
		
		if ((chunkName == null) || (chunkName.equals(""))) {
			lblEnc = new KlangLabel(KlangConstants.KLANG_TEXTENC_DIALOG_TEXT);
		} else {
			lblEnc = new KlangLabel(KlangConstants.KLANG_TEXTENC_DIALOG_TEXT1 + chunkName + KlangConstants.KLANG_TEXTENC_DIALOG_TEXT2);
		}
		
		pnlEncoding = new JPanel();
		FlowLayout f4 = new FlowLayout();
		f4.setAlignment(FlowLayout.LEFT);
		pnlEncoding.setLayout(f4);
		pnlEncoding.add(lblEnc);
		pnlEncoding.add(combEncoding);
		
		pnlOK = new JPanel();
		FlowLayout f2 = new FlowLayout();
		f2.setAlignment(FlowLayout.RIGHT);
		pnlOK.setLayout(f2);
		btnOK = new JButton("OK");
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				setVisible(false);
			}
		});
		pnlOK.add(btnOK);
		
		pnlContent.add(pnlEncoding);
		pnlContent.add(pnlOK);
		this.setContentPane(pnlContent);
		this.pack();
		KlangUtil.centerWindow(this);
		this.setVisible(true);
	}
	
	/**
	 * Return the selected text encoding
	 * @return the selected text encoding
	 */
	public String getChosenEncoding() {
		return (String)combEncoding.getSelectedItem();
	}

}
