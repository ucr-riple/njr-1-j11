package jneat;

import bbms.GlobalFuncs;

public class JNEATGlobal {
	
	// NOTE: Due to constructors, there may be some gaps, i.e. 
	// a new ID number is guaranteed to NOT duplicate an existing one,
	// but it is not necessarily sequential.
	
	// The Gene and Node ID numbers are NOT unique depending on how genomes are updated. 
	static int numGenes = 0;
	static int numGenomes = 0;
	static int numNetworks = 0;
	static int numNodes = 0;
	static int numLinks = 0;
	static int numSpecies = 0;
	static int numTraits = 0;
	
	// Coefficeints for evaluating the compatibility of two genomes
	static double p_disjoint_coeff = 0.1;		// Disjoint genes
	static double p_excess_coeff = 0.1;			// Excess genes
	static double p_mutdiff_coeff = 0.1;		// Weight difference between genes
	
	/** Age at which the Species starts to be penalized */
	static int p_dropoff_age = 5;
	
	/** Fitness multiplier if age exceeds dropoff_age*/
	static double p_dropoff_coeff = 0.5;	
	
	/** Organisms are considered "young" (and thus entitled to fitness multipliers) up until this age */
	static int p_age_youngOrganism = 10;
	
	/** Fitness multiplier for young organisms */
	static double p_age_significance = 1.0;
	
	
	/** Percentage of organisms that will be allowed to reproduce */
	static double p_survival_threshold = 0.5;
	
	
	// /** Size of the population is locally defined in the Population class */
	// public static int p_pop_size = 500;
	
	
	
	// Mutation probabilities
	static double p_mutate_add_link_prob = GlobalFuncs.mutateProbability;
	static double p_mutate_add_node_prob = GlobalFuncs.mutateProbability;	// 0.2
	static double p_mutate_gene_reenable_prob = GlobalFuncs.mutateProbability;
	static double p_mutate_link_trait_prob = 0.0;
	static double p_mutate_link_weights_prob = GlobalFuncs.mutateProbability;
	static double p_mutate_node_trait_prob = 0.0;
	static double p_mutate_only_prob = 1.0;
	static double p_mutate_random_trait_prob = 0.0; 
	static double p_mutate_toggle_enable_prob = GlobalFuncs.mutateProbability;
	static double p_mutate_toggle_reenable_prob = GlobalFuncs.mutateProbability;	// Probability of reenabling one gene in the genome during mutation
	static double p_mutate_weight_power = 1.0;
	static int p_newlink_tries = 1;
	
	static double p_interspecies_mate_rate = 0.0;
	static double p_mate_multipoint_prob = 0.0;
	static double p_mate_multipoint_avg_prob = 0.0;
	static double p_mate_singlepoint_prob = 0.0;
	static double p_mate_only_prob = 0.0;	
	static double p_recur_only_prob = 0.0;
	
	/** Number of babies siphoned off to the champion */
	static int p_babies_stolen = 0;
	
	/** Threshold of compatibility in which two genomes are considered of the same species */
	static double p_compat_threshold = 0.1;		
	
	/** Number of parameters in a trait */
	static int numTraitParams = 8;
	
	/** Probability of mutating a single trait parameter*/
	static double traitParamMutProb = 0.00; // 0.05
	
	/** Severity of trait mutations (by default -1 to +1) */
	static double traitMutationPower = 1.00;
	
	/** Max number of activation cycles a network will go through before declaring a disconnection error */
	static int maxActivationCycles = 30;

	/** Returns the ID of the next available species ID.  Increments the global counter. */
	public static int NewSpeciesID() {
		return numSpecies++;
	}
	
	/** Returns the ID of the next available gene ID.  Synomyous with innovation number.  Increments global counter. */
	public static int NewGeneID() {
		return numGenes++;
	}
	
	/** Returns the ID of the next available genome ID.  Increments the global counter. */
	public static int NewGenomeID() {
		return numGenomes++;
	}	
	
	/** Returns the ID of the next available network ID.  Increments the global counter. */
	public static int NewNetworkID() {
		return numNetworks++;
	}
	
	/** Returns the ID of the next available node ID.  Increments the global counter. */
	public static int NewNodeID() {
		return numNodes++;
	}
	
	/** Returns the ID of the next available link ID.  Increments the global counter. */
	public static int NewLinkID() {
		return numLinks++;
	}
	
	/** Returns the ID of the next available trait ID.  Increments the global counter. */
	public static int NewTraitID() {
		return numTraits++;
	}
	
	/** Sigmoid function for the given parameters. 
	 * 
	 * @param activesum
	 * @param slope	 * 
	 * @return
	 */
   public static double fsigmoid(double activesum,double slope) 
    {
		 return (1/(1+(Math.exp(-(slope*activesum))))); //Compressed
    }
   
   /** Duplicates a trait and returns it, or returns a newly generated Trait if a null pointer is passed.
    */
   public static Trait derive_trait(Trait t) {
	   Trait ret = new Trait();
	   	   
	   if (t != null) {
		   for (int i = 0; i < JNEATGlobal.numTraitParams; i++) {
				ret.params[i] = t.params[i];
		   }
	   }
	   
	   return ret;
   }

}
