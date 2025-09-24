/**
 * 
 */
package utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;

import utility.Compare.DataOfPoint;

/**
 * @author chenqian
 *
 */
public class ReadDigest {

	
	public HashMap<Integer, DigestCell> digestHashMap = new HashMap<Integer, DigestCell>();
	
	public RecordManager recmanOfPoint = null;
	public PrimaryTreeMap<Long, byte[]> btOfPoint = null;
	public int ThreadNum = 16;
	public boolean[] threadStatus = new boolean[ThreadNum];
	
	
	public ReadDigest(String destFileName){
		try {
			recmanOfPoint = RecordManagerFactory.createRecordManager(destFileName);
			btOfPoint = recmanOfPoint.treeMap("treemap");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(btOfPoint.containsKey((long)2) == false){
			System.out.println("fail to open!");
		}
		final int[] lock = new int[1];
		lock[0] = 1;
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
						long curId;
						synchronized (lock) {
							curId = lock[0];
							lock[0] ++;
						}
//						if(curId >= totalNum)break;
						if(btOfPoint.containsKey(curId) == false)break;
						System.out.println("Thread:\t" + threadId + "\tid:\t" + curId);
						DataOfPoint dop = new DataOfPoint(btOfPoint.get(curId));
						digestHashMap.put((int)curId, new DigestCell(dop.p.getDigest(), dop.p.getDigestX(), dop.p.getDigestY(), 
								dop.gf_x.getDigest(), dop.gf_y.getDigest()));
						if(curId % 500 == 0){
							try {
								recmanOfPoint.clearCache();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
//							System.out.println("Commit Thread:\t" + threadId + "\tid:\t" + curId);
						}
					}
					threadStatus[threadId] = true;
				}
			}).start();
		}
		//pw.flush();
		//pw.close();
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
		System.out.println("Finished!");
		try {
			recmanOfPoint.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("input filename");
		String destFileName = in.nextLine() + ".PointsData";
		System.out.println("Filename : " + destFileName);
		ReadDigest readDigest = new ReadDigest(destFileName);
	}

}

class DigestCell{
	String pDigest;
	String pDigestX;
	String pDigestY;
	String gfxDigest;
	String gfyDigest;
	public DigestCell(String pDigest, String pDigestX, String pDigestY,
			String gfxDigest, String gfyDigest) {
		super();
		this.pDigest = pDigest;
		this.pDigestX = pDigestX;
		this.pDigestY = pDigestY;
		this.gfxDigest = gfxDigest;
		this.gfyDigest = gfyDigest;
	}
}