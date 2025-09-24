package rivercityransom.test;

import rivercityransom.contracts.ObjetContract;
import rivercityransom.impl.ObjetImpl;

public class ObjetTestContract extends AbstractObjetTest {

	@Override
	public void beforeTests() {
		setObjet(new ObjetContract(new ObjetImpl()));
	}
}
