package edu.usc.cs.nsl.counting;

import java.io.*;
import java.security.*;
import java.math.BigInteger;
import java.nio.*;
import java.util.*;


/**
 * This class does in memory counting for a very large file.
 * @author nilmish
 *
 */
public class CountingUtil {
	
	private HashMap <Integer,Integer>COLLISION_PROCESS_SIZE = null;
	private String tmpDir = null;
	private int max_ram = 0;
	private MessageDigest md = null;
	private int big_array_size = 0;

	
	/**
	 * 
	 * @param tmpDir Location of the directory to store temporary files.
	 * @param max_ram Maximum amount of ram to be used for counting.
	 * @param collision_process_size array with the amount of memory to use for collision processing for each string length size.
	 * @throws NoSuchAlgorithmException
	 */
	public CountingUtil(String tmpDir, int max_ram, HashMap <Integer,Integer> collision_process_size) throws NoSuchAlgorithmException{
		this.tmpDir = tmpDir;
		this.max_ram = max_ram;
		this.md = MessageDigest.getInstance("md5");
		this.COLLISION_PROCESS_SIZE = collision_process_size;
		this.big_array_size = closestPrime(this.max_ram/8);
		
	}
	
	/**
	 * Get the temporary file name for the file with the words with a given maximum size string.
	 * @param the_size size of the max string.
	 * @return
	 */
	
	private String getTmpFileName(int the_size){
		String file_name = "";
		file_name = tmpDir+"/"+the_size+"_tmp.txt";
		return file_name;
	}

	/**
	 * Get the temporary chunk list file name for a given maximum string size. 
	 * @param the_size size of the max string.
	 * @return
	 */
	private String getTmpChunkListFileName(int the_size){
		String file_name = "";
		file_name = tmpDir+"/chunk_list_"+the_size+".txt";
		return file_name;
	}
	
	/**
	 * File name for the file with the indexes with collision.
	 * @param the_size size of the max string.
	 * @return
	 */
	private String getCollisionTmpFileName(int the_size) {
		String file_name = "";
		file_name = tmpDir+"/chunk_collision_"+the_size+".txt";
		return file_name;
	}
	
	/**
	 * Get the md5 hash of the input string.
	 * @param input input string.
	 * @return
	 */
	private long getHash(String input){
		md.reset();
		md.update(input.getBytes());
		byte []result = null;
		result = md.digest();
		ByteBuffer bb = ByteBuffer.wrap(result);
		long the_hash = 0;
		the_hash = bb.getLong();
		return the_hash;
	}

