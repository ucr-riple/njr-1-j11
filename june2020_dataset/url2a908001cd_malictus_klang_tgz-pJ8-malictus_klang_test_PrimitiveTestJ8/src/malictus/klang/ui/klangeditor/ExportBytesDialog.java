/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import malictus.klang.KlangConstants;
import malictus.klang.KlangUtil;
import malictus.klang.ui.*;

/**
 * A ExportBytesDialog is a helper dialog box for exporting raw bytes from a file.
 * @author Jim Halliday
 */
public class ExportBytesDialog extends JDialog {

	JPanel pnlFrom;
	JPanel pnlTo;
	JPanel pnlOKCancel;
	JPanel pnlContent;
	FileBrowseSubPanel pnlFile;
	
	JButton btnOK;
	JButton btnCancel;
	KlangLabel lblFrom;
	KlangTextField txtfFrom;
	KlangLabel lblTo;
	KlangTextField txtfTo;
	
	private boolean canceled = false;
	
	public ExportBytesDialog(KlangEditor parent) {
		super(parent);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle(KlangConstants.KLANGEDITOR_EXPORTBYTESDIALOG_TITLE);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		pnlContent = new JPanel();
		pnlContent.setBorder( new EmptyBorder(11,11,11,11) );
		pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
		this.getContentPane().add(pnlContent);
		pnlFrom = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		pnlFrom.setLayout(fl);
		pnlContent.add(pnlFrom);
		pnlTo = new JPanel();
		FlowLayout f2 = new FlowLayout();
		f2.setAlignment(FlowLayout.LEFT);
		pnlTo.setLayout(f2);
		pnlContent.add(pnlTo);
		
		pnlFile = new FileBrowseSubPanel(KlangConstants.KLANGEDITOR_EXPORTBYTESDIALOG_FILELBL);
		pnlContent.add(pnlFile);
		
		pnlOKCancel = new JPanel();
		FlowLayout f3 = new FlowLayout();
		f3.setAlignment(FlowLayout.RIGHT);
		pnlOKCancel.setLayout(f3);
		btnOK = new JButton("OK");
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				canceled = false;
				setVisible(false);
			}
		});
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				canceled = true;
				setVisible(false);
			}
		});
		pnlOKCancel.add(btnOK);
		pnlOKCancel.add(btnCancel);
		pnlContent.add(pnlOKCancel);
		lblFrom = new KlangLabel(KlangConstants.KLANGEDITOR_BYTESDIALOG_FROM);
		lblTo = new KlangLabel(KlangConstants.KLANGEDITOR_BYTESDIALOG_TO);
		txtfFrom = new KlangTextField("", 125);
		txtfTo = new KlangTextField("", 125);
		if (parent.isHexMode()) {
			txtfFrom.setText(KlangUtil.convertToHex(parent.currentByte));
			txtfTo.setText(KlangUtil.convertToHex(parent.currentByte + 1));
		} else {
			txtfFrom.setText("" + parent.currentByte);
			txtfTo.setText("" + (parent.currentByte + 1));
		}
		pnlFrom.add(lblFrom);
		pnlFrom.add(txtfFrom);
		pnlTo.add(lblTo);
		pnlTo.add(txtfTo);
		this.pack();
		if (parent != null) {
        	//center over parent window
        	this.setLocation(parent.getX() + (parent.getWidth() / 2) - (this.getWidth() / 2),
        			parent.getY() + (parent.getHeight() / 2) - (this.getHeight() / 2));
        }
		this.setVisible(true);
	}
	
	/**
	 * Tell if the user canceled the dialog or not
	 * @return true if user canceled the dialog, and false otherwise
	 */
	public boolean wasCanceled() {
		return canceled;
	}
	
	/**
	 * Get the start position that the user typed in
	 * @return the start byte position
	 * @throws NumberFormatException if the string cannot be decoded as a long
	 */
	public long getStartPosition() throws NumberFormatException {
		Long fromL = Long.decode(this.txtfFrom.getText());
		return fromL.longValue();
	}
	
	/**
	 * Get the end position that the user typed in
	 * @return the end byte position
	 * @throws NumberFormatException if the string cannot be decoded as a long
	 */
	public long getEndPosition() throws NumberFormatException {
		Long toL = Long.decode(this.txtfTo.getText());
		return toL.longValue();
	}
	
	/**
	 * Get the selected file
	 * @return the selected file
	 * @throws IOException if string can't be parsed into a file, or user hasn't type anything in
	 */
	public File getSelectedFile() throws IOException {
		String s = this.pnlFile.getSelectedFilename();
		if (s.trim().equals("")) {
			throw new IOException();
		}
		File f = new File(s);
		if (f == null) {
			throw new IOException();
		}
		return f;
	}

}
