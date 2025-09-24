package rule;

import query.QueryParser;

public class RuleParser {

	public static Rule parse(String exp) {
		String[] tokens = exp.split("=>");
		
		if ( tokens.length != 2) {
			throw new IllegalArgumentException("invalid rule expression: " + exp);
		}
		
		String head = tokens[0];
		String body = tokens[1];
		
		return new Rule(QueryParser.parse(head), QueryParser.parse(body));
	}
	
	private RuleParser() {};
}
