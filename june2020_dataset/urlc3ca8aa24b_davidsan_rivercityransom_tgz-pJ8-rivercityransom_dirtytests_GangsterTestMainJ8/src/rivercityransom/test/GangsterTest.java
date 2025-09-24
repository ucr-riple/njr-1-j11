package rivercityransom.test;

import rivercityransom.impl.GangsterImpl;

public class GangsterTest extends AbstractGangsterTest {
	@Override
	public void beforeTests() {
		setGangster(new GangsterImpl());
	}
}
