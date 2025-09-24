package rpg.item;

import java.util.Enumeration;

import rpg.exception.IllegalAddItemException;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * A class of purses.
 * a purse is a container that only contains dukats
 * 
 * @author Mathias, Frederic
 * 
 * @invar The capacity of this purse is a valid capacity.
 *        | hasValidCapacity()
 *
 */
public class Purse extends Container{
	
	/**
	 * @param  weight
	 * 		   The weight of this purse
	 * @param  amountOfDukats
	 *         The amount of dukats to be added to this purse.
	 * @effect A new container is initialized with 
	 * 		   the generated id and the given weight, parent and capacity
	 * 		   | super(generateId(), weight, parent, capacity)
	 * @effect The given amount of dukats are added to this purse.
	 *         | addDukats(amountOfDukats)
	 * @effect The fibonacci numbers are shifted.
	 *         | shiftFibonacciNumbers()
	 * @post   The purse contains the given number of dukats
	 * 		   | 
	 */
	@Raw
	public Purse(Weight weight, Weight capacity, int amountOfDukats) {
		super(generateId(), weight, capacity);
		addDukats(amountOfDukats);
		shiftFibonacciNumbers();
	}
	
	/**
	 * @param  weight
	 * 		   The weight of this purse
	 * @param  amountOfDukats
	 *         The amount of dukats to be added to this purse.
	 * @effect A new purse is initialized with the given weight 
	 * 		   and capacity and 0 as its amount of dukats
	 * 		   | this(weight, capacity, 0)
	 */
	@Raw
	public Purse(Weight weight, Weight capacity) {
		this(weight, capacity, 0);
	}
	
	/**
	 * Adds the given amount of dukats to this purse.
	 * 
	 * @param amountOfDukats
	 *        The amount of dukats to add to this purse.
	 * @post  The number of dukats in this purse equals the sum of the given amount
	 *        of dukats and the dukats which were already in this purse.
	 *        | new.getDirectItems().size() == amountOfDukats + old.getDirectItems().size()
	 */
	@Model
	private void addDukats(int amountOfDukats)
	{
		for(int i = 0; i < amountOfDukats; i++)
			addDukat(new Dukat());
	}
	
	/**
	 * Proceed in the row of fibonacci.
	 * 
	 * @post The new second last id is equal to the old last id.
	 *       | new.getSecondLastid() == new.getLastId()
	 * @Post The new last id is equal to the id.
	 *       | new.getLastId() == getId()
	 */
	@Model @Raw
	private void shiftFibonacciNumbers() {
		secondLastId = lastId;
		lastId = getId();
	}
	
	private static long secondLastId = 0;
	/**
	 * Returns the second last id.
	 */
	@Model @Basic
	private static long getSecondLastId()
	{
		return secondLastId;
	}
	
	private static long lastId = 0;
	/**
	 * Returns the last id.
	 */
	@Model @Basic
	private static long getLastId()
	{
		return lastId;
	}
	
	/**
	 * Generates the next fibonacci number based on the last two ids.
	 * 
	 * @return One if the sum of the secondLastId plus the lastId is less than or equal to one.
	 *         | if(getSecondLastId() + getLastId() <= 1) then
	 *         |    result == 1
	 *         Otherwise return the same sum of the secondLastId and the lastId
	 *         | else then
	 *         |    result == getSecondLastId() + getLastId()
	 */
	@Model
	private static long generateId(){
		long nextId = secondLastId + lastId;
		if(nextId <= 1)
			nextId = 1;
		return nextId;
	}
	/**
	 * Checks whether this purse can have the given id as its id.
	 * 
	 * @param  id
	 *         The id to check.
	 * @return False if Item can't have the given id as its id.
	 *         | if(!super.canHaveAsId(id)) then
	 *         |    result == false
	 * @return True if and only if the given id is a fibonacci number.
	 *         | let
	 *         |    firstRoot = Math.sqrt(5*Math.pow(id,2) + 4);
	 *	       |    secondRoot = Math.sqrt(5*Math.pow(id,2) - 4);
	 *         | in
	 *         |    if(Math.round(firstRoot) == firstRoot || Math.round(secondRoot) == secondRoot) then
	 *         |       result == true;
	 *         |    else then
	 *         |       result == false;
	 */
	@Override @Raw
	public boolean canHaveAsId(long id)
	{
		if(!super.canHaveAsId(id))
			return false;
		double firstRoot = Math.sqrt(5*Math.pow(id,2) + 4);
		double secondRoot = Math.sqrt(5*Math.pow(id,2) - 4);
		if(Math.round(firstRoot) == firstRoot || Math.round(secondRoot) == secondRoot)
			return true;
		else
			return false;
	}
	
	/**
	 * Add the given dukat to this purse
	 * 
	 * @param  dukat
	 * 		   The dukat to add to this purse
	 * @effect If the given dukat can't be added to this purse, this purse is terminated
	 * 		   | if(!canAddItem(dukat)) then
	 *	   	   |	terminate()
	 * 		   Otherwise,The dukat is added to the enclosing container of this purse
	 * 		   | else
	 *   	   |	super.addItem(dukat)
	 */
	public void addDukat(Dukat dukat){
		if(!canAddItem(dukat))
			terminate();
		else 
			super.addItem(dukat);
	}
	/**
	 * Sets the capacity of this purse to the given capacity.
	 * 
	 * @param  capacity
	 *         The capacity of this purse
	 * @effect Sets the capacity of the enclosing container to the given capacity.
	 *         | super.setCapacity(capacity)
	 */
	@Override @Raw
	public void setCapacity(Weight capacity)
	{
		super.setCapacity(capacity);
	}
	
	/**
	 * Add all the dukats of the given purse to this purse
	 * 
	 * @param  other
	 * 		   The purse to transfer the dukats from
	 * @post   This purse contains all the dukats of the other purse
	 * 		   | for each dukat in other.getItems
	 * 		   | 	this.containsDirectItem(dukat)
	 * @throws IllegalAddItemException(this, other)
	 * 		   The sum of the weight of the dukats in this purse and the other purse exceed the capacity of this purse
	 * 		   | ( getTotalWeight().subtract(getWeight()) )
	 * 		   | 	.add( (other.getTotalWeight()).subtract(other.getWeight())
	 * 		   |  .compareTo(getCapacity())>0
	 */
	public void addPurse(Purse other){
		Weight newCapacity = ( getTotalWeight().subtract(getWeight()) ).add( (other.getTotalWeight()).subtract(other.getWeight()) );
		
		if(newCapacity.compareTo(getCapacity())>0)
			throw new IllegalAddItemException(this, other);
		
		Enumeration<Item> dukats = other.getItems();
		while(dukats.hasMoreElements()){
			Dukat dukat = (Dukat) dukats.nextElement();
			other.removeDirectItem(dukat);
			this.addDukat(dukat);
		}
	}
	/**
	 * Returns the value of this purse which is equal to the total value of this purse.
	 * @return The total value of this purse.
	 *         | result == getTotalValue()
	 */
	@Override
	public int getValue()
	{
		return getTotalValue();
	}
}
