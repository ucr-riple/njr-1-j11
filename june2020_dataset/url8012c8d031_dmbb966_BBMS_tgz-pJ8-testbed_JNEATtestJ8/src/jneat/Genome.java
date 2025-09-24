package jneat;

import java.util.Iterator;
import java.util.Vector;

import bbms.GlobalFuncs;

public class Genome {
	
	/** Reference from this genotype to its phenotype, that is, from its genetic roots to its observable characteristics  */
	Network phenotype;
	
	public int genome_id;
	
	/** 
	 * Each Gene has a market telling when it arose historically.
	 * Therefore, these Genes can be used to speciate the population and
	 * provides an evolutionary history of innovation and link-building.
	 */
	public Vector<Gene> genes;
	public Vector<Trait> traits;
	public Vector<NNode> nodes;
	
	/** Duplicates and returns the existing Genome with the next ID in sequence */
	public Genome duplicate(int newID) {
		
		// Duplicate traits		
		Iterator<Trait> itr_trait = traits.iterator();
		Vector<Trait> traits_dup = new Vector<Trait>();
		
		while (itr_trait.hasNext()) {
			Trait _trait = (Trait)itr_trait.next();
			Trait newTrait = new Trait(_trait);
			traits_dup.add(newTrait);
		}
		
		// Duplicate Nodes
		Iterator<NNode> itr_node = nodes.iterator();
		Vector<NNode> nodes_dup = new Vector<NNode>();
		
		while (itr_node.hasNext()) {
			NNode _node = (NNode)itr_node.next();
			Trait assoc_trait = null;
			
			if (_node.nodeTrait != null) {
				itr_trait = traits_dup.iterator();
				
				while (itr_trait.hasNext()) {
					Trait _trait = (Trait)itr_trait.next();
					if (_trait.id == _node.nodeTrait.id) {
						assoc_trait = _trait;
						break;
					}
				}
			}
			
			NNode newNode = new NNode(_node, assoc_trait);
			_node.dup = newNode;
			nodes_dup.add(newNode);
		}
		
		// Duplicate Genes
		Iterator<Gene> itr_gene = genes.iterator();
		Vector<Gene> genes_dup = new Vector<Gene>();
		
		while (itr_gene.hasNext()) {
			Gene _gene = (Gene)itr_gene.next();
			
			// Point to the new nodes created in the previous step
			NNode iNode = _gene.lnk.in_node.dup;
			NNode oNode = _gene.lnk.out_node.dup;
			Trait traitPtr = _gene.lnk.linkTrait;
			
			Trait assoc_trait = null;
			
			if (traitPtr != null) {
				itr_trait = traits_dup.iterator();
				while (itr_trait.hasNext()) {
					Trait _trait = (Trait) itr_trait.next();
					if (_trait.id == traitPtr.id) {
						assoc_trait = _trait;
						break;
					}
				}
			}
			
			// Creates a new gene with pointer to the new node
			Gene newGene = new Gene(_gene, assoc_trait, iNode, oNode);
			genes_dup.add(newGene);
		}
		
		// Now generates the new genome
		return new Genome(genes_dup, traits_dup, nodes_dup, newID);						
	}
	
	
	public void MutateLinkWeight(double power, double rate, MutationTypeEnum mutation_type) {
		int num = 0;					// Counts gene placement
		int gene_total = genes.size();
		double powerMod = 1.0;			// (Supposedly) Modified power by gene number
		
		// The power of mutation will increase the farther you go into the genome.
		// This is based on the theory that older genes are more fit since they have remained the longest
		
		double randNum;
		double randChoice;						// Determines which kind of mutation to do on a gene
		double endPart = gene_total * 0.8;		// The last part of the genome
		double gaussPoint;
		double coldGaussPoint;
		
		boolean severe;		// Occasionally makes a significant mutation with 50% probability
		if (GlobalFuncs.randFloat() > 0.5) severe = true;
		else severe = false;
		
		Iterator<Gene> itr_gene = genes.iterator();
		
		while (itr_gene.hasNext()) {
			
			// Adjust mutation severity
			Gene _gene = (Gene)itr_gene.next();
			if (severe) {
				gaussPoint = 0.3;
				coldGaussPoint = 0.1;
			} else {
				if (gene_total >= 10 && num > endPart) {
					gaussPoint = 0.5;
					coldGaussPoint = 0.3;
				} else {
					gaussPoint = 1.0 - rate;
					if (GlobalFuncs.randFloat() > 0.5) coldGaussPoint = 1.0 - rate - 0.1;
					else coldGaussPoint = 1.0 - rate;					
				}
			}
			
			// Returns a random number from [-1, +1]
			randNum = GlobalFuncs.randPosNeg() * GlobalFuncs.randFloat() * power * powerMod;
			
			if (mutation_type == MutationTypeEnum.GAUSSIAN){
				randChoice = GlobalFuncs.randFloat();
				if (randChoice > gaussPoint) _gene.lnk.weight += randNum;
				else if (randChoice > coldGaussPoint) _gene.lnk.weight = randNum;
			} else if (mutation_type == MutationTypeEnum.COLD_GAUSSIAN) {
				_gene.lnk.weight = randNum;
			}
			
			_gene.mutation_num = _gene.lnk.weight;
			num += 1;
		}
	}
	
	/** Chooses a random gene, extracts the link from it, and repoints the link to a random trait.
	 * Specify the number of times you want this to be done */
	public void MutateLinkTrait(int times) {
		for (int i = 0; i < times; i++) {
			int traitNum = GlobalFuncs.randRange(0, traits.size() - 1);
			int geneNum = GlobalFuncs.randRange(0,  genes.size() - 1);
			
			// Set the link to point to the new trait
			Gene _gene = genes.elementAt(geneNum);
			_gene.lnk.linkTrait = traits.elementAt(traitNum);
		}		
	}
	
	/** Chooses a random node and repoints the node to a random trait.
	 * Specify the number of times you want this to be done */
	public void MutateNodeTrait(int times) {
		for (int i = 0; i < times; i++) {
			int traitNum = GlobalFuncs.randRange(0, traits.size() - 1);
			int nodeNum = GlobalFuncs.randRange(0, nodes.size() - 1);
			
			// Set the link to point to the new trait
			NNode _node = nodes.elementAt(nodeNum);
			_node.nodeTrait = traits.elementAt(traitNum);
		}		
	}
	
	/** Selects a random trait in this genome and mutates it. */
	public void MutateRandomTrait() {
		int traitNum = GlobalFuncs.randRange(0,  traits.size() - 1);
		Trait _trait = traits.elementAt(traitNum);
		_trait.Mutate();
	}
	
