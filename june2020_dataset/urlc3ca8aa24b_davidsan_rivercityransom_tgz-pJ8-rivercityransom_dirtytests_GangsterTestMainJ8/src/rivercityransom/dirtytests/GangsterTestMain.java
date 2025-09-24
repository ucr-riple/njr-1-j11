package rivercityransom.dirtytests;

import rivercityransom.contracts.BlocContract;
import rivercityransom.contracts.GangsterContract;
import rivercityransom.contracts.ObjetContract;
import rivercityransom.enumerations.Tresor;
import rivercityransom.enumerations.Type;
import rivercityransom.impl.BlocImpl;
import rivercityransom.impl.GangsterImpl;
import rivercityransom.impl.ObjetImpl;
import rivercityransom.services.BlocService;
import rivercityransom.services.GangsterService;
import rivercityransom.services.ObjetService;

public class GangsterTestMain {

	public static void test1() {
		GangsterService g = new GangsterContract(new GangsterImpl());
		g.init("poutine", 41, 11, 89, 1, 120, 0, 1, 3, null);
		System.err.println(g);
	}

	public static void test2() {
		GangsterService g = new GangsterContract(new GangsterImpl());
		ObjetService o = new ObjetContract(new ObjetImpl());
		o.init(Tresor.CHAINEDEVELO);
		g.init("poutine", 41, 11, 89, 1, 120, 0, 1, 3, o);

		System.err.println(g);
		
		BlocService b = new BlocContract(new BlocImpl());
		b.init(Type.FOSSE, null);
		g.jeter(b);
		System.err.println(g);
	}
	

	public static void test3() {
		GangsterService g = new GangsterContract(new GangsterImpl());
		ObjetService o = new ObjetContract(new ObjetImpl());
		o.init(Tresor.CHAINEDEVELO);
		g.init("poutine", 41, 11, 89, 1, 120, 0, 1, 3, o);

		System.err.println(g);
		g.depot(50);
		System.err.println(g);
		g.retrait(50);
		System.err.println(g);
	}


	public static void main(String[] args) {
		test1();
		test2();
		test3();
	}
}
