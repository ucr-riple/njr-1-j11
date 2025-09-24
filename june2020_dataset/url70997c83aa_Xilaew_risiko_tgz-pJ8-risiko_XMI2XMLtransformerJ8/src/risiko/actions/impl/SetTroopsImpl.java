/**
 */
package risiko.actions.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import risiko.actions.SetTroops;
import risiko.actions.actionPackage;

import risiko.board.Country;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Set Troops</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.actions.impl.SetTroopsImpl#getCountry <em>Country</em>}</li>
 *   <li>{@link risiko.actions.impl.SetTroopsImpl#getTroops <em>Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SetTroopsImpl extends InGameActionImpl implements SetTroops {
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
	protected SetTroopsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return actionPackage.Literals.SET_TROOPS;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, actionPackage.SET_TROOPS__COUNTRY, oldCountry, country));
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
			eNotify(new ENotificationImpl(this, Notification.SET, actionPackage.SET_TROOPS__COUNTRY, oldCountry, country));
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
			eNotify(new ENotificationImpl(this, Notification.SET, actionPackage.SET_TROOPS__TROOPS, oldTroops, troops));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case actionPackage.SET_TROOPS__COUNTRY:
				if (resolve) return getCountry();
				return basicGetCountry();
			case actionPackage.SET_TROOPS__TROOPS:
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
			case actionPackage.SET_TROOPS__COUNTRY:
				setCountry((Country)newValue);
				return;
			case actionPackage.SET_TROOPS__TROOPS:
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
			case actionPackage.SET_TROOPS__COUNTRY:
				setCountry((Country)null);
				return;
			case actionPackage.SET_TROOPS__TROOPS:
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
			case actionPackage.SET_TROOPS__COUNTRY:
				return country != null;
			case actionPackage.SET_TROOPS__TROOPS:
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

} //SetTroopsImpl
