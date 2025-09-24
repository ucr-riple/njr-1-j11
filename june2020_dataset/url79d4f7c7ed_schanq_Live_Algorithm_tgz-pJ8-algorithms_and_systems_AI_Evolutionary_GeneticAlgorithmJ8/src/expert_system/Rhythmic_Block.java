// ##################################################################
// ##################################################################
// #	Rhythmic Block: Each type contains an arraylist of rhythmic #
// #    values that make up the rhythm for that rhythmic block.		#
// ##################################################################
// ##################################################################
package expert_system;

public enum Rhythmic_Block {
		
	// ##########################################################
	// #					   	  FIELDS						#
	// ##########################################################
	END(2),
	LASTNOTE(2),
	MINIM1(2),
	CROTCHETS1(1),
	CROTCHETS2(2),
	CROTCHET_SWUNG_T1(2),
	CROTCHET_SWUNG_T2(2),
	CROTCHET_SWUNG_T3(2),
	CROTCHET_SWUNG_T4(2),
	TRIPLET_CROTCHETS_FULL(2),
	TRIPLET_CROTCHETS_FIRST_OFF(2),
	TRIPLET_CROTCHETS_SECOND_OFF(2),
	TRIPLET_CROTCHETS_THIRD_OFF(2),
	QUAVERS2_FIRST_ON(1),
	QUAVERS2_SECOND_ON(1),
	QUAVERS2(1),
	QUAVERS4(2),
	QUAVERS4_FIRST_OFF(2),
	QUAVERS4_SECOND_OFF(2),
	QUAVERS4_THIRD_OFF(2),
	QUAVERS4_FORTH_OFF(2),
	QUAVERS4_FIRST_AND_SECOND_OFF(2),
	QUAVERS4_SECOND_AND_THIRD_OFF(2),
	QUAVERS4_THIRD_AND_FORTH_OFF(2),
	QUAVERS4_FIRST_AND_FORTH_OFF(2),
	QUAVERS4_FIRST_AND_THIRD_OFF(2),
	QUAVERS4_SECOND_AND_FORTH_OFF(2),
	QUAVERS_SWUNG_T1(1),
	QUAVERS_SWUNG_T2(1),
	QUAVERS_SWUNG_T3(1),
	QUAVERS_SWUNG_T4(1),
	TRIPLET_QUAVERS_FULL(1),
	TRIPLET_QUAVERS_FIRST_OFF(1),
	TRIPLET_QUAVERS_SECOND_OFF(1),
	TRIPLET_QUAVERS_THIRD_OFF(1),
	SEMI_QUAVERS4(1),
	SEMI_QUAVERS4_FIRST_OFF(1),
	SEMI_QUAVERS4_SECOND_OFF(1),
	SEMI_QUAVERS4_THIRD_OFF(1),
	SEMI_QUAVERS4_FORTH_OFF(1),
	SEMI_QUAVERS4_FIRST_AND_SECOND_OFF(1),
	SEMI_QUAVERS4_SECOND_AND_THIRD_OFF(1),
	SEMI_QUAVERS4_THIRD_AND_FORTH_OFF(1),
	SEMI_QUAVERS4_FIRST_AND_THIRD_OFF(1),
	SEMI_QUAVERS4_SECOND_AND_FORTH_OFF(1),
	SEMI_QUAVERS8(2),
	SEMI_QUAVERS8_FIRST_THIRD_FIFTH_AND_SEVENTH_OFF(2),
	SEMI_QUAVERS8_SECOND_FORTH_SIXTH_AND_EIGTH_OFF(2),
	SEMI_QUAVERS8_SECOND_THIRD_FIFTH_AND_SIXTH_OFF(2),
	SEMI_QUAVERS8_FIRST_SECOND_FORTH_AND_FIFTH_OFF(2),
	SEMI_QUAVERS8_SECOND_FIFTH_SEVENTH_AND_EIGTH_OFF(2),
	SEMI_QUAVERS8_THIRD_SIXTH_AND_EIGTH_OFF(2),
	SEMI_QUAVERS_SWUNG_T1(1),
	SEMI_QUAVERS_SWUNG_T2(1),
	SEMI_QUAVERS_SWUNG_T3(1),
	SEMI_QUAVERS_SWUNG_T4(1),
	TRIPLET_SEMI_QUAVERS_FULL(1),
	TRIPLET_SEMI_QUAVERS_VAR1(1),
	TRIPLET_SEMI_QUAVERS_VAR2(1),
	TRIPLET_SEMI_QUAVERS_VAR3(1),
	DEMI_SEMI_QUAVERS8(1),
	DEMI_SEMI_QUAVERS8_FIRST_THIRD_FIFTH_AND_SEVENTH_OFF(1),
	DEMI_SEMI_QUAVERS8_SECOND_FORTH_SIXTH_AND_EIGTH_OFF(1),
	DEMI_SEMI_QUAVERS8_SECOND_THIRD_FIFTH_AND_SIXTH_OFF(1),
	DEMI_SEMI_QUAVERS8_FIRST_SECOND_FORTH_AND_FIFTH_OFF(1),
	DEMI_SEMI_QUAVERS8_SECOND_FIFTH_SEVENTH_AND_EIGTH_OFF(1),
	DEMI_SEMI_QUAVERS8_THIRD_SIXTH_AND_EIGTH_OFF(1),
	TRIPLET_DEMI_SEMI_QUAVERS_FULL(1),
	TRIPLET_DEMI_SEMI_QUAVERS_VAR1(1),
	TRIPLET_DEMI_SEMI_QUAVERS_VAR2(1),
	TRIPLET_DEMI_SEMI_QUAVERS_VAR3(1),
	LATIN_V1(2),
	LATIN_V2(2),
	LATIN_V3(2),
	LATIN_V4(2),
	LATIN_V5(2),
	LATIN_V6(2);
	
