/**
 */
package risiko.gamestate.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import risiko.board.Country;

import risiko.gamestate.CountryState;
import risiko.gamestate.GameState;
import risiko.gamestate.Player;
import risiko.gamestate.State;
import risiko.gamestate.TurnPhase;
import risiko.gamestate.statePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.gamestate.impl.StateImpl#getPlayers <em>Players</em>}</li>
 *   <li>{@link risiko.gamestate.impl.StateImpl#getCountryState <em>Country State</em>}</li>
 *   <li>{@link risiko.gamestate.impl.StateImpl#getTurn <em>Turn</em>}</li>
 *   <li>{@link risiko.gamestate.impl.StateImpl#getPhase <em>Phase</em>}</li>
 *   <li>{@link risiko.gamestate.impl.StateImpl#getState <em>State</em>}</li>
 *   <li>{@link risiko.gamestate.impl.StateImpl#getTroopsToSet <em>Troops To Set</em>}</li>
 *   <li>{@link risiko.gamestate.impl.StateImpl#isConqueredCountry <em>Conquered Country</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateImpl extends MinimalEObjectImpl.Container implements State {
	/**
	 * The cached value of the '{@link #getPlayers() <em>Players</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlayers()
	 * @generated
	 * @ordered
	 */
	protected EList<Player> players;

	/**
	 * The cached value of the '{@link #getCountryState() <em>Country State</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountryState()
	 * @generated
	 * @ordered
	 */
	protected EMap<Country, CountryState> countryState;

	/**
	 * The cached value of the '{@link #getTurn() <em>Turn</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTurn()
	 * @generated
	 * @ordered
	 */
	protected Player turn;

	/**
	 * The default value of the '{@link #getPhase() <em>Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhase()
	 * @generated
	 * @ordered
	 */
	protected static final TurnPhase PHASE_EDEFAULT = TurnPhase.GAIN_TROOPS;

	/**
	 * The cached value of the '{@link #getPhase() <em>Phase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhase()
	 * @generated
	 * @ordered
	 */
	protected TurnPhase phase = PHASE_EDEFAULT;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final GameState STATE_EDEFAULT = GameState.ACCEPTING_PLAYERS;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected GameState state = STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTroopsToSet() <em>Troops To Set</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTroopsToSet()
	 * @generated
	 * @ordered
	 */
	protected static final int TROOPS_TO_SET_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTroopsToSet() <em>Troops To Set</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTroopsToSet()
	 * @generated
	 * @ordered
	 */
	protected int troopsToSet = TROOPS_TO_SET_EDEFAULT;

	/**
	 * The default value of the '{@link #isConqueredCountry() <em>Conquered Country</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConqueredCountry()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONQUERED_COUNTRY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConqueredCountry() <em>Conquered Country</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConqueredCountry()
	 * @generated
	 * @ordered
	 */
	protected boolean conqueredCountry = CONQUERED_COUNTRY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return statePackage.Literals.STATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Player> getPlayers() {
		if (players == null) {
			players = new EObjectContainmentEList<Player>(Player.class, this, statePackage.STATE__PLAYERS);
		}
		return players;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Country, CountryState> getCountryState() {
		if (countryState == null) {
			countryState = new EcoreEMap<Country,CountryState>(statePackage.Literals.COUNTRY_TO_COUNTRY_STATE_MAP, CountryToCountryStateMapImpl.class, this, statePackage.STATE__COUNTRY_STATE);
		}
		return countryState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Player getTurn() {
		return turn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTurn(Player newTurn) {
		Player oldTurn = turn;
		turn = newTurn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statePackage.STATE__TURN, oldTurn, turn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TurnPhase getPhase() {
		return phase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPhase(TurnPhase newPhase) {
		TurnPhase oldPhase = phase;
		phase = newPhase == null ? PHASE_EDEFAULT : newPhase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statePackage.STATE__PHASE, oldPhase, phase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(GameState newState) {
		GameState oldState = state;
		state = newState == null ? STATE_EDEFAULT : newState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statePackage.STATE__STATE, oldState, state));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getTroopsToSet() {
		return troopsToSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTroopsToSet(int newTroopsToSet) {
		int oldTroopsToSet = troopsToSet;
		troopsToSet = newTroopsToSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statePackage.STATE__TROOPS_TO_SET, oldTroopsToSet, troopsToSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConqueredCountry() {
		return conqueredCountry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConqueredCountry(boolean newConqueredCountry) {
		boolean oldConqueredCountry = conqueredCountry;
		conqueredCountry = newConqueredCountry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, statePackage.STATE__CONQUERED_COUNTRY, oldConqueredCountry, conqueredCountry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case statePackage.STATE__PLAYERS:
				return ((InternalEList<?>)getPlayers()).basicRemove(otherEnd, msgs);
			case statePackage.STATE__COUNTRY_STATE:
				return ((InternalEList<?>)getCountryState()).basicRemove(otherEnd, msgs);
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
			case statePackage.STATE__PLAYERS:
				return getPlayers();
			case statePackage.STATE__COUNTRY_STATE:
				if (coreType) return getCountryState();
				else return getCountryState().map();
			case statePackage.STATE__TURN:
				return getTurn();
			case statePackage.STATE__PHASE:
				return getPhase();
			case statePackage.STATE__STATE:
				return getState();
			case statePackage.STATE__TROOPS_TO_SET:
				return getTroopsToSet();
			case statePackage.STATE__CONQUERED_COUNTRY:
				return isConqueredCountry();
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
			case statePackage.STATE__PLAYERS:
				getPlayers().clear();
				getPlayers().addAll((Collection<? extends Player>)newValue);
				return;
			case statePackage.STATE__COUNTRY_STATE:
				((EStructuralFeature.Setting)getCountryState()).set(newValue);
				return;
			case statePackage.STATE__TURN:
				setTurn((Player)newValue);
				return;
			case statePackage.STATE__PHASE:
				setPhase((TurnPhase)newValue);
				return;
			case statePackage.STATE__STATE:
				setState((GameState)newValue);
				return;
			case statePackage.STATE__TROOPS_TO_SET:
				setTroopsToSet((Integer)newValue);
				return;
			case statePackage.STATE__CONQUERED_COUNTRY:
				setConqueredCountry((Boolean)newValue);
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
			case statePackage.STATE__PLAYERS:
				getPlayers().clear();
				return;
			case statePackage.STATE__COUNTRY_STATE:
				getCountryState().clear();
				return;
			case statePackage.STATE__TURN:
				setTurn((Player)null);
				return;
			case statePackage.STATE__PHASE:
				setPhase(PHASE_EDEFAULT);
				return;
			case statePackage.STATE__STATE:
				setState(STATE_EDEFAULT);
				return;
			case statePackage.STATE__TROOPS_TO_SET:
				setTroopsToSet(TROOPS_TO_SET_EDEFAULT);
				return;
			case statePackage.STATE__CONQUERED_COUNTRY:
				setConqueredCountry(CONQUERED_COUNTRY_EDEFAULT);
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
			case statePackage.STATE__PLAYERS:
				return players != null && !players.isEmpty();
			case statePackage.STATE__COUNTRY_STATE:
				return countryState != null && !countryState.isEmpty();
			case statePackage.STATE__TURN:
				return turn != null;
			case statePackage.STATE__PHASE:
				return phase != PHASE_EDEFAULT;
			case statePackage.STATE__STATE:
				return state != STATE_EDEFAULT;
			case statePackage.STATE__TROOPS_TO_SET:
				return troopsToSet != TROOPS_TO_SET_EDEFAULT;
			case statePackage.STATE__CONQUERED_COUNTRY:
				return conqueredCountry != CONQUERED_COUNTRY_EDEFAULT;
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
		result.append(" (phase: ");
		result.append(phase);
		result.append(", state: ");
		result.append(state);
		result.append(", troopsToSet: ");
		result.append(troopsToSet);
		result.append(", conqueredCountry: ");
		result.append(conqueredCountry);
		result.append(')');
		return result.toString();
	}

} //StateImpl
