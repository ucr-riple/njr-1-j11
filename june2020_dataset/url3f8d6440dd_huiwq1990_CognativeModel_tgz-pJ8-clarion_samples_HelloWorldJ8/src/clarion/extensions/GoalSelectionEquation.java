package clarion.extensions;

import java.util.Collection;

import clarion.system.*;

/**
 * This class implements a goal selection equation within CLARION. It extends the AbstractEquation class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class implements one option for setting a goal using the goal equation:
 * <br>
 * <b>GoalActivation = Sum(RelevantDriveStrength * Relevance)</b>
 * <p>
 * This class can be used within the goal selection module of the MCS for setting the goal in the goal structure.
 * <p>
 * The inputs to the equation are a collection of "DriveStrengths" for the drives that are relevant for goal selection.
 * These drive strengths are obtained from the toDimensionValueCollection method in the DriveStrengthCollection class.
 * The outputs must be of the type "Goal".
 * <p>
 * While it is completely within the capabilities of the CLARION Library to use equations, they are NOT 
 * sub-symbolic or distributed in nature. Therefore, it is encouraged that you only use equations in the bottom 
 * level for testing and debugging purposes. Instead, you are advised to use a more sub-symbolic structure 
 * (such as a neural network) for the implicit modules within CLARION.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class GoalSelectionEquation extends AbstractEquation {
	
	/**
	 * Initializes the goal selection equation.
	 * @param InputSpace The input space for the goal selection equation, which is a collection of drive strengths.
	 * @param Outputs The outputs for the equation (i.e. goals).
	 * @throws InvalidFormatException If any of the specified outputs are of a type other than "Goal".
	 */
	public GoalSelectionEquation(Collection<Dimension> InputSpace, 
			AbstractOutputChunkCollection<? extends AbstractOutputChunk> Outputs) throws InvalidFormatException{
		super(InputSpace, Outputs);
		for(AbstractOutputChunk g : Outputs.values())
		{
			if(!(g instanceof Goal))
			{
				throw new InvalidFormatException ("One or more of the specified outputs are of a type other than Goal.");
			}
		}
	}

	public void forwardPass() {
		for(AbstractOutputChunk g : Output.values())
		{
			g.resetActivation();
			for(Value ds : InputAsCollection.getValueCollection())
			{
				if(((Goal)g).getRelevance(ds.getID()) != null)
				{
					g.setActivation(g.getActivation() + 
							((Goal)g).getRelevance(ds.getID()).getActivation()
							* ds.getActivation());
				}
			}
		}
	}
}
