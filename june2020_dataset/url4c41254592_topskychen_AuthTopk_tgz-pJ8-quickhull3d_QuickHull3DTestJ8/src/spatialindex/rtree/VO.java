package spatialindex.rtree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import mesh.IntArray;

import Math.MathUtility;

import spatialindex.core.IShape;
import spatialindex.core.Point;
import spatialindex.core.Region;
import spatialindex.core.VOErrorException;
import utility.StatisticForAuth;
import utility.knnwithcryop;
import utility.Compare.DataOfPoint;
import utility.Compare.DistanceCompare;
import utility.geo.DataOfLine;
import utility.geo.Line;
import utility.geo.VoronoiNeighbors;
import utility.security.DataIO;
import utility.security.Gfunction;
import utility.security.Hasher;
import utility.security.SecurityUtility;

/**
 * note that, if isSoloAuth = true, no privacy is considered. 
 * @author chenqian
 * */

public class VO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int queryHits;
	private boolean Debug = false;
	/**
	 * use for counting in analyzing VO String
	 */
	private String rsaEntaValue;
	private boolean isParallel = false;
	private int ThreadNum = 16;
	private HashMap<Integer, VOCell> nodeVO = new HashMap<Integer, VOCell>();
	private HashMap<Integer, VOCell> dataVO = new HashMap<Integer, VOCell>();
	private ArrayList<dcCell> dcVO = new ArrayList<dcCell>();
	private ArrayList<lineCell> lineVO = new ArrayList<lineCell>();
	private ArrayList<gfCell> gfVO = new ArrayList<gfCell>();
	private RTree rtree;
	private SecurityTree srtree;
	private StatisticForAuth statForAuth;
	private String sigWithRSA;
	private int type_VO; // 0 means rtree based, 1 means voronoi diagram based, 2 means embeded kdtree rtree based
	private int limit;
	public static boolean isSoloAuth = true;
	private ArrayList<StateCell> states;
	public VO(){	
	}
	
	void handleVOInParallel(final Point query, final int type){
		final ArrayList<cell> vos = new ArrayList<VO.cell>();
		for(int i = 0; i < dcVO.size(); i++){
			vos.add(dcVO.get(i));
		}
		for(int i = 0; i < lineVO.size(); i++){
			vos.add(lineVO.get(i));
		}
		for(int i = 0; i < gfVO.size(); i++){
			vos.add(gfVO.get(i));
		}
		if(isParallel()){
			final boolean[] threadStatus = new boolean[ThreadNum];
			final int[] lock = new int[1];
			//final int limit = vos.size();
			lock[0] = 0;
			for(int id = 0; id < ThreadNum; id ++){
				threadStatus[id] = false;
				final int tid = id;
				new Thread(new Runnable() {
					int threadId;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						threadId = tid;
						while(true){
							int curid;
							synchronized (lock) {
								curid = lock[0];
								lock[0] ++;
							}
							if(curid >= vos.size())break;
							if(type == 0){
								vos.get(curid).verify(query);
							}
							else vos.get(curid).generateVeryfyPart(query);
						}
						threadStatus[threadId] = true;
					}
				}).start();
			}
			while(true){
				boolean found = false;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i = 0; i < ThreadNum; i++){
					if(threadStatus[i] == false){
						found = true;
					}
				}
				if(!found)break;
			}
		}
	}
	
	/**
	 * For Voronoi Diagram
	 * */
	public VO(int k, final IShape query, HashMap<Integer, Integer> dataState, boolean greedy, RTree _rtree, int _limit, boolean isParallel){
		statForAuth = new StatisticForAuth();
		rtree = _rtree;
		type_VO = 1;
		limit = _limit;
		setParallel(isParallel);
		//ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		//long start = bean.getCurrentThreadCpuTime(), end;
		long start = System.currentTimeMillis(), end;
		Integer[] dataList = new Integer[dataState.size()], fDataList = new Integer[dataState.size()];
		int dataLen = 0, fDataLen = 1;
		Iterator<Integer> iter = dataState.keySet().iterator();
		HashSet<Integer> neighborIds = new HashSet<Integer>();
		HashSet<Integer> neighborsOfp1 = new HashSet<Integer>();
		
		
		
		/**
		 * Get neighbors of 1nn, and put points which are near than knn and farther than knn to corresponding arrays.
		 * */
		while(iter.hasNext()){
			int key = iter.next();
//			System.out.print(key + " ");
			if(dataState.get(key) == 0){
				dataList[dataLen ++] = key;
				DataOfPoint dop = rtree.loadDataOfPointFromIndex(key);
				if(neighborsOfp1.size() == 0){
					for(int i = 0; i < dop.delaunayIds.length; i++){
						neighborsOfp1.add(dop.delaunayIds[i].intValue());
					}
				}
				for(int i = 0; i < dop.delaunayIds.length; i++){
					if(neighborIds.contains(dop.delaunayIds[i].intValue()) == false 
							&& (dataState.containsKey(dop.delaunayIds[i].intValue()) == false || dataState.get(dop.delaunayIds[i].intValue()) == 1)){
						if(fDataList == null || fDataLen >= fDataList.length){							
							if(fDataList == null || fDataList.length == 0){
								fDataList = new Integer[1];
							}else{
								Integer[] tmp = new Integer[fDataList.length * 2];
								for(int id = 0; id < fDataList.length; id++){
									tmp[id] = fDataList[id];
								}
								fDataList = tmp;
							}
						}
						fDataList[fDataLen ++] = dop.delaunayIds[i].intValue();
						neighborIds.add(dop.delaunayIds[i].intValue());
					}
				}
			}
		}
		
		/**
		 * sort these points according to distance to query point.
		 * */
		//System.out.println("num of far points : " + fDataLen);
		Arrays.sort(fDataList, 1, fDataLen, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				DataOfPoint dp1 = rtree.loadDataOfPointFromIndex(o1.longValue());
				DataOfPoint dp2 = rtree.loadDataOfPointFromIndex(o2.longValue());
				double dist1 = query.getMinimumDistance(new Point(new double[]{dp1.p.x, dp1.p.y, Math.sqrt(dp1.p.w)}));
				double dist2 = query.getMinimumDistance(new Point(new double[]{dp2.p.x, dp2.p.y, Math.sqrt(dp2.p.w)}));
				if(dist1 < dist2) return -1;
				else if(dist1 > dist2)return 1;
				return 0;
			}
		});
		Arrays.sort(dataList, 0, dataLen, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				DataOfPoint dp1 = rtree.loadDataOfPointFromIndex(o1.longValue());
				DataOfPoint dp2 = rtree.loadDataOfPointFromIndex(o2.longValue());
				double dist1 = query.getMinimumDistance(new Point(new double[]{dp1.p.x, dp1.p.y, Math.sqrt(dp1.p.w)}));
				double dist2 = query.getMinimumDistance(new Point(new double[]{dp2.p.x, dp2.p.y, Math.sqrt(dp2.p.w)}));
				if(dist1 > dist2) return -1;
				else if(dist1 < dist2)return 1;
				return 0;
			}
		});
		fDataList[0] = dataList[0];
		
		
		/**
		 * check the datastate
		 * */
