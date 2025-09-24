package com.miguel.sxl;

import java.util.ArrayList;

public class SXLVistor implements SXLParserVisitor {
	
	@Override
	public Object visit(SimpleNode node, Object data) {
		// SKIP
		return null;
	}

	//== OPERATORS ==========
	
	@Override
	public Object visit(ASTAddOp node, Object data) {
		return node.firstToken.image;
	}

	@Override
	public Object visit(ASTMultOp node, Object data) {
		return node.firstToken.image;
	}

	@Override
	public Object visit(ASTUnaryOp node, Object data) {
		return node.firstToken.image;
	}

	@Override
	public Object visit(ASTRelOp node, Object data) {
		return node.firstToken.image;
	}

	@Override
	public Object visit(ASTGreaterThan node, Object data) {
		return node.firstToken.image;
	}

	@Override
	public Object visit(ASTLessThan node, Object data) {
		return node.firstToken.image;
	}

	@Override
	public Object visit(ASTGreaterEqualsTo node, Object data) {
		return node.firstToken.image;
	}

	@Override
	public Object visit(ASTLessEqualsTo node, Object data) {
		return node.firstToken.image;
	}

	@Override
	public Object visit(ASTEqualTo node, Object data) {
		return node.firstToken.image;
	}

	@Override
	public Object visit(ASTNotEqualTo node, Object data) {
		return node.firstToken.image;
	}

	
	
	
	
	
	/**
	 * INTEGER LITERAL
	 */
	@Override
	public Object visit(ASTIntegerLiteral node, Object data) {
		try {
			return new SXLInteger( node.firstToken.image );
		} catch( Exception e ) {
			System.err.println("Invalid integer literal");
		}
		return null;
	}

	/**
	 * REAL LITERAL
	 */
	@Override
	public Object visit(ASTRealLiteral node, Object data) {
		try {
			return new SXLReal( node.firstToken.image );
		} catch(Exception e ) {
			System.err.println("Invalid real litearl");
		}
		return null;
	}

	/**
	 * CHARACTER LITERAL
	 */
	@Override
	public Object visit(ASTCharLiteral node, Object data) {
		try {
			String literal = node.firstToken.image;
			
			// Look for single quotes at the start and end of the literal. If not found, throw exception
			if ( !literal.startsWith("'") || !literal.endsWith("'") ) throw new Exception();
			// If not character is specified, return a null character
			if ( literal.length() == 2 ) return new SXLCharacter( '\0' );
			
			// Otherwise, return the character in the middle
			else {
				String charStr = literal.substring(1, literal.length() - 1);
				char c = '\0';
				char first = charStr.charAt(0);
				// check if the character is an escaped one
				if ( first == '\\' ) {
					switch( charStr ) {
						case "\\\\": c = '\\'; break;
						case "\\'": c = '\''; break;
						case "\\n": c = '\n'; break;
						case "\\r": c = '\r'; break;
						case "\\f": c = '\f'; break;
						case "\\b": c = '\b'; break;
						case "\\t": c = '\t'; break;
						default: throw new Exception();
					}
				} else if ( charStr.length() == 1 ) {
					c = first;
				} else {
					throw new Exception();
				}
				
				return new SXLCharacter( c );
			}
		} catch( Exception e ) {
			System.err.println("Invalid character literal");
		}
		return null;
	}

	/**
	 * STRING LITERAL
	 */
	
	@Override
	public Object visit(ASTStringLiteral node, Object data) {
		try {
			String literal = node.firstToken.image;
			// Look for double quotes at the start and end of the literal. If not found, throw exception
			if ( !literal.startsWith("\"") || !literal.endsWith("\"") ) throw new Exception();
			// Get the string between the quotes
			String str = literal.substring(1, literal.length() - 1);
			// Prepare the string
			str = Utils.sanitizeString(str);
			return new SXLString( str );
		} catch( Exception e ) {
			System.err.println("Invalid string literal");
		}
		return null;
	}

	/**
	 * BOOLEAN LITERAL
	 */
	@Override
	public Object visit(ASTBooleanLiteral node, Object data) {
		try {
			return new SXLBoolean( node.firstToken.image );
		} catch( Exception e ) {
			// Should not occur
			System.err.println("Invalid boolean literal");
		}
		return null;
	}

