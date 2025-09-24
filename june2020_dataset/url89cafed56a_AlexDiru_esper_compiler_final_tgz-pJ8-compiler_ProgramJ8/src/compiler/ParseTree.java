package compiler;

import java.util.ArrayList;

/**
 * A class which is used as a node of the parse tree (output from the post parser)
 */
public class ParseTree {

	public String value = "4";
	public String attribute = "0";
	public ArrayList<ParseTree> children = new ArrayList<ParseTree>();
	
	/**
	 * Prints this node and all its children
	 * @param indent The number of indents to indent the children by
	 */
	public void print(int indent) {
		
		//The string to represent child nodes of the AST
		String indentString = "";
		for (int i = 0; i < indent; i++)
			indentString += "  ";
		
		//Recursively print this node's children
		for (int i = 0; i < children.size(); i++) {
			//Print the child node's details
			System.out.println(indentString + children.get(i).value + " [ " + children.get(i).attribute + " ] ");
			children.get(i).print(indent+1);		
		}
	}

}
