package rivercityransom.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rivercityransom.contracts.ObjetContract;
import rivercityransom.enumerations.Tresor;
import rivercityransom.enumerations.Type;
import rivercityransom.impl.ObjetImpl;
import rivercityransom.services.BlocService;
import rivercityransom.services.ObjetService;

public abstract class AbstractBlocTest {
	private BlocService bloc;
	private ObjetService objet;

	protected AbstractBlocTest() {
		setBloc(null);
		setObjet(null);
	}

	public void setBloc(BlocService bloc) {
		this.bloc = bloc;
	}

	public void setObjet(ObjetService objet) {
		this.objet = objet;
	}

	@Before
	public abstract void beforeTests();

	@After
	public final void afterTests() {
		setBloc(null);
		setObjet(null);
		System.out.println();
	}

	public void testInvariants() {
	}

	@Test
	public void testInitOK() {
		Tresor myTresor = Tresor.CINQUANTECENTIMES;
		Type myType = Type.VIDE;
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("bloc.init(" + myType + ", objet);");
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		bloc.init(myType, objet);
		testInvariants();
		assertEquals(bloc.getObjet().getTresor(), myTresor);
		assertEquals(bloc.getType(), myType);
	}

	@Test
	public void testSetTresorOK() {
		Tresor myTresor = Tresor.CINQUANTECENTIMES;
		Type myType = Type.VIDE;
		ObjetService myObjet = new ObjetContract(new ObjetImpl());
		objet.init(myTresor);
		System.out.println(SpecTestStrings.SPEC_TEST_NAME + new Object() {
		}.getClass().getEnclosingMethod().getName());
		System.out.println(SpecTestStrings.SPEC_TEST_COND_INIT);
		System.out.println("objet.init(" + myTresor + ");");
		objet.init(myTresor);
		myTresor = Tresor.POUBELLEMETALLIQUE;
		System.out.println("myObjet.init(" + myTresor + ");");
		myObjet.init(myTresor);
		System.out.println("bloc.init(\"" + myType + "\", objet);");
		bloc.init(myType, objet);
		testInvariants();
		System.out.println(SpecTestStrings.SPEC_TEST_OPERATIONS);
		System.out.println("bloc.setTresor(objet)");
		bloc.setObjet(myObjet);
		System.out.println(SpecTestStrings.SPEC_TEST_ORACLE);
		assertEquals(bloc.getObjet().getTresor(), myTresor);
	}

}
