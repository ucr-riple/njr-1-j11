package clarion.extensions;

import java.util.EnumMap;
import java.util.ArrayList;
import java.util.Collection;

import clarion.system.*;

/**
 * This class implements a personality model loosely based on the primary drives posited in the CLARION theory.
 * This class contains only enumerators and static methods, therefore it does not need to be instantiated.
 * <p>
 * <b>Usage:</b>
 * <p>
 * This class is primarily used to setup drives within the motivational subsystem (MS) in CLARION. It
 * provides enumerators and methods that can assist in speeding up the process of initializing the MS.
 * <p>
 * For information regarding the theory behind this implementation of the personality model, please see the 
 * addendum to the CLARION tutorial on the MS (Sun, 2008).
 * @version 6.0.6
 * @author Nick Wilson
 */
public class Personality {
	/**The primary drives posited by CLARION.*/
	public enum PrimaryDrives {FOOD, WATER, REST, AVOIDING_PHYSICAL_DANGER, REPRODUCTION,
		AVOIDING_THE_UNPLEASANT, AFFILIATION_AND_BELONGINGNESS, RECOGNITION_AND_ACHEIVEMENT,
		DOMINANCE_AND_POWER, AUTONOMY, DEFERENCE, SIMILANCE, FAIRNESS, HONOR, NURTURANCE, 
		CONSERVATION, CURIOSITY};
	
	/**For the PrimaryDrives enumerator, this specifies to which behavioral system they belong.
	 * By using this enumerator, you can find out which of the PrimaryDrives belong to:<br>
	 * <ul>
	 * <li>The BAS only</li>
	 * <li>The BIS only</li>
	 * <li>Both systems</li>
	 * <li>Neither system</li>
	 * </ul>
	 * The members of the systems are stored in the Drives list accessed by typing, for example:
	 * <p>
	 * <i>Personality.BehavioralSystemMembers.BAS_ONLY.Drives</i>
	 * <p>
	 * The objects within the Drives list are the enumerated PrimaryDrives.
	 */
	public enum BehavioralSystemMembers {
		BAS_ONLY (PrimaryDrives.FOOD, PrimaryDrives.WATER, PrimaryDrives.REPRODUCTION,
				PrimaryDrives.RECOGNITION_AND_ACHEIVEMENT, PrimaryDrives.DOMINANCE_AND_POWER,
				PrimaryDrives.NURTURANCE, PrimaryDrives.CURIOSITY),
		BIS_ONLY (PrimaryDrives.REST, PrimaryDrives.AVOIDING_PHYSICAL_DANGER, 
				PrimaryDrives.AVOIDING_THE_UNPLEASANT, PrimaryDrives.HONOR,
				PrimaryDrives.CONSERVATION),
		BOTH_SYSTEMS (PrimaryDrives.AFFILIATION_AND_BELONGINGNESS, PrimaryDrives.AUTONOMY,
				PrimaryDrives.DEFERENCE, PrimaryDrives.SIMILANCE, PrimaryDrives.FAIRNESS),
		NEITHER_SYSTEM ();
		
		/**The list of primary drives contained within the behavioral system.*/
		public final ArrayList <PrimaryDrives> Drives;
		
		/**
		 * Initializes the enumerated types for this enumerator.
		 * @param d The primary drives associated with the behavioral system identified by the enumerated type.
		 */
		private BehavioralSystemMembers (PrimaryDrives... d){
			Drives = new ArrayList<PrimaryDrives> (d.length);
			for(PrimaryDrives i : d)
				Drives.add(i);
		}
	};
	
