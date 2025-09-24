package net.slreynolds.ds.export;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.slreynolds.ds.internal.util.FileUtil;
import net.slreynolds.ds.model.Edge;
import net.slreynolds.ds.model.Graph;
import net.slreynolds.ds.model.GraphPoint;
import net.slreynolds.ds.model.Named;
import net.slreynolds.ds.model.Node;
import net.slreynolds.ds.model.NodeArray;

/**
 * Base class for the simple file exporters that save the graph
 * topology with a simple label for each node. Fields inside
 * the nodes are not shown.
 * 
 */
public abstract class AbstractSimpleExporter  extends AbstractExporter {

    private final Map<GraphPoint,ExportVertex> exportVertices;
    private final Map<Edge,ExportEdge> exportEdges;
    private boolean debug = false;
    private int nextID = -1;
    
    public AbstractSimpleExporter() {
    	exportVertices = new HashMap<GraphPoint,ExportVertex>();
    	exportEdges = new HashMap<Edge,ExportEdge>();
    }
    
    public void setDebug(boolean flag) {
    	debug = flag;
    }
    
    protected void resetExportSession(Map<String,Object> opts) throws IOException {
    	exportVertices.clear();
    	exportEdges.clear();
    	nextID = -1;
    }
    
    /**
     * Make and return a PrintStream from the file specified in opts by OUTPUT_PATH.
     * 
     * @param opts Options that must include ExporterOptions.OUTPUT_PATH
     * @return PrintStream from given path
     * @throws IOException
     */
    protected PrintStream makePrintStream(Map<String,Object> opts) throws IOException {
    	String path = (String)opts.get(ExporterOptions.OUTPUT_PATH);
        if (path == null) {
            throw new IllegalArgumentException("Must specify output path"); 
        }
        File out = FileUtil.createEmptyWritableFile(path);
        PrintStream ostream = new PrintStream(out);
        return ostream;
    }
    
    public void export(Graph g, Map<String,Object> opts) throws ExportException, IOException {
        resetExportSession(opts);
		
        startExportSession(); 
        
        // --- Export the nodes ----
        List<GraphPoint> points = g.getGraphPoints();
        
        for (GraphPoint point: points) {
        	//  special case for symbol
        	if (point.hasAttr(Named.SYMBOL) && (Boolean)point.getAttr(Named.SYMBOL)) {
        		exportVertex(getExportVertexFromSymbol(point));
        	}
            else if (point.isArray() && point.areValuesInlined()) {
                addArray((NodeArray)point);
            }
        	else  {
            	exportVertex(getExportVertexFromNode(point));
            }

        }
        
        
       // --- Export the edges ----
       
        for (GraphPoint node: g.getGraphPoints()) {
            List<Edge> edges = node.getNeighbors();
            for (Edge e : edges) {
            	ExportEdge ee = getExportEdge(e);
            	if (debug) {
            		System.out.printf("Adding edge %s from %s to %s\n", ee, ee.getFromID(),ee.getToID());
            	}
            	exportEdge(ee);
            }
        }  

	    finishExportSession();
	   
    }
    
    private ExportVertex getExportVertexFromSymbol(GraphPoint gp) {
    	if (exportVertices.containsKey(gp)) {
    		return exportVertices.get(gp);
    	}
    	
    	ExportVertex ev = new ExportVertex(gp.getID(),gp.getName(),VertexType.SYMBOL, gp.getGeneration());
    	exportVertices.put(gp, ev);
    	return ev;
    }
    
    private ExportVertex getExportVertexFromNode(GraphPoint gp) {
    	if (exportVertices.containsKey(gp)) {
    		return exportVertices.get(gp);
    	}

    	ExportVertex ev = new ExportVertex(gp.getID(),getNodeLabel(gp),VertexType.PLAIN, gp.getGeneration());
    	exportVertices.put(gp, ev);
    	return ev;
    }
    
