// ##################################################################
// ##################################################################
// # Dynamic: Enum types that store the velocity range for a phrase #									
// ##################################################################
// ##################################################################
package expert_system;

public enum Dynamic {
	
	// ##############################################################
	// #						   FIELDS							#
	// ##############################################################
	PIANISSIMO_POSSIBLE (0 , 18),
	PIANISSIMO 			(12, 34), 
	PIANO 				(28, 50), 
	MEZZO_PIANO 		(44, 66), 
	MEZZO_FORTE			(60, 82), 
	FORTE 				(76, 98), 
	FORTISSIMO 			(92, 114),
	FORTISSIMO_POSSIBLE (108,127);
	
	private int minimumVelocity, maximumVelocity;
	private int fitnessID;
	
	private int [] velocityRange;
	
	// ##############################################################
	// #						 CONSTRUCTORS						#
	// ##############################################################
	private Dynamic(int min, int max){
		
		minimumVelocity = min;
		maximumVelocity = max;
		
		this.setVelocityRange();
		
	}
	// Returns the minimumVelocity field
	public int getMinimumVelocity() {
		return minimumVelocity;
	}
	// Sets the minimumVelocity field to the value of the input parameter
	public void setMinimumVelocity(int minimumVelocity) {
		this.minimumVelocity = minimumVelocity;
	}
	// Returns the maximumVelocity field
	public int getMaximumVelocity() {
		return maximumVelocity;
	}
	// Sets the maximumVelocity field to the value of the input parameter
	public void setMaximumVelocity(int maximumVelocity) {
		this.maximumVelocity = maximumVelocity;
	}
	// Returns the velocityRange array
	public int [] getVelocityRange(){	
		return velocityRange;
	}
	// Sets the velocityRange array by inputing the values begining with
	// the minimumVelocity field incrementing up to the maximumVelocity field
	// to the velocityRange array.
	public void setVelocityRange(){
		velocityRange = new int[maximumVelocity-minimumVelocity+1];
		for(int i = minimumVelocity; i < minimumVelocity+velocityRange.length; i++){
			velocityRange[i-minimumVelocity] = i;
		}
	}

	// Returns FitnessID field
	public int getFitnessID() {
		return fitnessID;
	}

	// Sets the FitnessID field to the value of the input parameter
	public void setFitnessID(int fitnessId) {
		this.fitnessID = fitnessId;
	}
	
}
