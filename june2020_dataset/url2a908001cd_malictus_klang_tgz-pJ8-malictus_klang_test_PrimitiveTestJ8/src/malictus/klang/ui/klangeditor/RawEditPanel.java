/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;
import javax.swing.border.*;

import java.io.*;
import java.awt.*;
import malictus.klang.ui.*;
import malictus.klang.*;
import malictus.klang.primitives.*;

/**
 * The Raw Edit panel provides a way to edit and view any file directly, 
 * regardless of file structure.
 * @author Jim Halliday
 */
public class RawEditPanel extends JScrollPane {
	
	private KlangEditor parent;
	private JPanel thePanel;
	private JPanel pnlButtons;
	private JPanel pnlBottomButtons;
	private KlangLabel lblCurByte;
	private KlangTextField txtfCurByte;
	private JButton btnJump;
	private JButton btnBack1;
	private JButton btnBack16;
	private JButton btnFor1;
	private JButton btnFor16;
	private JButton btnInsertBytes;
	private JButton btnDeleteBytes;
	private JButton btnExportBytes;
	
	private KlangLabel lblPrimView1;
	private JComboBox combPrimView;
	private KlangLabel lblPrimView2;
	private JButton btnPrimView;
	
	private JPanel pnlHexText;
	private KlangTextPane txtpHex;
	private JPanel pnlStringText;
	private KlangTextPane txtpString;
	private JComboBox combPrims;
	private KlangLabel lblHex;
	private KlangLabel lblASCII;
	
	private PrimitiveData curPrim;
	
