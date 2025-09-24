package jneat;

import java.util.Iterator;
import java.util.Vector;

/** a NODE is either a NEURON or a SENSOR.
 * As a sensor, it can be loaded with a value for output.
 * As a neuron, it has a list of its incoming input signals.  Use an activation count to avoid flushing.
 */
public class NNode {
	
	/** Activation function type.  SIGMOID is default. */
	NodeFuncEnum fType;
	
	/** Node type.  NEURON or SENSOR. */
	NodeTypeEnum nType;
	
	/** Used for genetic marking of nodes.  Can be INPUT, BIAS, HIDDEN, or OUTPUT*/
	NodeLabelEnum gNodeLabel;
	
	/** Incoming activation sum prior to processing */
	double activesum;
	
	/** Total activation amount entering this node */
	public double activation;
	
	/** Activation value of the node at time t-1.*/
	public double last_activation;
	
	/** Activation value of the node at time t-2.*/
	public double prior_activation;
	
	
	boolean active_flag;
	
	boolean is_traversed;
	
	public int id;
	
	int activation_count;
	
	int inner_level;
	
	
	/** Links from other nodes to this node. */
	Vector<Link> incoming;
	/** Links from this node to other nodes. */
	Vector<Link> outgoing;

	/** Trait parameters associated with this node */
	Trait nodeTrait;
	
	/** Temporary reference to a node.  Used to generate a new genome in Genome.duplicate()*/
	NNode dup;
	
	/** A reference to a Node.  Used to generate and point from a genetic node (genotype) 
	 * to a real node (phenotype) during the 'genesis' process.  Just don't use protomatter
	 * in the genesis matrix. */
	NNode analogue;
	
	/** Vector of real values for Hebbian or other advanced function - future work */
	double[] params = new double[JNEATGlobal.numTraitParams];

	
	public NNode(NodeTypeEnum nType, NodeLabelEnum placement, Trait t, int id) {
		fType = NodeFuncEnum.SIGMOID;
		this.nType = nType;
		gNodeLabel = placement;
		activesum = 0;
		activation = 0;
		active_flag = false;
		this.id = id;
		activation_count = 0;
		last_activation = 0;
		prior_activation = 0;
		incoming = new Vector<Link>();
		outgoing = new Vector<Link>();
		nodeTrait = t;
		is_traversed = false;
		inner_level = 0;
		dup = null;
		analogue = null;
	}
	
	public NNode(NodeTypeEnum nType, int id, NodeLabelEnum placement) {
		this(nType, placement, null, id);
	}
	
	public NNode(NodeTypeEnum nType, NodeLabelEnum placement, Trait t) {
		this(nType, placement, t, JNEATGlobal.NewNodeID());
	}
	
	public NNode(NodeTypeEnum nType, NodeLabelEnum placement)
	{
		this(nType, placement, null);
	}
	
	public NNode(NodeTypeEnum nType) {				
		this(nType, NodeLabelEnum.HIDDEN, null);
		
		if (nType == NodeTypeEnum.SENSOR) gNodeLabel = NodeLabelEnum.INPUT; 
	}
	
	
	public NNode(NNode n, Trait t) {
		this(n.nType, n.gNodeLabel, t);
		this.id = n.id;	
	}
	
	/** Adds link l to the incoming links of this node. */
	public void AddIncomingLink(Link l) {
		if (l == null) {
			System.out.println("ERROR!  Attempted to link a non-existant node!");
			return;
		}
		
		incoming.addElement(l);
		System.out.println("Added incoming link to Node " + id);
	}
	
	/** Adds link l to the outgoing links of this node. */
	public void AddOutgoingLink(Link l) {
		if (l == null) {
			System.out.println("ERROR!  Attempted to link a non-existant node!");
			return;
		}
		
		outgoing.addElement(l);
		System.out.println("Added outgoing link to Node " + id);
	}
	
	public void resetNode() {
		activation_count = 0;
		activation = 0.0;
		last_activation = 0.0;
		prior_activation = 0.0;
		
		// Flush back link
		Iterator<Link> itr_link = incoming.iterator();
		while (itr_link.hasNext()) {
			Link _link = itr_link.next();
			_link.bias = 0.0;
			_link.is_traversed = false;
		}
		
		// Flush forward link
		itr_link = outgoing.iterator();
		while (itr_link.hasNext()) {
			Link _link = itr_link.next();
			_link.bias = 0.0;
			_link.is_traversed = false;
		}		
	}
	
	/** Returns the activation from the previous (T-1) step, or 0.0 if there was none */
	public double getTimeDelayActivation() {
		if (activation_count > 1) return last_activation;
		else return 0.0;
	}
	
	/** Returns the activation from the current step, or 0.0 if unactivated */
	public double getActivation() {
		if (activation_count > 0) return activation;
		else return 0.0;
	}
	
