package rivercityransom.test;

import rivercityransom.impl.TerrainImpl;

public class TerrainTest extends AbstractTerrainTest {

	@Override
	public void beforeTests() {
		setTerrain(new TerrainImpl());
	}

}
