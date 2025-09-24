package clarion.system;

import java.util.Collection;
import java.util.Map;

/**
 * This class implements an output chunk within CLARION. It extends the AbstractChunk class
 * and implements the InterfaceTracksTime and InterfaceStochasticallySelectable interfaces.
 * This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * An output chunk is a specific case of a chunk that can be set as a node in the output layer of an
 * implicit module. More specifically, unlike a normal chunk, an output chunk has an activation that can be 
 * used to store pertinent activation information for objects like drives, goals, or actions.
 * <p>
 * In addition, output chunks can also be used to keep track of selection measures for both the top and bottom level
 * of the CLARION subsystems, which is used for combination and/or stochastic selection.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractAction</li>
 * <li>DimensionlessOutputChunk</li>
 * <li>DriveStrength</li>
 * <li>ExternalAction</li>
 * <li>Goal</li>
 * <li>GoalAction</li>
 * <li>WorkingMemoryAction</li>
 * </ul>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public abstract class AbstractOutputChunk extends AbstractChunk implements InterfaceTracksTime, InterfaceStochasticallySelectable{
	private static final long serialVersionUID = 1476949155641762329L;

	/**The activation of the output chunk.*/
	private double Activation;
	/**The selection measure used by the bottom level (usually the same as the activation).*/
	private double SelectionMeasureBL;
	/**The selection measure used by the top level (in the case of the ACS, can be: Support or Utility).*/
	private double SelectionMeasureTL;
	/**The final selection measure used during stochastic selection. By default this is set to the bottom level
	 * selection measure.*/
	private double FinalSelectionMeasure;
	
	/**How close two final selection measures must be in order to be considered equal. Needed due to the nature
	 * of double precision arithmetic.*/
	public static double GLOBAL_FINAL_SELECTION_MEASURE_EPSILON = .0001;
	
	/**The minimum threshold an output chunk activation must be above to be
	 * considered activated.*/
	public static double GLOBAL_MINIMUM_ACTIVATION_THRESHOLD = 0;
	/**The minimum threshold an output chunk activation must be above to be
	 * considered activated.*/
	public double MINIMUM_ACTIVATION_THRESHOLD = GLOBAL_MINIMUM_ACTIVATION_THRESHOLD;
	/**The level at which an output chunk is considered to be fully activated.*/
	public static double GLOBAL_FULL_ACTIVATION_LEVEL = 1;
	/**The level at which an output chunk is considered to be fully activated.*/
	public double FULL_ACTIVATION_LEVEL = GLOBAL_FULL_ACTIVATION_LEVEL;
	/**The persistence factor for the previous selection (if used).*/
    public static double GLOBAL_PERSISTENCE = 0;
    /**The persistence factor for the previous selection (if used).*/
    public double PERSISTENCE = GLOBAL_PERSISTENCE;

	
	/**
	 * Initializes the output chunk with the ID specified.
	 * @param id The ID of the output chunk.
	 */
	public AbstractOutputChunk (Object id)
	{
		super(id);
	}
	
	/**
	 * Initializes the output chunk with the specified ID and dimensions.
	 * @param id The ID of the output chunk.
	 * @param dims The dimensions for the output chunk.
	 */
	public AbstractOutputChunk (Object id, Collection <? extends Dimension> dims)
	{
		super(id, dims);
	}
	
	/**
	 * Initializes the output chunk with the specified ID and map of dimensions.
	 * @param id The ID of the output chunk.
	 * @param dims The map of dimensions for the output chunk.
	 */
	public AbstractOutputChunk (Object id, Map <? extends Object, ? extends Dimension> dims)
	{
		super(id, dims);
	}
	
	/**
	 * Gets the activation level.
	 * @return The activation level.
	 */
	public double getActivation ()
	{
		return Activation;
	}
	
	/**
	 * Sets the activation level for the output chunk.
	 * @param act The activation level to set.
	 */
	public void setActivation (double act)
	{
		Activation = act;
	}
	
	/**
	 * Gets the bottom level selection measure.
	 * @return The bottom level selection measure.
	 */
	public double getBLSelectionMeasure ()
	{
		return SelectionMeasureBL;
	}
	
	/**
	 * Sets the bottom level selection measure for this chunk. This method also sets the final 
	 * selection measure to the measure specified.
	 * @param Measure The selection measure to set.
	 */
	public void setBLSelectionMeasure (double Measure)
	{
		SelectionMeasureBL = Measure;
		FinalSelectionMeasure = Measure;
	}
	
	/**
	 * Gets the top level selection measure.
	 * @return The top level selection measure.
	 */
	public double getTLSelectionMeasure ()
	{
		return SelectionMeasureTL;
	}
	
	/**
	 * Sets the top level selection measure for this chunk.
	 * @param Measure The selection measure to set.
	 */
	public void setTLSelectionMeasure (double Measure)
	{
		SelectionMeasureTL = Measure;
	}
	
	/**
	 * Gets the final selection measure used for stochastic selection.
	 * @return The final selection measure.
	 */
	public double getFinalSelectionMeasure ()
	{
		return FinalSelectionMeasure;
	}
	
	/**
	 * Sets the final selection measure.
	 * @param Measure The value to set for the final selection measure.
	 */
	public void setFinalSelectionMeasure (double Measure)
	{
		FinalSelectionMeasure = Measure;
	}
	
	/**
	 * Resets the activation of the output chunk.
	 */
	public void resetActivation ()
	{
		Activation = MINIMUM_ACTIVATION_THRESHOLD;
	}
	
	/**
	 * Adjusts the specified selection measure by the persistence factor.
	 * @param current The current level of the specified selection measure for the output chunk.
	 * @param last The level of the specified selection measure for the output chunk from the last time step.
	 * @return The selection measure adjusted for the persistence factor.
	 */
	public double adjustSelectionMeasure (double current, double last)
	{
		return (1 - PERSISTENCE) * current + PERSISTENCE * last;
	}
	
	/**
	 * Checks to see if the specified object is an abstract output chunk and if the ID and final selection measure of the 
	 * specified abstract output chunk is the same as this abstract output chunk.
	 * @param chunk The chunk to compare to this chunk.
	 * @return True if the two chunks are equal, otherwise false.
	 */
	public boolean equals (Object chunk)
	{
		if(!super.equals(chunk))
			return false;
		if(!(chunk instanceof AbstractOutputChunk))
			return false;
		return Math.abs(((AbstractOutputChunk)chunk).FinalSelectionMeasure - FinalSelectionMeasure) <= GLOBAL_FINAL_SELECTION_MEASURE_EPSILON;
	}
	
	/**
	 * Compares the final selection measure of this abstract output chunk to the specified stochastically selectable object.
	 * @param o The stochastically selectable object to compare.
	 * @return A negative integer, 0, or positive integer if the final selection measure of this object is 
	 * less than, equal to, or greater than the final selection measure of the specified object.
	 */
	public int compareTo(InterfaceStochasticallySelectable o) {
		if(Math.abs(o.getFinalSelectionMeasure() - FinalSelectionMeasure) <= GLOBAL_FINAL_SELECTION_MEASURE_EPSILON)
			return 0;
		if(o.getFinalSelectionMeasure() > FinalSelectionMeasure)
			return -1;
		else
			return 1;
	}
}
