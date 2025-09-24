/**
 */
package risiko.board;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * <!-- begin-model-doc -->
 * New documentation note
 * <!-- end-model-doc -->
 * @see risiko.board.boardFactory
 * @model kind="package"
 * @generated
 */
public interface boardPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "board";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/risiko/board";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "board";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	boardPackage eINSTANCE = risiko.board.impl.boardPackageImpl.init();

	/**
	 * The meta object id for the '{@link risiko.board.impl.BoardImpl <em>Board</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.board.impl.BoardImpl
	 * @see risiko.board.impl.boardPackageImpl#getBoard()
	 * @generated
	 */
	int BOARD = 0;

	/**
	 * The feature id for the '<em><b>Countries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD__COUNTRIES = 0;

	/**
	 * The feature id for the '<em><b>Borders</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD__BORDERS = 1;

	/**
	 * The feature id for the '<em><b>Continents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD__CONTINENTS = 2;

	/**
	 * The feature id for the '<em><b>Additional Troops</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD__ADDITIONAL_TROOPS = 3;

	/**
	 * The feature id for the '<em><b>Cards</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD__CARDS = 4;

	/**
	 * The feature id for the '<em><b>Initial Troops</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD__INITIAL_TROOPS = 5;

	/**
	 * The number of structural features of the '<em>Board</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Board</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOARD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.board.impl.CountryImpl <em>Country</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.board.impl.CountryImpl
	 * @see risiko.board.impl.boardPackageImpl#getCountry()
	 * @generated
	 */
	int COUNTRY = 1;

	/**
	 * The feature id for the '<em><b>Border</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY__BORDER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY__NAME = 1;

	/**
	 * The feature id for the '<em><b>Continent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY__CONTINENT = 2;

	/**
	 * The number of structural features of the '<em>Country</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Country</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.board.impl.BorderImpl <em>Border</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.board.impl.BorderImpl
	 * @see risiko.board.impl.boardPackageImpl#getBorder()
	 * @generated
	 */
	int BORDER = 2;

	/**
	 * The feature id for the '<em><b>Country</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BORDER__COUNTRY = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BORDER__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Border</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BORDER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Border</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BORDER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.board.impl.ContinentImpl <em>Continent</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.board.impl.ContinentImpl
	 * @see risiko.board.impl.boardPackageImpl#getContinent()
	 * @generated
	 */
	int CONTINENT = 3;

	/**
	 * The feature id for the '<em><b>Country</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT__COUNTRY = 0;

	/**
	 * The feature id for the '<em><b>Additional Troops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT__ADDITIONAL_TROOPS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT__NAME = 2;

	/**
	 * The number of structural features of the '<em>Continent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Continent</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTINENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.board.impl.CardImpl <em>Card</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.board.impl.CardImpl
	 * @see risiko.board.impl.boardPackageImpl#getCard()
	 * @generated
	 */
	int CARD = 4;

	/**
	 * The feature id for the '<em><b>Symbol</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD__SYMBOL = 0;

	/**
	 * The feature id for the '<em><b>Country</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD__COUNTRY = 1;

	/**
	 * The number of structural features of the '<em>Card</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Card</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CARD_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.board.BorderType <em>Border Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.board.BorderType
	 * @see risiko.board.impl.boardPackageImpl#getBorderType()
	 * @generated
	 */
	int BORDER_TYPE = 5;

	/**
	 * The meta object id for the '{@link risiko.board.CardSymbol <em>Card Symbol</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.board.CardSymbol
	 * @see risiko.board.impl.boardPackageImpl#getCardSymbol()
	 * @generated
	 */
	int CARD_SYMBOL = 6;


	/**
	 * Returns the meta object for class '{@link risiko.board.Board <em>Board</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Board</em>'.
	 * @see risiko.board.Board
	 * @generated
	 */
	EClass getBoard();

	/**
	 * Returns the meta object for the containment reference list '{@link risiko.board.Board#getCountries <em>Countries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Countries</em>'.
	 * @see risiko.board.Board#getCountries()
	 * @see #getBoard()
	 * @generated
	 */
	EReference getBoard_Countries();

	/**
	 * Returns the meta object for the containment reference list '{@link risiko.board.Board#getBorders <em>Borders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Borders</em>'.
	 * @see risiko.board.Board#getBorders()
	 * @see #getBoard()
	 * @generated
	 */
	EReference getBoard_Borders();

	/**
	 * Returns the meta object for the containment reference list '{@link risiko.board.Board#getContinents <em>Continents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Continents</em>'.
	 * @see risiko.board.Board#getContinents()
	 * @see #getBoard()
	 * @generated
	 */
	EReference getBoard_Continents();

	/**
	 * Returns the meta object for the attribute list '{@link risiko.board.Board#getAdditionalTroops <em>Additional Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Additional Troops</em>'.
	 * @see risiko.board.Board#getAdditionalTroops()
	 * @see #getBoard()
	 * @generated
	 */
	EAttribute getBoard_AdditionalTroops();

	/**
	 * Returns the meta object for the containment reference list '{@link risiko.board.Board#getCards <em>Cards</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cards</em>'.
	 * @see risiko.board.Board#getCards()
	 * @see #getBoard()
	 * @generated
	 */
	EReference getBoard_Cards();

	/**
	 * Returns the meta object for the attribute list '{@link risiko.board.Board#getInitialTroops <em>Initial Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Initial Troops</em>'.
	 * @see risiko.board.Board#getInitialTroops()
	 * @see #getBoard()
	 * @generated
	 */
	EAttribute getBoard_InitialTroops();

	/**
	 * Returns the meta object for class '{@link risiko.board.Country <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Country</em>'.
	 * @see risiko.board.Country
	 * @generated
	 */
	EClass getCountry();

	/**
	 * Returns the meta object for the reference list '{@link risiko.board.Country#getBorder <em>Border</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Border</em>'.
	 * @see risiko.board.Country#getBorder()
	 * @see #getCountry()
	 * @generated
	 */
	EReference getCountry_Border();

	/**
	 * Returns the meta object for the attribute '{@link risiko.board.Country#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see risiko.board.Country#getName()
	 * @see #getCountry()
	 * @generated
	 */
	EAttribute getCountry_Name();

	/**
	 * Returns the meta object for the reference '{@link risiko.board.Country#getContinent <em>Continent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Continent</em>'.
	 * @see risiko.board.Country#getContinent()
	 * @see #getCountry()
	 * @generated
	 */
	EReference getCountry_Continent();

	/**
	 * Returns the meta object for class '{@link risiko.board.Border <em>Border</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Border</em>'.
	 * @see risiko.board.Border
	 * @generated
	 */
	EClass getBorder();

	/**
	 * Returns the meta object for the reference list '{@link risiko.board.Border#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Country</em>'.
	 * @see risiko.board.Border#getCountry()
	 * @see #getBorder()
	 * @generated
	 */
	EReference getBorder_Country();

	/**
	 * Returns the meta object for the attribute '{@link risiko.board.Border#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see risiko.board.Border#getType()
	 * @see #getBorder()
	 * @generated
	 */
	EAttribute getBorder_Type();

	/**
	 * Returns the meta object for class '{@link risiko.board.Continent <em>Continent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Continent</em>'.
	 * @see risiko.board.Continent
	 * @generated
	 */
	EClass getContinent();

	/**
	 * Returns the meta object for the reference list '{@link risiko.board.Continent#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Country</em>'.
	 * @see risiko.board.Continent#getCountry()
	 * @see #getContinent()
	 * @generated
	 */
	EReference getContinent_Country();

	/**
	 * Returns the meta object for the attribute '{@link risiko.board.Continent#getAdditionalTroops <em>Additional Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Additional Troops</em>'.
	 * @see risiko.board.Continent#getAdditionalTroops()
	 * @see #getContinent()
	 * @generated
	 */
	EAttribute getContinent_AdditionalTroops();

	/**
	 * Returns the meta object for the attribute '{@link risiko.board.Continent#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see risiko.board.Continent#getName()
	 * @see #getContinent()
	 * @generated
	 */
	EAttribute getContinent_Name();

	/**
	 * Returns the meta object for class '{@link risiko.board.Card <em>Card</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Card</em>'.
	 * @see risiko.board.Card
	 * @generated
	 */
	EClass getCard();

	/**
	 * Returns the meta object for the attribute '{@link risiko.board.Card#getSymbol <em>Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Symbol</em>'.
	 * @see risiko.board.Card#getSymbol()
	 * @see #getCard()
	 * @generated
	 */
	EAttribute getCard_Symbol();

	/**
	 * Returns the meta object for the reference '{@link risiko.board.Card#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Country</em>'.
	 * @see risiko.board.Card#getCountry()
	 * @see #getCard()
	 * @generated
	 */
	EReference getCard_Country();

	/**
	 * Returns the meta object for enum '{@link risiko.board.BorderType <em>Border Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Border Type</em>'.
	 * @see risiko.board.BorderType
	 * @generated
	 */
	EEnum getBorderType();

	/**
	 * Returns the meta object for enum '{@link risiko.board.CardSymbol <em>Card Symbol</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Card Symbol</em>'.
	 * @see risiko.board.CardSymbol
	 * @generated
	 */
	EEnum getCardSymbol();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	boardFactory getboardFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link risiko.board.impl.BoardImpl <em>Board</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.board.impl.BoardImpl
		 * @see risiko.board.impl.boardPackageImpl#getBoard()
		 * @generated
		 */
		EClass BOARD = eINSTANCE.getBoard();

		/**
		 * The meta object literal for the '<em><b>Countries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOARD__COUNTRIES = eINSTANCE.getBoard_Countries();

		/**
		 * The meta object literal for the '<em><b>Borders</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOARD__BORDERS = eINSTANCE.getBoard_Borders();

		/**
		 * The meta object literal for the '<em><b>Continents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOARD__CONTINENTS = eINSTANCE.getBoard_Continents();

		/**
		 * The meta object literal for the '<em><b>Additional Troops</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOARD__ADDITIONAL_TROOPS = eINSTANCE.getBoard_AdditionalTroops();

		/**
		 * The meta object literal for the '<em><b>Cards</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOARD__CARDS = eINSTANCE.getBoard_Cards();

		/**
		 * The meta object literal for the '<em><b>Initial Troops</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOARD__INITIAL_TROOPS = eINSTANCE.getBoard_InitialTroops();

		/**
		 * The meta object literal for the '{@link risiko.board.impl.CountryImpl <em>Country</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.board.impl.CountryImpl
		 * @see risiko.board.impl.boardPackageImpl#getCountry()
		 * @generated
		 */
		EClass COUNTRY = eINSTANCE.getCountry();

		/**
		 * The meta object literal for the '<em><b>Border</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUNTRY__BORDER = eINSTANCE.getCountry_Border();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COUNTRY__NAME = eINSTANCE.getCountry_Name();

		/**
		 * The meta object literal for the '<em><b>Continent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUNTRY__CONTINENT = eINSTANCE.getCountry_Continent();

		/**
		 * The meta object literal for the '{@link risiko.board.impl.BorderImpl <em>Border</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.board.impl.BorderImpl
		 * @see risiko.board.impl.boardPackageImpl#getBorder()
		 * @generated
		 */
		EClass BORDER = eINSTANCE.getBorder();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BORDER__COUNTRY = eINSTANCE.getBorder_Country();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BORDER__TYPE = eINSTANCE.getBorder_Type();

		/**
		 * The meta object literal for the '{@link risiko.board.impl.ContinentImpl <em>Continent</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.board.impl.ContinentImpl
		 * @see risiko.board.impl.boardPackageImpl#getContinent()
		 * @generated
		 */
		EClass CONTINENT = eINSTANCE.getContinent();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTINENT__COUNTRY = eINSTANCE.getContinent_Country();

		/**
		 * The meta object literal for the '<em><b>Additional Troops</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTINENT__ADDITIONAL_TROOPS = eINSTANCE.getContinent_AdditionalTroops();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTINENT__NAME = eINSTANCE.getContinent_Name();

		/**
		 * The meta object literal for the '{@link risiko.board.impl.CardImpl <em>Card</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.board.impl.CardImpl
		 * @see risiko.board.impl.boardPackageImpl#getCard()
		 * @generated
		 */
		EClass CARD = eINSTANCE.getCard();

		/**
		 * The meta object literal for the '<em><b>Symbol</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CARD__SYMBOL = eINSTANCE.getCard_Symbol();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CARD__COUNTRY = eINSTANCE.getCard_Country();

		/**
		 * The meta object literal for the '{@link risiko.board.BorderType <em>Border Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.board.BorderType
		 * @see risiko.board.impl.boardPackageImpl#getBorderType()
		 * @generated
		 */
		EEnum BORDER_TYPE = eINSTANCE.getBorderType();

		/**
		 * The meta object literal for the '{@link risiko.board.CardSymbol <em>Card Symbol</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.board.CardSymbol
		 * @see risiko.board.impl.boardPackageImpl#getCardSymbol()
		 * @generated
		 */
		EEnum CARD_SYMBOL = eINSTANCE.getCardSymbol();

	}

} //boardPackage