	/** Toggles a random gene between enabled or disabled.
	 * If disabling a gene will isolate part of the network, this will not do anything.
	 *  Repeats the specified number of times. */
	public void MutateToggleEnable(int times) {
		for (int i = 0; i < times; i++) {
			int geneNum = GlobalFuncs.randRange(0,  genes.size() - 1);
			Gene _gene = genes.elementAt(geneNum);
			boolean done = false;
			
			// Need to ensure that another gene connects out of the in-node, if not this will break off
			// and isolate a section of the network
			if (_gene.enabled) {
				for (int j = 0; j < genes.size(); j++) {
					Gene _jGene = genes.elementAt(j);
					if ((_gene.lnk.in_node == _jGene.lnk.in_node) && _jGene.enabled 
							&& (_jGene.innovation_num != _gene.innovation_num)) {
						done = true;
						break;
					}						
				}
				
				if (done) _gene.enabled = false;
				else System.out.println("DEBUG: Toggling gene will disconnect network.  Reenabling.");
				
				if (!verify()) {
					System.out.println("DEBUG: Toggling gene will disconnect network - reenabling.");
					_gene.enabled = true;
				}
			} else {
				_gene.enabled = true;
			}
		}
	}
	
	public boolean MutateAddLink(Population pop, int tries) {
		boolean do_recurrent = false;
		boolean found = false;
		int first_nonsensor = 0;
		int trycount = 0;
		int nodeNum1 = 0;
		int nodeNum2 = 0;
		NNode nodeA = null;
		NNode nodeB = null;
		Gene newGene = null;
		
		if (GlobalFuncs.randFloat() < JNEATGlobal.p_recur_only_prob) do_recurrent = true; 
		
		// Find the first non-sensor so the to-node won't look at sensors as possible destinations
		Iterator<NNode> itr_node = nodes.iterator();
		while (itr_node.hasNext()) {
			NNode finger = itr_node.next();
			if (finger.nType != NodeTypeEnum.SENSOR) break;
			
			first_nonsensor++;
		}
		
		while (trycount < tries) {
			if (do_recurrent) {
				// 50% probability to decide a loop recurrency, i.e. node X to node X
				// 50% probability to normal recurrency, i.e. node X to node Y
				if (GlobalFuncs.randFloat() > 0.5) {
					nodeNum1 = GlobalFuncs.randRange(first_nonsensor,  nodes.size() - 1);
					nodeNum2 = nodeNum1;
				} else {
					nodeNum1 = GlobalFuncs.randRange(0, nodes.size() - 1);
					nodeNum2 = GlobalFuncs.randRange(first_nonsensor,  nodes.size() - 1);
				}
			} 
			
			// If not recurrent
			else {
				nodeNum1 = GlobalFuncs.randRange(0,  nodes.size() - 1);
				nodeNum2 = GlobalFuncs.randRange(first_nonsensor,  nodes.size() - 1);
			}
			
			
			// Point to the object's nodes
			nodeA = nodes.elementAt(nodeNum1);
			nodeB = nodes.elementAt(nodeNum2);
			
			// Verify if the possible new gene already exists
			boolean bypass = false;
			for (int j = 0; j < genes.size(); j++) {
				Gene _gene = genes.elementAt(j);
				if (nodeB.nType == NodeTypeEnum.SENSOR) {
					bypass = true;
					break;
				}
				
				if (_gene.lnk.in_node == nodeA && _gene.lnk.out_node == nodeB) {
					if ((_gene.lnk.recurrent && do_recurrent) || 
						(!_gene.lnk.recurrent && !do_recurrent)) {
						bypass = true;
						break;
					}						
				}
			}
			
				
			if (!bypass) {
				phenotype.status = NetworkStatusEnum.NORMAL;
				boolean recurflag = phenotype.HasPath(nodeA.analogue, nodeB.analogue, 0, nodes.size() * nodes.size());
				
				if (phenotype.status == NetworkStatusEnum.HAS_LOOP) {
					System.out.println("\n LOOP DETECTED IN NETWORK #" + phenotype.net_id + " DURING RECURRENCY CHECK.");
					return false;
				}
				
				if ((!recurflag && do_recurrent) || (recurflag && !do_recurrent)) trycount++;
				else {
					trycount = tries;
					found = true;
				}
			} 
			else trycount++;	// If bypassed, this gene is not good - skip to the next cycle													
			
		} // End trycount block
		
		if (found) {
			// Check to see if this innovation already occurred in the population
			Iterator<Innovation> itr_innovation = pop.innovations.iterator();
			boolean done = false;
			
			while (!done) {
				if (!itr_innovation.hasNext()) {
					// If the phenotype doesn't exist, exit on false.
					// This should never happen
					if (phenotype == null) {
						System.out.println("ERROR!  Attempted to add link to genome with no phenotype.");
						return false;
					}
					
					// Choose a random trait
					//Trait chosenTrait = traits.elementAt(GlobalFuncs.randRange(0, traits.size() - 1));
					Trait chosenTrait = new Trait();
					
					// Choose the new weight
					double newWeight = GlobalFuncs.randPosNeg() * GlobalFuncs.randFloat() * 10.0;
					
					int curr_innov = pop.getCurNodeID_Inc();
					newGene = new Gene(chosenTrait, newWeight, nodeA, nodeB, do_recurrent, curr_innov, newWeight);
					
					// Add the innvation
					pop.innovations.add(new Innovation(nodeA, nodeB, curr_innov, newWeight, chosenTrait));
					done = true;
				}
				
				// If there are more innovations, match from the list
				else {
					Innovation _innov = itr_innovation.next();
					if (_innov.innovation_type == InnovationTypeEnum.NEW_LINK &&
							_innov.inNode.id == nodeA.id &&
							_innov.outNode.id == nodeB.id &&
							_innov.recurrent == do_recurrent) {
						
						newGene = new Gene(_innov.newTrait, _innov.new_weight, nodeA, nodeB, do_recurrent, _innov.innovation_num1, 0);
						done = true;						
					}
				}
			}
			
			genes.add(newGene);
			return true;			
		}
		
		return false;
	}
	
