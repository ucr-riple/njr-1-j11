package AMath;

import Shirage.Shire;

public class TestShire extends Shire {

	private TestRealm parentRealm;
	
	public TestShire(int x, int y, TestRealm testRealm) {
		super(x, y);
		this.parentRealm = testRealm;
	}
	
	@Override
	public Shire getSomeNeighbor() {
		return parentRealm.getSomeNeighborOf(this);
	}

}
