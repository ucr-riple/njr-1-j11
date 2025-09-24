package utility.security;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;

public class DataIO {

	public static String defaultCharSet = "ISO-8859-1";
	
	public static int[] readIntArrays(DataInputStream dis){
		int len;
		int[] a = null;
		try {
			len = dis.readInt();
			a = new int[len];
			for(int i = 0; i < len; i++){
				a[i] = dis.readInt();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}
	
	public static void writeIntArrays(DataOutputStream dos, int[] a){
		try {
			dos.writeInt(a.length);
			for(int v : a){
				dos.writeInt(v);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeIntArrays(DataOutputStream dos, Integer[] a){
		try {
			dos.writeInt(a.length);
			for(int v : a){
				dos.writeInt(v);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeBigInteger(DataOutputStream dos, BigInteger b){
		byte[] data = b.toByteArray();
		try {
			dos.writeInt(data.length);
			dos.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static BigInteger readBigInteger(DataInputStream dis){
		int len;
		byte[] data = null;
		try {
			len = dis.readInt();
			data = new byte[len];
			dis.read(data, 0, len);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new BigInteger(data);
	}
	
	public static void writeString(DataOutputStream dos, String str, String charset){
		try {
			if(str == null || str.length() == 0){
				dos.writeInt(0);
			}
			dos.writeInt(str.length());
			dos.write(str.getBytes(charset));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	public static void writeString(DataOutputStream dos, String str){
		try {
			if(str == null || str.length() == 0){
				dos.writeInt(0);
				return;
			}
			dos.writeInt(str.length());
			dos.write(str.getBytes(defaultCharSet));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	public static String readString(DataInputStream dis){
		int len;
		byte[] data;
		try {
			len = dis.readInt();
			if(len == 0)return null;
			data = new byte[len];
			dis.read(data);
			return new String(data, defaultCharSet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean compareString(String str1, String str2, String charset){
		byte[] data1 = null;
		try {
			data1 = str1.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] data2 = null;
		try {
			data2 = str2.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(data1.length != data2.length)return false;
		for(int i = 0; i < data1.length; i++){
			if(data1[i] != data2[i])return false;
		}
		return true;
	}
	
	/**
	 * In RSA, all values must be non-negative.
	 * */
	public static boolean compareStringInRSA(String str1, String str2){
		try {
			BigInteger b1 = new BigInteger(str1.getBytes(DataIO.defaultCharSet));
			BigInteger b2 = new BigInteger(str2.getBytes(DataIO.defaultCharSet));
			if(b1.signum() == -1)b1 = b1.mod(SecurityUtility.rsa.n);
			if(b2.signum() == -1)b2 = b2.mod(SecurityUtility.rsa.n);
			if(b1.equals(b2)){
				return true;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean compareString(String str1, String str2){
		byte[] data1 = null;
		try {
			data1 = str1.getBytes(defaultCharSet);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] data2 = null;
		try {
			data2 = str2.getBytes(defaultCharSet);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(data1.length != data2.length)return false;
		for(int i = 0; i < data1.length; i++){
			if(data1[i] != data2[i])return false;
		}
		return true;
	}
	
	public static void main(String[] args){
		String str1 = "ćîÖpYÎ@Ř­Î§	ođôç0";
		try {
			if(compareString(str1, new String(str1.getBytes("ISO-8859-1"), "ISO-8859-1"), "ISO-8859-1")){
				System.out.println("right!");
			}else{
				System.err.println("error!");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
