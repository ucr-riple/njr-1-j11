package clarion.extensions;

import clarion.system.*;

/**
 * This class implements an ACS level probability setting module within CLARION. It
 * extends AbstractMetaCognitiveModule and implements InterfaceMCSRunsBeforeACS.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This module can be optionally added to the MCS if the user wishes to have the MCS provide a probability
 * measure to the ACS to influence level selection. This module uses an implicit module to determine the
 * probability measure. The user can decide, during initialization, the implicit module they wish to use.
 * <p>
 * NOTE:
 * <ul>
 * <li>After construction, but before this module can be used, the user MUST specify the instance of the ACS
 * that this module is to act upon. Failure to attach an ACS to this module (using the attachACS method) during
 * initialization will result in an exception being thrown during runtime.</li>
 * <li>The level probability setting implicit module MUST have an output chunk that has an ID that 
 * specifies which level probabilities are to be set. The ID must be of the enumerated type "RequiredOutputs"
 * ("TL" for top level probability or "BL" for bottom level probability).</li>
 * </ul>
 * If this module is initialized without specifying an implicit module to use, the ACSLevelProbabilitySettingEquation 
 * will be used.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class ACSLevelProbabilitySettingModule extends AbstractMetaCognitiveModule implements InterfaceMCSRunsBeforeACS{
	
	/**The enumerator to use for the IDs for the outputs that are required for this module.*/
	public enum RequiredOutputs {TL, BL};
	
	/**The ACS.*/
	private ACS acs;
	
	/**
	 * Initializes the ACS level probability setting module using the ACS level probability setting equation.
	 * @param AffiliatedDrives The drives affiliated with ACS level selection probability setting.
	 */
	public ACSLevelProbabilitySettingModule (DriveStrengthCollection AffiliatedDrives)
	{
		super(new ACSLevelProbabilitySettingEquation (AffiliatedDrives.toDimensionValueCollection().values(), generateOutputs()));
	}
	
	/**
	 * Initializes the ACS level probability setting module given the implicit module specified.
	 * <p>
	 * The input nodes of the specified implicit module must be specified as Value 
	 * objects that have the same ID as the drives in the MS that you wish to use for 
	 * setting ACS level probabilities.
	 * <p>
	 * Note that the specified level probability setting implicit module MUST have an output chunk that has an ID that 
	 * specifies which level probabilities are to be set. The ID must be of the enumerated type "RequiredOutputs"
	 * ("TL" for top level probability or "BL" for bottom level probability). If no such output chunk(s) exist(s),
	 * this constructor throws an exception. If the specified implicit module contains more than the 
	 * aforementioned output chunks in its output, those other chunks will be ignored.
	 * @param im The level probability setting implicit module to use.
	 * @throws InvalidFormatException If the specified implicit module does not 
	 * contain an output chunk with a dimension that has an ID of the enumerated type "RequiredOutputs"
	 * ("TL" for top level probability or "BL" for bottom level probability).
	 */
	public ACSLevelProbabilitySettingModule (AbstractImplicitModule im) throws InvalidFormatException
	{
		super(im);
		boolean check = false;
		for(AbstractOutputChunk o : im.getOutput())
		{
			if(o.getID().equals(RequiredOutputs.TL) || o.getID().equals(RequiredOutputs.BL))
			{
				check = true;
				break;
			}
		}
		if(!check)
			throw new InvalidFormatException ("The specified implicit module does not contain an " +
			"output chunk with a dimension that has an ID of type \"RequiredOutputs\".");
	}

	/**
	 * Sets the probability of the top and bottom level of the ACS given the drive strengths
	 * specified.
	 * @param ModuleInput The drive strengths to use for calculating the probability.
	 * @param TimeStamp The current time stamp.
	 * @throws MissingACSException If no instance of the ACS has been attached to this module.
	 */
	public void performMetaCognition(DimensionValueCollection ModuleInput, long TimeStamp)  throws MissingACSException
	{
		if(acs == null)
			throw new MissingACSException ("The ACS Level Probability Module does not have an ACS specified. " +
					"Please specify an ACS for this module during initialization using the attachACS method.");
		ImplicitModule.setInput(ModuleInput);
		ImplicitModule.forwardPass();
		
		if(acs.LEVEL_SELECTION_METHOD == ACS.LevelSelectionMethods.STOCHASTIC)
		{
			acs.MCS_BL_PROBABILITY = ImplicitModule.getOutput(RequiredOutputs.BL).getActivation();
			acs.MCS_TL_PROBABILITY = ImplicitModule.getOutput(RequiredOutputs.TL).getActivation();
				
		}
		else
		{
			acs.MCS_BL_WEIGHT = ImplicitModule.getOutput(RequiredOutputs.BL).getActivation();
			acs.MCS_TL_WEIGHT = ImplicitModule.getOutput(RequiredOutputs.TL).getActivation();
		}
	}
	
	/**
	 * Attaches the specified ACS to this module.
	 * <p>
	 * NOTE: The ACS specified MUST be the SAME ACS that is contained within the CLARION agent that 
	 * intends to use this module.
	 * @param Acs The ACS.
	 */
	public void attachACS (ACS Acs)
	{
		acs = Acs;
	}
	
	/**
	 * Generates a set of outputs that can be used by the implicit module of this meta-cognitive module.
	 * @return A DimensionlessOutputChunkCollection containing a set of outputs that can be used
	 * by the implicit module of this meta-cognitive module.
	 */
	public static DimensionlessOutputChunkCollection generateOutputs ()
	{
		DimensionlessOutputChunkCollection cc = new DimensionlessOutputChunkCollection ();
		cc.put(RequiredOutputs.TL, new DimensionlessOutputChunk(RequiredOutputs.TL));
		cc.put(RequiredOutputs.BL, new DimensionlessOutputChunk(RequiredOutputs.BL));
		return cc;
	}
}
