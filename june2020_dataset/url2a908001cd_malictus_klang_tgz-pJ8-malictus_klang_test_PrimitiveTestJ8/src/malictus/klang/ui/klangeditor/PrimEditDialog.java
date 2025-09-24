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
import java.util.*;
import malictus.klang.KlangConstants;
import malictus.klang.KlangUtil;
import malictus.klang.primitives.*;
import malictus.klang.ui.*;

/**
 * A PrimEditDialog is a dialog box for editing a single PrimitiveData value.
 * @author Jim Halliday
 */
public class PrimEditDialog extends JDialog {
	
	JScrollPane scrPane;
	JPanel pnlContent;
	JPanel pnlValue;
	JPanel pnlEncoding;
	JPanel pnlOKCancel;
	
	JButton btnOK;
	JButton btnCancel;
	KlangLabel lblName;
	KlangLabel lblType;
	KlangLabel lblValue;
	KlangLabel lblEnc;
	KlangTextField txtfValue;
	KlangTextArea txtaValue;
	JComboBox combEncoding;
	JComboBox combValue;
	
	private String origString = "";
	private String origEncoding = "";
	private boolean canceled = false;
	PrimitiveData origPrim;
	
	Vector<String> localVals = new Vector<String>();
	
	public PrimEditDialog(PrimitiveData data, KlangEditor parent) {
		super(parent);
		boolean isInt = false;
		if (data.getPrimitive() instanceof PrimitiveInt) {
			isInt = true;
		}
		origPrim = data;
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setResizable(true);
		this.setTitle(KlangConstants.KLANGEDITOR_PRIMDIALOG_TITLE + " " + data.getDescription());
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		pnlContent = new JPanel();
		pnlContent.setBorder( new EmptyBorder(11,11,11,11) );
		pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
		pnlValue = new JPanel();
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		pnlValue.setLayout(fl);
		
		pnlEncoding = new JPanel();
		FlowLayout f4 = new FlowLayout();
		f4.setAlignment(FlowLayout.LEFT);
		pnlEncoding.setLayout(f4);
		
		scrPane = new JScrollPane();
		scrPane.setViewportView(pnlContent);
		this.getContentPane().add(scrPane);
		pnlOKCancel = new JPanel();
		FlowLayout f2 = new FlowLayout();
		f2.setAlignment(FlowLayout.RIGHT);
		pnlOKCancel.setLayout(f2);
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
		/* POPULATE VALUES */
		lblName = new KlangLabel(KlangConstants.KLANGEDITOR_PRIMDIALOG_PRIMNAME + ": " + data.getDescription());
		lblType = new KlangLabel(KlangConstants.KLANGEDITOR_PRIMDIALOG_PRIMTYPE + ": " + data.getPrimitive().getPrimitiveDescription());
		lblValue = new KlangLabel(KlangConstants.KLANGEDITOR_PRIMDIALOG_PRIMVAL + ": ");
		String value = "";
		String valueAsHex = "";
		if (data.getPrimitive().valueExists()) {
			try {
				value = data.getPrimitive().writeValueToString();
				if (parent.isHexMode() && isInt) {
					valueAsHex = KlangUtil.convertToHex(((PrimitiveInt)data.getPrimitive()).getValueAsLong());
				}
			} catch (BadValueException err) {
				value = "";
				valueAsHex = "";
			}
		}
		if (data.getValueStrings() != null) {
			this.combValue = new JComboBox();
			this.combValue.setEditable(true);
			Set<String> keys = data.getValueStrings().keySet();
			Iterator<String> i = keys.iterator();
			while (i.hasNext()) {
				String key = i.next();
				if (parent.isHexMode() && isInt) {
					Long lo = new Long(key);
					combValue.addItem(KlangUtil.convertToHex(lo.longValue()) + " (" + data.getValueStrings().get(key) + ")");
				} else {
					combValue.addItem(key + " (" + data.getValueStrings().get(key) + ")");
				}
				//not the best or most elegant way to do this!
				localVals.add(key);
			}
			if (data.getValueStrings().get(value) == null) {
				if (parent.isHexMode() && isInt) {
					combValue.setSelectedItem(valueAsHex);
				} else {
					combValue.setSelectedItem(value);
				}
			} else {
				combValue.setSelectedIndex(localVals.indexOf(value));
			}
		}
		
		if (parent.isHexMode() && isInt) {
			txtfValue = new KlangTextField(valueAsHex, 200);
		} else {
			if (isInt || (data.getPrimitive() instanceof PrimitiveFixedByte)) {
				txtfValue = new KlangTextField(value, 200);
			} else {
				txtaValue = new KlangTextArea(value);
				if (data.getPrimitive() instanceof StringPrim) {
					StringPrim sp = (StringPrim)data.getPrimitive();
					if (sp.canChangeEncoding()) {
						combEncoding = new JComboBox();
						combEncoding.setEditable(true);
						Vector<String> encs = KlangUtil.getCommonTextEncodings();
						int counter = 0;
						while (counter < encs.size()) {
							combEncoding.addItem(encs.get(counter));
							counter = counter + 1;
						}
						combEncoding.setSelectedItem(sp.getTextEncoding());
						origEncoding = sp.getTextEncoding();
					}
				}
			}
		}
		origString = value;
		pnlValue.add(lblValue);
		
		pnlContent.add(lblName);
		pnlContent.add(lblType);
		if (txtfValue != null) {
			pnlContent.add(pnlValue);
		}
		if (combValue == null) {
			if (txtfValue == null) {
				pnlContent.add(txtaValue);
				if (combEncoding != null) {
					lblEnc = new KlangLabel(KlangConstants.KLANGEDITOR_PRIMDIALOG_TEXTENC);
					pnlEncoding.add(lblEnc);
					pnlEncoding.add(combEncoding);
					pnlContent.add(pnlEncoding);
				}
			} else {
				pnlValue.add(txtfValue);
			}
		} else {
			pnlContent.add(combValue);
		}
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
	
	/**
	 * Tell whether the user has actually edited the string value or not
	 * @return true if the original string value has been altered, and false otherwise
	 */
	public boolean valueChanged() {
		if (!(origEncoding.equals(""))) {
			String newEncoding = (String)combEncoding.getSelectedItem();
			if (!(newEncoding.equals(origEncoding))) {
				return true;
			}
		}
		if (combValue == null) {
			if (txtfValue == null) {
				if (!origString.equals(txtaValue.getText())) {
					return true;
				} else {
					return false;
				}
			} else {
				if (!origString.equals(txtfValue.getText())) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			String newval = "";
			if (combValue.getSelectedIndex() < 0) {
				newval = (String)combValue.getSelectedItem();
			} else {
				newval = localVals.elementAt(combValue.getSelectedIndex());
			}
			if (!origString.equals(newval)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Retrieval method for the selected text encoding
	 * @return the new text encoding
	 */
	public String getNewEncoding() {
		if (combEncoding == null) {
			return null;
		}
		return (String)combEncoding.getSelectedItem();
	}
	
	/**
	 * This method returns the new string value
	 * @return the edited string value
	 */
	public String getNewValue() {
		if (this.combValue == null) {
			if (txtfValue == null) {
				return txtaValue.getText();
			}
			return txtfValue.getText();
		} else {
			if (this.combValue.getSelectedIndex() < 0) {
				//grab whatever was typed
				return (String)combValue.getSelectedItem();	
			} else {
				//grab the appropriate value from the vector
				return localVals.get(combValue.getSelectedIndex());
			}
		}
	}

}
