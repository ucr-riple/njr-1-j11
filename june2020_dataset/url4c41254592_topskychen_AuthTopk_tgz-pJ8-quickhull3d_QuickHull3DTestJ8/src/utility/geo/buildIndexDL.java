/**
 * 
 */
package utility.geo;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Scanner;

import mesh.PDelaunay;
import utility.Compare.DataOfPoint;
import utility.security.Point;
import utility.security.RSA;

/**
 * @author chenqian
 * Build index for data of line. The index has two files. 
 * One is *.dat, and the other is *.idx, which are data and index respectively.
 * We dont use btree this time. We use a hash map store the index, and every 
 * tuple has <position, length>.
 * The input data should be formatted to [0, 1].
 */
public class buildIndexDL {

	
	public static ArrayList<long[]> points = null;	
	public static int ThreadNum = 8;
	public static boolean[] threadStatus = new boolean[ThreadNum];
	public static RSA rsa = new RSA(); // sign for every data of point.

	/**
	 * Load data.
	 * 
	 * */
	public static void loadData(String sourceFileName){
		points = new ArrayList<long[]>();
		
		try {
			String line = null;
			LineNumberReader lr = new LineNumberReader(new FileReader(sourceFileName));
			try {
				while((line = lr.readLine()) != null){
					String[] tks = line.split("\t");
					long[] point = new long[tks.length - 1];
					for(int i = 1; i < tks.length; i++){
						point[i - 1] = Long.parseLong(tks[i]);
					}
					points.add(point);
					//if(DEBUG) System.err.println();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Load finished!");
	}

	public static void buildIndex(String sourceFileName, String destFileName, int fromId, int toId) throws IOException{
		loadData(sourceFileName);
		if(toId == -1)toId = points.size();
		
		final DataOutputStream dos_idx =  new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(destFileName + ".idx"))));
		final DataOutputStream dos_dat = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(destFileName + ".dat"))));
		
		final int[] lock = new int[1];
		final long[] w_lock = new long[1];
		final int limit = toId; 
		lock[0] = fromId;
		w_lock[0] = 0;
		for(int id = 0; id < ThreadNum; id ++){
			threadStatus[id] = false;
			final int tid = id;
			new Thread(new Runnable() {
				int threadId;
				@Override
				public void run() {
					// TODO Auto-generated method stub
					threadId = tid;
					while(true){
						int curId;
						synchronized (lock) {
							curId = lock[0];
							lock[0] ++;
						}
						if(curId >= limit)break;
						if(curId % 2000 == 0)System.out.println("Thread:\t" + threadId + "\tid:\t" + curId);
						ArrayList<Long> tmp = new ArrayList<Long>();
						long[] point = points.get(curId);
						Point pPoint = new Point(point[0], point[1], point[2]);
						ArrayList<DataOfLine> dls = new ArrayList<DataOfLine>();
						for(int j = 3; j < point.length; j++){
							int id1 = curId, id2 = (int)point[j];
							if(id1 >= id2)continue;
							Long lineId = DataOfLine.calcLineId(id1, id2);
							long[] point2 = points.get(id2);
							Line nline = new Line(point[0], point[1], point[2], point2[0], point2[1], point2[2]);
							DataOfLine nDataOfLine = new DataOfLine(lineId, nline);
							nDataOfLine.signWithRSA(rsa);
							dls.add(nDataOfLine);
						}
//						System.out.println("");
						try {
							synchronized (w_lock) {
								for(int i = 0; i < dls.size(); i++){
									DataOfLine nDataOfLine = dls.get(i);
									byte[] data = nDataOfLine.writeToBytes();
									dos_dat.write(data);
									dos_idx.writeLong(nDataOfLine.getLineId());
									dos_idx.writeLong(w_lock[0]);
									dos_idx.writeLong(data.length);
									w_lock[0] += data.length;
								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					threadStatus[threadId] = true;
				}
			}).start();
		}
		while(true){
			boolean found = false;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i = 0; i < ThreadNum; i++){
				if(threadStatus[i] == false){
					found = true;
				}
			}
			if(!found)break;
		}
		dos_dat.flush();
		dos_dat.close();
		dos_idx.flush();
		dos_idx.close();
		System.out.println("Build finished!");
	}
	
	/**
	 * @param args
	 * @throws NumberFormatException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		if(args.length > 0){
			buildIndex(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
			if(args.length >= 5){
				ThreadNum = Integer.parseInt(args[4]);
			}
		}else{
			Scanner in = new Scanner(System.in);
			System.out.println("Please input your source file name:");
			String sourceFileName = in.nextLine();
			System.out.println("Please input your destination file name:");
			String destFileName = in.nextLine();
			System.out.println("Please input fromidx and toidx: (enter by default)");
			String idxs = in.nextLine();
			int toIdx = -1, fromIdx = 0;
			if(!idxs.equals("")){
				String[] tks = idxs.split(" ");
				fromIdx = Integer.parseInt(tks[0]);
				toIdx = Integer.parseInt(tks[1]);
			}
			buildIndex(sourceFileName, destFileName, fromIdx, toIdx);
		}
	}

}
