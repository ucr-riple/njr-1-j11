// ##################################################################
// ##################################################################
// # TargetChromosome: Stores the Genes that the Genetic Algorithm 	#
// # 					should eventually produce					#			
// ##################################################################
// ##################################################################
package algorithms_and_systems_AI_Evolutionary;

import expert_system.DesiredLoudness;
import expert_system.Feel;
import expert_system.Tonality;

public class TargetChromosome {
	
	// ##########################################################
	// #					     FIELDS					 	S	#
	// ##########################################################
	Tonality targetTonality;
	DesiredLoudness targetLoudness;
	Feel targetFeel;
	
	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################
	public TargetChromosome(Tonality t, DesiredLoudness dl){
		
		this.targetTonality = t;
		this.targetLoudness = dl;
		
	}
	public TargetChromosome(Tonality t, DesiredLoudness dl, Feel f){
		
		this.targetTonality = t;
		this.targetLoudness = dl;
		this.targetFeel 	= f; 

	}
	
	// ##########################################################
	// #					     METHODS 						#
	// ##########################################################
	
	// Returns targetTonality field
	public Tonality getTargetTonality() {
		return targetTonality;
	}
	// Sets the targetTonality field to the value of the input parameter
	public void setTargetTonality(Tonality targetTonality) {
		this.targetTonality = targetTonality;
	}
	// Returns targetLoudness field
	public DesiredLoudness getTargetLoudness() {
		return targetLoudness;
	}
	// Sets the targetLoudness field to to the value of the input parameter
	public void setTargetLoudness(DesiredLoudness targetLoudness) {
		this.targetLoudness = targetLoudness;
	}
	// Returns targetFeel field
	public Feel getTargetFeel() {
		return targetFeel;
	}
	// Sets the targetFeel field to to the value of the input parameter
	public void setTargetFeel(Feel targetFeel) {
		this.targetFeel = targetFeel;
	}
	// Prints out the fields of the target chromosome.
	public void print(){	
		System.out.println("############################################################################");
		System.out.println("############################################################################");
		System.out.println("                      THE TARGET CHROMOSOME'S GENES ARE: "+
						 "\n============================================================================\n"
						  +this.getTargetTonality().toString()+
				      "\n"+this.getTargetLoudness().toString()+
				      "\nFEEL ["+this.getTargetFeel()+"]");
		System.out.println("############################################################################");
		System.out.println("############################################################################");
	}
}
