package controllers.momcts;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

import controllers.momcts.core.MOMCTS;
import controllers.momcts.core.MOUCT;
import controllers.momcts.utils.Archive;
import controllers.momcts.utils.Debug;
import controllers.momcts.utils.Logger;
import controllers.momcts.utils.MathOperation;
import controllers.momcts.utils.Presentation;
import controllers.momcts.utils.SetOperation;
import controllers.momcts.utils.Transformation;
import framework.core.Controller;
import framework.core.Game;
import framework.core.GameObject;
import framework.core.PTSPConstants;
import framework.core.Ship;
import framework.core.Waypoint;
import framework.graph.Node;
import framework.graph.Path;

/**
 * This class is created to solve the point to point navigation problem in the PTSP problem.
 * Macro actions are used in this class.
 * @author wj
 * @date	created 2013/07/17
 * @date	modified 2013/07/19
 */
public class ShortNavigator extends MOMCTS{
    /**
     * Best individual (route) found in the current search step.
     */
    public Vector<Integer> m_bestRandomPath;

    /**
     * Best heuristic cost of the best individual
     */
    public Vector<Double> m_bestPntFound;

    /**
     * Next two waypoints in the route to pick up.
     */
    public static int[] m_nextPickups;

    /**
     * Current game state
     */
    public static Game m_currentGameState;

    /**
     * Game state used to roll actions and evaluate  the current path.
     */
    public static Game m_futureGameState;

    /**
     * Cache  for speeding up looks for nodes in the graph.
     */
    public static HashMap<Integer, Node> m_nodeLookup;
    
    private static int collisionNb;
    private static int lavaNb;
    
    private int visitedWaypointNb = 0;
    
    /**
     * Use this variable to calculate the interval between visiting two waypoints, which is an objective to minimize in this controller
     */
    private int lastWaypointVisitTime = 0;
    
    private int currentWaypointVisitTime = 0;
    
    /**
     * Use the following variables to denote the number of abrupt turning actions which should be minimized
     * in order to encourage straight line navigation
     */
    private int lastSuccRotate = 0;
    private int lastAct;
    private int tmpLastAct;
    private int currentSuccRotate = 0;
    private Vector<Integer> rotateActions;



    //-----------parameters-------------
    /**
     * Number of macro-actions that form the random path.
     */
    public static int MACRO_ACTIONS_DEPTH = 4;
    
    /**
     * Determines how long should the random rollout phase extend
     */
    public static int prolongedStrgyLength = 2;//1;//
    
    /**
     * Number of single actions that form a macro action.
     */
    public static int[] MACRO_ACTION_LENGTHs = {10,15,15,15,15,15};
    
    public static int seqTimeGranularity = 15;//20;//
    
    public static int wayPointReward = 500;
    
    public static int succRotationPenalty = 500;
    
    public static int[] fuelTankRewards = {400, 500, 900, 1000};//the value of collecting fuel tanks increase gradually
    
    public static double collisionAvoidance = 0.8;//1;//.1;

    public static double lavaAvoidance = 0.5;//1;//.1;
    
    public static int defNeighbourhoodSize = 100;

    
    
    //-----------modeParams-------------
    private enum Modes {NORMAL, CONER, WAYPOINT, LAVA, HIGHSPEED};
    private class ModeParams{
    	public Modes gameMode = Modes.NORMAL;
        /**
         * Generation a weight vector indicating our preference to the points
         * in order ["dist","distNext","fuel","collDmg","lavaDmg","p2ptime","succRotPenl","pnt","fuelTank"]
         */
        private final double[] regularWts = {10, 3, 0.25, 5, 1, 10, wayPointReward};//, fuelTankRewards[0]};
        private final double[] conerWts = {10, 5, 0.15, 5, 1, 10, wayPointReward};//, fuelTankRewards[0]};
        private final double[] closeWaypointWts = {10, 5, 0.25, 1, 1, 10, wayPointReward};//, 0.5* fuelTankReward};
        private final double[] lavaWts = {10, 3, 0.15, 1, 5, 10, wayPointReward};//, 0.3* fuelTankReward};
        private final double[] highSpdWts = {10, 6, 0.25, 5, 0.5, 10, wayPointReward};//, 0.7 * fuelTankReward};, succRotationPenalty
        
