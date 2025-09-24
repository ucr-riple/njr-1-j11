/**
 * 
 */
package Object_Interfaces;

import java.util.List;

/**
 * Defines an object that can contain other objects that are containable
 * 
 * @author benjamin
 * 
 */
public interface Container {
	/**
	 * Returns true if the item can be contained within this container e.g.
	 * whether this chest can hold a hot dog.
	 * 
	 * @param item
	 *            The item to test whether it can fit
	 * @return boolean
	 */
	public boolean canContain(Containable item);

	/**
	 * Returns a list of the containable's that this container contains
	 * 
	 * @return List
	 */
	public List<Containable> getItems();

	/**
	 * Returns the number of items that the container can hold. Must be either
	 * 4, 9 or 16 until further discussion.
	 */
	public int getInventorySize();
}
