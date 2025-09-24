package utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class generator {
	public static void GeneratePointsofInteger(int range, int num, String filepath){
		Random ramdom = new Random();
		try {
			PrintStream out = new PrintStream(new FileOutputStream(filepath));
			for(int i = 0; i < num; i++){
				int x = ramdom.nextInt(range), y = ramdom.nextInt(range);
				out.println("1 " + (i + 1) + " " + x + " " + y + " " + x + " " + y);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void GeneratePointsofInteger(int sx, int sy, int rangex, int rangey, int num, String filepath){
		Random ramdom = new Random();
		try {
			PrintStream out = new PrintStream(new FileOutputStream(filepath));
			for(int i = 0; i < num; i++){
				int x = ramdom.nextInt(rangex), y = ramdom.nextInt(rangey);
				out.println("1 " + (i + 1) + " " + (sx + x) + " " + (sy + y) + " " + (sx + x) + " " + (sy + y));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void GeneratePointsofDouble(double sx, double sy, double mx, double my, int num, String filepath){
		Random ramdom = new Random();
		double rangex = mx - sx;
		double rangey = my - sy;
		try {
			PrintStream out = new PrintStream(new FileOutputStream(filepath));
			for(int i = 0; i < num; i++){
				double x = ramdom.nextDouble() * rangex, y = ramdom.nextDouble() * rangey;
				out.println((sx + x) + "\t" + (sy + y));
			}
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void generatorofnear(int num, String filepath){
		Scanner in;
		Random ramdom = new Random();
		try {
			in = new Scanner(new FileInputStream(new File("source/ca.1000.dat")));
			PrintStream out = new PrintStream(new FileOutputStream(filepath));
			int i = 0;
			int sx = 1 << 20, sy = 1 << 20, mx = -1, my = -1;
			while(in.hasNext()){
				in.nextInt(); in.nextInt();
				in.nextInt(); in.nextInt();
				int x1 = in.nextInt(), y1 = in.nextInt();
				if(x1 > mx) mx = x1;
				if(x1 < sx) sx = x1;
				if(y1 > my) my = y1;
				if(y1 < sy) sy = y1;
				int x = ramdom.nextInt(1000) - 500, y = ramdom.nextInt(1000) - 500;
				if(ramdom.nextFloat() > 0.9){
					out.println("1 " + (i + 1) + " " + (x1 + x) + " " + (y1 + y) + " " + (x1 + x) + " " + (y1 + y));
					i ++;
				}
				if(i >= num)break;
			}
			for(; i < num; i++){
				int x = ramdom.nextInt(mx - sx), y = ramdom.nextInt(my - sy);
				out.println("1 " + (i + 1) + " " + (x + sx) + " " + (y + sy) + " " + (x + sx) + " " + (y + sy));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String args[]){
//		Scanner in;
//		try {
//			in = new Scanner(new FileInputStream(new File("source/ca.1000.dat")));
//			int sx = 1 << 20, sy = 1 << 20, mx = -1, my = -1;
//			while(in.hasNext()){
//				in.nextInt(); in.nextInt();
//				in.nextInt(); in.nextInt();
//				int x1 = in.nextInt(), y1 = in.nextInt();
//				if(x1 > mx) mx = x1;
//				if(x1 < sx) sx = x1;
//				if(y1 > my) my = y1;
//				if(y1 < sy) sy = y1;
//			}
//			generator.GeneratePointsofInteger(sx, sy, mx - sx, my - sy, 200, "query/query.in.200.v1");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("fin!");
		//generatorofnear(25, "query/query.in.25.v2");
		//GeneratePointsofDouble(sx, sy, mx, my, num, filepath);
	}
}
