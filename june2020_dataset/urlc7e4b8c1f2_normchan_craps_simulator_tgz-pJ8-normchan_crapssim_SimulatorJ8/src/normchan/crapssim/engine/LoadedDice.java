package normchan.crapssim.engine;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import normchan.crapssim.engine.event.GameEvent;

public class LoadedDice extends Dice {
	Queue<int[]> pairs = new LinkedList<int[]>();

	public LoadedDice(int[][] sequence) {
		super();
		this.pairs.addAll(Arrays.asList(sequence));
	}

	@Override
	public void roll() {
		int [] pair = pairs.poll();
		if (pair != null) {
			die1 = pair[0];
			die2 = pair[1];
			announceRoll();
		} else {
			super.roll();
		}
	}
	
	@Override
	public boolean toggleTrickDice() {
		// Can only turn trick mode off
		boolean previous = false;
		if (!pairs.isEmpty()) {
			previous = true;
			pairs.clear();
		}
		return previous;
	}

	@Override
	public boolean isTrickDice() {
		return !pairs.isEmpty();
	}

	private static class TestObserver implements Observer {

		@Override
		public void update(Observable arg0, Object arg1) {
			System.out.println(((GameEvent)arg1).getMessage());
		}
		
	}

	public static void main(String[] args) {
		int[][] sequence = { {2, 5}, {4, 6}, {2, 1}, {5, 6}, {3, 3} };
		LoadedDice dice = new LoadedDice(sequence);
		dice.addObserver(new LoadedDice.TestObserver());
		for (int i = 0; i < 10; i++) {
			dice.roll();
		}
	}
}