	/**
	 * UNIT LITERAL
	 */
	@Override
	public Object visit(ASTUnitLiteral node, Object data) {
		if ( node.firstToken.image.equals("#") ) {
			return new SXLUnit();
		} else {
			// Should not occur
			System.err.println("Invalid unit literal ");
		}
		return null;
	}

	
	
	
	
	/**
	 * TYPE NODE
	 */
	@Override
	public Object visit(ASTType node, Object data) {
		return node.jjtGetChild(0).jjtAccept(this, data);
	}
	/**
	 * INT TYPE KW
	 */
	@Override
	public Object visit(ASTTypeInt node, Object data) {
		return new SXLInteger(0);
	}
	/**
	 * REAL TYPE KW
	 */
	@Override
	public Object visit(ASTTypeReal node, Object data) {
		return new SXLReal(0);
	}
	/**
	 * BOOL TYPE KW
	 */
	@Override
	public Object visit(ASTTypeBool node, Object data) {
		return new SXLBoolean(false);
	}
	/**
	 * CHAR TYPE KW
	 */
	@Override
	public Object visit(ASTTypeChar node, Object data) {
		return new SXLCharacter('\0');
	}
	/**
	 * STRING TYPE KW
	 */
	@Override
	public Object visit(ASTTypeString node, Object data) {
		return new SXLString("");
	}
	/**
	 * UNIT TYPE KW
	 */
	@Override
	public Object visit(ASTTypeUnit node, Object data) {
		return new SXLUnit();
	}

	
	
	
	/**
	 * IDENTIFIER
	 */
	@Override
	public Object visit(ASTIdentifier node, Object data) {
		String identifier = node.firstToken.image;
		
		try {
			SXLValue value = SXL.runtime.symtable.getCurrentScope().getSymbol(identifier);
			return value;
		} catch (Exception e) {
			System.err.println(e.getMessage() + ", " + node.getPosition());
		}
		
		return null;
	}

	
	
	
	/**
	 * TYPE CAST
	 */
	@Override
	public Object visit(ASTTypeCast node, Object data) {
		if ( node.jjtGetNumChildren() == 0 ) return null;
		
		try {
			// Visit the first node - the type
			Object typeObj = node.jjtGetChild(0).jjtAccept(this, data);
			if ( typeObj == null || !(typeObj instanceof SXLValue) ) {
				throw new Exception("Invalid type in type cast" );
			}
		
			// Get the second node - the value to cast
			Object valueObj = node.jjtGetChild(1).jjtAccept(this, data);
			if ( valueObj == null || !(valueObj instanceof SXLValue) ) {
				throw new Exception("Invalid expression value in type cast");
			}
			
			// Get the type
			String type = ((SXLValue)typeObj).getType();
			// Get the value
			SXLValue value = (SXLValue)valueObj;
			
			return value.castInto(type);
		}
		catch( Exception e ) {
			System.err.println(e.getMessage() + ", " + node.getPosition());
			return null;
		}
	}



	
	
	
	/**
	 * UNARY
	 */
	@Override
	public Object visit(ASTUnary node, Object data) {
		try {
			// Get the unary operator
			String op = (String)node.jjtGetChild(0).jjtAccept(this, data);

			// Get the second node - the value to perform unary on
			Object operand = node.jjtGetChild(1).jjtAccept(this, data);
			if ( operand == null || !(operand instanceof SXLValue) ) {
				throw new Exception("Invalid expression value operand for unary operation");
			}
			
			// Get the value
			SXLValue value = (SXLValue)operand;
			// Get the unary result
			SXLValue result = value.unary(op);
			
			// Compute the result of the unary operation and return it
			return Operation.perform(op, (SXLValue)value, null);
		} catch ( Exception e ) {
			System.err.println(e.getMessage() + ", " + node.getPosition());
			return null;
		}
	}

	
	
	
	
	/**
	 * FACTOR
	 */
	@Override
	public Object visit(ASTFactor node, Object data) {
		if ( node.jjtGetNumChildren() == 0 ) return null;
		
		// visit the first node
		return node.jjtGetChild(0).jjtAccept(this, data);
	}

