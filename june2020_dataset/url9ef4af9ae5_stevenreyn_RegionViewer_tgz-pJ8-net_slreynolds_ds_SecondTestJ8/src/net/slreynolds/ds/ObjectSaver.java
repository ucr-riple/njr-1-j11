
package net.slreynolds.ds;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.slreynolds.ds.export.ExportException;
import net.slreynolds.ds.export.Exporter;
import net.slreynolds.ds.model.BuildException;
import net.slreynolds.ds.model.Builder;
import net.slreynolds.ds.model.BuilderOptions;
import net.slreynolds.ds.model.Graph;
import net.slreynolds.ds.model.ReflectiveBuilder;
import net.slreynolds.ds.model.internal.GraphBuildContext;


/**
 * Save or display objects
 */
public class ObjectSaver {
    

    private final Exporter _exporter;
    private final Builder _builder;
    
    public ObjectSaver(Exporter exporter)	{
    	_exporter = exporter;
    	_builder = new ReflectiveBuilder();
    }
    
    public ObjectSaver(Builder builder, Exporter exporter)	{
    	_exporter = exporter;
    	_builder = builder;
    }   
    


    // TODO exceptions are printed, should log them
    
    public void save(List<Object> srcs, List<String> srcNames, Map<String,Object> options) {
    	String[] srcNamesArray = new String[srcNames.size()];
    	save(srcs.toArray(),srcNames.toArray(srcNamesArray),options);
    }
    
    public void save(Object[] srcs, String[] srcNames, Map<String,Object> options) {
    	if (srcs.length != srcNames.length) {
    		throw new IllegalArgumentException("Length of srcs and srcNames must match");
    	}
    	
    	final int N = srcs.length;
    	Graph g = new Graph();
		GraphBuildContext context = new GraphBuildContext(g,options);
		Map<String,Object> optionsWithGeneration = null;
    	for(int i = 0; i < N; i++) {
    		try {
    			/*
    			 * Don't try to put keys into options, if it's a clojure APersistentMap,
    			 * the put method will throw an exception.
    			 */
    			optionsWithGeneration = new HashMap<String,Object>(options);
    			optionsWithGeneration.put(BuilderOptions.GENERATION, i);
    			context.setGeneration(i);
    			_builder.build(srcNames[i],srcs[i], g, context, optionsWithGeneration);
    		}
    		catch (BuildException be) {
    			be.printStackTrace();
    			return; // TODO should log exception here
    		}
    	}
    	
        try {
            _exporter.export(g, options);
        }
        catch (ExportException ee) {
            ee.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
	
    }
    
    /**
     * Save src with name srcName to options.OUTPUT_PATH.
     * 
     * @param src
     * @param srcName
     * @param options
     */
    public void save(Object src, String srcName, Map<String,Object> options) {
        save(new Object[]{src},
        	 new String[]{srcName},
        	 options);
    }
    

}
