package controllers.utils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * This class contains functions used in the Multi-Objective Optimization
 * @author wj
 *
 */
public class MOOTools {
	/**
	 * Given a set of points, return the dimension of each point
	 * @param pnts
	 * @return
	 */
	public static Vector<Integer> dim(Vector<Vector<Double>> pnts){
		Vector<Integer> dims = new Vector<Integer>();
		for(Vector<Double> p : pnts){
			dims.add(p.size());
		}
		return dims;
	}
	
	/**
	 * If all dimensions in dim are the same, then return this dimension, else return 0
	 * @param dims
	 * @return
	 */
	public static int sameDimension(Vector<Vector<Double>> pnts){
		Vector<Integer> dims = dim(pnts);
		Collections.sort(dims);
		if(dims.firstElement() == dims.lastElement()) return dims.firstElement();
		else return 0;
	}

	/**
	 * Judge if p1 dominates p2
	 * @param p1
	 * @param p2
	 * @param maximize	defines the direction of optimization, by default, true (meaning the greater, the better)
	 * @assure pnt1 and pnt2 have the same dimension, if not, return false
	 * @return
	 */
	public static boolean dominates(Vector<Double> p1, Vector<Double> p2, boolean maximize){
		if(p1.size()!= p2.size()) return false;
		boolean flag = false;
		for(int i=0; i<p1.size();i++){
			if(maximize){
				if(p1.get(i) < p2.get(i)) return false;
				if(p1.get(i) > p2.get(i)) flag = true;
			} else {
				if(p1.get(i) > p2.get(i)) return false;
				if(p1.get(i) < p2.get(i)) flag = true;			
			}
		}
		return flag;
	}
	
	public static boolean dominates(Vector<Double> p1, Vector<Double> p2){
		return dominates(p1, p2, true);
	}
	
	public static boolean domEquals(Vector<Double> p1, Vector<Double> p2){
		return p1.equals(p2) || dominates(p1,p2);
	}
	
	/**
	 * Judge if p is not dominated by any point in pnts
	 * (and if p dominats at leat one point in pnts if strict == true)
	 * @param p
	 * @param pnts
	 * @param strict	if true, then p must dominate at least one point in pnts, by default, true
	 * @param maximize	by default, true
	 * @return
	 */
	public static boolean dominatesSet(Vector<Double> p, Vector<Vector<Double>> pnts, boolean strict, boolean maximize){
		boolean flag;
		if(strict) flag = false;
		else flag = true;
		for(Vector<Double> pt : pnts){
			if(dominates(pt,p, maximize)) return false;
			if(dominates(p, pt, maximize)) flag = true;
		}
		return flag;
	}
	
	public static boolean dominatesSet(Vector<Double> p, Vector<Vector<Double>> pnts, boolean strict){
		return dominatesSet(p,pnts,strict, true);
	}
	
	public static boolean dominatesSet(Vector<Double> p, Vector<Vector<Double>> pnts){
		return dominatesSet(p,pnts,true);
	}
	
	/**
	 * Judge if p is dominated by all points in pnts
	 * @param p
	 * @param pnts
	 * @param strict if true, then p must be dominated at least one point in pnts, by default, true
	 * @param maximize	by default, true
	 * @return
	 */
	public static boolean isDominatedByAllSet(Vector<Double> p, Vector<Vector<Double>> pnts, boolean strict, boolean maximize){
		for(Vector<Double> pt : pnts){
			if(dominates(pt, p, maximize)) continue;
			else {
				if(dominates(p, pt, maximize)) return false;
				if(strict && pt.equals(p)) return false;
			}
		}
		return true;
	}
	
	public static boolean isDominatedByAllSet(Vector<Double> p, Vector<Vector<Double>> pnts, boolean strict){
		return isDominatedByAllSet(p, pnts, strict, true);
	}
	
	public static boolean isDominatedByAllSet(Vector<Double> p, Vector<Vector<Double>> pnts){
		return isDominatedByAllSet(p, pnts, true);
	}

	public static Vector<Vector<Double>> eliminateRepetitions(Vector<Vector<Double>> pnts){
		Map<Vector<Double>, Boolean> mp = new HashMap<Vector<Double>, Boolean>();
		for(Vector<Double> p:pnts){
			if(mp.containsKey(p)) continue;
			mp.put(p, true);
		}
		return SetOperation.getKeys(mp);
	}
	
	/**
	 * Return the index of points in pnts which are dominated by p
	 * @param pnts
	 * @param p
	 * @param maximize
	 * @param strict	if true, then return also those point which are equal to p, by default, false
	 * @return
	 */
	public static Vector<Integer> dominatedInds(Vector<Vector<Double>> pnts, Vector<Double> p, boolean maximize, boolean strict){
		Vector<Integer> inds = new Vector<Integer>();
		for(int i=0; i<pnts.size();i++){
			if(!strict && p.equals(pnts.get(i))) inds.add(i);
			else if(dominates(p, pnts.get(i), maximize)) inds.add(i);
		}
		return inds;
	}
	
