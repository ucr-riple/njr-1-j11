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
import rivercityransom.services.PersonnageService;

public abstract class AbstractPersonnageTest {

	private PersonnageService personnage;

	protected AbstractPersonnageTest() {
		setPersonnage(null);
	}

	public void setPersonnage(PersonnageService personnage) {
		this.personnage = personnage;
	}

	@Before
	public abstract void beforeTests();

	@After
	public final void afterTests() {
		setPersonnage(null);
		System.out.println();
	}

	public void testInvariants() {
		assertEquals(personnage.estVaincu(), personnage.pointsDeVie() <= 0);
		assertTrue(personnage.sommeArgent() >= 0);
		assertEquals(personnage.estEquipe(),
				personnage.laChoseEquipee() != null
						&& personnage.laChoseEquipee().isEquipable());

	}

	@Test
	public void testInitOK() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage.nom() = " + nom);
		System.out.println("personnage.largeur() = " + largeur);
		System.out.println("personnage.hauteur() = " + hauteur);
		System.out.println("personnage.profondeur() = " + profondeur);
		System.out.println("personnage.force() = " + force);
		System.out.println("personnage.pointsDeVie() = " + pointsDeVie);
		System.out.println("personnage.getX() = " + x);
		System.out.println("personnage.getY() = " + y);
		System.out.println("personnage.getZ() = " + z);
		testInvariants();
		assertEquals(personnage.nom(), nom);
		assertEquals(personnage.largeur(), largeur);
		assertEquals(personnage.hauteur(), hauteur);
		assertEquals(personnage.profondeur(), profondeur);
		assertEquals(personnage.force(), force);
		assertEquals(personnage.pointsDeVie(), pointsDeVie);
		assertEquals(personnage.getX(), x);
		assertEquals(personnage.getY(), y);
		assertEquals(personnage.getZ(), z);
		assertEquals(personnage.sommeArgent(), 0);
	}

	@Test
	public void testInitFailEmptyName() {
		String nom = "";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");

		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage = null");
		try {
			personnage.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z);
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
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage = null");
		try {
			personnage.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z);
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
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage = null");
		try {
			personnage.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z);
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
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage = null");
		try {
			personnage.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z);
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
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage = null");
		try {
			personnage.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z);
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
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage = null");
		try {
			personnage.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z);
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
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage = null");
		try {
			personnage.init(nom, largeur, hauteur, profondeur, force,
					pointsDeVie, x, y, z);
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
		int x = 1;
		int y = 0;
		int z = 0;

		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.depot(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		personnage.depot(inc);
		System.out.println("personnage.pointsDeVie() = "
				+ personnage.pointsDeVie());
		assertEquals(pointsDeVie + inc, personnage.pointsDeVie());

	}

	@Test
	public void testDepotFail1() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;

		int inc = -5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.depot(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		try {
			personnage.depot(inc);
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
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.retrait(1005)");
		System.out.println("personnage.depot(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		personnage.retrait(1005);
		try {
			personnage.depot(inc);
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
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.retrait(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage.pointsDeVie() = " + (pointsDeVie - inc));
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		try {
			personnage.retrait(inc);
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
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = -5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.retrait(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		try {
			personnage.retrait(inc);
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
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.retrait(" + inc + ")");
		System.out.println("personnage.retrait(1002)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		personnage.retrait(1002);
		try {
			personnage.retrait(inc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testDepotArgentOk() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.depotArgent(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage.sommeArgent() = "
				+ (personnage.sommeArgent() + inc));
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		try {
			personnage.depotArgent(inc);
			assertTrue(true);
		} catch (ContractError e) {
			assertTrue(false);
		}
	}

	@Test
	public void testDepotArgentFail1() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = -5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("personnage.depotArgent(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		try {
			personnage.depotArgent(inc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testDepotArgentFail2() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.depotArgent(" + inc + ")");
		System.out.println("personnage.retrait(1004)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		personnage.retrait(1004);
		try {
			personnage.depotArgent(5);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testRetraitArgentOk() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 100;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("personnage.depotArgent(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.retraitArgent(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage.sommeArgent() = " + (inc - inc));
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		personnage.depotArgent(inc);
		try {
			personnage.retraitArgent(inc);
			assertTrue(true);
		} catch (ContractError e) {
			assertTrue(false);
		}
	}

	@Test
	public void testRetraitArgentFail1() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = -5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.retraitArgent(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		try {
			personnage.retraitArgent(inc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testRetraitArgentFail2() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("personnage.depotArgent(100)");
		System.out.println("personnage.retrait(1004)");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.retraitArgent(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		personnage.depotArgent(100);
		personnage.retrait(1004);
		try {
			personnage.retraitArgent(inc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	public void testRetraitArgentFail3() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 5;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("personnage.depotArgent(100)");
		System.out.println("personnage.retrait(1004)");
		System.out.println("personnage.retraitArgent(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		personnage.retrait(1004);
		try {
			personnage.retraitArgent(inc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	public void testRamasserOk() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.ramasser(bloc)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage.estEquipe() != null");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		BlocService bloc = new BlocContract(new BlocImpl());
		bloc.init(Type.VIDE, obj);

		try {
			personnage.ramasser(bloc);
			assertTrue(true);
		} catch (ContractError e) {
			assertTrue(false);
		}
	}

	public void testRamasserFail1() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.retrait(2000)");
		System.out.println("personnage.ramasser(bloc)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		BlocService bloc = new BlocContract(new BlocImpl());
		bloc.init(Type.VIDE, obj);
		personnage.retrait(2000);
		try {
			personnage.ramasser(bloc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	public void testRamasserFail2() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println("personnage.ramasser(bloc)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CINQUANTECENTIMES);
		BlocService bloc = new BlocContract(new BlocImpl());
		bloc.init(Type.VIDE, obj);
		try {
			personnage.ramasser(bloc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	public void testRamasserFail3() {
		String nom = "foo";
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int force = 50;
		int pointsDeVie = 1001;
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.ramasser(bloc)");
		System.out.println("personnage.retrait(2000)");
		System.out.println("personnage.ramasser(bloc2)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		BlocService bloc = new BlocContract(new BlocImpl());
		bloc.init(Type.VIDE, obj);
		ObjetService obj2 = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		BlocService bloc2 = new BlocContract(new BlocImpl());
		bloc.init(Type.VIDE, obj2);
		personnage.ramasser(bloc);
		personnage.retrait(2000);
		try {
			personnage.ramasser(bloc2);
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
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.ramasser(bloc)");
		System.out.println("personnage.jeter(bloc)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage.estEquipe() = null");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		BlocService bloc = new BlocContract(new BlocImpl());
		bloc.init(Type.VIDE, obj);
		personnage.ramasser(bloc);
		try {
			personnage.jeter(bloc);
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
		int x = 1;
		int y = 0;
		int z = 0;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.jeter(bloc)");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		ObjetService obj = new ObjetContract(new ObjetImpl());
		obj.init(Tresor.CHAINEDEVELO);
		BlocService bloc = new BlocContract(new BlocImpl());
		bloc.init(Type.VIDE, obj);
		try {
			personnage.jeter(bloc);
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
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 10;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.setX(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage.getX() = " + x + inc);
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		try {
			personnage.setX(inc);
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
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 10;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.setY(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage.getY() = " + x + inc);
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		try {
			personnage.setY(10);
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
		int x = 1;
		int y = 0;
		int z = 0;
		int inc = 10;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("personnage.init(\"" + nom + "\", " + largeur + ", "
				+ hauteur + ", " + profondeur + ", " + force + ", "
				+ pointsDeVie + ", " + x + ", " + y + ", " + z + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("personnage.setZ(" + inc + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("personnage.getZ() = " + x + inc);
		personnage.init(nom, largeur, hauteur, profondeur, force, pointsDeVie,
				x, y, z);
		testInvariants();
		try {
			personnage.setZ(10);
			assertTrue(true);
		} catch (ContractError e) {
			assertTrue(false);
		}
	}

}
