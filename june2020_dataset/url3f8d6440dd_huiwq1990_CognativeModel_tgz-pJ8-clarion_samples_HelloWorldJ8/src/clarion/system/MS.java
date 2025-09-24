package clarion.system;

import java.util.Collection;

/**
 * This class implements the Motivational Subsystem (MS) within CLARION. 
 * It extends the AbstractSubsystem class.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This implementation of the motivational subsystem is in charge of one thing, and one thing
 * only:<br>
 * <b>The Drives!</b>
 * <p>
 * Drives are primarily used in CLARION to guide the meta-cognitive tasks performed by the various 
 * modules within the MCS. Each drive has an implicit module that is used to derive an activation
 * level for its "drive strength." The drive strengths are then passed to the MCS as well as possibly
 * other subsystems (as defined by the user) so they can be used to guide anything from parameter setting to 
 * goal selection and action decision-making.
 * <p>
 * The implicit module must be user defined, however, several methods have been developed within the CLARION Library 
 * to make the generation of drives simpler (see the DriveEquation and Personality classes in the extensions package).
 * In addition, CLARION does not require any specific inputs for a drive. However, a set of typical inputs has been 
 * specified within the "TypicalInputs" enumerator located in the Drive class. If you wish to use the typical inputs for 
 * the implicit module of a drive, then the input values of the implicit module MUST have IDs that are of the enumerated 
 * type "TypicalInputs". Also, those values MUST be contained within a dimension that has the same ID as the drive 
 * to which the implicit module is associated. The MS has been setup to automatically generate appropriate input for
 * those drive inputs that have IDs of the enumerated type "TypicalInputs".
 * <p>
 * The CLARION theory (see Sun 2003, 2007) posits several "primary drives" that are the basic motivators of human behavior.
 * If you wish to use any of these primary drives, a PrimaryDrives enumerator can be found in the Personality class located 
 * within the extensions package of the CLARION Library. Primary drives within CLARION should have an ID that is of the 
 * enumerated type "PrimaryDrives".
 * <p>
 * When adding drives to the MS, they can be placed in any of 3 "behavioral systems" located within the MS:<br>
 * <ul>
 * <li>The Behavioral Approach System - for those drives that are approach in nature.*</li>
 * <li>The Behavioral Inhibition System - for those drives that are avoidance in nature.*</li>
 * <li>Neither Behavioral System - for those drives that are neither approach nor avoidance
 * in nature.</li>
 * </ul>
 * <i>*If a drive is both approach and avoidance in nature, it should be placed within both of
 * the behavioral systems (using the addDriveToBothSystems or addDrivesToBothSystems methods). Additionally, if the 
 * drive belongs to both the BIS and BAS, the SYSTEM_GAIN (of the enumerated type TypicalInputs) will be set
 * by the MS to the average of the gains of the two systems.
 * <p>
 * Note that each of the behavioral systems has its own gain parameter that can be used when calculating drive strengths 
 * for the drives located within each system respectively.
 * <p>
 * Below is a list of the primary drives and the behavioral system to which they are affiliated:<br>
 * <ul>
 * <li>FOOD - Approach (BAS)</li>
 * <li>WATER - Approach (BAS)</li>
 * <li>REST - Avoidance (BIS)</li>
 * <li>AVOIDING_PHYSICAL_DANGER - Avoidance (BIS)</li>
 * <li>REPRODUCTION - Approach (BAS)</li>
 * <li>AVOIDING_THE_UNPLEASANT - Avoidance (BIS)</li>
 * <li>AFFILIATION_AND_BELONGINGNESS - Both</li>
 * <li>RECOGNITION_AND_ACHEIVEMENT - Approach (BAS)</li>
 * <li>DOMINANCE_AND_POWER - Approach (BAS)</li>
 * <li>AUTONOMY - Both</li>
 * <li>DEFERENCE - Both</li>
 * <li>SIMILANCE - Both</li>
 * <li>FAIRNESS - Both</li>
 * <li>HONOR - Avoidance (BIS)</li>
 * <li>NURTURANCE - Approach (BAS)</li>
 * <li>CONSERVATION - Avoidance (BIS)</li>
 * <li>CURIOSITY - Approach (BAS)</li>
 * </ul>
 * The CLARION Library is perfectly capable of handing drives that are arbitrarily defined (i.e. not a 
 * primary drive). However, conceptually, any user defined drives should be thought of as a
 * derived drive that (theoretically, not implementationally) extends from a combination of the
 * primary drives. For example, the hypothetical drive for "success" could derive from a
 * combination of the Affiliation & Belongingness, Recognition & Achievement, Dominance & Power, etc.
 * drives.
 * <p>
 * Although, conceptually, the goal structure is also located on the top level of the MS, implementationally 
 * it is logical to specify it as an intermediate module since it is used by several subsystems. 
 * Therefore, in the CLARION Library, the goal structure is contained within the CLARION class.
 * <p>
 * Note that it is not required that a CLARION agent have an MS at all. If the task being performed
 * by the agent does not require an MS, the user can choose simply not to attach the MS to the
 * CLARION agent and that agent will be able to operate quite successfully without this subsystem being
 * specified (for some tasks).
 * @version 6.0.6
 * @author Nick Wilson
 */
