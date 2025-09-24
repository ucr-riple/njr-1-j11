package MazeRoomLogic;

import MazeGame.Player;

public class IncreaseHealthEffect implements PlayerEffect {

	@Override
	public void ApplyEffect() {
		Player.getInstance().increaseHealth();
		Player.getInstance().addPoints(200);
		System.out.println("Player health increased!");
	}

}
