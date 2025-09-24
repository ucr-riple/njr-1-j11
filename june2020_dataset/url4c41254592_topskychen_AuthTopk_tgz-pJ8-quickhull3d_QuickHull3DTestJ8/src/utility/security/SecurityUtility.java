package utility.security;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;

import spatialindex.core.IVisitor;
import spatialindex.core.Region;
import spatialindex.core.VOErrorException;
import utility.security.Gfunction;
import utility.security.RSA;

/**
 * provide some security function
 * 
 * @author chenqian
 */
public class SecurityUtility {

	private static final double SCALE = 1000000;
	private static double U = 1d, L = 0d;
	private static BigInteger PRIME_P = BigIntegerUtility.PRIME_P;
	private static BigInteger PRIME_Q = BigIntegerUtility.PRIME_Q;
	public final static BigInteger N = PRIME_P.multiply(PRIME_Q);
	public static String U_HASHVALUE, L_HASHVALUE;
	public static RSA rsa = new RSA(1024);
	private static int COUNTER = 0;
	private static boolean debug = false;
	
	static {
		try {
			U_HASHVALUE = Hasher.hashString(U + "");
			L_HASHVALUE = Hasher.hashString(L + "");
			rsa.initKey();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	public static void main(String[] args) throws IndexOutOfBoundsException, Exception {
		Region cell = new Region(new double[] {0.312769, 0.312769}, new double[] {0.449261, 0.449262});
		Region qr = new Region(new double[] {0.34, 0.4}, new double[] { 0.340312, 0.400312  });
		Region qr2 = new Region(new double[] {0.303410, 0.212779}, new double[] {0.513411, 0.512790});
		Region qr3 = new Region(new double[] {0.2, 0.2}, new double[] {0.3, 0.3});
		mbrGfunction mbrgf = new mbrGfunction();
		String g1 = null, g2 = null;
		System.out.println("GValue:" + (g1 = computeGValue(mbrgf, cell)));
		String[][] gComponent = computeEssentialGValueComponents(mbrgf, cell, IVisitor.TYPE_INTERSECT, qr);
		System.out.println("Recovered GValue:" + (g2 = computeAndVerifyGValue(IVisitor.TYPE_INTERSECT, gComponent, qr)));
		if(DataIO.compareString(g1, g2, "ISO-8859-1") == false){
			if(qr.intersects(cell)){
				System.err.println("Actually they intersected!");
			}
			System.err.println("Fail to verify Intersection!");
		}else{
			System.out.println("Succeed to verify Intersection!");
		}
		System.out.println("GValue:" + (g1 = computeGValue(mbrgf, cell)));
		gComponent = computeEssentialGValueComponents(mbrgf, cell, IVisitor.TYPE_INSIDE, qr2);
		System.out.println("Recovered GValue:" + (g2 = computeAndVerifyGValue(IVisitor.TYPE_INSIDE, gComponent, qr2)));
		if(DataIO.compareString(g1, g2, "ISO-8859-1") == false){
			System.err.println("Fail to verify Inside!");
		}else{
			System.out.println("Succeed to verify Inside!");
		}
		System.out.println("GValue:" + (g1 = computeGValue(mbrgf, cell)));
		gComponent = computeEssentialGValueComponents(mbrgf, cell, IVisitor.TYPE_OUTSIDE, qr3);
		System.out.println("Recovered GValue:" + (g2 = computeAndVerifyGValue(IVisitor.TYPE_OUTSIDE, gComponent, qr3)));
		if(DataIO.compareString(g1, g2, "ISO-8859-1") == false){
			System.err.println("Fail to verify Outside!");
		}else{
			System.out.println("Succeed to verify Outside!");
		}

//		String s1 = SecurityUtility.computeHashValue(new String[]{"1", "2"});
		String s1 = Hasher.hashString("173205402497660143052521920502136922837355899010513938201054307241231231231231");
		String s2 = SecurityUtility.signWithRSA(s1);
		String s3 = SecurityUtility.deSignWithRSA(s2);
				
		if(DataIO.compareStringInRSA(s1, s3) == false){
//			System.out.println("s1" + s1);
//			System.out.println("s3" + s3);
			System.err.println("err!");
		}else{
			System.out.println("Right!");
		}
	}

	public static int getCount() {
		return COUNTER;
	}

	public static void resetCount() {
		COUNTER = 0;
	}

	/**
	 * @param mbr
	 * @return hash(4*d components) of gValue, arrange as
	 *         d*f(U-low),d*f(low-L),d*f(U-high),d*f(high-L)
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	public static String computeGValue(mbrGfunction mbrgf, Region mbr) throws IndexOutOfBoundsException, Exception {
		
		if(mbrgf.gfs == null){
			mbrgf.setGf(computeGValueComponents(mbr));
		}
		return computeHashValue(mbrgf.gfs);
	}

	/**
	 * @param mbr
	 * @return 4*d components of gValue, arrange as
	 *         d*f(U-low),d*f(low-L),d*f(U-high),d*f(high-L)
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	public static Gfunction[][] computeGValueComponents(Region mbr) throws IndexOutOfBoundsException, Exception {
		int dimension = (int) mbr.getDimension();
		Gfunction[][] result = new Gfunction[2][dimension];
		for (int i = 0; i < dimension; i++) {
			result[0][i] = computeFunctionG(mbr.getLow(i));
			result[1][i] = computeFunctionG(mbr.getHigh(i));
		}
		return result;
	}


	/**
	 * get the value to prove the relationship
	 * @param gf Gfunction
	 * @param i value of querybound
	 *            the relationship between query and node, see IVisitor.TYPE
	 * @return String[]
	 * @throws Exception 
	 */
	public static String[] computeEssentialGValueComponents(Gfunction gf, int i, boolean isL) throws Exception {
		return gf.GenerateVeryfyPart(i, isL);
	}

	/**
	 * get the value to prove the relationship
	 * @param gf Gfunction
	 * @param i value of querybound
	 *            the relationship between query and node, see IVisitor.TYPE
	 * @return String[]
	 * @throws Exception 
	 */
	public static String[] computeEssentialGValueComponents(Gfunction gf, double i, boolean isL) throws Exception {
		int d = (int) (Math.round(i * SCALE));
		return gf.GenerateVeryfyPart(d, isL);
	}

	/**
	 * get the value to prove the relationship
	 * 
	 * @param type
	 *            the relationship between query and node, see IVisitor.TYPE
	 * @throws Exception 
	 */
	public static String[] computeEssentialGValueComponents(Gfunction gf, long i, boolean isL) throws Exception {
		return gf.GenerateVeryfyPart(i, isL);
	}

	/**
	 * get the value to prove the relationship
	 * @param mbr
	 * @param type
	 *            the relationship between query and node, means
	 *            queryaRegion.getRelationCode(MBR), see IVisitor.TYPE
	 * @param queryRegion
	 * 
	 * @return the 4 or 5-*dimension key value of G(), the 5th array is
	 *         complement of NaN
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	public static String[][] computeEssentialGValueComponents(mbrGfunction mbrgf, Region mbr, int type, Region queryRegion) throws IndexOutOfBoundsException, Exception {
		int dimension = (int) mbr.getDimension();
		if(type == IVisitor.TYPE_INSIDE){
			String[][] result = new String[2 * dimension][];
			for (int i = 0; i < dimension; i++) {
				result[i] = computeEssentialGValueComponents(mbrgf.gfs[0][i], queryRegion.getLow(i), true);
				result[dimension + i] = computeEssentialGValueComponents(mbrgf.gfs[1][i], queryRegion.getHigh(i), false);
			}
			return result;
		}else if(type == IVisitor.TYPE_INTERSECT){
			String[][] result = new String[2 * dimension][];
			for (int i = 0; i < dimension; i++) {
				result[i] = computeEssentialGValueComponents(mbrgf.gfs[0][i], queryRegion.getHigh(i), false);
//				System.out.println("0 " + i);
//				System.out.println(combineFunctionG(result[i], U - queryRegion.getHigh(i)));
//				System.out.println(mbrgf.gfs[0][i].HashCode());
				result[dimension + i] = computeEssentialGValueComponents(mbrgf.gfs[1][i], queryRegion.getLow(i), true);
//				System.out.println("1 " + i);
//				System.out.println(combineFunctionG(result[dimension + i], queryRegion.getLow(i) - L));
//				System.out.println(mbrgf.gfs[1][i].HashCode());
			}
			return result;
		}else{
			boolean ok = false;
			int id = -1;
			String[][] result = new String[3 * dimension][];
			for(int i = 0; i < dimension; i++){
				result[i] = new String[] {mbrgf.gfs[0][i].getDigest()};
				result[dimension + i] = new String[] {mbrgf.gfs[1][i].getDigest()};
				if(!ok && mbr.getLow(i) >= queryRegion.getHigh(i)){
					id = i;
					ok = true;
					result[i] = computeEssentialGValueComponents(mbrgf.gfs[0][i], queryRegion.getHigh(i), true);
					result[dimension + i] = new String[] {mbrgf.gfs[1][i].getDigest()};
				}
				if(!ok && mbr.getHigh(i) <= queryRegion.getLow(i)){
					id = dimension + i;
					ok = true;
					result[i] = new String[] {mbrgf.gfs[0][i].getDigest()};
					result[dimension + i] = computeEssentialGValueComponents(mbrgf.gfs[1][i], queryRegion.getLow(i), false);
				}
			}
			if(!ok)throw new Exception("No verification found!");
			result[2 * dimension] = new String[] {Integer.valueOf(id).toString()};
			return result;
		}
	}

	/**
	 * get the value to prove the relationship
	 * @param mbr
	 * @param type
	 *            the relationship between query and node, means
	 *            queryaRegion.getRelationCode(MBR), see IVisitor.TYPE
	 * @param queryRegion
	 * 
	 * @return the 4 or 5-*dimension key value of G(), the 5th array is
	 *         complement of NaN
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	public static String[][] computeEssentialGValueComponents(mbrGfunction mbrgf, Region mbr, int type, Region queryRegion, int maxOmitLength) throws IndexOutOfBoundsException, Exception {
		int dimension = (int) mbr.getDimension();
		if(type == IVisitor.TYPE_INSIDE){
			String[][] result = new String[2 * dimension][];
			for (int i = maxOmitLength; i < dimension; i++) {
				result[i] = computeEssentialGValueComponents(mbrgf.gfs[0][i], queryRegion.getLow(i), true);
				result[dimension + i] = computeEssentialGValueComponents(mbrgf.gfs[1][i], queryRegion.getHigh(i), false);
			}
			return result;
		}else if(type == IVisitor.TYPE_INTERSECT){
			String[][] result = new String[2 * dimension][];
			for (int i = maxOmitLength; i < dimension; i++) {
				result[i] = computeEssentialGValueComponents(mbrgf.gfs[0][i], queryRegion.getHigh(i), false);
//				System.out.println("0 " + i);
//				System.out.println(combineFunctionG(result[i], U - queryRegion.getHigh(i)));
//				System.out.println(mbrgf.gfs[0][i].HashCode());
				result[dimension + i] = computeEssentialGValueComponents(mbrgf.gfs[1][i], queryRegion.getLow(i), true);
//				System.out.println("1 " + i);
//				System.out.println(combineFunctionG(result[dimension + i], queryRegion.getLow(i) - L));
//				System.out.println(mbrgf.gfs[1][i].HashCode());
			}
			return result;
		}else{
			boolean ok = false;
			int id = -1;
			String[][] result = new String[3 * dimension][];
			for(int i = maxOmitLength; i < dimension; i++){
				result[i] = new String[] {mbrgf.gfs[0][i].getDigest()};
				result[dimension + i] = new String[] {mbrgf.gfs[1][i].getDigest()};
				if(!ok && mbr.getLow(i) >= queryRegion.getHigh(i)){
					id = i;
					ok = true;
					result[i] = computeEssentialGValueComponents(mbrgf.gfs[0][i], queryRegion.getHigh(i), true);
					result[dimension + i] = new String[] {mbrgf.gfs[1][i].getDigest()};
				}
				if(!ok && mbr.getHigh(i) <= queryRegion.getLow(i)){
					id = dimension + i;
					ok = true;
					result[i] = new String[] {mbrgf.gfs[0][i].getDigest()};
					result[dimension + i] = computeEssentialGValueComponents(mbrgf.gfs[1][i], queryRegion.getLow(i), false);
				}
			}
			if(!ok)throw new Exception("No verification found!");
			result[2 * dimension] = new String[] {Integer.valueOf(id).toString()};
			return result;
		}
	}

	
	/**
	 * compute the original gValue from gValueComponents
	 * 
	 * @param type
	 * @param gValueComponents
	 * @param r
	 *            query region
	 * @return when the verification occurred error, then return
	 *         Integer.MIN_VALUE;
	 * @throws IndexOutOfBoundsException 
	 * @throws Exception
	 */
	public static String computeAndVerifyGValue(int type, String[][] gValueComponents, Region queryRegion) throws IndexOutOfBoundsException, Exception {
		int dimension = (int) queryRegion.getDimension();
		String[] conponents = new String[2 * dimension];
		if (type == IVisitor.TYPE_INSIDE) {
			if (gValueComponents.length != 2 * dimension) {
				throw new VOErrorException("Error in verification, wrong gValueComponents");
			}
			for (int i = 0; i < dimension; i++) {
				conponents[i] = combineFunctionG(gValueComponents[i], queryRegion.getLow(i) - L);
				conponents[dimension + i] = combineFunctionG(gValueComponents[dimension + i], U - queryRegion.getHigh(i));
 			}
			return computeHashValue(conponents);
		} else if (type == IVisitor.TYPE_INTERSECT) {
			if (gValueComponents.length != 2 * dimension) {
				throw new VOErrorException("Error in verification, wrong gValueComponents");
			}
			for (int i = 0; i < dimension; i++) {
				conponents[i] = combineFunctionG(gValueComponents[i], U - queryRegion.getHigh(i));
				conponents[dimension + i] = combineFunctionG(gValueComponents[dimension + i], queryRegion.getLow(i) - L);
			}
			return computeHashValue(conponents);
		} else if (type == IVisitor.TYPE_OUTSIDE) {
			if (gValueComponents.length != 3 * dimension) {
				throw new VOErrorException("Error in verification, wrong gValueComponents");
			}
			int id = Integer.parseInt(gValueComponents[2 * dimension][0]);
			for (int i = 0; i < dimension; i++) {
				if(i == id){
					conponents[i] = combineFunctionG(queryRegion.getHigh(i) - L, gValueComponents[i]);
					conponents[dimension + i] = gValueComponents[dimension + i][0];
				}else if(i + dimension == id){
					conponents[i] = gValueComponents[i][0];
					conponents[dimension + i] = combineFunctionG(U - queryRegion.getLow(i), gValueComponents[dimension + i]);
				}else{
					conponents[i] = gValueComponents[i][0];
					conponents[dimension + i] = gValueComponents[dimension + i][0];
				}
			}
			return computeHashValue(conponents);
		} else {
			throw new VOErrorException("Error in verification, wrong gValueComponents");
		}
	}

	/**
	 * G() is a additive function and has no definition when input<0
	 * 
	 * @param gf
	 * @param input
	 * @return Gfunction
	 * @throws Exception 
	 */
	public static Gfunction computeFunctionG(double input) throws Exception {
		int d = (int) (Math.round(input * SCALE));
		return computeFunctionG(d);
	}

	/**
	 * G() is a additive function, now return ServerReturned
	 * 
	 * @param x
	 * @return Gfunction
	 * @throws Exception 
	 */
	public static Gfunction computeFunctionG(int x) throws Exception {
		Gfunction gf = new Gfunction(x, 16, 0, (int)SCALE);
		return gf;
	}

	/**
	 * compute G(x+y) when given G(x) and (y), F is a additive function
	 * 
	 * @param ServerReturned
	 * @param y
	 * @throws Exception 
	 * 
	 */
	public static String combineFunctionG(String[] ServerReturned, double y) throws Exception {
		int by = (int) (Math.round(y * SCALE));
		//System.out.println("by : " + by);
		if (by < 0) {
			System.out.println("y = " + by + "<0!");
			System.exit(-1);
		}
		COUNTER++;
		Gfunction gf = new Gfunction(16);
		String ans = gf.ClientComputed(ServerReturned, by);
		if(debug){
			System.out.println("Client computed out : " + ans);
		}
		return ans;
	}

	public static String combineFunctionG(double y, String[] gx) throws Exception {
		return combineFunctionG(gx, y);
	}

	/**
	 * hash them together
	 * 
	 * @param components
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public static String computeHashValue(Gfunction[][] components) throws UnsupportedEncodingException {
		BigInteger tmp = BigIntegerUtility.ONE;
		if (components != null) {
			for (int i = 0; i < components.length; i++) {
				for(int j = 0; j < components[i].length; j++){
					BigInteger tmpBigInteger = new BigInteger(Hasher.hashString(components[i][j].getDigest()).getBytes("ISO-8859-1"));
					tmp = tmp.multiply(tmpBigInteger).mod(N);
				}
			}
		}
		tmp = tmp.multiply(tmp).mod(N);
		String ans = Hasher.hashString(tmp.toString());//toString based on radis 16
		if (debug) {
			System.out.println("compute hash value:" + tmp + "\t =" + ans);
		}
		return ans;
	}
	
	/**
	 * hash them together
	 * 
	 * @param components
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public static String computeHashValue(String[][] components) throws UnsupportedEncodingException {
		BigInteger tmp = BigIntegerUtility.ONE;
		if (components != null) {
			int dim = components.length;
			for (int i = 0; i < dim; i++) {
				for(int j = 0; j < components[i].length; j++){
					BigInteger tmpBigInteger = new BigInteger(Hasher.hashString(components[i][j]).getBytes( "ISO-8859-1"));
					tmp = tmp.multiply(tmpBigInteger).mod(N);
				}
			}
		}
		tmp = tmp.multiply(tmp).mod(N);
		String ans = Hasher.hashString(tmp.toString());//toString based on radis 16
		if (debug) {
			System.out.println("compute hash value:" + tmp + "\t =" + ans);
		}
		return ans;
	}
	
	/**
	 * hash them together
	 * 
	 * @param components
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public static String computeHashValue(String[] components) throws UnsupportedEncodingException {
		BigInteger tmp = BigIntegerUtility.ONE;
		if (components != null) {
			for (int i = 0; i < components.length; i++) {
				BigInteger tmpBigInteger = new BigInteger(Hasher.hashString(components[i]).getBytes("ISO-8859-1"));
				tmp = tmp.multiply(tmpBigInteger).mod(N);
			}
		}
		tmp = tmp.multiply(tmp).mod(N);
//		System.out.println(tmp);
		String ans = Hasher.hashString(tmp.toString());
		if (debug) {
			System.out.println("compute hash value:" + tmp + "\t =" + ans);
		}
		return ans;
	}

	/**
	 * hash them together
	 * 
	 * @param components
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public static String computeHashValue(BigInteger[] components) throws UnsupportedEncodingException {
		BigInteger tmp = BigIntegerUtility.ONE;
		if (components != null) {
			for (int i = 0; i < components.length; i++) {
				tmp = tmp.multiply(components[i]).mod(N);
			}
		}
		tmp = tmp.multiply(tmp).mod(N);
		String ans = Hasher.hashString(tmp.toString());//toString based on radis 16
		if (debug) {
			System.out.println("compute hash value:" + tmp + "\t =" + ans);
		}
		return ans;
	}
	
	/**
	 * hash them together with | separated
	 * @param components long[]
	 * @return String
	 * */
	public static String computeGeneralHashValue(Long[] components){
		StringBuffer str = new StringBuffer();
		if(components != null){
			for(int i = 0; i < components.length ; i++){
				if(i != 0)str.append("|");
				str.append(components[i]);
			}
		}
		String ans = Hasher.hashString(str.toString());
		return ans;
	}
	
	/**
	 * hash them together with | separated
	 * @param components long[]
	 * @return String
	 * */
	public static String computeGeneralHashValue(long[] components){
		StringBuffer str = new StringBuffer();
		if(components != null){
			for(int i = 0; i < components.length ; i++){
				if(i != 0)str.append("|");
				str.append(components[i]);
			}
		}
		String ans = Hasher.hashString(str.toString());
		return ans;
	}
	
	/**
	 * hash them together with | separated
	 * @param components int[]
	 * @return String
	 * */
	public static String computeGeneralHashValue(int[] components){
		StringBuffer str = new StringBuffer();
		if(components != null){
			for(int i = 0; i < components.length ; i++){
				if(i != 0)str.append("|");
				str.append(components[i]);
			}
		}
		String ans = Hasher.hashString(str.toString());
		return ans;
	}
	
	/**
	 * hash them together with | separated
	 * @param components String[]
	 * @return String
	 * */
	public static String computeGeneralHashValue(String[] components){
		StringBuffer str = new StringBuffer();
		if(components != null){
			for(int i = 0; i < components.length ; i++){
				if(i != 0)str.append("|");
				str.append(components[i]);
			}
		}
		String ans = Hasher.hashString(str.toString());
		return ans;
	}
	/**
	 * use RSA to sign string
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String signWithRSA(String str) throws UnsupportedEncodingException {
		return rsa.encrypt(str);
	}
	
	public static String deSignWithRSA(String str) throws UnsupportedEncodingException{
		return rsa.decrypt(str);
	}
	
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
