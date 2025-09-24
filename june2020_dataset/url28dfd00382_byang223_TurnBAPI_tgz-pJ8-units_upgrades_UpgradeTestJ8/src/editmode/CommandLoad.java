package editmode;

/**
 * Function to load a file
 * @author tianyu shi
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import modes.EditMode;
import modes.EditMode.GameState;
import serialization.GameElement;
import serialization.MapElement;

public class CommandLoad extends EditModeCommand {

	private MapElement map;
	private GameElement game;
	private GameState State;
	private EditMode EM;

	public CommandLoad(double x, double y) {
		super(x, y);
		super.setImageFilepath("resources/editmode/loadButton.png");
		super.setClickedImageFilepath("resources/editmode/clickedloadbutton.png");
		super.setImage();
	}

	/**
	 * Makes sure that the button was clicked before loading
	 * 
	 */
	
	public void performCommand(GameState State, EditMode Mode) {
		this.State = State;
		this.EM = Mode;

		if(EM.wasclicked()) {
			load();
		}
	}

	public String CommandName() {
		return "CommandLoad";
	}

	public MapElement getMap() {
		return map;
	}

	/**
	 * Load the file as a game or map save depending on the name. In save, all maps are saved w/ map and all games are saved with "game"
	 * Through java serialization, recreates the .ser file as a save element
	 * 
	 */
	
	public void load() {

		if(EM.getselectedButton()!=null) {
			EM.getselectedButton().setUnclickedButtonImage();
			EM.setselectedButton(null);
		}

		String input = null;
		JFileChooser fc = new JFileChooser();

		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			input = file.getName();
		}

		if(input.contains("map")) {
			map = new MapElement();
			try
			{
				FileInputStream fileIn =
						new FileInputStream(input);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				map = (MapElement) in.readObject();
				EM.rendermap(map);
				in.close();
				fileIn.close();
			}catch(IOException i)
			{
				i.printStackTrace();
				return;
			}catch(ClassNotFoundException c)
			{
				c.printStackTrace();
				return;
			}
		}

		else{
			game = new GameElement();
			try
			{
				FileInputStream fileIn =
						new FileInputStream(input);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				game = (GameElement) in.readObject();
				in.close();
				fileIn.close();
			}catch(IOException i)
			{
				i.printStackTrace();
				return;
			}catch(ClassNotFoundException c)
			{
				c.printStackTrace();
				return;
			}
		}
	}
}