	public boolean MutateAddNode(Population pop) {
		int j;
		boolean found = false;
		boolean step1 = true;
		boolean step2 = false;
		
		NNode newNode = null;
		Gene newGene1 = null;
		Gene newGene2 = null;
		
		Gene _gene = null;
		
		if (genes.size() < 15) {
			step2 = false;
			for (j = 0; j < genes.size(); j++) {
				_gene = genes.elementAt(j);
				if (_gene.enabled && _gene.lnk.in_node.gNodeLabel != NodeLabelEnum.BIAS) break;
			}
			
			for (; j < genes.size(); j++) {
				_gene = genes.elementAt(j);
				if (GlobalFuncs.randFloat() >= 0.3 && _gene.lnk.in_node.gNodeLabel != NodeLabelEnum.BIAS) {
					step2 = true;
					break;
				}
			}
			
			if (step2 && _gene.enabled) found = true;
		}
		
		// For genes of large size
		else {
			int trycount = 0;
			
			while (trycount < 20 && !found) {
				// Pure random splitting
				_gene = genes.elementAt(GlobalFuncs.randRange(0, genes.size() - 1));
				if (_gene.enabled && _gene.lnk.in_node.gNodeLabel != NodeLabelEnum.BIAS) found = true;
				
				++trycount;
			}
		}
		if (!found) return false;
		
		_gene.enabled = false;
		
		// Extraction phase
		Link oldLink = _gene.lnk;
		double oldWeight = oldLink.weight;
		Trait oldTrait = oldLink.linkTrait;
		
		NNode in_node = oldLink.in_node;
		NNode out_node = oldLink.out_node;
		boolean done = false;
		Iterator<Innovation> itr_innovation = pop.innovations.iterator();
		
		while (!done) {
			if (!itr_innovation.hasNext()) {
				// This innvation has not occured in the population so needs to be created
				// By convention it will point to the first trait in the genome.
				
				int curnode_id = pop.getCurNodeID_Inc();
				newNode = new NNode(NodeTypeEnum.NEURON, curnode_id, NodeLabelEnum.HIDDEN);
				//newNode.nodeTrait = traits.firstElement();
				newNode.nodeTrait = new Trait();
				
				int gene_innov1 = pop.getCurInnov_Inc();
				newGene1 = new Gene(oldTrait, 1.0, in_node, newNode, oldLink.recurrent, gene_innov1, 0);
				
				// Re-read the current innovation with increment
				int gene_innov2 = pop.getCurInnov_Inc();
				newGene2 = new Gene(oldTrait, oldWeight, newNode, out_node, false, gene_innov2, 0);
				
				pop.innovations.add(new Innovation(in_node, out_node, gene_innov1, gene_innov2, newNode, _gene.innovation_num));
				done = true;
			}
			
			else {
				Innovation _innov = itr_innovation.next();
				if ((_innov.innovation_type == InnovationTypeEnum.NEW_NODE) &&
						(_innov.inNode.id == in_node.id) &&
						(_innov.outNode.id == out_node.id) &&
						(_innov.old_innovation_num == _gene.innovation_num)) {
					
					// Create the new genes and pass current nodeID to new node
					newNode = new NNode(NodeTypeEnum.NEURON, _innov.newNode.id, NodeLabelEnum.HIDDEN);
					//newNode.nodeTrait = traits.firstElement();
					newNode.nodeTrait = new Trait();
					
					newGene1 = new Gene(oldTrait, 1.0, in_node, newNode, oldLink.recurrent, _innov.innovation_num1, 0);
					newGene2 = new Gene(oldTrait, oldWeight, newNode, out_node, false, _innov.innovation_num2, 0);
					done = true;
				}
			}
		}
		
		// Add the new NNode and Genes to the Genome
		genes.add(newGene1);
		genes.add(newGene2);
		node_insert(nodes, newNode);
		
		return true;
	}
	
	/** Reenables one gene in the genome (the first sequentially encountered) */
	public void MutateGene_Reenable() {
		Iterator<Gene> itr_gene = genes.iterator();
		
		while (itr_gene.hasNext()) {
			Gene _gene = itr_gene.next();
			if (!_gene.enabled) {
				_gene.enabled = true;
				break;
			}
		}
	}
	
	
	/** 
	 * Generates and returns a new Network based on this genome.
	 * "Genesis!  I want it!"
	 */
	public Network Genesis(int NetworkID) {
		Network newNet = new Network(NetworkID);
		Iterator<NNode> itr_node = nodes.iterator();
		
		while (itr_node.hasNext()) {
			NNode _node = itr_node.next();
			
			// Copies the gene node for the phenotype
			NNode newNode = new NNode(_node, null);
			
			// Derive the link's parameters from the node's trait
			Trait curTrait = _node.nodeTrait;
			newNode.nodeTrait = JNEATGlobal.derive_trait(curTrait);
			
			if (_node.gNodeLabel == NodeLabelEnum.BIAS || _node.gNodeLabel == NodeLabelEnum.INPUT) {
				newNet.attachInput(newNode);
			} else if (_node.gNodeLabel == NodeLabelEnum.OUTPUT) {
				newNet.attachOutput(newNode);
			} else {
				newNet.attachHidden(newNode);
			}
			
			_node.analogue = newNode;	
		}
		
		if (genes.size() == 0) {
			System.out.println("ALERT!  There are no genes for the network generated from genome #" + genome_id);
		}
		
		if (newNet.outputs.size() == 0) {
			System.out.println("ALERT!  There are no outputs for the network generated from genome #" + genome_id);
		}
		
		Iterator<Gene> itr_gene = genes.iterator();
		
		while (itr_gene.hasNext()) {
			Gene _gene = itr_gene.next();
			
			// System.out.println("GENE INFO: " + _gene.PrintGene());
									
			// Only creates the link if the gene is enabled
			if (_gene.enabled){
				Link curLink = _gene.lnk;
				
				// System.out.println("LINK INFO: " + curLink.PrintLink());

				if (_gene.lnk == null) System.out.println ("Gene link null");
				if (curLink.in_node == null) System.out.println("ONode - oh no!");
				if (curLink.out_node == null) System.out.println("INode - oh ni!");
												
				NNode iNode = curLink.in_node.analogue;
				NNode oNode = curLink.out_node.analogue;
				
				// During population epochs, the analogues are null pointers, so we includle this as a backup				
				if (curLink.in_node.analogue == null) iNode = curLink.in_node;
				if (curLink.out_node.analogue == null) oNode = curLink.out_node;
											
				// NOTE: This line could be run through a recurrency check if desired.
				// There is no need to do this with the current implementation of NEAT.
				
				Link newLink = new Link(curLink.weight, iNode, oNode, curLink.recurrent);
				
				// System.out.println(" NEW LINK: " + newLink.PrintLink());
				


				
				oNode.incoming.add(newLink);
				iNode.outgoing.add(newLink);
				
				// System.out.println(" Outgoing Node: " + oNode.PrintNode());
				
				// Derive the link's parameters from its trait pointer
				Trait curTrait = curLink.linkTrait;
				curLink.linkTrait = JNEATGlobal.derive_trait(curTrait);
			}
		}
		
		// Attaches the genotype and phenotype
		newNet.genotype = this;
		phenotype = newNet;
				
		return newNet;
	}
	
