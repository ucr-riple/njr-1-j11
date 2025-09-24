package MazeRoomLogic;

import MazeGame.Player;

public class GrantKeyEffect implements PlayerEffect {

	@Override
	public void ApplyEffect() {
		System.out.println("Keys given!");
		Player.getInstance().addPoints(250);
		Player.getInstance().addKey();
	}

}
