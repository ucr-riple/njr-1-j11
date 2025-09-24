package controllers.momcts.utils;

import java.util.Random;

/**
 * This class defines various daily math operations
 * @author wj
 * @date	created	2013/07/17
 * @date	modified 2013/07/17
 *
 */
public class MathOperation {
	static Random rnd = new Random();
	
	/**
	 * Return a random integer uniformly distributed in [lowBound, upBound)
	 * @param upBound
	 * @param lowBound
	 * @return
	 */
	public static int rndInt(int upBound, int lowBound){
		int diff = upBound - lowBound;
		if(diff > 0) return lowBound + rnd.nextInt(diff);
		else return lowBound - rnd.nextInt(-diff);
	}
	
	/**
	 * Return a random integer uniformly distributed in [0, upBound)
	 * @param upBound
	 * @return
	 */
	public static int rndInt(int upBound){
		return rndInt(upBound, 0);
	}
	
	public static double rand(double upBound, double lowBound){
		double diff = upBound - lowBound;
		return lowBound+rnd.nextDouble()*diff;
	}
	
	public static double rand(double upBound){
		return rand(upBound, 0);
	}
	
	/**
	 * @return a number uniformly distributed in [0,1)
	 */
	public static double rand1(){
		return rnd.nextDouble();
	}
	
	/**
	 * Draw a random variable uniformly distributed between [0,1), if such variable is smaller than p, return 1, else, return 0
	 * @param p
	 * @return
	 */
	public static boolean bernouilli(double p){
		if(rand1() < p) return true;
		else return false;
 	}
	
	public static void main(String[] args){
		for(int i=0; i< 100; i++){
			if(i%20==0) System.out.println();
			if(bernouilli(0.1)) System.out.print(1);
			else System.out.print(0);			
		}
	}
}