        private final int[] regularActs = {1,2,3,4,5};
        private final int[] conerActs = {1,2,3,4,5};
        private final int[] closeWaypointActs = {1,2,3,4,5};
        private final int[] lavaActs = {1,2,3,4,5};
        
    	public Vector<Double> getPrefs(Game gm){
    		
    		double fuelRwd = fuelTankRewards[0];
    		if(gm != null) {
    			if(PTSPConstants.INITIAL_FUEL - gm.getShip().getRemainingFuel() < PTSPConstants.FUEL_TANK_BOOST *0.6) fuelRwd = 0;
    			else fuelRwd = fuelTankRewards[gm.getFuelTanksCollected()%fuelTankRewards.length];
    		}
    		Vector<Double> wts;
    		switch(gameMode){
    		case CONER: wts = Transformation.doubleAry2Vec(conerWts);wts.add(fuelRwd); break;
    		case WAYPOINT: wts = Transformation.doubleAry2Vec(closeWaypointWts);wts.add(0.5*fuelRwd); break;
    		case LAVA: wts = Transformation.doubleAry2Vec(lavaWts);wts.add(fuelRwd); break;
    		case HIGHSPEED: wts = Transformation.doubleAry2Vec(highSpdWts);wts.add(fuelRwd); break;
    		default: wts = Transformation.doubleAry2Vec(regularWts);wts.add(fuelRwd); break;
    		}
    		return wts;
     	}
    	
    	public Vector<Double> getPrefs(){
    		return getPrefs(null);
    	}
    	
    	public Vector<Integer> getCandidateActSet(){
    		switch(gameMode){
    		case CONER: return Transformation.intAry2Vec(conerActs);
    		case WAYPOINT: return Transformation.intAry2Vec(closeWaypointActs);
    		case LAVA: return Transformation.intAry2Vec(lavaActs);
    		default: return Transformation.intAry2Vec(regularActs);
    		}
    	}
    }

    /**
     * Record the user preference which determines the priorities between non-dominated solutions
     */
    private static ModeParams gameParams;
    //-----------end of modeParams-------------

    //-----------end of parameters-------------
    
    //-----------Debug use----------------
    public Debug dbg = new Debug();
    //-----------end of debug use--------

	public ShortNavigator() {
		super(ShortNavigator.class.getName()+RdomType , false, 1, 10, 0.5);
		String[] objs = {"dist","dNext","fuel","collDmg","lavaDmg","p2pT","pnt","fuelTank"};//,"succRotate"};//,"scRotP"
		Vector<String> objv = Transformation.strAry2Vec(objs);
		setObjectives(objv);
		this.setSolNbLimitPerPoint(3);
		this.setEvEConst(metaRewardType, 10);
        
        int[] rActs = {4,5};
        rotateActions = Transformation.intAry2Vec(rActs);
        
		this.init(-1);
		this.turnOffGameCounts();

        m_nodeLookup = new HashMap<Integer, Node>();
        gameParams = new ModeParams();

        //dbg.turnOffDebug();
	}
	
	public static Integer oppositeAction(int a){
		switch(a){
		case 1:return 2;
		case 2:return 1;
		case 4:return 5;
		case 5:return 4;
		default:break;
		}
		return null;
	}
	
	public void turnOffGameCounts(){
		collisionNb = -1;
		lavaNb = -1;
	}

	public void turnOnGameCounts(){
		collisionNb = 0;
		lavaNb = 0;
	}
	
	public void resetRotationCounts(int a, int baseCount){
	    tmpLastAct = a;
	    lastSuccRotate = baseCount;
	}
	
	public void resetRotationCounts(int a){
		resetRotationCounts(a, 0);
	}
	
	public void resetRotationCounts(){
	    resetRotationCounts(m_bestRandomPath.firstElement());
	}
	
	public boolean isSuccRotate(int a, int lastA){
		if( rotateActions.contains(a) && a == lastA) return true;
		return false;
	}
	
