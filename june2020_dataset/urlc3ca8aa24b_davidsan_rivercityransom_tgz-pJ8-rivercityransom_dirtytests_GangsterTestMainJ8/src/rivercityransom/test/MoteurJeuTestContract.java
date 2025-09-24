package rivercityransom.test;

import rivercityransom.contracts.MoteurJeuContract;
import rivercityransom.impl.MoteurJeuImpl;

public class MoteurJeuTestContract extends AbstractMoteurJeuTest{
	@Override
	public void beforeTests() {
		setMoteurJeu(new MoteurJeuContract(new MoteurJeuImpl()));
	}
}
