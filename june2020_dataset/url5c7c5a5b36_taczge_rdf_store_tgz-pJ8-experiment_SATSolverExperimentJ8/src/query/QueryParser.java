package query;

import java.util.LinkedList;
import java.util.List;

import query.primitive.PrimitiveQueryParser;

public class QueryParser {

	public static Query parse(String exp) {
		String[] primitives = exp.trim().split("\\.");
		
		List<PrimitiveQuery> query = new LinkedList<>();
		for (int i = 0; i < primitives.length; i++ ) {
			query.add( PrimitiveQueryParser.parse(primitives[i]) );
		}
		
		return new Query(query);
	}
	
}