//		for(int i = 1; i < dataLen; i++){
//			DataOfPoint dp1 = rtree.loadDataOfPointFromIndex(dataList[0]);
//			DataOfPoint dp2 = rtree.loadDataOfPointFromIndex(dataList[i]);
//			double dist1 = query.getMinimumDistance(new Point(new double[]{dp1.p.x, dp1.p.y, Math.sqrt(dp1.p.w)}));
//			double dist2 = query.getMinimumDistance(new Point(new double[]{dp2.p.x, dp2.p.y, Math.sqrt(dp2.p.w)}));
//			if(dist1 <= dist2){
//				System.out.println("near error!");
//			}
//		}
//		
//		for(int i = 1; i < fDataLen; i++){
//			DataOfPoint dp1 = rtree.loadDataOfPointFromIndex(dataList[0]);
//			DataOfPoint dp2 = rtree.loadDataOfPointFromIndex(fDataList[i]);
//			double dist1 = query.getMinimumDistance(new Point(new double[]{dp1.p.x, dp1.p.y, Math.sqrt(dp1.p.w)}));
//			double dist2 = query.getMinimumDistance(new Point(new double[]{dp2.p.x, dp2.p.y, Math.sqrt(dp2.p.w)}));
//			if(dist1 >= dist2){
//				System.out.println("far error!!");
//			}
//		}
		
		if(Debug){
			System.out.println(dataLen);
			System.out.println(fDataLen);
		}
		if(greedy){
			for(int i = 0; i < dataLen; i ++){
				Integer pi = dataList[i];
				DataOfLine dataofline = null;
				dataVO.put(pi, new VOCell(false, rtree.loadDataOfPointFromIndex(pi), -1));
				if(neighborsOfp1.contains(pi)){
					if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(pi, dataList[dataLen - 1]);
					if(dataofline != null && computePos(limit, dataVO.get(pi).dataOfPoint, dataList[dataLen - 1])){				
						lineCell linecell = new lineCell(dataofline);
						if(!isParallel())linecell.generateVeryfyPart((Point)query);
						lineVO.add(linecell);
					}else if(lineVO.size() > 0 && computePos(limit, pi, dataList[dataLen - 1])){
						lineVO.add(lineVO.get(0));
						if(Debug)System.out.print(".");
					}else{
						dcCell dccell = new dcCell(dataVO.get(pi).dataOfPoint.p, dataVO.get(dataList[0]).dataOfPoint.p);
						if(!isParallel())dccell.generateVeryfyPart((Point)query);
						dcVO.add(dccell);
//						if(dccell.verify((Point)query) == false){
//							System.out.println("error");
//						}
						statForAuth.num_miss_1n ++;
					}
				}
				if(i == 0)continue;
				boolean found = false;
				for(int j = 0; j < i; j++){//Dist(j, k) >= Dist(i, k)
					Integer pj = dataList[j];
					if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(pi, pj);
					if(dataofline != null && computePos(limit, dataVO.get(pi).dataOfPoint, pj)){						
						lineCell linecell = new lineCell(dataofline);
						if(!isParallel())linecell.generateVeryfyPart((Point)query);
						lineVO.add(linecell);
						found = true;
						break;
					}else if(lineVO.size() > 0 && computePos(limit, pi, pj)){
						lineVO.add(lineVO.get(0));
						if(Debug)System.out.print(".");
						found = true;
						break;
					}
				}
				if(found == false){
					dcCell dccell = new dcCell(dataVO.get(pi).dataOfPoint.p, dataVO.get(dataList[0]).dataOfPoint.p);
					if(!isParallel())dccell.generateVeryfyPart((Point)query);
					dcVO.add(dccell);
//					if(dccell.verify((Point)query) == false){
//						System.out.println("error");
//					}
					statForAuth.num_miss_near ++;
				}
			}
			for(int i = 1; i < fDataLen; i ++){
				Integer pi = fDataList[i];
				dataVO.put(pi, new VOCell(true, rtree.loadDataOfPointFromIndex(pi), -1));
				boolean found = false;
				for(int j = 0; j < i; j++){//Dist(j, k) >= Dist(i, k)
					Integer pj = fDataList[j];
					DataOfLine dataofline = null;
					if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(pi, pj);
					if(dataofline != null && computePos(limit, dataVO.get(pi).dataOfPoint, pj)){			
						lineCell linecell = new lineCell(dataofline);
						if(!isParallel())linecell.generateVeryfyPart((Point)query);
						lineVO.add(linecell);
						found = true;
						break;
					}else if(lineVO.size() > 0 && computePos(limit, pi, pj)){
						lineVO.add(lineVO.get(0));
						if(Debug)System.out.print(".");
						found = true;
						break;
					}
				}
				if(found == false){
					dcCell dccell = new dcCell(dataVO.get(fDataList[0]).dataOfPoint.p, dataVO.get(pi).dataOfPoint.p);
					if(!isParallel())dccell.generateVeryfyPart((Point)query);
					dcVO.add(dccell);
//					if(dccell.verify((Point)query) == false){
//						System.out.println("error");
//					}
					statForAuth.num_miss_far ++;
				}
			}
		}else{
			for(int i = 0; i < dataLen; i++){
				Integer pi = dataList[i];
				DataOfLine dataofline = null;
				dataVO.put(pi, new VOCell(false, rtree.loadDataOfPointFromIndex(pi), -1));
//				System.out.println("res : " + pi + " distance : " + 
//						utility.security.Point.Distance2((long)((Point)query).getCoord(0), (long)((Point)query).getCoord(1), 
//						dataVO.get(pi).dataOfPoint.p.x, dataVO.get(pi).dataOfPoint.p.y));
				if(neighborsOfp1.contains(pi)){
					if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(pi, dataList[dataLen - 1]);
					if(dataofline != null && computePos(limit, dataVO.get(pi).dataOfPoint, dataList[dataLen - 1])){				
						lineCell linecell = new lineCell(dataofline);
						if(!isParallel())linecell.generateVeryfyPart((Point)query);
						lineVO.add(linecell);
					}else if(lineVO.size() > 0 && computePos(limit, pi, dataList[dataLen - 1])){
						lineVO.add(lineVO.get(0));
						if(Debug)System.out.print(".");
					}else{
						dcCell dccell = new dcCell(dataVO.get(pi).dataOfPoint.p, dataVO.get(dataList[0]).dataOfPoint.p);
						if(!isParallel())dccell.generateVeryfyPart((Point)query);
						dcVO.add(dccell);
						statForAuth.num_miss_1n ++;
					}
				}
				if(i == 0)continue;
				if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(pi, dataList[0]);
				if(dataofline != null && computePos(limit, dataVO.get(pi).dataOfPoint, dataList[0])){
					lineCell linecell = new lineCell(dataofline);
					if(!isParallel())linecell.generateVeryfyPart((Point)query);
					lineVO.add(linecell);
				}else if(lineVO.size() > 0 && computePos(limit, pi, dataList[0])){
					lineVO.add(lineVO.get(0));
					if(Debug)System.out.print(".");
				}else{
					dcCell dccell = new dcCell(dataVO.get(pi).dataOfPoint.p, dataVO.get(dataList[0]).dataOfPoint.p);
					if(!isParallel())dccell.generateVeryfyPart((Point)query);
//					if(dccell.verify((Point)query) == false){
//						System.err.println("Err res: " + pi);
//					}
					dcVO.add(dccell);
					statForAuth.num_miss_near ++;
				}
			}
			for(int i = 1; i < fDataLen; i++){
				Integer pi = fDataList[i];
				dataVO.put(pi, new VOCell(true, rtree.loadDataOfPointFromIndex(pi), -1));
				DataOfLine dataofline = null;
//				System.out.println("far : " + pi + " distance : " + 
//						utility.security.Point.Distance2((long)((Point)query).getCoord(0), (long)((Point)query).getCoord(1), 
//						dataVO.get(pi).dataOfPoint.p.x, dataVO.get(pi).dataOfPoint.p.y));
				if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(pi, dataList[0]);
				if(dataofline != null && computePos(limit, dataVO.get(pi).dataOfPoint, dataList[0])){
					lineCell linecell = new lineCell(dataofline);
					if(!isParallel())linecell.generateVeryfyPart((Point)query);
					lineVO.add(linecell);
				}else if(lineVO.size() > 0 && computePos(limit, pi, dataList[0])){
					lineVO.add(lineVO.get(0));
					if(Debug)System.out.print(".");
				}else{
					dcCell dccell = new dcCell(dataVO.get(fDataList[0]).dataOfPoint.p, dataVO.get(pi).dataOfPoint.p);
					if(!isParallel())dccell.generateVeryfyPart((Point)query);
//					if(dccell.verify((Point)query) == false){
//						dccell.dc.pL.print();
//						dccell.dc.pH.print();
//						System.err.println("Err far: " + pi);
//					}
					dcVO.add(dccell);
					statForAuth.num_miss_far ++;
				}
			}
		}
		if(isParallel()){
			if(!isSoloAuth())handleVOInParallel((Point)query, 1);
		}
		ArrayList<String> sigs = new ArrayList<String>();
		for(int i = 0; i < dataLen; i++){
			sigs.add(dataVO.get(dataList[i]).dataOfPoint.getSignature());
		}
		for(int i = 0; i < fDataLen; i++){
			sigs.add(dataVO.get(fDataList[i]).dataOfPoint.getSignature());
		}
		sigWithRSA = SecurityUtility.rsa.getCondensedRSA(sigs.toArray(new String[0]));
		end = System.currentTimeMillis();
		statForAuth.con_time_SP += (end - start);
		statForAuth.size_VO += getVOSize();
		if(!isSoloAuth())computeDataIo();
	}
	
	/**
	 * Judge id is in limit hop of p.
	 * */
	public boolean computePos(int limit, DataOfPoint p, int id){
		if(p == null)return false;
		for(int i = 0; i < p.delaunayIds.length && limit != 0; i++){
			if(p.delaunayIds[i] == id)return true;
		}
		return false;
	}
	
	/**
	 * judge uid and vid are in hop of limit
	 * 
	 * */
	public boolean computePos(int limit, int uid, int vid){
		Queue<Integer> q = new LinkedList<Integer>();
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
		q.add(uid);
		hashMap.put(uid, 0);
		while(!q.isEmpty()){
			int key = q.poll();
			int level = hashMap.get(key);
			if(level > limit)break;
			int[] pid = knnwithcryop.nbHashMap.get(key);
			for(int i = 0; i < pid.length; i++){
				if(hashMap.containsKey(pid[i]) == false && level + 1 <= limit){
					hashMap.put(pid[i], level + 1);
					q.add(pid[i]);
				}
			}
		}
		if(hashMap.containsKey(vid))return true;
		else return false;
	}
	
	public VO(int k, final IShape query, 
			boolean greedy, RTree _rtree, SecurityTree _srtree, int type, double kDistance, int _limit, boolean isParallel){
		statForAuth = new StatisticForAuth();
		rtree = _rtree;
		srtree = _srtree;
		type_VO = type;
		states = new ArrayList<StateCell>();
		limit = _limit;
		setParallel(isParallel);
		utility.security.Point sP = new utility.security.Point((long)((Point)query).getCoord(0), (long)((Point)query).getCoord(1), 0); // Note here
		sP.buildByPaillier();
//		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
//		long start = bean.getCurrentThreadCpuTime(), end;
		long start = System.currentTimeMillis(), end;
		long dios = rtree.getStatistics().getReads(), dioe;
		if(type == 2){
			SecurityVisitor svisitor = new SecurityVisitor();
			BuildVerificationKDTreeStrategy bdKDSG = new BuildVerificationKDTreeStrategy(rtree, query, svisitor , kDistance, 
					states);
			rtree.queryStrategy(bdKDSG);
		}else{
			SecurityVisitor svisitor = new SecurityVisitor();
			BuildRTreeStrategy bdRSG = new BuildRTreeStrategy(rtree, query, svisitor , kDistance, 
					states);
			rtree.queryStrategy(bdRSG);
		}
		StateCell[] dataList = new StateCell[states.size()], fDataList = new StateCell[states.size()];
		int dataLen = 0, fDataLen = 0;
		for(int i = 0; i < states.size(); i++){
			StateCell cell = states.get(i);
			if(cell.getStatus() == 0){
				dataList[dataLen ++] = cell;
			}else{
				fDataList[fDataLen ++] = cell;
			}
		}
		Arrays.sort(fDataList, 0, fDataLen, new Comparator<StateCell>(){
			@Override
			public int compare(StateCell o1, StateCell o2) {
				// TODO Auto-generated method stub
				if(o1.getDist() < o2.getDist()) return -1;
				else if(o1.getDist() > o2.getDist())return 1;
				return 0;
			}
		});
		Arrays.sort(dataList, 0, dataLen, new Comparator<StateCell>() {
			@Override
			public int compare(StateCell o1, StateCell o2) {
				// TODO Auto-generated method stub
				if(o1.getDist() < o2.getDist()) return 1;
				else if(o1.getDist() > o2.getDist())return -1;
				return 0;
			}
		});
		if(Debug){
			System.out.println(dataLen);
			System.out.println(fDataLen);
		}
		StateCell cellk = dataList[0];//pro
		utility.security.Point qk = null;
		fDataList[0] = dataList[0];
		if(greedy){
			for(int i = 0; i < dataLen; i ++){
				StateCell celli = dataList[i];
				if(celli.isLeafEntry()){
					dataVO.put(celli.getId(), new VOCell(false, rtree.loadDataOfPointFromIndex(celli.getId()), -1));
				}else{
					Node node = rtree.readNode(celli.getId());
					SecurityNode snode = srtree.getSecurityNode(celli.getId());
					int[] childIdentidiers = new int[node.getChildrenCount()];
					for(int ch = 0; ch < node.getChildrenCount(); ch ++){
						childIdentidiers[ch] = node.getChildIdentifier(ch);
					}
					nodeVO.put(celli.getId(), new VOCell(false, snode.getMbrDigest(), childIdentidiers, node.m_level, computePoint(query, celli.getId(), sP)));
				}
				if(i == 0){
					if(cellk.isLeafEntry()){
						qk = dataVO.get(cellk.getId()).dataOfPoint.p;
					}else{
						qk = nodeVO.get(cellk.getId()).sPint;
					}
					continue;
				}
				boolean found = false;
				for(int j = 0; j < i; j++){//Dist(j, q) >= Dist(i, q)
					StateCell cellj = dataList[j];
					DataOfLine dataofline = null;
					if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(celli.getId(), cellj.getId());
					DataOfPoint dop = null;
					if(celli.isLeafEntry()){
						dop = dataVO.get(celli.getId()).dataOfPoint;
					}
					if(dataofline != null && computePos(limit, dop, cellj.getId())){						
						lineCell linecell = new lineCell(dataofline);
						if(!isParallel())linecell.generateVeryfyPart((Point)query);
						lineVO.add(linecell);
						found = true;
						break;
					}
					if(isSigned(celli, cellj) && lineVO.size() > 0){
						lineVO.add(lineVO.get(0));
						if(Debug)System.out.print(".");
						found = true;
						break;
					}
				}
				if(found == false){
					utility.security.Point p;
					if(celli.isLeafEntry()){
						p = dataVO.get(celli.getId()).dataOfPoint.p;
					}else{
						p = nodeVO.get(celli.getId()).sPint;
					}
					dcCell dccell = new dcCell(p, qk);
					if(!isParallel())dccell.generateVeryfyPart((Point)query);
					dcVO.add(dccell);
					statForAuth.num_miss_near ++;
				}
			}
			for(int i = 1; i < fDataLen; i ++){
				StateCell celli = fDataList[i];
				if(celli.isLeafEntry()){
					dataVO.put(celli.getId(), new VOCell(true, rtree.loadDataOfPointFromIndex(celli.getId()), -1));
				}else{
					Node node = rtree.readNode(celli.getId());
					SecurityNode snode = srtree.getSecurityNode(celli.getId());
					int[] childIdentidiers = new int[node.getChildrenCount()];
					for(int ch = 0; ch < node.getChildrenCount(); ch ++){
						childIdentidiers[ch] = node.getChildIdentifier(ch);
					}
					nodeVO.put(celli.getId(), new VOCell(false, snode.getMbrDigest(), childIdentidiers, node.m_level, computePoint(query, celli.getId(), sP)));
				}
				boolean found = false;
				for(int j = 0; j < i; j++){//Dist(j, k) >= Dist(i, k)
					StateCell cellj = fDataList[j];
					DataOfLine dataofline = null;
					if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(celli.getId(), cellj.getId());
					DataOfPoint dop = null;
					if(celli.isLeafEntry()){
						dop = dataVO.get(celli.getId()).dataOfPoint;
					}
					if(dataofline != null && computePos(limit, dop, cellj.getId())){						
						lineCell linecell = new lineCell(dataofline);
						if(!isParallel())linecell.generateVeryfyPart((Point)query);
						lineVO.add(linecell);
						found = true;
						break;
					}
					if(isSigned(celli, cellj) && lineVO.size() > 0){
						lineVO.add(lineVO.get(0));
						if(Debug)System.out.print(".");
						found = true;
						break;
					}
				}
				if(found == false){
					utility.security.Point p;
					if(celli.isLeafEntry()){
						p = dataVO.get(celli.getId()).dataOfPoint.p;
					}else{
						p = nodeVO.get(celli.getId()).sPint;
					}
					dcCell dccell = new dcCell(qk, p);
					if(!isParallel())dccell.generateVeryfyPart((Point)query);
					dcVO.add(dccell);
					statForAuth.num_miss_far ++;
				}
			}
		}else{
			for(int i = 0; i < dataLen; i++){
				StateCell celli = dataList[i];
				if(celli.isLeafEntry()){
					dataVO.put(celli.getId(), new VOCell(false, rtree.loadDataOfPointFromIndex(celli.getId()), -1));
				}else{
					Node node = rtree.readNode(celli.getId());
					SecurityNode snode = srtree.getSecurityNode(celli.getId());
					int[] childIdentidiers = new int[node.getChildrenCount()];
					for(int ch = 0; ch < node.getChildrenCount(); ch ++){
						childIdentidiers[ch] = node.getChildIdentifier(ch);
					}
					nodeVO.put(celli.getId(), new VOCell(false, snode.getMbrDigest(), childIdentidiers, node.m_level, computePoint(query, celli.getId(), sP)));
				}
				if(i == 0){
					if(cellk.isLeafEntry()){
						qk = dataVO.get(cellk.getId()).dataOfPoint.p;
					}else{
						qk = nodeVO.get(cellk.getId()).sPint;
					}
					continue;
				}
				DataOfLine dataofline = null;
				if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(celli.getId(), cellk.getId());
				DataOfPoint dop = null;
				if(celli.isLeafEntry()){
					dop = dataVO.get(celli.getId()).dataOfPoint;
				}
				if(dataofline != null && computePos(limit, dop, cellk.getId())){
					lineCell linecell = new lineCell(dataofline);
					if(!isParallel())linecell.generateVeryfyPart((Point)query);
					lineVO.add(linecell);
				}else if(isSigned(celli, cellk) && lineVO.size() > 0){
					lineVO.add(lineVO.get(0));
					if(Debug)System.out.print(".");
				}else{
					utility.security.Point p;
					if(celli.isLeafEntry()){
						p = dataVO.get(celli.getId()).dataOfPoint.p;
					}else{
						p = nodeVO.get(celli.getId()).sPint;
					}
					dcCell dccell = new dcCell(p, qk);
					if(!isParallel())dccell.generateVeryfyPart((Point)query);
					dcVO.add(dccell);
					statForAuth.num_miss_near ++;
				}
			}
			for(int i = 1; i < fDataLen; i++){
				StateCell celli = fDataList[i];
				if(celli.isLeafEntry()){
					dataVO.put(celli.getId(), new VOCell(true, rtree.loadDataOfPointFromIndex(celli.getId()), -1));
				}else{
					Node node = rtree.readNode(celli.getId());
					SecurityNode snode = srtree.getSecurityNode(celli.getId());
					int[] childIdentidiers = new int[node.getChildrenCount()];
					for(int ch = 0; ch < node.getChildrenCount(); ch ++){
						childIdentidiers[ch] = node.getChildIdentifier(ch);
					}
					nodeVO.put(celli.getId(), new VOCell(false, snode.getMbrDigest(), childIdentidiers, node.m_level, computePoint(query, celli.getId(), sP)));
				}
				DataOfLine dataofline = null;
				if(!isSoloAuth)dataofline = rtree.loadDataOfLineFromBtree(celli.getId(), cellk.getId());
				DataOfPoint dop = null;
				if(celli.isLeafEntry()){
					dop = dataVO.get(celli.getId()).dataOfPoint;
				}
				if(dataofline != null && computePos(limit, dop, cellk.getId())){
					lineCell linecell = new lineCell(dataofline);
					if(!isParallel())linecell.generateVeryfyPart((Point)query);
					lineVO.add(linecell);
				}else if(isSigned(celli, cellk) && lineVO.size() > 0){
						lineVO.add(lineVO.get(0));
						if(Debug)System.out.print(".");
				}else{
					utility.security.Point p;
					if(celli.isLeafEntry()){
						p = dataVO.get(celli.getId()).dataOfPoint.p;
					}else{
						p = nodeVO.get(celli.getId()).sPint;
					}
					dcCell dccell = new dcCell(qk, p);
					if(!isParallel())dccell.generateVeryfyPart((Point)query);
					dcVO.add(dccell);
					statForAuth.num_miss_far ++;
				}
			}
		}
		if(isParallel()){
			if(!isSoloAuth())handleVOInParallel((Point)query, 1);
		}
//		end = bean.getCurrentThreadCpuTime();
		end = System.currentTimeMillis();
		dioe = rtree.getStatistics().getReads();
		statForAuth.con_time_SP += (end - start);
		statForAuth.size_VO += getVOSize();
		statForAuth.num_dataio += dioe - dios;
		if(!isSoloAuth())computeDataIo();
	}
	
	public utility.security.Point computePoint(IShape query, int key, utility.security.Point sP){
		Node node = rtree.readNode(key);
		SecurityNode snode = srtree.getSecurityNode(key);
		//System.out.println(key);
		nodeVO.put(key, new VOCell(true, snode.getChildDigest(), snode.getMbrDigest(), node.m_level));
		Region region = node.m_nodeMBR;
		DataOfPoint[] dops = snode.getDOPs(rtree);
		gfCell gfcell = new gfCell();
		utility.security.Point p = new utility.security.Point();
		if(query.getMBR().m_pLow[0] < region.m_pLow[0]){
			gfcell.setGf(dops[0].gf_x, true);
			p.setXSide(dops[0].p);
		}
		if(query.getMBR().m_pLow[0] > region.m_pHigh[0]){
			gfcell.setGf(dops[1].gf_x, false);
			p.setXSide(dops[1].p);
		}
		if(query.getMBR().m_pLow[1] < region.m_pLow[1]){
			gfcell.setGf2(dops[0].gf_y, true);
			p.setYSide(dops[0].p);
		}
		if(query.getMBR().m_pLow[1] > region.m_pHigh[1]){
			gfcell.setGf2(dops[1].gf_y, false);
			p.setYSide(dops[1].p);
		}
		if(p.g_p_x2 == null){
			p.setXSide(sP);
		}
		if(p.g_p_y2 == null){
			p.setYSide(sP);
		}
		if(!isParallel())gfcell.generateVeryfyPart((Point)query);
		gfVO.add(gfcell);
		return p;
	}
	
	public void computeDataIo(){
		if(type_VO == 0 || type_VO == 2){
			Iterator<Integer> iter = nodeVO.keySet().iterator();
			while(iter.hasNext()){
				statForAuth.num_dataio += Math.ceil(nodeVO.get(iter.next()).writeToBytes().length / 4.0 / 1024);
			}
			iter = dataVO.keySet().iterator();
			while(iter.hasNext()){
				statForAuth.num_dataio += Math.ceil(dataVO.get(iter.next()).writeToBytes().length / 4.0 / 1024);
			}
			for(int i = 0; i < dcVO.size(); i++){
				statForAuth.num_dataio += Math.ceil(dcVO.get(i).writeToBytes().length/ 4.0 / 1024);
			}
			for(int i = 0; i < lineVO.size(); i++){
				statForAuth.num_dataio += Math.ceil(lineVO.get(i).writeToBytes().length / 4.0 / 1024);
			}
			for(int i = 0; i < gfVO.size(); i++){
				statForAuth.num_dataio += Math.ceil(gfVO.get(i).writeToBytes().length / 4.0 / 1024);
			}
		}else if(type_VO == 1){
			Iterator<Integer> iter = dataVO.keySet().iterator();
			while(iter.hasNext()){
				statForAuth.num_dataio += Math.ceil(dataVO.get(iter.next()).writeToBytes().length / 4.0 / 1024);
			}
			for(int i = 0; i < dcVO.size(); i++){
				statForAuth.num_dataio += Math.ceil(dcVO.get(i).writeToBytes().length / 4.0 / 1024);
			}
			for(int i = 0; i < lineVO.size(); i++){
				statForAuth.num_dataio += Math.ceil(lineVO.get(i).writeToBytes().length / 4.0 / 1024);
			}
		}else{
			System.err.println("No such type!");
		}
	}
	
	public long getVOSize(){
		long ans = 0;
		if(type_VO == 0 || type_VO == 2){
			Iterator<Integer> iter = nodeVO.keySet().iterator();
			while(iter.hasNext()){
				int key = iter.next();
				if(!isSoloAuth())ans += nodeVO.get(key).getVOSize();
				else ans += 160 / 8 + 4 * 4 * 2;
			}
			iter = dataVO.keySet().iterator();
			while(iter.hasNext()){
				int key = iter.next();
				if(!isSoloAuth())ans += dataVO.get(key).getVOSize();
				else ans += 160 / 8 + 4 * 3;
			}
			for(int i = 0; i < dcVO.size(); i++){
				if(!isSoloAuth())ans += dcVO.get(i).getVOSize();
				else ans += 4;
			}
			for(int i = 0; i < lineVO.size(); i++){
//				System.out.print(i + "\t");
				if(!isSoloAuth())ans += lineVO.get(i).getVOSize();
				else ans += 4;
			}
			for(int i = 0; i < gfVO.size(); i++){
				if(!isSoloAuth())ans += gfVO.get(i).getVOSize();
			}
			ans += 256;
		}else if(type_VO == 1){
			Iterator<Integer> iter = dataVO.keySet().iterator();
			while(iter.hasNext()){
				int key = iter.next();
				if(!isSoloAuth())ans += dataVO.get(key).getVOSizeBOVD();
				else ans += 160 / 8 + 4 * 3;
			}
			for(int i = 0; i < dcVO.size(); i++){
				if(!isSoloAuth())ans += dcVO.get(i).getVOSize();
				else ans += 4;
			}
			for(int i = 0; i < lineVO.size(); i++){
				if(!isSoloAuth())ans += lineVO.get(i).getVOSize();
				else ans += 4;
			}
			ans += 256;
		}else{
			System.err.println("No such type!");
		}
		return ans;
	}
	
	public boolean isSigned(StateCell a, StateCell b){
		if(a.getParent() != b.getParent())return false;
		if(a.getLevel() <= 0){
			if(Math.abs(a.getLevel() - b.getLevel()) <= (limit))return true;
			else return false;
		}
		else if((a.getLevel() >> (limit)) == (b.getLevel() >> (limit)))return true;
//		else if(a.getLevel() == (b.getLevel() >> 2) || a.getLevel() == (b.getLevel() >> 1) ||  (a.getLevel() >> 1) == b.getLevel() || (a.getLevel() >> 2) == b.getLevel())return true;
		return false;
	}
	
	public StatisticForAuth getStatistics(){
		return statForAuth;
	}
	
	public String computeRootEnta(Region queryRegion) throws IndexOutOfBoundsException, Exception {
		return null;
	}

	public String getrsaEntaValue(){
		return null;
	}
	
	public void buildenta(VOCell cell, VOCell[] childCells, boolean parentNodeInside, Region queryRegion) throws IndexOutOfBoundsException, VOErrorException, Exception{
		
	}
	
	public String reConstruct(int root){
		if(nodeVO.containsKey(root) == false){
			System.err.println("Not found id :" + root);
			return "-1";
		}
		VOCell vocell = nodeVO.get(root);
		ArrayList<String> tmp = new ArrayList<String>();
		if(vocell.level > 0){
			tmp.add(vocell.mbrDigest);
			if(vocell.isFinish){
				tmp.add(vocell.childDigest);
			}else{
				ArrayList<String> tmp2 = new ArrayList<String>();
				for(int i = 0; i < vocell.childIdentifiers.length; i++){
					tmp2.add(reConstruct(vocell.childIdentifiers[i]));
				}
				try {
					tmp.add(SecurityUtility.computeHashValue(tmp2.toArray(new String[0])));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			if(vocell.isFinish){
				tmp.add(vocell.childDigest);
			}else{
				ArrayList<String> tmp2 = new ArrayList<String>();
				for(int i = 0; i < vocell.childIdentifiers.length; i++){
					if(dataVO.containsKey(vocell.childIdentifiers[i]) == false){
						System.err.println("Not found data id: " + vocell.childIdentifiers[i]);
						return "-1";
					}
					VOCell datacell = dataVO.get(vocell.childIdentifiers[i]);
//					System.err.println(srtree.getSecurityNode(root).getChildGValueById(i));
//					System.err.println(datacell.dataOfPoint.p.getDigest());
//					if(DataIO.compareString(datacell.dataOfPoint.p.getDigest(), srtree.getSecurityNode(root).getChildGValueById(i)) == false){
//						System.err.println("Err at data entry : " + i + ", " + vocell.childIdentifiers[i] + ", " + rtree.readNode(root).getChildIdentifier(i));
//						if(DataIO.compareString(rtree.loadDataOfPointFromBtree(vocell.childIdentifiers[i]).p.getDigest(), datacell.dataOfPoint.p.getDigest()) == false){
//							System.err.println("!!!!");
//						}
//						if(DataIO.compareString(rtree.loadDataOfPointFromBtree(vocell.childIdentifiers[i]).p.getDigest(), srtree.getSecurityNode(root).getChildGValueById(i)) == false){
//							System.err.println("!!!!");
//						}
//					}
					tmp2.add(datacell.dataOfPoint.p.getDigest());
					tmp2.add(new Integer(vocell.childIdentifiers[i]).toString());	
				}
				try {
					tmp.add(SecurityUtility.computeHashValue(tmp2.toArray(new String[0])));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			tmp.add(vocell.mbrDigest);	
		}
		try {
			//System.out.println("now id: " + root);
			String digest = SecurityUtility.computeHashValue(tmp.toArray(new String[0]));
//			if(DataIO.compareString(digest, srtree.getSecurityNode(root).getEntaValue()) == false){
//				System.err.println("Err at id:" + root);
//			}else{
//				System.out.println("Pass at id:" + root);
//			}
			return digest;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean verify(final Point query){
		statForAuth.num_PPB += dcVO.size();
		statForAuth.num_PLB += lineVO.size();
		statForAuth.num_Gf += gfVO.size();
		
//		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
//		long start = bean.getCurrentThreadCpuTime(), end;
		long start = System.currentTimeMillis(), end;
//		final ArrayList<cell> vos = new ArrayList<VO.cell>();
		if(type_VO == 0){
			if(!isParallel()){
				for(int i = 0; i < dcVO.size(); i++){
					if(!dcVO.get(i).verify(query)){
						//					System.out.println(i + "\t:fail");
						//					return false;
					}
				}
				//System.out.println("Pass dc!");
				for(int i = 0; i < lineVO.size(); i++){
					if(!lineVO.get(i).verify(query)){
//					return false;
					}					
				}
//			System.out.println("Pass line!");
				for(int i = 0; i < gfVO.size(); i++){
					if(!gfVO.get(i).verify(query)){
//					return false;
					}
				}
			}
			if(isSoloAuth()){
				for(int i = 0; i < dcVO.size(); i++){
					dcCell cell = dcVO.get(i);
					double dist1 = MathUtility.getDistance(cell.p1.x, cell.p1.y, query.getCoord(0), query.getCoord(1));
					double dist2 = MathUtility.getDistance(cell.p2.x, cell.p2.y, query.getCoord(0), query.getCoord(1));
					if(dist1 < dist2);
					Hasher.hashString(new Integer(i).toString());
					Hasher.hashString(new Integer(i + 1).toString());
				}
				for(int i = 0; i < lineVO.size(); i++){
					lineCell cell = lineVO.get(i);
					double dist1 = MathUtility.getDistance(cell.line.pL.x, cell.line.pL.y, query.getCoord(0), query.getCoord(1));
					double dist2 = MathUtility.getDistance(cell.line.pH.x, cell.line.pH.y, query.getCoord(0), query.getCoord(1));
					if(dist1 < dist2);
					Hasher.hashString(new Integer(i).toString());
					Hasher.hashString(new Integer(i + 1).toString());
				}
			}
//			System.out.println("Pass gf!");
//			String rootDigest = reConstruct(rtree.m_rootID);
			//System.out.println(rootDigest);System.out.println(srtree.getRootEntaValue());
			try {
				SecurityUtility.deSignWithRSA(srtree.getrsarootentaValue());
//				if(DataIO.compareStringInRSA(SecurityUtility.deSignWithRSA(srtree.getrsarootentaValue()), rootDigest) == false)return false;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(type_VO == 1){// voronoi diagram
			ArrayList<String> digests = new ArrayList<String>();
			if(!isParallel()){
				for(int i = 0; i < dcVO.size(); i++){
					//System.out.println(i);
					if(!dcVO.get(i).verify(query)){
//					return false;
					}
				}
//			System.out.println("Pass dc!");
				for(int i = 0; i < lineVO.size(); i++){
					if(!lineVO.get(i).verify(query)){
						//					return false;
					}
				}
//			System.out.println("Pass line!");
				
			}
			if(isSoloAuth()){
				for(int i = 0; i < dcVO.size(); i++){
					dcCell cell = dcVO.get(i);
					double dist1 = MathUtility.getDistance(cell.p1.x, cell.p1.y, query.getCoord(0), query.getCoord(1));
					double dist2 = MathUtility.getDistance(cell.p2.x, cell.p2.y, query.getCoord(0), query.getCoord(1));
					if(dist1 < dist2);
					Hasher.hashString(new Integer(i).toString());
					Hasher.hashString(new Integer(i + 1).toString());
				}
				for(int i = 0; i < lineVO.size(); i++){
					lineCell cell = lineVO.get(i);
					double dist1 = MathUtility.getDistance(cell.line.pL.x, cell.line.pL.y, query.getCoord(0), query.getCoord(1));
					double dist2 = MathUtility.getDistance(cell.line.pH.x, cell.line.pH.y, query.getCoord(0), query.getCoord(1));
					if(dist1 < dist2);	
					Hasher.hashString(new Integer(i).toString());
					Hasher.hashString(new Integer(i + 1).toString());
				}
			}
			Iterator<Integer> iter = dataVO.keySet().iterator();
			while(iter.hasNext()){
				digests.add(dataVO.get(iter.next()).dataOfPoint.getDigest());
			}
			String combineDig = SecurityUtility.rsa.getCondensedRSA(digests.toArray(new String[0]));
			try {
				if(DataIO.compareStringInRSA(combineDig, SecurityUtility.deSignWithRSA(sigWithRSA)) == false)return false;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{//kd tree embeded
			
			if(!isParallel()){				
				for(int i = 0; i < dcVO.size(); i++){
					if(!dcVO.get(i).verify(query)){
//					return false;
					}
				}
//			System.out.println("Pass dc!");
				for(int i = 0; i < lineVO.size(); i++){
					if(!lineVO.get(i).verify(query)){
						//					return false;
					}
				}
//			System.out.println("Pass line!");
				for(int i = 0; i < gfVO.size(); i++){
					if(!gfVO.get(i).verify(query)){
						//					return false;
					}
				}
//			System.out.println("Pass gf!");
			}
			if(isSoloAuth()){
				for(int i = 0; i < dcVO.size(); i++){
					dcCell cell = dcVO.get(i);
					double dist1 = MathUtility.getDistance(cell.p1.x, cell.p1.y, query.getCoord(0), query.getCoord(1));
					double dist2 = MathUtility.getDistance(cell.p2.x, cell.p2.y, query.getCoord(0), query.getCoord(1));
					if(dist1 < dist2);
					Hasher.hashString(new Integer(i).toString());
					Hasher.hashString(new Integer(i + 1).toString());
				}
				for(int i = 0; i < lineVO.size(); i++){
					lineCell cell = lineVO.get(i);
					double dist1 = MathUtility.getDistance(cell.line.pL.x, cell.line.pL.y, query.getCoord(0), query.getCoord(1));
					double dist2 = MathUtility.getDistance(cell.line.pH.x, cell.line.pH.y, query.getCoord(0), query.getCoord(1));
					if(dist1 < dist2);	
					Hasher.hashString(new Integer(i).toString());
					Hasher.hashString(new Integer(i + 1).toString());
				}
			}
			try {
				SecurityUtility.deSignWithRSA(srtree.getrsarootentaValue());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(isParallel()){
			if(!isSoloAuth())handleVOInParallel(query, 0);
		}
//		end = bean.getCurrentThreadCpuTime();
		end = System.currentTimeMillis();
		statForAuth.vrf_time_CL += (end - start);
		return true;
	}
	
	public byte[] writeToBytes() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeInt(nodeVO.size());
		dos.writeInt(dataVO.size());
		dos.flush();
		dos.close();
		return baos.toByteArray();
	}
	
	public void readFromBytes(byte[] data) throws IOException{
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(bais);
		int nodeLen = dis.readInt(), dataLen = dis.readInt();
		dis.close();
		bais.close();
	}
	
	public boolean isParallel() {
		return isParallel;
	}
	
	public boolean isSoloAuth(){
		return isSoloAuth;
	}

	public void setParallel(boolean isParallel) {
		this.isParallel = isParallel;
	}

	public int getThreadNum() {
		return ThreadNum;
	}

	public void setThreadNum(int threadNum) {
		ThreadNum = threadNum;
	}

	private class VOCell implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Integer identifier;
		public boolean isFinish;
		public DataOfPoint dataOfPoint = null;
		public utility.security.Point sPint;
		public String mbrDigest;
		public int[] childIdentifiers;
		public String childDigest;
		public int level;
		public VOCell(){}
		
		public long getVOSize(){
			long ans = 0;
			if(isFinish){
				if(level >= 0){
					ans += mbrDigest.getBytes().length;
					ans += childDigest.getBytes().length;
				}else{
					ans += dataOfPoint.p.getDigest().getBytes().length;
//					ans += sPint.getDigest().getBytes().length;
				}
				ans += 4; //level
			}else{
				if(level >= 0){
					ans += mbrDigest.getBytes().length;
					ans += childIdentifiers.length * 4;
				}else{
					ans += dataOfPoint.p.getDigest().getBytes().length;
//					ans += sPint.getDigest().getBytes().length;
				}
				ans += 4; //level
			}
			return ans;
		}
		
		public long getVOSizeBOVD(){
			long ans = 0;
			for(int i = 0; i < dataOfPoint.delaunayIds.length; i++){
				ans += Hasher.hashString(dataOfPoint.delaunayIds[i].toString()).getBytes().length;
			}
			ans += dataOfPoint.p.getDigest().getBytes().length;
			ans += 4; // id
			return ans;
		}
		
		public void setLevel(int _level){
			level = _level;
		}
		
		public VOCell(boolean _isResult, DataOfPoint _dataOfPoint, int _level){
			identifier = _dataOfPoint.pid;
			isFinish =_isResult;
			dataOfPoint = _dataOfPoint;
			level = _level;
		} 
		
		public VOCell(boolean _isResult, String digest, int[] _childIdentifiers, int _level, utility.security.Point _sPoint){
			isFinish = _isResult;
			mbrDigest = digest;
			childIdentifiers = new int[_childIdentifiers.length];
			for(int i = 0; i < childIdentifiers.length; i++){
				childIdentifiers[i] = _childIdentifiers[i];
			}
			level = _level;
			sPint = _sPoint;
		}
		
		public VOCell(boolean _isResult, String _childDigest, String digest, int _level){
			isFinish = _isResult;
			childDigest = _childDigest;
			mbrDigest = digest;
			level = _level;
		}
		
		public void writeStringArray(DataOutputStream dos, String[][] strs) throws IOException{
			if(strs == null){
				dos.writeInt(0);
				return;
			}
			int len = strs.length;
			dos.writeInt(len);
			for(int i = 0; i < len; i++){
				if(strs[i] == null || strs[i].length == 0){
					dos.writeInt(0);
					continue;
				}
				dos.writeInt(strs[i].length);
				for(int j = 0; j < strs[i].length; j++){
					DataIO.writeString(dos, strs[i][j]);
				}
			}
		}
		
		public String[][] readStringArray(DataInputStream dis) throws IOException{
			int len = dis.readInt();
			if(len == 0)return null;
			String[][] ans = new String[len][];
			for(int i = 0; i < len; i++){
				int m = dis.readInt();
				if(m == 0)continue;
				ans[i] = new String[m];
				for(int j = 0; j < m; j++){
					ans[i][j] = DataIO.readString(dis);
				}
			}
			return ans;
		}
		
		public void writeToFile(DataOutputStream dos) throws IOException{
			
		}
		
		public void readFromFile(DataInputStream dis) throws IOException{
			
		}
		
		public byte[] writeToBytes(){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			if(dataOfPoint != null){
				try {
					return dataOfPoint.writeToBytes();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				sPint.writeToFile(dos);
				return baos.toByteArray();
			}
			return null;
		}
	}
	
	public class lineCell implements cell{
		public long id1, id2;
		public Line line;
		public String signature;
		public lineCell(){}
		public lineCell(DataOfLine dataOfLine){
			id1 = dataOfLine.lid / DataOfLine.M;
			id2 = dataOfLine.lid % DataOfLine.M;
			line = dataOfLine.line;
			signature = dataOfLine.signature;
		}
		
		public void generateVeryfyPart(Point q){
			line.GenerateVeryfyPart(new utility.security.Point((long)q.getCoord(0), (long)q.getCoord(1), 0), true); // here may need further check, about 'true'
		}
		
		public boolean verify(Point q){
			return line.ClientVerify(new utility.security.Point((long)q.getCoord(0), (long)q.getCoord(1), 0));
		}
		
		public long getVOSize(){
			long ans = 0;
			ans += line.getVOSize();
			return ans;
		}
		
		public byte[] writeToBytes(){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			line.writeToFile(dos);
			return baos.toByteArray();
		}
	}
	
	public class dcCell implements cell{
		public DistanceCompare dc = null;
		public utility.security.Point p1, p2;
		public dcCell(){}
		public dcCell(utility.security.Point p1, utility.security.Point p2){
			//dc = new DistanceCompare(p1, p2);
			this.p1 = p1;
			this.p2 = p2;
		}
		public void generateVeryfyPart(Point q){
			if(dc == null){
				dc = new DistanceCompare(p1, p2);
			}
			dc.GenerateVeryfyPart(new utility.security.Point((long)q.getCoord(0), (long)q.getCoord(1), 0));
		}
		public boolean verify(Point q){
			return dc.ClientVerify(new utility.security.Point((long)q.getCoord(0), (long)q.getCoord(1), 0));
		}
		
		public long getVOSize(){
			long ans = 0;
			ans += dc.getVOSize();
			return ans;
		}
		
		public byte[] writeToBytes(){
			return dc.writeToBytes();
		}
	}
	
	public class gfCell implements cell{
		public Gfunction gf = null, gf2 = null;
		public String[] ServerReturned, ServerReturned2;
		public boolean isL, isL2;
		public gfCell(){}
		public gfCell(Gfunction _gf, Gfunction _gf2, boolean _isL, boolean _isL2){
			gf = _gf;
			gf2 = _gf2;
			isL = _isL;
			isL2 = _isL2;
		}
		
		public long getVOSize(){
			long ans = 0;
			if(ServerReturned != null){
				ans += ServerReturned.length;
			}
			if(ServerReturned2 != null){
				ans += ServerReturned2.length;
			}
			return ans;
		}
		
		public void setGf(Gfunction _gf, boolean _isL){
			gf = _gf;
			isL = _isL;
		}
		
		public void setGf2(Gfunction _gf, boolean _isL){
			gf2 = _gf;
			isL2 = _isL;
		}
		
		public void generateVeryfyPart(Point p){
			if(gf != null)ServerReturned = gf.GenerateVeryfyPart((long)p.getCoord(0), isL);
			if(gf2 != null)ServerReturned2 = gf2.GenerateVeryfyPart((long)p.getCoord(1), isL2);
		}
		
		public byte[] writeToBytes(){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(baos);
			if(gf != null)
				try {
					gf.writeToFile(dos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(gf2 != null)
				try {
					gf2.writeToFile(dos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return baos.toByteArray();
		}
		
		public boolean verify(Point p){
			if(!isL){
				try {
					if(gf != null) if(gf.ClientComputed(ServerReturned, gf.U - ((long)p.getCoord(0))).equals(gf.getDigest()) == false)return false;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					if(gf != null)if(gf.ClientComputed(ServerReturned, ((long)p.getCoord(0)) - gf.L).equals(gf.getDigest()) == false)return false;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!isL2){
				try {
					if(gf2 != null) if(gf2.ClientComputed(ServerReturned2, gf2.U - ((long)p.getCoord(1))).equals(gf2.getDigest()) == false)return false;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					if(gf2 != null)if(gf2.ClientComputed(ServerReturned2, ((long)p.getCoord(1)) - gf2.L).equals(gf2.getDigest()) == false)return false;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		}
	}
	
	interface cell{
		public boolean verify(Point query);
		public void generateVeryfyPart(Point query);
	}
}
