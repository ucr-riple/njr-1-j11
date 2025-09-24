/**
 */
package risiko.gamestate.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import risiko.board.Country;

import risiko.gamestate.CountryState;
import risiko.gamestate.Player;
import risiko.gamestate.statePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Country State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.gamestate.impl.CountryStateImpl#getPlayer <em>Player</em>}</li>
 *   <li>{@link risiko.gamestate.impl.CountryStateImpl#getCountry <em>Country</em>}</li>
 *   <li>{@link risiko.gamestate.impl.CountryStateImpl#getTroops <em>Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CountryStateImpl extends MinimalEObjectImpl.Container implements CountryState {
	/**
	 * The cached value of the '{@link #getPlayer() <em>Player</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlayer()
	 * @generated
	 * @ordered
	 */
	protected Player player;

	/**
	 * The cached value of the '{@link #getCountry() <em>Country</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountry()
	 * @generated
	 * @ordered
	 */
	protected Country country;

	/**
	 * The default value of the '{@link #getTroops() <em>Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTroops()
	 * @generated
	 * @ordered
	 */
	protected static final int TROOPS_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getTroops() <em>Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTroops()
	 * @generated
	 * @ordered
	 */
	protected int troops = TROOPS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CountryStateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return statePackage.Literals.COUNTRY_STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Player getPlayer() {
		if (player != null && player.eIsProxy()) {
			InternalEObject oldPlayer = (InternalEObject)player;
			player = (Player)eResolveProxy(oldPlayer);
			if (player != oldPlayer) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, statePackage.COUNTRY_STATE__PLAYER, oldPlayer, player));
			}
		}
		return player;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Player basicGetPlayer() {
		return player;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlayer(Player newPlayer, NotificationChain msgs) {
		Player oldPlayer = player;
		player = newPlayer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, statePackage.COUNTRY_STATE__PLAYER, oldPlayer, newPlayer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlayer(Player newPlayer) {
		if (newPlayer != player) {
			NotificationChain msgs = null;
			if (player != null)
				msgs = ((InternalEObject)player).eInverseRemove(this, statePackage.PLAYER__OWNED_COUNTRIES, Player.class, msgs);
			if (newPlayer != null)
				msgs = ((InternalEObject)newPlayer).eInverseAdd(this, statePackage.PLAYER__OWNED_COUNTRIES, Player.class, msgs);
			msgs = basicSetPlayer(newPlayer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statePackage.COUNTRY_STATE__PLAYER, newPlayer, newPlayer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Country getCountry() {
		if (country != null && country.eIsProxy()) {
			InternalEObject oldCountry = (InternalEObject)country;
			country = (Country)eResolveProxy(oldCountry);
			if (country != oldCountry) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, statePackage.COUNTRY_STATE__COUNTRY, oldCountry, country));
			}
		}
		return country;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Country basicGetCountry() {
		return country;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCountry(Country newCountry) {
		Country oldCountry = country;
		country = newCountry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statePackage.COUNTRY_STATE__COUNTRY, oldCountry, country));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTroops() {
		return troops;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTroops(int newTroops) {
		int oldTroops = troops;
		troops = newTroops;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statePackage.COUNTRY_STATE__TROOPS, oldTroops, troops));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case statePackage.COUNTRY_STATE__PLAYER:
				if (player != null)
					msgs = ((InternalEObject)player).eInverseRemove(this, statePackage.PLAYER__OWNED_COUNTRIES, Player.class, msgs);
				return basicSetPlayer((Player)otherEnd, msgs);
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
			case statePackage.COUNTRY_STATE__PLAYER:
				return basicSetPlayer(null, msgs);
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
			case statePackage.COUNTRY_STATE__PLAYER:
				if (resolve) return getPlayer();
				return basicGetPlayer();
			case statePackage.COUNTRY_STATE__COUNTRY:
				if (resolve) return getCountry();
				return basicGetCountry();
			case statePackage.COUNTRY_STATE__TROOPS:
				return getTroops();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case statePackage.COUNTRY_STATE__PLAYER:
				setPlayer((Player)newValue);
				return;
			case statePackage.COUNTRY_STATE__COUNTRY:
				setCountry((Country)newValue);
				return;
			case statePackage.COUNTRY_STATE__TROOPS:
				setTroops((Integer)newValue);
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
			case statePackage.COUNTRY_STATE__PLAYER:
				setPlayer((Player)null);
				return;
			case statePackage.COUNTRY_STATE__COUNTRY:
				setCountry((Country)null);
				return;
			case statePackage.COUNTRY_STATE__TROOPS:
				setTroops(TROOPS_EDEFAULT);
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
			case statePackage.COUNTRY_STATE__PLAYER:
				return player != null;
			case statePackage.COUNTRY_STATE__COUNTRY:
				return country != null;
			case statePackage.COUNTRY_STATE__TROOPS:
				return troops != TROOPS_EDEFAULT;
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
		result.append(" (troops: ");
		result.append(troops);
		result.append(')');
		return result.toString();
	}

} //CountryStateImpl
