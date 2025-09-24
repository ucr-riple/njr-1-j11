/**
 */
package risiko.actions;

import risiko.board.Country;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Set Troops</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.actions.SetTroops#getCountry <em>Country</em>}</li>
 *   <li>{@link risiko.actions.SetTroops#getTroops <em>Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.actions.actionPackage#getSetTroops()
 * @model
 * @generated
 */
public interface SetTroops extends InGameAction {
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
	 * @see risiko.actions.actionPackage#getSetTroops_Country()
	 * @model required="true"
	 * @generated
	 */
	Country getCountry();

	/**
	 * Sets the value of the '{@link risiko.actions.SetTroops#getCountry <em>Country</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Country</em>' reference.
	 * @see #getCountry()
	 * @generated
	 */
	void setCountry(Country value);

	/**
	 * Returns the value of the '<em><b>Troops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Troops</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Troops</em>' attribute.
	 * @see #setTroops(int)
	 * @see risiko.actions.actionPackage#getSetTroops_Troops()
	 * @model required="true"
	 * @generated
	 */
	int getTroops();

	/**
	 * Sets the value of the '{@link risiko.actions.SetTroops#getTroops <em>Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Troops</em>' attribute.
	 * @see #getTroops()
	 * @generated
	 */
	void setTroops(int value);

} // SetTroops
