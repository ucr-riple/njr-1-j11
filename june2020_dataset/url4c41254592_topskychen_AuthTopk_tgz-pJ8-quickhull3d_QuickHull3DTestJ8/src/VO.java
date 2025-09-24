//package utility.geo;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.HashSet;
//
//import jdbm.PrimaryTreeMap;
//import jdbm.RecordManager;
//import jdbm.RecordManagerFactory;
//
//import utility.Hasher;
//import utility.Statistics;
//import utility.Compare.DataOfPoint;
//import utility.Compare.DistanceCompare;
//import utility.Compare.buildBtreeOfPoints;
//import utility.security.IVo;
//import utility.security.Point;
//import utility.security.RSA;
//
//public class VO implements IVo{
//	public Statistics statistics = new Statistics();
//	public boolean DEBUG = false;
//	public boolean MIN_SAPN_TREE = false;
//	public int[] p_id, p_x, p_y;
//	public ArrayList<IVo> nearVos = new ArrayList<IVo>(), farVos = new ArrayList<IVo>();
//	RecordManager recordOfPoint = null;
//	RecordManager recordOfLine = null;
//	public RSA rsa;
//	public BigInteger condensedRSAValuebyServer;
//	public VO(){}
//	private void load_array(Integer [] id, Integer[] x, Integer[] y){
//		p_id = new int[id.length];
//		p_x = new int[id.length];
//		p_y = new int[id.length];
// 		for(int i = 0; i < id.length; i++){
//			p_id[i] = id[i];
//			p_x[i] = x[i];
//			p_y[i] = y[i];
//		}
//	}
//	private void load_btree(){
//		try {
//			recordOfPoint = RecordManagerFactory.createRecordManager(buildBtreeOfPoints.filename);
//			recordOfLine = RecordManagerFactory.createRecordManager(buildBtreeOfLines.filename);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public VO(Integer [] id, Integer[] x, Integer[] y, int q_x, int q_y, boolean IS_MIN_SAPN_TREE) throws IOException{
//		MIN_SAPN_TREE = IS_MIN_SAPN_TREE;
//		rsa = new RSA(1024);
//		long start = System.currentTimeMillis(), loadingStart;
//		try {
//			load_btree();
//			final PrimaryTreeMap<Long, byte[]> btOfPoint = recordOfPoint.treeMap("treemap");
//			PrimaryTreeMap<Long, byte[]> btOfLine = recordOfLine.treeMap("treemap");
//			load_array(id, x, y);
//			DataOfLine nnpoint = new DataOfLine();
//			nnpoint.loadFromBytes(btOfPoint.find((long)id[0]));
//			int dellen = nnpoint.ids.length;
//			DataOfLine nnline = new DataOfLine();
//			nnline.loadFromBytes(btOfLine.find((long)id[0]));
//			for(int i = 0; i < dellen; i++){
//				nnline.lines[i].GenerateVeryfyPart(new Point(q_x, q_y));
//				nearVos.add(nnline.lines[i]);
//			}
//			statistics.k_of_knn = id.length;
//			statistics.kind = true;
//			if(MIN_SAPN_TREE){
//				//load
//				long k_id = p_id[id.length - 1], cur_id = id.length - 1;
//				DataOfPoint dataOfPoint_k_id = new DataOfPoint();
//				loadingStart = System.currentTimeMillis();
//				dataOfPoint_k_id.loadFromBytes(btOfPoint.find(k_id));
//				statistics.load_time += System.currentTimeMillis() - loadingStart;
//				HashMap<Long, Long> idmap = new HashMap<Long, Long>();
//				//initial
//				//for far
//				ArrayList<Long> farids = new ArrayList<Long>();
//				HashSet<Long> dict = new HashSet<Long>();
//				
//				//for near
//				final Point qPoint = new Point(q_x, q_y);
//				dict.add(k_id);
//				
//				int[] weight = new int[id.length], cnt = new int[id.length];
//				for(int i = 0; i < id.length; i++){
//					weight[i] = -1;
//					cnt[i] = 0;
//					idmap.put((long)p_id[i], (long)i);
//				}
//				cnt[id.length - 1] = 1;
//				Line[] lines = new Line[id.length];
//				for(int i = 0 ; i < id.length - 1; i++){
//					dict.add((long) p_id[i]);
//					boolean found = false;
//					loadingStart = System.currentTimeMillis();
//					DataOfLine dataOfLine = new DataOfLine(btOfLine.find((long) p_id[(int) cur_id]));
//					statistics.load_time += System.currentTimeMillis() - loadingStart;
//					for(int  j = 0; j < dataOfLine.lines.length; j++){
//						long t_id = dataOfLine.ids[j];
//						if(idmap.containsKey(t_id)){
//							int tt = idmap.get(t_id).intValue();
//							if(weight[tt] != -1 || cnt[tt] != 0)continue;
//							weight[tt] = (int) cur_id;
//							lines[tt] = dataOfLine.lines[j];
//							long genStart = System.currentTimeMillis();
//							lines[tt].GenerateVeryfyPart(qPoint);
//							statistics.generate_time += System.currentTimeMillis() - genStart;
//						}
//					}
//					for(int j = 0; j < id.length; j++){
//						if(cnt[j] == 0)cur_id = j;
//						if(cnt[j] == 0 && weight[j] != -1){
//							found = true;
//							nearVos.add(lines[j]);
//							statistics.num_of_Lines ++;
//							break;
//						}
//					}
//					if(!found){
//						DataOfPoint dataOfPoint_t_id = new DataOfPoint();
//						loadingStart = System.currentTimeMillis();
//						dataOfPoint_t_id.loadFromBytes(btOfPoint.find((long) p_id[(int) cur_id]));
//						statistics.load_time += System.currentTimeMillis() - loadingStart;
//						DistanceCompare distanceCompare = new DistanceCompare(dataOfPoint_t_id.p, dataOfPoint_k_id.p, true);
//						long genStart = System.currentTimeMillis();
//						distanceCompare.GenerateVeryfyPart(qPoint);
//						statistics.generate_time += System.currentTimeMillis() - genStart;
//						nearVos.add(distanceCompare);
//						statistics.num_of_Pailliar ++;
//					}
//					cnt[(int) cur_id] = 1;
//				}
//
//				for(int i = 0; i < p_id.length; i++){
//					long t_id = p_id[i];
//					btOfPoint.find(t_id);
//					DataOfPoint dataOfPoint = new DataOfPoint();
//					loadingStart = System.currentTimeMillis();
//					dataOfPoint.loadFromBytes(btOfPoint.find(t_id));
//					statistics.load_time += System.currentTimeMillis() - loadingStart;
//					for(Long n_id : dataOfPoint.delaunayIds){
//						if(dict.contains(n_id) == false){
//							farids.add(n_id);
//							dict.add(n_id);
//						}
//					}
//				}
//				farids.add(k_id);
//				dict.clear();
//				idmap.clear();
//				weight = new int[farids.size()];
//				cnt = new int[farids.size()];
//				for(int i = 0; i < farids.size(); i++){
//					weight[i] = -1;
//					cnt[i] = 0;
//					idmap.put((long)farids.get(i), (long)i);
//				}
//				cnt[farids.size() - 1] = 1;
//				lines = new Line[farids.size()];
//				cur_id = farids.size() - 1;
//				
//				for(int i = 0 ; i < farids.size() - 1; i++){
//					boolean found = false;
//					loadingStart = System.currentTimeMillis();
//					DataOfLine dataOfLine = new DataOfLine(btOfLine.find(farids.get((int) cur_id)));
//					statistics.load_time += System.currentTimeMillis() - loadingStart;
//					for(int  j = 0; j < dataOfLine.lines.length; j++){
//						long t_id = dataOfLine.ids[j];
//						if(idmap.containsKey(t_id)){
//							int tt = idmap.get(t_id).intValue();
//							if(weight[tt] != -1 || cnt[tt] != 0)continue;
//							weight[tt] = (int) cur_id;
//							lines[tt] = dataOfLine.lines[j];
//							long genStart = System.currentTimeMillis();
//							lines[tt].GenerateVeryfyPart(qPoint);
//							statistics.generate_time += System.currentTimeMillis() - genStart;
//						}
//					}
//					for(int j = 0; j < farids.size(); j++){
//						if(cnt[j] == 0)cur_id = j;
//						if(cnt[j] == 0 && weight[j] != -1){
//							found = true;
//							farVos.add(lines[j]);
//							statistics.num_of_Lines ++;
//							break;
//						}
//					}
//					if(!found){
//						DataOfPoint dataOfPoint_t_id = new DataOfPoint();
//						loadingStart = System.currentTimeMillis();
//						dataOfPoint_t_id.loadFromBytes(btOfPoint.find(farids.get((int) cur_id)));
//						statistics.load_time += System.currentTimeMillis() - loadingStart;
//						DistanceCompare distanceCompare = new DistanceCompare(dataOfPoint_k_id.p, dataOfPoint_t_id.p, true);
//						long genStart = System.currentTimeMillis();
//						distanceCompare.GenerateVeryfyPart(qPoint);
//						statistics.generate_time += System.currentTimeMillis() - genStart;
//						farVos.add(distanceCompare);
//						statistics.num_of_Pailliar ++;
//					}
//					cnt[(int) cur_id] = 1;
//				}
//			}else{
//				long k_id = p_id[id.length - 1], cur_id = id.length - 1;
//				DataOfPoint dataOfPoint_k_id = new DataOfPoint();
//				dataOfPoint_k_id.loadFromBytes(btOfPoint.find(k_id));
//				HashMap<Long, Long> idmap = new HashMap<Long, Long>();
//				DataOfLine dataOfLine_k_id = new DataOfLine(btOfLine.find(k_id));
//				//initial
//				//for far
//				ArrayList<Long> farids = new ArrayList<Long>();
//				HashSet<Long> dict = new HashSet<Long>();
//				
//				//for near
//				final Point qPoint = new Point(q_x, q_y);
//				dict.add(k_id);
//				
//				int[] weight = new int[id.length];
//				for(int i = 0; i < id.length; i++){
//					weight[i] = -1;
//					idmap.put((long)p_id[i], (long)i);
//				}
//				Line[] lines = new Line[id.length];
//				for(int  j = 0; j < dataOfLine_k_id.lines.length; j++){
//					long t_id = dataOfLine_k_id.ids[j];
//					if(idmap.containsKey(t_id)){
//						int tt = idmap.get(t_id).intValue();
//						if(weight[tt] != -1)continue;
//						weight[tt] = (int) cur_id;
//						lines[tt] = dataOfLine_k_id.lines[j];
//						long genStart = System.currentTimeMillis();
//						lines[tt].GenerateVeryfyPart(qPoint);
//						statistics.generate_time += System.currentTimeMillis() - genStart;
//					}
//				}
//				for(int i = 0 ; i < id.length - 1; i++){
//					dict.add((long) p_id[i]);
//					if(weight[i] != -1){
//						nearVos.add(lines[i]);
//						statistics.num_of_Lines ++;
//					}else{
//						DataOfPoint dataOfPoint_t_id = new DataOfPoint();
//						dataOfPoint_t_id.loadFromBytes(btOfPoint.find((long) p_id[(int) cur_id]));
//						DistanceCompare distanceCompare = new DistanceCompare(dataOfPoint_t_id.p, dataOfPoint_k_id.p, true);
//						long genStart = System.currentTimeMillis();
//						distanceCompare.GenerateVeryfyPart(qPoint);
//						statistics.generate_time += System.currentTimeMillis() - genStart;
//						nearVos.add(distanceCompare);
//						statistics.num_of_Pailliar ++;
//					}
//				}
//
//				for(int i = 0; i < p_id.length; i++){
//					long t_id = p_id[i];
//					btOfPoint.find(t_id);
//					DataOfPoint dataOfPoint = new DataOfPoint();
//					dataOfPoint.loadFromBytes(btOfPoint.find(t_id));
//					for(Long n_id : dataOfPoint.delaunayIds){
//						if(dict.contains(n_id) == false){
//							farids.add(n_id);
//							dict.add(n_id);
//						}
//					}
//				}
//				
//				farids.add(k_id);
//				dict.clear();
//				idmap.clear();
//				weight = new int[farids.size()];
//				for(int i = 0; i < farids.size(); i++){
//					weight[i] = -1;
//					idmap.put((long)farids.get(i), (long)i);
//				}
//				lines = new Line[farids.size()];
//				cur_id = farids.size() - 1;
//				for(int  j = 0; j < dataOfLine_k_id.lines.length; j++){
//					long t_id = dataOfLine_k_id.ids[j];
//					if(idmap.containsKey(t_id)){
//						int tt = idmap.get(t_id).intValue();
//						if(weight[tt] != -1)continue;
//						weight[tt] = (int) cur_id;
//						lines[tt] = dataOfLine_k_id.lines[j];
//						long genStart = System.currentTimeMillis();
//						lines[tt].GenerateVeryfyPart(qPoint);
//						statistics.generate_time += System.currentTimeMillis() - genStart;
//					}
//				}
//				for(int i = 0 ; i < farids.size() - 1; i++){
//					if(weight[i] != -1){
//						farVos.add(lines[i]);
//						statistics.num_of_Lines ++;
//					}
//					else{
//						DataOfPoint dataOfPoint_t_id = new DataOfPoint();
//						dataOfPoint_t_id.loadFromBytes(btOfPoint.find(farids.get((int) cur_id)));
//						DistanceCompare distanceCompare = new DistanceCompare(dataOfPoint_k_id.p, dataOfPoint_t_id.p, true);
//						long genStart = System.currentTimeMillis();
//						distanceCompare.GenerateVeryfyPart(qPoint);
//						statistics.generate_time += System.currentTimeMillis() - genStart;
//						farVos.add(distanceCompare);
//						statistics.num_of_Pailliar ++;
//					}
//				}
//			}
//			buildRSA();
//			recordOfPoint.close();
//			recordOfLine.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		statistics.construction_time += System.currentTimeMillis() - start;
//	}
//
//	public void buildRSA(){
//		ArrayList<BigInteger> RSAfactors = new ArrayList<BigInteger>();
//		for(int i = 0 ; i < nearVos.size(); i++){
//			RSAfactors.add(rsa.encrypt(new BigInteger(nearVos.get(i).getHashcode(), 16)));
//		}
//		for(int i = 0; i < farVos.size(); i++){
//			RSAfactors.add(rsa.encrypt(new BigInteger(farVos.get(i).getHashcode(), 16)));
//		}
//		BigInteger ans = BigInteger.ONE;
//		long start = System.currentTimeMillis();
//		for(BigInteger x : RSAfactors){
//			ans.multiply(x).mod(rsa.n);
//		}
//		condensedRSAValuebyServer = ans;
//		statistics.generate_time += System.currentTimeMillis() - start;
//	}
//	@Override
//	public boolean ClientVerify(int q_x, int q_y) {
//		// TODO Auto-generated method stub
//		
//		long start = System.currentTimeMillis(), tot = 0, tmps = System.currentTimeMillis();
//		ArrayList<BigInteger> RSAfactors = new ArrayList<BigInteger>();
//		for(int i = 0; i < nearVos.size(); i++){
//			RSAfactors.add(new BigInteger(nearVos.get(i).getHashcode(), 16));
//			tmps = System.currentTimeMillis();
//			if(nearVos.get(i).ClientVerify(q_x, q_y) == false){
//				System.err.println("near vor :" + i);
//				return false;
//			}
//			tot += System.currentTimeMillis() - tmps;
//		}
//		for(int i = 0; i < farVos.size(); i++){
//			if(DEBUG)System.out.println(i);
//			RSAfactors.add(new BigInteger(farVos.get(i).getHashcode(), 16));
//			tmps = System.currentTimeMillis();
//			if(farVos.get(i).ClientVerify(q_x, q_y) == false){
//				System.err.println("far vor : " + i);
//				return false;
//			}
//			tot += System.currentTimeMillis() - tmps;
//		}
//		tmps = System.currentTimeMillis();
//		BigInteger condensedRSAValue = rsa.getCondensedRSA(RSAfactors.toArray(new BigInteger[0]));
//		if(condensedRSAValue.equals(rsa.decrypt(condensedRSAValuebyServer)) == false)return false;
//		tot += System.currentTimeMillis() - tmps;
//		statistics.verify_time += System.currentTimeMillis() - start;
//		System.out.println("total verify time : " + tot);
//		statistics.num_of_near_points = nearVos.size();
//		statistics.num_of_far_points = farVos.size();
//		statistics.num_of_gf_points = 0;
//		
//		return true;
//	}
//	/* (non-Javadoc)
//	 * @see utility.security.IVo#getHashcode()
//	 */
//	@Override
//	public String getDigest() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
