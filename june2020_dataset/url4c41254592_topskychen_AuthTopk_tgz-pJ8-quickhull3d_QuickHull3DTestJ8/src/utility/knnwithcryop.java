package utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import spatialindex.core.IData;
import spatialindex.core.INode;
import spatialindex.core.IShape;
import spatialindex.core.IVisitor;
import spatialindex.core.Point;
import spatialindex.core.Region;
import spatialindex.io.DiskStorageManager;
import spatialindex.io.IBuffer;
import spatialindex.io.IStorageManager;
import spatialindex.io.RandomEvictionsBuffer;
import spatialindex.rtree.MyRtree;
import spatialindex.rtree.RTree;
import spatialindex.rtree.SecurityTree;
import spatialindex.rtree.SecurityVisitor;
import spatialindex.rtree.VO;
import spatialindex.setting.PropertySet;
import utility.Compare.buildBtreeOfPoints;
import utility.geo.VoronoiNeighbors;
import utility.geo.buildBtreeOfLines;


public class knnwithcryop {
	
	public static RTree rtree, rtree_kd;
	public static SecurityTree srtree, srtree_kd;
	public static MyRtree myrtree, myrtree_kd;
	public static HashMap<Integer, int[]> nbHashMap = new HashMap<Integer, int[]>();
//	public static String FileInPath = "input/data.in";
	public static boolean IS_BATCH_QUERY;
	public static MemStat memStat = new MemStat();
	
	
	/**
	 * MyRtree is with/without embedded kd-tree.
	 * Srtree is for MRTree.
	 * 
	 * 
	 * */
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
	
