package mp1401.examples.misterx.model.game.states;

import mp1401.examples.misterx.model.game.Game;
import mp1401.examples.misterx.model.gameitems.Character;
import mp1401.examples.misterx.model.gameitems.Detective;

public class CheckSituationGameStates extends AbstractGameState {

	private static final long serialVersionUID = 4628543882519137561L;

	@Override
	public void checkSituation() {
		if (isGameFinished()) {
			setWinner();
			changeGameState(new FinishedGameState());
		} else {
			getGame().increaseRound();
			changeGameState(new MisterXMovementGameState());
		}
	}

	private boolean isGameFinished() {
		return getGame().getPositionChecker().isMisterXFound()
				|| hasMisterXWon();
	}

	private boolean hasMisterXWon() {
		return getGame().getRound() == Game.NUMBER_OF_ROUNDS_UNTIL_MISTER_X_WINS;
	}
	
	private Character determineWinner() {
		for (Detective detective : getGame().getDetectives()) {
			if(hasDetectiveFoundMisterX(detective)) {
				return detective;
			}
		}
		return getGame().getMisterX();
	}
	
	private boolean hasDetectiveFoundMisterX(Detective detective) {
		return detective.getCurrentPosition().equals(getGame().getMisterX().getCurrentPosition());
	}
	
	private void setWinner() {
		getGame().setWinner(determineWinner());
	}
}