	/** Measures the compatibility between this Genome and the one supplied in the argument.
	 * Measures by computing a linear combination of three characteristics:
	 * - Percent disjoint genes
	 * - Percent excess genes
	 * - Mutational difference within matching genes
	 * 
	 * Formula is: Disjoint_coef * pdg + excess_coef * peg + mutdiff_coef * mdmg
	 * The three coefficients are global parameters
	 */
	public double Compatibility(Genome g) {
		int max_genome_size = Math.max(genes.size(), g.genes.size());
		int j1 = 0;
		int j2 = 0;
		int excess_genes = 0;	
		int matching_genes = 0;	
		int disjoint_genes = 0;	
		
		double mut_diff = 0.0;
		
		for (int j = 0; j < max_genome_size; j++) {
			if (j1 >= genes.size()) {
				excess_genes ++;
				j2 ++;
			} else if (j2 >= g.genes.size()) {
				excess_genes ++;
				j1 ++;
			} else {
				Gene _gene1 = genes.elementAt(j1);
				Gene _gene2 = g.genes.elementAt(j2);
				
				// Extract current innovation numbers
				int p1innov = _gene1.innovation_num;
				int p2innov = _gene2.innovation_num;
				
				if (p1innov == p2innov) {
					matching_genes++;
					mut_diff += Math.abs(_gene1.mutation_num - _gene2.mutation_num);
					j1++;
					j2++;
				} else if (p1innov < p2innov) {
					j1++;
					disjoint_genes++;
				} else if (p2innov < p1innov) {
					j2++;
					disjoint_genes++;
				}
			}
		}
		
		// NOTE: (mut_diff_total / num_matching) gives the AVERAGE difference between mutation_nums for any two matching Genes
		
		return (JNEATGlobal.p_disjoint_coeff * disjoint_genes +
				JNEATGlobal.p_excess_coeff * excess_genes +
				JNEATGlobal.p_mutdiff_coeff * (mut_diff / matching_genes));				
	}
	
	/** For use in mating functions.
	 * Averages the traits from this Genome and the one passed to it.
	 */
	public Vector<Trait> AverageTraits(Genome g) {
		Vector<Trait> newTraits = new Vector<Trait>();
		
		// First, average the traits from the two parents to form the child trait
		// If one trait vector is larger, it will take those additional traits
		// unmodified into the child trait vector.
		for (int j = 0; j < Math.max(traits.size(), g.traits.size()); j++) {
			Trait _trait1 = null; 
			Trait _trait2 = null;
			if (j < traits.size()) _trait1 = traits.elementAt(j);
			if (j < g.traits.size()) _trait2 = g.traits.elementAt(j);
			
			Trait newTrait = new Trait(_trait1, _trait2);
			newTraits.add(newTrait);
		}
		
		return newTraits;		
	}
	
	
	/** Takes two genes and returns the averaged gene */
	public Gene AverageGenes (Gene geneA, Gene geneB){
		Gene avgGene = new Gene(null, null, 0.0);
		
		if (GlobalFuncs.randFloat() > 0.5) avgGene.lnk.linkTrait = geneA.lnk.linkTrait;
		else avgGene.lnk.linkTrait = geneB.lnk.linkTrait;
				
		// Average weights
		avgGene.lnk.weight = (geneA.lnk.weight + geneB.lnk.weight)/ 2.0;
		
		// Randomly takes the in and out nodes from its parent genes
		if (GlobalFuncs.randFloat() > 0.5) avgGene.lnk.in_node = geneA.lnk.in_node;
		else avgGene.lnk.in_node = geneB.lnk.in_node;
		
		if (GlobalFuncs.randFloat() > 0.5) avgGene.lnk.out_node = geneA.lnk.out_node;
		else avgGene.lnk.out_node = geneB.lnk.out_node;
		
		if (GlobalFuncs.randFloat() > 0.5) avgGene.lnk.recurrent = geneA.lnk.recurrent;
		else avgGene.lnk.recurrent = geneB.lnk.recurrent;
		
		avgGene.innovation_num = geneA.innovation_num;
		avgGene.mutation_num = (geneA.mutation_num + geneB.mutation_num) / 2.0;

		return avgGene;
	}
	
	/** Checks the chosenGene versus the newGene vector and returns if it is duplicate, i.e. should skip
	 * 
	 * @param newGenes
	 * @param chosenGene
	 * @return
	 */
	public boolean CheckGeneConflict(Vector<Gene> newGenes, Gene chosenGene) {
		boolean skipGene = false;
		
		// Check to see if the chosenGene conflicts with one already chosen
		// i.e. do they represent the same link?
		
		Iterator<Gene> itr_newGenes = newGenes.iterator();
		while (itr_newGenes.hasNext()) {
			Gene _curGene = itr_newGenes.next();
			
			if (_curGene.lnk.in_node.id == chosenGene.lnk.in_node.id &&
				_curGene.lnk.out_node.id == chosenGene.lnk.out_node.id &&
				_curGene.lnk.recurrent == chosenGene.lnk.recurrent) {
				skipGene = true;
				break;
			}
		}		
		
		return skipGene;
	}
	
