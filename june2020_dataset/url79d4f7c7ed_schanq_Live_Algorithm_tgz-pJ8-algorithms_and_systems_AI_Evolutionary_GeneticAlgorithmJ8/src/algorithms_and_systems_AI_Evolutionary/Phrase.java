// ##################################################################
// ##################################################################
// # Phrase: Based on a chromosome's genes it generates a musical 	#
// # 							phrase								#
// ##################################################################
// ##################################################################
package algorithms_and_systems_AI_Evolutionary;

import java.util.List;
import java.util.ArrayList;

import expert_system.Dynamic;
import expert_system.DynamicShape;
import expert_system.Key;
import expert_system.Rhythmic_Block;
import expert_system.Rhythmic_Value;
import expert_system.Scale;


public class Phrase {
	
	// ##########################################################
	// #					      FIELDS						#
	// ##########################################################
	
	public DynamicShape theShape;
	
	public List <Rhythmic_Block> rhythm;
	
	public int [] availableMidiNoteValues;
	public int [] availableMidiVelocityValues;
	
	public Note [] musicalPhrase;
	
	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################

	public Phrase(int min, int max, Scale s, Dynamic d, DynamicShape ds, ArrayList <Rhythmic_Block> arr) {
		
		this.theShape = ds;
		this.availableMidiNoteValues = s.getScaleInRange(min, max);
		this.availableMidiVelocityValues = d.getVelocityRange();
		this.rhythm = arr;
		this.generatePhrase();
	}

	// Returns musicalPhrase field
	public Note[] getMusicalPhrase() {
		return musicalPhrase;
	}

	// ##########################################################
	// #						 METHODS	 					#
	// ##########################################################
	
