package utility;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashSet;

public class DataFormater {
	public static boolean ISCAR = true;
	public static long SCALE = 100000;
	public static int DATASET_SIZE = 3000000;
	public static String input_file_name = "source/NE/NE.txt";
	public static String output_file_name = "source/NE.dat";
	
	public static void genCa(){
		try{
			String line = null;
			LineNumberReader lr = new LineNumberReader(new FileReader(input_file_name));
			PrintWriter pw = new PrintWriter(new FileWriter(output_file_name));
			HashSet<String> cnt = new HashSet<String>();
			try {
				int line_num = 0;
				while((line = lr.readLine()) != null){
					
					String[] num = line.split(" ");
					float[] point = new float[2];
					point[0] = Float.parseFloat(num[2]);
					point[1] = Float.parseFloat(num[3]);
					if(ISCAR){
						point[0] = (int)(point[0] * SCALE);
						point[1] = (int)(point[1] * SCALE);
					}
					String sid = new Integer(Integer.parseInt(num[1]) + 1).toString();
					String n1 = new Integer((int)point[0]).toString();
					String n2 = new Integer((int)point[1]).toString();
					if(cnt.contains(n1 + " " + n2)){
						continue;
					}else{
						line_num ++;
					}
					cnt.add(n1 + " " + n2);
					pw.println(num[0] + " " + line_num + " " + n1 + " " + n2 + " " + n1 + " " + n2);
					//if(DEBUG) System.err.println();
					//if(line_num >= DATASET_SIZE)break;
				}
				lr.close();
				pw.flush();
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("data format fin!");
	}
	
	public static void genNE(){
		try{
			String line = null;
			LineNumberReader lr = new LineNumberReader(new FileReader(input_file_name));
			PrintWriter pw = new PrintWriter(new FileWriter(output_file_name));
			HashSet<String> cnt = new HashSet<String>();
			try {
				int line_num = 0;
				while((line = lr.readLine()) != null){
					
					String[] num = line.split(" ");
					float[] point = new float[2];
					point[0] = Float.parseFloat(num[0]);
					point[1] = Float.parseFloat(num[1]);
					if(ISCAR){
						point[0] = (int)(point[0] * SCALE);
						point[1] = (int)(point[1] * SCALE);
					}
					String n1 = new Integer((int)point[0]).toString();
					String n2 = new Integer((int)point[1]).toString();
					if(cnt.contains(n1 + " " + n2)){
						continue;
					}else{
						line_num ++;
					}
					cnt.add(n1 + " " + n2);
					pw.println("1" + " " + line_num + " " + n1 + " " + n2 + " " + n1 + " " + n2);
					//if(DEBUG) System.err.println();
					//if(line_num >= DATASET_SIZE)break;
				}
				lr.close();
				pw.flush();
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("data format fin!");
	}
	public static void main(String[] args){
		genNE();
	}

}
