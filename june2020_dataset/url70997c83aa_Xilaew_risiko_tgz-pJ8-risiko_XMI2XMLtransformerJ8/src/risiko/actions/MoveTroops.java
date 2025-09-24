/**
 */
package risiko.actions;

import risiko.board.Country;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Move Troops</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.actions.MoveTroops#getFrom <em>From</em>}</li>
 *   <li>{@link risiko.actions.MoveTroops#getTo <em>To</em>}</li>
 *   <li>{@link risiko.actions.MoveTroops#getTroops <em>Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.actions.actionPackage#getMoveTroops()
 * @model
 * @generated
 */
public interface MoveTroops extends InGameAction {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Country)
	 * @see risiko.actions.actionPackage#getMoveTroops_From()
	 * @model required="true"
	 * @generated
	 */
	Country getFrom();

	/**
	 * Sets the value of the '{@link risiko.actions.MoveTroops#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Country value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Country)
	 * @see risiko.actions.actionPackage#getMoveTroops_To()
	 * @model required="true"
	 * @generated
	 */
	Country getTo();

	/**
	 * Sets the value of the '{@link risiko.actions.MoveTroops#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Country value);

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
	 * @see risiko.actions.actionPackage#getMoveTroops_Troops()
	 * @model required="true"
	 * @generated
	 */
	int getTroops();

	/**
	 * Sets the value of the '{@link risiko.actions.MoveTroops#getTroops <em>Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Troops</em>' attribute.
	 * @see #getTroops()
	 * @generated
	 */
	void setTroops(int value);

} // MoveTroops
