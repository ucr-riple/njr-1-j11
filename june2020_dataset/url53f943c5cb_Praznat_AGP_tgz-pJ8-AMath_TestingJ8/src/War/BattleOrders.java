package War;

import War.CombatDefs.FormationType;

public class BattleOrders {

	// attack general
	// attack isolated groups
	// stick together
	// spread out
	// attack ranged
	// attack unarmored
	// defend ranged
	// evade cavalry
	// defend general
	
	
	// evade, protect, attack
	
	private static class SimpleOrder {
		FormationType.Part target; //defend if friendly, attack if hostile
		
	}
	
	private static class SubOrder {
		
	}
	private static SubOrder Evade() {
		return new SubOrder();
	}
}
