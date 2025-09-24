/**
 */
package risiko.board;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Card</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.board.Card#getSymbol <em>Symbol</em>}</li>
 *   <li>{@link risiko.board.Card#getCountry <em>Country</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.board.boardPackage#getCard()
 * @model
 * @generated
 */
public interface Card extends EObject {
	/**
	 * Returns the value of the '<em><b>Symbol</b></em>' attribute.
	 * The literals are from the enumeration {@link risiko.board.CardSymbol}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbol</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbol</em>' attribute.
	 * @see risiko.board.CardSymbol
	 * @see #setSymbol(CardSymbol)
	 * @see risiko.board.boardPackage#getCard_Symbol()
	 * @model required="true"
	 * @generated
	 */
	CardSymbol getSymbol();

	/**
	 * Sets the value of the '{@link risiko.board.Card#getSymbol <em>Symbol</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbol</em>' attribute.
	 * @see risiko.board.CardSymbol
	 * @see #getSymbol()
	 * @generated
	 */
	void setSymbol(CardSymbol value);

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
	 * @see risiko.board.boardPackage#getCard_Country()
	 * @model
	 * @generated
	 */
	Country getCountry();

	/**
	 * Sets the value of the '{@link risiko.board.Card#getCountry <em>Country</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Country</em>' reference.
	 * @see #getCountry()
	 * @generated
	 */
	void setCountry(Country value);

} // Card
