/**
 */
package risiko.actions;

import org.eclipse.emf.common.util.EList;

import risiko.board.Card;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Coin Cards</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.actions.CoinCards#getCards <em>Cards</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.actions.actionPackage#getCoinCards()
 * @model
 * @generated
 */
public interface CoinCards extends InGameAction {
	/**
	 * Returns the value of the '<em><b>Cards</b></em>' reference list.
	 * The list contents are of type {@link risiko.board.Card}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cards</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cards</em>' reference list.
	 * @see risiko.actions.actionPackage#getCoinCards_Cards()
	 * @model lower="3" upper="3"
	 * @generated
	 */
	EList<Card> getCards();

} // CoinCards