	/** Adds chosenGene and associated nodes and traits 
	 */
	public void AddGene(Vector<NNode> newNodes, Vector<Trait> newTraits, Vector<Gene> newGenes, Gene chosenGene, boolean disableGene) {
		
		// IF traits are going to be implemented (which they aren't yet) fix this code:
		// int traitNum = 0;
		
		// Add chosenGene to the child
		// if (chosenGene.lnk.linkTrait == null) traitNum = traits.firstElement().id;
		// else traitNum = chosenGene.lnk.linkTrait.id - traits.firstElement().id; 
		
		NNode inode = chosenGene.lnk.in_node;
		NNode onode = chosenGene.lnk.out_node;
		NNode newinode = null;
		NNode newonode = null;
		
		boolean foundiNode = false;
		boolean foundoNode = false;
		
		// For ordering, and stuff.
		if (inode.id >= onode.id){
			NNode temp = inode;
			inode = onode;
			onode = temp;
		}
						
		// Search the inode and onode
		for (int i = 0; i < newNodes.size(); i++) {
			NNode curNode = newNodes.elementAt(i);
			if (curNode.id == inode.id){
				foundiNode = true;
				newinode = curNode;							
			}
			if (curNode.id == onode.id) {
				foundoNode = true;
				newonode = curNode;
			}
		}
		
		// Insert inode if needed
		if (!foundiNode) {
			System.out.println("DEBUG: Inserting iNode");
			
			int nodeTraitNum = 0;
			
			if (inode.nodeTrait != null) nodeTraitNum = inode.nodeTrait.id - traits.firstElement().id;						
			
			//Trait newTrait = newTraits.elementAt(nodeTraitNum);
			Trait newTrait = new Trait();
			newinode = new NNode(inode, newTrait);
			System.out.println("DEBUG: Inserted node is: " + newinode.PrintNode());
			node_insert(newNodes, newinode);						
		}				
							
		// Insert onode if needed
		if (!foundoNode) {
			System.out.println("DEBUG: Inserting oNode");
			int nodeTraitNum = 0;
			
			if (onode.nodeTrait != null) nodeTraitNum = onode.nodeTrait.id - traits.firstElement().id;
			
			//Trait newTrait = newTraits.elementAt(nodeTraitNum);
			Trait newTrait = new Trait();
			
			newonode = new NNode(onode, newTrait);
			System.out.println("DEBUG: Inserted node is: " + newonode.PrintNode());
			node_insert(newNodes, newonode);
		}
		
		System.out.println("DEBUG: Number of nodes in newNodes: " + newNodes.size());
		
		// Add the gene
		Trait newTrait = new Trait();
		Gene newGene = new Gene(chosenGene, newTrait, newinode, newonode);
		if (disableGene) {
			newGene.enabled = false;
			disableGene = false;
		}
		
		newGenes.add(newGene);				
	}
	
	public Genome MateMultipoint(int gID, Genome g, double fitness1, double fitness2) {
		
		// First, average traits
		Vector<Trait> newTraits = AverageTraits(g);		
		
		// Second, determine which genome is better.
		// The "worse" genome shouldn't be allowed to add extra structural baggage.
		// If they are equally fit, then the smaller one's disjoint and excess genes will be used.

		boolean p1better = false;
		int size1 = genes.size();
		int size2 = g.genes.size();
		
		if (fitness1 > fitness2) p1better = true;
		else if (fitness1 == fitness2 && size1 < size2) p1better = true;
				
		Vector<Gene> newGenes = new Vector<Gene>();
		Vector<NNode> newNodes = new Vector<NNode>();
		
		int j1 = 0;
		int j2 = 0;
		boolean skipGene = false;
		boolean disableGene = false;
		Gene chosenGene = null;
		
		while (j1 < size1 || j2 < size2) {
			skipGene = false;		// Defaults to not skipping a chosen gene
			disableGene = false;	
			if (j1 >= size1) {
				chosenGene = g.genes.elementAt(j2);
				j2++;
				if (p1better) skipGene = true;		// Skip excess from the worse genome
			} else if (j2 >= size2) {
				chosenGene = genes.elementAt(j1);
				j1++;
				if (!p1better) skipGene = true;		// Skip excess from the worse genome
			} else {
				Gene _p1Gene = genes.elementAt(j1);
				Gene _p2Gene = g.genes.elementAt(j2);
				
				if (_p1Gene.innovation_num == _p2Gene.innovation_num) {
					if (GlobalFuncs.randFloat() < 0.5) chosenGene = _p1Gene;
					else chosenGene = _p2Gene;
					
					// If one of the genes is disabled, the corresponding gene in the offspring
					// has a high chance of being disabled as well
					if (!_p1Gene.enabled || !_p2Gene.enabled) {
						if (GlobalFuncs.randFloat() < 0.75) disableGene = true;
					}		
					j1++;
					j2++;	
				} else if (_p1Gene.innovation_num < _p2Gene.innovation_num) {
					chosenGene = _p1Gene;
					j1++;
					if (!p1better) skipGene = true;												
				} else if (_p2Gene.innovation_num < _p1Gene.innovation_num) {
					chosenGene = _p2Gene;
					j2++;
					if (p1better) skipGene = true;
				}
			}
			
			
			if (!skipGene) skipGene = CheckGeneConflict(newGenes, chosenGene);			
			
			// Add gene if not skipped
			if (!skipGene) {
				AddGene(newNodes, newTraits, newGenes, chosenGene, disableGene);
			} 
		} // End while loop
		
		Genome newGenome = new Genome(newGenes, newTraits, newNodes, gID);
		
		boolean outputPresent = false;
		
		// Verify to ensure there are outputs
		for (int i = 0; i < newNodes.size(); i++) {
			NNode curNode = newNodes.elementAt(i);
			if (curNode.gNodeLabel == NodeLabelEnum.OUTPUT) {
				outputPresent = true;
				break;
			}
		}
		
		if (!outputPresent) {
			System.out.println("WARNING!  When conducting MateMultipoint, no output nodes found.");
			System.out.println("Genome A:\n" + this.PrintGenome());
			System.out.println("\nGenome B:\n" + g.PrintGenome());
			System.out.println("\nResulting Genome:\n" + newGenome.PrintGenome());			
		}
		return newGenome;
	}
	
