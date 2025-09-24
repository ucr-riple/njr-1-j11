package utility;

import java.awt.Point;
import java.awt.PointerInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

import spatialindex.core.IEntry;
import spatialindex.core.IQueryStrategy;
import spatialindex.core.IShape;
import spatialindex.core.Region;
import spatialindex.io.DiskStorageManager;
import spatialindex.io.IBuffer;
import spatialindex.io.IStorageManager;
import spatialindex.io.RandomEvictionsBuffer;
import spatialindex.rtree.MyRtree;
import spatialindex.rtree.Node;
import spatialindex.rtree.RTree;
import spatialindex.rtree.SecurityTree;
import spatialindex.setting.PropertySet;
import utility.geo.DataOfLine;
import utility.geo.VoronoiNeighbors;

/**
 * @author chenqian
 *
 */
public class CalcNumberOfMR {
	public static RTree rtree, rtree_kd;
	public static SecurityTree srtree, srtree_kd;
	public static MyRtree myrtree, myrtree_kd;
	
	public static void LoadMyRTree(String localRtreeFilePath, boolean isLoad, MyRtree[] _myrtree, SecurityTree[] _srtree, boolean isKDtree) throws IndexOutOfBoundsException, Exception{
		//load rtree
		PropertySet ps = new PropertySet();
		ps.setProperty("FileName", localRtreeFilePath); // .idx and .dat extensions will be added.
		IStorageManager diskfile = new DiskStorageManager(ps);
		IBuffer file = new RandomEvictionsBuffer(diskfile, 10, false);
		PropertySet ps2 = new PropertySet();
		Integer i = new Integer(1);
		ps2.setProperty("IndexIdentifier", i);
		_myrtree[0] = new MyRtree(ps2, file);
		if(!isLoad && isKDtree){
			_myrtree[0].BuildEmbededIndex_KDTree();
		}
		_srtree[0] = new SecurityTree(_myrtree[0], localRtreeFilePath, isLoad);
	}
	/**
	 * @param args
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	public static void main(String[] args) throws IndexOutOfBoundsException, Exception {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String line = null;
		while(true){
			System.out.println("(a) load NE\n(b) load CA\n(c) load test\n(d) exit");
			String filename = null;
			line = in.nextLine();
			if(line.equalsIgnoreCase("a")){
				filename = "./database/NE";
			}else if(line.equalsIgnoreCase("b")){
				filename = "./database/CA";
			}else if(line.equalsIgnoreCase("c")){
				filename = "./database/test";
			}else{
				break;
			}
			System.out.println("Input limit:");
			int limit = Integer.parseInt(in.nextLine());
			System.out.println("begin loading " + filename + " ...");
			MyRtree[] myrtrees = new MyRtree[1];
			SecurityTree[] srtrees = new SecurityTree[1];
			LoadMyRTree(filename, true, myrtrees, srtrees, false);
			myrtree = myrtrees[0];
			srtree = srtrees[0];
			MyRtree[] myrtrees_kd = new MyRtree[1];
			SecurityTree[] srtrees_kd = new SecurityTree[1];
			LoadMyRTree(filename + "_kd", true, myrtrees_kd, srtrees_kd, true);
			myrtree_kd = myrtrees_kd[0];
			srtree_kd = srtrees_kd[0];
			System.out.println("Load Successfully!");
//			VoronoiNeighbors vn = new VoronoiNeighbors();
//			vn.loadVoronoiNeighbors(filename, nbHashMap);
			travesalStrategy qs = new travesalStrategy(myrtree, limit);
			myrtree.queryStrategy(qs);
			System.out.println("Number of MRtree's preSigned line : " + qs.getNumberOfMRtreeNode() + " " + qs.num);
			travesalStrategy_kd qs_kd = new travesalStrategy_kd(limit);
			myrtree_kd.queryStrategy(qs_kd);
			System.out.println("Number of EMRtree's preSigned line : " + qs_kd.getNumberOfMRtreeNode());
		}
	}
}

class travesalStrategy implements IQueryStrategy{

	ArrayList<Integer> ids = new ArrayList<Integer>();
	HashSet<Long> lineHashSet_index = new HashSet<Long>(), lineHashSet_leaf = new HashSet<Long>();
	int limit;
	public int num = 0;
	RTree rtree = null;
	public long calcLineId(int u, int v, int id){
		return DataOfLine.calcLineId(u, v) * 10 + id;
	}
	
	public void computePreSignedLines(Node node){
		if(node.isIndex()){		
			Node[] nds = new Node[node.getChildrenCount()];
			for(int i = 0; i < nds.length; i++){
				nds[i] = rtree.readNode(node.getChildIdentifier(i));
				ids.add(node.getChildIdentifier(i));
			}
			for(int i = 0; i < nds.length; i++){//ll
				final Point q = new Point((int)nds[i].getShape().getMBR().getLow(0), (int)nds[i].getShape().getMBR().getLow(1));
				PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(nds.length, new Comparator<Node>() {
					@Override
					public int compare(Node o1, Node o2) {
						// TODO Auto-generated method stub
						Point p1 = new Point((int)o1.getShape().getMBR().getLow(0), (int)o1.getShape().getMBR().getLow(1));
						Point p2 = new Point((int)o2.getShape().getMBR().getLow(0), (int)o2.getShape().getMBR().getLow(1));
						double dist1 = Point.distance(q.x, q.y, p1.x, p1.y);
						double dist2 = Point.distance(q.x, q.y, p2.x, p2.y);
						if(dist1 < dist2)return -1;
						else if(dist1 > dist2) return 1;
						return 0;
					}
				});
				for(int j = 0; j < nds.length; j++){
					if(i != j){
						priorityQueue.add(nds[j]);
					}
				}
				int sz = limit;
				while(!priorityQueue.isEmpty() && sz -- > 0){
					Node top = priorityQueue.poll();
					long id = calcLineId(nds[i].getIdentifier(), top.getIdentifier(), 0);
					if(lineHashSet_index.contains(id) == false){
						lineHashSet_index.add(id);
					}num++;
				}
			}
			for(int i = 0; i < nds.length; i++){//lh
				final Point q = new Point((int)nds[i].getShape().getMBR().getLow(0), (int)nds[i].getShape().getMBR().getHigh(1));
				PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(nds.length, new Comparator<Node>() {
					@Override
					public int compare(Node o1, Node o2) {
						// TODO Auto-generated method stub
						Point p1 = new Point((int)o1.getShape().getMBR().getLow(0), (int)o1.getShape().getMBR().getHigh(1));
						Point p2 = new Point((int)o2.getShape().getMBR().getLow(0), (int)o2.getShape().getMBR().getHigh(1));
						double dist1 = Point.distance(q.x, q.y, p1.x, p1.y);
						double dist2 = Point.distance(q.x, q.y, p2.x, p2.y);
						if(dist1 < dist2)return -1;
						else if(dist1 > dist2) return 1;
						return 0;
					}
				});
				for(int j = 0; j < nds.length; j++){
					if(i != j){
						priorityQueue.add(nds[j]);
					}
				}
				int sz = limit;
				while(!priorityQueue.isEmpty() && sz -- > 0){
					Node top = priorityQueue.poll();
					long id = calcLineId(nds[i].getIdentifier(), top.getIdentifier(), 1);
					if(lineHashSet_index.contains(id) == false){
						lineHashSet_index.add(id);
					}num++;
				}
			}
			for(int i = 0; i < nds.length; i++){//hl
				final Point q = new Point((int)nds[i].getShape().getMBR().getHigh(0), (int)nds[i].getShape().getMBR().getLow(1));
				PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(nds.length, new Comparator<Node>() {
					@Override
					public int compare(Node o1, Node o2) {
						// TODO Auto-generated method stub
						Point p1 = new Point((int)o1.getShape().getMBR().getHigh(0), (int)o1.getShape().getMBR().getLow(1));
						Point p2 = new Point((int)o2.getShape().getMBR().getHigh(0), (int)o2.getShape().getMBR().getLow(1));
						double dist1 = Point.distance(q.x, q.y, p1.x, p1.y);
						double dist2 = Point.distance(q.x, q.y, p2.x, p2.y);
						if(dist1 < dist2)return -1;
						else if(dist1 > dist2) return 1;
						return 0;
					}
				});
				for(int j = 0; j < nds.length; j++){
					if(i != j){
						priorityQueue.add(nds[j]);
					}
				}
				int sz = limit;
				while(!priorityQueue.isEmpty() && sz -- > 0){
					Node top = priorityQueue.poll();
					long id = calcLineId(nds[i].getIdentifier(), top.getIdentifier(), 2);
					if(lineHashSet_index.contains(id) == false){
						lineHashSet_index.add(id);
					}num ++;
				}
			}
			for(int i = 0; i < nds.length; i++){//hh
				final Point q = new Point((int)nds[i].getShape().getMBR().getHigh(0), (int)nds[i].getShape().getMBR().getHigh(1));
				PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(nds.length, new Comparator<Node>() {
					@Override
					public int compare(Node o1, Node o2) {
						// TODO Auto-generated method stub
						Point p1 = new Point((int)o1.getShape().getMBR().getHigh(0), (int)o1.getShape().getMBR().getHigh(1));
						Point p2 = new Point((int)o2.getShape().getMBR().getHigh(0), (int)o2.getShape().getMBR().getHigh(1));
						double dist1 = Point.distance(q.x, q.y, p1.x, p1.y);
						double dist2 = Point.distance(q.x, q.y, p2.x, p2.y);
						if(dist1 < dist2)return -1;
						else if(dist1 > dist2) return 1;
						return 0;
					}
				});
				for(int j = 0; j < nds.length; j++){
					if(i != j){
						priorityQueue.add(nds[j]);
					}
				}
				int sz = limit;
				while(!priorityQueue.isEmpty() && sz -- > 0){
					Node top = priorityQueue.poll();
					long id = calcLineId(nds[i].getIdentifier(), top.getIdentifier(), 3);
					if(lineHashSet_index.contains(id) == false){
						lineHashSet_index.add(id);
					}num ++;
				}
			}
		}else{
			MRData[] nds = new MRData[node.getChildrenCount()];
			for(int i = 0; i < nds.length; i++){
				nds[i] = new MRData(node.getChildIdentifier(i), node.getChildShape(i));
			}
			for(int i = 0; i < nds.length; i++){//ll
				final Point q = new Point((int)nds[i].getShape().getMBR().getLow(0), (int)nds[i].getShape().getMBR().getLow(1));
				PriorityQueue<MRData> priorityQueue = new PriorityQueue<MRData>(nds.length, new Comparator<MRData>() {
					@Override
					public int compare(MRData o1, MRData o2) {
						// TODO Auto-generated method stub
						Point p1 = new Point((int)o1.getShape().getMBR().getLow(0), (int)o1.getShape().getMBR().getLow(1));
						Point p2 = new Point((int)o2.getShape().getMBR().getLow(0), (int)o2.getShape().getMBR().getLow(1));
						double dist1 = Point.distance(q.x, q.y, p1.x, p1.y);
						double dist2 = Point.distance(q.x, q.y, p2.x, p2.y);
						if(dist1 < dist2)return -1;
						else if(dist1 > dist2) return 1;
						return 0;
					}
				});
				for(int j = 0; j < nds.length; j++){
					if(i != j){
						priorityQueue.add(nds[j]);
					}
				}
				int sz = limit;
				while(!priorityQueue.isEmpty() && sz -- > 0){
					MRData top = priorityQueue.poll();
					long id = calcLineId(nds[i].getIdentifier(), top.getIdentifier(), 0);
					if(lineHashSet_leaf.contains(id) == false){
						lineHashSet_leaf.add(id);
					}num++;
				}
			}
			for(int i = 0; i < nds.length; i++){//lh
				final Point q = new Point((int)nds[i].getShape().getMBR().getLow(0), (int)nds[i].getShape().getMBR().getHigh(1));
				PriorityQueue<MRData> priorityQueue = new PriorityQueue<MRData>(nds.length, new Comparator<MRData>() {
					@Override
					public int compare(MRData o1, MRData o2) {
						// TODO Auto-generated method stub
						Point p1 = new Point((int)o1.getShape().getMBR().getLow(0), (int)o1.getShape().getMBR().getHigh(1));
						Point p2 = new Point((int)o2.getShape().getMBR().getLow(0), (int)o2.getShape().getMBR().getHigh(1));
						double dist1 = Point.distance(q.x, q.y, p1.x, p1.y);
						double dist2 = Point.distance(q.x, q.y, p2.x, p2.y);
						if(dist1 < dist2)return -1;
						else if(dist1 > dist2) return 1;
						return 0;
					}
				});
				for(int j = 0; j < nds.length; j++){
					if(i != j){
						priorityQueue.add(nds[j]);
					}
				}
				int sz = limit;
				while(!priorityQueue.isEmpty() && sz -- > 0){
					MRData top = priorityQueue.poll();
					long id = calcLineId(nds[i].getIdentifier(), top.getIdentifier(), 1);
					if(lineHashSet_leaf.contains(id) == false){
						lineHashSet_leaf.add(id);
					}num ++;
				}
			}
			for(int i = 0; i < nds.length; i++){//hl
				final Point q = new Point((int)nds[i].getShape().getMBR().getHigh(0), (int)nds[i].getShape().getMBR().getLow(1));
				PriorityQueue<MRData> priorityQueue = new PriorityQueue<MRData>(nds.length, new Comparator<MRData>() {
					@Override
					public int compare(MRData o1, MRData o2) {
						// TODO Auto-generated method stub
						Point p1 = new Point((int)o1.getShape().getMBR().getHigh(0), (int)o1.getShape().getMBR().getLow(1));
						Point p2 = new Point((int)o2.getShape().getMBR().getHigh(0), (int)o2.getShape().getMBR().getLow(1));
						double dist1 = Point.distance(q.x, q.y, p1.x, p1.y);
						double dist2 = Point.distance(q.x, q.y, p2.x, p2.y);
						if(dist1 < dist2)return -1;
						else if(dist1 > dist2) return 1;
						return 0;
					}
				});
				for(int j = 0; j < nds.length; j++){
					if(i != j){
						priorityQueue.add(nds[j]);
					}
				}
				int sz = limit;
				while(!priorityQueue.isEmpty() && sz -- > 0){
					MRData top = priorityQueue.poll();
					long id = calcLineId(nds[i].getIdentifier(), top.getIdentifier(), 2);
					if(lineHashSet_leaf.contains(id) == false){
						lineHashSet_leaf.add(id);
					}num ++;
				}
			}
			for(int i = 0; i < nds.length; i++){//hh
				final Point q = new Point((int)nds[i].getShape().getMBR().getHigh(0), (int)nds[i].getShape().getMBR().getHigh(1));
				PriorityQueue<MRData> priorityQueue = new PriorityQueue<MRData>(nds.length, new Comparator<MRData>() {
					@Override
					public int compare(MRData o1, MRData o2) {
						// TODO Auto-generated method stub
						Point p1 = new Point((int)o1.getShape().getMBR().getHigh(0), (int)o1.getShape().getMBR().getHigh(1));
						Point p2 = new Point((int)o2.getShape().getMBR().getHigh(0), (int)o2.getShape().getMBR().getHigh(1));
						double dist1 = Point.distance(q.x, q.y, p1.x, p1.y);
						double dist2 = Point.distance(q.x, q.y, p2.x, p2.y);
						if(dist1 < dist2)return -1;
						else if(dist1 > dist2) return 1;
						return 0;
					}
				});
				for(int j = 0; j < nds.length; j++){
					if(i != j){
						priorityQueue.add(nds[j]);
					}
				}
				int sz = limit;
				while(!priorityQueue.isEmpty() && sz -- > 0){
					MRData top = priorityQueue.poll();
					long id = calcLineId(nds[i].getIdentifier(), top.getIdentifier(), 3);
					if(lineHashSet_leaf.contains(id) == false){
						lineHashSet_leaf.add(id);
					}num ++;
				}
			}
		}
	}
	
	public long getNumberOfMRtreeNode(){
		return lineHashSet_index.size() + lineHashSet_leaf.size();
	}
	
	@Override
	public void getNextEntry(IEntry e, int[] nextEntry, boolean[] hasNext) {
		// TODO Auto-generated method stub
		Node node = (Node) e;
//		LoadRTree(node, query, knnc, v, node.m_nodeMBR, dist);
		computePreSignedLines(node);
		hasNext[0] = true;
		if(ids.size() == 0){
			hasNext[0] = false;
		}else{
			nextEntry[0] = ids.remove(0).intValue();
		}
	}
	
	public travesalStrategy() {
		// TODO Auto-generated constructor stub
	}
	
	public travesalStrategy(RTree _rtree, int _limit){
		rtree = _rtree;
		limit = _limit;
	}
}


class travesalStrategy_kd implements IQueryStrategy{

	ArrayList<Integer> nextIds = new ArrayList<Integer>();
	long num = 0;
	int limit;
	HashSet<Integer> lineHashSet = new HashSet<Integer>();
	
	public void getids(ArrayList<Integer> ids, int nd, int l, int r, int dp){
		if(dp > limit || l > r)return;
		if(dp == limit)ids.add(nd);
		else{
			int mid = (l + r) >> 1;
			getids(ids, nd << 1, l, mid, dp + 1);
			getids(ids, (nd << 1) + 1, mid + 1, r, dp + 1);
		}
	}
	
	public void computePreSignedLines(Node node, int nd, int l, int r){
		if(l == r){
			if(node.isIndex())nextIds.add(node.getChildIdentifier(l));
			return;
		}else{
			int mid = (l + r) >> 1;
			ArrayList<Integer> ids = new ArrayList<Integer>();
			getids(ids, nd, l, r, 0);
			num += ids.size() * (ids.size() - 1) * 2;
			computePreSignedLines(node, nd << 1, l, mid);
			computePreSignedLines(node, (nd << 1), mid + 1, r);
		}
	}
	
	public long getNumberOfMRtreeNode(){
		return num;
	}
	
	@Override
	public void getNextEntry(IEntry e, int[] nextEntry, boolean[] hasNext) {
		// TODO Auto-generated method stub
		Node node = (Node) e;
//		LoadRTree(node, query, knnc, v, node.m_nodeMBR, dist);
		computePreSignedLines(node, 1, 0, node.getChildrenCount() - 1);
		hasNext[0] = true;
		if(nextIds.size() == 0){
			hasNext[0] = false;
		}else{
			nextEntry[0] = nextIds.remove(0).intValue();
		}
	}
	
	public travesalStrategy_kd() {
		// TODO Auto-generated constructor stub
	}
	
	public travesalStrategy_kd(int _limit){
		limit = _limit;
	}
}

class MRData{
	int id;
	IShape shape;
	public MRData(){}
	public MRData(int id, IShape shape) {
		super();
		this.id = id;
		this.shape = shape;
	}
	public int getIdentifier(){
		return id;
	}
	public IShape getShape(){
		return shape;
	}
}
