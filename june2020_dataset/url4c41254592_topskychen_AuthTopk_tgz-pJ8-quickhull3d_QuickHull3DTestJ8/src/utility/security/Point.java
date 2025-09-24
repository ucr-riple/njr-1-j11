package utility.security;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.BitSet;

public class Point {
	public long x, y, w; // x, y and weight
	public BigInteger g_p_x2 = null, g_p_y2 = null, g_2p_x, g_2p_y, g_p_w;
	public BigInteger g_a, g_0;
	public static SeedsGenerater seeds = new SeedsGenerater(true);
	public static Paillier pailliar = new Paillier(true);
	public static BigInteger a = pailliar.p;
	
	public Point(){
		x = 0; 
		y = 0;
		w = 0;
	}
	
	public Point(long _x, long _y, long _w){
		x = _x;
		y = _y;
		w = _w;
	}
	public Point(long[] c){
		x = c[0];
		y = c[1];
		w = c[2];
	}
	
	public Point(int[] c){
		x = c[0];
		y = c[1];
		w = c[2];
	}
	
	public Point(Point _p){
		this.x = _p.x;
		this.y = _p.y;
		this.w = _p.w;
	}
	
	public Point(Point _p, boolean isLoad){
		this.x = _p.x;
		this.y = _p.y;
		this.w = _p.w;
		if(isLoad){
			this.g_2p_x = _p.g_2p_x;
			this.g_2p_y = _p.g_2p_y;
			this.g_p_x2 = _p.g_p_x2;
			this.g_p_y2 = _p.g_p_y2;
			this.g_p_w = _p.g_p_w;
		}
	}
	
	public void setXSide(Point p){
		this.x = p.x;
		this.g_2p_x = p.g_2p_x;
		this.g_p_x2 = p.g_p_x2;
		this.g_a = p.g_a;
		this.g_p_w = p.g_p_w;
	}
	
	public void setYSide(Point p){
		this.y = p.y;
		this.g_2p_y = p.g_2p_y;
		this.g_p_y2 = p.g_p_y2;
		this.g_a = p.g_a;
		this.g_p_w = p.g_p_w;
	}
	
	
	/**
	 * This didn't consider w.,
	 * 
	 * */
	public Point(Point p1, Point p2){
		this.x = p1.x;
		this.y = p2.y;	
		this.w = p1.w;
		this.g_2p_x = p1.g_2p_x;
		this.g_2p_y = p2.g_2p_y;
		this.g_p_x2 = p1.g_p_x2;
		this.g_p_y2 = p2.g_p_y2;
		this.g_a = p1.g_a;
		this.g_p_w = p1.g_p_w;
	}
	
	public Point doublePoint(){
		return new Point(x * 2, y * 2, w * 4); // we need square at last, so we multiply 4 for w.
	}
	
	public void Add(Point q){
		x += q.x;
		y += q.y;
		w += q.w;
	}
	
	public static long Areax2(Point L, Point H, Point Q){
		return (Q.x - H.x) * (Q.y - L.y) - (Q.y - H.y) * (Q.x - L.x);
	}
	
	public static long Distance2(Point a, Point b){
		return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
	}
	
	public static long Distance2(long x1, long y1, long x2, long y2) {
		// TODO Auto-generated method stub
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
		//return null;
	}
	
	/**
	 * point a,b,c
	 * distance of Dist(b,c) - Dist(a,c)
	 * 
	 * */
	/*public static BigInteger[] buildFactorsByPaillier(Point pL, Point pH, long q_x, long q_y){
		BigInteger dis1 = new BigInteger(new Long(Point.Distance2(pH.x, pH.y, q_x, q_y)).toString());
		BigInteger dis2 = new BigInteger(new Long(Point.Distance2(pL.x, pL.y, q_x, q_y)).toString());
		return seeds.Decompose(dis1.subtract(dis2));
	}*/
	
	/**
	 * point a,b,c
	 * distance of Dist(b,c) - Dist(a,c)
	 * distance is signed
	 * */
	public static BigInteger buildDelta(Point pL, Point pH, long q_x, long q_y){
		BigInteger dis1 = new BigInteger(new Long(Point.Distance2(pH.x, pH.y, q_x, q_y) + pH.w).toString());
		BigInteger dis2 = new BigInteger(new Long(Point.Distance2(pL.x, pL.y, q_x, q_y) + pL.w).toString());
		//return seeds.Decompose2(dis1.subtract(dis2).multiply(a));
		//System.out.println(dis1 + " " + dis2);
		return seeds.Decompose2(dis1.subtract(dis2));
	}
	
