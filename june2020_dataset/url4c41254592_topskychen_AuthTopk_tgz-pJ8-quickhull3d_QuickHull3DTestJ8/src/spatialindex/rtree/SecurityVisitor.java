package spatialindex.rtree;

import java.util.ArrayList;
import java.util.HashMap;

import spatialindex.core.IData;
import spatialindex.core.INode;
import spatialindex.core.IVisitor;
import spatialindex.core.Region;

public class SecurityVisitor implements IVisitor {
	static int UNDEFINED_INT_VALUE = -1;
	static String UNDEFINED_STR_VALUE = null;

	private static final boolean DEBUG = false;

	private double distance;
	
	private boolean parentNodeInside = false;

	public HashMap<Integer, Integer> nodeState = new HashMap<Integer, Integer>(), dataState = new HashMap<Integer, Integer>();

	private int queryHits = 0;

	public SecurityVisitor() {
		// TODO Auto-generated constructor stub
	}


	@Override
	/**
	 * visit node with range authentication, construct the VO
	 */
	public void visitNode(INode n, int type){
		nodeState.put(n.getIdentifier(), type);
	}

	/**
	 * replace the VisitNode when node is a leaf-node and intersects with the
	 * query range
	 */
	public void visitNode(INode n, int type, int[] childTypes, int maxOmitLength) {
	}

	@Override
	/**
	 * visit node without range authentication
	 */
	public void visitNode(INode n) {
		System.err.println("must invoke visitNode(Node n, int type) instead!");
	}

	@Override
	public void visitData(IData d) {
		if (DEBUG) {
			println("\tvisit data:" + d.getIdentifier());
		}
	}

	private void println(String string) {
		System.out.println(string);
		//SRTreeShower.printLog(string);
	}

	@Override
	public void setParentNodeInside(boolean b) {
		parentNodeInside = b;
	}

	@Override
	public ArrayList<String> getVOStringArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void searchFinished(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParentCellInside(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitNode(INode arg0, int arg1, int[] arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitData(IData d, int type) {
		// TODO Auto-generated method stub
//		System.out.println(d.getIdentifier());
		dataState.put(d.getIdentifier(), type);
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}
}