public final class MS extends AbstractSubsystem{
	private static final long serialVersionUID = -2375234373273521625L;
	
	/**The behavioral approach system*/
	private BehavioralApproachSystem BAS = new BehavioralApproachSystem ();
	
	/**The behavioral inhibition system*/
	private BehavioralInhibitionSystem BIS = new BehavioralInhibitionSystem ();
	
	/**The system for drives that belong to neither behavioral system*/
	private NeitherBehavioralSystem NEITHER = new NeitherBehavioralSystem ();
	
	/**The current input (including current sensory information, current goal, chunks in 
	 * working memory, and drive stimulus).*/
	private DimensionValueCollection CurrentInput;
	
	/**
	 * Minimally initializes the MS. This constructor builds a "frame" for this instance of
	 * the MS from which all desired components can be attached. During initialization
	 * this instance of the MS will attach itself to the CLARION agent you specify.
	 * @param Agent The agent to which the subsystem is being attached.
	 */
	public MS (CLARION Agent) 
	{
		super(Agent);
	}
	
	/**
	 * Gets the drive strength for the drive with the specified ID. If none of the 
	 * behavioral systems have a drive with the specified ID, this method returns null.
	 * <p>
	 * If you are using the drive equation, the specified drive input MUST have a dimension whose ID is 
	 * equal to the specified ID and that dimension must contain a value with the ID: STIMULUS 
	 * (of enumerated type RequiredEquationInputs located in the from the DriveEquation class).
	 * @param ID The ID of the drive whose drive strength you want to get.
	 * @param DriveInput The current input for the MS.
	 * @return The strength of the drive with the specified ID.
	 */
	public DriveStrength calculateDriveStrength (Object ID, DimensionValueCollection DriveInput)
	{
		CurrentInput = DriveInput.clone();
		Drive d = BAS.get(ID);
		if(d != null)
		{
			Dimension dim = CurrentInput.get(d.getID());
			if (dim == null)
			{
				dim = new Dimension (d.getID());
				CurrentInput.put(dim.getID(),dim);
			}
			Value val = dim.get(Drive.TypicalInputs.SYSTEM_GAIN);
			if (BIS.containsKey(d.getID()))
			{
				if (val == null)
				{
					dim.put(Drive.TypicalInputs.SYSTEM_GAIN,
							new Value(Drive.TypicalInputs.SYSTEM_GAIN, 
							(BAS.GAIN + BIS.GAIN)/2));
				}
				else
				{
					val.setActivation((BAS.GAIN + BIS.GAIN)/2);
				}
			}
			else
			{
				if (val == null)
				{
					dim.put(Drive.TypicalInputs.SYSTEM_GAIN,
							new Value(Drive.TypicalInputs.SYSTEM_GAIN,BAS.GAIN));
				}
				else
				{
					val.setActivation(BAS.GAIN);
				}
			}
			return d.calculateDriveStrength(CurrentInput);
		}
		d = BIS.get(ID);
		if(d != null)
		{
			Dimension dim = CurrentInput.get(d.getID());
			if (dim == null)
			{
				dim = new Dimension (d.getID());
				CurrentInput.put(dim.getID(),dim);
			}
			Value val = dim.get(Drive.TypicalInputs.SYSTEM_GAIN);
			if (val == null)
			{
				dim.put(Drive.TypicalInputs.SYSTEM_GAIN,
						new Value(Drive.TypicalInputs.SYSTEM_GAIN,BIS.GAIN));
			}
			else
			{
				val.setActivation(BIS.GAIN);
			}
			return d.calculateDriveStrength(CurrentInput);
		}
		d = NEITHER.get(ID);
		if(d == null)
			return null;
		else
		{
			Dimension dim = CurrentInput.get(d.getID());
			if (dim == null)
			{
				dim = new Dimension (d.getID());
				CurrentInput.put(dim.getID(),dim);
			}
			Value val = dim.get(Drive.TypicalInputs.SYSTEM_GAIN);
			if (val == null)
			{
				dim.put(Drive.TypicalInputs.SYSTEM_GAIN,
						new Value(Drive.TypicalInputs.SYSTEM_GAIN,NEITHER.GAIN));
			}
			else
			{
				val.setActivation(NEITHER.GAIN);
			}
			return d.calculateDriveStrength(CurrentInput);
		}
	}
	
