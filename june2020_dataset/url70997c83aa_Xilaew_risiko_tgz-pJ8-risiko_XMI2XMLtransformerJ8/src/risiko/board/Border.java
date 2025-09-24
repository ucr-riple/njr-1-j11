/**
 */
package risiko.board;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Border</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.board.Border#getCountry <em>Country</em>}</li>
 *   <li>{@link risiko.board.Border#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.board.boardPackage#getBorder()
 * @model
 * @generated
 */
public interface Border extends EObject {
	/**
	 * Returns the value of the '<em><b>Country</b></em>' reference list.
	 * The list contents are of type {@link risiko.board.Country}.
	 * It is bidirectional and its opposite is '{@link risiko.board.Country#getBorder <em>Border</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Country</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Country</em>' reference list.
	 * @see risiko.board.boardPackage#getBorder_Country()
	 * @see risiko.board.Country#getBorder
	 * @model opposite="border" lower="2" upper="2"
	 * @generated
	 */
	EList<Country> getCountry();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>"LAND"</code>.
	 * The literals are from the enumeration {@link risiko.board.BorderType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see risiko.board.BorderType
	 * @see #setType(BorderType)
	 * @see risiko.board.boardPackage#getBorder_Type()
	 * @model default="LAND" required="true"
	 * @generated
	 */
	BorderType getType();

	/**
	 * Sets the value of the '{@link risiko.board.Border#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see risiko.board.BorderType
	 * @see #getType()
	 * @generated
	 */
	void setType(BorderType value);

} // Border
