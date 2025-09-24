package compiler;

import java.util.ArrayList;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.Tree;

public class EsperPostParser {
	
	private ParseTree parseTree;
	public ArrayList<VariableInformation> variableList = new ArrayList<VariableInformation>();
	
	//Keep track of undeclared variables so the error messages are only displayed once
	public ArrayList<String> undeclaredVariables = new ArrayList<String>();
	
	public void getVariableList() {
		getNodeVariableList(parseTree);
	}
	
	private void getNodeVariableList(ParseTree node) {
		
		if (node.attribute.equals("DECLARE"))
			if (!containsVariable(node.children.get(0).value))
				variableList.add(new VariableInformation(node.children.get(0).value, node.children.get(1).value));
		
		//Iterate children
		for (int i = 0; i < node.children.size(); i++)
			getNodeVariableList(node.children.get(i));
		
		//Check for undeclared variables
		for (int i = 0; i < node.children.size(); i++)
			checkUndeclaredVariable(node.children.get(i));
	}
	
	/**
	 * Checks if a node (and its children) contains an undeclared variable
	 * @param node The node to check
	 */
	private void checkUndeclaredVariable(ParseTree node) {
	
		if (node.attribute.equals("IDENTIFIER") && !containsVariable(node.value) && !undeclaredVariables.contains(node.value)) {
			System.out.println("Undeclared Variable: " + node.value);
			undeclaredVariables.add(node.value);
		}
	
		for (int i = 0; i < node.children.size(); i++)
			checkUndeclaredVariable(node.children.get(i));
	}
	
	/**
	 * If the variable list has a variable with a certain name
	 * @param name The name to check for
	 * @return The existance of the variable
	 */
	private boolean containsVariable(String name) {
		for (VariableInformation var : variableList)
			if (var.name.equals(name))
				return true;
		return false;
	}
	
	/**
	 * Converts ANTLRs CommonTree to a ParseTree to aid code generation
	 * @param ast The syntax tree output from the parser
	 * @return The root node of the parse tree
	 */
	public ParseTree getParseTree(CommonTree ast) {
		
		parseTree = new ParseTree();
		
		//Add the root node
		parseTree.value = ast.getText();
		parseTree.attribute = EsperCompiler.getTokenName(Integer.parseInt(Integer.toString(ast.getType())));

		//Add the children
		for (int i = 0; i < ast.getChildCount(); i++)
			parseTree.children.add(convertNode(ast.getChild(i)));
		
		return parseTree;
	}
	
	/**
	 * Converts a node (and it's children) to a parse tree
	 * @param ast The node to convert
	 * @return The root of the parse tree generated from the node
	 */
	private ParseTree convertNode(Tree ast) {

		ParseTree parent = new ParseTree();
		parent.value = ast.toString();
		parent.attribute = EsperCompiler.getTokenName(Integer.parseInt(Integer.toString(ast.getType())));
		
		for (int i = 0; i < ast.getChildCount(); i++) {
			parent.children.add(convertNode(ast.getChild(i)));
		}
		
		return parent;
	}
	
}
