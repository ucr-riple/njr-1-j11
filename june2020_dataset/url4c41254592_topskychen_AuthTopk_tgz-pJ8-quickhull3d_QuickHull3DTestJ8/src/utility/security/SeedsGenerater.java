package utility.security;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class SeedsGenerater {
	public static BigInteger[] seeds, rsa_g_seeds, g_seeds;
	public static Paillier paillier = new Paillier(true);
	final static BigInteger ONE = BigInteger.ONE;
	final static BigInteger TWO = BigInteger.ONE.add(BigInteger.ONE);
	public static RSA rsa = new RSA();
	public static int base = 16; 
	public String filename = "./seeds";
	public SeedsGenerater(){}
	public SeedsGenerater(int bitlength){
		seeds = new BigInteger[bitlength + 1];
		rsa_g_seeds = new BigInteger[bitlength + 1];
		g_seeds = new BigInteger[bitlength + 1];
		seeds[0] = BigIntegerUtility.ZERO;
		g_seeds[0] = paillier.Encryption(seeds[0], BigIntegerUtility.ONE);
		rsa_g_seeds[0] = rsa.encrypt(g_seeds[0]);
		//g_seeds[0] = g_seeds[0].multiply(paillier.r2n).mod(paillier.nsquare);
		BigInteger val = BigIntegerUtility.ONE;
		for(int i = 0; i < bitlength; i++){
			seeds[i + 1] = val;
			g_seeds[i + 1] = paillier.Encryption(val, BigIntegerUtility.ONE);
			rsa_g_seeds[i + 1] = rsa.encrypt(g_seeds[i + 1]);
			//g_seeds[i + 1] = g_seeds[i + 1].multiply(paillier.r2n).mod(paillier.nsquare);
			val = val.multiply(BigInteger.valueOf(base));
		}
		try {
			DataOutputStream seedsdos = new DataOutputStream(new FileOutputStream(filename + ".dat"));
			DataOutputStream gseedsdos = new DataOutputStream(new FileOutputStream(filename + ".gdat"));
			try {
				seedsdos.writeInt(seeds.length);
				seedsdos.writeInt(base);
				for(BigInteger x : seeds){
					DataIO.writeBigInteger(seedsdos, x);
				}
				for(BigInteger x : g_seeds){
					DataIO.writeBigInteger(gseedsdos, x);
				}
				for(BigInteger x : rsa_g_seeds){
					DataIO.writeBigInteger(gseedsdos, x);
				}
				seedsdos.flush();
				seedsdos.close();
				gseedsdos.flush();
				gseedsdos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SeedsGenerater(boolean load){
		if(load == false){
			//SeedsGenerater
			return;
		}
		try {
			DataInputStream seedsdis = new DataInputStream(new FileInputStream(filename + ".dat"));
			DataInputStream gseedsdis = new DataInputStream(new FileInputStream(filename + ".gdat"));
			try {
				int len = seedsdis.readInt();
				base = seedsdis.readInt();
				seeds = new BigInteger[len];
				g_seeds = new BigInteger[len];
				rsa_g_seeds = new BigInteger[len];
				for(int i = 0 ; i < len; i++){
					seeds[i] = DataIO.readBigInteger(seedsdis);
				}
				for(int i = 0 ; i < len; i++){
					g_seeds[i] = DataIO.readBigInteger(gseedsdis);
				}
				for(int i = 0 ; i < len; i++){
					rsa_g_seeds[i] = DataIO.readBigInteger(gseedsdis);					
				}
				seedsdis.close();
				gseedsdis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public BigInteger[] Decompose(BigInteger val){
		ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
		BigInteger one = BigIntegerUtility.ONE;
		for(int i = seeds.length - 1 ; i >= 0; i --){
			if(val.compareTo(seeds[i]) >= 0){
				val = val.subtract(seeds[i]);
				factors.add(g_seeds[i]);
			}else{
				factors.add(one);
			}
		}
		return (BigInteger[])factors.toArray(new BigInteger[0]);
	}
	
	public BigInteger Decompose2(BigInteger val){
//		System.out.println(val);
		BigInteger ans = BigIntegerUtility.ONE;
		if(val.signum() < 0)return ans;
		for(int i = 0 ; i < seeds.length - 1; i ++){
			//if(val.compareTo(seeds[i]) >= 0){
				BigInteger exp = val.mod(BigInteger.valueOf(base));
				ans = ans.multiply(rsa_g_seeds[i + 1].modPow(exp, rsa.n)).mod(rsa.n);
				val = val.divide(BigInteger.valueOf(base));
			//}
		}
//		System.out.println("decrypt:\t" + rsa.decrypt(ans));
		return ans;
	}
	
	public long[] getRepresentation(long val, int len){
		//System.out.println(val);
		long[] ans = new long[len];
		for(int i = 0; i < len; i ++){
				ans[i] = val % base;
				val /= base;
		}
		return ans;
	}
	
	public BigInteger[] getRepresentationBase(int len){
		BigInteger[] ans = new BigInteger[len];
		for(int i = 0; i < len; i ++){
			ans[i] = g_seeds[i + 1];
		}
		return ans;
	}
	
	public static void main(String args[]){
		//SeedsGenerater seed = new SeedsGenerater(1024);
		SeedsGenerater seed = new SeedsGenerater(true);
		System.out.println("seeds' size : " + seed.seeds.length);
		for(int i = 0; i < seed.seeds.length; i++){
			//System.out.println(seed.seeds[i]);
			System.out.println(seed.rsa_g_seeds[i]);
			if(i > 10)break;
		}
		BigInteger tmp = paillier.Encryption(new BigInteger("128"));
		System.out.println(tmp.toByteArray().length);
	}
}
