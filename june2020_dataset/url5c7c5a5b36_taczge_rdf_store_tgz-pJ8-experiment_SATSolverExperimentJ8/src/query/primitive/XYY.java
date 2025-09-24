package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Triple;

public class XYY extends AbstractPrimitiveQuery<Variable,Variable,Variable> {

	public XYY(Variable s, Variable p, Variable o) {
		super(s, p, o);
	}

	@Override
	public Resolution solve(QueryTarget target) {
		Set<Substitution> substitutions = new HashSet<>();
		
		for ( final Triple t : target.listXYY() ) {
			Substitution subs = new Substitution();
			subs.put(s, t.getSubject());
			subs.put(p, t.getPredicate());

			substitutions.add(subs);
		}

		return Resolution.of(substitutions);
	}

}