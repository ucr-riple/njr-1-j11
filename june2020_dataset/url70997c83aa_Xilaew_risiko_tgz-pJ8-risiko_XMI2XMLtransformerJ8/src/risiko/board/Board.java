/**
 */
package risiko.board;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Board</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.board.Board#getCountries <em>Countries</em>}</li>
 *   <li>{@link risiko.board.Board#getBorders <em>Borders</em>}</li>
 *   <li>{@link risiko.board.Board#getContinents <em>Continents</em>}</li>
 *   <li>{@link risiko.board.Board#getAdditionalTroops <em>Additional Troops</em>}</li>
 *   <li>{@link risiko.board.Board#getCards <em>Cards</em>}</li>
 *   <li>{@link risiko.board.Board#getInitialTroops <em>Initial Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.board.boardPackage#getBoard()
 * @model
 * @generated
 */
public interface Board extends EObject {
	/**
	 * Returns the value of the '<em><b>Countries</b></em>' containment reference list.
	 * The list contents are of type {@link risiko.board.Country}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Countries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Countries</em>' containment reference list.
	 * @see risiko.board.boardPackage#getBoard_Countries()
	 * @model containment="true" keys="name" lower="2"
	 * @generated
	 */
	EList<Country> getCountries();

	/**
	 * Returns the value of the '<em><b>Borders</b></em>' containment reference list.
	 * The list contents are of type {@link risiko.board.Border}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Borders</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Borders</em>' containment reference list.
	 * @see risiko.board.boardPackage#getBoard_Borders()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Border> getBorders();

	/**
	 * Returns the value of the '<em><b>Continents</b></em>' containment reference list.
	 * The list contents are of type {@link risiko.board.Continent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Continents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Continents</em>' containment reference list.
	 * @see risiko.board.boardPackage#getBoard_Continents()
	 * @model containment="true" keys="name" required="true"
	 * @generated
	 */
	EList<Continent> getContinents();

	/**
	 * Returns the value of the '<em><b>Additional Troops</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Additional Troops</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Additional Troops</em>' attribute list.
	 * @see risiko.board.boardPackage#getBoard_AdditionalTroops()
	 * @model unique="false" lower="5"
	 * @generated
	 */
	EList<Integer> getAdditionalTroops();

	/**
	 * Returns the value of the '<em><b>Cards</b></em>' containment reference list.
	 * The list contents are of type {@link risiko.board.Card}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cards</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cards</em>' containment reference list.
	 * @see risiko.board.boardPackage#getBoard_Cards()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Card> getCards();

	/**
	 * Returns the value of the '<em><b>Initial Troops</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.Integer}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Troops</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial Troops</em>' attribute list.
	 * @see risiko.board.boardPackage#getBoard_InitialTroops()
	 * @model unique="false" lower="2"
	 * @generated
	 */
	EList<Integer> getInitialTroops();

} // Board
