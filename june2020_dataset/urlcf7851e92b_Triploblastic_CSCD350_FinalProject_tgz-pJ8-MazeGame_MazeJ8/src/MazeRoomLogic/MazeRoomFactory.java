package MazeRoomLogic;


import java.util.Random;

import MazeRoomLogic.DoorRoomEnterBehavior;
import MazeRoomLogic.ExitRoomEnterBehavior;
import MazeRoomLogic.MazeEnums.RoomType;
import MazeRoomLogic.MazeRoom;
import MazeRoomLogic.MazeRoomEnterBehavior;
import MazeRoomLogic.QuestionRoomEnterBehavior;
import MazeRoomLogic.StartRoomEnterBehavior;
import MazeRoomLogic.WallRoomEnterBehavior;

//This class assembles each MazeRoom object with appropriate WinEffect, LoseEffect and EnterBehavior behaviors
public class MazeRoomFactory {

	private static MazeRoomFactory instance = null;
	private static Random rand = new Random();
	
	private MazeRoomFactory(){
		
	}
	
	public static MazeRoomFactory getInstance(){
		if(instance == null){
			instance = new MazeRoomFactory();
		}
		return instance;
	}
	
	public MazeRoom getMazeRoom(RoomType roomType){
		MazeRoom result = new MazeRoom();
		result.setEnterBehavior(getBehavior(roomType));
		result.setWinEffect(getWinEffect(roomType));
		result.setLoseEffect(getLoseEffect(roomType));
		return result;
	}
	
	private PlayerEffect getWinEffect(RoomType roomType) {
		PlayerEffect effect = null;
		int randNum = rand.nextInt(100);
		switch(roomType){
		case DOOR:
			effect = new UseKeyEffect();
			break;
		case EXIT:
			effect = new WinGameEffect();
			break;
		case PATH:
			if(randNum < 30)
				effect = new IncreaseHealthEffect();
			else if(randNum > 85)
				effect = new GrantKeyEffect();
			else
				effect = new GrantPointsEffect();
			break;
		default:
			effect = new NullEffect();
			break;
		
		}
		return effect;
	}
	
	private PlayerEffect getLoseEffect(RoomType roomType) {
		PlayerEffect effect;
		if (roomType.equals(RoomType.PATH)){
			effect = new DecreaseHealthEffect();
		} else {
			effect = new NullEffect();
		}
		return effect;
	}
	
	private MazeRoomEnterBehavior getBehavior(RoomType roomType){
		MazeRoomEnterBehavior enterBehavior;
		switch(roomType){
		case DOOR:
			enterBehavior = new DoorRoomEnterBehavior();
			break;
		case EXIT:
			enterBehavior = new ExitRoomEnterBehavior();
			break;
		case PATH:
			if(rand.nextDouble() >= .5)
				enterBehavior = new QuestionRoomEnterBehavior();
			else
				enterBehavior = new TrueRoomEnterBehavior();
			break;
		case START:
			enterBehavior = new StartRoomEnterBehavior();
			break;
		case WALL:
			enterBehavior = new WallRoomEnterBehavior();
			break;
		default:
			enterBehavior = new WallRoomEnterBehavior();
		}
		return enterBehavior;
	}
}
