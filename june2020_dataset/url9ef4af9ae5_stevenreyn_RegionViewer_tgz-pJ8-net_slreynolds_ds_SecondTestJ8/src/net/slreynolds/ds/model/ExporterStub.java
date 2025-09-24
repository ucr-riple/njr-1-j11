package net.slreynolds.ds.model;
import java.io.IOException;
import java.util.Map;

import net.slreynolds.ds.export.ExportException;
import net.slreynolds.ds.export.Exporter;
import net.slreynolds.ds.model.Graph;


public class ExporterStub implements Exporter {
	private Graph _g;
	
	public void export(Graph g, Map<String,Object> opts) throws ExportException, IOException {
		_g = g;
	}
	
	public Graph getGraph() { 
		return _g;
	}
}