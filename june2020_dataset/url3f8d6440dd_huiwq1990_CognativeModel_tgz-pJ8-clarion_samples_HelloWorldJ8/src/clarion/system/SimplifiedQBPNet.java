package clarion.system;

import java.util.Collection;

/**
 * This class implements a simplified Q-learning backpropagating neural network within CLARION. 
 * It extends the AbstractRuntimeTrainableBPNet class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A simplified Q-learning backpropagating neural network can be taken advantage of when reinforcement to train 
 * the network is recieved at every time step. This class is capable of performing learning during runtime. 
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
 * <li>backwardPass</li>
 * <li>Goto Step 1</li>
 * </ol>
 * <p>
 * Note: The current implementation of CLARION does not allow for multiple action dimensions 
 * on the bottom level as stipulated in the CLARION tutorial. However, actions that contain multiple
 * action dimensions are still possible by simply specifying action chunks on the top level that contain
 * multiple activated action dimensions. This is the case because the output of the bottom level only
 * specifies actions and not action dimension-values.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class SimplifiedQBPNet extends AbstractRuntimeTrainableBPNet{
    
    /**
     * Initializes a backpropagating neural network that uses simplified Q-Learning for 
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
    public SimplifiedQBPNet(Collection <Dimension> InputSpace, int NumHidden, AbstractOutputChunkCollection <? extends AbstractOutputChunk> Outputs)
    {
    	super(InputSpace,NumHidden,Outputs);
    }
    
	 /** 
     * Updates the neural network using simplified Q-Learning. This method should not be called
     * before the setFeedback and setChosenAction methods have been called.
     */
    public void backwardPass()
    {
    	for(AbstractOutputChunk i : Output.values())
		{
			if(getChosenOutput().getID().equals(i.getID()))
				DesiredOutput.get(i.getID()).setActivation(getFeedback());
			else
				DesiredOutput.get(i.getID()).setActivation(i.getActivation());
		}
	  	super.backwardPass();
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
}
