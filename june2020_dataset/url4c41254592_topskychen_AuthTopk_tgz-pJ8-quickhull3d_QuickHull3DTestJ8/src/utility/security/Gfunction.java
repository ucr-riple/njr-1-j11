package utility.security;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;


/**
 * @author chenqian
 *
 */
public class Gfunction {
	
	public long L = -((long)1 << 60), U = (long)1 << 60;
	public String r = "something";
	public String[] MHTree = null;
	public String[][] GvalueU, GvalueL;
	public String[] GvalueUs, GvalueLs;
	public int[][] valueU, valueL;
	public long v;
	public int base, m;
	public int whichSide; //0 both, -1 left, 1 right
	public boolean isL;
	
	public Gfunction(long x, int _base, long _L, long _U) {
		// TODO Auto-generated constructor stub
		L = _L;
		U = _U;
		v = x;
		base = _base;
		m = (int)(Math.log(U - L) / Math.log(base));
		whichSide = 0;
		GenerateU();
		GenerateL();
		getDigest();
	}
	
	public Gfunction(long x, int _base, long _L, long _U, int _whichSide) {
		// TODO Auto-generated constructor stub
		L = _L;
		U = _U;
		v = x;
		base = _base;
		m = (int)(Math.log(U - L) / Math.log(base));
		whichSide = _whichSide;
		GenerateU();
		GenerateL();
		getDigest();
	}
	
	public Gfunction(long x, int _base) {
		// TODO Auto-generated constructor stub
		v = x;
		base = _base;
		m = (int)(Math.log(U - L) / Math.log(base));
		whichSide = 0;
		GenerateU();
		GenerateL();
		getDigest();
	}
	
	public Gfunction() {
		// TODO Auto-generated constructor stub
	}
	
	public Gfunction(int _base) {
		base = _base;
		// TODO Auto-generated constructor stub
	}
	
	public static String HashXmore1times(String s, long m){
		if(m < 0)return null;
		String ans = s;
		while(m -- >= 0){
			ans = Hasher.hashString(ans);
		}
		return ans;
	}
	
	public static String HashXtimes(String s, long m){
		if(m < 0)return null;
		String ans = s;
		while(m -- > 0){
			ans = Hasher.hashString(ans);
		}
		return ans;
	}
	
	public void GenerateU(){
		GvalueUs = new String[m + 1];
		GvalueU = new String[m + 1][m + 1];
		valueU = new int[m + 1][m + 1];
		if(whichSide >= 0){
			for(int i = 0; i < m; i++){
				long t = U - v;
				boolean found = false;
				//long t = U - x - 1; //if '=' is not allowed
				for(int j = 0; j < m + 1; j++){
					if(j == 0){
						valueU[i][j] = (int) (t % base + base);
					}else if(j <= i){
						valueU[i][j] = (int) (t % base + base - 1);
					}else if( j == i + 1){
						valueU[i][j] = (int) (t % base - 1);
					}else{
						valueU[i][j] = (int) (t % base);
					}
					if(valueU[i][j] < 0){
						found = true;
						valueU[i][0] = -1;
						break;
					}
					t /= base;
				}
				if(found)GvalueU[i] = null;
				else{
					for(int j = 0; j < m + 1; j++){
						GvalueU[i][j] = HashXmore1times(r, valueU[i][j]);
					}
				}
			}
			long t = U - v;
			for(int i = 0; i < m + 1; i++){
				valueU[m][i] = (int) (t % base);
				t /= base;
				GvalueU[m][i] = HashXmore1times(r, valueU[m][i]);
			}
			for(int i = 0; i < m + 1; i++){
				GvalueUs[i] = SecurityUtility.computeGeneralHashValue(GvalueU[i]);
			}
		}else{
			for(int i = 0; i < m + 1; i++){
				for(int j = 0; j < m + 1; j++){
					valueU[i][j] = 0;
					GvalueU[i][j] = null;
				}
				GvalueUs[i] = null;
			}
		}
	}
	
