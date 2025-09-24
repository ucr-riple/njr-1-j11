package rivercityransom.dirtytests;

import rivercityransom.contracts.BlocContract;
import rivercityransom.contracts.ObjetContract;
import rivercityransom.enumerations.Tresor;
import rivercityransom.enumerations.Type;
import rivercityransom.impl.BlocImpl;
import rivercityransom.impl.ObjetImpl;
import rivercityransom.services.BlocService;

public class BlocTestMain {

	// Ca teste les BlocService et les ObjetService

	public static void test1() {
		BlocService b = new BlocContract(new BlocImpl());
		b.init(Type.FOSSE, null);
		System.out.println(b);
	}

	public static void test2() {
		BlocService b = new BlocContract(new BlocImpl());
		b.init(Type.VIDE, null);
		System.out.println(b);
	}

	public static void test3() {
		BlocService b = new BlocContract(new BlocImpl());
		ObjetContract o = new ObjetContract(new ObjetImpl());
		o.init(Tresor.CHAINEDEVELO);
		b.init(Type.VIDE, o);
		System.out.println(b);
	}

	public static void test4() {
		BlocService b = new BlocContract(new BlocImpl());
		ObjetContract o = new ObjetContract(new ObjetImpl());
		o.init(Tresor.POUBELLEMETALLIQUE);
		b.init(Type.VIDE, o);
		System.out.println(b);
	}

	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();

	}

}