	/**Default personality types whose drives can be used to initialize the MS.
	 * By using this enumerator, you can quickly setup the MS to one of the default personalities.
	 * This is done by:<br>
	 * <ol>
	 * <li>Adding the BAS only drives to the MS. For example -
	 * <br><i>MS.addDrivesToBAS(Personality.PersonalityTypes.GENERIC.Drives.get(Personality.BehavioralSystemMembers.BAS_ONLY).values());</i></li>
	 * <li>Adding the BIS only drives to the MS. For example -
	 * <br><i>MS.addDrivesToBIS(Personality.PersonalityTypes.GENERIC.Drives.get(Personality.BehavioralSystemMembers.BIS_ONLY).values());</i></li>
	 * <li>Adding the drives that belong to BOTH systems to the MS. For example -
	 * <br><i>MS.addDrivesToBothSystems(Personality.PersonalityTypes.GENERIC.Drives.get(Personality.BehavioralSystemMembers.BOTH_SYSTEMS).values());</i></li>
	 * <li>Adding the drives that belong to NEITHER system to the MS. For example -
	 * <br><i>MS.addDrivesToNeither(Personality.PersonalityTypes.GENERIC.Drives.get(Personality.BehavioralSystemMembers.NEITHER_SYSTEM).values());</i></li>
	 * </ol>
	 * The drives contained within the personalities have been initialized using the drive equation for the implicit module.
	 * If you wish to substitute a different implicit module, call the setImplicitModule method for each drive specifying
	 * the implicit module you wish to use. 
	 */
	public enum PersonalityTypes {
		SOCIABLE (/*F*/.01,/*W*/.01,/*R*/.05,/*D*/.2,/*Re*/.4,/*U*/.2,/*AB*/.9,/*RA*/.5,/*DP*/.3,/*A*/.4,/*De*/.3,/*S*/.5,/*Fa*/.3,/*H*/.3,/*N*/.4,/*C*/.3,/*Cu*/.5),
		SHY (/*F*/.01,/*W*/.01,/*R*/.2,/*D*/.4,/*Re*/.05,/*U*/.4,/*AB*/.2,/*RA*/.0,/*DP*/.0,/*A*/.1,/*De*/.7,/*S*/.7,/*Fa*/.05,/*H*/.3,/*N*/.4,/*C*/.7,/*Cu*/.2),
		CONFIDENT (/*F*/.01,/*W*/.01,/*R*/.0,/*D*/.0,/*Re*/.5,/*U*/.0,/*AB*/.4,/*RA*/.8,/*DP*/.8,/*A*/.5,/*De*/.1,/*S*/.3,/*Fa*/.05,/*H*/.3,/*N*/.4,/*C*/.1,/*Cu*/.8),
		ANXIOUS (/*F*/.01,/*W*/.01,/*R*/.1,/*D*/.7,/*Re*/.05,/*U*/.7,/*AB*/.6,/*RA*/.0,/*DP*/.0,/*A*/.3,/*De*/.7,/*S*/.9,/*Fa*/.3,/*H*/.3,/*N*/.4,/*C*/.9,/*Cu*/.1),
		RESPONSIBLE (/*F*/.01,/*W*/.01,/*R*/.05,/*D*/.2,/*Re*/.1,/*U*/.2,/*AB*/.5,/*RA*/.9,/*DP*/.4,/*A*/.7,/*De*/.3,/*S*/.3,/*Fa*/.5,/*H*/.6,/*N*/.4,/*C*/.3,/*Cu*/.4),
		LAZY (/*F*/.01,/*W*/.01,/*R*/.6,/*D*/.0,/*Re*/.05,/*U*/.6,/*AB*/.2,/*RA*/.0,/*DP*/.0,/*A*/.1,/*De*/.1,/*S*/.9,/*Fa*/.05,/*H*/.1,/*N*/.4,/*C*/.9,/*Cu*/.0),
		GENERIC (/*F*/.01,/*W*/.01,/*R*/.05,/*D*/.2,/*Re*/.1,/*U*/.2,/*AB*/.6,/*RA*/.4,/*DP*/.3,/*A*/.5,/*De*/.3,/*S*/.5,/*Fa*/.1,/*H*/.3,/*N*/.4,/*C*/.3,/*Cu*/.4);
		
		/**An enumerated map containing collections of drives that collectively define the personality.
		 * The keys for the map are of the enumerated type BehavioralSystemMembers.*/
		public EnumMap <Personality.BehavioralSystemMembers, DriveCollection> Drives = 
			new EnumMap<Personality.BehavioralSystemMembers, DriveCollection> (Personality.BehavioralSystemMembers.class);
		/**
		 * Initializes a personality type from the list of deficits specified. The order of the 
		 * deficits must be the same as the order from the PrimaryDrives enumerator.
		 * @param deficits The deficits to set for the drives.
		 */
		private PersonalityTypes (double... deficits)
		{
			Drives.put(BehavioralSystemMembers.BAS_ONLY, new DriveCollection ());
			Drives.put(BehavioralSystemMembers.BIS_ONLY, new DriveCollection ());
			Drives.put(BehavioralSystemMembers.BOTH_SYSTEMS, new DriveCollection ());
			Drives.put(BehavioralSystemMembers.NEITHER_SYSTEM, new DriveCollection ());
			for(int i = 0; i < deficits.length; i++)
			{
				Object id = PrimaryDrives.values()[i];
				if(BehavioralSystemMembers.BAS_ONLY.Drives.contains(id))
				{
					Drives.get(BehavioralSystemMembers.BAS_ONLY).put(id, new Drive (id, deficits[i], 
							new DriveEquation(id, Drive.generateTypicalInput(id).values(), Drive.generateOutput(id))));
				}
				else if(BehavioralSystemMembers.BIS_ONLY.Drives.contains(id))
				{
					Drives.get(BehavioralSystemMembers.BIS_ONLY).put(id, new Drive (id, deficits[i], 
							new DriveEquation(id, Drive.generateTypicalInput(id).values(), Drive.generateOutput(id))));
				}
				else if(BehavioralSystemMembers.BOTH_SYSTEMS.Drives.contains(id))
				{
					Drives.get(BehavioralSystemMembers.BOTH_SYSTEMS).put(id, new Drive (id, deficits[i], 
							new DriveEquation(id, Drive.generateTypicalInput(id).values(), Drive.generateOutput(id))));
				}
				else
					Drives.get(BehavioralSystemMembers.NEITHER_SYSTEM).put(id, new Drive (id, deficits[i], 
							new DriveEquation(id, Drive.generateTypicalInput(id).values(), Drive.generateOutput(id))));
			}
		}
	};
	