	// Generates Musical phrase
	public void generatePhrase(){
	
		// Variable for the storage of amount of rhythmic 
		// Values contained in the rhythmic blocks
		int l = 0;
		// An iterator 
		int i = 0;
		
		// Count how many rhythmic values are stored in the 
		// rhythmic blocks
		for(Rhythmic_Block r: rhythm){
			l += r.getRhythmicValues().length;
		}
		// Initialize the musical phrase array
		musicalPhrase = new Note[l];
		
		// Create an array storing all the rhythmic values 
		// so the notes can have their note values initialized
		Rhythmic_Value [] rv = new Rhythmic_Value[l];
		
		for(Rhythmic_Block r: rhythm){
			Rhythmic_Value [] temp = r.getRhythmicValues();
			for(int j = 0; j < temp.length; j++){
				rv[i] = temp[j];
				i++;
			}
		}
		
		// Fill Notes with tones
		for (i = 0; i < musicalPhrase.length; i++){
			Note n = new Note();
			n.setTone(availableMidiNoteValues[(int)Math.round(Math.random()*(availableMidiNoteValues.length-1))]);
			musicalPhrase[i] = n;
			
		}
		
		// Fill Note Values with the Strings of the rv array
		for (i = 0; i < musicalPhrase.length; i++){
			musicalPhrase[i].setNoteVal(rv[i].getNoteVal());
			
		}
		
		// Fill Velocities with appropriate values according to the Dynamic and 
		// the Dynamic shape
		switch(theShape){
		
		// If Sporadic just select random values from 
		// the available midi velocity values array
		case SPORADIC:
			for (i = 0; i < musicalPhrase.length; i++){
				if(rv[i].getNoteOnOff() == 1){
					musicalPhrase[i].setVelocity(availableMidiVelocityValues[(int)Math.round(Math.random()*(availableMidiVelocityValues.length-1))]);
				}
				else{
					musicalPhrase[i].setVelocity(0);
				}
			}
			break;
		// If Crescendo then create a variable to store how much to 
		// increment by, then initialise it by checking if
		// availableMidiVelocityValues array is longer than the musicalPhrase
		// array's length. If true then divide it by the length of the musical phrase
		// array (else just set to 1) and set each notes velocity to the value stored at the 
		// index of the availableMidiVelocityValues array that the summed increment 
		// variable has landed on.  
		case CRESCENDO:
			int inc = 0;
			if(musicalPhrase.length < availableMidiVelocityValues.length)
				inc = (int)Math.round((float)availableMidiVelocityValues.length/(float)musicalPhrase.length);
			else{
				inc = 1;
			}
			int sum = 0 ;
			for(i = 0; i < musicalPhrase.length; i++){
				if(rv[i].getNoteOnOff() == 1){
					musicalPhrase[i].setVelocity(availableMidiVelocityValues[sum]);
					if(sum < availableMidiVelocityValues.length-1)
						sum+=inc;
					else
						sum = availableMidiVelocityValues.length-1;
				}
			}
			break;
		
		// Decrescendo works exactly the same as the crescendo case except 
		// by decrementing through the availableMidiVelocityValues array
		case DECRESCENDO:
			int decr = 0;
			if(musicalPhrase.length < availableMidiVelocityValues.length)
				decr = (int)Math.round((float)availableMidiVelocityValues.length/(float)musicalPhrase.length);
			else{
				decr = 1;
			}
			sum = availableMidiVelocityValues.length-1;
			for(i = 0; i < musicalPhrase.length; i++){
				if(rv[i].getNoteOnOff() == 1){
					musicalPhrase[i].setVelocity(availableMidiVelocityValues[sum]);
					if(sum > 0){
						sum-=decr;
					}
					else
						sum = 0;
				}
			}
			break;
			
		// If On Beat_Accents sum the beat values of each rhythmic value, if a multiple of 
		// 1 then set the velocity to a one of the top three values in the 
		// availableMidiVelocityValues array, else set it to on of the values less than the 
		// top three
		case ON_BEAT_ACCENTS:
			double suma = 0;
			for(i = 0; i < musicalPhrase.length; i++){
				if(rv[i].getNoteOnOff() == 1){
		            if((suma == 0) || (suma <= 1 && suma >= 0.95) 
		             ||(suma == 1) || (suma <= 2 && suma >= 1.95) 
		             ||(suma == 2) || (suma <= 3 && suma >= 2.95) 
		             ||(suma == 3)) {
		            	
						int rand = (int)Math.round(Math.random()*3);
						musicalPhrase[i].setVelocity(availableMidiVelocityValues[(availableMidiVelocityValues.length-1)-rand]);
					}
					else{
						musicalPhrase[i].setVelocity(availableMidiVelocityValues[(int)Math.round(Math.random()*((availableMidiVelocityValues.length-5)))]);
					}
					suma += rv[i].getBeatVal();
				}
			}
			break;
			
		// Off_Beat_Accents works the same as On_Beat_Accents except for 0.5*(3,5,7)
		case OFF_BEAT_ACCENTS:
			suma = 0;
			for(i = 0; i < musicalPhrase.length; i++){
				if(rv[i].getNoteOnOff() == 1){
					
		            if((suma == 0.5) || (suma <= 0.5 && suma >= 1.45) 
		             ||(suma == 1.5) || (suma <= 1.5 && suma >= 2.45) 
		             ||(suma == 2.5) || (suma <= 2.5 && suma >= 3.45) 
		             ||(suma == 3.5)) {
		            	
						int rand = (int)Math.round(Math.random()*3);
						musicalPhrase[i].setVelocity(availableMidiVelocityValues[(availableMidiVelocityValues.length-1)-rand]);
					}
					else{
						musicalPhrase[i].setVelocity(availableMidiVelocityValues[(int)Math.round(Math.random()*((availableMidiVelocityValues.length-5)))]);
					}
					suma += rv[i].getBeatVal();
				}
			}
			break;
		}
	}
	// Returns a String version of the phrase in a way that Max msp's coll 
	// objects understand - 
	public String toString(){
		String s = "clear,\n";
		for(Note n : musicalPhrase)
			n.setNoteData();
		for(int i = 0; i < this.musicalPhrase.length; i++){
			s += "insert "+i+" "+this.musicalPhrase[i].getNoteData()+",\n";
		}
		return s;
	}
	// Prints Musical phrase
	public void print(){
		System.out.print("Phrase: ");
		for(Note n : musicalPhrase){
			n.setNoteData();
			System.out.print("["+n.noteData+"] ");
		}
		System.out.println("");
	}
	public static void main(String[] args) {
		ArrayList <Rhythmic_Block> al = new ArrayList<Rhythmic_Block>();
		al.add(Rhythmic_Block.QUAVERS4);
		al.add(Rhythmic_Block.CROTCHETS1);
		al.add(Rhythmic_Block.TRIPLET_QUAVERS_FIRST_OFF);
		Scale s = Scale.CPARKER_MAJOR_FLAT6;
		s.initialise(Key.A);
		Phrase p = new Phrase(2,5,s,Dynamic.MEZZO_FORTE, DynamicShape.OFF_BEAT_ACCENTS, al);
		p.print();
		System.out.println(p.toString());
	}

}