	public static Vector<Vector<Double>> dominatedPnts(Vector<Vector<Double>> pnts, Vector<Double> p, 
			boolean maximize, boolean strict){
		Vector<Integer> inds = dominatedInds(pnts, p, maximize, strict);
		return SetOperation.getElements(pnts, inds);
	}

	/**
	 * Return the index of points in pnts which dominates p
	 * @param pnts
	 * @param p
	 * @param maximize
	 * @param strict	if true, then return also those point which are equal to p, by default, false
	 * @return
	 */
	public static Vector<Integer> dominatingInds(Vector<Vector<Double>> pnts, Vector<Double> p, boolean maximize, boolean strict){
		Vector<Integer> inds = new Vector<Integer>();
		for(int i=0; i<pnts.size();i++){
			if(!strict && p.equals(pnts.get(i))) inds.add(i);
			else if(dominates(pnts.get(i), p, maximize)) inds.add(i);
		}
		return inds;
	}
	
	public static Vector<Vector<Double>> dominatingPnts(Vector<Vector<Double>> pnts, Vector<Double> p, 
			boolean maximize, boolean strict){
		Vector<Integer> inds = dominatingInds(pnts, p, maximize, strict);
		return SetOperation.getElements(pnts, inds);
	}
	
	public static Vector<Vector<Double>> dominatingPnts(Vector<Vector<Double>> pnts, Vector<Double> p,boolean maximize){
		return dominatingPnts(pnts, p, maximize, false);
	}
	/**
	 * Return the indices of points ordered in different sets of Pareto ranks according to the 
	 * Fast Non-Dominated Sort algorithm by K. Deb, 2000
	 * @param points
	 * @param maximize
	 * @return	and index of points in different Pareto rank layers
	 */
	public static Vector<Vector<Integer>> fastNonDominatedSort(Vector<Vector<Double>> points, boolean maximize){
		Vector<Vector<Integer>> Sp = new Vector<Vector<Integer>>();//Sp[i] store the index of points who are dominated by points[i]
		Vector<Integer> np = new Vector<Integer>();//np[i] stores the number of points who are dominated by points[i]
		for(int i=0;i<points.size();i++){
			Sp.add(new Vector<Integer>());
			np.add(0);
		}

		Vector<Vector<Integer>> F = new Vector<Vector<Integer>>();;//F[n] store the index of points in rank n
		F.add(new Vector<Integer>());
		for(int i=0; i< points.size(); i++){
			for(int j=0; j< points.size(); j++){
				if( dominates(points.get(i),points.get(j), maximize) ){
					Sp.get(i).add(j);
				} else if(dominates(points.get(j),points.get(i), maximize)){
					np.set(i, np.get(i)+1);
				}
			}
			if(np.get(i) == 0){
				F.get(0).add(i);
			}
		}
		
		int rk = 0;
		while(F.get(rk).size() > 0){
			Vector<Integer> H = new Vector<Integer>();
			for(int i=0; i<F.get(rk).size(); i++){
				int p= F.get(rk).get(i);
				for(int j=0; j<Sp.get(p).size();j++){
					int q = Sp.get(p).get(j);
					np.set(q, np.get(q)-1);
					if(np.get(q)==0){
						H.add(q);
					}
				}
			}
			
			F.add(new Vector<Integer>());
			rk++;
			F.set(rk, H);
		}
		
		if(F.get(rk).size() == 0){
			F.remove(rk);//eliminate the empty set if exists
		}
		return F;
	}
	
	public static Vector<Integer> nonDominatedInds(Vector<Vector<Double>> pnts, boolean maximize){
		return 	fastNonDominatedSort(pnts,maximize).firstElement();
	}
	
	@SuppressWarnings("unchecked")
	public static Vector<Vector<Double>> nonDominatedPnts(Vector<Vector<Double>> pnts, boolean maximize){
		Vector<Vector<Double>> nonDomPnts = SetOperation.getElements(pnts,nonDominatedInds(pnts, maximize));
		Collections.sort(nonDomPnts, new firstDimComparator(maximize));
		return nonDomPnts;
	}
	
	@SuppressWarnings("rawtypes")
	public static class firstDimComparator implements Comparator{
		private int optDirection;
		
		public firstDimComparator(boolean max){
			if(max) optDirection = 1;
			else optDirection = -1;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public int compare(Object arg0, Object arg1) {
			Vector<Double> p0 = (Vector<Double>) arg0;
			Vector<Double> p1 = (Vector<Double>) arg1;
			double diff = p0.firstElement() - p1.firstElement();
			if(diff > 0) return 1*optDirection;
			else if(diff == 0) return 0;
			else return -1*optDirection;
		}
		
	}
	
