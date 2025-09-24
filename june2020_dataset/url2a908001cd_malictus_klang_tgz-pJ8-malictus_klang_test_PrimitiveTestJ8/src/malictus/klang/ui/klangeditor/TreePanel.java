/**
 * This file is distributed under a BSD-style license. See the included LICENSE.txt file
 * for more information. 
 * Copyright (c) 2009, James Halliday
 * All rights reserved.
 */
package malictus.klang.ui.klangeditor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.*;
import java.util.*;

import malictus.klang.KlangConstants;
import malictus.klang.chunk.*;

/**
 * The tree panel displays the file/chunk tree.
 * @author Jim Halliday
 */
public class TreePanel extends JScrollPane {
	
	private KlangEditor parent;
	private JTree tree;
	private boolean dontTrigger = false;
	
	/**
	 * Initiate the tree panel
	 * @param parent the KlangEditor UI object
	 */
	TreePanel(KlangEditor parent) {
		this.parent = parent;
		tree = new JTree();
    	DefaultMutableTreeNode fakeNode = new DefaultMutableTreeNode();
		tree = new JTree(fakeNode);
		tree.setEditable(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		tree.setExpandsSelectedPaths(true);
		tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                doTreeChange();
            }
        });
    	this.setViewportView(tree);
    	TitledBorder tb = BorderFactory.createTitledBorder(KlangConstants.KLANGEDITOR_TREEVIEW_PANELNAME);
		tb.setTitleFont(KlangConstants.KLANGEDITOR_BORDERTITLE_FONT);
		this.setBorder(tb);
		updatePanel();
    }
	
	/**
	 * Called whenever this panel should be updated to reflect the current file
	 */
	protected void updatePanel() {
		if (this.parent.getCurrentFile() == null) {
			DefaultMutableTreeNode fakeNode = new DefaultMutableTreeNode();
			DefaultTreeModel model = new DefaultTreeModel(fakeNode);
	    	tree.setModel(model);
			tree.setEnabled(false);
		} else {
			DefaultMutableTreeNode parent = new DefaultMutableTreeNode(this.parent.getCurrentFile().getFile().getName());
			buildNodes(parent, this.parent.getCurrentFile().getChunks());
			DefaultTreeModel model = new DefaultTreeModel(parent);
	    	tree.setModel(model);
			tree.setEnabled(true);
		}
	}
	
	/**
	 * Called when the tree node should move programmatically to a new node (without triggering any other UI changes).
	 */
	protected void updatePanelToNewByte() {
		dontTrigger = true;
		Enumeration<DefaultMutableTreeNode> numer = ((DefaultMutableTreeNode)tree.getModel().getRoot()).depthFirstEnumeration();
		while (numer.hasMoreElements()) {
			DefaultMutableTreeNode node = numer.nextElement();
			if (node.getUserObject() instanceof Chunk) {
				Chunk z = (Chunk)node.getUserObject();
				if (z.equals(parent.getCurrentChunk())) {
					tree.setSelectionPath(new TreePath(node.getPath()));
					break;
				}
			}
		}
		dontTrigger = false;
	}
	
	/**
	 * Recursively build up the chunk tree
	 * @param parent current parent node
	 * @param chunks vector of chunk objects that should be added to this node
	 */
	private void buildNodes(DefaultMutableTreeNode parent, Vector<Chunk> chunks) {
		int counter = 0;
		while (counter < chunks.size()) {
			Chunk x = chunks.get(counter);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(x);
			parent.add(node);
			if (x instanceof ContainerChunk) {
				ContainerChunk xx = (ContainerChunk)x;
				buildNodes(node, xx.getSubChunks());
			}
			counter = counter + 1;
		}
	}
	
	/**
	 * Called when the tree selection changes. Updates the parent window to reflect the new chunk.
	 */
	private void doTreeChange() {
		if (dontTrigger) {
			return;
		}
		if ( (tree == null) || (tree.getSelectionPath() == null) || (!tree.isEnabled()) ) {
			parent.curChunk = null;
			if ((parent.getCurrentFile() == null) || (parent.getCurrentFile().getFile().length() == 0)) {
				parent.currentByte = -1;
			} else {
				parent.currentByte = 0;
			}
    	} else {
    		TreePath x = tree.getSelectionPath();
        	Object obj = ((DefaultMutableTreeNode)x.getLastPathComponent()).getUserObject();
        	if (!(obj instanceof Chunk)) {
        		parent.curChunk = null;
        		if ((parent.getCurrentFile() == null) || (parent.getCurrentFile().getFile().length() == 0)) {
    				parent.currentByte = -1;
    			} else {
    				parent.currentByte = 0;
    			}
        	} else {
	        	Chunk chunk = (Chunk)obj;
	        	parent.curChunk = chunk;
	        	parent.currentByte = chunk.getStartPosition();
        	}
    	}
		parent.doTreePanelChange();
	}
	
}