	/**
	 * Initiate the raw edit panel
	 * @param parent the KlangEditor UI object
	 */
	RawEditPanel(KlangEditor parent) {
		this.parent = parent;
		thePanel = new JPanel();
		thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
		this.setViewportView(thePanel);
		pnlButtons = new JPanel();
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.CENTER);
		f.setVgap(0);
		pnlButtons.setLayout(f);
		lblCurByte = new KlangLabel(KlangConstants.KLANGEDITOR_RAWEDIT_CURBYTELABEL + ":");
		txtfCurByte = new KlangTextField("", 70);
		btnJump = new JButton(KlangConstants.KLANGEDITOR_RAWEDIT_JUMPBUTTON);
		btnJump.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doJump();
			}
		});
		btnBack1 = new JButton(KlangConstants.KLANGEDITOR_RAWEDIT_BACK1);
		btnBack1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doMove(-1);
			}
		});
		btnBack16 = new JButton(KlangConstants.KLANGEDITOR_RAWEDIT_BACK16);
		btnBack16.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doMove(-16);
			}
		});
		btnFor16 = new JButton(KlangConstants.KLANGEDITOR_RAWEDIT_FOR16);
		btnFor16.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doMove(16);
			}
		});
		btnFor1 = new JButton(KlangConstants.KLANGEDITOR_RAWEDIT_FOR1);
		btnFor1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doMove(1);
			}
		});
		
		pnlButtons.add(btnBack16);
		pnlButtons.add(btnBack1);
		pnlButtons.add(lblCurByte);
		pnlButtons.add(btnFor1);
		pnlButtons.add(btnFor16);
		pnlButtons.add(Box.createRigidArea(new Dimension(40, 1)));
		pnlButtons.add(txtfCurByte);
		pnlButtons.add(btnJump);
		thePanel.add(pnlButtons);
		pnlHexText = new JPanel();
		pnlHexText.setLayout(new BorderLayout());
		lblHex = new KlangLabel(KlangConstants.KLANGEDITOR_RAWEDIT_LBLHEX);
		lblHex.setHorizontalAlignment(JTextField.RIGHT);
		lblHex.setPreferredSize(new Dimension(60, lblHex.getHeight()));
		lblHex.setBorder(new EmptyBorder(2,7,2,5));
		txtpHex = new KlangTextPane("", KlangConstants.KLANGEDITOR_RAWEDIT_EDITSTRIP_WIDTH);
		txtpHex.setBorder(BorderFactory.createLineBorder(Color.black));
		pnlHexText.add(lblHex, BorderLayout.WEST);
		pnlHexText.add(txtpHex, BorderLayout.CENTER);
		pnlHexText.setMaximumSize(new Dimension(10000, txtpHex.getHeight()));
		thePanel.add(pnlHexText);
		pnlStringText = new JPanel();
		pnlStringText.setLayout(new BorderLayout());
		txtpString = new KlangTextPane("", KlangConstants.KLANGEDITOR_RAWEDIT_EDITSTRIP_WIDTH);
		txtpString.setBorder(BorderFactory.createLineBorder(Color.black));
		txtpString.setMaximumSize(new Dimension((int)txtpString.getMaximumSize().getWidth(), (int)txtpString.getMaximumSize().getHeight() + 3));
		txtpString.setPreferredSize(new Dimension((int)txtpString.getPreferredSize().getWidth(), (int)txtpString.getPreferredSize().getHeight() + 3));
		txtpHex.setMaximumSize(new Dimension((int)txtpHex.getMaximumSize().getWidth(), (int)txtpHex.getMaximumSize().getHeight() + 3));
		txtpHex.setPreferredSize(new Dimension((int)txtpHex.getPreferredSize().getWidth(), (int)txtpHex.getPreferredSize().getHeight() + 3));
		lblASCII = new KlangLabel(KlangConstants.KLANGEDITOR_RAWEDIT_LBLASCII);
		lblASCII.setHorizontalAlignment(JTextField.RIGHT);
		lblASCII.setPreferredSize(new Dimension(60, lblASCII.getHeight()));
		lblASCII.setBorder(new EmptyBorder(2,7,2,5));
		pnlStringText.add(lblASCII, BorderLayout.WEST);
		pnlStringText.add(txtpString, BorderLayout.CENTER);
		pnlStringText.setMaximumSize(new Dimension(10000, txtpString.getHeight()));
		thePanel.add(pnlStringText);
	    combPrims = new JComboBox();
	    combPrims.setPreferredSize(new Dimension(75, txtpString.getHeight()));
	    combPrims.setMaximumSize(new Dimension(75, txtpString.getHeight()));
		
		txtpHex.setEditable(false);
		txtpString.setEditable(false);
		txtpHex.setContentType("text/html");
		txtpString.setContentType("text/html");
		
		pnlBottomButtons = new JPanel();
		FlowLayout f2 = new FlowLayout();
		f2.setAlignment(FlowLayout.CENTER);
		pnlBottomButtons.setLayout(f);
		
		lblPrimView1 = new KlangLabel(KlangConstants.KLANGEDITOR_RAWEDIT_PRIMVIEW1);
		combPrimView = new JComboBox();
		combPrimView.setEditable(false);
		int counter = 0;
		while (counter < KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.size()) {
			try {
				Object x = Class.forName(KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.get(counter)).newInstance();
				if ((x instanceof Primitive) && (x instanceof PrimitiveFixedByte)) {
					combPrimView.addItem(((Primitive)x).getPrimitiveDescription());
				}
			} catch (Exception err) {
				err.printStackTrace();
				//just ignore this one and keep going
			}
			counter = counter + 1;
		}
		combPrimView.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				updatePanel();
			}
		});
		
		lblPrimView2 = new KlangLabel("***");
		btnPrimView = new JButton(KlangConstants.KLANGEDITOR_RAWEDIT_PRIMEDITBUTTON);
		btnPrimView.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doEditCurrentPrim();
			}
		});
		pnlBottomButtons.add(lblPrimView1);
		pnlBottomButtons.add(combPrimView);
		pnlBottomButtons.add(lblPrimView2);
		pnlBottomButtons.add(btnPrimView);
		lblPrimView1.setEnabled(true);
		combPrimView.setEnabled(false);
		lblPrimView2.setEnabled(false);
		lblPrimView2.setPreferredSize(new Dimension(85, lblPrimView2.getHeight()));
		btnPrimView.setEnabled(false);
		pnlBottomButtons.add(Box.createRigidArea(new Dimension(20, 1)));
		
		btnInsertBytes = new JButton(KlangConstants.KLANGEDITOR_RAWEDIT_INSERTBYTES);
		btnInsertBytes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doInsertBytes();
			}
		});
		btnDeleteBytes = new JButton(KlangConstants.KLANGEDITOR_RAWEDIT_DELETEBYTES);
		btnDeleteBytes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doDeleteBytes();
			}
		});
		btnExportBytes = new JButton(KlangConstants.KLANGEDITOR_RAWEDIT_EXPORTBYTES);
		btnExportBytes.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doExportBytes();
			}
		});
		btnInsertBytes.setEnabled(false);
		btnDeleteBytes.setEnabled(false);
		btnExportBytes.setEnabled(false);
		pnlBottomButtons.add(btnInsertBytes);
		pnlBottomButtons.add(btnDeleteBytes);
		pnlBottomButtons.add(btnExportBytes);
		thePanel.add(Box.createRigidArea(new Dimension(1, 10)));
		thePanel.add(pnlBottomButtons);
		
		thePanel.setBorder( new EmptyBorder(7,7,7,0) );
		TitledBorder tb = BorderFactory.createTitledBorder(KlangConstants.KLANGEDITOR_RAWEDIT_PANELNAME);
		tb.setTitleFont(KlangConstants.KLANGEDITOR_BORDERTITLE_FONT);
		this.setBorder(tb);
		updatePanel();
    }
	
	/**
	 * Called whenever this panel should be updated to reflect the current state
	 */
	protected void updatePanel() {
		updatePanel("");
	}
	
	/**
	 * Called whenever this panel should be updated to reflect the current state.
	 * @param primitiveType the type of primitive that should be reflected; 
	 * 		leave blank or null if not specified
	 */
	protected void updatePanel(String primitiveType) {
		curPrim = null;
		if (primitiveType != null) {
			if (!primitiveType.equals("")) {
				//select the primitive, if it exists in the list
				if (KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.contains(primitiveType)) {
					this.combPrimView.setSelectedIndex(KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.indexOf(primitiveType));
				}
			}
		}
		if (this.parent.getCurrentFile() == null) {
			this.lblCurByte.setText(KlangConstants.KLANGEDITOR_RAWEDIT_CURBYTELABEL + ":");
			this.lblCurByte.setEnabled(false);
			this.txtfCurByte.setEnabled(false);
			this.btnJump.setEnabled(false);
			this.txtpHex.setEnabled(false);
			this.txtpString.setEnabled(false);
			this.txtpHex.setText("");
			this.txtpString.setText("");
			this.btnBack1.setEnabled(false);
			this.btnBack16.setEnabled(false);
			this.btnFor1.setEnabled(false);
			this.btnFor16.setEnabled(false);
			this.btnInsertBytes.setEnabled(false);
			this.btnExportBytes.setEnabled(false);
			this.btnDeleteBytes.setEnabled(false);
			lblPrimView2.setEnabled(false);
			lblPrimView2.setText("***");
			combPrimView.setEnabled(false);
			this.btnPrimView.setEnabled(false);
			return;
		} 
		this.lblCurByte.setEnabled(true);
		this.txtfCurByte.setEnabled(true);
		this.btnJump.setEnabled(true);
		this.txtpHex.setEnabled(true);
		this.txtpString.setEnabled(true);
		this.btnBack1.setEnabled(true);
		this.btnBack16.setEnabled(true);
		this.btnFor1.setEnabled(true);
		this.btnFor16.setEnabled(true);
		combPrimView.setEnabled(true);
		if (parent.isReadOnlyMode()) {
			this.btnInsertBytes.setEnabled(false);
			this.btnDeleteBytes.setEnabled(false);
		} else {
			this.btnInsertBytes.setEnabled(true);
			this.btnDeleteBytes.setEnabled(true);
		}
		this.btnExportBytes.setEnabled(true);
		//for now; may change below
		lblPrimView2.setEnabled(false);
		this.btnPrimView.setEnabled(false);
		if (this.parent.currentByte == -1) {
			this.lblCurByte.setText(KlangConstants.KLANGEDITOR_RAWEDIT_CURBYTELABEL + ":");
		} else {
			if (parent.isHexMode()) {
				this.lblCurByte.setText(KlangConstants.KLANGEDITOR_RAWEDIT_CURBYTELABEL + ": " + KlangUtil.convertToHex(parent.currentByte));
			} else {
				this.lblCurByte.setText(KlangConstants.KLANGEDITOR_RAWEDIT_CURBYTELABEL + ": " + parent.currentByte);
			}
		}	
		RandomAccessFile raf = null;
		try {
			//populate text panes
			raf = new RandomAccessFile(parent.getCurrentFile().getFile(), "r");
			if (parent.currentByte > 4) {
				raf.seek(parent.currentByte - 4);
			} else {
				raf.seek(0);
			}
			long length = parent.getCurrentFile().getFile().length();
			int numToShow = 50;
			String hexTxt = "<font face=\"monospace\">";
			String stringTxt = "<font face=\"monospace\">";
			int counter = 0;
			if (parent.currentByte < 4) {
				int x = 4 - (int)parent.currentByte;
				while (x > 0) {
					hexTxt = hexTxt + "&nbsp;&nbsp;&nbsp;&nbsp;";
					stringTxt = stringTxt + "&nbsp;&nbsp;&nbsp;&nbsp;";
					counter = counter + 1;
					x = x - 1;
				}
			}
			while ((raf.getFilePointer() < length) && (counter < numToShow)) {
				int x = raf.readUnsignedByte();
				if (raf.getFilePointer() == (parent.currentByte + 1)) {
					hexTxt = hexTxt + "<b><font color=\"red\" size=\"+1\">";
					hexTxt = hexTxt + convertHexString(Integer.toHexString(x));
					hexTxt = hexTxt + "</font></b>&nbsp;&nbsp;";
					stringTxt = stringTxt + "<b><font color=\"red\" size=\"+1\">" + convertASCIIString(x) + "</font></b>&nbsp;&nbsp;&nbsp;";
				} else {
					hexTxt = hexTxt + convertHexString(Integer.toHexString(x)) + "&nbsp;&nbsp;&nbsp;";
					stringTxt = stringTxt + convertASCIIString(x) + "&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				counter = counter + 1;
			}
			hexTxt = hexTxt + "</font>";
			stringTxt = stringTxt + "</font>";
			this.txtpHex.setText(hexTxt);
			this.txtpHex.select(0, 100);
			this.txtpString.setText(stringTxt);
			//populate label
			if (this.parent.currentByte != -1) {
				Object x = Class.forName(KlangConstants.KLANGEDITOR_DISPLAY_PRIMS.get(this.combPrimView.getSelectedIndex())).newInstance();
				if ((x instanceof Primitive) && (x instanceof PrimitiveFixedByte)) {
					PrimitiveFixedByte fb = (PrimitiveFixedByte)x;
					if (fb.getFixedLength() > (this.parent.getCurrentFile().getFile().length() - this.parent.currentByte)) {
						this.lblPrimView2.setText("***");
					} else {
						Primitive prim = (Primitive)x;
						raf.seek(parent.currentByte);	
						long start = parent.currentByte;
						prim.setValueFromFile(raf);
						long end = raf.getFilePointer();
						String val = "";
						if ( (x instanceof PrimitiveInt) && (parent.isHexMode()) ) {
							val = KlangUtil.convertToHex(((PrimitiveInt)x).getValueAsLong());
						} else {
							val = prim.writeValueToString();
						}	
						this.lblPrimView2.setText(val);
						if (!parent.isReadOnlyMode()) {
							this.btnPrimView.setEnabled(true);
						}
						PrimitiveData pd = new PrimitiveData(prim, KlangConstants.PRIMITIVE_DATA_RAW_VALUE_NAME, start, end);
						this.curPrim = pd;
					}
					//overwrite of KlangLabel's resetting of label's width
					lblPrimView2.setPreferredSize(new Dimension(80, lblPrimView2.getHeight()));
				}
				lblPrimView2.setEnabled(true);
			}
			raf.close();
		} catch (Exception err) {
			if (raf != null) {
				try {
					raf.close();
				} catch (Exception foo) {}
			}
		}
	}
	
	/**
	 * Return the ASCII representation of an integer, but only if it's one of regaular ASCII characters
	 * @param val the integer value
	 * @return the ASCII value that this integer represents, or a space if the ASCII symbol is out of the normal range
	 */
	private String convertASCIIString(int val) {
		if ((val <= 32) || (val > 126)) {
			return "&nbsp;";
		}
		//this one character isn't displaying correctly in HTML; are there others?
		if (val == 60) {
			return "&#60;";
		}
		return new Character((char)val).toString();
	}
	
	/**
	 * Append a zero to front of a hex string if it's a single digit
	 * @param input the input hex string
	 * @return the reformatted hex string
	 */
	private String convertHexString(String input) {
		if (input.length() < 2) {
			input = "0" + input;
		}
		return input;
	}
	
	/**
	 * Move forward or backwards from the current position
	 * @param amt the amt to move; may be positive or negative
	 */
	private void doMove(int amt) {
		long newbyte = this.parent.currentByte + amt;
		if (newbyte < 0) {
			newbyte = 0;
		}
		if (newbyte >= this.parent.getCurrentFile().getFile().length()) {
			newbyte = this.parent.getCurrentFile().getFile().length() - 1;
		}
		parent.jumpToByte(newbyte);
	}
	
	/**
	 * Called whenever the jump button is pressed
	 */
	private void doJump() {
		if (this.txtfCurByte.getText().trim().equals("")) {
			return;
		}
		String x = txtfCurByte.getText().trim();
		try {
			Long lo = Long.decode(x);
			if (lo.longValue() < 0) {
				//ignore
				updatePanel();
			} else {
				if (lo.longValue() >= parent.getCurrentFile().getFile().length()) {
					lo = new Long(parent.getCurrentFile().getFile().length() - 1);
				}
				parent.jumpToByte(lo.longValue());
				updatePanel();
			}
		} catch (Exception err) {
			//just ignore and reset
			updatePanel();
		}
	}
	
	/**
	 * Edit the current prim value, as selected in the prim combo box.
	 */
	private void doEditCurrentPrim() {
		//double check
		if (parent.isReadOnlyMode()) {
			return;
		}
		PrimEditDialog pep = new PrimEditDialog(curPrim, this.parent);
		if (pep.wasCanceled()) {
			return;
		}
		if (!pep.valueChanged()) {
			return;
		}
		//alter the bytes
		RandomAccessFile raf = null;
		try {
			curPrim.getPrimitive().setValueFromString(pep.getNewValue());
			raf = new RandomAccessFile(parent.getCurrentFile().getFile(), "rw");
			raf.seek(curPrim.getStartBytePosition());
			curPrim.getPrimitive().writeValueToFile(raf);
		} catch (Exception err) {
			JOptionPane.showMessageDialog(parent, err.getMessage(), KlangConstants.ERROR_GENERAL_FILE, JOptionPane.ERROR_MESSAGE);
			return;
		}
		//update file itself with the change
		parent.reparseEntireFile(curPrim.getStartBytePosition());
	}
	
	/**
	 * Insert button pressed
	 */
	protected void doInsertBytes() {
		InsertBytesDialog ibd = new InsertBytesDialog(this.parent);
		if (ibd.wasCanceled()) {
			return;
		}
		try {
			long pos = ibd.getInsertPosition();
			File fil = null;
			Long numbytes = null;
			if (pos < 0) {
				pos = 0;
			}
			if (pos > parent.getCurrentFile().getFile().length()) {
				pos = parent.getCurrentFile().getFile().length();
			}
			if (ibd.isUseFile()) {
				fil = ibd.getSelectedFile();
				parent.insertFile(pos, fil);
			} else {
				numbytes = ibd.getNumBytes();
				parent.insertBytes(pos, numbytes);
			}
			
		} catch (NumberFormatException err) {
			JOptionPane.showMessageDialog(this, KlangConstants.KLANGEDITOR_RAWEDIT_NUMERR, KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		} catch (IOException err2) {
			JOptionPane.showMessageDialog(this, KlangConstants.ERROR_GENERAL_FILE, KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	/**
	 * Delete button pressed
	 */
	protected void doDeleteBytes() {
		DeleteBytesDialog dbd = new DeleteBytesDialog(this.parent);
		if (dbd.wasCanceled()) {
			return;
		}
		try {
			long start = dbd.getStartPosition();
			long end = dbd.getEndPosition();
			if (start < 0) {
				start = 0;
			}
			if (end > parent.getCurrentFile().getFile().length()) {
				end = parent.getCurrentFile().getFile().length();
			}
			if (end <= start) {
				JOptionPane.showMessageDialog(this, KlangConstants.KLANGEDITOR_RAWEDIT_NUMERR2, KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
				return;
			}
			parent.deleteBytes(start, end);
		} catch (NumberFormatException err) {
			JOptionPane.showMessageDialog(this, KlangConstants.KLANGEDITOR_RAWEDIT_NUMERR, KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
	/**
	 * Export button pressed
	 */
	protected void doExportBytes() {
		ExportBytesDialog ebd = new ExportBytesDialog(this.parent);
		if (ebd.wasCanceled()) {
			return;
		}
		try {
			long start = ebd.getStartPosition();
			long end = ebd.getEndPosition();
			File fil = ebd.getSelectedFile();
			if (start < 0) {
				start = 0;
			}
			if (end > parent.getCurrentFile().getFile().length()) {
				end = parent.getCurrentFile().getFile().length();
			}
			if (end <= start) {
				JOptionPane.showMessageDialog(this, KlangConstants.KLANGEDITOR_RAWEDIT_NUMERR2, KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (fil.exists()) {
				int keepgoing = JOptionPane.showConfirmDialog(this, KlangConstants.KLANGEDITOR_RAWEDIT_COFIRMDEL, KlangConstants.KLANGEDITOR_RAWEDIT_COFIRMDEL, JOptionPane.YES_NO_OPTION);
				if (keepgoing != JOptionPane.YES_OPTION) {
					return;
				}
				fil.delete();
			}
			fil.createNewFile();
			parent.exportBytes(start, end, fil);
		} catch (NumberFormatException err) {
			JOptionPane.showMessageDialog(this, KlangConstants.KLANGEDITOR_RAWEDIT_NUMERR, KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		} catch (IOException err2) {
			JOptionPane.showMessageDialog(this, KlangConstants.ERROR_GENERAL_FILE, KlangConstants.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
	
}
