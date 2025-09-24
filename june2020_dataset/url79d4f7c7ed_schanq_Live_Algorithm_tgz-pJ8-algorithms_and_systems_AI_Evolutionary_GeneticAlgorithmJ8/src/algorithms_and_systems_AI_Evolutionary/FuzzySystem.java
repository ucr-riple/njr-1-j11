// ##################################################################
// ##################################################################
// # FuzzySystem: Contains most of the common membership functions, # 
// # hedges and defuzzification techniques used in fuzzy systems. 	#
// # These are then used in methods to determine the genes for the 	#
// # target chromosome to be used in the Genetic Algorithm.			#
// ##################################################################
// ##################################################################
package algorithms_and_systems_AI_Evolutionary;

import java.util.Arrays;
import java.util.EnumSet;

import expert_system.DesiredLoudness;
import expert_system.Dynamic;
import expert_system.DynamicShape;
import expert_system.Feel;
import expert_system.Key;
import expert_system.Scale;
import expert_system.Tonality;


public class FuzzySystem {
	
	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################

	public FuzzySystem() {
		//BLANK
	}
	
	// ##########################################################
	// #					   FUNCTIONS						#
	// ##########################################################
	
	
	// ==========================================================
	// =			       Membership Functions			    	=
	// ==========================================================
	
	// Positive Grade function									
	public double grade(double x, double x0, double x1){
		
		double fuzzied = 0;
		
		if(x <= x0)
			fuzzied = 0;
		else if(x > x0 && x < x1)
			fuzzied = (x / (x1 - x0)) - (x0 / (x1 - x0));
		else if(x >= x1)
			fuzzied = 1;
		
		return fuzzied;
		
	}
	// Negative Grade Function									
	public double inverseGrade(double x, double x0, double x1){
		
		double fuzzied = 0;
		
		if(x <= x0)
			fuzzied = 1;
		else if(x > x0 && x < x1)
			fuzzied = (-x / (x1 - x0)) + (x1 / (x1 - x0));
		else if(x >= x1)
			fuzzied = 0;
		
		return fuzzied;
	}
	// Triangular Membership Function							
	public double triangular(double x, double x0, double x1, double x2){
		
		double fuzzied = 0;
		
		if(x <= x0 || x >= x2)
			fuzzied = 0;
		else if(x > x0 && x < x1)
			fuzzied = (x / (x1 - x0)) - (x0 / (x1 - x0));
		else if (x == x1)
			fuzzied = 1;
		else if (x > x1 && x < x2)
			fuzzied = (-x / (x2 - x1)) + (x2 / (x2 - x1));
		
		return fuzzied;
	}
	// Trapezoid Membership function							
	public double trapezoid(double x, double x0, double x1, double x2, double x3){
		
		double fuzzied = 0;
		
		if(x <= x0 || x >= x3)
			fuzzied = 0;
		else if(x > x0 && x < x1)
			fuzzied = (x / (x1 - x0)) - (x0 / (x1 - x0));
		else if(x > x1 && x < x2)
			fuzzied = 1;
		else if(x > x2 && x < x3)
			fuzzied = (-x / (x3 - x2)) + (x3 / (x3 - x2));
		
		return fuzzied;
	}
	// Gaussian Bell Membership Function						
	public double gaussian(double x, double ci, double oi){
		
		double fuzzied = Math.exp(- (Math.pow(ci - x, 2) 
								   /(2 * Math.pow(oi,2))));
		return fuzzied;
		
	}
	// Sigmoid Curve Membership Function: if the sign of a 		
	// is positive the curve increases from left to right		
	// else if it is negative it decreases from right to left.	
	// c is the point of inflection of the curve.				
	public double sigmoid(double a, double c, double x){
		
		return 1/(1+Math.exp(-a*(x-c)));
		
	}
	
	// ==========================================================
	// =						Hedges							=
	// ==========================================================
	
	// Hedge: Little											
	public double A_LITTLE(double x){
		
		return Math.pow(x, 1.3);
		
	}
	// Hedge: Slight											
	public double SLIGHTLY(double x){
		
		return Math.pow(x, 1.7);
		
	}
	// Hedge: Very												
	public double VERY(double x){
		
		return Math.pow(x, 2);
		
	}
	// Hedge: Greatly											
	public double GREATLY(double x){
		
		return Math.pow(x, 3);
		
	}
	// Hedge: Extremely 										
	public double EXTREMELY(double x){
		
		return Math.pow(x, 4);
		
	}
	
	// ==========================================================
	// =					Zadeh Operators						=					
	// ==========================================================

	// Logical operator: Disjunction (2 values)					
	public double OR(double A, double B){
		
		return Math.max(A, B);
		
	}
	// Logical operator: Conjunction (2 values)					
	public double AND(double A, double B){
		
		return Math.min(A, B);
		
	}
	// Logical operator: Negation (2 values)					
	public double NOT(double A){
		
		return 1.0f - A;
	}
	// Logical operator: Exclusive Disjunction					
	public double XOR(double A, double B){
		
		return (A+B)-(2*this.AND(A,B));
		
	}
	// Logical operator: Logical Implication					
	public double IMPLIES(double A, double B){
		
		return 1 - (this.AND(A, B));
		
	}
	// Logical operator: Negated Conjunction					
	public double NAND(double A, double B){
		
		return 1 - this.AND(A, B);
		
	}
	// Logical operator: Negated Disjunction					
	public double NOR(double A, double B){
		
		return 1 - this.OR(A, B);
		
	}
	// Logical operator: Negated Exclusive Disjunction			
	public double NXOR(double A, double B){
		
		return 1 - this.XOR(A, B);
		
	}
	// Logical operator: Negated Logical Implication			
	public double NOT_IMPLIES(double A, double B){
		
		return this.AND(A, 1-B);
		
	}
	
