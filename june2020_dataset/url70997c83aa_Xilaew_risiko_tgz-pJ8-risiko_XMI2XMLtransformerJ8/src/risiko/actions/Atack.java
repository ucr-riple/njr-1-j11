/**
 */
package risiko.actions;

import risiko.board.Border;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Atack</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.actions.Atack#getTroops <em>Troops</em>}</li>
 *   <li>{@link risiko.actions.Atack#getBorder <em>Border</em>}</li>
 *   <li>{@link risiko.actions.Atack#isDirection <em>Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.actions.actionPackage#getAtack()
 * @model
 * @generated
 */
public interface Atack extends InGameAction {
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
	 * @see risiko.actions.actionPackage#getAtack_Troops()
	 * @model id="true" required="true"
	 * @generated
	 */
	int getTroops();

	/**
	 * Sets the value of the '{@link risiko.actions.Atack#getTroops <em>Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Troops</em>' attribute.
	 * @see #getTroops()
	 * @generated
	 */
	void setTroops(int value);

	/**
	 * Returns the value of the '<em><b>Border</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Border</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Border</em>' reference.
	 * @see #setBorder(Border)
	 * @see risiko.actions.actionPackage#getAtack_Border()
	 * @model required="true"
	 * @generated
	 */
	Border getBorder();

	/**
	 * Sets the value of the '{@link risiko.actions.Atack#getBorder <em>Border</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Border</em>' reference.
	 * @see #getBorder()
	 * @generated
	 */
	void setBorder(Border value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The default value is <code>"true"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see #setDirection(boolean)
	 * @see risiko.actions.actionPackage#getAtack_Direction()
	 * @model default="true" required="true"
	 * @generated
	 */
	boolean isDirection();

	/**
	 * Sets the value of the '{@link risiko.actions.Atack#isDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see #isDirection()
	 * @generated
	 */
	void setDirection(boolean value);

} // Atack
