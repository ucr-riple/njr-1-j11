// ############################################################
// ############################################################
// #    Enumerated type Scale: Each scale described in the    # 
// # Charlie Parker Omnibook's Scale Syllabus is represented  #
// #   as an Enum here. Each Scale is categorized by harmony  #
// # (according to the scale syllabus) and contains the step  #  
// #   sizes (interval) necessary to reach the next octave.	  #
// #														  #
// # It also has methods for scale construction based on key  # 
// # across the entire midi range and for a specified         #
// # 			    (octave	to octave) range.				  #
// ############################################################
// ############################################################
package expert_system;

public enum Scale {
	
	//#########################################################
	//# 					FIELDS							  #
	//#########################################################
	
	// W W H W W W H    (CAT: MAJOR)
	MAJOR 						   (0,2,2,1,2,2,2), 
	
	// W W H W W H W    (CAT: DOMINANT 7TH)
	DOMINANT_7TH 				   (0,2,2,1,2,2,1), 
	
	// W H W W W H W    (CAT: MINOR DORIAN)
	MINOR_DORIAN				   (0,2,1,2,2,2,1),
	
	// W W W H W W H    (CAT: MAJOR)
	LYDIAN						   (0,2,2,2,1,2,2), 
	
	// W W W W H W H    (CAT: MAJOR)
	LYDIAN_AUGMENTED			   (0,2,2,2,2,1,2),
	
	// W W W H W H W    (CAT: DOMINANT 7TH)
	LYDIAN_DOMINANT				   (0,2,2,2,1,2,1), 
	
	//-3 H -3 H -3 H    (CAT: MAJOR)
	AUGMENTED					   (0,3,1,3,1,3),   
	
	//-3 W H H -3 W     (CAT: MAJOR, DOMINANT 7TH, MINOR DORIAN)
	BLUES						   (0,3,2,1,1,3),   
	
	// W W H W H W W    (CAT: DOMINANT 7TH)
	HINDU						   (0,2,2,1,2,1,2), 
	
	// W W H W H -3 H   (CAT: MAJOR)
	CPARKER_MAJOR_FLAT6			   (0,2,2,1,2,1,3), 
	
	// W H W W H W W    (CAT: MINOR DORIAN)
	PURE_MINOR					   (0,2,1,2,2,1,2), 
	
	// Including both 7th's so both ascending 
	// and descending melodies are possible
	// W H W W W H H    (CAT: MINOR DORIAN)
	MELODIC_MINOR				   (0,2,1,2,2,2,1), 
	
	// W H W W H -3 W   (CAT: MINOR DORIAN)
	HARMONIC_MINOR				   (0,2,1,2,2,1,3),
	
	// H W W W H W W    (CAT: MINOR DORIAN)
	PHRYGIAN					   (0,1,2,2,2,1,2),
	
	// W W W W W W	    (CAT: DOMINANT 7TH)
	WHOLE_TONE					   (0,2,2,2,2,2),   
	
	// H W W H W W W    (CAT: HALF DIMINISHED)
	HALF_DIMINISHED_LOCRIAN		   (0,1,2,2,1,2,2), 
	
	// W H W H W W W    (CAT: HALF DIMINISHED)
	HALF_DIMINISHED_LOCRIAN_SHARP2 (0,2,1,2,1,2,2), 
	
	// W H W H W H W H  (CAT: DIMINISHED)
	DIMINISHED					   (0,2,1,2,1,2,1,2),
	
	// H W H W H W H W  (CAT: MAJOR, DOMINANT7TH, MINOR DORIAN)
	DIMINISHED_HALFSTEP_FIRST      (0,1,2,1,2,1,2,1),
	
	// H W H W W W W    (CAT: DOMINANT 7TH)
	DIMINISHED_WHOLE_TONE		   (0,1,2,1,2,2,2); 
	
	private int [] intervals;
	private int [] wholeScale;
	private Key key;
	private int [] octaves;
	
	//#########################################################
	//# 				   CONSTRUCTORS						  #
	//#########################################################
	
	//	Root + 5 Interval Constructor (Whole Tone)
	private Scale(int root, int interval2, int interval3, 
				  int interval4, int interval5, int interval6){
		
		int [] temp = {root, interval2, interval3, 
				  	   interval4, interval5, interval6};
		
		this.setIntervals(temp);
		
	}
	//	Root + 6 Interval Constructor (Most Scales)
	private Scale(int root, int interval2, int interval3, 
				  int interval4, int interval5, int interval6, 
				  int interval7){
		
		int [] temp = {root, interval2, interval3, 
			  	   interval4, interval5, interval6, interval7};
	
		this.setIntervals(temp);
	
	}
	//	Root + 7 Note Constructor (Diminished Scale)
	private Scale(int root, int interval2, int interval3, 
				  int interval4, int interval5, int interval6, 
				  int interval7, int interval8){
	
		int [] temp = {root, interval2, interval3, 
			  	   	   interval4, interval5, interval6,
			  	   	   interval7, interval8};
	
		this.setIntervals(temp);
	
	}
	
