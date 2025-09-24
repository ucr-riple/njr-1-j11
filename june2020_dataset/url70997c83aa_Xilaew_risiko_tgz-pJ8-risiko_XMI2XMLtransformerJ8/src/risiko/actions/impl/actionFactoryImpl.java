/**
 */
package risiko.actions.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import risiko.actions.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class actionFactoryImpl extends EFactoryImpl implements actionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static actionFactory init() {
		try {
			actionFactory theactionFactory = (actionFactory)EPackage.Registry.INSTANCE.getEFactory(actionPackage.eNS_URI);
			if (theactionFactory != null) {
				return theactionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new actionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public actionFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case actionPackage.ATACK: return createAtack();
			case actionPackage.SET_TROOPS: return createSetTroops();
			case actionPackage.COIN_CARDS: return createCoinCards();
			case actionPackage.MOVE_TROOPS: return createMoveTroops();
			case actionPackage.ADD_PLAYER: return createAddPlayer();
			case actionPackage.START_GAME: return createStartGame();
			case actionPackage.REMOVE_PLAYER: return createRemovePlayer();
			case actionPackage.DRAW_CARD: return createDrawCard();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Atack createAtack() {
		AtackImpl atack = new AtackImpl();
		return atack;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SetTroops createSetTroops() {
		SetTroopsImpl setTroops = new SetTroopsImpl();
		return setTroops;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoinCards createCoinCards() {
		CoinCardsImpl coinCards = new CoinCardsImpl();
		return coinCards;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MoveTroops createMoveTroops() {
		MoveTroopsImpl moveTroops = new MoveTroopsImpl();
		return moveTroops;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddPlayer createAddPlayer() {
		AddPlayerImpl addPlayer = new AddPlayerImpl();
		return addPlayer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StartGame createStartGame() {
		StartGameImpl startGame = new StartGameImpl();
		return startGame;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemovePlayer createRemovePlayer() {
		RemovePlayerImpl removePlayer = new RemovePlayerImpl();
		return removePlayer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DrawCard createDrawCard() {
		DrawCardImpl drawCard = new DrawCardImpl();
		return drawCard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public actionPackage getactionPackage() {
		return (actionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static actionPackage getPackage() {
		return actionPackage.eINSTANCE;
	}

} //actionFactoryImpl
