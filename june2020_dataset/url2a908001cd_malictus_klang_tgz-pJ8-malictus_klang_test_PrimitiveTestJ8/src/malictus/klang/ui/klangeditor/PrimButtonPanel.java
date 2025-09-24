/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;
import java.awt.*;
import malictus.klang.*;
import malictus.klang.chunk.*;
import malictus.klang.file.*;

/**
 * This panel displays the general chunk edit buttons for a chunk
 * @author Jim Halliday
 */
public class PrimButtonPanel extends JPanel {
	
	private JButton btnExport;
	private JButton btnDelete;
	private JButton btnEditName;
	private JButton btnAdd;
	private PrimPanel parent;
	
	/**
	 * Initiate the primitive button panel
	 * @param parent the parent panel to this subpanel
	 */
	public PrimButtonPanel(PrimPanel parent) {
		super();
		this.parent = parent;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		btnExport = new JButton(KlangConstants.KLANGEDITOR_CHUNKEDIT_EXPORT);
		btnExport.setEnabled(false);
		btnExport.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doExport();
			}
		});
		this.add(btnExport);
		this.add(Box.createRigidArea(new Dimension(10,1)));
		btnDelete = new JButton(KlangConstants.KLANGEDITOR_CHUNKEDIT_DELETE);
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doDelete();
			}
		});
		this.add(btnDelete);
		this.add(Box.createRigidArea(new Dimension(10,1)));
		btnEditName = new JButton(KlangConstants.KLANGEDITOR_CHUNKEDIT_EDITNAME);
		btnEditName.setEnabled(false);
		btnEditName.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doEditName();
			}
		});
		this.add(btnEditName);
		this.add(Box.createRigidArea(new Dimension(10,1)));
		btnAdd = new JButton(KlangConstants.KLANGEDITOR_CHUNKEDIT_ADD);
		btnAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				doAddChunk();
			}
		});
		btnAdd.setEnabled(false);
		this.add(btnAdd);		
		if (this.parent.getKlangParent().getCurrentChunk() == null) {
			btnExport.setEnabled(false);
		} else {
			btnExport.setEnabled(true);
		}
		btnDelete.setEnabled(false);
		if ((this.parent.getKlangParent().getCurrentChunk() != null) && (!(this.parent.getKlangParent().isReadOnlyMode()))) {
			if (this.parent.getKlangParent().getCurrentChunk().chunkCanBeDeleted()) {
				btnDelete.setEnabled(true);
			} else {
				btnDelete.setEnabled(false);
			}
		}
		btnEditName.setEnabled(false);
		if ((this.parent.getKlangParent().getCurrentChunk() != null) && (!(this.parent.getKlangParent().isReadOnlyMode()))) {
			if (this.parent.getKlangParent().getCurrentChunk().chunkNameCanBeAltered()) {
				btnEditName.setEnabled(true);
			} else {
				btnEditName.setEnabled(false);
			}
		}
		btnAdd.setEnabled(false);
		if ((this.parent.getKlangParent().getCurrentChunk() != null) && (!(this.parent.getKlangParent().isReadOnlyMode()))) {
			Chunk chu = this.parent.getKlangParent().getCurrentChunk();
			//several cases where we could be able to add chunks; we'll enable the button if any of them is true
			if (chu instanceof EditableContainerChunk) {
				//first, if this chunk itself is an editable container chunk (so that chunks could be added under this)
				btnAdd.setEnabled(true);
			} else if (chu.getParentChunk() != null) {
				//second, if the immediate parent of this chunk is an editable container chunk (so chunks could be added before or after)
				if (chu.getParentChunk() instanceof EditableContainerChunk) {
					btnAdd.setEnabled(true);
				}
			} else if (chu.getParentChunk() == null) {
				//third, parent is file itself, and we are able to add chunks at the highest level
				if (chu.getParentFile() instanceof EditableFileBase) {
					EditableFileBase efb = (EditableFileBase) chu.getParentFile();
					if (efb.canAddTopLevelChunks()) {
						btnAdd.setEnabled(true);
					}
				}
			}
		}
	}
	
	/**
	 * Tell parent the export button was pressed
	 */
	private void doExport() {
		parent.getKlangParent().exportCurrentChunk();
	}
	
	/**
	 * Tell parent the delete button was pressed
	 */
	private void doDelete() {
		parent.getKlangParent().deleteCurrentChunk();
	}
	
	/**
	 * Tell parent to edit the current chunk's name
	 */
	private void doEditName() {
		parent.getKlangParent().editCurrentChunkName();
	}
	
	/**
	 * Tell parent to add a new chunk
	 */
	private void doAddChunk() {
		parent.getKlangParent().addNewChunk();
	}


}
