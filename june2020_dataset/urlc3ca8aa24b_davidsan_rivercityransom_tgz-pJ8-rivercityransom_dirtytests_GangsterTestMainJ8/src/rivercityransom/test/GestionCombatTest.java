package rivercityransom.test;

import rivercityransom.impl.GestionCombatImpl;

public class GestionCombatTest extends AbstractGestionCombatTest {

	@Override
	public void beforeTests() {
		setGestionCombat(new GestionCombatImpl());
	}
}