	/**
	 * Gets the drive strengths for all drives in the behavioral approach system.
	 * <p>
	 * If you are using the drive equation, the dimensions within the specified drive input MUST have 
	 * IDs equal to the IDs of drives you want to use and those dimensions must contain a value with 
	 * the ID: STIMULUS (of enumerated type RequiredEquationInputs located in the DriveEquation class).
	 * <p>
	 * If there are no drives that belong to the BAS, this method returns null.
	 * @param DriveInput The current input to the MS.
	 * @return The drive strengths for the drives in the BAS. The ID of the drive 
	 * strengths are the same as the specified drives with which they are associated. Null if 
	 * no drives belong to the BAS.
	 */
	public DriveStrengthCollection calculateBASDriveStrengths (DimensionValueCollection DriveInput)
	{
		DriveStrengthCollection dss = new DriveStrengthCollection ();
		for(Drive i : BAS.values())
		{
			DriveStrength ds = calculateDriveStrength(i.getID(), DriveInput);
			if(ds != null)
				dss.put(ds.getID(),ds);
		}
		if(dss.size() == 0)
			return null;
		return dss;
	}
	
	/**
	 * Gets the drive strengths for all drives in the behavioral inhibition system.
	 * <p>
	 * If you are using the equation, the dimensions within the specified drive input MUST have 
	 * IDs equal to the IDs of drives you want to use and those dimensions must contain a value with 
	 * the ID: STIMULUS (of enumerated type RequiredEquationInputs located in the DriveEquation class).
	 * <p>
	 * If there are no drives that belong to the BIS, this method returns null.
	 * @param DriveInput The current input to the MS.
	 * @return The drive strengths for the drives in the BIS. The ID of the drive 
	 * strengths are the same as the specified drives with which they are associated. Null if 
	 * no drives belong to the BIS.
	 */
	public DriveStrengthCollection calculateBISDriveStrengths (DimensionValueCollection DriveInput)
	{
		DriveStrengthCollection dss = new DriveStrengthCollection ();
		for(Drive i : BIS.values())
		{
			DriveStrength ds = calculateDriveStrength(i.getID(), DriveInput);
			if(ds != null)
				dss.put(ds.getID(),ds);
		}
		if (dss.size() == 0)
			return null;
		return dss;
	}
	
	/**
	 * Gets the drive strengths for all drives that belong to both the BAS and BIS.
	 * <p>
	 * If you are using the equation, the dimensions within the specified drive input MUST have 
	 * IDs equal to the IDs of drives you want to use and those dimensions must contain a value with 
	 * the ID: STIMULUS (of enumerated type RequiredEquationInputs located in the DriveEquation class).
	 * @param DriveInput The current input to the MS.
	 * @return The drive strengths for the drives that belong to both the BAS and BIS. The ID of 
	 * the drive strengths are the same as the specified drives with which they are associated.
	 */
	public DriveStrengthCollection calculateBothDriveStrengths (DimensionValueCollection DriveInput)
	{
		DriveStrengthCollection dss = new DriveStrengthCollection ();
		
		for(Drive i : BAS.values())
		{
			if(BIS.containsKey(i.getID()))
			{
				DriveStrength ds = calculateDriveStrength(i.getID(), DriveInput);
				if(ds != null)
					dss.put(ds.getID(),ds);
			}
		}
		if(dss.size() == 0)
			return null;
		return dss;
	}
	
