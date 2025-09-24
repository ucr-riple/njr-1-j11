package compiler;

import java.util.ArrayList;

public class EsperCGenerator {

	private static ArrayList<VariableInformation> variableList;

	private static String getVariableTypeFromName(String name) {

		for (VariableInformation var : variableList)
			if (var.name.equals(name))
				return var.type;

		if (name.equals("inf") || name.equals("-inf") || name.equals("nullity"))
			return "transreal";

		return "unknown";
	}

	/** Generates the left brace with the required indentation
	 * @param indent The number of tabs to indent with
	 * @return The string with the left brace */
	private static String leftBrace(int indent) {
		return "\n" + getIndentString(indent) + "{\n";
	}

	/** Generates the right brace with the required indentation
	 * @param indent The number of tabs to indent with
	 * @return The string with the right brace */
	private static String rightBrace(int indent) {
		return getIndentString(indent) + "}\n";
	}

	/** Gets the string with the correct number of indentations
	 * @param indent The number of indentations
	 * @return The string of indentation */
	private static String getIndentString(int indent) {
		String indentString = "";

		for (int i = 0; i < indent; i++)
			indentString += "\t";
		return indentString;
	}

	/** Generates the C code for the given tree
	 * @param parseRoot The root node of the tree to parse
	 * @param tvariableList The list of variables in the code
	 * @return The generated C code */
	public static String generate(ParseTree parseRoot, ArrayList<VariableInformation> tvariableList) {

		variableList = tvariableList;

		String code = "#include <stdio.h>\n\n";
		code += getTransrealLibrary();
		code += "int main() \n{\n";

		for (int i = 0; i < parseRoot.children.size(); i++) {
			code += generateNode(parseRoot.children.get(i), 1);
		}

		return code + "}\n";
	}

