package query.primitive;

import java.util.HashSet;
import java.util.Set;

import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Variable;
import core.Triple;

public class XYZ extends AbstractPrimitiveQuery<Variable,Variable,Variable> {

	public XYZ(Variable s, Variable p, Variable o) {
		super(s, p, o);
	}

	@Override
	public Resolution solve(QueryTarget target) {
		Set<Substitution> substitutions = new HashSet<>();
		
		for ( final Triple t : target.listXYZ() ) {
			Substitution subs = new Substitution();
			subs.put(s, t.getSubject());
			subs.put(p, t.getPredicate());
			subs.put(o, t.getObject());

			substitutions.add(subs);
		}

		return Resolution.of(substitutions);
	}

}
