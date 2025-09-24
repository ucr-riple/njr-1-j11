package spatialindex.rtree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class SecurityTree {
	public String filePath = "";
	private RTree rtree;
	SecurityNode root;
	private HashMap<Integer, SecurityNode> SecurityNodeMap = new HashMap<Integer, SecurityNode>();

	/**
	 * if this contains a value means it should be load from the file, save (ID,
	 * pos in index file) pairs
	 */
	private HashMap<Integer, Long> SecurityNodeMapPos = new HashMap<Integer, Long>();
	private HashMap<Integer, Integer> SecurityNodeMapLength = new HashMap<Integer, Integer>();

	int level;

	public SecurityTree(RTree _rtree, String _filePath, boolean isLoad) throws IndexOutOfBoundsException, Exception {
		System.out.println("building security info");
		long startTime = System.currentTimeMillis();
		filePath = _filePath;
		if(!isLoad){
			rtree = _rtree;
			buildSecurityTree(this, _rtree);
		}else{
			rtree = _rtree;
			readFromFile(rtree, filePath);
			root = getSecurityNode(rtree.m_rootID);
			//System.out.println(new BigInteger(root.getEntaValue().getBytes("ISO-8859-1")));
		}
		long endTime = System.currentTimeMillis();
		long cpuTime = endTime - startTime;
		System.out.println("build security info finished, time used:" + cpuTime + "ms");
	}

	public void buildSecurityTree(SecurityTree srtree, RTree _rtree) throws IndexOutOfBoundsException, Exception {
		root = SecurityNode.buildSecurityNode(srtree, rtree);
		level = root.getLevel();
	}

	public void saveSecurityNode(SecurityNode n, int id) {
//		if (n.getLevel() > 1) {
//			System.out.println("sNode[" + id + "] saved, lv=" + n.getLevel());
//		}
//		if(n.getLevel() > 1) 
//			SecurityNodeMap.put(new Integer(id), n);
	}

	public SecurityNode getSecurityNode(int id) {
		SecurityNode snode = SecurityNodeMap.get(new Integer(id));
		if (snode == null) {
			Long pos = SecurityNodeMapPos.get(new Integer(id));
			if (pos == null) {
				return null;
			} else {
				int length = SecurityNodeMapLength.get(new Integer(id)).intValue();
				//load from file
				try {
					RandomAccessFile rfile = new RandomAccessFile(new File(filePath + ".sdat"), "r");
					rfile.seek(pos.longValue());
					byte[] data = new byte[length];
					rfile.read(data);
					rfile.close();
//					if(id == 2){
//						for(int i = 0; i < length; i++){
//							System.out.print(data[i]);
//						}System.out.println();
//					}
					snode = new SecurityNode(data, rtree);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				//cache it
				saveSecurityNode(snode, id);
				return snode;
			}
		} else {
			return snode;
		}
	}

	public String getRootEntaValue() {
		return root.getEntaValue();
	}

	public String getrsarootentaValue(){
		return root.getrsaentaValue();
	}
	
	/**
	 * auto add "idx" and "dat"
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void writeToFile(DataOutputStream indexDos, DataOutputStream dataDos, int key, long dataPos, byte[] snodeData) throws IOException {
		indexDos.writeInt(key);
		indexDos.writeLong(dataPos);
		if(dataPos < 0){
			System.err.println("error of pas");
		}
		indexDos.writeInt(snodeData.length);
		dataDos.write(snodeData);
			//System.err.println("key : " + key + " pos : " + dataPos + " len : " + snodeData.length);
	}
	
	public void clearcache(){
		SecurityNodeMap.clear();
	}
	
	public boolean checkindex(){
		if(SecurityNodeMapLength.size() != SecurityNodeMapPos.size())return false;
		Iterator<Integer> keyset = SecurityNodeMapPos.keySet().iterator();
		while(keyset.hasNext()){
			int key = keyset.next();
			if(SecurityNodeMapPos.get(key) < 0){
				System.err.println("error in pos");
				return false;
			}
			if(SecurityNodeMapLength.get(key) < 0){
				System.err.println("error in len");
				return false;
			}
		}
		return true;
	}
	
	private void readFromFile(RTree rtree, String filePath) throws IOException {
		DataInputStream indexDis = new DataInputStream(new FileInputStream(filePath + ".sidx"));
		while (indexDis.available() > 0) {
			Integer key = new Integer(indexDis.readInt());
			long pos = indexDis.readLong();
			SecurityNodeMapPos.put(key, new Long(pos));
			int dataLength = indexDis.readInt();
			SecurityNodeMapLength.put(key, new Integer(dataLength));
		}
		indexDis.close();
	}

	@Override
	public String toString() {
		if (root == null) {
			return "root:null";
		}
		return root.toString();
	}
}
