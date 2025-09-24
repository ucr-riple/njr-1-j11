package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;

public class AnyCraps extends Bet {

	public AnyCraps(Layout layout, Player player, int bet) {
		super(layout, player, bet);
	}

	@Override
	public boolean diceRolled() {
		int roll = layout.getDice().getTotal();
		if (roll == 2 || roll == 3 || roll == 12)
			betWon(mainBet * 8);
		else
			betLost();
		return true;
	}

}
