/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.*;

import malictus.klang.*;
import malictus.klang.ui.*;

/**
 * The Chunk Info panel displays chunk information
 * @author Jim Halliday
 */
public class ChunkInfoPanel extends JScrollPane {
	
	private KlangEditor parent;
	private JPanel iPanel;
	
	private KlangLabel lblChunkName;
	private KlangLabel lblChunkDesc;
	private KlangLabel lblStartEndSize;
	private KlangLabel lblDataStartEndSize;
	private JButton btnChecksum;
	private KlangLabel lblChecksum;
	private KlangLabel lblDataChecksum;
	
	/**
	 * Initiate the chunk info panel
	 * @param parent the KlangEditor UI object
	 */
	ChunkInfoPanel(KlangEditor parent) {
		this.parent = parent;
		iPanel = new JPanel();
		iPanel.setLayout(new BoxLayout(iPanel, BoxLayout.Y_AXIS));
		this.setViewportView(iPanel);
		lblChunkName = new KlangLabel(KlangConstants.KLANGEDITOR_CHUNKINFO_NAME + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_TYPE);
		iPanel.add(lblChunkName);
		lblChunkDesc = new KlangLabel(KlangConstants.KLANGEDITOR_CHUNKINFO_DESC);
		iPanel.add(lblChunkDesc);
		lblStartEndSize = new KlangLabel(KlangConstants.KLANGEDITOR_CHUNKINFO_START + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_END + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_SIZE);
		iPanel.add(lblStartEndSize);
		lblDataStartEndSize = new KlangLabel(KlangConstants.KLANGEDITOR_CHUNKINFO_DATASTART + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_DATAEND + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_DATASIZE);
		iPanel.add(lblDataStartEndSize);
		
		lblChecksum = new KlangLabel(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMLABEL);
		iPanel.add(lblChecksum);
		lblDataChecksum = new KlangLabel(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMDATALABEL);
		iPanel.add(lblDataChecksum);
		
		iPanel.add(Box.createRigidArea(new Dimension(0,10)));
		
		btnChecksum = new JButton(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMBUTTON);
		btnChecksum.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				getChecksum();
			}
		});
		iPanel.add(btnChecksum);
		TitledBorder tb = BorderFactory.createTitledBorder(KlangConstants.KLANGEDITOR_CHUNKINFO_PANELNAME);
		tb.setTitleFont(KlangConstants.KLANGEDITOR_BORDERTITLE_FONT);
		this.setBorder(tb);
		updatePanel();
    }
	
	/**
	 * Called whenever this panel should be updated to reflect the current state
	 */
	public void updatePanel() {
		if (this.parent.getCurrentChunk() == null) {
			lblChunkName.setEnabled(false);
			lblChunkDesc.setEnabled(false);
			lblStartEndSize.setEnabled(false);
			lblDataStartEndSize.setEnabled(false);
			lblChecksum.setEnabled(false);
			lblDataChecksum.setEnabled(false);
			
			btnChecksum.setVisible(false);
			lblChecksum.setVisible(true);
			lblChecksum.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMLABEL);
			lblDataChecksum.setVisible(true);
			lblDataChecksum.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMDATALABEL);
			lblChunkName.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_NAME + "        " + KlangConstants.KLANGEDITOR_CHUNKINFO_TYPE);
			lblChunkDesc.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_DESC);
			lblStartEndSize.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_START + "     " + KlangConstants.KLANGEDITOR_CHUNKINFO_END + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_SIZE);
			lblDataStartEndSize.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_DATASTART + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_DATAEND + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_DATASIZE);
		} else {
			lblChunkName.setEnabled(true);
			lblChunkDesc.setEnabled(true);
			lblStartEndSize.setEnabled(true);
			lblDataStartEndSize.setEnabled(true);
			btnChecksum.setEnabled(true);
			lblChunkName.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_NAME + parent.getCurrentChunk().getChunkName() + "   "  + KlangConstants.KLANGEDITOR_CHUNKINFO_TYPE + parent.getCurrentChunk().getChunkTypeDescription());
			lblChunkDesc.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_DESC + parent.getCurrentChunk().getChunkNameDescription());
			long size = parent.getCurrentChunk().getEndPosition() - parent.getCurrentChunk().getStartPosition();
			long dSize = parent.getCurrentChunk().getDataEndPosition() - parent.getCurrentChunk().getDataStartPosition();
			long start = parent.getCurrentChunk().getStartPosition();
			long end = parent.getCurrentChunk().getEndPosition();
			long dstart = parent.getCurrentChunk().getDataStartPosition();
			long dend = parent.getCurrentChunk().getDataEndPosition();
			String sString = "";
			String dString = "";
			String startString = "";
			String endString = "";
			String dStartString = "";
			String dEndString = "";
			if (parent.isHexMode()) {
				sString = KlangUtil.convertToHex(size);
				dString = KlangUtil.convertToHex(dSize);
				startString = KlangUtil.convertToHex(start);
				endString = KlangUtil.convertToHex(end);
				dStartString = KlangUtil.convertToHex(dstart);
				dEndString = KlangUtil.convertToHex(dend);
			} else {
				sString = "" + size;
				dString = "" + dSize;
				startString = "" + start;
				endString = "" + end;
				dStartString = "" + dstart;
				dEndString = "" + dend;
			}
			lblStartEndSize.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_START + startString + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_END + endString + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_SIZE + sString);
			lblDataStartEndSize.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_DATASTART + dStartString + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_DATAEND + dEndString + "   " + KlangConstants.KLANGEDITOR_CHUNKINFO_DATASIZE + dString);
			if (parent.getCurrentChunk().getChecksum().equals("")) {
				btnChecksum.setVisible(true);
				lblChecksum.setVisible(false);
				lblDataChecksum.setVisible(false);
			} else {
				btnChecksum.setVisible(false);
				lblChecksum.setVisible(true);
				lblChecksum.setEnabled(true);
				lblChecksum.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMLABEL + parent.getCurrentChunk().getChecksum());
				if (parent.getCurrentChunk().chunkIsAllData()) {
					lblDataChecksum.setVisible(false);
				} else {
					lblDataChecksum.setVisible(true);
					lblDataChecksum.setEnabled(true);
					lblDataChecksum.setText(KlangConstants.KLANGEDITOR_CHUNKINFO_CHECKSUMDATALABEL + parent.getCurrentChunk().getDataChecksum());
				}
			}
		}
	}
	
	/**
	 * Helper method to calculate checksums for a chunk. Will only calculate
	 * a separate data checksum in the chunk has header/footer information
	 */
	private void getChecksum() {
		KlangProgressWindow prog = new KlangProgressWindow(parent);
		prog.calculateChecksumFor(parent.getCurrentChunk());
		if (!(prog.getErrMsg().equals(""))) {
			JOptionPane.showMessageDialog(this, prog.getErrMsg(), KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		updatePanel();		
	}

}
