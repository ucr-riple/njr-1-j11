package net.slreynolds.ds.model;

import java.util.Map;

import net.slreynolds.ds.model.internal.GraphBuildContext;

public interface Builder {

	public void build(String name, Object obj, Graph graph, GraphBuildContext context, Map<String,Object> options)
			throws BuildException;
}
