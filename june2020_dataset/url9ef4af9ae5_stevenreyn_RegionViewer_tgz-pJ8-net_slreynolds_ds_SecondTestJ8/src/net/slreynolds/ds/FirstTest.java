package net.slreynolds.ds;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.slreynolds.ds.export.ExportException;
import net.slreynolds.ds.export.ExporterOptions;
import net.slreynolds.ds.export.TulipExporter;
import net.slreynolds.ds.model.BuilderOptions;
import net.slreynolds.ds.model.Graph;
import net.slreynolds.ds.model.NamedIDGenerator;
import net.slreynolds.ds.model.Node;

public class FirstTest {

	public static void main(String[] args) throws ExportException, IOException {
		
		Graph g = new Graph();
		Node top = Node.createSymbol(NamedIDGenerator.next(),"top",0);
		g.add(top);
		Node n1 = new Node(NamedIDGenerator.next(),"n1",0);
		Node n2 = new Node(NamedIDGenerator.next(),"n2",0);
		g.add(n1);
		g.add(n2);
		g.addEdge(top, n1);
		g.addEdge(top, n2);
		
		Node top2 = Node.createSymbol(NamedIDGenerator.next(),"top2",0);
		g.add(top2);
		Node n3 = new Node(NamedIDGenerator.next(),"n3",0);
		g.add(n3);
		g.addEdge(top2, n2);
		g.addEdge(top2, n3);
		
		Node n31 = new Node(NamedIDGenerator.next(),"n31",0);
		g.add(n31);
		g.addEdge(n3,n31);
		g.addEdge(n31,n3); // JungExporter dies. can't have any loops. boo.
		
		TulipExporter exporter = new TulipExporter();
		Map<String,Object> options = new HashMap<String,Object>();
        options.put(BuilderOptions.INLINE_STRINGS,false);
        final String PATH = "../graphs/test/";
        options.put(ExporterOptions.OUTPUT_PATH, PATH+"t1.tlp");
		exporter.export(g,options);
		
	}
}
