package controllers.utils;

import java.util.Map;
import java.util.Vector;

/**
 * This class contains all functions that are used to facilitate the presentation
 * of strings and numbers in console mode of java programs
 * @author wj
 * @date created on 2013/07/11
 * @date modified on 2013/07/12
 *
 */
public class Presentation {
	public static String defElemSep = "\t";
	public static String defLineSep = "\n";
	
	public static void showSeq(Vector seq){
		showSeq(seq, defElemSep);
	}
	
	public static void showSeq(Vector seq, String sp){
		for(int i=0; i<seq.size(); i++){
			if(i!=0) System.out.print(sp);
			System.out.print(seq.get(i));
		}
	}
	
	public static void showSeqln(Vector seq){
		showSeqln(seq, defElemSep);
	}
	
	public static void showSeqln(Vector seq, String sp){
		showSeq(seq, sp);
		System.out.println();
	}

	public static void showSeq(int[] is) {
		showSeq(Transformation.intAry2Vec(is));
	}
	
	public static void showSeq(double[] is) {
		showSeq(Transformation.doubleAry2Vec(is));
	}
	
	public static void showSeq(String[] is) {
		showSeq(Transformation.strAry2Vec(is));
	}

	public static void multiOut(String pattern){
		multiOut(pattern,5);
	}
	
	public static void multiOut(String pattern, int repNb){
		for(int i=0; i<repNb;i++){
			System.out.print(pattern);
		}
	}
	
	/**
	 * Print a separation line
	 */
	public static void spr(String s){
		multiOut(s,10);
		System.out.println();
	}
	
	public static void spr(){
		spr("-");
	}
	
	public static <T> void showMatrix(Vector<Vector<T>> mx, String elemSep, String lineSep){
		for(int i=0; i< mx.size(); i++){
			if(i!=0) System.out.print(lineSep);
			Vector<T> line = mx.get(i);
			for(int j=0; j< line.size();j++ ){
				if(j!=0) System.out.print(elemSep);
				System.out.print(line.get(j));
			}
		}
		System.out.println();
	}
	
	public static <T> void showMatrix(Vector<Vector<T>> mx, String elemSep){
		showMatrix(mx, elemSep, defLineSep);
	}
	
	public static <T> void showMatrix(Vector<Vector<T>> mx){
		showMatrix(mx, defElemSep);
	}
	
	public static <K,V> void showMap(Map<K,V> mp){
		System.out.println(mp);
	}
	
	
	/**
	 * Given a decimal number, show the decimal with accuracy to indicated decimal places
	 * @param n the number to be shown
	 * @param precision the number of decimals to show
	 * @return
	 */
	//TODO to correct the not sishewuru pb
	public static double ndigits(double nb, int precision){
		double base = (int) Math.pow(10, precision);
		double mul = nb*base;
		if(mul < 1.0) return 0;
		else return Math.round(mul)/base;
	}
	
	public static double ndigits(double nb){
		return ndigits(nb,2);
	}
	
	public static void main(String[] args){
		double a = 4.423782231483507E-9;
		System.out.println(ndigits(a,3));
	}
	
}
