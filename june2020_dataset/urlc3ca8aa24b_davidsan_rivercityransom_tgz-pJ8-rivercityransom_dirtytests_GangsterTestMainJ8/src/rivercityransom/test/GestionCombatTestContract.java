package rivercityransom.test;

import rivercityransom.contracts.GestionCombatContract;
import rivercityransom.impl.GestionCombatImpl;

public class GestionCombatTestContract extends AbstractGestionCombatTest{
	@Override
	public void beforeTests() {
		setGestionCombat(new GestionCombatContract(new GestionCombatImpl()));
	}
}