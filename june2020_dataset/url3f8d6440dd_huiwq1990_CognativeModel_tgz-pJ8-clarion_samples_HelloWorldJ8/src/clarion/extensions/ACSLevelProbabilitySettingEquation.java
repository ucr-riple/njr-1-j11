package clarion.extensions;

import java.util.Collection;

import clarion.system.*;

/**
 * This class implements an ACS level probability setting equation within CLARION. It extends the AbstractEquation class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class implements one option for setting the ACS level probability using an inverse parabolic equation
 * sometimes referred to as an "inverted U curve." This class can be used within the ACS level probability setting module of the 
 * MCS for setting the ACS level probabilities.
 * <p>
 * The inputs to the equation are a collection of "DriveStrengths" combined into a dimension-value collection obtained from the 
 * toDimensionValueCollection method in the DriveStrengthCollection class. The outputs should be of type "DimensionlessOutputChunk".
 * <p>
 * While it is completely within the capabilities of the CLARION Library to use equations, they are NOT 
 * sub-symbolic or distributed in nature. Therefore, it is encouraged that you only use equations in the bottom 
 * level for testing and debugging purposes. Instead, you are advised to use a more sub-symbolic structure 
 * (such as a neural network) for the implicit modules within CLARION.
 * <p>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class ACSLevelProbabilitySettingEquation extends AbstractEquation {
	
	/**The constant A used in the level probability setting equation.*/
	public static double GLOBAL_A = -.4;
	/**The constant B used in the level probability setting equation.*/
	public static double GLOBAL_B = .2;
	/**The constant C used in the level probability setting equation.*/
	public static double GLOBAL_C = .6;
	/**The constant A used in the level probability setting equation.*/
	public double A = GLOBAL_A;
	/**The constant B used in the level probability setting equation.*/
	public double B = GLOBAL_B;
	/**The constant C used in the level probability setting equation.*/
	public double C = GLOBAL_C;
	
	/**
	 * Initializes the ACS level probability setting equation. The inputs to the equation should be
	 * of type "DriveStrength" and the outputs should be of type "DimensionlessOutputChunk".
	 * @param InputSpace The input space for the equation.
	 * @param Outputs The outputs for the equation.
	 */
	public ACSLevelProbabilitySettingEquation(Collection<Dimension> InputSpace, 
			AbstractOutputChunkCollection<? extends AbstractOutputChunk> Outputs)
	{
		super(InputSpace, Outputs);
	}

	public void forwardPass() {
		double max = 0;
		for(Value i : InputAsCollection.getValueCollection())
		{
			if(i.getActivation() > max)
				max = i.getActivation();
		}
		double output = A * Math.pow(max, 2) + B * max + C;
		if(Output.containsKey("TL"))
			Output.get("TL").setActivation(output);
		if(Output.containsKey("BL"))
			Output.get("BL").setActivation(1 - output);
		
	}
}
