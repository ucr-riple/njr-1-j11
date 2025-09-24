package rivercityransom.dirtytests;

import rivercityransom.contracts.BlocContract;
import rivercityransom.contracts.ObjetContract;
import rivercityransom.contracts.TerrainContract;
import rivercityransom.enumerations.Tresor;
import rivercityransom.enumerations.Type;
import rivercityransom.impl.BlocImpl;
import rivercityransom.impl.ObjetImpl;
import rivercityransom.impl.TerrainImpl;
import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;
import rivercityransom.services.TerrainService;

public class TerrainTestMain {
	public static void test1() {
		TerrainService t = new TerrainContract(new TerrainImpl());
		t.init(1000, 10, 5);
		System.err.println("res " + t.getBloc(0, 0, 0));

		BlocService b = new BlocContract(new BlocImpl());
		ObjetService tresor = new ObjetContract(new ObjetImpl());
		tresor.init(Tresor.UNDOLLAR);
		b.init(Type.FOSSE, tresor);
		t.setBloc(0, 0, 0, b);
		System.err.println("res " + t.getBloc(0, 0, 0));
	}

	public static void main(String[] args) {
		test1();
	}

}
