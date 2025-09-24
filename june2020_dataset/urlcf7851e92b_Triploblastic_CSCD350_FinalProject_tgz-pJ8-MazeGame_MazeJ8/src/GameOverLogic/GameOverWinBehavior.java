package GameOverLogic;

import javax.swing.JOptionPane;

import MazeGame.Maze;

public class GameOverWinBehavior implements GameOverBehavior {
	
	@Override
	public void Behave() {
		JOptionPane.showMessageDialog(Maze.mainWindow, "Congratualations! You have won the game!");
		
	}
	
	

}
