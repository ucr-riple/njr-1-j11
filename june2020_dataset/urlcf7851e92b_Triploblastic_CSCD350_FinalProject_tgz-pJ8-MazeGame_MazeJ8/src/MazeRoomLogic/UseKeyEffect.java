package MazeRoomLogic;

import MazeGame.Player;

//MazeRoom behavior that removes a key from the player
public class UseKeyEffect implements PlayerEffect {

	@Override
	public void ApplyEffect() {
		Player.getInstance().removeKey();
	}

}
