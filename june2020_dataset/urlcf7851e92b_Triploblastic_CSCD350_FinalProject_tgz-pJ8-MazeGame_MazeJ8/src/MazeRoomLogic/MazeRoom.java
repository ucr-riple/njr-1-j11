package MazeRoomLogic;

import MazeGame.Player;

public class MazeRoom {
	private PlayerEffect winEffect;
	private PlayerEffect loseEffect;
	private MazeRoomEnterBehavior enterBehavior;
	private boolean isVisited;

	public boolean enter() {
		if(!isVisited){
			if(enterBehavior.enter()){
				winEffect.ApplyEffect();
				isVisited = true;
				return true;
			}
			loseEffect.ApplyEffect();
			return false;
		}
		return true;
	}

	public void setWinEffect(PlayerEffect effect){
		winEffect = effect;
	}
	
	public void setLoseEffect(PlayerEffect effect){
		loseEffect = effect;
	}


	public void leave() {
		//Remove?
	}

	public MazeRoomEnterBehavior getEnterBehavior(){
		return this.enterBehavior;
	}
	
	public void setEnterBehavior(MazeRoomEnterBehavior enterBehavior){
		this.enterBehavior=enterBehavior;
	}
}

