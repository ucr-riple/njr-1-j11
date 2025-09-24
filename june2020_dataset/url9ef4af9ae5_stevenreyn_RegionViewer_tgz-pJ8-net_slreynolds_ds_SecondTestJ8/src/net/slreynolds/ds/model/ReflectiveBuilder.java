package net.slreynolds.ds.model;


import java.util.Map;

import net.slreynolds.ds.model.internal.EdgeSuspension;
import net.slreynolds.ds.model.internal.GraphBuildContext;
import net.slreynolds.ds.model.internal.GraphPointSuspension;
import net.slreynolds.ds.model.internal.NodeBuilder;

public class ReflectiveBuilder implements Builder {


	public void build(String name, Object obj, Graph graph, GraphBuildContext context, Map<String,Object> options)
			throws BuildException {
		
		final int generation = (Integer)options.get(BuilderOptions.GENERATION);
		GraphPoint symbol = Node.createSymbol(NamedIDGenerator.next(),name, generation);
		graph.setPrimaryGraphPoint(symbol);
		GraphPoint first = null;
		if (context.hasPoint(obj)) {
			first = context.getPoint(obj);
		}
		else {
			first = NodeBuilder.buildNode(obj, graph, context, 1);
		}
		
		graph.addEdge(symbol, first);
		while (context.hasPointsToBuild()) {
			GraphPointSuspension gpSuspension = context.dequeuePointToBuild();
			if (context.hasPoint(gpSuspension.getObject()))
				continue;
			
			NodeBuilder.buildNode(gpSuspension.getObject(), 
					graph, context, gpSuspension.getNestingLevel());
		} 
		
		while (context.hasEdgesToBuild()) {
			
			EdgeSuspension edgeSuspension = context.dequeueEdgeToBuild();
			GraphPoint src = null;
			if (edgeSuspension.getFrom() instanceof GraphPoint) {
				src = (GraphPoint)edgeSuspension.getFrom();
			}
			else {
				src = context.getPoint(edgeSuspension.getFrom());
				if (src == null) {
					throw new BuildException("Unable to find source point for new edge");
				}
			}
			GraphPoint dest = null;
			if (edgeSuspension.getTo() instanceof GraphPoint) {
				dest = (GraphPoint)edgeSuspension.getTo();
			}
			else {
				dest = context.getPoint(edgeSuspension.getTo());
				if (dest == null) {
					throw new BuildException("Unable to find destination point for new edge");
				}
			} 
			graph.addEdge(edgeSuspension.getName(), src, dest);
		} 
		
		
	}

}
