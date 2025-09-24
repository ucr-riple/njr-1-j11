/**
 */
package risiko.gamestate.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import risiko.board.Card;

import risiko.gamestate.CountryState;
import risiko.gamestate.Player;
import risiko.gamestate.statePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Player</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.gamestate.impl.PlayerImpl#getOwnedCountries <em>Owned Countries</em>}</li>
 *   <li>{@link risiko.gamestate.impl.PlayerImpl#getOwnedCards <em>Owned Cards</em>}</li>
 *   <li>{@link risiko.gamestate.impl.PlayerImpl#getName <em>Name</em>}</li>
 *   <li>{@link risiko.gamestate.impl.PlayerImpl#getTotalTroops <em>Total Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PlayerImpl extends MinimalEObjectImpl.Container implements Player {
	/**
	 * The cached value of the '{@link #getOwnedCountries() <em>Owned Countries</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedCountries()
	 * @generated
	 * @ordered
	 */
	protected EList<CountryState> ownedCountries;

	/**
	 * The cached value of the '{@link #getOwnedCards() <em>Owned Cards</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedCards()
	 * @generated
	 * @ordered
	 */
	protected EList<Card> ownedCards;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTotalTroops() <em>Total Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalTroops()
	 * @generated
	 * @ordered
	 */
	protected static final int TOTAL_TROOPS_EDEFAULT = 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlayerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return statePackage.Literals.PLAYER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CountryState> getOwnedCountries() {
		if (ownedCountries == null) {
			ownedCountries = new EObjectWithInverseResolvingEList<CountryState>(CountryState.class, this, statePackage.PLAYER__OWNED_COUNTRIES, statePackage.COUNTRY_STATE__PLAYER);
		}
		return ownedCountries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Card> getOwnedCards() {
		if (ownedCards == null) {
			ownedCards = new EObjectResolvingEList<Card>(Card.class, this, statePackage.PLAYER__OWNED_CARDS);
		}
		return ownedCards;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statePackage.PLAYER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int getTotalTroops() {
		int totalTroops = 0;
		for (CountryState c : this.getOwnedCountries()){
			totalTroops += c.getTroops();
		}
		return totalTroops;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalTroops(int newTotalTroops) {
		// TODO: implement this method to set the 'Total Troops' attribute
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case statePackage.PLAYER__OWNED_COUNTRIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOwnedCountries()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case statePackage.PLAYER__OWNED_COUNTRIES:
				return ((InternalEList<?>)getOwnedCountries()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case statePackage.PLAYER__OWNED_COUNTRIES:
				return getOwnedCountries();
			case statePackage.PLAYER__OWNED_CARDS:
				return getOwnedCards();
			case statePackage.PLAYER__NAME:
				return getName();
			case statePackage.PLAYER__TOTAL_TROOPS:
				return getTotalTroops();
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
			case statePackage.PLAYER__OWNED_COUNTRIES:
				getOwnedCountries().clear();
				getOwnedCountries().addAll((Collection<? extends CountryState>)newValue);
				return;
			case statePackage.PLAYER__OWNED_CARDS:
				getOwnedCards().clear();
				getOwnedCards().addAll((Collection<? extends Card>)newValue);
				return;
			case statePackage.PLAYER__NAME:
				setName((String)newValue);
				return;
			case statePackage.PLAYER__TOTAL_TROOPS:
				setTotalTroops((Integer)newValue);
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
			case statePackage.PLAYER__OWNED_COUNTRIES:
				getOwnedCountries().clear();
				return;
			case statePackage.PLAYER__OWNED_CARDS:
				getOwnedCards().clear();
				return;
			case statePackage.PLAYER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case statePackage.PLAYER__TOTAL_TROOPS:
				setTotalTroops(TOTAL_TROOPS_EDEFAULT);
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
			case statePackage.PLAYER__OWNED_COUNTRIES:
				return ownedCountries != null && !ownedCountries.isEmpty();
			case statePackage.PLAYER__OWNED_CARDS:
				return ownedCards != null && !ownedCards.isEmpty();
			case statePackage.PLAYER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case statePackage.PLAYER__TOTAL_TROOPS:
				return getTotalTroops() != TOTAL_TROOPS_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //PlayerImpl
