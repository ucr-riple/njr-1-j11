package rivercityransom.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rivercityransom.enumerations.Commande;
import rivercityransom.error.ContractError;
import rivercityransom.services.GangsterService;
import rivercityransom.services.GestionCombatService;
import rivercityransom.services.PersonnageService;

public abstract class AbstractGestionCombatTest {
	private GestionCombatService gestionCombat;

	protected AbstractGestionCombatTest() {
		setGestionCombat(null);
	}

	public void setGestionCombat(GestionCombatService gestionCombat) {
		this.gestionCombat = gestionCombat;
	}

	@Before
	public abstract void beforeTests();

	@After
	public final void afterTests() {
		setGestionCombat(null);
	}

	public void testInvariants() {
		// collisionPerso
		PersonnageService[] goodguys = { gestionCombat.ryan(),
				gestionCombat.alex() };
		for (PersonnageService p : goodguys) {
			List<GangsterService> gl = gestionCombat.collisionPerso(p);
			if (gl.isEmpty()) {
				continue;
			}
			for (GangsterService g : gl) {
				assertTrue(!((Math.abs(p.getX() - g.getX()) <= 1)
						&& (Math.abs(p.getY() - g.getY()) <= 1) && (Math.abs(p
						.getZ() - g.getZ()) <= 1)));
			}
		}
		// collisionGangster
		List<GangsterService> badguys = new ArrayList<GangsterService>(
				gestionCombat.gangster());
		badguys.add(gestionCombat.slick());
		for (GangsterService g : badguys) {
			List<PersonnageService> pl = gestionCombat.collisionGangster(g);
			if (pl.isEmpty()) {
				continue;
			}
			for (PersonnageService p : pl) {
				assertTrue(!((Math.abs(g.getX() - p.getX()) <= 1)
						&& (Math.abs(g.getY() - p.getY()) <= 1) && (Math.abs(g
						.getZ() - p.getZ()) <= 1)));
			}
		}
	}

	@Test
	public void testInitOK() {
		int largeur = 50;
		int hauteur = 50;
		int profondeur = 20;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gestionCombat.init(" + largeur + ", " + hauteur
				+ ", " + profondeur + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gestionCombat.getTerrain().nbBlocLargeur() == "
				+ largeur);
		System.out.println("gestionCombat.getTerrain().nbBlocPronfondeur() == "
				+ profondeur);
		System.out.println("gestionCombat.getTerrain().nbBlocHauteur() == "
				+ hauteur);
		System.out.println("gestionCombat.estFrappe(\"Alex\") == false");
		System.out.println("gestionCombat.estFrappe(\"Ryan\") == false");
		System.out.println("gestionCombat.estFrappe(\"Slick\") == false");
		System.out.println("gestionCombat.estGele(\"Alex\") == false");
		System.out.println("gestionCombat.estGele(\"Ryan\") == false");
		System.out.println("gestionCombat.estGele(\"Slick\") == false");
		System.out.println("for (GangsterService g : gangster()) {"
				+ "if (!(gestionCombat.estFrappe(g.nom()) == false)) {"
				+ "throw new PostconditionError(\"init\");" + "}"
				+ "if (!(gestionCombat.estGele(g.nom()) == false)) {"
				+ "throw new PostconditionError(\"init\");" + "}" + "}");
		gestionCombat.init(largeur, hauteur, profondeur);
		testInvariants();
		assertEquals(largeur, gestionCombat.getTerrain().nbBlocLargeur());
		assertEquals(hauteur, gestionCombat.getTerrain().nbBlocHauteur());
		assertEquals(profondeur, gestionCombat.getTerrain().nbBlocProfondeur());
		assertTrue((gestionCombat.estFrappe("Alex") == false));
		assertTrue((gestionCombat.estFrappe("Ryan") == false));
		assertTrue((gestionCombat.estFrappe("Slick") == false));
		for (GangsterService g : gestionCombat.gangster()) {
			assertTrue((gestionCombat.estFrappe(g.nom()) == false));
			assertTrue((gestionCombat.estGele(g.nom()) == false));
		}
		assertTrue((gestionCombat.estGele("Alex") == false));
		assertTrue((gestionCombat.estGele("Ryan") == false));
		assertTrue((gestionCombat.estGele("Slick") == false));
	}

	public void testInitFail1() {
		int largeur = -50;
		int hauteur = 50;
		int profondeur = 20;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gestionCombat.init(" + largeur + ", " + hauteur
				+ ", " + profondeur + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gestionCombat = null");

		testInvariants();
		try {
			gestionCombat.init(largeur, hauteur, profondeur);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	public void testInitFail2() {
		int largeur = 50;
		int hauteur = -50;
		int profondeur = 20;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gestionCombat.init(" + largeur + ", " + hauteur
				+ ", " + profondeur + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gestionCombat = null");

		testInvariants();
		try {
			gestionCombat.init(largeur, hauteur, profondeur);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	public void testInitFail3() {
		int largeur = 50;
		int hauteur = 50;
		int profondeur = -20;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gestionCombat.init(" + largeur + ", " + hauteur
				+ ", " + profondeur + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("gestionCombat = null");

		testInvariants();
		try {
			gestionCombat.init(largeur, hauteur, profondeur);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	public void testGererOK() {
		int largeur = 50;
		int hauteur = 50;
		int profondeur = 20;
		String nomV = "alex";
		String nomA = "slick";

		Commande c1 = Commande.RIEN;
		Commande c2 = Commande.RIEN;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("gestionCombat.init(" + largeur + ", " + hauteur
				+ ", " + profondeur + ");");
		System.out.println("gestionCombat.gerer(" + c1 + ", " + c2 + ")");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("");

		PersonnageService[] goodguys = { gestionCombat.ryan(),
				gestionCombat.alex() };

		List<GangsterService> badguys = new ArrayList<GangsterService>(
				gestionCombat.gangster());
		badguys.add(gestionCombat.slick());
		gestionCombat.init(largeur, hauteur, profondeur);
		testInvariants();
		gestionCombat.gerer(c1, c2);
		assertTrue(gestionCombat.estFrappe(nomV));

	}

}
