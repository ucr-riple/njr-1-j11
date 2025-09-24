// Spatial Index Library
//
// Copyright (C) 2002  Navel Ltd.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License aint with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Contact information:
//  Mailing address:
//    Marios Hadjieleftheriou
//    University of California, Riverside
//    Department of Computer Science
//    Surge Building, Room 310
//    Riverside, CA 92521
//
//  Email:
//    marioh@cs.ucr.edu

package spatialindex.rtree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

import spatialindex.core.IData;
import spatialindex.core.IEntry;
import spatialindex.core.INearestNeighborComparator;
import spatialindex.core.INodeCommand;
import spatialindex.core.IQueryStrategy;
import spatialindex.core.IShape;
import spatialindex.core.ISpatialIndex;
import spatialindex.core.IStatistics;
import spatialindex.core.IVisitor;
import spatialindex.core.Point;
import spatialindex.core.RWLock;
import spatialindex.core.Region;
import spatialindex.io.DiskStorageManager;
import spatialindex.io.IBuffer;
import spatialindex.io.IStorageManager;
import spatialindex.io.InvalidPageException;
import spatialindex.io.RandomEvictionsBuffer;
import spatialindex.setting.PropertySet;
import spatialindex.spatialindex.SpatialIndex;
import utility.Compare.DataOfPoint;
import utility.geo.DataOfLine;
import utility.security.DataIO;
import utility.security.SecurityUtility;

public class RTree implements ISpatialIndex {
	
	public RandomAccessFile rafPoint = null;
	public RandomAccessFile rafLine = null;
	public HashMap<Long, long[]> tOfPoint = null;
	public HashMap<Long, long[]> tOfLine = null;
	public HashMap<Long, DataOfPoint> buffer_Points = new HashMap<Long, DataOfPoint>();
	public HashMap<Long, DataOfLine> buffer_Lines = new HashMap<Long, DataOfLine>();
	public String destFileNamePoint, destFileNameLine;
	IShape queryRegion = null;

	RWLock m_rwLock;

	IStorageManager m_pStorageManager;

	int m_rootID;
	int m_headerID;

	/**
	 * indicates different trees, see SpatialIndex.RtreeVariantLinear
	 */
	int m_treeVariant;

	double m_fillFactor;

	int m_indexCapacity;

	int m_leafCapacity;

	int m_nearMinimumOverlapFactor;
	// The R*-Tree 'p' constant, for calculating nearly minimum overlap cost.
	// [Beckmann, Kriegel, Schneider, Seeger 'The R*-tree: An efficient and Robust Access Method
	// for Points and Rectangles, Section 4.1]

	double m_splitDistributionFactor;
	// The R*-Tree 'm' constant, for calculating spliting distributions.
	// [Beckmann, Kriegel, Schneider, Seeger 'The R*-tree: An efficient and Robust Access Method
	// for Points and Rectangles, Section 4.2]

	double m_reinsertFactor;
	// The R*-Tree 'p' constant, for removing entries at reinserts.
	// [Beckmann, Kriegel, Schneider, Seeger 'The R*-tree: An efficient and Robust Access Method
	//  for Points and Rectangles, Section 4.3]

	int m_dimension;

	private int update_node_size;
	Region m_infiniteRegion;

	Statistics m_stats;

	ArrayList<INodeCommand> m_writeNodeCommands = new ArrayList<INodeCommand>();
	ArrayList<INodeCommand> m_readNodeCommands = new ArrayList<INodeCommand>();
	ArrayList<INodeCommand> m_deleteNodeCommands = new ArrayList<INodeCommand>();

	/**
	 * @return the update_node_size
	 */
	public int getUpdate_node_size() {
		return update_node_size;
	}

	/**
	 * @param update_node_size the update_node_size to set
	 */
	public void setUpdate_node_size(int update_node_size) {
		this.update_node_size = update_node_size;
	}

	public void setDestFileNamePoint(String name){
		destFileNamePoint = name;
	}
	
	public void setDestFileNameLine(String name){
		destFileNameLine = name;
	}
	
