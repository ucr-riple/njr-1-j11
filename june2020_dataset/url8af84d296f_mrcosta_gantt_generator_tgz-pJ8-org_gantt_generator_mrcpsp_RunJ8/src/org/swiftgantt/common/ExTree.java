package org.swiftgantt.common;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ExTree extends JScrollPane {
	/**
	 * This is a scrollable tree.
	 */
	private static final long serialVersionUID = 1L;

	private JTree tree = null;

	//    private DefaultTreeModel treeModel = null;  //  ?

	//    private DefaultMutableTreeNode rootNode = null; //?

	private DefaultMutableTreeNode selectedNode = null; //  @jve:decl-index=0:

	public ExTree() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(478, 198));
		this.setViewportView(getTree());
		//        this.tree.setRootVisible(false);
	}

	/**
	 * Expand all the tree nodes.
	 * 
	 * @param rootNode
	 */
	public void expandAllTreeNode(DefaultMutableTreeNode rootNode) {
		for (int i = 0; i < rootNode.getChildCount(); i++) {
			DefaultMutableTreeNode curNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
			TreePath path = new TreePath(curNode.getPath());
			tree.expandPath(path);
			expandAllTreeNode(curNode);
		}
	}

	public void expandTreeNode(DefaultMutableTreeNode node) {
		TreePath nodePath = new TreePath(node.getPath());
		tree.expandPath(nodePath);
	}

	public void select(DefaultMutableTreeNode treeNode) {
		this.setSelectedNode(treeNode);
		TreePath path = null;
		if (treeNode != null) {
			path = new TreePath(treeNode.getPath());
		} else {
			path = new TreePath(this.getTreeModel().getRoot());
		}
		this.getTree().setSelectionPath(path);
	}

	/**
	 * This method initializes tree
	 * 
	 * @return javax.swing.JTree
	 */
	protected JTree getTree() {
		if (tree == null) {
			tree = new JTree();
			tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
				public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
					selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				}
			});
		}
		return tree;
	}

	/**
	 * 
	 * @param selectedNode
	 */
	protected void fireTreeSelection(TreePath selectedNode) {
		Object[] listeners = listenerList.getListenerList();
		TreeSelectionEvent e = null;
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeSelectionListener.class) {
				if (e == null)
					e = new TreeSelectionEvent(this, selectedNode, true, null, null);
				((TreeSelectionListener) listeners[i + 1]).valueChanged(e);
			}
		}
	}

	public TreeModel getTreeModel() {
		return this.tree.getModel();
	}

	public void setTreeModel(DefaultTreeModel treeModel) {
		this.tree.setModel(treeModel);
	}

	public DefaultMutableTreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(DefaultMutableTreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
