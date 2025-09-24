/**
 */
package risiko.gamestate;

import org.eclipse.emf.ecore.EObject;

import risiko.board.Country;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Country State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.gamestate.CountryState#getPlayer <em>Player</em>}</li>
 *   <li>{@link risiko.gamestate.CountryState#getCountry <em>Country</em>}</li>
 *   <li>{@link risiko.gamestate.CountryState#getTroops <em>Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.gamestate.statePackage#getCountryState()
 * @model
 * @generated
 */
public interface CountryState extends EObject {
	/**
	 * Returns the value of the '<em><b>Player</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link risiko.gamestate.Player#getOwnedCountries <em>Owned Countries</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Player</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Player</em>' reference.
	 * @see #setPlayer(Player)
	 * @see risiko.gamestate.statePackage#getCountryState_Player()
	 * @see risiko.gamestate.Player#getOwnedCountries
	 * @model opposite="ownedCountries" required="true"
	 * @generated
	 */
	Player getPlayer();

	/**
	 * Sets the value of the '{@link risiko.gamestate.CountryState#getPlayer <em>Player</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Player</em>' reference.
	 * @see #getPlayer()
	 * @generated
	 */
	void setPlayer(Player value);

	/**
	 * Returns the value of the '<em><b>Country</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Country</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Country</em>' reference.
	 * @see #setCountry(Country)
	 * @see risiko.gamestate.statePackage#getCountryState_Country()
	 * @model required="true"
	 * @generated
	 */
	Country getCountry();

	/**
	 * Sets the value of the '{@link risiko.gamestate.CountryState#getCountry <em>Country</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Country</em>' reference.
	 * @see #getCountry()
	 * @generated
	 */
	void setCountry(Country value);

	/**
	 * Returns the value of the '<em><b>Troops</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Troops</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Troops</em>' attribute.
	 * @see #setTroops(int)
	 * @see risiko.gamestate.statePackage#getCountryState_Troops()
	 * @model default="1" required="true"
	 * @generated
	 */
	int getTroops();

	/**
	 * Sets the value of the '{@link risiko.gamestate.CountryState#getTroops <em>Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Troops</em>' attribute.
	 * @see #getTroops()
	 * @generated
	 */
	void setTroops(int value);

} // CountryState
