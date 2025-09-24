package normchan.crapssim.engine.bets;

import normchan.crapssim.engine.Layout;
import normchan.crapssim.engine.Player;
import normchan.crapssim.engine.exception.InvalidBetException;

public class HardWay extends FixedNumberBet {
	public HardWay(Layout layout, Player player, int bet, int number) {
		super(layout, player, bet, number);
		if (!(number == 4 || number == 6 || number == 8 || number == 10))
			throw new InvalidBetException("Invalid number for hard way bet");
	}

	@Override
	public boolean diceRolled() {
		if (layout.isNumberEstablished()) {
			if (layout.getDice().getTotal() == number) {
				if (layout.getDice().getDie1() == layout.getDice().getDie2()) {
					betWon();
					return true;
				} else {
					betLost();
					return true;
				}
			} else if (layout.getDice().getTotal() == 7) {
				betLost();
				return true;
			}
		}
		return false;
	}

	@Override
	protected void betWon() {
		if (number == 4 || number == 10)
			betWon(mainBet*8);
		else
			betWon(mainBet*10);
	}
}
