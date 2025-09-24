package xx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.namespace.QName;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import yyy.NodeNameTest;
import yyy.NodeTest;
import yyy.NodeTypeTest;
import yyy.Step;
import zz.NodeComparator;

/**
 */
public class MonCompiler implements Compiler {

	private Document document;
	private List<Node> currentContext;
	public static final short LOCATIONPATH = 13;
	public static final short NODE_NAME_TEST = 14;
	public static final short NODE_TYPE_TEST = 15;
	public static final short NODE_ATTRIBUT_TEST = 16;
	public static final short NODE_TYPE_NUMBER = 17;
	private static final short NODE_TYPE_BOOLEAN = 18;
	private static final short NODE_TYPE_RELATIVE_LOCATIONPATH = 19;
	private static final short NODE_TYPE_FUNCTION_CALL = 20;
	private static final short NODE_TYPE_STRING = 21;
	private static int last_axe = 0;

	private boolean isPredicate = false;

	/**
	 * Constructor for MonCompiler.
	 * @param document Document
	 */
	public MonCompiler(Document document) {

		this.document = document;
		this.currentContext = new ArrayList<>();
	}

	/**
	 * Method number.
	 * @param value String
	 * @return Object
	 * @see xx.Compiler#number(String)
	 */
	@Override
	/**
	 * 
	 * retourne un object de type nombre
	 */
	public Object number(String value) {
		NodeTest ret = new NodeTest();
		ret.setNodeValue(Utils.asString(Utils.asNumber(value)));
		ret.setNodeType(NODE_TYPE_NUMBER);
		return ret;
	}

	/**
	 * retourne le parametre en String
	 * @param value String
	 * @return Object
	 * @see xx.Compiler#literal(String)
	 */
	@Override
	public Object literal(String value) {
		NodeTest ret = new NodeTest();
		ret.setNodeValue(Utils.asString((value)));
		ret.setNodeType(NODE_TYPE_STRING);
		return ret;
	}

/**
	 * [12]   	STag	   ::=   	'<' QName (S Attribute)* S? '>'	
	 * [13]   	ETag	   ::=   	'</' QName S? '>' 
	 * [14]   	EmptyElemTag	   ::=   	'<' QName (S Attribute)* S? '/>'
	 * @param prefix String
 * @param name String
 * @return Object
 * @see xx.Compiler#qname(String, String)
 */
	@Override
	public Object qname(String prefix, String name) {
		return new QName(prefix, name);
	}

	/**
	 * Si aucun argument n'est un chemin relative retourne la somme des argument
	 * Si un des argument est un chemin relative on ajoute un appel a la
	 * fonction sum dans la pile des fonctions
	 * @param arguments Object[]
	 * @return Object
	 * @see xx.Compiler#sum(Object[])
	 */
	@Override
	public Object sum(Object[] arguments) {
		NodeTest ret = new NodeTest();
		int val = 0;
		NodeTest[] nodes = toNodeTestArray(arguments);
		if (hasType(nodes, NODE_TYPE_RELATIVE_LOCATIONPATH)) {

			ret.getFunctionCalls().put("sum", nodes);
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		}

		for (int i = 0; i < arguments.length; i++) {
//			System.out.print(getCurrentMethodName() + " ");
			if (((NodeTest) arguments[i]).getNodeType() == NODE_TYPE_NUMBER) {
				val += Utils.asDouble(((NodeTest) arguments[i]).getNodeValue());
				ret.setNodeType(NODE_TYPE_NUMBER);
				ret.setNodeValue(Utils.asString(val));
				//System.out.println(Utils.asDouble(((NodeTest) arguments[i]).getNodeValue()));

			}
		}
		;
		return ret;
	}

	/**
	 * Si aucun argument n'est un chemin relative retourne la soustraction des
	 * argument Si un des argument est un chemin relative on ajoute un appel a
	 * la fonction minus dans la pile des fonctions
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#minus(Object, Object)
	 */
	@Override
	public Object minus(Object left, Object right) {
		NodeTest ret = new NodeTest();
		double val = 0;

		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH
				|| ((NodeTest) right).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("minus",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		}
		if (((NodeTest) left).getNodeType() == NODE_TYPE_NUMBER
				&& ((NodeTest) right).getNodeType() == NODE_TYPE_NUMBER) {
			val = Utils.asDouble(((NodeTest) left).getNodeValue())
					- Utils.asDouble(((NodeTest) right).getNodeValue());

			ret.setNodeType(NODE_TYPE_NUMBER);
			ret.setNodeValue(Utils.asString(val));
		}
		return ret;
	}

	/**
	 * Si aucun argument n'est un chemin relative retourne la multiplication des
	 * argument Si un des argument est un chemin relative on ajoute un appel a
	 * la fonction multiply dans la pile des fonctions
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#multiply(Object, Object)
	 */
	@Override
	public Object multiply(Object left, Object right) {
		//System.out.println(getCurrentMethodName() + left + "\t" + right); // TODO
		NodeTest ret = new NodeTest();
		double val = 0;

		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH
				|| ((NodeTest) right).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("multiply",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		}
		if (((NodeTest) left).getNodeType() == NODE_TYPE_NUMBER
				&& ((NodeTest) right).getNodeType() == NODE_TYPE_NUMBER) {
			val = Utils.asDouble(((NodeTest) left).getNodeValue())
					* Utils.asDouble(((NodeTest) right).getNodeValue());

			ret.setNodeType(NODE_TYPE_NUMBER);
			ret.setNodeValue(Utils.asString(val));
		}
		return ret;
	}

	/**
	 * Si aucun argument n'est un chemin relative retourne la divide des
	 * argument Si un des argument est un chemin relative on ajoute un appel a
	 * la fonction divide dans la pile des fonctions
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#divide(Object, Object)
	 */
	@Override
	public Object divide(Object left, Object right) {
		NodeTest ret = new NodeTest();
		double val = 0;
		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH
				|| ((NodeTest) right).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("divide",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		}
		if (((NodeTest) left).getNodeType() == NODE_TYPE_NUMBER
				&& ((NodeTest) right).getNodeType() == NODE_TYPE_NUMBER) {
			val = Utils.asDouble(((NodeTest) left).getNodeValue())
					/ Utils.asDouble(((NodeTest) right).getNodeValue());

			ret.setNodeType(NODE_TYPE_NUMBER);
			ret.setNodeValue(Utils.asString(val));
		}
		return ret;
	}

	/**
	 * Si aucun argument n'est un chemin relative retourne la mod des argument
	 * Si un des argument est un chemin relative on ajoute un appel a la
	 * fonction mod dans la pile des fonctions
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#mod(Object, Object)
	 */
	@Override
	public Object mod(Object left, Object right) {
		NodeTest ret = new NodeTest();
		double val = 0;

		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH
				|| ((NodeTest) right).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("mod",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		}
		if (((NodeTest) left).getNodeType() == NODE_TYPE_NUMBER
				&& ((NodeTest) right).getNodeType() == NODE_TYPE_NUMBER) {
			val = Utils.asDouble(((NodeTest) left).getNodeValue())
					% Utils.asDouble(((NodeTest) right).getNodeValue());
			ret.setNodeType(NODE_TYPE_NUMBER);
			ret.setNodeValue(Utils.asString(val));
		}

		return ret;
	}

