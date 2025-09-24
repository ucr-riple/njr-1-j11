// ##################################################################
// ##################################################################
// #	Chord: 	Based on the Charlie Parker Omnibook Scale Syllabus #
// #	(inside Back Cover) not to be taken as the p module hearing #
// #    an actual chord, but as a variable for fitness function - 	#
// # 	the allowable scales or rather harmonicintervals considered #
// #	fit.														#
// ##################################################################
// ##################################################################
package expert_system;

public enum Chord {
	
	// ##########################################################
	// #					   	 FIELDS							#
	// ##########################################################
	MAJOR			(Scale.MAJOR, Scale.LYDIAN, Scale.LYDIAN_AUGMENTED,
					 Scale.AUGMENTED, Scale.BLUES, Scale.CPARKER_MAJOR_FLAT6,
					 Scale.DIMINISHED_HALFSTEP_FIRST), 
	
	DOMINANT_7TH	(Scale.DOMINANT_7TH, Scale.LYDIAN_DOMINANT, Scale.BLUES,
					 Scale.BLUES, Scale.HINDU, Scale.WHOLE_TONE, 
					 Scale.DIMINISHED_HALFSTEP_FIRST, Scale.DIMINISHED_WHOLE_TONE),
							 
	MINOR_DORIAN	(Scale.MINOR_DORIAN, Scale.BLUES, Scale.PURE_MINOR,
					 Scale.MELODIC_MINOR, Scale.HARMONIC_MINOR, Scale.PHRYGIAN,
					 Scale.DIMINISHED_HALFSTEP_FIRST),
							 
	HALF_DIMINISHED	(Scale.HALF_DIMINISHED_LOCRIAN, Scale.HALF_DIMINISHED_LOCRIAN_SHARP2); 
	
	private Scale [] scales;
	
	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################
	private Chord(){}
	private Chord(Scale s1, Scale s2){
		
		scales = new Scale[2];
		scales[0] = s1;
		scales[1] = s2;
		
	}
	private Chord(Scale s1, Scale s2, Scale s3,
				  Scale s4, Scale s5, Scale s6,
				  					  Scale s7){
		
		scales = new Scale[7];
		scales[0] = s1;
		scales[1] = s2;
		scales[2] = s3;
		scales[3] = s4;
		scales[4] = s5;
		scales[5] = s6;
		scales[6] = s7;
	}
	private Chord(Scale s1, Scale s2, Scale s3,
				  Scale s4, Scale s5, Scale s6,
						    Scale s7, Scale s8){

		scales = new Scale[8];
		scales[0] = s1;
		scales[1] = s2;
		scales[2] = s3;
		scales[3] = s4;
		scales[4] = s5;
		scales[5] = s6;
		scales[6] = s7;
		scales[7] = s8;
	}
	// Returns the scales associated with the chord
	public Scale [] getScales(){
		return scales;
	}
}