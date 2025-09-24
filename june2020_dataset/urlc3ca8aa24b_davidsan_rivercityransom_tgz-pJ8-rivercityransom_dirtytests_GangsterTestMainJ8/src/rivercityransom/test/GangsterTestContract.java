package rivercityransom.test;

import rivercityransom.contracts.GangsterContract;
import rivercityransom.impl.GangsterImpl;

public class GangsterTestContract extends AbstractGangsterTest {
	@Override
	public void beforeTests() {
		setGangster(new GangsterContract(new GangsterImpl()));
	}
}
