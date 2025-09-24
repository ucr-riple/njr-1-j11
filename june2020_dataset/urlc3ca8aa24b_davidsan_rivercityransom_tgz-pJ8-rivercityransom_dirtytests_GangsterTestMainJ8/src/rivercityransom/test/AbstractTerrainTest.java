package rivercityransom.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rivercityransom.enumerations.Tresor;
import rivercityransom.enumerations.Type;
import rivercityransom.error.ContractError;
import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;
import rivercityransom.services.TerrainService;

public abstract class AbstractTerrainTest {
	private TerrainService terrain;
	private BlocService bloc;
	private ObjetService objet;

	protected AbstractTerrainTest() {
		setTerrain(null);
		setBloc(null);
		setTresor(null);
	}

	@Before
	public abstract void beforeTests();

	@After
	public final void afterTests() {
		setTerrain(null);
		setBloc(null);
		setTresor(null);
		System.out.println();
	}

	public void testInvariants() {
	}

	public TerrainService getTerrain() {
		return terrain;
	}

	public void setTerrain(TerrainService terrain) {
		this.terrain = terrain;
	}

	public BlocService getBloc() {
		return bloc;
	}

	public void setBloc(BlocService bloc) {
		this.bloc = bloc;
	}

	public ObjetService getTresor() {
		return objet;
	}

	public void setTresor(ObjetService objet) {
		this.objet = objet;
	}

	@Test
	public void testInitOK() {
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		terrain.init(largeur, hauteur, profondeur);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);

		System.out.println("terrain.nbBlocLargeur() = " + largeur);
		System.out.println("terrain.nbBlocHauteur() = " + hauteur);
		System.out.println("terrain.nbBlocProfondeur() = " + profondeur);

