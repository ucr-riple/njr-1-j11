package units.upgrades;

import java.util.ArrayList;

/**
 * The tree structure that holds all of the upgrades 
 * a unit can access over the course of a game, holds many 
 * UpgradeNodes
 * @author Alex Mariakakis
 *
 */
public class UpgradeTree implements java.io.Serializable {

	private UpgradeNode root;
	private ArrayList<UpgradeNode> nodes;

	public UpgradeTree() {
		this.setRoot(new UpgradeNode(null));
		getRoot().setAvailable(true);
		nodes = new ArrayList<UpgradeNode>();
		nodes.add(getRoot());
	}

	public UpgradeNode getRoot() {
	    return root;
    }

	public void setRoot(UpgradeNode root) {
	    this.root = root;
    }
	
	/**
	 * Adds a leaf to the tree
	 */
	public void addLeaf(UpgradeNode parent, UpgradeNode leaf) {
		if (nodes.contains(parent)) {
			if (parent.equals(getRoot())) {
				// Makes nodes right after the root automatically available
				leaf.setAvailable(true);
			}
			leaf.setParent(parent);
			parent.getChildren().add(leaf);
			nodes.add(leaf);
		}
	}
	
}
