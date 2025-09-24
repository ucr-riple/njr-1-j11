package rivercityransom.test;

import rivercityransom.impl.ObjetImpl;

public class ObjetTest extends AbstractObjetTest {

	@Override
	public void beforeTests() {
		setObjet(new ObjetImpl());
	}
}
