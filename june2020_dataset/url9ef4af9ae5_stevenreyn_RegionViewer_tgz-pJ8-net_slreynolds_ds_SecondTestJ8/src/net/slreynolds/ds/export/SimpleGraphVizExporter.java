
package net.slreynolds.ds.export;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

/**
 * (simpler)Export graph to  GraphViz file format. Compared to GraphVizExporter,
 * suppresses internal fields for objects. See
 * <a href="http://www.graphviz.org/">graphviz.org</a>.
 */
public class SimpleGraphVizExporter extends AbstractSimpleExporter {
    
	
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
		 ostream.printf("digraph %s {\n",ExporterUtils.encloseInQuotes(ExporterUtils.name(name)));
	     ostream.printf("\n   node [shape=ellipse];\n"); 
    }
    
	@Override
    protected void exportVertex(ExportVertex vertex) { 
		Color c = getColor(vertex.getGeneration());
		String shapeSpecifier;
		
		if (vertex.getVertexType() == VertexType.SYMBOL) {
			shapeSpecifier="shape=plaintext"; 
    	}
		else {
			shapeSpecifier = "";
		}
		ostream.printf("node%s", filterID(vertex.getID()));
        ostream.printf("[label = \"%s\" %s", vertex.getLabel(),shapeSpecifier);
        ostream.printf(" fillcolor=\"#%2x%2x%2x\" style=filled ",c.getRed(),c.getGreen(),c.getBlue() );
        ostream.printf("];\n");
	}
    
	@Override
    protected void exportEdge(ExportEdge edge) { 
		ostream.printf("   node%s -> node%s [ label = \"%s\" ];\n", 
				filterID(edge.getFromID()),
				filterID(edge.getToID()),
				edge.getLabel());
	}
    
	@Override
    protected void  finishExportSession() {
        ostream.print("}");
        ostream.close();
    }
    
	private static String filterID(int id) {
		if (id >= 0) {
			return String.format("%d",id);
		}
		return String.format("m%d",-id);
	}
}
