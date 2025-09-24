/**
 * 
 */
package utility.Compare;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import utility.geo.DataOfLine;

/**
 * @author chenqian
 *	This program is to merge the index, and also test the index at the same time.
 */
public class mergeIndexDP {
	static int[] machineList = {40, 42, 43, 44, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68};
	static ArrayList<long[]> testIndexs = new ArrayList<long[]>();
	
	public static boolean testIntegrity(String type, String filePath){
		File[] files = new File(filePath).listFiles();
		System.out.println("File Path:\t" + filePath);
		for(int i = 0; i < machineList.length; i ++){
			boolean found = false;
			for(int j = 0; j < files.length; j++){
				if(files[j].getName().equals(type + "." + machineList[i] + ".idx")){
					found = true;
					break;
				}
			}
			if(!found)return false;
		}
		return true;
	}
	
	public static void mergeFile(String type, String filePath) throws IOException{
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(filePath + "/" + type + ".idx")));
		long pos = 0, cnt;
		for(int i = 0; i < machineList.length; i++){
			String fileName = filePath + "/" + type + "." + machineList[i] + ".idx";
			System.out.println(fileName);
			DataInputStream dis = new DataInputStream(new FileInputStream(new File(fileName)));
			long id = 0, p, l, num = 0;
			
			while(dis.available() > 0){
				id = dis.readLong();
				p = dis.readLong();
				l = dis.readLong();
				dos.writeLong(id);
				dos.writeLong(pos);
				dos.writeLong(l);
				if(num == 0){
					testIndexs.add(new long[]{id, pos, l});
					//System.out.print(machineList[testIndexs.size() - 1] + ":\t");
					//runTest(type, filePath, new long[]{id, p, l}, machineList[testIndexs.size() - 1]);
				}
				pos += l;
				num ++;
			}
			dis.close();
		}
		dos.flush();
		dos.close();
		runTest(type, filePath);
	}
	
	public static void runTest(String type, String filePath, long idx[], int machineId) throws IOException{
		RandomAccessFile raf = new RandomAccessFile(filePath + "/" + type + "." + machineId + ".dat", "r");
		raf.seek(idx[1]);
		byte[] data = new byte[(int) idx[2]];
		raf.read(data);
		DataOfPoint dp = new DataOfPoint(data);
		if(dp.getPointId() != idx[0]){
			System.out.println("Fail");
		}else{
			System.out.println("Pass");
		}
		raf.close();
	}
	
	public static void runTest(String type, String filePath) throws IOException{
		RandomAccessFile raf = new RandomAccessFile(filePath + "/" + type + ".dat", "r");
		System.out.println(filePath + "/" + type + ".dat");
		for(int i = 0; i < testIndexs.size(); i++){
			long [] idx = testIndexs.get(i);
			raf.seek(idx[1]);
			byte[] data = new byte[(int) idx[2]];
			raf.read(data);
			DataOfPoint dp = new DataOfPoint(data);
			if(dp.getPointId() != idx[0]){
				System.out.println(idx[0] + "\t" + idx[1] + "\t" + idx[2]);
				System.out.println(dp.getPointId());
				System.out.println("Fail in csr" + machineList[i]);
			}else{
				System.out.println("Pass in csr" + machineList[i]);
			}
		}
		raf.close();
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * 
	 * type is either NE.dp or CA.dp or GW.dp
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath = "", type = "";
		if(args.length > 0){
			filePath = args[0];
			type = args[1];
		}else{
			Scanner in = new Scanner(System.in);
			System.out.println("Please input file path");
			filePath = in.nextLine();
			System.out.println("Please input your type");
			type = in.nextLine();
		}
		if(testIntegrity(type, filePath) == false){
			System.out.println("Lack file!");
		}else{
			System.out.println("Complete index file!");
		}
		mergeFile(type, filePath);
	}

}
