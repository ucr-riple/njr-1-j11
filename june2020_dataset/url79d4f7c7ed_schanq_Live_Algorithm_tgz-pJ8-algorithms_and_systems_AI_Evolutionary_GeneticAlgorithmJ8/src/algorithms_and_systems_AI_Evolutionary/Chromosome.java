// ##################################################################
// ##################################################################
// #	Chromosome: Stores genetic information used to spawn 		#
// #				musical phrases. 								#
// ##################################################################
// ##################################################################

package algorithms_and_systems_AI_Evolutionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Arrays;
import java.util.Random;

import expert_system.Dynamic;
import expert_system.DynamicShape;
import expert_system.Key;
import expert_system.Rhythmic_Block;
import expert_system.Scale;


public class Chromosome implements Comparable<Chromosome>{
	
	// ##########################################################
	// #					     FIELDS							#
	// ##########################################################
	
	// Range fields - non-genetic, selected by user for the kind of 
	// Accompaniment instrument they would like - bassAccomp, 
	// midAccomp, Sololist or fullRange
	private int 	 min, max;
	
	public int [] 	 scaleIntervals;
	public int    	 fitness;
	public Phrase 	 phrase;
	public Random 	 random;
	
	// 0 = neither key or scale, 
	// 1 = scale is right
	// 2 = key is right
	// 3 = both are right
	public int 		 levelOfTonality;
	
	// Gene Pool - all possible genes
	private Key   			[] allKeys;
	private Scale 			[] allScales;
	private Dynamic 		[] allDynamics;
	private DynamicShape 	[] allDynamicShapes;
	private Rhythmic_Block 	[] allRhythms;
	
	// Now the actual Genes
	public Key   	 					keyGene;
	public Scale	 					scaleGene;
	public Dynamic   					dynamicGene;
	public DynamicShape					dshapeGene;
	public ArrayList <Rhythmic_Block> 	rhythmGenes;
	
	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################
	
	// Blank constructor for child initialisation
	public Chromosome(){
		
		this.random = new Random();
		
	}
	
	// Default Constructor parameters used to set instrument (MIDI) range
	public Chromosome(int _min , int _max) {
		
		this.random = new Random();
		this.rhythmGenes = new ArrayList <Rhythmic_Block>();
		
		this.setMin(_min);
		this.setMax(_max);
		
		this.initGenePool();
		this.initGenes();
		this.setFitness(0);
		
	}
	
	// Child constructor for crossover/mutation
	public Chromosome(int _min , int _max, Key k, Scale s) {
		
		this.random = new Random();
		this.rhythmGenes = new ArrayList <Rhythmic_Block>();
		
		this.setMin(_min);
		this.setMax(_max);
		
		this.setKeyGene(k);
		this.setScaleGene(s);
		this.initGenePool();
		
		this.scaleGene.initialise  (this.keyGene);
		this.setScaleIntervals(scaleGene.getScaleInRange(this.min, this.max));
		
		this.setFitness(0);
	
	}
	
	// ##########################################################
	// #	METHODS: Getters/Setters for fields, initialisation #
	// #			 methods for the genes, 					#
	// ##########################################################
	
