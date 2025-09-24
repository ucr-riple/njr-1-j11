
package net.slreynolds.ds.model;

import java.util.Map;

/**
 * Build a Graph
 */
public interface GraphBuilder {

    /**
     * Build a Graph for obj using given options
     * 
     * @param name Name for the primary node in the graph
     * @param obj Object for which we want a Graph
     * @param options Options to use while building the Graph
     * @return Graph that represents the given obj
     * @throws BuildException 
     */
    public void build(String name, Object obj, Graph g,  Map<String,Object> options) throws BuildException;

}
