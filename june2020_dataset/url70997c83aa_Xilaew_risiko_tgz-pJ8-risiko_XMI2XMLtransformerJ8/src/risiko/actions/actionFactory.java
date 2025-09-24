/**
 */
package risiko.actions;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see risiko.actions.actionPackage
 * @generated
 */
public interface actionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	actionFactory eINSTANCE = risiko.actions.impl.actionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Atack</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Atack</em>'.
	 * @generated
	 */
	Atack createAtack();

	/**
	 * Returns a new object of class '<em>Set Troops</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Set Troops</em>'.
	 * @generated
	 */
	SetTroops createSetTroops();

	/**
	 * Returns a new object of class '<em>Coin Cards</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Coin Cards</em>'.
	 * @generated
	 */
	CoinCards createCoinCards();

	/**
	 * Returns a new object of class '<em>Move Troops</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Move Troops</em>'.
	 * @generated
	 */
	MoveTroops createMoveTroops();

	/**
	 * Returns a new object of class '<em>Add Player</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Add Player</em>'.
	 * @generated
	 */
	AddPlayer createAddPlayer();

	/**
	 * Returns a new object of class '<em>Start Game</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Start Game</em>'.
	 * @generated
	 */
	StartGame createStartGame();

	/**
	 * Returns a new object of class '<em>Remove Player</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remove Player</em>'.
	 * @generated
	 */
	RemovePlayer createRemovePlayer();

	/**
	 * Returns a new object of class '<em>Draw Card</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Draw Card</em>'.
	 * @generated
	 */
	DrawCard createDrawCard();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	actionPackage getactionPackage();

} //actionFactory
