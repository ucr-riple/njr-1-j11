package utility.Compare;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;

import utility.Compare.DataOfPoint;

/**
 * @author qchen
 *
 */
public class DataOfPointParser {

	public static String filein = "tmp/output";
	public static String filename = "database/PointsData.NE";
	public RecordManager recmanOfPoint;
	public PrimaryTreeMap<Long, byte[]> btOfPoint;
	
	public void loadData() throws IOException{
		try {
			File files = new File(filein);
			if(!files.exists()){
				throw new FileNotFoundException();
			}
			if(files.isDirectory()){
				File[] file = files.listFiles();
				for(int i = 0 ; i < file.length; i++){
					System.out.println(file[i].toString());
					DataInputStream dis = new DataInputStream(new FileInputStream(file[i]));
					try {
						int lines = 0;
						while(dis.available() != 0){
							lines ++;
							dis.readByte();
							int id = dis.readInt();
							DataOfPoint dataOfPoint = new DataOfPoint();
							dataOfPoint.load(dis);
							dis.readByte();
							btOfPoint.put((long) id, dataOfPoint.writeToBytes());
							if(lines % 100 == 0)recmanOfPoint.commit();
						}
						recmanOfPoint.commit();
						dis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DataOfPointParser(boolean needLoad) throws IOException{
		recmanOfPoint = RecordManagerFactory.createRecordManager(filename);
		btOfPoint = recmanOfPoint.treeMap("treemap");
		if(needLoad){
			loadData();
		}
		//recmanOfPoint.close();
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		DataOfPointParser dataOfPointParser = new DataOfPointParser(false);
		System.out.println("Time consume: " + (System.currentTimeMillis() - start));
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.println("input id below:");
			long id = in.nextLong();
			byte[] str = dataOfPointParser.btOfPoint.find(id);
			DataOfPoint data = new DataOfPoint();
			data.loadFromBytes(str);
			System.out.println(id + " : " + data.p.x + " " + data.p.y + " dels : ");
			for(long x : data.delaunayIds){
				System.out.print(x + " ");
			}
			System.out.println("");
		}
	}
}
