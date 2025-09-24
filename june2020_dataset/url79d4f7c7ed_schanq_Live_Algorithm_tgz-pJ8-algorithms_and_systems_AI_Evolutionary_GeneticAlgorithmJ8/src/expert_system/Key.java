// ############################################################
// ############################################################
// #  Enumerated Type Key: Stores the MIDI octaves for each   #
// #      of the 12 tones used in western popular music. 	  #
// # 	   When a key is instantiated its chord variable	  #
// #  is instantiated as a Chord.MAJOR because all the other  #
// # Chord types can be deduced from the majorIntervals of a  #
// #		   major (or its relative minor) key. 			  #
// ############################################################
// ############################################################

package expert_system;

import java.util.Arrays;
import java.util.EnumSet;

public enum Key {
	
	// ##########################################################
	// #					     FIELDS		  					#
	// ##########################################################
	
	C		( 0,12,24,36,48,60,72,84, 96,110,122,134,146),
	C_SHARP	( 1,13,25,37,49,61,73,85, 97,111,123,135,147),
	D 		( 2,14,26,38,50,62,74,86, 98,112,124,136,148),
	D_SHARP ( 3,15,27,39,51,63,75,87, 99,113,125,137,149),
	E		( 4,16,28,40,52,64,76,88,100,114,126,138,150),
	F 		( 5,17,29,41,53,65,77,89,101,115,127,139,151),
	F_SHARP	( 6,18,30,42,54,66,78,90,102,116,128,140,152),
	G 		( 7,19,31,43,55,67,79,91,103,117,129,141,153),
	G_SHARP ( 8,20,32,44,56,68,80,92,104,118,130,142,154),
	A		( 9,21,33,45,57,69,81,93,105,119,131,143,155),
	A_SHARP (10,22,34,46,58,70,82,94,106,120,132,144,156),
	B		(11,23,35,47,59,71,83,95,107,121,133,145,157);
	
	
	private final int octave0, octave1, octave2, 
					  octave3, octave4, octave5, 
					  octave6, octave7, octave8,
					  octave9, octave10, octave11, octave12;
	
	private Key relativeMinor;
	
	private static final Key [] allKeys;
	
	public int [] majorIntervals = {0,2,2,1,2,2,2};
	public int [] minorIntervals = {0,2,1,2,2,1,2};
	
	private Chord chord;
	
	// The allkeys array that stores all the key types for retrieval
	// in other classes has to be initialise in a static constructor
	// as until they have been created they cannot be assigned but 
	// because a static constructor is run at the end of a class 
	// it makes it possible to initialise the array.
	static{
		int i  = 0;
		allKeys = new Key[24];
		for (Key k : EnumSet.allOf(Key.class)){
			
			allKeys[i]    = k;
			allKeys[i+12] = k;
			i++;
		}
	}
	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################
	private Key(int oct0, int oct1, int oct2, 
			    int oct3, int oct4, int oct5,
			    int oct6, int oct7, int oct8, 
			    int oct9, int oct10,int oct11, int oct12){
		
		
		this.octave0 = oct0;
		this.octave1 = oct1;
		this.octave2 = oct2;
		this.octave3 = oct3;
		this.octave4 = oct4;
		this.octave5 = oct5;
		this.octave6 = oct6;
		this.octave7 = oct7;
		this.octave8 = oct8;
		this.octave9 = oct9;
		this.octave10= oct10; 
		this.octave11 = oct11;
		this.octave12 = oct12;
		
		
	}
	// Returns an array containing all the octave fields
	public int [] getOctaves(){
		
		int [] temp = {octave0,octave1,octave2,octave3,
					   octave4,octave5,octave6,octave7,
					   octave8,octave9,octave10, octave11, octave12};
		return temp;
		
	}
	// Returns relativeMinor field
	public Key getRelativeMinor(){
		
		return this.relativeMinor;
		
	}
	// Set the relative minor of each key according to the name
	// of each enum as using the enum itself doesn't work unless
	// it has already been initialised so encapsulating this method 
	// in the constructor is problematic unless done this way.
	public void setRelativeMinor(){
		
		String name = this.name();
		
		if(name == "C"){
		    this.relativeMinor = Key.A;
		}
		else if(name == "C_SHARP"){
			this.relativeMinor = Key.A_SHARP;
			
		}
		else if(name == "D"){
			this.relativeMinor = Key.B;
			
		}
		else if(name == "D_SHARP"){
			this.relativeMinor = Key.C;
		}
		else if(name == "E"){
			this.relativeMinor = Key.C_SHARP;
		}
		else if(name == "F"){
			this.relativeMinor = Key.D;
		}
		else if(name == "F_SHARP"){
			this.relativeMinor = Key.D_SHARP;
		}
		else if(name == "G"){
			this.relativeMinor = Key.E;
		}
		else if(name == "G_SHARP"){
			this.relativeMinor = Key.F;
		}
		else if(name == "A"){ 
			this.relativeMinor = Key.F_SHARP;
		}
		else if(name == "A_SHARP"){
			this.relativeMinor = Key.G;
		}
		else if(name == "B"){
			this.relativeMinor = Key.G_SHARP;
		}
	}
	// Returns allKeys field
	public Key [] getAllKeys() {
		return allKeys;
	}
	// Returns chord field
	public Chord getChord() {
		return chord;
	}
	// Sets the chord field to the value of the input parameter
	public void setChord(Chord chord) {
		this.chord = chord;
		//this.chord.
	}

	// Returns octave0 field
	public int getOctave0() {
		return octave0;
	}
	//@SuppressWarnings("unused")
	public void print(){
		
		System.out.println("Key's Octaves "+Arrays.toString(this.getOctaves()));	
		
	}	
}
