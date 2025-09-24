// ##################################################################
// ##################################################################
// # Tonality Class: Used to store data about the current			#
// #	 	         tonality (Key and major/minor inclination). 	#
// #															    #	
// #	Stores the current tonality (eg C major), Its related keys	#
// #	and their related chord type. Each Chord type allows access	#
// #			 to the scales playable over that chord.			#
// ##################################################################
// ##################################################################
package expert_system;

public class Tonality {
	
	// ##########################################################
	// #					      FIELDS						#
	// ##########################################################

	public Key root;
	public Key [] relatedKeys;
	
	public int Major_Minor_Tonality;
	
	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################

	public Tonality(Key k, int i) {
		
		root = k;
		Major_Minor_Tonality = i;
		
		if(Major_Minor_Tonality == 1)
			root.setChord(Chord.MAJOR);
		else
			root.setChord(Chord.MINOR_DORIAN);
		
		this.setRelatedKeys();
	}

	// ##########################################################
	// #	METHODS: Getters/Setters for relatedKeys, 			#
	// #			 Major_Minor_Tonality, root, 				#
	// #			 relativeMajor_Minor						#
	// ##########################################################
	
	// Returns relatedKeys field
	public Key[] getRelatedKeys() {
		return relatedKeys;
	}
	// Sets relatedKeys field
	public void setRelatedKeys() {
		
		// Switch statement to determine whether its major or minor
		switch(Major_Minor_Tonality){
		
		// If Minor set the relatedKeys to the intervals in the Aeolian mode
		// (Pure minor), and set each of their Chord's to the chord related
		// to that interval - eg 5th -> Dominant 7th.
		case 0:
			Key [] temp = root.getAllKeys();
			
			try{
				relatedKeys = new Key[root.minorIntervals.length];
				for(int k = 0; k < root.getAllKeys().length; k++){
					if(temp[k] == root){
						int itr = 0;
						for(int i = 0; i < root.minorIntervals.length; i++){
							
							itr += root.minorIntervals[i];
							this.relatedKeys[i] = temp[k+itr];
							
						}
						break;	
					}	
				}
			}catch(Exception e){
				
				e.printStackTrace();
				
			}
			relatedKeys[0].setChord(Chord.MINOR_DORIAN);
			relatedKeys[1].setChord(Chord.HALF_DIMINISHED);
			relatedKeys[2].setChord(Chord.MAJOR);
			relatedKeys[3].setChord(Chord.MINOR_DORIAN);
			relatedKeys[4].setChord(Chord.MINOR_DORIAN);
			relatedKeys[5].setChord(Chord.MAJOR);
			relatedKeys[6].setChord(Chord.DOMINANT_7TH);
			
			break;
		// If Major set the relatedKeys to the intervals in the Ionian mode
		// (Major), and set each of their Chord's to the chord related
		// to that interval - eg 5th -> Dominant 7th.
		case 1:
			Key [] temp2 = root.getAllKeys();
			
			try{
				relatedKeys = new Key[root.majorIntervals.length];
				for(int k = 0; k < root.getAllKeys().length; k++){
					if(temp2[k] == root){
						int itr = 0;
						for(int i = 0; i < root.majorIntervals.length; i++){
							
							itr += root.majorIntervals[i];
							this.relatedKeys[i] = temp2[k+itr];
							
						}
						break;
					}
				}
			}catch(Exception e){
				
				e.printStackTrace();
				
			}
			relatedKeys[0].setChord(Chord.MAJOR);
			relatedKeys[1].setChord(Chord.MINOR_DORIAN);
			relatedKeys[2].setChord(Chord.MINOR_DORIAN);
			relatedKeys[3].setChord(Chord.MAJOR);
			relatedKeys[4].setChord(Chord.DOMINANT_7TH);
			relatedKeys[5].setChord(Chord.MINOR_DORIAN);
			relatedKeys[6].setChord(Chord.HALF_DIMINISHED);
			break;
		}
		
		
	}
	// Getter for root field
	public Key getRoot() {
		return root;
	}
	// Setter for root field
	public void setRoot(Key root) {
		this.root = root;
	}
	// Getter for Major_Minor_Tonality field
	public int getMajor_Minor_Tonality() {
		return Major_Minor_Tonality;
	}
	// Setter for Major_Minor_Tonality field
	public void setMajor_Minor_Tonality(int m) {
		Major_Minor_Tonality = m;
	}
	public String toString(){
		String s = "";
		if(this.Major_Minor_Tonality == 1)
			s = "MAJOR";
		else
			s = "MINOR";
		return "TONALITY ["+this.getRoot()+" "+s+"] ";
		
	}
	public static void main(String[] args) {
		
		Tonality t = new Tonality (Key.C, 1);
		t.setRelatedKeys();
		for(int i = 0; i < t.relatedKeys.length; i++){

			System.out.print(t.relatedKeys[i]+" "+t.relatedKeys[i].getChord()+", ");
	
		}
		System.out.println("\n");
		
		Tonality t2 = new Tonality (Key.A, 0);
		t2.setRelatedKeys();
		for(int i = 0; i < t2.relatedKeys.length; i++){

			System.out.print(t2.relatedKeys[i]+" "+t2.relatedKeys[i].getChord()+", ");
	
		}
	}		

}