	public Genome MateMultiAverage(Genome g, int gID, double fitness1, double fitness2) {
		
		// First, average traits
		Vector<Trait> newTraits = AverageTraits(g);		
		
		// Second, determine which genome is better.
		// The "worse" genome shouldn't be allowed to add extra structural baggage.
		// If they are equally fit, then the smaller one's disjoint and excess genes will be used.

		boolean p1better = false;
		int size1 = genes.size();
		int size2 = g.genes.size();
		
		if (fitness1 > fitness2) p1better = true;
		else if (fitness1 == fitness2 && size1 < size2) p1better = true;
				
		Vector<Gene> newGenes = new Vector<Gene>();
		Vector<NNode> newNodes = new Vector<NNode>();
		
		int j1 = 0;
		int j2 = 0;
		boolean skipGene = false;
		boolean disableGene = false;
		Gene chosenGene = null;
		
		while (j1 < size1 || j2 < size2) {
			skipGene = false;		// Defaults to not skipping a chosen gene
			disableGene = false;	
			if (j1 >= size1) {
				chosenGene = g.genes.elementAt(j2);
				j2++;
				if (p1better) skipGene = true;		// Skip excess from the worse genome
			} else if (j2 >= size2) {
				chosenGene = genes.elementAt(j1);
				j1++;
				if (!p1better) skipGene = true;		// Skip excess from the worse genome
			} else {
				Gene _p1Gene = genes.elementAt(j1);
				Gene _p2Gene = g.genes.elementAt(j2);
				
				if (_p1Gene.innovation_num == _p2Gene.innovation_num) {
					
					chosenGene = AverageGenes(_p1Gene, _p2Gene);
					
					// If one of the genes is disabled, the corresponding gene in the offspring
					// has a high chance of being disabled as well
					if (!_p1Gene.enabled || !_p2Gene.enabled) {
						if (GlobalFuncs.randFloat() < 0.75) disableGene = true;
					}		
					
					j1++;
					j2++;	
				} else if (_p1Gene.innovation_num < _p2Gene.innovation_num) {
					chosenGene = _p1Gene;
					j1++;
					if (!p1better) skipGene = true;												
				} else if (_p2Gene.innovation_num < _p1Gene.innovation_num) {
					chosenGene = _p2Gene;
					j2++;
					if (p1better) skipGene = true;
				}
			}			
			
			if (!skipGene) skipGene = CheckGeneConflict(newGenes, chosenGene);		
			
			// Add gene if not skipped
			if (!skipGene) {
				AddGene(newNodes, newTraits, newGenes, chosenGene, disableGene);
			} 
		} // End while loop
		
		Genome newGenome = new Genome(newGenes, newTraits, newNodes, gID);
		
		boolean outputPresent = false;
		
		// Verify to ensure there are outputs
		for (int i = 0; i < newNodes.size(); i++) {
			NNode curNode = newNodes.elementAt(i);
			if (curNode.gNodeLabel == NodeLabelEnum.OUTPUT) {
				outputPresent = true;
				break;
			}
		}
		
		if (!outputPresent) {
			System.out.println("WARNING!  When conducting MateMultipointAvg, no output nodes found.");
			System.out.println("Genome A:\n" + this.PrintGenome());
			System.out.println("\nGenome B:\n" + g.PrintGenome());
			System.out.println("\nResulting Genome:\n" + newGenome.PrintGenome());			
		}
		return newGenome;
	}
	
	public Genome MateSinglePoint(Genome g, int gID) {
		Vector<Trait> newTraits = AverageTraits(g);
		Vector<Gene> newGenes = new Vector<Gene>();
		Vector<NNode> newNodes = new Vector<NNode>();
		
		int genecounter = 0;
		int crosspoint = 0;
		int stopA = 0;
		int stopB = 0;
		int len_genome = 0;
		
		Gene avgGene = new Gene(null, null, 0.0);	// Will be filled out later in the code
		
		int size1 = genes.size();
		int size2 = g.genes.size();
		Vector<Gene> genomeA = null;
		Vector<Gene> genomeB = null;
		
		if (size1 < size2) {
			crosspoint = GlobalFuncs.randRange(0, size1 - 1);
			stopA = size1;
			stopB = size2;
			len_genome = size2;
			genomeA = genes;
			genomeB = g.genes;			
		} else {
			crosspoint = GlobalFuncs.randRange(0, size2 - 1);
			stopA = size2;
			stopB = size1;
			len_genome = size1;
			genomeA = g.genes;
			genomeB = genes;
		}
		
		// Compute the height innovation
		int last_innovB = genomeB.elementAt(stopB - 1).innovation_num;
		double cross_innov = 0.0;
		boolean done = false;
		int j1 = 0;
		int j2 = 0;
		Gene geneA = null;
		Gene geneB = null;
		Gene chosenGene = null;
		int v1 = 0;
		int v2 = 0;
		int cellA = 0;
		int cellB = 0;	
		
		while (!done) {
			boolean doneA = false;
			boolean doneB = false;
			boolean skipGene = false;
					
			if (j1 < stopA) {
				geneA = genomeA.elementAt(j1);
				v1 = geneA.innovation_num;
				doneA = true;
			}
			
			if (j2 < stopB) {
				geneB = genomeB.elementAt(j2);
				v2 = geneB.innovation_num;
				doneB = true;
			}
			
			if (doneA && doneB) {
				if (v1 < v2) {
					cellA = v1;
					cellB = 0;
					j1++;
				} else if (v1 == v2) {
					cellA = v1;
					cellB = v1;
					j1++;
					j2++;
				} else {
					cellA = 0;
					cellB = v2;
					j2++;
				}
			}
			
			else {
				if (doneA && !doneB) {
					cellA = v1;
					cellB = 0;
					j1++;
				} 
				else if (!doneA && doneB) {
					cellA = 0;
					cellB = v2;
					j2++;
				} else {
					done = true;
				}
			}
			
			if (!done) {
				
				
				// innovA = innovB
				if (cellA == cellB) {
					System.out.println("DEBUG: cellA==cellB");
					if (genecounter < crosspoint) {
						chosenGene = geneA;
						genecounter++;
					} else if (genecounter == crosspoint) {
						avgGene = AverageGenes(geneA, geneB);
						
						// If one gene is disabled, the corresponding gene in the offspring is likely disabled
						if (!geneA.enabled || !geneB.enabled) avgGene.enabled = false;
						
						chosenGene = avgGene;
						genecounter++;
						cross_innov = cellA;
					} else if (genecounter > crosspoint) {
						chosenGene = geneB;
						genecounter++;
					}
				}
				
				// innovA < innovB
				else if (cellA != 0 && cellB == 0) {
					System.out.println("DEBUG: innoA<innovB");
					if (genecounter < crosspoint) {
						chosenGene = geneA;
						genecounter++;
					} else if (genecounter == crosspoint) {
						chosenGene = geneA;
						genecounter++;
						cross_innov = cellA;
					} else if (genecounter > crosspoint) {
						if (cross_innov > last_innovB) {
							chosenGene = geneA;
							genecounter++;
						} else skipGene = true;
					}
				}
				
				// innovA > innovB 
				else {
					if (cellA == 0 && cellB != 0) {
						if (genecounter < crosspoint) skipGene = true; 			// skip geneB
						else if (genecounter == crosspoint) skipGene = true;	// skip such a highly illogical case
						else if (genecounter > crosspoint) {
							if (cross_innov > last_innovB) {
								chosenGene = geneA;
								genecounter++;
							} else {
								chosenGene = geneB;	// This is a pure case of single crossing
								genecounter++;
							}
						}
					}
				}
								
				skipGene = CheckGeneConflict(newGenes, chosenGene) || skipGene;				
				
				
				// Add gene if not skipped
				if (!skipGene) {
					/*
					System.out.println("DEBUG: New nodes are: ");
					for (int x = 0; x < newNodes.size(); x++) {
						System.out.println("--DEBUG: Node " + x + " of " + newNodes.size());
						System.out.println(newNodes.elementAt(x).PrintNode());
					}
					
					System.out.println("DEBUG: New Genes are: ");
					for (int x = 0; x < newGenes.size(); x++) {
						System.out.println("--DEBUG: Gene " + x + " of " + newGenes.size());
						System.out.println(newGenes.elementAt(x).PrintGene());
					}
					
					if (geneA == null) System.out.println("DEBUG: GeneA null");
					
					if (geneB == null) System.out.println("DEBUG: GeneB null");
					
					if (chosenGene == null) System.out.println("DEBUG: ChosenGene null");
					*/
					System.out.println("DEBUG: Chosen Gene is: " + chosenGene.PrintGene());
					
					
					AddGene(newNodes, newTraits, newGenes, chosenGene, false);	
				} 
			}
		}
				
		return new Genome(newGenes, newTraits, newNodes, gID);
	}
	
	
	/** Inserts a NNode into a NNode vector such that it remains sorted by node ID (ascending)
	 */
	public void node_insert(Vector<NNode> nlist, NNode n) {
		int i = 0;
		
		for (i = 0; i < nlist.size(); i++) {
			if (nlist.elementAt(i).id >= n.id) {
				break;
			}
		}
		
		nlist.insertElementAt(n, i);
	}
	
