package jneat;

/** This class records innovations so that an innovation in one genome can be compared with others in the same epoch.
 * If they are the same innovation, they can both be assigned the same innovation number.
 * This class can encode innovations that represent a new link forming or a new node being added.
 * In each case, two nodes fully specify the innovation and where it occurred (i.e. between them) */
public class Innovation {
	InnovationTypeEnum innovation_type;
	
	/** Two nodes specify where the innovation took place.  This is the input node. */
	NNode inNode;
	
	/** Two nodes specify where the innovation took place.  This is the output node. */
	NNode outNode;
	
	int innovation_num1;
	/** If this is a new node innovation, then there are two innovations (links) added for the new node */
	int innovation_num2;
	
	/** If a link is added, this is its weight */
	double new_weight;
	
	/** If a link is added, this is its connected trait */
	Trait newTrait;
	
	
	/** If a new node is created, this is the node */
	NNode newNode;
	
	/** If a new node was created, this is the innovation of the gene's link it is being stuck inside */
	int old_innovation_num;
	
	boolean recurrent;
	
	/** NEW_LINK constructor*/
	public Innovation(NNode in, NNode out, int idNum, double w, Trait t) {
		innovation_type = InnovationTypeEnum.NEW_LINK;
		inNode = in;
		outNode = out;
		innovation_num1 = idNum;
		new_weight = w;
		newTrait = t;
		
		// Sets unused parameters
		new_weight = 0;
		newNode = null;
		recurrent = false;
	}
	
	/** NEW_NODE constructor */
	public Innovation(NNode in, NNode out, int idNum1, int idNum2, NNode newNode, int oldInnov) {
		innovation_type = InnovationTypeEnum.NEW_NODE;
		inNode = in;
		outNode = out;
		innovation_num1 = idNum1;
		innovation_num2 = idNum2;
		this.newNode = newNode;
		old_innovation_num = oldInnov;
		
		// Sets unused parameters
		new_weight = 0;
		newTrait = null;
		recurrent = false;
	}
	
	public String SaveInnovHeader() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("# Innovation data format follows:\n");
		buf.append("# 'Innovation', innovation_type, innov_num1, innov_num2, new_weight, recurrent, inNode ID, outNode ID, newNode ID\n");		
		
		return buf.toString();
	}
	
	public String SaveInnov() {
		StringBuffer buf = new StringBuffer("");
		
		buf.append("Innovation, " + innovation_type + ", ");
		buf.append(innovation_num1 + ", " + innovation_num2 + ", ");
		buf.append(new_weight + ", ");
		buf.append(recurrent + ", ");		
		buf.append(inNode.id + ", ");
		buf.append(outNode.id + ", ");
		if (newNode != null) buf.append(newNode.id + "\n");
		else buf.append("null\n");				
		
		return buf.toString();
	}
}
