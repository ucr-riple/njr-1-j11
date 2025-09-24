package utility.geo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import utility.testData;

public class mergeIndexDL {

	static int[] machineList = {40, 42, 43, 44, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68};
	static ArrayList<long[]> testIndexs = new ArrayList<long[]>();
	public static boolean testIntegrity(String type, String filePath){
		File[] files = new File(filePath).listFiles();
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
		HashSet<Long> cc = new HashSet<Long>();
		for(int i = 0; i < machineList.length; i++){
			String fileName = filePath + "/" + type + "." + machineList[i] + ".idx";
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
				}
				pos += l;
				num ++;
				cc.add(id);
			}
			dis.close();
		}
		dos.flush();
		dos.close();
		//System.out.println(cc.size() + " " + cnt);
		runTest(type, filePath);
	}
	
	
	public static void runTest(String type, String filePath) throws IOException{
		RandomAccessFile raf = new RandomAccessFile(filePath + "/" + type + ".dat", "r");
		for(int i = 0; i < testIndexs.size(); i++){
			long [] idx = testIndexs.get(i);
			raf.seek(idx[1]);
			byte[] data = new byte[(int) idx[2]];
			raf.read(data);
			DataOfLine dl = new DataOfLine(data);
			if(dl.getLineId() != idx[0]){
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
	 * type is either NE.dl or CA.dl or GW.dl
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
		}else {
			System.out.println("Complete Index file!");
		}
		mergeFile(type, filePath);
	}

}
