/**
 */
package risiko.gamestate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Turn Phase</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see risiko.gamestate.statePackage#getTurnPhase()
 * @model
 * @generated
 */
public enum TurnPhase implements Enumerator {
	/**
	 * The '<em><b>Gain Troops</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GAIN_TROOPS_VALUE
	 * @generated
	 * @ordered
	 */
	GAIN_TROOPS(0, "gainTroops", ""),

	/**
	 * The '<em><b>Set Troops</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SET_TROOPS_VALUE
	 * @generated
	 * @ordered
	 */
	SET_TROOPS(1, "setTroops", "setTroops"),

	/**
	 * The '<em><b>Fight</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIGHT_VALUE
	 * @generated
	 * @ordered
	 */
	FIGHT(2, "fight", "fight"),

	/**
	 * The '<em><b>Move Troops</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MOVE_TROOPS_VALUE
	 * @generated
	 * @ordered
	 */
	MOVE_TROOPS(3, "moveTroops", "moveTroops");

	/**
	 * The '<em><b>Gain Troops</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Gain Troops</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GAIN_TROOPS
	 * @model name="gainTroops" literal=""
	 * @generated
	 * @ordered
	 */
	public static final int GAIN_TROOPS_VALUE = 0;

	/**
	 * The '<em><b>Set Troops</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Set Troops</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SET_TROOPS
	 * @model name="setTroops"
	 * @generated
	 * @ordered
	 */
	public static final int SET_TROOPS_VALUE = 1;

	/**
	 * The '<em><b>Fight</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Fight</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FIGHT
	 * @model name="fight"
	 * @generated
	 * @ordered
	 */
	public static final int FIGHT_VALUE = 2;

	/**
	 * The '<em><b>Move Troops</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Move Troops</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MOVE_TROOPS
	 * @model name="moveTroops"
	 * @generated
	 * @ordered
	 */
	public static final int MOVE_TROOPS_VALUE = 3;

	/**
	 * An array of all the '<em><b>Turn Phase</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TurnPhase[] VALUES_ARRAY =
		new TurnPhase[] {
			GAIN_TROOPS,
			SET_TROOPS,
			FIGHT,
			MOVE_TROOPS,
		};

	/**
	 * A public read-only list of all the '<em><b>Turn Phase</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TurnPhase> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Turn Phase</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TurnPhase get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TurnPhase result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Turn Phase</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TurnPhase getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TurnPhase result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Turn Phase</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TurnPhase get(int value) {
		switch (value) {
			case GAIN_TROOPS_VALUE: return GAIN_TROOPS;
			case SET_TROOPS_VALUE: return SET_TROOPS;
			case FIGHT_VALUE: return FIGHT;
			case MOVE_TROOPS_VALUE: return MOVE_TROOPS;
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
	private TurnPhase(int value, String name, String literal) {
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
	
} //TurnPhase
