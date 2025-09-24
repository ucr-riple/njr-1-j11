package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Resource;
import core.Triple;

public class XPY extends AbstractPrimitiveQuery<Variable,Resource,Variable> {

	public XPY(Variable s, Resource p, Variable o) {
		super(s, p, o);
	}

	public Resolution solve(QueryTarget target) {
		Set<Substitution> substitutions = new HashSet<>();
		
		for ( final Triple t : target.listXPY(p) ) {
			Substitution subs = new Substitution();
			subs.put(s, t.getSubject());
			subs.put(o, t.getObject());

			substitutions.add(subs);
		}

		return Resolution.of(substitutions);
	}
	
}
