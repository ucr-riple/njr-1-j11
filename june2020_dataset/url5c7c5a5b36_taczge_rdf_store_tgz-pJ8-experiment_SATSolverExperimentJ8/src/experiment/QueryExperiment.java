package experiment;

import io.RDFFileReader;

import java.io.File;

import query.Query;
import query.QueryParser;
import query.QueryTarget;

import com.google.common.base.Joiner;

public class QueryExperiment {
	
	public static QueryTarget load() {
		final String path = Joiner.on(File.separator).join( 
				System.getProperty("user.dir"), "src", "main", "resources", "onto.rdf");

		return new RDFFileReader().read(path);
	}

	public static void main(String[] args) {
		QueryTarget target = load();
		
		Query q = QueryParser.parse("?x,rdfs:subClassOf,?y.?y,rdfs:subClassOf,?z.");
		//Query q = QueryParser.parse("?x,?y,?z.?s,?p,?o.");
		
		System.out.println(q);
		System.out.println(q.solve(target));
	}
}