	public static RTree createRTree(String[] args) {
		try {
			if (args.length != 6) {
				println("Usage: RTreeLoad input_file tree_file capacity query_type [intersection | 10NN].");
				System.exit(-1);
			}

			LineNumberReader lr = null;

			try {
				lr = new LineNumberReader(new FileReader(args[0]));
			} catch (FileNotFoundException e) {
				println("Cannot open data file " + args[0] + ".");
				System.exit(-1);
			}

			// Create a disk based storage manager.
			PropertySet ps = new PropertySet();

			Boolean b = new Boolean(true);
			ps.setProperty("Overwrite", b);
			//overwrite the file if it exists.

			ps.setProperty("FileName", args[1]);
			// .idx and .dat extensions will be added.

			Integer i = new Integer(4096);
			ps.setProperty("PageSize", i);
			// specify the page size. Since the index may also contain user defined data
			// there is no way to know how big a single node may become. The storage manager
			// will use multiple pages per node if needed. Off course this will slow down performance.

			IStorageManager diskfile = new DiskStorageManager(ps);

			IBuffer file = new RandomEvictionsBuffer(diskfile, 10, false);
			// applies a main memory random buffer on top of the persistent storage manager
			// (LRU buffer, etc can be created the same way).

			// Create a new, empty, RTree with dimensionality 2, minimum load 70%, using "file" as
			// the StorageManager and the RSTAR splitting policy.
			PropertySet ps2 = new PropertySet();

			Double f = new Double(0.7);
			ps2.setProperty("FillFactor", f);

			i = new Integer(args[2]);
			ps2.setProperty("IndexCapacity", i);
			ps2.setProperty("LeafCapacity", i);
			// Index capacity and leaf capacity may be different.

			i = new Integer(3);
			ps2.setProperty("Dimension", i);
			ps2.setProperty("destFileNamePoint", args[4]);
			ps2.setProperty("destFileNameLine", args[5]);
			
			ISpatialIndex tree = new RTree(ps2, file);

			int count = 0;
			//int indexIO = 0;
			//int leafIO = 0;
			int id = 0, op;
			double x1, y1, z1;
			double[] f1 = new double[3];
			double[] f2 = new double[3];

			long start = System.currentTimeMillis();
			String line = lr.readLine();

			while (line != null) {
				StringTokenizer st = new StringTokenizer(line);
//				op = new Integer(st.nextToken()).intValue();
//				id = new Integer(st.nextToken()).intValue();
//				x1 = new Double(st.nextToken()).doubleValue();
//				y1 = new Double(st.nextToken()).doubleValue();
//				x2 = new Double(st.nextToken()).doubleValue();
//				y2 = new Double(st.nextToken()).doubleValue();

				op = 1;
				st.nextToken();
				x1 = Double.parseDouble(st.nextToken());
				y1 = Double.parseDouble(st.nextToken());
				z1 = Double.parseDouble(st.nextToken());
				
				if (op == 0) {
					//delete

					f1[0] = x1;
					f1[1] = y1;
					f1[2] = Math.sqrt(z1);
					f2[0] = x1;
					f2[1] = y1;
					f2[2] = Math.sqrt(z1);
					
					Region r = new Region(f1, f2);

					if (tree.deleteData(r, id) == false) {
						println("Cannot delete id: " + id + " , count: " + count + ".");
						System.exit(-1);
					}
				} else if (op == 1) {
					//insert


					f1[0] = x1;
					f1[1] = y1;
					f1[2] = Math.sqrt(z1);
					f2[0] = x1;
					f2[1] = y1;
					f2[2] = Math.sqrt(z1);
					
					Region r = new Region(f1, f2);

					//String data = r.toString();
					// associate some data with this region. I will use a string that represents the
					// region itself, as an example.
					// NOTE: It is not necessary to associate any data here. A null pointer can be used. In that
					// case you should store the data externally. The index will provide the data IDs of
					// the answers to any query, which can be used to access the actual data from the external
					// storage (e.g. a hash table or a database table, etc.).
					// Storing the data in the index is convinient and in case a clustered storage manager is
					// provided (one that stores any node in consecutive pages) performance will improve substantially,
					// since disk accesses will be mostly sequential. On the other hand, the index will need to
					// manipulate the data, resulting in larger overhead. If you use a main memory storage manager,
					// storing the data externally is highly recommended (clustering has no effect).
					// A clustered storage manager is NOT provided yet.
					// Also you will have to take care of converting you data to and from binary format, since only
					// array of bytes can be inserted in the index (see RTree::Node::load and RTree::Node::store for
					// an example of how to do that).

					//tree.insertData(data.getBytes(), r, id);

					tree.insertData(null, r, id);
					id ++;
					// example of passing a null pointer as the associated data.
				} else if (op == 2) {
					//query


					f1[0] = x1;
					f1[1] = y1;
					f1[2] = Math.sqrt(z1);
					f2[0] = x1;
					f2[1] = y1;
					f2[2] = Math.sqrt(z1);
					
					Region r = new Region(f1, f2);

					//MyVisitor vis = new MyVisitor();
					//SecurityVisitor vis = new SecurityVisitor(r);

					if (args[3].equals("intersection")) {
						//tree.intersectionQuery(r, vis);
						// this will find all data that intersect with the query range.
					} else if (args[3].equals("10NN")) {
						//Point p = new Point(f1);
						//tree.nearestNeighborQuery(10, p, vis);
						// this will find the 10 nearest neighbors.
					} else {
						println("Unknown query type.");
						System.exit(-1);
					}
				}

				if (count % 1000 == 0) {
					System.out.print(".");
					if(count % 10000 == 0)println(count + "");
				}

				count++;
				line = lr.readLine();
			}
//			System.out.println("Total id :\t" + id);
			long end = System.currentTimeMillis();

			println("Operations: " + count);
			println(tree.toString());
			println("Minutes: " + (end - start) / 1000.0f / 60.0f);

			// since we created a new RTree, the PropertySet that was used to initialize the structure
			// now contains the IndexIdentifier property, which can be used later to reuse the index.
			// (Remember that multiple indices may reside in the same storage manager at the same time
			//  and every one is accessed using its unique IndexIdentifier).
			Integer indexID = (Integer) ps2.getProperty("IndexIdentifier");
			println("Index ID: " + indexID);

			boolean ret = tree.isIndexValid();
			if (ret == false) {
				println("Structure is INVALID!");
			}

			// flush all pending changes to persistent storage (needed since Java might not call finalize when JVM exits).
			tree.flush();
			return (RTree) tree;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void loadIndexOfPoints(){
		try {
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(destFileNamePoint + ".idx"))));
			rafPoint = new RandomAccessFile(destFileNamePoint + ".dat", "r");
			tOfPoint = new HashMap<Long, long[]>();
			while(dis.available() > 0){
				tOfPoint.put(dis.readLong(), new long[]{dis.readLong(), dis.readLong()});
			}
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeIndex(){
		try {
			if(rafPoint != null){
				rafPoint.close();
			}
			if(rafLine != null){
				rafLine.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadIndexOfLines(){
		try {
			DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(destFileNameLine + ".idx"))));
			rafLine = new RandomAccessFile(destFileNameLine + ".dat", "r");
			tOfLine = new HashMap<Long, long[]>();
			System.out.println("Load line index:\t" + destFileNameLine);
			while(dis.available() > 0){
				tOfLine.put(dis.readLong(), new long[]{dis.readLong(), dis.readLong()});
			}
			dis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void loadIndex(){
		loadIndexOfPoints();
		loadIndexOfLines();
	}
	
	public DataOfPoint loadDataOfPointFromIndex(long id){
		if(VO.isSoloAuth){
			if(buffer_Points.containsKey(id))return buffer_Points.get(id);
		}
		if(rafPoint == null || !tOfPoint.containsKey(id)){
			loadIndexOfPoints();
		}
		if(tOfPoint.containsKey(id) == false){
			System.err.println("Not found point :\t" + id);
			return null;
		}
		long[] tmp = tOfPoint.get(id);
		long pos = tmp[0], len = tmp[1];
		byte[] data = new byte[(int)len];
		try {
			rafPoint.seek(pos);
			rafPoint.read(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("id :\t" + id);
		DataOfPoint dataOfPoint = new DataOfPoint(data);
		if(VO.isSoloAuth){
			buffer_Points.put(id, dataOfPoint);
		}
		return dataOfPoint;
	}
	
	public DataOfLine loadDataOfLineFromIndex(long id1, long id2){
		long id = DataOfLine.calcLineId(id1, id2);
		if(VO.isSoloAuth){
			if(buffer_Lines.containsKey(id))return buffer_Lines.get(id);
		}
		if(tOfLine == null){
			loadIndexOfLines();
		}
		if(tOfLine.containsKey(id) == false){
			//System.err.println("Not found line:\t" + id1 + ", " + id2);
//			System.out.print("x");
			return null;
		}
		long[] tmp = tOfLine.get(id);
		long pos = tmp[0], len = tmp[1];
		byte[] data = new byte[(int)len];
		try {
			rafLine.seek(pos);
			rafLine.read(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataOfLine dataOfLine = new DataOfLine(data);
		if(VO.isSoloAuth){
			buffer_Lines.put(id, dataOfLine);
		}
		return dataOfLine;
	}
	
	public DataOfLine loadDataOfLineFromBtree(int id1, int id2){
		long id = DataOfLine.calcLineId(id1, id2);
		if(VO.isSoloAuth){
			if(buffer_Lines.containsKey(id))return buffer_Lines.get(id);
		}
		if(tOfLine == null){
			loadIndexOfLines();
		}
		if(tOfLine.containsKey(id) == false){
//			System.out.print("x");
//			System.err.println("Not found line:\t" + id1 + ", " + id2);
			return null;
		}
		long[] tmp = tOfLine.get(id);
		long pos = tmp[0], len = tmp[1];
		byte[] data = new byte[(int)len];
		try {
			rafLine.seek(pos);
			rafLine.read(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataOfLine dataOfLine = new DataOfLine(data);
		if(VO.isSoloAuth){
			buffer_Lines.put(id, dataOfLine);
		}
		return dataOfLine;
	}
	
	/**
	 * ps contains FillFactor, IndexCapacity, LeafCapacity, Dimension. Sm is the
	 * io manager of r-tree file
	 * 
	 * @param ps
	 * @param sm
	 */
	public RTree(PropertySet ps, IStorageManager sm) {
		m_rwLock = new RWLock();
		m_pStorageManager = sm;
		m_rootID = IStorageManager.NewPage;
		m_headerID = IStorageManager.NewPage;
		m_treeVariant = SpatialIndex.RtreeVariantRstar;
		m_fillFactor = 0.7f;
		m_indexCapacity = 100;
		m_leafCapacity = 100;
		m_nearMinimumOverlapFactor = 32;
		m_splitDistributionFactor = 0.4f;
		m_reinsertFactor = 0.3f;
		m_dimension = 2;

		m_infiniteRegion = new Region();
		m_stats = new Statistics();

		destFileNamePoint = (String)ps.getProperty("destFileNamePoint");
		destFileNameLine = (String)ps.getProperty("destFileNameLine");
		
		Object var = ps.getProperty("IndexIdentifier");
		if (var != null) {
			if (!(var instanceof Integer)) {
				throw new IllegalArgumentException("Property IndexIdentifier must an Integer");
			}
			m_headerID = ((Integer) var).intValue();
			try {
				initOld(ps);
			} catch (IOException e) {
				System.err.println(e);
				throw new IllegalStateException("initOld failed with IOException");
			}
		} else {
			try {
				initNew(ps);
			} catch (IOException e) {
				System.err.println(e);
				throw new IllegalStateException("initNew failed with IOException");
			}
			Integer i = new Integer(m_headerID);
			ps.setProperty("IndexIdentifier", i);
		}
	}

	//
	// ISpatialIndex interface
	//

	/**
	 * insert data with id and shape
	 */
	@Override
	public void insertData(final byte[] data, final IShape shape, int id) {
		try {
			insertData(data, shape, id, false);
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * updateDigest is used in experiment only!
	 * 
	 * @param data
	 * @param shape
	 * @param id
	 * @param nodeChangeListener
	 *            save the nodes changed in insertion
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	public void insertData(final byte[] data, final IShape shape, int id, boolean updateDigest) throws IndexOutOfBoundsException, Exception {
		ArrayList<Integer> changedNodeID = null;
		if (updateDigest)
			changedNodeID = new ArrayList<Integer>();
		if (shape.getDimension() != m_dimension) {
			throw new IllegalArgumentException("insertData: Shape has the wrong number of dimensions.");
		}

		m_rwLock.write_lock();

		try {
			Region mbr = shape.getMBR();

			byte[] buffer = null;

			if (data != null && data.length > 0) {
				buffer = new byte[data.length];
				System.arraycopy(data, 0, buffer, 0, data.length);
			}

			insertData_impl(buffer, mbr, id, changedNodeID);
			// the buffer is stored in the tree. Do not delete here.
		} finally {
			m_rwLock.write_unlock();
		}

		if (changedNodeID != null) {
			//remove duplicated ID
			Collections.sort(changedNodeID);
			for (int i = 0; i < changedNodeID.size() - 1;) {
				if (changedNodeID.get(i) == changedNodeID.get(i + 1)) {
					changedNodeID.remove(i + 1);
				} else {
					i++;
				}
			}
			//limit size
			/*while (changedNodeID.size() > 3) {
				changedNodeID.remove(changedNodeID.size() - 1);
			}*/
			//sort by level
			ArrayList<Integer> lvs = new ArrayList<Integer>();
			for (Integer x : changedNodeID) {
				Node n = readNode(x);
				if(n != null)
				lvs.add(n.m_level);
			}
			//System.out.println("idsize : " + lvs.size());
			while (lvs.size() > 0) {
				int minLv = Integer.MAX_VALUE;
				int minID = -1;
				//find min lv node
				for (int i = 0; i < lvs.size(); i++) {
					if (lvs.get(i) < minLv) {
						minLv = lvs.get(i);
						minID = i;
					}
				}
				int rebuildID = lvs.remove(minID);
				changedNodeID.remove(minID);
				//build node's digest
				SecurityNode node = new SecurityNode();
				try {
					update_node_size += node.buildSelfSecurityInfo(this, readNode(rebuildID));
				} catch (Exception e) {
					update_node_size += node.buildSelfSecurityInfo(this, readNode(readNode(m_rootID).getChildIdentifier(0)));
				}
			}
		}
	}

	/**
	 * delete data with id and shape
	 */
	@Override
	public boolean deleteData(final IShape shape, int id) {
		try {
			return deleteData(shape, id, false);
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * delete data with id and shape, updateDigest used in experiments only!
	 * @throws Exception 
	 * @throws IndexOutOfBoundsException 
	 */
	public boolean deleteData(final IShape shape, int id, boolean updateDigest) throws IndexOutOfBoundsException, Exception {
		ArrayList<Integer> changedNodeID = null;
		if (updateDigest)
			changedNodeID = new ArrayList<Integer>();
		if (shape.getDimension() != m_dimension) {
			throw new IllegalArgumentException("deleteData: Shape has the wrong number of dimensions.");
		}

		m_rwLock.write_lock();

		boolean result = false;

		try {
			Region mbr = shape.getMBR();
			result = deleteData_impl(mbr, id, changedNodeID);
		} finally {
			m_rwLock.write_unlock();
		}

		if (changedNodeID != null) {
			//remove duplicated ID
			Collections.sort(changedNodeID);
			for (int i = 0; i < changedNodeID.size() - 1;) {
				if (changedNodeID.get(i) == changedNodeID.get(i + 1)) {
					changedNodeID.remove(i + 1);
				} else {
					i++;
				}
			}
			//limit size
			/*while (changedNodeID.size() > 3) {
				changedNodeID.remove(changedNodeID.size() - 1);
			}*/
			//sort by level
			ArrayList<Integer> lvs = new ArrayList<Integer>();
			for (Integer x : changedNodeID) {
				Node n = readNode(x);
				if(n != null)
					lvs.add(n.m_level);
			}
			while (lvs.size() > 0) {
				int minLv = Integer.MAX_VALUE;
				int minID = -1;
				//find min lv node
				for (int i = 0; i < lvs.size(); i++) {
					if (lvs.get(i) < minLv) {
						minLv = lvs.get(i);
						minID = i;
					}
				}
				int rebuildID = lvs.remove(minID);
				changedNodeID.remove(minID);
				//build node's digest
				SecurityNode node = new SecurityNode();
				try {
					update_node_size += node.buildSelfSecurityInfo(this, readNode(rebuildID));
				} catch (Exception e) {
					update_node_size += node.buildSelfSecurityInfo(this, readNode(readNode(m_rootID).getChildIdentifier(0)));
				}
			}
		}

		return result;
	}

	@Override
	public void containmentQuery(final IShape query, final IVisitor v) {
		if (query.getDimension() != m_dimension) {
			throw new IllegalArgumentException("containmentQuery: Shape has the wrong number of dimensions.");
		}
		rangeQuery(SpatialIndex.ContainmentQuery, query, v);
	}

	@Override
	public void intersectionQuery(final IShape query, final IVisitor v) {
		if (query.getDimension() != m_dimension) {
			throw new IllegalArgumentException("intersectionQuery: Shape has the wrong number of dimensions.");
		}
		rangeQuery(SpatialIndex.IntersectionQuery, query, v);
	}

	@Override
	public void pointLocationQuery(final IShape query, final IVisitor v) {
		if (query.getDimension() != m_dimension) {
			throw new IllegalArgumentException("pointLocationQuery: Shape has the wrong number of dimensions.");
		}

		Region r = null;
		if (query instanceof Point) {
			r = new Region((Point) query, (Point) query);
		} else if (query instanceof Region) {
			r = (Region) query;
		} else {
			throw new IllegalArgumentException("pointLocationQuery: IShape can be Point or Region only.");
		}

		rangeQuery(SpatialIndex.IntersectionQuery, r, v);
	}

	@Override
	public void nearestNeighborQuery(int k, final IShape query, final IVisitor v, final INearestNeighborComparator nnc) {
		// TODO Need read nearestNeighborQuery
		if (query.getDimension() != m_dimension) {
			throw new IllegalArgumentException("nearestNeighborQuery: Shape has the wrong number of dimensions.");
		}

		m_rwLock.read_lock();

		try {
			// I need a priority queue here. It turns out that TreeSet sorts unique keys only and since I am
			// sorting according to distances, it is not assured that all distances will be unique. TreeMap
			// also sorts unique keys. Thus, I am simulating a priority queue using an ArrayList and binarySearch.
			ArrayList<NNEntry> queue = new ArrayList<NNEntry>();

			Node n = readNode(m_rootID);
			queue.add(new NNEntry(n, 0.0));

			int count = 0;
			double knearest = 0.0;

			while (queue.size() != 0) {
				NNEntry first = queue.remove(0);

				if (first.m_pEntry instanceof Node) {
					n = (Node) first.m_pEntry;
					v.visitNode(n);

					for (int cChild = 0; cChild < n.m_children; cChild++) {
						IEntry e;

						if (n.m_level == 0) {
							e = new Data(n.m_pData[cChild], n.m_pMBR[cChild], n.m_pIdentifier[cChild]);
						} else {
							e = readNode(n.m_pIdentifier[cChild]);
						}

						NNEntry e2 = new NNEntry(e, nnc.getMinimumDistance(query, e));

						// Why don't I use a TreeSet here? See comment above...
						int loc = Collections.binarySearch(queue, e2, new NNEntryComparator());
						if (loc >= 0) {
							queue.add(loc, e2);
						} else {
							queue.add((-loc - 1), e2);
						}
					}
				} else {
					// report all nearest neighbors with equal furthest distances.
					// (neighbors can be more than k, if many happen to have the same
					//  furthest distance).
					if (count >= k) {
						break;
					}

					v.visitData((IData) first.m_pEntry);
					m_stats.m_queryResults++;
					count++;
					knearest = first.m_minDist;
				}
			}
		} finally {
			m_rwLock.read_unlock();
		}
	}

	@Override
	public void nearestNeighborQuery(int k, final IShape query, final IVisitor v) {
		if (query.getDimension() != m_dimension) {
			throw new IllegalArgumentException("nearestNeighborQuery: Shape has the wrong number of dimensions.");
		}
		NNComparator nnc = new NNComparator();
		nearestNeighborQuery(k, query, v, nnc);
	}

	@Override
	public void queryStrategy(final IQueryStrategy qs) {
		m_rwLock.read_lock();

		int[] next = new int[] { m_rootID };

		try {
			while (true) {
				Node n = readNode(next[0]);
				boolean[] hasNext = new boolean[] { false };
				qs.getNextEntry(n, next, hasNext);
				if (hasNext[0] == false) {
					break;
				}
			}
		} finally {
			m_rwLock.read_unlock();
		}
	}

	/**
	 * get the RTree property set
	 */
	@Override
	public PropertySet getIndexProperties() {
		PropertySet pRet = new PropertySet();

		// dimension
		pRet.setProperty("Dimension", new Integer(m_dimension));

		// index capacity
		pRet.setProperty("IndexCapacity", new Integer(m_indexCapacity));

		// leaf capacity
		pRet.setProperty("LeafCapacity", new Integer(m_leafCapacity));

		// R-tree variant
		pRet.setProperty("TreeVariant", new Integer(m_treeVariant));

		// fill factor
		pRet.setProperty("FillFactor", new Double(m_fillFactor));

		// near minimum overlap factor
		pRet.setProperty("NearMinimumOverlapFactor", new Integer(m_nearMinimumOverlapFactor));

		// split distribution factor
		pRet.setProperty("SplitDistributionFactor", new Double(m_splitDistributionFactor));

		// reinsert factor
		pRet.setProperty("ReinsertFactor", new Double(m_reinsertFactor));

		return pRet;
	}

	@Override
	public void addWriteNodeCommand(INodeCommand nc) {
		m_writeNodeCommands.add(nc);
	}

	@Override
	public void addReadNodeCommand(INodeCommand nc) {
		m_readNodeCommands.add(nc);
	}

	@Override
	public void addDeleteNodeCommand(INodeCommand nc) {
		m_deleteNodeCommands.add(nc);
	}

	/**
	 * verify the whole RTree index
	 */
	@Override
	public boolean isIndexValid() {
		boolean ret = true;
		Stack<ValidateEntry> st = new Stack<ValidateEntry>();
		Node root = readNode(m_rootID);

		//check root's level
		if (root.m_level != m_stats.m_treeHeight - 1) {
			System.err.println("Invalid tree height");
			return false;
		}

		HashMap<Integer, Integer> nodesInLevel = new HashMap<Integer, Integer>();
		nodesInLevel.put(new Integer(root.m_level), new Integer(1));

		//put root into VE, iteration
		ValidateEntry e = new ValidateEntry(root.m_nodeMBR, root);
		st.push(e);

		while (!st.empty()) {
			e = st.pop();//for every internal node

			Region tmpRegion = (Region) m_infiniteRegion.clone();
			//build MBR and verify it
			for (int cDim = 0; cDim < m_dimension; cDim++) {
				tmpRegion.m_pLow[cDim] = Double.POSITIVE_INFINITY;
				tmpRegion.m_pHigh[cDim] = Double.NEGATIVE_INFINITY;

				for (int cChild = 0; cChild < e.m_pNode.m_children; cChild++) {
					tmpRegion.m_pLow[cDim] = Math.min(tmpRegion.m_pLow[cDim], e.m_pNode.m_pMBR[cChild].m_pLow[cDim]);
					tmpRegion.m_pHigh[cDim] = Math.max(tmpRegion.m_pHigh[cDim], e.m_pNode.m_pMBR[cChild].m_pHigh[cDim]);
				}
			}

			if (!tmpRegion.equals(e.m_pNode.m_nodeMBR)) {
				System.err.println("Invalid parent information");
				ret = false;
			} else if (!tmpRegion.equals(e.m_parentMBR)) {
				System.err.println("Error in parent");
				ret = false;
			}

			if (e.m_pNode.m_level != 0) {
				for (int cChild = 0; cChild < e.m_pNode.m_children; cChild++) {
					ValidateEntry tmpEntry = new ValidateEntry(e.m_pNode.m_pMBR[cChild], readNode(e.m_pNode.m_pIdentifier[cChild]));

					if (!nodesInLevel.containsKey(new Integer(tmpEntry.m_pNode.m_level))) {
						nodesInLevel.put(new Integer(tmpEntry.m_pNode.m_level), new Integer(1));
					} else {
						int i = nodesInLevel.get(new Integer(tmpEntry.m_pNode.m_level)).intValue();
						nodesInLevel.put(new Integer(tmpEntry.m_pNode.m_level), new Integer(i + 1));
					}

					st.push(tmpEntry);
				}
			}
		}

		int nodes = 0;
		for (int cLevel = 0; cLevel < m_stats.m_treeHeight; cLevel++) {
			int i1 = nodesInLevel.get(new Integer(cLevel)).intValue();
			int i2 = m_stats.m_nodesInLevel.get(cLevel).intValue();
			if (i1 != i2) {
				System.err.println("Invalid nodesInLevel information");
				ret = false;
			}
			nodes += i2;
		}

		if (nodes != m_stats.m_nodes) {
			System.err.println("Invalid number of nodes information");
			ret = false;
		}

		return ret;
	}

	@Override
	public IStatistics getStatistics() {
		return (IStatistics) m_stats.clone();
	}

	@Override
	public void flush() throws IllegalStateException {
		try {
			storeHeader();
			m_pStorageManager.flush();
		} catch (IOException e) {
			System.err.println(e);
			throw new IllegalStateException("flush failed with IOException");
		}
	}

	/**
	 * only works in 2-dimension condition
	 * 
	 * @param g
	 * @param r
	 */
	public void drawSelf(Graphics g, Rectangle r) {
		//find the max-minimum board
		Region mbr = readNode(m_rootID).m_nodeMBR;
		double a = r.width / (mbr.getHigh(0) - mbr.getLow(0));
		double b = r.height / (mbr.getHigh(1) - mbr.getLow(1));
		double ratio = a > b ? b : a;
		//draw to graphics
		readNode(m_rootID).drawSelf(g, ratio);
		//draw query
		if (queryRegion != null) {
			g.setColor(Color.RED);
			double x1 = queryRegion.getMBR().m_pLow[0], x2 = queryRegion.getMBR().m_pHigh[0];
			double y1 = queryRegion.getMBR().m_pLow[1], y2 = queryRegion.getMBR().m_pHigh[1];
			g.drawRect((int) (x1 * ratio), (int) (y1 * ratio), (int) ((x2 - x1) * ratio), (int) ((y2 - y1) * ratio));
		}
	}

	//
	// Internals
	//

	private static void println(String string) {
		System.out.println(string);
		//SRTreeShower.printLog(string);
	}

	/**
	 * create a new RTree and save it's root node
	 */
	private void initNew(PropertySet ps) throws IOException {
		Object var;

		// tree variant.
		var = ps.getProperty("TreeVariant");
		if (var != null) {
			if (var instanceof Integer) {
				int i = ((Integer) var).intValue();
				if (i != SpatialIndex.RtreeVariantLinear && i != SpatialIndex.RtreeVariantQuadratic && i != SpatialIndex.RtreeVariantRstar) {
					throw new IllegalArgumentException("Property TreeVariant not a valid variant");
				}
				m_treeVariant = i;
			} else {
				throw new IllegalArgumentException("Property TreeVariant must be an Integer");
			}
		}

		// fill factor.
		var = ps.getProperty("FillFactor");
		if (var != null) {
			if (var instanceof Double) {
				double f = ((Double) var).doubleValue();
				if (f <= 0.0f || f >= 1.0f) {
					throw new IllegalArgumentException("Property FillFactor must be in (0.0, 1.0)");
				}
				m_fillFactor = f;
			} else {
				throw new IllegalArgumentException("Property FillFactor must be a Double");
			}
		}

		// index capacity.
		var = ps.getProperty("IndexCapacity");
		if (var != null) {
			if (var instanceof Integer) {
				int i = ((Integer) var).intValue();
				if (i < 3) {
					throw new IllegalArgumentException("Property IndexCapacity must be >= 3");
				}
				m_indexCapacity = i;
			} else {
				throw new IllegalArgumentException("Property IndexCapacity must be an Integer");
			}
		}

		// leaf capacity.
		var = ps.getProperty("LeafCapacity");
		if (var != null) {
			if (var instanceof Integer) {
				int i = ((Integer) var).intValue();
				if (i < 3) {
					throw new IllegalArgumentException("Property LeafCapacity must be >= 3");
				}
				m_leafCapacity = i;
			} else {
				throw new IllegalArgumentException("Property LeafCapacity must be an Integer");
			}
		}

		// near minimum overlap factor.
		var = ps.getProperty("NearMinimumOverlapFactor");
		if (var != null) {
			if (var instanceof Integer) {
				int i = ((Integer) var).intValue();
				if (i < 1 || i > m_indexCapacity || i > m_leafCapacity) {
					throw new IllegalArgumentException("Property NearMinimumOverlapFactor must be less than both index and leaf capacities");
				}
				m_nearMinimumOverlapFactor = i;
			} else {
				throw new IllegalArgumentException("Property NearMinimumOverlapFactor must be an Integer");
			}
		}

		// split distribution factor.
		var = ps.getProperty("SplitDistributionFactor");
		if (var != null) {
			if (var instanceof Double) {
				double f = ((Double) var).doubleValue();
				if (f <= 0.0f || f >= 1.0f) {
					throw new IllegalArgumentException("Property SplitDistributionFactor must be in (0.0, 1.0)");
				}
				m_splitDistributionFactor = f;
			} else {
				throw new IllegalArgumentException("Property SplitDistriburionFactor must be a Double");
			}
		}

		// reinsert factor.
		var = ps.getProperty("ReinsertFactor");
		if (var != null) {
			if (var instanceof Double) {
				double f = ((Double) var).doubleValue();
				if (f <= 0.0f || f >= 1.0f) {
					throw new IllegalArgumentException("Property ReinsertFactor must be in (0.0, 1.0)");
				}
				m_reinsertFactor = f;
			} else {
				throw new IllegalArgumentException("Property ReinsertFactor must be a Double");
			}
		}

		// dimension
		var = ps.getProperty("Dimension");
		if (var != null) {
			if (var instanceof Integer) {
				int i = ((Integer) var).intValue();
				if (i <= 1) {
					throw new IllegalArgumentException("Property Dimension must be >= 1");
				}
				m_dimension = i;
			} else {
				throw new IllegalArgumentException("Property Dimension must be an Integer");
			}
		}

		m_infiniteRegion.m_pLow = new double[m_dimension];
		m_infiniteRegion.m_pHigh = new double[m_dimension];

		for (int cDim = 0; cDim < m_dimension; cDim++) {
			m_infiniteRegion.m_pLow[cDim] = Double.POSITIVE_INFINITY;
			m_infiniteRegion.m_pHigh[cDim] = Double.NEGATIVE_INFINITY;
		}

		m_stats.m_treeHeight = 1;
		m_stats.m_nodesInLevel.add(new Integer(0));

		Leaf root = new Leaf(this, -1);
		m_rootID = writeNode(root);

		storeHeader();
	}

	/**
	 * load a exist RTree and build it
	 * 
	 * @param ps
	 * @throws IOException
	 */
	private void initOld(PropertySet ps) throws IOException {
		loadHeader();

		// only some of the properties may be changed.
		// the rest are just ignored.

		Object var;

		// tree variant.
		var = ps.getProperty("TreeVariant");
		if (var != null) {
			if (var instanceof Integer) {
				int i = ((Integer) var).intValue();
				if (i != SpatialIndex.RtreeVariantLinear && i != SpatialIndex.RtreeVariantQuadratic && i != SpatialIndex.RtreeVariantRstar) {
					throw new IllegalArgumentException("Property TreeVariant not a valid variant");
				}
				m_treeVariant = i;
			} else {
				throw new IllegalArgumentException("Property TreeVariant must be an Integer");
			}
		}

		// near minimum overlap factor.
		var = ps.getProperty("NearMinimumOverlapFactor");
		if (var != null) {
			if (var instanceof Integer) {
				int i = ((Integer) var).intValue();
				if (i < 1 || i > m_indexCapacity || i > m_leafCapacity) {
					throw new IllegalArgumentException("Property NearMinimumOverlapFactor must be less than both index and leaf capacities");
				}
				m_nearMinimumOverlapFactor = i;
			} else {
				throw new IllegalArgumentException("Property NearMinimumOverlapFactor must be an Integer");
			}
		}

		// split distribution factor.
		var = ps.getProperty("SplitDistributionFactor");
		if (var != null) {
			if (var instanceof Double) {
				double f = ((Double) var).doubleValue();
				if (f <= 0.0f || f >= 1.0f) {
					throw new IllegalArgumentException("Property SplitDistributionFactor must be in (0.0, 1.0)");
				}
				m_splitDistributionFactor = f;
			} else {
				throw new IllegalArgumentException("Property SplitDistriburionFactor must be a Double");
			}
		}

		// reinsert factor.
		var = ps.getProperty("ReinsertFactor");
		if (var != null) {
			if (var instanceof Double) {
				double f = ((Double) var).doubleValue();
				if (f <= 0.0f || f >= 1.0f) {
					throw new IllegalArgumentException("Property ReinsertFactor must be in (0.0, 1.0)");
				}
				m_reinsertFactor = f;
			} else {
				throw new IllegalArgumentException("Property ReinsertFactor must be a Double");
			}
		}

		m_infiniteRegion.m_pLow = new double[m_dimension];
		m_infiniteRegion.m_pHigh = new double[m_dimension];

		for (int cDim = 0; cDim < m_dimension; cDim++) {
			m_infiniteRegion.m_pLow[cDim] = Double.POSITIVE_INFINITY;
			m_infiniteRegion.m_pHigh[cDim] = Double.NEGATIVE_INFINITY;
		}
	}

	/**
	 * store the info of RTree into storage manager
	 * 
	 * @throws IOException
	 */
	private void storeHeader() throws IOException {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		DataOutputStream ds = new DataOutputStream(bs);

		ds.writeInt(m_rootID);
		ds.writeInt(m_treeVariant);
		ds.writeDouble(m_fillFactor);
		ds.writeInt(m_indexCapacity);
		ds.writeInt(m_leafCapacity);
		ds.writeInt(m_nearMinimumOverlapFactor);
		ds.writeDouble(m_splitDistributionFactor);
		ds.writeDouble(m_reinsertFactor);
		ds.writeInt(m_dimension);
		ds.writeLong(m_stats.m_nodes);
		ds.writeLong(m_stats.m_data);
		ds.writeInt(m_stats.m_treeHeight);
		DataIO.writeString(ds, destFileNamePoint);
		DataIO.writeString(ds, destFileNameLine);
		
		for (int cLevel = 0; cLevel < m_stats.m_treeHeight; cLevel++) {
			ds.writeInt(m_stats.m_nodesInLevel.get(cLevel).intValue());
		}

		ds.flush();
		m_headerID = m_pStorageManager.storeByteArray(m_headerID, bs.toByteArray());
	}

	private void loadHeader() throws IOException {
		byte[] data = m_pStorageManager.loadByteArray(m_headerID);
		DataInputStream ds = new DataInputStream(new ByteArrayInputStream(data));

		m_rootID = ds.readInt();
		m_treeVariant = ds.readInt();
		m_fillFactor = ds.readDouble();
		m_indexCapacity = ds.readInt();
		m_leafCapacity = ds.readInt();
		m_nearMinimumOverlapFactor = ds.readInt();
		m_splitDistributionFactor = ds.readDouble();
		m_reinsertFactor = ds.readDouble();
		m_dimension = ds.readInt();
		m_stats.m_nodes = ds.readLong();
		m_stats.m_data = ds.readLong();
		m_stats.m_treeHeight = ds.readInt();
		destFileNamePoint = DataIO.readString(ds);
		destFileNameLine = DataIO.readString(ds);
		
		for (int cLevel = 0; cLevel < m_stats.m_treeHeight; cLevel++) {
			m_stats.m_nodesInLevel.add(new Integer(ds.readInt()));
		}
	}

	/**
	 * create overflowTable then insert data
	 * 
	 * @param pData
	 * @param mbr
	 * @param id
	 */
	protected void insertData_impl(byte[] pData, Region mbr, int id, ArrayList<Integer> changedNodeID) {
		boolean[] overflowTable;

		Stack<Integer> pathBuffer = new Stack<Integer>();

		Node root = readNode(m_rootID);

		overflowTable = new boolean[root.m_level];
		for (int cLevel = 0; cLevel < root.m_level; cLevel++) {
			overflowTable[cLevel] = false;
		}

		Node l = root.chooseSubtree(mbr, 0, pathBuffer);
		if (changedNodeID != null)
			for (Integer i : pathBuffer)
				changedNodeID.add(i);
		l.insertData(pData, mbr, id, pathBuffer, overflowTable, changedNodeID);

		m_stats.m_data++;
	}

	/**
	 * insert data with given overflowTable
	 * 
	 * @param pData
	 * @param mbr
	 * @param id
	 * @param level
	 * @param overflowTable
	 */
	protected void insertData_impl(byte[] pData, Region mbr, int id, int level, boolean[] overflowTable, ArrayList<Integer> changedNodeID) {
		assert mbr.getDimension() == m_dimension;

		Stack<Integer> pathBuffer = new Stack<Integer>();

		Node root = readNode(m_rootID);
		Node n = root.chooseSubtree(mbr, level, pathBuffer);
		if (changedNodeID != null)
			for (Integer i : pathBuffer)
				changedNodeID.add(i);
		n.insertData(pData, mbr, id, pathBuffer, overflowTable, changedNodeID);
	}

	/**
	 * delete a data
	 * 
	 * @param mbr
	 *            contains the data
	 * @param id
	 *            indicates the data
	 * @return
	 */
	protected boolean deleteData_impl(final Region mbr, int id, ArrayList<Integer> changedNodeID) {
		assert mbr.getDimension() == m_dimension;

		boolean bRet = false;

		Stack<Integer> pathBuffer = new Stack<Integer>();

		Node root = readNode(m_rootID);
		Leaf l = root.findLeaf(mbr, id, pathBuffer);

		if (l != null) {
			l.deleteData(id, pathBuffer, changedNodeID);
			m_stats.m_data--;
			bRet = true;
		}

		return bRet;
	}

	/**
	 * write the node into file
	 * 
	 * @param n
	 * @return
	 * @throws IllegalStateException
	 */
	protected int writeNode(Node n) throws IllegalStateException {
		byte[] buffer = null;

		try {
			//store node into byte
			buffer = n.store();
		} catch (IOException e) {
			System.err.println(e);
			throw new IllegalStateException("writeNode failed with IOException");
		}

		int page;//select which page should stored
		if (n.m_identifier < 0) {
			page = IStorageManager.NewPage;
		} else {
			page = n.m_identifier;
		}

		try {
			//store byte into storage manager
			page = m_pStorageManager.storeByteArray(page, buffer);
		} catch (InvalidPageException e) {
			System.err.println(e);
			throw new IllegalStateException("writeNode failed with InvalidPageException");
		}

		if (n.m_identifier < 0) {
			//change id from -1 to reserved page
			n.m_identifier = page;
			//change data in STAT
			m_stats.m_nodes++;
			int i = m_stats.m_nodesInLevel.get(n.m_level).intValue();
			m_stats.m_nodesInLevel.set(n.m_level, new Integer(i + 1));
		}

		m_stats.m_writes++;

		// TODO What this means? Any class inherit from INodeCommand?
		for (int cIndex = 0; cIndex < m_writeNodeCommands.size(); cIndex++) {
			m_writeNodeCommands.get(cIndex).execute(n);
		}

		return page;
	}

	public synchronized Node readNode(int id) {
		byte[] buffer;
		DataInputStream ds = null;
		int nodeType = -1;
		Node n = null;

		try {
			buffer = m_pStorageManager.loadByteArray(id);
			ds = new DataInputStream(new ByteArrayInputStream(buffer));
			nodeType = ds.readInt();

			if (nodeType == SpatialIndex.PersistentIndex) {
				n = new Index(this, -1, 0);
			} else if (nodeType == SpatialIndex.PersistentLeaf) {
				n = new Leaf(this, -1);
			} else {
				throw new IllegalStateException("readNode failed reading the correct node type information");
			}

			n.m_pTree = this;
			n.m_identifier = id;
			n.load(buffer);

			m_stats.m_reads++;
		} catch (InvalidPageException e) {
			System.err.println(e);
			System.err.println("id : " + id + "is not existed!");
			return n;
			//throw new IllegalStateException("readNode failed with InvalidPageException");
		} catch (IOException e) {
			System.err.println(e);
			throw new IllegalStateException("readNode failed with IOException");
		}

		for (int cIndex = 0; cIndex < m_readNodeCommands.size(); cIndex++) {
			m_readNodeCommands.get(cIndex).execute(n);
		}

		return n;
	}

	protected void deleteNode(Node n) {
		try {
			m_pStorageManager.deleteByteArray(n.m_identifier);
		} catch (InvalidPageException e) {
			System.err.println(e);
			throw new IllegalStateException("deleteNode failed with InvalidPageException");
		}

		m_stats.m_nodes--;
		int i = m_stats.m_nodesInLevel.get(n.m_level).intValue();
		m_stats.m_nodesInLevel.set(n.m_level, new Integer(i - 1));

		for (int cIndex = 0; cIndex < m_deleteNodeCommands.size(); cIndex++) {
			m_deleteNodeCommands.get(cIndex).execute(n);
		}
	}

	private void rangeQuery(int type, final IShape query, final IVisitor v) {
		queryRegion = query;
		m_rwLock.read_lock();

		try {
			Stack<Node> st = new Stack<Node>();
			Node root = readNode(m_rootID);

			if (root.m_children > 0 
					//&& query.intersects(root.m_nodeMBR)
					) {
				st.push(root);
			}

			int insideChildCount = 0;
			int computeCount = 0;
			while (!st.empty()) {
				Node n = st.pop();
				computeCount = SecurityUtility.getCount();

				if (n.isLeaf()) {
					if (query.contains(n.m_nodeMBR)) {
						v.setParentNodeInside(insideChildCount > 0);
						v.visitNode(n, IVisitor.TYPE_INSIDE);
						if (insideChildCount > 0) {
							insideChildCount--;
						}
					} else if (query.intersects(n.m_nodeMBR)) {
						//System.err.println(query + "||" + n.m_nodeMBR);
						v.visitNode(n, IVisitor.TYPE_INTERSECT);
						//for intersect leaf, every data must be processed
						int[] childTypes = new int[n.m_children];
						Region[] childRegions = new Region[n.m_children];
						for (int cChild = 0; cChild < n.m_children; cChild++) {
							childRegions[cChild] = n.m_pMBR[cChild].getMBR();
							Data data = new Data(n.m_pData[cChild], n.m_pMBR[cChild], n.m_pIdentifier[cChild]);
							v.visitData(data);
							if (query.contains(n.m_pMBR[cChild])) {
								m_stats.m_queryResults++;
								childTypes[cChild] = IVisitor.TYPE_INSIDE;
							} else if (type != SpatialIndex.ContainmentQuery && query.intersects(n.m_pMBR[cChild])) {
								m_stats.m_queryResults++;
								childTypes[cChild] = IVisitor.TYPE_INTERSECT;
							} else {
								childTypes[cChild] = IVisitor.TYPE_OUTSIDE;
							}
						}
						int maxOmitLength = 0;
						if(SecurityNode.USE_X_ORDER){
							Arrays.sort(childRegions, SecurityNode.COMPARATOR);
							for (int i = 0; i < n.m_children; i++) {
								Region tupleRegion = childRegions[i].getMBR();
								if (tupleRegion.getHigh(0) < query.getMBR().getLow(0)) {
									maxOmitLength = i + 1;
									//System.out.println(maxOmitLength);
								}
							}
						}
						((SecurityVisitor)v).visitNode(n, IVisitor.TYPE_INTERSECT, childTypes, maxOmitLength);
					} else {
						v.visitNode(n, IVisitor.TYPE_OUTSIDE);
					}
				} else {
					if (query.contains(n.m_nodeMBR)) {
						v.setParentNodeInside(insideChildCount > 0);
						v.visitNode(n, IVisitor.TYPE_INSIDE);
						if (insideChildCount > 0) {
							insideChildCount--;
						}
						for (int cChild = n.m_children - 1; cChild >= 0; cChild--) {
							st.push(readNode(n.m_pIdentifier[cChild]));
						}
						insideChildCount += n.m_children;
					} else if (query.intersects(n.m_nodeMBR)) {
						v.visitNode(n, IVisitor.TYPE_INTERSECT);
						for (int cChild = n.m_children - 1; cChild >= 0; cChild--) {
							st.push(readNode(n.m_pIdentifier[cChild]));
						}
					} else {
						v.visitNode(n, IVisitor.TYPE_OUTSIDE);
					}
				}
			}
			v.visitNode(root, -1);
		} finally {
			m_rwLock.read_unlock();
		}
	}

	@Override
	public String toString() {
		String s = "Dimension: " + m_dimension + "\n" + "Fill factor: " + m_fillFactor + "\n" + "Index capacity: " + m_indexCapacity + "\n" + "Leaf capacity: "
				+ m_leafCapacity + "\n";

		if (m_treeVariant == SpatialIndex.RtreeVariantRstar) {
			s += "Near minimum overlap factor: " + m_nearMinimumOverlapFactor + "\n" + "Reinsert factor: " + m_reinsertFactor + "\n"
					+ "Split distribution factor: " + m_splitDistributionFactor + "\n";
		}

		s += "Utilization: " + 100 * m_stats.getNumberOfData() / (m_stats.getNumberOfNodesInLevel(0) * m_leafCapacity) + "%" + "\n" + m_stats;

		return s;
	}

	class NNEntry {
		IEntry m_pEntry;
		double m_minDist;

		NNEntry(IEntry e, double f) {
			m_pEntry = e;
			m_minDist = f;
		}
	}

	class NNEntryComparator implements Comparator<Object> {
		@Override
		public int compare(Object o1, Object o2) {
			NNEntry n1 = (NNEntry) o1;
			NNEntry n2 = (NNEntry) o2;

			if (n1.m_minDist < n2.m_minDist) {
				return -1;
			}
			if (n1.m_minDist > n2.m_minDist) {
				return 1;
			}
			return 0;
		}
	}

	class NNComparator implements INearestNeighborComparator {
		@Override
		public double getMinimumDistance(IShape query, IEntry e) {
			IShape s = e.getShape();
			return query.getMinimumDistance(s);
		}
	}

	class ValidateEntry {
		Region m_parentMBR;
		Node m_pNode;

		ValidateEntry(Region r, Node pNode) {
			m_parentMBR = r;
			m_pNode = pNode;
		}
	}

	class Data implements IData {
		int m_id;
		Region m_shape;
		byte[] m_pData;

		Data(byte[] pData, Region mbr, int id) {
			m_id = id;
			m_shape = mbr;
			m_pData = pData;
		}

		@Override
		public int getIdentifier() {
			return m_id;
		}

		@Override
		public IShape getShape() {
			return new Region(m_shape);
		}

		@Override
		public byte[] getData() {
			byte[] data = new byte[m_pData.length];
			System.arraycopy(m_pData, 0, data, 0, m_pData.length);
			return data;
		}
	}
}