	private int beatValue;
	Rhythmic_Value [] rhythmicValues;
	
	// ##########################################################
	// #					  CONSTRUCTORS						#
	// ##########################################################
	private Rhythmic_Block(int beatVal){
		this.setBeatValue(beatVal);
		this.initialise();
	}

	// ##########################################################
	// #					   	METHODS			   				#
	// ##########################################################
	// Returns beatValue field
	public int getBeatValue() {
		return beatValue;
	}

	// Sets the beatValue field to the value of the input parameter
	public void setBeatValue(int beatValue) {
		this.beatValue = beatValue;
	}
	
	// Returns rhythmicValues field
	public Rhythmic_Value[] getRhythmicValues() {
		return rhythmicValues;
	}

	// Sets the rhythmicValues field to the value of the input parameter
	public void setRhythmicValues(Rhythmic_Value[] rhythmicValues) {
		this.rhythmicValues = rhythmicValues;
	}
	
	// Initialises Each Rhythmic Block's rhythmicValues array with the appropriate Rhythmic Values
	public void initialise(){
		
		String name = this.name();
		if(name == "END"){
			rhythmicValues = new Rhythmic_Value [1];
			rhythmicValues[0] = Rhythmic_Value.MINIM_REST;
		}
		else if(name == "LASTNOTE"){
			rhythmicValues = new Rhythmic_Value [1];
			rhythmicValues[0] = Rhythmic_Value.MINIM;
		}
		else if(name == "MINIM1"){
			rhythmicValues = new Rhythmic_Value [1];
			rhythmicValues[0] = Rhythmic_Value.MINIM;
		}
		else if(name == "CROTCHETS1"){
			rhythmicValues = new Rhythmic_Value [1];
			rhythmicValues[0] = Rhythmic_Value.CROTCHET;
		}
		else if(name == "CROTCHETS2"){
			rhythmicValues = new Rhythmic_Value [2];
			rhythmicValues[0] = Rhythmic_Value.CROTCHET;
			rhythmicValues[1] = Rhythmic_Value.CROTCHET;
		}
		else if(name == "CROTCHET_SWUNG_T1"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.CROTCHET;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;
			rhythmicValues[2] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.DOTTED_SEMI_QUAVER;
			
		}
		else if(name == "CROTCHET_SWUNG_T2"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.CROTCHET;
			
		}
		else if(name == "CROTCHET_SWUNG_T3"){
			rhythmicValues = new Rhythmic_Value [5];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;
			rhythmicValues[4] = Rhythmic_Value.QUAVER;
			
		}
		else if(name == "CROTCHET_SWUNG_T4"){
			rhythmicValues = new Rhythmic_Value [6];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.DOTTED_SEMI_QUAVER;
			
		}
		else if(name == "TRIPLET_CROTCHETS_FULL"){
			rhythmicValues = new Rhythmic_Value [3];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_CROTCHET;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_CROTCHET;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_CROTCHET;

		}
		else if(name == "TRIPLET_CROTCHETS_FIRST_OFF"){
			rhythmicValues = new Rhythmic_Value [3];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_CROTCHET_REST;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_CROTCHET;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_CROTCHET;

		}
		else if(name == "TRIPLET_CROTCHETS_SECOND_OFF"){
			rhythmicValues = new Rhythmic_Value [3];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_CROTCHET;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_CROTCHET_REST;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_CROTCHET;
			
		}
		else if(name == "TRIPLET_CROTCHETS_THIRD_OFF"){
			rhythmicValues = new Rhythmic_Value [3];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_CROTCHET;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_CROTCHET;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_CROTCHET_REST;

		}
		else if(name == "QUAVERS2_FIRST_ON"){
			rhythmicValues = new Rhythmic_Value [2];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.QUAVER_REST;

		}
		else if(name == "QUAVERS2_SECOND_ON"){
			rhythmicValues = new Rhythmic_Value [2];
			rhythmicValues[0] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;

		}
		else if(name == "QUAVERS2"){
			rhythmicValues = new Rhythmic_Value [2];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;

		}
		else if(name == "QUAVERS4"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;
			rhythmicValues[2] = Rhythmic_Value.QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;

		}
		else if(name == "QUAVERS4_FIRST_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;
			rhythmicValues[2] = Rhythmic_Value.QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;

		}
		else if(name == "QUAVERS4_SECOND_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;

		}
		else if(name == "QUAVERS4_THIRD_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;
			rhythmicValues[2] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;

		}
		else if(name == "QUAVERS4_FORTH_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;
			rhythmicValues[2] = Rhythmic_Value.QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER_REST;

		}
		else if(name == "QUAVERS4_FIRST_AND_SECOND_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;

		}
		else if(name == "QUAVERS4_SECOND_AND_THIRD_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;

		}
		else if(name == "QUAVERS4_THIRD_AND_FORTH_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;
			rhythmicValues[2] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.QUAVER_REST;

		}
		else if(name == "QUAVERS4_FIRST_AND_FORTH_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;
			rhythmicValues[2] = Rhythmic_Value.QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER_REST;

		}
		else if(name == "QUAVERS4_FIRST_AND_THIRD_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.QUAVER;
			rhythmicValues[2] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;

		}
		else if(name == "QUAVERS4_SECOND_AND_FORTH_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER_REST;

		}
		else if(name == "QUAVERS_SWUNG_T1"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.HEMI_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.DOTTED_DEMI_SEMI_QUAVER;
			
		}
		else if(name == "QUAVERS_SWUNG_T2"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.HEMI_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;
			
		}
		else if(name == "QUAVERS_SWUNG_T3"){
			rhythmicValues = new Rhythmic_Value [5];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.HEMI_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.SEMI_QUAVER;
			
		}
		else if(name == "QUAVERS_SWUNG_T4"){
			rhythmicValues = new Rhythmic_Value [6];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.HEMI_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.HEMI_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.HEMI_DEMI_SEMI_QUAVER_REST;

		}
		else if(name == "TRIPLET_QUAVERS_FULL"){
			rhythmicValues = new Rhythmic_Value [3];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_QUAVER;
			
		}
		else if(name == "TRIPLET_QUAVERS_FIRST_OFF"){
			rhythmicValues = new Rhythmic_Value [3];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_QUAVER;
		}
		else if(name == "TRIPLET_QUAVERS_SECOND_OFF"){
			rhythmicValues = new Rhythmic_Value [3];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_QUAVER;
		}
		else if(name == "TRIPLET_QUAVERS_THIRD_OFF"){
			rhythmicValues = new Rhythmic_Value [3];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_QUAVER_REST;
		}
		else if(name == "SEMI_QUAVERS4"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			
		}
		else if(name == "SEMI_QUAVERS4_FIRST_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			
		}
		else if(name == "SEMI_QUAVERS4_SECOND_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			
		}
		else if(name == "SEMI_QUAVERS4_THIRD_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			
		}
		else if(name == "SEMI_QUAVERS4_FORTH_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER_REST;
			
		}
		else if(name == "SEMI_QUAVERS4_FIRST_AND_SECOND_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			
		}
		else if(name == "SEMI_QUAVERS4_SECOND_AND_THIRD_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			
		}
		else if(name == "SEMI_QUAVERS4_THIRD_AND_FORTH_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER_REST;
			
		}
		else if(name == "SEMI_QUAVERS4_FIRST_AND_THIRD_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			
		}
		else if(name == "SEMI_QUAVERS4_SECOND_AND_FORTH_OFF"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER_REST;
			
		}
		else if(name == "SEMI_QUAVERS8"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.SEMI_QUAVER;
		}
		else if(name == "SEMI_QUAVERS8_FIRST_THIRD_FIFTH_AND_SEVENTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[7] = Rhythmic_Value.SEMI_QUAVER;
		}
		else if(name == "SEMI_QUAVERS8_SECOND_FORTH_SIXTH_AND_EIGTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[4] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[6] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.SEMI_QUAVER_REST;
		}
		else if(name == "SEMI_QUAVERS8_SECOND_THIRD_FIFTH_AND_SIXTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[6] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.SEMI_QUAVER;
		}
		else if(name == "SEMI_QUAVERS8_FIRST_SECOND_FORTH_AND_FIFTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[4] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.SEMI_QUAVER;
		}
		else if(name == "SEMI_QUAVERS8_SECOND_FIFTH_SEVENTH_AND_EIGTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[7] = Rhythmic_Value.SEMI_QUAVER_REST;
		}
		else if(name == "SEMI_QUAVERS8_THIRD_SIXTH_AND_EIGTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[6] = Rhythmic_Value.SEMI_QUAVER_REST;
			rhythmicValues[7] = Rhythmic_Value.SEMI_QUAVER;
		}
		else if(name == "SEMI_QUAVERS_SWUNG_T1"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE_REST;
			rhythmicValues[3] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE_REST;
			rhythmicValues[7] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER;
		}
		else if(name == "SEMI_QUAVERS_SWUNG_T2"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE_REST;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE_REST;
			rhythmicValues[6] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.SEMI_QUAVER;
		}
		else if(name == "SEMI_QUAVERS_SWUNG_T3"){
			rhythmicValues = new Rhythmic_Value [10];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE_REST;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE_REST;
			rhythmicValues[7] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER;
			rhythmicValues[8] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[9] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
		}
		else if(name == "SEMI_QUAVERS_SWUNG_T4"){
			rhythmicValues = new Rhythmic_Value [12];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE_REST;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[4] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE_REST;
			rhythmicValues[5] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[7] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE;
			rhythmicValues[8] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER;
			rhythmicValues[9] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[10] = Rhythmic_Value.ONE_TWO_EIGHT_NOTE_REST;
			rhythmicValues[11] = Rhythmic_Value.DOTTED_HEMI_DEMI_SEMI_QUAVER_REST;
		}
		else if(name == "TRIPLET_SEMI_QUAVERS_FULL"){
			rhythmicValues = new Rhythmic_Value [6];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			
		}
		else if(name == "TRIPLET_SEMI_QUAVERS_VAR1"){
			rhythmicValues = new Rhythmic_Value [6];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_SEMI_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.TRIPLET_SEMI_QUAVER_REST;
			rhythmicValues[4] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
		}
		else if(name == "TRIPLET_SEMI_QUAVERS_VAR2"){
			rhythmicValues = new Rhythmic_Value [6];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.TRIPLET_SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
		}
		else if(name == "TRIPLET_SEMI_QUAVERS_VAR3"){
			rhythmicValues = new Rhythmic_Value [6];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.TRIPLET_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.TRIPLET_SEMI_QUAVER_REST;
		}
		else if(name == "DEMI_SEMI_QUAVERS8"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.DEMI_SEMI_QUAVER;
		}
		else if(name == "DEMI_SEMI_QUAVERS8_FIRST_THIRD_FIFTH_AND_SEVENTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[7] = Rhythmic_Value.DEMI_SEMI_QUAVER;
		}
		else if(name == "DEMI_SEMI_QUAVERS8_SECOND_FORTH_SIXTH_AND_EIGTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[6] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
		}
		else if(name == "DEMI_SEMI_QUAVERS8_SECOND_THIRD_FIFTH_AND_SIXTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[6] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.DEMI_SEMI_QUAVER;
		}
		else if(name == "DEMI_SEMI_QUAVERS8_FIRST_SECOND_FORTH_AND_FIFTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.DEMI_SEMI_QUAVER;
		}
		else if(name == "DEMI_SEMI_QUAVERS8_SECOND_FIFTH_SEVENTH_AND_EIGTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[7] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
		}
		else if(name == "DEMI_SEMI_QUAVERS8_THIRD_SIXTH_AND_EIGTH_OFF"){
			rhythmicValues = new Rhythmic_Value [8];
			rhythmicValues[0] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.DEMI_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[6] = Rhythmic_Value.DEMI_SEMI_QUAVER_REST;
			rhythmicValues[7] = Rhythmic_Value.DEMI_SEMI_QUAVER;
		}
		else if(name == "TRIPLET_DEMI_SEMI_QUAVERS_FULL"){
			rhythmicValues = new Rhythmic_Value [12];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[8] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[9] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[10] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[11] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			
		}
		else if(name == "TRIPLET_DEMI_SEMI_QUAVERS_VAR1"){
			rhythmicValues = new Rhythmic_Value [12];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[4] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[7] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[8] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[9] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[10] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[11] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
		}
		else if(name == "TRIPLET_DEMI_SEMI_QUAVERS_VAR2"){
			rhythmicValues = new Rhythmic_Value [12];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[5] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[6] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[8] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[9] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[10] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[11] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;

		}
		else if(name == "TRIPLET_DEMI_SEMI_QUAVERS_VAR3"){
			rhythmicValues = new Rhythmic_Value [12];
			rhythmicValues[0] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[3] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[4] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[5] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[6] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[7] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[8] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
			rhythmicValues[9] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[10] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER;
			rhythmicValues[11] = Rhythmic_Value.TRIPLET_DEMI_SEMI_QUAVER_REST;
		}
		else if(name == "LATIN_V1"){
			rhythmicValues = new Rhythmic_Value [3];
			rhythmicValues[0] = Rhythmic_Value.DOTTED_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DOTTED_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.QUAVER;
		}
		else if(name == "LATIN_V2"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.DOTTED_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DOTTED_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
		}
		else if(name == "LATIN_V3"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.DOTTED_QUAVER;
			rhythmicValues[1] = Rhythmic_Value.DOTTED_QUAVER_REST;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
		}
		else if(name == "LATIN_V4"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.DOTTED_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.DOTTED_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.SEMI_QUAVER;
		}
		else if(name == "LATIN_V5"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.DOTTED_QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;
		}
		else if(name == "LATIN_V6"){
			rhythmicValues = new Rhythmic_Value [4];
			rhythmicValues[0] = Rhythmic_Value.QUAVER_REST;
			rhythmicValues[1] = Rhythmic_Value.SEMI_QUAVER;
			rhythmicValues[2] = Rhythmic_Value.DOTTED_QUAVER;
			rhythmicValues[3] = Rhythmic_Value.QUAVER;
		}
	}
}
