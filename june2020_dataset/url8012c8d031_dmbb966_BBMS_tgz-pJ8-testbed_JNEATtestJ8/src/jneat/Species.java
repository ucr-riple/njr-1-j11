package jneat;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

import bbms.GlobalFuncs;

public class Species {
	public int id;
	int age;
	
	double avg_fitness;
	double max_fitness;
	double max_fitness_ever;
	
	/** Length of time since the last update; if too old3, species will go extinct */
	int age_lastimprovement;
	
	/** Number of expected children */
	int expected_offspring;
	
	boolean newSpecies;
	boolean beenChecked;
	
	/** All organisms in the species */
	Vector<Organism> organisms;
	
	public Species(int newID, boolean novel) {
		id = newID;
		age = 1;
		avg_fitness = 0.0;
		expected_offspring = 0;
		newSpecies = novel;
		age_lastimprovement = 0;
		max_fitness = 0.0;
		max_fitness_ever = 0.0;
		organisms = new Vector<Organism>();
	}

	public Species() {
		this(JNEATGlobal.NewSpeciesID(), false);
	}
	
	public Species(int id) {
		this(id, false);
	}
	
	public int TimeSinceImprovement() {
		return (age - age_lastimprovement);
	}
	
	/** Change the fitness of the organisms in the species to higher values for very new species
	 * (to protect them from premature pruning).  
	 * 
	 * Automatically computes the average and maximum fitness for this species.
	 * */
	public void AdjustFitness() {
		int age_debt = (TimeSinceImprovement() + 1) - JNEATGlobal.p_dropoff_age;
		
		for (int i = 0; i < organisms.size(); i++) {
			Organism _organism = organisms.elementAt(i);
		
			// Remember original, unmodified fitness.
			_organism.orig_fitness = _organism.fitness;
			
			// Fitness decreases after a stagnation point (dropoff_age)
			if (age_debt >= 1) _organism.fitness *= JNEATGlobal.p_dropoff_coeff;
			
			// Fitness boost given to young children
			if (age <= JNEATGlobal.p_age_youngOrganism) _organism.fitness *= JNEATGlobal.p_age_significance;
			
			// Fitness cannot be negative
			if (_organism.fitness < 0.0) _organism.fitness = 0.0001;
			
			// Shares fitness within the species <DISABLED since organism fitness doesn't represent the species fitness
			// _organism.fitness /= organisms.size();
			
			System.out.println("*Adjusted fitness of organism " + _organism.genome.genome_id + ": " + _organism.orig_fitness + " --> " + _organism.fitness);
			
		}
		
		// Sorts the population and marks for death those after the survival_thresh * pop_size		
		Collections.sort(organisms, new OrganismComparator());
		
		
		
		// Update age of last improvement if applicable
		if (organisms.firstElement().orig_fitness > max_fitness_ever) {
			age_lastimprovement = age;
			max_fitness_ever = organisms.firstElement().orig_fitness;
		}
		
		// Determine the number that get to reproduce depending on the survival threshold
		// Checks to ensure at least one parent will survive
		int num_parents = (int) Math.floor(JNEATGlobal.p_survival_threshold * (double)organisms.size());
		if (num_parents <= 0) num_parents = 1;
		
		// Mark for death those ranked too low to be parents, as well as mark the champion
		organisms.firstElement().champion = true;
		
		int count = 1;
		Iterator<Organism> itr_organism = organisms.iterator();
		
		while (itr_organism.hasNext() && count <= num_parents) {
			Organism _organism = itr_organism.next();
			count++;
		}
		
		// At this point, all remaining organisms can be eliminated
		while (itr_organism.hasNext()) {
			itr_organism.next().eliminate = true;
		}
		
		ComputeAvgFitness();
		ComputeMaxFitness();
		
		// System.out.println("\n\n--- Sorted by fitness and end of adjust fitness ---\n" + this.PrintSpecies());
	}
	
	/** Determines the average fitness of the species and updates its attributes accordingly */
	public void ComputeAvgFitness() {
		Iterator<Organism> itr_organism = organisms.iterator();
		double total = 0.0;
		
		while (itr_organism.hasNext()) {
			total += itr_organism.next().fitness;
		}
		
		System.out.println("Avg fitness calc: " + total + " / " + organisms.size());
		
		avg_fitness = total / (double) organisms.size();
	}
	
	/** Updates the maximum fitness attribute for this species */
	public void ComputeMaxFitness() {
		Iterator<Organism> itr_organism = organisms.iterator();
		
		while (itr_organism.hasNext()) {
			Organism _organism = itr_organism.next();
			if (_organism.fitness > max_fitness) max_fitness = _organism.fitness;
		}
		
		// if (max_fitness > max_fitness_ever) max_fitness_ever = max_fitness;
	}
	
