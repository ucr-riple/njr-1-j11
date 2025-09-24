
package net.slreynolds.ds;

import java.util.ArrayList;
import java.util.HashMap;
import net.slreynolds.ds.export.ExporterOptions;
import net.slreynolds.ds.export.SimpleGraphVizExporter;

/**
 * Shows how to use the simpler GraphViz exporter. This
 * exporter suppresses primitive fields, only showing 
 * objects and their relationships.
 */
public class SimpleGrapVizExporterExample  {
	
	public static void main(String[] args) {

        ArrayList<Integer> alist = new ArrayList<Integer>();
        alist.add(new Integer(1));
        alist.add(new Integer(2));
        
	    HashMap<String,Object> options = new HashMap<String,Object>();
	    options.put(ExporterOptions.OUTPUT_PATH, "alist2_simple.dot");
	    
	    ObjectSaver gvizSaver = new ObjectSaver(new SimpleGraphVizExporter());
	    gvizSaver.save(new Object[]{alist},
	    		       new String[]{"alist2"}, 
	    		       options);

        System.out.printf("All done. Convert to SVG using GraphViz command \"dot -O -Tsvg alist2_simple.dot\".\n");
	}
}
