package logic;

/*
 * This class is the functional object.
 */
public class PLParser {

	private PLParser() {};
	
	public static final Proposition parse(String expression) {
		return next(expression).p;
	}
	
	private static final ParseResult next(String expression) {
		final String trimmed = expression.trim();
		
		if ( trimmed.startsWith("(not ") ) {
			String rest = trimmed.replaceFirst("\\(not ", "");
			
			ParseResult result = next(rest);
			Proposition not = new Not(result.p);
			
			return new ParseResult(not, result.rest);
		}
		
		if ( trimmed.startsWith("(and ") ) {
			String rest = trimmed.replaceFirst("\\(and ", "");
			
			ParseResult left  = next(rest);
			ParseResult right = next(left.rest);
			Proposition and = new And(left.p, right.p);
			
			return new ParseResult(and, right.rest);
		}
		
		if ( trimmed.startsWith("(or ") ) {
			String rest = trimmed.replaceFirst("\\(or ", "");
			
			ParseResult left  = next(rest);
			ParseResult right = next(left.rest);
			Proposition or = new Or(left.p, right.p);
			
			return new ParseResult(or, right.rest);
		}
				
		String[] token = trimmed.split("[  \\)]", 2);
		final String atomExp = token[0];
				
		if ( atomExp.contains("(") || atomExp.contains(")") ) {
			throw new IllegalArgumentException(
					String.format("expression: %s is invalid syntax.", trimmed));
		}
		
		Proposition atom = new Atom(token[0]);
		
		return token.length == 2 ? new ParseResult(atom, token[1]) : new ParseResult(atom, "");  
	}

	private static class ParseResult {
		final Proposition p;
		final String rest;
		
		ParseResult(Proposition p, String rest) {
			this.p = p;
			this.rest = rest;
		}
	}
}
