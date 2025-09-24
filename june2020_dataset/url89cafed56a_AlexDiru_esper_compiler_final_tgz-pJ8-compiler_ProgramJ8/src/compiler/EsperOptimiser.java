package compiler;

import java.util.ArrayList;

public class EsperOptimiser {

	
	public ParseTree optimise(ParseTree root) {
		//This is the root that will replace the unoptimised root
		ParseTree optimisedTree = new ParseTree();
		
		//Copy the root's data into the optimised tree
		optimisedTree.value = root.value;
		optimisedTree.attribute = root.attribute;
		
		//Optimise all children
		if (root.children != null && root.children.size() > 0)
			for (int i = 0; i < root.children.size(); i++)
				optimisedTree.children.add(optimise(root.children.get(i)));
		
		//Optimise the current node
		optimisedTree = optimiseNode(optimisedTree);
		
		return optimisedTree;
	}
	
	/**
	 * This will optimise arithmetic such as + a + 3 2 would become + a + 5
	 * @param root The node to optmise
	 * @return Optmised note
	 */
	public ParseTree optimiseNode(ParseTree root) {
		
		//Check for operator
		boolean add = false;
		boolean sub = false;
		boolean mult = false;
		boolean div = false;
		
		if (root.value == null || root.value.length() < 1)
			return root;
		
		//Get the operator used
		switch (root.value.charAt(0)) {
		case '+':
			add = true;
			break;
		case '-':
			sub = true;
			break;
		case '*':
			mult = true;
			break;
		case '/':
			div = true;
			break;
		}
		
		try {
			//Make sure the children are integers
			int a = Integer.parseInt(root.children.get(0).value);
			int b = Integer.parseInt(root.children.get(1).value);
			
			if (add) {
				root.value = String.valueOf(a + b);
				root.attribute = "DIGITS";
				root.children = new ArrayList<ParseTree>();
			}
			if (sub) {
				root.value = String.valueOf(a - b);
				root.attribute = "DIGITS";
				root.children = new ArrayList<ParseTree>();
			}
			if (mult) {
				root.value = String.valueOf(a * b);
				root.attribute = "DIGITS";
				root.children = new ArrayList<ParseTree>();
			}
			if (div) {
				root.value = String.valueOf(a / b);
				root.attribute = "DIGITS";
				root.children = new ArrayList<ParseTree>();
			}
			
			return root;
		} catch (Exception ex) {
			return root;
		}
	}

}
