package utility.Compare;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;

import Math.MathUtility;

import utility.security.IVo;
import utility.security.Paillier;
import utility.security.Point;
import utility.security.RSA;

public class DistanceCompare implements IVo{
	boolean runTest = true;
	public Point pL, pH;
	public BigInteger rsa_delta;
	public static Paillier pailliar = new Paillier(true);
	public RSA rsa = new RSA();
	
	public DistanceCompare(Point _pL, Point _pH, boolean isLoad){
		pL = new Point(_pL, isLoad);
		pH = new Point(_pH, isLoad);
	}
	
	public DistanceCompare(Point _pL, Point _pH){
		pL = new Point(_pL);
		pH = new Point(_pH);
		init();
	}
	
	public DistanceCompare(int x1, int y1, int w1, int x2, int y2, int w2){
		pL = new Point(x1, y1, w1);
		pH = new Point(x2, y2, w2);
		init();
	}
	
	public DistanceCompare(int[] _pL, int[] _pH ){
		pL = new Point(_pL);
		pH = new Point(_pH);
		init();
	}
	
	public void init(){
		pL.buildByPaillier();
		pH.buildByPaillier();
	}
	
	public void GenerateVeryfyPart(Point Q){
		rsa_delta = Point.buildDelta(pL, pH, Q.x, Q.y);//for server
	}
	
	public boolean ClientVerify(Point Q){
		return ClientVerify((int)Q.x, (int)Q.y);
	}
	
	public boolean ClientVerify(int q_x, int q_y){
		//long start = System.currentTimeMillis();
		if(Point.verifyByClient(pL, pH, new Point(q_x, q_y, 0), rsa.decrypt(rsa_delta))){
			//System.out.println("Success!");
			return true;
		}else{
			//System.err.println("Fail!");
			if(!runTest){
				System.out.println("L:\t" + pL.getX() + ", " + pL.getY() + ", " + pL.getW());
				System.out.println("H:\t" + pH.getX() + ", " + pH.getY() + ", " + pH.getW());
				System.out.println("Q:\t" + q_x + ", " + q_y);
				System.out.println("Tdelta:\t" + (
						MathUtility.getDistance2(new double[]{pH.getX(), pH.getY(), Math.sqrt(pH.getW())}, new double[]{q_x, q_y, 0}) 
						- MathUtility.getDistance2(new double[]{pL.getX(), pL.getY(), Math.sqrt(pL.getW())}, new double[]{q_x, q_y, 0})
						)
						);
				System.out.println("Cdelta:\t" + pailliar.Decryption(rsa.decrypt(rsa_delta)));
			}
			return false;
		}
	}

	public long getVOSize(){
		long ans = 0;
		ans += pL.getVOSize();
		ans += pH.getVOSize();
		ans += rsa_delta.toByteArray().length;
		return ans;
	}
	/* (non-Javadoc)
	 * @see utility.security.IVo#getHashcode()
	 */
	@Override
	public String getDigest() {
		// TODO Auto-generated method stub
		return null;
		//return SecurityUtility.computeHashValue(new BigInteger[]{new BigInteger(pL.HashCode(), 16), new BigInteger(pH.HashCode(), 16)});
	}
	
	public byte[] writeToBytes(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		pL.writeToFile(dos);
		pH.writeToFile(dos);
		return baos.toByteArray();
	}
	
	public static void main(String[] args){
		
		/**
		 * example, Dist(pL, q) <= Dist(pH, q)
		 * */
		Point q = new Point(1, 1, 0);
		Point p1 = new Point(31468, 34963, 0);
		Point p2 = new Point(31560, 34801, 0);
//		DistanceCompare dc1 = new DistanceCompare(31468, 34963, 31560, 34801);
//		DistanceCompare dc1 = new DistanceCompare(31468, 34963, 0, 31560, 34801, 0);
		DistanceCompare dc1 = new DistanceCompare(p1, p2);
		System.out.println(Point.Distance2(q.getX(), q.getY(), 31468, 34963));
		System.out.println(Point.Distance2(q.getX(), q.getY(), 31560, 34801));
		
		/**
		 * fail
		 * */
		dc1.GenerateVeryfyPart(q);
		if(dc1.ClientVerify(q)){ 
			System.out.println("pass!");
		}else{
			System.err.println("fail!");
		}
		
		/**
		 * pass
		 * */
		dc1 = new DistanceCompare(p2, p1);
		dc1.GenerateVeryfyPart(q);
		if(dc1.ClientVerify(q)){
			System.out.println("pass!");
		}else{
			System.err.println("fail!");
		}
		
		/**
		 * 
		 * fail
		 * */
		p1 = new Point(1, 2, 6);
		p2 = new Point(3, 1, 4);
		q = new Point(4, 4, 0);
		dc1 = new DistanceCompare(p1, p2);
		dc1.GenerateVeryfyPart(q);
		if(dc1.ClientVerify(q)){
			System.out.println("pass!");
		}else{
			System.err.println("fail!");
		}
	}
}
