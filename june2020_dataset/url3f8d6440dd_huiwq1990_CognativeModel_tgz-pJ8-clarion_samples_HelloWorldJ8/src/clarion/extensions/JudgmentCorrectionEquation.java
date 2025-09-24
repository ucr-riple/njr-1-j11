package clarion.extensions;

import java.util.Collection;

import clarion.system.*;

/**
 * This class implements a judgment correction equation within CLARION. It extends the AbstractEquation class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class implements one option for performing a judgment correction using the equations:
 * <table cellpadding=0 cellspacing=0 border=0>
 * <tr><td>&nbsp;</td><td rowspan=5 valign="middle"><font size=20px>{</font></td><td colspan=2>&nbsp;</td>
 * </tr><tr><td>&nbsp;</td>
 * <td NOWRAP><b>Target_Rating' - (BETA_1 * Primer_Rating)</b></td>
 * <td NOWRAP><b>&nbsp;&nbsp;If the Target and Primer are Assimilative</b></td>
 * </tr><tr><td NOWRAP><b>Target_Rating" = </b></td><td colspan=2>&nbsp;</td>
 * </tr><tr><td>&nbsp;</td>
 * <td NOWRAP><b>Target_Rating' + (BETA_2 * Primer_Rating)</b></td>
 * <td NOWRAP><b>&nbsp;&nbsp;If the Target and Primer are Contrastive</b></td>
 * </tr><tr>&nbsp;<td></td><td colspan=2>&nbsp;</td></tr></table>
 * <p>
 * Assimilative vs. Contrastive determination*:
 * <table cellpadding=0 cellspacing=0 border=0>
 * <tr><td colspan=2>&nbsp;</td></tr>
 * <tr><td><b><i>if |Target_Rating' - Primer_Rating| > PHI_1,</i></b></td>
 * <td><b><i>&nbsp;&nbsp;Contrastive</i></b></td></tr>
 * <tr><td><b><i>else, if |Target_Rating' - Primer_Rating| < PHI_2,</i></b></td>
 * <td><b><i>&nbsp;&nbsp;Assimilative</i></b></td></tr>
 * <tr><td><b><i>else,</i></b></td>
 * <td><b><i>&nbsp;&nbsp;NO Correction</i></b></td></tr></table><br>
 * <i>*This method for determining assimilative vs. contrastive can be bypassed by setting either 
 * the FORCE_ASSIMILATIVE or FORCE_CONTRASTIVE parameter to true.</i>
 * <p>
 * This class can be used within the judgment correction module of the MCS for making judgment corrections.
 * <p>
 * The inputs of this equation must have two dimensions with the IDs: "PRIMER" and "TARGET" (of the 
 * enumerated type RequiredInputDimensions located in the JudgmentCorrectionModule). The values contained 
 * within these dimensions MUST have the same IDs as the values within the "RATING" (of the enumerated type 
 * RequiredActionDimensions located in the JudgmentCorrectionModule) dimension of the actions that are 
 * eligible for judgment correction. These IDs MUST be of a type that can be parsed into the primitive type 
 * double (e.g., Integer, Float, a numeric String, etc.).
 * <p>
 * The outputs for the equation are the actions that are to be involved in judgment correction.
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
public class JudgmentCorrectionEquation extends AbstractEquation {
	
	/**The constant PHI 1 used in the judgment correction equation.*/
	public static double GLOBAL_PHI_1 = 1;
	/**The constant PHI 2 used in the judgment correction equation.*/
	public static double GLOBAL_PHI_2 = 1;
	/**The constant BETA 1 used in the judgment correction equation.*/
	public static double GLOBAL_BETA_1 = .5;
	/**The constant BETA 2 used in the judgment correction equation.*/
	public static double GLOBAL_BETA_2 = .5;
	/**The constant PHI 1 used in the judgment correction equation.*/
	public double PHI_1 = GLOBAL_PHI_1;
	/**The constant PHI 2 used in the judgment correction equation.*/
	public double PHI_2 = GLOBAL_PHI_2;
	/**The constant BETA 1 used in the judgment correction equation.*/
	public double BETA_1 = GLOBAL_BETA_1;
	/**The constant BETA 2 used in the judgment correction equation.*/
	public double BETA_2 = GLOBAL_BETA_2;
	
	/**Forces the equation to use the assimilative method for judgment correction, if set to true.
	 * Note that the equation can be forced to be either assimilative or contrastive, but no both.*/
	public static boolean GLOBAL_FORCE_ASSIMILATIVE = false;
	/**Forces the equation to use the contrastive method for judgment correction, if set to true.
	 * Note that the equation can be forced to be either assimilative or contrastive, but no both.*/
	public static boolean GLOBAL_FORCE_CONTRASTIVE = false;
	/**Forces the equation to use the assimilative method for judgment correction, if set to true.
	 * Note that the equation can be forced to be either assimilative or contrastive, but no both.*/
	public boolean FORCE_ASSIMILATIVE = GLOBAL_FORCE_ASSIMILATIVE;
	/**Forces the equation to use the contrastive method for judgment correction, if set to true.
	 * Note that the equation can be forced to be either assimilative or contrastive, but no both.*/
	public boolean FORCE_CONTRASTIVE = GLOBAL_FORCE_CONTRASTIVE;
	
	/**
	 * Initializes the judgment correction equation. The inputs of this equation must have two dimensions with the 
	 * IDs: "PRIMER" and "TARGET" (of the enumerated type RequiredInputDimensions located in the JudgmentCorrectionModule). 
	 * The values contained within these dimensions MUST have the same IDs as the values within the "RATING" (of the 
	 * enumerated type RequiredActionDimensions located in the JudgmentCorrectionModule) dimension of the actions that are 
	 * eligible for judgment correction. These IDs MUST be of a type that can be parsed into the primitive type 
	 * double (e.g., Integer, Float, a numeric String, etc.).
	 * <p>
	 * The outputs for the equation are the actions that are to be involved in judgment correction.
	 * @param InputSpace The input space for the equation.
	 * @param Outputs The outputs for the equation.
	 */
	public JudgmentCorrectionEquation(Collection<Dimension> InputSpace,
			AbstractOutputChunkCollection<? extends AbstractOutputChunk> Outputs) {
		
		super(InputSpace, Outputs);
	}

	/**
     * Performs a forward pass from the input to output of the implicit module. This is the method used by
     * the CLARION Library to obtain activations on the output layer given the input.
	 * @throws SettingsConflictException If both FORCE_ASSIMILATIVE and FORCE_CONTRASTIVE are both set to true.
	 */
	public void forwardPass() throws SettingsConflictException{
		
		if(FORCE_ASSIMILATIVE && FORCE_CONTRASTIVE)
			throw new SettingsConflictException ("The settings FORCE_ASSIMILATIVE and FORCE_CONTRASTIVE " +
					"cannot both be set to true.");
		
		Dimension p = InputAsCollection.get(JudgmentCorrectionModule.RequiredInputDimensions.PRIMER);
		Dimension t = InputAsCollection.get(JudgmentCorrectionModule.RequiredInputDimensions.TARGET);
		
		double Rp = 0;
		for(Value v : p.values())
		{
			if(v.isFullyActivated())
			{
				Rp = Double.parseDouble(v.getID().toString());
				break;
			}
		}
		
		double Rt = 0;
		for(Value v : t.values())
		{
			if(v.isFullyActivated())
			{
				Rt = Double.parseDouble(v.getID().toString());
				break;
			}
		}
		
		double correction = Math.abs(Rt - Rp);
		if(FORCE_CONTRASTIVE || (!FORCE_ASSIMILATIVE && correction > PHI_1))
		{
			correction = Rt + (BETA_2 * Rp);
		}
		else if (FORCE_ASSIMILATIVE || (!FORCE_CONTRASTIVE && correction < PHI_2))
		{
			correction = Rt - (BETA_1 * Rp);
		}
		else
		{
			correction = Rt;
		}
		
		double max = 0;
		for(AbstractOutputChunk o : getOutput())
		{
			for(Value v : o.get(JudgmentCorrectionModule.RequiredActionDimensions.RATING).values())
			{
				double dv = Double.parseDouble(v.getID().toString());
				
				if(v.isFullyActivated() && dv > max)
				{
					max = dv;
				}
			}
		}
		
		double min = max;
		for(AbstractOutputChunk o : getOutput())
		{
			for(Value v : o.get(JudgmentCorrectionModule.RequiredActionDimensions.RATING).values())
			{
				double dv = Double.parseDouble(v.getID().toString());
				
				if(v.isFullyActivated() && dv < min)
				{
					min = dv;
				}
			}
		}
		
		if(correction > max)
			correction = max;
		if(correction < min)
			correction = min;
		
		for(AbstractOutputChunk o : getOutput())
		{
			boolean check = false;
			for(Value v : o.get(JudgmentCorrectionModule.RequiredActionDimensions.RATING).values())
			{
				double dv = Double.parseDouble(v.getID().toString());
				if(v.isFullyActivated() && ((v.getID() instanceof Integer && dv == Math.round(correction)) 
						|| Math.abs(dv - correction) <= JudgmentCorrectionModule.GLOBAL_EPSILON))
				{
					check = true;
					break;
				}
			}
			
			if(check)
			{
				o.setActivation(AbstractOutputChunk.GLOBAL_FULL_ACTIVATION_LEVEL);
			}
			else
				o.setActivation(AbstractOutputChunk.GLOBAL_MINIMUM_ACTIVATION_THRESHOLD);
		}
	}

}