	/**
	 * Si aucun argument n'est un chemin relative retourne la lessThan des
	 * argument Si un des argument est un chemin relative on ajoute un appel a
	 * la fonction lessThan dans la pile des fonctions
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#lessThan(Object, Object)
	 */
	@Override
	public Object lessThan(Object left, Object right) {
		NodeTest ret = new NodeTest();
		boolean val = false;
		ret.setNodeType(NODE_TYPE_BOOLEAN);
		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH
				|| ((NodeTest) right).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("lessThan",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else if (((NodeTest) left).getNodeType() == NODE_TYPE_FUNCTION_CALL) {

			ret.getFunctionCalls().put("lessThan",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else if (((NodeTest) left).getNodeType() == NODE_TYPE_NUMBER
				&& ((NodeTest) right).getNodeType() == NODE_TYPE_NUMBER) {
			val = Utils.asDouble(((NodeTest) left).getNodeValue()) < Utils
					.asDouble(((NodeTest) right).getNodeValue());
		} else {
			String s1 = ((NodeTest) left).getNodeValue();
			String s2 = ((NodeTest) right).getNodeValue();
			val = s1.compareTo(s2) < 0;
		}

		ret.setNodeValue(Utils.asString(val));
		//System.out.println(getCurrentMethodName() + val + " val ");
		return ret;
	}

	/**
 * 
 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#lessThanOrEqual(Object, Object)
	 */
	@Override
	public Object lessThanOrEqual(Object left, Object right) {
		NodeTest ret = new NodeTest();
		boolean val = false;
		ret.setNodeType(NODE_TYPE_BOOLEAN);
		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH
				|| ((NodeTest) right).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("lessThanOrEqual",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else {
			String s1 = ((NodeTest) left).getNodeValue();
			String s2 = ((NodeTest) right).getNodeValue();
			val = s1.compareTo(s2) <= 0;
		}
		ret.setNodeValue(Utils.asString(val));
		//System.out.println(getCurrentMethodName() + val + " val ");
		return ret;
	}

	/**
 * 
 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#greaterThan(Object, Object)
	 */
	@Override
	public Object greaterThan(Object left, Object right) {
		//System.out.println(getCurrentMethodName() + left + "\t" + right); // TODO
		NodeTest ret = new NodeTest();
		boolean val = false;
		ret.setNodeType(NODE_TYPE_BOOLEAN);
		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH
				|| ((NodeTest) right).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("greaterThan",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else if (((NodeTest) left).getNodeType() == NODE_TYPE_NUMBER
				&& ((NodeTest) right).getNodeType() == NODE_TYPE_NUMBER) {
			val = Utils.asDouble(((NodeTest) left).getNodeValue()) > Utils
					.asDouble(((NodeTest) right).getNodeValue());
		} else {
			String s1 = ((NodeTest) left).getNodeValue();
			String s2 = ((NodeTest) right).getNodeValue();
			val = s1.compareTo(s2) > 0;
		}
		//System.out.println(getCurrentMethodName() + val + " val ");
		ret.setNodeValue(Utils.asString(val));
		return ret;
	}

	/**
 * 
 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#greaterThanOrEqual(Object, Object)
	 */
	@Override
	public Object greaterThanOrEqual(Object left, Object right) {
		//System.out.println(getCurrentMethodName() + left + "\t" + right); // TODO
		NodeTest ret = new NodeTest();
		boolean val = false;
		ret.setNodeType(NODE_TYPE_BOOLEAN);
		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("greaterThanOrEqual",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else {
			String s1 = ((NodeTest) left).getNodeValue();
			String s2 = ((NodeTest) right).getNodeValue();
			val = s1.compareTo(s2) >= 0;
		}

		ret.setNodeValue(Utils.asString(val));
		return ret;
	}

	/**
	 * 
	 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#equal(Object, Object)
	 */

	@Override
	public Object equal(Object left, Object right) {
	//	System.out.println(getCurrentMethodName() + left + "\t" + right); // TODO
		NodeTest ret = new NodeTest();
		boolean val = false;
		ret.setNodeType(NODE_TYPE_BOOLEAN);
		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("equal",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else {
			String s1 = ((NodeTest) left).getNodeValue();
			String s2 = ((NodeTest) right).getNodeValue();
			val = s1.equals(s2);
		}

		ret.setNodeValue(Utils.asString(val));
		return ret;
	}

	/**
 * 
 * @param left Object
	 * @param right Object
	 * @return Object
	 * @see xx.Compiler#notEqual(Object, Object)
	 */
	@Override
	public Object notEqual(Object left, Object right) {
		//System.out.println(getCurrentMethodName() + left + "\t" + right); // TODO
		NodeTest ret = new NodeTest();
		boolean val = false;
		ret.setNodeType(NODE_TYPE_BOOLEAN);
		if (((NodeTest) left).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH
				|| ((NodeTest) right).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			ret.getFunctionCalls().put("notEqual",
					new NodeTest[] { ((NodeTest) left), ((NodeTest) right) });
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else {
			String s1 = ((NodeTest) left).getNodeValue();
			String s2 = ((NodeTest) right).getNodeValue();
			val = !s1.equals(s2);
		}

		ret.setNodeValue(Utils.asString(val));
		return ret;
	}

	/**
	 * TODO
	 * @param argument Object
	 * @return Object
	 * @see xx.Compiler#minus(Object)
	 */
	@Override
	public Object minus(Object argument) {
		//System.out.println(getCurrentMethodName());
		return "minus";
	}

	/**
	 * TODO
	 * @param qName Object
	 * @return Object
	 * @see xx.Compiler#variableReference(Object)
	 */
	@Override
	public Object variableReference(Object qName) {
		//System.out.println(getCurrentMethodName()); // TODO
		return "variablereferenec";
	}

	/**
	 * Ajout l'appel a la fonction dans la stack
	 * @param code int
	 * @param args Object[]
	 * @return Object
	 * @see xx.Compiler#function(int, Object[])
	 */
	@Override
	public Object function(int code, Object[] args) {
		NodeTest ret = new NodeTest();
		boolean val = false;

		ret.getFunctionCalls().put("" + code, toNodeTestArray(args));
		ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);

		ret.setNodeValue(Utils.asString(val));
		return ret;
	}

	/**
	 * TODO
	 * @param name Object
	 * @param args Object[]
	 * @return Object
	 * @see xx.Compiler#function(Object, Object[])
	 */
	@Override
	public Object function(Object name, Object[] args) {
		//System.out.println(getCurrentMethodName()); // TODO
		//System.out.println(getCurrentMethodName() + "code " + name); // TODO
		return "fonction2";
	}

	/**
	 * Si aucune argument n'est un chemin relative retourn le et logic des
	 * parametre sinon ajoute un appel a la fonction dans la stack
	 * @param arguments Object[]
	 * @return Object
	 * @see xx.Compiler#and(Object[])
	 */
	@Override
	public Object and(Object[] arguments) {
		NodeTest ret = new NodeTest();
		NodeTest[] nodes = toNodeTestArray(arguments);
		ret.setNodeType(NODE_TYPE_BOOLEAN);
		if (hasType(nodes, NODE_TYPE_RELATIVE_LOCATIONPATH)) {

			ret.getFunctionCalls().put("and", nodes);
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else {
			boolean val = true;
			for (int i = 0; i < nodes.length; i++) {
				val = val && Utils.asBoolean(nodes[i].getNodeValue());
			}
			ret.setNodeValue(Utils.asString(val));
		}
		return ret;
	}

	/**
	 * 
	 * @param arguments
	
	 * @return Object
	 */
	public Object not(Object[] arguments) {
		NodeTest ret = new NodeTest();
		NodeTest[] nodes = toNodeTestArray(arguments);
		ret.setNodeType(NODE_TYPE_BOOLEAN);
		if (hasType(nodes, NODE_TYPE_RELATIVE_LOCATIONPATH)) {

			ret.getFunctionCalls().put("not", nodes);
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else {
			boolean val = !Utils.asBoolean(nodes[0].getNodeValue());

			ret.setNodeValue(Utils.asString(val));
		}
		return ret;
	}

	/**
	 * retourne si un des argument est d'un type donnee
	 * 
	 * @param nodes
	 * @param nodeType
	
	 * @return boolean
	 */
	private boolean hasType(NodeTest[] nodes, short nodeType) {
		// TODO Auto-generated method stub
		for (NodeTest node : nodes) {
			if (node.getNodeType() == nodeType) {
				return true;
			}
		}
		return false;
	}

	/**
 * 
 * @param arguments Object[]
	 * @return Object
	 * @see xx.Compiler#or(Object[])
	 */
	@Override
	public Object or(Object[] arguments) {
		NodeTest ret = new NodeTest();
		NodeTest[] nodes = toNodeTestArray(arguments);
		ret.setNodeType(NODE_TYPE_BOOLEAN);
		if (hasType(nodes, NODE_TYPE_RELATIVE_LOCATIONPATH)) {

			ret.getFunctionCalls().put("or", nodes);
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		} else {
			boolean val = false;
			for (int i = 0; i < nodes.length; i++) {
				val = val || Utils.asBoolean(nodes[i].getNodeValue());
			}

			ret.setNodeValue(Utils.asString(val));
		}
		return ret;
	}

	/**
	 * 
	 * @param arguments Object[]
	 * @return Object
	 * @see xx.Compiler#union(Object[])
	 */
	@Override
	public Object union(Object[] arguments) {
		NodeTest ret = new NodeTest();
		ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
		List<Node> val = new ArrayList<>();
		for (Object o : arguments) {
			for (Node node : (List<Node>) o) {
				val.add(node);
			}

		}
		return val;
	}

	/**
	 * 
	 * @param qname Object
	 * @return Object
	 * @see xx.Compiler#nodeNameTest(Object)
	 */
	@Override
	public Object nodeNameTest(Object qname) {
		return new NodeNameTest((QName) qname);
	}

	/**
 * 
 * @param nodeType int
	 * @return Object
	 * @see xx.Compiler#nodeTypeTest(int)
	 */
	@Override
	public Object nodeTypeTest(int nodeType) {
		return new NodeTypeTest(nodeType);
	}

	/**
 * 
 * @param instruction String
	 * @return Object
	 * @see xx.Compiler#processingInstructionTest(String)
	 */
	@Override
	public Object processingInstructionTest(String instruction) {
		NodeTypeTest ret = new NodeTypeTest(NODE_TYPE_PI);
		ret.setNodeValue(instruction);
		return ret;
	}

	/**
	 * 
	 * @param axis int
	 * @param nodeTest Object
	 * @param predicates Object[]
	 * @return Object
	 * @see xx.Compiler#step(int, Object, Object[])
	 */
	@Override
	public Object step(int axis, Object nodeTest, Object[] predicates) {

		return new Step(axis, (NodeTest) nodeTest, toElementArray(predicates));
	}

	/**
	 * intrepreter les chemin absolue ou relatif
	 * @param absolute boolean
	 * @param steps Object[]
	 * @return Object
	 * @see xx.Compiler#locationPath(boolean, Object[])
	 */
	@Override
	public Object locationPath(boolean absolute, Object[] steps) {
		// initier le context
		if (currentContext == null || currentContext.size() == 0) {
			addNodeToArray(document, currentContext);
		}

		// si il s'agit d'un predicat il suffi d'ajouter les step dans la stack
		// aucun traitement ne peut se faire a l'instant
		if (isPredicate) {
			NodeTest ret = new NodeTest();
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
			ret.setSteps(toStepArray(steps));
			return ret;

		}
		// si le chemin ne contient aucune step NOP
		if (steps.length == 0) {
			return currentContext;
		}
		for (int i = 0; i < steps.length; i++) {
			((Step) steps[i]).getAxis();
			((Step) steps[i]).getNodeTest();
			// selon les step on va ajouter des noeud au context
			Object result = createContextForStep(currentContext,
					((Step) steps[i]).getAxis(),
					((Step) steps[i]).getNodeTest());

			sortContext((List<Node>) result);
			currentContext = (result instanceof NodeList) ? nodeListToArray((NodeList) result)
					: (ArrayList<Node>) result;
			
			// on applique les predicat au context
			Node[] predicates = ((Step) steps[i]).getPredicates();
			if (predicates != null) {
				for (int j = 0; j < predicates.length; j++) {
					currentContext = addPredicate(currentContext,
							(NodeTest) predicates[j]);
				}
			}
		}
		return currentContext;
	}

	/**
	 * Evaluer le context dans un predicat 
	 * @param context
	 * @param steps
	
	 * @return Object
	 */
	public Object locationPath(List<Node> context, Object[] steps) {

		if (isPredicate) {
			NodeTest ret = new NodeTest();
			ret.setNodeType(NODE_TYPE_RELATIVE_LOCATIONPATH);
			ret.setSteps(toStepArray(steps));
			return ret;

		}
		if (steps.length == 0) {
			return context;
		}
		for (int i = 0; i < steps.length; i++) {
			  ((Step) steps[i]).getAxis();
			 ((Step) steps[i]).getNodeTest();
			Object result = createContextForStep(context,
					((Step) steps[i]).getAxis(),
					((Step) steps[i]).getNodeTest());

			context = (result instanceof NodeList) ? nodeListToArray((NodeList) result)
					: (ArrayList<Node>) result;
			Node[] predicates = ((Step) steps[i]).getPredicates();
			if (predicates != null) {
				for (int j = 0; j < predicates.length; j++) {
					context = addPredicate(context, predicates[j]);
				}
			}
		}
		return context;
	}
/**
 * Evaluer les predicat 
 * @param context
 * @param node

 * @return List<Node>
 */
	private List<Node> addPredicate(List<Node> context, Node node) {
		List<Node> tmp = new ArrayList<>();
		if (((NodeTest) node).getNodeType() == NODE_TYPE_NUMBER) {
			if (last_axe == Compiler.AXIS_PRECEDING_SIBLING) {
				Collections.reverse(context);
			}
			tmp.add(context.get(-1
					+ (int) Utils.asDouble(((NodeTest) node).getNodeValue())));
			if (last_axe == Compiler.AXIS_PRECEDING_SIBLING) {
				Collections.reverse(context);
			}
		}
		if (((NodeTest) node).getNodeType() == NODE_TYPE_BOOLEAN) {
			if (Utils.asBoolean(((NodeTest) node).getNodeValue())) {
				return context;
			}
		}
		if (((NodeTest) node).getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			List<Node> predResult = new ArrayList<>();
			int last = context.size();
			int cur = 0;
			for (Node n : context) {
				if (n instanceof DocumentType) {
					continue;
				}

				predResult.clear();
				cur++;
				predResult.add(n);
				if (((NodeTest) node).getFunctionCalls().size() > 0) {
					// if (((NodeTest) node).getFunctionCalls().containsKey(
					// "equal")) {
					// predResult = (List<Node>) locationPath(
					// predResult,
					// ((NodeTest) node).getFunctionCalls().get(
					// "equal")[0].getSteps());
					// for (Node e : predResult) {
					// boolean test;
					// if (e instanceof Attr) {
					// String v = ((Attr) e).getValue();
					// NodeTest leftValue = new NodeTest();
					// leftValue.setNodeValue(Utils.asString(Utils
					// .asNumber(v)));
					// leftValue.setNodeType(NODE_TYPE_NUMBER);
					// test = Utils.asBoolean(equal(leftValue,
					// ((NodeTest) node).getFunctionCalls()
					// .get("equal")[1]));
					// } else {
					//
					// String v = Utils.asString(e);
					// NodeTest leftValue = new NodeTest();
					// leftValue.setNodeValue(Utils.asString(v));
					// leftValue.setNodeType(NODE_TYPE_STRING);
					// test = Utils.asBoolean(equal(leftValue,
					// ((NodeTest) node).getFunctionCalls()
					// .get("equal")[1]));
					// // test=false;
					// }
					//
					// if (test && !tmp.contains(n)) {
					// tmp.add(n);
					// }
					// }
					// }
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"equal")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "equal");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(equal(args[0],
									args[1]));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"notEqual")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "notEqual");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(notEqual(args[0],
									args[1]));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"divide")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "divide");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(divide(args[0],
									args[1]));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"multiply")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "multiply");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(multiply(args[0],
									args[1]));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"minus")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "minus");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(minus(args[0],
									args[1]));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey("sum")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "sum");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(sum(args));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}

					if (((NodeTest) node).getFunctionCalls().containsKey(
							"lessThan")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "lessThan");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(lessThan(args[0],
									args[1]));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}
					// if (((NodeTest) node).getFunctionCalls().containsKey(
					// "lessThan")) {
					// NodeTest left = ((NodeTest) node).getFunctionCalls()
					// .get("lessThan")[0];
					// predResult = (List<Node>) locationPath(predResult,
					// left.getSteps());
					// for (Node e : predResult) {
					// String v = ((Attr) e).getValue();
					// NodeTest leftValue = new NodeTest();
					// leftValue.setNodeValue(Utils.asString(Utils
					// .asNumber(v)));
					// leftValue.setNodeType(NODE_TYPE_NUMBER);
					// boolean test = Utils.asBoolean(lessThan(
					// leftValue,
					// ((NodeTest) node).getFunctionCalls().get(
					// "lessThan")[1]));
					// if (test && !tmp.contains(n)) {
					// tmp.add(n);
					// }
					// }
					// }
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"lessThanOrEqual")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "lessThanOrEqual");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(lessThanOrEqual(
									args[0], args[1]));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}
					// if (((NodeTest) node).getFunctionCalls().containsKey(
					// "lessThanOrEqual")) {
					// predResult = (List<Node>) locationPath(
					// predResult,
					// ((NodeTest) node).getFunctionCalls().get(
					// "lessThanOrEqual")[0].getSteps());
					// for (Node e : predResult) {
					// String v = ((Attr) e).getValue();
					// NodeTest leftValue = new NodeTest();
					// leftValue.setNodeValue(Utils.asString(Utils
					// .asNumber(v)));
					// leftValue.setNodeType(NODE_TYPE_NUMBER);
					// boolean test = Utils.asBoolean(lessThanOrEqual(
					// leftValue,
					// ((NodeTest) node).getFunctionCalls().get(
					// "lessThanOrEqual")[1]));
					// if (test && !tmp.contains(n)) {
					// tmp.add(n);
					// }
					// }
					// }
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"greaterThan")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "greaterThan");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(greaterThan(args[0],
									args[1]));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}
					// if (((NodeTest) node).getFunctionCalls().containsKey(
					// "greaterThan")) {
					// predResult = (List<Node>) locationPath(
					// predResult,
					// ((NodeTest) node).getFunctionCalls().get(
					// "greaterThan")[0].getSteps());
					// for (Node e : predResult) {
					// String v = ((Attr) e).getValue();
					// NodeTest leftValue = new NodeTest();
					// leftValue.setNodeValue(Utils.asString(Utils
					// .asNumber(v)));
					// leftValue.setNodeType(NODE_TYPE_NUMBER);
					// boolean test = Utils.asBoolean(greaterThan(
					// leftValue,
					// ((NodeTest) node).getFunctionCalls().get(
					// "greaterThan")[1]));
					// if (test && !tmp.contains(n)) {
					// tmp.add(n);
					// }
					// }
					// }
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"greaterThanOrEqual")) {

						List<NodeTest[]> newArgs = getNewNotEqualArgs(node,
								predResult, "greaterThanOrEqual");
						for (NodeTest[] args : newArgs) {
							boolean test = Utils.asBoolean(greaterThanOrEqual(
									args[0], args[1]));
							if (test && !tmp.contains(n)) {
								tmp.add(n);
							}
						}
					}
					// if (((NodeTest) node).getFunctionCalls().containsKey(
					// "greaterThanOrEqual")) {
					// predResult = (List<Node>) locationPath(
					// predResult,
					// ((NodeTest) node).getFunctionCalls().get(
					// "greaterThanOrEqual")[0].getSteps());
					// for (Node e : predResult) {
					// String v = ((Attr) e).getValue();
					// NodeTest leftValue = new NodeTest();
					// leftValue.setNodeValue(Utils.asString(Utils
					// .asNumber(v)));
					// leftValue.setNodeType(NODE_TYPE_NUMBER);
					// boolean test = Utils.asBoolean(greaterThanOrEqual(
					// leftValue,
					// ((NodeTest) node).getFunctionCalls().get(
					// "greaterThanOrEqual")[1]));
					// if (test && !tmp.contains(n)) {
					// tmp.add(n);
					// }
					// }
					// }
					if (((NodeTest) node).getFunctionCalls().containsKey("and")) {
						// predResult = (List<Node>) locationPath(
						// predResult,
						// ((NodeTest) node).getFunctionCalls().get(
						// "and")[1].getSteps());
						predResult.clear();
						predResult.add(n);
						Object[] newArgs = getNewAndArgs(node, predResult,
								"and");

						boolean test = Utils.asBoolean(and(newArgs));
						if (test && !tmp.contains(n)) {
							tmp.add(n);
						}

					}
					if (((NodeTest) node).getFunctionCalls().containsKey("or")) {
						// predResult = (List<Node>) locationPath(
						// predResult,
						// ((NodeTest) node).getFunctionCalls().get(
						// "and")[1].getSteps());
						predResult.clear();
						predResult.add(n);
						Object[] newArgs = getNewAndArgs(node, predResult, "or");

						boolean test = Utils.asBoolean(or(newArgs));
						if (test && !tmp.contains(n)) {
							tmp.add(n);
						}

					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"" + FUNCTION_LAST)) {
						if (cur == last) {
							tmp.add(n);
						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"" + FUNCTION_CONTAINS)) {
						NodeTest[] argument = ((NodeTest) node)
								.getFunctionCalls().get("" + FUNCTION_CONTAINS);
						predResult = (List<Node>) locationPath(predResult,
								((NodeTest) argument[0]).getSteps());
						for (Node nx : predResult) {
							String keyword = ((NodeTest) argument[1])
									.getNodeValue();
							if (contains(nx, keyword)) {
								tmp.add(n);
							}
						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"" + FUNCTION_NOT)) {
						NodeTest[] argument = ((NodeTest) node)
								.getFunctionCalls().get("" + FUNCTION_NOT);
						predResult = (List<Node>) locationPath(predResult,
								((NodeTest) argument[0]).getSteps());
						for (Node nx : predResult) {
							String keyword = ((NodeTest) argument[1])
									.getNodeValue();
							if (contains(nx, keyword)) {
								tmp.add(n);
							}
						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"" + FUNCTION_STARTS_WITH)) {
						NodeTest[] argument = ((NodeTest) node)
								.getFunctionCalls().get(
										"" + FUNCTION_STARTS_WITH);
						predResult = (List<Node>) locationPath(predResult,
								((NodeTest) argument[0]).getSteps());
						for (Node nx : predResult) {
							String keyword = ((NodeTest) argument[1])
									.getNodeValue();
							if (startsWith(nx, keyword)) {
								tmp.add(n);
							}
						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"" + FUNCTION_LANG)) {
						NodeTest[] argument = ((NodeTest) node)
								.getFunctionCalls().get("" + FUNCTION_LANG);

						String keyword = ((NodeTest) argument[0])
								.getNodeValue();
						if (lang(n, keyword)) {
							tmp.add(n);

						}
					}
					if (((NodeTest) node).getFunctionCalls().containsKey(
							"" + FUNCTION_SUBSTRING)) {

						NodeTest[] argument = ((NodeTest) node)
								.getFunctionCalls()
								.get("" + FUNCTION_SUBSTRING);
						predResult = (List<Node>) locationPath(predResult,
								((NodeTest) argument[0]).getSteps());
						for (Node nx : predResult) {
							int i1 = Integer.parseInt(((NodeTest) argument[1])
									.getNodeValue());
							int i2 = Integer.parseInt(((NodeTest) argument[2])
									.getNodeValue());
							NodeTest ret = new NodeTest();
							ret.setNodeType(NODE_TYPE_STRING);
							ret.setNodeValue(subString(nx, i1, i2));

							tmp.add(ret);

						}
					}
				} else {
					predResult = (List<Node>) locationPath(predResult,
							((NodeTest) node).getSteps());
					if (predResult.size() > 0) {
						tmp.add(n);
					}

				}
			}

		}

		return tmp;
	}

	/**
	 * 
	 * @param nx
	 * @param i1
	 * @param i2
	
	 * @return String
	 */
	private String subString(Node nx, int i1, int i2) {
		//System.out.println(getCurrentMethodName() + " " + nx + " " + i1 + " "				+ i2);
		if (nx.getTextContent() != null && nx.getTextContent().length() >= i2) {
			return nx.getTextContent().substring(i1 - 1, i2);
		}
		return "";
	}

	/**
	 * 
	 * @param nx
	 * @param text
	
	 * @return String
	 */
	private String subStringBefore(Node nx, String text) {
		// TODO Auto-generated method stub
		if (nx.getTextContent() != null
				&& nx.getTextContent().indexOf(text) != -1) {
			return nx.getTextContent().substring(0,
					nx.getTextContent().indexOf(text));
		}
		return "";
	}

	/**
	 * 
	 * @param nx
	 * @param text
	
	 * @return String
	 */
	private String subStringAfter(Node nx, String text) {
		// TODO Auto-generated method stub
		if (nx.getTextContent() != null
				&& nx.getTextContent().indexOf(text) != -1) {
			return nx.getTextContent().substring(
					nx.getTextContent().indexOf(text) + text.length());
		}
		return "";
	}
