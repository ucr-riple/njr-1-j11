// ##################################################################
// ##################################################################
// # Feel Enum: 7 types to define rhythmic feels for responses		#						
// ##################################################################
// ##################################################################
package expert_system;

import java.util.ArrayList;


public enum Feel {
	
	// ##########################################################
	// #					 	  FIELDS						#
	// ##########################################################
	
	STRAIGHTWALK,
	SWUNGWALK,
	STRAIGHTSOLO,
	SWUNGSOLO,
	LATIN,
	LASTNOTE,
	END;
	
	public ArrayList <Rhythmic_Block> fitRhythms;
	
	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################
	private Feel(){
		fitRhythms = new ArrayList<Rhythmic_Block>();
		this.initialise();
	}
	// ##########################################################
	// #					   	 METHODS						#
	// ##########################################################
	
	// Returns fitRhythms field
	public ArrayList<Rhythmic_Block> getFitRhythms() {
		return fitRhythms;
	}
	
	// Adds the appropriate rhythmic blocks to the fitRhythms arraylist
	private void initialise(){
		
		String name = this.name();
		
		if(name == "END"){
			fitRhythms.add(Rhythmic_Block.END);
		}
		else if(name == "LASTNOTE"){
			fitRhythms.add(Rhythmic_Block.END);
			fitRhythms.add(Rhythmic_Block.LASTNOTE);
			
		}
		else if(name == "LATIN"){
			fitRhythms.add(Rhythmic_Block.MINIM1);
			fitRhythms.add(Rhythmic_Block.CROTCHETS1);
			fitRhythms.add(Rhythmic_Block.CROTCHETS2);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.LATIN_V1);
			fitRhythms.add(Rhythmic_Block.LATIN_V2);
			fitRhythms.add(Rhythmic_Block.LATIN_V3);
			fitRhythms.add(Rhythmic_Block.LATIN_V4);
			fitRhythms.add(Rhythmic_Block.LATIN_V5);
			fitRhythms.add(Rhythmic_Block.LATIN_V6);
			
		}
		else if(name == "SWUNGSOLO"){
			fitRhythms.add(Rhythmic_Block.CROTCHETS1);
			fitRhythms.add(Rhythmic_Block.QUAVERS2);
			fitRhythms.add(Rhythmic_Block.QUAVERS4);
			fitRhythms.add(Rhythmic_Block.QUAVERS2_FIRST_ON);
			fitRhythms.add(Rhythmic_Block.QUAVERS2_SECOND_ON);
			fitRhythms.add(Rhythmic_Block.QUAVERS_SWUNG_T1);
			fitRhythms.add(Rhythmic_Block.QUAVERS_SWUNG_T2);
			fitRhythms.add(Rhythmic_Block.QUAVERS_SWUNG_T3);
			fitRhythms.add(Rhythmic_Block.QUAVERS_SWUNG_T4);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS_SWUNG_T1);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS_SWUNG_T2);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS_SWUNG_T3);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS_SWUNG_T4);
			fitRhythms.add(Rhythmic_Block.TRIPLET_SEMI_QUAVERS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_SEMI_QUAVERS_VAR1);
			fitRhythms.add(Rhythmic_Block.TRIPLET_SEMI_QUAVERS_VAR2);
			fitRhythms.add(Rhythmic_Block.TRIPLET_SEMI_QUAVERS_VAR3);
			
		}
		else if(name == "STRAIGHTSOLO"){
			
			fitRhythms.add(Rhythmic_Block.CROTCHETS1);
			fitRhythms.add(Rhythmic_Block.QUAVERS2_FIRST_ON);
			fitRhythms.add(Rhythmic_Block.QUAVERS2_SECOND_ON);
			fitRhythms.add(Rhythmic_Block.QUAVERS2);
			fitRhythms.add(Rhythmic_Block.QUAVERS4);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FIRST_AND_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FIRST_AND_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FIRST_AND_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_SECOND_AND_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_SECOND_AND_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_THIRD_AND_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4_FIRST_AND_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4_FIRST_AND_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4_SECOND_AND_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4_SECOND_AND_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4_THIRD_AND_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS4_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS8);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS8_FIRST_SECOND_FORTH_AND_FIFTH_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS8_FIRST_THIRD_FIFTH_AND_SEVENTH_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS8_SECOND_FIFTH_SEVENTH_AND_EIGTH_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS8_SECOND_FORTH_SIXTH_AND_EIGTH_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS8_SECOND_THIRD_FIFTH_AND_SIXTH_OFF);
			fitRhythms.add(Rhythmic_Block.SEMI_QUAVERS8_THIRD_SIXTH_AND_EIGTH_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_SEMI_QUAVERS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_SEMI_QUAVERS_VAR1);
			fitRhythms.add(Rhythmic_Block.TRIPLET_SEMI_QUAVERS_VAR2);
			fitRhythms.add(Rhythmic_Block.TRIPLET_SEMI_QUAVERS_VAR3);
			fitRhythms.add(Rhythmic_Block.DEMI_SEMI_QUAVERS8);
			fitRhythms.add(Rhythmic_Block.DEMI_SEMI_QUAVERS8_FIRST_SECOND_FORTH_AND_FIFTH_OFF);
			fitRhythms.add(Rhythmic_Block.DEMI_SEMI_QUAVERS8_FIRST_THIRD_FIFTH_AND_SEVENTH_OFF);
			fitRhythms.add(Rhythmic_Block.DEMI_SEMI_QUAVERS8_SECOND_FIFTH_SEVENTH_AND_EIGTH_OFF);
			fitRhythms.add(Rhythmic_Block.DEMI_SEMI_QUAVERS8_SECOND_FORTH_SIXTH_AND_EIGTH_OFF);
			fitRhythms.add(Rhythmic_Block.DEMI_SEMI_QUAVERS8_SECOND_THIRD_FIFTH_AND_SIXTH_OFF);
			fitRhythms.add(Rhythmic_Block.DEMI_SEMI_QUAVERS8_THIRD_SIXTH_AND_EIGTH_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_DEMI_SEMI_QUAVERS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_DEMI_SEMI_QUAVERS_VAR1);
			fitRhythms.add(Rhythmic_Block.TRIPLET_DEMI_SEMI_QUAVERS_VAR2);
			fitRhythms.add(Rhythmic_Block.TRIPLET_DEMI_SEMI_QUAVERS_VAR3);
			
		}
		else if(name == "SWUNGWALK"){
			
			fitRhythms.add(Rhythmic_Block.MINIM1);
			fitRhythms.add(Rhythmic_Block.CROTCHETS1);
			fitRhythms.add(Rhythmic_Block.CROTCHETS2);
			fitRhythms.add(Rhythmic_Block.CROTCHET_SWUNG_T1);
			fitRhythms.add(Rhythmic_Block.CROTCHET_SWUNG_T2);
			fitRhythms.add(Rhythmic_Block.CROTCHET_SWUNG_T3);
			fitRhythms.add(Rhythmic_Block.CROTCHET_SWUNG_T4);
			fitRhythms.add(Rhythmic_Block.TRIPLET_CROTCHETS_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_CROTCHETS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_CROTCHETS_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_CROTCHETS_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS_SWUNG_T1);
			fitRhythms.add(Rhythmic_Block.QUAVERS_SWUNG_T2);
			fitRhythms.add(Rhythmic_Block.QUAVERS_SWUNG_T3);
			fitRhythms.add(Rhythmic_Block.QUAVERS_SWUNG_T4);
			fitRhythms.add(Rhythmic_Block.TRIPLET_DEMI_SEMI_QUAVERS_VAR3);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_THIRD_OFF);
				
		}
		else if(name == "STRAIGHTWALK"){
			
			fitRhythms.add(Rhythmic_Block.MINIM1);
			fitRhythms.add(Rhythmic_Block.CROTCHETS1);
			fitRhythms.add(Rhythmic_Block.CROTCHETS2);
			fitRhythms.add(Rhythmic_Block.TRIPLET_CROTCHETS_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_CROTCHETS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_CROTCHETS_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_CROTCHETS_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS2_FIRST_ON);
			fitRhythms.add(Rhythmic_Block.QUAVERS2_SECOND_ON);
			fitRhythms.add(Rhythmic_Block.QUAVERS2);
			fitRhythms.add(Rhythmic_Block.QUAVERS4);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FIRST_AND_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FIRST_AND_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FIRST_AND_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_SECOND_AND_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_SECOND_AND_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_THIRD_AND_FORTH_OFF);
			fitRhythms.add(Rhythmic_Block.QUAVERS4_THIRD_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FIRST_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_FULL);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_SECOND_OFF);
			fitRhythms.add(Rhythmic_Block.TRIPLET_QUAVERS_THIRD_OFF);
			
		}
	}
	// Prints the name of the feel type and its rhythmic blocks
	public void print(){
		
		System.out.println(this.name()+": ");
		for(Rhythmic_Block r : fitRhythms)
			System.out.println(r);
		
	}
}