	/**
	 * Gets the drive strengths for all drives in the MS that do not belong to a behavioral
	 * system.
	 * <p>
	 * If you are using the equation, the dimensions within the specified drive input MUST have 
	 * IDs equal to the IDs of drives you want to use and those dimensions must contain a value with 
	 * the ID: STIMULUS (of enumerated type RequiredEquationInputs located in the DriveEquation class).
	 * <p>
	 * If there are no drives that belong to neither behavioral system, this method returns null.
	 * @param DriveInput The current input to the MS.
	 * @return The drive strengths for the drives in neither behavioral system. The ID of the drive 
	 * strengths are the same as the specified drives with which they are associated. Null if 
	 * no drives belong to the neither behavioral system.
	 */
	public DriveStrengthCollection calculateNeitherDriveStrengths (DimensionValueCollection DriveInput)
	{
		DriveStrengthCollection dss = new DriveStrengthCollection ();
		for(Drive i : NEITHER.values())
		{
			DriveStrength ds = calculateDriveStrength(i.getID(), DriveInput);
			if(ds != null)
				dss.put(ds.getID(),ds);
		}
		if(dss.size() == 0)
			return null;
		return dss;
	}
	
	/**
	 * Gets the drive strengths for ALL drives in the MS.
	 * <p>
	 * If you are using the equation, the dimensions within the specified drive input MUST have 
	 * IDs equal to the IDs of drives you want to use and those dimensions must contain a value with 
	 * the ID: STIMULUS (of enumerated type RequiredEquationInputs located in the DriveEquation class).
	 * <p>
	 * If there are no drives in the MS, this method returns null.
	 * @param DriveInput The current input to the MS.
	 * @return The drive strengths for the drives in the MS. The ID of the drive 
	 * strengths are the same as the specified drives with which they are associated. Null if there 
	 * are no drives in the MS.
	 */
	public DriveStrengthCollection calculateAllDriveStrengths (DimensionValueCollection DriveInput)
	{
		DriveStrengthCollection dss = new DriveStrengthCollection ();
		for(Drive i : BAS.values())
		{
			DriveStrength ds = calculateDriveStrength(i.getID(), DriveInput);
			if(ds != null)
				dss.put(ds.getID(),ds);
		}
		for(Drive i : BIS.values())
		{
			if(!dss.containsValue(i.DS))
			{
				DriveStrength ds = calculateDriveStrength(i.getID(), DriveInput);
				if(ds != null)
					dss.put(ds.getID(),ds);
			}
		}
		for(Drive i : NEITHER.values())
		{
			DriveStrength ds = calculateDriveStrength(i.getID(), DriveInput);
			dss.put(ds.getID(),ds);
		}
		if(dss.size() == 0)
			return null;
		return dss;
	}
	
	/**
	 * Gets the number of drives in the MS.
	 * @return The number of drives.
	 */
	public int getNumDrives ()
	{
		int size = BAS.size() + BIS.size() + NEITHER.size() - getBothDrives().size();
		return size;
	}
	
	/**
	 * Gets the drive with the specified ID. If the MS does not contain
	 * a drive with the specified ID, this method returns null.
	 * @param ID The ID of the drive to get.
	 * @return The drive with the specified ID. Null if the MS does not contain
	 * a drive with the specified ID.
	 */
	public Drive getDrive (Object ID)
	{
		if(BAS.containsKey(ID))
			return BAS.get(ID);
		if(BIS.containsKey(ID))
			return BIS.get(ID);
		if(NEITHER.containsKey(ID))
			return NEITHER.get(ID);
		return null;
	}
	
	/**
	 * Gets the all the drives in the MS. The drives are returned in a new collection, so
	 * modifying the returned collection will not affect the structure of any of the behavioral
	 * systems.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If no drives exist in the MS, this method returns null.
	 * @return The drives in the MS. Null if the MS does not have any drives.
	 */
	public Collection <Drive> getAllDrives ()
	{
		DriveCollection ds = new DriveCollection ();
		ds.putAll(BAS);
		for(Drive i : BIS.values())
		{
			if(!ds.containsKey(i.getID()))
				ds.put(i.getID(),i);
		}
		ds.putAll(NEITHER);
		if(ds.size() == 0)
			return null;
		return ds.values();
	}

