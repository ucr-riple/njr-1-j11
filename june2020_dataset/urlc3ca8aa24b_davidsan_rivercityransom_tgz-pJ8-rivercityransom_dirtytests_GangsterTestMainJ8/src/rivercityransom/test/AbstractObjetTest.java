package rivercityransom.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rivercityransom.enumerations.Tresor;
import rivercityransom.services.ObjetService;

public abstract class AbstractObjetTest {
	private ObjetService objet;

	protected AbstractObjetTest() {
		setObjet(null);
	}

	public ObjetService getObjet() {
		return objet;
	}

	public void setObjet(ObjetService objet) {
		this.objet = objet;
	}

	@Before
	public abstract void beforeTests();

	@After
	public final void afterTests() {
		setObjet(null);
		System.out.println();
	}

	public void testInvariants() {
	}

	@Test
	public void testInitOK1() {
		Tresor myTresor = Tresor.CHAINEDEVELO;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("objet.getTresor() = " + myTresor);
		testInvariants();
		assertEquals(myTresor, objet.getTresor());
	}

	@Test
	public void testInitOK2() {
		Tresor myTresor = Tresor.CINQUANTECENTIMES;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("objet.getTresor() = " + myTresor);
		testInvariants();
		assertEquals(myTresor, objet.getTresor());
	}

	@Test
	public void testInitOK3() {
		Tresor myTresor = Tresor.POUBELLEMETALLIQUE;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("objet.getTresor() = " + myTresor);
		testInvariants();
		assertEquals(myTresor, objet.getTresor());
	}

	@Test
	public void testInitOK4() {
		Tresor myTresor = Tresor.UNDOLLAR;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		System.out.println("objet.getTresor() = " + myTresor);
		testInvariants();
		assertEquals(myTresor, objet.getTresor());
	}
}
