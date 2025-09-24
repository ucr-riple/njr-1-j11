/**
 * 
 */
package utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import utility.geo.DataOfLine;

import mesh.PDelaunay;

/**
 * @author chenqian
 * This is to generate PDelaunay, and store it to file.
 */
public class delaynayGenerator {

	public static ArrayList<int[]> points = null;
	public static HashSet<Long> cnt = new HashSet<Long>();
	public static PDelaunay pd = null;
	
	public static long getId(int id1, int id2){
		return id1 * DataOfLine.M + id2;
	}
	
	public static void loadData(String sourceFileName){
		points = new ArrayList<int[]>();
		
		try {
			String line = null;
			LineNumberReader lr = new LineNumberReader(new FileReader(sourceFileName));
			try {
				while((line = lr.readLine()) != null){
					String[] tks = line.split("\t");
					int[] point = new int[3];
					point[0] = Integer.parseInt(tks[0]);
					point[1] = Integer.parseInt(tks[1]);
					point[2] = Integer.parseInt(tks[2]);
					long id = getId(point[0], point[1]);
					if(cnt.contains(id)){
						continue;
					}else{
						cnt.add(id);
					}
					points.add(point);
					//if(DEBUG) System.err.println();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Load finished!");
		
	}
	
	public static void storeToFile(String destFileName) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(destFileName))));
		ArrayList<float[]> tmpPoints = new ArrayList<float[]>();
		for(int i = 0; i < points.size(); i++){
			int[] point = points.get(i);
			tmpPoints.add(new float[]{(float)point[0], (float)point[1], (float)point[2]});
		}
		pd = new PDelaunay(tmpPoints.toArray(new float[0][0]));
		for(int i = 0; i < points.size(); i++){
			int curId = i;
			pw.print(curId);
			int[] point = points.get(i);
			int[] pid = pd.getLinked(curId + 1);
			pw.print("\t" + point[0] + "\t" + point[1] + "\t" + point[2]);
			for(int j = 0; j < pid.length && pid[j] > 0; j++){
				pw.print("\t" + (pid[j] - 1));
			}
			pw.println("");
		}
		pw.flush();
		pw.close();
	}
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		if(args.length > 0){
			loadData(args[0]);
			storeToFile(args[1]);
		}else{
			Scanner in = new Scanner(System.in);
			System.out.println("Please input your source file name:");
			String sourceFileName = in.nextLine();
			System.out.println("Please input your destination file name:");
			String destFileName = in.nextLine();
			loadData(sourceFileName);
			storeToFile(destFileName);
		}
	}

}
