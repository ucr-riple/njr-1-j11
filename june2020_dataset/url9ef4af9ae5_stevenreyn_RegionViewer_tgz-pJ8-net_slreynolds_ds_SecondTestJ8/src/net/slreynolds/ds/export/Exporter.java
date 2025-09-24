
package net.slreynolds.ds.export;

import java.io.IOException;
import java.util.Map;

import net.slreynolds.ds.model.Graph;

/**
 * Export a Graph
 *
 */
public interface Exporter {
    
    public void export(Graph g, Map<String,Object> opts) throws ExportException, IOException;
}