	public double CountOffspringFloat() {
		Iterator<Organism> itr_organism = organisms.iterator();
		
		double sum = 0.0;
		
		while (itr_organism.hasNext()) {
			Organism _org = itr_organism.next();
			sum += _org.expected_offspring;			
		}
		
		expected_offspring = (int) Math.round(sum);
		return sum;
	}
	
	/** Computes the collective offspring of the entire species (sum of all organisms' offspring). */
	public double CountOffspring(double skim) {
		Iterator<Organism> itr_organism = organisms.iterator();		
		
		double x1 = 0.0;
		double y1 = 1.0;
		double r1 = 0.0;
		double r2 = skim;
		int n1 = 0;
		int n2 = 0;
		
		while (itr_organism.hasNext()) {			
			Organism _organism = itr_organism.next();		
			x1 = _organism.expected_offspring;
			 
			n1 = (int) (x1 / y1);
			r1 = x1 - ((int) (x1 / y1) * y1);
			n2 = n2 + n1;
			r2 = r2 + r1;
		 
			if (r2 >= 1.0) 
			{
			   n2 = n2 + 1;
			   r2 = r2 - 1.0;
			}
		}
		
		expected_offspring = n2;
		return r2;
	}
	
	public boolean reproduce(int generation, Population pop, Vector<Species> sorted_species) {
		
		if (expected_offspring > 0 && organisms.size() == 0) {
			System.out.println("ERROR!  Attempted to reproduce an empty species!");
			return false;
		}
		
		Organism thechamp = organisms.firstElement();
		Organism mother = null;
		Organism baby = null;
		Genome newGenome = null;
		Network net_analogue = null;
		
		boolean champ_done = false;
		boolean mut_struct_baby = false;	// Indicates if we change the structure of the baby
		boolean mate_baby = false;
		
		// Create the designated number of offspring for the species one by one
		for (int i = 0; i < expected_offspring; i++) {
			
			System.out.println("DEBUG: Species #" + id + " is reproducing expected offspring #" + (i + 1) + " of " + expected_offspring);
			
			mut_struct_baby = false;
			mate_baby = false;						
			
			// If there is a super champion (population champion) finish off some special clones
			if (thechamp.super_champ_offspring > 0) {
				System.out.println("DEBUG: Super champ present in Species #" + id);
				mother = thechamp;
		
				newGenome = mother.genome.duplicate(i);
				
				if (thechamp.super_champ_offspring > 1) {
					if (GlobalFuncs.randFloat() < 0.8 || JNEATGlobal.p_mutate_add_link_prob == 0.0) {
						newGenome.MutateLinkWeight(JNEATGlobal.p_mutate_weight_power, 1.0, MutationTypeEnum.GAUSSIAN);						
					} else {
						// Occasionally add a link to a superchamp
						net_analogue = newGenome.Genesis(generation);
						newGenome.MutateAddLink(pop, JNEATGlobal.p_newlink_tries);
						mut_struct_baby = true;
					}
				} 
				
				baby = new Organism(0.0, newGenome, generation);
				
				if (thechamp.super_champ_offspring == 1) {
					if (thechamp.pop_champ){
						baby.pop_champ_child = true;
						baby.high_fit = mother.orig_fitness;
					}
				}
				
				thechamp.super_champ_offspring--;				
			}
			
			// If we have a Species champion, just clone it
			else if (!champ_done && expected_offspring > 5) {
				System.out.println("DEBUG: Cloning species champion.");
				
				mother = thechamp;
				newGenome = mother.genome.duplicate(i);		
				baby = new Organism(0.0, newGenome, generation);	// The baby takes after its mother
				champ_done = true;
			}
			
			// Performs mutation only if there is only one organism in the species, else random probability
			else if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_only_prob || (organisms.size() - 1) <= 1) {
				System.out.println("DEBUG: Performing mutation.");
				
				// Choose the random parent
				mother = organisms.elementAt(GlobalFuncs.randRange(0,  organisms.size() - 1));
				newGenome = mother.genome.duplicate(i);
				
				// Mutate according to probabilities
				if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_add_node_prob) {
					newGenome.MutateAddNode(pop);
					System.out.println("DEBUG: Add node mutation performed.");
					mut_struct_baby = true;
				}
				else if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_add_link_prob) {
					net_analogue = newGenome.Genesis(generation);
					newGenome.MutateAddLink(pop,  JNEATGlobal.p_newlink_tries);
					System.out.println("DEBUG: Add link mutation performed.");
					mut_struct_baby = true;
				}
				else {
					// If no structural mutation, then there are other kinds:
					if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_random_trait_prob) {
						newGenome.MutateRandomTrait();
						System.out.println("DEBUG: Trait mutation performed.");
					}
					
