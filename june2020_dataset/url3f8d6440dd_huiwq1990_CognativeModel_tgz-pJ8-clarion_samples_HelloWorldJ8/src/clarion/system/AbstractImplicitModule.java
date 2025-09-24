package clarion.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class implements an implicit module within CLARION.
 * This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that extends an abstract implicit module can be used in the bottom level of the CLARION subsystems. This class
 * acts as the base foundation for building implicit modules to use in the bottom level. All modules that are used within the
 * bottom level of CLARION MUST extend from this class.
 * <p>
 * The CLARION Library is very loose about what a user can define as an implicit module on the bottom level. As long as your
 * user defined implicit module extends from this class, you are free to implement just about any type of structure you 
 * would like.
 * <p>
 * The following structures come pre-defined for you to use within the CLARION Library:<br>
 * <ul>
 * <li>Three-Layer Neural Network</li>
 * <li>Equation (see extensions package)</li>
 * <li>Table Look-up (see extensions package)</li>
 * </ul>
 * Below are some suggestions of possible structures that could be implemented by the user:<br>
 * <ul>
 * <li>Bayesien Network</li>
 * <li>Hopfield Network</li>
 * <li>Hebbian Network</li>
 * <li>Hebb/GenRec Network (see O'Reily)</li>
 * <li>Etc.</li>
 * </ul>
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <u>System Package</u>
 * <li>AbstractNeuralNet</li>
 * <li>AbstractRuntimeTrainableBPNet</li>
 * <li>AbstractRuntimeTrainableImplicitModule</li>
 * <li>AbstractTrainableImplicitModule</li>
 * <li>BPNet</li>
 * <li>QBPNet</li>
 * <li>SimplifiedQBPNet</li>
 * <br><u>Extensions Package</u>
 * <li>AbstractEquation</li>
 * <li>ACSLevelProbabilitySettingEquation</li>
 * <li>DriveEquation</li>
 * <li>GoalSelectionEquation</li>
 * <li>JudgmentCorrectionEquation</li>
 * <li>TableLookup</li>
 * </ul>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of any subclasses of this class are initialized. Changing constants in this class will
 * also change those constant values for all classes that extend from this class.
 * @version 6.0.6
 * @author Nick Wilson
 */
public abstract class AbstractImplicitModule{
    /**The input layer represented as a collection.*/
    protected DimensionValueCollection InputAsCollection;
	/**The output layer.*/
    protected AbstractOutputChunkCollection <? extends AbstractOutputChunk> Output;
	/**The index of the output node relating to the chosen output.*/
	protected AbstractOutputChunk ChosenOutput;
	
	/**Perception time. Used for calculating response time.*/
	public static double GLOBAL_PERCEPTION_TIME = .200;
	/**Perception time. Used for calculating response time.*/
	public double PERCEPTION_TIME = GLOBAL_PERCEPTION_TIME;
	/**Decision time. Used for calculating response time.*/
	public static double GLOBAL_DECISION_TIME = .350;
	/**Decision time. Used for calculating response time.*/
	public double DECISION_TIME = GLOBAL_DECISION_TIME;
	/**Actuation time. Used for calculating response time.*/
	public static double GLOBAL_ACTUATION_TIME = .500;
	/**Actuation time. Used for calculating response time.*/
	public double ACTUATION_TIME = GLOBAL_ACTUATION_TIME;
	
	/**Default Eligibility. Used for determining eligibility (true by default).*/
	public static boolean GLOBAL_DEFAULT_ELIGIBILITY = true;
	/**Default Eligibility. Used for determining eligibility (true by default).*/
	public boolean DEFAULT_ELIGIBILITY = GLOBAL_DEFAULT_ELIGIBILITY;
    
	/**
	 * Initializes an implicit module.
	 * <p>
	 * If this is being used as an implicit module in the ACS and you are using goals
	 * or specialized working memory chunks, remember that the input space must also contain 
	 * all dimension-value pairs in those chunks that are not already contained within the sensory information
	 * space.
	 * @param InputSpace The input space for the implicit module.
	 * @param Outputs The outputs for the implicit module.
	 * @throws InvalidFormatException If the input or output space contain dimensions or chunks respectively that
	 * have the same ID.
	 */
    public AbstractImplicitModule (Collection <Dimension> InputSpace, AbstractOutputChunkCollection <? extends AbstractOutputChunk> Outputs) throws InvalidFormatException
    {
    	InputAsCollection = new DimensionValueCollection ();
    	ArrayList <Object> IDcheck = new ArrayList <Object> (InputSpace.size());
    	for(Dimension i : InputSpace)
    	{
    		if(IDcheck.contains(i.getID()))
    			throw new InvalidFormatException ("The input space contains two dimensions " +
    					"with the same ID.");
    		else
    			IDcheck.add(i.getID());
    		Dimension dd = i.clone();
    		InputAsCollection.put(dd.getID(), dd);
    	}
    	
    	IDcheck = new ArrayList <Object> (Outputs.size());
    	for(AbstractOutputChunk o : Outputs.values())
        {
        	if(IDcheck.contains(o.getID()))
    			throw new InvalidFormatException ("The output contains two output chunks " +
    					"with the same ID.");
    		else
    			IDcheck.add(o.getID());
        }
        Output = Outputs.clone();
    }
	
    /**
     * Gets the input nodes in the form of a dimension-value collection. The collection returned 
     * is unmodifiable and is meant for reporting the internal state only.
     * @return An unmodifiable collection of dimension-value pairs representing the input nodes.
     */
    public Collection <Dimension> getInput()
    {
    	return Collections.unmodifiableCollection(InputAsCollection.values());
    }
    
    /**
     * Sets the activations for the input layer to the specified input.
     * This method should be called before the forwardPass method is called.
     * @param input The input from which to set the activations on the input layer.
     */
    public void setInput (DimensionValueCollection input)
    {
    	for(Dimension i : InputAsCollection.values())
    	{
    		Dimension d = input.get(i.getID());
    		if(d != null)
    		{
    			for(Value v : i.values())
    			{
    				if(d.containsKey(v.getID()))
    					v.setActivation(d.get(v.getID()).getActivation());
    				else
    					v.resetActivation();
    			}
    		}
    	}
    }
    
    /**
     * Sets the activation of the input nodes that equal the specified value to the 
     * activation of the specified value.
     * <p>
     * In general, this method should NOT be used from outside of the CLARION library. This
     * method will set the activation of ANY and ALL values within the input layer that match
     * the ID of the specified value regardless of the dimension to which they belong. This
     * method should only be used if you know for sure that the input layer does not contain
     * values with the same ID or you want to set ALL of the values in the input layer with the
     * same ID to the activation of the specified value.
     * @param val The value related to the input nodes whose activations you wish to set.
     */
    public void setInput (Value val)
    {
    	for(Dimension d : InputAsCollection.values())
    	{
    		for(Value v : d.values())
        	{
        		if(v.equals(val))
        		{
        			v.setActivation(val.getActivation());
        		}	
        	}
    	}
    }
    
    /**
     * Sets the activation of the input node for the dimension with the specified ID 
     * to the activation of the specified value.
     * @param ID The ID of the dimension where the value you wish to set is located.
     * @param val The value related to the input node whose activation you wish to set.
     */
    public void setInput (Object ID, Value val)
    {
    	Dimension d = InputAsCollection.get(ID);
		if(d != null)
		{
			Value v = d.get(val.getID());
    		if(v != null)
    			v.setActivation(val.getActivation());
		}
    }
    
    /**
     * Gets the number of input nodes.
     * @return The number of input nodes.
     */
    public int getNumInput()
    {
       return InputAsCollection.getNumDVPairs();
    }
    
    /**
     * Gets the number of output nodes.
     * @return The number of output nodes.
     */
    public int getNumOutput()
    {
      	return Output.size();
    }
    
    /**
     * Gets the output chunk with the specified ID. If the output does not have a chunk with 
     * the specified ID, this method returns null.
     * @param ID The ID of the output chunk to get.
     * @return The output chunk with the specified ID. Null if the output chunk is not in the
     * output.
     */
    public AbstractOutputChunk getOutput (Object ID)
    {
    	for(AbstractOutputChunk i : Output.values())
    		if(i.getID().equals(ID))
    			return i;
    	return null;
    }

    /**
     * Gets the current output. The collection returned is unmodifiable and is meant
	 * for reporting (or reading) the internal state only.
     * @return An unmodifiable collection containing the output chunks.
     */
    public Collection <? extends AbstractOutputChunk> getOutput()
    {
        return Collections.unmodifiableCollection(Output.values());
    }
    
    /**
     * Gets the chosen output that was last provided to the implicit module.
     * @return The chosen output.
     */
    public AbstractOutputChunk getChosenOutput ()
    {
    	return ChosenOutput;
    }
    
    /**
     * Sets the chosen output for this implicit module. If the specified output 
     * is not within the output layer of the implicit module, this method will do nothing.
     * @param chosenOut The chosen output.
     */
    public void setChosenOutput (AbstractOutputChunk chosenOut)
    {
    	AbstractOutputChunk o = Output.get(chosenOut.getID());
		if(o != null)
			ChosenOutput = o;
    }
    
    /**
     * Gets the response time for the implicit module.
     * @return The response time.
     */
    public double getResponseTime ()
    {
    	return PERCEPTION_TIME + DECISION_TIME + ACTUATION_TIME;
    }
    
	/**
	 * Checks to see if an implicit module is eligible to be used at a given step. If you wish to implement
	 * an eligibility condition for an implicit module, this method should be be overridden.
	 * @return The default eligibility (as specified by the DEFAULT_ELIGIBILITY parameter).
	 */
	public boolean checkEligibility()
	{
		return DEFAULT_ELIGIBILITY;
	}
	
	/**
	 * Checks to see if an implicit module is eligible to be used at a given step. If you wish to implement
	 * an eligibility condition for an implicit module, this method should be be overridden.
	 * @param stamp The current time stamp.
	 * @return True by default.
	 */
	public boolean checkEligibility(long stamp)
	{
		return DEFAULT_ELIGIBILITY;
	}
	
    /**
     * Performs a forward pass from the input to output of the implicit module. This is the method used by
     * the CLARION Library to obtain activations on the output layer given the input.
     */
	public abstract void forwardPass();
}