/**
 */
package risiko.gamestate;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import risiko.board.Country;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This Class stores the actual game state. While the classes in the board package have no fields to store information such as which player owns wich country and how many troops are placed where. This class stores which players turn it is, and which actions he is allowed to do.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.gamestate.State#getPlayers <em>Players</em>}</li>
 *   <li>{@link risiko.gamestate.State#getCountryState <em>Country State</em>}</li>
 *   <li>{@link risiko.gamestate.State#getTurn <em>Turn</em>}</li>
 *   <li>{@link risiko.gamestate.State#getPhase <em>Phase</em>}</li>
 *   <li>{@link risiko.gamestate.State#getState <em>State</em>}</li>
 *   <li>{@link risiko.gamestate.State#getTroopsToSet <em>Troops To Set</em>}</li>
 *   <li>{@link risiko.gamestate.State#isConqueredCountry <em>Conquered Country</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.gamestate.statePackage#getState()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='countryToStateMapIsAccurate'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL countryToStateMapIsAccurate='true'"
 * @generated
 */
public interface State extends EObject {
	/**
	 * Returns the value of the '<em><b>Players</b></em>' containment reference list.
	 * The list contents are of type {@link risiko.gamestate.Player}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Players</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Players</em>' containment reference list.
	 * @see risiko.gamestate.statePackage#getState_Players()
	 * @model containment="true"
	 * @generated
	 */
	EList<Player> getPlayers();

	/**
	 * Returns the value of the '<em><b>Country State</b></em>' map.
	 * The key is of type {@link risiko.board.Country},
	 * and the value is of type {@link risiko.gamestate.CountryState},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Country State</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Country State</em>' map.
	 * @see risiko.gamestate.statePackage#getState_CountryState()
	 * @model mapType="risiko.gamestate.CountryToCountryStateMap<risiko.board.Country, risiko.gamestate.CountryState>"
	 * @generated
	 */
	EMap<Country, CountryState> getCountryState();

	/**
	 * Returns the value of the '<em><b>Turn</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Turn</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Turn</em>' reference.
	 * @see #setTurn(Player)
	 * @see risiko.gamestate.statePackage#getState_Turn()
	 * @model resolveProxies="false"
	 * @generated
	 */
	Player getTurn();

	/**
	 * Sets the value of the '{@link risiko.gamestate.State#getTurn <em>Turn</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Turn</em>' reference.
	 * @see #getTurn()
	 * @generated
	 */
	void setTurn(Player value);

	/**
	 * Returns the value of the '<em><b>Phase</b></em>' attribute.
	 * The literals are from the enumeration {@link risiko.gamestate.TurnPhase}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Phase</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Phase</em>' attribute.
	 * @see risiko.gamestate.TurnPhase
	 * @see #setPhase(TurnPhase)
	 * @see risiko.gamestate.statePackage#getState_Phase()
	 * @model
	 * @generated
	 */
	TurnPhase getPhase();

	/**
	 * Sets the value of the '{@link risiko.gamestate.State#getPhase <em>Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Phase</em>' attribute.
	 * @see risiko.gamestate.TurnPhase
	 * @see #getPhase()
	 * @generated
	 */
	void setPhase(TurnPhase value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * The literals are from the enumeration {@link risiko.gamestate.GameState}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see risiko.gamestate.GameState
	 * @see #setState(GameState)
	 * @see risiko.gamestate.statePackage#getState_State()
	 * @model required="true"
	 * @generated
	 */
	GameState getState();

	/**
	 * Sets the value of the '{@link risiko.gamestate.State#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see risiko.gamestate.GameState
	 * @see #getState()
	 * @generated
	 */
	void setState(GameState value);

	/**
	 * Returns the value of the '<em><b>Troops To Set</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Troops To Set</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Troops To Set</em>' attribute.
	 * @see #setTroopsToSet(int)
	 * @see risiko.gamestate.statePackage#getState_TroopsToSet()
	 * @model default="0"
	 * @generated
	 */
	int getTroopsToSet();

	/**
	 * Sets the value of the '{@link risiko.gamestate.State#getTroopsToSet <em>Troops To Set</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Troops To Set</em>' attribute.
	 * @see #getTroopsToSet()
	 * @generated
	 */
	void setTroopsToSet(int value);

	/**
	 * Returns the value of the '<em><b>Conquered Country</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conquered Country</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conquered Country</em>' attribute.
	 * @see #setConqueredCountry(boolean)
	 * @see risiko.gamestate.statePackage#getState_ConqueredCountry()
	 * @model default="false"
	 * @generated
	 */
	boolean isConqueredCountry();

	/**
	 * Sets the value of the '{@link risiko.gamestate.State#isConqueredCountry <em>Conquered Country</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conquered Country</em>' attribute.
	 * @see #isConqueredCountry()
	 * @generated
	 */
	void setConqueredCountry(boolean value);

} // State
