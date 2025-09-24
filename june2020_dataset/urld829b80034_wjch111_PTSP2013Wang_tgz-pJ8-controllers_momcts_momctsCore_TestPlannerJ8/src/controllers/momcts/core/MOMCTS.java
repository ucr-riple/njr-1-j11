package controllers.momcts.core;

import java.util.HashMap;
import java.util.Vector;

import controllers.momcts.MOOTools;
import controllers.momcts.utils.Archive;
import controllers.momcts.utils.Debug;
import controllers.momcts.utils.Presentation;
import controllers.momcts.utils.SetOperation;


/**
 * Based on Monte-Carlo Tree Search algorithm, MOMCTS (Multi-Objective Monte-Carlo Tree Search) 
 * is used in Multi-objective squential decision making problems.
 * This class is a general purpose MOMCTS planner which accepts 1) initial state, 2) action number/action rule, 
 * and 3) simulator as input,
 * and returns optimal action sequence and predictive state model as output
 * @author wj 
 * @date 	created on 2013/07/11
 * @date	modified on 2013/07/16
 */
public abstract class MOMCTS {
	//-------------Meta reward prefix------------
	protected static String RdomType = "Dom";
	public String getRdomType(){
		return RdomType;
	}
	//-------------end of Meta reward prefix------------
	
	//-------------MOMCTS global variables---------------
	protected MOUCT root;
	
	protected Archive archive;

	/**
	 * Indicate the objectives to optimize in the MOMCTS planner
	 */
	protected Vector<String> objectives;
	
	/**
	 * Defines the node selection criterion
	 */
	protected String metaRewardType;
	
	/**
	 * Indicate the optimization direction in the planner
	 */
	protected boolean maximize;
	
	/**
	 * A counter which records the number of simulations passed in the planner
	 */
	protected int smt;
	
	/**
	 * Used to recored if one new non-dominated solution was found after one tree-walk
	 */
	protected boolean domAppeared;
	
	/**
	 * Used to record the vectorial rewards obtained in the last tree-walk
	 */
	protected Vector<Double> objRwds;
	
	/**
	 * Record the last evaluated strategy
	 */
	protected Vector<Integer> strtgy;
	//-------------end of MOMCTS global variables---------------
	
	//-------------MOMCTS parameters---------------------
	/**
	 * The constant to control the speed of progressive widening
	 */
	protected double pwConst;
	
	/**
	 * The Exploration vs. Exploitation constants used in the UCB formula
	 */
	protected HashMap<String,Double> EvEConsts;
	
	/**
	 * The rave parameter used to adjust the balance between global rave and local rave
	 */
	protected int raveLocal;
	
	/**
	 * Define the speed of discounting in reward updates
	 */
	protected double defDiscount;
	
	/**
	 * Limit the number of solutions associated with each vectorial reward
	 */
	protected int solNbLimitPerPoint;
	
	//-------------end of MOMCTS parameters---------------------
	
	//-------------Real time control parameters-----------------
	public static int seqTimeGranularity = 10;
	//-------------end of real time control parameters----------
	
	
	//-------------Debug paramaters---------------
	protected Debug dbg = new Debug();
	//-------------end of debug parameters--------
	
	public MOMCTS(String metaRewardType, boolean maximize,
			double pwConst, int raveLocal, double defDiscount) {
		super();
		this.root = new MOUCT(-1);
		this.archive = new Archive();

		this.metaRewardType = metaRewardType;
		this.maximize = maximize;
		this.pwConst = pwConst;
		this.raveLocal = raveLocal;
		this.defDiscount = defDiscount;
		this.domAppeared = false;
		
		this.EvEConsts = new HashMap<String,Double>();		
		this.smt = 0;
		this.solNbLimitPerPoint = -1;
		
		dbg.counter=0;
	}
	
	public void setObjectives(Vector<String> objs){
		objectives = objs;
		MOUCT.setDefRwdTypes(objs);
	}
	
	public String getMetaRewardType() {
		return metaRewardType;
	}

	public void setMetaRewardType(String metaRewardType) {
		this.metaRewardType = metaRewardType;
	}

	public double getPwConst() {
		return pwConst;
	}

	public void setPwConst(double pwConst) {
		this.pwConst = pwConst;
	}