	public int get_next_gene_innovnum() {
		return genes.lastElement().innovation_num + 1;
	}
	
	public int get_next_nodeID() {
		return nodes.lastElement().id + 1;
	}
	
	
	public boolean verify() {
		return verify(false);
	}
	
	public boolean verify(boolean showErr) {
		if (genes.size() == 0) return false;
		if (nodes.size() == 0) return false;
		if (traits.size() == 0) return false;
		
		Iterator<Gene> itr_gene = genes.iterator();
		while (itr_gene.hasNext()) {
			Gene _gene = itr_gene.next();
			NNode inode = _gene.lnk.in_node;
			NNode onode = _gene.lnk.out_node;
			
			if (inode == null) {
				if (showErr) System.out.println(" ERROR: inode = null in genome #" + genome_id);
				return false;
			}
			if (onode == null) {
				if (showErr) System.out.println (" ERROR: onode = null in genome #" + genome_id);
				return false;
			}
			if (!nodes.contains(inode)) {
				if (showErr) System.out.println(" ERROR: inode #" + inode.id + " missing.  Defined in gene but not in vector nodes of genome #" + genome_id);
				return false;
			}
			if (!nodes.contains(onode)) {
				if (showErr) System.out.println(" ERROR: onode #" + onode.id + " missing.  Defined in gene but not in vectro nodes of genome #" + genome_id);
				return false;
			}
		}
		
		Iterator<NNode> itr_node = nodes.iterator();
		int last_id = 0;
		while (itr_node.hasNext()) {
			NNode _node = itr_node.next();
			if (_node.id < last_id) {
				if (showErr) System.out.println(" ALERT: Nodes out of order! Last node_id = " + last_id + " vs current id - " + _node.id);
				return false;				
			}
			last_id = _node.id;
		}
		
		itr_gene = genes.iterator();
		while (itr_gene.hasNext()) {
			Gene _gene = itr_gene.next();
			int iID = _gene.lnk.in_node.id;
			int oID = _gene.lnk.out_node.id;
			boolean rec = _gene.lnk.recurrent;
			
			Iterator<Gene> iGene = itr_gene;
			while (iGene.hasNext()) {
				Gene _gene1 = iGene.next();
				if (_gene1.lnk.in_node.id == iID && _gene1.lnk.out_node.id == oID && _gene1.lnk.recurrent == rec) {
					if (showErr) {				
						System.out.println(" ALERT: DUPLICATE GENES in Genome #" + genome_id + " with:");
						System.out.println("   Gene1: " + _gene.PrintGene());
						System.out.println("   Gene2: " + _gene1.PrintGene());
					}
					return false;					
				}
			}				
		}
		
		if (nodes.size() >= 500) {
			boolean disab = false;
			itr_gene = genes.iterator();
			while (itr_gene.hasNext()) {
				Gene _gene = itr_gene.next();
				
				if (!_gene.enabled && disab) {
					if (showErr) {
						System.out.println(" ALERT: 2 DISABLED GENES IN A ROW in Genome #" + genome_id + " with:");
						System.out.println("   2nd disable: " + _gene.PrintGene());
					}					
				}
				
				disab = !_gene.enabled;
			}
		}
		
		return true;
	}
	
	public String PrintGenome() {
		String ret = "---GENOME #" + genome_id + " START---\n";
		ret = ret + "Has " + genes.size() + " genes, " +  nodes.size() + " nodes, and " + traits.size() + " traits.\n";
		
		ret = ret + "\n || Node Info ||\n\n";
		
		Iterator<NNode> itr_node = nodes.iterator();
		while (itr_node.hasNext()) {
			NNode _node = itr_node.next();
			ret = ret + _node.PrintNode() + "\n\n";
		}
		
		ret = ret + "\n || Gene Info ||\n\n";
		
		Iterator<Gene> itr_gene = genes.iterator();
		while (itr_gene.hasNext()) {
			Gene _gene = itr_gene.next();
			ret = ret + _gene.PrintGene() + "\n\n";
		}
		
		ret = ret + "\n || Trait Info ||\n\n";
		
		Iterator<Trait> itr_trait = traits.iterator();
		while (itr_trait.hasNext()) {
			Trait _trait = itr_trait.next();
			ret = ret + _trait.PrintTrait() + "\n";
		}
		
		ret = ret + "\n ---GENOME END---";
		
		return ret;
	}
	
	public String Debug_DisplayConnectionMatrix(boolean[] connectionMatrix, int totalNodes) {
		String ret = "";
		for (int i = 0; i < totalNodes; i++) {
			for (int j = 0; j < totalNodes; j++) {
				if (connectionMatrix[(i * totalNodes) + j]) ret += "1";
				else ret += "0";
			}
			ret += "\n";
		}
		ret += "\n";		
		return ret;
	}
	
