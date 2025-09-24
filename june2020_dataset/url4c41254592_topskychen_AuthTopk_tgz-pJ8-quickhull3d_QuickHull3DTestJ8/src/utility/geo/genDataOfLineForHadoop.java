/**
 * 
 */
package utility.geo;

/**
 * @author qchen
 *
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import utility.Compare.DataOfPoint;
import utility.Compare.DataOfPointParser;
import utility.Compare.buildBtreeOfPoints;

/**
 * remember first build buildBtreeOfPoints to get delaynay information
 * @author qchen
 * */
public class genDataOfLineForHadoop {
	public static boolean DEBUG = true;
	public static String filename = "database/LinesData.NE.5HOP.tmp";
	
	public void loadData(int k) throws IOException{
		RecordManager recmanOfPoints = RecordManagerFactory.createRecordManager(DataOfPointParser.filename);
		PrimaryTreeMap<Long, byte[]> btOfPoints = recmanOfPoints.treeMap("treemap");
		PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(filename))));
		Set<Long> keys = btOfPoints.keySet();
		Queue<Long> qLongs = new LinkedList<Long>();
		int timeToCommit = 0, constk = k;
		for(Long key : keys){
		//for(Long key = (long) 4000; key <= 5000; key ++){
			pw.print(key);
			k = constk;
			DataOfPoint dataOfPoint = new DataOfPoint();
			byte[] byteData = btOfPoints.find(key);
			dataOfPoint.loadFromBytes(byteData);
			HashSet<Long> cnt = new HashSet<Long>();
			cnt.add(key);
			for(Long id : dataOfPoint.delaunayIds){
				qLongs.offer(id);
				cnt.add(id);
				k ++;
			}
			while(!qLongs.isEmpty() && k > 0 && constk >= 0){
				Long id = qLongs.poll();
				byteData = btOfPoints.find(id);
				DataOfPoint tmpDataOfPoint = new DataOfPoint();
				tmpDataOfPoint.loadFromBytes(byteData);
				//ids.add(id);
				//lines.add(new Line(dataOfPoint.p.doublePoint(), tmpDataOfPoint.p.doublePoint()));
				pw.print(" " + id + " " + dataOfPoint.p.doublePoint().x + " " + dataOfPoint.p.doublePoint().y + " " 
						+ tmpDataOfPoint.p.doublePoint().x + " " + tmpDataOfPoint.p.doublePoint().y);
				for(Long tmpLong : tmpDataOfPoint.delaunayIds){
					if(cnt.contains(tmpLong) || qLongs.size() >= k)continue;
					qLongs.offer(tmpLong);
					cnt.add(tmpLong);
				}
				k --;
			}
			pw.println();
			//byte[] testdata = newDataOfLine.writeToBytes();
			timeToCommit ++;
			qLongs.clear();
		}
		recmanOfPoints.close();
		pw.close();
	}
	
	public void loadDataByhop(int k) throws IOException{
		RecordManager recmanOfPoints = RecordManagerFactory.createRecordManager(DataOfPointParser.filename);
		PrimaryTreeMap<Long, byte[]> btOfPoints = recmanOfPoints.treeMap("treemap");
		PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(filename))));
		Set<Long> keys = btOfPoints.keySet();
		Queue<Long> qLongs = new LinkedList<Long>();
		int timeToCommit = 0, constk = k;
		for(Long key : keys){
		//for(Long key = (long) 4000; key <= 5000; key ++){
			pw.print(key);
			k = constk;
			DataOfPoint dataOfPoint = new DataOfPoint();
			byte[] byteData = btOfPoints.find(key);
			dataOfPoint.loadFromBytes(byteData);
			HashMap<Long, Integer> cnt = new HashMap<Long, Integer>();
			cnt.put(key, 0);
			for(Long id : dataOfPoint.delaunayIds){
				qLongs.offer(id);
				cnt.put(id, 1);
			}
			while(!qLongs.isEmpty()){
				Long id = qLongs.poll();
				int lev = cnt.get(id);
				if(lev > k)break;
				byteData = btOfPoints.find(id);
				DataOfPoint tmpDataOfPoint = new DataOfPoint();
				tmpDataOfPoint.loadFromBytes(byteData);
				//ids.add(id);
				//lines.add(new Line(dataOfPoint.p.doublePoint(), tmpDataOfPoint.p.doublePoint()));
				pw.print(" " + id + " " + dataOfPoint.p.doublePoint().x + " " + dataOfPoint.p.doublePoint().y + " " 
						+ tmpDataOfPoint.p.doublePoint().x + " " + tmpDataOfPoint.p.doublePoint().y);
				for(Long tmpLong : tmpDataOfPoint.delaunayIds){
					if(cnt.containsKey(tmpLong))continue;
					qLongs.offer(tmpLong);
					cnt.put(tmpLong, lev + 1);
				}
			}
			pw.println();
			//byte[] testdata = newDataOfLine.writeToBytes();
			timeToCommit ++;
			qLongs.clear();
		}
		recmanOfPoints.close();
		pw.close();
	}
	
	public genDataOfLineForHadoop(boolean is_Khop) throws IOException{
		//check();
		if(!is_Khop){
			System.out.println("Input number of k : ");
			Scanner in = new Scanner(System.in);
			int k = in.nextInt();
			loadData(k);
		}else{
			System.out.println("Input number of k : ");
			Scanner in = new Scanner(System.in);
			int k = in.nextInt();
			loadDataByhop(k);
		}
	}

	public static void main(String[] args) throws IOException{
		long start = System.currentTimeMillis();
		genDataOfLineForHadoop bTree = new genDataOfLineForHadoop(true); 
		System.out.println("Time consume: " + (System.currentTimeMillis() - start));
	}
}