	public void setEvEConst(String rwdType, double c) {
		EvEConsts.put(rwdType, c);
	}

	public double getEvEConst(String rwdType){
		return SetOperation.getDoubleValue(EvEConsts, rwdType);
	}
	
	public int getRaveLocal() {
		return raveLocal;
	}

	public void setRaveLocal(int raveLocal) {
		this.raveLocal = raveLocal;
	}

	public double getDefDiscount() {
		return defDiscount;
	}

	public void setDefDiscount(double defDiscount) {
		this.defDiscount = defDiscount;
	}

	public int getSolNbLimitPerPoint(){
		return solNbLimitPerPoint;
	}

	public void setSolNbLimitPerPoint(int solNbLimitPerPoint) {
		this.solNbLimitPerPoint = solNbLimitPerPoint;
		archive.setSolNbLimit(solNbLimitPerPoint);
	}

	public void setRoot(MOUCT rt){
		root = rt;
	}
	
	public MOUCT getRoot(){
		return root;
	}

	public Vector<String> getObjectives() {
		return objectives;
	}

	public boolean isMaximize() {
		return maximize;
	}

	public void setMaximize(boolean maximize) {
		this.maximize = maximize;
	}
	
	public int getSmt(){
		return smt;
	}
	
	public Archive getArchive() {
		return archive;
	}

	/**
	 * Get the sum of play times accumulated in reward rwdType
	 * @param sonNodes
	 * @param rwdType
	 * @return
	 */
	protected double totalNb(Vector<MOUCT> sonNodes, String rwdType){
		double totNb=0;
		for(MOUCT s: sonNodes){
			totNb += s.getNb(rwdType);
		}
		return totNb;
	}
	
	public boolean isDomAppeared(){
		return domAppeared;
	}
	
	public Vector<Double> getObjRwds(){
		return objRwds;
	}
	
	public Vector<Integer> getStrtgy(){
		return strtgy;
	}
 
	protected Vector<Double> rewards(Vector<MOUCT> sonNodes, String rwdType){
		Vector<Double> rs = new Vector<Double>();
		for(MOUCT s: sonNodes){
			rs.add(s.avgR(rwdType));
		}
		return rs;
	}
	
	protected Vector<Double> UCBRanks(Vector<MOUCT> sonNodes, String rwdType, double eveConst, boolean avg){
		Vector<Double> rks = new Vector<Double>();
		double totNb= totalNb(sonNodes, rwdType);
		for(MOUCT s: sonNodes){
			double r = s.avgR(rwdType);
			if(!avg) r = s.getRwd(rwdType);
			double rk = r + eveConst*Math.sqrt(Math.log10(totNb)/s.getNb(rwdType));
			rks.add(rk);
		}
		return rks;	
	}
	
	protected Vector<Double> UCBRanks(Vector<MOUCT> sonNodes, String rwdType, double eveConst){
		return UCBRanks(sonNodes, rwdType, eveConst, true);
	}
	
	protected Vector<Double> UCBRanks(Vector<MOUCT> sonNodes, String rwdType){
		return UCBRanks(sonNodes, rwdType, getEvEConst(rwdType));
	}
	
	public MOUCT bestUCB(Vector<MOUCT> sonNodes, String rwdType, boolean avg){
		return SetOperation.maxItem(sonNodes, UCBRanks(sonNodes, rwdType, getEvEConst(rwdType), avg));
	}
	
	public MOUCT bestUCB(MOUCT currNode, String rwdType, boolean avg){
		return bestUCB(currNode.getSons(), rwdType, avg);
	}
	
	public MOUCT bestUCB(MOUCT currNode, String rwdType){
		return bestUCB(currNode, rwdType, true);
	}
	
	/**
	 * Given a set of sonNodes, return the vectorial rewards of each sonNode
	 * @param sonNodes
	 * @param rwdTypes
	 * @return
	 */
	protected Vector<Vector<Double>> MOUCBRanks(Vector<MOUCT> sonNodes, Vector<String> rwdTypes){
		Vector<Vector<Double>> rks = new Vector<Vector<Double>>();
		for(String rt : rwdTypes){
			rks.add(UCBRanks(sonNodes, rt));
		}
		return SetOperation.transposeMatrix(rks);
	}

