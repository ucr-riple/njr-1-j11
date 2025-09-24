package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;

public class Field extends Bet {

	public Field(Layout layout, Player player, int bet) {
		super(layout, player, bet);
		checkBetLimits();
	}

	@Override
	public boolean diceRolled() {
		int roll = layout.getDice().getTotal();
		if (roll == 2 || roll == 12)
			betWon(mainBet * 3); // pay double
		else if ((roll >= 3 && roll <= 4) || (roll >= 9 && roll <= 11))
			betWon();
		else
			betLost();
		return true;
	}

}
