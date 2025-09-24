package com.miguel.sxl;

public class SXLNode extends SimpleNode {

	private static final String dash = "\u00C4";
	private static final String vBar = "\u00B3";
	private static final String corner = "\u00C0";
	private static final String junction = "\u00C3";
	
	public int line;
	public int col;
	
	public SXLNode(int i) {
		super(i);
	}
	public SXLNode(SXLParser p, int i) {
		super(p, i);
	}
	
	public String toString() {
		return SXLParserTreeConstants.jjtNodeName[id] + ": " + this.firstToken.image;
	}
	
	@Override
	public Object jjtAccept(SXLParserVisitor visitor, Object data) {
		//System.out.println(data.toString());
		System.out.println("Accepted visitor");
		return data;
	}
	
	public void dump(String prefix) {
		if ( prefix.equals("") ) prefix = " ";
		// Print the node
		System.out.println(toString(prefix));
		// If the node has children nodes
		if (children != null) {
			// Print the children nodes
			for (int i = 0; i < children.length; i++) {
				// Get the node
				SimpleNode n = (SimpleNode)children[i];
				// if not null, print it
				if (n != null) {
					// This section alters the prefix to show characters that outline the tree
					// more clearly.
					
					// Corner/Junction: If showing the last child, show a corner, otherwise a junction
					String cj = (i == children.length - 1) ? corner : junction;
					
					// Replace all [junction][dash] sequences in the prefix into  vertical bar and a space.
					prefix = prefix.replace(junction + dash, vBar + " ");
					// Replace all [corner][dash] sequences in the prefix into double spaces
					prefix = prefix.replace(corner + dash, "  ");
					
					// Dump the child using the prefix, the corner/junction string, and a dash and space
					n.dump(prefix + cj + dash + " ");
				}
			}
		}
	}
	
	
	/**
	 * Returns the position of the node's token in the input.
	 * 
	 * @return A string in the format "on line <line> column <column>"
	 */
	public String getPosition() {
		return "on line " + this.firstToken.beginLine + " column " + this.firstToken.beginColumn;
	}
	
	
	/**
	 * Returns the hash id of the node/
	 */
	public String hashID() {
		return "#" + this.firstToken.beginLine + ":" + this.firstToken.beginColumn;
	}
}