	/**
	 * Return the RAVE values of each action in acts under node.
	 * (RAVE values will be used to estimate action values when one action is 
	 * executed for the first time under certain node
	 * @param acts		the actions whose rave to be calculated
	 * @param rwdType
	 * @param node		if indicated, calculte the weighted sum between global rave and local rave
	 * @return
	 */
	protected Vector<Double> RAVERanks(Vector<Integer> acts, String rwdType, MOUCT node){
		Vector<Double> rks = new Vector<Double>();
		for(int a:acts){
			double gRAVE = root.getRAVE(a, rwdType);//global rave
			if(node != null){
				double beta = ((double)raveLocal)/(raveLocal+node.getRAVEnb(a, rwdType));
				double lRAVE = node.getRAVE(a, rwdType);//local rave
				rks.add((1-beta)*lRAVE+beta*gRAVE);
			} else {
				rks.add(gRAVE);
			}
		}
		return rks;
	}
	
	protected Vector<Double> RAVERanks(Vector<Integer> acts, String rwdType){
		return RAVERanks(acts, rwdType, null);
	}
	
	//protected Vector<Double> 
	

	/**
	 * Given a parent node, return the candidate actions that are feasible in this node
	 * @param parentNode The node containing the state information
	 * @return candidate actions that are feasible
	 */
	public abstract Vector<Integer> candidateActions(MOUCT parentNode);
	
	/**
	 * Given a sequence of actions, evaluate such sequence and return the vectorial reward
	 * @param actSeq the action sequence to be evaluated
	 * @return the rewards obtained by executing actSeq
	 */
	public abstract Vector<Double> evaluateSeq(Vector<Integer> actSeq);
	
	/**
	 * @param leafNode	the node from which the random path starts
	 * @return a random path which leads to the terminal state
	 */
	public abstract Vector<Integer> generateRandomPath(MOUCT leafNode);

	protected void updateRAVEs(Vector<MOUCT> ns, 
			Vector<Integer> rndActSeq, Vector<String> rwdTypes, Vector<Double> rs, double discount){
		@SuppressWarnings("unchecked")
		Vector<MOUCT> nodes = (Vector<MOUCT>) ns.clone();
		while(nodes.size()>0){
			MOUCT currN = SetOperation.popFront(nodes);
			Vector<Integer> actS = SetOperation.joinSeq(MOUCT.extractActs(nodes), rndActSeq);
			currN.updateRAVEs(actS,rwdTypes,rs, discount);
		}
	}
	
	protected void updateRAVEs(Vector<MOUCT> ns, 
			Vector<Integer> rndActSeq, Vector<String> rwdTypes, Vector<Double> rs){
		updateRAVEs(ns, rndActSeq, rwdTypes, rs, 1.0);
	}
	
	protected void updateRwds(Vector<MOUCT> nodes, Vector<String> objs, Vector<Double> objRwds, int age, double discount){
		MOUCT.updateRwds(nodes, objs, objRwds, age, discount);
	}
	
	protected void updateRwds(Vector<MOUCT> nodes, Vector<String> objs, Vector<Double> objRwds, int age){
		updateRwds(nodes, objs, objRwds, age, 1.0);
	}
	
	protected void updateArchive(Vector<Double> newPnt, Vector<Integer> sol){
		if(archive.addNewPoint(newPnt, sol)) archive.rearrange(maximize);
	}
	
	protected void updateArchive(Vector<Double> newPnt){
		if(archive.addNewPoint(newPnt)) archive.rearrange(maximize);
	}
	
	public Vector<Vector<Integer>> getOptimalSolutions(){
		return archive.getAllSolutions();
	}
	
	protected double updateDomReward(Vector<MOUCT> nodes, Vector<Double> newPnt, int age, String rwdType){
		double domRwd = 0;
		if(MOOTools.dominatesSet(newPnt,archive.getPoints(),maximize)) domRwd = 1;
		MOUCT.update(nodes, rwdType, domRwd, age, defDiscount);
		return domRwd;
	}
	
