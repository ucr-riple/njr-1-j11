/**
 * 
 */
package utility;

import java.util.HashMap;
import java.util.Scanner;

import utility.geo.VoronoiNeighbors;

/**
 * @author chenqian
 *
 */
public class CalcNumberOfVN {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		while(true){
			VoronoiNeighbors vn = new VoronoiNeighbors();
			HashMap<Integer, int[]> nbHashMap = new HashMap<Integer, int[]>();
			System.out.println("Input FileName: ");
			String destFileName = in.nextLine();
			System.out.println("Input limit: ");
			int limit = Integer.parseInt(in.nextLine());
			vn.loadVoronoiNeighbors(destFileName, nbHashMap);
			long num = vn.getNumberOfVoronoiNeighbor(limit, nbHashMap);
			System.out.println("Point num: " + nbHashMap.size());
			System.out.println("VN num: " + num);
		}
	}

}
