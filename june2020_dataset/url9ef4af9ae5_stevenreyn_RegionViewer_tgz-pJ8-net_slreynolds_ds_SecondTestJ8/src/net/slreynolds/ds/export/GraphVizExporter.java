
package net.slreynolds.ds.export;


import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import net.slreynolds.ds.internal.util.FileUtil;
import net.slreynolds.ds.model.Edge;
import net.slreynolds.ds.model.Graph;
import net.slreynolds.ds.model.GraphPoint;
import net.slreynolds.ds.model.Named;
import net.slreynolds.ds.model.NodeArray;

/**
 * Export graph to GraphViz file format. See
 * <a href="http://www.graphviz.org/">graphviz.org</a>.
 */
public class GraphVizExporter extends AbstractExporter {
    
    
    public GraphVizExporter() {
        
    }
    
    public void export(Graph g, Map<String,Object> opts) throws ExportException, IOException {
        String path = (String)opts.get(ExporterOptions.OUTPUT_PATH);
        if (path == null) {
            throw new IllegalArgumentException("Must specify output path");
        }
        File out = FileUtil.createEmptyWritableFile(path);
        PrintStream ostream = new PrintStream(out);

        ostream.printf("digraph %s {\n",ExporterUtils.encloseInQuotes(ExporterUtils.name(path)));
        ostream.printf("\n   node [shape=record];\n"); 
      

        // --- Export the nodes ----
        List<GraphPoint> points = g.getGraphPoints();
        ostream.printf("\n   // Nodes\n");
        for (GraphPoint point: points) {
        	//  special case for symbol
        	if (point.hasAttr(Named.SYMBOL) && (Boolean)point.getAttr(Named.SYMBOL)) {
        		printSymbol(point,1,ostream);
        	}
            else if (point.isArray() && point.areValuesInlined()) {
                printArray((NodeArray)point,1,ostream);
            }
        	else {
            	printNode(point,1,ostream);
            }

        }
        
       // --- Export the edges ----
        ostream.printf("\n   // Edges\n");
        for (GraphPoint node: g.getGraphPoints()) {
            List<Edge> edges = node.getNeighbors();
            for (Edge e : edges) {
            	if (e.isAnonymous()) {
            		ostream.printf("   node%s -> node%s;\n", 
            				ExporterUtils.id(e.getFrom()),
                            ExporterUtils.id(e.getTo()));           		
            	}
            	else {
            		ostream.printf("   node%s -> node%s [ label = \"%s\" ];\n", 
            			ExporterUtils.id(e.getFrom()),
                        ExporterUtils.id(e.getTo()),e.getName());
            	}
            }
        }  

        ostream.print("}");
        ostream.close();
        
    }

    private  void printSymbol(GraphPoint n, int level, PrintStream ostream ) { 
        ostream.printf("%snode%s",ExporterUtils.getIndent(level),n.getID());
        final StringBuilder label = n.isAnonymous() ? new StringBuilder(' ') : new StringBuilder(n.getName());
        ostream.printf("[label = \"%s\" shape=plaintext", label.toString());
        
        Color color = getColor(n.getGeneration());
        ostream.printf(" fillcolor=\"#%2x%2x%2x\" style=filled ",color.getRed(),color.getGreen(),color.getBlue() );
        
        ostream.printf("];\n");
    }
    
    private  void printNode(GraphPoint n, int level, PrintStream ostream ) { 
        ostream.printf("%snode%s",ExporterUtils.getIndent(level),n.getID());
        final StringBuilder label = n.isAnonymous() ? new StringBuilder() : new StringBuilder(n.getName() + " ");
        
        if (n.hasAttr(Named.SYSTEM_HASH)) {
            String hashLabel = String.format("0x%X\\n", n.getAttr(Named.SYSTEM_HASH));    
            label.append(hashLabel);
        }
        
        if (n.hasAttr(Named.CLASS)) {
        	String attrLabel = String.format("%s = %s\\n", Named.CLASS,n.getAttr(Named.CLASS));
        	label.append(attrLabel);
        }
        
        for (String key : n.getAttrKeys()) {
        	if (key.equals(Named.SYSTEM_HASH)) {
        		continue;
        	}
        	else if (key.equals(Named.CLASS)) {
        		continue;
        	}
        	String attrLabel = String.format("%s = %s\\n", key,n.getAttr(key));
        	label.append(attrLabel);
        }

        ostream.printf("[label = \"%s\"", label.toString());
        
        Color color = getColor(n.getGeneration());
        ostream.printf(" fillcolor=\"#%2x%2x%2x\" style=filled",color.getRed(),color.getGreen(),color.getBlue() );
        
        ostream.printf("];\n", label.toString());
        
    }
    
    private void printArray(NodeArray array, int level, PrintStream ostream ) { 

        StringBuilder label = new StringBuilder();

        final int len = array.getLength();
        final boolean hasSysHash = array.hasAttr(Named.SYSTEM_HASH);
        if (hasSysHash) {
            label.append("{ {");
        }

        int i = 0;
        for (GraphPoint ele : array.getElements()) {
            
            String subaddr = String.format("<e%d> ", ele.getAttr(Named.ARRAY_INDEX));
            label.append(subaddr);
            if (ele.hasAttr(Named.VALUE)) {
            	Object val = ele.getAttr(Named.VALUE);
            	if (val == null) {
            		label.append("null");
            	}
            	else {
            		String valstr = val.toString();
            		label.append(valstr);
            	}
            }
            else {
                label.append(" ");
            }
            if (i < len-1) {
                label.append('|');
            }
            i++;
        }
        if (hasSysHash) {
            label.append(String.format(" } | 0x%X }",array.getAttr(Named.SYSTEM_HASH)));
        }
        ostream.printf("%snode%s[label = \"%s\"", 
                    ExporterUtils.getIndent(level),array.getID(),label.toString()); 
        
        Color color = getColor(array.getGeneration());
        ostream.printf(" fillcolor=\"#%2x%2x%2x\" style=filled ",color.getRed(),color.getGreen(),color.getBlue() );
        
        ostream.printf("];\n");
                    
    }
     
}