	/** Takes a trait's parameters and uses it for this node's parameters (distinct from the node trait)*/
	public void derive_trait(Trait t) {
		Trait x = JNEATGlobal.derive_trait(t);
		params = x.params;
	}
	
	/** Recursively traverses and marks nodes in the network
	 * Works from the outputs to the inputs and terminates upon reaching sensor.*/
	public boolean mark(int xlevel) {
		if (xlevel > 100) return false;		// Depth too deep
		
		// Base case
		if (this.nType == NodeTypeEnum.SENSOR) {
			this.is_traversed = true;
			return true;
		}
		
		// Recurrent case
		Iterator<Link> itr_link = this.incoming.iterator();
		boolean rc = false;
		
		while (itr_link.hasNext()) {
			Link _link = itr_link.next();
			if (!_link.in_node.is_traversed) {
				_link.in_node.is_traversed = true;
				rc = _link.in_node.mark(xlevel + 1);
				if (!rc) return false;
			}
		}
		
		return true;
	}
	
	/** Loads the sensor with a particular value, moving existing values down the history of last and prior activations.
	 * Returns false if this is not a sensor node. */
	public boolean LoadSensor(double value) {
		if (nType == NodeTypeEnum.SENSOR) {
			// Time-delayed memory
			prior_activation = last_activation;
			last_activation = activation;
			activation = value;
			activation_count++;
			
			return true;
		}
		else return false;
	}
	
	/** Recursively explores the network from outputs to inputs and returns the greatest separation between the two */
	public int depth(int xlevel, int xmax_level) {
		if (xlevel > 100) {
			System.out.println(" *** DEPTH NOT DETERMINED FOR NETWORK WITH LOOP *** ");
			return 100;
		}
		
		// Base case
		if (this.nType == NodeTypeEnum.SENSOR) return xlevel;
					
		// Recurrent case
		xlevel++;
		
		Iterator<Link> itr_link = this.incoming.iterator();
		int cur_depth = 0;		// Depth of the current node
		
		while (itr_link.hasNext()) {
			Link _link = itr_link.next();
			NNode _ynode = _link.in_node;
			
			if (!_ynode.is_traversed) {
				_ynode.is_traversed = true;
				cur_depth = _ynode.depth(xlevel, xmax_level);
				_ynode.inner_level = cur_depth - xlevel;
			}
			else {
				cur_depth = xlevel + _ynode.inner_level;
			}
			
			if (cur_depth > xmax_level) xmax_level = cur_depth;
		}
		
		return xmax_level;
	}
	
	public String PrintNode() {
		return PrintNode(false);
	}

	
	public String PrintNode(boolean showLinks) {
		String ret = "";
		
		switch (gNodeLabel) {
		case BIAS:
			ret = "Bias ";
			break;
		case INPUT:
			ret = "Input ";
			break;
		case OUTPUT: 
			ret = "Output ";
			break;
		case HIDDEN:
			ret = "Hidden ";
			break;					
		}
		
		ret += "Node " + id + " ";
		if (active_flag) ret += " ACTIVE ";
		else ret += " INACTIVE ";
		
		ret += "with " + activation_count + " activations.  ";
		ret += ">> Current: " + activation + " || Past: " + last_activation + " || Prior: " + prior_activation + " <<";
		
		if (!showLinks) return ret;
		
		
		Iterator<Link> itr_link = outgoing.iterator();
		while (itr_link.hasNext()) {
			ret += "\n  >> " + itr_link.next().PrintLink();
		}
		
		/*
		
		// Since links involve two nodes, showing this would be redundant
		itr_link = outgoing.iterator();
		while (itr_link.hasNext()) {
			ret += "\n  << " + itr_link.next().PrintLink();
		}
		*/
		
				
		return ret;				
	}
	
	public String SaveNodeHeader() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("# Node data format follows:\n");
		buf.append("# 'Node', id, fType, nType, gNodeLabel\n");		
		
		return buf.toString();
	}
	
	public String SaveNode() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("Node, " + id + ", ");
		buf.append(fType + ", ");
		buf.append(nType + ", ");
		buf.append(gNodeLabel + "\n");						
		
		return buf.toString();
	}
	
	/** Takes a tokenized string to form this Node.  See Organism for parent function.
	 * 
	 * @param tokenized
	 */
	NNode(String[] tokenized) {
		this(NodeTypeEnum.valueOf(tokenized[3]), Integer.parseInt(tokenized[1]), NodeLabelEnum.valueOf(tokenized[4]));		
		fType = NodeFuncEnum.valueOf(tokenized[2]);
		if (tokenized.length > 5) System.out.println("ERROR: Too many segments on reading node from file.");
	}
	
}
