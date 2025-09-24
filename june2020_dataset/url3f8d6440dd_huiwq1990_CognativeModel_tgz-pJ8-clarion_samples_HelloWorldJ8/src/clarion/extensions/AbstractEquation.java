package clarion.extensions;

import java.util.Collection;

import clarion.system.*;

/**
 * This class implements an equation within CLARION. It extends the AbstractImplicitModule class
 * and implements the InterfaceHandlesFeedback interface. This class is abstract and therefore 
 * cannot be instantiated on its own.
 * <p>
 * <b>Usage:</b>
 * <p>
 * Any class that extends an abstract equation can be used in the bottom level of the CLARION subsystems. This class
 * mainly provides a framework for building equations to use as implicit modules in the bottom level.
 * <p>
 * While it is completely within the capabilities of the CLARION Library to use equations, they are NOT 
 * sub-symbolic or distributed in nature. Therefore, it is encouraged that you only use equations in the bottom 
 * level for testing and debugging purposes. Instead, you are advised to use a more sub-symbolic structure 
 * (such as a neural network) for the implicit modules within CLARION.
 * <p>
 * <b>Known Subclasses:</b><br>
 * <ul>
 * <li>ACSLevelProbabilitySettingEquation</li>
 * <li>DriveEquation</li>
 * <li>GoalSelectionEquation</li>
 * <li>JudgmentCorrectionEquation</li>
 * </ul>
 * @version 6.0.6
 * @author Nick Wilson
 */
public abstract class AbstractEquation extends AbstractImplicitModule implements InterfaceHandlesFeedback{
    
    /**The immediate feedback given to the equation (if given).*/
    protected double Feedback;
    
	/**
	 * Initializes an equation.
	 * <p>
	 * If this is being used as an implicit module in the ACS and you are using goals
	 * or specialized working memory chunks, remember that the input space must also contain 
	 * all dimension-value pairs within those chunks that differ from the sensory information
	 * space.
	 * @param InputSpace The input space for the equation.
	 * @param Outputs The outputs for the equation.
	 */
	public AbstractEquation(Collection<Dimension> InputSpace, AbstractOutputChunkCollection<? extends AbstractOutputChunk> Outputs)
	{
		super(InputSpace, Outputs);
	}
	
    /**
     * Gets the immediate feedback that was last provided to the equation. This method is only used
     * if feedback is being provided to the equation (usually for the purposes of extraction).
     * @return The feedback.
     */
    public double getFeedback()
    {
    	return Feedback;
    }
	
    /**
	 * Sets the immediate feedback for the equation. This method is only used if feedback is being 
	 * provided to the equation (usually for the purposes of extraction).
	 * @param R The value of the feedback.
	 */
	public void setFeedback (double R)
	{
		Feedback = R;
	}
}
