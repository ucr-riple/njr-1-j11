package ori.mas.core;

import java.util.List;

/**
 * Heart of a World.
 * <p>
 * Manages all influences submitted by its associated world.
 * After each agent decision, the different influences are submit to it, then,
 * the heart pulses to apply them and their consequences.
 * </p>
 */
public interface Heart {

	/**
	 * Sets the heart world.
	 * <p>
	 * The world <b>MUST</b> be set, and should not be changed after.
	 * </p>
	 * @param w The world to set.
	 */
	public void setWorld(World w);

	/**
	 * Submits and influence to the heart.
	 * <p>
	 * The influence may be either directly rejected, or be stacked into a waiting list.
	 * </p>
	 * @param i The influence to add.
	 */
	public void submitInfluence(Influence i);

	/**
	 * The heart pulsation marks the world clock.
	 * <p>To each pulsation corresponds a world tick.</p>
	 * A pulsation consists of :
	 * <ul><li>selecting influences from all which have been submitted during last tick,</li>
	 * <li>applying their results,</li>
	 * <li>applying different other rules which may happens randomly or at precise
	 * time.</li>
	 * </ul>
	 * @return The list of influences which have been applied by the heart and made to
	 * the world.
	 */
	public List<Influence> pulse();

};