	public void advanceGame(Game gm, int a){
		if(a == -1) return;
		
		/*
		if(isSuccRotate(a, tmpLastAct)){
			lastSuccRotate++;
		} else {
			resetRotationCounts(a);
		}*/
		
		
		for(int i = 0; i < MACRO_ACTION_LENGTHs[a]; i++){
            //make the moves to advance the game state.
            gm.tick(a);
            if(currentWaypointVisitTime == -1 && visitedWaypointNb < gm.getWaypointsVisited()){
            	currentWaypointVisitTime = gm.getTotalTime();
            }
            
            if(collisionNb != -1 && gm.getShip().getCollLastStep()) {
            	switch(gm.getShip().getLastCollisionType()){
            	case PTSPConstants.ELASTIC_COLLISION_TYPE: break;//no damage
            	case PTSPConstants.NORMAL_COLLISION_TYPE: collisionNb += PTSPConstants.DAMAGE_NORMAL_COLLISION; break;
            	case PTSPConstants.DAMAGE_COLLISION_TYPE: collisionNb += PTSPConstants.DAMAGE_DAMAGE_COLLISION; break;
            	default: break;
            	}
            }
            if(lavaNb != -1 && gm.getShip().isOnLava()) lavaNb += PTSPConstants.DAMAGE_LAVA;
        }
	}

	public void advanceGame(Game gm, Vector<Integer> as){
		currentWaypointVisitTime = -1;//initialize the waypoint visiting time recorder
		int maxSuccRotate = 0;
		
		//System.out.print(lastAct+"->"+currentSuccRotate+":");Presentation.showSeqln(as);		
		for(Integer a : as){
			advanceGame(gm, a);
			if(maxSuccRotate < lastSuccRotate) maxSuccRotate = lastSuccRotate;
		}
		lastSuccRotate = maxSuccRotate;
		//Debug.debug(lastSuccRotate);
		//Presentation.spr();
	}

	public int isInCorner(Game gm, int neighbourHood){
		int[] testActs = {3,4,5};
		int wallSides = 0;
		for(int i=0; i<testActs.length;i++){
			Game tmpGm = gm.getCopy();
			Ship tmpShip = tmpGm.getShip();
			tmpShip.radius = neighbourHood;
			advanceGame(tmpGm, testActs[i]);
			advanceGame(tmpGm, 3);
			advanceGame(tmpGm, 3);
			advanceGame(tmpGm, 3);
			advanceGame(tmpGm, 3);
			if(tmpShip.getCollLastStep()) wallSides++;
		}		
		return wallSides;
	}
	
	public int isInCorner(Game gm){
		return isInCorner(gm, defNeighbourhoodSize);
	}

	public double distToWaypoint(Game gm){
		if(m_nextPickups == null) return 0;
		Waypoint obj0 = gm.getWaypoints().get(m_nextPickups[0]);
        if(obj0.isCollected()) {
        	return 0;
        } else {
        	Path pathToFirst = getPathToGameObject(gm,obj0, m_nextPickups[0]);
        	return pathToFirst.m_cost;
        }
	}
	
	public double distToNextWaypoint(Game gm){
		if(m_nextPickups == null) return 0;
		if(m_nextPickups.length == 1){
        	return 0;
        } else {
        	Waypoint obj1 = gm.getWaypoints().get(m_nextPickups[1]);
        	if(obj1.isCollected()) return 0;
        	else {
        		return gm.getShip().s.dist(obj1.s);//
        		//Path pathToSecond = getPathToGameObject(m_futureGameState,obj1, m_nextPickups[1]);
        		//distanceToNextNext = pathToSecond.m_cost;//
        	}
        }
	}
	
	public boolean isCloseToWaypoint(Game gm){
		double dist = distToWaypoint(gm);
		if(dist <= defNeighbourhoodSize) return true;
		else return false;
	}
	
	public boolean isOnLava(Game gm){
		return gm.getShip().isOnLava();
	}
	
	public boolean isHighSpeed(Game gm, double spdTh){
		return gm.getShip().v.mag() >= spdTh;
	}
	
	public boolean isHighSpeed(Game gm){
		return isHighSpeed(gm, 1.0);
	}
	
