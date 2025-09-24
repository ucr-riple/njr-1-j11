package normchan.crapssim.simulation.tracker;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class ResultAggregator {
	private List<ResultTracker.Stats> results = new ArrayList<ResultTracker.Stats>();
	
	public void addResult(ResultTracker.Stats stats) {
		results.add(stats);
	}
	
	public void printResults(PrintStream stream) {
		if (results.isEmpty())
			return;
		
		int totalWinnings = 0;
		int totalLosses = 0;
		int sumOfSquares = 0;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		double mean;
		double stdDeviation;

		for (ResultTracker.Stats stats : results) {
			totalWinnings += stats.winAmount;
			totalLosses += stats.lossAmount;
			if (stats.winAmount - stats.lossAmount < min)
				min = stats.winAmount - stats.lossAmount;
			if (stats.winAmount - stats.lossAmount > max)
				max = stats.winAmount - stats.lossAmount;
		}
		mean = (double)(totalWinnings - totalLosses) / (double)results.size();
		for (ResultTracker.Stats stats : results) {
			sumOfSquares += Math.pow((double)stats.winAmount - stats.lossAmount - mean, 2);
		}
		stdDeviation = Math.sqrt(sumOfSquares / results.size());

		Formatter formatter = new Formatter(stream);
		formatter.format("Average win/loss: $%.2f\n", mean);
		formatter.format("Standard deviation: $%.2f\n", stdDeviation);
		formatter.format("Max: $%d Min: $%d\n\n", max, min);
		formatter.flush();
		stream.flush();
	}
}
