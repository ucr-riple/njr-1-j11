package attribute;

import units.Unit;
import units.upgrades.UpgradeTree;

/**
 * A unit attribute that holds an upgrade tree for easy access
 * @author Alex Mariakakis
 *
 */
public class AttributeUpgrades extends Attribute {
	
    public AttributeUpgrades(Unit owner) {
        super(owner);
    }

    private UpgradeTree myTree = new UpgradeTree();
	
	@Override
    public void refresh() {
    }
	
	public UpgradeTree getMyTree() {
		return myTree;
	}
	
	public void setMyTree(UpgradeTree tree) {
		myTree = tree;
	}

    @Override
    public String name() {
        return "Upgrades";
    }

    @Override
    public void augmentDataTemplate(Object dataElement) {
        
    }
}
