/**
 */
package risiko.board;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Continent</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A Continent is a Group of Countries. If a Player holds all Countries in one Continent he gains additional Troops at the beginning of his turn each round.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.board.Continent#getCountry <em>Country</em>}</li>
 *   <li>{@link risiko.board.Continent#getAdditionalTroops <em>Additional Troops</em>}</li>
 *   <li>{@link risiko.board.Continent#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.board.boardPackage#getContinent()
 * @model
 * @generated
 */
public interface Continent extends EObject {
	/**
	 * Returns the value of the '<em><b>Country</b></em>' reference list.
	 * The list contents are of type {@link risiko.board.Country}.
	 * It is bidirectional and its opposite is '{@link risiko.board.Country#getContinent <em>Continent</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Country</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Country</em>' reference list.
	 * @see risiko.board.boardPackage#getContinent_Country()
	 * @see risiko.board.Country#getContinent
	 * @model opposite="continent" required="true"
	 * @generated
	 */
	EList<Country> getCountry();

	/**
	 * Returns the value of the '<em><b>Additional Troops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Troops</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Troops</em>' attribute.
	 * @see #setAdditionalTroops(int)
	 * @see risiko.board.boardPackage#getContinent_AdditionalTroops()
	 * @model required="true"
	 * @generated
	 */
	int getAdditionalTroops();

	/**
	 * Sets the value of the '{@link risiko.board.Continent#getAdditionalTroops <em>Additional Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Additional Troops</em>' attribute.
	 * @see #getAdditionalTroops()
	 * @generated
	 */
	void setAdditionalTroops(int value);

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
	 * @see risiko.board.boardPackage#getContinent_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link risiko.board.Continent#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Continent
