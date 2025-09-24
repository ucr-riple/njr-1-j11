/**
 * 
 */
package utility;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;

import utility.Compare.DataOfPoint;
import utility.geo.DataOfLine;

/**
 * @author chenqian
 *
 */
public class VerifyIndex {

	public static String idx = "/tmp/qchen/database/GO1.0";
	public static RandomAccessFile rafPoint = null;
	public static HashMap<Long, long[]> tOfPoint = null;
	public static RandomAccessFile rafLine = null;
	public static HashMap<Long, long[]> tOfLine = null;
	
	public static void loadIndex() throws IOException{
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(idx + ".dp.idx"))));
		rafPoint = new RandomAccessFile(idx + ".dp.dat", "r");
		tOfPoint = new HashMap<Long, long[]>();
		while(dis.available() > 0){
			tOfPoint.put(dis.readLong(), new long[]{dis.readLong(), dis.readLong()});
		}
		dis.close();
		dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(idx + ".dl.idx"))));
		rafLine = new RandomAccessFile(idx + ".dl.dat", "r");
		tOfLine = new HashMap<Long, long[]>();
		while(dis.available() > 0){
			tOfLine.put(dis.readLong(), new long[]{dis.readLong(), dis.readLong()});
		}
		dis.close();
		System.out.println("Index laoded!");
		System.out.println("Points:\t" + tOfPoint.size());
		System.out.println("Lines:\t" + tOfLine.size());
	}
	
	
	public static void verify() throws IOException{
		System.out.println("Begin testing points");
		Iterator<Long> iter = tOfPoint.keySet().iterator();
		int lineid = 1;
		while(iter.hasNext()){
			Long key = iter.next();
			long[] tmp = tOfPoint.get(key);
			long pos = tmp[0], len = tmp[1];
			byte[] data = new byte[(int)len];
			rafPoint.seek(pos);
			rafPoint.read(data);
			DataOfPoint dataOfPoint = new DataOfPoint(data);
			if(dataOfPoint.getPointId() != key){
				System.out.println("Error:\t" + key + ", " + dataOfPoint.getPointId());
			}
			System.out.print(dataOfPoint.p.getW());
			if(lineid % 999 == 0)System.out.print(".");
			if(lineid % 99999 == 0)System.out.println("");
			lineid ++;
		}
		lineid = 1;
		System.out.println("Begin testing lines");
		iter = tOfLine.keySet().iterator();
		while(iter.hasNext()){
			Long key = iter.next();
			long[] tmp = tOfLine.get(key);
			long pos = tmp[0], len = tmp[1];
			byte[] data = new byte[(int)len];
			rafLine.seek(pos);
			rafLine.read(data);
			DataOfLine dataOfLine = new DataOfLine(data);
			if(dataOfLine.getLineId() != key){
				System.out.println("Error:\t" + key + ", " + dataOfLine.getLineId());
			}
			if(lineid % 999 == 0)System.out.print(".");
			if(lineid % 99999 == 0)System.out.println("");
			lineid ++;
		}
		rafPoint.close();
		rafLine.close();
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		loadIndex();
		verify();
	}

}
