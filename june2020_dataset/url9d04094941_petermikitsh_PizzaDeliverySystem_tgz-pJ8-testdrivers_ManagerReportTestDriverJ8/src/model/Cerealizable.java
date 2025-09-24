package model;

/**
 * This interface ensures all models can be saved to the database
 * 
 * @author  Isioma Nnodum	iun4534@rit.edu
 *
 * @param <T>
 */

public interface Cerealizable<T> {

	/**
	 * Return a hashCode to be used as a key
	 * 
	 * @return
	 */
	public int getKey();

}