	public void GenerateL(){
		GvalueLs = new String[m + 1];
		GvalueL = new String[m + 1][m + 1];
		valueL = new int[m + 1][m + 1];
		
		if(whichSide <= 0){
			for(int i = 0; i < m; i++){
				long t = v - L;
				boolean found = false;
				//long t = x - L - 1; // if '=' is not allowed
				for(int j = 0; j < m + 1; j++){
					if(j == 0){
						valueL[i][j] = (int) (t % base + base);
					}else if(j <= i){
						valueL[i][j] = (int) (t % base + base - 1);
					}else if( j == i + 1){
						valueL[i][j] = (int) (t % base - 1);
					}else{
						valueL[i][j] = (int) (t % base);
					}
					if(valueL[i][j] < 0){
						found = true;
						valueL[i][0] = -1;
						break;
					}
					t /= base;
				}
				if(found)GvalueL[i] = null;
				else{
					for(int j = 0; j < m + 1; j ++){
						GvalueL[i][j] = HashXmore1times(r, valueL[i][j]);
					}
				}
			}
			long t = v - L;
			for(int i = 0; i < m + 1; i++){
				valueL[m][i] = (int) (t % base);
				t /= base;
				GvalueL[m][i] = HashXmore1times(r, valueL[m][i]);
			}
			for(int i = 0; i < m + 1; i++){
				GvalueLs[i] = SecurityUtility.computeGeneralHashValue(GvalueL[i]);
			}
		}else{
			for(int i = 0; i < m + 1; i++){
				for(int j = 0; j < m + 1; j++){
					valueL[i][j] = 0;
					GvalueL[i][j] = null;
				}
				GvalueLs[i] = null;
			}
		}
		//System.out.println(GvalueL[m][m]);
	}
	
	/**
	 * @param isL boolean, is this part for L or not
	 * @param cur int, current id
	 * @param can int[], composition of current value 
	 * @return String[] for verification part
	 * */
	public String[] generateVerifyPart(boolean isL, int cur, int[] can){
		ArrayList<String> str = new ArrayList<String>();
		buildVO(1, 1, m + 1, cur + 1, str);
		String[] ServerReturned	= new String[2 + 1 + m + 1 + str.size()];
		if(isL){
			ServerReturned[0] = new Integer(m + 1).toString();
			ServerReturned[1] = new Integer(-1).toString();
			for(int i = 0; i < m + 1; i++){
				ServerReturned[2 + i] = HashXmore1times(r, valueL[cur][i] - can[i]);
			}
			ServerReturned[2 + 1 + m] = GvalueUs[cur];
		}else{
			ServerReturned[0] = new Integer(-1).toString();
			ServerReturned[1] = new Integer(m + 1).toString();
			ServerReturned[2] = GvalueLs[cur];
			for(int i = 0; i < m + 1; i++){
				ServerReturned[2 + 1 + i] = HashXmore1times(r, valueU[cur][i] - can[i]);
			}
		}
		for(int i = 0; i < str.size(); i++){
			ServerReturned[2 + 1 + m + 1 + i] = str.get(i);
		}
		return ServerReturned;
	}
	
