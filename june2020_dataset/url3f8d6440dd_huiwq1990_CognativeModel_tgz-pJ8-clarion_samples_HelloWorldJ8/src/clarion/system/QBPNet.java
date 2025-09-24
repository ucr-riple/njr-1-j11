package clarion.system;

import java.util.Collection;
import java.util.Collections;

/**
 * This class implements a Q-learning backpropagating neural network within CLARION. 
 * It extends the AbstractRuntimeTrainableBPNet class and implements the InterfaceHandlesNewInput,
 * InterfaceUsesQLearning, and InterfaceHasMatchCalculator interfaces.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A Q-learning backpropagating neural network uses the Q-learning reinforcement algorithm to train the network
 * and is capable of performing learning during runtime. 
 * <p>
 * Note that the weight vectors and thresholds for the network can be hardcoded if they were recorded 
 * from a previous training session (just like a standard backpropagating neural network).
 * <p>
 * The Input for the neural network is a collection of dimension-values where each value represents one node.
 * <p>
 * If the network is being used as an action network in the ACS and you are using goals
 * or specialized working memory chunks, remember that the input space must also contain 
 * all dimension-value pairs within those chunks that differ from the sensory information
 * space.
 * <p>
 * Once the input space has been defined it cannot be changed. At any given forward pass through the network, the 
 * network can accept an arbitrary collection of dimension-values as input, but it will only adjust the activations of 
 * the inputs that were specified during initialization.
 * <p>
 * The nodes in the output layer are represented as output chunks.
 * <p>
 * The general procedure when using this class is:<br>
 * <ol>
 * <li>setInput</li>
 * <li>forwardPass</li>
 * <li>setChosenAction</li>
 * <li>setFeedback</li>
 * <li>setNewState</li>
 * <li>backwardPass</li>
 * <li>Goto Step 1</li>
 * </ol>
 * <p>
 * Note: The current implementation of CLARION does not allow for multiple action dimensions 
 * on the bottom level as stipulated in the CLARION tutorial. However, actions that contain multiple
 * action dimensions are still possible by simply specifying action chunks on the top level that contain
 * multiple activated action dimensions. This is the case because the output of the bottom level only
 * specifies actions and not action dimension-values.
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class QBPNet extends AbstractRuntimeTrainableBPNet implements InterfaceHandlesNewInput, InterfaceUsesQLearning, InterfaceHasMatchCalculator{
	/**The discount factor for q-learning.*/
	public static double GLOBAL_DISCOUNT = .99;
	/**The discount factor for q-learning.*/
    public double DISCOUNT = GLOBAL_DISCOUNT;
    
    /**The match calculator used for updating match statistic within this class.*/
    private AbstractMatchCalculator LocalMatchCalculator = new QLearningMatchCalculator (this);
    
	/**The new input after the chosen output is performed (if network is an action network
	 * that leads to a new state) represented as a collection.*/
	protected DimensionValueCollection NewInput;
    
    /**
     * Initializes a backpropagating neural network that uses Q-Learning for 
     * training the network.
     * <p>
	 * The Input for the neural network is a collection of dimension-values where each value 
	 * represents one node.
	 * <p>
	 * If the network is being used as an action network in the ACS and you are using goals
	 * or specialized working memory chunks, remember that the input space must also contain 
	 * all dimension-value pairs within those chunks that differ from the sensory information
	 * space.
	 * <p>
	 * Once input has been set it cannot be changed. At any given forward pass through the network, 
	 * the network can accept an arbitrary collection of dimension-values as input, but it will only 
	 * adjust the activations of the inputs that were specified during initialization.
	 * <p>
	 * The nodes in the output layer are represented as output chunks.
     * @param InputSpace A collection of dimension-value pairs to set as the input nodes.
     * @param NumHidden The number of hidden nodes.
     * @param Outputs The chunks to associate with the output layer.
     */
    public QBPNet(Collection <Dimension> InputSpace, int NumHidden, AbstractOutputChunkCollection <? extends AbstractOutputChunk> Outputs)
    {
    	super(InputSpace,NumHidden,Outputs);
    	NewInput = new DimensionValueCollection ();
    	for(Dimension i : InputSpace)
    	{
    		Dimension nd = i.clone();
    		NewInput.put(nd.getID(), nd);
    	}
    }
    
    /** 
     * Updates the neural network using Q-Learning. This method should not be called before the
     * setFeedback, setChosenAction, and setNewState methods have been called.
     */
    public void backwardPass()
    {
		double maxQ = DISCOUNT * getMaxQ();
		for(AbstractOutputChunk i : Output.values())
		{
			if(getChosenOutput().getID().equals(i.getID()))
				DesiredOutput.get(i.getID()).setActivation(getFeedback() + maxQ);
			else
				DesiredOutput.get(i.getID()).setActivation(i.getActivation());
		}
		super.backwardPass();
    }
    
	/**
     * Returns the new input in the form of a dimension-value collection. The collection returned is 
     * unmodifiable and is meant for reporting the internal state only.
     * @return An unmodifiable collection of dimension-value pairs representing the new input nodes 
     * of the network.
     */
    public Collection <Dimension> getNewInput()
    {
    	return Collections.unmodifiableCollection(NewInput.values());
    }
    
    /**
     * Sets the activations for the new input to the specified input.
     * If the new input is used to update the weights then this method should be called 
     * before the backwardPass method is called.
     * @param input The new input from which to set the activations on the new input.
     */
    public void setNewInput (Collection <Dimension> input)
    {
    	for(Dimension i : input)
    	{
    		Dimension d = NewInput.get(i.getID());
    		if(d != null)
    		{
    			for(Value v : d.values())
    			{
    				if(i.containsKey(v.getID()))
    					v.setActivation(i.get(v.getID()).getActivation());
    				else
    					v.resetActivation();
    			}
    		}
    	}
    }
    
    /**
     * Checks to see if the extraction criterion is satisfied given the state before action a is 
     * performed, the state after action a is performed, any immediate feedback received, and the 
     * index of the action performed. This check is usually performed before the backwardPass method 
     * is called but after the setFeedback, setChosenAction, and setNewInput methods have been called.
     * @return True if the extraction criterion has been satisfied, otherwise false.
     */
    public boolean checkExtraction()
    {
    	double Ey = DISCOUNT * getMaxQ();
    	if(Ey + getFeedback() - getChosenOutput().getActivation() > POSITIVE_MATCH_THRESHOLD)
    		return true;
    	else
    		return false;
    }
    
    /**
     * Gets the value of Max(Q(y,b)) where y is equal to the new state.
     * @return The maximum Q value at the new state.
     */
    public double getMaxQ ()
    {
    	double MaxQ = 0;
    	DimensionValueCollection oldState = new DimensionValueCollection ();
    	for(Dimension i : getInput())
    		oldState.put(i.getID(),i.clone());
		setInput(NewInput);
		forwardPass();
		for(AbstractOutputChunk i : Output.values())
		{
			if(MaxQ < i.getActivation())
				MaxQ = i.getActivation();
		}
		super.setInput(oldState);
		super.forwardPass();
    	return MaxQ;
    }

	public double getDiscount() {
		return DISCOUNT;
	}
	
	public AbstractMatchCalculator getMatchCalculator() {
		return LocalMatchCalculator;
	}

	public void setMatchCalculator(AbstractMatchCalculator MatchCalculator) {
		LocalMatchCalculator = MatchCalculator;
	}
}