/**
 * 
 * @param nx

 * @return String
 */
	private String name(Node nx) {
		return nx.getNodeName();
	}
/**
 * 
 * @param nx
 * @param lng

 * @return boolean
 */
	private boolean lang(Node nx, String lng) {
		return lng.equals(((Element) nx).getAttribute("xml:lang"));
	}
/**
 * 
 * @param nx
 * @param keyword

 * @return boolean
 */
	private boolean contains(Node nx, String keyword) {
		// TODO Auto-generated method stub
		if (nx.getTextContent() != null
				&& nx.getTextContent().contains(keyword)) {
			return true;
		}
		for (int i = 0; i < nx.getChildNodes().getLength(); i++) {
			contains(nx.getChildNodes().item(i), keyword);
		}

		return false;
	}
/**
 * 
 * @param nx
 * @param keyword

 * @return boolean
 */
	private boolean startsWith(Node nx, String keyword) {
		// TODO Auto-generated method stub
		if (nx.getTextContent() != null
				&& nx.getTextContent().startsWith(keyword)) {
			return true;
		}
		for (int i = 0; i < nx.getChildNodes().getLength(); i++) {
			contains(nx.getChildNodes().item(i), keyword);
		}

		return false;
	}
/**
 * 
 * @param node
 * @param predResult
 * @param op

 * @return List<NodeTest[]>
 */
	private List<NodeTest[]> getNewNotEqualArgs(Node node,
			List<Node> predResult, String op) {

		NodeTest[] args = ((NodeTest) node).getFunctionCalls().get(op);
		List<NodeTest[]> newArgs = new ArrayList<>();
		List<Node> predResultBackListLeft = new ArrayList<>();
		if (args[0].getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {
			Node nodeTest = args[0];
			Node n = predResult.get(0);
			List<Node> tmp = new ArrayList<>();
			if (((NodeTest) nodeTest).getFunctionCalls().size() > 0) {

				if (((NodeTest) nodeTest).getFunctionCalls().containsKey("mod")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "mod");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListLeft.add((NodeTest) mod(args1[0],
								args1[1]));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"multiply")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "multiply");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListLeft.add((NodeTest) multiply(
								args1[0], args1[1]));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"divide")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "divide");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListLeft.add((NodeTest) divide(args1[0],
								args1[1]));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"minus")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "minus");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListLeft.add((NodeTest) minus(args1[0],
								args1[1]));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey("sum")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "sum");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListLeft.add((NodeTest) sum(args1));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						FUNCTION_NOT + "")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, FUNCTION_NOT + "");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListLeft.add((NodeTest) not(args1));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"" + FUNCTION_SUBSTRING)) {
					//System.out.println(getCurrentMethodName());
					NodeTest[] argument = ((NodeTest) nodeTest)
							.getFunctionCalls().get("" + FUNCTION_SUBSTRING);
					predResult = (List<Node>) locationPath(predResult,
							((NodeTest) argument[0]).getSteps());
					for (Node nx : predResult) {
						int i1 = Integer.parseInt(((NodeTest) argument[1])
								.getNodeValue());
						int i2 = Integer.parseInt(((NodeTest) argument[2])
								.getNodeValue());
						NodeTest ret = new NodeTest();
						ret.setNodeType(NODE_TYPE_STRING);
						ret.setNodeValue(subString(nx, i1, i2));

						predResultBackListLeft.add(ret);

					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"" + FUNCTION_SUBSTRING_BEFORE)) {

					NodeTest[] argument = ((NodeTest) nodeTest)
							.getFunctionCalls().get(
									"" + FUNCTION_SUBSTRING_BEFORE);
					predResult = (List<Node>) locationPath(predResult,
							((NodeTest) argument[0]).getSteps());
					for (Node nx : predResult) {
						NodeTest ret = new NodeTest();
						ret.setNodeType(NODE_TYPE_STRING);
						ret.setNodeValue(subStringBefore(nx,
								((NodeTest) argument[1]).getNodeValue()));
						predResultBackListLeft.add(ret);

					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"" + FUNCTION_SUBSTRING_AFTER)) {
					//System.out.println(getCurrentMethodName());
					NodeTest[] argument = ((NodeTest) nodeTest)
							.getFunctionCalls().get(
									"" + FUNCTION_SUBSTRING_AFTER);
					predResult = (List<Node>) locationPath(predResult,
							((NodeTest) argument[0]).getSteps());
					for (Node nx : predResult) {
						NodeTest ret = new NodeTest();
						ret.setNodeType(NODE_TYPE_STRING);
						ret.setNodeValue(subStringAfter(nx,
								((NodeTest) argument[1]).getNodeValue()));
						predResultBackListLeft.add(ret);

					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"" + FUNCTION_NAME)) {
					//System.out.println(getCurrentMethodName());
					NodeTest[] argument = ((NodeTest) nodeTest)
							.getFunctionCalls().get("" + FUNCTION_NAME);
					predResult = (List<Node>) locationPath(predResult,
							((NodeTest) argument[0]).getSteps());
					for (Node nx : predResult) {
						NodeTest ret = new NodeTest();
						ret.setNodeType(NODE_TYPE_STRING);
						ret.setNodeValue(name(nx));
						predResultBackListLeft.add(ret);

					}
				}

			} else {
				predResultBackListLeft = (List<Node>) locationPath(predResult,
						((NodeTest) nodeTest).getSteps());
			}

		} else {
			predResultBackListLeft.add(args[0]);
		}
		List<Node> predResultBackListRight = new ArrayList<>();
		if ((args.length > 1)
				&& args[1].getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {

			Node nodeTest = args[1];
			Node n = predResult.get(0);
			List<Node> tmp = new ArrayList<>();
			if (((NodeTest) nodeTest).getFunctionCalls().size() > 0) {

				if (((NodeTest) nodeTest).getFunctionCalls().containsKey("mod")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "mod");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListRight.add((NodeTest) mod(args1[0],
								args1[1]));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"multiply")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "multiply");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListRight.add((NodeTest) multiply(
								args1[0], args1[1]));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						FUNCTION_NOT + "")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, FUNCTION_NOT + "");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListRight.add((NodeTest) not(args1));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"divide")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "divide");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListRight.add((NodeTest) divide(args1[0],
								args1[1]));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"minus")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "minus");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListRight.add((NodeTest) minus(args1[0],
								args1[1]));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey("sum")) {

					List<NodeTest[]> newArgs1 = getNewNotEqualArgs(nodeTest,
							predResult, "sum");
					for (NodeTest[] args1 : newArgs1) {
						predResultBackListRight.add((NodeTest) sum(args1));
					}
				}
				if (((NodeTest) nodeTest).getFunctionCalls().containsKey(
						"" + FUNCTION_SUBSTRING)) {
					//System.out.println(getCurrentMethodName());
					NodeTest[] argument = ((NodeTest) nodeTest)
							.getFunctionCalls().get("" + FUNCTION_SUBSTRING);
					predResult = (List<Node>) locationPath(predResult,
							((NodeTest) argument[0]).getSteps());
					for (Node nx : predResult) {
						int i1 = Integer.parseInt(((NodeTest) argument[1])
								.getNodeValue());
						int i2 = Integer.parseInt(((NodeTest) argument[2])
								.getNodeValue());
						NodeTest ret = new NodeTest();
						ret.setNodeType(NODE_TYPE_STRING);
						ret.setNodeValue(subString(nx, i1, i2));

						predResultBackListRight.add(ret);

					}
				}
			} else {
				predResultBackListRight = (List<Node>) locationPath(predResult,
						((NodeTest) nodeTest).getSteps());
			}

		} else {
			if (args.length > 1) {
				predResultBackListRight.add(args[1]);
			}
		}
		for (Node l : predResultBackListLeft) {
			if (predResultBackListRight.size() > 0) {
				for (Node r : predResultBackListRight) {
					NodeTest newL = getNodeTestFromNode(l);
					NodeTest newR = getNodeTestFromNode(r);
					newArgs.add(new NodeTest[] { newL, newR });
				}
			} else {
				NodeTest newL = getNodeTestFromNode(l);
				newArgs.add(new NodeTest[] { newL });
			}
		}

		return newArgs;
	}

	/**
	 * Method getNodeTestFromNode.
	 * @param e Node
	 * @return NodeTest
	 */
	private NodeTest getNodeTestFromNode(Node e) {
		NodeTest leftValue;
		if (e instanceof Attr) {
			String v = ((Attr) e).getValue();
			leftValue = new NodeTest();
			leftValue.setNodeValue(Utils.asString(Utils.asNumber(v)));
			leftValue.setNodeType(NODE_TYPE_NUMBER);
		} else if (e instanceof NodeTest && e.getNodeType() == NODE_TYPE_STRING) {

			leftValue = (NodeTest) e;
		} else if (e instanceof NodeTest && e.getNodeType() == NODE_TYPE_NUMBER) {

			leftValue = (NodeTest) e;
		} else {

			String v = Utils.asString(e);
			leftValue = new NodeTest();
			leftValue.setNodeValue(Utils.asString(v));
			leftValue.setNodeType(NODE_TYPE_STRING);
		}
		return leftValue;
	}

	/**
	 * Method getNewAndArgs.
	 * @param nodeTest Node
	 * @param predResult List<Node>
	 * @param op String
	 * @return Object[]
	 */
	private Object[] getNewAndArgs(Node nodeTest, List<Node> predResult,
			String op) {
		// TODO Auto-generated method stub
		NodeTest[] args = ((NodeTest) nodeTest).getFunctionCalls().get(op);
		List<NodeTest> newArgs = new ArrayList<>();
		List<Node> predResultBackList = new ArrayList<>();
		for (NodeTest arg : args) {
			NodeTest ret = new NodeTest();
			boolean val = false;
			ret.setNodeType(NODE_TYPE_BOOLEAN);

			if (arg.getNodeType() == NODE_TYPE_RELATIVE_LOCATIONPATH) {
				if (((NodeTest) arg).getFunctionCalls().size() > 0) {

					if (((NodeTest) arg).getFunctionCalls().containsKey(
							"lessThan")) {

						List<NodeTest[]> newArgs1 = getNewNotEqualArgs(arg,
								predResult, "lessThan");
						for (NodeTest[] args1 : newArgs1) {
							ret = ((NodeTest) lessThan(args1[0], args1[1]));
						}
					}

					if (((NodeTest) arg).getFunctionCalls().containsKey(
							"greaterThan")) {

						List<NodeTest[]> newArgs1 = getNewNotEqualArgs(arg,
								predResult, "greaterThan");
						for (NodeTest[] args1 : newArgs1) {
							ret = (NodeTest) greaterThan(args1[0], args1[1]);
						}
					}
					if (((NodeTest) arg).getFunctionCalls().containsKey(
							FUNCTION_NOT + "")) {

						List<NodeTest[]> newArgs1 = getNewNotEqualArgs(arg,
								predResult, FUNCTION_NOT + "");
						NodeTest rest = new NodeTest();
						if (newArgs1.size() > 1) {
							rest.setNodeType(NODE_TYPE_BOOLEAN);

						} else {
							rest.setNodeValue(Utils.asString(true));

						}
						ret = rest;
					}

				} else {
					predResultBackList = (List<Node>) locationPath(predResult,
							arg.getSteps());
					ret.setNodeValue(Utils.asString(predResultBackList.size() > 0));
				}

			} else {
				ret = arg;
			}

			newArgs.add(ret);
		}

		return newArgs.toArray();
	}

	/**
	 * Method expressionPath.
	 * @param expression Object
	 * @param predicates Object[]
	 * @param steps Object[]
	 * @return Object
	 * @see xx.Compiler#expressionPath(Object, Object[], Object[])
	 */
	@Override
	public Object expressionPath(Object expression, Object[] predicates,
			Object[] steps) {
		//System.out.println(getCurrentMethodName()); // TODO

		return "expressionPath";
	}
	
	/**
	 * 
	
	 * @return String
	 */

	public String getCurrentMethodName() {
		StackTraceElement stackTraceElements[] = (new Throwable())
				.getStackTrace();
		return stackTraceElements[1].toString();
	}

	/**
	 * Get an Object[] as an Expression[].
	 * 
	 * @param array
	 *            Object[]
	
	 * @return Expression[] */
	private Element[] toElementArray(Object[] array) {
		Element[] expArray = null;
		if (array != null) {
			expArray = new Element[array.length];
			for (int i = 0; i < expArray.length; i++) {
				expArray[i] = (Element) array[i];
			}
		}
		return expArray;
	}

	/**
	 * Method toNodeTestArray.
	 * @param array Object[]
	 * @return NodeTest[]
	 */
	private NodeTest[] toNodeTestArray(Object[] array) {
		NodeTest[] expArray = null;
		if (array != null) {
			expArray = new NodeTest[array.length];
			for (int i = 0; i < expArray.length; i++) {
				if (array[i] instanceof ArrayList || array[i] instanceof String) {
					// TODO
				} else {
					expArray[i] = (NodeTest) array[i];
				}
			}
		}
		return expArray;
	}

	/**
	 * Method toStepArray.
	 * @param steps Object[]
	 * @return Step[]
	 */
	private Step[] toStepArray(Object[] steps) {
		Step[] expArray = null;
		if (steps != null) {
			expArray = new Step[steps.length];
			for (int i = 0; i < expArray.length; i++) {
				expArray[i] = (Step) steps[i];
			}
		}
		return expArray;
	}

	/**
	 * 
	 * @param context
	 * @param axis
	 * @param nodeTest
	
	 * @return Object
	 */
	protected Object createContextForStep(List<Node> context, int axis,
			NodeTest nodeTest) {
		List<Node> tmp = new ArrayList<>();

		if (nodeTest instanceof NodeNameTest) {
			QName qname = ((NodeNameTest) nodeTest).getNodeQName();
			String prefix = qname.getPrefix();
			if (prefix != null) {
				nodeTest = new NodeNameTest(qname);
			}
		}

		switch (axis) {
		case Compiler.AXIS_ANCESTOR:
			for (Node node : context) {
				addParents(node, tmp, nodeTest, false);
			}
			last_axe = Compiler.AXIS_ANCESTOR;
			return tmp;
			// new AncestorContext(context, false,
			// nodeTest);
		case Compiler.AXIS_ANCESTOR_OR_SELF:
			for (Node node : context) {
				addParents(node, tmp, nodeTest, false);
				if (testNode(nodeTest, node)) {
					tmp.add(node);
				}
			}
			last_axe = Compiler.AXIS_ANCESTOR_OR_SELF;
			return tmp;// new AncestorContext(context, true,
						// nodeTest);
		case Compiler.AXIS_ATTRIBUTE:
			for (Node node : context) {

				tmp.addAll(getAttributNamed((getSpecificNode(node)), nodeTest));
			}
			last_axe = Compiler.AXIS_ATTRIBUTE;
			return tmp;// new AttributeContext(context, nodeTest);
		case Compiler.AXIS_CHILD:

			for (Node node : context) {

				tmp.addAll(getChildNamed((getSpecificNode(node)), nodeTest));
			}
			last_axe = Compiler.AXIS_CHILD;
			return tmp;// new
						// ChildContext(context,
						// nodeTest, false,
						// false);
		case Compiler.AXIS_DESCENDANT:
			for (Node node : context) {
				addDescendant(node, tmp, nodeTest);
			}
			last_axe = Compiler.AXIS_DESCENDANT;
			return tmp;// addDescendant new DescendantContext(context, false,
						// nodeTest);
		case Compiler.AXIS_DESCENDANT_OR_SELF:
			for (Node node : context) {
				if (testNode(nodeTest, node)) {
					tmp.add(node);
				}
				addDescendant(node, tmp, nodeTest);

			}
			last_axe = Compiler.AXIS_DESCENDANT_OR_SELF;
			return tmp;// new DescendantContext(context,
						// true, nodeTest);
		case Compiler.AXIS_FOLLOWING:
			for (Node node : context) {
				addFollowing(node, tmp, nodeTest);

			}
			last_axe = Compiler.AXIS_FOLLOWING;
			return tmp;// new PrecedingOrFollowingContext(context,
						// nodeTest, false);
		case Compiler.AXIS_FOLLOWING_SIBLING:
			for (Node node : context) {
				addFollowing_sibling(node, tmp, nodeTest);

			}
			last_axe = Compiler.AXIS_FOLLOWING_SIBLING;
			return tmp;// new ChildContext(context,
						// nodeTest, true, false);
		case Compiler.AXIS_NAMESPACE:
			last_axe = Compiler.AXIS_NAMESPACE;
			return "AXIS_NAMESPACE";// new NamespaceContext(context, nodeTest);
		case Compiler.AXIS_PARENT:
			for (Node node : context) {
				if (node instanceof Attr) {
					tmp.add(node);
				} else {
					Node parent = node.getParentNode();
					if (testNode(nodeTest, parent) && parent != null) {
						tmp.add(parent);
					}
				}
			}
			last_axe = Compiler.AXIS_PARENT;
			return tmp;// new ParentContext(context, nodeTest);
		case Compiler.AXIS_PRECEDING:
			for (Node node : context) {
				addPreceding(node, tmp, nodeTest);
			}
			last_axe = Compiler.AXIS_PRECEDING;
			return tmp;// new PrecedingOrFollowingContext(context,
						// nodeTest, true);
		case Compiler.AXIS_PRECEDING_SIBLING:
			for (Node node : context) {
				addPreceding_sibling(node, tmp, nodeTest);
			}
			last_axe = Compiler.AXIS_PRECEDING_SIBLING;
			return tmp;// new ChildContext(context,
						// nodeTest, true, true);
		case Compiler.AXIS_SELF:
			for (Node node : context) {
				if (testNode(nodeTest, node)) {
					tmp.add(node);
				}
			}
			last_axe = Compiler.AXIS_SELF;
			return tmp;// new
			// SelfContext(context,
			// nodeTest);
		default:
			return "axis";// null; // Never happens
		}

	}

	/**
	 * 
	 * @param node
	 * @param tmp
	 * @param nodeTest
	 */
	private void addFollowing(Node node, List<Node> tmp, NodeTest nodeTest) {
		List<Node> ret = new ArrayList<>();
		ret.add(node);
		addParents(node, ret, nodeTest, true);
		for (Node n : ret) {
			addFollowing_sibling_and_child(n, tmp, nodeTest);
		}
	}

	/**
	 * 
	 * @param node
	 * @param tmp
	 * @param nodeTest
	 */
	private void addPreceding(Node node, List<Node> tmp, NodeTest nodeTest) {
		// TODO
		List<Node> ret = new ArrayList<>();
		addParents(node, ret, nodeTest, true);
		ret.add(node);
		for (Node n : ret) {
			addPreceding_sibling_child(n, tmp, nodeTest);
		}
		Collections.reverse(tmp);

	}

	/**
	 * 
	 * @param node
	 * @param ret
	 * @param nodeTest
	 */
	private void addFollowing_sibling_and_child(Node node, List<Node> ret,
			NodeTest nodeTest) {
		Node fol = node.getNextSibling();
		if (fol != null) {
			if (!ret.contains(fol) && testNode(nodeTest, fol)) {
				ret.add(fol);

			}
			addDescendant(fol, ret, nodeTest);

			addFollowing_sibling_and_child(fol, ret, nodeTest);

		}

	}
