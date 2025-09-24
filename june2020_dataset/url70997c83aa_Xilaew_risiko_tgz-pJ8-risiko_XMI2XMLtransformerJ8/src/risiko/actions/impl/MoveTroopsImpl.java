/**
 */
package risiko.actions.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import risiko.actions.MoveTroops;
import risiko.actions.actionPackage;

import risiko.board.Country;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Move Troops</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.actions.impl.MoveTroopsImpl#getFrom <em>From</em>}</li>
 *   <li>{@link risiko.actions.impl.MoveTroopsImpl#getTo <em>To</em>}</li>
 *   <li>{@link risiko.actions.impl.MoveTroopsImpl#getTroops <em>Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MoveTroopsImpl extends InGameActionImpl implements MoveTroops {
	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected Country from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected Country to;

	/**
	 * The default value of the '{@link #getTroops() <em>Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTroops()
	 * @generated
	 * @ordered
	 */
	protected static final int TROOPS_EDEFAULT = 0;

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
	protected MoveTroopsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return actionPackage.Literals.MOVE_TROOPS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Country getFrom() {
		if (from != null && from.eIsProxy()) {
			InternalEObject oldFrom = (InternalEObject)from;
			from = (Country)eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, actionPackage.MOVE_TROOPS__FROM, oldFrom, from));
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Country basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrom(Country newFrom) {
		Country oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, actionPackage.MOVE_TROOPS__FROM, oldFrom, from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Country getTo() {
		if (to != null && to.eIsProxy()) {
			InternalEObject oldTo = (InternalEObject)to;
			to = (Country)eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, actionPackage.MOVE_TROOPS__TO, oldTo, to));
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Country basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(Country newTo) {
		Country oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, actionPackage.MOVE_TROOPS__TO, oldTo, to));
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
			eNotify(new ENotificationImpl(this, Notification.SET, actionPackage.MOVE_TROOPS__TROOPS, oldTroops, troops));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case actionPackage.MOVE_TROOPS__FROM:
				if (resolve) return getFrom();
				return basicGetFrom();
			case actionPackage.MOVE_TROOPS__TO:
				if (resolve) return getTo();
				return basicGetTo();
			case actionPackage.MOVE_TROOPS__TROOPS:
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
			case actionPackage.MOVE_TROOPS__FROM:
				setFrom((Country)newValue);
				return;
			case actionPackage.MOVE_TROOPS__TO:
				setTo((Country)newValue);
				return;
			case actionPackage.MOVE_TROOPS__TROOPS:
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
			case actionPackage.MOVE_TROOPS__FROM:
				setFrom((Country)null);
				return;
			case actionPackage.MOVE_TROOPS__TO:
				setTo((Country)null);
				return;
			case actionPackage.MOVE_TROOPS__TROOPS:
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
			case actionPackage.MOVE_TROOPS__FROM:
				return from != null;
			case actionPackage.MOVE_TROOPS__TO:
				return to != null;
			case actionPackage.MOVE_TROOPS__TROOPS:
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

} //MoveTroopsImpl