	/**
	 * Given a set of 2D points nonDomPnts which form an undominated set, return the hypervolume indicator of this point set
	 * If the dimension of the points are not 2, return -1
	 * @param nonDomPnts
	 * @param refPnt
	 * @param maximizeD1	indicate the optimization direction, by default, true
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static double hypervolumeIndicator2D(Vector<Vector<Double>> nonDomPnts,
			boolean maximizeD1, Vector<Double> refPnt){
		if(sameDimension(nonDomPnts) != 2)	return -1;		
		if(!isDominatedByAllSet(refPnt, nonDomPnts, false, maximizeD1)) return 0;
		double volume = 0;
		double lastX = refPnt.firstElement();
		Collections.sort(nonDomPnts, new firstDimComparator(maximizeD1));
		
		for(int i=0; i<nonDomPnts.size();i++){
			double diffX = nonDomPnts.get(i).get(0) - lastX;
			double diffY = nonDomPnts.get(i).get(1)  - refPnt.get(1);
			double incrm = diffX * diffY;
			volume = volume + incrm;
			lastX = nonDomPnts.get(i).get(0);
		}
		return volume;
	}
	
	@SuppressWarnings("unchecked")
	public static double hypervolumeIndicator(Vector<Vector<Double>> pnts,
			boolean maximize, Vector<Double> rPnt){
		Vector<Vector<Double>> nonDomPnts = SetOperation.duplicateMatrix(pnts);
		Vector<Double> refPnt = (Vector<Double>) rPnt.clone();
		int dimension = sameDimension(nonDomPnts);
		if(dimension < 2) {
			Debug.debug();
			return -1;
		}
		if(refPnt.size()==0){
			if(maximize){
				for(int i=0; i< dimension;i++)	refPnt.add(0.);			
			} else {
				for(int i=0; i< dimension;i++) refPnt.add(1.);
			}
		}
		if (dimension == 2) return hypervolumeIndicator2D(nonDomPnts, maximize, refPnt);

		
		if(!isDominatedByAllSet(refPnt, nonDomPnts, false, maximize)) return 0;		
		nonDomPnts = eliminateRepetitions(nonDomPnts);
		nonDomPnts = dominatingPnts(nonDomPnts, refPnt, maximize, false);
		nonDomPnts = nonDominatedPnts(nonDomPnts, maximize);
		Collections.sort(nonDomPnts, new firstDimComparator(maximize));
		
		double hvi = 0;
		double baseX = SetOperation.popFront(refPnt);
		Vector<Double> xs = SetOperation.extractCol(nonDomPnts,0);
		for(int i=0; i<xs.size();i++){
			double diffX = xs.get(i)-baseX;
			double surfDn1 = hypervolumeIndicator(SetOperation.subSeq(nonDomPnts, i), maximize, refPnt);
			hvi += diffX * surfDn1;
			baseX = xs.get(i);
		}
		return hvi;		
	}
	
	public static double hypervolumeIndicator(Vector<Vector<Double>> nonDomPnts,boolean maximize){
		return hypervolumeIndicator(nonDomPnts, maximize, new Vector<Double>());
	}
	
	public static double hypervolumeIndicator(Vector<Vector<Double>> nonDomPnts){
		return hypervolumeIndicator(nonDomPnts, true);
	}
	
	public static double HVIContrib(Vector<Double> pnt, Vector<Vector<Double>> nonDomPnts
			, boolean maximize, Vector<Double> refPnt){
		if(!dominatesSet(pnt, nonDomPnts, false, maximize)) return 0;

		double orgHVI = hypervolumeIndicator(nonDomPnts, maximize, refPnt);
		@SuppressWarnings("unchecked")
		Vector<Vector<Double>> newPnts = (Vector<Vector<Double>>) nonDomPnts.clone();
		newPnts.add(pnt);
		double newHVI = hypervolumeIndicator(newPnts, maximize, refPnt);		
		return Double.valueOf(Presentation.ndigits(newHVI - orgHVI,3));
	}
	
	public static double HVIContrib(Vector<Double> pnt, Vector<Vector<Double>> nonDomPnts	, boolean maximize){
		return HVIContrib(pnt, nonDomPnts, maximize, new Vector<Double>());
	}
	
	public static double HVIContrib(Vector<Double> pnt, Vector<Vector<Double>> nonDomPnts){
		return HVIContrib(pnt, nonDomPnts, true);
	}
	
	public static void showPoints(Vector<Vector<Double>> pnts){
		Presentation.showMatrix(pnts);
	}
	
	
	//-----------test programs-------------
	public void testHVI(){
		double[] as = {3,3,3};
		Vector<Double> av = Transformation.doubleAry2Vec(as);
		
		double[] b1 = {1,2,0.5};
		double[] b2 = {2,1,2};
		//double[] b3 = {1.1,2.2,3.3};
		Vector<Double> bv1 = Transformation.doubleAry2Vec(b1);
		Vector<Double> bv2 = Transformation.doubleAry2Vec(b2);
		//Vector<Double> bv3 = Transformation.doubleAry2Vec(b3);
		
		Vector<Vector<Double>> bs = new Vector<Vector<Double>>();
		bs.add(bv1);
		bs.add(bv2);
		//bs.add(bv3);
		Presentation.showMatrix(bs);
		System.out.println(hypervolumeIndicator(bs));
		System.out.println(HVIContrib(av, bs));
		System.out.println(this.fastNonDominatedSort(bs, true));
	}
	
	//-----------end of test programs----------
	
	public static void main(String[] args){
		MOOTools mot = new MOOTools();
		mot.testHVI();
	}
}
