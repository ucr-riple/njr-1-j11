package utility.geo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import mesh.Delaunay;

import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import utility.Compare.DataOfPoint;
import utility.Compare.buildBtreeOfPoints;
import utility.security.RSA;

/*
 * remember first build buildBtreeOfPoints to get delaynay information
 * @author qchen
 * */
public class buildBtreeOfLines {
	public static boolean DEBUG = false;
//	public static String filename = "database/LinesData.test.1HOP";
//	public static String destFileName;
	public RecordManager recmanOfLine;
	public PrimaryTreeMap<Long, byte[]> btOfLine;
	public int ThreadNum = 1;
	public ArrayList<float[]> points = null;
	public ArrayList<Integer> idmap = null;
	public Delaunay delaunay = null;
	public boolean[] threadStatus = new boolean[ThreadNum];
	public static RSA rsa = new RSA();
	
	public void loadData(String sourceFileName) throws IOException{
		points = new ArrayList<float[]>();
		idmap = new ArrayList<Integer>();
		
		try {
			String line = null;
			LineNumberReader lr = new LineNumberReader(new FileReader(sourceFileName));
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		delaunay = new Delaunay(points.toArray(new float[0][0]));
		final long start = System.currentTimeMillis();
		final int[] lock = new int[1];
		lock[0] = 0;
		final int totalNum = points.size();
		for(int id = 0; id < ThreadNum; id ++){
			threadStatus[id] = false;
			final int tid  = id;
			new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					int curid, threadId = tid;
					while(true){
						synchronized (lock) {
							curid = lock[0];
							lock[0]++;	
						}
						if(curid >= totalNum)break;
						int[] pid = delaunay.getLinked(curid + 1);
						for(int id : pid){
							if(id == 0)continue;
							int id1 = idmap.get(curid), id2 = idmap.get(id - 1);
							if(id1 >= id2)continue;
							Long lineId = DataOfLine.calcLineId(id1, id2);
							Line nline = new Line(points.get(curid), points.get(id - 1));
							DataOfLine nDataOfLine = new DataOfLine(lineId, nline);
							nDataOfLine.signWithRSA(rsa);
							btOfLine.put(lineId, nDataOfLine.writeToBytes());
						}
						if(curid % 200 == 0){
							System.out.println("Commit Thread:\t" + threadId + "\tid:\t" + curid);
							long end = System.currentTimeMillis();
							long expectedTime = (end - start) / (curid + 1) * (totalNum - curid) / 1000;
							System.out.println("Expected time remaining: " + expectedTime + " s = " + (expectedTime / 60) + " m = " + (expectedTime / 3600) + " h");
							try {
								recmanOfLine.commit();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(DEBUG){
							//recmanOfLine.commit();
							System.out.println("Thread :\t" + threadId + " id :\t" + curid + " has finished");
						}
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
		recmanOfLine.commit();
	}
	
	public buildBtreeOfLines(boolean isLoad, String sourceFileName, String destFileName) throws IOException{
		recmanOfLine = RecordManagerFactory.createRecordManager(destFileName);
		btOfLine = recmanOfLine.treeMap("treemap");
		//check();
		if(isLoad){
			loadData(sourceFileName);
		}
	}
		
	public static void main(String[] args) throws IOException{
		long start = System.currentTimeMillis();
		Scanner in = new Scanner(System.in);
		System.out.println("load or not (y/n)?");
		String line = in.nextLine();
		buildBtreeOfLines bTree  = null;
		if(line.equals("y")){			
			System.out.println("Input name of source file:");
			String sourcefile = in.nextLine();
			System.out.println("Input name of source file:");
			String destfile = in.nextLine();
			bTree = new buildBtreeOfLines(true, sourcefile + ".dat", destfile + ".LinesData.1HOP"); 
		}else{
			System.out.println("Input name of source file:");
			String sourcefile = in.nextLine();
			System.out.println("Input name of source file:");
			String destfile = in.nextLine();
			bTree = new buildBtreeOfLines(false, sourcefile + ".dat", destfile + ".LinesData.1HOP");
		}
		System.out.println("Time consume: " + (System.currentTimeMillis() - start));
		while(true){
			System.out.println("input id1 and id2 below:");
			int id1 = in.nextInt();
			int id2 = in.nextInt();
			long lineid = DataOfLine.calcLineId(id1, id2);
			if(bTree.btOfLine.containsKey(lineid) == false){
				System.err.println("No! not found id:\n" + lineid);
				continue;
			}else{
				System.out.println("yes!");
			}
			byte[] str = bTree.btOfLine.find(lineid);
			DataOfLine dataOfLine = new DataOfLine(str);
		}
	}
}
