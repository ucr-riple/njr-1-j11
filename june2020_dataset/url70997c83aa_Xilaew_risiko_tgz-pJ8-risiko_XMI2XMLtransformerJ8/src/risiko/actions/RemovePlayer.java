/**
 */
package risiko.actions;

import org.eclipse.emf.common.util.EList;

import risiko.gamestate.Player;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Remove Player</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.actions.RemovePlayer#getPlayers <em>Players</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.actions.actionPackage#getRemovePlayer()
 * @model
 * @generated
 */
public interface RemovePlayer extends Action {
	/**
	 * Returns the value of the '<em><b>Players</b></em>' reference list.
	 * The list contents are of type {@link risiko.gamestate.Player}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Players</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Players</em>' reference list.
	 * @see risiko.actions.actionPackage#getRemovePlayer_Players()
	 * @model required="true"
	 * @generated
	 */
	EList<Player> getPlayers();

} // RemovePlayer
