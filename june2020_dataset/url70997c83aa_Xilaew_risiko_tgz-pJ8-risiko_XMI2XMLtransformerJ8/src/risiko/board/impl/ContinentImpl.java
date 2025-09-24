/**
 */
package risiko.board.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import risiko.board.Continent;
import risiko.board.Country;
import risiko.board.boardPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Continent</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.board.impl.ContinentImpl#getCountry <em>Country</em>}</li>
 *   <li>{@link risiko.board.impl.ContinentImpl#getAdditionalTroops <em>Additional Troops</em>}</li>
 *   <li>{@link risiko.board.impl.ContinentImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ContinentImpl extends MinimalEObjectImpl.Container implements Continent {
	/**
	 * The cached value of the '{@link #getCountry() <em>Country</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountry()
	 * @generated
	 * @ordered
	 */
	protected EList<Country> country;

	/**
	 * The default value of the '{@link #getAdditionalTroops() <em>Additional Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalTroops()
	 * @generated
	 * @ordered
	 */
	protected static final int ADDITIONAL_TROOPS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAdditionalTroops() <em>Additional Troops</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalTroops()
	 * @generated
	 * @ordered
	 */
	protected int additionalTroops = ADDITIONAL_TROOPS_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContinentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return boardPackage.Literals.CONTINENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Country> getCountry() {
		if (country == null) {
			country = new EObjectWithInverseResolvingEList<Country>(Country.class, this, boardPackage.CONTINENT__COUNTRY, boardPackage.COUNTRY__CONTINENT);
		}
		return country;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getAdditionalTroops() {
		return additionalTroops;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdditionalTroops(int newAdditionalTroops) {
		int oldAdditionalTroops = additionalTroops;
		additionalTroops = newAdditionalTroops;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, boardPackage.CONTINENT__ADDITIONAL_TROOPS, oldAdditionalTroops, additionalTroops));
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
			eNotify(new ENotificationImpl(this, Notification.SET, boardPackage.CONTINENT__NAME, oldName, name));
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
			case boardPackage.CONTINENT__COUNTRY:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCountry()).basicAdd(otherEnd, msgs);
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
			case boardPackage.CONTINENT__COUNTRY:
				return ((InternalEList<?>)getCountry()).basicRemove(otherEnd, msgs);
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
			case boardPackage.CONTINENT__COUNTRY:
				return getCountry();
			case boardPackage.CONTINENT__ADDITIONAL_TROOPS:
				return getAdditionalTroops();
			case boardPackage.CONTINENT__NAME:
				return getName();
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
			case boardPackage.CONTINENT__COUNTRY:
				getCountry().clear();
				getCountry().addAll((Collection<? extends Country>)newValue);
				return;
			case boardPackage.CONTINENT__ADDITIONAL_TROOPS:
				setAdditionalTroops((Integer)newValue);
				return;
			case boardPackage.CONTINENT__NAME:
				setName((String)newValue);
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
			case boardPackage.CONTINENT__COUNTRY:
				getCountry().clear();
				return;
			case boardPackage.CONTINENT__ADDITIONAL_TROOPS:
				setAdditionalTroops(ADDITIONAL_TROOPS_EDEFAULT);
				return;
			case boardPackage.CONTINENT__NAME:
				setName(NAME_EDEFAULT);
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
			case boardPackage.CONTINENT__COUNTRY:
				return country != null && !country.isEmpty();
			case boardPackage.CONTINENT__ADDITIONAL_TROOPS:
				return additionalTroops != ADDITIONAL_TROOPS_EDEFAULT;
			case boardPackage.CONTINENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (additionalTroops: ");
		result.append(additionalTroops);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ContinentImpl