/**
 * 
 * @param node
 * @param ret
 * @param nodeTest
 */
	private void addFollowing_sibling(Node node, List<Node> ret,
			NodeTest nodeTest) {
		Node fol = node.getNextSibling();
		if (fol != null) {
			if (!ret.contains(fol) && testNode(nodeTest, fol)) {
				ret.add(fol);
			}

			addFollowing_sibling(fol, ret, nodeTest);

		}

	}
/**
 * 
 * @param node
 * @param ret
 * @param nodeTest
 */
	private void addPreceding_sibling_child(Node node, List<Node> ret,
			NodeTest nodeTest) {
		Node fol = node.getPreviousSibling();
		if (fol != null && !ret.contains(fol)) {
			addPreceding_sibling_child(fol, ret, nodeTest);
			if (testNode(nodeTest, fol)) {

				ret.add(fol);
			}
			addDescendant(fol, ret, nodeTest);
		}

	}
/**
 * 
 * @param node
 * @param ret
 * @param nodeTest
 */
	private void addPreceding_sibling(Node node, List<Node> ret,
			NodeTest nodeTest) {
		Node fol = node.getPreviousSibling();
		if (fol != null) {

			addPreceding_sibling(fol, ret, nodeTest);
			if (!ret.contains(fol) && testNode(nodeTest, fol)) {
				ret.add(fol);
			}

		}

	}
