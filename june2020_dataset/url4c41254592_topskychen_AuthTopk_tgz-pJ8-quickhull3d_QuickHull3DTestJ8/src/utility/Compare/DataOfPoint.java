package utility.Compare;

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

import utility.security.DataIO;
import utility.security.Gfunction;
import utility.security.Point;
import utility.security.RSA;
import utility.security.SecurityUtility;

public class DataOfPoint{
	public int pid;
	public Point p;
	public Gfunction gf_x, gf_y;
	public Long[] delaunayIds = null;
	public String signature;
	
	public int getPointId(){
		return pid;
	}
	
	public DataOfPoint(){
		p = new Point();
		delaunayIds = new Long[0];
		gf_x = new Gfunction();
		gf_y = new Gfunction();
	}
	
	public DataOfPoint(int id, Point _p, Long[] _ids){
		pid = id;
		p = _p;
		delaunayIds = _ids;
		gf_x = new Gfunction(p.x, 16);
		gf_y = new Gfunction(p.y, 16); 
	}
	
	public DataOfPoint(Point _p, Gfunction _gf_x, Gfunction _gf_y){
		p = _p;
		gf_x = _gf_x;
		gf_y = _gf_y;
	}
	
	public DataOfPoint(byte[] buf){
		this();
		try {
			loadFromBytes(buf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] writeToBytes() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeInt(pid);
		p.writeToFile(dos);
		dos.writeInt(delaunayIds.length);
		for(int i = 0 ; i < delaunayIds.length; i++){
			dos.writeLong(delaunayIds[i]);
		}		
		DataIO.writeString(dos, signature);
		gf_x.writeToFile(dos);
		gf_y.writeToFile(dos);
		return baos.toByteArray();
	}
	
	public void load(DataInputStream dis){
		try {
			pid = dis.readInt();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		p.readFromFile(dis);
		int len;
		try {
			len = dis.readInt();
			delaunayIds = new Long[len];
			for(int i = 0 ; i < len; i++){
				delaunayIds[i] = dis.readLong();
			}
			signature = DataIO.readString(dis);
			gf_x.readFromFile(dis);
			gf_y.readFromFile(dis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadFromBytes(byte[] buffer) throws IOException{
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(buffer));
		pid = dis.readInt();
		p.readFromFile(dis);
		int len = dis.readInt();
		delaunayIds = new Long[len];
		for(int i = 0 ; i < len; i++){
			delaunayIds[i] = dis.readLong();
		}
		signature = DataIO.readString(dis);
		gf_x.readFromFile(dis);
		gf_y.readFromFile(dis);
		dis.close();
	}
	
	public String getDigest(){
		String hashValueOfNei = SecurityUtility.computeGeneralHashValue(delaunayIds);
		return SecurityUtility.computeGeneralHashValue(new String[]{
				new Integer(pid).toString(),
				p.getDigest(),
				hashValueOfNei
		});
	}
	
	public String getSignature(){
		return signature;
	}
	
	public void signWithRSA(RSA rsa){
		signature = rsa.encrypt(getDigest());
	}
	
	/**
	 * This is for test.
	 * @throws IOException 
	 * */
	public static void main(String[] args) throws IOException{
		File file = new File("DataOfPoint.testfile");
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
		Point p = new Point(6, 8, 10);
		p.buildByPaillier();
		DataOfPoint dp = new DataOfPoint(10, p, new Long[]{(long) 0, (long) 1, (long) 2});
		byte[] data = dp.writeToBytes();
		int len = data.length;
		dos.write(data);
		DataOfPoint dp2 = new DataOfPoint(12, p, new Long[]{(long) 0, (long) 2, (long) 3});
		byte[] data2 = dp2.writeToBytes();
		int len2 = data.length;
		dos.write(data2);
		dos.flush();
		dos.close();
		
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		raf.seek(len);
		data2 = new byte[len2];
		raf.read(data2);
		dp2 = new DataOfPoint(data2);
//		System.out.println(dp2.getPointId());
		raf.close();
		
		file.delete();
		
//		testReadFromFile();
	}
	
	public static void testReadFromFile() throws IOException{
		ArrayList<long[]> testIndexs = new ArrayList<long[]>();
		DataInputStream dis = new DataInputStream(new FileInputStream(new File("input/NE.dp.idx")));
		System.out.println("Read from file:");
		long id, p, l;
		while(dis.available() > 0){
			id = dis.readLong();
			p = dis.readLong();
			l = dis.readLong();
			testIndexs.add(new long[]{id, p, l});
			System.out.println(id);
		}
		dis.close();
		System.out.println("Begin test:");
		RandomAccessFile raf = new RandomAccessFile("input/NE.dp.dat", "r");
		for(int i = 0; i < testIndexs.size(); i++){
			long [] idx = testIndexs.get(i);
			raf.seek(idx[1]);
			byte[] data = new byte[(int) idx[2]];
			raf.read(data);
			DataOfPoint dp = new DataOfPoint(data);
			if(dp.getPointId() != idx[0]){
				System.out.print("x");
			}else{
				System.out.print(".");
			}
			if(i % 100 == 99)System.out.println("");
		}
		raf.close();
	}
}