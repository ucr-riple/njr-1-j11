package units.upgrades;

import java.util.ArrayList;

import units.interactions.Interaction;
import achiever.Achiever;
import attribute.Attribute;

/**
 * The interface shared by Units and UnitDecorators in 
 * order to make the decorator pattern possible
 * @author Alex Mariakakis
 *
 */
public interface UnitUpgradable extends Upgradable {

    public Attribute getAttribute(String attr);
    public ArrayList<Interaction> getInteractionList();
    public Achiever getOwner();
}
