
package net.slreynolds.ds;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.slreynolds.ds.export.ExporterOptions;
import net.slreynolds.ds.export.GraphVizExporter;

/**
 *  Example showing how to get your own colors when using the region viewer.
 */
public class CustomColorsExample  {
	
	public static void main(String[] args) {

        String brother = "brother";
        String the = brother.substring(3, 6);

	    HashMap<String,Object> options = new HashMap<String,Object>();
	    options.put(ExporterOptions.OUTPUT_PATH, "custcolors_strings.dot");
	    
	    GraphVizExporter exporter = new GraphVizExporter();
	    List<Color> colors = new ArrayList<Color>();
	    colors.add(Color.GRAY);
	    colors.add(Color.LIGHT_GRAY);
	    exporter.setColors(colors);
	    ObjectSaver gvizSaver = new ObjectSaver(exporter);
	    gvizSaver.save(new Object[]{brother,the},
	    		       new String[]{"brother","the"}, 
	    		       options);

        System.out.printf("All done. Convert to SVG using GraphViz command \"dot -O -Tsvg custcolors_strings.dot\".\n");
	}
}
