package edu.usc.cs.nsl.counting;

import java.security.*;
import java.util.Arrays;
import java.util.Random;
import java.io.*;
import java.math.BigInteger;

/**
 * The real challenge is to generate an input good enough for this problem.
 * Text files are not good enough as English words are over in 100,000 words.
 * Here we will generate a dictionary by counting numbers and taking hash of them.
 * We can then randomly create additional strings from these hashes by randomly 
 * splitting it. 
 * @author nilmish
 *
 */
public class InputGenerator {

	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 */	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("sha1");
		int end_value = Integer.parseInt(args[0]);
		BufferedWriter bw = new BufferedWriter(new FileWriter(args[1]));
		Random num_splits = new Random();
		Random split_where = new Random();
		for(int i=0; i < end_value; i++){			
			md.update(toBytes(i));
			String str = new BigInteger(1,md.digest()).toString(16);
			bw.write(str+"\n");
			String []hash_words = splitHash(str,num_splits,split_where);
			for(String each_split:hash_words){
				bw.write(each_split+"\n");
			}
		}
		bw.close();
		
	}
	
	private static String[] splitHash(String str, Random num_splits_ran, Random split_where_ran) {
		int num_splits = num_splits_ran.nextInt(5)+1;
		int []split_where = new int[num_splits];
		for(int i =0; i < num_splits; i++){
			split_where[i] = split_where_ran.nextInt(str.length());
		}
		Arrays.sort(split_where);
		String[] splits = new String[num_splits+1];
		int start_index = 0;
		for(int i=0;i < split_where.length; i++){
			splits[i] = str.substring(start_index, split_where[i]);
			start_index = split_where[i];
			if(i == split_where.length -1)
				splits[i+1] = str.substring(start_index, str.length());
		}
		return splits;
	}

	public static byte[] toBytes(int i)
	{
	  byte[] result = new byte[4];

	  result[0] = (byte) (i >> 24);
	  result[1] = (byte) (i >> 16);
	  result[2] = (byte) (i >> 8);
	  result[3] = (byte) (i /*>> 0*/);

	  return result;
	}

}