/**
 * 
 * @param node
 * @param nodeTest

 * @return List<Node>
 */
	private List<Node> getAttributNamed(Node node, NodeTest nodeTest) {
		NamedNodeMap nodeList = node.getAttributes();
		List<Node> ret = new ArrayList<>();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (testNode(nodeTest, nodeList.item(i)))
					ret.add(nodeList.item(i));
			}
		}
		return ret;
	}

	/**
	 * Method getSpecificNode.
	 * @param node Node
	 * @return Node
	 */
	private static Node getSpecificNode(Node node) {
		if (node instanceof Document)
			return (Document) node;
		if (node instanceof Element)
			return (Element) node;
		if (node instanceof Text)
			return (Text) node;
		if (node instanceof Node)
			return (Node) node;

		return node;
	}
/**
 * 
 * @param nodeList

 * @return List<Node>
 */
	private static List<Node> nodeListToArray(NodeList nodeList) {
		List<Node> ret = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			ret.add(nodeList.item(i));
		}
		return ret;
	}
/**
 * 
 * @param nodeList
 * @param ret
 */
	private static void addNodeListToArray(NodeList nodeList, List<Node> ret) {

		for (int i = 0; i < nodeList.getLength(); i++) {
			ret.add(nodeList.item(i));
		}
	}
