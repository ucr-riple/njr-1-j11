package spatialindex.rtree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import spatialindex.core.Region;
import utility.Compare.DataOfPoint;
import utility.geo.DataOfLine;
import utility.security.DataIO;
import utility.security.Gfunction;
import utility.security.Point;
import utility.security.SecurityUtility;
import utility.security.mbrGfunction;

/**
 * every SecurityNode has its correspond RTree Node, and can only be constructed
 * by "SecurityNode buildSecurityNode(RTree rTree)"
 * 
 * @author celon
 * @author chenqian
 */
public class SecurityNode {

	private ArrayList<String> childAttrHashValues = new ArrayList<String>();

	private ArrayList<String> childGValues = new ArrayList<String>();
	
	//private ArrayList<mbrGfunction> childmbrGfunctions = new ArrayList<mbrGfunction>();
	private long[][] correspondingMBRids;
	
	private long[][] correspondingMBR;
	
	private int correspondNodeID;
	
	public Node correspondNode;

	private String entaValue;

	private String gValue;

	public DataOfPoint[] dop;
	
	public String mbrDigest;
	
	public String childDigest;
	
	private String rsaentaValue;
	
	private int level = -1;

	public static boolean USE_HILBERT_ORDER = false;
	public static boolean USE_X_ORDER = true;

	public final static int HILBERT_LV = 10;

	public static Comparator<Region> COMPARATOR = new Comparator<Region>() {
		@Override
		public int compare(Region r1, Region r2) {
			if (r1 == null && r2 == null) {
				return 0;
			} else if (r1 == null) {
				return Integer.MAX_VALUE;
			} else if (r2 == null) {
				return Integer.MIN_VALUE;
			}

			if(USE_X_ORDER){
				return (int) (r1.getHigh(0) * 100000) - (int) (r2.getHigh(0) * 100000);
			} else{
				return (int) (r1.getHigh(0) * 100000) - (int) (r2.getHigh(0) * 100000);
			} 
		}
	};

	
	public SecurityNode() {

	}

	public int getID(){
		return correspondNodeID;
	}
	
	public ArrayList<String> readStringArraylist(DataInputStream dis) throws IOException{
		ArrayList<String> ans = new ArrayList<String>();
		int count = dis.readInt();
		for(int i = 0; i < count; i++){
			ans.add(DataIO.readString(dis));
		}
		return ans;
	}
	
