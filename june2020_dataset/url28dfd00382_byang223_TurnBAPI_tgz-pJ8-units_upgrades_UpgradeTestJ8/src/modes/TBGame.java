package modes;
import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFileChooser;

import serialization.GameElement;
import serialization.MapElement;
import units.BulbasaurUnit;
import units.CharmanderUnit;
import units.ElectrodeUnit;
import units.HewnerUnit;
import units.HoohUnit;
import units.LugiaUnit;
import units.MewUnit;
import units.PikachuFactoryUnit;
import units.PikachuUnit;
import units.SnorlaxUnit;
import units.Unit;
import units.WeezingUnit;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;

import environment.Environment;
import environment.Mountain;
import environment.PoisonGas;
import environment.Snow;

/**
 * Basic Setup stuff, not sure if necessary this way so rather than change I just decided to add copies
 * @author 
 *
 */

public class TBGame extends GameEngine implements java.io.Serializable {
    
	public static HashMap<String, Unit> UnitAddMap;
	public static HashMap<String, Environment> EnviromentAddMap;
//	public static HashMap<String, double[]> EnviromentSizeMap;
	public static HashMap<String, Double> EnviromentSizeMap;
	
	public static final int	TITLE_SCREEN = 0, GAME_MODE = 1, LOAD_GAME_MODE = 2, EDIT_MODE = 3;
	private int xDim, yDim;
	
	private static MapElement save_file;
	private static GameElement save_game;
	
	public TBGame() {};
	
	public TBGame(MapElement save_file, int x, int y){
		this.save_file = save_file;
		xDim = x;
		yDim = y;
	}
	
	public TBGame(GameElement save_game, int x, int y){
		this.save_game = save_game;
		xDim = x;
		yDim = y;
	}

	public void initResources() {
		nextGameID = TITLE_SCREEN;
		
		UnitAddMap = new HashMap<String, Unit>();
		UnitAddMap.put("Pikachu", new PikachuUnit());
		UnitAddMap.put("Bulbasaur", new BulbasaurUnit());
		UnitAddMap.put("Charmander", new CharmanderUnit());
		UnitAddMap.put("Electrode", new ElectrodeUnit());
		UnitAddMap.put("Lugia", new LugiaUnit());
		UnitAddMap.put("Hooh", new HoohUnit());
		UnitAddMap.put("Mew", new MewUnit());
		UnitAddMap.put("Snorlax", new SnorlaxUnit());
		UnitAddMap.put("PikachuFactory", new PikachuFactoryUnit());
		UnitAddMap.put("Electrode", new ElectrodeUnit());
		UnitAddMap.put("Hewner", new HewnerUnit());
		UnitAddMap.put("Weezing", new WeezingUnit());
		
		EnviromentAddMap = new HashMap<String, Environment>();
		EnviromentAddMap.put("PoisonGas", new PoisonGas(1, 0 , 1000));
		EnviromentAddMap.put("Mountain", new Mountain(1));
		EnviromentAddMap.put("Snow", new Snow(0.3, 0, 1000));

//		EnviromentSizeMap = new HashMap<String, double[]>();
//		EnviromentSizeMap.put("PoisonGas", new double[] {1.0, 0.0, 1000.0});
//		EnviromentSizeMap.put("Mountain", new double[] {1.0});
//		EnviromentSizeMap.put("Snow", new double[] {0.3, 0.0, 1000.0});
		
		EnviromentSizeMap = new HashMap<String, Double>();
		EnviromentSizeMap.put("PoisonGas", 1.0);
		EnviromentSizeMap.put("Mountain", 1.0);
		EnviromentSizeMap.put("Snow", 0.3);
	}

	public GameObject getGame(int GameID) {
		switch (GameID) {
		case TITLE_SCREEN: 
			return new TitleScreen(this);
		case GAME_MODE: 
			return new GameMode(this, xDim, yDim);
		case LOAD_GAME_MODE:
			JFileChooser myChooser = new JFileChooser(System.getProperties().getProperty("user.dir"));
			int retval = myChooser.showOpenDialog(null);
			if (retval != JFileChooser.APPROVE_OPTION) {
				return null;
			}
			File file = myChooser.getSelectedFile();
			return new GameMode(this,file);
		case EDIT_MODE:
			return new EditMode(this, save_file);
		}
		return null;
	} 

	public static void main(String[] args) {
		GameLoader game = new GameLoader();
		game.setup(new TBGame(save_file, 800, 700), new Dimension(800, 700), false);
		game.start();
	}

}