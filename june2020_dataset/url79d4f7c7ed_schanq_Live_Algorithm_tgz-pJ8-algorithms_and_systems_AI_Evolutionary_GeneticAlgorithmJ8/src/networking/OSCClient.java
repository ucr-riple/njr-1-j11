// ##################################################################
// ##################################################################
// # OSCClient: code based on oscP5 examples by andreas schlegel	#
// # Receives incoming messages from a UDP send object in Max and	#
// # submits the data to the fuzzy system and genetic algorithm for	#
// # analysis and generation of a response.							#
// ##################################################################
// ##################################################################
package networking;

import java.util.Arrays;

import algorithms_and_systems_AI_Evolutionary.Chromosome;
import algorithms_and_systems_AI_Evolutionary.FuzzySystem;
import algorithms_and_systems_AI_Evolutionary.GeneticAlgorithm;
import algorithms_and_systems_AI_Evolutionary.TargetChromosome;
import oscP5.OscMessage;
import oscP5.OscP5;
import netP5.NetAddress;

public class OSCClient {

	// ##########################################################
	// #						FIELDS							#
	// ##########################################################
	public OscP5 oscP5;
	public NetAddress myRemoteLocation;
	
	int minimumRange, maximumRange;
	double BPM;
	
	public int [] noteValues;
	public int [] velocityValues;
	public double [] millisValues;
	
	public boolean 	areTheNotesStored = true,
					areTheVelocitiesStored = true, 
					areTheMillisStored = true, 
					isTheRangeSet = true, 
					isTheBPMSet = true, 
					readyToGo = true;
	
	public FuzzySystem 		theFuzzySystem;
	public GeneticAlgorithm theGeneticAlgorithm;
	
	String response;
	
