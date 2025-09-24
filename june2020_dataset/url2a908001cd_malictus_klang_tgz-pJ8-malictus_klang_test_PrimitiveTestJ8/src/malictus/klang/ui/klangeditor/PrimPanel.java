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
import java.awt.*;

import malictus.klang.primitives.*;
import malictus.klang.ui.TextEncodingDialog;
import malictus.klang.KlangConstants;
import malictus.klang.chunk.*;

/**
 * The panel displays the actual known data inside a chunk.
 * @author Jim Halliday
 */
public class PrimPanel extends JScrollPane {
	
	private KlangEditor parent;
	private JPanel iPanel;
	private PrimButtonPanel btnPanel;
	private Chunk curChunk = null;
	
	/**
	 * Initiate the basic info panel
	 * @param parent the KlangEditor UI object
	 */
	PrimPanel(KlangEditor parent) {
		this.parent = parent;
		updatePanel(true);
    }
	
	/**
	 * Retrieval method for parent window
	 * @return the KlangEditor window
	 */
	protected KlangEditor getKlangParent() {
		return parent;
	}
	
	/**
	 * Called whenever this panel should be updated to reflect the current file
	 * @param force if true, force the panel to update, even if chunk hasn't changed
	 */
	protected void updatePanel(boolean force) {
		boolean redo = force;
		if ((parent.getCurrentChunk() != curChunk) || (parent.getCurrentChunk() == null)) {
			redo = true;
		}
		if (redo) {
			this.curChunk = parent.getCurrentChunk();
			//redo panel entirely, since things may have changed
			iPanel = new JPanel();
			iPanel.setLayout(new BoxLayout(iPanel, BoxLayout.Y_AXIS));
			this.setViewportView(iPanel);
			btnPanel = new PrimButtonPanel(this);
			iPanel.add(btnPanel);
			if (parent.getCurrentChunk() != null) {
				addLabelsFor(parent.getCurrentChunk().getPrimitiveData());
			}
			iPanel.setBorder( new EmptyBorder(7,7,7,7) );
			TitledBorder tb = BorderFactory.createTitledBorder(KlangConstants.KLANGEDITOR_CHUNKPRIM_PANELNAME);
			tb.setTitleFont(KlangConstants.KLANGEDITOR_BORDERTITLE_FONT);
			this.setBorder(tb);
		}
		boldAppropriatePrim();
	}
	
	/**
	 * Called by the sub-panels when an edit button pressed. Open up a dialog to edit
	 * this value, then reparse the file.
	 * @param prim the primitive to reparse
	 * @param position the primitive's position
	 */
	protected void editPrim(PrimitiveData prim, int position) {
		if (!parent.getCurrentChunk().getPrimitiveData().contains(prim)) {
			JOptionPane.showMessageDialog(this, KlangConstants.ERROR_NO_SUCH_PRIM, KlangConstants.ERROR_NO_SUCH_PRIM, JOptionPane.ERROR_MESSAGE);
			return;
		}
		PrimEditDialog pep = new PrimEditDialog(prim, this.getKlangParent());
		if (pep.wasCanceled()) {
			return;
		}
		if (!pep.valueChanged()) {
			return;
		}
		try {
			//try to update the primitive value
			parent.getCurrentChunk().getPrimitiveData().get(position).getPrimitive().setValueFromString(pep.getNewValue());
			if (parent.getCurrentChunk().getPrimitiveData().get(position).getPrimitive() instanceof StringPrim) {
				if (((StringPrim)parent.getCurrentChunk().getPrimitiveData().get(position).getPrimitive()).canChangeEncoding()) {
					((StringPrim)parent.getCurrentChunk().getPrimitiveData().get(position).getPrimitive()).setTextEncoding(pep.getNewEncoding());
					TextEncodingDialog.currentEncoding = pep.getNewEncoding();
					if (parent.getCurrentChunk() instanceof PlainTextChunk) {
						PlainTextChunk pt = (PlainTextChunk)parent.getCurrentChunk();
						pt.setTextEncoding(pep.getNewEncoding());
					}
				}
			}
		} catch (BadValueException err) {
			JOptionPane.showMessageDialog(parent, err.getMessage(), KlangConstants.ERROR_BAD_VALUE_EXCEPTION, JOptionPane.ERROR_MESSAGE);
			return;
		}
		//update file itself with the change
		parent.reparseCurrentChunk();
	}
	
	/**
	 * Called by the sub panels when the jump button is pressed. Sends the message on up to
	 * the parent window.
	 * @param prim the PrimitiveData object to jump to
	 */
	protected void jumpToPrim(PrimitiveData prim) {
		parent.jumpToPrim(prim);
		boldAppropriatePrim();
	}

	/**
	 * Add additional label panels for each primitivedata object that the file has
	 * @param pd the vector of PrimitiveData objects
	 */
	private void addLabelsFor(Vector<PrimitiveData> pd) {
		int counter = 0;
		while (counter < pd.size()) {
			PrimitiveData prim = pd.get(counter);
			PrimSubPanel sub = new PrimSubPanel(this, prim, counter);
			iPanel.add(sub);
			counter = counter + 1;
		}
	}
	
	/**
	 * Figure out which primitive data element should be bolded (if any); also
	 * scroll panel to show bolded value
	 */
	private void boldAppropriatePrim() {
		if (iPanel == null) {
			return;
		}
		Component[] components = iPanel.getComponents();
		int counter = 0;
		while (counter < components.length) {
			if (components[counter] instanceof PrimSubPanel) {
				PrimSubPanel x = (PrimSubPanel)components[counter];
				if (x.getPrim().getStartBytePosition() == null) {
					x.setBold(false);
				} else {
					if ((x.getPrim().getStartBytePosition() <= this.parent.currentByte) && (x.getPrim().getEndBytePosition() > this.parent.currentByte)) {
						x.setBold(true);
						final PrimSubPanel X = x;
						Runnable updateIt = new Runnable() {
						     public void run() {
						    	 getViewport().scrollRectToVisible(new Rectangle(X.getX(), X.getY(), 5, X.getHeight()));
						     }
						};
						SwingUtilities.invokeLater(updateIt);
					} else {
						x.setBold(false);
					}
				}
			}
			counter = counter + 1;
		}
	}
	
}
