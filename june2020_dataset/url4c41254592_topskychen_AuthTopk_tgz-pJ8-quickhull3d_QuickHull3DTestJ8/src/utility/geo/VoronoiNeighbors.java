/**
 * 
 */
package utility.geo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


import mesh.Delaunay;

import utility.Compare.buildBtreeOfPoints;
import utility.security.DataIO;

/**
 * @author chenqian
 *
 */
public class VoronoiNeighbors {

	private ArrayList<float[]> points = null;
	private ArrayList<Integer> idmap = null;
	private Delaunay delaunay = null;
	
	public void generateVoronoiNeighbors(String sourceFileName, String destFileName){
		String line = null;
		LineNumberReader lr = null;
		try {
			lr = new LineNumberReader(new FileReader(sourceFileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		points = new ArrayList<float[]>();
		idmap = new ArrayList<Integer>();
		try {
			while((line = lr.readLine()) != null){
				String[] num = line.split(" ");
				idmap.add(Integer.parseInt(num[1]));
				float[] point = new float[2];
				point[0] = Float.parseFloat(num[2]);
				point[1] = Float.parseFloat(num[3]);
				if(buildBtreeOfPoints.ISCAR){
					point[0] = (int)(point[0] * buildBtreeOfPoints.SCALE);
					point[1] = (int)(point[1] * buildBtreeOfPoints.SCALE);
				}
				points.add(point);
				//if(DEBUG) System.err.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delaunay = new Delaunay(points.toArray(new float[0][0]));
		DataOutputStream dos = null;
		try {
			if(destFileName.endsWith(".nb") == false)destFileName += ".nb";
			dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(destFileName))));
			dos.writeInt(points.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int id = 0; id < points.size(); id++){
			int[] pid = delaunay.getLinked(id + 1);
			if(id % 1000 == 0){
				System.out.println( " " + (id / 1000) + "k");
			}else{
				if(id % 50 == 0)System.out.print(".");
			}
			try {
				dos.writeInt(idmap.get(id));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			for(int v : pid){
				if(v == 0)break;
				tmp.add(v);
			}
			DataIO.writeIntArrays(dos, tmp.toArray(new Integer[0]));
		}
		try {
			dos.flush();
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("fin!");
	}
	
	public void loadVoronoiNeighbors(String destFileName, HashMap<Integer, int[]> nbHashMap){
		Scanner in;
		System.out.println("begin loading voronoi neighbors");
		try {
			in = new Scanner(new File(destFileName));
			while(in.hasNext()){
				String line = in.nextLine();
				String[] tks = line.split("\t");
				int key = Integer.parseInt(tks[0]);
				int[] pids = new int[tks.length - 3];
				for(int i = 4; i < tks.length; i++){
					pids[i - 4] = Integer.parseInt(tks[i]);
				}
				nbHashMap.put(key, pids);
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\nload fin!");
	}
	
	public VoronoiNeighbors(){
		
	}
	
	public long getNumberOfVoronoiNeighbor(int limit, HashMap<Integer, int[]> nbHashMap){
		Iterator<Integer> iter = nbHashMap.keySet().iterator();
		HashSet<Long> idHashSet = new HashSet<Long>();
		while(iter.hasNext()){
			int key = iter.next();
			Queue<Integer> q = new LinkedList<Integer>();
			HashMap<Integer, Integer> lev = new HashMap<Integer, Integer>();
			q.add(key);
			lev.put(key, 0);
			while(!q.isEmpty()){
				int top = q.poll();
				int level = lev.get(top);
				int[] pid = nbHashMap.get(top);
				for(int i = 0; i < pid.length; i++){
					if(lev.containsKey(pid[i]) == false && level + 1 <= limit){
						long id = DataOfLine.calcLineId(key, pid[i]);
						lev.put(pid[i], level + 1);
						if(idHashSet.contains(id) == false)idHashSet.add(id);
						if(level + 1 < limit)q.add(pid[i]);
					}
				}
			}
//			System.out.println(idHashSet.size());break;
		}
		return idHashSet.size();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		VoronoiNeighbors vn = new VoronoiNeighbors();
		if(new File("database/NE.nb").exists() == false){
			vn.generateVoronoiNeighbors("source/NE.dat", "database/NE");
		}
		HashMap<Integer, int[]> nbHashMap = new HashMap<Integer, int[]>();
		vn.loadVoronoiNeighbors("database/NE", nbHashMap);
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.println("please input the id of query(-1 means stops) : ");
			String line = in.nextLine();
			int id = Integer.parseInt(line);
			if(id == -1)break;
			if(nbHashMap.containsKey(id) == false){
				System.out.println("err!: no such id!");
				continue;
			}
			int[] pid = nbHashMap.get(id);
			System.out.println("neighbors:");
			for(int v : pid){
				System.out.print(v + " ");
			}
			System.out.println();
		}
	}

}
