package normchan.crapssim.engine;

public class NoSevenDice extends Dice {
	private final int goodRolls;
	private int countdown;
	private boolean trickMode = true;

	public NoSevenDice(int rolls) {
		super();
		goodRolls = rolls;
		countdown = rolls;
	}

	@Override
	public void roll() {
		if (!trickMode) {
			super.roll();
			return;
		}
		
		if (countdown > 0) {
			rollNonSeven();
			countdown--;
		} else {
			die1 = 6;
			die2 = 1;
			countdown = goodRolls;
		}
		announceRoll();
	}
	
	@Override
	public boolean toggleTrickDice() {
		if (trickMode) {
			trickMode = false;
			countdown = goodRolls; // reset countdown
		} else {
			trickMode = true;
		}
		return !trickMode;
	}

	@Override
	public boolean isTrickDice() {
		return trickMode;
	}

	private void rollNonSeven() {
		int total = 7;
		while (total == 7) {
			shakeDice();
			total = getTotal();
		}
	}
}