	//#########################################################
	//#GETTERS/SETTERS FOR INTERVALS/WHOLESCALE/OCTAVES/SCALE/#
	//# KEY IN THE RANGE OF, THE FOR THE CONSTRUCTION OF THE  #
	//# WHOLE SCALE (ACROSS THE MIDI RANGE) AND AN      	  #
	//#        			INITIALISATION METHOD				  #
	//#########################################################
	
	// Intervals Getter/Setter
	public int [] getIntervals() {
		return intervals;
	}
	public void setIntervals(int [] intervals) {
		this.intervals = intervals;
	}
	
	
	// Whole Scale Getter/Setter
	public int[] getWholeScale() {
		return wholeScale;
	}
	public void setWholeScale(int[] wholeScale) {
		this.wholeScale = wholeScale;
	}
	
	
	// Key Getter/Setter
	public void setKey(Key key) {
		this.key = key;
	}
	public Key getKey() {
		return key;
	}
	
	
	// Octaves Getter/Setter
	public void setOctaves(int [] octaves) {
		this.octaves = octaves;
	}
	public int [] getOctaves() {
		return octaves;
	}
	
	// Initialisation method - encapsulation of the 
	// necessary methods for the setup of a Scale
	public void initialise(Key k){
		this.setKey(k);
		this.setOctaves(this.getKey().getOctaves());
		this.constructScale();
	}
	// Construct scale method
	public void constructScale(){
		
		// Temporary scale to store a 13 octaves of the scale, 
		// obviously there are only 11 octaves but this means
		// that the scale can be constructed from a negative octave:
		// The first octave (midi octave 0) will have the lower 
		// intervals of the scale before the first root - the first
		// B is midi note 11, but the major scale (from index 0) is 
		// 1, 3, 4, 6, 8, 10, 11 ... 
		int [] temp = new int [((octaves.length)*(intervals.length))];
		
		// The array that will the wholeScale variable will clone
		// - values between 0 - 127. 
		int [] theScale = new int [((11)*(intervals.length))];
		
		// Iterator for temporary array and note value (where the 
		// step sizes are summed to create the scale's intervals) 
		int itr = 0;
		int note = 0;
		
		// Fill the temp array with values from a negative root (eg in 
		// the Key of B -11) to a value above 127
		for(int i = 0; i < octaves.length; i++){
			note = (octaves[i])-12;
			for(int j = 0; j < intervals.length; j++){
				
				note += intervals[j]; 
				temp[itr] = note;
				
				itr++;
			}
		}
		
		itr = 0;
		
		// Iterate through the temp array, and add all the values,
		// between 0 -> 127 to the theScale array
		for(int i = 0; i < temp.length; i++){
			
			if(temp[i] >= 0 && temp[i] <= 127){
				theScale[itr] = temp[i];
				itr++;
			}
			
		}
		// Set the instance wholeScale field to the theScale array
		this.setWholeScale(theScale);
	}
	
	// Method for retrieving only a portion of the scale - 
	// for mimicking ranged instruments (not completely accurate
	// to instrumental ranges but nice for the purpose of of an
	// Accompanist - mean GUI can allow the human performer to 
	// select the range of their accompanist - if a guitarist
	// maybe they would prefer the Live algorithm to be in a bassist
	// range, for example.)
	public int [] getScaleInRange(int minOctave, int maxOctave){
		
		// Variables for setting the minimum midi range for
		// the scale, eg B octaves 0->3 = 11 -> 49
		int lowOct 	   = 0;
		int highOct    = 0;
		
		// Variables for the storage of the minimum/maximum 
		// indices to count between in the wholeScale array 
		int startIndex = 0;
		int endIndex   = 0;
		
		// Iterator for the loops
		int i 		   = 0;
		
		// Set the low and high octave variables by iterating
		// through the octave array and finding the index
		// that matches. If the minOctave method input 
		// parameter is -1 () ie the user wants the lowest
		// interval in the scale up to the maxOctave value
		// set the minOctave variable to the value at index 0
		// of the wholeScale array.
		for(i = 0; i < this.getOctaves().length; i++){
			if(minOctave == -1)
				lowOct = wholeScale[0];
			if(i == minOctave)
				lowOct = octaves[i];
			
			else if(i == maxOctave)
				highOct = octaves[i];
		}
		
		// Set the Start/end index variables to the index
		// that matches the low/high octave variables
		for(i = 0; i < wholeScale.length; i++){
			
			if(this.wholeScale[i] == lowOct)
				startIndex = i;
			
			else if(this.wholeScale[i] == highOct)
				endIndex = i;
			
		}
		
		// Create a new integer array for the storage of 
		// constrained scale, set its length to the distance
		// between the index variables+1 so that the maxOctave is 
		// the final note in the array.
		int [] returnable = new int [((endIndex-startIndex)+1)];
		
		for(i = startIndex; i <= endIndex; i++){
			
			returnable[i-startIndex] = wholeScale[i];
			
		}
		
		return returnable;
		
	}
	
	
	

}
