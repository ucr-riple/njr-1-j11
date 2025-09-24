/**
 */
package risiko.actions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see risiko.actions.actionFactory
 * @model kind="package"
 * @generated
 */
public interface actionPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "actions";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/risiko/actions";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "actions";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	actionPackage eINSTANCE = risiko.actions.impl.actionPackageImpl.init();

	/**
	 * The meta object id for the '{@link risiko.actions.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.ActionImpl
	 * @see risiko.actions.impl.actionPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 7;

	/**
	 * The number of structural features of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link risiko.actions.impl.InGameActionImpl <em>In Game Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.InGameActionImpl
	 * @see risiko.actions.impl.actionPackageImpl#getInGameAction()
	 * @generated
	 */
	int IN_GAME_ACTION = 3;

	/**
	 * The feature id for the '<em><b>Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_GAME_ACTION__PLAYER = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>In Game Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_GAME_ACTION_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>In Game Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IN_GAME_ACTION_OPERATION_COUNT = ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link risiko.actions.impl.AtackImpl <em>Atack</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.AtackImpl
	 * @see risiko.actions.impl.actionPackageImpl#getAtack()
	 * @generated
	 */
	int ATACK = 0;

	/**
	 * The feature id for the '<em><b>Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATACK__PLAYER = IN_GAME_ACTION__PLAYER;

	/**
	 * The feature id for the '<em><b>Troops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATACK__TROOPS = IN_GAME_ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Border</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATACK__BORDER = IN_GAME_ACTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATACK__DIRECTION = IN_GAME_ACTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Atack</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATACK_FEATURE_COUNT = IN_GAME_ACTION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Atack</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATACK_OPERATION_COUNT = IN_GAME_ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link risiko.actions.impl.SetTroopsImpl <em>Set Troops</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.SetTroopsImpl
	 * @see risiko.actions.impl.actionPackageImpl#getSetTroops()
	 * @generated
	 */
	int SET_TROOPS = 1;

	/**
	 * The feature id for the '<em><b>Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TROOPS__PLAYER = IN_GAME_ACTION__PLAYER;

	/**
	 * The feature id for the '<em><b>Country</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TROOPS__COUNTRY = IN_GAME_ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Troops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TROOPS__TROOPS = IN_GAME_ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Set Troops</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TROOPS_FEATURE_COUNT = IN_GAME_ACTION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Set Troops</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SET_TROOPS_OPERATION_COUNT = IN_GAME_ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link risiko.actions.impl.CoinCardsImpl <em>Coin Cards</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.CoinCardsImpl
	 * @see risiko.actions.impl.actionPackageImpl#getCoinCards()
	 * @generated
	 */
	int COIN_CARDS = 2;

	/**
	 * The feature id for the '<em><b>Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COIN_CARDS__PLAYER = IN_GAME_ACTION__PLAYER;

	/**
	 * The feature id for the '<em><b>Cards</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COIN_CARDS__CARDS = IN_GAME_ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Coin Cards</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COIN_CARDS_FEATURE_COUNT = IN_GAME_ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Coin Cards</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COIN_CARDS_OPERATION_COUNT = IN_GAME_ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link risiko.actions.impl.MoveTroopsImpl <em>Move Troops</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.MoveTroopsImpl
	 * @see risiko.actions.impl.actionPackageImpl#getMoveTroops()
	 * @generated
	 */
	int MOVE_TROOPS = 4;

	/**
	 * The feature id for the '<em><b>Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_TROOPS__PLAYER = IN_GAME_ACTION__PLAYER;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_TROOPS__FROM = IN_GAME_ACTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_TROOPS__TO = IN_GAME_ACTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Troops</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_TROOPS__TROOPS = IN_GAME_ACTION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Move Troops</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_TROOPS_FEATURE_COUNT = IN_GAME_ACTION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Move Troops</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MOVE_TROOPS_OPERATION_COUNT = IN_GAME_ACTION_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link risiko.actions.impl.AddPlayerImpl <em>Add Player</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.AddPlayerImpl
	 * @see risiko.actions.impl.actionPackageImpl#getAddPlayer()
	 * @generated
	 */
	int ADD_PLAYER = 5;

	/**
	 * The feature id for the '<em><b>Players</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_PLAYER__PLAYERS = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Add Player</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_PLAYER_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Add Player</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADD_PLAYER_OPERATION_COUNT = ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link risiko.actions.impl.StartGameImpl <em>Start Game</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.StartGameImpl
	 * @see risiko.actions.impl.actionPackageImpl#getStartGame()
	 * @generated
	 */
	int START_GAME = 6;

	/**
	 * The number of structural features of the '<em>Start Game</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_GAME_FEATURE_COUNT = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Start Game</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int START_GAME_OPERATION_COUNT = ACTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link risiko.actions.impl.RemovePlayerImpl <em>Remove Player</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.RemovePlayerImpl
	 * @see risiko.actions.impl.actionPackageImpl#getRemovePlayer()
	 * @generated
	 */
	int REMOVE_PLAYER = 8;

	/**
	 * The feature id for the '<em><b>Players</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_PLAYER__PLAYERS = ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Remove Player</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_PLAYER_FEATURE_COUNT = ACTION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Remove Player</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMOVE_PLAYER_OPERATION_COUNT = ACTION_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link risiko.actions.impl.DrawCardImpl <em>Draw Card</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see risiko.actions.impl.DrawCardImpl
	 * @see risiko.actions.impl.actionPackageImpl#getDrawCard()
	 * @generated
	 */
	int DRAW_CARD = 9;

	/**
	 * The feature id for the '<em><b>Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRAW_CARD__PLAYER = IN_GAME_ACTION__PLAYER;

	/**
	 * The number of structural features of the '<em>Draw Card</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRAW_CARD_FEATURE_COUNT = IN_GAME_ACTION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Draw Card</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DRAW_CARD_OPERATION_COUNT = IN_GAME_ACTION_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link risiko.actions.Atack <em>Atack</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Atack</em>'.
	 * @see risiko.actions.Atack
	 * @generated
	 */
	EClass getAtack();

	/**
	 * Returns the meta object for the attribute '{@link risiko.actions.Atack#getTroops <em>Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Troops</em>'.
	 * @see risiko.actions.Atack#getTroops()
	 * @see #getAtack()
	 * @generated
	 */
	EAttribute getAtack_Troops();

	/**
	 * Returns the meta object for the reference '{@link risiko.actions.Atack#getBorder <em>Border</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Border</em>'.
	 * @see risiko.actions.Atack#getBorder()
	 * @see #getAtack()
	 * @generated
	 */
	EReference getAtack_Border();

	/**
	 * Returns the meta object for the attribute '{@link risiko.actions.Atack#isDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see risiko.actions.Atack#isDirection()
	 * @see #getAtack()
	 * @generated
	 */
	EAttribute getAtack_Direction();

	/**
	 * Returns the meta object for class '{@link risiko.actions.SetTroops <em>Set Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Set Troops</em>'.
	 * @see risiko.actions.SetTroops
	 * @generated
	 */
	EClass getSetTroops();

	/**
	 * Returns the meta object for the reference '{@link risiko.actions.SetTroops#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Country</em>'.
	 * @see risiko.actions.SetTroops#getCountry()
	 * @see #getSetTroops()
	 * @generated
	 */
	EReference getSetTroops_Country();

	/**
	 * Returns the meta object for the attribute '{@link risiko.actions.SetTroops#getTroops <em>Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Troops</em>'.
	 * @see risiko.actions.SetTroops#getTroops()
	 * @see #getSetTroops()
	 * @generated
	 */
	EAttribute getSetTroops_Troops();

	/**
	 * Returns the meta object for class '{@link risiko.actions.CoinCards <em>Coin Cards</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Coin Cards</em>'.
	 * @see risiko.actions.CoinCards
	 * @generated
	 */
	EClass getCoinCards();

	/**
	 * Returns the meta object for the reference list '{@link risiko.actions.CoinCards#getCards <em>Cards</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Cards</em>'.
	 * @see risiko.actions.CoinCards#getCards()
	 * @see #getCoinCards()
	 * @generated
	 */
	EReference getCoinCards_Cards();

	/**
	 * Returns the meta object for class '{@link risiko.actions.InGameAction <em>In Game Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>In Game Action</em>'.
	 * @see risiko.actions.InGameAction
	 * @generated
	 */
	EClass getInGameAction();

	/**
	 * Returns the meta object for the reference '{@link risiko.actions.InGameAction#getPlayer <em>Player</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Player</em>'.
	 * @see risiko.actions.InGameAction#getPlayer()
	 * @see #getInGameAction()
	 * @generated
	 */
	EReference getInGameAction_Player();

	/**
	 * Returns the meta object for class '{@link risiko.actions.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see risiko.actions.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for class '{@link risiko.actions.RemovePlayer <em>Remove Player</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remove Player</em>'.
	 * @see risiko.actions.RemovePlayer
	 * @generated
	 */
	EClass getRemovePlayer();

	/**
	 * Returns the meta object for the reference list '{@link risiko.actions.RemovePlayer#getPlayers <em>Players</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Players</em>'.
	 * @see risiko.actions.RemovePlayer#getPlayers()
	 * @see #getRemovePlayer()
	 * @generated
	 */
	EReference getRemovePlayer_Players();

	/**
	 * Returns the meta object for class '{@link risiko.actions.DrawCard <em>Draw Card</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Draw Card</em>'.
	 * @see risiko.actions.DrawCard
	 * @generated
	 */
	EClass getDrawCard();

	/**
	 * Returns the meta object for class '{@link risiko.actions.MoveTroops <em>Move Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Move Troops</em>'.
	 * @see risiko.actions.MoveTroops
	 * @generated
	 */
	EClass getMoveTroops();

	/**
	 * Returns the meta object for the reference '{@link risiko.actions.MoveTroops#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see risiko.actions.MoveTroops#getFrom()
	 * @see #getMoveTroops()
	 * @generated
	 */
	EReference getMoveTroops_From();

	/**
	 * Returns the meta object for the reference '{@link risiko.actions.MoveTroops#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see risiko.actions.MoveTroops#getTo()
	 * @see #getMoveTroops()
	 * @generated
	 */
	EReference getMoveTroops_To();

	/**
	 * Returns the meta object for the attribute '{@link risiko.actions.MoveTroops#getTroops <em>Troops</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Troops</em>'.
	 * @see risiko.actions.MoveTroops#getTroops()
	 * @see #getMoveTroops()
	 * @generated
	 */
	EAttribute getMoveTroops_Troops();

	/**
	 * Returns the meta object for class '{@link risiko.actions.AddPlayer <em>Add Player</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Add Player</em>'.
	 * @see risiko.actions.AddPlayer
	 * @generated
	 */
	EClass getAddPlayer();

	/**
	 * Returns the meta object for the containment reference list '{@link risiko.actions.AddPlayer#getPlayers <em>Players</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Players</em>'.
	 * @see risiko.actions.AddPlayer#getPlayers()
	 * @see #getAddPlayer()
	 * @generated
	 */
	EReference getAddPlayer_Players();

	/**
	 * Returns the meta object for class '{@link risiko.actions.StartGame <em>Start Game</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Start Game</em>'.
	 * @see risiko.actions.StartGame
	 * @generated
	 */
	EClass getStartGame();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	actionFactory getactionFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link risiko.actions.impl.AtackImpl <em>Atack</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.AtackImpl
		 * @see risiko.actions.impl.actionPackageImpl#getAtack()
		 * @generated
		 */
		EClass ATACK = eINSTANCE.getAtack();

		/**
		 * The meta object literal for the '<em><b>Troops</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATACK__TROOPS = eINSTANCE.getAtack_Troops();

		/**
		 * The meta object literal for the '<em><b>Border</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATACK__BORDER = eINSTANCE.getAtack_Border();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATACK__DIRECTION = eINSTANCE.getAtack_Direction();

		/**
		 * The meta object literal for the '{@link risiko.actions.impl.SetTroopsImpl <em>Set Troops</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.SetTroopsImpl
		 * @see risiko.actions.impl.actionPackageImpl#getSetTroops()
		 * @generated
		 */
		EClass SET_TROOPS = eINSTANCE.getSetTroops();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SET_TROOPS__COUNTRY = eINSTANCE.getSetTroops_Country();

		/**
		 * The meta object literal for the '<em><b>Troops</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SET_TROOPS__TROOPS = eINSTANCE.getSetTroops_Troops();

		/**
		 * The meta object literal for the '{@link risiko.actions.impl.CoinCardsImpl <em>Coin Cards</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.CoinCardsImpl
		 * @see risiko.actions.impl.actionPackageImpl#getCoinCards()
		 * @generated
		 */
		EClass COIN_CARDS = eINSTANCE.getCoinCards();

		/**
		 * The meta object literal for the '<em><b>Cards</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COIN_CARDS__CARDS = eINSTANCE.getCoinCards_Cards();

		/**
		 * The meta object literal for the '{@link risiko.actions.impl.InGameActionImpl <em>In Game Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.InGameActionImpl
		 * @see risiko.actions.impl.actionPackageImpl#getInGameAction()
		 * @generated
		 */
		EClass IN_GAME_ACTION = eINSTANCE.getInGameAction();

		/**
		 * The meta object literal for the '<em><b>Player</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IN_GAME_ACTION__PLAYER = eINSTANCE.getInGameAction_Player();

		/**
		 * The meta object literal for the '{@link risiko.actions.impl.ActionImpl <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.ActionImpl
		 * @see risiko.actions.impl.actionPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '{@link risiko.actions.impl.RemovePlayerImpl <em>Remove Player</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.RemovePlayerImpl
		 * @see risiko.actions.impl.actionPackageImpl#getRemovePlayer()
		 * @generated
		 */
		EClass REMOVE_PLAYER = eINSTANCE.getRemovePlayer();

		/**
		 * The meta object literal for the '<em><b>Players</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMOVE_PLAYER__PLAYERS = eINSTANCE.getRemovePlayer_Players();

		/**
		 * The meta object literal for the '{@link risiko.actions.impl.DrawCardImpl <em>Draw Card</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.DrawCardImpl
		 * @see risiko.actions.impl.actionPackageImpl#getDrawCard()
		 * @generated
		 */
		EClass DRAW_CARD = eINSTANCE.getDrawCard();

		/**
		 * The meta object literal for the '{@link risiko.actions.impl.MoveTroopsImpl <em>Move Troops</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.MoveTroopsImpl
		 * @see risiko.actions.impl.actionPackageImpl#getMoveTroops()
		 * @generated
		 */
		EClass MOVE_TROOPS = eINSTANCE.getMoveTroops();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVE_TROOPS__FROM = eINSTANCE.getMoveTroops_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MOVE_TROOPS__TO = eINSTANCE.getMoveTroops_To();

		/**
		 * The meta object literal for the '<em><b>Troops</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MOVE_TROOPS__TROOPS = eINSTANCE.getMoveTroops_Troops();

		/**
		 * The meta object literal for the '{@link risiko.actions.impl.AddPlayerImpl <em>Add Player</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.AddPlayerImpl
		 * @see risiko.actions.impl.actionPackageImpl#getAddPlayer()
		 * @generated
		 */
		EClass ADD_PLAYER = eINSTANCE.getAddPlayer();

		/**
		 * The meta object literal for the '<em><b>Players</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADD_PLAYER__PLAYERS = eINSTANCE.getAddPlayer_Players();

		/**
		 * The meta object literal for the '{@link risiko.actions.impl.StartGameImpl <em>Start Game</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see risiko.actions.impl.StartGameImpl
		 * @see risiko.actions.impl.actionPackageImpl#getStartGame()
		 * @generated
		 */
		EClass START_GAME = eINSTANCE.getStartGame();

	}

} //actionPackage