	/**
	 * Generate VO for server
	 * 
	 * @param x
	 * @return String[]
	 * @throws Exception
	 * */
	public String[] GenerateVeryfyPart(long x, boolean _isL){
		isL = _isL;
		if(isL && v >= x){
			x = x - L;
			int[] can = new int[m + 1];
			boolean ok = true;
			for(int i = 0; i < m + 1; i++){
				can[i] = (int) (x % base);
				x /= base;
				if(can[i] > valueL[m][i]){
					ok = false;
				}
			}
			if(ok){
				return generateVerifyPart(true, m, can);
			}
			for(int i = m - 1; i >= 0; i--){
				ok = true;
				for(int j = 0; j < m + 1; j++){
					if(valueL[i][j] < can[j]){
						ok = false;
						break;
					}
				}
				if(ok){
					return generateVerifyPart(true, i, can);
				}
			}
		}
		if(!isL && v <= x){
			x = U - x;
			int[] can = new int[m + 1];
			boolean ok = true;
			for(int i = 0; i < m + 1; i++){
				can[i] = (int) (x % base);
				x /= base;
				if(can[i] > valueU[m][i]){
					ok = false;
				}
			}
			if(ok){
				return generateVerifyPart(false, m, can);
			}
			for(int i = m - 1; i  >= 0; i--){
				ok = true;
				for(int j = 0; j < m + 1; j ++){
					if(valueU[i][j] < can[j]){
						ok = false;
						break;
					}
				}
				if(ok){
					return generateVerifyPart(false, i, can);
				}
			}
		}
		System.err.println("G function error in server side!");
		return null;
		//throw new Exception("G function error in server side!");
	}
	
		
	public void writeToFile(DataOutputStream dos) throws IOException{
		/*
		 * 	public long L, U;
			public String r = "something";
			public String[][] GvalueU, GvalueL;
			public int[][] valueU, valueL;
			public long v;
			public int base, m;
		 * */
		dos.writeLong(L);
		dos.writeLong(U);
		dos.writeLong(v);
		dos.writeInt(base);
		dos.writeInt(MHTree.length);
		//System.out.println("m:\t" + m + " MHTree:\t" + MHTree.length);
		dos.writeInt(whichSide);
		for(int i = 0; i < MHTree.length; i++){
			DataIO.writeString(dos, MHTree[i]);
		}
		for(int i = 0 ; i <= m; i++){
			for(int j = 0; j <= m; j++){
				dos.writeInt(valueL[i][j]);
				dos.writeInt(valueU[i][j]);
			}
			if(GvalueL[i] != null){
				dos.writeBoolean(true);
				for(int j = 0; j <= m; j++){
					DataIO.writeString(dos, GvalueL[i][j]);
				}
			}else{
				dos.writeBoolean(false);
			}
			if(GvalueU[i] != null){
				dos.writeBoolean(true);
				for(int j = 0; j <= m; j++){
					DataIO.writeString(dos, GvalueU[i][j]);
				}
			}else{
				dos.writeBoolean(false);
			}
			DataIO.writeString(dos, GvalueLs[i]);
			DataIO.writeString(dos, GvalueUs[i]);
		}
		//dos.writeInt(m);
	}

	
	public void readFromFile(DataInputStream dis) throws IOException{
		L = dis.readLong();
		U = dis.readLong();
		v = dis.readLong();
		base = dis.readInt();
		m = (int)(Math.log(U - L) / Math.log(base));
		int len = dis.readInt();
		whichSide = dis.readInt();
		MHTree = new String[len];
		for(int i = 0; i < len ; i++){
			MHTree[i] = DataIO.readString(dis);
		}
		valueL = new int[m + 1][m + 1];
		valueU = new int[m + 1][m + 1];
		GvalueL = new String[m + 1][m + 1];
		GvalueU = new String[m + 1][m + 1];
		GvalueLs = new String[m + 1];
		GvalueUs = new String[m + 1];
		for(int i = 0; i <= m; i ++){
			for(int j = 0; j <= m ; j ++){
				valueL[i][j] = dis.readInt(); // for no using here
				valueU[i][j] = dis.readInt();
			}
			if(dis.readBoolean()){
				for(int j = 0; j <= m; j++){
					//System.out.println(i + "\t" + j);
					GvalueL[i][j] = DataIO.readString(dis);
				}
			}
			if(dis.readBoolean()){
				for(int j = 0; j <= m ;j++){
					GvalueU[i][j] = DataIO.readString(dis);
				}
			}
			GvalueLs[i] = DataIO.readString(dis);
			GvalueUs[i] = DataIO.readString(dis);
		}
	}
	
	public void buildMHTree(int nd, int l, int r, String[] str){
		if(l == r){
			MHTree[nd] = str[l - 1];
			return;
		}
		int mid = (l + r) >> 1;
		buildMHTree(nd * 2, l, mid, str);
		buildMHTree(nd * 2 + 1, mid + 1, r, str);
		MHTree[nd] = Hasher.hashString(MHTree[nd * 2] + "|" + MHTree[nd * 2 + 1]);
	}
	
	public void buildVO(int nd, int l, int r, int v, ArrayList<String> str){
		if(l == r){
			return;
		}
		int mid = (l + r) >> 1;
		if(v <= mid){
			str.add("|");
			buildVO(nd * 2, l, mid, v, str);
			str.add(MHTree[nd * 2 + 1]);
		}else {
			str.add(MHTree[nd * 2]);
			buildVO(nd * 2 + 1, mid + 1, r, v, str);
			str.add("|");
		}
	}
	
