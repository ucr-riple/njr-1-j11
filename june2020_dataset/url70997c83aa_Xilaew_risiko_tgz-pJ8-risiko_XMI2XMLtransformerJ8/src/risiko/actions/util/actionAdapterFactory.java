/**
 */
package risiko.actions.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import risiko.actions.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see risiko.actions.actionPackage
 * @generated
 */
public class actionAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static actionPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public actionAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = actionPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected actionSwitch<Adapter> modelSwitch =
		new actionSwitch<Adapter>() {
			@Override
			public Adapter caseAtack(Atack object) {
				return createAtackAdapter();
			}
			@Override
			public Adapter caseSetTroops(SetTroops object) {
				return createSetTroopsAdapter();
			}
			@Override
			public Adapter caseCoinCards(CoinCards object) {
				return createCoinCardsAdapter();
			}
			@Override
			public Adapter caseInGameAction(InGameAction object) {
				return createInGameActionAdapter();
			}
			@Override
			public Adapter caseMoveTroops(MoveTroops object) {
				return createMoveTroopsAdapter();
			}
			@Override
			public Adapter caseAddPlayer(AddPlayer object) {
				return createAddPlayerAdapter();
			}
			@Override
			public Adapter caseStartGame(StartGame object) {
				return createStartGameAdapter();
			}
			@Override
			public Adapter caseAction(Action object) {
				return createActionAdapter();
			}
			@Override
			public Adapter caseRemovePlayer(RemovePlayer object) {
				return createRemovePlayerAdapter();
			}
			@Override
			public Adapter caseDrawCard(DrawCard object) {
				return createDrawCardAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.Atack <em>Atack</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.Atack
	 * @generated
	 */
	public Adapter createAtackAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.SetTroops <em>Set Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.SetTroops
	 * @generated
	 */
	public Adapter createSetTroopsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.CoinCards <em>Coin Cards</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.CoinCards
	 * @generated
	 */
	public Adapter createCoinCardsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.InGameAction <em>In Game Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.InGameAction
	 * @generated
	 */
	public Adapter createInGameActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.Action
	 * @generated
	 */
	public Adapter createActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.RemovePlayer <em>Remove Player</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.RemovePlayer
	 * @generated
	 */
	public Adapter createRemovePlayerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.DrawCard <em>Draw Card</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.DrawCard
	 * @generated
	 */
	public Adapter createDrawCardAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.MoveTroops <em>Move Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.MoveTroops
	 * @generated
	 */
	public Adapter createMoveTroopsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.AddPlayer <em>Add Player</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.AddPlayer
	 * @generated
	 */
	public Adapter createAddPlayerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link risiko.actions.StartGame <em>Start Game</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see risiko.actions.StartGame
	 * @generated
	 */
	public Adapter createStartGameAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //actionAdapterFactory
