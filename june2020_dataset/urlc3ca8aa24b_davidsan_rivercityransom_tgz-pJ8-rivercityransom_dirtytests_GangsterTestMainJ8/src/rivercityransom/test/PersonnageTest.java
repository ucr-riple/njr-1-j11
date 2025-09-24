package rivercityransom.test;

import rivercityransom.impl.PersonnageImpl;

public class PersonnageTest extends AbstractPersonnageTest {

	@Override
	public void beforeTests() {
		setPersonnage(new PersonnageImpl());
	}
}
