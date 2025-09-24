package GameOverLogic;

import javax.swing.JOptionPane;

import MazeGame.Maze;

public class GameOverLoseBehavior implements GameOverBehavior {

	@Override
	public void Behave() {
		JOptionPane.showMessageDialog(Maze.mainWindow, "OH NO! You just lost THE GAME");
		
	}

}
