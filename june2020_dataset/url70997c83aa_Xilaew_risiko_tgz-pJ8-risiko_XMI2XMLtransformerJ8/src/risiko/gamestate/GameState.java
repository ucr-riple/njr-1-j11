/**
 */
package risiko.gamestate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Game State</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see risiko.gamestate.statePackage#getGameState()
 * @model
 * @generated
 */
public enum GameState implements Enumerator {
	/**
	 * The '<em><b>Accepting Players</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ACCEPTING_PLAYERS_VALUE
	 * @generated
	 * @ordered
	 */
	ACCEPTING_PLAYERS(0, "acceptingPlayers", "acceptingPlayers"),

	/**
	 * The '<em><b>Initial Troop Distribution</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INITIAL_TROOP_DISTRIBUTION_VALUE
	 * @generated
	 * @ordered
	 */
	INITIAL_TROOP_DISTRIBUTION(0, "initialTroopDistribution", "initialTroopDistribution"),

	/**
	 * The '<em><b>Play</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PLAY_VALUE
	 * @generated
	 * @ordered
	 */
	PLAY(0, "play", "play"),

	/**
	 * The '<em><b>Game Over</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GAME_OVER_VALUE
	 * @generated
	 * @ordered
	 */
	GAME_OVER(0, "gameOver", "gameOver");

	/**
	 * The '<em><b>Accepting Players</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Accepting Players</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ACCEPTING_PLAYERS
	 * @model name="acceptingPlayers"
	 * @generated
	 * @ordered
	 */
	public static final int ACCEPTING_PLAYERS_VALUE = 0;

	/**
	 * The '<em><b>Initial Troop Distribution</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Initial Troop Distribution</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INITIAL_TROOP_DISTRIBUTION
	 * @model name="initialTroopDistribution"
	 * @generated
	 * @ordered
	 */
	public static final int INITIAL_TROOP_DISTRIBUTION_VALUE = 0;

	/**
	 * The '<em><b>Play</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Play</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PLAY
	 * @model name="play"
	 * @generated
	 * @ordered
	 */
	public static final int PLAY_VALUE = 0;

	/**
	 * The '<em><b>Game Over</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Game Over</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GAME_OVER
	 * @model name="gameOver"
	 * @generated
	 * @ordered
	 */
	public static final int GAME_OVER_VALUE = 0;

	/**
	 * An array of all the '<em><b>Game State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final GameState[] VALUES_ARRAY =
		new GameState[] {
			ACCEPTING_PLAYERS,
			INITIAL_TROOP_DISTRIBUTION,
			PLAY,
			GAME_OVER,
		};

	/**
	 * A public read-only list of all the '<em><b>Game State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<GameState> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Game State</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GameState get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			GameState result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Game State</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GameState getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			GameState result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Game State</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GameState get(int value) {
		switch (value) {
			case ACCEPTING_PLAYERS_VALUE: return ACCEPTING_PLAYERS;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private GameState(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
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
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //GameState
