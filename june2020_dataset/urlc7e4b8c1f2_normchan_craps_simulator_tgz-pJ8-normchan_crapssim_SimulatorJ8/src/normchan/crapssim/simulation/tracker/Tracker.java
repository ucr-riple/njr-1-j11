package normchan.crapssim.simulation.tracker;

import java.io.PrintStream;

public interface Tracker {
	public void calculateStats();
	public void printResults(PrintStream stream);
}