	/** Generates C code for the given node
	 * @param parseRoot The node to generate the code for
	 * @param indent The number of indents
	 * @return The C code */
	private static String generateNode(ParseTree parseRoot, int indent) {

		// The string by which the code is indented
		String indentString = getIndentString(indent);

		// The generated C code
		String code = "";

		// To store a variable name and variable type of a node
		String variableName;
		String variableType;

		// The code is generated depending on the attribute of the current node
		switch (parseRoot.attribute) {

		// Transreal numbers
		case "INFINITY":
			code += "get_infinity()";
			break;

		case "NEGATIVEINFINITY":
			code += "get_negative_infinity()";
			break;

		case "NULLITY":
			code += "get_nullity()";
			break;

		// Arithmetic
		case "PLUS":
		case "MULT":
		case "MINUS":
		case "DIV":

			// Get the name and types of the variables
			variableName = parseRoot.children.get(0).value;
			String variableNameTwo = parseRoot.children.get(1).value;
			variableType = getVariableTypeFromName(variableName);
			String variableTypeTwo = getVariableTypeFromName(variableNameTwo);

			// If the variable is transreal
			if (variableType.equals("transreal") || variableTypeTwo.equals("transreal")) {

				// Use a transreal arithmetic function
				if (parseRoot.attribute.equals("PLUS"))
					code += " transreal_add(";
				else if (parseRoot.attribute.equals("MULT"))
					code += " transreal_mult(";
				else if (parseRoot.attribute.equals("MINUS"))
					code += " transreal_sub(";
				else if (parseRoot.attribute.equals("DIV"))
					code += " transreal_div(";

				// If the child attribute is a transreal number)
				code += generateNode(parseRoot.children.get(0), 0);

				// Parameter separator
				code += " ,";

				// If the child attribute is a transreal number
				code += generateNode(parseRoot.children.get(1), 0);
				;

				code += ")";
				break;
			}

			// Otherwise non-transreal arithmetic
			code += " (";
			code += generateNode(parseRoot.children.get(0), 0);

			if (parseRoot.attribute.equals("PLUS"))
				code += " +";
			else if (parseRoot.attribute.equals("MULT"))
				code += " *";
			else if (parseRoot.attribute.equals("MINUS"))
				code += " -";
			else if (parseRoot.attribute.equals("DIV"))
				code += " /";

			code += generateNode(parseRoot.children.get(1), 0);
			code += " )";
			break;

		// Condition
		case "LESSTHAN":
		case "GREATERTHAN":
		case "LESSTHANEQUAL":
		case "GREATERTHANEQUAL":
		case "EQUALTO":

			code += " (";
			code += generateNode(parseRoot.children.get(0), 0);

			if (parseRoot.attribute.equals("LESSTHAN"))
				code += " <";
			else if (parseRoot.attribute.equals("GREATERTHAN"))
				code += " >";
			else if (parseRoot.attribute.equals("LESSTHANEQUAL"))
				code += " <=";
			else if (parseRoot.attribute.equals("GREATERTHANEQUAL"))
				code += " >=";
			else
				code += " ==";

			code += generateNode(parseRoot.children.get(1), 0);
			code += ")";

			break;

		// Number
		case "DIGITS":
			code += " " + parseRoot.value;
			break;

		// While loop
		case "WHILE":

			code += " while";
			code += generateNode(parseRoot.children.get(0), 0);
			code += leftBrace(indent);

			// Generate the statements
			for (int i = 1; i < parseRoot.children.size(); i++)
				code += generateNode(parseRoot.children.get(i), indent + 1);

			code += rightBrace(indent);

			break;

		// Variable declaration
		case "DECLARE":

			code += indentString + parseRoot.children.get(1).value + " "
					+ parseRoot.children.get(0).value + ";\n";

			break;

		// Variable setting
		case "ASSIGN":

			// Check transreal
			variableName = parseRoot.children.get(0).value;
			variableType = getVariableTypeFromName(variableName);
			code += indentString + parseRoot.children.get(0).value + " = ";
			try {
				Float.parseFloat(parseRoot.children.get(1).value);
				if (variableType.equals("transreal"))
					code += "get_value(" + parseRoot.children.get(1).value + ");\n";
				else
					code += generateNode(parseRoot.children.get(1), 0) + ";\n";
			} catch (Exception e) {
				code += generateNode(parseRoot.children.get(1), 0) + ";\n";
			}

			break;

		// For loop
		case "FOR":

			code += " for (";
			// Get identifier
			String indexIdentifier = parseRoot.children.get(0).children.get(0).value;
			code += " " + indexIdentifier + "; " + indexIdentifier;

			String target = parseRoot.children.get(0).children.get(1).value;

			if (parseRoot.children.get(0).attribute.equals("INCREASING"))
				code += " <= " + target + "; " + indexIdentifier + "++";
			else
				code += " >= " + target + "; " + indexIdentifier + "--";

			code += ")" + leftBrace(indent);

			// Generate the statements
			for (int i = 1; i < parseRoot.children.size(); i++)
				code += generateNode(parseRoot.children.get(i), indent + 1);

			code += rightBrace(indent);

			break;

		// If statement
		case "IF":

			code += indentString + "if"
					+ generateNode(parseRoot.children.get(0), 0)
					+ leftBrace(indent);

			// Generate the statements
			for (int i = 1; i < parseRoot.children.size(); i++)
				code += generateNode(parseRoot.children.get(i), indent + 1);

			code += rightBrace(indent);

			break;

		// Else if statement
		case "ELSEIF":

			code += indentString + "else if"
					+ generateNode(parseRoot.children.get(0), 0)
					+ leftBrace(indent);

			// Generate the statements
			for (int i = 1; i < parseRoot.children.size(); i++)
				code += generateNode(parseRoot.children.get(i), indent + 1);

			code += rightBrace(indent);

			break;

		// Else statement
		case "ELSE":

			code += indentString + "else " + leftBrace(indent);

			// Generate the statements
			for (int i = 0; i < parseRoot.children.size(); i++)
				code += generateNode(parseRoot.children.get(i), indent + 1);

			code += rightBrace(indent);

			break;

		// Print function
		case "PRINT":

			// Check transreal
			variableName = parseRoot.children.get(0).value;
			variableType = getVariableTypeFromName(variableName);

			if (variableType.equals("transreal")) {
				code += indentString + "transreal_print(" + variableName + ");\n";
				break;
			}

			code += indentString + "printf(\"";

			// Need a variable list to determine type
			int n = 0;
			variableName = parseRoot.children.get(n).value;
			// Have to push through all the left parens
			while (variableName.equals("(") || variableName.equals("*") || variableName.equals("/") || variableName.equals("+") || variableName.equals("-")) {
				variableName = parseRoot.children.get(0).children.get(0).value;
			}

			variableType = getVariableTypeFromName(variableName);

			if (variableType.equals("unknown"))
				// Check string
				if (parseRoot.children.get(0).value.charAt(0) == '\"' && parseRoot.children.get(0).value.charAt(parseRoot.children.get(0).value.length() - 1) == '\"')
					variableType = "string";
				else
					System.out.println("UNKNOWN: " + parseRoot.children.get(0).value);

			if (variableType.equals("int"))
				code += "%d";
			else if (variableType.equals("string"))
				code += "%s";

			code += "\\n\",";

			for (int i = 0; i < parseRoot.children.size(); i++)
				code += generateNode(parseRoot.children.get(i), indent + 1);

			code += ");\n";

			break;

		case "IDENTIFIER":

			code += " " + parseRoot.value;

			break;

		case "STRING":

			code += parseRoot.value;

			break;

		default:

			break;
		}

		return code;
	}

