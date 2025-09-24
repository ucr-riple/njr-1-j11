package controllers.momcts.utils;

import java.util.HashMap;
import java.util.Vector;

import controllers.momcts.MOOTools;


/**
 * This class maintains a non-dominated point set (Vector<Double>) and operates on these points
 * @author wj
 * @date	created 2013/07/17
 * @date	modified 2013/07/17
 *
 */
public class Archive {
	private Vector<Vector<Double>> points;
	private int solNbLimit;
	/**
	 * Record the solutions leading to the given point
	 */
	private HashMap<Vector<Double>,Vector<Vector<Integer>>> pnt2sols;
	
	public Archive(){
		points = new Vector<Vector<Double>>();
		pnt2sols = new HashMap<Vector<Double>,Vector<Vector<Integer>>>();
		solNbLimit = -1;
	}	

	public Archive(int sLimit){
		points = new Vector<Vector<Double>>();
		pnt2sols = new HashMap<Vector<Double>,Vector<Vector<Integer>>>();
		solNbLimit = sLimit;
	}	
	
	public int getSolNbLimit() {
		return solNbLimit;
	}

	public void setSolNbLimit(int solNbLimit) {
		this.solNbLimit = solNbLimit;
	}

	public Vector<Vector<Double>> getPoints(){
		return points;
	}
	
	public void setSols(Vector<Double> pnt, Vector<Vector<Integer>> sols){
		pnt2sols.put(pnt, sols);
	}
	
	public void addSol(Vector<Double> pnt, Vector<Integer> sol){
		if(pnt2sols.containsKey(pnt)){
			pnt2sols.put(pnt, SetOperation.joinSeq(pnt2sols.get(pnt), sol, solNbLimit));
		} else {
			Vector<Vector<Integer>> sols = new Vector<Vector<Integer>>();
			sols.add(sol);
			pnt2sols.put(pnt, sols);
		}
	}
	
	public Vector<Vector<Integer>> getSols(Vector<Double> pnt){
		if(pnt2sols.containsKey(pnt)) return pnt2sols.get(pnt);
		else return new Vector<Vector<Integer>>();
	}
	
	public Vector<Integer> getOneSol(Vector<Double> pnt){
		return SetOperation.randomElement(getSols(pnt));
	}
	
	/**
	 * Add a new point pnt in the archive
	 * @param pnt
	 * @return ture if pnt is not included in points, else false
	 */
	public boolean addNewPoint(Vector<Double> pnt){
		if(points.contains(pnt)) return false;
		else {
			points.add(pnt);
			return true;
		}
	}
	
	public boolean addNewPoint(Vector<Double> pnt, Vector<Integer> sol){
		if(addNewPoint(pnt)) {
			addSol(pnt, sol);
			return true;
		} else if(points.contains(pnt)){
			if(getSols(pnt).contains(sol)) return false;
			else {
				addSol(pnt,sol);
				return true;
			}
		}
		return false;
	}
	
	public boolean contains(Vector<Double> pnt){
		return points.contains(pnt);
	}
	
	/**
	 * Order the points according to their Pareto rank by executing the Fast Non-dominated Sort algorithm
	 * Then keep only the non-dominated points (Pareto rank = 0) in the archive,and eliminate all other points
	 * @param 	maximize defines the optimization direction,
	 * 			if true, the greater the point values in each dimension is , the better
	 * @return the rank 0 points
	 */
	public Vector<Vector<Double>> rearrange(boolean maximize){
		points = MOOTools.nonDominatedPnts(points, maximize);
		return points;
	}
	
	public Vector<Vector<Integer>> getAllSolutions(){
		Vector<Vector<Integer>> allSols = new Vector<Vector<Integer>>();
		for(Vector<Double> p : points){
			allSols.addAll(pnt2sols.get(p));
		}
		return allSols;
	}
	
	/**
	 * Get the weighted sum of all objectives of each point according to the given weights
	 * @param wts	weights associated to each dimension in the point
	 * @return
	 */
	public Vector<Double> getPointScores(Vector<Double> wts){
		Vector<Double> sums = new Vector<Double>();
		for(Vector<Double> p : points){
			sums.add(SetOperation.weightedSum(p, wts));
		}
		return sums;
	}
	
	public Vector<Double> getBestPoint(Vector<Double> wts, boolean maximize){
		Vector<Double> rks = getPointScores(wts);
		int bestInd = SetOperation.getBestItemInd(rks, maximize);
		//Presentation.showSeq(rks);Debug.debug(bestInd+" "+maximize);
		return points.get(bestInd);
		//return SetOperation.getBestItem(points, rks, maximize);		
	}
	
	public void showPointScores(Vector<Double> wts){
		Vector<Double> rks = getPointScores(wts);
		for(int i=0; i<points.size();i++){
			Presentation.showSeq(points.get(i)); System.out.println(":"+rks.get(i));
		}
		Presentation.spr();
	}
	
	public double getBestScore(Vector<Double> wts, boolean maximize){
		Vector<Double> rks = getPointScores(wts);
		return SetOperation.getBestItem(rks, maximize);		
	}
	
	/**
	 * Return the solution of the point whose weighted sum is the best (minimum or maximum)
	 * @param wts
	 * @param maximize
	 * @return
	 */
	public Vector<Vector<Integer>> getBestSolutions(Vector<Double> wts, boolean maximize){
		return getSols(getBestPoint(wts, maximize));
	}
	
	public Vector<Integer> getBestSol(Vector<Double> wts, boolean maximize){
		return SetOperation.randomElement(getBestSolutions(wts,maximize));
	}
	
	public void showPntSol(){
		for(Vector<Double> p:points){
			Presentation.showSeq(p);System.out.print("\t");Presentation.showSeqln(getSols(p).firstElement());
		}
	}
	
	public void showPntSols(){
		for(Vector<Double> p:points){
			Presentation.showSeqln(p);
			for(Vector<Integer> sol:getSols(p)){
				Presentation.showSeqln(sol);
			}
			Presentation.spr();
		}
	}
	
	public void showPoints(){
		Presentation.showMatrix(points);
	}
	
	public static void testPnt2Sol(){
		double[] p1 = {1.1,2.005,3.4};
		double[] p2 = {1.1,2.005,3.4001};
		
		int[] s1 = {1,2,3,3,4,4,4};
		int[] s2 = {2,3,3,3,5,1};
		
		Transformation ts = new Transformation();
		Archive ar = new Archive();
		ar.addNewPoint(ts.doubleAry2Vec(p1), ts.intAry2Vec(s1));
		ar.addNewPoint(ts.doubleAry2Vec(p2), ts.intAry2Vec(s2));
		
		Presentation.showMatrix(ar.getSols(ts.doubleAry2Vec(p1)));
		Presentation.spr();
		Presentation.showMatrix(ar.getSols(ts.doubleAry2Vec(p2)));
	}
	
	public static void main(String[] args){
		testPnt2Sol();
	}
	
}
