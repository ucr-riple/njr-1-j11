/**
 * 
 */
package spatialindex.rtree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import spatialindex.core.IEntry;
import spatialindex.core.IQueryStrategy;
import spatialindex.core.Region;

/**
 * @author chenqian
 *
 */
public class BuildKDTreeStrategy implements IQueryStrategy{
	ArrayList<Integer> ids = null;
	kdentry [] node_list = null;
	RTree rtree = null;
	public int d;
	public void build_kd_tree_internal(int s, int e, int depth){
		if(s >= e)return;
		final int indicator = depth % (2 * d);
		if(indicator < d){			
			Arrays.sort(node_list, s, e, new Comparator<kdentry>() {
				
				@Override
				public int compare(kdentry o1, kdentry o2) {
					// TODO Auto-generated method stub
					if(o1.region.m_pLow[indicator] < o2.region.m_pLow[indicator])return -1;
					else if(o1.region.m_pLow[indicator] > o2.region.m_pLow[indicator])return 1;
					else return 0;
				}
			});
		}else{			
			Arrays.sort(node_list, s, e, new Comparator<kdentry>() {
				
				@Override
				public int compare(kdentry o1, kdentry o2) {
					// TODO Auto-generated method stub
					if(o1.region.m_pHigh[indicator - d] < o2.region.m_pHigh[indicator - d])return -1;
					else if(o1.region.m_pHigh[indicator - d] > o2.region.m_pHigh[indicator - d])return 1;
					else return 0;
				}
			});
		}
		int mid = (e + s) >> 1;
		build_kd_tree_internal(s, mid, depth + 1);
		build_kd_tree_internal(mid + 1, e, depth + 1);
	}
	

	public void build_kd_tree_leaf(int s, int e, int depth){
		if(s >= e)return;
		final int indicater = depth % d;
		Arrays.sort(node_list, s, e, new Comparator<kdentry>() {
			
			@Override
			public int compare(kdentry o1, kdentry o2) {
				// TODO Auto-generated method stub
				if(o1.region.m_pLow[indicater] < o2.region.m_pLow[indicater])return -1;
				else if(o1.region.m_pLow[indicater] > o2.region.m_pLow[indicater])return 1;
				else return 0;
			}
		});
		int mid = (e + s) >> 1;
		build_kd_tree_leaf(s, mid, depth + 1);
		build_kd_tree_leaf(mid + 1, e, depth + 1);
	}
	@Override
	public void getNextEntry(IEntry arg0, int[] arg1, boolean[] arg2) {
		// TODO Auto-generated method stub
		Node node = (Node) arg0;
		node_list = new kdentry[node.m_children];
		int len = node.m_children;
		for(int i = 0; i < len; i ++){
			node_list[i] = new kdentry(i, node.m_pMBR[i]);
		}
		if(node.m_level > 0)build_kd_tree_internal(0, len - 1, 0);
		else build_kd_tree_leaf(0, len - 1, 0);
		int[] tmpdatalength = new int[node.m_capacity + 1];
		byte[][] tmpdata = new byte[node.m_capacity + 1][];
		Region[] tmpmbr = new Region[node.m_capacity + 1];
		int[] tmpidentifier = new int[node.m_capacity + 1];
		for(int i = 0; i < len; i ++){
			int id = node_list[i].id;
			tmpdatalength[i] = node.m_pDataLength[id];
			tmpdata[i] = node.m_pData[id];
			tmpmbr[i] = node.m_pMBR[id];
			tmpidentifier[i] = node.m_pIdentifier[id];
		}
		for(int i = 0; i < len; i++){
			node.m_pDataLength[i] = tmpdatalength[i];
			node.m_pData[i] = tmpdata[i];
			node.m_pMBR[i] = tmpmbr[i];
			node.m_pIdentifier[i] = tmpidentifier[i];
			rtree.writeNode(node);
		}
		if(node.m_level != 0){
			for(int i = 0; i < len; i++){
				ids.add(node.getChildIdentifier(i));
			}
		}
		arg2[0] = true;
		if(ids.size() == 0){
			arg2[0] = false;
		}else{
			arg1[0] = ids.remove(0).intValue();
		}
	}

	public BuildKDTreeStrategy(RTree rtree){
		this.rtree = rtree;
		d = this.rtree.m_dimension;
		ids = new ArrayList<Integer>();
	}
	
	class kdentry{
		int id;
		Region region;
		public kdentry(int id, Region region){
			this.id = id;
			this.region = region;
		}
	}
}
