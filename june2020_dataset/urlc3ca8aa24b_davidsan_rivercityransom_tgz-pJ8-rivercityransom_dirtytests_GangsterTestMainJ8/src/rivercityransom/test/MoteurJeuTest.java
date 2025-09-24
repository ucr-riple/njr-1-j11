package rivercityransom.test;

import rivercityransom.impl.MoteurJeuImpl;

public class MoteurJeuTest extends AbstractMoteurJeuTest{

	@Override
	public void beforeTests() {
		setMoteurJeu(new MoteurJeuImpl());
	}
}