	/**
	 * Initializes a user defined personality using the specified collection of deficits for the drives you wish to use.
	 * The values representing the deficits should have IDs that are of the enumerated type PrimaryDrives. 
	 * These IDs relate to the names of the primary drives that are used for creating a personality.
	 * <p>
	 * By using this method, you can quickly setup a user-defined personality within the MS.
	 * This is done by:<br>
	 * <ol>
	 * <li>Calling this method. For example -
	 * <br><i>EnumMap &lt;BehavioralSystemMembers, DriveCollection&gt; Drives = 
	 * Personality.initUserDefinedPersonality(User_Defined_Deficit_Collection);</i></li>
	 * <li>Adding the BAS only drives to the MS. For example -
	 * <br><i>MS.addDrivesToBAS(Drives.get(BehavioralSystemMembers.BAS_ONLY).values());</i></li>
	 * <li>Adding the BIS only drives to the MS. For example -
	 * <br><i>MS.addDrivesToBIS(Drives.get(BehavioralSystemMembers.BIS_ONLY).values());</i></li>
	 * <li>Adding the drives that belong to BOTH systems to the MS. For example -
	 * <br><i>MS.addDrivesToBothSystems(Drives.get(BehavioralSystemMembers.BOTH_SYSTEMS).values());</i></li>
	 * <li>Adding the drives that belong to NEITHER system to the MS. For example -
	 * <br><i>MS.addDrivesToNeither(Drives.get(BehavioralSystemMembers.NEITHER_SYSTEM).values());</i></li>
	 * </ol>
	 * The map returned by this method contains collections of drives that collectively define the personality. The keys for 
	 * the returned map are of the enumerated type BehavioralSystemMembers. The drives contained within the collections in
	 * the returned map are initialized using the drive equation for the implicit module. If you wish to substitute a 
	 * different implicit module, call the setImplicitModule method for each drive specifying the implicit module you wish 
	 * to use.
	 * <p>
	 * Any drives whose deficit is included within the specified collection, but whose ID is of a type other than the 
	 * enumerated type PrimaryDrives will be placed in the NEITHER_SYSTEM collection of the returned map.
	 * @param deficits A collection of values that specify the deficits for the drives that define the personality.
	 * @return A map containing collections of drives that have been initialized using the specified deficits.
	 */
	public static EnumMap<BehavioralSystemMembers, DriveCollection> initUserDefinedPersonality (Collection <Value> deficits)
	{
		EnumMap<BehavioralSystemMembers, DriveCollection> Drives = new EnumMap<BehavioralSystemMembers, DriveCollection> (BehavioralSystemMembers.class);
		Drives.put(BehavioralSystemMembers.BAS_ONLY, new DriveCollection ());
		Drives.put(BehavioralSystemMembers.BIS_ONLY, new DriveCollection ());
		Drives.put(BehavioralSystemMembers.BOTH_SYSTEMS, new DriveCollection ());
		Drives.put(BehavioralSystemMembers.NEITHER_SYSTEM, new DriveCollection ());
		for(Value i : deficits)
		{
			Object id = i.getID();
			if(BehavioralSystemMembers.BAS_ONLY.Drives.contains(id))
			{
				Drives.get(BehavioralSystemMembers.BAS_ONLY).put(id, new Drive (id, i.getActivation(), 
						new DriveEquation(id, Drive.generateTypicalInput(id).values(), Drive.generateOutput(id))));
			}
			else if(BehavioralSystemMembers.BIS_ONLY.Drives.contains(id))
			{
				Drives.get(BehavioralSystemMembers.BIS_ONLY).put(id, new Drive (id, i.getActivation(), 
						new DriveEquation(id, Drive.generateTypicalInput(id).values(), Drive.generateOutput(id))));
			}
			else if(BehavioralSystemMembers.BOTH_SYSTEMS.Drives.contains(id))
			{
				Drives.get(BehavioralSystemMembers.BOTH_SYSTEMS).put(id, new Drive (id, i.getActivation(), 
						new DriveEquation(id, Drive.generateTypicalInput(id).values(), Drive.generateOutput(id))));
			}
			else
				Drives.get(BehavioralSystemMembers.NEITHER_SYSTEM).put(id, new Drive (id, i.getActivation(), 
						new DriveEquation(id, Drive.generateTypicalInput(id).values(), Drive.generateOutput(id))));
		}
		return Drives;
	}
}
