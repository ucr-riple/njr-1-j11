// ##################################################################
// ##################################################################
// #		Genetic Algorithm: Based on a target, it reproduces		#
// #		A population of chromosomes until an individual that	#
// #		meets the target emerges. 								#
// ##################################################################
// ##################################################################
package algorithms_and_systems_AI_Evolutionary;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import expert_system.DesiredLoudness;
import expert_system.Dynamic;
import expert_system.DynamicShape;
import expert_system.Feel;
import expert_system.Key;
import expert_system.Rhythmic_Block;
import expert_system.Scale;
import expert_system.Tonality;


public class GeneticAlgorithm {

	// ##########################################################
	// #					     FIELDS							#
	// ##########################################################
	
	public Tonality 		 targetTonality;
	public DesiredLoudness 	 targetLoudness;
	public Feel				 targetFeel;
	
	public int 				 populationSize = 50;
	public int 				 totalFitnessOfPopulation = 0;
	public int 				 theFittest = 0;
	public int 				 minOctave, maxOctave;
	
	public List <Chromosome> citezens;
	
	public Random 	 		 random;

	// ##########################################################
	// #					   CONSTRUCTORS						#
	// ##########################################################
	
	public GeneticAlgorithm(TargetChromosome t, int min, int max){
		
		this.random 		= new Random();
		
		this.targetTonality = t.getTargetTonality();
		this.targetLoudness = t.getTargetLoudness();
		this.targetFeel		= t.getTargetFeel();
		this.minOctave 		= min;
		this.maxOctave 		= max;
		this.citezens 		= new ArrayList <Chromosome> ();
		
		
	}
	// ##########################################################
	// #	METHODS: for the initialisation of Population,  	#
	// #			 calculation of fitness, selection of 		#
	// #			 Parents and reproducion					#
	// ##########################################################
	// ==========================================================
	// Returns populationSize field								=
	// ==========================================================
	public int getPopulationSize() {
		return populationSize;
	}
	// ==========================================================
	// Sets the populationSize field to the value of the input	=
	// parameter 												=
	// ==========================================================
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	
	// Initialises the citezen list with a population of 
	// chromosomes with a set of random genes
	public void populate(){
		
		for(int i = 0; i < this.populationSize; i++){
			Chromosome chr = new Chromosome(this.minOctave, this.maxOctave);
			this.citezens.add(chr);
		}
	}
	// ==========================================================
	// Uses a fitness function to determine how fit the 		=
	// input Chromosome is in comparison to the target			=
	// ==========================================================
	public void calculateFitness(Chromosome c){
		
		// Determines the Chromosomes fitness according to its Key
		// and scale genes. As the Scale gene can only be determined
		// as fit if the Key gene is fit The Level of tonality function is 
		// used in conjunction with the fitness to determine whether
		// the chromosome's Key/Scale genes are dominant or recessive.
		Key [] relatedKeys = this.targetTonality.getRelatedKeys();
		for(Key k : relatedKeys){
			if(c.getKeyGene() == k){
				c.setFitness(4);
				c.setLevelOfTonality(1);
				for(Scale s :k.getChord().getScales()){
					if(c.getScaleGene() == s){
						c.setFitness(8);
						c.setLevelOfTonality(2);
					}
				}
				break;
			}
		}
		
		// Determines whether the Dynamic genes are fit in
		// comparison to the target chromosome
		Dynamic [] dyns = this.targetLoudness.allDynamics; 
		int [] fits     = this.targetLoudness.howCloseAreTheDynamics;
		DynamicShape ds = this.targetLoudness.getTheShape();
		
		for(int i = 0; i < dyns.length; i++){
			if(dyns[i] ==  c.getDynamicGene()){
				c.setFitness(c.getFitness()+fits[i]);
				break;
			}
		}
		if(c.getDshapeGene() == ds){
			c.setFitness(c.getFitness()+8);
		}
		
		// Determines the Fitness of the Chromosome's rhythmGenes by getting the
		// beat value of each rhythmic block (considered fit by the target feel) 
		// and assigning a fitness level for each based on its length so if length 1 then
		// fitness 2 if beatlength 2 fitness 4 (The purpose is so that the ideal rhythmic 
		// gene will equate to a fitness of 8). It then compares the chromosome's 
		// rhythmic_blocks to the ones considered fit, if one matches then add the 
		// appropriate fitness value to the chromosome's fitness.
		ArrayList <Rhythmic_Block> fitRhythms = this.targetFeel.getFitRhythms();
		int [] levelsOfFitness = new int [fitRhythms.toArray().length];
		for(int i = 0; i < levelsOfFitness.length; i++){
			if(fitRhythms.get(i).getBeatValue() == 1){
				levelsOfFitness[i] = 2;
			}
			else if(fitRhythms.get(i).getBeatValue() == 2){
				levelsOfFitness[i] = 4;
			}
		}
		int i = 0;
		for(Rhythmic_Block rb : fitRhythms){
			for(Rhythmic_Block r : c.rhythmGenes){
				if(r.equals(rb)){
					c.setFitness(c.getFitness()+levelsOfFitness[i]);
				}
			}
			i++;
		}
	}
	// ==========================================================
	// Genetic crossover - crosses over the genes, however the  =	
	// specific difference between this and other crossover		=
	// functions is that it is slightly hard coded so that the 	=
	// tonal genes (Key and Scale) are crossed over together if =
	// they are fit.											=
	// ==========================================================
	public Chromosome crossover(Chromosome mother, Chromosome father){
		Chromosome child = new Chromosome();
		
		int rand = random.nextInt(2)+1;
		int randTwoDom = random.nextInt(3);
		
		// If neither Chromosome is tonally relevant randomly select either parents' key/scale
		// to give to the child as all tonal genes are recessive
		if(mother.getLevelOfTonality() == 0 && father.getLevelOfTonality() == 0){
			switch(rand){
			
			case 1:
				child = new Chromosome(this.minOctave, this.maxOctave, mother.getKeyGene(), father.getScaleGene());
				break;
			case 2:
				child = new Chromosome(this.minOctave, this.maxOctave, father.getKeyGene(), mother.getScaleGene());
				break;
			}
		}
		
		// If one of the parents has a relevant key to the target tonality the child inherits  
		// this key as it is the dominant tonal gene of the two. It also inherits the other
		// parents scale as although both scales are tonally recessive, we don't want a
		// clone as this culls diversity
		else if(mother.levelOfTonality == 1 && father.levelOfTonality == 0){
			child = new Chromosome(this.minOctave, this.maxOctave, mother.getKeyGene(), father.getScaleGene());
			
		}
		else if(mother.levelOfTonality == 0 && father.levelOfTonality == 1){
			child = new Chromosome(this.minOctave, this.maxOctave, father.getKeyGene(), mother.getScaleGene());
			
		}
		
		// If both of the parents have a dominant tonal gene randomly select which of the two 
		// are to be passed on as they are equal in relevance
		else if(mother.levelOfTonality == 1 && father.levelOfTonality == 1){
			switch(rand){
			
			case 1:
				child = new Chromosome(this.minOctave, this.maxOctave, mother.getKeyGene(), father.getScaleGene());
				break;
			case 2:
				child = new Chromosome(this.minOctave, this.maxOctave, father.getKeyGene(), mother.getScaleGene());
				break;
			}
		}
		
		// If one of the parents has two dominant tonal genes and the other has none, pass on both genes of the
		// tonally dominant parent
		else if(mother.levelOfTonality == 2 && father.levelOfTonality == 0){
			
			child = new Chromosome(this.minOctave, this.maxOctave, mother.getKeyGene(), mother.getScaleGene());
			
		}
		else if(mother.levelOfTonality == 0 && father.levelOfTonality == 2){
			
			child = new Chromosome(this.minOctave, this.maxOctave, father.getKeyGene(), father.getScaleGene());
			
		}
		
		// If one of the parents have two dominant tonal genes and the other has one dominant tonal gene randomly select with
		// a probability of 2:3 and 1:3 respectively, which genes to pass on.
		else if(mother.levelOfTonality == 2 && father.levelOfTonality == 1){
			if(randTwoDom == 0 || randTwoDom == 1)
				child = new Chromosome(this.minOctave, this.maxOctave, mother.getKeyGene(), mother.getScaleGene());
			else
				child = new Chromosome(this.minOctave, this.maxOctave, father.getKeyGene(), mother.getScaleGene());
		}
		else if(mother.levelOfTonality == 1 && father.levelOfTonality == 2){
			if(randTwoDom == 0 || randTwoDom == 1)
				child = new Chromosome(this.minOctave, this.maxOctave, father.getKeyGene(), father.getScaleGene());
			else
				child = new Chromosome(this.minOctave, this.maxOctave, mother.getKeyGene(), father.getScaleGene());
			
		}
		
		// If Both parents have two dominant tonal genes randomly select which of the two to pass on
		else if(mother.levelOfTonality == 2 && father.levelOfTonality == 2){
			switch(rand){
			
			case 1:
				child = new Chromosome(this.minOctave, this.maxOctave, mother.getKeyGene(), father.getScaleGene());
				break;
			case 2:
				child = new Chromosome(this.minOctave, this.maxOctave, father.getKeyGene(), mother.getScaleGene());
				break;
			}
			
		}
		// Crossover for both types of Dynamic Gene
		switch(rand){
		case 1:
			child.setDynamicGene(mother.getDynamicGene());
			child.setDshapeGene(father.getDshapeGene());
			break;
		case 2:
			child.setDynamicGene(father.getDynamicGene());
			child.setDshapeGene(mother.getDshapeGene());
			break;
		}
		
		// Crossover of Rhythmic Genes..
		
		// If crossover occurs before sorting the arrays the rhythmic blocks can 
		// overflow a bar length ie if the second block of one parent is a 2 beat 
		// block then if crossover occurs there it can end up leaving the child with
		// 5 or 6 beats. So to avoid this, both parents are sorted and the rand variable
		// selects one to be reversed so that 2 beat blocks that the child chromosome
		// inherits aren't always the end block.
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
		// Store the parents rhythmic genes in two ArrayLists
		ArrayList <Rhythmic_Block> mothersRhythm = mother.rhythmGenes;
		ArrayList <Rhythmic_Block> fathersRhythm = father.rhythmGenes;
		
		ArrayList <Rhythmic_Block> childsRhythm = new ArrayList <Rhythmic_Block>();
		
		int sum = 0;
		
		// The arraylists are then crossed over, the crossover point is always at the 
		// start of the third beat of bar in the set of rhythmic_blocks.
		// The rand variable decides which parent has their first 2 beats crossed over.
		switch(rand){
		case 1:
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
				if(sum >= 3 && sum <= 4){
					childsRhythm.add(rb);
				}
			}
			break;
			
		case 2:
			for(Rhythmic_Block rb : fathersRhythm){
				sum += rb.getBeatValue();
				if(sum <= 2){
					childsRhythm.add(rb);
				}
				else
					break;
			}
			sum = 0;
			for(Rhythmic_Block rb : mothersRhythm){
				sum += rb.getBeatValue();
				if(sum >= 3 && sum <= 4){
					childsRhythm.add(rb);
				}
			}
			break;
		}
		
