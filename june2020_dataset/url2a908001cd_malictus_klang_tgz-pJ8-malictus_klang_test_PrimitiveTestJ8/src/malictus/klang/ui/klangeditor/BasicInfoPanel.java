/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import malictus.klang.primitives.*;
import malictus.klang.KlangConstants;
import malictus.klang.KlangUtil;
import malictus.klang.ui.*;

/**
 * The Basic Info panel displays some basic file-level information for the open file.
 * @author Jim Halliday
 */
public class BasicInfoPanel extends JScrollPane {
	
	private KlangEditor parent;
	private JPanel iPanel;
	
	private KlangLabel lblFileName;
	private KlangLabel lblFileLocation;
	private KlangLabel lblFileSize;
	private KlangLabel lblFileType;
	private KlangLabel lblChecksum;
	private JButton btnChecksum;
	
	/**
	 * Initiate the basic info panel
	 * @param parent the KlangEditor UI object
	 */
	BasicInfoPanel(KlangEditor parent) {
		this.parent = parent;
		updatePanel();
    }
	
	/**
	 * Called whenever this panel should be updated to reflect the current file
	 */
	protected void updatePanel() {
		//redo panel entirely, since things may have changed
		iPanel = new JPanel();
		iPanel.setLayout(new BoxLayout(iPanel, BoxLayout.Y_AXIS));
		this.setViewportView(iPanel);
		lblFileName = new KlangLabel(KlangConstants.KLANGEDITOR_BASICINFO_NAME);
		iPanel.add(lblFileName);
		lblFileLocation = new KlangLabel(KlangConstants.KLANGEDITOR_BASICINFO_LOCATION);
		iPanel.add(lblFileLocation);
		lblFileSize = new KlangLabel(KlangConstants.KLANGEDITOR_BASICINFO_SIZE);
		iPanel.add(lblFileSize);
		lblFileType = new KlangLabel(KlangConstants.KLANGEDITOR_BASICINFO_FILETYPE);
		iPanel.add(lblFileType);
		lblChecksum = new KlangLabel(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMLABEL);
		iPanel.add(lblChecksum);
		btnChecksum = new JButton(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMBUTTON);
		btnChecksum.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				getChecksum();
			}
		});
		iPanel.add(btnChecksum);
		if (this.parent.getCurrentFile() == null) {
			lblFileName.setEnabled(false);
			lblFileLocation.setEnabled(false);
			lblFileSize.setEnabled(false);
			lblFileType.setEnabled(false);
			lblChecksum.setEnabled(false);
			lblChecksum.setVisible(true);
			lblChecksum.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMLABEL);
			btnChecksum.setVisible(false);
		} else {
			lblFileName.setEnabled(true);
			lblFileLocation.setEnabled(true);
			lblFileSize.setEnabled(true);
			lblFileType.setEnabled(true);
			btnChecksum.setEnabled(true);
			lblFileName.setText(KlangConstants.KLANGEDITOR_BASICINFO_NAME + parent.getCurrentFile().getFile().getName());
			lblFileLocation.setText(KlangConstants.KLANGEDITOR_BASICINFO_LOCATION + parent.getCurrentFile().getFile().getParent());
			String fileSize = "";
			if (parent.isHexMode()) {
				fileSize = KlangUtil.convertToHex(parent.getCurrentFile().getFile().length());
			} else {
				fileSize = "" + parent.getCurrentFile().getFile().length();
			}
			if (parent.getCurrentFile().getFile().length() > 1024) {
				fileSize = fileSize + " (" + KlangUtil.stringForBytes(parent.getCurrentFile().getFile().length()) + ")";
				
			}
			lblFileSize.setText(KlangConstants.KLANGEDITOR_BASICINFO_SIZE + fileSize);
			lblFileType.setText(KlangConstants.KLANGEDITOR_BASICINFO_FILETYPE + parent.getCurrentFile().getFileType());
			
			if (parent.getCurrentFile().getChecksum().equals("")) {
				btnChecksum.setVisible(true);
				lblChecksum.setVisible(false);
			} else {
				btnChecksum.setVisible(false);
				lblChecksum.setVisible(true);
				lblChecksum.setEnabled(true);
				lblChecksum.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMLABEL + parent.getCurrentFile().getChecksum());
			}
			
			addLabelsFor(parent.getCurrentFile().getFileLevelData());
		}
		TitledBorder tb = BorderFactory.createTitledBorder(KlangConstants.KLANGEDITOR_BASICINFO_PANELNAME);
		tb.setTitleFont(KlangConstants.KLANGEDITOR_BORDERTITLE_FONT);
		this.setBorder(tb);
	}

	/**
	 * Add additional labels for each primitivedata object that the file has
	 * @param pd the vector of PrimitiveData objects
	 */
	private void addLabelsFor(Vector<PrimitiveData> pd) {
		int counter = 0;
		while (counter < pd.size()) {
			PrimitiveData prim = pd.get(counter);
			String x = prim.getDescription() + ": ";
			if (prim.getPrimitive().valueExists()) {
				try {
					x = x + prim.getPrimitive().writeValueToString();
					if (prim.getCurrentValueString() != null) {
						x = x + " (" + prim.getCurrentValueString() + ")";
					}
				} catch (BadValueException err) {
					x = prim.getDescription() + ": ";
				}
			}
			KlangLabel label = new KlangLabel(x);
			iPanel.add(label);
			counter = counter + 1;
		}
	}
	
	/**
	 * Helper method to calculate checksums for a file.
	 */
	private void getChecksum() {
		KlangProgressWindow prog = new KlangProgressWindow(parent);
		prog.calculateChecksumFor(parent.getCurrentFile());
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		updatePanel();		
	}
	
}