	// ##########################################################
	// #					  CONSTRUCTOR						#
	// ##########################################################
	public OSCClient() {

		int port 				= 59998;
		oscP5 					= new OscP5(this, port);
		
		noteValues				= new int [1];
		velocityValues 			= new int [1];
		millisValues 			= new double [1];
		
		areTheNotesStored 		= false;
		areTheVelocitiesStored	= false;
		areTheMillisStored		= false;
		isTheRangeSet			= false;
		isTheBPMSet				= false;
		readyToGo				= false;
		
		theFuzzySystem = new FuzzySystem();
		
		response = "";
		
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// ##########################################################
	// #						METHODS							#
	// ##########################################################
	void oscEvent(OscMessage message) {
		
		// check if theOscMessage has the address pattern we are looking for
		if (message.checkAddrPattern("thelist")) {
			
			// Store arguments in an array
			Object [] args = message.arguments();
			
			// Store the String type in a variable type to test what 
			String type = (String) args[0];
			
			// Create and initialize an ID variable for the below switch statement
			int typeID  = 0;
			if(type.compareTo("notes") == 0)
				typeID  = 0;
			else if(type.compareTo("vels") == 0)
				typeID	= 1;
			else if(type.compareTo("millis") == 0)
				typeID = 2;
			else if(type.compareTo("range") == 0)
				typeID = 3;
			else if(type.compareTo("BPM") == 0)
				typeID = 4;
			else if(type.compareTo("bang") == 0)
				typeID = 5;
			
			// Runs initialisation code appropriate to the type of data input:
			// initialises the array with the data associated with it and sets
			// the corresponding boolean to true. 
			switch(typeID){
			case 0:
				int [] tempns = new int [args.length-1];
				for(int i = 0; i < tempns.length; i++)
					tempns[i] = Integer.parseInt(args[i+1].toString());
				
				this.setNoteValues(tempns);
				this.setAreTheNotesStored(true);
				
				break;
			
			case 1:
				int [] tempvs = new int [args.length-1];
				for(int i = 0; i < tempvs.length; i++)
					tempvs[i] = Integer.parseInt(args[i+1].toString());
				
				this.setVelocityValues(tempvs);
				this.setAreTheVelocitiesStored(true);
				break;
			
			case 2:
				double [] tempms = new double [args.length-1];
				for(int i = 0; i < tempms.length; i++)
					tempms[i] = Double.parseDouble(args[i+1].toString());
				
				this.setMillisValues(tempms);
				this.setAreTheMillisStored(true);
				break;
				
			case 3:
				
				this.setMinimumRange(message.get(1).intValue());
				this.setMaximumRange(message.get(2).intValue());
				this.setTheRangeSet(true);
				break;
				
			case 4:
				this.setBPM((double)message.get(1).floatValue());
				this.setTheBPMSet(true);
				break;
			
			// In the case of the input being a bang message flush the response and 
			// set all the booleans to false.
			case 5:
				this.setFalse();
				new OSCServer(this.response,59999);
				
			}
			
			// If all the data necessary to run the AI systems have been received
			// set the readyToGo boolean to true
			if(areTheNotesStored && 
			   areTheVelocitiesStored &&
			   areTheMillisStored &&
			   isTheRangeSet &&
			   isTheBPMSet){
				readyToGo = true;
			}
			
			if(readyToGo){
				// Determine the target chromosome
				TargetChromosome theTarget 	= this.theFuzzySystem.determineTarget(this.getNoteValues(), 
																				  this.getVelocityValues(),
																				  this.getMillisValues(), 
																				  this.getBPM());
				// Initialise the Genetic Algorithm and reproduce the population until a near perfect chromosome is produced 
				this.theGeneticAlgorithm 	= new GeneticAlgorithm(theTarget, this.getMinimumRange(), this.getMaximumRange());
				Chromosome c 				= this.theGeneticAlgorithm.produceBeauIdeal();
				
				// Store the phrase in a string format to be sent to max msp
				response					= c.getPhrase().toString();
				
				
			}
			return;
			
		}
		
	}
	// Print the input parameters received from Max
	public void printParameters(){
		System.out.println("Input parameters are:");
		System.out.println("Notes: "+Arrays.toString(this.getNoteValues())+" \nVelocities: "
									+Arrays.toString(this.getVelocityValues())+" \nMillisecond Intervals: "
									+Arrays.toString(this.getMillisValues())+" \nRange: "
									+this.getMinimumRange()+" "+this.getMaximumRange()
									+"\nBPM: "+this.getBPM());
	}
	public static void println(String s) {
		print(s + "\n");
	}

	public static void print(String s) {
		System.out.print(s);
	}
	
	public void setFalse(){
		this.setAreTheMillisStored(false);
		this.setAreTheNotesStored(false);
		this.setAreTheVelocitiesStored(false);
		this.setTheBPMSet(false);
		this.setTheRangeSet(false);
		this.setReadyToGo(false);
	}
	// ==================================================================
	// =				Getters and Setters for fields					=
	// ==================================================================
	

	// Returns minimumRange field
	public int getMinimumRange() {
		return minimumRange;
	}

	// Sets the minimumRange field to to the value of the input parameter
	public void setMinimumRange(int minimumRange) {
		this.minimumRange = minimumRange;
	}

	// Returns maximumRange field
	public int getMaximumRange() {
		return maximumRange;
	}

	// Sets the maximumRange field to to the value of the input parameter
	public void setMaximumRange(int maximumRange) {
		this.maximumRange = maximumRange;
	}

	// Returns BPM field
	public double getBPM() {
		return BPM;
	}

	// Sets the BPM field to to the value of the input parameter
	public void setBPM(double bPM) {
		BPM = bPM;
	}

	// Returns noteValues field
	public int[] getNoteValues() {
		return noteValues;
	}

	// Sets the noteValues field to to the value of the input parameter
	public void setNoteValues(int[] noteValues) {
		this.noteValues = noteValues;
	}

	// Returns velocityValues field
	public int[] getVelocityValues() {
		return velocityValues;
	}

	// Sets the velocityValues field to to the value of the input parameter
	public void setVelocityValues(int[] velocityValues) {
		this.velocityValues = velocityValues;
	}

	// Returns millisValues field
	public double[] getMillisValues() {
		return millisValues;
	}

	// Sets the millisValues field to to the value of the input parameter
	public void setMillisValues(double[] millisValues) {
		this.millisValues = millisValues;
	}

	// Returns isTheRangeSet field
	public boolean isTheRangeSet() {
		return isTheRangeSet;
	}

	// Sets the isTheRangeSet field to to the value of the input parameter
	public void setTheRangeSet(boolean isTheRangeSet) {
		this.isTheRangeSet = isTheRangeSet;
	}

	// Returns areTheNotesStored field
	public boolean isAreTheNotesStored() {
		return areTheNotesStored;
	}

	// Sets the areTheNotesStored field to to the value of the input parameter
	public void setAreTheNotesStored(boolean areTheNotesStored) {
		this.areTheNotesStored = areTheNotesStored;
	}

	// Returns areTheVelocitiesStored field
	public boolean isAreTheVelocitiesStored() {
		return areTheVelocitiesStored;
	}

	// Sets the areTheVelocitiesStored field to to the value of the input parameter
	public void setAreTheVelocitiesStored(boolean areTheVelocitiesStored) {
		this.areTheVelocitiesStored = areTheVelocitiesStored;
	}

	// Returns areTheMillisStored field
	public boolean isAreTheMillisStored() {
		return areTheMillisStored;
	}

	// Sets the areTheMillisStored field to to the value of the input parameter
	public void setAreTheMillisStored(boolean areTheMillisStored) {
		this.areTheMillisStored = areTheMillisStored;
	}

	// Returns isTheBPMSet field
	public boolean isTheBPMSet() {
		return isTheBPMSet;
	}

	// Sets the isTheBPMSet field to to the value of the input parameter
	public void setTheBPMSet(boolean isTheBPMSet) {
		this.isTheBPMSet = isTheBPMSet;
	}

	// Returns readyToGo field
	public boolean isReadyToGo() {
		return readyToGo;
	}

	// Sets the readyToGo field to to the value of the input parameter
	public void setReadyToGo(boolean readyToGo) {
		this.readyToGo = readyToGo;
	}

	public static void main(String[] args) {
		new OSCClient();
	}
}