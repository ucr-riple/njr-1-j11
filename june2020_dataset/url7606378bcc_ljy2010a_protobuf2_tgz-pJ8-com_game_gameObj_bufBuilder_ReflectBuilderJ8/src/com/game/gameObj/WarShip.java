package com.game.gameObj;

public class WarShip {
	
    public int GameObjId;
    
	private String GameObjType = this.getClass().getName();
	
	public int WarShipType;

	public int WarShipLevel;

	public int WarShipSk1;

	public int WarShipSk2;
	
	public Coordinate Coor = new Coordinate();

	public int getGameObjId() {
		return GameObjId;
	}

	public void setGameObjId(int gameObjId) {
		GameObjId = gameObjId;
	}

	public int getWarShipType() {
		return WarShipType;
	}

	public void setWarShipType(int warShipType) {
		WarShipType = warShipType;
	}

	public int getWarShipLevel() {
		return WarShipLevel;
	}

	public void setWarShipLevel(int warShipLevel) {
		WarShipLevel = warShipLevel;
	}

	public int getWarShipSk1() {
		return WarShipSk1;
	}

	public void setWarShipSk1(int warShipSk1) {
		WarShipSk1 = warShipSk1;
	}

	public int getWarShipSk2() {
		return WarShipSk2;
	}

	public void setWarShipSk2(int warShipSk2) {
		WarShipSk2 = warShipSk2;
	}

	public String getGameObjType() {
		return GameObjType;
	}

	public Coordinate getCoor() {
		return Coor;
	}

	public void setCoor(Coordinate coor) {
		Coor = coor;
	}
	
	

}