	public double OR(double [] set){
		
		Arrays.sort(set);
		return set[set.length-1];
		
	}
	
	// ##########################################################
	// #  METHODS : Defuzzification, Calculating the slope		#
	// #			of a line (IE for working out whether the	#
	// # 			velocity is ascending/descending), 			#
	// #			determining Tonality, desiredLoudness, feel #
	// #			for fitness function						#
	// ##########################################################
	
	
	// ==========================================================
	// =				DEFUZZIFICATION METHODS					=
	// ==========================================================

	// Used in the Maximum Defuzzification technique			
	public void sort(double [] set){
		
		Arrays.sort(set);
		
	}
	public void sort(int [] set){
		
		Arrays.sort(set);
		
	}
	// The Maximum Defuzzification Technique: хA(x*) │ хA(x)	
	// for all x E X											
	public double defuzz_Max(double [] u, double [] x){
	
		// Return error if the degree array (u) and 
		// crisp array(x) are not the same length
		if(u.length != x.length){
			System.out.println("YOUR DEGREE INPUTS AND DESIRED OUTPUT ARRAYS ARE NOT EQUAL IN LENGTH");
			return -99999;
		}
		
		double crisp = 0;
		double [] max = u.clone();
		
		this.sort(max);
		
		// If the maximum degree - marginalValue (deduct a small value to check 
		// if it really is a peak value: set to 0.1) is still greater than the
		// next highest value, set the crisp output to this value, else set 
		// crisp to -1 so the singleton method may be used instead.
		if(max[max.length-1]-0.1 > max[max.length-2]){
			for(int i = 0; i < u.length; i++){
				if(u[i] == max[max.length-1]){
					crisp = x[i];
					break;
				}
			}
		}
		else{
			crisp = -1;
		}
		return crisp;
		
	}
	// Weighted Average Defuzzification Technique or Singleton  
	// technique:  хA(x*) = (ихixi)/(иxi)						
	public double defuzz_Singleton(double [] u, double [] x){
		
		// Return error if the degree array (u) and 
		// crisp array(x) are not the same length
		if(u.length != x.length){
			System.out.println("YOUR DEGREE INPUTS AND DESIRED OUTPUT ARRAYS ARE NOT EQUAL IN LENGTH");
			return -99999;
		}
		
		double sumOfDegrees = 0;
		double sumOfDegreesMultipliedByCrisps = 0;
		
		for(int i = 0; i < u.length; i++)	
			sumOfDegrees += u[i];
		
		for(int i = 0; i < x.length; i++)
			sumOfDegreesMultipliedByCrisps += u[i]*x[i];
		
		return sumOfDegreesMultipliedByCrisps/sumOfDegrees;
			
	}
	
	// ==========================================================
	// =		METHOD TO DETERMINE TARGET TONALITY				=
	// ==========================================================
	// Method to determine the Tonality that is most true		=
	// for the performers current playing state. The 			=
	// output to be fed into the fitness function of the Genetic= 
	// Algorithm.												=
	// NOTE: This method is not a fuzzy assessment: it decides 	=
	// by comparing the input notes with the major scale of 	=
	// each key, the closest matching (ie the highest occuring) =
	// will determine the tonality.								=
	// ==========================================================

	private Tonality determineTonality(int [] notes){
		
		// Parameters for the returned tonality:
		// ie what is being determined.
		Key theKey = Key.B;
		int majmin = 1;
		
		// Declare a 2d array, whose y axis contains 
		// all the possible notes in the major scale 
		// for each key - x axis
		int [][] allTwelveTonesMajorScales = new int [12][77];
		int [] matches = new int [12];
		
		int i = 0;
		// And initialise the array
		for (Key k : EnumSet.allOf(Key.class)){
				Scale c = Scale.MAJOR;
				c.initialise(k);
				allTwelveTonesMajorScales[k.ordinal()] = c.getWholeScale();
		}
		
		// Count through the input notes and compare them to each scales major scale,
		// if one matches use the matches array as a tally by incrementing the matches row
		// equal to the one the scales stored in.
		for(i = 0; i < notes.length; i++){
			for(int row = 0; row < allTwelveTonesMajorScales.length; row++){
				for(int col = 0; col < allTwelveTonesMajorScales[row].length; col++){
					// Make sure that only zeros contained in the first column are tallied 
					if((col == 0 && allTwelveTonesMajorScales[row][col] == 0) && notes[i] == 0){
						matches[row]++;
					}
					else if((col > 0 && allTwelveTonesMajorScales[row][col] == 0) && notes[i] == 0){
						matches[row] = matches[row];
					}
					// Otherwise keep counting
					else if(notes[i] == allTwelveTonesMajorScales[row][col]){
						matches[row]++;
					}	
				}
			}
		}
		
		// Clone the matches array and sort the clone, then 
		// compare to the original. Take the index of the original 
		// with a value matching the highest value in the sorted   
		// array and the Enum ordinal that matches that index is the
		// key (or at least the major possibility.
		int [] sorted = matches.clone();
		this.sort(sorted);
		
		for(i = 0; i < matches.length; i++){
			if(matches[i] == sorted[sorted.length-1])
				break;
		}
		for (Key k : EnumSet.allOf(Key.class)){
			if(k.ordinal() == i){
				theKey = k;
				break;
			}
		}
		// Then create arrays for the relative minor and majorthirds,
		// tally the matches, sort the result and the rule that returns
		// true determines the tonality
		int firstOct = theKey.getOctave0();
		theKey.setRelativeMinor();
		int [] majorRoots_minorThirds 	= theKey.getOctaves();
		int [] minorRoots_majorSixths 	= theKey.getRelativeMinor().getOctaves();
		int [] majorThirds_minorTsFifth	= new int [allTwelveTonesMajorScales[theKey.ordinal()].length/7];
		
		matches = new int [3];
		
		if(firstOct == 0){
			i = 0;
			for(int j = firstOct+2; j < allTwelveTonesMajorScales[theKey.ordinal()].length; j+=7){
				majorThirds_minorTsFifth[i] = allTwelveTonesMajorScales[theKey.ordinal()][j];
				i++;
			}
		}
		else{
			i = 0;
			for(int j = firstOct-5; j < allTwelveTonesMajorScales[theKey.ordinal()].length; j+=7){
				if(j >= 0){
					majorThirds_minorTsFifth[i] = allTwelveTonesMajorScales[theKey.ordinal()][j];
					i++;
				}
			}
		}
		for(int row = 0; row < notes.length; row++){
			for(i = 0; i < majorThirds_minorTsFifth.length; i++){
				if(notes[row] == majorRoots_minorThirds[i])
					matches[0]++;
				if(notes[row] == minorRoots_majorSixths[i])
					matches[1]++;
				if(notes[row] == majorThirds_minorTsFifth[i])
					matches[2]++;
			}
		}
		int maj1_min3 = matches[0];
		int maj6_min1 = matches[1];
		int maj3_min5 = matches[2];
		
		this.sort(matches);
		
		if((maj1_min3 == matches[2] && maj6_min1 == matches[1])
		 ||(maj1_min3 == matches[2] && maj3_min5 == matches[1])
		 ||(maj3_min5 == matches[2] && maj1_min3 == matches[1]))
			majmin = 1;
		else if((maj6_min1 == matches[2] && maj3_min5 == matches[1])
			  ||(maj6_min1 == matches[2] && maj1_min3 == matches[1])
			  ||(maj3_min5 == matches[2] && maj6_min1 == matches[1]))
			majmin = 0;
		
		return new Tonality(theKey, majmin);
	}
	