	public ArrayList<mbrGfunction> readmbrGfunctionArraylist(DataInputStream dis) throws IOException{
		ArrayList<mbrGfunction> ans = new ArrayList<mbrGfunction>();
		int len = dis.readInt();
		for(int i = 0 ; i < len ; i++){
			mbrGfunction mbrgf = new mbrGfunction();
			mbrgf.readFromFile(dis);
			ans.add(mbrgf);
		}
		return ans;
	}
	/**
	 * write an ArrayList into bytes
	 * 
	 * @param dos
	 * @param list
	 * @throws IOException 
	 * */
	public void writeStringArraylist(DataOutputStream dos, ArrayList<String> list) throws IOException{
		dos.writeInt(list.size());
		for(String str : list){
			DataIO.writeString(dos, str);
		}
	}
	
	
	public void writembrGfunctionArraylist(DataOutputStream dos, ArrayList<mbrGfunction> list) throws IOException{
		dos.writeInt(list.size());
		for(mbrGfunction mbrgf : list){
			mbrgf.writeToFile(dos);
		}
	}
	/**
	 * load from integer data
	 * 
	 * @param data
	 * @param rtree
	 * @throws IOException 
	 */
	public SecurityNode(byte[] data, RTree rTree) throws IOException {
		ByteArrayInputStream dais = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(dais);
		childAttrHashValues = readStringArraylist(dis);
		childGValues = readStringArraylist(dis);
		//childmbrGfunctions = readmbrGfunctionArraylist(dis);
		correspondingMBR = new long[2][2];
		correspondingMBRids = new long[2][2];
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				correspondingMBR[i][j] = dis.readLong();
				correspondingMBRids[i][j] = dis.readLong();
			}
		}
		correspondNodeID = dis.readInt();
		if (correspondNode == null) {
			correspondNode = rTree.readNode(correspondNodeID);
		}
		entaValue = DataIO.readString(dis);
		gValue = DataIO.readString(dis);
		level = dis.readInt();
		rsaentaValue = DataIO.readString(dis);
		mbrDigest = DataIO.readString(dis);
		childDigest = DataIO.readString(dis);
	}

	/**
	 * save the SNode into Byte Array format:
	 * <p>
	 * childAttrHashValues, childIDs, correspondNodeID, entaValue, gValue, level
	 * 
	 * @return
	 * @throws IOException 
	 */
	public byte[] saveTobytes() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		writeStringArraylist(dos, childAttrHashValues);
		writeStringArraylist(dos, childGValues);
		//writembrGfunctionArraylist(dos, childmbrGfunctions);
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				dos.writeLong(correspondingMBR[i][j]);
				dos.writeLong(correspondingMBRids[i][j]);
			}
		}
		dos.writeInt(correspondNodeID);
		DataIO.writeString(dos, entaValue);
		DataIO.writeString(dos, gValue);
		dos.writeInt(level);
		DataIO.writeString(dos, rsaentaValue);
		DataIO.writeString(dos, mbrDigest);
		DataIO.writeString(dos, childDigest);
		dos.flush();
		dos.close();
		return baos.toByteArray();
	}

	/**
	 * given a RTree node, this method outputs a correspond security node which
	 * has a similar structure
	 * 
	 * @param rTree
	 * @return
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	public static SecurityNode buildSecurityNode(SecurityTree srtree, RTree rTree) throws IndexOutOfBoundsException, Exception {
		if (rTree == null) {
			return null;
		}
		Node currentNode = rTree.readNode(rTree.m_rootID);
		SecurityNode node = new SecurityNode();
		DataOutputStream indexDos = new DataOutputStream(new FileOutputStream(srtree.filePath + ".sidx"));
		DataOutputStream dataDos = new DataOutputStream(new FileOutputStream(srtree.filePath + ".sdat"));
		node.buildSecurityInfo(rTree, currentNode, indexDos, dataDos, 0);
		indexDos.flush();
		indexDos.close();
		dataDos.flush();
		dataDos.close();
		return node;
	}

	
	/**
	 * build the whole architecture of security sub-tree
	 * @param rTree
	 * @param node
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	private long buildSecurityInfo(RTree rTree, Node currentNode, DataOutputStream indexDos, DataOutputStream dataDos, long pos) throws IndexOutOfBoundsException, Exception {
		//init attributes
		correspondNode = currentNode;
		correspondNodeID = currentNode.m_identifier;
		level = correspondNode.m_level;
		//build the security tree
		int childCount = correspondNode.m_children;
		long len = 0;
		//compute gValue
		//System.err.println("gValue size :"  + gValue.length());
		if(level > 0){
			System.out.println("level : " + level + " correspondNodeID : " + correspondNodeID);
		}
		if(level > 0) {
			String[] entas = new String[childCount];
			//compute entaVaule
			//entas[0] = gValue;
			//internal node, iteration for children
			correspondingMBRids = new long[2][2];
			correspondingMBR = new long[2][2];
			for (int i = 0; i < childCount; i++) {
				SecurityNode node = new SecurityNode();
				len += node.buildSecurityInfo(rTree, rTree.readNode(correspondNode.getChildIdentifier(i)), indexDos, dataDos, pos + len);
				//childNodes.add(node);
				entas[i] = node.entaValue;
				if(i == 0){
					for(int r = 0; r < 2; r++){
						for(int c = 0; c < 2; c++){
							correspondingMBRids[r][c] = node.correspondingMBRids[r][c];
							correspondingMBR[r][c] = node.correspondingMBR[r][c];
						}
					}
				}else{
					if(correspondingMBR[0][0] > node.correspondingMBR[0][0]){
						correspondingMBRids[0][0] = node.correspondingMBRids[0][0];
						correspondingMBR[0][0] = node.correspondingMBR[0][0];
					}
					if(correspondingMBR[0][1] > node.correspondingMBR[0][1]){
						correspondingMBRids[0][1] = node.correspondingMBRids[0][1];
						correspondingMBR[0][1] = node.correspondingMBR[0][1];
					}
					if(correspondingMBR[1][0] < node.correspondingMBR[1][0]){
						correspondingMBRids[1][0] = node.correspondingMBRids[1][0];
						correspondingMBR[1][0] = node.correspondingMBR[1][0];
					}
					if(correspondingMBR[1][1] < node.correspondingMBR[1][1]){
						correspondingMBRids[1][1] = node.correspondingMBRids[1][1];
						correspondingMBR[1][1] = node.correspondingMBR[1][1];
					}
				}
			}
			String tmpGvalue[] = new String[8];
			for(int r = 0; r < 2; r++){
				for(int c = 0; c < 2; c++){
					DataOfPoint dataOfPoint = rTree.loadDataOfPointFromIndex(correspondingMBRids[r][c]);
					if(c == 0){
						tmpGvalue[r * 2 + c] = dataOfPoint.p.getDigestX(); 
						tmpGvalue[r * 2 + c + 4] = dataOfPoint.gf_x.getDigest();
					}else{
						tmpGvalue[r * 2 + c] = dataOfPoint.p.getDigestY();
						tmpGvalue[r * 2 + c + 4] = dataOfPoint.gf_y.getDigest();
					}
				}
			}
			mbrDigest = SecurityUtility.computeHashValue(tmpGvalue);
			childDigest = SecurityUtility.computeHashValue(entas);
			entaValue = SecurityUtility.computeHashValue(new String[]{mbrDigest, childDigest});
		} else {
			//leaf node
			String[] entas = new String[2 * childCount];
			//sortTuples(); // actually no need here
			//compute entaVaule
			//leaf node, iteration for children
			correspondingMBRids = new long[2][2];
			correspondingMBR = new long[2][2];
			for (int i = 0; i < childCount; i++) {
				DataOfPoint dataOfPoint = rTree.loadDataOfPointFromIndex(correspondNode.m_pIdentifier[i]);
				childGValues.add(dataOfPoint.p.getDigest());
				childAttrHashValues.add(new Integer(correspondNode.m_pIdentifier[i]).toString());
//				if(correspondNode.m_pIdentifier[i] == 966)
//					System.out.println(childGValues.get(i));
				entas[i] = childGValues.get(i);
				entas[childCount + i] = childAttrHashValues.get(i);
				if(i == 0){
					for(int r = 0; r < 2; r++){
						for(int c = 0; c < 2; c++){
							correspondingMBRids[r][c] = correspondNode.m_pIdentifier[i];
							if(c == 0)correspondingMBR[r][c] = (long)correspondNode.m_pMBR[i].getLow(0);
							else correspondingMBR[r][c] = (long)correspondNode.m_pMBR[i].getLow(1);
						}
					}
				}else{
					if(correspondingMBR[0][0] > (long)correspondNode.m_pMBR[i].getLow(0)){//x
						correspondingMBRids[0][0] = correspondNode.m_pIdentifier[i];
						correspondingMBR[0][0] = (long)correspondNode.m_pMBR[i].getLow(0);
					}
					if(correspondingMBR[0][1] > (long)correspondNode.m_pMBR[i].getLow(1)){//y
						correspondingMBRids[0][1] = correspondNode.m_pIdentifier[i];
						correspondingMBR[0][1] = (long)correspondNode.m_pMBR[i].getLow(1);
					}
					if(correspondingMBR[1][0] < (long)correspondNode.m_pMBR[i].getLow(0)){
						correspondingMBRids[1][0] = correspondNode.m_pIdentifier[i];
						correspondingMBR[1][0] = (long)correspondNode.m_pMBR[i].getLow(0);
					}
					if(correspondingMBR[1][1] < (long)correspondNode.m_pMBR[i].getLow(1)){
						correspondingMBRids[1][1] = correspondNode.m_pIdentifier[i];
						correspondingMBR[1][1] = (long)correspondNode.m_pMBR[i].getLow(1);
					}
				}
			}
			String tmpGvalue[] = new String[8];
			for(int r = 0; r < 2; r++){
				for(int c = 0; c < 2; c++){
					DataOfPoint dataOfPoint = rTree.loadDataOfPointFromIndex(correspondingMBRids[r][c]);
					if(c == 0){
						tmpGvalue[r * 2 + c] = dataOfPoint.p.getDigestX(); 
						tmpGvalue[r * 2 + c + 4] = dataOfPoint.gf_x.getDigest();
					}else{
						tmpGvalue[r * 2 + c] = dataOfPoint.p.getDigestY();
						tmpGvalue[r * 2 + c + 4] = dataOfPoint.gf_y.getDigest();
					}
				}
			}
			mbrDigest = SecurityUtility.computeHashValue(tmpGvalue);
			childDigest = SecurityUtility.computeHashValue(entas);
			entaValue = SecurityUtility.computeHashValue(new String[]{mbrDigest, childDigest});
		}
		//SecurityTree.saveSecurityNode(this, correspondNodeID);
		if(correspondNodeID == rTree.m_rootID){
			rsaentaValue = SecurityUtility.signWithRSA(entaValue);
//			if(DataIO.compareStringInRSA(SecurityUtility.deSignWithRSA(rsaentaValue), entaValue) == false){
//				System.err.println("Err!!");
//			}else{
//				System.out.println("Pass!");
//			}
		}else{
			rsaentaValue = null;
		}
		byte[] data = this.saveTobytes();
		SecurityTree.writeToFile(indexDos, dataDos, correspondNodeID, pos + len, data);
		//dataDos.flush();
//		SecurityNode snode = new SecurityNode(data, rTree);
//		for (int i = 0; i < childCount; i++) {
//			if(snode.correspondNode.m_pIdentifier[i] == 966){
//				for(int j = 0; j < data.length; j++){
//					System.out.print(data[j]);
//				}System.out.println();
//			}
//				//System.out.println(snode.getChildGValueById(i));
//		}
		if(level > 0){
//			rTree.closeBtree();
		}
		return len + data.length;
	}

	public String getMbrDigest(){
		return mbrDigest;
	}
	
	public String getChildDigest(){
		return childDigest;
	}
	public DataOfPoint[] getDOPs(RTree rTree){
		dop = new DataOfPoint[2];
		DataOfPoint dop00 = rTree.loadDataOfPointFromIndex(correspondingMBRids[0][0]);
		DataOfPoint dop01 = rTree.loadDataOfPointFromIndex(correspondingMBRids[0][1]);
		DataOfPoint dop10 = rTree.loadDataOfPointFromIndex(correspondingMBRids[1][0]);
		DataOfPoint dop11 = rTree.loadDataOfPointFromIndex(correspondingMBRids[1][1]);
		dop[0] = new DataOfPoint(new Point(dop00.p, dop01.p), dop00.gf_x, dop01.gf_y);
		dop[1] = new DataOfPoint(new Point(dop10.p, dop11.p), dop10.gf_x, dop11.gf_y);
		return dop;
	}

	public void sortTuples() {
		Arrays.sort(correspondNode.m_pMBR, COMPARATOR);
	}

	public void printBytes(byte[] data){
		for(int i = 0; i < data.length; i++ ){
			System.out.print((int)data[i] + " ");
		}
		System.out.println();
	}
	
	/**
	 * build selves info of security, used in experiment only
	 * 
	 * @param node
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	protected int buildSelfSecurityInfo(RTree rTree, Node currentNode) throws IndexOutOfBoundsException, Exception {
		//init attributes
		/*correspondNode = currentNode;
		correspondNodeID = currentNode.m_identifier;
		//gValueComponents = new double[4 * DIMENSION];
		level = correspondNode.m_level;
		//build the security tree
		int childCount = correspondNode.m_children;
		//compute gValue
		//gValueComponents = SecurityUtility.computeGValueComponents(correspondNode.m_nodeMBR);
		correspondmbrGfunction = new mbrGfunction();
		gValue = SecurityUtility.computeGValue(correspondmbrGfunction, correspondNode.m_nodeMBR);
		int bytelen = 0;
		if (level > 0) {
			String[] entas = new String[childCount + 1];
			//compute entaVaule
			entas[0] = gValue;
			//internal node, iteration for children
			Region[] tmpRegions = new Region[childCount];
			for (int i = 0; i < childCount; i++) {
				//childNodes.add(node);
				entas[i + 1] = gValue;//node.entaValue;
			}
			entaValue = SecurityUtility.computeHashValue(entas);
			bytelen = entaValue.getBytes().length;
		} else {
			//leaf node
			String[] entas = new String[2 * childCount + 1];
			//compute entaVaule
			entas[0] = gValue;
			//leaf node, iteration for children
			for (int i = 0; i < childCount; i++) {
//				if(i == 0){
//					mbrGfunction mbrgf = new mbrGfunction();
//					childGValues.add(SecurityUtility.computeGValue(mbrgf, correspondNode.m_pMBR[i]));
//					childmbrGfunctions.add(mbrgf);
//					childAttrHashValues.add(new Integer(correspondNode.m_pIdentifier[i]).toString());
//					entas[i + 1] = childGValues.get(i);
//					entas[childCount + i + 1] = childAttrHashValues.get(i);
//				}else{
//					entas[i + 1] = gValue;
//					entas[childCount + i + 1] = gValue;
//				}
				entas[i + 1] = gValue;
				entas[childCount + i + 1] = gValue;
				if(USE_HILBERT_ORDER || USE_X_ORDER){
					bytelen += gValue.getBytes().length;
				}
				//break;//simulate the digest cache
			}
			if(USE_HILBERT_ORDER){
				Arrays.sort(correspondNode.m_pMBR, COMPARATOR);
			}else{
				Arrays.sort(correspondNode.m_pMBR, COMPARATOR);
			}

			entaValue = SecurityUtility.computeHashValue(entas);
			bytelen = entaValue.getBytes().length;
		}
		return bytelen;*/
		return 0;
	}

	/**
	 * get the value to prove the relationship
	 * 
	 * @param type
	 *            the relationship between query and node, see IVisitor.TYPE
	 * @return the 4 or 5-*dimension key value of G(), the 5th array is
	 *         complement of NaN when outside
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	/*public String[][] getEssentialGComponents(int type, Region queryRegion){
		try {
			return SecurityUtility.computeEssentialGValueComponents(correspondmbrGfunction, correspondNode.m_nodeMBR, type, queryRegion);
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/

	/**
	 * get the value to prove the relationship
	 * 
	 * @param type
	 *            the relationship between query and node, see IVisitor.TYPE
	 * @return the 4 or 5-*dimension key value of G(), the 5th array is
	 *         complement of NaN when outside
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
//	public String[][] getEssentialGComponents(int type, Region queryRegion, int maxOmitLength){
//		try {
//			return SecurityUtility.computeEssentialGValueComponents(correspondmbrGfunction, correspondNode.m_nodeMBR, type, queryRegion);
//		} catch (IndexOutOfBoundsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
	
//	public ArrayList<String[][]> getChildEssentialGValueComponents(int[] childTypes, Region queryRegion) {
//		ArrayList<String[][]> result = new ArrayList<String[][]>();
//		int childCount = correspondNode.m_children;
//		for (int i = 0; i < childCount; i++) {
//			try {
//				result.add(SecurityUtility.computeEssentialGValueComponents(childmbrGfunctions.get(i), correspondNode.m_pMBR[i], childTypes[i], queryRegion));
//			} catch (IndexOutOfBoundsException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}
	
	public ArrayList<String> getChildGValues() {
		return childGValues;
	}
	
	public String getChildGValueById(int id){
		if(childGValues == null || id >= childGValues.size()){
			System.err.println("not exists or out of range");
		}
		return childGValues.get(id);
	}

	public ArrayList<String> getChildHashValues() {
		return childAttrHashValues;
	}

	/*public ArrayList<mbrGfunction> getChildmbrGfunctions(){
		return childmbrGfunctions;
	}*/
	
	public String getEntaValue() {
		return entaValue;
	}

	public int getChildCount(){
		return correspondNode.m_children;
	}
	/*public mbrGfunction getmbrGfunction(){
		return correspondmbrGfunction;
	}*/
	/*public double[] getGComponents() {
		return gValueComponents;
	}*/

	public String getGValue() {
		return gValue;
	}

	protected int getLevel() {
		return level;
	}

	public String getrsaentaValue(){
		return rsaentaValue;
	}
	@Override
	public String toString() {
		
		String ret = "";
		ret += "SNode[lv:" + level + "\tid:" + correspondNode.m_identifier + "\tet:" + entaValue + "\tg:" + gValue + "\t" + "mbr:" + correspondNode.m_nodeMBR
				+ "\t]";
		/*for (int i = 0; i < childNodes.size(); i++) {
			ret += "\n" + tab + childNodes.get(i).toString();
		}*/
		return ret;
	}

}