	/**
	 * TERM
	 */
	@Override
	public Object visit(ASTTerm node, Object data) {
		// If no nodes
		if ( node.children.length == 0 ) return null;
		
		try {
			// Visit first node and get data
			Object obj1 = node.children[0].jjtAccept(this, data);
			
			// If this is the only node, return its value
			if ( node.jjtGetNumChildren() == 1 ) {
				return obj1;
			}
			// Otherwise, visit the operator and the last node
			else {
				// Visit operator and third node and get their data
				Object operator = node.jjtGetChild(1).jjtAccept(this, data);
				Object obj2 = node.jjtGetChild(2).jjtAccept(this, data);
				
				// Perform the operation. Can only be multiplicative due to AST structure
				Object value = Operation.perform(operator, (SXLValue)obj1, (SXLValue)obj2);
				return value;
			}
		}
		catch( Exception e ) {
			System.err.println(e.getMessage() + ", " + node.getPosition());
			return null;
		}
	}

	
	/**
	 * SIMPLE EXPRESSION
	 */
	@Override
	public Object visit(ASTSimpleExpression node, Object data) {
		// If no nodes
		if ( node.children.length == 0 ) return null;
		
		try {
			// Visit first node and get data
			Object obj1 = node.children[0].jjtAccept(this, data);
			
			// If this is the only node, return its value
			if ( node.jjtGetNumChildren() == 1 ) {
				return obj1;
			}
			// Otherwise, visit the operator and the last node
			else {
				// Visit operator and third node and get their data
				Object operator = node.jjtGetChild(1).jjtAccept(this, data);
				Object obj2 = node.jjtGetChild(2).jjtAccept(this, data);
				
				// Perform the operation. Can only be additive due to AST structure
				Object value = Operation.perform(operator, (SXLValue)obj1, (SXLValue)obj2);
				return value;
			}
		} catch( Exception e ) {
			System.err.println(e.getMessage() + ", " + node.getPosition());
			return null;
		}
	}

	
	/**
	 * EXPRESSION
	 * @throws Exception 
	 */
	@Override
	public Object visit(ASTExpression node, Object data) {
		// If no nodes
		if ( node.jjtGetNumChildren() == 0 ) return null;
		
		try {
			// Visit first node and get data
			Object obj1 = node.children[0].jjtAccept(this, data);
			
			// If this is the only node, return its value
			if ( node.jjtGetNumChildren() == 1 ) {
				return obj1;
			}
			// Otherwise, visit the operator and the last node
			else {
				// Visit operator and third node and get their data
				Object operator = node.jjtGetChild(1).jjtAccept(this, data);
				Object obj2 = node.jjtGetChild(2).jjtAccept(this, data);
				
				// Perform the operation. Can only be relational due to AST structure
				Object value = Operation.perform(operator, (SXLValue)obj1, (SXLValue)obj2);
				return value;
			}
		} catch (Exception e) {
			System.err.println(e.getMessage() + ", " + node.getPosition());
			return null;
		}
	}

	
	
	
	/**
	 * VARIABLE DECLARATION
	 */
	@Override
	public Object visit(ASTVariableDecl node, Object data) {
		// First child node is the identifier
		String identifier = ((ASTIdentifier)node.jjtGetChild(0)).firstToken.image;
		// Second child node is the type
		SXLValue type = (SXLValue)node.jjtGetChild(1).jjtAccept(this, data);
		// Third child node is the value (an expression)
		SXLValue value = (SXLValue)node.jjtGetChild(2).jjtAccept(this, data);
		
		// Type checking
		if ( !type.getType().equals( value.getType() ) ) {
			System.err.println("Type mismatch: " + type.getType() + " and " + value.getType() + ", " + node.getPosition() );
			return null;
		}
		
		try {
			SXL.runtime.symtable.getCurrentScope().putSymbol(identifier, value);
		} catch (Exception e) {
			System.err.println(e.getMessage() + ", " + node.getPosition());
			return null;
		}
		
		// If node has 4 children, then we have a "in <block>" case
		if ( node.jjtGetNumChildren() == 4 ) {
			// Visit the block 
			Object block = node.jjtGetChild(3).jjtAccept(this, data);
			// Type checking - ensure the return result is an SXLValue
			if ( block instanceof SXLValue ) {
				try {
					// Set the variable value to the returned value
					SXL.runtime.symtable.getCurrentScope().setSymbol(identifier, (SXLValue)block);
				} catch (SymbolNotFoundException e) {
					System.out.println(e.getMessage() + ", " + node.getPosition());
				}
			} else {
				System.err.println("Invalid return value in variable declaration block, " + node.getPosition());
			}
		}
		
		return null;
	}

