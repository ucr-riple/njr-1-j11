/**
 * 
 */
package utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import Math.MathUtility;

import utility.geo.DataOfLine;

/**
 * @author chenqian
 * This file is to parse data from gowalla dataset http://snap.stanford.edu/data/loc-gowalla.html.
 * It will will generate the rating/score for every location.
 * The format of original dataset is [user]	[check-in time]		[latitude]	[longitude]	[location id].
 * 
 * rate is to control the ratio of sqrt(z) weight compared with x,y. 
 * 
 */
public class gowallaParser {

	public static HashMap<Long, location> locations = new HashMap<Long, location>();
	public static double rate ;
	public static double minx = 1e50, maxx = -1e50, miny = 1e50, maxy = -1e50, minz = 1e50, maxz = -1e50;
	public static HashSet<Long> cnt = new HashSet<Long>();
	public static String destFileName;
	public static String fileInputPath = "source/GO.dat";
	
	public static long getId(long id1, long id2, long id3){
		return id1 * DataOfLine.M * DataOfLine.M + id2 * DataOfLine.M + id3;
//		return id1 * DataOfLine.M + id2;
	}
	
	public static void loadData(String fileInputPath) throws FileNotFoundException{
		Scanner in = new Scanner(new BufferedInputStream(new FileInputStream(new File(fileInputPath))));
		int lineId = 0;
		while(in.hasNext()){
			String line = in.nextLine();
			String[] tks = line.split("\t");
			long locId = Long.parseLong(tks[4]);
			double lat = Double.parseDouble(tks[2]);
			double lng = Double.parseDouble(tks[3]);
			if(locations.containsKey(locId)){				
				locations.get(locId).addOneRating();
			}else{
				locations.put(locId, new gowallaParser().new location(locId, lat, lng));
			}
			lineId ++;
			if(lineId % 9999 == 0)System.out.print(".");
			if(lineId % 999999 == 0)System.out.println("");
		}
		in.close();
		System.out.println("Load data finished!");
		System.out.println("Locations:\t" + locations.size());
	}
	
	public static void formatData(String destFileName) throws FileNotFoundException{
		loadData(fileInputPath);
		ArrayList<double[]> points = new ArrayList<double[]>();
		Iterator<Long> iter = locations.keySet().iterator();
		while(iter.hasNext()){
			Long key = iter.next();
			location loc = locations.get(key);
			double[] point = new double[]{loc.getX(), loc.getY(), loc.getW()};
			if(minx > point[0]) minx = point[0];
			if(maxx < point[0]) maxx = point[0];
			if(miny > point[1]) miny = point[1];
			if(maxy < point[1]) maxy = point[1];
			if(minz > point[2]) minz = point[2];
			if(maxz < point[2]) maxz = point[2];
			points.add(point);
		}
		double tminx = minx, tmaxx = maxx, tminy = miny, tmaxy = maxy;
		minx = (tmaxx - tminx) * 0.22 + minx;
		maxx = (tmaxx - tminx) * 0.36  + minx;
		miny = (tmaxy - tminy) * 0.125 + miny;
		maxy = (tmaxy - tminy) * 0.5 + miny;
//		minx = 30.75629467265251; miny = -117.68412699999999;
//		maxx = 45.42058094907479; maxy = -66.99663100000009;
		System.out.println("Coordinates bounds:\t" + minx + ", " + maxx + ", " + miny + ", " + maxy);
		System.out.println("Distance:\t" + MathUtility.calculateLatLngDist(minx, miny, maxx, maxy));
		System.out.println("Distance:\t" + MathUtility.calculateLatLngDist(minx, miny, maxx, miny));
		System.out.println("Distance:\t" + MathUtility.calculateLatLngDist(minx, miny, minx, maxy));
		ArrayList<long[]> pointsAfter = new ArrayList<long[]>();
		PrintWriter pw = new PrintWriter(new File(destFileName + rate + ".nm"));
		for(int i = 0; i < points.size(); i++){
			double[] point = points.get(i);
			if(point[0] > maxx || point[0] < minx)continue;
			if(point[1] > maxy || point[1] < miny)continue;
			long x = (long)((point[0] - minx)/ (maxx - minx) * FormatData2Norm.scale);
			long y = (long)((point[1] - miny) / (maxy - miny) * FormatData2Norm.scale);
			long z = (long)0;
			if(maxz != minz) z = (long)((1 - (point[2] - minz) / (maxz - minz)) * FormatData2Norm.scale * FormatData2Norm.scale * rate * rate);
			long id = getId(x, y, z);
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
		System.out.println("Format finish!");
		System.out.println("Number of points:\t" + pointsAfter.size());
	}
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		if(args.length > 0){
			rate = Double.parseDouble(args[0]);
			destFileName = args[1];
		}else{
			Scanner in = new Scanner(System.in);
			System.out.println("Please input the rate to convert:(0.1,1,10)");
			rate = Double.parseDouble(in.nextLine());
			System.out.println("Please input the destination file name:");
			destFileName = in.nextLine();
		}
		formatData(destFileName);
	}
	
	class location{
		long locId;
		long rating;
		double lat, lng;
		public location() {
			// TODO Auto-generated constructor stub
			rating = 0;
		}
		public location(long _locId, double _lat, double _lng){
			locId = _locId;
			lat = _lat;
			lng = _lng;
			rating = 1;
		}
		public void addOneRating(){
			++ rating ;
		}
		
		public double getX(){
			return lat;
		}
		
		public double getY(){
			return lng;
		}
		
		public double getW(){
			return rating;
		}
		
	}

}