	public static String rebuildVO(int l, int r, String[] buf, String str){
		String ans = "";
		//System.out.println(l + " " + r);
		if(l > r){
			return str;
		}
		if(buf[l].equals("|")){
			ans = rebuildVO(l + 1, r - 1, buf, str) + "|" + buf[r];
			return Hasher.hashString(ans);
		}else{
			ans = buf[l] + "|" + rebuildVO(l + 1, r - 1, buf, str);
			return Hasher.hashString(ans);
		}
	}
	
	public void copyStringArray(String[] dest, String[] src){
		for(int i = 0; i < src.length; i++){
			dest[i] = src[i];
		}
	}
	
	public String getDigest(){
		if(MHTree != null){
			return MHTree[1];
		}
		String[] strs = new String[m + 1];
		for(int i = 0; i <= m; i++){
			String[] s = new String[2];
			s[0] = GvalueLs[i];
			s[1] = GvalueUs[i];
			strs[i] = SecurityUtility.computeGeneralHashValue(s);
		}
		MHTree = new String[3 * (m + 1)];
		buildMHTree(1, 1, m + 1, strs);
		return MHTree[1];
	}
	
	public Gfunction getcopy(){
		/**
		 * 	public static long L = 0, U = 1000000;
			public String r = "something";
			public String[] MHTree = null;
			public String[][] GvalueU, GvalueL;
			public String[] GvalueUs, GvalueLs;
			public int[][] valueU, valueL;
			public long v;
			public static int base, m;
		 * */
		Gfunction gf = new Gfunction();
		gf.L = this.L;
		gf.U = this.U;
		gf.MHTree = new String[this.MHTree.length];
		copyStringArray(gf.MHTree, this.MHTree);
		gf.GvalueU = new String[this.GvalueU.length][this.GvalueU[0].length];
		for(int i = 0; i < this.GvalueU.length; i++){
			copyStringArray(gf.GvalueU[i], this.GvalueU[i]);
		}
		gf.GvalueL = new String[this.GvalueL.length][this.GvalueL[0].length];
		for(int i = 0; i < this.GvalueL.length; i++){
			copyStringArray(gf.GvalueL[i], this.GvalueL[i]);
		}
		gf.GvalueUs = new String[this.GvalueUs.length];
		copyStringArray(gf.GvalueUs, this.GvalueUs);
		gf.GvalueLs = new String[this.GvalueLs.length];
		copyStringArray(gf.GvalueLs, this.GvalueLs);
		gf.valueU = new int[this.valueU.length][this.valueU[0].length];
		gf.valueL = new int[this.valueL.length][this.valueL[0].length];
		for(int i = 0; i < this.valueU.length; i++){
			for(int j = 0; j < this.valueU[i].length; j++){
				gf.valueU[i][j] = this.valueU[i][j];
				gf.valueL[i][j] = this.valueL[i][j];
			}
		}
		gf.v = this.v;
		gf.base = this.base;
		gf.m = this.m;
		return gf;
	}
	
	public String ClientComputed(String[] ServerReturned, long x) throws Exception{
		long can;
		String str;
		int len = ServerReturned.length;
		int l = Integer.parseInt(ServerReturned[0]);
		int r = Integer.parseInt(ServerReturned[1]);
		if(l == -1){
			m = r - 1;
			String[] strs = new String[m + 1];
			if(x < 0){
				throw new Exception("value of x < 0!");
			}
			//System.out.println("==========");
			for(int i = 0; i < m + 1; i++){
				can = (x % base);
				x /= base;
				strs[i] = HashXtimes(ServerReturned[2 + i + 1], can);
				//System.out.println(strs[i]);
			}
			str = SecurityUtility.computeGeneralHashValue(strs);
			//System.out.println("Computed : " + str);
			str = SecurityUtility.computeGeneralHashValue(new String[]{ServerReturned[2], str});
			String ans = rebuildVO(2 + 1 + m + 1, len - 1, ServerReturned, str);
			return ans;
		}else if(r == -1){
			m = l - 1;
			String[] strs = new String[m + 1];
			if(x < 0){
				throw new Exception("value of x < 0!");
			}
			for(int i = 0; i < m + 1; i++){
				can = (x % base);
				x /= base;
				strs[i] = HashXtimes(ServerReturned[2 + i], can);
			}
			str = SecurityUtility.computeGeneralHashValue(strs);
			str = SecurityUtility.computeGeneralHashValue(new String[]{str, ServerReturned[2 + 1 + m]});
			String ans = rebuildVO(2 + 1 + m + 1, len - 1, ServerReturned, str);
			return ans;
		}else{
			throw new Exception("Client verified error!");
		}
	}
	
