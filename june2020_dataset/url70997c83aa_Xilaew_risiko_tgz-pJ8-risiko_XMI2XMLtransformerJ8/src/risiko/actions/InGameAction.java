/**
 */
package risiko.actions;

import risiko.gamestate.Player;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>In Game Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link risiko.actions.InGameAction#getPlayer <em>Player</em>}</li>
 * </ul>
 * </p>
 *
 * @see risiko.actions.actionPackage#getInGameAction()
 * @model abstract="true"
 * @generated
 */
public interface InGameAction extends Action {
	/**
	 * Returns the value of the '<em><b>Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Player</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Player</em>' reference.
	 * @see #setPlayer(Player)
	 * @see risiko.actions.actionPackage#getInGameAction_Player()
	 * @model required="true"
	 * @generated
	 */
	Player getPlayer();

	/**
	 * Sets the value of the '{@link risiko.actions.InGameAction#getPlayer <em>Player</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Player</em>' reference.
	 * @see #getPlayer()
	 * @generated
	 */
	void setPlayer(Player value);

} // InGameAction