	/**
	 * Function to break the input file into multiple files based on string length.
	 * @param inputFile Name of the input file.
	 * @param size_range array with string length ranges.
	 * @param size_count array to keep a count of number of words of each string length range.
	 * @throws IOException
	 */
	public void breakTheFile(String inputFile, int[] size_range, int[] size_count) throws IOException{
		BufferedWriter bw[] = new BufferedWriter[size_range.length];
		for(int i = 0; i < size_range.length; i++){
			try {
				bw[i] = new BufferedWriter(new FileWriter(getTmpFileName(size_range[i])));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		String the_line = null;
		try {
			the_line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(the_line != null){
			String []tokens = the_line.split("\\b\\[A-Z0-9a-z]+\\b");
			//String []tokens = the_line.split("\\b\\w+\\b");
			for(String each_token:tokens){
				int the_length = each_token.length();
				boolean flag = false;
				for(int i =0; i < size_range[i]; i++){
					if(the_length <= size_range[i]){
						size_count[i] += 1;
						bw[i].write(each_token+"\n");
						flag = true;
						break;
					}
				}
				if(!flag){
					size_count[size_range.length - 1] += 1;
					bw[size_range.length - 1].write(each_token+"\n");
				}
			}
			try {
				the_line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		br.close();
		for(BufferedWriter each_bw: bw){
			each_bw.close();
		}
	}
	
	/**
	 * In order to have good hashing we need a prime number base.
	 * This function tries to find a close enough approximate
	 * prime number to the passed value by reducing the range by 1 % 
	 * each time of the original value till a prime number less
	 * than the passed value is provided.
	 * @param the_value The base of the hash function.
	 * @return the closest prime.
	 */
	
	private int closestPrime(int the_value){
		float depreciation_factor = 0.01f;
		int current_value = (int)(the_value * (1.0f-depreciation_factor));
		while(true){
			BigInteger bi = BigInteger.valueOf((long) current_value);
			int result = bi.nextProbablePrime().intValue();
			if(result <= the_value)
				return result;
			current_value = (int)(current_value * (1-depreciation_factor));
			System.out.println("Stuck here? "+current_value+"\t"+the_value);
		}
	}

	/**
	 * Function to count the words for a given maximum size string in RAM.
	 * It breaks the count array into chunks. Each chunk size takes into 
	 * consideration the maximum ram available.
	 * @param max_string_size maximum string size.
	 * @return
	 * @throws IOException
	 */

	public int countInRAM(int max_string_size) throws IOException{
		int num_of_chunks = 0;
		int chunk_size = closestPrime(max_ram/(max_string_size + 8));
		
		/* We need to read in long as the file may only have a 
		 * single alphabet repeated multiple times which will then 
		 * exceed the INTEGER.MAX_VALUE limit assuming we have a 32 bit JVM.
		 */		
		long[] the_big_array = new long[big_array_size];   		
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(getTmpFileName(max_string_size)));
		String the_line = br.readLine();
		int index = 0;
		int num_lines = 0;
		while(the_line != null){
			index = (int)Math.abs(getHash(the_line.replace("\n", "").replace("\r", ""))%big_array_size);
			the_big_array[index] += 1;
			the_line = br.readLine();
			num_lines++;
		}
		br.close();
		
		int this_chunk_size = 0;
		long number_of_words_in_chunk = 0;
		BufferedWriter bw = new BufferedWriter(new FileWriter(tmpDir+"/chunk_list_"+max_string_size+".txt"));
		bw.write('0');
		for(int i = 0; i < big_array_size; i++){
			if(the_big_array[i] > 0){
				number_of_words_in_chunk += the_big_array[i]; 
				this_chunk_size++;
				if(this_chunk_size == chunk_size - 1){
					this_chunk_size = 0;
					num_of_chunks++;
					bw.write("\t"+(i+1)+"\t"+number_of_words_in_chunk+"\n"+(i+1)); // start of range is inclusive end is exclusive.
					number_of_words_in_chunk = 0;
				}
			}
		}
		num_of_chunks++;
		bw.write("\t"+big_array_size+"\t"+number_of_words_in_chunk+"\n");
		bw.close();
		System.out.println("Max String: "+max_string_size+"\tNumLines: "+num_lines+"\tnum of chunks: "+num_of_chunks);
		return num_of_chunks;
	}
	
	
	/**
	 * Function to process chunks. This function uses hashing to do counting.
	 * Count for words without hash index collision are written to final 
	 * output file. Words with index collision have the indexes written into 
	 * a temporary file.
	 * Note: There are collisions in the RAM counting phase (same big index) 
	 * and during the processing of chunks (this index). Both these cases
	 * need to be handled separately.
	 * 
	 * @param max_string_size
	 * @param num_chunks
	 * @param output_file
	 * @throws IOException
	 */
	
	public void processChunks(int max_string_size, int num_chunks,
			String output_file) throws IOException {
		// The chunks can be loaded in memory and hence they can be counted.
		int chunk_size = closestPrime(max_ram/(max_string_size + 8));
		System.out.println("Big array size: "+big_array_size+"\t chunk size: "+chunk_size);
		BufferedReader chunk_list_file = null;
		chunk_list_file = new BufferedReader(new FileReader(getTmpChunkListFileName(max_string_size)));
		String the_line = chunk_list_file.readLine();		
		int chunk_index[][] = new int[num_chunks][2];
		int index = 0;
		int num_collision = 0;
		while(the_line != null){
			the_line.replace("\n","").replace("\r", "");
			if(the_line.equals("")){
				the_line = chunk_list_file.readLine();
				continue;
			}
			String tokens[] = the_line.split("\t");
			if(tokens[0] != null && tokens[1] != null){
				chunk_index[index][0] = Integer.parseInt(tokens[0]);
				chunk_index[index][1] = Integer.parseInt(tokens[1]);
			}
			index++;
			the_line = chunk_list_file.readLine();
		}
		chunk_list_file.close();
		
		BufferedWriter bw_collision = new BufferedWriter(new FileWriter(getCollisionTmpFileName(max_string_size),true));

		for (int this_chunk_index = 0; this_chunk_index < chunk_index.length; this_chunk_index++) {			
			String word_array[] = new String[chunk_size];
			long count_array[] = new long[chunk_size];
			int start_index = chunk_index[this_chunk_index][0];
			int end_index = chunk_index[this_chunk_index][1];
			BufferedReader br = null;
			br = new BufferedReader(new FileReader(getTmpFileName(max_string_size)));
			the_line = br.readLine();
			int processed_this_chunk = 0;
			while(the_line != null){
				String the_word = the_line.replace("\n", "").replace("\r", "");
				boolean the_flag = false;
				long the_hash = getHash(the_word);
				int big_index = (int)Math.abs(the_hash%big_array_size);
				long index_hash = getHash(""+big_index);
				int this_index = (int)Math.abs(index_hash%chunk_size);
				if(this_index == 154930)
					System.out.println(the_word);
				if(big_index >= start_index && big_index < end_index){
					processed_this_chunk++;
					if(the_word.equals("0017d7d14cbaf2c1c94de80ef039")){
						the_flag = true;
						System.out.println("Indexes "+big_index+"\t"+this_index);
					}
					if(count_array[this_index] == 0){ // new word
						word_array[this_index] = the_word;
						count_array[this_index] = 1;
						if(the_flag)
							System.out.println("in 0");
					}else{
						if(word_array[this_index].equals(the_word)){ //word seen increment
							if(count_array[this_index] > 0)
								count_array[this_index] += 1;
							else if(count_array[this_index] != Long.MIN_VALUE){
								count_array[this_index] -= 1;
							}
							if(the_flag)
								System.out.println("in equals");
						} else{ // collision (True collision)
							if(the_flag)
								System.out.println("in collision");
							int the_other_big_index = (int)Math.abs(getHash(word_array[this_index])%big_array_size);
							if(the_other_big_index == big_index){
								count_array[this_index] = Long.MIN_VALUE;
								num_collision += 2;
								bw_collision.write(big_index+"\n");
								bw_collision.flush();
							}else {
								if(count_array[this_index] > 0){
									if(the_flag)
										System.out.println("in collision: initial");
									count_array[this_index] = -1*count_array[this_index];
									num_collision += 2;
									bw_collision.write(big_index+"\n");
									bw_collision.flush();
								}
								else{
									if(the_flag)
										System.out.println("in collision: additional "+big_index+"\t"+this_index);
									bw_collision.write(big_index+"\n");
									bw_collision.flush();
									num_collision++;
									//System.out.println(the_word+" Do I come here? "+count_array[this_index]);
								}
							}
						}
					}
				}
				the_line = br.readLine();
			}
			int written_this_chunk = 0;
			BufferedWriter bw_output = new BufferedWriter(new FileWriter(output_file,true));
			for(int i=0; i < chunk_size; i++){
				if(count_array[i] > 0){
					bw_output.write(word_array[i]+"\t"+count_array[i]+"\n");
					written_this_chunk++;
				}else if(count_array[i] < 0){
					if(count_array[i] != Long.MIN_VALUE){
						bw_output.write(word_array[i]+"\t"+(-1*count_array[i])+"\n");
						written_this_chunk++;
					}
				}
			}
			System.out.println("Processed this chunk: "+processed_this_chunk+"\tWritten this chunk: "+written_this_chunk+"\t Collisions: "+num_collision);
			bw_output.close();
		}
		bw_collision.close();
		processCollision(max_string_size,output_file,num_collision,0,num_collision);
		//processCollisionSimple(max_string_size,output_file,num_collision);
	}
	
	/**
	 * Simple counting function (not used)
	 * @param max_string_size
	 * @param output_file
	 * @param num_collisions
	 * @throws IOException
	 */
	private void processCollisionSimple(int max_string_size,String output_file,int num_collisions) throws IOException{
		BufferedWriter bw_output = new BufferedWriter(new FileWriter(output_file,true));
		HashSet<Integer> index_set = new HashSet<Integer>(num_collisions+10,1);
		BufferedReader br_collision = new BufferedReader(new FileReader(getCollisionTmpFileName(max_string_size)));
		String the_line = br_collision.readLine();
		while(the_line != null){			
			index_set.add(Integer.parseInt(the_line.replace("\n", "").replace("\r","")));			
			the_line = br_collision.readLine();
		}
		br_collision.close();
		BufferedReader br;
		HashMap<String,Long> string_map = new HashMap<String,Long>(num_collisions/10);
		br = new BufferedReader(new FileReader(getTmpFileName(max_string_size)));
		the_line = br.readLine();
		int the_count = 0;
		while(the_line != null){
			String the_word = the_line.replace("\n","").replace("\r", "");
			if(the_word.equals("0017d7d14cbaf2c1c94de80ef039")){
				System.out.println("is read");
			}
			int big_index = (int) Math.abs(getHash(the_word)%big_array_size);
			if(index_set.contains(big_index)){
				the_count += 1;
				if(the_word.equals("0017d7d14cbaf2c1c94de80ef039")){
					System.out.println("and is added to map");
				}
				if(string_map.containsKey(the_word)){
					long value = string_map.get(the_word);
					string_map.put(the_word, value+1);
				}else{
					string_map.put(the_word, 1L);
				}
			}
			the_line = br.readLine();
		}
		for(String a_word: string_map.keySet()){
			bw_output.write(a_word+"\t"+string_map.get(a_word)+"\n");
		}
		System.out.println("The count: "+the_count);
		bw_output.close();		
	}
	
	
	/**
	 * Function to process collisions. Assumption is collisions are small.
	 * @param max_string_size
	 * @param output_file
	 * @param num_collisions
	 * @param start_index
	 * @param end_index
	 * @throws IOException
	 */


	private void processCollision(int max_string_size,String output_file,int num_collisions,int start_index,int end_index) throws IOException{
		if(num_collisions == 0)
			return;
		if(start_index >= end_index)
			return;
		int cps = COLLISION_PROCESS_SIZE.get(max_string_size).intValue();
		if(end_index - start_index <= cps){
			HashMap<String,Integer> string_map = new HashMap<String,Integer>(num_collisions/10);
			HashSet<Integer> index_set = new HashSet<Integer>(num_collisions+10,1);
			BufferedReader br_collision = new BufferedReader(new FileReader(getCollisionTmpFileName(max_string_size)));
			String the_line = br_collision.readLine();
			int i = 0;
			while(the_line != null){
				if(i < start_index){
					i++;
					the_line = br_collision.readLine();
					continue;
				}if(i >= end_index){
					break;
				}
				index_set.add(Integer.parseInt(the_line.replace("\n","").replace("\r", "")));
				the_line = br_collision.readLine();
				i++;
				//System.out.println("and here 2?");
			}
			br_collision.close();
			
			BufferedReader br;
			br = new BufferedReader(new FileReader(getTmpFileName(max_string_size)));
			the_line = br.readLine();
			while(the_line != null){
				String the_word = the_line.replace("\n","").replace("\r", "");
				long the_hash = getHash(the_word);
				int big_index = (int)Math.abs(the_hash%big_array_size);
				if(index_set.contains(big_index)){
					if(string_map.containsKey(the_word)){
						int value = string_map.get(the_word);
						int new_value = value + 1;
						string_map.put(the_word, new_value);
					} else{
						string_map.put(the_word, 1);
					}
				}
				the_line = br.readLine();
			}
			br.close();
			BufferedWriter bw_output = new BufferedWriter(new FileWriter(output_file,true));
			Iterator<String> it = string_map.keySet().iterator();
			while(it.hasNext()){
				String the_string = it.next();
				bw_output.write(""+the_string+"\t"+string_map.get(the_string)+"\n");
			}
			bw_output.close();						
		} else{
			int rounds = num_collisions/COLLISION_PROCESS_SIZE.get(new Integer(max_string_size)).intValue() + 1;
			int new_num_collisions = COLLISION_PROCESS_SIZE.get(new Integer(max_string_size)).intValue();
			System.out.println("Rounds: "+rounds);
			for(int i = 0; i < rounds; i++){
				int new_start_index = i*new_num_collisions;
				int new_end_index = (i+1)*new_num_collisions;
				if(i == rounds - 1){
					//new_end_index = i*new_num_collisions + (end_index - i*new_num_collisions);
					new_end_index = end_index;
				}
					
				processCollision(max_string_size, output_file, new_num_collisions, new_start_index, new_end_index);
			}
		}
	}

}
