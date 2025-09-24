package experiment;

import io.RDFFileReader;

import java.io.File;

import rule.Rule;
import rule.RuleParser;
import rule.RuleTarget;

import com.google.common.base.Joiner;

public class RuleExperiment {
	
	public static RuleTarget load() {
		final String path = Joiner.on(File.separator).join( 
				System.getProperty("user.dir"), "src", "main", "resources", "onto.rdf");

		return new RDFFileReader().read(path);
	}

	public static void main(String[] args) {
		RuleTarget target = load();
		
		Rule rule = RuleParser.parse(
				"?x,rdfs:subClassOf,?y.=>?x,rdfs:subClassOf,?x.?y,rdfs:subClassOf,?y.");

		System.out.println(rule);
		System.out.println(Joiner.on(System.lineSeparator()).join(rule.apply(target)));
	}
	
}
