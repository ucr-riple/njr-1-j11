package GameOverLogic;

import java.awt.Dialog.ModalityType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import MazeGame.Maze;
import MazeGame.Player;

public class GameOverObserver implements Observer {
	private final String highScoresLocation = "HighScores.dat";
	private String[][] highScores;
	private HighScoresWindow hsWindow;
	
	public GameOverObserver(){
		deserializeHighScores();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		((GameOverBehavior)arg).Behave();
		
		if (Player.getInstance().getPoints() > Integer.parseInt(highScores[9][1])){
			String name = (String)JOptionPane.showInputDialog(Maze.mainWindow,"Please enter your name","New high score!",JOptionPane.PLAIN_MESSAGE,null,null,null);
			insertScore(name,Player.getInstance().getPoints());
			serializeHighScores();
		}
		
		hsWindow = new HighScoresWindow();
		hsWindow.setData(parseToString(),this);
		hsWindow.setVisible(true);
		hsWindow.setModalityType(ModalityType.APPLICATION_MODAL);
		hsWindow.setBounds(Maze.mainWindow.getBounds());
		
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		hsWindow.dispose();
		//Maze.mainWindow.dispose();
		int option = JOptionPane.showOptionDialog(Maze.mainWindow, "Would you like to play again?", "Play again", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
		if(option == 0){
			Maze.getInstance().reset();
		} else {
			Maze.mainWindow.dispose();
			System.exit(0);
		}
		
	}

	public void ClearHighScores(){
		if (0 == JOptionPane.showOptionDialog(hsWindow, "Are you sure?", "Clear High Scores", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null)){
			File hsFile = new File(highScoresLocation);
			hsFile.delete();
		}
	}
	
	private void insertScore(String name, int score) {
		int position = 12;
		for (int x = 0; x < highScores.length && position == 12; x++){
			if (score > Integer.parseInt(highScores[x][1]))
				position = x;
		}
		String[] toInsert = new String[]{name,Integer.toString(score)};
		
		for (int x = position; x < highScores.length; x++){
			String[] temp = highScores[x];
			highScores[x] = toInsert;
			toInsert = temp;
		}
	}
	
	private String parseToString() {
		StringBuilder temp = new StringBuilder();
		temp.append("\tName \t\t Score \n");
		int i = 1;
		for (String[] row : highScores){
			temp.append(i++ + "\t" + row[0] + "\t\t" + row[1]);
			temp.append("\n");
		}
		
		return temp.toString();
	}
	
	private void serializeHighScores() {
		try (FileOutputStream fout = new FileOutputStream(highScoresLocation);
				ObjectOutputStream oout = new ObjectOutputStream(fout);) {
				oout.writeObject(highScores);
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				Object[] options = new Object[]{"Yes","No"};
				int n = JOptionPane.showOptionDialog(Maze.mainWindow, "Error writing high scores. Try again?", "ERROR", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				if (n == 0){
					serializeHighScores();
				}
				e.printStackTrace();
			}
	}
	private void deserializeHighScores() {
		try (FileInputStream fin = new FileInputStream(highScoresLocation);
			ObjectInputStream oin = new ObjectInputStream(fin);) {
			
			highScores = (String[][])oin.readObject();
			
		} catch (FileNotFoundException e) {
			try (FileOutputStream fout = new FileOutputStream(highScoresLocation);
				ObjectOutputStream oout = new ObjectOutputStream(fout);) {
				
				initializeHighScores();
				oout.writeObject(highScores);
				deserializeHighScores();
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initializeHighScores() {
		highScores = new String[10][2];
		for(String[] row : highScores) {
			row[0] = "";
			row[1] = "0";
		}
	}
}