	/**
	 * Heuristics added
	 * x 1. no successive controversial actions
	 * 2. collision avoidance with probability collisionAvoidance
	 * 3. lava avoidance with probability lavaAvoidance
	 */
	public Vector<Integer> gameRelatedCandidateActions(Vector<Integer> contextActs) {
		m_futureGameState = m_currentGameState.getCopy();
		advanceGame(m_futureGameState, contextActs);
		Vector<Integer> candActs = gameParams.getCandidateActSet();
		
		for(int i=0; i<candActs.size();i++){
			Game nextFutureGameState = m_futureGameState.getCopy();
			Integer act = candActs.get(i);
			advanceGame(nextFutureGameState, act);
			Ship sp = nextFutureGameState.getShip();

			if(sp.checkCollisionInPosition(sp.s)) {
				//System.out.println("collision");
				if(MathOperation.bernouilli(collisionAvoidance)) candActs.remove(act);
			} else if(sp.isOnLava()){
				//System.out.println("lava!");
				if(MathOperation.bernouilli(lavaAvoidance)) candActs.remove(act);
			}
		}
		//if(gameParams.gameMode == Modes.CONER) candActs.remove( oppositeAction(contextActs.lastElement()));
		
		if(candActs.size()==0) return gameParams.getCandidateActSet();
		else return candActs;
	}	
	
	
	@Override
	public Vector<Integer> candidateActions(MOUCT parentNode) {		
		if(parentNode == null) return SetOperation.arithmeticProgression(Controller.NUM_ACTIONS);
		if(parentNode.getDepth() < MACRO_ACTIONS_DEPTH){
			//Vector<Integer> candAs = SetOperation.arithmeticProgression(Controller.NUM_ACTIONS);
			Vector<Integer> candAs = gameParams.getCandidateActSet();//SetOperation.arithmeticProgression(Controller.NUM_ACTIONS-1, 1);//do not generate action 0
			//if(gameParams.gameMode == Modes.CONER) candAs.remove(oppositeAction(parentNode.getAction()));
			//candAs
			return candAs;
		} else return new Vector<Integer>();
	}

	@Override
	public Vector<Integer> generateRandomPath(MOUCT leafNode) {
		Vector<Integer> path = leafNode.getHistoryActs();
		Vector<Integer> rndPath = new Vector<Integer>();
		int act = leafNode.getAction();
		for(int i = leafNode.getDepth();i < MACRO_ACTIONS_DEPTH + prolongedStrgyLength; i++){
			//act = MathOperation.rndInt(Controller.NUM_ACTIONS);//
			act = SetOperation.randomElement(gameRelatedCandidateActions(SetOperation.joinSeq(path, rndPath)));//
			rndPath.add(act);
		}
		return rndPath;
	}
	
	@Override
	public Vector<Double> evaluateSeq(Vector<Integer> actSeq) {
        m_futureGameState = m_currentGameState.getCopy();
        turnOnGameCounts();//turn on the collision and lava counts
        advanceGame(m_futureGameState, actSeq);
        Vector<Double> rs = gameRewards();
        turnOffGameCounts();
        return rs;
	}
	