	// Initialise the gene pool - set up the all keys, scales and dynamics arrays
	public void initGenePool(){
		int i = 0;
		
		allKeys 			= new Key  	 		[12];
		allScales 			= new Scale	 		[20];
		allDynamics 		= new Dynamic		[8]; 
		allDynamicShapes 	= new DynamicShape	[5]; 
		allRhythms 			= new Rhythmic_Block[77];
		
		for (Key k : EnumSet.allOf(Key.class)){
			allKeys[i] = k;
			i++;
		}
		i = 0;
		for (Scale s : EnumSet.allOf(Scale.class)){
			allScales[i] = s;
			i++;
		}
		i = 0;
		for (Dynamic d : EnumSet.allOf(Dynamic.class)){
			allDynamics[i] = d;
			i++;
		}
		i = 0;
		for (DynamicShape ds : EnumSet.allOf(DynamicShape.class)){
			allDynamicShapes[i] = ds;
			i++;
		}
		i = 0;
		for (Rhythmic_Block rb : EnumSet.allOf(Rhythmic_Block.class)){
			allRhythms[i] = rb;
			i++;
		}
	}
	// Initialise Genes, randomises a chromosomes genes to any within the all arrays
	public void initGenes(){
		
		this.setKeyGene  		  (this.allKeys    [(int)Math.round(Math.random()*11)]);
		this.setScaleGene		  (this.allScales  [(int)Math.round(Math.random()*19)]);
		this.setDynamicGene		  (this.allDynamics[(int)Math.round(Math.random()*7)]);
		this.scaleGene.initialise (this.keyGene);
		this.setScaleIntervals(scaleGene.getScaleInRange(this.min, this.max));
		this.setDshapeGene(this.allDynamicShapes[(int)Math.round(Math.random()*4)]);
		
		int bar = 0;		
		while (bar != 4){
			Rhythmic_Block rb = this.allRhythms  [(int)Math.round(Math.random()*76)];
			if((rb.getBeatValue()+bar) <= 4){
				bar += rb.getBeatValue();
				this.rhythmGenes.add(rb);
			}
		}
		
		
	}
	// Returns min field
	public int getMin() {
		return min;
	}

	// Sets the min field to the value of the input parameter 
	public void setMin(int min) {
		this.min = min;
	}

	// Returns max field
	public int getMax() {
		return max;
	}

	// Sets the min field to the value of the input parameter 
	public void setMax(int max) {
		this.max = max;
	}

	// Returns scaleIntervals field
	public int[] getScaleIntervals() {
		return scaleIntervals;
	}

	// Sets the scaleIntervals field to to the value of the input parameter 
	public void setScaleIntervals(int[] scaleIntervals) {
		this.scaleIntervals = scaleIntervals;
	}

	// Returns fitness field
	public int getFitness() {
		return fitness;
	}

	// Sets the fitness field to to the value of the input parameter 
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	// Returns levelOfTonality field
	public int getLevelOfTonality() {
		return levelOfTonality;
	}

	// Sets the levelOfTonality field to to the value of the input parameter 
	public void setLevelOfTonality(int levelOfTonality) {
		this.levelOfTonality = levelOfTonality;
	}

	// Returns keyGene field
	public Key getKeyGene() {
		return keyGene;
	}

	// Sets the keyGene field to the value of the input parameter
	public void setKeyGene(Key keyGene) {
		this.keyGene = keyGene;
	}

	// Returns scaleGene field
	public Scale getScaleGene() {
		return scaleGene;
	}

	// Sets the scaleGene field to the value of the input parameter 
	public void setScaleGene(Scale scaleGene) {
		this.scaleGene = scaleGene;
	}
	// Returns dynamicGene field
	public Dynamic getDynamicGene() {
		return dynamicGene;
	}

	// Sets the dynamicGene field to the value of the input parameter 
	public void setDynamicGene(Dynamic dynamicGene) {
		this.dynamicGene = dynamicGene;
	}
	// Returns allKeys field
	public Key[] getAllKeys() {
		return allKeys;
	}
	// Returns allScales field
	public Scale[] getAllScales() {
		return allScales;
	}
	// Returns allDynamics field
	public Dynamic[] getAllDynamics() {
		return allDynamics;
	}
	// Returns allDynamicShapes field
	public DynamicShape[] getAllDynamicShapes() {
		return allDynamicShapes;
	}

	// Returns allRhythms field
	public Rhythmic_Block[] getAllRhythms() {
		return allRhythms;
	}

	// Returns dshapeGene field
	public DynamicShape getDshapeGene() {
		return dshapeGene;
	}

	// Sets the dshapeGene field to value of input parameter
	public void setDshapeGene(DynamicShape dshapeGene) {
		this.dshapeGene = dshapeGene;
	}