    private ExportVertex getExportVertexFromArrray(NodeArray na) {
    	if (exportVertices.containsKey(na)) {
    		return exportVertices.get(na);
    	}

    	ExportVertex ev = new ExportVertex(na.getID(),getNodeLabel(na),VertexType.ARRAY, na.getGeneration());
    	exportVertices.put(na, ev);
    	return ev;
    }
    
    private ExportVertex getExportVertexFromArrrayElement(GraphPoint ele) {
    	if (exportVertices.containsKey(ele)) {
    		return exportVertices.get(ele);
    	}
    	Object value = ele.getAttr(Named.VALUE);
    	if (value == null) {
    		return null;
    	}

    	String label = value.toString();
    	ExportVertex ev = new ExportVertex(nextID(), label, VertexType.ARRAY_ELEMENT, ele.getGeneration());
    	exportVertices.put(ele, ev);
    	return ev;
    }
    
    private int nextID() {
    	int id = nextID;
    	nextID--;
    	return id;
    }

	private String getNodeLabel(GraphPoint gp) {
		String nameString = gp.getName();
    	String label = null;
    	if (gp.isAnonymous() || nameString == null || nameString.isEmpty() ||
    			nameString.matches("\\s")) {
    		label = (String) gp.getAttr(Named.CLASS);
    	}
    	else {
    		
    		if (gp.hasAttr(Named.CLASS)) {
    			label = String.format("%s:%s (%d)", nameString, gp.getAttr(Named.CLASS));
    		}
    		else {
    			label = nameString;
    		}
    	}
		return label;
	}
    
    private ExportEdge getExportEdge(Edge e) {
    	if (exportEdges.containsKey(e)) {
    		return exportEdges.get(e);
    	}
    	String label = e.getName();
    	
    	if (e.isAnonymous() || label == null || label.isEmpty() ||
    			label.matches("\\s")) {
    		label =  "";
    	}
    	ExportEdge ee = new ExportEdge(e.getID(), e.getFrom().getID(), e.getTo().getID(),label);
    	exportEdges.put(e, ee);
    	return ee;
    }
    
    private ExportEdge makeExportEdgeFromArrayElement(int i, int fromID, int toID) {
    	
    	String label = String.format("%d", i);
    	ExportEdge ee = new ExportEdge(nextID(),fromID, toID, label);
    	// TODO didn't save this edge in the edge table, no appropriate key for the table. Hackish!
    	return ee;
    }
    

    private void addArray(NodeArray array ) { 

        ExportVertex arrayVertex = getExportVertexFromArrray(array);
        exportVertex(arrayVertex);
        final int len = array.getLength();
        ExportVertex[] vertices = new ExportVertex[len];
        boolean hasAValue = false;
        int i = 0;
        // TODO Can I simplify this logic? Seems too complicated.
        for (GraphPoint ele : array.getElements()) {
           
            if (ele.hasAttr(Named.VALUE)) {
            	hasAValue = true;
            	vertices[i] = getExportVertexFromArrrayElement(ele);
            	if (vertices[i] != null) {
            		exportVertex(vertices[i]);
            	}
            }
            i++;
            // TODO do we need to handle else case here?
        }
        
        if (!hasAValue) return; 
        final int fromID = arrayVertex.getID();
        for (int j = 0; j < len; j++) {
        	if (vertices[j] != null) {
        		ExportEdge eej = makeExportEdgeFromArrayElement(j,fromID,vertices[j].getID());
        		if (debug) {
        			System.out.printf("Adding edge %s from %s to %s\n", eej,arrayVertex,vertices[j]);
        		}
        		exportEdge(eej);
        	}
        	// TODO handle else case here too?
        }
        
    }
    
    
    protected abstract void startExportSession();
    
    protected abstract  void exportVertex(ExportVertex vertex);
    
    protected abstract void exportEdge(ExportEdge edge);
    
    protected abstract void  finishExportSession();
    

}
