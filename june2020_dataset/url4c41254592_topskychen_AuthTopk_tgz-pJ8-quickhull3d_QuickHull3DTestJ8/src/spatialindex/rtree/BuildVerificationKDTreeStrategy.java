/**
 * 
 */
package spatialindex.rtree;

import java.util.ArrayList;
import java.util.HashMap;

import javax.jws.Oneway;

import spatialindex.core.IData;
import spatialindex.core.IEntry;
import spatialindex.core.INode;
import spatialindex.core.IQueryStrategy;
import spatialindex.core.IShape;
import spatialindex.core.IVisitor;
import spatialindex.core.Point;
import spatialindex.core.Region;
import spatialindex.rtree.RTree.Data;

/**
 * @author chenqian
 *
 */
public class BuildVerificationKDTreeStrategy implements IQueryStrategy {
	ArrayList<Integer> ids = null;
	RTree rtree = null;
	KNNCompartor knnc = null;
	IShape query = null;
	IVisitor v = null;
	ArrayList<StateCell> states;
	double dist;
	
	public void LoadKDTree(int nd, int s, int e, Node node, IShape query, KNNCompartor knnc, IVisitor v, Region MBR, double dist){
		if(s > e) return;
		IEntry e1 = null;
		if(s == e){
			if(node.m_level == 0){
				e1 = rtree.new Data(node.m_pData[s], node.m_pMBR[s], node.m_pIdentifier[s]);
//				d.visitData((IData)e1);
				double mindist = knnc.getMinimumDistance(query, e1);
				if(mindist > dist)	{
					states.add(new StateCell(node.m_pIdentifier[s], 1, true, node.m_identifier, nd, mindist));
				}
				else {
					states.add(new StateCell(node.m_pIdentifier[s], 0, true, node.m_identifier, nd, mindist));
				}
			}else{
				e1 = rtree.readNode(node.m_pIdentifier[s]);
				double mindist = knnc.getMinimumDistance(query, e1);
				double maxdist = knnc.getMaximunDistance(query, e1);

				if(mindist > dist){
					states.add(new StateCell(node.m_pIdentifier[s], 1, false, node.m_identifier, nd, mindist));
				}else if(maxdist <= dist){
					states.add(new StateCell(node.m_pIdentifier[s], 0, false, node.m_identifier, nd, maxdist));
				}else{
					ids.add(e1.getIdentifier());
				}
			}
		}else{
			e1 = rtree.new Data(null, MBR, -1);
			double mindist = knnc.getMinimumDistance(query, e1);
			double maxdist = knnc.getMaximunDistance(query, e1);
			int mid = (s + e) >> 1;
			e1 = rtree.new Data(null, node.m_pMBR[mid], -1);
			double tdist = knnc.getMinimumDistance(query, e1);
			if(mindist > dist){
				if(node.m_level == 0){
					states.add(new StateCell(node.m_pIdentifier[mid], 1, true, node.m_identifier, nd, tdist));
				}else {
					states.add(new StateCell(node.m_pIdentifier[mid], 1, false, node.m_identifier, nd, tdist));
				}
			}else if(maxdist <= dist){
				if(node.m_level == 0){
					states.add(new StateCell(node.m_pIdentifier[mid], 0, true, node.m_identifier, nd, tdist));
				} else{
					states.add(new StateCell(node.m_pIdentifier[mid], 0, false, node.m_identifier, nd, tdist));
				}
			}else{
				Region mbr1 = new Region(node.m_pMBR[s]), mbr2 = new Region(node.m_pMBR[e]);
				for(int i = s + 1; i <= mid; i++){
					Region.combinedRegion(mbr1, node.m_pMBR[i]);
				}
				for(int i = mid + 1; i < e; i++){
					Region.combinedRegion(mbr2, node.m_pMBR[i]);
				}
				LoadKDTree(nd << 1, s, mid, node, query, knnc, v, mbr1, dist);
				LoadKDTree((nd << 1) + 1, mid + 1, e, node, query, knnc, v, mbr2, dist);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see spatialindex.core.IQueryStrategy#getNextEntry(spatialindex.core.IEntry, int[], boolean[])
	 */
	@Override
	public void getNextEntry(IEntry arg0, int[] arg1, boolean[] arg2) {
		// TODO Auto-generated method stub
		Node node = (Node) arg0;
		LoadKDTree(1, 0, node.m_children - 1, node, query, knnc, v, node.m_nodeMBR, dist);
		arg2[0] = true;
		if(ids.size() == 0){
			arg2[0] = false;
		}else{
			arg1[0] = ids.remove(0).intValue();
		}
	}

	public BuildVerificationKDTreeStrategy(RTree rtree, IShape query, IVisitor v, double dist, 
			ArrayList<StateCell> _states){
		this.rtree = rtree;
		this.knnc = new KNNCompartor();
		this.query = query;
		this.v = v;
		this.dist = dist;
		ids = new ArrayList<Integer>();
		states = _states;;
	}
	
	class KNNCompartor {

		public double getMinimumDistance(IShape query, IEntry e) {
			// TODO Auto-generated method stub
			IShape s = e.getShape();
			return query.getMinimumDistance(s);
		}
		
		public double getMaximunDistance(IShape query, IEntry e){
			Region region = e.getShape().getMBR();
			double ans = 0.0;
			double x1 = region.getLow(0);
			double y1 = region.getLow(1);
			double z1 = region.getLow(2);
			double x2 = region.getHigh(0);
			double y2 = region.getHigh(1);
			double z2 = region.getHigh(2);
			ans = Math.max(ans, query.getMinimumDistance(new Point(new double[] {x1, y1, z2})));
			ans = Math.max(ans, query.getMinimumDistance(new Point(new double[] {x1, y2, z2})));
			ans = Math.max(ans, query.getMinimumDistance(new Point(new double[] {x2, y1, z2})));
			ans = Math.max(ans, query.getMinimumDistance(new Point(new double[] {x2, y2, z2})));
			return ans;
		}
	}
}
