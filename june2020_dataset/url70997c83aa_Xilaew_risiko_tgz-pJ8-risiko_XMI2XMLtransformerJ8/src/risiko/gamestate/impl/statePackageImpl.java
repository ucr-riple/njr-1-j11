/**
 */
package risiko.gamestate.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import risiko.actions.actionPackage;

import risiko.actions.impl.actionPackageImpl;

import risiko.board.boardPackage;

import risiko.board.impl.boardPackageImpl;

import risiko.gamestate.CountryState;
import risiko.gamestate.GameState;
import risiko.gamestate.Player;
import risiko.gamestate.State;
import risiko.gamestate.TurnPhase;
import risiko.gamestate.stateFactory;
import risiko.gamestate.statePackage;
import risiko.gamestate.util.stateValidator;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class statePackageImpl extends EPackageImpl implements statePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass playerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass countryStateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass countryToCountryStateMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum turnPhaseEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum gameStateEEnum = null;

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
	 * @see risiko.gamestate.statePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private statePackageImpl() {
		super(eNS_URI, stateFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link statePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static statePackage init() {
		if (isInited) return (statePackage)EPackage.Registry.INSTANCE.getEPackage(statePackage.eNS_URI);

		// Obtain or create and register package
		statePackageImpl thestatePackage = (statePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof statePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new statePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		boardPackageImpl theboardPackage = (boardPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(boardPackage.eNS_URI) instanceof boardPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(boardPackage.eNS_URI) : boardPackage.eINSTANCE);
		actionPackageImpl theactionPackage = (actionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(actionPackage.eNS_URI) instanceof actionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(actionPackage.eNS_URI) : actionPackage.eINSTANCE);

		// Create package meta-data objects
		thestatePackage.createPackageContents();
		theboardPackage.createPackageContents();
		theactionPackage.createPackageContents();

		// Initialize created meta-data
		thestatePackage.initializePackageContents();
		theboardPackage.initializePackageContents();
		theactionPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(thestatePackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return stateValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		thestatePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(statePackage.eNS_URI, thestatePackage);
		return thestatePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPlayer() {
		return playerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlayer_OwnedCountries() {
		return (EReference)playerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPlayer_OwnedCards() {
		return (EReference)playerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayer_Name() {
		return (EAttribute)playerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPlayer_TotalTroops() {
		return (EAttribute)playerEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getState() {
		return stateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Players() {
		return (EReference)stateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_CountryState() {
		return (EReference)stateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getState_Turn() {
		return (EReference)stateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_Phase() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_State() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_TroopsToSet() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getState_ConqueredCountry() {
		return (EAttribute)stateEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCountryState() {
		return countryStateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCountryState_Player() {
		return (EReference)countryStateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCountryState_Country() {
		return (EReference)countryStateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCountryState_Troops() {
		return (EAttribute)countryStateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCountryToCountryStateMap() {
		return countryToCountryStateMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCountryToCountryStateMap_Key() {
		return (EReference)countryToCountryStateMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCountryToCountryStateMap_Value() {
		return (EReference)countryToCountryStateMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTurnPhase() {
		return turnPhaseEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getGameState() {
		return gameStateEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public stateFactory getstateFactory() {
		return (stateFactory)getEFactoryInstance();
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
		playerEClass = createEClass(PLAYER);
		createEReference(playerEClass, PLAYER__OWNED_COUNTRIES);
		createEReference(playerEClass, PLAYER__OWNED_CARDS);
		createEAttribute(playerEClass, PLAYER__NAME);
		createEAttribute(playerEClass, PLAYER__TOTAL_TROOPS);

		stateEClass = createEClass(STATE);
		createEReference(stateEClass, STATE__PLAYERS);
		createEReference(stateEClass, STATE__COUNTRY_STATE);
		createEReference(stateEClass, STATE__TURN);
		createEAttribute(stateEClass, STATE__PHASE);
		createEAttribute(stateEClass, STATE__STATE);
		createEAttribute(stateEClass, STATE__TROOPS_TO_SET);
		createEAttribute(stateEClass, STATE__CONQUERED_COUNTRY);

		countryStateEClass = createEClass(COUNTRY_STATE);
		createEReference(countryStateEClass, COUNTRY_STATE__PLAYER);
		createEReference(countryStateEClass, COUNTRY_STATE__COUNTRY);
		createEAttribute(countryStateEClass, COUNTRY_STATE__TROOPS);

		countryToCountryStateMapEClass = createEClass(COUNTRY_TO_COUNTRY_STATE_MAP);
		createEReference(countryToCountryStateMapEClass, COUNTRY_TO_COUNTRY_STATE_MAP__KEY);
		createEReference(countryToCountryStateMapEClass, COUNTRY_TO_COUNTRY_STATE_MAP__VALUE);

		// Create enums
		turnPhaseEEnum = createEEnum(TURN_PHASE);
		gameStateEEnum = createEEnum(GAME_STATE);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(playerEClass, Player.class, "Player", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlayer_OwnedCountries(), this.getCountryState(), this.getCountryState_Player(), "ownedCountries", null, 0, -1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPlayer_OwnedCards(), theboardPackage.getCard(), null, "ownedCards", null, 0, -1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlayer_Name(), ecorePackage.getEString(), "name", null, 0, 1, Player.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPlayer_TotalTroops(), ecorePackage.getEInt(), "totalTroops", null, 0, 1, Player.class, !IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(stateEClass, State.class, "State", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getState_Players(), this.getPlayer(), null, "players", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_CountryState(), this.getCountryToCountryStateMap(), null, "countryState", null, 0, -1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getState_Turn(), this.getPlayer(), null, "turn", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getState_Phase(), this.getTurnPhase(), "phase", null, 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getState_State(), this.getGameState(), "state", null, 1, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getState_TroopsToSet(), ecorePackage.getEInt(), "troopsToSet", "0", 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getState_ConqueredCountry(), ecorePackage.getEBoolean(), "conqueredCountry", "false", 0, 1, State.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(countryStateEClass, CountryState.class, "CountryState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCountryState_Player(), this.getPlayer(), this.getPlayer_OwnedCountries(), "player", null, 1, 1, CountryState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCountryState_Country(), theboardPackage.getCountry(), null, "country", null, 1, 1, CountryState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCountryState_Troops(), ecorePackage.getEInt(), "troops", "1", 1, 1, CountryState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(countryToCountryStateMapEClass, Map.Entry.class, "CountryToCountryStateMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCountryToCountryStateMap_Key(), theboardPackage.getCountry(), null, "key", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCountryToCountryStateMap_Value(), this.getCountryState(), null, "value", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(turnPhaseEEnum, TurnPhase.class, "TurnPhase");
		addEEnumLiteral(turnPhaseEEnum, TurnPhase.GAIN_TROOPS);
		addEEnumLiteral(turnPhaseEEnum, TurnPhase.SET_TROOPS);
		addEEnumLiteral(turnPhaseEEnum, TurnPhase.FIGHT);
		addEEnumLiteral(turnPhaseEEnum, TurnPhase.MOVE_TROOPS);

		initEEnum(gameStateEEnum, GameState.class, "GameState");
		addEEnumLiteral(gameStateEEnum, GameState.ACCEPTING_PLAYERS);
		addEEnumLiteral(gameStateEEnum, GameState.INITIAL_TROOP_DISTRIBUTION);
		addEEnumLiteral(gameStateEEnum, GameState.PLAY);
		addEEnumLiteral(gameStateEEnum, GameState.GAME_OVER);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore/OCL
		createOCLAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "validationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL"
		   });	
		addAnnotation
		  (stateEClass, 
		   source, 
		   new String[] {
			 "constraints", "countryToStateMapIsAccurate"
		   });
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore/OCL</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createOCLAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore/OCL";	
		addAnnotation
		  (stateEClass, 
		   source, 
		   new String[] {
			 "countryToStateMapIsAccurate", "true"
		   });
	}

} //statePackageImpl
