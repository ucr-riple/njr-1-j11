package achiever.upgrades;

import units.upgrades.Upgradable;
import attribute.Attribute;

/**
 * Interface that can be used by Achiever implementation to upgrade Achiever attribtues
 * @author aks
 *
 */
public interface AchieverUpgradable extends Upgradable {

    public Attribute getAttribute(String attr);
    
}
