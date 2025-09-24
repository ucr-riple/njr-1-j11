package MazeRoomLogic;

import MazeGame.Player;

public class DecreaseHealthEffect implements PlayerEffect {

	@Override
	public void ApplyEffect() {
		Player.getInstance().decreaseHealth();
	}

}
