package clarion.extensions;

import java.util.Collection;

import clarion.system.*;

/**
 * This class implements a drive equation within CLARION. It extends the AbstractEquation class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class implements one option for calculating drive strengths for a drive using:
 * <br>
 * <b>DriveStrength = (DRIVE_GAIN * SYSTEM_GAIN * UNIVERAL_GAIN) * Deficit * Stimulus + Baseline</b>
 * <p>
 * This class can be used as the implicit module within a drive for calculating a drive strength.
 * <p>
 * If you are using the drive equation, the input MUST contain a dimension with an ID 
 * equal to the ID of the drive associated with this equation (i.e. the drive in which this equation is
 * to be used) that contains values with the IDs: 
 * <ul>
 * <li><b>DRIVE_GAIN</b></li>
 * <li><b>SYSTEM_GAIN</b></li>
 * <li><b>UNIVERAL_GAIN</b></li>
 * <li><b>STIMULUS</b></li>
 * <li><b>DEFICIT</b></li>
 * <li><b>BASELINE</b></li>
 * </ul>
 * The above IDs must be of the enumerated type <b>TypicalInputs</b> located in the Drive class. Any other dimensions 
 * and values in the input will be ignored. The outputs for the equation should be of the type "DriveStrength" and 
 * each output MUST have an ID that is equal to a drive that will be using this equation.
 * <p>
 * Note that if a drive to which this equation is associated belongs to both the BIS and BAS, the SYSTEM_GAIN 
 * should be set to the average of the gains of the two drives.
 * <p>
 * While it is completely within the capabilities of the CLARION Library to use equations, they are NOT 
 * sub-symbolic or distributed in nature. Therefore, it is encouraged that you only use equations in the bottom 
 * level for testing and debugging purposes. Instead, you are advised to use a more sub-symbolic structure 
 * (such as a neural network) for the implicit modules within CLARION.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class DriveEquation extends AbstractEquation {

		
	/**The ID of the drive to which this equation is attached.*/
	private Object ID;
	
	/**
	 * Initializes a drive equation.
	 * @param InputSpace The input space for the equation.
	 * @param Outputs The outputs for the equation.
	 * @throws InvalidFormatException If the input space is not in the correct format for the drive equation.
	 */
	public DriveEquation(Object id, Collection<Dimension> InputSpace, AbstractOutputChunkCollection<? extends AbstractOutputChunk> Outputs) 
	throws InvalidFormatException{
		super(InputSpace, Outputs);
		ID = id;
		if(!InputAsCollection.containsKey(ID))
			throw new InvalidFormatException ("The input space MUST contain a dimension with the ID specified.");
		Value s = InputAsCollection.get(ID).get(Drive.TypicalInputs.STIMULUS);
		Value sgain = InputAsCollection.get(ID).get(Drive.TypicalInputs.SYSTEM_GAIN);
		Value dgain = InputAsCollection.get(ID).get(Drive.TypicalInputs.DRIVE_GAIN);
		Value ugain = InputAsCollection.get(ID).get(Drive.TypicalInputs.UNIVERSAL_GAIN);
		Value d = InputAsCollection.get(ID).get(Drive.TypicalInputs.DEFICIT);
		Value b = InputAsCollection.get(ID).get(Drive.TypicalInputs.BASELINE);
		if(s == null || sgain == null || dgain == null || ugain == null || d == null || b == null)
			throw new InvalidFormatException ("The input space contains a dimension with the specified ID. "+
					"HOWEVER, that dimension does not contain the correct values for a drive equation. The drive " +
					"equation requires inputs to have values with the following IDs (of enumerated type " +
					"TypicalInputs found in the Drive class): STIMULUS, SYSTEM_GAIN, DRIVE_GAIN, UNIVERSAL_GAIN, DEFICIT, " +
					"BASELINE.");
	}

	/**
     * Calculates the output activations based on the current input. This method should not 
     * be called until the setInput method has been called setting the input activations to 
     * the current state.
     */
	public void forwardPass(){
		Value s = InputAsCollection.get(ID).get(Drive.TypicalInputs.STIMULUS);
		Value sgain = InputAsCollection.get(ID).get(Drive.TypicalInputs.SYSTEM_GAIN);
		Value dgain = InputAsCollection.get(ID).get(Drive.TypicalInputs.DRIVE_GAIN);
		Value ugain = InputAsCollection.get(ID).get(Drive.TypicalInputs.UNIVERSAL_GAIN);
		Value d = InputAsCollection.get(ID).get(Drive.TypicalInputs.DEFICIT);
		Value b = InputAsCollection.get(ID).get(Drive.TypicalInputs.BASELINE);
		AbstractOutputChunk ds = Output.get(ID);
		ds.setActivation((dgain.getActivation() * sgain.getActivation() * ugain.getActivation())
				* d.getActivation() * s.getActivation() + b.getActivation());
	}
}
