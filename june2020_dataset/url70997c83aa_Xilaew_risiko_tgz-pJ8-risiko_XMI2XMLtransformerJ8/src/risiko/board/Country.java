/**
 */
package risiko.board;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Country</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Country is a Field on the Board. During the game each Country will be held by one Player and on each Country troops can be placed. Players can Attack other Countries if there is a Border from this Country to another one.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.board.Country#getBorder <em>Border</em>}</li>
 *   <li>{@link risiko.board.Country#getName <em>Name</em>}</li>
 *   <li>{@link risiko.board.Country#getContinent <em>Continent</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.board.boardPackage#getCountry()
 * @model
 * @generated
 */
public interface Country extends EObject {
	/**
	 * Returns the value of the '<em><b>Border</b></em>' reference list.
	 * The list contents are of type {@link risiko.board.Border}.
	 * It is bidirectional and its opposite is '{@link risiko.board.Border#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Border</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Border</em>' reference list.
	 * @see risiko.board.boardPackage#getCountry_Border()
	 * @see risiko.board.Border#getCountry
	 * @model opposite="country" required="true"
	 * @generated
	 */
	EList<Border> getBorder();

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
	 * @see risiko.board.boardPackage#getCountry_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link risiko.board.Country#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Continent</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link risiko.board.Continent#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Continent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Continent</em>' reference.
	 * @see #setContinent(Continent)
	 * @see risiko.board.boardPackage#getCountry_Continent()
	 * @see risiko.board.Continent#getCountry
	 * @model opposite="country" required="true"
	 * @generated
	 */
	Continent getContinent();

	/**
	 * Sets the value of the '{@link risiko.board.Country#getContinent <em>Continent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Continent</em>' reference.
	 * @see #getContinent()
	 * @generated
	 */
	void setContinent(Continent value);

} // Country
