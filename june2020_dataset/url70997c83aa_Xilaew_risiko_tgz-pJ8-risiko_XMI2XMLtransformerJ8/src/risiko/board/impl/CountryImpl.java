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

import risiko.board.Border;
import risiko.board.Continent;
import risiko.board.Country;
import risiko.board.boardPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Country</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.board.impl.CountryImpl#getBorder <em>Border</em>}</li>
 *   <li>{@link risiko.board.impl.CountryImpl#getName <em>Name</em>}</li>
 *   <li>{@link risiko.board.impl.CountryImpl#getContinent <em>Continent</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CountryImpl extends MinimalEObjectImpl.Container implements Country {
	/**
	 * The cached value of the '{@link #getBorder() <em>Border</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBorder()
	 * @generated
	 * @ordered
	 */
	protected EList<Border> border;

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
	 * The cached value of the '{@link #getContinent() <em>Continent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContinent()
	 * @generated
	 * @ordered
	 */
	protected Continent continent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CountryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return boardPackage.Literals.COUNTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Border> getBorder() {
		if (border == null) {
			border = new EObjectWithInverseResolvingEList.ManyInverse<Border>(Border.class, this, boardPackage.COUNTRY__BORDER, boardPackage.BORDER__COUNTRY);
		}
		return border;
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
			eNotify(new ENotificationImpl(this, Notification.SET, boardPackage.COUNTRY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Continent getContinent() {
		if (continent != null && continent.eIsProxy()) {
			InternalEObject oldContinent = (InternalEObject)continent;
			continent = (Continent)eResolveProxy(oldContinent);
			if (continent != oldContinent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, boardPackage.COUNTRY__CONTINENT, oldContinent, continent));
			}
		}
		return continent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Continent basicGetContinent() {
		return continent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContinent(Continent newContinent, NotificationChain msgs) {
		Continent oldContinent = continent;
		continent = newContinent;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, boardPackage.COUNTRY__CONTINENT, oldContinent, newContinent);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContinent(Continent newContinent) {
		if (newContinent != continent) {
			NotificationChain msgs = null;
			if (continent != null)
				msgs = ((InternalEObject)continent).eInverseRemove(this, boardPackage.CONTINENT__COUNTRY, Continent.class, msgs);
			if (newContinent != null)
				msgs = ((InternalEObject)newContinent).eInverseAdd(this, boardPackage.CONTINENT__COUNTRY, Continent.class, msgs);
			msgs = basicSetContinent(newContinent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, boardPackage.COUNTRY__CONTINENT, newContinent, newContinent));
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
			case boardPackage.COUNTRY__BORDER:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getBorder()).basicAdd(otherEnd, msgs);
			case boardPackage.COUNTRY__CONTINENT:
				if (continent != null)
					msgs = ((InternalEObject)continent).eInverseRemove(this, boardPackage.CONTINENT__COUNTRY, Continent.class, msgs);
				return basicSetContinent((Continent)otherEnd, msgs);
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
			case boardPackage.COUNTRY__BORDER:
				return ((InternalEList<?>)getBorder()).basicRemove(otherEnd, msgs);
			case boardPackage.COUNTRY__CONTINENT:
				return basicSetContinent(null, msgs);
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
			case boardPackage.COUNTRY__BORDER:
				return getBorder();
			case boardPackage.COUNTRY__NAME:
				return getName();
			case boardPackage.COUNTRY__CONTINENT:
				if (resolve) return getContinent();
				return basicGetContinent();
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
			case boardPackage.COUNTRY__BORDER:
				getBorder().clear();
				getBorder().addAll((Collection<? extends Border>)newValue);
				return;
			case boardPackage.COUNTRY__NAME:
				setName((String)newValue);
				return;
			case boardPackage.COUNTRY__CONTINENT:
				setContinent((Continent)newValue);
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
			case boardPackage.COUNTRY__BORDER:
				getBorder().clear();
				return;
			case boardPackage.COUNTRY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case boardPackage.COUNTRY__CONTINENT:
				setContinent((Continent)null);
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
			case boardPackage.COUNTRY__BORDER:
				return border != null && !border.isEmpty();
			case boardPackage.COUNTRY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case boardPackage.COUNTRY__CONTINENT:
				return continent != null;
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

} //CountryImpl