	/** Checks if a string is a float
	 * @param string The string to check
	 * @return Whether the string is a float */
	public static boolean isFloat(String string) {
		try {
			Float.parseFloat(string);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/** Returns the C code which implements the transreal functions
	 * @return The C code */
	public static String getTransrealLibrary() {

		StringBuilder sb = new StringBuilder();

		sb.append("#define INF 1\n");
		sb.append("#define NINF 2\n");
		sb.append("#define NULLITY 3\n\n");

		sb.append("struct _transreal\n");
		sb.append("{\n");
		sb.append("\tint special_case; //0 = nothing, 1 = -inf, 2 = inf, 3 = nullity\n");
		sb.append("\tfloat value;\n");
		sb.append("};\n\n");

		sb.append("typedef struct _transreal transreal;\n\n");

		sb.append("void transreal_print(transreal t)\n");
		sb.append("{\n");
		sb.append("\tif (t.special_case == 0)\n");
		sb.append("\t\tprintf(\"%f\\n\", t.value);\n");
		sb.append("\telse if (t.special_case == NINF)\n");
		sb.append("\t\tprintf(\"-INFINITY\\n\");\n");
		sb.append("\telse if (t.special_case == INF)\n");
		sb.append("\t\tprintf(\"INFINITY\\n\");\n");
		sb.append("\telse if (t.special_case == NULLITY)\n");
		sb.append("\t\tprintf(\"NULLITY\\n\");\n");
		sb.append("}\n\n");

		sb.append("transreal get_negative_infinity()\n");
		sb.append("{\n");
		sb.append("\ttransreal t;\n");
		sb.append("\tt.special_case = NINF;\n");
		sb.append("\treturn t;\n");
		sb.append("}\n\n");

		sb.append("transreal get_infinity()\n");
		sb.append("{\n");
		sb.append("\ttransreal t;\n");
		sb.append("\tt.special_case = INF;\n");
		sb.append("\treturn t;\n");
		sb.append("}\n\n");

		sb.append("transreal get_nullity()\n");
		sb.append("{\n");
		sb.append("\ttransreal t;\n");
		sb.append("\tt.special_case = NULLITY;\n");
		sb.append("\treturn t;\n");
		sb.append("}\n\n");

		sb.append("transreal get_value(float v)\n");
		sb.append("{\n");
		sb.append("\ttransreal t;\n");
		sb.append("\tt.special_case = 0;\n");
		sb.append("\tt.value = v;\n");
		sb.append("\treturn t;\n");
		sb.append("}\n\n");

		sb.append("transreal negative(transreal t)\n");
		sb.append("{\n");
		sb.append("\tif (t.special_case == NULLITY)\n");
		sb.append("\t\treturn get_nullity();\n");

		sb.append("\tif (t.special_case == INF)\n");
		sb.append("\t\treturn get_negative_infinity();\n");

		sb.append("\tif (t.special_case == NINF)\n");
		sb.append("\t\treturn get_infinity();\n");

		sb.append("\treturn get_value(-t.value);\n");
		sb.append("}\n\n");

		sb.append("transreal transreal_add(transreal a, transreal b)\n");
		sb.append("{\n");
		sb.append("\t//Nullity\n");
		sb.append("\tif (a.special_case == NULLITY || b.special_case == NULLITY)\n");
		sb.append("\t\treturn get_nullity();\n");

		sb.append("\t//+INF + -INF\n");
		sb.append("\tif ((a.special_case == INF && b.special_case == NINF) ||\n");
		sb.append("\t    (a.special_case == NINF && b.special_case == INF))\n");
		sb.append("\t\treturn get_nullity();\n");

		sb.append("\t//INF\n");
		sb.append("\tif (a.special_case == INF || b.special_case == INF)\n");
		sb.append("\t\treturn get_infinity();\n");

		sb.append("\t//NINF\n");
		sb.append("\tif (a.special_case == NINF || b.special_case == NINF)\n");
		sb.append("\t\treturn get_negative_infinity();\n");

		sb.append("\t//Regular\n");
		sb.append("\treturn get_value(a.value + b.value);\n");
		sb.append("}\n\n");

		sb.append("transreal transreal_sub(transreal a, transreal b)\n");
		sb.append("{\n");
		sb.append("\treturn transreal_add(a, negative(b));\n");
		sb.append("}\n\n");

		sb.append("transreal transreal_mult(transreal a, transreal b)\n");
		sb.append("{\n");
		sb.append("\t//Regular numbers\n");
		sb.append("\tif (a.special_case == 0 && b.special_case == 0)\n");
		sb.append("\t\treturn get_value(a.value*b.value);\n");

		sb.append("\t//Zero\n");
		sb.append("\tif ((a.special_case == 0 && a.value == 0) ||\n");
		sb.append("\t    (b.special_case == 0 && b.value == 0) ||\n");
		sb.append("\t    a.special_case == NULLITY ||\n");
		sb.append("\t    b.special_case == NULLITY)\n");
		sb.append("\t\treturn get_nullity();\n");

		sb.append("\t//PINF * NINF\n");
		sb.append("\tif ((a.special_case == NINF && b.special_case == INF) ||\n");
		sb.append("\t    (a.special_case == INF && b.special_case == NINF))\n");
		sb.append("\t\treturn get_negative_infinity();\n");

		sb.append("\t//INF * x\n");
		sb.append("\tif (a.special_case == INF || b.special_case == INF)\n");
		sb.append("\t\treturn get_infinity();\n");

		sb.append("\t//NINF * x\n");
		sb.append("\t\treturn get_negative_infinity();\n");
		sb.append("}\n\n");

		sb.append("transreal invert(transreal a)\n");
		sb.append("{\n");
		sb.append("\tif (a.special_case == 0)\n");
		sb.append("\t\tif (a.value == 0)\n");
		sb.append("\t\t\treturn get_infinity();\n");
		sb.append("\t\telse\n");
		sb.append("\t\t\treturn get_value(1/a.value);\n");

		sb.append("\tif (a.special_case == INF || a.special_case == NINF)\n");
		sb.append("\t\treturn get_value(0);\n");

		sb.append("\treturn get_nullity();\n");
		sb.append("}\n\n");

		sb.append("transreal transreal_div(transreal a, transreal b)\n");
		sb.append("{\n");
		sb.append("\treturn transreal_mult(a, invert(b));\n");
		sb.append("}\n\n");

		return sb.toString();
	}
}
