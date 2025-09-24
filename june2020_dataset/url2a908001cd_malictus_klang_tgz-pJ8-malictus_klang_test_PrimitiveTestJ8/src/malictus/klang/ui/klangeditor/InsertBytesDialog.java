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
 * A InsertBytesDialog is a helper dialog box for inserting raw bytes into the current file. The inserted
 * byte may be blank (all zeroes) or imported from another file.
 * @author Jim Halliday
 */
public class InsertBytesDialog extends JDialog {

	JPanel pnlFrom;
	JPanel pnlOKCancel;
	JPanel pnlContent;
	JPanel pnlButtons;
	FileBrowseSubPanel pnlFile;
	
	JButton btnOK;
	JButton btnCancel;
	KlangLabel lblFrom;
	KlangTextField txtfFrom;	
	JRadioButton bUseBlank;
	KlangTextField txtfByteNum;
	KlangLabel lblUseBlankEnd;
	JRadioButton bUseFile;
	ButtonGroup grp;
	
	private boolean canceled = false;
	
	public InsertBytesDialog(KlangEditor parent) {
		super(parent);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle(KlangConstants.KLANGEDITOR_INSERTBYTESDIALOG_TITLE);
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
		
		pnlButtons = new JPanel();
		FlowLayout f5 = new FlowLayout();
		f5.setAlignment(FlowLayout.LEFT);
		pnlButtons.setLayout(f5);
		pnlContent.add(pnlButtons);
		bUseBlank = new JRadioButton(KlangConstants.KLANGEDITOR_INSERTBYTESDIALOG_RADBLANK);
		bUseFile = new JRadioButton(KlangConstants.KLANGEDITOR_INSERTBYTESDIALOG_RADFILE);
		grp = new ButtonGroup();
		grp.add(bUseBlank);
		grp.add(bUseFile);
		pnlButtons.add(bUseBlank);
		txtfByteNum = new KlangTextField("", 60);
		pnlButtons.add(txtfByteNum);
		lblUseBlankEnd = new KlangLabel(KlangConstants.KLANGEDITOR_INSERTBYTESDIALOG_FILEEND);
		pnlButtons.add(lblUseBlankEnd);
		pnlButtons.add(bUseFile);
		bUseBlank.setSelected(true);
		bUseBlank.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				pnlFile.disableInterface();
				txtfByteNum.setEnabled(true);
			}
		});
		bUseFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				pnlFile.enableInterface();
				txtfByteNum.setEnabled(false);
			}
		});
		pnlContent.add(pnlButtons);
		
		pnlFile = new FileBrowseSubPanel(KlangConstants.KLANGEDITOR_INSERTBYTESDIALOG_FILELBL);
		pnlFile.disableInterface();
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
		txtfFrom = new KlangTextField("", 125);
		if (parent.isHexMode()) {
			txtfFrom.setText(KlangUtil.convertToHex(parent.currentByte));
		} else {
			txtfFrom.setText("" + parent.currentByte);
		}
		pnlFrom.add(lblFrom);
		pnlFrom.add(txtfFrom);
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
	 * Get the insert position that the user typed in
	 * @return the insert byte position
	 * @throws NumberFormatException if the string cannot be decoded as a long
	 */
	public long getInsertPosition() throws NumberFormatException {
		Long fromL = Long.decode(this.txtfFrom.getText());
		return fromL.longValue();
	}
	
	/**
	 * Get the number of blank bytes to insert
	 * @return the number of bytes to insert
	 * @throws NumberFormatException if the string cannot be decoded as a long
	 */
	public long getNumBytes() throws NumberFormatException {
		Long fromL = Long.decode(this.txtfByteNum.getText());
		return fromL.longValue();
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
	
	/**
	 * Tell if the user has selected blank bytes or read from file
	 * @return true if the user selected to import bytes from a file, and false if the user wants to write blank bytes
	 */
	public boolean isUseFile() {
		if (this.bUseFile.isSelected()) {
			return true;
		}
		return false;
	}
	
}
