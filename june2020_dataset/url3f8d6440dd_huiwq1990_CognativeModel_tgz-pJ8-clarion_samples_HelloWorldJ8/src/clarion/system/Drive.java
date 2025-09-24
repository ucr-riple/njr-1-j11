package clarion.system;

/**
 * This class implements a drive within CLARION.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class represents a drive, which exist at the bottom-level of the MS within the
 * behavioral approach and/or behavioral inhibition systems (or a special "neither" behavioral system). The drive class 
 * uses an implicit module in order to obtain a drive strength. The implicit module must be user defined, however,
 * several methods have been developed within the CLARION Library to make the generation of drives simpler (see
 * the DriveEquation and Personality classes in the extensions package).
 * <p>
 * The output of the implicit module MUST have an output chunk that has an ID equal to the ID of the drive for 
 * which it is being used and that output chunk MUST be of type DriveStrength. If no such output chunk exists, the drive 
 * will fail to initialize.
 * <p>
 * CLARION does not require any specific inputs for a drive. However, a set of typical inputs has been specified
 * within the "TypicalInputs" enumerator. If you wish to use the typical inputs for the implicit module of a drive, then
 * the input values of the implicit module MUST have IDs that are of the enumerated type "TypicalInputs". Additionally, 
 * those values MUST be contained within a dimension that has the same ID as the drive to which the implicit module is 
 * associated.
 * <p>
 * Note:
 * <ul>
 * <li>If a neural network is used as the implicit module for the drive, it should be pre-trained before it 
 * is used as drives are not (generally) runtime trainable (although the you can certainly use a runtime 
 * trainable implicit module if you provide your own method for training it during runtime).</li>
 * <li>If the implicit module contains more than one output chunk in the output layer, all other output chunks besides the 
 * drive strength chunk associated with this drive will be ignored. However, a user COULD setup a single implicit module 
 * that has drive strengths on the output layer for multiple (or even all) drives in the MS. That single implicit module 
 * COULD then be specified as the implicit module for each of the drives that have an associated drive strength on the 
 * output layer. This would cause each of those drive to use the single implicit module. Setting up drives in this 
 * fashion MIGHT reduce the amount of time needed to perform pre-training.</li>
 * </ul>
 * This class contains both global (static) and local constants. The default is to use the local 
 * constants. If you want to change any of the global constants, you need to do so before any
 * instances of this class are initialized.
 * @version 6.0.6
 * @author Nick Wilson
 */
public class Drive {
	/**The enumerator to use as the IDs for inputs that are typically used for drives.*/
	public enum TypicalInputs {DRIVE_GAIN, SYSTEM_GAIN, UNIVERSAL_GAIN, STIMULUS, DEFICIT, BASELINE};
	
	/**Identifies the drive. A name (in the form of a string) is simply one option for identifying a
	 * drive.*/
	private Object ID;
	
	/**The hash for this object. When this item is cloned, so is this hash.*/
	private int hash;
	
	/**The implicit module that is used to get drive strengths.*/
	private AbstractImplicitModule DriveMod;

	/**The drive strength associated with this drive.*/
	protected DriveStrength DS;
	
	/**The universal gain for all drives*/
	public static double GLOBAL_UNIVERSAL_GAIN = 1;
	/**The rate of change for the deficit.*/
	public static double GLOBAL_DEFICIT_CHANGE_RATE = 1;
	/**The universal gain for all drives*/
	public double UNIVERSAL_GAIN = GLOBAL_UNIVERSAL_GAIN;
	/**The rate of change for the deficit.*/
	private double DEFICIT_CHANGE_RATE = GLOBAL_DEFICIT_CHANGE_RATE;
	
	/**The drive deficit represented as a value object.*/
	private Value Deficit;
	/**The initial (pre-decay) drive deficit. This is used to calculate the baseline when a deficit
	 * portion is being used.*/
	private Value InitialDeficit;
	/**The gain for the particular drive*/
	public static double GLOBAL_GAIN = 1;
	/**The baseline for this particular drive*/
	public static double GLOBAL_BASELINE = .01;
	/**The gain for the particular drive*/
	public double GAIN = GLOBAL_GAIN;
	/**The baseline for this particular drive*/
	public double BASELINE = GLOBAL_BASELINE;
	
