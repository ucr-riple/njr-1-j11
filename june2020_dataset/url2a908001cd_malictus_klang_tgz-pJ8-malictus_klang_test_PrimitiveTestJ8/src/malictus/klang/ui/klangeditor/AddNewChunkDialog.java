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

import malictus.klang.KlangConstants;
import malictus.klang.ui.*;
import malictus.klang.chunk.*;
import malictus.klang.file.EditableFileBase;

/**
 * An AddNewChunkDialog is a helper dialog box for adding a new chunk above, below, or under the current chunk.
 * @author Jim Halliday
 */
public class AddNewChunkDialog extends JDialog {
	
	JPanel pnlContent;
	
	JPanel pnlTop;
	KlangLabel lblCurChunkName1;
	
	JPanel pnlChunkName;
	KlangLabel lblChunkName;
	KlangTextField txtfChunkName;
	
	JRadioButton bBefore;
	JRadioButton bAfter;
	JRadioButton bUnder;
	ButtonGroup grpbau;
	
	KlangLabel lblCurChunkName2;
	
	JRadioButton bUseBlank;
	JRadioButton bUseFile;
	ButtonGroup grpbf;
	JPanel pnlButtons;
	
	FileBrowseSubPanel pnlFile;
	
	JPanel pnlOKCancel;
	JButton btnOK;
	JButton btnCancel;
	
	private boolean canceled = false;
	
	public AddNewChunkDialog(KlangEditor parent, Chunk curChunk) {
		super(parent);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_TITLE);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		pnlContent = new JPanel();
		pnlContent.setBorder( new EmptyBorder(11,11,11,11) );
		pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
		this.getContentPane().add(pnlContent);
		
		pnlChunkName = new JPanel();
		FlowLayout f1 = new FlowLayout();
		f1.setAlignment(FlowLayout.LEFT);
		pnlChunkName.setLayout(f1);
		pnlContent.add(pnlChunkName);
		
		lblCurChunkName2 = new KlangLabel(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_CURCHUNK + ": " + curChunk.getChunkName());
		pnlChunkName.add(lblCurChunkName2);
		lblChunkName = new KlangLabel(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_CHUNKNAMELBL);
		pnlChunkName.add(lblChunkName);
		txtfChunkName = new KlangTextField("", 100);
		pnlChunkName.add(txtfChunkName);
		
		pnlTop = new JPanel();
		FlowLayout f2 = new FlowLayout();
		f2.setAlignment(FlowLayout.LEFT);
		pnlTop.setLayout(f2);
		pnlContent.add(pnlTop);
		
		lblCurChunkName1 = new KlangLabel(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_CURCHUNKTEXT1);
		pnlTop.add(lblCurChunkName1);
		
		bBefore = new JRadioButton(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_BEFORE);
		bAfter = new JRadioButton(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_AFTER);
		bUnder = new JRadioButton(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_UNDER);
		grpbau = new ButtonGroup();
		grpbau.add(bBefore);
		grpbau.add(bAfter);
		grpbau.add(bUnder);
		pnlTop.add(bBefore);
		pnlTop.add(bAfter);
		pnlTop.add(bUnder);
		bBefore.setEnabled(false);
		bAfter.setEnabled(false);
		bUnder.setEnabled(false);
		
		//enable any radio buttons that apply
		if (curChunk instanceof EditableContainerChunk) {
			bUnder.setEnabled(true);
		}
		if (curChunk.getParentChunk() != null) {
			if (curChunk.getParentChunk() instanceof EditableContainerChunk) {
				bBefore.setEnabled(true);
				bAfter.setEnabled(true);
			}
		}
		if (curChunk.getParentChunk() == null) {
			if (curChunk.getParentFile() instanceof EditableFileBase) {
				EditableFileBase efb = (EditableFileBase)curChunk.getParentFile();
				if (efb.canAddTopLevelChunks()) {
					bBefore.setEnabled(true);
					bAfter.setEnabled(true);
				}
			}
		}
		//set default
		if (bBefore.isEnabled()) {
			bBefore.setSelected(true);
		} else if (bAfter.isEnabled()) {
			bAfter.setSelected(true);
		} else {
			bUnder.setSelected(true);
		}
		
		pnlButtons = new JPanel();
		FlowLayout f5 = new FlowLayout();
		f5.setAlignment(FlowLayout.LEFT);
		pnlButtons.setLayout(f5);
		pnlContent.add(pnlButtons);
		
		bUseBlank = new JRadioButton(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_RADBLANK);
		bUseFile = new JRadioButton(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_RADFILE);
		grpbf = new ButtonGroup();
		grpbf.add(bUseBlank);
		grpbf.add(bUseFile);
		pnlButtons.add(bUseBlank);
		
		pnlFile = new FileBrowseSubPanel(KlangConstants.KLANGEDITOR_ADDNEWCHUNKDIALOG_FILELBL);
		pnlFile.disableInterface();
		pnlContent.add(pnlFile);
		
		pnlButtons.add(bUseFile);
		bUseBlank.setSelected(true);
		bUseBlank.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				pnlFile.disableInterface();
			}
		});
		bUseFile.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				pnlFile.enableInterface();
			}
		});
		
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

}
