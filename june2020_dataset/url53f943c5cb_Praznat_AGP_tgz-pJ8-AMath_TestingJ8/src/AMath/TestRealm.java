package AMath;

import Game.*;
import Shirage.*;

public class TestRealm extends Realm {
	public TestRealm(int pX, int pY, int cN) {
		super(pX, pY, cN);
	}

	@Override
	protected void generateShires(int pX, int pY) {
		shires = new TestShire[pX*pY];
		for (int x = 0; x < pX; x++) {
			for (int y = 0; y < pY; y++) {
				shires[x + y*pX] = new TestShire(x, y, this);
				Plot p = new Plot(0.5); p.makeLand();
				shires[x + y*pX].setLinkedPlot(p);
			}
		}
	}
	
	Shire getSomeNeighborOf(Shire s) {
		int newX = (s.getX() + 2 * AGPmain.rand.nextInt(1) - 1 + shiresX) % shiresX;
		int newY = (s.getY() + 2 * AGPmain.rand.nextInt(1) - 1 + shiresY) % shiresY;
		return shires[newX + newY*shiresX];
	}
}
