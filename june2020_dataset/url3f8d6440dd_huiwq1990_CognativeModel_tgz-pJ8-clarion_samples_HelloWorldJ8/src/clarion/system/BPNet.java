package clarion.system;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class implements a 3-layer standard backpropagating neural network within CLARION.
 * It extends the AbstractNeuralNetwork class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * A standard backpropagating neural network is the base type of network in CLARION and while it can be
 * used as the neural network for the bottom level of any subsystem of CLARION, it is not considered to
 * be capable of "ONLINE" (or runtime) learning. Therefore, the network should be pre-trained "offline"
 * before it is added to the appropriate CLARION subsystem. The weight vectors and thresholds for the 
 * network can be hardcoded if they were recorded from a previous training session.
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
 * <li>setDesiredOutput</li>
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
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractRuntimeTrainableBPNet</li>
 * <li>QBPNet</li>
 * <li>SimplifiedQBPNet</li>
 * </ul>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.4
 * @author Nick Wilson
 */
public class BPNet extends AbstractNeuralNet{
    /**The momentum for the hidden layers.*/
    private ArrayList <Double> HiddenMomentum;
    /**The momentum from the output layers.*/
    private ArrayList <Double> OutputMomentum;
    /**The input to hidden momentum matrix.*/
    private ArrayList <ArrayList <Double>> InputToHiddenMomentum;
    /**The hidden to output momentum matrix.*/
    private ArrayList <ArrayList <Double>> HiddenToOutputMomentum;
    /**The derivative of the hidden layers. */
	private ArrayList <Double> HiddenDeriv;
	/**The derivative of the output layers. */
    private ArrayList <Double> OutputDeriv;
    /**The errors of the hidden layer. */
    private ArrayList <Double> HiddenErrors;
    /**The errors of the output*/
    private ArrayList <Double> Errors;
    /**Momentum. used for the updating of weights. */
    public static double GLOBAL_MOMENTUM = 0.1;
    /**Momentum. used for the updating of weights. */
    public double MOMENTUM = GLOBAL_MOMENTUM;
    /**Learning rate. */
    public static double GLOBAL_LEARNING_RATE = 0.1;
    /**Learning rate. */
    public double LEARNING_RATE = GLOBAL_LEARNING_RATE;
    /**Tolerance of error. */
    public static double GLOBAL_RZERO = 0;
    /**Tolerance of error. */
    public double RZERO = GLOBAL_RZERO;

    /**
     * Initializes a backpropagating neural network. 
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
     * @param InputSpace A collection of dimension-value pairs to set as the input nodes.
     * @param NumHidden The number of hidden nodes.
     * @param Outputs The chunks to associate with the output layer.
     */
    public BPNet(Collection <Dimension> InputSpace, int NumHidden, AbstractOutputChunkCollection <? extends AbstractOutputChunk> Outputs)
    {
    	super(InputSpace, NumHidden, Outputs);
    	
    	InputToHiddenMomentum = new ArrayList <ArrayList<Double>> (getNumInput());
        for(int i = 0; i < getNumInput(); i++)
        {
        	InputToHiddenMomentum.add(new ArrayList<Double> (NumHidden));
        	for(int j = 0; j < NumHidden; j++)
        		InputToHiddenMomentum.get(i).add(new Double (MOMENTUM));
        }
        
        HiddenMomentum = new ArrayList <Double> (getNumInput());
    	HiddenErrors = new ArrayList <Double> (getNumInput());
    	HiddenDeriv = new ArrayList <Double> (getNumInput());
    	HiddenToOutputMomentum = new ArrayList <ArrayList <Double>> (getNumInput());
        for(int i = 0; i < NumHidden; i++)
        {
        	HiddenMomentum.add(new Double (0));
        	HiddenErrors.add(new Double (0));
        	HiddenDeriv.add(new Double (0));
        	HiddenToOutputMomentum.add(new ArrayList<Double> (Outputs.size()));
        	for(int j = 0; j < Outputs.size(); j++)
        		HiddenToOutputMomentum.get(i).add(new Double (MOMENTUM));
        }
        
        OutputMomentum = new ArrayList <Double> (Outputs.size());
    	Errors = new ArrayList <Double> (Outputs.size());
    	OutputDeriv = new ArrayList <Double> (Outputs.size());
        for(int i = 0; i < Outputs.size(); i++)
        {
        	OutputMomentum.add(new Double (0));
        	Errors.add(new Double (0));
        	OutputDeriv.add(new Double (0));
        }
    }

    /** 
     * Updates the neural network using standard backpropagation. This method should not be called
     * until the setDesiredOutput method has been called.
     */
    public void backwardPass()
    {
       	computeErrors();
        computeHiddenErrors();
        modifyHiddenToOutput();
        modifyInputToHidden();
    }

    /**
     * Calculates the hidden derivative.
     */
    protected void computeHiddenActivation()
    {
       	double sum,v;
       	for( int j = 0; j < super.Hidden.size(); j++ ){
             sum = HiddenThresholds.get(j);
             for(Dimension d : InputAsCollection.values())
             {
            	 int i = 0;
            	 for(Value val : d.values())
            	 {
            		 sum += val.getActivation() * InputToHiddenWeights.get(i).get(j);
            		 i++;
            	 }
             }
             // simple sigmoid function
             v = (1.0 / (1.0 + Math.exp( -sum)));
             Hidden.set(j, v);
             HiddenDeriv.set(j, derivative(v));
        }
    }