	/**
	 * Gets the drives in the behavioral approach system. The drives are returned in a new collection, 
	 * therefore modifying the returned collection will not affect the structure of the BAS.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If the BAS is not being used, this method returns null.
	 * @return A collection of the drives in the behavioral approach system. Null
	 * if the system is not being used.
	 */ 
	public Collection <Drive> getBASDrives ()
	{
		if(BAS.size() == 0)
			return null;
		return ((DriveCollection)BAS.clone()).values();
	}
	
	/**
	 * Gets the drives in the behavioral inhibition system. The drives are returned in a new collection, 
	 * therefore modifying the returned collection will not affect the structure of the BIS.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If the BIS is not being used, this method returns null.
	 * @return A collection of the drives in the behavioral inhibition system. Null
	 * if the system is not being used.
	 */
	public Collection <Drive> getBISDrives ()
	{
		if(BIS.size() == 0)
			return null;
		return ((DriveCollection)BIS.clone()).values();
	}
	
	/**
	 * Gets the drives that are in both the BAS and BIS. The drives are returned in a new collection,
	 * therefore modifying the returned collection will not affect the structure of any of the drive 
	 * systems.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If neither the BIS or BAS is being used, this method returns null. If only one of the systems 
	 * is being used, this method will return just the items from that system.
	 * @return A collection of the drives in the BIS and/or BAS. Null
	 * if neither system is being used.
	 */
	public Collection <Drive> getBothDrives ()
	{
		DriveCollection dc = new DriveCollection();
		for(Drive d : BAS.values())
		{
			if(BIS.containsKey(d.getID()))
				dc.put(d.getID(), d);
		}
		if(dc.size() == 0)
			return null;
		return dc.values();
	}
	
	/**
	 * Gets the drives that have been specified as being in neither behavioral system. The drives are 
	 * returned in a new collection, therefore modifying the returned collection will not affect the 
	 * structure of the neither behavioral system collection.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If no drives have been specified as belonging to neither behavioral system, this method 
	 * returns null.
	 * @return A collection of the drives in neither behavioral system. Null
	 * if the system is not being used.
	 */
	public Collection <Drive> getNeitherDrives ()
	{
		if(NEITHER.size() == 0)
			return null;
		return ((DriveCollection)NEITHER.clone()).values();
	}
	
	/**
	 * Gets the drive strength for the drive with the specified ID. If the MS does not contain
	 * a drive with the specified ID, this method returns null.
	 * @param ID The ID of the drive whose drive strength you wish to get.
	 * @return The drive strength for the drive with the specified ID. Null if the MS does not contain
	 * a drive with the specified ID.
	 */
	public DriveStrength getDriveStrength (Object ID)
	{
		if(BAS.containsKey(ID))
			return BAS.get(ID).DS;
		if(BIS.containsKey(ID))
			return BIS.get(ID).DS;
		if(NEITHER.containsKey(ID))
			return NEITHER.get(ID).DS;
		return null;
	}
	
	/**
	 * Gets all of the drive strength objects for the drives in the MS. The drive strengths for the drives 
	 * are returned in a new collection, therefore modifying the returned collection will not affect 
	 * the structure of any of the drive systems.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If no drives exist in the MS, this method returns null.
	 * @return The drive strengths for the drives in the MS. Null if the MS does not have any drives.
	 */
	public DriveStrengthCollection getAllDriveStrengths ()
	{
		DriveStrengthCollection ds = new DriveStrengthCollection ();
		ds.putAll(BAS.getDriveStrengths());
		for(DriveStrength i : BIS.getDriveStrengths().values())
		{
			if(!ds.containsValue(i))
				ds.put(i.getID(),i);
		}
		ds.putAll(NEITHER.getDriveStrengths());
		if(ds.size() == 0)
			return null;
		return ds;
	}

	/**
	 * Gets the drive strengths for the drives in the behavioral approach system. The drive strengths 
	 * for the drives are returned in a new collection, therefore modifying the returned collection 
	 * will not affect the structure of any of the drive system.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If the BAS is not being used, this method returns null.
	 * @return A collection of the drive strengths for the drives in the behavioral 
	 * approach system. Null if the system is not being used.
	 */ 
	public DriveStrengthCollection getBASDriveStrengths ()
	{
		if(BAS.size() == 0)
			return null;
		return BAS.getDriveStrengths();
	}
	
