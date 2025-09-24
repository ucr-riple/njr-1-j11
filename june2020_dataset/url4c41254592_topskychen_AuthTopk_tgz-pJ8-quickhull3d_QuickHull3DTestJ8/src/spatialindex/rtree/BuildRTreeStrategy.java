/**
 * 
 */
package spatialindex.rtree;

import java.util.ArrayList;

import spatialindex.core.IEntry;
import spatialindex.core.IQueryStrategy;
import spatialindex.core.IShape;
import spatialindex.core.IVisitor;
import spatialindex.core.Point;
import spatialindex.core.Region;
import spatialindex.rtree.BuildVerificationKDTreeStrategy.KNNCompartor;
import spatialindex.rtree.RTree.Data;

/**
 * @author qchen
 *
 */
public class BuildRTreeStrategy implements IQueryStrategy {

	/* (non-Javadoc)
	 * @see spatialindex.core.IQueryStrategy#getNextEntry(spatialindex.core.IEntry, int[], boolean[])
	 */

	ArrayList<Integer> ids = null;
	RTree rtree = null;
	KNNCompartor knnc = null;
	IShape query = null;
	IVisitor v = null;
	ArrayList<StateCell> states;
	double dist;
	public void LoadRTree(Node node, IShape query, KNNCompartor knnc, IVisitor v, Region MBR, double dist){
		IEntry e1 = null;
		e1 = rtree.new Data(null, MBR, -1);
		for(int i = 0; i < node.m_children; i++){
			double mindist = knnc.getMinimumDistance(query, node.m_pMBR[i]);
			double maxdist = knnc.getMaximunDistance(query, node.m_pMBR[i]);
			if(mindist > dist){
				if(node.m_level == 0){
					states.add(new StateCell(node.m_pIdentifier[i], 1, true, node.m_identifier, -i, mindist));
				}else {
					states.add(new StateCell(node.m_pIdentifier[i], 1, false, node.m_identifier, -i, mindist));
				}
			}else if(maxdist <= dist){
				if(node.m_level == 0){
					states.add(new StateCell(node.m_pIdentifier[i], 0, true, node.m_identifier, -i, maxdist));
				} else{
					states.add(new StateCell(node.m_pIdentifier[i], 0, false, node.m_identifier, -i, maxdist));
				}
			}else{
				if(node.m_level > 0)ids.add(node.m_pIdentifier[i]);
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
		LoadRTree(node, query, knnc, v, node.m_nodeMBR, dist);
		arg2[0] = true;
		if(ids.size() == 0){
			arg2[0] = false;
		}else{
			arg1[0] = ids.remove(0).intValue();
		}
	}
	
	public BuildRTreeStrategy(RTree rtree, IShape query, IVisitor v, double dist, 
			ArrayList<StateCell> _states){
		this.rtree = rtree;
		this.knnc = new KNNCompartor();
		this.query = query;
		this.v = v;
		this.dist = dist;
		ids = new ArrayList<Integer>();
		states = _states;
	}
	
	class KNNCompartor {

		public double getMinimumDistance(IShape query, Region e) {
			// TODO Auto-generated method stub
			return query.getMinimumDistance(e);
		}
		
		public double getMaximunDistance(IShape query, Region e){
			Region region = e;
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
