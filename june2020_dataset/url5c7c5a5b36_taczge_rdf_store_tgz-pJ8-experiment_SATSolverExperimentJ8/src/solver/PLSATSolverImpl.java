package solver;

import java.util.Set;

import logic.PLParser;
import query.Query;
import query.QueryParser;
import rule.Rule;
import rule.RuleParser;
import rule.RuleTarget;
import rule.Rules;
import core.Triple;
import core.Triples;

public class PLSATSolverImpl implements PLSATSolver {
	
	private static final Rule r1 = RuleParser.parse(
			"1,AND,?v1.?v1,?v2,?v3.?v2,?v21,?v22.?v3,?v31,?v32.=>1,?v21,?v22.1,?v31,?v32.");
	private static final Rule r2 = RuleParser.parse(
			"0,OR,?v1.?v1,?v2,?v3.?v2,?v21,?v22.?v3,?v31,?v32.=>0,?v21,?v22.0,?v31,?v32.");
	private static final Rule r3 = RuleParser.parse(
			"1,NOT,?v1.?v1,?v11,?v12.=>0,?v11,?v12.");
	private static final Rule r4 = RuleParser.parse(
			"0,NOT,?v1.?v1,?v11,?v12.=>1,?v11,?v12.");
	private static final Rules rules = new Rules(r1, r2, r3, r4);
	private static final Query query = QueryParser.parse("1,ATOM,?x.0,ATOM,?x.");

	public Satisfiability check(String exp) {
		Set<Triple> ts = PLParser.parse(exp).toTriples();
		RuleTarget target = rules.apply( new Triples(ts) );
		
		return query.solve(target).isEmpty() ? Satisfiability.YES : Satisfiability.NO;
	}
}
