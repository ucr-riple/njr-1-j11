package clarion.extensions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import clarion.system.*;

/**
 * This class implements a table lookup within CLARION. It extends the 
 * AbstractRuntimeTrainableImplicitModule class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A table lookup is the simplest form of implicit module that can be used in the bottom level of the
 * CLARION subsystems. It consists of a hash map that uses dimension-value collections as keys and
 * output chunk collections as values. The keys of the hash map are configurations of the input layer 
 * that are compared to the current input and the values of the hash map are output chunks that correspond
 * to the chunks on the output layer.
 * <p>
 * When the forwardPass method is called, the table lookup takes the current input and compares it to the
 * keys of the hash map until it finds a match. If no match is found, the forwardPass method does nothing.
 * When a match is found, the method sets the activations of the output chunks of the table lookup to the
 * activations of the output chunks contained within the collection located at the slot in the hash map
 * whose key matched the current input.
 * <p>
 * This class is considered to be runtime trainable in that a value can be specified by the feedback for
 * a chosen output chunk and that value will then be set as the new activation for that output chunk within
 * the collection of output chunks located at the slot in the hash map whose key matched the current input.
 * This procedure is performed during by the backwardPass method.
 * <p>
 * While it is completely within the capabilities of the CLARION Library to use a table lookup, it is NOT 
 * sub-symbolic or distributed in nature. Therefore, it is encouraged that you only use table lookups in the 
 * bottom level for testing and debugging purposes. Instead, you are advised to use a more sub-symbolic 
 * structure (such as a neural network) for the implicit modules within CLARION.
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class TableLookup extends AbstractRuntimeTrainableImplicitModule {
	
	/**The hash map backed table lookup.*/
	private HashMap <DimensionValueCollection, AbstractOutputChunkCollection <? extends AbstractOutputChunk>> LookupTable;
	
	/**The immediate feedback given (if given).*/
    protected double Feedback;
    
    /**The positive match counter.*/
	protected double PM = 0;
	/**The negative match counter.*/
	protected double NM = 0;
	
	/**The threshold for the positive match criterion. */
    public static double GLOBAL_POSITIVE_MATCH_THRESHOLD = .5;
    /**The threshold for the positive match criterion. */
    public double POSITIVE_MATCH_THRESHOLD = GLOBAL_POSITIVE_MATCH_THRESHOLD;
	
    /**
     * Initializes the table lookup with the input space, outputs, and map specified.
     * @param InputSpace The input space for the table lookup.
     * @param Outputs The output chunks for the output layer of the table lookup.
     * @param Table The table to use for looking up outputs from the table lookup.
     */
	public TableLookup(Collection<Dimension> InputSpace, AbstractOutputChunkCollection <? extends AbstractOutputChunk> Outputs, 
			Map <DimensionValueCollection, AbstractOutputChunkCollection <? extends AbstractOutputChunk>> Table) {
		super(InputSpace, Outputs);
		LookupTable = 
			new HashMap<DimensionValueCollection, AbstractOutputChunkCollection<? extends AbstractOutputChunk>> (Table);
	}

	/** 
     * Updates the table lookup table given the chosen action and the feedback. This method 
     * should not be called before the setFeedback and setChosenAction methods have been 
     * called.
     * <p>
     * In the backwardPass method, a value is specified by the feedback for a chosen output chunk and that value is then be set as 
     * the new activation for that output chunk within the collection of output chunks located at the slot in the hash map whose 
     * key matched the current input.
     */
	public void backwardPass()
	{
		AbstractOutputChunk out = Output.get(ChosenOutput.getID());
		if(out != null)
		{
			out.setActivation(Feedback);
		}
	}
	
	/**
     * Calculates the output activations based on the current input. This method should not 
     * be called until the setInput method has been called setting the input activations to 
     * the current state.
     * <p>
	 * When the forwardPass method is called, the table lookup takes the current input and compares it to the
	 * keys of the hash map until it finds a match. If no match is found, the forwardPass method does nothing.
	 * When a match is found, the method sets the activations of the output chunks of the table lookup to the
	 * activations of the output chunks contained within the collection located at the slot in the hash map
	 * whose key matched the current input.
     */
	public void forwardPass() 
	{
		AbstractOutputChunkCollection <? extends AbstractOutputChunk> occ = null;
		for(DimensionValueCollection dvc : LookupTable.keySet())
		{
			if(dvc.equals(InputAsCollection))
			{
				occ = LookupTable.get(dvc);
				break;
			}
		}
		
		if(occ != null)
		{
			for(AbstractOutputChunk o : Output.values())
			{
				if(occ.containsKey(o.getID()))
				{
					o.setActivation(occ.get(o.getID()).getActivation());
				}
				else
					o.resetActivation();
			}
		}
	}

	/**
     * Checks to see if the extraction criterion is satisfied given the immediate 
     * feedback received.
     * <p>
     * This check is usually performed before the backwardPass method is called but after 
     * the setFeedback and setChosenAction methods have been called.
     * @return True if the extraction criterion has been satisfied, otherwise false.
     */
    public boolean checkExtraction()
    {
    	if(getFeedback() >= POSITIVE_MATCH_THRESHOLD)
    		return true;
    	else
    		return false;
    }
	
	/**
	 * Gets the success rate. If no match statistics have been updated (i.e. PM & NM are both 0), this
	 * method will return null.
	 * @return The success rate (or null if no match statistics have been updated).
	 */
	public Double getSuccessRate() {
		if(PM + NM > 0)
			return PM / PM + NM;
		else
			return null;
	}
}
