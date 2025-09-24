// ##################################################################
// ##################################################################
// # Rhythmic_Value: Each type stores a string representation, a 	#
// # decimal value and a binary value for itself. The string value 	#
// # is used for to tell the pipe object in max how long to delay 	#
// # notes for, the decimal values can be summed to see how long a 	#
// # set of rhythmic_Values are and the binary value signifies 		#
// # whether note is on or off (note or rest).						#
// #																#
// ##################################################################
// ##################################################################
package expert_system;

public enum Rhythmic_Value{
	
	// ############################################################
	// #						TYPES						      #
	// ############################################################
	
	SEMIBREVE						("1n", 4, 1),
	DOTTED_MINIM					("2nd", 3, 1),
	MINIM							("2n", 2, 1),
	TRIPLET_MINIM					("2nt", 1.333, 1),
	DOTTED_CROTCHET					("4nd", 1.5, 1),
	CROTCHET						("4n", 1, 1),
	TRIPLET_CROTCHET				("4nt", 0.6666, 1),
	DOTTED_QUAVER					("8nd", 0.75, 1),
	QUAVER							("8n", 0.5, 1),
	TRIPLET_QUAVER					("8nt", 0.3333, 1),
	DOTTED_SEMI_QUAVER				("16nd", 0.375, 1),
	SEMI_QUAVER						("16n", 0.25, 1),
	TRIPLET_SEMI_QUAVER				("16nt", 0.16666, 1),
	DOTTED_DEMI_SEMI_QUAVER			("32nd", 0.1875, 1),
	DEMI_SEMI_QUAVER				("32n", 0.125, 1),
	TRIPLET_DEMI_SEMI_QUAVER		("32nt",0.083333, 1),
	DOTTED_HEMI_DEMI_SEMI_QUAVER	("64nd", 0.09375, 1),
	HEMI_DEMI_SEMI_QUAVER			("64n", 0.0625, 1),
	ONE_TWO_EIGHT_NOTE				("128n",0.03125, 1),
	
	SEMIBREVE_REST					("1n", 4, 0),
	DOTTED_MINIM_REST				("2nd", 3, 0),
	MINIM_REST						("2n", 2, 0),
	TRIPLET_MINIM_REST				("2nt", 1.333, 0),
	DOTTED_CROTCHET_REST			("4nd", 1.5, 0),
	CROTCHET_REST					("4n", 1, 0),
	TRIPLET_CROTCHET_REST			("4nt", 0.6666, 0),
	DOTTED_QUAVER_REST				("8nd", 0.75, 0),
	QUAVER_REST						("8n", 0.5, 0),
	TRIPLET_QUAVER_REST				("8nt", 0.3333, 0),
	DOTTED_SEMI_QUAVER_REST			("16nd", 0.375, 0),
	SEMI_QUAVER_REST				("16n", 0.25, 0),
	TRIPLET_SEMI_QUAVER_REST		("16nt", 0.16666, 0),
	DOTTED_DEMI_SEMI_QUAVER_REST	("32nd", 0.1875, 0),
	DEMI_SEMI_QUAVER_REST			("32n", 0.125, 0),
	TRIPLET_DEMI_SEMI_QUAVER_REST	("32nt",0.083333, 0),
	DOTTED_HEMI_DEMI_SEMI_QUAVER_REST("64nd", 0.09375, 0),
	HEMI_DEMI_SEMI_QUAVER_REST		("64n", 0.0625, 0),
	ONE_TWO_EIGHT_NOTE_REST			("128n",0.03125, 0);
	// ############################################################
	// #						 FIELDS							  #
	// ############################################################
	
	public String noteVal;
	public double beatVal;
	public int noteOnOff;
	public int order;
	
	// ############################################################
	// #					  CONSTRUCTOR						  #
	// ############################################################
	private Rhythmic_Value(String s, double d, int i){
		
		this.noteVal 	= s;
		this.beatVal 	= d;
		this.noteOnOff  = i;
		
	}
	
	// ############################################################
	// #					 GETTER METHODS						  #
	// ############################################################
	// Returns noteVal field
	public String getNoteVal() {
		return noteVal;
	}

	// Returns beatVal field
	public double getBeatVal() {
		return beatVal;
	}

	// Returns noteOnOff field
	public int getNoteOnOff() {
		return noteOnOff;
	}

	// Returns order field
	public int getOrder() {
		return order;
	}

	// Sets the order field to the value of the input parameter
	public void setOrder(int order) {
		this.order = order;
	}

}