	/** Generates a random genome 
	 * 
	 * @param newID
	 * @param inNodes - input nodes
	 * @param outNodes - output nodes
	 * @param hidNodes
	 * @param maxNodes - maximum number of nodes in the genome
	 * @param recur
	 * @param linkprob
	 */
	public Genome(int newID, int inNodes, int outNodes, int hidNodes, int nodeMax, boolean recur, double linkprob) {
		
		//    i i i n n n n n n n n n n n n n n n n . . . . . . . . o o o o
		//    |                                   |                 ^     |
		//    |<----------- maxnode ------------->|                 |     | 
		//    |                                                     |     |
		//    |<-----------------------total nodes -----------------|---->|
		//                                                          |
		//                                                          |
		//     first output ----------------------------------------+

		// Ensures totalNodes is an index beyond the first output (see Figure A, above)
		int totalNodes = inNodes + outNodes + nodeMax;
		
		traits = new Vector<Trait>();
		nodes = new Vector<NNode>();
		genes = new Vector<Gene>();
		
		genome_id = newID;
		
		// Creates a dummy trait - for future expansion of the system.
		Trait newTrait = new Trait();
		newTrait.setTraitParam(0, 1.0);
		traits.add(newTrait);
		int firstOutput = totalNodes - outNodes + 1;
		
		// Build the input nodes
		for (int i = 1; i <= inNodes; i++) {
			NNode newNode = null;
			if (i < inNodes) newNode = new NNode(NodeTypeEnum.SENSOR, i, NodeLabelEnum.INPUT);
			else newNode = new NNode(NodeTypeEnum.SENSOR, i, NodeLabelEnum.BIAS);
			
			newNode.nodeTrait = newTrait;
			
			//System.out.println("Adding new node: " + newNode.PrintNode());
			
			nodes.add(newNode);
		}
		
		// Build the hidden nodes
		for (int i = inNodes + 1; i <= inNodes + hidNodes; i++) {
			NNode newNode = new NNode(NodeTypeEnum.NEURON, i, NodeLabelEnum.HIDDEN);
			newNode.nodeTrait = newTrait;
			
			// System.out.println("Adding new node: " + newNode.PrintNode());
			
			nodes.add(newNode);
		}
		
		// System.out.println(" FACT CHECK: i: " + inNodes + ", o: " + outNodes + ", n: " + hidNodes + ", max: " + nodeMax + ", firstO: " + firstOutput);
		
		// Build the output nodes
		for (int i = firstOutput; i <= totalNodes; i++) {
			NNode newNode = new NNode(NodeTypeEnum.NEURON, i, NodeLabelEnum.OUTPUT);
			newNode.nodeTrait = newTrait;
			
			// System.out.println("Adding new node: " + newNode.PrintNode());
			
			nodes.add(newNode);
		}
		
		
		// Create links
		boolean done = false;
		int abortCount = 0;
		int matrixDim = totalNodes * totalNodes;
		boolean[] connectionMatrix = new boolean[matrixDim];		
		
		/** If the network takes too long to form, it will switch to this higher rate and increase it steadily */
		double forcedProbability = 0.5;
		
		while (!done) {
			abortCount++;
			if (abortCount >= 20) {
				 linkprob = forcedProbability;
				 //forcedProbability += 0.01;		// Cannot do this - a fully connected network in all regards is invalid.
			}
			
			if (abortCount >= 700) {
				System.out.println("\nSEVERE ERROR in genome random creation constructor.  Exiting.");
				System.exit(12);
			}
			
			// Determine which connections to make
			for (int i = 0; i < matrixDim; i++) {
				if (GlobalFuncs.randFloat() < linkprob) connectionMatrix[i] = true;
				else connectionMatrix[i] = false;
			}
			
			// Build the connections
			int innov_num = 0;
			int gene_num = 0;
			int maxNode = inNodes + hidNodes;

			NNode new_inNode = null;
			NNode new_outNode = null;
			
			
			// System.out.println(Debug_DisplayConnectionMatrix(connectionMatrix, totalNodes));
			
			
			// Step through the connection matrix, creating connection genes
			for (int col = 1; col <= totalNodes; col++) {
				for (int row = 1; row <= totalNodes; row++) {
					// Ensures that this connection is valid
					if ((connectionMatrix[innov_num] && (col > inNodes))
						 && (col <= maxNode || col >= firstOutput)
						 && (row <= maxNode || row >= firstOutput)) {
						
						boolean create_gene = true;		// Defaults to true
						boolean flag_recurrent;
						if (col > row) flag_recurrent = false;
						else {
							if (!recur) create_gene = false;		// If recurrent links are banned
							flag_recurrent = true;
						}
						
						if (create_gene) {
							Iterator<NNode> itr_node = nodes.iterator();
							int found = 0;
							
							while (itr_node.hasNext() && found < 2) {
								NNode _node = itr_node.next();
								if (_node.id == row) {
									found++;
									new_inNode = _node;
								}
								if (_node.id == col) {
									found++;
									new_outNode = _node;
								}
							}
							
							// Creates the gene and link
							double new_weight = GlobalFuncs.randPosNeg() * GlobalFuncs.randFloat();
							Gene newGene = new Gene(newTrait, new_weight, new_inNode, new_outNode, flag_recurrent, innov_num, new_weight);
							
							genes.add(newGene);
						}						
					}						 					
					innov_num++;	
				}
			}
			
			boolean rcheck1 = verify();
			if (rcheck1) {
				Network net = this.Genesis(genome_id);
				
				boolean rcheck2 = net.IsMinimal();
				
				if (rcheck2) {
					int lx = net.max_depth();
					int dx = net.IsStabilized(lx);
					
					if ((dx == lx && !recur) || (lx > 0 && recur && dx == 0)) done = true;
				}
				
				net.genotype = null;
				this.phenotype = null;
			}
			
			if (!done) genes.clear();		// For whatever reason, this gene isn't valid - try again.
		}		
	}
	
	public Genome(Vector<Gene> g, Vector<Trait> t, Vector<NNode> n, int newID) {
		genome_id = newID;
		if (newID > JNEATGlobal.numGenomes) JNEATGlobal.numGenomes = newID;
		traits = t;
		nodes = n;
		genes = g;
		phenotype = null;		
	}
	
	public Genome() {
		this(new Vector<Gene>(), new Vector<Trait>(), new Vector<NNode>(), JNEATGlobal.NewGenomeID());		
	}
	
	public Genome(Vector<Gene> g, Vector<Trait> t, Vector<NNode> n) {
		this(g, t, n, JNEATGlobal.NewGenomeID());		
	}
	
	public String SaveGenomeHeader() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("# Genome data format follows:\n");		
		buf.append("#    + Nodes\n");
		
		
		return buf.toString();
	}
	
	public String SaveGenome() {
		StringBuffer buf = new StringBuffer("");
		
		// Nodes
		Iterator<NNode> itr_node = nodes.iterator();
		while (itr_node.hasNext()) {
			buf.append(itr_node.next().SaveNode());
		}			
		
		// Genes
		Iterator<Gene> itr_gene = genes.iterator();
		while (itr_gene.hasNext()) {
			buf.append(itr_gene.next().SaveGene());
		}
		
		// Does NOT save traits
				
		
		return buf.toString();
	}

}
