package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Resource;
import core.Triple;

public class XYO extends AbstractPrimitiveQuery<Variable,Variable,Resource> {
	
	public XYO(Variable s, Variable p, Resource o) {
		super(s, p, o);
	}

	@Override
	public Resolution solve(QueryTarget target) {
		Set<Substitution> substitutions = new HashSet<>();
		
		for ( final Triple t : target.listXYO(o) ) {
			Substitution subs = new Substitution();
			subs.put(s, t.getSubject());
			subs.put(p, t.getPredicate());

			substitutions.add(subs);
		}

		return Resolution.of(substitutions);
	}

}