/**
 * 
 * @param node
 * @param ret
 */
	private static void addNodeToArray(Node node, List<Node> ret) {

		ret.add(node);
	}
/**
 * 
 * @param node
 * @param nodeTest

 * @return List<Node>
 */
	private static List<Node> getChildNamed(Node node, NodeTest nodeTest) {
		NodeList nodeList = node.getChildNodes();
		List<Node> ret = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (!(nodeList.item(i) instanceof Comment
					|| nodeList.item(i) instanceof ProcessingInstruction || nodeList
						.item(i) instanceof Text))
				if (testNode(nodeTest, nodeList.item(i))) {
					ret.add(nodeList.item(i));
				}
			if (nodeTest instanceof NodeTypeTest) {
				if (nodeTest.getNodeType() == NODE_TYPE_TEXT
						&& nodeList.item(i) instanceof Text) {
					ret.add(nodeList.item(i));
				}
			}
			if (nodeTest instanceof NodeTypeTest) {
				if (nodeTest.getNodeType() == NODE_TYPE_COMMENT
						&& nodeList.item(i) instanceof Comment) {
					ret.add(nodeList.item(i));
				}
			}
			if (nodeTest instanceof NodeTypeTest) {
				if (nodeTest.getNodeType() == NODE_TYPE_PI
						&& nodeList.item(i) instanceof ProcessingInstruction) {
					ProcessingInstruction test = (ProcessingInstruction) nodeList
							.item(i);
					if (test.getTarget().equals(
							((NodeTest) nodeTest).getNodeValue())
							|| ((NodeTest) nodeTest).getNodeValue() == null) {
						ret.add(nodeList.item(i));
					}
				}
			}
			if (nodeTest instanceof NodeTypeTest) {
				if (nodeTest.getNodeType() == NODE_TYPE_NODE
						&& nodeList.item(i) instanceof Node) {
					ret.add(nodeList.item(i));
				}
			}
		}
		return ret;
	}