	/**
	 * Initializes the drive to use the specified implicit module for reporting 
	 * drive strengths. Also initializes the deficit to the specified deficit and sets the 
	 * ID to the specified ID. Once the drive has been initialized, the initial deficit 
	 * and implicit module cannot be changed.
	 * <p>
	 * Note that the specified implicit module MUST have an output chunk on the 
	 * output layer that has an ID that is equal to the ID of this drive instance and 
	 * that output chunk MUST be of type DriveStrength. If no such output chunk exists, the 
	 * drive will fail to initialize.
	 * @param id The ID to set for this drive.
	 * @param deficit The initial drive deficit.
	 * @param im The implicit module to use for this drive.
	 * @throws InvalidFormatException If the specified implicit module does not contain an output 
	 * chunk with an ID equal to the ID of this drive or the output chunk has the correct ID but 
	 * is not of type DriveStrength.
	 */
	public Drive (Object id, double deficit, AbstractImplicitModule im) throws InvalidFormatException
	{
		InitialDeficit = new Value (this, deficit);
		Deficit = InitialDeficit.clone();
		ID = id;
		DS = null;
		for(AbstractOutputChunk o : im.Output.values())
		{
			if(o.getID().equals(ID) && (o instanceof DriveStrength))
			{
				DS = (DriveStrength)o;
				break;
			}
		}
		if(DS == null)
			throw new InvalidFormatException ("The specified implicit module does " +
					"not contain an output chunk with an ID equal to the ID of this drive or " +
					"the output chunk has the correct ID but is not of type DriveStrength.");
		DriveMod = im;
		hash = System.identityHashCode(this);
	}
    
	/**
	 * Calculates the drive strength given the specified drive input. In this method, the drive 
	 * specific parameters are added to the drive input before it is passed to the drive's implicit module. 
	 * <p>
	 * If the implicit module uses either <b>STIMULUS</b> or <b>SYSTEM_GAIN</b> (of enumerated type TypicalInputs) as
	 * input, then the specified DriveInput MUST have a dimension with an ID equal to the ID of this drive and
	 * that dimension must contain a value with the ID <b>STIMULUS</b> and/or <b>SYSTEM_GAIN</b> (of enumerated type 
	 * TypicalInputs).
	 * <p>
	 * Note that if your drive belongs to both the BIS and BAS, the SYSTEM_GAIN should be set to the average of the gains 
	 * of the two systems.
	 * @param DriveInput The input for the drive.
	 * @return The strength of the drive.
	 * @throws InvalidFormatException If the drive input does not contain a dimension with an ID equal to the ID of this 
	 * drive.
	 */
	public DriveStrength calculateDriveStrength (DimensionValueCollection DriveInput) throws InvalidFormatException
	{
		Dimension d = null;
		if(DriveInput != null)
			d = DriveInput.get(ID);
		if(d == null)
			throw new InvalidFormatException ("The drive input does not contain a dimension " +
					"with the same ID as this drive.");
		
		d.put(TypicalInputs.UNIVERSAL_GAIN, 
				new Value (TypicalInputs.UNIVERSAL_GAIN,UNIVERSAL_GAIN));
		d.put(TypicalInputs.DRIVE_GAIN, 
				new Value (TypicalInputs.DRIVE_GAIN,GAIN));
		d.put(TypicalInputs.DEFICIT, 
				new Value (TypicalInputs.DEFICIT,Deficit.getActivation()));
		d.put(TypicalInputs.BASELINE, 
				new Value (TypicalInputs.BASELINE,BASELINE));
		
		DriveMod.setInput(DriveInput);
		DriveMod.forwardPass();
		return DS;
	}
	
	/**
	 * Gets the ID of this drive.
	 * @return The ID of this drive.
	 */
	public Object getID ()
	{
		return ID;
	}
	
	/**
	 * Gets the implicit module for this drive. This method is primarily meant for
	 * internal purposes. It should NOT be used outside of the CLARION Library for purposes 
	 * other than reporting the internal state.
	 * @return The implicit module for this drive.
	 */
	public AbstractImplicitModule getImplicitModule ()
	{
		return DriveMod;
	}
	
	/**
	 * Sets the implicit module for this drive. This method should only be used
	 * during initialization.
	 * @param im The implicit module to set for this drive.
	 * @throws InvalidFormatException If the specified implicit module does not 
	 * contain an output chunk with an ID equal to the ID of this drive or the output chunk 
	 * has the correct ID but is not of type DriveStrength.
	 */
	public void setImplicitModule (AbstractImplicitModule im) throws InvalidFormatException
	{
		DS = null;
		for(AbstractOutputChunk o : im.Output.values())
		{
			if(o.getID().equals(ID) && (o instanceof DriveStrength))
			{
				DS = (DriveStrength)o;
				break;
			}
		}
		if(DS == null)
			throw new InvalidFormatException ("The specified implicit module does " +
					"not contain an output chunk with an ID equal to the ID of this drive or " +
					"the output chunk has the correct ID but is not of type DriveStrength.");
		DriveMod = im;
	}
	
