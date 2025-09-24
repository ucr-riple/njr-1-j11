/**
 * 
 */
package utility;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import Math.MathPoint;
import Math.MathUtility;

/**
 * @author chenqian
 *
 */
public class CompareFunction {

	public static double functionL(double w, MathPoint p, MathPoint q){
		return w * MathUtility.getDistance(p.getX(), p.getY(), q.getX(), q.getY()) + (1 - w) * p.getZ();
	}
	
	public static double functionNL(double w, MathPoint p, MathPoint q){
		return w * w * MathUtility.getDistance2(p.getX(), p.getY(), q.getX(), q.getY()) + (1 - w) * (1 - w) * p.getZ() * p.getZ(); 
	}
	
	public static int topkQuery(ArrayList<data> datas, int k, double x, double y, double w){
		MathPoint q = new MathPoint(x, y, 0);
		ArrayList<tuple> tL = new ArrayList<tuple>();
		ArrayList<tuple> tNL = new ArrayList<tuple>();
		HashMap<Integer, Integer> hashmap = new HashMap<Integer, Integer>();
		for(int i = 0; i < datas.size(); i ++){
			data d = datas.get(i);
			double dist = functionL(w, d.p, q);
			int id = d.id;
			tL.add(new tuple(dist, id, MathUtility.getDistance(d.p.getX(), d.p.getY(), q.getX(), q.getY()), d.p.getZ()));
			dist = functionNL(w, d.p, q);
			id = d.id;
			tNL.add(new tuple(dist, id, MathUtility.getDistance2(d.p.getX(), d.p.getY(), q.getX(), q.getY()), d.p.getZ() * d.p.getZ()));
		}
		int res = 0;
		Collections.sort(tL);
		Collections.sort(tNL);
//		System.out.println("============ tL ============");
//		for(int i = 0; i < k; i ++){
//			System.out.println(i + ": " + tL.get(i).dist + ", " + tL.get(i).id + ", " + tL.get(i).w1 + ", " + tL.get(i).w2);
//		}
//		System.out.println("============ tNL ============");
//		for(int i = 0; i < k; i ++){
//			System.out.println(i + ": " + tNL.get(i).dist + ", " + tNL.get(i).id + ", " + tNL.get(i).w1 + ", " + tNL.get(i).w2);
//		}
		for(int i = 0; i < tNL.size(); i ++){
			hashmap.put(tNL.get(i).id,  i + 1);
		}
		for(int i = 0, j = 0; i < tL.size(); i ++){
			int id = tL.get(i).id;
			int pos = hashmap.get(id);
			if(pos > 100)continue;
			j ++;
			if(res < pos){
				res = pos;
			}
			System.out.print(pos + " ");
			if(j == k)break;
		}System.out.println();
		return res;
//		System.out.println(tL.firstKey());
//		System.out.println(tNL.firstKey());
	}
	
	static int getPos(double ratio, double[] ratios){
		System.out.println(ratio);
		for(int i = 0; i < ratios.length; i ++){
			if(ratio >= ratios[i] && ratio <= ratios[i + 1]){
				return i;
			}
		}
		return ratios.length;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		ArrayList<data> datas = new ArrayList<data>();
		String file = null;
		if(args.length > 0){
			file = args[0];
		}else{
			file = in.nextLine(); 
		}
		LineNumberReader lr = new LineNumberReader(new FileReader(file));
		String line = lr.readLine();
		while (line != null) {
			StringTokenizer st = new StringTokenizer(line);
			int id = Integer.parseInt(st.nextToken());
			double x1 = Double.parseDouble(st.nextToken());
			double y1 = Double.parseDouble(st.nextToken());
			double z1 = Double.parseDouble(st.nextToken());
			line = lr.readLine();
//			datas.add(new data(new MathPoint(x1, y1, Math.sqrt(z1)), id));
			datas.add(new data(new MathPoint(x1, y1, Math.sqrt(z1) * 2), id));
		}
		lr.close();
		System.out.println("loading finished!");
		if(args.length > 1)file = args[1];
		else file = in.nextLine();
		lr = new LineNumberReader(new FileReader(file));
		line = lr.readLine();
//		double[] ws = {0.01, 0.03, 0.05, 0.08, 0.1, 0.3, 0.5, 0.7, 0.9};
//		double[] ws = {0.01, 0.03, 0.05, 0.08, 0.1, 0.3, 0.5, 0.7, 0.9};
		double[] ws = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};
		double[] ratios = {1, 1.05, 1.1, 1.15, 1.2, 1.25, 1.3, 1.35, 1.4, 1.45, 1.5, 1.55, 1.6, 1.65, 1.7, 1.75, 1.8, 1.85, Double.MAX_VALUE}; int num = 0;
		int res[] = new int[ratios.length];
		for(int i = 0; i < ratios.length; i ++) res[i] = 0;
		while (line != null) {
			String[] tokens = line.split(" "); num ++; System.out.print(".");
//			int k = Integer.parseInt(tokens[0]);
			int k = 16;
			double x = Double.parseDouble(tokens[0]), y = Double.parseDouble(tokens[1]); 
//			System.out.println("====== k = " + k + " ======" );
			for(int i = 0; i < ws.length; i ++){
				int ans = topkQuery(datas, k, x, y, ws[i]);
//				ratios[i] += 100.0 * ans / k;
				double ratio = 1.0 * ans / k;
				res[getPos(ratio, ratios)] ++;
				//System.out.println("w = " + ws[i] + " ratio = " + 100.0 * ans / k + "%");
			}
			line = lr.readLine();
		}System.out.println();
		int tot = 0;
		for(int i = 0; i < ratios.length - 1; i ++){
			tot += res[i];
		}
		for(int i = 0; i < ratios.length - 1; i ++){
			System.out.println(ratios[i] + " ~ " + ratios[i + 1] + " has " + 100.0 * res[i] / tot);
		}
		in.close();
	}	
}

class data{
	
	public data(MathPoint p, int id){
		this.p = p;
		this.id = id;
	}
	MathPoint p;
	int id;
}

class tuple implements Comparable<tuple>{
	public tuple(double dist, int id, double w1, double w2){
		this.dist = dist;
		this.id = id;
		this.w1 = w1;
		this.w2 = w2;
	}
	double w1, w2;
	double dist;
	int id;
	@Override
	public int compareTo(tuple o) {
		// TODO Auto-generated method stub
		if(MathUtility.D(dist - o.dist) < 0) return -1;
		else if(MathUtility.D(dist - o.dist) > 0) return 1;
		else {
			if(id < o.id) return -1;
			else if(id > o.id)return 1;
			else return 0;
		}
	}
	
}
