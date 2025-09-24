// ##################################################################
// ##################################################################
// # Note: Used by Phrase class to create a phrase: stores midi 	#
// # data needed by Max to output note through the noteout object.	#
// ##################################################################
// ##################################################################
package algorithms_and_systems_AI_Evolutionary;

public class Note {
	
	// ##########################################################
	// #					   	FIELDS							#
	// ##########################################################
	public int tone;
	public int velocity;
	public String noteVal;
	public String noteData;
	
	// ##########################################################
	// #					  CONSTRUCTOR						#
	// ##########################################################
	public Note(){}
	public Note(int t, int v, String s){
		
		this.tone = t;
		this.velocity = v;
		this.noteVal = s;
		
	}

	// Returns tone field
	public int getTone() {
		return tone;
	}

	// Sets the tone field to the value of the input parameter
	public void setTone(int tone) {
		this.tone = tone;
	}

	// Returns velocity field
	public int getVelocity() {
		return velocity;
	}

	// Sets the velocity field to the value of the input parameter
	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	// Returns noteVal field
	public String getNoteVal() {
		return noteVal;
	}

	// Sets the noteVal field to the value of the input parameter
	public void setNoteVal(String noteVal) {
		this.noteVal = noteVal;
	}

	// Returns noteData field
	public String getNoteData() {
		return noteData;
	}

	// Sets the noteData field to the value of the input parameter
	public void setNoteData() {
		this.noteData = ""+this.getTone()+" "+this.getVelocity()+" "+this.noteVal;
	}
	
}
