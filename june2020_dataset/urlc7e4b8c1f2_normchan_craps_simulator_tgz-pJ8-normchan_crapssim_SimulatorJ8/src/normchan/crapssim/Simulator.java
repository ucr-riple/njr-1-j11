package normchan.crapssim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import normchan.crapssim.engine.Dice;
import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.NoSevenDice;
import normchan.crapssim.simulation.Controller;
import normchan.crapssim.simulation.strategy.ControlComeOutHedgeStrategy10;
import normchan.crapssim.simulation.strategy.MaxStrategy2;
import normchan.crapssim.simulation.strategy.ProgressiveComeOutHedgeStrategy10;
import normchan.crapssim.simulation.strategy.ProgressiveRollStrategy10;
import normchan.crapssim.simulation.tracker.ComparisonTracker;
import normchan.crapssim.simulation.tracker.PlayerTracker;
import normchan.crapssim.simulation.tracker.ResultAggregator;
import normchan.crapssim.simulation.tracker.Tracker;

public class Simulator {
	private final static int TOTAL_SIMULATION_RUNS = 100000;
	private static List<GameManager> managers = new ArrayList<GameManager>();
	private static Tracker tracker = null;

	private final static int[][] DICE_SEQUENCE = { {3, 5}, {5, 3}, {2, 2}, {3, 3}, {1, 3}, {1, 5}, {4, 4}, {2, 2}, {2, 5}, {6, 2}, {4, 4} };
//	private final static Class[] STRATEGIES = { LayStrategy.class };
	private final static Class[] STRATEGIES = { ProgressiveComeOutHedgeStrategy10.class, ControlComeOutHedgeStrategy10.class };
	
	public static void main(String[] args) {
		Dice dice = null;
//		dice = new NoSevenDice(25);
//		dice = new LoadedDice(DICE_SEQUENCE);
		List<Class> playerStrategyClasses = Arrays.asList( STRATEGIES );
		for (Class playerStrategyClass : playerStrategyClasses) {
			try {
				GameManager manager = new GameManager(dice, playerStrategyClass);
				manager.setLogGameDetails(false);
				managers.add(manager);
			} catch (Exception e) {
				System.err.println("Failed to instantiate GameManager using "+playerStrategyClass.getName()+" class, exiting...");
				e.printStackTrace();
				System.exit(1);
			}
		}

		if (playerStrategyClasses.size() == 1) {
			tracker = new PlayerTracker(managers.get(0), GameManager.INITIAL_BALANCE);
		} else if (playerStrategyClasses.size() == 2) {
			tracker = new ComparisonTracker(managers.get(0), managers.get(1), GameManager.INITIAL_BALANCE);
		} else {
			System.err.println("Invalid number of strategies classes specified ("+playerStrategyClasses.size()+"), exiting...");
			System.exit(1);
		}
		
		for (GameManager manager : managers) {
			for (int i = 0; i < TOTAL_SIMULATION_RUNS; i++) {
				Controller controller = setup(manager);
				controller.run();
			}
			System.out.println(TOTAL_SIMULATION_RUNS+" runs complete with "+manager.getPlayer().getStrategy().getClass().getSimpleName()+".");
		}

		summarizeData();
	}
	
	private static void summarizeData() {
		tracker.calculateStats();
		tracker.printResults(System.out);
	}
	
	private static Controller setup(GameManager manager) {
		manager.resetPlayer();
//		controller = new SimpleController();
		Controller controller = new Controller();
		controller.setManager(manager);
		controller.reset();
		return controller;
	}
}
