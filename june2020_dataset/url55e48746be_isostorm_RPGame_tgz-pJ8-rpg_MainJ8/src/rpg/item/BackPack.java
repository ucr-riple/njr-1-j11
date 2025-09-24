package rpg.item;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of backpacks
 * A backpack is a special type of container
 * 
 * @author Mathias, Frederic
 *
 */
public class BackPack extends Container{

	/**
	 * @param  weight
	 * 		   The weight of this backpack
	 * @param  capacity
	 * 		   The capacity of this backpack
	 * @param  value
	 *         The value of this backpack
	 * @effect A new container is initialized with 
	 * 		   the given a generated id and the given weight, capacity and value
	 * 		   | super(generateId(), weight, capacity, value)
	 * @post   The number of backpacks is increased by one
	 * 		   | new.getNbOfBackPacks() == old.getNbOfBackPacks() + 1
	 */
	@Raw
	public BackPack(Weight weight, Weight capacity, int value) {
		super(generateId(), weight, capacity, value);
		nbOfBackPacks++;
	}
	
	/**
	 * A variable storing the total number of created backpacks.
	 */
	private static int nbOfBackPacks = 1;
	
	/**
	 * Returns the number of backpacks that have been created.
	 */
	@Model @Raw
	private static int getNbOfBackPacks()
	{
		return nbOfBackPacks;
	}
	
	/**
	 * Generates the id for the n-th backpack.
	 * 
	 * @return Return the sum of all binominal coefficients between 1
	 *         and the total number of backpacks.
	 *         | let
	 *         |    previousBin = 1
	 *         |    retValue = previousBin
	 *         | in
	 *         |    for each I in 1..getNbOfBackPacks():
	 *         |       previousBin *= (getNbOfBackPacks()-I-1)/(I)
	 *         |       retValue += previousBin
	 * 		   |    result == retValue
	 */
	@Model
	private static long generateId(){
		double previousBin = 1;
		int result = (int)previousBin;
		for(int i = 1; i <= getNbOfBackPacks(); i++)
		{
			double k = i-1;
			previousBin *= (getNbOfBackPacks()-k)/(k+1);
			result += (int)previousBin;
		}
		return (int)result;
	}
	
	/**
	 * Add an item to this backpack
	 * 
	 * @effect Add an item to the enclosing container of this backpack
	 * 		   | super.addItem(item)
	 */
	@Override
	public void addItem(Item item){
		super.addItem(item);
	}
	
	
	/**
	 * Checks whether this backpack can have the given value as its value.
	 * 
	 * @return True if and only if the given value is less than or equal 
	 *         to 500 and greater than or equal to 0.
	 *         | result == ( (value <= 500) && (value >= 0) )
	 */
	@Override @Raw
	public boolean canHaveAsValue(int value)
	{
		return value <= 500 && value >= 0;
	}
	
	/**
	 * Set the value of this backpack to the given value
	 * 
	 * @param  val
	 * 		   The value to set
	 * @effect The value of the enclosing container is set to the given value
	 * 		   | super.setValue(value)
	 */
	@Raw
	public void setValue(int value){
		super.setValue(value);
	}
}

	
