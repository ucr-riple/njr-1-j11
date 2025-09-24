/**
 */
package risiko.board;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Card Symbol</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see risiko.board.boardPackage#getCardSymbol()
 * @model
 * @generated
 */
public enum CardSymbol implements Enumerator {
	/**
	 * The '<em><b>Infanterie</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INFANTERIE_VALUE
	 * @generated
	 * @ordered
	 */
	INFANTERIE(1, "Infanterie", "Infanterie"),

	/**
	 * The '<em><b>Kavallerie</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #KAVALLERIE_VALUE
	 * @generated
	 * @ordered
	 */
	KAVALLERIE(2, "Kavallerie", "Kavallerie"),

	/**
	 * The '<em><b>Artillerie</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ARTILLERIE_VALUE
	 * @generated
	 * @ordered
	 */
	ARTILLERIE(3, "Artillerie", "Artillerie"),

	/**
	 * The '<em><b>Joker</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #JOKER_VALUE
	 * @generated
	 * @ordered
	 */
	JOKER(-1, "Joker", "Joker");

	/**
	 * The '<em><b>Infanterie</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Infanterie</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INFANTERIE
	 * @model name="Infanterie"
	 * @generated
	 * @ordered
	 */
	public static final int INFANTERIE_VALUE = 1;

	/**
	 * The '<em><b>Kavallerie</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Kavallerie</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #KAVALLERIE
	 * @model name="Kavallerie"
	 * @generated
	 * @ordered
	 */
	public static final int KAVALLERIE_VALUE = 2;

	/**
	 * The '<em><b>Artillerie</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Artillerie</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ARTILLERIE
	 * @model name="Artillerie"
	 * @generated
	 * @ordered
	 */
	public static final int ARTILLERIE_VALUE = 3;

	/**
	 * The '<em><b>Joker</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Joker</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #JOKER
	 * @model name="Joker"
	 * @generated
	 * @ordered
	 */
	public static final int JOKER_VALUE = -1;

	/**
	 * An array of all the '<em><b>Card Symbol</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final CardSymbol[] VALUES_ARRAY =
		new CardSymbol[] {
			INFANTERIE,
			KAVALLERIE,
			ARTILLERIE,
			JOKER,
		};

	/**
	 * A public read-only list of all the '<em><b>Card Symbol</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<CardSymbol> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Card Symbol</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CardSymbol get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CardSymbol result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Card Symbol</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CardSymbol getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CardSymbol result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Card Symbol</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CardSymbol get(int value) {
		switch (value) {
			case INFANTERIE_VALUE: return INFANTERIE;
			case KAVALLERIE_VALUE: return KAVALLERIE;
			case ARTILLERIE_VALUE: return ARTILLERIE;
			case JOKER_VALUE: return JOKER;
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
	private CardSymbol(int value, String name, String literal) {
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
	
} //CardSymbol