	// ==========================================================
	// =		METHODS TO DETERMINE TARGET LOUDNESS			=
	// ==========================================================

	// Equation for working out the slope of a line (used to	
	// analyse whether a data set is on average ascending/		
	// descending /flat as makes no difference					
	private double slope(double x0, double y0, double x1, double y1){
		
		double m = (y1-y0)/(x1-x0);
		return m;
		
	}
	// Uses above slope equation to determine if there is a 	
	// direction/slope to a data set.							
	protected double averageDirection(int [] set){
		
		double summedSlope = 0;
		
		for(int i = 1; i < set.length-1; i++){
			
			summedSlope += this.slope(i, set[i], i+1, set[i+1]);
			
		}
		return summedSlope/set.length;
	}
	// Method to determine the DynamicShape enum is most true	
	// for the performers current playing state. The output	to	
	// be fed into the fitness function of the Genetic Algorithm
	private DynamicShape determineDynamicShape(int [] vels){
		
		// DynamicShape Variable
		DynamicShape d = DynamicShape.SPORADIC;
		
		// Fuzzy sets: descendingGradient (a descending slope across the set)
		//			   flatOrAsMakesNoDifference (neither distinctly descending or ascending)
		//			   ascendingGradient (an ascending slope across the set)
		double descendingGradient 		 = 0; 
		double flatOrAsMakesNoDifference = 0; 
		double ascendingGradient 		 = 0;
		
		// Fuzzy sets for whole velocity set: 
		//			   column 0 Ghost articulation
		// 			   column 1 Unarticulated
		//			   column 2 Articulated 
		double [][] ghostUnarticulatedOrAccent = new double [vels.length][3];
		
		// Fuzzy sets for on beat and off beat accents:
		double onBeatGhost 			 = 0;
		double offBeatGhost 		 = 0;
		double onBeatUnarticulation  = 0;
		double offBeatUnarticulation = 0;
		double onBeatAccent 		 = 0; 
		double offBeatAccent 		 = 0;
		
		// Fuzzify the who velocity set into the gradient sets
		descendingGradient 			= this.inverseGrade(this.averageDirection(vels), -7, -4);
		flatOrAsMakesNoDifference	= this.trapezoid   (this.averageDirection(vels), -7, -4, 4, 7);
		ascendingGradient			= this.grade	   (this.averageDirection(vels), 4, 7);
		
		// Fuzzify the entire velocity set into the articulation sets
		for(int row = 0; row < ghostUnarticulatedOrAccent.length; row++){
			ghostUnarticulatedOrAccent[row][0] = this.inverseGrade(vels[row], 44, 57);
			ghostUnarticulatedOrAccent[row][1] = this.triangular(vels[row], 44, 64, 84);
			ghostUnarticulatedOrAccent[row][2] = this.grade(vels[row], 71, 84);
		}
		
		// Average the on/off beat sets in the ghostUnarticulatedOrAccent array
		// to get declare the on/off beat variables
		int count = 1;
		for(int row = 0; row < ghostUnarticulatedOrAccent.length; row += 4){
			
			onBeatGhost += ghostUnarticulatedOrAccent[row][0];
			onBeatUnarticulation += ghostUnarticulatedOrAccent[row][1];
			onBeatAccent += ghostUnarticulatedOrAccent[row][2];
			count++;
		}
		onBeatGhost = onBeatGhost/count;
		onBeatUnarticulation = onBeatUnarticulation/count;
		onBeatAccent = onBeatAccent/count;
		
		count = 1;
		for(int row = 2; row < ghostUnarticulatedOrAccent.length; row += 4){
			
			offBeatGhost += ghostUnarticulatedOrAccent[row][0];
			offBeatUnarticulation += ghostUnarticulatedOrAccent[row][1];
			offBeatAccent += ghostUnarticulatedOrAccent[row][2];
			count++;
		}
		offBeatGhost = offBeatGhost/count;
		offBeatUnarticulation = offBeatUnarticulation/count;
		offBeatAccent = offBeatAccent/count;
		
		// Define Heavily nested fuzzy rules : See Rule Table in report
		double Decrescendo = this.OR(this.AND(onBeatAccent,descendingGradient),
								this.OR(this.AND(offBeatAccent,descendingGradient),
										this.OR(this.OR(this.AND(this.AND(onBeatGhost,descendingGradient),this.NOT(offBeatAccent))
													   ,this.AND(this.AND(onBeatUnarticulation,descendingGradient),this.NOT(offBeatAccent)))
											   ,this.OR(this.AND(this.AND(offBeatGhost, descendingGradient),this.NOT(onBeatAccent))
													   ,this.AND(this.AND(offBeatUnarticulation,descendingGradient),this.NOT(onBeatAccent)))
									     )));
		double Crescendo   = this.OR(this.AND(offBeatAccent,ascendingGradient),
								this.OR(this.AND(onBeatAccent,ascendingGradient),
									this.OR(this.OR(this.AND(this.AND(onBeatGhost,ascendingGradient),this.NOT(offBeatAccent))
												   ,this.AND(this.AND(onBeatUnarticulation,ascendingGradient),this.NOT(offBeatAccent)))
										   ,this.OR(this.AND(this.AND(offBeatGhost, ascendingGradient),this.NOT(onBeatAccent))
												   ,this.AND(this.AND(offBeatUnarticulation,ascendingGradient),this.NOT(onBeatAccent)))
								     )));
		double Sporadic    = this.OR(
								this.OR(this.AND(this.AND(onBeatGhost,flatOrAsMakesNoDifference),this.NOT(offBeatAccent))
									   ,this.AND(this.AND(onBeatUnarticulation,flatOrAsMakesNoDifference),this.NOT(offBeatAccent)))
							   ,this.OR(this.AND(this.AND(offBeatGhost, flatOrAsMakesNoDifference),this.NOT(onBeatAccent))
									   ,this.AND(this.AND(offBeatUnarticulation,flatOrAsMakesNoDifference),this.NOT(onBeatAccent)))
									 );
		
		double On_Beat_Accents = this.OR(this.AND(this.AND(offBeatUnarticulation,flatOrAsMakesNoDifference),onBeatAccent),
									this.OR(this.AND(this.AND(offBeatUnarticulation,descendingGradient),onBeatAccent),
										this.OR(this.AND(this.AND(offBeatGhost,flatOrAsMakesNoDifference),onBeatAccent),
											this.OR(this.AND(this.AND(offBeatGhost,descendingGradient),onBeatAccent),
															this.AND(onBeatAccent,flatOrAsMakesNoDifference)))));
		
		double Off_Beat_Accents = this.OR(this.AND(this.AND(onBeatUnarticulation,flatOrAsMakesNoDifference),offBeatAccent),
									this.OR(this.AND(this.AND(onBeatUnarticulation,descendingGradient),offBeatAccent),
											this.OR(this.AND(this.AND(onBeatGhost,flatOrAsMakesNoDifference),offBeatAccent),
												this.OR(this.AND(this.AND(onBeatGhost,descendingGradient),offBeatAccent),
																this.AND(offBeatAccent,flatOrAsMakesNoDifference)))));
		
		// ...Whew! Now Defuzzify!!
		double [] u = {Decrescendo,Crescendo,Sporadic,On_Beat_Accents,Off_Beat_Accents};
		double [] x = {4,8,12,16,20};
		double crisp = 0;
		
		// First check to see if there is an obvious peak in the results of the rule
		// evaluations,If so then use then use the maximum method else use the singleton method. 			 		
		if(this.defuzz_Max(u, x) > -1){
			crisp = this.defuzz_Max(u, x);
		}
		else{	
			crisp = this.defuzz_Singleton(u, x);
		}
		
		// As this is defuzzifing into classifications a series of if
		// then conditionals outlines the ranges of each type
		if(crisp >= 0 && crisp <= 4)
			d = DynamicShape.DECRESCENDO;
		else if(crisp > 4 && crisp <=8)
			d = DynamicShape.CRESCENDO;
		else if(crisp > 8 && crisp <= 12)
			d = DynamicShape.SPORADIC;
		else if(crisp > 12 && crisp <= 16)
			d = DynamicShape.ON_BEAT_ACCENTS;
		else if(crisp > 16 && crisp <= 20)
			d = DynamicShape.OFF_BEAT_ACCENTS;
		
		return d;
		
	}
	// Method to determine what Dynamic enum is most true		
	// for the performers current playing state. The 			
	// output to be fed into the fitness function of the 		
	// Genetic Algorithm 										
	private Dynamic determineDynamic(int [] vels){
		
		// Fuzzy sets
		double ppp 	= 0;
		double pp 	= 0;
		double p 	= 0;
		double mp 	= 0;
		double mf 	= 0;	
		double f 	= 0;
		double ff 	= 0;
		double fff 	= 0;
		
		// Declare a Dynamic variable
		Dynamic d = Dynamic.FORTISSIMO_POSSIBLE;
		
		// Average the fuzzification of the input set 
		for(int i = 0; i < vels.length; i ++){
			
			ppp += this.inverseGrade(vels[i], 13, 18);
			pp 	+= this.trapezoid(vels[i], 12, 17, 29, 34); 
			p 	+= this.trapezoid(vels[i], 28, 33, 45, 50); 
			mp  += this.trapezoid(vels[i], 44, 49, 61, 66); 
			mf  += this.trapezoid(vels[i], 60, 65, 77, 82); 
			f   += this.trapezoid(vels[i], 76, 81, 93, 98); 
			ff  += this.trapezoid(vels[i], 92, 97, 109, 114);
			fff += this.grade(vels[i], 108, 113);
			
		}
		ppp = ppp/vels.length;
		pp 	= pp/vels.length; 
		p   = p/vels.length; 
		mp  = mp/vels.length; 
		mf  = mf/vels.length; 
		f   = f/vels.length; 
		ff  = ff/vels.length;
		fff = fff/vels.length;
		
		// No rules are needed to determine the output as there 
		// is only a single set of membership functions, so we
		// can now defuzzify. 
		
		double crisp = 0;
		
		double [] u = {ppp,pp,p,mp,mf,f,ff,fff};
		double [] x   = {4,8,12,16,20,24,28,32}; 
		
		// First check to see if there is an obvious peak in the results of the rule
		// evaluations,If so then use then use the maximum method else use the singleton method. 
		if(this.defuzz_Max(u, x) > -1){
			crisp = this.defuzz_Max(u, x);
		}
		else{
			crisp = (int) this.defuzz_Singleton(u, x);
		}
		
		// As this is defuzzifing into classifications a series of if
		// then conditionals outlines the ranges of each type
		if(crisp > 0 && crisp <= 4)
			d = Dynamic.PIANISSIMO_POSSIBLE;
		else if(crisp > 4 && crisp <= 8)
			d = Dynamic.PIANISSIMO;
		else if(crisp > 8 && crisp <= 12)
			d = Dynamic.PIANO;
		else if(crisp > 12 && crisp <= 16)
			d = Dynamic.MEZZO_PIANO;
		else if(crisp > 16 && crisp <= 20)
			d = Dynamic.MEZZO_FORTE;
		else if(crisp > 20 && crisp <= 24)
			d = Dynamic.FORTE;
		else if(crisp > 24 && crisp <= 28)
			d = Dynamic.FORTISSIMO;
		else if(crisp > 28 && crisp <= 32)
			d = Dynamic.FORTISSIMO_POSSIBLE;
			
		return d;
	}
	private DesiredLoudness determineDynamics(int [] vels){
		
		return new DesiredLoudness(this.determineDynamic(vels), this.determineDynamicShape(vels));
		
	}
	
