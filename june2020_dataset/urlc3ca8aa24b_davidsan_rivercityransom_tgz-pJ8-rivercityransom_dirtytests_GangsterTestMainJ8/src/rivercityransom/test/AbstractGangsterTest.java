package rivercityransom.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rivercityransom.contracts.BlocContract;
import rivercityransom.contracts.ObjetContract;
import rivercityransom.enumerations.Tresor;
import rivercityransom.enumerations.Type;
import rivercityransom.error.ContractError;
import rivercityransom.impl.BlocImpl;
import rivercityransom.impl.ObjetImpl;
import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;
import rivercityransom.services.GangsterService;

public abstract class AbstractGangsterTest {

	private GangsterService gangster;

	protected AbstractGangsterTest() {
		setGangster(null);
	}

	public void setGangster(GangsterService gangster) {
		this.gangster = gangster;
	}

	@Before
	public abstract void beforeTests();

	@After
	public final void afterTests() {
		setGangster(null);
		System.out.println();
	}

	public void testInvariants() {
		assertEquals(gangster.estVaincu(), gangster.pointsDeVie() <= 0);
		assertEquals(gangster.estEquipe(),
				gangster.laChoseEquipee() != null
						&& gangster.laChoseEquipee().isEquipable());

	}

	@Test
	public void testInitOK() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster.nom() = " + nom);
		System.out.println("gangster.largeur() = " + largeur);
		System.out.println("gangster.hauteur() = " + hauteur);
		System.out.println("gangster.profondeur() = " + profondeur);
		System.out.println("gangster.force() = " + force);
		System.out.println("gangster.pointsDeVie() = " + pointsDeVie);
		System.out.println("gangster.getX() = " + x);
		System.out.println("gangster.getY() = " + y);
		System.out.println("gangster.getZ() = " + z);
		testInvariants();
		assertEquals(gangster.nom(), nom);
		assertEquals(gangster.largeur(), largeur);
		assertEquals(gangster.hauteur(), hauteur);
		assertEquals(gangster.profondeur(), profondeur);
		assertEquals(gangster.force(), force);
		assertEquals(gangster.pointsDeVie(), pointsDeVie);
		assertEquals(gangster.getX(), x);
		assertEquals(gangster.getY(), y);
		assertEquals(gangster.getZ(), z);
	}

	@Test
	public void testInitFailEmptyName() {
		String nom = "";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");

		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster = null");
		try {
			gangster.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z, obj);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testInitFailLargeur() {
		String nom = "foo";
		int largeur = -1;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster = null");
		try {
			gangster.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z, obj);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testInitFailHauteur() {
		String nom = "foo";
		int largeur = 50;
		int hauteur = -5;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster = null");
		try {
			gangster.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z, obj);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testInitFailProfondeur() {
		String nom = "foo";
		int largeur = 50;
		int hauteur = 5;
		int profondeur = -20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster = null");
		try {
			gangster.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z, obj);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testInitFailNegativeForce() {
		String nom = "foo";
		int largeur = 50;
		int hauteur = 5;
		int profondeur = 20;
		int force = -50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster = null");
		try {
			gangster.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z, obj);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testInitFailZeroForce() {
		String nom = "foo";
		int largeur = 50;
		int hauteur = 5;
		int profondeur = 20;
		int force = 0;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster = null");
		try {
			gangster.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z, obj);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testInitFailForceGreaterThanLifePoint() {
		String nom = "foo";
		int largeur = 50;
		int hauteur = 5;
		int profondeur = 20;
		int force = 9000;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster = null");
		try {
			gangster.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z, obj);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testDepotOk() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;

		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.depot(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster.pointsDeVie() = " + pointsDeVie + inc);
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		gangster.depot(inc);
		assertEquals(pointsDeVie + inc, gangster.pointsDeVie());

	}

	@Test
	public void testDepotFail1() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;

		int inc = -5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.depot(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		try {
			gangster.depot(inc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testDepotFail2() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.retrait(1005)");
		System.out.println("gangster.depot(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		gangster.retrait(1005);
		try {
			gangster.depot(inc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testRetraitOk() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.retrait(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster.pointsDeVie() = " + (pointsDeVie - inc));
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		try {
			gangster.retrait(inc);
			assertTrue(true);
		} catch (ContractError e) {
			assertTrue(false);
		}
	}

	@Test
	public void testRetraitFail1() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = -5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.retrait(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		try {
			gangster.retrait(inc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testRetraitFail2() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.retrait(" + inc + ")");
		System.out.println("gangster.retrait(1002)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		gangster.retrait(1002);
		try {
			gangster.retrait(inc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
		
	}
	
	public void testJetterOk() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.ramasser(bloc)");
		System.out.println("gangster.jeter(bloc)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster.estEquipe() = null");
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		BlocService bloc = new BlocContract(new BlocImpl());
		bloc.init(Type.VIDE, obj);
		
		try {
			gangster.jeter(bloc);
			assertTrue(true);
		} catch (ContractError e) {
			assertTrue(false);
		}
	}

	public void testJetterFail1() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.jeter(bloc)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		BlocService bloc = new BlocContract(new BlocImpl());
		bloc.init(Type.VIDE, obj);
		try {
			gangster.jeter(bloc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	public void testSetXOk() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 10;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.setX("+ inc +")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster.getX() = " + x + inc);
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		try {
			gangster.setX(inc);
			assertTrue(true);
		} catch (ContractError e) {
			assertTrue(false);
		}
	}

	public void testSetYOk() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 10;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.setY("+ inc +")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster.getY() = " + x + inc);
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		try {
			gangster.setY(10);
			assertTrue(true);
		} catch (ContractError e) {
			assertTrue(false);
		}
	}

	public void testSetZOk() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 10;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gangster.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("gangster.setZ("+ inc +")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gangster.getZ() = " + x + inc);
		gangster.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z, obj);
		testInvariants();
		try {
			gangster.setZ(10);
			assertTrue(true);
		} catch (ContractError e) {
			assertTrue(false);
		}
	}
	
}
