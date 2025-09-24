package query.primitive;

import query.PrimitiveQuery;
import query.Token;
import query.Variable;
import core.Resource;

public class PrimitiveQueryFactory {

	private PrimitiveQueryFactory() {}
	
	public static PrimitiveQuery create(Token s, Token p, Token o) {
		final boolean isSVariable = s instanceof Variable; 
		final boolean isPVariable = p instanceof Variable; 
		final boolean isOVariable = o instanceof Variable; 
		
		if ( isSVariable && isPVariable && isOVariable ) {
			return create( (Variable)s, (Variable)p, (Variable)o );
		}
		
		if ( isSVariable && isPVariable ) {
			return create( (Variable)s, (Variable)p, (Resource)o );
		}
		
		if ( isPVariable && isOVariable ) {
			return create( (Resource)s, (Variable)p, (Variable)o );
		}
		
		if ( isSVariable && isOVariable ) {
			return create( (Variable)s, (Resource)p, (Variable)o );
		}
		
		if ( isSVariable ) {
			return create( (Variable)s, (Resource)p, (Resource)o );
		}
		
		if ( isPVariable ) {
			return create( (Resource)s, (Variable)p, (Resource)o );
		}
		
		if ( isOVariable ) {
			return create( (Resource)s, (Resource)p, (Variable)o );
		}
		
		return create( (Resource)s, (Resource)p, (Resource)o );
	}
	
	public static PrimitiveQuery create(Resource s, Resource p, Resource o) {
		return new SPO(s, p, o);
	}

	public static PrimitiveQuery create(Variable s, Resource p, Resource o) {
		return new XPO(s, p, o);
	}

	public static PrimitiveQuery create(Resource s, Variable p, Resource o) {
		return new SXO(s, p, o);
	}

	public static PrimitiveQuery create(Resource s, Resource p, Variable o) {
		return new SPX(s, p, o);
	}

	public static PrimitiveQuery create(Variable s, Variable p, Resource o) {
		return s.equals(p) ? new XXO(s, p, o) : new XYO(s, p, o);
	}
	
	public static PrimitiveQuery create(Resource s, Variable p, Variable o) {
		return p.equals(o) ? new SXX(s, p, o) : new SXY(s, p, o); 
	}

	public static PrimitiveQuery create(Variable s, Resource p, Variable o) {
		return s.equals(o) ? new XPX(s, p, o) : new XPY(s, p, o); 
	}

	public static PrimitiveQuery create(Variable s, Variable p, Variable o) {
		if ( s.equals(p) && p.equals(o) ) return new XXX(s, p, o); 
		
		if ( s.equals(p) ) return new XXY(s, p, o); 
		if ( p.equals(o) ) return new XYY(s, p, o); 
		if ( s.equals(o) ) return new XYX(s, p, o); 
		
		return new XYZ(s, p, o);
	}

}
