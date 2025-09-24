package units.upgrades;

import java.util.ArrayList;

import units.interactions.Interaction;

/**
 * A modification that changes a unit's interactions
 * @author Alex Mariakakis
 *
 */
abstract public class InteractionModification extends UnitDecorator{

	private ArrayList<Interaction> interaction;
	
	public InteractionModification(UnitUpgradable unit, int cost) {
	    super(unit, cost);
	    interaction = this.getDecoratedUnit().getInteractionList();
    }

	public ArrayList<Interaction> getInteraction() {
	    return interaction;
    }

	public void setInteraction(ArrayList<Interaction> inter) {
	    this.interaction = inter;
    }

}
