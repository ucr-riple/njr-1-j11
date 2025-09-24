package clarion.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Collections;

/**
 * This class implements a three-layer, feed-forward neural network within CLARION. It extends the 
 * AbstractTrainableImplicitModule class. This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class provides the framework for implementing a three-layer, feed forward neural network. If you want to
 * define your own three-layer, feed forward network can do so by extending this class. Any classes that extend
 * this class can be used as an implicit module on the bottom level of the CLARION subsystems.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractRuntimeTrainableBPNet</li>
 * <li>BPNet</li>
 * <li>QBPNet</li>
 * <li>SimplifiedQBPNet</li>
 * </ul>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.4
 * @author Nick Wilson & Xi Zang
 */
public abstract class AbstractNeuralNet extends AbstractTrainableImplicitModule{
    /**The hidden layer.*/
    protected ArrayList <Double> Hidden;
    /**The thresholds for the hidden layer.*/
    protected ArrayList <Double> HiddenThresholds;
    /**The thresholds for the output layer.*/
    protected ArrayList <Double> OutputThresholds;
    /**The input to hidden weight matrix.*/
    protected ArrayList <ArrayList <Double>> InputToHiddenWeights;
    /**The hidden to output weight matrix.*/
    protected ArrayList <ArrayList <Double>>HiddenToOutputWeights;
    /**The upper bound for the initial weights.*/
    public static double  GLOBAL_UPPER_INIT_WEIGHT = 0.01;
    /**The low bound for the initial weights.*/
    public static double  GLOBAL_LOWER_INIT_WEIGHT = -0.01;
    /**The upper bound for the initial threshold.*/
    public static double  GLOBAL_UPPER_INIT_THRESHOLD = 0.1;
    /**The low bound for the initial threshold.*/
    public static double  GLOBAL_LOWER_INIT_THRESHOLD = -0.1;
    /**The upper bound for the initial weights.*/
    public double  UPPER_INIT_WEIGHT = GLOBAL_UPPER_INIT_WEIGHT;
    /**The low bound for the initial weights.*/
    public double  LOWER_INIT_WEIGHT = GLOBAL_LOWER_INIT_WEIGHT;
    /**The upper bound for the initial threshold.*/
    public double  UPPER_INIT_THRESHOLD = GLOBAL_UPPER_INIT_THRESHOLD;
    /**The low bound for the initial threshold.*/
    public double  LOWER_INIT_THRESHOLD = GLOBAL_LOWER_INIT_THRESHOLD;
    
	/**
     * Initializes a neural network.
     * <p>
	 * If this is being used as an implicit module in the ACS and you are using goals
	 * or specialized working memory chunks, remember that the input space must also contain 
	 * all dimension-value pairs within those chunks that differ from the sensory information
	 * space.
     * @param InputSpace A collection of dimension-value pairs to set as the input nodes.
     * @param NumHidden The number of hidden nodes.
     * @param Outputs The chunks to associate with the output layer.
     */
    public AbstractNeuralNet(Collection <Dimension> InputSpace, int NumHidden, AbstractOutputChunkCollection <? extends AbstractOutputChunk> Outputs)
    {
    	super(InputSpace, Outputs);
    	InputToHiddenWeights = new ArrayList <ArrayList<Double>> (getNumInput());
        for(int i = 0; i < getNumInput(); ++i)
        {
        	InputToHiddenWeights.add(new ArrayList<Double> (NumHidden));
        	for(int j = 0; j < NumHidden; ++j)
        		InputToHiddenWeights.get(i).add(new Double (randomWeights()));
        }
        
        Hidden = new ArrayList <Double> (NumHidden);
        HiddenThresholds = new ArrayList <Double> (NumHidden);
        HiddenToOutputWeights = new ArrayList <ArrayList<Double>> (NumHidden);
        for(int i = 0; i < NumHidden; ++i)
        {
        	Hidden.add(new Double (0));
        	HiddenThresholds.add(new Double (randomThresholds()));
        	HiddenToOutputWeights.add(new ArrayList<Double> (Outputs.size()));
        	for(int j = 0; j < Outputs.size(); ++j)
        		HiddenToOutputWeights.get(i).add(new Double (randomWeights()));
        }
        OutputThresholds = new ArrayList <Double> (Output.size());
        for(int i = 0; i < Output.size(); i++)
        	OutputThresholds.add(new Double (randomThresholds()));

        initWeights();
    }

    /**
     * Gets the number of hidden nodes.
     * @return The number of hidden nodes.
     */
    public int getNumHidden()
    {
       return Hidden.size();
    }

    /**
     * Gets the input to hidden layer weight matrix. This is usually only used to
     * collect the weights so the network can be hard coded at a later date.
     * The list returned is unmodifiable and is meant for reporting the internal state only.
     * @return the input to hidden layer weight matrix as an unmodifiable list containing
     * unmodifiable lists (i.e. a 2x2 read only matrix).
     */
    public List <List <Double>> getItoHWeightMatrix()
    {
    	ArrayList <List <Double>> a = 
    		new ArrayList <List <Double>> (InputToHiddenWeights.size());
    	for(ArrayList <Double> i : InputToHiddenWeights)
    		a.add(Collections.unmodifiableList(i));
    	return Collections.unmodifiableList(a);
    }
    
