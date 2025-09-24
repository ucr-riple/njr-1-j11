package MazeRoomLogic;


public class MazeNode {

	private MazeNode northNode, southNode, eastNode, westNode;
	private MazeRoom mazeRoom;
	
	public MazeNode movePlayer(MazeEnums.Direction direction) {
		switch(direction){
		case EAST:
			if(eastNode.enter())
				return eastNode;
			break;
		case NORTH:
			if(northNode.enter())
				return northNode;
			break;
		case SOUTH:
			if(southNode.enter())
				return southNode;
			break;
		case WEST:
			if(westNode.enter())
				return westNode;
			break;
		}
		return this;
	}

	public void setMazeRoom(MazeRoom room) {
		mazeRoom = room;
	}


	public MazeNode getNode(MazeEnums.Direction direction){
		switch(direction){
		case NORTH:
			return northNode;
		case SOUTH:
			return southNode;
		case EAST:
			return eastNode;
		case WEST:
			return westNode;
		default:
			return this;
		}
	}
	public void setNode(MazeEnums.Direction direction, MazeNode node){
		switch(direction){
		case NORTH:
			northNode = node;
			break;
		case SOUTH:
			southNode = node;
			break;
		case EAST:
			eastNode = node;
			break;
		case WEST:
			westNode = node;
			break;
		}
	}

	private boolean enter() {
		if(this.mazeRoom.enter())
			return true;
		
		return false;
	}
}
