/**
 * 
 */
package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import utility.geo.DataOfLine;

/**
 * @author chenqian
 *
 */
public class FormatData2Norm {

	public static int scale = 10000;
	public static ArrayList<long[]> pointsAfter = null;
	public static HashSet<Long> cnt = new HashSet<Long>();
	
	public static long getId(long id1, long id2){
		return id1 * DataOfLine.M + id2;
	}

	public static void loadFile(String sourceFileName, String destFileName) throws FileNotFoundException{
		Scanner filein = new Scanner(new File(sourceFileName));
		PrintWriter pw = new PrintWriter(new File(destFileName));
		ArrayList<double[]> points = new ArrayList<double[]>();
		double minx = 1e20, maxx=-1e20, miny = 1e20, maxy = -1e20, minz = 1e20, maxz = -1e20;
		while(filein.hasNext()){
			/**
			 * data format : operation, id, x1, y1, x2, y2
			 * */
			String line = filein.nextLine();
			String[] tks = line.split(" ");
			double[] point = null;
			if(sourceFileName.endsWith("CA.dat"))point = new double[]{Double.parseDouble(tks[2]), Double.parseDouble(tks[3]), 0};
			else point = new double[]{Double.parseDouble(tks[0]), Double.parseDouble(tks[1]), Double.parseDouble(tks[2])};
			points.add(point);
			if(minx > point[0]) minx = point[0];
			if(maxx < point[0]) maxx = point[0];
			if(miny > point[1]) miny = point[1];
			if(maxy < point[1]) maxy = point[1];
			if(minz > point[2]) minz = point[2];
			if(maxz < point[2]) maxz = point[2];
		}
		filein.close();
		pointsAfter = new ArrayList<long[]>();
		for(int i = 0; i < points.size(); i++){
			double[] point = points.get(i);
			long x = (long)((point[0] - minx) / (maxx - minx) * scale);
			long y = (long)((point[1] - miny) / (maxy - miny) * scale);
			long z = 0;
			if(maxz != minz)z = (long)((point[2] - minz) / (maxz - minz) * scale);
			long id = getId(x, y);
			if(cnt.contains(id)){
				continue;
			}else{
				cnt.add(id);
			}
			z += -(x * x + y * y);
			pointsAfter.add(new long[]{x, y, z});
			//pw.println(x + "\t" + y);
//			pw.println(x + "\t" + y + "\t" + z);
		}
		pw.println("3");
		pw.println(pointsAfter.size());
		for(int i = 0; i < pointsAfter.size(); i++){
			long[] point = pointsAfter.get(i);
			pw.println(point[0] + "\t" + point[1] + "\t" + point[2]);
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
		String sourceFileName = null, destFileName = null;
		if(args.length > 0){			
			sourceFileName = args[0];
			destFileName = args[1];
		}else{
			Scanner in = new Scanner(System.in);
			String line = "Choose dataset \n(a) NE\n(b) User input";
			System.out.println(line);
			String option = in.nextLine();
			if(option.equalsIgnoreCase("a")){
			}else if(option.equalsIgnoreCase("b")){
				System.out.println("Please input source file name:");
				sourceFileName = in.nextLine();
				System.out.println("Please input destination file name:");
				destFileName = in.nextLine();
			}
		}
		loadFile(sourceFileName, destFileName);
		System.out.println("Finished!");
	}

}
