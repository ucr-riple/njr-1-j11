/**
 */
package risiko.actions.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import risiko.actions.Action;
import risiko.actions.AddPlayer;
import risiko.actions.Atack;
import risiko.actions.CoinCards;
import risiko.actions.DrawCard;
import risiko.actions.InGameAction;
import risiko.actions.MoveTroops;
import risiko.actions.RemovePlayer;
import risiko.actions.SetTroops;
import risiko.actions.StartGame;
import risiko.actions.actionFactory;
import risiko.actions.actionPackage;

import risiko.board.boardPackage;

import risiko.board.impl.boardPackageImpl;

import risiko.gamestate.impl.statePackageImpl;

import risiko.gamestate.statePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class actionPackageImpl extends EPackageImpl implements actionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass atackEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass setTroopsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass coinCardsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass inGameActionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass removePlayerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass drawCardEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass moveTroopsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass addPlayerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass startGameEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see risiko.actions.actionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private actionPackageImpl() {
		super(eNS_URI, actionFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link actionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static actionPackage init() {
		if (isInited) return (actionPackage)EPackage.Registry.INSTANCE.getEPackage(actionPackage.eNS_URI);

		// Obtain or create and register package
		actionPackageImpl theactionPackage = (actionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof actionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new actionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		boardPackageImpl theboardPackage = (boardPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(boardPackage.eNS_URI) instanceof boardPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(boardPackage.eNS_URI) : boardPackage.eINSTANCE);
		statePackageImpl thestatePackage = (statePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(statePackage.eNS_URI) instanceof statePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(statePackage.eNS_URI) : statePackage.eINSTANCE);

		// Create package meta-data objects
		theactionPackage.createPackageContents();
		theboardPackage.createPackageContents();
		thestatePackage.createPackageContents();

		// Initialize created meta-data
		theactionPackage.initializePackageContents();
		theboardPackage.initializePackageContents();
		thestatePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theactionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(actionPackage.eNS_URI, theactionPackage);
		return theactionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAtack() {
		return atackEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAtack_Troops() {
		return (EAttribute)atackEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAtack_Border() {
		return (EReference)atackEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAtack_Direction() {
		return (EAttribute)atackEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSetTroops() {
		return setTroopsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSetTroops_Country() {
		return (EReference)setTroopsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSetTroops_Troops() {
		return (EAttribute)setTroopsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCoinCards() {
		return coinCardsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCoinCards_Cards() {
		return (EReference)coinCardsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInGameAction() {
		return inGameActionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInGameAction_Player() {
		return (EReference)inGameActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAction() {
		return actionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemovePlayer() {
		return removePlayerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemovePlayer_Players() {
		return (EReference)removePlayerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDrawCard() {
		return drawCardEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMoveTroops() {
		return moveTroopsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMoveTroops_From() {
		return (EReference)moveTroopsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMoveTroops_To() {
		return (EReference)moveTroopsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMoveTroops_Troops() {
		return (EAttribute)moveTroopsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAddPlayer() {
		return addPlayerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAddPlayer_Players() {
		return (EReference)addPlayerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStartGame() {
		return startGameEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public actionFactory getactionFactory() {
		return (actionFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		atackEClass = createEClass(ATACK);
		createEAttribute(atackEClass, ATACK__TROOPS);
		createEReference(atackEClass, ATACK__BORDER);
		createEAttribute(atackEClass, ATACK__DIRECTION);

		setTroopsEClass = createEClass(SET_TROOPS);
		createEReference(setTroopsEClass, SET_TROOPS__COUNTRY);
		createEAttribute(setTroopsEClass, SET_TROOPS__TROOPS);

		coinCardsEClass = createEClass(COIN_CARDS);
		createEReference(coinCardsEClass, COIN_CARDS__CARDS);

		inGameActionEClass = createEClass(IN_GAME_ACTION);
		createEReference(inGameActionEClass, IN_GAME_ACTION__PLAYER);

		moveTroopsEClass = createEClass(MOVE_TROOPS);
		createEReference(moveTroopsEClass, MOVE_TROOPS__FROM);
		createEReference(moveTroopsEClass, MOVE_TROOPS__TO);
		createEAttribute(moveTroopsEClass, MOVE_TROOPS__TROOPS);

		addPlayerEClass = createEClass(ADD_PLAYER);
		createEReference(addPlayerEClass, ADD_PLAYER__PLAYERS);

		startGameEClass = createEClass(START_GAME);

		actionEClass = createEClass(ACTION);

		removePlayerEClass = createEClass(REMOVE_PLAYER);
		createEReference(removePlayerEClass, REMOVE_PLAYER__PLAYERS);

		drawCardEClass = createEClass(DRAW_CARD);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		boardPackage theboardPackage = (boardPackage)EPackage.Registry.INSTANCE.getEPackage(boardPackage.eNS_URI);
		statePackage thestatePackage = (statePackage)EPackage.Registry.INSTANCE.getEPackage(statePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		atackEClass.getESuperTypes().add(this.getInGameAction());
		setTroopsEClass.getESuperTypes().add(this.getInGameAction());
		coinCardsEClass.getESuperTypes().add(this.getInGameAction());
		inGameActionEClass.getESuperTypes().add(this.getAction());
		moveTroopsEClass.getESuperTypes().add(this.getInGameAction());
		addPlayerEClass.getESuperTypes().add(this.getAction());
		startGameEClass.getESuperTypes().add(this.getAction());
		removePlayerEClass.getESuperTypes().add(this.getAction());
		drawCardEClass.getESuperTypes().add(this.getInGameAction());

		// Initialize classes, features, and operations; add parameters
		initEClass(atackEClass, Atack.class, "Atack", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAtack_Troops(), ecorePackage.getEInt(), "troops", null, 1, 1, Atack.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAtack_Border(), theboardPackage.getBorder(), null, "border", null, 1, 1, Atack.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAtack_Direction(), ecorePackage.getEBoolean(), "direction", "true", 1, 1, Atack.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(setTroopsEClass, SetTroops.class, "SetTroops", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSetTroops_Country(), theboardPackage.getCountry(), null, "country", null, 1, 1, SetTroops.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSetTroops_Troops(), ecorePackage.getEInt(), "troops", null, 1, 1, SetTroops.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(coinCardsEClass, CoinCards.class, "CoinCards", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCoinCards_Cards(), theboardPackage.getCard(), null, "cards", null, 3, 3, CoinCards.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(inGameActionEClass, InGameAction.class, "InGameAction", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInGameAction_Player(), thestatePackage.getPlayer(), null, "player", null, 1, 1, InGameAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(moveTroopsEClass, MoveTroops.class, "MoveTroops", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMoveTroops_From(), theboardPackage.getCountry(), null, "from", null, 1, 1, MoveTroops.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMoveTroops_To(), theboardPackage.getCountry(), null, "to", null, 1, 1, MoveTroops.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMoveTroops_Troops(), ecorePackage.getEInt(), "troops", null, 1, 1, MoveTroops.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(addPlayerEClass, AddPlayer.class, "AddPlayer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAddPlayer_Players(), thestatePackage.getPlayer(), null, "players", null, 1, -1, AddPlayer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(startGameEClass, StartGame.class, "StartGame", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(actionEClass, Action.class, "Action", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(removePlayerEClass, RemovePlayer.class, "RemovePlayer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRemovePlayer_Players(), thestatePackage.getPlayer(), null, "players", null, 1, -1, RemovePlayer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(drawCardEClass, DrawCard.class, "DrawCard", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //actionPackageImpl
