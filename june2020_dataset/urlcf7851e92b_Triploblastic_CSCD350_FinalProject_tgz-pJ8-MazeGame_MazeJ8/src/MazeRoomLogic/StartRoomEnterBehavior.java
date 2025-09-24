package MazeRoomLogic;

import javax.swing.JOptionPane;

import MazeGame.Maze;
import MazeGame.Player;

public class StartRoomEnterBehavior implements MazeRoomEnterBehavior {

	//start room is always enterable
	@Override
	public boolean enter() {
		Player p = Player.getInstance();
		JOptionPane.showMessageDialog(Maze.mainWindow, "Player points: " + p.getPoints() + "\nPlayer Health: "+ p.getHealth() + "\nPlayer Keys: " + p.getKeys(), "Player Statistics", JOptionPane.INFORMATION_MESSAGE, null);
		return true;
	}

}
