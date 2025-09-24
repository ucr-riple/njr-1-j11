/**
 */
package risiko.actions.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import risiko.actions.CoinCards;
import risiko.actions.actionPackage;

import risiko.board.Card;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Coin Cards</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.actions.impl.CoinCardsImpl#getCards <em>Cards</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CoinCardsImpl extends InGameActionImpl implements CoinCards {
	/**
	 * The cached value of the '{@link #getCards() <em>Cards</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCards()
	 * @generated
	 * @ordered
	 */
	protected EList<Card> cards;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CoinCardsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return actionPackage.Literals.COIN_CARDS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Card> getCards() {
		if (cards == null) {
			cards = new EObjectResolvingEList<Card>(Card.class, this, actionPackage.COIN_CARDS__CARDS);
		}
		return cards;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case actionPackage.COIN_CARDS__CARDS:
				return getCards();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case actionPackage.COIN_CARDS__CARDS:
				getCards().clear();
				getCards().addAll((Collection<? extends Card>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case actionPackage.COIN_CARDS__CARDS:
				getCards().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case actionPackage.COIN_CARDS__CARDS:
				return cards != null && !cards.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CoinCardsImpl
