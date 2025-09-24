package units.upgrades;

import java.util.ArrayList;

/**
 * Fundamental unit of UpgradeTree, holds a UnitDecorator 
 * for upgrades
 * @author Alex Mariakakis
 *
 */
public class UpgradeNode implements java.io.Serializable {

    private UpgradeNode parent;
    private ArrayList<UpgradeNode> children;
    private boolean available;
    private UnitDecorator decorator;
    
    public UpgradeNode(UnitDecorator dec) {
    	parent = null;
    	children = new ArrayList<UpgradeNode>();
    	available = false;
    	decorator = dec;
    }
    
	public ArrayList<UpgradeNode> getChildren() {
	    return children;
    }

	public UpgradeNode getParent() {
	    return parent;
    }

	public void setParent(UpgradeNode parent) {
	    this.parent = parent;
    }

	public void setChildren(ArrayList<UpgradeNode> children) {
	    this.children = children;
    }

	public boolean isAvailable() {
	    return available;
    }

	public void setAvailable(boolean available) {
	    this.available = available;
    }

	/**
	 * Activates an upgrade if it is possible, applies the charge, 
	 * and unlocks future upgrades
	 */
	public void modify() {
		// Apply decorator if possible
		if (decorator.checkCost() && available) {
			decorator.modify();
			decorator.applyCost();
			
			// Unlock its children
			for (UpgradeNode node: children) {
				node.setAvailable(true);
			}
		}
	}
}