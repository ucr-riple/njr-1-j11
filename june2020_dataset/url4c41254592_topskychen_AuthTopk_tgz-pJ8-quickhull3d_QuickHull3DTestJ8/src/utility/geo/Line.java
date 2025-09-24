package utility.geo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.crypto.Data;

import Math.BigMathPoint;
import Math.BigMathUtility;
import Math.MathPoint;
import Math.MathUtility;

import utility.security.DataIO;
import utility.security.Gfunction;
import utility.security.IVo;
import utility.security.Paillier;
import utility.security.Point;
import utility.security.SecurityUtility;
import utility.security.SeedsGenerater;

public class Line implements IVo{
	public Point pL, pH;
	BigMathPoint o_pL, o_pH; 
	Point farL_pL, farL_pH;
	Point farR_pL, farR_pH;
	public static Paillier paillier = new Paillier(true);
	public static SeedsGenerater seeds = new SeedsGenerater(true);
	BigInteger g_L_x1, g_L_y1, g_L_x2, g_L_y2, g_L_x1y2, g_L_x2y1;
	BigInteger g_R_x1, g_R_y1, g_R_x2, g_R_y2, g_R_x1y2, g_R_x2y1;
	public long[] areaRep;
	BigInteger[] baseRep;
	public Gfunction gf;
	public String[] ServerReturned;
	boolean runTest = true;
	public Line(){
		pL = new Point();
		pH = new Point();
	}
	
	public Line(long x1, long y1, long w1, long x2, long y2, long w2){
		pL = new Point(x1, y1, w1).doublePoint();
		pH = new Point(x2, y2, w2).doublePoint();
		init();
	}
	
	public Line(Point _pL, Point _pH){
		pL = new Point(_pL).doublePoint();
		pH = new Point(_pH).doublePoint();
		init();
	}
	
	public Line(int[] _pL, int[] _pH){
		pL = new Point(_pL).doublePoint();
		pH = new Point(_pH).doublePoint();
		init();
	}
	
	public Line(float[] _pL, float[] _pH){
		this(new int[]{(int)_pL[0], (int)_pL[1]}, new int[]{(int)_pH[0], (int)_pH[1]});
		//System.out.println(pL.x);
	}
	
	public void init(){
//		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
//		long start, end;
//		o_pL = new Point((pL.x + pH.x) / 2, (pL.y + pH.y) / 2, 0);
//		Point vertical = new Point(pH.y - pL.y, pL.x - pH.x, 0);
//		o_pH = new Point(o_pL);
//		o_pH.Add(vertical);
		
		/**
		 * x_{i,j} = (|x_j|^2 - |x_i|^2 + w_j - w_i) / (2|x_i - x_j|^2) (x_j - x_i),
		 * where x_i, x_j are points
		 * 
		 * */
		BigMathPoint bpH = new BigMathPoint(new long[]{pH.getX(), pH.getY(), pH.getW()});
		BigMathPoint bpL = new BigMathPoint(new long[]{pL.getX(), pL.getY(), pL.getW()});
		
		BigDecimal numerator = BigMathUtility.Square(bpH.getX()).add(
							BigMathUtility.Square(bpH.getY())
						).subtract(
							BigMathUtility.Square(bpL.getX())
						).subtract(
							BigMathUtility.Square(bpL.getY())
						).add(
							bpH.getW()
						).subtract(
							bpL.getW()
						);
		BigDecimal denomintor = BigMathUtility.getDistance2in2d(bpL, bpH).multiply(new BigDecimal("2"));
		BigDecimal k = numerator.divide(denomintor, 0, BigDecimal.ROUND_HALF_EVEN);
		o_pL = new BigMathPoint(
				k.multiply(bpH.getX().subtract(bpL.getX())), 
				k.multiply(bpH.getY().subtract(bpL.getY()))
			);
		BigMathPoint vertical_mp = new BigMathPoint(
				bpH.getY().subtract(bpL.getY()), 
				bpL.getX().subtract(bpH.getX())
		);
		Point vertical = new Point(pH.y - pL.y, pL.x - pH.x, 0);
		o_pH = new BigMathPoint(BigMathPoint.add(o_pL, vertical_mp));
		
		Point[] far_pL = new Point[4], far_pH = new Point[4];
		far_pL[0] = new Point(-(1 << 25), -(1 << 25), 0);
		far_pL[1] = new Point((1 << 25) - 1, -(1 << 25), 0);
		far_pL[2] = new Point(-(1 << 25), (1 << 25) - 1, 0);
		far_pL[3] = new Point((1 << 25) - 1, (1 << 25) - 1, 0);
		BigDecimal[] area = new BigDecimal[4];
		BigDecimal min = new BigDecimal("1e60"), max = new BigDecimal("-1e60");
		for(int i = 0; i < 4; i++ ){
			far_pH[i] = new Point(far_pL[i]);
			far_pH[i].Add(vertical);
			area[i] = BigMathUtility.Det(o_pL, new BigMathPoint(far_pL[i].getX(), far_pL[i].getY()), 
					new BigMathPoint(far_pH[i].getX(), far_pH[i].getY()));
			if(min.compareTo(area[i]) > 0){
				farL_pL = far_pL[i];
				farL_pH = far_pH[i];
				min = area[i];
			}
			if(max.compareTo(area[i]) < 0){
				farR_pL = far_pL[i];
				farR_pH = far_pH[i];
				max = area[i];
			}
		}
		
//		System.out.println(min + " " + min.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue() + " " + max + " " + max.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue());
		gf = new Gfunction(0, 16, min.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue(), max.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue());
		init_paillier();
	}
	
