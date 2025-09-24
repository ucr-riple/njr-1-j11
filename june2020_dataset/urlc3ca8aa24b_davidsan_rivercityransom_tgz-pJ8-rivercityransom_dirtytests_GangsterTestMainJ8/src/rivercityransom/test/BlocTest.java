package rivercityransom.test;

import rivercityransom.impl.BlocImpl;
import rivercityransom.impl.ObjetImpl;

public class BlocTest extends AbstractBlocTest {

	@Override
	public void beforeTests() {
		setBloc(new BlocImpl());
		setObjet(new ObjetImpl());
	}
}