	/**
	 * Gets the drive strengths for the drives in the behavioral inhibition system. The drive strengths
	 * for the drives are returned in a new collection, therefore modifying the returned collection 
	 * will not affect the structure of any of the drive system.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If the BIS is not being used, this method returns null.
	 * @return A collection of the drive strengths for the drives in the behavioral 
	 * inhibition system. Null if the system is not being used.
	 */
	public DriveStrengthCollection getBISDriveStrengths ()
	{
		if(BIS.size() == 0)
			return null;
		return BIS.getDriveStrengths();
	}
	
	/**
	 * Gets the drive strengths for the drives that are in both the BAS and BIS. The drive strengths 
	 * for the drives are returned in a new collection, therefore modifying the returned collection 
	 * will not affect the structure of any of the drive systems.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If neither the BIS or BAS is being used, this method returns null. If only one of the systems 
	 * is being used, this method will return just the items from that system.
	 * @return A collection of the drive strengths for the drives in the BIS and/or BAS. Null
	 * if neither system is being used.
	 */
	public DriveStrengthCollection getBothDriveStrengths ()
	{
		DriveStrengthCollection dc = new DriveStrengthCollection();
		for(DriveStrength d : BAS.getDriveStrengths().values())
		{
			if(BIS.containsKey(d.getID()))
				dc.put(d.getID(),d);
		}
		if(dc.size() == 0)
			return null;
		return dc;
	}
	
	/**
	 * Gets the drive strengths for the drives that have been specified as being in neither behavioral system.
	 * The drive strengths for the drives are returned in a new collection, therefore modifying the 
	 * returned collection will not affect the structure of any of the drive system.
	 * <p>
	 * This method should NOT be used for obtaining drive strengths from the drives. If you
	 * wish to obtain the drive strengths, use one of the calculate methods.
	 * <p>
	 * If no drives have been specified as belonging to the neither behavioral system, this method 
	 * returns null.
	 * @return A collection of the drive strengths for the drives in neither behavioral system. Null
	 * if the system is not being used.
	 */
	public DriveStrengthCollection getNeitherDriveStrengths ()
	{
		if(NEITHER.size() == 0)
			return null;
		return NEITHER.getDriveStrengths();
	}
	
	/**
	 * Adds the specified drive to the behavior approach system. Since drives are posited as
	 * being hard-wired for the most part, this method should only be used during 
	 * initialization. If the specified drive is already in the behavioral approach system, 
	 * an exception is thrown.
	 * @param drive The drive to add to the BAS
	 * @throws IllegalArgumentException If the specified drive is already in the MS.
	 */
	public void addDriveToBAS (Drive drive) throws IllegalArgumentException
	{
		if(!NEITHER.containsKey(drive.getID()) && !BIS.containsKey(drive.getID()))
		{
			updateInputSpace(drive.getImplicitModule().getInput());
			BAS.put(drive.getID(), drive);
		}
		else
			throw new IllegalArgumentException ("The specified drive is already in the MS.");
	}
	
	/**
	 * Adds the specified drives to the behavior approach system. Since drives are posited as
	 * being hard-wired for the most part, this method should only be used during 
	 * initialization.
	 * @param drives The drives to add to the BAS.
	 */
	public void addDrivesToBAS (Collection <Drive> drives)
	{
		for(Drive d : drives)
			addDriveToBAS(d);
	}
	
	/**
	 * Adds the specified drive to the behavior inhibition system. Since drives are posited as
	 * being hard-wired for the most part, this method should only be used during 
	 * initialization. If the specified drive is already in the MS, 
	 * an exception is thrown.
	 * @param drive The drive to add to the BIS
	 * @throws IllegalArgumentException If the specified drive is already in the MS.
	 */
	public void addDriveToBIS (Drive drive) throws IllegalArgumentException
	{
		if(!BAS.containsKey(drive.getID()) && !NEITHER.containsKey(drive.getID()))
		{
			updateInputSpace(drive.getImplicitModule().getInput());
			BIS.put(drive.getID(), drive);
		}
		else
			throw new IllegalArgumentException ("The specified drive is already in the MS.");
	}
	