	void init_paillier(){
		BigInteger x1 = new BigInteger(new Long(farL_pL.x).toString());
		BigInteger y1 = new BigInteger(new Long(farL_pL.y).toString());
		BigInteger x2 = new BigInteger(new Long(farL_pH.x).toString());
		BigInteger y2 = new BigInteger(new Long(farL_pH.y).toString());
		/**
		 * For L line
		 * */
		g_L_x1 = paillier.Encryption(x1);
		g_L_y1 = paillier.Encryption(y1);
		g_L_x2 = paillier.Encryption(x2);
		g_L_y2 = paillier.Encryption(y2);
		g_L_x1y2 = paillier.Encryption(x1.multiply(y2));
		g_L_x2y1 = paillier.Encryption(x2.multiply(y1));
		
		x1 = new BigInteger(new Long(farR_pL.x).toString());
		y1 = new BigInteger(new Long(farR_pL.y).toString());
		x2 = new BigInteger(new Long(farR_pH.x).toString());
		y2 = new BigInteger(new Long(farR_pH.y).toString());
		/**
		 * for R line
		 * */
		
		g_R_x1 = paillier.Encryption(x1);
		g_R_y1 = paillier.Encryption(y1);
		g_R_x2 = paillier.Encryption(x2);
		g_R_y2 = paillier.Encryption(y2);
		g_R_x1y2 = paillier.Encryption(x1.multiply(y2));
		g_R_x2y1 = paillier.Encryption(x2.multiply(y1));
	}
	
	
	/**
	 *  To judge side first.
	 * */
	public void GenerateVeryfyPart(Point Q, boolean isL){
		Q = Q.doublePoint();
		if(o_pL == null || o_pH == null){
			BigMathPoint bpH = new BigMathPoint(new long[]{pH.getX(), pH.getY(), pH.getW()});
			BigMathPoint bpL = new BigMathPoint(new long[]{pL.getX(), pL.getY(), pL.getW()});
			
			BigDecimal numerator = BigMathUtility.Square(bpH.getX()).add(
								BigMathUtility.Square(bpH.getY())
							).subtract(
								BigMathUtility.Square(bpL.getX())
							).subtract(
								BigMathUtility.Square(bpL.getY())
							).add(
								bpH.getW()
							).subtract(
								bpL.getW()
							);
			BigDecimal denomintor = BigMathUtility.getDistance2in2d(bpL, bpH).multiply(new BigDecimal("2"));
			BigDecimal k = numerator.divide(denomintor, 100, BigDecimal.ROUND_HALF_EVEN);
			o_pL = new BigMathPoint(
					k.multiply(bpH.getX().subtract(bpL.getX())), 
					k.multiply(bpH.getY().subtract(bpL.getY()))
				);
			BigMathPoint vertical_mp = new BigMathPoint(
					bpH.getY().subtract(bpL.getY()), 
					bpL.getX().subtract(bpH.getX())
			);
			Point vertical = new Point(pH.y - pL.y, pL.x - pH.x, 0);
			o_pH = new BigMathPoint(BigMathPoint.add(o_pL, vertical_mp));
		}
		
		MathPoint oq = new MathPoint(Q.getX(), Q.getY());
		BigMathPoint boq = new BigMathPoint(Q.getX(), Q.getY());
		//long area = Point.Areax2(o_pL, o_pH, Q);
		BigDecimal area = BigMathUtility.Det(boq, o_pL, o_pH);
		if(area.signum() < 0){
//			if(MathUtility.D(area) != 0){
//				if(isL != false){
////					System.err.println("Error side!");
////					return;
//				}
//			}
			long area2 = Point.Areax2(farR_pL, farR_pH, Q);
			areaRep = seeds.getRepresentation(area2, gf.m + 1);
			baseRep = seeds.getRepresentationBase(gf.m + 1);
			ServerReturned = gf.GenerateVeryfyPart(area.negate().setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue(), false);
//			try {
//				if(gf.getDigest().equals(gf.ClientComputed(ServerReturned, area2 - 1))){
//					System.out.println("Pass!");
//				}else{
//					System.out.println("Fail!");
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}else{
//			if(MathUtility.D(area) != 0){
//				if(isL != true){
////					System.err.println("Error side!");
////					return;
//				}
//			}
			long area2 = Point.Areax2(farL_pL, farL_pH, Q);
			areaRep = seeds.getRepresentation(- area2, gf.m + 1);
			baseRep = seeds.getRepresentationBase(gf.m + 1);
			ServerReturned = gf.GenerateVeryfyPart(area.negate().setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue(), true);	
//			try {
//				if(gf.getDigest().equals(gf.ClientComputed(ServerReturned, - area2 - 1))){
//					System.out.println("Pass!");
//				}else{
//					System.out.println("Fail!");
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
		
	void writeToFileOfPaillier(DataOutputStream dos){
		DataIO.writeBigInteger(dos, g_L_x1);
		DataIO.writeBigInteger(dos, g_L_y1);
		DataIO.writeBigInteger(dos, g_L_x2);
		DataIO.writeBigInteger(dos, g_L_y2);
		DataIO.writeBigInteger(dos, g_L_x1y2);
		DataIO.writeBigInteger(dos, g_L_x2y1);
		
		DataIO.writeBigInteger(dos, g_R_x1);
		DataIO.writeBigInteger(dos, g_R_y1);
		DataIO.writeBigInteger(dos, g_R_x2);
		DataIO.writeBigInteger(dos, g_R_y2);
		DataIO.writeBigInteger(dos, g_R_x1y2);
		DataIO.writeBigInteger(dos, g_R_x2y1);
	}
	
	public void writeToFile(DataOutputStream dos){
		try {
			writeToFileOfPaillier(dos);
			dos.writeLong(pL.x);
			dos.writeLong(pL.y);
			dos.writeLong(pH.x);
			dos.writeLong(pH.y);
			dos.writeLong(farL_pL.x);
			dos.writeLong(farL_pL.y);
			dos.writeLong(farL_pH.x);
			dos.writeLong(farL_pH.y);
			dos.writeLong(farR_pL.x);
			dos.writeLong(farR_pL.y);
			dos.writeLong(farR_pH.x);
			dos.writeLong(farR_pH.y);
			gf.writeToFile(dos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void readFromFileOfPaillier(DataInputStream dis){
		g_L_x1 = DataIO.readBigInteger(dis);
		g_L_y1 = DataIO.readBigInteger(dis);
		g_L_x2 = DataIO.readBigInteger(dis);
		g_L_y2 = DataIO.readBigInteger(dis);
		g_L_x1y2 = DataIO.readBigInteger(dis);
		g_L_x2y1 = DataIO.readBigInteger(dis);
		
		g_R_x1 = DataIO.readBigInteger(dis);
		g_R_y1 = DataIO.readBigInteger(dis);
		g_R_x2 = DataIO.readBigInteger(dis);
		g_R_y2 = DataIO.readBigInteger(dis);
		g_R_x1y2 = DataIO.readBigInteger(dis);
		g_R_x2y1 = DataIO.readBigInteger(dis);
	}
	
	public static int seekLenOfPaillier(DataInputStream dis){
		int ans = 0;
		for(int i = 0; i < 10; i++){
			int len;
			try {
				len = dis.readInt();
				dis.skipBytes(len);
				ans += 4 + len;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ans;
	}
	
	public static int seekLen(DataInputStream dis){
		int ans = 0;
		/*try {
			ans += seekLenOfPaillier(dis);
			ans += 6 * (8 + 8);
			dis.skipBytes(6 * (8 + 8));
			ans += Gfunction.seekLen(dis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return ans;
	}
	
	public void readFromFile(DataInputStream dis){
		try {
			readFromFileOfPaillier(dis);
			pL = new Point(dis.readLong(), dis.readLong(), 0);
			pH = new Point(dis.readLong(), dis.readLong(), 0);
			farL_pL = new Point(dis.readLong(), dis.readLong(), 0);
			farL_pH = new Point(dis.readLong(), dis.readLong(), 0);
			farR_pL = new Point(dis.readLong(), dis.readLong(), 0);
			farR_pH = new Point(dis.readLong(), dis.readLong(), 0);
			gf = new Gfunction();
			gf.readFromFile(dis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean ClientVerify(int q_x, int q_y){
		return ClientVerify(new Point(q_x, q_y, 0));
	}

	public boolean ClientVerify(Point q) {
		// TODO Auto-generated method stub
		q = q.doublePoint();
		//long start = System.currentTimeMillis();
		BigInteger b_q_x = BigInteger.valueOf(q.x);
		BigInteger b_q_y = BigInteger.valueOf(q.y);
		BigInteger left, right;
		if(gf.isL){
			left = g_L_x1y2;
			left = left.multiply(g_L_x2.modPow(b_q_y, paillier.nsquare)).mod(paillier.nsquare);
			left = left.multiply(g_L_y1.modPow(b_q_x, paillier.nsquare)).mod(paillier.nsquare);
			right = g_L_x2y1;
			right = right.multiply(g_L_x1.modPow(b_q_y, paillier.nsquare)).mod(paillier.nsquare);
			right = right.multiply(g_L_y2.modPow(b_q_x, paillier.nsquare)).mod(paillier.nsquare);
			
			//System.out.println(paillier.Decryption(left));
			for(int i = 0; i <= gf.m; i++){
				right = right.multiply(baseRep[i].modPow(BigInteger.valueOf(areaRep[i]), paillier.nsquare)).mod(paillier.nsquare);
			}
			//System.out.println(paillier.Decryption(right));
		}else{
			left = g_R_x1y2;
			left = left.multiply(g_R_x2.modPow(b_q_y, paillier.nsquare)).mod(paillier.nsquare);
			left = left.multiply(g_R_y1.modPow(b_q_x, paillier.nsquare)).mod(paillier.nsquare);
			right = g_R_x2y1;
			right = right.multiply(g_R_x1.modPow(b_q_y, paillier.nsquare)).mod(paillier.nsquare);
			right = right.multiply(g_R_y2.modPow(b_q_x, paillier.nsquare)).mod(paillier.nsquare);
			for(int i = 0; i <= gf.m; i++){
				left = left.multiply(baseRep[i].modPow(BigInteger.valueOf(areaRep[i]), paillier.nsquare)).mod(paillier.nsquare);
			}
			//System.out.println(paillier.Decryption(left));
			
			//System.out.println(paillier.Decryption(right));
		}
		
		if(left.equals(right) == false){
			if(!runTest){
				System.err.println("Err at verify value of area!");
				return false;
			}
		}
		try {
			if(gf.getDigest().equals(gf.ClientComputed(ServerReturned, areaRep)) == false){
				if(!runTest){
					System.err.println("Err at g function");
					return false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("time : " + (System.currentTimeMillis() - start));
		return true;
	}

	/* (non-Javadoc)
	 * @see utility.security.IVo#getHashcode()
	 */
	@Override
	public String getDigest() {
		// TODO Auto-generated method stub

		return SecurityUtility.computeGeneralHashValue(new String[]{
				g_L_x1.toString(), 
				g_L_y1.toString(), 
				g_L_x2.toString(), 
				g_L_y2.toString(), 
				g_L_x1y2.toString(), 
				g_L_x2y1.toString(),
				g_R_x1.toString(), 
				g_R_y1.toString(), 
				g_R_x2.toString(), 
				g_R_y2.toString(), 
				g_R_x1y2.toString(), 
				g_R_x2y1.toString(),
				gf.getDigest()
				
		});
	}
	
	public long getVOSize(){
		long ans = 0;
		if(gf.isL){
			ans += g_L_x1.toByteArray().length;
			ans += g_L_y1.toByteArray().length;
			ans += g_L_x2.toByteArray().length;
			ans += g_L_y2.toByteArray().length;
			ans += g_L_x1y2.toByteArray().length;
			ans += g_L_x2y1.toByteArray().length;
		}else{
			ans += g_R_x1.toByteArray().length;
			ans += g_R_y1.toByteArray().length;
			ans += g_R_x2.toByteArray().length;
			ans += g_R_y2.toByteArray().length;
			ans += g_R_x1y2.toByteArray().length;
			ans += g_R_x2y1.toByteArray().length;
		}
		ans += ServerReturned.length;
		for(int i = 0; i < baseRep.length; i++){
			ans += baseRep[i].toByteArray().length;
			ans += 4;
		}
		return ans;
	}
	
	
	/**
	 * if q is located to left side, set 'whichside' to false;
	 * if q is located to right side, set 'whichside' to true;
	 * 
	 * */
	public static void main(String[] args){
		
		/**
		 * example
		 * */
		int times = 1;
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		long start = bean.getCurrentThreadCpuTime(), end;	
		Line l = new Line(new int[]{ 0, 100, 0}, new int[]{100, 0, 0});
		end = bean.getCurrentThreadCpuTime();
		System.out.println("For init:\t" + (end -start) / 1000000.0 + " ms");
		Point Q = new Point (1, 1, 0);
		//System.out.println("area: " + Point.Areax2(l.o_pL, l.o_pH, Q.doublePoint()));
//		l.GenerateVeryfyPart(Q, false);
//
//		for(int i = 0; i < times; i++){
//			if(l.ClientVerify(Q)){
//				//System.out.println("Pass verification");
//			}else{
//				System.err.println("fail verification");
//			}
//		}
//		System.err.println("===========================");
		double[][] data ={
				{3,	4,	3},
				{1,	2,	6},
				{3, 1, 4},
				{6,	1,	5},
				{7,	2,	10},
				{8,	1,	10},
				{6,	6,	13},
				{3,	7,	15},
				{7,	7,	13}
				};
		for(int i = 0; i < data.length; i++){
			if(i == 3) continue;
			l = new Line(new int[]{(int)data[i][0], (int)data[i][1], (int)data[i][2]}, new int[]{6, 1, 5});
			
			Q = new Point (4, 4, 0);
			//System.out.println("area: " + Point.Areax2(l.o_pL, l.o_pH, Q.doublePoint()));
			if(i == 0 || i == 2)l.GenerateVeryfyPart(Q, true);
			else l.GenerateVeryfyPart(Q, false);
			start = System.currentTimeMillis();	
			System.out.print("id : " + i + " ");
			if(l.ClientVerify(Q)){
				System.out.println("Pass verification");
			}else{
				System.err.println("fail verification");
			}
		}
		
		
		l = new Line(new int[]{3, 1, 4}, new int[]{6, 1, 5});
		
		Q = new Point (4, 4, 0);
		//System.out.println("area: " + Point.Areax2(l.o_pL, l.o_pH, Q.doublePoint()));
		l.GenerateVeryfyPart(Q, true);
		start = System.currentTimeMillis();	
		for(int i = 0; i < times; i++){
			if(l.ClientVerify(Q)){
				System.out.println("Pass verification");
			}else{
				System.err.println("fail verification");
			}
		}
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(new FileOutputStream(new File("./tmp/line")));
			l.writeToFile(dos);
			dos.close();
			DataInputStream dis = new DataInputStream(new FileInputStream(new File("./tmp/line")));
			l.readFromFile(dis);
			dis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
