/**
 */
package risiko.actions.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import risiko.actions.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see risiko.actions.actionPackage
 * @generated
 */
public class actionSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static actionPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public actionSwitch() {
		if (modelPackage == null) {
			modelPackage = actionPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case actionPackage.ATACK: {
				Atack atack = (Atack)theEObject;
				T result = caseAtack(atack);
				if (result == null) result = caseInGameAction(atack);
				if (result == null) result = caseAction(atack);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case actionPackage.SET_TROOPS: {
				SetTroops setTroops = (SetTroops)theEObject;
				T result = caseSetTroops(setTroops);
				if (result == null) result = caseInGameAction(setTroops);
				if (result == null) result = caseAction(setTroops);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case actionPackage.COIN_CARDS: {
				CoinCards coinCards = (CoinCards)theEObject;
				T result = caseCoinCards(coinCards);
				if (result == null) result = caseInGameAction(coinCards);
				if (result == null) result = caseAction(coinCards);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case actionPackage.IN_GAME_ACTION: {
				InGameAction inGameAction = (InGameAction)theEObject;
				T result = caseInGameAction(inGameAction);
				if (result == null) result = caseAction(inGameAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case actionPackage.MOVE_TROOPS: {
				MoveTroops moveTroops = (MoveTroops)theEObject;
				T result = caseMoveTroops(moveTroops);
				if (result == null) result = caseInGameAction(moveTroops);
				if (result == null) result = caseAction(moveTroops);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case actionPackage.ADD_PLAYER: {
				AddPlayer addPlayer = (AddPlayer)theEObject;
				T result = caseAddPlayer(addPlayer);
				if (result == null) result = caseAction(addPlayer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case actionPackage.START_GAME: {
				StartGame startGame = (StartGame)theEObject;
				T result = caseStartGame(startGame);
				if (result == null) result = caseAction(startGame);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case actionPackage.ACTION: {
				Action action = (Action)theEObject;
				T result = caseAction(action);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case actionPackage.REMOVE_PLAYER: {
				RemovePlayer removePlayer = (RemovePlayer)theEObject;
				T result = caseRemovePlayer(removePlayer);
				if (result == null) result = caseAction(removePlayer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case actionPackage.DRAW_CARD: {
				DrawCard drawCard = (DrawCard)theEObject;
				T result = caseDrawCard(drawCard);
				if (result == null) result = caseInGameAction(drawCard);
				if (result == null) result = caseAction(drawCard);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Atack</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Atack</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAtack(Atack object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Set Troops</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Set Troops</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSetTroops(SetTroops object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Coin Cards</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Coin Cards</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCoinCards(CoinCards object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>In Game Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>In Game Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInGameAction(InGameAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Action</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAction(Action object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remove Player</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remove Player</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRemovePlayer(RemovePlayer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Draw Card</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Draw Card</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDrawCard(DrawCard object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Move Troops</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Move Troops</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMoveTroops(MoveTroops object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Add Player</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Add Player</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAddPlayer(AddPlayer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Start Game</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Start Game</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStartGame(StartGame object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //actionSwitch