	public String ClientComputed(String[] ServerReturned, long[] x) throws Exception{
		String str;
		int len = ServerReturned.length;
		int l = Integer.parseInt(ServerReturned[0]);
		int r = Integer.parseInt(ServerReturned[1]);
		if(l == -1){
			m = r - 1;
			String[] strs = new String[m + 1];
			//System.out.println("==========");
			for(int i = 0; i < m + 1; i++){
				strs[i] = HashXtimes(ServerReturned[2 + i + 1], x[i]);
				//System.out.println(strs[i]);
			}
			str = SecurityUtility.computeGeneralHashValue(strs);
			//System.out.println("Computed : " + str);
			str = SecurityUtility.computeGeneralHashValue(new String[]{ServerReturned[2], str});
			String ans = rebuildVO(2 + 1 + m + 1, len - 1, ServerReturned, str);
			return ans;
		}else if(r == -1){
			m = l - 1;
			String[] strs = new String[m + 1];
			for(int i = 0; i < m + 1; i++){
				strs[i] = HashXtimes(ServerReturned[2 + i], x[i]);
			}
			str = SecurityUtility.computeGeneralHashValue(strs);
			str = SecurityUtility.computeGeneralHashValue(new String[]{str, ServerReturned[2 + 1 + m]});
			String ans = rebuildVO(2 + 1 + m + 1, len - 1, ServerReturned, str);
			return ans;

		}else{
			throw new Exception("Client verified error!");
		}
	}
	
	
	/**
	 * It seems if isL = true, x <= val can pass; if isR = true, x >= val can pass.
	 * 
	 * 
	 * */
	public static void main(String[] args) throws Exception{
		
		/**
		 * test for g function
		 * */
		long val = 20608, x = 20658, times = 1000;
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		long start = bean.getCurrentThreadCpuTime(), cputime;
		Gfunction gf = new Gfunction(val, 2);
		String[]  ServerReturned = null;
		start = bean.getCurrentThreadCpuTime();
		for(int i = 0 ; i < times; i ++){
			ServerReturned = gf.GenerateVeryfyPart(x, false);
		}
		cputime = bean.getCurrentThreadCpuTime() - start;
		System.out.println("SP consume:\t" + cputime / times / 1000000.0 + "ms");
		start = bean.getCurrentThreadCpuTime();
		for(int i = 0 ; i < times; i ++){
			if(gf.getDigest().equals(gf.ClientComputed(ServerReturned, gf.U - x)) == true){
				//System.out.println("Pass verification!");
			}else {
				System.out.println("Fail verification!");
				break;
			}
		}
		cputime = bean.getCurrentThreadCpuTime() - start;
		System.out.println("Client consume:\t" + cputime / times / 1000000.0 + "ms");
		val = 15169; 
		x = 15104;
		gf = new Gfunction(val, 2);
		ServerReturned = gf.GenerateVeryfyPart(x, true);
		start = System.currentTimeMillis();
		for(int i = 0 ; i < times; i ++){
			if(gf.getDigest().equals(gf.ClientComputed(ServerReturned, x - gf.L)) == true){
				//System.out.println("Pass verification!");
			}else {
				System.out.println("Fail verification!");
				break;
			}
		}
		
		/**
		 * test file in and file out
		 * */
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("./tmp/gf")));
		gf.writeToFile(dos);
		dos.close();
		DataInputStream dis = new DataInputStream(new FileInputStream(new File("./tmp/gf")));
		gf.readFromFile(dis);
		dis.close();
		cputime = System.currentTimeMillis() - start;
		System.out.println("Pass G function " + times + " times consume cputime : " + cputime);
	}

}
