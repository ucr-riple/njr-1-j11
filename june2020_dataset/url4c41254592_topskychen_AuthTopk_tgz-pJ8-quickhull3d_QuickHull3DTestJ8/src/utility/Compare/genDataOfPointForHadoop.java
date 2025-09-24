package utility.Compare;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


import mesh.Delaunay;

import utility.security.Point;
import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
/*
 * 
 * @author qchen
 * */
public class genDataOfPointForHadoop {
	public static boolean DEBUG = true;
	public static boolean ISCAR = false;
	public static long SCALE = 100000;
	public static String filename = "database/PointsData.tmp.dat";
	
	public static void main(String[] args) throws IOException{
		ArrayList<float[]> points = new ArrayList<float[]>();	
		ArrayList<Integer> idmap = new ArrayList<Integer>();
		PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(filename))));
		try {
			String line = null;
			LineNumberReader lr = new LineNumberReader(new FileReader("source/NE.dat"));
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
		Delaunay delaunay = new Delaunay(points.toArray(new float[0][0]));
		for(int i = 0; i < points.size(); i++){
			int[] pid = delaunay.getLinked(i + 1);
			ArrayList<Long> tmp = new ArrayList<Long>();
			Point pPoint = new Point((int)points.get(i)[0], (int)points.get(i)[1], 0);
			pw.print((long)idmap.get(i) + " " + pPoint.x + " " + pPoint.y);
			for(int j = 0; j < pid.length && pid[j] > 0; j++){
				tmp.add((long)idmap.get(pid[j] - 1));
				pw.print(" " + (long)idmap.get(pid[j] - 1));
			}
			pw.println();
		}
		pw.flush();
		pw.close();
	}
}