	/**
	 * VARIABLE ASSIGNMENT
	 */
	@Override
	public Object visit(ASTAssignmentStatement node, Object data) {
		// First child node is the identifier
		String identifier = ((ASTIdentifier)node.jjtGetChild(0)).firstToken.image;
		// Second child node is the value (an expression)
		SXLValue value = (SXLValue)node.jjtGetChild(1).jjtAccept(this, data);
		
		try {
			SXL.runtime.symtable.getCurrentScope().setSymbol(identifier, value);
		} catch (Exception e) {
			System.err.println(e.getMessage() + ", " + node.getPosition());
		}
		
		return null;
	}
	
	
	
	
	
	
	
	/**
	 * FUNCTION DECLARATION
	 */
	@Override
	public Object visit(ASTFunctionDecl node, Object data) {
		// Get the identifier
		String identifier = ((ASTIdentifier)node.jjtGetChild(0)).firstToken.image;
		// Get the params
		Object paramsObj = node.jjtGetChild(1).jjtAccept(this, data);
		// Get the return type
		Object returnTypeObj = node.jjtGetChild(2).jjtAccept(this, data);
		
		// Get the block node
		ASTBlock blockNode = (ASTBlock)node.jjtGetChild(3);
		
		// Cast params into a params ArrayList
		ArrayList<SXLParam> params = (ArrayList<SXLParam>)paramsObj;
		
		// Cast return type into an SXLValue
		SXLValue returnType = (SXLValue)returnTypeObj;
		
		// Generate the scope name for the function
		String fnName = Utils.generateFunctionScopeName(identifier, params);
		try {
			SXL.runtime.symtable.putSymbol(fnName, new SXLFunction(returnType, params, blockNode) );
		} catch(Exception e) {
			System.err.println(e.getMessage() + ", " + node.getPosition());
			return null;
		}
		
		return null;
	}

	/**
	 * PARAMS
	 */
	@Override
	public Object visit(ASTParams node, Object data) {
		// Prepare the params list
		ArrayList<SXLParam> params = new ArrayList<SXLParam>(0);
		if ( node.children != null ) {
			// Iterate all child nodes
			for(int i = 0; i < node.children.length; i++) {
				// Visit the param
				Object paramObj = node.children[i].jjtAccept(this, data);
				// Cast returned object into an SXL Param and add it to the params ArrayList
				params.add( (SXLParam)paramObj );
			}
		}
		// Return the params
		return params;
	}

