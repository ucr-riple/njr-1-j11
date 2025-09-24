/**
 */
package risiko.gamestate;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import risiko.board.Card;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Player</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.gamestate.Player#getOwnedCountries <em>Owned Countries</em>}</li>
 *   <li>{@link risiko.gamestate.Player#getOwnedCards <em>Owned Cards</em>}</li>
 *   <li>{@link risiko.gamestate.Player#getName <em>Name</em>}</li>
 *   <li>{@link risiko.gamestate.Player#getTotalTroops <em>Total Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.gamestate.statePackage#getPlayer()
 * @model
 * @generated
 */
public interface Player extends EObject {
	/**
	 * Returns the value of the '<em><b>Owned Countries</b></em>' reference list.
	 * The list contents are of type {@link risiko.gamestate.CountryState}.
	 * It is bidirectional and its opposite is '{@link risiko.gamestate.CountryState#getPlayer <em>Player</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Countries</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Countries</em>' reference list.
	 * @see risiko.gamestate.statePackage#getPlayer_OwnedCountries()
	 * @see risiko.gamestate.CountryState#getPlayer
	 * @model opposite="player"
	 * @generated
	 */
	EList<CountryState> getOwnedCountries();

	/**
	 * Returns the value of the '<em><b>Owned Cards</b></em>' reference list.
	 * The list contents are of type {@link risiko.board.Card}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owned Cards</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owned Cards</em>' reference list.
	 * @see risiko.gamestate.statePackage#getPlayer_OwnedCards()
	 * @model
	 * @generated
	 */
	EList<Card> getOwnedCards();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see risiko.gamestate.statePackage#getPlayer_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link risiko.gamestate.Player#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Total Troops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Troops</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Troops</em>' attribute.
	 * @see #setTotalTroops(int)
	 * @see risiko.gamestate.statePackage#getPlayer_TotalTroops()
	 * @model volatile="true" derived="true"
	 * @generated
	 */
	int getTotalTroops();

	/**
	 * Sets the value of the '{@link risiko.gamestate.Player#getTotalTroops <em>Total Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Troops</em>' attribute.
	 * @see #getTotalTroops()
	 * @generated
	 */
	void setTotalTroops(int value);

} // Player
