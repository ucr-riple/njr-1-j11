package Game_World;

import java.util.ArrayList;

public class Player implements java.io.Serializable {
	/**
	 * This Class represents the player in the game.
	 * @author Danesh Abeyratne
	 *
	 */
	private String name;
	private Avatar character;
	private ArrayList<String> quests;// the quests the player has successfully completed
	private int number;

	public Player(String name, ArrayList<String> quests){
		this.name = name;
		this.quests = quests;
	}

	public Player(){} // default constructor

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Avatar getCharacter() {
		return character;
	}

	public void setCharacter(Avatar character) {
		this.character = character;
	}

	public ArrayList<String> getQuests() {
		return quests;
	}

	public void setQuests(ArrayList<String> quests) {
		this.quests = quests;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}



}
