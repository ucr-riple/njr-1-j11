package controllers.momcts;

//import controllers.utils.Debug;
import controllers.momcts.utils.Debug;
import framework.core.Controller;
import framework.core.Game;
import framework.graph.Graph;

/**
 * This is the controller created for the MO-PTSP 2013 competition based on the MOMCTS algorithm
 * 
 * @author wj 
 * @date created on 2013/07/11
 * @date modified on 2013/07/19
 */
public class MOMCTSController extends Controller{
    /**
     * Graph to do pathfining.
     */
    public static Graph m_graph;

    /**
     * Best route (order of waypoints) found to follow.
     */
    public int[] m_bestRoute;

    /**
     *   Current action in the macro action being executed
     */
    private int m_executedMacroActionNb;

    /**
     * TSP solver.
     */
    public static LongNavigator m_tspGraph;
    
    /**
     * Random Search engine to find the optimal macro-action to execute.
     */
    private ShortNavigator m_p2pNivgator;

    /**
     * Flag that indicates if the RS engine must be restarted (a new action has been decided).
     */
    boolean m_resetRS;

    /**
     *  Last macro action to be executed.
     */
    private int m_macroActionUnderExecution;
    
    /**
     * Record the last macro-action
     */
    private int m_lastMacroActionExecuted;
    
    /**
     * Record the number of waypoints left to visit
     */
    private int wptVistNb;
    
    //-----------Debug use----------------
    public Debug dbg = new Debug();
    //-----------end of debug use--------
    
    
    public MOMCTSController(Game a_game, long a_timeDue){
        m_resetRS = true;
        m_graph = new Graph(a_game);
        m_tspGraph = new LongNavigator(a_game, m_graph);
        m_p2pNivgator = new ShortNavigator();
        m_executedMacroActionNb = 0;
        m_macroActionUnderExecution = 0;
        
        m_tspGraph.solve();
        m_bestRoute = m_tspGraph.getBestPath();
        wptVistNb = a_game.getWaypointsLeft();

        dbg.resetMemoryCount();
    }
    
	@Override
	public int getAction(Game a_game, long a_timeDue) {
    	int cycle = a_game.getTotalTime();
        int nextMacroAction;
        if(wptVistNb != a_game.getWaypointsLeft()){
        	wptVistNb = a_game.getWaypointsLeft();
        }

        if(cycle == 0){
            //First cycle of a match is special, we need to execute any action to start looking for the next one.
            m_macroActionUnderExecution = 1;
            nextMacroAction = m_macroActionUnderExecution;
            m_lastMacroActionExecuted = m_macroActionUnderExecution;
            m_resetRS = true;
            m_executedMacroActionNb = ShortNavigator.MACRO_ACTION_LENGTHs[m_macroActionUnderExecution]-1;
            //dbg.resetMemoryCount();
        } else {
            //advance the game until the last action of the macro action
            prepareGameCopy(a_game);
            if(m_executedMacroActionNb > 0){
            	if(m_resetRS){
                    //search needs to be restarted.
                    m_p2pNivgator.init(m_lastMacroActionExecuted);
                }
                //keep searching, but it is not time to retrieve the best action found
                m_p2pNivgator.run(a_game, a_timeDue);
                //we keep executing the same action decided in the past.
                nextMacroAction = m_macroActionUnderExecution;
                m_executedMacroActionNb--;
                m_resetRS = false;
            } else if(m_executedMacroActionNb == 0){
            	nextMacroAction = m_macroActionUnderExecution; //default value
                //keep searching and retrieve the action suggested by the random search engine.
                int suggestedAction = m_p2pNivgator.run(a_game, a_timeDue);
                //now it's time to execute this action. Also, in next cycle, we need to reset the search
                m_resetRS = true;
                if(suggestedAction != -1){
                	m_lastMacroActionExecuted = m_macroActionUnderExecution;
                	m_macroActionUnderExecution = suggestedAction;
                	m_executedMacroActionNb = ShortNavigator.MACRO_ACTION_LENGTHs[m_macroActionUnderExecution]-1;
                }
            }else{
            	throw new RuntimeException("This should not be happening: " + m_executedMacroActionNb);
            }
        }
        
        //Debug.debug(dbg.getMemoryChangeMB()+" MB");
        return nextMacroAction;
	}

	/**
     * Updates the game state using the macro-action that is being executed. It rolls the game up to the point in the
     * future where the current macro-action is finished.
     * @param a_game  State of the game.
     */
    public void prepareGameCopy(Game a_game){
        //If there is a macro action being executed now.
        if(m_macroActionUnderExecution != -1){
            //Find out how long have we executed this macro-action
            int first = ShortNavigator.MACRO_ACTION_LENGTHs[m_macroActionUnderExecution] - m_executedMacroActionNb - 1;
            for(int i = first; i < ShortNavigator.MACRO_ACTION_LENGTHs[m_macroActionUnderExecution]; i++){
                //make the moves to advance the game state.
                a_game.tick(m_macroActionUnderExecution);
            }
        }
    }
}
