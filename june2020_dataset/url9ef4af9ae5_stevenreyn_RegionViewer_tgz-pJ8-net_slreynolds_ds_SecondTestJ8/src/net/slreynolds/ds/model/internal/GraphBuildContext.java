package net.slreynolds.ds.model.internal;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import net.slreynolds.ds.model.BuilderOptions;
import net.slreynolds.ds.model.Graph;
import net.slreynolds.ds.model.GraphPoint;

public class GraphBuildContext {
	
	private final IdentityHashMap<Object,GraphPoint> _objectToPointMap;
	private final Map<String,Object> _opts;
	private final Queue<GraphPointSuspension> _pointsToBuild;
	private final Queue<EdgeSuspension> _edgesToBuild;
	private final Graph _graph;
	
	public GraphBuildContext(Graph graph, Map<String,Object> opts) {
		_objectToPointMap = new IdentityHashMap<Object,GraphPoint>();
		_pointsToBuild = new LinkedList<GraphPointSuspension>();
		_edgesToBuild = new LinkedList<EdgeSuspension>();
		_opts = new HashMap<String,Object>(opts);
		_graph = graph;
	}
	
	public boolean hasPoint(Object o) {
		return _objectToPointMap.containsKey(o);
	}
	
	public void setGeneration(int generation) {
		if (generation < 0) {
			throw new IllegalArgumentException("generation cannot be negative");
		}
		_opts.put(BuilderOptions.GENERATION,generation);
	}
	
	public Graph getGraph() {
		return _graph;
	}
	
	public GraphPoint getPoint(Object o) {
		return _objectToPointMap.get(o);
	}

	public void addPoint(Object o, GraphPoint pt) {
		_objectToPointMap.put(o,pt);
		_graph.add(pt);
	}
	
	public Map<String,Object> getOptions() {
		return _opts;
	}
	
	public void enqueuePointToBuild(GraphPointSuspension c) {
		_pointsToBuild.add(c);
	}
	
	public GraphPointSuspension dequeuePointToBuild() {
		return _pointsToBuild.poll();
	}
	
	public boolean hasPointsToBuild() {
		return !_pointsToBuild.isEmpty();
	}
	
	public void enqueueEdgeToBuild(EdgeSuspension c) {
		_edgesToBuild.add(c);
	}
	
	public EdgeSuspension dequeueEdgeToBuild() {
		return _edgesToBuild.poll();
	}
	
	public boolean hasEdgesToBuild() {
		return !_edgesToBuild.isEmpty();
	}	
}
