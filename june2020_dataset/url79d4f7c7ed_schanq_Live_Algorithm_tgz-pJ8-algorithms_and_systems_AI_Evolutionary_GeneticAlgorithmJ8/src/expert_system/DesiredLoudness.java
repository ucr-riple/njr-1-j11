// ##################################################################
// ##################################################################
// #	DesiredLoudness: Stores information about what dynamic		#
// #					 range the GA should being aiming towards	#
// ##################################################################
// ##################################################################
package expert_system;

import java.util.EnumSet;


public class DesiredLoudness {

	// ##########################################################
	// #					   	FIELDS							#
	// ##########################################################
	
	public Dynamic 		degreeOfLoudness;
	public Dynamic []	allDynamics; 
	public DynamicShape theShape;
	public int	   []	howCloseAreTheDynamics;
	
	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################
	public DesiredLoudness(Dynamic d) {
		
		this.degreeOfLoudness = d;
		this.initHowCloseAreDynamics();
	}
	public DesiredLoudness(Dynamic d, DynamicShape ds) {
		
		this.degreeOfLoudness = d;
		this.theShape = ds;
		this.initHowCloseAreDynamics();
	}
	
	// ##########################################################
	// #	METHODS: Getters/Setters for howCloseAreDynamics	#
	//				 field and a method for the initialisation	#
	//				 of the howCloseAreDynamics array			#
	// ##########################################################
	public void initHowCloseAreDynamics(){
		
		// For loops set up an array with all the dynamic values
		// and an array to store a fitness ID for how close each 
		// type is to the ideal - ie if PIANO is the desired 
		// type then the array would look like 
		// [6, 7, 8, 7, 6, 5, 4, 3] ->
		// [(PIANISSMO_POSSIBLE) 6, (PIANISSIMO) 7, (PIANO) 8, 
		//	(MEZZO_PIANO) 7, (MEZZO_FORTE) 6, (FORTE) 5, 
		//  (FORTISSIMO) 4, (FORTISSIMO_POSSIBLE) 3]
		
		int i = 0;
		int indexOfDegree = 0;
		allDynamics = new Dynamic[8];
		for (Dynamic d : EnumSet.allOf(Dynamic.class)){
			allDynamics[i] = d;
			i++;
		}
		
		i = 0;
		for(Dynamic d : this.allDynamics){
			if(d == this.degreeOfLoudness){
				indexOfDegree = i;
				break;
			}
			i++;
		}
		int f = 8;
		for(i = indexOfDegree; i >= 0; i--){
			this.allDynamics[i].setFitnessID(f);
			f--;	
		}
		f = 8;
		for(i = indexOfDegree; i < allDynamics.length; i++){
			this.allDynamics[i].setFitnessID(f);
			f--;
		}
		i = 0;
		int [] temp = new int [this.allDynamics.length];
		for(Dynamic d : this.allDynamics){
			temp[i] = d.getFitnessID();
			i++;
		}
		this.setHowCloseAreTheDynamics(temp);
		
	}
	// Returns getHowCloseAreTheDynamics field
	public int [] getHowCloseAreTheDynamics() {
		return howCloseAreTheDynamics;
	}
	// Sets the getHowCloseAreTheDynamics field to value of input parameter
	public void setHowCloseAreTheDynamics(int [] howCloseAreTheDynamics) {
		this.howCloseAreTheDynamics = howCloseAreTheDynamics;
	}
	// Returns theShape field
	public DynamicShape getTheShape() {
		return theShape;
	}
	// Sets the theShape field to  to value of input parameter
	public void setTheShape(DynamicShape theShape) {
		this.theShape = theShape;
	}
	// Returns a string representation of the Desired loudness
	public String toString(){
		
		return "DESIRED LOUDNESS [Dynamic: "+this.degreeOfLoudness+" , Dynamic Shape: "+this.theShape+"] ";
		
	}
}
