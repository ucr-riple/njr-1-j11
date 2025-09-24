package com.game.gameObj;

public class Soldier {
	
	public int GameObjId;
	
	private final static String GameObjType = "Soldier";
	
	public int SoldierType;

	public int SoldierLevel;

	public int SoldierSk1;

	public int SoldierSk2;
	
	public Coordinate Coor = new Coordinate();

	public int getSoldierType() {
		return SoldierType;
	}

	public void setSoldierType(int soldierType) {
		SoldierType = soldierType;
	}

	public int getSoldierLevel() {
		return SoldierLevel;
	}

	public void setSoldierLevel(int soldierLevel) {
		SoldierLevel = soldierLevel;
	}

	public int getSoldierSk1() {
		return SoldierSk1;
	}

	public void setSoldierSk1(int soldierSk1) {
		SoldierSk1 = soldierSk1;
	}

	public int getSoldierSk2() {
		return SoldierSk2;
	}

	public void setSoldierSk2(int soldierSk2) {
		SoldierSk2 = soldierSk2;
	}

	public int getGameObjId() {
		return GameObjId;
	}

	public void setGameObjId(int gameObjId) {
		GameObjId = gameObjId;
	}

	public String getGameObjType() {
		return GameObjType;
	}

	public Coordinate getCoor() {
		return Coor;
	}
	
	
}