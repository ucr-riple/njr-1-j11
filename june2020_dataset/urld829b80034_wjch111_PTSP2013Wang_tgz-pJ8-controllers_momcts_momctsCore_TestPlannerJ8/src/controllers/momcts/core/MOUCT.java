package controllers.momcts.core;

import java.util.HashMap;
import java.util.Vector;

import controllers.momcts.utils.Presentation;
import controllers.momcts.utils.SetOperation;



/**
 * One node in the MOUCT tree which stores the reward information
 * @author wj
 * @date: 	2013/07/11 created
 * 			2013/07/12 modified
 */
public class MOUCT {
	/**
	 * An action is necessary for the creation of node in MOUCT tree 
	 */
	private int action;
	/**
	 * The state identification of a node is not necessarily used in many occassions
	 */
	private int state;
	
	/**
	 * The information about the following state of the current state is stored in sons 
	 */
	private Vector<MOUCT> sons;
	
	/**
	 * Record the context of the current node
	 * (the sequence of actions which leads to the current node from the root)
	 */
	private Vector<Integer> context;
	
	/**
	 * The rwds map stores the total reward of each type
	 */
	private HashMap<String, Double> rwds;
	
	/**
	 * The nbs map stores the total number of visits of each type
	 */
	private HashMap<String, Double> nbs;
	
	/**
	 * Record the time when the reward was updated for the last time
	 */
	private HashMap<String, Integer> rwdAges;
		
	
	/**
	 * Define several default reward types
	 */
	public static Vector<String> defRwdTypes = new Vector<String>();

