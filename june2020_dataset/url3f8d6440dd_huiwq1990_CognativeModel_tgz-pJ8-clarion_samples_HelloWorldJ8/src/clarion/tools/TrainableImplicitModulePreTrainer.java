package clarion.tools;

import java.util.Collection;
import java.util.ListIterator;
import java.util.LinkedList;

import clarion.system.*;

/**
 * This class implements a pre-trainer that can be used on trainable implicit modules in CLARION. 
 * <p>
 * <b>Usage:</b>
 * <p>
 * The pre-trainer is a tool for training implicit modules that are trainable (i.e. they extend from
 * AbstractTrainableImplicitModule). Any class which extends the AbstractTriainableImplicitModule class
 * can be trained by this pre-trainer.
 * <p>
 * This class is called a PRE-trainer because it is intended to be used "offline" BEFORE the start of a 
 * task (i.e. during initialization).
 * <p>
 * If the implicit module you wish to train is a "runtime trainable" implicit module (i.e. it extends from
 * AbstractRuntimeTrainableImplicitModule), it will be trained in an "online" fashion. That
 * is, at each time step, the "runtime trainable" implicit module you are training will stochastically choose
 * an output, receive feedback, and learn based on the feedback.
 * <p>
 * Below are the things that need to be provided in order to use this pre-trainer:
 * <ol>
 * <li>A target AbstractTrainableImplicitModule<br>
 * <i><ul>
 * <li>The module you intend to train.</li>
 * </ul></i>
 * </li>
 * <li>An AbstractImplicitModule to act as a trainer.<br>
 * <i><ul>
 * <li>Can be ANY implicit module.<br>Examples:<br>
 * <ul>
 * <li>An equation (such as the drive equation, goal equation, etc.).</li>
 * <li>A table lookup (for learning the values in a table).<br>
 * <ul><li>Ideal for training with a training set.</li></ul></li>
 * <li>Another, already trained, trainable implicit module.<br>
 * <ul><li>Target module is trained to imitate the trainer module.</li></ul></li>
 * </ul>
 * </li>
 * <li>Note: The inputs and outputs of the trainer should be in the same format as
 * the target.</li>
 * </ul></i>
 * </li>
 * <li>A data set, specified as a collection of dimension-value collections.<br>
 * <i><ul>
 * <li>Examples of possible data sets:<br>
 * <ul>
 * <li>Training sets<br>
 * <ul><li>A collection of dimension-value collections where each dimension-value collection is
 * a different configuration of the inputs of the target and training modules. In other words,
 * each dimension-value collection is a different "state."</li></ul>
 * </li>
 * <li>Ranges<br>
 * <ul><li>A collection of dimension-value collections where the Values inside the Dimensions are
 * of type "Range" and have the same ID as the Values of the inputs of the target module.</li>
 * <li>If a "Range" is specified for a Value within a Dimension for one of these dimension-value 
 * collections, the associated Value in the input layer of the target module will be trained
 * to handle all of the activations between the lower-bound and the upper-bound at a precision
 * determined by the increment parameter specified by that range.</li></ul>
 * </li>
 * <li>A combination of ranges and training sets<br>
 * <ul><li>Where some dimension-value collections are training sets and others are ranges.</li>
 * <li>Where each dimension-value collection has a combination of Values and Ranges.
 * (i.e. each "state" has some Values that are Ranges and others that are simply Values).</li></ul></li>
 * </ul>
 * <li>Note: The data set just specifies the inputs to use during training. It is the "trainer" 
 * module's job to provide the desired output for the target.</li>
 * <li>Also, if you wish to use only one data set (i.e. a single dimension-value collection) for 
 * training, then simply create a collection of dimension-value collections that contains only 1
 * dimension-value collection.<br>
 * <ul><li>This is most often the case when you want to train ALL of the input nodes using ranges.
 * </li></ul>
 * </li>
 * </ul>
 * </i>
 * </li>
 * </ol>
 * <p>
 * There are three possible termination conditions for training 
 * (as determined by the TERMINATION_CONDITION constant):<br>
 * <ol>
 * <li>Can train for a fixed number of trials (a trial is 1 time through the data set).<br>
 * <i><ul><li>As determined by the NUM_TRAINING_REPEATS constant (5000 by default)</li>
 * <li>This is the default option.</li></ul></i></li>
 * <li>Can train until the sum of squared errors reaches a certain threshold.<br>
 * <i><ul><li>As determined by the SUM_SQ_ERRORS_THRESHOLD constant (.001 by default)</li></ul></i></li>
 * <li>A combination of a fixed number of trials AND sum of squared errors.<br>
 * <i><ul><li>This option will train until the sum of squared errors reaches the threshold OR the fixed 
 * number of trials is reached (whichever comes first)</li></ul></i></li>
 * </ol>
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.4.5
 * @author Nick Wilson
 */

public class TrainableImplicitModulePreTrainer {
	/**The termination condition options*/
    public enum TerminationConditions {FIXED, SUM_SQ_ERROR, BOTH};
    
