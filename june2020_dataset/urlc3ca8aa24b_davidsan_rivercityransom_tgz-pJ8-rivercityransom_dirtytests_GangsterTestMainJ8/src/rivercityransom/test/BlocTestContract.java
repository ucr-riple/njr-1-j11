package rivercityransom.test;

import rivercityransom.contracts.BlocContract;
import rivercityransom.contracts.ObjetContract;
import rivercityransom.impl.BlocImpl;
import rivercityransom.impl.ObjetImpl;

public class BlocTestContract extends AbstractBlocTest {

	@Override
	public void beforeTests() {
		setBloc(new BlocContract(new BlocImpl()));
		setObjet(new ObjetContract(new ObjetImpl()));
	}
}
