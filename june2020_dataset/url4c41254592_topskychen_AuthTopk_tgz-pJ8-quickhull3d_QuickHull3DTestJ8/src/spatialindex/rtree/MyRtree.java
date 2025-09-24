package spatialindex.rtree;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import spatialindex.core.IData;
import spatialindex.core.IEntry;
import spatialindex.core.INearestNeighborComparator;
import spatialindex.core.IShape;
import spatialindex.core.IVisitor;
import spatialindex.core.Point;
import spatialindex.core.Region;
import spatialindex.io.IStorageManager;
import spatialindex.setting.PropertySet;
import utility.knnwithcryop;


/**
 * @author chenqian
 */

public class MyRtree extends RTree{
	
	public void BuildEmbededIndex_KDTree(){
		this.queryStrategy(new BuildKDTreeStrategy(this));
		this.flush();
	}
	
//	public MyRtree(PropertySet ps, IStorageManager sm, boolean isBuild) {
//		super(ps, sm);
//		if(isBuild)BuildEmbededIndex_KDTree();
//		// TODO Auto-generated constructor stub
//	}
	
	public MyRtree(PropertySet ps, IStorageManager sm) {
		super(ps, sm);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void nearestNeighborQuery(int k, final IShape query, final IVisitor v, final INearestNeighborComparator nnc) {
		// TODO Need read nearestNeighborQuery
		if (query.getDimension() != m_dimension) {
			throw new IllegalArgumentException("nearestNeighborQuery: Shape has the wrong number of dimensions.");
		}
		m_rwLock.read_lock();

		try {
			// I need a priority queue here. It turns out that TreeSet sorts unique keys only and since I am
			// sorting according to distances, it is not assured that all distances will be unique. TreeMap
			// also sorts unique keys. Thus, I am simulating a priority queue using an ArrayList and binarySearch.
			ArrayList<NNEntry> queue = new ArrayList<NNEntry>();

			Node n = readNode(m_rootID);
			queue.add(new NNEntry(n, 0.0));

			int count = 0;
			double knearest = 0.0;
			int isFinish = 0;
			while (queue.size() != 0) {
				NNEntry first = queue.remove(0);

				if (first.m_pEntry instanceof Node) {
					n = (Node) first.m_pEntry;
					v.visitNode(n, isFinish);
					if(isFinish == 1){
						continue;
					}
					for (int cChild = 0; cChild < n.m_children; cChild++) {
						IEntry e;

						if (n.m_level == 0) {
							e = new Data(n.m_pData[cChild], n.m_pMBR[cChild], n.m_pIdentifier[cChild]);
						} else {
							e = readNode(n.m_pIdentifier[cChild]);
						}

						NNEntry e2 = new NNEntry(e, nnc.getMinimumDistance(query, e));

						// Why don't I use a TreeSet here? See comment above...
						int loc = Collections.binarySearch(queue, e2, new NNEntryComparator());
						if (loc >= 0) {
							queue.add(loc, e2);
						} else {
							queue.add((-loc - 1), e2);
						}
					}
				} else {
					// report all nearest neighbors with equal furthest distances.
					// (neighbors can be more than k, if many happen to have the same
					//  furthest distance).
					if (count >= k) {
						if(isFinish == 0){
							((SecurityVisitor)v).setDistance(knearest);
						}
						isFinish = 1;
						
						//BuildVerification_KDTree(query, v, knearest);
						//LoadKDTree(0, n.m_children - 1, n, query, knnc, v, n.m_nodeMBR, knearest);
					}

					v.visitData((IData) first.m_pEntry, isFinish);
					m_stats.m_queryResults++;
					count++;
					knearest = first.m_minDist;
				}
			}
		} finally {
			m_rwLock.read_unlock();
		}
	}

	@Override
	public void nearestNeighborQuery(int k, final IShape query, final IVisitor v) {
		if (query.getDimension() != m_dimension) {
			throw new IllegalArgumentException("nearestNeighborQuery: Shape has the wrong number of dimensions.");
		}
		NNComparator nnc = new NNComparator();
		nearestNeighborQuery(k, query, v, nnc);
	}
	
	public static void main(String[] args) 
			throws IndexOutOfBoundsException, Exception{
		System.out.println("Please input the filename");
		Scanner in = new Scanner(System.in);
		String filePath = in.nextLine();
	}
}
