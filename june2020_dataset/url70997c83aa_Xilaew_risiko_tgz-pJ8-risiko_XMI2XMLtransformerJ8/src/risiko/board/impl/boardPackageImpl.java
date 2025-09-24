/**
 */
package risiko.board.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import risiko.actions.actionPackage;

import risiko.actions.impl.actionPackageImpl;

import risiko.board.Board;
import risiko.board.Border;
import risiko.board.BorderType;
import risiko.board.Card;
import risiko.board.CardSymbol;
import risiko.board.Continent;
import risiko.board.Country;
import risiko.board.boardFactory;
import risiko.board.boardPackage;

import risiko.gamestate.impl.statePackageImpl;

import risiko.gamestate.statePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class boardPackageImpl extends EPackageImpl implements boardPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass boardEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass countryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass borderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass continentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cardEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum borderTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum cardSymbolEEnum = null;

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
	 * @see risiko.board.boardPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private boardPackageImpl() {
		super(eNS_URI, boardFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link boardPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static boardPackage init() {
		if (isInited) return (boardPackage)EPackage.Registry.INSTANCE.getEPackage(boardPackage.eNS_URI);

		// Obtain or create and register package
		boardPackageImpl theboardPackage = (boardPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof boardPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new boardPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		statePackageImpl thestatePackage = (statePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(statePackage.eNS_URI) instanceof statePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(statePackage.eNS_URI) : statePackage.eINSTANCE);
		actionPackageImpl theactionPackage = (actionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(actionPackage.eNS_URI) instanceof actionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(actionPackage.eNS_URI) : actionPackage.eINSTANCE);

		// Create package meta-data objects
		theboardPackage.createPackageContents();
		thestatePackage.createPackageContents();
		theactionPackage.createPackageContents();

		// Initialize created meta-data
		theboardPackage.initializePackageContents();
		thestatePackage.initializePackageContents();
		theactionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theboardPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(boardPackage.eNS_URI, theboardPackage);
		return theboardPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBoard() {
		return boardEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBoard_Countries() {
		return (EReference)boardEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBoard_Borders() {
		return (EReference)boardEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBoard_Continents() {
		return (EReference)boardEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBoard_AdditionalTroops() {
		return (EAttribute)boardEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBoard_Cards() {
		return (EReference)boardEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBoard_InitialTroops() {
		return (EAttribute)boardEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCountry() {
		return countryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCountry_Border() {
		return (EReference)countryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCountry_Name() {
		return (EAttribute)countryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCountry_Continent() {
		return (EReference)countryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBorder() {
		return borderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBorder_Country() {
		return (EReference)borderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBorder_Type() {
		return (EAttribute)borderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContinent() {
		return continentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getContinent_Country() {
		return (EReference)continentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContinent_AdditionalTroops() {
		return (EAttribute)continentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContinent_Name() {
		return (EAttribute)continentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCard() {
		return cardEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCard_Symbol() {
		return (EAttribute)cardEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCard_Country() {
		return (EReference)cardEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBorderType() {
		return borderTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCardSymbol() {
		return cardSymbolEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boardFactory getboardFactory() {
		return (boardFactory)getEFactoryInstance();
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
		boardEClass = createEClass(BOARD);
		createEReference(boardEClass, BOARD__COUNTRIES);
		createEReference(boardEClass, BOARD__BORDERS);
		createEReference(boardEClass, BOARD__CONTINENTS);
		createEAttribute(boardEClass, BOARD__ADDITIONAL_TROOPS);
		createEReference(boardEClass, BOARD__CARDS);
		createEAttribute(boardEClass, BOARD__INITIAL_TROOPS);

		countryEClass = createEClass(COUNTRY);
		createEReference(countryEClass, COUNTRY__BORDER);
		createEAttribute(countryEClass, COUNTRY__NAME);
		createEReference(countryEClass, COUNTRY__CONTINENT);

		borderEClass = createEClass(BORDER);
		createEReference(borderEClass, BORDER__COUNTRY);
		createEAttribute(borderEClass, BORDER__TYPE);

		continentEClass = createEClass(CONTINENT);
		createEReference(continentEClass, CONTINENT__COUNTRY);
		createEAttribute(continentEClass, CONTINENT__ADDITIONAL_TROOPS);
		createEAttribute(continentEClass, CONTINENT__NAME);

		cardEClass = createEClass(CARD);
		createEAttribute(cardEClass, CARD__SYMBOL);
		createEReference(cardEClass, CARD__COUNTRY);

		// Create enums
		borderTypeEEnum = createEEnum(BORDER_TYPE);
		cardSymbolEEnum = createEEnum(CARD_SYMBOL);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(boardEClass, Board.class, "Board", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBoard_Countries(), this.getCountry(), null, "countries", null, 2, -1, Board.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getBoard_Countries().getEKeys().add(this.getCountry_Name());
		initEReference(getBoard_Borders(), this.getBorder(), null, "borders", null, 1, -1, Board.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBoard_Continents(), this.getContinent(), null, "continents", null, 1, -1, Board.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		getBoard_Continents().getEKeys().add(this.getContinent_Name());
		initEAttribute(getBoard_AdditionalTroops(), ecorePackage.getEInt(), "additionalTroops", null, 5, -1, Board.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBoard_Cards(), this.getCard(), null, "cards", null, 1, -1, Board.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBoard_InitialTroops(), ecorePackage.getEInt(), "initialTroops", null, 2, -1, Board.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(countryEClass, Country.class, "Country", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCountry_Border(), this.getBorder(), this.getBorder_Country(), "border", null, 1, -1, Country.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCountry_Name(), ecorePackage.getEString(), "name", null, 1, 1, Country.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCountry_Continent(), this.getContinent(), this.getContinent_Country(), "continent", null, 1, 1, Country.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(borderEClass, Border.class, "Border", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBorder_Country(), this.getCountry(), this.getCountry_Border(), "country", null, 2, 2, Border.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBorder_Type(), this.getBorderType(), "type", "LAND", 1, 1, Border.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(continentEClass, Continent.class, "Continent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContinent_Country(), this.getCountry(), this.getCountry_Continent(), "country", null, 1, -1, Continent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContinent_AdditionalTroops(), ecorePackage.getEInt(), "additionalTroops", null, 1, 1, Continent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContinent_Name(), ecorePackage.getEString(), "name", null, 1, 1, Continent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cardEClass, Card.class, "Card", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCard_Symbol(), this.getCardSymbol(), "symbol", null, 1, 1, Card.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCard_Country(), this.getCountry(), null, "country", null, 0, 1, Card.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(borderTypeEEnum, BorderType.class, "BorderType");
		addEEnumLiteral(borderTypeEEnum, BorderType.LAND);
		addEEnumLiteral(borderTypeEEnum, BorderType.SEA);

		initEEnum(cardSymbolEEnum, CardSymbol.class, "CardSymbol");
		addEEnumLiteral(cardSymbolEEnum, CardSymbol.INFANTERIE);
		addEEnumLiteral(cardSymbolEEnum, CardSymbol.KAVALLERIE);
		addEEnumLiteral(cardSymbolEEnum, CardSymbol.ARTILLERIE);
		addEEnumLiteral(cardSymbolEEnum, CardSymbol.JOKER);

		// Create resource
		createResource(eNS_URI);
	}

} //boardPackageImpl