    /**
     * Calculates the output derivative.
     */
    protected void computeOutputActivation ()
    {
       	double sum,v;
       	int j = 0;
       	for(AbstractOutputChunk o : Output.values())
       	{
			sum = OutputThresholds.get(j);
			for( int i = 0; i < Hidden.size(); i++ )
			     sum += Hidden.get(i) * HiddenToOutputWeights.get(i).get(j);
			// simple sigmoid function
			v = (1.0 / (1.0 + Math.exp( -sum)));
			o.setActivation(v);
			OutputDeriv.set(j, derivative(v));
			++j;
       	}
    }

    /**
     * Computes the errors of the output layer.
     */
    private void computeErrors()
    {
        int i = 0;
       	for(AbstractOutputChunk o : Output.values()){
             double err = DesiredOutput.get(o.getID()).getActivation() - o.getActivation();
             Errors.set(i, err * OutputDeriv.get(i));
             ++i;
        }
    }

    /**
     * Computes the errors of the hidden layer.
     */
    private void computeHiddenErrors ()
    {
       	double h_err;
       	for( int i = 0; i < Hidden.size(); i++ ){
             h_err = 0.0;
             for( int j = 0; j < Output.size(); j++ )
     	          h_err += HiddenToOutputWeights.get(i).get(j) * Errors.get(j);
             HiddenErrors.set(i, h_err * HiddenDeriv.get(i));
        }
    }

    /**
     * Modifies the weights of the hidden to output layer.
     */
    protected void modifyHiddenToOutput ()
    {
        double x;
        for( int j = 0; j < Output.size(); j++ ) {
       	     if( Math.abs( Errors.get(j) ) > RZERO ) {
              	 x = Errors.get(j);
                 // update HiddenToOutputWeights
                 for( int i = 0; i < Hidden.size(); i++ ){
                      if( MOMENTUM > RZERO ){
                            /* momentum */
                          HiddenToOutputMomentum.get(i).set(j, LEARNING_RATE * x * Hidden.get(i) +
                                  MOMENTUM * HiddenToOutputMomentum.get(i).get(j));
                          HiddenToOutputWeights.get(i).set(j, 
                        		  HiddenToOutputWeights.get(i).get(j) + 
                        		  HiddenToOutputMomentum.get(i).get(j));
                      }
                      else
                          HiddenToOutputWeights.get(i).set(j, HiddenToOutputWeights.get(i).get(j)
                        		  + LEARNING_RATE * x * Hidden.get(i));
                 }
                 // update OutputThresholds
                 if( MOMENTUM > RZERO ){
                     OutputMomentum.set(j, LEARNING_RATE * x + MOMENTUM * OutputMomentum.get(j));
                     OutputThresholds.set(j, OutputThresholds.get(j) + OutputMomentum.get(j));
                 }
                 else
                     OutputThresholds.set(j, OutputThresholds.get(j) + LEARNING_RATE * x);
      	    }
       	}
    }

    /**
     * Modifies the weights of the input to hidden layer.
     */
    protected void modifyInputToHidden ()
    {
       	double x;
       	for( int j = 0; j < Hidden.size(); j++ ){
             if( Math.abs( HiddenErrors.get(j) ) > RZERO ){
                 x = HiddenErrors.get(j);
                 // update the InputToHiddenWeights
                 int i = 0;
            	 for(Value v : InputAsCollection.getValueCollection()){
                     if( MOMENTUM > RZERO ){
                     	 InputToHiddenMomentum.get(i).set(j, LEARNING_RATE * x* v.getActivation()
                     			 + MOMENTUM * InputToHiddenMomentum.get(i).get(j));
                     	 InputToHiddenWeights.get(i).set(j, InputToHiddenWeights.get(i).get(j) + 
                     			 InputToHiddenMomentum.get(i).get(j));
                     }
                     else
                    	 InputToHiddenWeights.get(i).set(j, InputToHiddenWeights.get(i).get(j) + 
                    			 LEARNING_RATE * x * v.getActivation());
                     ++i;
               	 }
                 // update the HiddenThresholds
            	 if( MOMENTUM > RZERO){
               	     HiddenMomentum.set(j, LEARNING_RATE * x + MOMENTUM * HiddenMomentum.get(j));
               	     HiddenThresholds.set(j, HiddenThresholds.get(j) + HiddenMomentum.get(j));
                 }
                 else
        	     HiddenThresholds.set(j, HiddenThresholds.get(j) + LEARNING_RATE * x);
             }
     	}
    }

    /**
     * The derivative of the activation function. For updating input to hidden layer weights.
     * @param input The weighted input to the node.
     * @return The value of the derivative of the activation function for a node 
     * given the weighted input.
     */
    private double derivative( double input )
    {
       	return( input * ( 1 - input ) );
    }
}
