package edu.usc.cs.nsl.counting;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
public class Counting {

	/**
	 * @param args
	 * Input for the file will be 
	 * 1. The location of the 100GB file.
	 * 2. Location of the temporary folder.
	 * 3. Output file.
	 * 4. max_ram available in bytes
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	
	/* 
	 * 
	 * Let's try to make a program which will work for 4GB RAM as well as increased RAMs or decreased RAMs
	 * 
	 */
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		//
		if(args.length < 4 || args.length > 4){
			System.out.println("Missing parameter");
			System.exit(0);
		}
		String input_file = args[0];
		String tmp_dir = args[1];
		String output_file = args[2];
		int max_ram = Integer.parseInt(args[3]);
		
		//Can be read from a configuration file.
		int size_range[] = {10,20,50,100,200,500,1000}; 
		int size_count[] = new int[size_range.length];
		
		/*
		 * This variable contains the number of words which can be loaded for counting when
		 * we are processing collisions.
		 */
		int collision_processing_size[] = new int[size_range.length];
		
		for(int i = 0; i < size_range.length; i++){
			collision_processing_size[i] = (max_ram-1000)/(size_range[i]+8+4+4); //(We need to store the string+the count and hash table overhead)
		}
		
		
		HashMap <Integer,Integer> cps = new HashMap<Integer,Integer>();
		for(int i = 0; i < size_count.length;i++){
			cps.put(new Integer(size_range[i]), new Integer(collision_processing_size[i]));
		}
		
		
		CountingUtil cu = new CountingUtil(tmp_dir, max_ram, cps);
		// Divide the input file into parts based on string length range.
		//cu.breakTheFile(args[0], size_range, size_count);
		
		/*
		 * Read each string size based part file and use the full RAM as a hash array.
		 * Do the counting in RAM.
		 * Break the RAM into chunk files based on number of items which can safely fit in memory.
		 * For each of this chunk file read the string size based part file and do the counting.
		 * Write the indices for hash collisions and count them later (as we are working on limit). 
		 * After the chunks are completed do it for the hash collision items. They may themselved need to be read partially.
		 */
		
		for(int i = 0; i < size_range.length; i++){
			int num_chunks = cu.countInRAM(size_range[i]);
			cu.processChunks(size_range[i],num_chunks,output_file);
		}
		
		//int num_chunks = cu.countInRAM(size_range[2]);
		//cu.processChunks(size_range[2],num_chunks,output_file);
		
		
	}
	
}