	// ==========================================================
	// =		METHODS TO DETERMINE TARGET FEEL				=
	// ==========================================================

	// Converts note values (eg 4n for crotchets) into its
	// millisecond value for the current bpm.
	public double convertNVToMillis(String s, double bpm){
		
		double millisVal = 0;
		double decimal = 0;
		String [] stringVals = {"1n","2nd","2n","4nd","2nt",
								"4nswg","4n","8nd","4nt","8nswg",
								"8n","16nd","8nt","16nswg","16n",
								"32nd","16nt","32n","32nt"};
		double [] decimalValues ={ 1, 1.33333333, 2, 2.666666666, 3,
								  3.2, 4, 5.333333333, 6, 6.4, 8, 
								  10.666666667, 12, 12.8, 16, 
								  21.333333333, 24, 32, 48};
		
		for(int i = 0; i < stringVals.length; i++){
			if(s == stringVals[i]){
				decimal = decimalValues[i];
				break;
			}	
		}
		millisVal = ((240000/decimal)/bpm);
		return millisVal;
	}
	// Method to determine the feel gene for the target chromosome
	private Feel determineFeel(double [] millis, double BPM){
		
		// Returnable Feel variable
		Feel theFeel = Feel.SWUNGWALK;
		
		// Fuzzy Sets
		double semi_breve			 	= 0; 
		double dotted_Minim 	 		= 0;
		double minim 					= 0;
		double dotted_Crotchet 			= 0;
		double triplet_Minim 			= 0;
		double swung_Crotchet			= 0;
		double crotchet					= 0;
		double dotted_Quaver			= 0;
		double triplet_Crotchet			= 0;
		double swung_Quaver				= 0;
		double quaver					= 0;
		double dotted_Semi_Quaver		= 0;
		double triplet_Quaver			= 0;
		double swung_Semi_Quaver		= 0;
		double semi_Quaver				= 0;
		double dotted_Demi_Semi_Quaver  = 0;
		double triplet_Semi_Quaver		= 0;
		double demi_Semi_Quaver			= 0;
		double triplet_Demi_Semi_Quaver = 0;
		
		// Count through the millis array, fuzzify the values and average them. 
		for(int i = 0; i < millis.length; i++){
			
			triplet_Demi_Semi_Quaver += this.triangular(millis[i], this.convertNVToMillis("32nt", BPM)/2, 
															this.convertNVToMillis("32nt", BPM), this.convertNVToMillis("32n", BPM));
			demi_Semi_Quaver		 += this.triangular(millis[i], this.convertNVToMillis("32nt", BPM), 
															this.convertNVToMillis("32n", BPM), this.convertNVToMillis("16nt", BPM));
			triplet_Semi_Quaver		 += this.triangular(millis[i], this.convertNVToMillis("32n", BPM), 
															this.convertNVToMillis("16nt", BPM), this.convertNVToMillis("32nd", BPM));
			dotted_Demi_Semi_Quaver	 += this.triangular(millis[i], this.convertNVToMillis("16nt", BPM), 
															this.convertNVToMillis("32nd", BPM), this.convertNVToMillis("16n", BPM));
			semi_Quaver				 += this.triangular(millis[i], this.convertNVToMillis("32nd", BPM),
															this.convertNVToMillis("16n", BPM),this.convertNVToMillis("16nswg", BPM));
			swung_Semi_Quaver		 += this.triangular(millis[i], this.convertNVToMillis("16n", BPM),
															this.convertNVToMillis("16nswg", BPM),this.convertNVToMillis("8nt", BPM));
			triplet_Quaver			 += this.triangular(millis[i], this.convertNVToMillis("16nswg", BPM),
															this.convertNVToMillis("8nt", BPM), this.convertNVToMillis("16nd", BPM));
			dotted_Semi_Quaver		 += this.triangular(millis[i], this.convertNVToMillis("8nt", BPM), 
															this.convertNVToMillis("16nd", BPM), this.convertNVToMillis("8n", BPM));
			quaver					 += this.triangular(millis[i], this.convertNVToMillis("16nd", BPM),
															this.convertNVToMillis("8n", BPM), this.convertNVToMillis("8nswg", BPM));
			swung_Quaver			 += this.triangular(millis[i],this.convertNVToMillis("8n", BPM),
															this.convertNVToMillis("8nswg", BPM),this.convertNVToMillis("4nt", BPM));
			triplet_Crotchet		 += this.triangular(millis[i],this.convertNVToMillis("8nswg", BPM),
															this.convertNVToMillis("4nt", BPM),this.convertNVToMillis("8nd", BPM));
			dotted_Quaver			 += this.triangular(millis[i], this.convertNVToMillis("4nt", BPM),
															this.convertNVToMillis("8nd", BPM),this.convertNVToMillis("4n", BPM));
			crotchet				 += this.triangular(millis[i],this.convertNVToMillis("8nd", BPM),
															this.convertNVToMillis("4n", BPM),this.convertNVToMillis("4nswg", BPM));
			swung_Crotchet			 += this.triangular(millis[i], this.convertNVToMillis("4n", BPM),
															this.convertNVToMillis("4nswg", BPM), this.convertNVToMillis("2nt", BPM));
			triplet_Minim			 += this.triangular(millis[i], this.convertNVToMillis("4nswg", BPM), 
															this.convertNVToMillis("2nt", BPM), this.convertNVToMillis("4nd", BPM));
			dotted_Crotchet			 += this.triangular(millis[i], this.convertNVToMillis("2nt", BPM), 
															this.convertNVToMillis("4nd", BPM), this.convertNVToMillis("2n", BPM));
			minim				  	 += this.triangular(millis[i], this.convertNVToMillis("4nd", BPM), 
															this.convertNVToMillis("2n", BPM), this.convertNVToMillis("2nd", BPM));
			dotted_Minim			 += this.triangular(millis[i], this.convertNVToMillis("2n", BPM), 
															this.convertNVToMillis("2nd", BPM), this.convertNVToMillis("1n", BPM));
			semi_breve					 += this.triangular(millis[i], this.convertNVToMillis("2nd", BPM), 
															this.convertNVToMillis("1n", BPM),this.convertNVToMillis("1n", BPM)*2);
		}
		semi_breve						= semi_breve/millis.length;
		dotted_Minim				= dotted_Minim/millis.length;
		minim						= minim/millis.length;
		dotted_Crotchet				= dotted_Crotchet/millis.length;
		triplet_Minim				= triplet_Minim/millis.length;
		swung_Crotchet				= swung_Crotchet/millis.length;
		crotchet					= crotchet/millis.length;
		dotted_Quaver				= dotted_Quaver/millis.length;
		triplet_Crotchet			= triplet_Crotchet/millis.length;
		swung_Quaver				= swung_Quaver/millis.length;
		quaver						= quaver/millis.length;
		dotted_Semi_Quaver			= dotted_Semi_Quaver/millis.length;
		triplet_Quaver				= triplet_Quaver/millis.length;
		swung_Semi_Quaver			= swung_Semi_Quaver/millis.length;
		semi_Quaver					= semi_Quaver/millis.length;
		dotted_Demi_Semi_Quaver		= dotted_Demi_Semi_Quaver/millis.length;
		triplet_Semi_Quaver			= triplet_Semi_Quaver/millis.length;
		demi_Semi_Quaver			= demi_Semi_Quaver/millis.length;
		triplet_Demi_Semi_Quaver	= triplet_Demi_Semi_Quaver/millis.length;
		
		// As the Feel rule table lists a huge amount of rules I have added an array version of OR
		// that sorts through the evaluations of the AND's and returns the highest one as the value 
		// for that feel types variable. 
		
		// Rules for Latin Feel
		double [] LatinArr		= {this.AND(dotted_Minim, dotted_Crotchet), 				this.AND(dotted_Minim, dotted_Quaver),				this.AND(dotted_Minim, quaver),
								   this.AND(dotted_Minim, dotted_Semi_Quaver), 				this.AND(dotted_Minim, dotted_Demi_Semi_Quaver),	this.AND(minim, dotted_Crotchet),
								   this.AND(minim, dotted_Quaver), 							this.AND(minim, dotted_Semi_Quaver), 				this.AND(minim, dotted_Demi_Semi_Quaver),
								   this.AND(dotted_Crotchet, triplet_Minim), 				this.AND(dotted_Crotchet, crotchet), 				this.AND(dotted_Crotchet, dotted_Quaver),
								   this.AND(dotted_Crotchet, triplet_Crotchet), 			this.AND(dotted_Crotchet, dotted_Semi_Quaver), 		this.AND(dotted_Crotchet, semi_Quaver),
								   this.AND(dotted_Crotchet, dotted_Demi_Semi_Quaver), 		this.AND(dotted_Crotchet, demi_Semi_Quaver), 		this.AND(dotted_Quaver, demi_Semi_Quaver),
								   this.AND(dotted_Quaver, dotted_Semi_Quaver), 			this.AND(dotted_Quaver, dotted_Demi_Semi_Quaver), 	this.AND(dotted_Semi_Quaver, semi_Quaver),
								   this.AND(dotted_Semi_Quaver, dotted_Demi_Semi_Quaver), 	this.AND(dotted_Semi_Quaver, dotted_Demi_Semi_Quaver),
								   this.AND(dotted_Semi_Quaver, demi_Semi_Quaver), 			this.AND(semi_Quaver, dotted_Demi_Semi_Quaver), 	this.AND(dotted_Demi_Semi_Quaver, triplet_Demi_Semi_Quaver)};
		double Latin			=  this.OR(LatinArr);
		
		// Rules For Swung Walk Feel
		double [] SwungWalkArr 	= {this.AND(swung_Quaver, triplet_Crotchet), 		 this.AND(swung_Quaver, quaver), 						this.AND(swung_Quaver, dotted_Semi_Quaver),
								   this.AND(swung_Quaver, triplet_Quaver), 			 this.AND(swung_Quaver, swung_Semi_Quaver), 			this.AND(swung_Quaver, quaver),
								   this.AND(swung_Quaver, dotted_Demi_Semi_Quaver),  this.AND(swung_Quaver, triplet_Semi_Quaver), 			this.AND(swung_Quaver, demi_Semi_Quaver),
								   this.AND(swung_Quaver, triplet_Demi_Semi_Quaver), this.AND(swung_Semi_Quaver, dotted_Minim), 			this.AND(swung_Semi_Quaver, minim),
								   this.AND(swung_Semi_Quaver, dotted_Crotchet), 	 this.AND(swung_Semi_Quaver, triplet_Minim), 			this.AND(swung_Semi_Quaver, swung_Crotchet),
								   this.AND(swung_Semi_Quaver, crotchet),			 this.AND(swung_Semi_Quaver, dotted_Quaver), 		    this.AND(swung_Semi_Quaver, triplet_Crotchet),
								   this.AND(swung_Semi_Quaver, quaver), 			 this.AND(swung_Semi_Quaver, dotted_Semi_Quaver), 	    this.AND(swung_Semi_Quaver, triplet_Quaver),
								   this.AND(swung_Semi_Quaver, semi_Quaver), 		 this.AND(swung_Semi_Quaver, dotted_Demi_Semi_Quaver),  this.AND(swung_Semi_Quaver, triplet_Semi_Quaver),
								   this.AND(swung_Semi_Quaver, demi_Semi_Quaver), 	 this.AND(swung_Semi_Quaver, triplet_Demi_Semi_Quaver)};
		
		double SwungWalk 		 = this.OR(SwungWalkArr);
		
		// Rules for Swung Solo feel
		double [] SwungSoloArr  = {this.AND(swung_Crotchet, dotted_Minim),		 this.AND(swung_Crotchet, minim),			this.AND(swung_Crotchet, dotted_Crotchet),
								   this.AND(swung_Crotchet, triplet_Minim), 	 this.AND(swung_Crotchet, crotchet),		this.AND(swung_Crotchet, dotted_Quaver),
								   this.AND(swung_Crotchet, triplet_Crotchet),	 this.AND(swung_Crotchet, swung_Quaver), 	this.AND(swung_Crotchet, quaver),
								   this.AND(swung_Crotchet, dotted_Semi_Quaver), this.AND(swung_Crotchet, semi_Quaver), 	this.AND(swung_Crotchet, dotted_Demi_Semi_Quaver),
								   this.AND(swung_Crotchet, triplet_Semi_Quaver),this.AND(swung_Crotchet, demi_Semi_Quaver),this.AND(swung_Crotchet, triplet_Demi_Semi_Quaver),
								   this.AND(swung_Quaver, dotted_Minim),		 this.AND(swung_Quaver, minim),				this.AND(swung_Quaver, dotted_Crotchet),
								   this.AND(swung_Quaver, triplet_Minim),		 this.AND(swung_Quaver, crotchet),			this.AND(swung_Quaver, dotted_Quaver)};
		
		double SwungSolo 		=  this.OR(SwungSoloArr);
		
		// Rules for Straight Walk Feel
		double [] StraightWalkArr = {this.AND(triplet_Demi_Semi_Quaver, dotted_Minim),   	this.AND(triplet_Demi_Semi_Quaver, minim), 
									 this.AND(triplet_Demi_Semi_Quaver, dotted_Crotchet),	this.AND(triplet_Demi_Semi_Quaver, triplet_Minim),
									 this.AND(triplet_Demi_Semi_Quaver, crotchet ), 	 	this.AND(triplet_Demi_Semi_Quaver, dotted_Quaver), 
									 this.AND(triplet_Demi_Semi_Quaver, triplet_Crotchet), 	this.AND(triplet_Demi_Semi_Quaver, quaver),
									 this.AND(triplet_Demi_Semi_Quaver, dotted_Semi_Quaver),this.AND(triplet_Demi_Semi_Quaver, triplet_Quaver),
									 this.AND(triplet_Demi_Semi_Quaver, semi_Quaver), 		this.AND(triplet_Demi_Semi_Quaver, demi_Semi_Quaver),
									 this.AND(demi_Semi_Quaver, triplet_Minim),				this.AND(demi_Semi_Quaver, crotchet),		
									 this.AND(demi_Semi_Quaver, triplet_Crotchet),			this.AND(demi_Semi_Quaver, quaver), 					
									 this.AND(demi_Semi_Quaver, triplet_Quaver), 			this.AND(demi_Semi_Quaver, semi_Quaver),
									 this.AND(demi_Semi_Quaver, dotted_Demi_Semi_Quaver), 	this.AND(dotted_Semi_Quaver, triplet_Minim),
									 this.AND(dotted_Semi_Quaver, crotchet), 				this.AND(dotted_Semi_Quaver, dotted_Quaver),
									 this.AND(dotted_Semi_Quaver, triplet_Crotchet), 		this.AND(dotted_Semi_Quaver, quaver),
									 this.AND(dotted_Semi_Quaver, dotted_Semi_Quaver), 		this.AND(dotted_Semi_Quaver, triplet_Quaver),
									 this.AND(dotted_Semi_Quaver, semi_Quaver),				this.AND(dotted_Demi_Semi_Quaver, crotchet),
									 this.AND(dotted_Demi_Semi_Quaver, triplet_Crotchet), 	this.AND(dotted_Demi_Semi_Quaver, triplet_Quaver),
									 this.AND(semi_Quaver, crotchet), 						this.AND(semi_Quaver, triplet_Crotchet),
									 this.AND(semi_Quaver,quaver), 							this.AND(semi_Quaver, triplet_Quaver),
									 this.AND(triplet_Quaver, dotted_Quaver), 				this.AND(triplet_Quaver, quaver),
									 this.AND(triplet_Quaver, dotted_Semi_Quaver), 			this.AND(dotted_Semi_Quaver, triplet_Crotchet),
									 this.AND(quaver, dotted_Crotchet)};
		
		double StraightWalk 	  =  this.OR(StraightWalkArr);
		
		// Rules for Straight Solo Feel
		double [] StraightSoloArr = {this.AND(dotted_Minim, minim), 		 this.AND(dotted_Minim, crotchet), 			  this.AND(dotted_Minim, triplet_Crotchet),
									 this.AND(dotted_Minim, triplet_Quaver), this.AND(dotted_Minim, semi_Quaver),		  this.AND(dotted_Minim, triplet_Semi_Quaver),
									 this.AND(dotted_Minim,demi_Semi_Quaver),this.AND(minim, triplet_Minim),			  this.AND(minim, crotchet),
									 this.AND(minim, triplet_Crotchet), 	 this.AND(minim, quaver), 					  this.AND(minim, triplet_Quaver),
									 this.AND(minim, semi_Quaver),			 this.AND(minim, triplet_Semi_Quaver), 		  this.AND(minim, demi_Semi_Quaver),
									 this.AND(dotted_Crotchet, crotchet), 	 this.AND(dotted_Crotchet, quaver),			  this.AND(dotted_Crotchet, triplet_Quaver),
									 this.AND(triplet_Minim, dotted_Minim),  this.AND(triplet_Minim, crotchet), 		  this.AND(triplet_Minim, triplet_Crotchet),
									 this.AND(triplet_Minim, quaver), 		 this.AND(triplet_Minim, dotted_Semi_Quaver), this.AND(triplet_Minim, triplet_Quaver),
									 this.AND(triplet_Minim, semi_Quaver), 	 this.AND(crotchet, dotted_Crotchet), 		  this.AND(crotchet, triplet_Crotchet),
									 this.AND(crotchet, quaver), 			 this.AND(crotchet, dotted_Semi_Quaver), 	  this.AND(crotchet, triplet_Quaver), 
									 this.AND(crotchet, semi_Quaver), 		 this.AND(triplet_Crotchet, triplet_Quaver)};
		
		double StraightSolo 	  =  this.OR(StraightSoloArr);
		
		// Rules For determining if it is the last note
		double [] LastNoteArr = {this.AND(semi_breve, dotted_Minim), 		this.AND(semi_breve, minim), 			 this.AND(semi_breve, dotted_Crotchet), 
								 this.AND(semi_breve, triplet_Minim), 		this.AND(semi_breve, swung_Crotchet), 	 this.AND(semi_breve, crotchet),
								 this.AND(semi_breve, dotted_Quaver), 		this.AND(semi_breve, triplet_Crotchet),  this.AND(semi_breve, swung_Quaver),
								 this.AND(semi_breve, quaver), 				this.AND(semi_breve, dotted_Semi_Quaver),this.AND(semi_breve, triplet_Quaver),
								 this.AND(semi_breve, swung_Semi_Quaver), 	this.AND(semi_breve, semi_Quaver), 		 this.AND(semi_breve, dotted_Demi_Semi_Quaver),
								 this.AND(semi_breve, triplet_Semi_Quaver), this.AND(semi_breve,demi_Semi_Quaver), 	 this.AND(semi_breve, triplet_Demi_Semi_Quaver),semi_breve};
		
		double LastNote 	  =  this.OR(LastNoteArr);
		
		// The evaluated rules are defuzzified using the Max method if 
		// there is a clear peak else the singleton method is used.
		double [] u = {Latin, SwungWalk, SwungSolo, StraightWalk, StraightSolo, LastNote};
		double [] x = {4,8,12,16,20,24};
		
		double crisp = 0;
		
		if(this.defuzz_Max(u, x) > -1){
			crisp = this.defuzz_Max(u, x);
		}
		else{
			crisp = (int) this.defuzz_Singleton(u, x);
		}
		
		// Feel type ranges, All the above degrees have a corresponding Feel type.
		// If the performer has stopped playing (ie if no input has been received 
		// in the millis) an extra type is included called End.  
		if (crisp == 0)
			theFeel = Feel.END;
		else if(crisp > 0 && crisp <= 4)
			theFeel = Feel.LATIN;
		else if(crisp > 4 && crisp <= 8)
			theFeel = Feel.SWUNGWALK;
		else if(crisp > 8 && crisp <= 12)
			theFeel = Feel.SWUNGSOLO;
		else if(crisp > 12 && crisp <= 16)
			theFeel = Feel.STRAIGHTWALK;
		else if(crisp > 16 && crisp <= 20)
			theFeel = Feel.STRAIGHTSOLO;
		else if(crisp > 20 && crisp <= 24)
			theFeel = Feel.LASTNOTE;
		
		return theFeel;
	}
	
	// ==========================================================
	// =		METHOD TO DETERMINE TARGET CHROMOSOME			=
	// ==========================================================

	
	public TargetChromosome determineTarget(int [] notes, int [] vels, double [] millis, double BPM){
		
		TargetChromosome tc1 = new TargetChromosome(this.determineTonality(notes), this.determineDynamics(vels), this.determineFeel(millis, BPM));
		return tc1;
	}
	
	// ##########################################################
	// #				    	TESTING..						#
	// ##########################################################

	public static void main(String [] args){
		int [] vels = {127,70,70,70,120,80,70,60,123,40,30,20,114,0};
		double [] millis = {1500};
		int [] notes = {9,0,0,5,7,9,11,12};
		FuzzySystem fs = new FuzzySystem();
		fs.determineTarget(notes, vels, millis, 120).print();
		
	}
}
