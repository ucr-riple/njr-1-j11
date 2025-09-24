/**
 */
package risiko.gamestate;

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
 * @see risiko.gamestate.stateFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL'"
 * @generated
 */
public interface statePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "gamestate";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/risiko/gamestate";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "gamestate";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	statePackage eINSTANCE = risiko.gamestate.impl.statePackageImpl.init();

	/**
	 * The meta object id for the '{@link risiko.gamestate.impl.PlayerImpl <em>Player</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.gamestate.impl.PlayerImpl
	 * @see risiko.gamestate.impl.statePackageImpl#getPlayer()
	 * @generated
	 */
	int PLAYER = 0;

	/**
	 * The feature id for the '<em><b>Owned Countries</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAYER__OWNED_COUNTRIES = 0;

	/**
	 * The feature id for the '<em><b>Owned Cards</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAYER__OWNED_CARDS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAYER__NAME = 2;

	/**
	 * The feature id for the '<em><b>Total Troops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAYER__TOTAL_TROOPS = 3;

	/**
	 * The number of structural features of the '<em>Player</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAYER_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Player</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAYER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.gamestate.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.gamestate.impl.StateImpl
	 * @see risiko.gamestate.impl.statePackageImpl#getState()
	 * @generated
	 */
	int STATE = 1;

	/**
	 * The feature id for the '<em><b>Players</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PLAYERS = 0;

	/**
	 * The feature id for the '<em><b>Country State</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__COUNTRY_STATE = 1;

	/**
	 * The feature id for the '<em><b>Turn</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TURN = 2;

	/**
	 * The feature id for the '<em><b>Phase</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PHASE = 3;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__STATE = 4;

	/**
	 * The feature id for the '<em><b>Troops To Set</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TROOPS_TO_SET = 5;

	/**
	 * The feature id for the '<em><b>Conquered Country</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__CONQUERED_COUNTRY = 6;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.gamestate.impl.CountryStateImpl <em>Country State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.gamestate.impl.CountryStateImpl
	 * @see risiko.gamestate.impl.statePackageImpl#getCountryState()
	 * @generated
	 */
	int COUNTRY_STATE = 2;

	/**
	 * The feature id for the '<em><b>Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_STATE__PLAYER = 0;

	/**
	 * The feature id for the '<em><b>Country</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_STATE__COUNTRY = 1;

	/**
	 * The feature id for the '<em><b>Troops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_STATE__TROOPS = 2;

	/**
	 * The number of structural features of the '<em>Country State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_STATE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Country State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_STATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.gamestate.impl.CountryToCountryStateMapImpl <em>Country To Country State Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.gamestate.impl.CountryToCountryStateMapImpl
	 * @see risiko.gamestate.impl.statePackageImpl#getCountryToCountryStateMap()
	 * @generated
	 */
	int COUNTRY_TO_COUNTRY_STATE_MAP = 3;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_TO_COUNTRY_STATE_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_TO_COUNTRY_STATE_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Country To Country State Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_TO_COUNTRY_STATE_MAP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Country To Country State Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUNTRY_TO_COUNTRY_STATE_MAP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.gamestate.TurnPhase <em>Turn Phase</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.gamestate.TurnPhase
	 * @see risiko.gamestate.impl.statePackageImpl#getTurnPhase()
	 * @generated
	 */
	int TURN_PHASE = 4;

	/**
	 * The meta object id for the '{@link risiko.gamestate.GameState <em>Game State</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.gamestate.GameState
	 * @see risiko.gamestate.impl.statePackageImpl#getGameState()
	 * @generated
	 */
	int GAME_STATE = 5;


	/**
	 * Returns the meta object for class '{@link risiko.gamestate.Player <em>Player</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Player</em>'.
	 * @see risiko.gamestate.Player
	 * @generated
	 */
	EClass getPlayer();

	/**
	 * Returns the meta object for the reference list '{@link risiko.gamestate.Player#getOwnedCountries <em>Owned Countries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Owned Countries</em>'.
	 * @see risiko.gamestate.Player#getOwnedCountries()
	 * @see #getPlayer()
	 * @generated
	 */
	EReference getPlayer_OwnedCountries();

	/**
	 * Returns the meta object for the reference list '{@link risiko.gamestate.Player#getOwnedCards <em>Owned Cards</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Owned Cards</em>'.
	 * @see risiko.gamestate.Player#getOwnedCards()
	 * @see #getPlayer()
	 * @generated
	 */
	EReference getPlayer_OwnedCards();

	/**
	 * Returns the meta object for the attribute '{@link risiko.gamestate.Player#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see risiko.gamestate.Player#getName()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_Name();

	/**
	 * Returns the meta object for the attribute '{@link risiko.gamestate.Player#getTotalTroops <em>Total Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Troops</em>'.
	 * @see risiko.gamestate.Player#getTotalTroops()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_TotalTroops();

	/**
	 * Returns the meta object for class '{@link risiko.gamestate.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see risiko.gamestate.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the containment reference list '{@link risiko.gamestate.State#getPlayers <em>Players</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Players</em>'.
	 * @see risiko.gamestate.State#getPlayers()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Players();

	/**
	 * Returns the meta object for the map '{@link risiko.gamestate.State#getCountryState <em>Country State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Country State</em>'.
	 * @see risiko.gamestate.State#getCountryState()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_CountryState();

	/**
	 * Returns the meta object for the reference '{@link risiko.gamestate.State#getTurn <em>Turn</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Turn</em>'.
	 * @see risiko.gamestate.State#getTurn()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Turn();

	/**
	 * Returns the meta object for the attribute '{@link risiko.gamestate.State#getPhase <em>Phase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Phase</em>'.
	 * @see risiko.gamestate.State#getPhase()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_Phase();

	/**
	 * Returns the meta object for the attribute '{@link risiko.gamestate.State#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see risiko.gamestate.State#getState()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_State();

	/**
	 * Returns the meta object for the attribute '{@link risiko.gamestate.State#getTroopsToSet <em>Troops To Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Troops To Set</em>'.
	 * @see risiko.gamestate.State#getTroopsToSet()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_TroopsToSet();

	/**
	 * Returns the meta object for the attribute '{@link risiko.gamestate.State#isConqueredCountry <em>Conquered Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Conquered Country</em>'.
	 * @see risiko.gamestate.State#isConqueredCountry()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_ConqueredCountry();

	/**
	 * Returns the meta object for class '{@link risiko.gamestate.CountryState <em>Country State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Country State</em>'.
	 * @see risiko.gamestate.CountryState
	 * @generated
	 */
	EClass getCountryState();

	/**
	 * Returns the meta object for the reference '{@link risiko.gamestate.CountryState#getPlayer <em>Player</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Player</em>'.
	 * @see risiko.gamestate.CountryState#getPlayer()
	 * @see #getCountryState()
	 * @generated
	 */
	EReference getCountryState_Player();

	/**
	 * Returns the meta object for the reference '{@link risiko.gamestate.CountryState#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Country</em>'.
	 * @see risiko.gamestate.CountryState#getCountry()
	 * @see #getCountryState()
	 * @generated
	 */
	EReference getCountryState_Country();

	/**
	 * Returns the meta object for the attribute '{@link risiko.gamestate.CountryState#getTroops <em>Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Troops</em>'.
	 * @see risiko.gamestate.CountryState#getTroops()
	 * @see #getCountryState()
	 * @generated
	 */
	EAttribute getCountryState_Troops();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Country To Country State Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Country To Country State Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="risiko.board.Country" keyRequired="true"
	 *        valueType="risiko.gamestate.CountryState" valueContainment="true" valueRequired="true"
	 * @generated
	 */
	EClass getCountryToCountryStateMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getCountryToCountryStateMap()
	 * @generated
	 */
	EReference getCountryToCountryStateMap_Key();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getCountryToCountryStateMap()
	 * @generated
	 */
	EReference getCountryToCountryStateMap_Value();

	/**
	 * Returns the meta object for enum '{@link risiko.gamestate.TurnPhase <em>Turn Phase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Turn Phase</em>'.
	 * @see risiko.gamestate.TurnPhase
	 * @generated
	 */
	EEnum getTurnPhase();

	/**
	 * Returns the meta object for enum '{@link risiko.gamestate.GameState <em>Game State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Game State</em>'.
	 * @see risiko.gamestate.GameState
	 * @generated
	 */
	EEnum getGameState();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	stateFactory getstateFactory();

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
		 * The meta object literal for the '{@link risiko.gamestate.impl.PlayerImpl <em>Player</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.gamestate.impl.PlayerImpl
		 * @see risiko.gamestate.impl.statePackageImpl#getPlayer()
		 * @generated
		 */
		EClass PLAYER = eINSTANCE.getPlayer();

		/**
		 * The meta object literal for the '<em><b>Owned Countries</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAYER__OWNED_COUNTRIES = eINSTANCE.getPlayer_OwnedCountries();

		/**
		 * The meta object literal for the '<em><b>Owned Cards</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAYER__OWNED_CARDS = eINSTANCE.getPlayer_OwnedCards();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAYER__NAME = eINSTANCE.getPlayer_Name();

		/**
		 * The meta object literal for the '<em><b>Total Troops</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAYER__TOTAL_TROOPS = eINSTANCE.getPlayer_TotalTroops();

		/**
		 * The meta object literal for the '{@link risiko.gamestate.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.gamestate.impl.StateImpl
		 * @see risiko.gamestate.impl.statePackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Players</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__PLAYERS = eINSTANCE.getState_Players();

		/**
		 * The meta object literal for the '<em><b>Country State</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__COUNTRY_STATE = eINSTANCE.getState_CountryState();

		/**
		 * The meta object literal for the '<em><b>Turn</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__TURN = eINSTANCE.getState_Turn();

		/**
		 * The meta object literal for the '<em><b>Phase</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__PHASE = eINSTANCE.getState_Phase();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__STATE = eINSTANCE.getState_State();

		/**
		 * The meta object literal for the '<em><b>Troops To Set</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__TROOPS_TO_SET = eINSTANCE.getState_TroopsToSet();

		/**
		 * The meta object literal for the '<em><b>Conquered Country</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__CONQUERED_COUNTRY = eINSTANCE.getState_ConqueredCountry();

		/**
		 * The meta object literal for the '{@link risiko.gamestate.impl.CountryStateImpl <em>Country State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.gamestate.impl.CountryStateImpl
		 * @see risiko.gamestate.impl.statePackageImpl#getCountryState()
		 * @generated
		 */
		EClass COUNTRY_STATE = eINSTANCE.getCountryState();

		/**
		 * The meta object literal for the '<em><b>Player</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUNTRY_STATE__PLAYER = eINSTANCE.getCountryState_Player();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUNTRY_STATE__COUNTRY = eINSTANCE.getCountryState_Country();

		/**
		 * The meta object literal for the '<em><b>Troops</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COUNTRY_STATE__TROOPS = eINSTANCE.getCountryState_Troops();

		/**
		 * The meta object literal for the '{@link risiko.gamestate.impl.CountryToCountryStateMapImpl <em>Country To Country State Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.gamestate.impl.CountryToCountryStateMapImpl
		 * @see risiko.gamestate.impl.statePackageImpl#getCountryToCountryStateMap()
		 * @generated
		 */
		EClass COUNTRY_TO_COUNTRY_STATE_MAP = eINSTANCE.getCountryToCountryStateMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUNTRY_TO_COUNTRY_STATE_MAP__KEY = eINSTANCE.getCountryToCountryStateMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUNTRY_TO_COUNTRY_STATE_MAP__VALUE = eINSTANCE.getCountryToCountryStateMap_Value();

		/**
		 * The meta object literal for the '{@link risiko.gamestate.TurnPhase <em>Turn Phase</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.gamestate.TurnPhase
		 * @see risiko.gamestate.impl.statePackageImpl#getTurnPhase()
		 * @generated
		 */
		EEnum TURN_PHASE = eINSTANCE.getTurnPhase();

		/**
		 * The meta object literal for the '{@link risiko.gamestate.GameState <em>Game State</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.gamestate.GameState
		 * @see risiko.gamestate.impl.statePackageImpl#getGameState()
		 * @generated
		 */
		EEnum GAME_STATE = eINSTANCE.getGameState();

	}

} //statePackage
