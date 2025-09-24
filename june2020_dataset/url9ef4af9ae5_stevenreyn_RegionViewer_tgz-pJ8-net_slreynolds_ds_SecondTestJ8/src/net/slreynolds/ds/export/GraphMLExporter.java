
package net.slreynolds.ds.export;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;


/**
 * Export graph using GraphML. See <a href="http://graphml.graphdrawing.org/">http://graphml.graphdrawing.org/</a>.
 */
public class GraphMLExporter extends AbstractSimpleExporter {
    
	private PrintStream ostream = null;
	private String name = "";
	
    @Override
    protected void resetExportSession(Map<String,Object> opts) throws IOException {
    	super.resetExportSession(opts);
    	ostream = makePrintStream(opts);
    	name = ExporterUtils.name((String)opts.get(ExporterOptions.OUTPUT_PATH));
    }
    
	@Override
    protected void startExportSession() {
    	String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
    			"   <graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\" \n" + 
    			"   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
    			"   xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\"> \n" +
    			"      <graph id=\"%s\" edgedefault=\"directed\"> \n";
    	ostream.printf(header, name);
        // TODO some of these "label" attributes are not necessary
    	ostream.printf("      <key id=\"label\" for=\"node\" attr.name=\"label\" attr.type=\"string\"/>\n");
    	ostream.printf("      <key id=\"description\" for=\"node\" attr.name=\"description\" attr.type=\"string\"/>\n");
    	ostream.printf("      <key id=\"text\" for=\"node\" attr.name=\"text\" attr.type=\"string\"/>\n");
    	ostream.printf("      <key id=\"r\" for=\"node\" attr.name=\"r\" attr.type=\"int\"/>\n");
    	ostream.printf("      <key id=\"g\" for=\"node\" attr.name=\"g\" attr.type=\"int\"/>\n");
    	ostream.printf("      <key id=\"b\" for=\"node\" attr.name=\"b\" attr.type=\"int\"/>\n");
    }
    
	@Override
    protected void exportVertex(ExportVertex vertex) {
		Color c = getColor(vertex.getGeneration());
		ostream.printf("         <node id=\"%d\">\n", vertex.getID());
		ostream.printf("            <data key=\"label\">%s</data>\n", vertex.getLabel());
		ostream.printf("            <data key=\"description\">%s</data> \n", vertex.getLabel());
		ostream.printf("            <data key=\"text\">%s</data> \n", vertex.getLabel());
		ostream.printf("            <data key=\"r\">%d</data>\n", c.getRed());
		ostream.printf("            <data key=\"g\">%d</data>\n", c.getGreen());
		ostream.printf("            <data key=\"b\">%d</data>\n", c.getBlue());
		ostream.printf("         </node>\n");
	}
    
	@Override
    protected void exportEdge(ExportEdge edge) {
    	ostream.printf("         <edge id=\"%d\" source=\"%d\" target=\"%d\"/>\n",
    			edge.getID(),
    			edge.getFromID(),edge.getToID() );
	}
    
	@Override
    protected void  finishExportSession() {
    	ostream.printf("    </graph>\n</graphml>\n");
    	ostream.close();
    }
    
}
