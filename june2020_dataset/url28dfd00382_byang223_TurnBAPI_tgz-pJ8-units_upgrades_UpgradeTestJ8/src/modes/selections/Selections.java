package modes.selections;

import java.util.ArrayList;

import map.Tile;
import player.Player;
import units.Unit;
import units.interactions.ButtonSprite;
import attribute.Attribute;

import com.golden.gamedev.object.SpriteGroup;
/**
 * This is a wrapper class for selected fields.
 * all fields are static because all of these fields
 * should only exist once in the game loop.
 * Accordingly, this data class contains only
 * getters and setters.
 * @author prithvi
 *
 */
public class Selections {

	private Selections() {
	    gameOver = false;
	}
	
	private static Tile selectedTile, selectedDestination;
	private static Unit selectedUnit;
	private static ButtonSprite selectedButton;
	private static Attribute selectedAttribute;
	private static SpriteGroup buttonGroup;
	private static SpriteGroup environmentGroup;
	private static ArrayList<Player> playerList;
	private static Player currentPlayer;
	private static boolean gameOver;
	
	public static void setSelectedTile(Tile selectedTile) {
		Selections.selectedTile = selectedTile;
	}
	
	public static Tile getSelectedTile() {
		return selectedTile;
	}
	
	public static void setSelectedDestination(Tile selectedDestination) {
		Selections.selectedDestination = selectedDestination;
	}
	
	public static Tile getSelectedDestination() {
		return selectedDestination;
	}
	
	public static void setSelectedUnit(Unit selectedUnit) {
		Selections.selectedUnit = selectedUnit;
	}
	
	public static Unit getSelectedUnit() {
		return selectedUnit;
	}
	
	public static void setSelectedButton(ButtonSprite selectedButton) {
		Selections.selectedButton = selectedButton;
	}
	
	public static ButtonSprite getSelectedButton() {
		return selectedButton;
	}

	public static void setSelectedAtrribute(Attribute selectedAtrribute) {
		Selections.selectedAttribute = selectedAtrribute;
	}

	public static Attribute getSelectedAtrribute() {
		return selectedAttribute;
	}

	public static SpriteGroup getButtonGroup() {
	    return buttonGroup;
    }

	public static void setButtonGroup(SpriteGroup buttonGroup) {
	    Selections.buttonGroup = buttonGroup;
    }

	public static void setPlayerList(ArrayList<Player> playerList) {
		Selections.playerList = playerList;
	}

	public static ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public static SpriteGroup getEnvironmentGroup() {
		return environmentGroup;
	}
	
	public static void setEnvironmentGroup(SpriteGroup enviro) {
		Selections.environmentGroup = enviro;
	}
	
	public static void setCurrentPlayer(Player currentPlayer) {
		Selections.currentPlayer = currentPlayer;
	}

	public static Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public static void setGameOver(boolean gameOver) {
	    Selections.gameOver = gameOver;
	}
	
	public static boolean getGameOver(){
		return Selections.gameOver;
	}
	
}