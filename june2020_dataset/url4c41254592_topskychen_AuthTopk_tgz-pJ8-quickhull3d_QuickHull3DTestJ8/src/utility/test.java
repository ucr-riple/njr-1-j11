package utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import utility.Compare.DistanceCompare;
import utility.geo.Line;
import utility.security.Hasher;
import utility.security.Paillier;
import utility.security.Point;
import utility.security.RSA;

import mesh.Delaunay;
import mesh.MPolygon;
import mesh.Voronoi;


public class test {
	
	public static Paillier paillier = new Paillier(true);
	public static RSA rsa = new RSA();
	
	
	public static void printarray(float[][] a){
		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < a[i].length; j++){
				System.out.print(a[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	
	public static void testTime(){
		System.out.println("hello");
		try{
			ThreadMXBean bean = ManagementFactory.getThreadMXBean();
			long start, end;
			double totalCpuTime;
			start = bean.getCurrentThreadCpuTime();
			
			BigInteger res = paillier.Encryption(new BigInteger("1"));
			
			end = bean.getCurrentThreadCpuTime();
			totalCpuTime = (end - start) / 1000000.0;
			System.out.println("Total time of cpu:\t" + totalCpuTime);
			System.out.println("======================================");
			
			start = bean.getCurrentThreadCpuTime();
			
			paillier.Decryption(res);
			
			end = bean.getCurrentThreadCpuTime();
			totalCpuTime = (end - start) / 1000000.0;
			System.out.println("Total time of cpu:\t" + totalCpuTime);
			System.out.println("======================================");
			
			start = bean.getCurrentThreadCpuTime();
			
			BigInteger res2 = rsa.encrypt(res);
			
			end = bean.getCurrentThreadCpuTime();
			totalCpuTime = (end - start) / 1000000.0;
			System.out.println("Total time of cpu:\t" + totalCpuTime);
			System.out.println("======================================");
			
			start = bean.getCurrentThreadCpuTime();
			
			BigInteger res3 = rsa.decrypt(res2);
			if(res3.equals(res2)){
				System.out.println("no!!");
			}
			
			end = bean.getCurrentThreadCpuTime();
			totalCpuTime = (end - start) / 1000000.0;
			System.out.println("Total time of cpu:\t" + totalCpuTime);
			System.out.println("======================================");
			
			start = bean.getCurrentThreadCpuTime();
			
			res2.modPow(Point.a, paillier.nsquare);
			
			end = bean.getCurrentThreadCpuTime();
			totalCpuTime = (end - start) / 1000000.0;
			System.out.println("Total time of cpu:\t" + totalCpuTime);
			System.out.println("======================================");
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException{
		//testTime();	
		long[] times = {100, 1000, 10000, 100000, 1000000};
		BigInteger a = BigInteger.probablePrime(1024, new Random());
		BigInteger b = BigInteger.probablePrime(1024, new Random());
		BigInteger n = BigInteger.probablePrime(1024, new Random());
		for(int j = 0 ; j < 5; j ++){
			System.out.println("================" + times[j] + "===============");
			long start = System.currentTimeMillis();
			BigInteger c = a;
			for(int i = 0; i < times[j]; i ++){
				c = c.multiply(b).mod(n);
			}
			long end = System.currentTimeMillis();
			System.out.println("mul Time consume:" + 1000.0 * (end - start) / times[j]  + " us");
			start = System.currentTimeMillis();
			String hash = "this is test";
			for(int i = 0; i < times[j]; i ++){
				hash = Hasher.hashString(hash);
			}
			end = System.currentTimeMillis();
			System.out.println("hash Time consume:" + 1000.0 * (end - start) / times[j]  + " us");
			start = System.currentTimeMillis();
			String mes = "this is test";
			RSA rsa = new RSA(2048);
			for(int i = 0; i < times[j]; i ++){
				mes = rsa.encrypt(mes);
			}
			end = System.currentTimeMillis();
			System.out.println("rsa Time consume:" + 1000.0 * (end - start) / times[j]  + " us");
			start = System.currentTimeMillis();
			for(int i = 0; i < times[j]; i ++){
				c = paillier.Encryption(c);
			}
			end = System.currentTimeMillis();
			System.out.println("pailler enc Time consume:" + (end - start) / times[j]  + " ms");
		}
	}
}