    /**The number of times to run through the training set before ending training.*/
    public static int GLOBAL_NUM_TRAINING_REPEATS = 5000;
    /**The threshold the sum of squared errors must meet before training can end.*/
    public static double GLOBAL_SUM_SQ_ERRORS_THRESHOLD = .001;
    /**The method of determining the termination condition(s) when training BPNets.*/ 
    public static TerminationConditions GLOBAL_TERMINATION_CONDITION = TerminationConditions.FIXED;
    /**The number of times to run through the training set before ending training.*/
    public int NUM_TRAINING_REPEATS = GLOBAL_NUM_TRAINING_REPEATS;
    /**The threshold the sum of squared errors must meet before training can end.*/
    public double SUM_SQ_ERRORS_THRESHOLD = GLOBAL_SUM_SQ_ERRORS_THRESHOLD;
    /**The method of determining the termination condition(s) when training BPNets.*/ 
    public TerminationConditions TERMINATION_CONDITION = GLOBAL_TERMINATION_CONDITION;
    
    /**Option for printing out the progress of training (false by default)*/
    public static boolean GLOBAL_PRINT_PROGRESS_TO_SYSTEM_OUT = false;
    /**Option for printing out the progress of training (false by default)*/
    public boolean PRINT_PROGRESS_TO_SYSTEM_OUT = GLOBAL_PRINT_PROGRESS_TO_SYSTEM_OUT;
    
    /**The stochastic selector to use if the target module to be trained is runtime trainable.*/
    public StochasticSelector SELECTOR = new StochasticSelector ();
    
    /**
     * Trains the specified target implicit module using the specified training implicit module over the
     * specified data set.
     * @param target The target implicit module to train.
     * @param trainer The implicit module to use as the trainer.<br>
	 * <i><ul>
	 * <li>Can be ANY implicit module.<br>Examples:<br>
	 * <ul>
	 * <li>An equation (such as the drive equation, goal equation, etc.).</li>
	 * <li>A table lookup (for learning the values in a table).<br>
	 * <ul><li>Ideal for training with a training set.</li></ul></li>
	 * <li>Another, already trained, trainable implicit module.<br>
	 * <ul><li>Target module is trained to imitate the trainer module.</li></ul></li>
	 * </ul>
	 * </li>
	 * <li>Note: The inputs and outputs of the trainer should be in the same format as
	 * the target.</li>
	 * </ul></i>
     * @param data The data set over which to train the target module.<br>
     * <i><ul>
	 * <li>Examples of possible data sets:<br>
	 * <ul>
	 * <li>Training sets<br>
	 * <ul><li>A collection of dimension-value collections where each dimension-value collection is
	 * a different configuration of the inputs of the target and training modules. In other words,
	 * each dimension-value collection is a different "state."</li></ul>
	 * </li>
	 * <li>Ranges<br>
	 * <ul><li>A collection of dimension-value collections where the Values inside the Dimensions are
	 * of type "Range" and have the same ID as the Values of the inputs of the target module.</li>
	 * <li>If a "Range" is specified for a Value within a Dimension for one of these dimension-value 
	 * collections, the associated Value in the input layer of the target module will be trained
	 * to handle all of the activations between the lower-bound and the upper-bound at a precision
	 * determined by the increment parameter specified by that range.</li></ul>
	 * </li>
	 * <li>A combination of ranges and training sets<br>
	 * <ul><li>Where some dimension-value collections are training sets and others are ranges.</li>
	 * <li>Where each dimension-value collection has a combination of Values and Ranges.
	 * (i.e. each "state" has some Values that are Ranges and others that are simply Values).</li></ul></li>
	 * </ul>
	 * <li>Note: The data set just specifies the inputs to use during training. It is the "trainer" 
	 * module's job to provide the desired output for the target.</li>
	 * <li>Also, if you wish to use only one data set (i.e. a single dimension-value collection) for 
	 * training, then simply create a collection of dimension-value collections that contains only 1
	 * dimension-value collection.<br>
	 * <ul><li>This is most often the case when you want to train ALL of the input nodes using ranges.
	 * </li></ul>
	 * </li>
	 * </ul>
	 * </i>
     */
    public void trainModule(AbstractTrainableImplicitModule target, AbstractImplicitModule trainer, Collection <? extends DimensionValueCollection> data)
    {
		SumSquaredErrorTracker sqe = new SumSquaredErrorTracker ();
		for(int i = 0; (TERMINATION_CONDITION == TerminationConditions.FIXED && i < NUM_TRAINING_REPEATS) || 
				(TERMINATION_CONDITION == TerminationConditions.SUM_SQ_ERROR && sqe.getMeanSumOfSquaredErrors() > SUM_SQ_ERRORS_THRESHOLD) ||
				(TERMINATION_CONDITION == TerminationConditions.BOTH && i < NUM_TRAINING_REPEATS  && sqe.getMeanSumOfSquaredErrors() > SUM_SQ_ERRORS_THRESHOLD); i++)
		{
			if((TERMINATION_CONDITION == TerminationConditions.FIXED || TERMINATION_CONDITION == TerminationConditions.BOTH) 
					&& PRINT_PROGRESS_TO_SYSTEM_OUT)
			{
				System.out.println("Training Trial # " + (i+1));
			}
			
			sqe.sumsqerr = 0;
			sqe.sumsqerrcounter = 0;
			for(DimensionValueCollection dmc : data)
			{
		    	LinkedList <Dimension> d = new LinkedList<Dimension> (dmc.values());
		    	LinkedList <Value> v = new LinkedList<Value> (d.getFirst().values());
		    	dataRecursor(target, trainer, d.listIterator(), v.listIterator(), sqe);
			}
			
			if((TERMINATION_CONDITION == TerminationConditions.SUM_SQ_ERROR || TERMINATION_CONDITION == TerminationConditions.BOTH) 
					&& PRINT_PROGRESS_TO_SYSTEM_OUT)
			{
				System.out.println("Mean Sum of Squared Error: " + sqe.getMeanSumOfSquaredErrors());
			}
		 }
	 }
     