		testInvariants();
		assertEquals(terrain.nbBlocLargeur(), largeur);
		assertEquals(terrain.nbBlocHauteur(), hauteur);
		assertEquals(terrain.nbBlocProfondeur(), profondeur);
		for (int x = 0; x < largeur; x++) {
			for (int y = 0; y < hauteur; y++) {
				for (int z = 0; z < profondeur; z++) {
					BlocService b = terrain.getBloc(x, y, z);
					assertNotNull(b);
					assertEquals(b.getType(), Type.VIDE);
					assertEquals(b.getObjet(), null);
				}
			}
		}
	}

	@Test
	public void testInitFailX() {
		int largeur = -1;
		int hauteur = 50;
		int profondeur = 20;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");

		try {
			terrain.init(largeur, hauteur, profondeur);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testInitFailY() {
		int largeur = 10;
		int hauteur = -1;
		int profondeur = 20;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");

		try {
			terrain.init(largeur, hauteur, profondeur);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testInitFailZ() {
		int largeur = 10;
		int hauteur = 50;
		int profondeur = -1;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");

		try {
			terrain.init(largeur, hauteur, profondeur);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetBlocOK() {
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int largeurBloc = 5;
		int hauteurBloc = 25;
		int profondeurBloc = 10;
		Type myType = Type.VIDE;
		Tresor myTresor = Tresor.CHAINEDEVELO;

		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		terrain.init(largeur, hauteur, profondeur);
		System.out.println("bloc.init(" + myType + ", tresor);");
		bloc.init(myType, objet);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);

		terrain.setBloc(largeurBloc, hauteurBloc, profondeurBloc, bloc);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);

		System.out.println("terrain.getBloc(" + largeurBloc + ", "
				+ hauteurBloc + ", " + profondeurBloc + ").getType() = "
				+ myType);
		System.out.println("terrain.getBloc(" + largeurBloc + ", "
				+ hauteurBloc + ", " + profondeurBloc
				+ ").getObjet().getTresor() = " + myTresor);

		testInvariants();
		assertEquals(terrain.getBloc(largeurBloc, hauteurBloc, profondeurBloc)
				.getType(), myType);
		assertEquals(terrain.getBloc(largeurBloc, hauteurBloc, profondeurBloc)
				.getObjet().getTresor(), myTresor);
	}

	@Test
	public void testSetBlocFailNegX() {
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int largeurBloc = -5;
		int hauteurBloc = 25;
		int profondeurBloc = 10;
		Type myType = Type.VIDE;
		Tresor myTresor = Tresor.CHAINEDEVELO;

		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		terrain.init(largeur, hauteur, profondeur);
		System.out.println("bloc.init(" + myType + ", tresor);");
		bloc.init(myType, objet);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);

		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");

		try {
			terrain.setBloc(largeurBloc, hauteurBloc, profondeurBloc, bloc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetBlocFailNegY() {
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int largeurBloc = 5;
		int hauteurBloc = -25;
		int profondeurBloc = 10;
		Type myType = Type.VIDE;
		Tresor myTresor = Tresor.CHAINEDEVELO;

		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		terrain.init(largeur, hauteur, profondeur);
		System.out.println("bloc.init(" + myType + ", tresor);");
		bloc.init(myType, objet);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);

		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");

		try {
			terrain.setBloc(largeurBloc, hauteurBloc, profondeurBloc, bloc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetBlocFailNegZ() {
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int largeurBloc = 5;
		int hauteurBloc = 25;
		int profondeurBloc = -10;
		Type myType = Type.VIDE;
		Tresor myTresor = Tresor.CHAINEDEVELO;

		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		terrain.init(largeur, hauteur, profondeur);
		System.out.println("bloc.init(" + myType + ", tresor);");
		bloc.init(myType, objet);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);

		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("fail");

		try {
			terrain.setBloc(largeurBloc, hauteurBloc, profondeurBloc, bloc);
			assertTrue(false);
		} catch (ContractError e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetBlocOKLowerBounds() {
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int largeurBloc = 0;
		int hauteurBloc = 0;
		int profondeurBloc = 0;
		Type myType = Type.VIDE;
		Tresor myTresor = Tresor.CHAINEDEVELO;

		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		terrain.init(largeur, hauteur, profondeur);
		System.out.println("bloc.init(" + myType + ", tresor);");
		bloc.init(myType, objet);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		terrain.setBloc(largeurBloc, hauteurBloc, profondeurBloc, bloc);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);

		System.out.println("terrain.getBloc(" + largeurBloc + ", "
				+ hauteurBloc + ", " + profondeurBloc + ").getType() = "
				+ myType);
		System.out.println("terrain.getBloc(" + largeurBloc + ", "
				+ hauteurBloc + ", " + profondeurBloc
				+ ").getObjet().getTresor() = " + myTresor);

		testInvariants();
		assertEquals(terrain.getBloc(largeurBloc, hauteurBloc, profondeurBloc)
				.getType(), myType);
		assertEquals(terrain.getBloc(largeurBloc, hauteurBloc, profondeurBloc)
				.getObjet().getTresor(), myTresor);
	}

	@Test
	public void testSetBlocOKUpperBounds() {
		int largeur = 10;
		int hauteur = 50;
		int profondeur = 20;
		int largeurBloc = largeur - 1;
		int hauteurBloc = hauteur - 1;
		int profondeurBloc = profondeur - 1;
		Type myType = Type.VIDE;
		Tresor myTresor = Tresor.CHAINEDEVELO;

		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("terrain.init(" + largeur + ", " + hauteur + ", "
				+ profondeur + ");");
		terrain.init(largeur, hauteur, profondeur);
		System.out.println("bloc.init(" + myType + ", tresor);");
		bloc.init(myType, objet);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		terrain.setBloc(largeurBloc, hauteurBloc, profondeurBloc, bloc);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);

		System.out.println("terrain.getBloc(" + largeurBloc + ", "
				+ hauteurBloc + ", " + profondeurBloc + ").getType() = "
				+ myType);
		System.out.println("terrain.getBloc(" + largeurBloc + ", "
				+ hauteurBloc + ", " + profondeurBloc
				+ ").getObjet().getTresor() = " + myTresor);

		testInvariants();
		assertEquals(terrain.getBloc(largeurBloc, hauteurBloc, profondeurBloc)
				.getType(), myType);
		assertEquals(terrain.getBloc(largeurBloc, hauteurBloc, profondeurBloc)
				.getObjet().getTresor(), myTresor);
	}

}
