package jneat;

import java.util.Iterator;


/** The connection from one node to another with an associated weight.
 *  Can be marked as recurrent.
 */
public class Link {
	int id;
	
	double weight;
	
	/** Weight added to the adjusted weight */
	double bias;
	
	NNode in_node;
	NNode out_node;
	
	/** Points to the trait of parameters for genetic creation.  */
	Trait linkTrait;
	
	boolean recurrent;
	boolean time_delay;
	boolean is_traversed;
	
	/** Link-related parameters that change during Hebbian-type learning */
	double[] params;
	
	public Link(double w, NNode inode, NNode onode, boolean recur) {
		this(null, w, inode, onode, recur);
	}
	
	public Link (Trait t, double w, NNode inode, NNode onode, boolean recur) {
		weight = w;
		in_node = inode;
		out_node = onode;
		recurrent = recur;
		time_delay = false;
		is_traversed = false;
		id = JNEATGlobal.NewLinkID();
		linkTrait = t;
	}
	
	public void DeriveTrait(Trait t) {
		if (t != null) {
			for (int i = 0; i < JNEATGlobal.numTraitParams; i++) {
				params[i] = t.params[i];
			}
		} else {
			for (int i = 0; i < JNEATGlobal.numTraitParams; i++) {
				params[i] = 0.0;
			}
		}
	}
	
	public String PrintLink() {
		String ret = "LINK #" + id + " is from Node #" + in_node.id + " to Node #" + out_node.id + 
				" with weight " + weight + " and bias " + bias;
		if (recurrent) ret = ret + " RECURRENT";
		if (time_delay) ret = ret + " DELAYED";
		
		return ret;
	}
	
	public String SaveLinkHeader() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("# Link data format follows:\n");
		buf.append("# 'Link', id, bias, recurrent, time_delay, weight, in_node ID, out_node ID\n");		
		
		return buf.toString();
	}
	
	public String SaveLink() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("Link, " + id + ", ");
		buf.append(bias + ", ");
		buf.append(recurrent + ", ");
		buf.append(time_delay + ", ");
		buf.append(weight + ", ");
		buf.append(in_node.id + ", ");
		buf.append(out_node.id + "\n");
		
		return buf.toString();
	}
	
	/** Builds this link from a tokenized string, parent function is Gene 
	 */
	Link(String[] tokenized, NNode inode, NNode oNode) {
		// NOTE: Earlier tokenized items are taken up by gene
		if (!tokenized[4].equals("Link")) System.out.println("ERROR!  Save file corrupt when reading link information.");
		else {
			id = Integer.parseInt(tokenized[5]);
			bias = Double.parseDouble(tokenized[6]);
			recurrent = Boolean.parseBoolean(tokenized[7]);
			time_delay = Boolean.parseBoolean(tokenized[8]);
			weight = Double.parseDouble(tokenized[9]);
			
			in_node = inode;
			out_node = oNode;
		}
	}
}