/**
 * 
 * @param node
 * @param ret
 * @param nodeTest
 * @param ignore
 */
	private static void addParents(Node node, List<Node> ret,
			NodeTest nodeTest, boolean ignore) {
		Node parent = node.getParentNode();
		if (parent != null && !ret.contains(parent)) {
			addParents(parent, ret, nodeTest, ignore);
			if (testNode(nodeTest, parent) || ignore) {
				ret.add(parent);
			}
		}

	}
/**
 * 
 * @param node
 * @param ret
 * @param nodeTest
 */
	private static void addDescendant(Node node, List<Node> ret,
			NodeTest nodeTest) {
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			NodeList nodeList = node.getChildNodes();
			if (!(nodeList.item(i) instanceof Comment
					|| nodeList.item(i) instanceof ProcessingInstruction || nodeList
						.item(i) instanceof Text)) {
				if (testNode(nodeTest, node.getChildNodes().item(i))) {
					ret.add(node.getChildNodes().item(i));
				}
				addDescendant(node.getChildNodes().item(i), ret, nodeTest);
			}

		}
	}

	/**
	 * 
	 * @param test
	 * @param node
	
	 * @return boolean
	 */
	public static boolean testNode(NodeTest test, Node node) {
		if (test == null) {
			return true;
		}
		if (test instanceof NodeNameTest) {

			NodeNameTest nodeNameTest = (NodeNameTest) test;
			QName testName = nodeNameTest.getNodeQName();
			QName nodeName = new QName(node.getNamespaceURI(),
					node.getNodeName());
			if (nodeName == null) {
				return false;
			}

			String testPrefix = testName.getPrefix();
			String nodePrefix = nodeName.getPrefix();
			if (!equalStrings(testPrefix, nodePrefix)) {
				String testNS = nodeNameTest.getNamespaceURI();
				String nodeNS = node.getNamespaceURI();
				if (!equalStrings(testNS, nodeNS)) {
					return false;
				}
			}
			if (nodeNameTest.isWildcard()) {
				return true;
			}
			return testName.getLocalPart().equals(nodeName.getLocalPart());
		}
		return test instanceof NodeTypeTest
				&& ((NodeTypeTest) test).getNodeType() == Compiler.NODE_TYPE_NODE;
	}