	/**
	 * PARAM
	 */
	@Override
	public Object visit(ASTParam node, Object data) {
		// First child node is the identifier
		String identifier = ((ASTIdentifier)node.jjtGetChild(0)).firstToken.image;
		// Second child node is the type
		SXLValue type = (SXLValue)node.jjtGetChild(1).jjtAccept(this, data);
		// Return the param
		return new SXLParam(identifier, type);
	}
	
	
	
	
	/**
	 * FUNCTION CALL
	 */
	@Override
	public Object visit(ASTFunctionCall node, Object data) {
		// Get the identifier
		String identifier = ((ASTIdentifier)node.jjtGetChild(0)).firstToken.image;
		// Get the arguments
		Object argsObj = node.jjtGetChild(1).jjtAccept(this, data);
		
		// If the arguments node visit returned null, return null
		if ( argsObj == null ) return null;
		
		try {
			// Cast the function call arguments
			ArrayList<SXLValue> args = (ArrayList<SXLValue>)argsObj;
			
			// Generate the function name
			String fnName = Utils.generateFunctionName(identifier, args);
			// Get the function object
			Object fnObj = SXL.runtime.symtable.getSymbol(fnName);
			// Type checking
			if ( !(fnObj instanceof SXLFunction ) ) {
				throw new Exception(identifier + " is not a function!");
			}
			// Cast into SXLFunction
			SXLFunction fn = (SXLFunction)fnObj;
			
			// Create a scope for this function call
			int scopeID = 0;
			SXLScope fnScope = null;
			// Keep trying until a scope is created
			while ( true ) {
				try {
					// Attempt to create scope
					fnScope = SXL.runtime.symtable.createScope(fnName + "#" + scopeID);
					// If successful, exit loop
					break;
				} catch( Exception e ){
					// Scope already exists, increment scopeID
					scopeID++;
				}
			}
			
			// Get the function params
			ArrayList<SXLParam> params = fn.getParams();
			// Prepare the variables in the function's scope
			for ( int i = 0; i < args.size(); i++ ) {
				fnScope.putSymbol(params.get(i).identifier, args.get(i));
			}
			// The return type in the scope.
			fnScope.putSymbol("#return", fn.getReturnType());
			
			// Get the current scope
			SXLScope currentScope = SXL.runtime.symtable.getCurrentScope();
			// Change scope
			SXL.runtime.symtable.enterScope(fnScope);
			
			// Visit the function node and save the result
			SXLValue result = (SXLValue)fn.getNode().jjtAccept(this, data);
			
			// Type check the returned value
			String declaredReturnType = fnScope.getSymbol("#return").getType();
			if ( !declaredReturnType.equals( result.getType() ) ) {
				throw new Exception("Invalid return value. Function declared " + declaredReturnType + " but returned " + result.getType());
			}
			
			// Change back to the scope before the function call
			SXL.runtime.symtable.enterScope(currentScope);
			
			// Return the result
			return result;
		} catch (Exception e) {
			System.err.println(e.getMessage() + " " + node.getPosition());
			return null;
		}
	}
	
	/**
	 * FUNCTION ARGUMENTS
	 */
	@Override
	public Object visit(ASTArgs node, Object data) {
		// Prepare the args list
		ArrayList<SXLValue> args = new ArrayList<SXLValue>(0);
		if ( node.children != null ) {
			// Iterate all child nodes
			for(int i = 0; i < node.children.length; i++) {
				// Visit the arg
				Object paramObj = node.children[i].jjtAccept(this, data);
				// Cast returned object into an SXLValue and add it to the args ArrayList
				if ( paramObj instanceof SXLValue ) {
					args.add( (SXLValue)paramObj );
				} else {
					System.err.println("Invalid function argument passed, " + node.getPosition());
					return null;
				}
			}
		}
		// Return the args
		return args;
	}

	/**
	 * ARGUMENT
	 */
	@Override
	public Object visit(ASTArg node, Object data) {
		return node.jjtGetChild(0).jjtAccept(this, data);
	}

	
	
	
	
	
	
	
	
