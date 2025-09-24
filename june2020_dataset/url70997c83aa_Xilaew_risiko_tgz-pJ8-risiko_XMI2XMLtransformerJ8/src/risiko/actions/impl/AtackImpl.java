/**
 */
package risiko.actions.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import risiko.actions.Atack;
import risiko.actions.actionPackage;

import risiko.board.Border;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Atack</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.actions.impl.AtackImpl#getTroops <em>Troops</em>}</li>
 *   <li>{@link risiko.actions.impl.AtackImpl#getBorder <em>Border</em>}</li>
 *   <li>{@link risiko.actions.impl.AtackImpl#isDirection <em>Direction</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AtackImpl extends InGameActionImpl implements Atack {
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
	 * The cached value of the '{@link #getBorder() <em>Border</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBorder()
	 * @generated
	 * @ordered
	 */
	protected Border border;

	/**
	 * The default value of the '{@link #isDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirection()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DIRECTION_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirection()
	 * @generated
	 * @ordered
	 */
	protected boolean direction = DIRECTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AtackImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return actionPackage.Literals.ATACK;
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
			eNotify(new ENotificationImpl(this, Notification.SET, actionPackage.ATACK__TROOPS, oldTroops, troops));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Border getBorder() {
		if (border != null && border.eIsProxy()) {
			InternalEObject oldBorder = (InternalEObject)border;
			border = (Border)eResolveProxy(oldBorder);
			if (border != oldBorder) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, actionPackage.ATACK__BORDER, oldBorder, border));
			}
		}
		return border;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Border basicGetBorder() {
		return border;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBorder(Border newBorder) {
		Border oldBorder = border;
		border = newBorder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, actionPackage.ATACK__BORDER, oldBorder, border));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDirection() {
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirection(boolean newDirection) {
		boolean oldDirection = direction;
		direction = newDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, actionPackage.ATACK__DIRECTION, oldDirection, direction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case actionPackage.ATACK__TROOPS:
				return getTroops();
			case actionPackage.ATACK__BORDER:
				if (resolve) return getBorder();
				return basicGetBorder();
			case actionPackage.ATACK__DIRECTION:
				return isDirection();
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
			case actionPackage.ATACK__TROOPS:
				setTroops((Integer)newValue);
				return;
			case actionPackage.ATACK__BORDER:
				setBorder((Border)newValue);
				return;
			case actionPackage.ATACK__DIRECTION:
				setDirection((Boolean)newValue);
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
			case actionPackage.ATACK__TROOPS:
				setTroops(TROOPS_EDEFAULT);
				return;
			case actionPackage.ATACK__BORDER:
				setBorder((Border)null);
				return;
			case actionPackage.ATACK__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
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
			case actionPackage.ATACK__TROOPS:
				return troops != TROOPS_EDEFAULT;
			case actionPackage.ATACK__BORDER:
				return border != null;
			case actionPackage.ATACK__DIRECTION:
				return direction != DIRECTION_EDEFAULT;
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
		result.append(", direction: ");
		result.append(direction);
		result.append(')');
		return result.toString();
	}

} //AtackImpl
