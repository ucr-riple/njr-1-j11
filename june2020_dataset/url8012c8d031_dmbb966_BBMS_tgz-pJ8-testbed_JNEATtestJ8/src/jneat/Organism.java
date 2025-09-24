package jneat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Iterator;

/** Organisms are Genomes and Networks with fitness information (i.e. genotype and phenotype together)
 */
public class Organism {
	int generation;
	
	/** When doing training, used to ensure there is only one copy of the organism being used */
	public boolean checkout = false;
	
	/** The adjusted fitness value (adjusted by Species.AdjustFitness())*/
	public double fitness;
	
	public int fitAveragedOver = 0;
	
	/** A DEBUG variable - high fitness of champ */
	double high_fit;			
	
	/** Fitness measure that doesn't change during adjustments */
	double orig_fitness;
	
	/** Used for reporting purposes */
	double error;
	
	boolean winner;
	boolean champion;
	boolean pop_champ;			// Best in the population
	
	/** Indicates if this is a duplicate child of a champion.  Used for tracking purposes. */
	boolean pop_champ_child;	
	
	/** Indicates if there's been a change in the structure of the baby */
	boolean mut_struct_baby;
	
	/** "Has a mating in baby?" */
	boolean mate_baby;
	
	/** Marker for the destruction of inferior Organisms */
	boolean eliminate;	
	
	public Network net;		// Phenotype
	public Genome genome;		// Genotype
	public Species species;
	
	/** Number of expected children.  Fractional because this is alloted based off its proportional fitness in the population. */
	double expected_offspring;
	
	/** Number of reserved offspring for a population leader */
	int super_champ_offspring;
	
	
	public Organism (double xFitness, Genome xGenome, int xGeneration) {
		fitness = xFitness;
		orig_fitness = xFitness;
		genome = xGenome;
		net = genome.Genesis(xGenome.genome_id);
		species = null;
		expected_offspring = 0;
		generation = xGeneration;
		eliminate = false;
		error = 0.0;
		winner = false;
		champion = false;
		super_champ_offspring = 0;
		pop_champ = false;
		pop_champ_child = false;
		high_fit = 0.0;
		mut_struct_baby = false;
		mate_baby = false;
	}
	
	public String PrintOrganism() {
		String ret = "";
		
		ret = ret + "ORGANISM -[Genome ID: " + genome.genome_id + "] ";
		if (species != null) ret = ret + "Species #" + species.id + " ";
		else ret = ret + "NO SPECIES ASSIGNED ";
		if (pop_champ) ret = ret + "(POP CHAMP) ";
		else if (champion) ret = ret + "(CHAMPION) ";
		ret = ret + " Fitness: " + fitness + " with offspring = " + expected_offspring + " ";
		if (eliminate) ret = ret + ">ELIMINATE< ";
		
		if (net != null) ret += net.PrintNetwork(true); 
		
		return ret;
	}
	
	/** Builds this from a save file, parent function that calls this is Population
	 */
	Organism(BufferedReader reader, String[] result) {	
		try {
			System.out.println("Loading new organism from file.");
			genome = new Genome();
			
			genome.genome_id = Integer.parseInt(result[1]);
			champion = Boolean.parseBoolean(result[2]);
			eliminate = Boolean.parseBoolean(result[3]);
			error = Double.parseDouble(result[4]);
			expected_offspring = Double.parseDouble(result[5]);
			fitness = Double.parseDouble(result[6]);
			generation = Integer.parseInt(result[7]);
			high_fit = Double.parseDouble(result[8]);
			mate_baby = Boolean.parseBoolean(result[9]);
			mut_struct_baby = Boolean.parseBoolean(result[10]);
			orig_fitness = Double.parseDouble(result[11]);
			pop_champ = Boolean.parseBoolean(result[12]);
			pop_champ_child = Boolean.parseBoolean(result[13]);
			super_champ_offspring = Integer.parseInt(result[14]);
			winner = Boolean.parseBoolean(result[15]);
			checkout = Boolean.parseBoolean(result[16]);
			
			if (result.length > 17) System.out.println("ERROR: Line longer than it should be!");
		
			String readL;
									
			while ((readL = reader.readLine()) != null) {

				if (readL.startsWith("#")) {} 					
				else if (readL.contentEquals("")) {
					System.out.println("Finishing constructor and attempting to complete organism creation.");
					
					net = genome.Genesis(genome.genome_id);
					species = null;						
					break;
				} 			
				
				// Reading header data for organism
				else {
					// Read specific information and then pass off to organism reader
					String[] tokenizer = readL.split(", ");
					
					if (tokenizer[0].equals("Node")) {
						genome.nodes.add(new NNode(tokenizer));
					}
					
					else if (tokenizer[0].equals("Gene")) {
						// We need to look ahead on this line and pass these down to the Gene/Link
						int inodeID = Integer.parseInt(tokenizer[10]);
						int onodeID = Integer.parseInt(tokenizer[11]);
						NNode inode = null;
						NNode onode = null;
						
						Iterator<NNode> itr_node = genome.nodes.iterator();						
						while (itr_node.hasNext()) {
							NNode finger = itr_node.next();
							if (finger.id == inodeID) {
								inode = finger;
								break;
							}
						}
						
						for (int j = genome.nodes.size() - 1; j >= 0; j--) {
							NNode finger = genome.nodes.elementAt(j);
							if (finger.id == onodeID) {
								onode = finger;
								break;
							}
						}
						
						genome.genes.add(new Gene(tokenizer, inode, onode));
					}
				}
			}			
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}		
		
	}
	
	/** Averages fitness over several runs */
	public void AverageFitness(double newFit) {
		
		double oldFitness = fitness * fitAveragedOver;
		fitAveragedOver++;
		fitness = (oldFitness + newFit) / (fitAveragedOver); 
	}
	
	public void ResetAvgFitness() {
		fitness = 0.0;
		fitAveragedOver = 0;
	}
	
	public String SaveOrgHeader() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("# Organism data format follows:\n");
		buf.append("# 'Organism', ID, champion, eliminate, error, expected_offspring, fitness, generation, high_fit, mate_baby, mut_struct_baby, orig_fitness, pop_champ, pop_champ_child, super_champ_offspring, winner, checkout\n");		
		buf.append("#    + Genome\n");
		
		
		return buf.toString();
	}
	
	public String SaveOrganism() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("Organism, " + genome.genome_id + ", ");
		buf.append(champion + ", ");
		buf.append(eliminate + ", ");
		buf.append(error + ", ");
		buf.append(expected_offspring + ", ");
		buf.append(fitness + ", ");
		buf.append(generation + ", ");
		buf.append(high_fit + ", ");
		buf.append(mate_baby + ", ");
		buf.append(mut_struct_baby + ", ");
		buf.append(orig_fitness + ", ");
		buf.append(pop_champ + ", ");
		buf.append(pop_champ_child + ", ");
		buf.append(super_champ_offspring + ", ");
		buf.append(winner + ", ");
		buf.append(checkout + "\n");
		
		// Genome
		buf.append(genome.SaveGenome());
		
		return buf.toString();
	}
}
