
package net.slreynolds.ds;

import java.util.HashMap;
import net.slreynolds.ds.export.ExporterOptions;
import net.slreynolds.ds.export.GraphVizExporter;

/**
 * Simplest Example of using the region viewer.
 */
public class SimplestExample  {
	
	public static void main(String[] args) {

        String brother = "brother";
        String the = brother.substring(3, 6);

	    HashMap<String,Object> options = new HashMap<String,Object>();
	    options.put(ExporterOptions.OUTPUT_PATH, "simplest_strings.dot");
	    
	    ObjectSaver gvizSaver = new ObjectSaver(new GraphVizExporter());
	    gvizSaver.save(new Object[]{brother,the},
	    		       new String[]{"brother","the"}, 
	    		       options);

        System.out.printf("All done. Convert to SVG using GraphViz command \"dot -O -Tsvg simplest_strings.dot\".\n");
	}
}
