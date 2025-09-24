/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;

import java.awt.*;

import malictus.klang.primitives.*;
import malictus.klang.ui.*;
import malictus.klang.*;

/**
 * This panel represents the UI for a single primitive value -- currently, just a label
 * and an 'edit' and 'go' button
 * @author Jim Halliday
 */
public class PrimSubPanel extends JPanel {
	
	private KlangLabel lblPanelName;
	private PrimPanel parent;
	private PrimitiveData prim;
	private JButton btnEdit;
	private JButton btnJump;
	private int position;
	
	/**
	 * Initiate the primitve sub-panel
	 * @param parent the parent panel to this subpanel
	 * @param prim the PrimitiveData object that this panel represents
	 * @param position the position of this primitive within the primitive vector
	 */
	public PrimSubPanel(PrimPanel parent, PrimitiveData prim, int position) {
		super();
		this.parent = parent;
		this.prim = prim;
		this.position = position;
		
		String x = prim.getDescription() + ": ";
		if (prim.getPrimitive().valueExists()) {
			try {
				if ((prim.getPrimitive() instanceof PrimitiveInt) && (parent.getKlangParent().isHexMode())) {
					x = x + KlangUtil.convertToHex(((PrimitiveInt)prim.getPrimitive()).getValueAsLong());
				} else {
					String truncate = prim.getPrimitive().writeValueToString();
					if (prim.getPrimitive().writeValueToString().length() > 60) {
						truncate = truncate.substring(0, 56) + "...";
					}
					x = x + truncate;
				}				
				if (prim.getCurrentValueString() != null) {
					x = x + " (" + prim.getCurrentValueString() + ")";
				}
			} catch (BadValueException err) {
				x = prim.getDescription() + ": ";
			}
		}
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		f.setHgap(3);
		f.setVgap(2);
		this.setLayout(f);
		this.setBorder(null);
		lblPanelName = new KlangLabel(x);		
		
		btnEdit = new JButton(KlangConstants.KLANGEDITOR_PRIMSUB_EDIT);
		btnEdit.setMargin(new Insets(1,3,1,3));
		btnEdit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doEdit();
			}
		});
		this.add(btnEdit);
		btnJump = new JButton(KlangConstants.KLANGEDITOR_PRIMSUB_JUMPTO);
		btnJump.setMargin(new Insets(1,3,1,3));
		btnJump.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doJump();
			}
		});
		if (this.prim.getStartBytePosition() == null) {
			btnJump.setEnabled(false);
		} else if (this.prim.getStartBytePosition() < 0) {
			btnJump.setEnabled(false);
		}
		this.add(btnJump);
		this.add(lblPanelName);
		redoButton();
	}
	
	/**
	 * Return the position of the this primitive within the overall primitive vector
	 * @return the position of the this primitive within the overall primitive vector
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * Disable the edit button if in read-only mode
	 */
	protected void redoButton() {
		if (parent.getKlangParent().isReadOnlyMode()) {
			this.btnEdit.setEnabled(false);
		} else {
			this.btnEdit.setEnabled(true);
		}
	}
	
	/**
	 * Retrieval method for primitive that this represents
	 * @return the PrimitiveData object that this panel represents
	 */
	protected PrimitiveData getPrim() {
		return prim;
	}
	
	/**
	 * Make this label bold, to represent that the current byte position is here.
	 * @param value true to bold the label, and false otherwise
	 */
	protected void setBold(boolean value) {
		Font bold = new Font(this.getFont().getFontName(), Font.BOLD, this.getFont().getSize());
		Font plain = new Font(this.getFont().getFontName(), Font.PLAIN, this.getFont().getSize());
		if (value) {
			this.lblPanelName.setFont(bold);
		} else {
			this.lblPanelName.setFont(plain);
		}
	}
	
	/**
	 * Edit button pressed. Call the parent to open up an editing window for this primitive.
	 */
	private void doEdit() {
		parent.editPrim(prim, position);
	}
	
	/**
	 * Jump button pressed. Call the parent to change navigation to this point.
	 */
	private void doJump() {
		parent.jumpToPrim(prim);
	}

}
