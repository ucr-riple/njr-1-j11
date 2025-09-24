/**
 * 
 */
package utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author chenqian
 * I depress the quickhull3d, which is too slowly.
 * I first generate with qhull first.
 */
public class newDelaunayParser {
	
	public static HashMap<Integer, HashSet<Integer>> pointsList = new HashMap<Integer, HashSet<Integer>>();
	public static ArrayList<long[]> points = new ArrayList<long[]>();
	
	public static void loadData(String fileInputPath) throws FileNotFoundException{
		Scanner in = new Scanner(new BufferedInputStream(new FileInputStream(new File(fileInputPath + ".tmp"))));
		int len = Integer.parseInt(in.nextLine());
		for(int i = 0; i < len; i++){
			String line = in.nextLine();
			String[] tks = line.split(" ");
			for(int j = 0; j < tks.length; j++){
				int p = Integer.parseInt(tks[j]);
				int q = Integer.parseInt(tks[(j + 1) % tks.length]);
				HashSet<Integer> pHS = new HashSet<Integer>();
				HashSet<Integer> qHS = new HashSet<Integer>();
				if(pointsList.containsKey(p)){					
					pHS = pointsList.get(p);
				}
				if(pointsList.containsKey(q)){					
					qHS = pointsList.get(q);
				}
				if(pHS.contains(q) == false)pHS.add(q);
				if(qHS.contains(p) == false)qHS.add(p);
				pointsList.put(p, pHS);
				pointsList.put(q, qHS);
			}
		}
		in.close();
		in = new Scanner(new BufferedInputStream(new FileInputStream(new File(fileInputPath + ".nm"))));
		in.nextLine(); // read 3
		len = Integer.parseInt(in.nextLine());
		for(int i = 0; i < len; i++){
			String[] tks = in.nextLine().split("\t");
			long x = Long.parseLong(tks[0]);
			long y = Long.parseLong(tks[1]);
			long z = Long.parseLong(tks[2]) + (x * x + y * y);
			points.add(new long[]{x, y, z});
		}
		in.close();
	}
	
	public static void writeToFile(String fileOutPath) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(new File(fileOutPath))));
		for(int i = 0; i < points.size(); i++){
			int curId = i;
			pw.print(curId);
			long[] point = points.get(i);
			pw.print("\t" + point[0] + "\t" + point[1] + "\t" + point[2]);
			if(pointsList.containsKey(curId) == false){
				pw.println("");
				continue;
			}
			HashSet<Integer> ids = pointsList.get(curId);
			Iterator<Integer> iter = ids.iterator();
			while(iter.hasNext()){
				int id = iter.next();
				pw.print("\t" + id);
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
		String fileIn = null, fileOut = null;
		if(args.length > 0){
			fileIn = args[0];
			fileOut = args[1];
		}else{
			Scanner in = new Scanner(System.in);
			System.out.println("Please input source file name:");
			fileIn = in.nextLine();
			System.out.println("Please input destination file name:");
			fileOut = in.nextLine();
		}
		loadData(fileIn);
		writeToFile(fileOut);
	}

}