/**
 * 
 * @param s1
 * @param s2

 * @return boolean
 */
	private static boolean equalStrings(String s1, String s2) {
		return s1 == s2 || s1 != null && s1.equals(s2);
	}
/**
 * 
 * @param arg

 * @return boolean
 */
	private boolean isNameAttributeTest(Node arg) {
		if (!(arg.getNodeType() == LOCATIONPATH)) {
			return false;
		}

		Step[] steps = ((NodeTest) arg).getSteps();
		if (steps.length != 1) {
			return false;
		}
		if (steps[0].getAxis() != Compiler.AXIS_ATTRIBUTE) {
			return false;
		}
		NodeTest test = steps[0].getNodeTest();
		if (!(test instanceof NodeNameTest)) {
			return false;
		}
		if (!((NodeNameTest) test).getNodeName().equals("name")) {
			return false;
		}
		return true;
	}
/**
 * 

 * @return boolean
 */
	public boolean isPredicate() {
		return isPredicate;
	}
/**
 * 
 * @param isPredicate boolean
 * @see xx.Compiler#setPredicate(boolean)
 */
	public void setPredicate(boolean isPredicate) {
		this.isPredicate = isPredicate;
	}
/**
 * 
 * @param conte

 * @return List<Node>
 */
	private static List<Node> sortContext(List<Node> conte) {
		for (Node n : conte) {
			if (n instanceof ProcessingInstruction) {
				return conte;
			}
		}
		Collections.sort(conte, new NodeComparator());
		return conte;
	}
}
