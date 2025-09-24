package rivercityransom.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rivercityransom.enumerations.Commande;
import rivercityransom.enumerations.Resultat;
import rivercityransom.services.MoteurJeuService;

public abstract class AbstractMoteurJeuTest {

	private MoteurJeuService moteur;

	protected AbstractMoteurJeuTest() {
		setMoteurJeu(moteur);
	}

	@Before
	public void beforeTests() {
	}

	public void setMoteurJeu(MoteurJeuService moteur) {
		this.moteur = moteur;
	}

	@After
	public final void afterTests() {
		setMoteurJeu(null);
		System.out.println();
	}

	public void testInvariants() {
		assertTrue(!(moteur.estFini() && !((moteur.combat().alex().estVaincu() && moteur
				.combat().ryan().estVaincu()) || moteur.combat().slick()
				.estVaincu())));
		assertTrue(!(moteur.resulatFinal() == Resultat.GAGNEE && !(moteur
				.combat().slick().estVaincu() && (!moteur.combat().alex()
				.estVaincu() || !moteur.combat().ryan().estVaincu()))));
		assertTrue(!(moteur.resulatFinal() == Resultat.PERDUE && !(moteur
				.combat().alex().estVaincu() || moteur.combat().ryan()
				.estVaincu())));

	}

	@Test
	public void testInitOK() {
		int largeur = 30;
		int hauteur = 10;
		int profondeur = 10;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("moteur.init(" + largeur + "," + hauteur + ","
				+ profondeur + ");");
		moteur.init(largeur, hauteur, profondeur);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("moteur.combat().getTerrain().nbBlocLargeur() = "
				+ largeur);
		System.out.println("moteur.combat().getTerrain().nbBlocHauteur() = "
				+ hauteur);
		System.out.println("moteur.combat().getTerrain().nbBlocProfondeur() = "
				+ profondeur);
		testInvariants();
		assertEquals(moteur.combat().getTerrain().nbBlocLargeur(), largeur);
		assertEquals(moteur.combat().getTerrain().nbBlocHauteur(), hauteur);
		assertEquals(moteur.combat().getTerrain().nbBlocProfondeur(),
				profondeur);
	}

	@Test
	public void testPasDeJeuOk() {
		int largeur = 30;
		int hauteur = 10;
		int profondeur = 10;
		Commande c = Commande.BAS;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("moteur.init(" + largeur + "," + hauteur + ","
				+ profondeur + ");");
		System.out.println("moteur.pasDeJeu(" + c + ", " + c + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("moteur.pasJeuCourant() = moteurPasJeuCourant_atPre + 1");
		moteur.init(largeur, hauteur, profondeur);
		int moteurPasJeuCourant_atPre = moteur.pasJeuCourant();
		moteur.pasDeJeu(c, c);
		assertEquals(moteur.pasJeuCourant(), moteurPasJeuCourant_atPre + 1);
	}

}