	/**
	 * For Client
	 * */
	/*public BigInteger CompositionPart1(int q_x, int q_y){
		//long start = System.currentTimeMillis();
		BigInteger b_q_x = BigInteger.valueOf(q_x), b_q_y = BigInteger.valueOf(q_y);
		//start = System.currentTimeMillis();
		BigInteger g_a_q_x = g_a.modPow(b_q_x, pailliar.nsquare);
		BigInteger g_a_q_y = g_a.modPow(b_q_y, pailliar.nsquare);
		BigInteger ans = g_a_q_x.multiply(g_a_q_y).mod(pailliar.nsquare);
		ans = ans.multiply(g_p_x2_y2)
		//System.err.println(System.currentTimeMillis() - start);
		return .multiply(dis2).mod(pailliar.nsquare);
	}
	
	public BigInteger CompositionPart2(int q_x, int q_y){
		BigInteger b_q_x = new BigInteger(new Integer(q_x).toString()), b_q_y = new BigInteger(new Integer(q_y).toString());
		BigInteger dis1 = g_2a2p_x.modPow(b_q_x, pailliar.nsquare);
		BigInteger dis2 = g_2a2p_y.modPow(b_q_y, pailliar.nsquare);
		return dis1.multiply(dis2).mod(pailliar.nsquare);
	}*/
	
	/**
	 * verify by client a, b, q, distance_delta
	 * dist(a, q) <= dist(b, q)
	 * */
	public static boolean verifyByClient(Point a, Point b, Point q, BigInteger delta){
		BigInteger b_q_x = BigInteger.valueOf(q.x), b_q_y = BigInteger.valueOf(q.y);
		BigInteger left = a.g_2p_x.modPow(b_q_x, pailliar.nsquare);
		left = left.multiply(a.g_2p_y.modPow(b_q_y, pailliar.nsquare)).mod(pailliar.nsquare);
		left = left.multiply(b.g_p_x2.multiply(b.g_p_y2).mod(pailliar.nsquare)).mod(pailliar.nsquare);
		left = left.multiply(b.g_a.modPow(b_q_x.add(b_q_y).subtract(BigInteger.ONE), pailliar.nsquare)).mod(pailliar.nsquare);
		left = left.multiply(b.g_p_w).mod(pailliar.nsquare);
//		left = left.multiply(delta).mod(pailliar.nsquare);
		BigInteger right = b.g_2p_x.modPow(b_q_x, pailliar.nsquare);
		right = right.multiply(b.g_2p_y.modPow(b_q_y, pailliar.nsquare)).mod(pailliar.nsquare);
		right = right.multiply(a.g_p_x2.multiply(a.g_p_y2).mod(pailliar.nsquare)).mod(pailliar.nsquare);
		right = right.multiply(a.g_a.modPow(b_q_x.add(b_q_y).subtract(BigInteger.ONE), pailliar.nsquare)).mod(pailliar.nsquare);
		right = right.multiply(a.g_p_w).mod(pailliar.nsquare);
		right = right.multiply(delta).mod(pailliar.nsquare);
		if(left.equals(right))return true;
		return false;
	}
	
