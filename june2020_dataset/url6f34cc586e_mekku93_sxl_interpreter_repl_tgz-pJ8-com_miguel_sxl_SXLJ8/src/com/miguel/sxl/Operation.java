package com.miguel.sxl;

public class Operation {

	/**
	 * Checks the types of the given 2 objects. The node parameter is used to print
	 * the line and column if any type mismatch is found.
	 * @throws Exception 
	 */
	private static void checkTypes(SXLValue val1, SXLValue val2) throws Exception {
		// Get the class names of the types
		String type1 = val1.getType();
		String type2 = val2.getType();
		
		// check if same type
		if ( !type1.equals( type2 ) ) {
			// If not equal type, print error and stop
			throw new Exception("Type Mismatch error: " + type1 + " and " + type2);
		}
	}
	
	
	/**
	 * Uses the passed operator to perform an arithmetic or logical operation.
	 * The operation performed depends on the operator and the types of the given objects.
	 * 
	 * @param operator The operator object. Must be an instance of String
	 * @param obj1 The first object
	 * @param obj2 The second object
	 * @param node The parent node of the nodes that returned the 2 objects.
	 * @return The Object resulting from the arithmetic operation performed on the 2 objects.
	 * @throws Exception 
	 */
	public static Object perform(Object operator, SXLValue value1, SXLValue value2) throws Exception {
		// If the operator is not a string, print error and stop
		if ( !operator.getClass().equals(String.class) ) {
			System.err.println("AST error: arithmetic operation failed due to an invalid operator token image" );
			System.exit(1);
		}

		// Get the string operator
		String stringOp = (String)operator;
		
		// Prepare the result object. This will be returned
		SXLValue result = null;
		
		if ( value2 != null ) {
			// Check for equal types
			checkTypes(value1, value2);			
		}
		
		// Perform the operation based on the operator
		switch( stringOp ) {
			case "not":
				result = value1.not();
				break;
			case "+":
				if ( value2 == null ) result = value1.unary(stringOp);
				else result = value1.add(value2);
				break;
			case "-":
				if ( value2 == null ) result = value1.unary(stringOp);
				else result = value1.subtract(value2);
				break;
			case "*":
				result = value1.multiply(value2);
				break;
			case "/":
				result = value1.divide(value2);
				break;
			case "and":
				result = value1.and(value2);
				break;
			case "or":
				result = value1.or(value2);
				break;
				
			case ">":
				result = value1.isGreaterThan(value2);
				break;
			case "<":
				result = value1.isLessThan(value2);
				break;
			case "==":
				result = value1.isEqualTo(value2);
				break;
				
			case ">=":
				result = value1.isGreaterThan(value2).or( value1.isEqualTo(value2) );
				break;
			case "<=":
				result = value1.isLessThan(value2).or( value1.isEqualTo(value2) );
				break;
			case "!=":
				result = value1.isEqualTo(value2).not();
				break;
		}
		
		// Check if the result is null.
		if ( result == null ) {
			// If so, then the SXLValue objects do not support the operator. Print error and stop
			throw new Exception("Invalid operation for " + value1.getType() + " types");
		}
		
		// Return the result
		return result;
	}
	
	
}
