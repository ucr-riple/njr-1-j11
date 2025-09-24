package MazeRoomLogic;

//This MazeRoom behavior is triggered when the player reaches the exit tile
//Not sure if this is how we want to trigger the win yet
public class WinGameEffect implements PlayerEffect {

	@Override
	public void ApplyEffect() {
		System.out.println("You win");

	}

}