	/**
	 * Simulate one tree-walk in MOMCTS
	 * @return the nodes visited in this tree walk
	 */
	public Vector<MOUCT> playOneSequence(long a_timeDue, boolean pwEnabled, String nbType, boolean raveOn, boolean localRaveOn){
		//The sequence of nodes visited during the tree walk
		Vector<MOUCT> nodes = new Vector<MOUCT>();
		nodes.add(root);
		MOUCT currNd = nodes.lastElement();
		double remaining = seqTimeGranularity+1;
		if(a_timeDue > 0) remaining = a_timeDue-System.currentTimeMillis();

		//dbg.stopWatch();
		while(remaining > seqTimeGranularity && (currNd.isRoot() || currNd.getNb(nbType)>0)){
			Vector<Integer> candAs = candidateActions(currNd);
			if(candAs.size()==0) break;//when there is no more action to execute (terminal state reached), stop the path finding
			
			int nb = (int) currNd.getNb(nbType);
			int n1 = (int) Math.pow(nb-1, pwConst);
			int n2 = (int) Math.pow(nb, pwConst);
			
			if(nb == 0 || currNd.getSons().size() == 0 || (n2>n1 && currNd.getSons().size() < candAs.size()) ) {
				//Unexecuted actions
				Vector<Integer> unplayedActs = SetOperation.complementSet(candAs, currNd.sonActions());
				int newAct = SetOperation.randomElement(unplayedActs);
				
				Vector<Double> actValues;
				if(raveOn) {
					if(localRaveOn) actValues = RAVERanks(unplayedActs, metaRewardType, currNd);
					else actValues = RAVERanks(unplayedActs, metaRewardType, null);
				} else {
					actValues = new Vector<Double>(); 
				}	
				if(actValues.size() > 0) newAct = SetOperation.getBestItem(unplayedActs, actValues, maximize);
				nodes.add(currNd.addSon(newAct));
			} else {
				if(metaRewardType.contains(RdomType)) nodes.add(bestUCB(currNd, metaRewardType, false));//
				//if(metaRewardType.contains(RdomType)) nodes.add(SetOperation.randomElement(currNd.getSons()));
				else nodes.add(bestUCB(currNd, metaRewardType, true));
			}
			currNd = nodes.lastElement();
			if(a_timeDue > 0) remaining = a_timeDue-System.currentTimeMillis();
		}
		
		Vector<Integer> path = MOUCT.extractActs(nodes);
		Vector<Integer> randomPath = generateRandomPath(currNd);

		strtgy = SetOperation.joinSeq(path,randomPath);
		objRwds = evaluateSeq(strtgy);
		//Debug.debug(dbg.stopWatch()+":"+root.size(),"!");
		
		updateRwds(nodes, objectives, objRwds, smt);//basic rewards in each objective updated
		//updateRAVEs(nodes, randomPath, objectives, objRwds);

		double r = 0;
		if(metaRewardType.contains(RdomType)) r = updateDomReward(nodes, objRwds, smt, metaRewardType);
		//else if(strContains(metaRewardType, RrkType)) r = updateRankReward(nodes, newPnt, smt, metaRewardType);
		//else if(strContains(metaRewardType, RhviType)) r = updateHviReward(nodes, newPnt, smt, metaRewardType);
		//else if(strContains(metaRewardType, RprRkType)) r = updatePrRkReward(nodes, newPnt, smt, metaRewardType);
		
		if(r>0) {
			domAppeared = true;
		} else domAppeared = false;
		updateArchive(objRwds, strtgy);
		//root.updateRAVE(randomPath, metaRewardType, r);
		//Debug.debug(dbg.stopWatch(),"!");

		smt++;
		return nodes;
	}
	
	public Vector<MOUCT> playOneSequence(long a_timeDue, boolean pwEnabled, String nbType){
		return playOneSequence(a_timeDue, pwEnabled, nbType, false, false);
	}
	
	public Vector<MOUCT> playOneSequence(long a_timeDue, boolean pwEnabled){
		return playOneSequence(a_timeDue, pwEnabled, objectives.firstElement());
	}
	
	public Vector<MOUCT> playOneSequence(long a_timeDue){
		return playOneSequence(a_timeDue, false);
	}
	
	public Vector<MOUCT> playOneSequence(){
		return playOneSequence(0);
	}
}