	private Vector<Double> gameRewards(){
		Vector<Double> gameRwds = new Vector<Double>();
		double distanceToNext = distToWaypoint(m_futureGameState);
		double distanceToNextNext = distToNextWaypoint(m_futureGameState);
		double fuelPoints = m_currentGameState.getShip().getRemainingFuel() - m_futureGameState.getShip().getRemainingFuel();
		//double damageTaken = m_futureGameState.getShip().getDamage();//- this.m_currentGameState.getShip().getDamage();//;//
		
		double wayPointsObtained = m_futureGameState.getWaypointsVisited() - m_currentGameState.getWaypointsVisited();
		//when waypoints other than the planned (2) waypoints are obtained, wayPointsObtained reward more
		//this measure is supposed to force the ship create better plans than the longNavigator's plan
		wayPointsObtained *= 2;
		if(m_nextPickups != null){
			if(distanceToNext == 0) wayPointsObtained -= 1;
			if(m_nextPickups.length > 1){
				if(distanceToNextNext == 0) wayPointsObtained -= 0.5;
			} else {
				wayPointsObtained += 1;//if there exist only one waypoint to get, getting this waypoint quickly then become critical
			}

		}
		
		int fuelTankLeft = m_currentGameState.getFuelTanksCollected() - m_futureGameState.getFuelTanksCollected();
		int timeIntWpts = 800;
		if(currentWaypointVisitTime > 0) {
			//Debug.debug(currentWaypointVisitTime +" time "+ lastWaypointVisitTime);
			timeIntWpts = currentWaypointVisitTime - lastWaypointVisitTime;
		}
		
		//double succPenalty = 0;
		//if(gameParams.gameMode == Modes.HIGHSPEED && lastSuccRotate > 1) succPenalty = lastSuccRotate*0.5;//
		

		//TODO modify rewards
        gameRwds.add(distanceToNext);
        gameRwds.add(distanceToNextNext);
        gameRwds.add(fuelPoints);
        gameRwds.add((double) collisionNb);
        gameRwds.add((double) lavaNb);
        gameRwds.add((double) timeIntWpts);
        //gameRwds.add((double) succPenalty);
        gameRwds.add(-wayPointsObtained);
        gameRwds.add((double) fuelTankLeft);

        return gameRwds;
	}

	
    /**
     * Gets the path from the current location of the ship to the object passed as parameter.
     * @param a_game copy of the current game state.
     * @param a_gObj object ot get the path to.
     * @param a_objKey index of the object to look for.
     * @return the path from the current ship position to  a_gObj.
     */
    private static Path getPathToGameObject(Game a_game, GameObject a_gObj, int a_objKey){
        //The closest node to the ship's location.
        Node shipNode = MOMCTSController.m_graph.getClosestNodeTo(a_game.getShip().s.x, a_game.getShip().s.y);

        //The closest node to the target's location (checking the cache).
        Node objectNode = null;
        if(m_nodeLookup.containsKey(a_objKey))
            objectNode = m_nodeLookup.get(a_objKey);
        else{
            objectNode = MOMCTSController.m_graph.getClosestNodeTo(a_gObj.s.x, a_gObj.s.y);
            m_nodeLookup.put(a_objKey, objectNode);
        }
        //Get the parh between the nodes.
        return MOMCTSController.m_graph.getPath(shipNode.id(), objectNode.id());
    }
    
	/**
     * Updates  m_nextPickups, that indicates the next a_howMany waypoints to follow.
     * @param a_howMany number of waypoints to include in the search.
     */
    public static void updateNextWaypoints(int a_howMany){
        m_nextPickups = null;
        LinkedList<Waypoint> waypoints = m_currentGameState.getWaypoints();
        //Number of waypoints visited.
        int nVisited = m_currentGameState.getWaypointsVisited();
        if(nVisited != waypoints.size()){
            //Array with the next waypoints to visit, considering the case where there are less available.
            m_nextPickups = new int[Math.min(a_howMany, waypoints.size() - nVisited)];
            int pLength =  m_nextPickups.length; //number of elements to pick up.
            int bestPath[] = MOMCTSController.m_tspGraph.getBestPath();
            
            //Go through the best path and check for what is collected.
            for(int i = 0, j = 0; j < pLength && i<bestPath.length; ++i){
                int key = bestPath[i];
                if(!waypoints.get(key).isCollected()){
                    //The first pLength elements not visited are selected.
                    m_nextPickups[j++] = key;
                }
            }
        }
        
        /*
        Vector<Integer> unvstWpKeys = new Vector<Integer>();
        Vector<Double> dists = new Vector<Double>();
        for(int i=0; i<waypoints.size();i++){
        	Waypoint wp = waypoints.get(i);
        	if(!wp.isCollected()) {
        		unvstWpKeys.add(i);
        		dists.add(m_currentGameState.getShip().s.dist(wp.s));
        	}
        }
        
        if(unvstWpKeys.size()==0) return;
        unvstWpKeys = SetOperation.sortItems(unvstWpKeys, dists, true);
        int nextNb = Math.min(a_howMany, unvstWpKeys.size());
        m_nextPickups = new int[nextNb];
        for(int i=0; i<nextNb; i++){
        	m_nextPickups[i] = unvstWpKeys.get(i);
        }*/
    }
    