	// Returns phrase field
	public Phrase getPhrase() {
		return phrase;
	}
	// Returns the rhythm in string format 
	public String rhythmToString(){
		
		String s = "";
		
		for(Rhythmic_Block rb : this.rhythmGenes){
			s += rb.name()+" ";
		}
	
		return s;
	}
	// Spawns a musical phrase based on the chromosome's genes
	public void spawnPhrase(){
		
		phrase = new Phrase(this.min, this.max,this.scaleGene, this.dynamicGene,this.dshapeGene,this.rhythmGenes);
			
	}
	// Prints Chromosome's fields
	public void print(){
		System.out.println("Chromosome's Finess:          "+this.getFitness());
		System.out.println("Chromosome's Genes:           "+keyGene+" "+scaleGene+" "+dynamicGene);
		System.out.println("Chromosome's Dynamic Range:   "+Arrays.toString(dynamicGene.getVelocityRange()));
		System.out.println("Chromosome's Tone Range:      "+Arrays.toString(scaleIntervals));
		System.out.println("Chromosome's Dynamic Shape:   "+this.dshapeGene);
		System.out.println("Chromosome's Rhythmic Blocks: "+this.rhythmToString());
		
	}
	// Prints Chromosome's fields with including the generated phrase.
	public void printPlusPhrase(){
		System.out.println("Chromosome's Finess:          "+this.getFitness());
		System.out.println("Chromosome's Genes:           "+keyGene+" "+scaleGene+" "+dynamicGene);
		System.out.println("Chromosome's Dynamic Range:   "+Arrays.toString(dynamicGene.getVelocityRange()));
		System.out.println("Chromosome's Tone Range:      "+Arrays.toString(scaleIntervals));
		System.out.println("Chromosome's Dynamic Shape:   "+this.dshapeGene);
		System.out.println("Chromosome's Rhythmic Blocks: "+this.rhythmToString());
		this.phrase.print();
	}
	
	// Uses the java.util Collections sort method to sort 
	// through the chromosome's rhythm genes arraylist
	public void sortRhythm(){
		Collections.sort(this.rhythmGenes);
	}
	
	// Overides the Comparator interface method compareTo:
	// outputs a 1, 0 or -1 depending on whether the 
	// queried chromsome has a higher, equal or lower fitness.
	@Override
	public int compareTo(Chromosome chr) {
		
		return (int)Math.signum(chr.getFitness() - this.getFitness());
	}
	
	// Nested Comparator class for the sorting of rhythmic 
	// blocks in the chromosome's rhythm genes arraylist
	protected class RhythmicComparator implements Comparator<Rhythmic_Block>{ 
		
		 public int compare(Rhythmic_Block n1, Rhythmic_Block n2){
			return n1.getBeatValue() - n2.getBeatValue();
		}
	}
	
	// TESTING... 
	public static void main(String[] args) {
		
		//@SuppressWarnings("unused")
		//Chromosome c = new Chromosome(1,4);
		//c.spawnPhrase();
		//c.print();
		
		Chromosome mother = new Chromosome(2,5);
		Chromosome father = new Chromosome(2,5);
		int rand = (int) Math.round(Math.random()*2);
		System.out.println(rand);
		// TODO rand selects which array to be COllections.reverse after sorting
		switch(rand){
		case 1:
			mother.sortRhythm();
			Collections.reverse(mother.rhythmGenes);
			father.sortRhythm();
			break;
		case 2:
			mother.sortRhythm();
			father.sortRhythm();
			Collections.reverse(father.rhythmGenes);
			break;
		}
		ArrayList <Rhythmic_Block> mothersRhythm = mother.rhythmGenes;
		ArrayList <Rhythmic_Block> fathersRhythm = father.rhythmGenes;
		
		Chromosome child = new Chromosome(2,5);
		
		ArrayList <Rhythmic_Block> childsRhythm = new ArrayList <Rhythmic_Block>();
		int sum = 0;
		for(Rhythmic_Block rb : mothersRhythm){
			sum += rb.getBeatValue();
			if(sum <= 2){
				childsRhythm.add(rb);
			}
			else
				break;
		}
		sum = 0;
		for(Rhythmic_Block rb : fathersRhythm){
			sum += rb.getBeatValue();
			if(sum >= 3){
				childsRhythm.add(rb);
			}
		}
		
		child.rhythmGenes = childsRhythm;
		System.out.println("MOTHER: ");
		mother.print();
		System.out.println("\nFATHER: ");
		father.print();
		System.out.println("\nCHILD: ");
		child.print();
		
		
	}
}