    /**
     * Gets the hidden to output layer weight matrix. This is usually only used to
     * collect the weights so the network can be hard coded at a later date.
     * The list returned is unmodifiable and is meant for reporting the internal state only.
     * @return the hidden to output layer weight matrix as a list containing
     * unmodifiable lists (i.e. a 2x2 read only matrix).
     */
    public List <List <Double>> getHtoOWeightMatrix()
    {
    	ArrayList <List <Double>> a = 
    		new ArrayList <List <Double>> (HiddenToOutputWeights.size());
    	for(ArrayList <Double> i : HiddenToOutputWeights)
    		a.add(Collections.unmodifiableList(i));
    	return a;
    }
    
    /**
     * Gets the hidden layer thresholds. This is usually only used to
     * collect the weights so the network can be hard coded at a later date.
     * The list returned is unmodifiable and is meant for reporting the internal state only.
     * @return An unmodifiable list representing the hidden layer thresholds for the network.
     */
    public List <Double> getHiddenThresholds()
    {
    	return Collections.unmodifiableList(HiddenThresholds);
    }
    
    /**
     * Gets the output layer thresholds. This is usually only used to
     * collect the weights so the network can be hard coded at a later date.
     * The list returned is unmodifiable and is meant for reporting the internal state only.
     * @return An unmodifiable list representing the output layer thresholds for the network.
     */
    public List <Double> getOutputThresholds()
    {
    	return Collections.unmodifiableList(OutputThresholds);
    }
    
    /**
     * Hard codes the weights of a neural net.
     * @param ItoH The Input to Hidden layer weights.
     * @param HtoO The Hidden to Output layer weights.
     * @param HiddenThreshold The thresholds for the hidden layer.
     * @param OutputThreshold The thresholds for the output layer.
     */
    public void hardcodeWeights(List <? extends List <Double>> ItoH, List <? extends List <Double>> HtoO, 
    		List <Double> HiddenThreshold, Collection <Double> OutputThreshold)
    {
    	InputToHiddenWeights.clear();
    	for(List <Double> a : ItoH)
    	{
    		ArrayList <Double> b = new ArrayList <Double> (a.size());
    		for(Double c : a)
    			b.add(new Double(c.doubleValue()));
    		InputToHiddenWeights.add(b);
    	}
    	
    	HiddenToOutputWeights.clear();
    	for(List <Double> a : HtoO)
    	{
    		ArrayList <Double> b = new ArrayList <Double> (a.size());
    		for(Double c : a)
    			b.add(new Double(c.doubleValue()));
    		HiddenToOutputWeights.add(b);
    	}
    	
    	HiddenThresholds.clear();
    	for(Double d : HiddenThreshold)
    		HiddenThresholds.add(new Double(d.doubleValue()));
    	
    	OutputThresholds.clear();
    	for(Double d : OutputThreshold)
    		OutputThresholds.add(new Double(d.doubleValue()));
    }
    
    /**
     * Calculates the output activations based on the current input. This method should not 
     * be called until the setInput method has been called setting the input activations to 
     * the current state.
     */
    public void forwardPass()
    {
       	computeHiddenActivation();
        computeOutputActivation();
    }
    
    /**
     * Initializes the weights to random values.
     */
    private void initWeights()
    {   
        for( int i = 0; i < Hidden.size(); ++i)
      	{
      		HiddenThresholds.set(i, randomThresholds());
      	}

      	for( int i = 0; i < Output.size(); ++i )
      	{
             OutputThresholds.set(i, randomThresholds());
      	}
      	
   	    for( int i = 0; i < getNumInput(); ++i )
   	    {
         	for( int j = 0; j < Hidden.size(); ++j )
         	{
         		InputToHiddenWeights.get(i).set(j, randomWeights());
         	}
   	    }
   	    
        for (int i = 0; i < Hidden.size(); ++i)
        {
            for (int j = 0; j < Output.size(); j++)
            {
            	HiddenToOutputWeights.get(i).set(j, randomWeights());
            }
        }
    }
    
    /**
     * Generates a random weight.
     * @return A randomly selected weight.
     */
    private double randomWeights()
    {
       	double sign,value;
        if ( Math.random() < 0.5 )
             sign = LOWER_INIT_WEIGHT;
        else
             sign = UPPER_INIT_WEIGHT;
        value = Math.random();
        return( value * sign );
    }
    
    /**
     * Generates a random threshold.
     * @return A randomly selected threshold.
     */
    private double randomThresholds()
    {
       	double sign,value;
        if ( Math.random() < 0.5 )
             sign = LOWER_INIT_THRESHOLD;
        else
             sign = UPPER_INIT_THRESHOLD;
        value = Math.random();
        return( value * sign );
    }
    
    /**
     * Calculates the hidden derivative.
     */
    protected abstract void computeHiddenActivation();

    /**
     * Calculates the output derivative.
     */
    protected abstract void computeOutputActivation ();
    
    /**
     * Modifies the weights of the hidden to output layer.
     */
    protected abstract void modifyHiddenToOutput ();

    /**
     * Modifies the weights of input to hidden layer.
     */
    protected abstract void modifyInputToHidden ();
    
    /** 
     * Updates the neural network. This method should not be called
     * until the setDesiredOutput method has been called.
     */
    public abstract void backwardPass();
}
