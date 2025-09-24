package query.primitive;

import query.PrimitiveQuery;
import query.QueryTarget;
import query.Resolution;
import query.Substitution;
import query.Token;
import core.Triple;

public abstract class
AbstractPrimitiveQuery<T1 extends Token,T2 extends Token,T3 extends Token>
implements PrimitiveQuery {
	
	protected final T1 s;
	protected final T2 p;
	protected final T3 o;

	public AbstractPrimitiveQuery(T1 s, T2 p, T3 o) {
		super();
		this.s = s;
		this.p = p;
		this.o = o;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((o == null) ? 0 : o.hashCode());
		result = prime * result + ((p == null) ? 0 : p.hashCode());
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		AbstractPrimitiveQuery other = (AbstractPrimitiveQuery) obj;
		if (o == null) {
			if (other.o != null) {
				return false;
			}
		} else if (!o.equals(other.o)) {
			return false;
		}
		if (p == null) {
			if (other.p != null) {
				return false;
			}
		} else if (!p.equals(other.p)) {
			return false;
		}
		if (s == null) {
			if (other.s != null) {
				return false;
			}
		} else if (!s.equals(other.s)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s,%s,%s.", s, p, o);
	}
	
	@Override
	abstract public Resolution solve(QueryTarget target);
	
	@Override
	public PrimitiveQuery apply(Substitution sub) {
		return PrimitiveQueryFactory.create( s.apply(sub), p.apply(sub), o.apply(sub) );
	}
	
	@Override
	public Triple toTriple() {
		throw new UnsupportedOperationException(
				this.getClass() + " is not supported toTriple.");
	}
	
}