	/**
	 * The action must be assigned during the creation of a node
	 * @param act
	 */
	public MOUCT(int act){
		action = act;
		sons = new Vector<MOUCT>();
		context = new Vector<Integer>();
		rwds = new HashMap<String,Double>();
		rwdAges = new HashMap<String,Integer>();
		nbs = new HashMap<String,Double>();
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Vector<Integer> getContext() {
		return context;
	}

	public void setContext(Vector<Integer> context) {
		this.context = context;
	}
	
	/**
	 * Return the context actions and the action in the current node
	 * @return
	 */
	public Vector<Integer> getHistoryActs(){
		return SetOperation.joinSeq(context, action);
	}
	
	public int getDepth(){
		return context.size();
	}

	public int getAction() {
		return action;
	}

	public Vector<MOUCT> getSons() {
		return sons;
	}

	public HashMap<String, Double> getRwds() {
		return rwds;
	}

	public HashMap<String, Double> getNbs() {
		return nbs;
	}
	
	public double getRwd(String rwdType, int precision){
		Double r = rwds.get(rwdType); 
		if(r == null) return 0;
		else {
			if(precision > 0) return Presentation.ndigits(r,precision);
			else return r;
		}
	}
	
	public double getRwd(String rwdType){
		return getRwd(rwdType, -1);
	}
	
	/**
	 * Return the average reward of type type
	 * @param type	indicate the type of the reward required
	 * @return the average reward
	 */
	public double avgR(String type, int precision){
		Double r = getRwd(type);
		Double n = getNb(type);
		if(r == null) return 0;
		if(n !=0 ) {
			if(precision > 0) return Presentation.ndigits(r/n,precision);
			else return r/n;
		}
		else return 0;
	}
	
	public double avgR(String type){
		return avgR(type, -1);
	}
	
	public double getNb(String rwdType){
		Double n = nbs.get(rwdType);
		if(n == null) return 0;
		else return n;
	}
	
	protected void setRwd(String rwdType, double r){
		rwds.put(rwdType, r);
	}
	
	protected void setNb(String rwdType, double n){
		nbs.put(rwdType, n);
	}

	public static Vector<String> getDefRwdTypes() {
		return defRwdTypes;
	}

	public static void setDefRwdTypes(Vector<String> defRwdTypes) {
		MOUCT.defRwdTypes = defRwdTypes;
	}
	
	public static void addDefRwdtypes(String rdTp){
		defRwdTypes.add(rdTp);
	}

	/**
	 * Increment the reward of type rwdType by r
	 * @param rwdType
	 * @param r
	 * @param discount	if specified, then the original reward will be discounted by factor discount
	 */
	public double incrementRwd(String rwdType, double r, double discount){
		Double orgR = this.getRwd(rwdType);
		Double orgN = this.getNb(rwdType);
		//if(rwdType.contains("Dom")) Debug.debug(orgR+" "+orgN);
		if(orgR == null) {
			orgR = 0.;
			orgN = 0.;
		} else {
			orgR *= discount;
			orgN *= discount;
		}
		this.setRwd(rwdType, orgR+r);
		this.setNb(rwdType, orgN+1);
		return this.avgR(rwdType);
	}
	
	public void incrementRwd(String rwdType, double r){
		incrementRwd(rwdType, r, 1.0);
	}
	
	public double incrementRwd(String rwdType, double r, int age, double discount){
		if(age < 0) return incrementRwd(rwdType, r, discount);
		int ageDiff = age - SetOperation.getIntValue(rwdAges, rwdType);
		rwdAges.put(rwdType, age);
		return incrementRwd(rwdType, r, Math.pow(discount, ageDiff));
	}

	
	/**
	 * Update the RAVE(Rapid Action Value Estimation) values of the indicated action of type rwdType
	 * @param rwdType
	 * @param a		The action whose RAVE is to be updated
	 * @param r
	 * @param discount
	 */
	public void incrementRAVE(int a, String rwdType, double r, double discount){
		incrementRwd(rwdType+"_RAVE"+a, r, discount);
	}
	
	public void incrementRAVE(int a, String rwdType, double r){
		incrementRwd(rwdType+"_RAVE"+a, r, 1.0);
	}
	
	public double getRAVE(int a, String rwdType){
		return avgR(rwdType+"_RAVE"+a);
	}
	
	public double getRAVEnb(int a, String rwdType){
		return nbs.get(rwdType+"_RAVE"+a);
	}
	
	/**
	 * Adding new new node with action = act, and inherits the context of the current node if indicated
	 * @param act	action of the created son
	 * @param contextInherit	indicate if the context of the current node should be inherited by the son, by default, true
	 * @notice the act added should not be included among the existing sons, if so, return null
	 * @return the created son
	 */
	public MOUCT addSon(int act, int initNb, boolean contextInherit){
		if(hasSon(act)) return null;
		MOUCT s = new MOUCT(act);
		if(initNb != -1) s.setNb(defRwdTypes.firstElement(), initNb);
		
		if(contextInherit) {
			@SuppressWarnings("unchecked")
			Vector<Integer> newContext = (Vector<Integer>)context.clone();
			newContext.add(action);
			s.setContext(newContext);
		}
		sons.add(s);
		return s;
	}
	
	public MOUCT addSon(int act, int initNb){
		return addSon(act, initNb, true);
	}
	
	public MOUCT addSon(int act){
		return addSon(act,-1);
	}
	
	public Vector<Integer> sonActions(){
		Vector<Integer> acts = new Vector<Integer>();
		for(int i=0; i<sons.size();i++){
			acts.add(sons.get(i).getAction());
		}
		return acts;
	}
	
	public MOUCT getSon(int index){
		return sons.get(index);
	}
	
	/**
	 * Find out the son whose action == act
	 * @param act
	 * @return the found son, if not found, return null
	 */
	public MOUCT findSon(int act){
		for(MOUCT s:sons){
			if(s.getAction() == act) return s;
		}
		return null;
	}
	
	public boolean hasSon(int act){
		if(findSon(act) != null) return true;
		else return false;
	}	

	public boolean isLeaf(){
		return !(sons.size()>0);
	}
	
	public boolean isRoot(){
		return context.size()==0;
	}
	
	public int size(){
		int sz = 1;
		for(MOUCT s : sons){
			sz += s.size();
		}
		return sz;
	}
	
	/**
	 * Show the subtree starting from the current node
	 * @param limit		the number of levels to be shown
	 * @param rwdTypes	indicate the reward types to be shown
	 * @param nbType	indicate the number of visits recored in one nbs key
	 * @param depth		pass the depth of the tree (used in presentation)
	 * @param avg		indicate whether the average reward or the total reward is to be displayed
	 */
	public void showSelf(int limit, Vector<String> rwdTypes, boolean avg, String nbType, int depth){
		if(rwdTypes.size()==0) rwdTypes = defRwdTypes;		
		if(nbType == "") nbType = MOUCT.defRwdTypes.firstElement();//rwdTypes.firstElement();
		
		Presentation.multiOut(".|",depth);
		if(context.size()>0 && depth == 0){
			System.out.print("<");
			Presentation.showSeq(context,",");
			System.out.print(">");
		}
		System.out.print(action);
		double n = Presentation.ndigits(this.getNb(nbType));
		if(isLeaf()) System.out.print("->"+n);
		else System.out.print("("+n+")");

		for(int i=0; i<rwdTypes.size();i++){
			String tp = rwdTypes.get(i);
			double r = avgR(tp, 4);
			if(!avg) r = getRwd(tp, 4);
			System.out.print("\t"+tp+":"+r);
		}

		if(limit != 0){
			System.out.println();
			for(int i=0; i<sons.size();i++){
				sons.get(i).showSelf(limit-1, rwdTypes, avg, nbType, depth+1);
			}
		} else {
			if( sons.size()>0){
				System.out.print("\t||Cut||");
			}
			System.out.println();
		}
	}
	
	public void showSelf(int limit, Vector<String> rwdTypes, boolean avg, String nbType){
		showSelf(limit, rwdTypes, avg, nbType, 0);
	}

	public void showSelf(int limit, Vector<String> rwdTypes, boolean avg){
		showSelf(limit, rwdTypes, avg, "");
	}
	
	public void showSelf(int limit, Vector<String> rwdTypes){
		showSelf(limit, rwdTypes, true);
	}
	
	public void showSelf(int limit,String rwdType, boolean avg){
		Vector<String> rwdTypes = new Vector<String>();
		rwdTypes.add(rwdType);
		showSelf(limit, rwdTypes, avg);
	}
	
	public void showSelf(int limit,String rwdType){
		showSelf(limit, rwdType, true);
	}
	
	public void showSelf(int limit){
		showSelf(limit,new Vector<String>());
	}
	
	/**
	 * Show the subtree represented by the current node
	 */
	public void showSelf(){
		showSelf(-1);
	}

	/**
	 * Given a set of nodes and the reward obtained in one terminal state, update the rewards and number of visits of all nodes
	 * @param nodePath	the list of nodes visited during one tree walk
	 * @param rwdType the type of the reward to be updated
	 * @param r	 the amount of reward to be incremented
	 * @param discount indicates the amount of discount that past rewards and nbs should take
	 * @return the average rewards of all updated nodes
	 */
	public static Vector<Double> update(Vector<MOUCT> nodePath, String rwdType, double r, int age, double discount){
		Vector<Double> avgRwds = new Vector<Double>();
		for(MOUCT nd : nodePath){
			avgRwds.add(nd.incrementRwd(rwdType, r, age, discount));
		}
		return avgRwds;
	}
	
	public static Vector<Double> update(Vector<MOUCT> nodePath, String rwdType, double r, int age){
		return update(nodePath, rwdType, r, age, 1.0);
	}
	
	public static Vector<Double> update(Vector<MOUCT> nodePath, String rwdType, double r){
		return update(nodePath, rwdType, r, -1);
	}
	
	/**
	 * Update the rewards of types indicated in rTypes in all nodes in nodePath
	 * @param nodePath
	 * @param rTypes
	 * @param rs
	 * @return the average vectorial rewards in each node (the size of returned points are equal to the size of nodePath)
	 */
	public static Vector<Vector<Double>> updateRwds(Vector<MOUCT> nodePath, 
			Vector<String> rTypes, Vector<Double> rs, int age, double discount){
		Vector<Vector<Double>> updatedAvgs = new Vector<Vector<Double>>();
		for(int i=0; i<rTypes.size(); i++){
			String rtp = rTypes.get(i);
			double r = rs.get(i);
			updatedAvgs.add(update(nodePath, rtp, r, age, discount));
		}
		
		return SetOperation.transposeMatrix(updatedAvgs);
	}
	
	public static Vector<Vector<Double>> updateRwds(Vector<MOUCT> nodePath, 
			Vector<String> rTypes, Vector<Double> rs, int age){
		return updateRwds(nodePath, rTypes, rs, age, 1.0);
	}
	
	public static Vector<Vector<Double>> updateRwds(Vector<MOUCT> nodePath, 
			Vector<String> rTypes, Vector<Double> rs){
		return updateRwds(nodePath, rTypes, rs, -1);
	}
	
	public void updateRAVE(Vector<Integer> acts, String rwdType, double r, double discount){
		for(int a : acts){
			incrementRAVE(a, rwdType, r, discount);
		}
	}
	
	public void updateRAVE(Vector<Integer> acts, String rwdType, double r){
		updateRAVE(acts, rwdType, r, 1.0);
	}
	
	public void updateRAVEs(Vector<Integer> acts, Vector<String> rwdTypes, Vector<Double> rs, double discount){
		for(int i=0;i<rwdTypes.size();i++){
			String rwdType = rwdTypes.get(i);
			double r = rs.get(i);
			updateRAVE(acts, rwdType, r, discount);
		}
	}
	
	public void updateRAVEs(Vector<Integer> acts, Vector<String> rwdTypes, Vector<Double> rs){
		updateRAVEs(acts, rwdTypes, rs, 1.0);
	}
	
	public static void updateRAVEs(Vector<MOUCT> nodes, 
			Vector<Integer> acts, Vector<String> rwdTypes, Vector<Double> rs, double discount){
		for(MOUCT n:nodes){
			n.updateRAVEs(acts, rwdTypes, rs, discount);
		}
	}
	
	public static void updateRAVEs(Vector<MOUCT> nodes, 
			Vector<Integer> acts, Vector<String> rwdTypes, Vector<Double> rs){
		updateRAVEs(nodes, acts, rwdTypes, rs, 1.0);
	}
	
	public static Vector<Integer> extractActs(Vector<MOUCT> nodes){
		Vector<Integer> acts = new Vector<Integer>();
		for(MOUCT n: nodes){
			int a = n.getAction();
			if(a!=-1) acts.add(a);
		}
		return acts;
	}
	
	/**
	 * When we set a subtree as the root node, contexts of all son nodes from such subtree should be modified 
	 */
	public void resetAsRoot(){
		action = -1;
		resetSonContexts(new Vector<Integer>());
	}
	
	public void resetSonContexts(Vector<Integer> cntxt){
		context = cntxt;
		Vector<Integer> newCntxt = (Vector<Integer>) cntxt.clone();
		newCntxt.add(this.action);
		for(MOUCT s : sons){
			s.resetSonContexts(newCntxt);
		}
	}
	
	public static void main(String[] args){
		MOUCT.defRwdTypes.add("a");
		MOUCT.defRwdTypes.add("b");
		
		MOUCT root = new MOUCT(-1);
		root.setNb(defRwdTypes.firstElement(), 0);
	
		for(int i=0; i<3; i++){
			MOUCT s = root.addSon(i);
			for(int j=0; j<5; j++){
				MOUCT ss = s.addSon(10-j);
				for(int k=0; k<8; k++){
					Vector<MOUCT> oneRun = new Vector<MOUCT>();
					oneRun.add(root);
					oneRun.add(s);
					oneRun.add(ss);
					if(k%2 == j%2) oneRun.add(ss.addSon(k));
					update(oneRun, defRwdTypes.firstElement(), 0.5, 3, 0.9);
				}
			}
		}
		
		root.showSelf();
		Presentation.spr();
		root.getSon(0).getSon(0).showSelf();
	}
}
