package utility.Compare;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Scanner;


import mesh.Delaunay;

import utility.security.Point;
import utility.security.RSA;
import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
/*
 * 
 * @author qchen
 * */
public class buildBtreeOfPoints {
	public static boolean DEBUG = true;
	public static boolean ISCAR = false;
	public static long SCALE = (5000);
//	public static String filename = "database/PointsData.NE";
	public RecordManager recmanOfPoint = null;
	public PrimaryTreeMap<Long, byte[]> btOfPoint = null;
	public int ThreadNum = 16;
	public ArrayList<float[]> points = null;
	public ArrayList<Integer> idmap = null;
	public Delaunay delaunay = null;
	public boolean[] threadStatus = new boolean[ThreadNum];
	public static RSA rsa = new RSA();
	
	public void loadData(String sourceFileName) throws IOException{
		//PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(filename))));
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
					if(ISCAR){
						point[0] = (int)(point[0] * SCALE);
						point[1] = (int)(point[1] * SCALE);
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
		final long start = System.currentTimeMillis();
		delaunay = new Delaunay(points.toArray(new float[0][0]));		
		final int[] lock = new int[1];
		lock[0] = 0;
		final int totalNum = points.size();
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
						int curId;
						synchronized (lock) {
							curId = lock[0];
							lock[0] ++;
						}
						if(curId >= totalNum)break;
						System.out.println("Thread:\t" + threadId + "\tid:\t" + curId);
						int[] pid = delaunay.getLinked(curId + 1);
						ArrayList<Long> tmp = new ArrayList<Long>();
						Point pPoint = new Point((int)points.get(curId)[0], (int)points.get(curId)[1], (int)points.get(curId)[2]);
						for(int j = 0; j < pid.length && pid[j] > 0; j++){
							tmp.add((long)idmap.get(pid[j] - 1));
						}
						pPoint.buildByPaillier();
						DataOfPoint data = new DataOfPoint(curId, pPoint, tmp.toArray(new Long[0]));
						data.signWithRSA(rsa);
						try {
							btOfPoint.put((long)idmap.get(curId), data.writeToBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(curId % 500 == 0){
							System.out.println("Commit Thread:\t" + threadId + "\tid:\t" + curId);
							long end = System.currentTimeMillis();
							long expectedTime = (end - start) / (curId + 1) * (totalNum - curId) / 1000;
							System.out.println("Expected time remaining: " + expectedTime + " s = " + (expectedTime / 60) + " m = " + (expectedTime / 3600) + " h");
							try {
								recmanOfPoint.commit();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					threadStatus[threadId] = true;
				}
			}).start();
		}
		//pw.flush();
		//pw.close();
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
		recmanOfPoint.commit();
	}
	
	public buildBtreeOfPoints(boolean needLoad, String sourceFileName, String destFileName) throws IOException{
		recmanOfPoint = RecordManagerFactory.createRecordManager(destFileName);
		btOfPoint = recmanOfPoint.treeMap("treemap");
		if(needLoad){
			loadData(sourceFileName);
		}
	}
	
	public static void main(String[] args) throws IOException{
		long start = System.currentTimeMillis();
		buildBtreeOfPoints bTree = null;
		Scanner in = new Scanner(System.in);
		System.out.println("load or not (y/n)?");
		String line = in.nextLine();
		if(line.equals("y")){
			System.out.println("Input name of source file:");
			String sourcefile = in.nextLine();
			System.out.println("Input name of dest file:");
			String destfile = in.nextLine();
			bTree = new buildBtreeOfPoints(true, sourcefile + ".dat", destfile + ".PointsData"); 
		}else{
			System.out.println("Input name of source file:");
			String sourcefile = in.nextLine();
			System.out.println("Input name of dest file:");
			String destfile = in.nextLine();
			bTree = new buildBtreeOfPoints(false, sourcefile + ".dat", destfile + ".PointsData"); 
		}
		System.out.println("Time consume: " + (System.currentTimeMillis() - start));
		while(true){
			System.out.println("input id below:");
			long id = in.nextLong();
			if(bTree.btOfPoint.containsKey(id) == false){
				System.err.println("No found id:\t" + id);
				continue;
			}
			byte[] str = bTree.btOfPoint.find(id);
			DataOfPoint data = new DataOfPoint();
			data.loadFromBytes(str);
			System.out.println(id + " : " + data.p.x + " " + data.p.y + " dels : ");
			for(long x : data.delaunayIds){
				System.out.print(x + " ");
			}
			System.out.println("");
		}
	}
}