					if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_link_trait_prob) {
						newGenome.MutateLinkTrait(1);
						System.out.println("DEBUG: Link trait mutation performed.");
					}
					
					if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_node_trait_prob) {
						newGenome.MutateNodeTrait(1);
						System.out.println("DEBUG: node trait mutation performed.");
					}
					
					if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_link_weights_prob) {
						newGenome.MutateLinkWeight(JNEATGlobal.p_mutate_weight_power,  1.0,  MutationTypeEnum.GAUSSIAN);
						System.out.println("DEBUG: Link weight mutation performed.");
					}
					
					if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_toggle_enable_prob) {
						newGenome.MutateToggleEnable(1);
						System.out.println("DEBUG: Gene enabling performed.");
					}
					
					if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_toggle_reenable_prob) {
						newGenome.MutateGene_Reenable();
						System.out.println("DEBUG: Gene disabling performed.");
					}
				}
				
				baby = new Organism(0.0, newGenome, generation);	
				System.out.println("DEBUG: Newly formed organism is now #" + baby.genome.genome_id);
			}
			
			// Otherwise we should mate
			else {
				System.out.println("DEBUG: Performing regular mating.");
				// Choose random mother
				mother = organisms.elementAt(GlobalFuncs.randRange(0, organisms.size() - 1));
				Organism father = null;
				
				// Choose random father - mate within species
				if (GlobalFuncs.randFloat()> JNEATGlobal.p_interspecies_mate_rate) {
					System.out.println("DEBUG: Mating within the species.");
					father = organisms.elementAt(GlobalFuncs.randRange(0, organisms.size() - 1));
				}
				else {
					System.out.println("DEBUG: Mating outside the species.");
					// Mate outside of species
					Species randSpecies = this;		// Save current species
					// Select random species
					int numTries = 0;
					int sp_ext = 0;
					
					// Tries several times to find a different species
					while ((randSpecies == this) && numTries < 5) {
						// Choose a random species tending towards better species
						double randmult = GlobalFuncs.randGauss() / 4;
						if (randmult > 1.0) randmult = 1.0;
						
						int randSpeciesNum = (int) Math.floor((randmult * (sorted_species.size() - 1.0)) + 0.5);
						for (sp_ext = 0; sp_ext < randSpeciesNum; sp_ext++) {}
						randSpecies = sorted_species.elementAt(sp_ext);
						++numTries;						
					}
					
					father = randSpecies.organisms.firstElement();
				}
				
				if (GlobalFuncs.randFloat() < JNEATGlobal.p_mate_multipoint_prob) {
					System.out.println("DEBUG: Mating multipoint");
					newGenome = mother.genome.MateMultipoint(i, father.genome, mother.orig_fitness, father.orig_fitness);
				}
				else if (GlobalFuncs.randFloat() < JNEATGlobal.p_mate_multipoint_avg_prob / (JNEATGlobal.p_mate_multipoint_avg_prob + JNEATGlobal.p_mate_singlepoint_prob)) {
					System.out.println("DEBUG: Mating multipoint average");
					newGenome = mother.genome.MateMultiAverage(father.genome, i, mother.orig_fitness, father.orig_fitness);
				}
				else {
					System.out.println("DEBUG: Mating singlepoint");
					newGenome = mother.genome.MateSinglePoint(father.genome, i);
				}
				
				
				mate_baby = true;
				
				// Determine whether to mutate the baby's genome - done randomly or if the mom/dad are the same organism
				if ((GlobalFuncs.randFloat() > JNEATGlobal.p_mate_only_prob) || 
						(father.genome.genome_id == mother.genome.genome_id) ||
						(father.genome.Compatibility(mother.genome) == 0.0)) {
					
					System.out.println("DEBUG: Mutating the baby's genome.");
					
					
					// Mutate according to probabilities
					if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_add_node_prob) {
						newGenome.MutateAddNode(pop);
						mut_struct_baby = true;
					}
					else if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_add_link_prob) {
						net_analogue = newGenome.Genesis(generation);
						newGenome.MutateAddLink(pop,  JNEATGlobal.p_newlink_tries);
						mut_struct_baby = true;
					}
					else {
						// If no structural mutation, then there are other kinds:
						if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_random_trait_prob) {
							newGenome.MutateRandomTrait();
						}
						
						if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_link_trait_prob) {
							newGenome.MutateLinkTrait(1);
						}
						
						if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_node_trait_prob) {
							newGenome.MutateNodeTrait(1);
						}
						
						if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_link_weights_prob) {
							newGenome.MutateLinkWeight(JNEATGlobal.p_mutate_weight_power,  1.0,  MutationTypeEnum.GAUSSIAN);						
						}
						
						if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_toggle_enable_prob) {
							newGenome.MutateToggleEnable(1);
						}
						
						if (GlobalFuncs.randFloat() < JNEATGlobal.p_mutate_toggle_enable_prob) {
							newGenome.MutateGene_Reenable();
						}
					}
					
					baby = new Organism(0.0, newGenome, generation);
				}
				
				// Create the baby without mutating first
				else {
					baby = new Organism(0.0, newGenome, generation);
				}
				
				System.out.println("DEBUG: Baby creation complete.");
				System.out.println("    >: Mother is: " + mother.genome.genome_id); //PrintOrganism());
				System.out.println("    >: Father is: " + father.genome.genome_id); //PrintOrganism());
				System.out.println("    >: Baby is: " + baby.genome.genome_id); //PrintOrganism());
						
			}
			
			// Add the baby to its proper species
			// If it doesn't fit in one, create a new one
			
			baby.mut_struct_baby = mut_struct_baby;
			baby.mate_baby = mate_baby;
			Species newSpecies = null;
			
			// If list species is empty, create one
			if (pop.species.isEmpty()) {
				newSpecies = new Species(pop.last_species, true);
				pop.AddSpecies(newSpecies, baby);
			} else {
				// Looooooooop in all species 
				Iterator<Species> itr_species = pop.species.iterator();
				boolean done = false;
				
				while (!done && itr_species.hasNext()) {
					Species _species = itr_species.next();
					Organism comp_org = _species.organisms.firstElement();
					double curr_compat = baby.genome.Compatibility(comp_org.genome);
					
					// If compatible enough, adds baby to the species
					if (curr_compat < JNEATGlobal.p_compat_threshold) {
						_species.organisms.add(baby);
						baby.species = _species;
						done = true;
					}
				}
				
				if (!done) {
					newSpecies = new Species(pop.last_species, true);
					pop.AddSpecies(newSpecies, baby);
				}
			}			
		}
			
			
		return true;
	}
	
	/** Return the number of generations since the last improvement */
	public int last_improved() {
		return (age - age_lastimprovement);
	}
	
	public void RemoveOrganism(Organism org) {
		boolean rOrg= organisms.removeElement(org);
		if (!rOrg) System.out.println("ALERT: Attempted to remove a nonexistant Organism from a Species");
	}
	
	public String PrintSpecies() {
		String ret = "";
		
		ret += "SPECIES # " + id + " with age " + age + "| avg_fit=" + avg_fitness + ", max_fit = " + max_fitness;
		ret += ", max_fit_ever = " + max_fitness_ever + ", eOffspring = " + expected_offspring;
		ret += ", last improved " + age_lastimprovement + "\n";
		ret += "  This species has " + organisms.size() + " organisms.\n"; 
		ret += "  Organisms #: ";
		
		for (int i = 0; i < organisms.size(); i++) {
			ret += organisms.elementAt(i).genome.genome_id + ", ";
		}
		
		return ret;
	}
	
	public String SaveSpeciesHeader() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("# Species data format follows:\n");
		buf.append("# 'Species', id, age, age_lastimprovement, max_fitness, max_fitness_ever, newSpecies\n");
		buf.append("# <Species Org>, <species organism numbers>\n");
		
		return buf.toString();
	}
	
	public String SaveSpecies() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("Species, " + id + ", ");
		buf.append(age + ", ");
		buf.append(age_lastimprovement + ", ");
		buf.append(max_fitness + ", ");
		buf.append(max_fitness_ever + ", ");
		buf.append(newSpecies + "\n");
		
		buf.append("<Species Orgs>");
		Iterator<Organism> itr_org = organisms.iterator();
		while (itr_org.hasNext()) {
			buf.append(", " + itr_org.next().genome.genome_id);
		}
		buf.append("\n");
				
		return buf.toString();
	}
	
	/** Constructs from a saved file.  Parent function is in Population. */
	public Species(String[] tokenized) {
		this(Integer.parseInt(tokenized[1]));
				
		age = Integer.parseInt(tokenized[2]);
		age_lastimprovement = Integer.parseInt(tokenized[3]);
		max_fitness = Double.parseDouble(tokenized[4]);
		max_fitness_ever = Double.parseDouble(tokenized[5]);
		newSpecies = Boolean.parseBoolean(tokenized[6]);				
	}
}
