package rivercityransom.dirtytests;

import rivercityransom.contracts.BlocContract;
import rivercityransom.contracts.ObjetContract;
import rivercityransom.contracts.PersonnageContract;
import rivercityransom.enumerations.Tresor;
import rivercityransom.enumerations.Type;
import rivercityransom.impl.BlocImpl;
import rivercityransom.impl.ObjetImpl;
import rivercityransom.impl.PersonnageImpl;
import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;
import rivercityransom.services.PersonnageService;

public class PersonnageTestMain {
	// ramasser
	public static void test1() {
		PersonnageService p = new PersonnageContract(new PersonnageImpl());
		p.init("totoro", 501, 445, 255, 20, 9950, 10, 20, 65);
		System.err.println(p.estEquipe());
		BlocService b = new BlocContract(new BlocImpl());
		ObjetService tresor = new ObjetContract(new ObjetImpl());
		tresor.init(Tresor.POUBELLEMETALLIQUE);
		b.init(Type.VIDE, tresor);
		p.ramasser(b);
		System.err.println(p.estEquipe());
	}

	// ramasser + jeter
	public static void test2() {
		PersonnageService p = new PersonnageContract(new PersonnageImpl());
		p.init("totoro", 501, 445, 255, 20, 9950, 10, 20, 65);
		System.err.println(p.estEquipe());
		BlocService b = new BlocContract(new BlocImpl());
		ObjetService tresor = new ObjetContract(new ObjetImpl());
		tresor.init(Tresor.POUBELLEMETALLIQUE);
		b.init(Type.VIDE, tresor);
		p.ramasser(b);
		System.err.println("apres ramasser : isEquipe= " + p.estEquipe());
		p.jeter(b);
		System.err.println("apres jeter : isEquipe= " + p.estEquipe());

	}

	// argent (depot retrait)
	public static void test3() {
		PersonnageService p = new PersonnageContract(new PersonnageImpl());
		p.init("totoro", 501, 445, 255, 20, 9950, 10, 20, 65);
		System.err.println("au début argent = " + p.sommeArgent());
		p.depotArgent(100);
		System.err.println("apres depotArgent(100) = " + p.sommeArgent());
		p.retraitArgent(50);
		System.err.println("apres retraitArgent(50) = " + p.sommeArgent());
	}

	// pts de vie (depot retrait)
	public static void test4() {
		PersonnageService p = new PersonnageContract(new PersonnageImpl());
		p.init("totoro", 501, 445, 255, 20, 9950, 10, 20, 65);
		System.err.println("au début pts de vie = " + p.pointsDeVie());
		p.depot(100);
		System.err.println("apres depot(100) = " + p.pointsDeVie());
		p.retrait(50);
		System.err.println("apres retrait(50) = " + p.pointsDeVie());
	}

	// est vaincu
	public static void test5() {
		PersonnageService p = new PersonnageContract(new PersonnageImpl());
		p.init("totoro", 501, 445, 255, 20, 9950, 10, 20, 65);
		System.err.println(p.estVaincu());
		p.retrait(999999);
		System.err.println(p.estVaincu());
	}

	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
		test5();
	}

}
