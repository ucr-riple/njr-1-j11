package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;

public class AnySeven extends Bet {

	public AnySeven(Layout layout, Player player, int bet) {
		super(layout, player, bet);
	}

	@Override
	public boolean diceRolled() {
		int roll = layout.getDice().getTotal();
		if (roll == 7)
			betWon(mainBet * 5);
		else
			betLost();
		return true;
	}

}
