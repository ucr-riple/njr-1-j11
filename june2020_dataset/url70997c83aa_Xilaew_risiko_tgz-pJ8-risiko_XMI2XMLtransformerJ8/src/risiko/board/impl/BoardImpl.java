/**
 */
package risiko.board.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import risiko.board.Board;
import risiko.board.Border;
import risiko.board.Card;
import risiko.board.Continent;
import risiko.board.Country;
import risiko.board.boardPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Board</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link risiko.board.impl.BoardImpl#getCountries <em>Countries</em>}</li>
 *   <li>{@link risiko.board.impl.BoardImpl#getBorders <em>Borders</em>}</li>
 *   <li>{@link risiko.board.impl.BoardImpl#getContinents <em>Continents</em>}</li>
 *   <li>{@link risiko.board.impl.BoardImpl#getAdditionalTroops <em>Additional Troops</em>}</li>
 *   <li>{@link risiko.board.impl.BoardImpl#getCards <em>Cards</em>}</li>
 *   <li>{@link risiko.board.impl.BoardImpl#getInitialTroops <em>Initial Troops</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BoardImpl extends MinimalEObjectImpl.Container implements Board {
	/**
	 * The cached value of the '{@link #getCountries() <em>Countries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCountries()
	 * @generated
	 * @ordered
	 */
	protected EList<Country> countries;

	/**
	 * The cached value of the '{@link #getBorders() <em>Borders</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBorders()
	 * @generated
	 * @ordered
	 */
	protected EList<Border> borders;

	/**
	 * The cached value of the '{@link #getContinents() <em>Continents</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContinents()
	 * @generated
	 * @ordered
	 */
	protected EList<Continent> continents;

	/**
	 * The cached value of the '{@link #getAdditionalTroops() <em>Additional Troops</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalTroops()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> additionalTroops;

	/**
	 * The cached value of the '{@link #getCards() <em>Cards</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCards()
	 * @generated
	 * @ordered
	 */
	protected EList<Card> cards;

	/**
	 * The cached value of the '{@link #getInitialTroops() <em>Initial Troops</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialTroops()
	 * @generated
	 * @ordered
	 */
	protected EList<Integer> initialTroops;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BoardImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return boardPackage.Literals.BOARD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Country> getCountries() {
		if (countries == null) {
			countries = new EObjectContainmentEList<Country>(Country.class, this, boardPackage.BOARD__COUNTRIES);
		}
		return countries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Border> getBorders() {
		if (borders == null) {
			borders = new EObjectContainmentEList<Border>(Border.class, this, boardPackage.BOARD__BORDERS);
		}
		return borders;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Continent> getContinents() {
		if (continents == null) {
			continents = new EObjectContainmentEList<Continent>(Continent.class, this, boardPackage.BOARD__CONTINENTS);
		}
		return continents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Integer> getAdditionalTroops() {
		if (additionalTroops == null) {
			additionalTroops = new EDataTypeEList<Integer>(Integer.class, this, boardPackage.BOARD__ADDITIONAL_TROOPS);
		}
		return additionalTroops;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Card> getCards() {
		if (cards == null) {
			cards = new EObjectContainmentEList<Card>(Card.class, this, boardPackage.BOARD__CARDS);
		}
		return cards;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Integer> getInitialTroops() {
		if (initialTroops == null) {
			initialTroops = new EDataTypeEList<Integer>(Integer.class, this, boardPackage.BOARD__INITIAL_TROOPS);
		}
		return initialTroops;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case boardPackage.BOARD__COUNTRIES:
				return ((InternalEList<?>)getCountries()).basicRemove(otherEnd, msgs);
			case boardPackage.BOARD__BORDERS:
				return ((InternalEList<?>)getBorders()).basicRemove(otherEnd, msgs);
			case boardPackage.BOARD__CONTINENTS:
				return ((InternalEList<?>)getContinents()).basicRemove(otherEnd, msgs);
			case boardPackage.BOARD__CARDS:
				return ((InternalEList<?>)getCards()).basicRemove(otherEnd, msgs);
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
			case boardPackage.BOARD__COUNTRIES:
				return getCountries();
			case boardPackage.BOARD__BORDERS:
				return getBorders();
			case boardPackage.BOARD__CONTINENTS:
				return getContinents();
			case boardPackage.BOARD__ADDITIONAL_TROOPS:
				return getAdditionalTroops();
			case boardPackage.BOARD__CARDS:
				return getCards();
			case boardPackage.BOARD__INITIAL_TROOPS:
				return getInitialTroops();
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
			case boardPackage.BOARD__COUNTRIES:
				getCountries().clear();
				getCountries().addAll((Collection<? extends Country>)newValue);
				return;
			case boardPackage.BOARD__BORDERS:
				getBorders().clear();
				getBorders().addAll((Collection<? extends Border>)newValue);
				return;
			case boardPackage.BOARD__CONTINENTS:
				getContinents().clear();
				getContinents().addAll((Collection<? extends Continent>)newValue);
				return;
			case boardPackage.BOARD__ADDITIONAL_TROOPS:
				getAdditionalTroops().clear();
				getAdditionalTroops().addAll((Collection<? extends Integer>)newValue);
				return;
			case boardPackage.BOARD__CARDS:
				getCards().clear();
				getCards().addAll((Collection<? extends Card>)newValue);
				return;
			case boardPackage.BOARD__INITIAL_TROOPS:
				getInitialTroops().clear();
				getInitialTroops().addAll((Collection<? extends Integer>)newValue);
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
			case boardPackage.BOARD__COUNTRIES:
				getCountries().clear();
				return;
			case boardPackage.BOARD__BORDERS:
				getBorders().clear();
				return;
			case boardPackage.BOARD__CONTINENTS:
				getContinents().clear();
				return;
			case boardPackage.BOARD__ADDITIONAL_TROOPS:
				getAdditionalTroops().clear();
				return;
			case boardPackage.BOARD__CARDS:
				getCards().clear();
				return;
			case boardPackage.BOARD__INITIAL_TROOPS:
				getInitialTroops().clear();
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
			case boardPackage.BOARD__COUNTRIES:
				return countries != null && !countries.isEmpty();
			case boardPackage.BOARD__BORDERS:
				return borders != null && !borders.isEmpty();
			case boardPackage.BOARD__CONTINENTS:
				return continents != null && !continents.isEmpty();
			case boardPackage.BOARD__ADDITIONAL_TROOPS:
				return additionalTroops != null && !additionalTroops.isEmpty();
			case boardPackage.BOARD__CARDS:
				return cards != null && !cards.isEmpty();
			case boardPackage.BOARD__INITIAL_TROOPS:
				return initialTroops != null && !initialTroops.isEmpty();
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
		result.append(", initialTroops: ");
		result.append(initialTroops);
		result.append(')');
		return result.toString();
	}

} //BoardImpl
