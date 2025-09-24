package rivercityransom.test;

import rivercityransom.contracts.PersonnageContract;
import rivercityransom.impl.PersonnageImpl;

public class PersonnageTestContract extends AbstractPersonnageTest {

	@Override
	public void beforeTests() {
		setPersonnage(new PersonnageContract(new PersonnageImpl()));
	}
}
