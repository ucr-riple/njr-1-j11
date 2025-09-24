package normchan.crapssim.simulation.tracker;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.event.SessionEvent;

public class PlayerTracker implements Tracker, Observer {
	private GameManager gameManager;
	private int initialBalance;
	private List<Integer> balances = new ArrayList<Integer>();
	
	private class Stats {
		int min;
		int max;
		double mean;
		double stdDeviation;
		double avgLosingBalance;
		double avgWinningBalance;
		double winPercentage;
		int wins = 0;
		int losses = 0;
	}
	private Stats stats;

	public Stats getStats() {
		return stats;
	}

	public PlayerTracker(GameManager manager, int initialBalance) {
		super();
		this.gameManager = manager;
		this.initialBalance = initialBalance;
		gameManager.getPlayer().addObserver(this);
	}

	@Override
	public void printResults(PrintStream stream) {
		Formatter formatter = new Formatter(stream);
		formatter.format("Player ended with an average balance of $%.2f (started with $%d)\n", stats.mean, initialBalance);
		formatter.format("Player ended with an standard deviation of $%.2f\n", stats.stdDeviation);
		if (stats.wins > 0) {
			formatter.format("Player's biggest win: $%d\n", stats.max - initialBalance);
			formatter.format("Player won on average $%.2f\n", stats.avgWinningBalance - initialBalance);
		}
		if (stats.losses > 0) {
			formatter.format("Player's largest loss: $%d\n", initialBalance - stats.min);
			formatter.format("Player lost on average $%.2f\n", initialBalance - stats.avgLosingBalance);
		}
		formatter.format("Player ended with winning percentage of %.1f%% (%d wins, %d losses)\n\n", stats.winPercentage*100, stats.wins, stats.losses);
		formatter.flush();
		stream.flush();
		
		gameManager.aggregateResults();
	}
	
	public void calculateStats() {
		stats = new Stats();
		
		Collections.sort(balances);
		stats.min = balances.get(0);
		stats.max = balances.get(balances.size() - 1);

		calculateStdDev();
	}
	
	private void calculateStdDev() {
		int sum = 0, losingTotal = 0, winningTotal = 0;
		List<Integer> losers = new ArrayList<Integer>();
		List<Integer> winners = new ArrayList<Integer>();
		for (Integer val : balances) {
			sum += val;
			if (val > initialBalance) {
				winners.add(val);
				winningTotal += val;
			} else if (val < initialBalance) {
				losers.add(val);
				losingTotal += val;
			}
		}
		stats.mean = (double)sum / (double)balances.size();
		stats.wins = winners.size();
		stats.losses = losers.size();
		stats.avgWinningBalance = winners.isEmpty() ? initialBalance : (double)winningTotal / (double)stats.wins;
		stats.avgLosingBalance = losers.isEmpty() ? initialBalance : (double)losingTotal / (double)stats.losses;
		stats.winPercentage = losers.isEmpty() ? 0 : (double)stats.wins / ((double)stats.wins + stats.losses); 
		
		int sumOfSquares = 0;
		for (Integer val : balances) {
			sumOfSquares += Math.pow((double)val - stats.mean, 2);
		}
		stats.stdDeviation = Math.sqrt(sumOfSquares / balances.size());
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof SessionEvent) {
			if (((SessionEvent)arg).getType() == SessionEvent.EventType.END)
				balances.add(new Integer(gameManager.getPlayer().getBalance()));
		}
	}
}
