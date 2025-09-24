package utility.geo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import utility.Compare.DataOfPoint;
import utility.security.DataIO;
import utility.security.Point;
import utility.security.RSA;
import utility.security.SecurityUtility;

public class DataOfLine {

	public Long lid;
	public Line line;
	public static Long M = (long) 100000000;
	public String signature;
	
	public DataOfLine() {
		line = new Line();
		// TODO Auto-generated constructor stub
	}

	public DataOfLine(long _lid, Line _line){
		lid = _lid;
		line = _line;
		signature = signWithRSA(new RSA());
	}
	
	public DataOfLine(byte[] buffer){
		this();
		this.loadFromBytes(buffer);
	}
	
	public byte[] writeToBytes(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			dos.writeLong(lid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		line.writeToFile(dos);
		DataIO.writeString(dos, signature);
		try {
			dos.flush();
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	public void loadFromBytes(byte[] buffer){
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(buffer));
		try {
			lid = dis.readLong();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		line.readFromFile(dis);
		signature = DataIO.readString(dis);
		try {
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadFromFile(DataInputStream dis){
		try {
			lid = dis.readLong();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		line.readFromFile(dis);
	}

	public long getLineId(){
		return lid;
	}
	
	public static long calcLineId(int id1, int id2){
		if(id1 > id2){
			return ((long)id2) * M + id1;
		}
		return ((long)id1) * M + id2;
	}
	
	public static long calcLineId(long id1, long id2){
		if(id1 > id2){
			return ((long)id2) * M + id1;
		}
		return id1 * M + id2;
	}
	
	public String getDigest(){
		return SecurityUtility.computeGeneralHashValue(new String[]{
			new Integer((int)(lid / M)).toString(),
			new Integer((int)(lid % M)).toString(),
			line.getDigest()
		});
	}
	
	public String signWithRSA(RSA rsa){
		return rsa.encrypt(getDigest());
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Line l = new Line(new int[]{3, 1, 4}, new int[]{6, 1, 5});
		DataOfLine dataOfLine = new DataOfLine(1, l);
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(new FileOutputStream(new File("./tmp/dataofline")));
			byte[] data = dataOfLine.writeToBytes();
			dos.writeInt(data.length);
			dos.write(data);
			dos.close();
			DataInputStream dis = new DataInputStream(new FileInputStream(new File("./tmp/dataofline")));
			int len = dis.readInt();
			data = new byte[len];
			dis.read(data);
			dataOfLine = new DataOfLine(data);
			dis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		ArrayList<long[]> testIndexs = new ArrayList<long[]>();
//		DataInputStream dis = new DataInputStream(new FileInputStream(new File("input/NE.dl.idx")));
//		System.out.println("Read from file:");
//		long id, p, l;
//		while(dis.available() > 0){
//			id = dis.readLong();
//			p = dis.readLong();
//			l = dis.readLong();
//			testIndexs.add(new long[]{id, p, l});
//			System.out.println(id);
//		}
//		dis.close();
//		System.out.println("Begin test:");
//		RandomAccessFile raf = new RandomAccessFile("input/NE.dl.dat", "r");
//		for(int i = 0; i < testIndexs.size(); i++){
//			long [] idx = testIndexs.get(i);
//			raf.seek(idx[1]);
//			byte[] data = new byte[(int) idx[2]];
//			raf.read(data);
//			DataOfLine dl = new DataOfLine(data);
//			if(dl.getLineId() != idx[0]){
//				System.out.print("x");
//			}else{
//				System.out.print(".");
//			}
//			if(i % 100 == 99)System.out.println("");
//		}
//		raf.close();
	}
}