		// Clear the child's rhythm genes arraylist and fill 
		// with the crossed over genes.
		child.rhythmGenes.clear();
		child.rhythmGenes = childsRhythm;
		
		return child;	
		
	}
	// ==========================================================
	// Method for the random mutation of genes, has a 1/100 	=
	// probability of occurring									=
	// ==========================================================
	public void mutate(Chromosome c){
		
		// Random variables to select a random gene of each type
		int randK 	= (int)Math.round(Math.random()*11);
		int randS 	= (int)Math.round(Math.random()*19);
		int randD 	= (int)Math.round(Math.random()*7);
		int randDS	= (int)Math.round(Math.random()*4);
		int randR	= (int)Math.round(Math.random()*76);
		
		// If the random variable (which has a range of 0 - 99)
		// lands of 0, mutate the variable in that if statement.
		if(random.nextInt(100) == 0){
			Key [] k = c.getAllKeys();
			c.setKeyGene(k[randK]);
		}
		if(random.nextInt(100) == 0){
			Scale [] s = c.getAllScales();
			c.setScaleGene(s[randS]);
		}
		if(random.nextInt(100) == 0){
			Dynamic [] d = c.getAllDynamics();
			c.setDynamicGene(d[randD]);
		}
		if(random.nextInt(100) == 0){
			DynamicShape [] ds = c.getAllDynamicShapes();
			c.setDshapeGene((ds[randDS]));
		}
		if(random.nextInt(100) == 0){
			Rhythmic_Block [] rb = c.getAllRhythms();
			int l = c.rhythmGenes.toArray().length;
			int r = (int)Math.round(Math.random()*(l-1));
			Rhythmic_Block temp = rb[randR];
			c.rhythmGenes.set(r, temp);
		}
	}
	// ==========================================================
	// Selects parents from the population using the roulette 	=
	// method:													=
	//															=
	// Sums the fitness of the entire population and randomly 	=
	// selects a number between 0 -> the entire populations 	=
	// fitness. The populations fitness is summed again and 	=
	// the individual whose fitness exceeds the random value 	=
	// during the summation is selected as the parent.			=
	// ==========================================================
	public Chromosome rouletteSelection(){
		
		Chromosome parent = new Chromosome();
		int totalFitness = 0;
		
		
		for(Chromosome c : this.citezens){
			totalFitness += c.getFitness();
		}
		if(totalFitness == 0){
			parent = this.citezens.get(random.nextInt(this.populationSize));
			return parent;
		}
		
		int r = random.nextInt(totalFitness);
		int sum = 0;
		
		for(Chromosome c : this.citezens){
			sum += c.getFitness();
			if(sum >= r){
				parent = c;
				break;
			}
		}
		
		return parent;
		
	}
	// ==========================================================
	// Method for producing a new generation of chromosomes		=
	// ==========================================================
	public void reproduce(){
		
		List <Chromosome> offspring = new ArrayList <Chromosome>();
		this.totalFitnessOfPopulation = 0;
		
		// Calculate the fitness of the population
		for(Chromosome c : this.citezens){	
			this.calculateFitness(c);
			this.totalFitnessOfPopulation += c.getFitness();
		}
		// Sort the Population by their fitness and reset the 
		// least fit individual by reinitialising its genes
		Collections.sort(this.citezens);
		this.theFittest = this.citezens.get(0).getFitness();
		this.citezens.get(this.populationSize-1).initGenes();
		
		// Ellitism retains the best traits in the previous generation.
		this.citezens.get(0).setFitness(0);
		offspring.add(this.citezens.get(0));

		// While the population size is equal to the desired size
		// create new chromosomes by crossing over the individuals
		// and allowing the chance of mutation. 
		while(offspring.size() < 50){
			
			Chromosome mother = this.rouletteSelection();
			Chromosome father = this.rouletteSelection();
			
			Chromosome child = this.crossover(mother, father);
			this.mutate(child);
			
			offspring.add(child);
		}
		
		// Replace the old population with the new one.
		this.citezens.clear();
		this.citezens = offspring;
	
	}
	// ==========================================================
	// Returns the Beau Ideal - ie the most perfect individual  =
	// ==========================================================
	public Chromosome produceBeauIdeal(){
		//System.out.println("working");
		// Initialize the population
		this.populate();

		// While the total population's fitness is less than an average
		// of 24 (ie 75% fitness rate, which means their will most likely
		// be some perfect individuals but without waiting for all of the 
		// population to be perfect as this could take longer than necessary)
		// reproduce the individuals to get new generations
		while(this.totalFitnessOfPopulation < 1200){
			this.reproduce();
		}
		// Once the population has a 75% fitness average, calculate the 
		// fitness of their offspring and sort by their fitness.
		for(Chromosome c : this.citezens)
			this.calculateFitness(c);
		
		Collections.sort(this.citezens);
		
		// Select the most fit individual in the population, 
		// initialize their phrase and return it.
		Chromosome beauIdeal = this.citezens.get(0);
		beauIdeal.spawnPhrase();
		
		return beauIdeal;
		
	}
	public static void main(String[] args) {
		
		Tonality t = new Tonality(Key.A, 0);
		DesiredLoudness dl = new DesiredLoudness(Dynamic.FORTISSIMO, DynamicShape.DECRESCENDO);
		Feel lat = Feel.STRAIGHTSOLO;
		TargetChromosome tc = new TargetChromosome(t,dl,lat);
		GeneticAlgorithm GA = new GeneticAlgorithm(tc,2,6);
		Chromosome beauIdeal = GA.produceBeauIdeal();
		System.out.println(beauIdeal.phrase.toString());					
	}

}
