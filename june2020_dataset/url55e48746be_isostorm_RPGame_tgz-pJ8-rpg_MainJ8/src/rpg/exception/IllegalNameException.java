package rpg.exception;

import rpg.creature.Creature;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * An exception class which signals an illegal name in creatures when 
 * trying to set the name.
 *    Each illegal name exception involves the illegal name and the creature.
 *    
 * @author Frederic Huysentruyt, Mathias benoďt
 * @see p.218 exception class example
 */
public class IllegalNameException extends RuntimeException {
	
	/**
	 * Initialize this new illegal name exception with the given name and 
	 * the given creature.
	 * @param name
	 *        The name for this new illegal name exception.
	 * @param creature
	 *        The creature for this new illegal name exception.
	 * @post The name of this new illegal name exception is equal to 
	 *       the given name.
	 *       | new.getName() == name
	 * @post The creature of this new illegal name exception is equal to 
	 *       the given creature.
	 *       | new.getcreature() == creature
	 * @effect This new illegal name exception is further initialized as a
	 *         new runtime exception involving no diagnostic message and no
	 *         cause.
	 *         | super()
	 */
	@Raw
	public IllegalNameException(String name, Creature creature)
	{
		this.name = name;
		this.creature = creature;
	}
	/**
	 * Return the name of this illegal name exception.
	 */
	@Basic @Raw @Immutable
	public String getName()
	{
		return name;
	}
	
	/**
	 * Variable referencing the name of this illegal name exception.
	 */
	private final String name;
	
	/**
	 * Return the creature of this illegal name exception.
	 */
	@Basic @Raw @Immutable
	public Creature getCreature()
	{
		return creature;
	}
	
	/**
	 * Variable referencing the creature of this illegal name exception.
	 */
	private final Creature creature;
	
	private static final long serialVersionUID = 1L;
}