	/**
	 * Gets the drive strength object associated with this drive. This method is mainly used during
	 * initialization and does NOT calculate the drive strength for this drive. If you want to 
	 * calculate the drive strength, call the calculateDriveStrength method.
	 * @return The drive strength object
	 */
	public DriveStrength getDriveStrength ()
	{
		return DS;
	}
    
    /**
     * Gets the deficit level.
     * @return The deficit.
     */
    public double getDeficit ()
    {
    	return Deficit.getActivation();
    }
    
    /**
     * Gets the level of the initial deficit. Once the drive has been initialized, the initial deficit
     * cannot be changed.
     * @return The initial deficit.
     */
    public double getInitialDeficit ()
    {
    	return InitialDeficit.getActivation();
    }
    
    /**
     * Sets the deficit. The initial deficit is left unchanged.
     * @param deficit The deficit value to set.
     */
    public void setDeficit (double deficit)
    {
    	if(Deficit == null)
    	{
    		Deficit = new Value (this, deficit);
    	}
    	else
    	{
	    	Deficit.setActivation(deficit);
    	}
    }
    
    /**
     * Sets the deficit change rate.
     * @param changeRate The rate of change to set.
     */
    public void setDeficitChangeRate (double changeRate)
    {
    	DEFICIT_CHANGE_RATE = changeRate;
    }
    
    /**
     * Updates the deficit using the rate of change.
     */
    protected void updateDeficit ()
    {
    	Deficit.setActivation(Deficit.getActivation() * DEFICIT_CHANGE_RATE);
    }
    
	/**
	 * Generates a drive-strength collection that can be used to initialize the output layer of an implicit module. 
	 * This implicit module (whose output is being generated) can then be used by a drive with the specified ID.
	 * @param id The ID of the drive whose implicit module output you are generating.
	 * @return A DriveStrengthCollection that can be used as the output for an implicit module.
	 */
	public static DriveStrengthCollection generateOutput (Object id)
	{
		DriveStrengthCollection cc = new DriveStrengthCollection ();
		cc.put(id, new DriveStrength(id));
		return cc;
	}
	
	/**
	 * Generates a dimension-value collection of "typical inputs" that can be used to initialize the input layer 
	 * of an implicit module. This implicit module (whose input is being generated) can then be used by a drive with 
	 * the specified ID.
	 * @param id The ID of the drive whose implicit module input you are generating.
	 * @return A dimension-value collection that can be used as the input for an implicit module.
	 */
	public static DimensionValueCollection generateTypicalInput (Object id)
	{
		DimensionValueCollection Input = new DimensionValueCollection();
		Dimension d = new Dimension(id);
		Input.put(d.getID(), d);
		d.put(TypicalInputs.DRIVE_GAIN, 
				new Value (TypicalInputs.DRIVE_GAIN));
		d.put(TypicalInputs.SYSTEM_GAIN, 
				new Value (TypicalInputs.SYSTEM_GAIN));
		d.put(TypicalInputs.UNIVERSAL_GAIN, 
				new Value (TypicalInputs.UNIVERSAL_GAIN));
		d.put(TypicalInputs.STIMULUS, 
				new Value (TypicalInputs.STIMULUS));
		d.put(TypicalInputs.DEFICIT, 
				new Value (TypicalInputs.DEFICIT));
		d.put(TypicalInputs.BASELINE, 
				new Value (TypicalInputs.BASELINE));
		return Input;
	}
    
    /**
	 * Checks to see if the specified object is a drive and has the same ID as the specified drive.
	 * @param drive The object to compare to this drive.
	 * @return True if the two drives are have the same ID, otherwise false.
	 */
	public boolean equals (Object drive)
	{
		if (this == drive)
			return true;
		if (!(drive instanceof Drive))
			return false;
		if (((Drive)drive).getID().equals(ID))
		{
				return true;
		}
		else
			return false;
	}
	
	public int hashCode()
	{
		return hash;
	}
	
	/**
	 * Clones the drive. This method should NOT be used outside of the CLARION library as
	 * it does NOT clone the implicit module and each instance of a drive should have its
	 * own implicit module (at least between different instances of a CLARION agent).
	 * @return A copy of this drive.
	 */
	public Drive clone ()
	{
		Drive d = new Drive (ID,InitialDeficit.getActivation(),DriveMod);
		d.DS = DS;
		d.GAIN = GAIN;
		d.BASELINE = BASELINE;
		d.hash = hash;
		return d;
	}
	
	public String toString()
	{
		if(!(ID instanceof Drive))
			return "Drive ID - " + ID.toString() + ": Deficit = " + Deficit.getActivation() + "\n";
		return "Drive ID - ?: Deficit = " + Deficit.getActivation();
	}
}
