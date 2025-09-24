package rivercityransom.test;

import rivercityransom.contracts.BlocContract;
import rivercityransom.contracts.ObjetContract;
import rivercityransom.contracts.TerrainContract;
import rivercityransom.impl.BlocImpl;
import rivercityransom.impl.ObjetImpl;
import rivercityransom.impl.TerrainImpl;

public class TerrainTestContract extends AbstractTerrainTest {

	@Override
	public void beforeTests() {
		setTerrain(new TerrainContract(new TerrainImpl()));
		setBloc(new BlocContract(new BlocImpl()));
		setTresor(new ObjetContract(new ObjetImpl()));
	}

}