	public static void main(String args[]) throws IndexOutOfBoundsException, Exception{
		new Thread(memStat).start();
		
		Scanner in = new Scanner(System.in);
		
		//System.out.println("input \"build\" means build a new tree, \"load\" means load from file");
		//String line = in.nextLine();
		while(true){
			String optionLine = "(a) genrate data\n(b) build a tree\n(c) load a tree\n(d) knn query\n" +
					"(e) batch query\n(f) build indexes of btree\n(g) build voronoi neighbors\n(h) show Mem\n(i) exit.";
			System.out.println(optionLine);
			String line = in.nextLine();
			if(line.equalsIgnoreCase("a")){
				System.out.println("input number range, quantity of number, filepath respectively:");
				String[] val = in.nextLine().split(" ");
				generator.GeneratePointsofInteger(Integer.parseInt(val[0]), Integer.parseInt(val[1]), val[2]);
				System.out.println("Success!");
			}else if(line.equalsIgnoreCase("b")){
				while(true){
					/**
					 * a,b,c is for normal ones;
					 * d,e,f is for embedded ones.
					 * 
					 * */
					System.out.println("(a) NE\n(b) CA\n(c) GO\n(d) NE\n(e) CA\n(f) GO\n(g) exit");
					line = in.nextLine();
					String sourceString = null, indexString = null;
					if(line.equalsIgnoreCase("a")){
						sourceString = "./input/NE.pd.dat";
						indexString = "./database/NE";
					}else if(line.equalsIgnoreCase("b")){
						sourceString = "./source/CA.dat";
						indexString = "./database/CA";
					}else if(line.equalsIgnoreCase("c")){
						System.out.println("Input the rate:");
						String rate = in.nextLine();
						sourceString = "./input/GO" + rate + ".pd.dat";
						indexString = "./database/GO" + rate;
					}else if(line.equalsIgnoreCase("d")){
						sourceString = "./input/NE.pd.dat";
						indexString = "./database/NE";
					}else if(line.equalsIgnoreCase("e")){
						sourceString = "./source/CA.dat";
						indexString = "./database/CA";
					}else if(line.equalsIgnoreCase("f")){
						System.out.println("Input the rate:");
						String rate = in.nextLine();
						sourceString = "./input/GO" + rate + ".pd.dat";
						indexString = "./database/GO" + rate;
					}else{
						break;
					}
					System.out.println("building rtree of " + sourceString + " ...");
					System.out.println("Input filename of index:");
					String destFileName = in.nextLine();
					
					if(line.equalsIgnoreCase("a") || line.equalsIgnoreCase("b") || line.equalsIgnoreCase("c")){
						rtree = RTree.createRTree(new String[] {sourceString, indexString, "100", "10nn", destFileName + ".dp", destFileName + ".dl"});
						System.out.println("fin building rtree.");
						System.out.println("building srtree...");
						MyRtree[] myrtrees = new MyRtree[1];
						SecurityTree[] srtrees = new SecurityTree[1];
						LoadMyRTree(indexString, false, myrtrees, srtrees, false);
						myrtree = myrtrees[0];
						srtree = srtrees[0];
						System.out.println("fin building srtree.");						
					}else{
						MyRtree[] myrtrees_kd = new MyRtree[1];
						SecurityTree[] srtrees_kd = new SecurityTree[1];
						System.out.println("building embeded kd rtree of " + sourceString + " ...");
						rtree_kd = RTree.createRTree(new String[] {sourceString, indexString + "_kd", "100", "10nn", destFileName + ".dp", destFileName + ".dl"});
						System.out.println("fin building embeded kd rtree.");
						LoadMyRTree(indexString + "_kd", false, myrtrees_kd, srtrees_kd, true);
						myrtree_kd = myrtrees_kd[0];
						srtree_kd = srtrees_kd[0];
					}
					break;
				}
			}else if(line.equalsIgnoreCase("c")){
				while(true){
					System.out.println("(a) load NE\n(b) load CA\n(c) load GO\n(d) exit");
					String filename = null, nbFileName = null;
					line = in.nextLine();
					if(line.equalsIgnoreCase("a")){
						filename = "./database/NE";
						nbFileName = "./input/NE.pd.dat";
					}else if(line.equalsIgnoreCase("b")){
						filename = "./database/CA";
					}else if(line.equalsIgnoreCase("c")){
						System.out.println("Input the rate:");
						String rate = in.nextLine();
						filename = "./database/GO" + rate;
						nbFileName = "./input/GO" + rate + ".pd.dat";
					}else{
						break;
					}
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
					VoronoiNeighbors vn = new VoronoiNeighbors();
					vn.loadVoronoiNeighbors(nbFileName, nbHashMap);
					break;
				}
			}else if(line.equalsIgnoreCase("d")){
				IS_BATCH_QUERY = false;
				System.out.println("===========start query===========");
				while(true){
					System.out.println("(a) new query\n(b) exit");
					line = in.nextLine();
					if(line.equalsIgnoreCase("a")){
						System.out.println("Please input the k of knn:");
						int kNum = Integer.parseInt(in.nextLine());
						System.out.println("Please input the coordinate of point:");
						String[] coords = in.nextLine().split(" ");
						System.out.println("Please input the limit:");
						int limit = Integer.parseInt(in.nextLine());
						System.out.println("Please input whether to use parallel (y/n)");
						boolean isParallel = in.nextLine().equalsIgnoreCase("y");
						Point pt = new Point(new double[]{Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), 0});
						query(kNum, pt, new PrintWriter(System.out), null, -1, limit, isParallel);				
					}else{
						System.out.println("===========end query===========");
						break;
					}
				}
			}else if(line.equalsIgnoreCase("e")){
				IS_BATCH_QUERY = true;
				System.out.println("Please input query's filename");
				String filename = in.nextLine();
				System.out.println("Please input answer of query's file name");
				String ans_file_name = in.nextLine();
				System.out.println("Please input the limit:");
				int limit = Integer.parseInt(in.nextLine());
				System.out.println("Please input whether to use parallel (y/n)");
				boolean isParallel = in.nextLine().equalsIgnoreCase("y");
				StatisticForAuth[] stat = new StatisticForAuth[6];
				//int[] k_of_knn = {1, 2, 4, 8, 16, 32, 64, 128};
//				int[] k_of_knn = {128, 1, 64, 32, 16, 8, 4, 2};
				int[] k_of_knn = {128, 128, 1, 1, 64, 64, 16, 8, 4, 2};
				for(int i = 0; i < 8; i ++){
					System.out.println("k = " + k_of_knn[i]);
					final PrintWriter datapw = new PrintWriter(new FileOutputStream(new File(ans_file_name + "_" + new Integer(k_of_knn[i]).toString() + ".data")));
					int lines = 0;
					Scanner in1 = new Scanner(new FileInputStream(new File(filename)));
					for(int j = 0; j < 6; j++){
						stat[j] = new StatisticForAuth();
					}
					while(in1.hasNext()){
						lines ++;
						if(lines % 20 == 0)
							System.out.println();
						else System.out.print(".");
						String[] tokens = in1.nextLine().split(" ");
						Point pt = new Point(new double[]{Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]), 0});
						for(int j = 0; j < 6; j++){
							query(k_of_knn[i], pt, datapw, stat, j, limit, isParallel);
						}
						if(lines > 50)break;
					}
					PrintWriter pw = new PrintWriter(new FileOutputStream(new File(ans_file_name + "_" + new Integer(k_of_knn[i]).toString())));
					for(int j = 0; j < 6; j ++){
						stat[j].getAveage(lines);
						stat[j].print();
						stat[j].printtoffile(pw);
					}
					in1.close();
					pw.flush();
					pw.close();
					datapw.flush();
					datapw.close();
				}
			}else if(line.equalsIgnoreCase("f")){
				while(true){
					System.out.println("(a) build index of points\n(b) build index of lines\n(c) return\n");
					line = in.nextLine();
					if(line.equalsIgnoreCase("a")){
						System.out.println("Input name of source file:");
						String sourcefile = in.nextLine();
						System.out.println("Input name of source file:");
						String destfile = in.nextLine();
						new buildBtreeOfPoints(true, sourcefile + ".dat", destfile + ".PointsData");
					}else if(line.equalsIgnoreCase("b")){
						System.out.println("Input name of source file:");
						String sourcefile = in.nextLine();
						System.out.println("Input name of source file:");
						String destfile = in.nextLine();
						new buildBtreeOfLines(true, sourcefile + ".dat", destfile + ".LinesData.1HOP"); 
					}else{
						break;
					}
				}
			}else if(line.equalsIgnoreCase("g")){
				while(true){
					System.out.println("(a) build Voronoi Neighbors\n(b) return" );
					line = in.nextLine();
					VoronoiNeighbors vn = new VoronoiNeighbors();
					if(line.equalsIgnoreCase("a")){
						System.out.println("Input name of source file:");
						String sourceFile = in.nextLine();
						System.out.println("Input name of destination file:");
						String destFileName = in.nextLine();
						vn.generateVoronoiNeighbors(sourceFile, destFileName);
					}else{
						break;
					}
				}
			}else if(line.equalsIgnoreCase("h")){
				memStat.printInfo();
				memStat.reSet();
//				System.out.println("Current Memory Usage:\t" + memStat.getCurrentMemSizeInMB());
			}else if(line.equalsIgnoreCase("i")){
				System.out.println("exit!");
				break;
			}
			else{
				System.out.println("No such option!");
			}
		}
	}
	
	public static void query(int kNum, Point pt, PrintWriter pw, StatisticForAuth[] stat, int type, int limit, boolean isParallel)throws IOException{
		
		SecurityVisitor svisitor = new SecurityVisitor();
		VO vo = null;
		if(type == -1 || type == 0){			
			myrtree.nearestNeighborQuery(kNum, pt, svisitor);
			vo = new VO(kNum, pt, true, myrtree, srtree, 0, svisitor.getDistance(), limit, isParallel);			
			if(vo.verify(pt)){
				if(type == -1)System.out.println("Pass!");
			}else{
				System.out.println("Fail!");
			}
			if(type == -1)vo.getStatistics().print();
			vo.getStatistics().printtoffile(pw);
			if(stat != null)stat[0].update(vo.getStatistics());
			if(type == -1)System.out.println("==============above rtree greedy===============");
		}
		
		if(limit > 0 && (type == -1 || type == 1)){	
			svisitor = new SecurityVisitor();
			myrtree.nearestNeighborQuery(kNum, pt, svisitor);
			vo = new VO(kNum, pt, false, myrtree, srtree, 0, svisitor.getDistance(), limit, isParallel);
			if(vo.verify(pt)){
				if(type == -1)System.out.println("Pass!");
			}else{
				System.out.println("Fail!");
			}
			if(type == -1)vo.getStatistics().print();
			vo.getStatistics().printtoffile(pw);
			if(stat != null)stat[1].update(vo.getStatistics());
			if(type == -1)System.out.println("==============above rtree===============");
		}
		
//		if(type == -1)return;
		if(type == -1 || type == 2){			
			//based on kd tree
			svisitor = new SecurityVisitor();
			myrtree_kd.nearestNeighborQuery(kNum, pt, svisitor);
			vo = new VO(kNum, pt, true, myrtree_kd, srtree_kd, 2, svisitor.getDistance(), limit, isParallel);
			if(vo.verify(pt)){
				if(type == -1)System.out.println("Pass!");
			}else{
				System.out.println("Fail!");
			}
			if(type == -1)vo.getStatistics().print();
			vo.getStatistics().printtoffile(pw);
			if(stat != null)stat[2].update(vo.getStatistics());
			if(type == -1)System.out.println("==============above embeded kdtree greedy===============");
		}
		
		if(limit > 0 && (type == -1 || type == 3)){
			svisitor = new SecurityVisitor();
			myrtree_kd.nearestNeighborQuery(kNum, pt, svisitor);
			vo = new VO(kNum, pt, false, myrtree_kd, srtree_kd, 2, svisitor.getDistance(), limit, isParallel);
			if(vo.verify(pt)){
				if(type == -1)System.out.println("Pass!");
			}else{
				System.out.println("Fail!");
			}
			if(type == -1)vo.getStatistics().print();
			vo.getStatistics().printtoffile(pw);
			if(stat != null)stat[3].update(vo.getStatistics());
			if(type == -1)System.out.println("=============above embeded kdtree================");
		}
		if(type == -1 || type == 4){
			svisitor = new SecurityVisitor();
			long s = myrtree.getStatistics().getReads(), e;
			myrtree.nearestNeighborQuery(kNum, pt, svisitor);
			e = myrtree.getStatistics().getReads();
//			System.out.println("io : " + (e - s));
			vo = new VO(kNum, pt, svisitor.dataState, true, myrtree, limit, isParallel);
			vo.getStatistics().num_dataio += e - s;
			if(vo.verify(pt)){
				if(type == -1)System.out.println("Pass!");
			}else{
				System.out.println("Fail!");
			}
			if(type == -1)vo.getStatistics().print();
			vo.getStatistics().printtoffile(pw);
			if(stat != null)stat[4].update(vo.getStatistics());
			if(type == -1)System.out.println("==============above voronoi diagram greedy===============");
		}
		if(limit > 0 && (type == -1 || type == 5)){
			svisitor = new SecurityVisitor();
			long s = myrtree.getStatistics().getReads(), e;
			myrtree.nearestNeighborQuery(kNum, pt, svisitor);
			e = myrtree.getStatistics().getReads();
			vo = new VO(kNum, pt, svisitor.dataState, false, myrtree, limit, isParallel);
			vo.getStatistics().num_dataio += e - s;
			if(vo.verify(pt)){
				if(type == -1)System.out.println("Pass!");
			}else{
				System.out.println("Fail!");
			}
			if(type == -1)vo.getStatistics().print();
			vo.getStatistics().printtoffile(pw);
			if(stat != null)stat[5].update(vo.getStatistics());
			if(type == -1)System.out.println("=============above voronoi diagram================");
		}
		
		
//		/**
//		 * Below is for rtree-based 
//		 * */
//		System.out.println("Starting Rtree-based Knn");
//		MyRtreeBasedVisitor rtreeBasedVisitor = (new knnwithcryop()).new MyRtreeBasedVisitor(kNum, pt);
//		myrtree.nearestNeighborQuery(kNum, pt, rtreeBasedVisitor);
//		rtreeBasedVisitor.printinfo();
//		VO2 vo = new VO2(rtreeBasedVisitor.ids.toArray(new Integer[0]), rtreeBasedVisitor.data.toArray(new IShape[0]), kNum, q_x, q_y, false);
//		if(vo.ClientVerify(q_x, q_y)){
//			//System.out.println("Pass verification");
//		}else{
//			System.err.println("Fail verification");
//		}
//		if(!IS_BATCH_QUERY)vo.statistics.printinfo();
//		else vo.statistics.printinfotofile(datapw);
//		stat1.Construction_time_for_server += vo.statistics.generate_time;
//		stat1.VO_size += vo.statistics.VOsize();
//		stat1.Verification_time_for_clients += vo.statistics.verify_time;
//		stat1.Pallier_points += vo.statistics.num_of_Pailliar;
//		stat1.Line_pairs_points += vo.statistics.num_of_Lines;
//		VO2 vo2 = new VO2(rtreeBasedVisitor.ids.toArray(new Integer[0]), rtreeBasedVisitor.data.toArray(new IShape[0]), kNum, q_x, q_y, true);
//		if(vo2.ClientVerify(q_x, q_y)){
//			//System.out.println("Pass verification");
//		}else{
//			System.err.println("Fail verification");
//		}
//		if(!IS_BATCH_QUERY)vo2.statistics.printinfo();
//		else vo2.statistics.printinfotofile(datapw);
//		stat2.Construction_time_for_server += vo2.statistics.generate_time;
//		stat2.VO_size += vo2.statistics.VOsize();
//		stat2.Verification_time_for_clients += vo2.statistics.verify_time;
//		stat2.Pallier_points += vo2.statistics.num_of_Pailliar;
//		stat2.Line_pairs_points += vo2.statistics.num_of_Lines;
//		/*
//		 * Below is knn with Geo function
//		 * */ 
//		System.out.println("Starting Voronoi-based Knn");
//		MyVisitor visitor = (new knnwithcryop()).new MyVisitor();
//		rtree.nearestNeighborQuery(kNum, pt, visitor);
//		utility.geo.VO voOfLine = new utility.geo.VO((Integer [])visitor.p_id.toArray(new Integer[0]), (Integer[])visitor.p_x.toArray(new Integer[0]), (Integer[])visitor.p_y.toArray(new Integer[0]), q_x, q_y, false);
//		if(voOfLine.ClientVerify(q_x, q_y)){
//			//System.out.println("Pass verification!");
//		}
//		else System.out.println("Fail verification!");
//		if(!IS_BATCH_QUERY)voOfLine.statistics.printinfo();
//		else voOfLine.statistics.printinfotofile(datapw);
//		stat3.Construction_time_for_server += voOfLine.statistics.generate_time;
//		stat3.VO_size += voOfLine.statistics.VOsize();
//		stat3.Verification_time_for_clients += voOfLine.statistics.verify_time;
//		stat3.Pallier_points += voOfLine.statistics.num_of_Pailliar;
//		stat3.Line_pairs_points += voOfLine.statistics.num_of_Lines;
//		
//		utility.geo.VO voOfLine2 = new utility.geo.VO((Integer [])visitor.p_id.toArray(new Integer[0]), (Integer[])visitor.p_x.toArray(new Integer[0]), (Integer[])visitor.p_y.toArray(new Integer[0]), q_x, q_y, true);	
//		if(voOfLine2.ClientVerify(q_x, q_y)){
//			//System.out.println("Pass verification!");
//		}
//		else System.out.println("Fail verification!");
//		if(!IS_BATCH_QUERY)voOfLine2.statistics.printinfo();
//		else voOfLine2.statistics.printinfotofile(datapw);
//		stat4.Construction_time_for_server += voOfLine2.statistics.generate_time;
//		stat4.VO_size += voOfLine2.statistics.VOsize();
//		stat4.Verification_time_for_clients += voOfLine2.statistics.verify_time;
//		stat4.Pallier_points += voOfLine2.statistics.num_of_Pailliar;
//		stat4.Line_pairs_points += voOfLine2.statistics.num_of_Lines;
	}
	
	class MyVisitor implements IVisitor{
		public ArrayList<Integer> p_id, p_x, p_y;
		
		public MyVisitor() {
			// TODO Auto-generated constructor stub
			p_x = new ArrayList<Integer>();
			p_y = new ArrayList<Integer>();
			p_id = new ArrayList<Integer>();
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
		public void setParentNodeInside(boolean arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitData(IData arg0) {
			// TODO Auto-generated method stub
			Region region = (Region)arg0.getShape();
			//System.out.println(arg0.getIdentifier() + " : " + (int)region.getLow(0) + " " + (int)region.getLow(1));
			p_id.add(arg0.getIdentifier());
			p_x.add((int)region.getLow(0));
			p_y.add((int)region.getLow(1));
		}

		@Override
		public void visitNode(INode arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitNode(INode arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitNode(INode arg0, int arg1, int[] arg2) {
			// TODO Auto-generated method stub
			
		}
		
		public void printinfo(){
			System.out.println("");
		}

		@Override
		public void visitData(IData d, int type) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class MyRtreeBasedVisitor implements IVisitor{

		public int kNum;
		public ArrayList<IShape> data;
		public ArrayList<Integer> ids;
		public Point pt;
		
		public MyRtreeBasedVisitor(int kNumm, Point pt) {
			// TODO Auto-generated constructor stub
			this.kNum = kNum;
			this.pt = pt;
			data = new ArrayList<IShape>();
			ids = new ArrayList<Integer>();
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
		public void setParentNodeInside(boolean arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitData(IData arg0) {
			// TODO Auto-generated method stub
			int loc = Collections.binarySearch(data, arg0.getShape(), new Comparator<IShape>() {

				@Override
				public int compare(IShape o1, IShape o2) {
					// TODO Auto-generated method stub
					double d1 = pt.getMinimumDistance(o1);
					double d2 = pt.getMinimumDistance(o2);
					if(d1 < d2)return -1;
					else if(d1 == d2)return 0;
					else return 1;
				}
			});
			if(loc >= 0){
				data.add(loc, arg0.getShape());
				ids.add(loc, arg0.getIdentifier());
			}else{
				data.add((-loc - 1), arg0.getShape());
				ids.add((-loc - 1), arg0.getIdentifier());
			}
		}

		@Override
		public void visitNode(INode arg0) {
			// TODO Auto-generated method stub
			int loc = Collections.binarySearch(data, arg0.getShape(), new Comparator<IShape>() {

				@Override
				public int compare(IShape o1, IShape o2) {
					// TODO Auto-generated method stub
					double d1 = pt.getMinimumDistance(o1);
					double d2 = pt.getMinimumDistance(o2);
					if(d1 < d2)return -1;
					else if(d1 == d2)return 0;
					else return 1;
				}
			});
			if(loc >= 0){
				data.add(loc, arg0.getShape());
				ids.add(loc, arg0.getIdentifier());
			}else{
				data.add((-loc - 1), arg0.getShape());
				ids.add((-loc - 1), arg0.getIdentifier());
			}
		}

		@Override
		public void visitNode(INode arg0, int arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void visitNode(INode arg0, int arg1, int[] arg2) {
			// TODO Auto-generated method stub
			
		}
		
		public void printinfo(){
			System.out.println("===========================");
			System.out.println("VO size : " + data.size());
			for(int i = 0; i < data.size(); i++){
				Region region = data.get(i).getMBR();
				System.out.println(region.toString());
			}
		}
		@Override
		public void visitData(IData d, int type) {
			// TODO Auto-generated method stub
			
		}
	}
}