	public void buildByPaillier(){
		//g_0 = pailliar.Encryption(BigInteger.ZERO);
		g_a = pailliar.Encryption(a);
		//factors = seeds.Decompose(paillier.Encryption(Distance2(pH.x, pH.y, q_x, q_y).subtract(Distance2(pL.x, pL.y, q_x, q_y)).multiply(a2)));
		BigInteger b_x = BigInteger.valueOf(x), b_y = BigInteger.valueOf(y);
//		g_p_x2_y2 = pailliar.Encryption(b_x.multiply(b_x).add(b_y.multiply(b_y)).multiply(a));
//		g_2p_x = pailliar.Encryption(b_x.multiply(BigInteger.valueOf(2)).multiply(a));
//		g_2p_y = pailliar.Encryption(b_y.multiply(BigInteger.valueOf(2)).multiply(a));
		g_p_x2 = pailliar.Encryption(b_x.multiply(b_x));
		g_p_y2 = pailliar.Encryption(b_y.multiply(b_y));
		g_2p_x = pailliar.Encryption(b_x.multiply(BigInteger.valueOf(2)));
		g_2p_y = pailliar.Encryption(b_y.multiply(BigInteger.valueOf(2)));
		g_p_w = pailliar.Encryption(BigInteger.valueOf(w));
	}
	
	
	public void readFromFile(DataInputStream dis){
		try {
			x = dis.readLong();
			y = dis.readLong();
			w = dis.readLong();
			g_p_x2 = DataIO.readBigInteger(dis);
			g_p_y2 = DataIO.readBigInteger(dis);
			g_2p_x = DataIO.readBigInteger(dis);
			g_2p_y = DataIO.readBigInteger(dis);
			g_a = DataIO.readBigInteger(dis);
			g_p_w = DataIO.readBigInteger(dis);
			//g_0 = DataIO.readBigInteger(dis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeToFile(DataOutputStream dos){
		try {
			dos.writeLong(x);
			dos.writeLong(y);
			dos.writeLong(w);
			DataIO.writeBigInteger(dos, g_p_x2);
			DataIO.writeBigInteger(dos, g_p_y2);
			DataIO.writeBigInteger(dos, g_2p_x);
			DataIO.writeBigInteger(dos, g_2p_y);
			DataIO.writeBigInteger(dos, g_a);
			DataIO.writeBigInteger(dos, g_p_w);
			//DataIO.writeBigInteger(dos, g_0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDigest(){
		BigInteger[] tmp = {g_p_x2, g_p_y2, g_2p_x, g_2p_y, g_a, g_p_w};
		try {
			return SecurityUtility.computeHashValue(tmp);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDigestX(){
		try {
			return SecurityUtility.computeHashValue(new BigInteger[]{g_p_x2, g_2p_x, g_a, g_p_w});
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getDigestY(){
		try {
			return SecurityUtility.computeHashValue(new BigInteger[]{g_p_y2, g_2p_y, g_a, g_p_w});
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String compactHashCode(){
		/*return SecurityUtility.computeHashValue(new BigInteger[]{
			new BigInteger(new Long(x).toString()),
			new BigInteger(new Long(y).toString())
		});*/
		return null;
	}
	
	public long getVOSize(){
		long ans = 0;
		ans += g_2p_x.toByteArray().length;
		ans += g_2p_y.toByteArray().length;
		ans += g_p_x2.toByteArray().length;
		ans += g_p_y2.toByteArray().length;
		ans += g_a.toByteArray().length;
		ans += g_p_w.toByteArray().length;
		return ans;
	}
	
	public void print(){
		System.out.print("x = " + x + ", y = " + y);
		System.out.println();
	}
	
	public long getX(){
		return this.x;
	}
	
	public long getY(){
		return this.y;
	}
	
	public long getW(){
		return this.w;
	}
	
	public void setX(long x){
		this.x = x;
	}
	
	public void setY(long y){
		this.y = y;
	}
	
	public void setW(long w){
		this.w = w;
	}
	
	public static void main(String args[]){
		Point L = new Point(1, 0, 0), H = new Point(0, 1, 0), Q = new Point(1, 1, 0);
		System.out.println("areax2 : " + Point.Areax2(L, H, Q));
		
		/**
		 * test for paillier point based function
		 * */
		Point a = new Point(100, 0, 0), b = new Point(0, 100, 0), q = new Point(0, 0, 0);//equal
		RSA rsa = new RSA();
		a.buildByPaillier();
		b.buildByPaillier();
		BigInteger rsa_delta = buildDelta(a, b, q.x, q.y);//for server
		if(verifyByClient(a, b, q, rsa.decrypt(rsa_delta))){
			System.out.println("Success!");
		}else{
			System.err.println("Fail!");
		}
		
		q = new Point(100, 0, 0);
		rsa_delta = buildDelta(a, b, q.x, q.y);//for server
		//System.out.println(rsa.decrypt(rsa_delta));
		if(verifyByClient(a, b, q, rsa.decrypt(rsa_delta))){
			System.out.println("Success!");
		}else{
			System.err.println("Fail!");
		}
		DataOutputStream dos;
		try {
			File tmpFile = new File("./tmp/point");
			dos = new DataOutputStream(new FileOutputStream(tmpFile));
			a.writeToFile(dos);
			b.writeToFile(dos);
			dos.close();
			DataInputStream dis = new DataInputStream(new FileInputStream(tmpFile));
			a.readFromFile(dis);
			b.readFromFile(dis);
			dis.close();
			tmpFile.delete();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
