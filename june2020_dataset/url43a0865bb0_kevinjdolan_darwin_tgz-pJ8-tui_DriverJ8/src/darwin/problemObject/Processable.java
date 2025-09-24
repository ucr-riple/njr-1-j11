package darwin.problemObject;

import darwin.population.Node;

/**
 * Interface to be implemented by problem-specific application
 * for the representation of what some node actually does
 * @author Kevin Dolan
 *
 * @param <returnType> what type of object this returns
 */
public abstract class Processable<returnType> {
	
	/**
	 * Process the given node with the given parameters
	 * @param parameters the problem-specific parameters
	 * @param childNodes array of the child nodes of this node
	 * @return 			 the problem-specific value of this node
	 */
	public abstract returnType getValue(Object parameters, Node[] childNodes);
	
	
	/**
	 * @return an identical, but separate Processable object
	 */
	public abstract Processable<returnType> clone();

	/**
	 * Checks if this nodes is identical to the other node in identity
	 * @return true if this is the case
	 */
	public boolean equalsIdentity(Processable<?> other) {
		return getClass().equals(other.getClass());
	}
	
	/**
	 * Checks if other is identical to this (in value)
	 * Should only be requested for processable of the same type
	 * @return true if the other processable is the same
	 */
	protected boolean equalsValue(Processable<?> other) {
		return true;
	}
	
	/**
	 * Checks if this nodes is identical to the other node in identity and value
	 * @return true if this is the case
	 */
	public boolean equals(Processable<?> other) {
		if(!equalsIdentity(other))
			return false;
		return equalsValue(other);
	}
	
	public String toString() {
		String name = getClass().getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}
}