    /**
     * Recursive method that iterates over the dimensions of a dimension-value collection (from the data set 
     * and trains the specified target implicit module based on the outputs from the specified trainer implicit 
     * module.
     * @param target The target implicit module to be trained.
     * @param trainer The implicit module to be used as the trainer.
     * @param dim A list iterator for the dimensions.
     * @param val A list iterator for the values within the dimension currently being addressed.
     * @param sqe The sum of squared error tracker being used to track the sum of squared error.
     */
	private void dataRecursor (AbstractTrainableImplicitModule target, AbstractImplicitModule trainer, ListIterator<Dimension> dim, ListIterator<? extends Value> val, SumSquaredErrorTracker sqe)
	{
		Dimension d = dim.next();
		Value v = val.next();
		if(v instanceof Range)
		{
			for(double a = ((Range)v).getLowerBound(); a <= ((Range)v).getUpperBound(); a+=((Range)v).INCREMENT)
			{
				v.setActivation(a);
				innerLoop(target, trainer, dim, val, d, v, sqe);
			}
		}
		else
		{
			innerLoop(target, trainer, dim, val, d, v, sqe);
		}
	}
	
	/**
     * The inner loop of the dataRecursor method.
     * @param target The target implicit module to be trained.
     * @param trainer The implicit module to be used as the trainer.
     * @param dim A list iterator for the dimensions.
     * @param val A list iterator for the values within the dimension currently being addressed.
     * @param d The dimension currently being addressed.
     * @param v The value currently being addressed.
     * @param sqe The sum of squared error tracker being used to track the sum of squared error.
     */
	private void innerLoop (AbstractTrainableImplicitModule target, AbstractImplicitModule trainer, ListIterator<Dimension> dim, ListIterator<? extends Value> val, Dimension d, Value v, SumSquaredErrorTracker sqe)
	{
		target.setInput(d.getID(), v);
		trainer.setInput(d.getID(), v);
		if(!val.hasNext() && !dim.hasNext())
		{
			target.forwardPass();
 			trainer.forwardPass();
 			if(target instanceof InterfaceRuntimeTrainable)
 			{
 				AbstractOutputChunk o = (AbstractOutputChunk)SELECTOR.select(target.getOutput());
 				target.setChosenOutput(o);
 				((InterfaceRuntimeTrainable) target).setFeedback(trainer.getOutput(o.getID()).getActivation());
 			}
 			else
 				target.setDesiredOutput(trainer.getOutput());
 			target.backwardPass();
 			sqe.sumsqerr += target.getSumSqErrors();
 			++sqe.sumsqerrcounter;
		}
		else if(val.hasNext())
		{
			dim.previous();
			dataRecursor(target, trainer, dim, val, sqe);
			val.previous();
		}
		else if(dim.hasNext())
		{
			d = dim.next();
			LinkedList <Value> l = new LinkedList<Value> (d.values());
			dim.previous();
			dataRecursor(target, trainer, dim, l.listIterator(), sqe);
			dim.previous();
		}
	}
     

	/**
	 * This class implements a sum of squared error tracker.
	 * <p>
	 * <b>Usage:</b>
	 * <p>
	 * This class is used to track the sum of squared error during training and is especially 
	 * useful when training with a recursive function.
	 * @version 6.0.4
	 * @author Nick Wilson
	 */
	private class SumSquaredErrorTracker
    {
		/**The sum of squared error*/
     	double sumsqerr = SUM_SQ_ERRORS_THRESHOLD;
     	/**The counter keeping track of the number of times the sum of squared error has been updated.
     	 * It is used to calculate the mean sum of squared errors.*/
     	int sumsqerrcounter;
     	
     	/**
     	 * Gets the mean sum of squared errors.
     	 * @return The mean sum of squared errors.
     	 */
     	public double getMeanSumOfSquaredErrors ()
     	{
     		return sumsqerr/sumsqerrcounter;
     	}
    }
}
