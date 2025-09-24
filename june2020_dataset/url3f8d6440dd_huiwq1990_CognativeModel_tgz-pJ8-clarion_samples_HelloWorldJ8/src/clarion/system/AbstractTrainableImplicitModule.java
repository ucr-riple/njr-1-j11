package clarion.system;

import java.util.Collection;

/**
 * This class implements a trainable implicit module within CLARION. It extends the AbstractImplicitModule class
 * and implements the InterfaceTrainable interface. This class is abstract and therefore cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * The trainable implicit module is a framework for building an implicit module that can be used in the bottom level of 
 * the CLARION subsystems. A trainable implicit module is different from some other implicit modules in that it
 * can be trained. Any implicit modules that extend this class has the capability of being trained.
 * <p>
 * Note: Implicit modules that extend this class are NOT necessarily trainable during runtime. This class only allows for a
 * implicit module to be pre-trained prior to being attached to a CLARION subsystem. If you want to implement an implicit module that 
 * is trainable during runtime from within CLARION, extend the AbstractRuntimeTrainableImpicitModule instead.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>AbstractNeuralNet</li>
 * <li>AbstractRuntimeTrainableBPNet</li>
 * <li>AbstractRuntimeTrainableImplicitModule</li>
 * <li>BPNet</li>
 * <li>QBPNet</li>
 * <li>SimplifiedQBPNet</li>
 * <li>TableLookup</li>
 * </ul>
 * @version 6.0.4
 * @author Nick Wilson
 */
public abstract class AbstractTrainableImplicitModule 
	extends AbstractImplicitModule implements InterfaceTrainable{
	
    /**The desired output.*/
    protected AbstractOutputChunkCollection <? extends AbstractOutputChunk> DesiredOutput;
    
    /**
     * Initializes the trainable implicit module.
     * <p>
	 * If this is being used as an implicit module in the ACS and you are using goals
	 * or specialized working memory chunks, remember that the input space must also contain 
	 * all dimension-value pairs within those chunks that differ from the sensory information
	 * space.
     * @param InputSpace The input space for the implicit module.
     * @param Outputs The outputs for the implicit module.
     */
    public AbstractTrainableImplicitModule (Collection <Dimension> InputSpace, AbstractOutputChunkCollection <? extends AbstractOutputChunk> Outputs)
    {
    	super(InputSpace, Outputs);
    	DesiredOutput = Outputs.clone();
    }
	
    /**
     * Sets the desired output given the collection of desired outputs.
     * <p>
     * Note that you should reset the desired output before you call this method and this 
     * method should be called before the backwardPass method is called.
     * @param DesiredOut A collection of output chunks to set as the desired output.
     */
    public void setDesiredOutput (Collection <? extends AbstractOutputChunk> DesiredOut)
    {
    	for(AbstractOutputChunk o : DesiredOut)
    		setDesiredOutput(o);
    }
    
    /**
     * Sets the desired output for the network given the specified desired output. If the
     * specified output is not within the output layer of the implicit module, this method
     * will do nothing.
     * <p>
     * Note that you should reset the desired output before you call this method and this 
     * method should be called before the backwardPass method is called.
     * @param DesiredOut The desired output to set.
     */
    public void setDesiredOutput (AbstractOutputChunk DesiredOut)
    {
    	AbstractOutputChunk o = DesiredOutput.get(DesiredOut.getID());
		if(o != null)
			o.setActivation(DesiredOut.getActivation());
    }
    
    /**
     * Performs a backward pass from the output to the input of the implicit module, "learning" (as defined
     * by the user) based on the desired output. This is the method used by the CLARION Library to learn (if the
     * implicit module is runtime trainable).
     */
	public abstract void backwardPass();
	
    /**
     * Gets the sum of squared errors.
     * @return The sum of squared errors.
     */
    public double getSumSqErrors()
    {
        double SumSqErrors = 0;
        double err;
        int i = 0;
       	for(AbstractOutputChunk o : Output.values()){
             err = DesiredOutput.get(o.getID()).getActivation() - o.getActivation();
             SumSqErrors += err * err;
             ++i;
        }
       	return SumSqErrors;
    }
}