    /**
     * Initializes the random search engine. This function is also called to reset it.
     * @param m_lastMacroActionExecuted 
     */
    public void init(int lastActionExecuted){
        //Resetting the random paths found and best fitness.
        m_bestRandomPath = new Vector<Integer>();
        for(int i=0; i<MACRO_ACTIONS_DEPTH + prolongedStrgyLength; i++){
        	m_bestRandomPath.add(0);
        }
        m_bestPntFound = new Vector<Double>();

        
        if(isSuccRotate(lastActionExecuted, lastAct)){
        	currentSuccRotate++;
        } else {
        	currentSuccRotate = 0;
        }
        resetRotationCounts(lastActionExecuted, currentSuccRotate);
        lastAct = lastActionExecuted;
        
        MOUCT s = null;//root.findSon(lastActionExecuted);//
        if(s==null) root = new MOUCT(-1);
        else {
        	s.resetAsRoot();
        	root = s;
        }
        archive = new Archive();
    }
    
    /**
     * Runs the Random Search engine for one cycle.
     * @param a_gameState Game state where the macro-action to be decided must be executed from.
     * @param a_timeDue When this function must end.
     * @return  the action decided to be executed.
     */
    public int run(Game a_gameState, long a_timeDue){
        m_currentGameState = a_gameState;
        updateNextWaypoints(2);
        int currentWptNb = m_currentGameState.getWaypointsVisited();
        if(visitedWaypointNb < currentWptNb){
        	//Debug.debug(visitedWaypointNb+" vs "+ currentWptNb);
        	//Debug.debug(lastWaypointVisitTime+" time "+m_currentGameState.getTotalTime());
        	visitedWaypointNb = currentWptNb;
        	lastWaypointVisitTime = m_currentGameState.getTotalTime();
        }
        double remaining = (a_timeDue-System.currentTimeMillis());
        if(isHighSpeed(a_gameState)){
        	gameParams.gameMode = Modes.HIGHSPEED;
        } /*else if(isCloseToWaypoint(a_gameState)){
        	gameParams.gameMode = Modes.WAYPOINT;
        }*/ else if (isOnLava(a_gameState)){
        	gameParams.gameMode = Modes.LAVA;
        } /*else if(isInCorner(a_gameState)>1) {
        	gameParams.gameMode = Modes.CONER;
        } */else {
        	gameParams.gameMode = Modes.NORMAL;
        }
        //check that we don't overspend
        while(remaining > seqTimeGranularity){
        	//m_futureGameState = m_currentGameState.getCopy();//m_futureGameState will be used in the candidateActions() function in playOneSequence()
        	playOneSequence(a_timeDue);
            //create and evaluate a new random path.
        	if(isDomAppeared()){
        		Vector<Double> prefs = gameParams.getPrefs(a_gameState);
        		Vector<Double> optPnt = archive.getBestPoint(prefs, maximize);
        		double optPathFitness = SetOperation.weightedSum(optPnt, prefs);
        		Vector<Integer> optSol = archive.getOneSol(optPnt);
                double bestFitness = Integer.MAX_VALUE;
                if(m_bestPntFound.size()>0) bestFitness = SetOperation.weightedSum(m_bestPntFound, prefs);
            	if(SetOperation.isBetter(optPathFitness, bestFitness, maximize)){
                	m_bestPntFound = optPnt;
            		m_bestRandomPath = optSol;
            	}

				//archive.showPointScores(prefs);
            	
                Debug.debug(gameParams.gameMode);
                Presentation.showSeq(optPnt); System.out.println("->"+Presentation.ndigits(optPathFitness));
        		Presentation.showSeqln(prefs);
        		System.out.println(archive.getPoints().size());
            	Presentation.spr();/**/
        	}
            //update remaining time.
            remaining = (a_timeDue-System.currentTimeMillis());
        }
        //take the best one so far, the best macroaction is the first one of the path.
        

        //Debug.debug(m_bestRandomPath.firstElement());
        //root.showSelf(1);//, this.metaRewardType);
        //Presentation.spr("x");
        return m_bestRandomPath.firstElement();
    }
    
}
