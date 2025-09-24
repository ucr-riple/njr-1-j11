package achiever;

import achiever.upgrades.AchieverUpgradable;
import attribute.Attribute;

/**
 * Interface that must be implemented by anything that can be affected by Achievements
 * (i.e. Players, Units, LevelMap)
 * @author aks
 *
 */
public interface Achiever extends AchieverUpgradable {

    public Attribute getAttribute(String name);
    public void addAttribute(Attribute attribute);
    public void removeAttribute(Attribute attribute);
}