	/**
	 * Adds the specified drives to the behavior inhibition system. Since drives are posited as
	 * being hard-wired for the most part, this method should only be used during 
	 * initialization.
	 * @param drives The drives to add to the BIS.
	 */
	public void addDrivesToBIS (Collection <Drive> drives)
	{
		for(Drive d : drives)
			addDriveToBIS(d);
	}
	
	/**
	 * Adds the specified drive to both the BIS and the BAS. Since drives are posited as
	 * being hard-wired for the most part, this method should only be used during 
	 * initialization. If the specified drive is already in another system, 
	 * an exception is thrown.
	 * @param drive The drive to add to the BIS and BAS.
	 * @throws IllegalArgumentException If the specified drive is already in the MS.
	 */
	public void addDriveToBothSystems (Drive drive) throws IllegalArgumentException
	{
		if(!NEITHER.containsKey(drive.getID()))
		{
			updateInputSpace(drive.getImplicitModule().getInput());
			BIS.put(drive.getID(), drive);
			BAS.put(drive.getID(), drive);
		}
		else
			throw new IllegalArgumentException ("The specified drive is already in the MS.");
	}
	
	/**
	 * Adds the specified drives to both the BIS and the BAS. Since drives are posited as
	 * being hard-wired for the most part, this method should only be used during 
	 * initialization.
	 * @param drives The drives to add to the BIS and BAS.
	 */
	public void addDrivesToBothSystems (Collection <Drive> drives)
	{
		for(Drive d : drives)
			addDriveToBothSystems(d);
	}
	
	/**
	 * Adds the specified drive to the MS without specifying a behavioral system. Since 
	 * drives are posited as being hard-wired for the most part, this method should only 
	 * be used during initialization. If the specified drive is already in the MS an exception
	 * is thrown.
	 * @param drive The drive to add to the MS.
	 * @throws IllegalArgumentException If the specified drive is already in the MS.
	 */
	public void addDriveToNeither (Drive drive) throws IllegalArgumentException
	{
		if(!BAS.containsKey(drive.getID()) && !BIS.containsKey(drive.getID()))
		{
			updateInputSpace(drive.getImplicitModule().getInput());
			NEITHER.put(drive.getID(), drive);
		}
		else
			throw new IllegalArgumentException ("The specified drive is already in the MS.");
	}
	
	/**
	 * Adds the specified drives to the MS without specifying a behavioral system. Since 
	 * drives are posited as being hard-wired for the most part, this method should only 
	 * be used during initialization.
	 * @param drives The drives to add to the MS.
	 */
	public void addDrivesToNeither (Collection <Drive> drives)
	{
		for(Drive d : drives)
			addDriveToNeither(d);
	}
	
	/**
	 * Gets the BAS. This method returns the ACTUAL behavioral approach system and therefore
	 * should be accessed and used with caution.
	 * <p>
	 * This method should ONLY be used for initialization and reporting purposes.
	 * @return The BAS.
	 */
	public BehavioralApproachSystem getBAS ()
	{
		return BAS;
	}
	
	/**
	 * Gets the BIS. This method returns the ACTUAL behavioral inhibition system and therefore
	 * should be accessed and used with caution.
	 * <p>
	 * This method should ONLY be used for initialization and reporting purposes.
	 * @return The BIS.
	 */
	public BehavioralInhibitionSystem getBIS ()
	{
		return BIS;
	}
	
	/**
	 * Gets the system for drives that belong to neither behavioral system. This method returns
	 * the ACTUAL system and therefore should be accessed and used with caution.
	 * <p>
	 * This method should ONLY be used for initialization and reporting purposes.
	 * @return The "neither" behavioral system.
	 */
	public NeitherBehavioralSystem getNeitherSystem ()
	{
		return NEITHER;
	}
	
	/**
	 * Attaches the MS to the specified CLARION agent.
	 * @param Agent The agent to wish this MS will be attached.
	 */
	protected void attachSelfToAgent(CLARION Agent) {
		Agent.attachMS(this);
	}

	/**
	 * Performs the appropriate end of episode instructions for the MS. This method is called by the
	 * CLARION class when its endEpisode method is called.
	 * @param Input A collection of various information to be used for ending the episode.
	 * @param TimeStamp The current time stamp.
	 */
	protected void endEpisode(DimensionValueCollection Input, long TimeStamp) {
		return;
	}
}
