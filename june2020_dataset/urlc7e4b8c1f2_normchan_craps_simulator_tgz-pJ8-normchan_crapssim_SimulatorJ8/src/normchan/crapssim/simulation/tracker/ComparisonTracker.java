package normchan.crapssim.simulation.tracker;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import normchan.crapssim.engine.GameManager;
import normchan.crapssim.engine.Player;

public class ComparisonTracker implements Tracker {
	private PlayerTracker tracker1;
	private PlayerTracker tracker2;

	public PlayerTracker getTracker1() {
		return tracker1;
	}

	public PlayerTracker getTracker2() {
		return tracker2;
	}

	public ComparisonTracker(GameManager manager1, GameManager manager2, int initialBalance) {
		this.tracker1 = new PlayerTracker(manager1, initialBalance);
		this.tracker2 = new PlayerTracker(manager2, initialBalance);
	}

	@Override
	public void calculateStats() {
		tracker1.calculateStats();
		tracker2.calculateStats();
	}

	@Override
	public void printResults(PrintStream stream) {
		stream.println("Tracker 1:");
		tracker1.printResults(stream);
		stream.println("Tracker 2:");
		tracker2.printResults(stream);
	}
}