	/**
	 * IF STATEMENT
	 */
	@Override
	public Object visit(ASTIfStatement node, Object data) {
		// Visit the expression node
		Object expObj = node.jjtGetChild(0).jjtAccept(this, data);
		// Boolean evaluation check
		if ( !(expObj instanceof SXLBoolean) ) {
			System.err.println("If statement expression does not evaluate to a boolean, " + node.getPosition());
			return null;
		}
		// Get the boolean result of the expression
		SXLBoolean value = (SXLBoolean)expObj;
		
		try {
			// If boolean is true, visit the "then" node
			if ( value.boolValue() ) {
				// Return the result
				return node.jjtGetChild(1).jjtAccept(this, data);
			}
			// Check if the "else" node exits
			else if ( node.jjtGetNumChildren() == 3 ) {
				// Visit the block and return the result
				return node.jjtGetChild(2).jjtAccept(this, data);
			}
			// Otherwise, do nothing for false value
			else return null;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	/**
	 * IF THEN STATEMENT
	 */
	@Override
	public Object visit(ASTThenStatement node, Object data) {
		// Visit the child node
		return node.jjtGetChild(0).jjtAccept(this, data);
	}
	/**
	 * IF ELSE STATEMENT
	 */
	@Override
	public Object visit(ASTElseStatement node, Object data) {
		// Visit the child node
		return node.jjtGetChild(0).jjtAccept(this, data);
	}

	
	
	
	
	/**
	 * WHILE LOOP
	 */
	@Override
	public Object visit(ASTWhileStatement node, Object data) {
		// Get the expression node
		SXLNode exprNode = (SXLNode) node.jjtGetChild(0);
		// The condition boolean
		SXLBoolean condition = new SXLBoolean(false);
		
		try {
			do {
				Object condObj = exprNode.jjtAccept(this, data);
				// Check if condition returned an SXLBoolean
				if ( !(condObj instanceof SXLBoolean) ) {
					System.err.println("While loop condition does not evaluate to a boolean, " + node.getPosition());
					return null;
				}
				// Prepare the condition SXLBoolean
				condition = (SXLBoolean)condObj;
				
				// Check if condition evaluated to true
				if ( condition.boolValue() ) {
					// Visit the block
					node.jjtGetChild(1).jjtAccept(this, data);
				}
			}
			while ( condition.boolValue() );
		}
		catch ( Exception e ) {
			System.out.println("Err:" + e.getMessage());
		}
		
		return null;
	}

	
	
	
	
	/**
	 * READ STATEMENT
	 */
	@Override
	public Object visit(ASTReadStatement node, Object data) {
		// Get the identifier
		String identifier = ((ASTIdentifier)node.jjtGetChild(0)).firstToken.image;
		
		try {
			// Get variable from symbol table
			SXLValue variable = SXL.runtime.symtable.getCurrentScope().getSymbol(identifier);
			// Read from input
			String read = SXL.runtime.scanner.next();
			try {
				// Parse from string
				variable.fromString(read);
			} catch (Exception e) {
				System.err.println("Input type error: failed to parse the input into type " + variable.getType() + " for variable " + identifier + ", " + node.getPosition());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}

	/**
	 * WRITE STATEMENT
	 */
	@Override
	public Object visit(ASTWriteStatement node, Object data) {
		// If no nodes
		if ( node.jjtGetNumChildren() == 0 ) return null;
		
		// visit first node, and get the return result
		Object obj = node.jjtGetChild(0).jjtAccept(this, data);
		
		// If obj is null or not an SXLValue, print an error
		if ( obj == null || !(obj instanceof SXLValue) ) {
			System.err.println("Invalid print value in write statment, " + node.getPosition());
		} else {
			// Otherwise, print the object
			System.out.println(obj);
		}
		
		return null;
	}

	
	/**
	 * HALT STATEMENT
	 */
	@Override
	public Object visit(ASTHaltStatement node, Object data) {
		// Get the data
		Object obj = node.jjtGetChild(0).jjtAccept(this, data);
		// check type
		if (obj instanceof SXLInteger) {
			SXLInteger value = (SXLInteger)obj;
			SXL.runtime.exit(value.intValue());
		} else {
			System.err.println("Exit code must be an integer, " + node.getPosition());
			SXL.runtime.exit(1);
		}
		// Java requires return, even though both branches of if statement will exit
		return null;
	}

	
	/**
	 * BLOCK
	 */
	@Override
	public Object visit(ASTBlock node, Object data) {
		// Visit all children
		Object result = null;
		
		try {
			// Save current scope
			SXLScope currentScope = SXL.runtime.symtable.getCurrentScope();
			
			// Create a new scope for this block, and enter it
			SXLScope blockScope = SXL.runtime.symtable.getCurrentScope().createScope("block" + node.hashID());
			SXL.runtime.symtable.enterScope(blockScope);
			
			// For each child node
			for(int i = 0; i < node.children.length; i++) {
				// Visit the block and get the result
				result = node.jjtGetChild(i).jjtAccept(this, data);
			}
			
			// Change back to the previous scope
			SXL.runtime.symtable.enterScope(currentScope);
			// Delete the block scope
			SXL.runtime.symtable.getCurrentScope().deleteScope("block" + node.hashID());
			
		} catch( Exception e ) {
			System.out.println(e.getMessage());
			result = null;
		}
		
		// If no result was returned, use a unit value
		if ( result == null ) result = new SXLUnit();
		// Return the result of the last statement
		return result;
	}

	/**
	 * SXL
	 */
	@Override
	public Object visit(ASTSXL node, Object data) {
		if ( node.children == null ) return null;
		
		for(int i = 0; i < node.children.length; i++) {
			node.children[i].jjtAccept(this, data);
		}
		return null;
	}
	
}
