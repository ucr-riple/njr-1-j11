package MazeRoomLogic;



import MazeRoomLogic.MazeEnums.Direction;
import MazeRoomLogic.MazeEnums.RoomType;
import MazeRoomLogic.MazeNode;

public class DefaultMazeParser {

	public static MazeNode parseMaze(RoomType[][] maze) {
		MazeNode result = new MazeNode();
		MazeNode current = result;
		MazeNode nextRow = result;
		MazeNode nextCol = result;
		for (int x = 0; x < maze.length; x++) {
			for (int j = 0; j < maze[x].length; j++) {
				if(maze[x][j] == RoomType.START)
					result = current;
				current.setMazeRoom(MazeRoomFactory.getInstance().getMazeRoom(maze[x][j]));
				
				if (j < maze[x].length - 1){
					
					MazeNode temp = nextCol.getNode(Direction.SOUTH);
					MazeNode node;
					if(temp == null){
						node = new MazeNode();
					} else {
						node = nextCol.getNode(Direction.SOUTH);
					}
				
					current.setNode(Direction.EAST, node);
					node.setNode(Direction.WEST, current);
					nextCol = nextCol.getNode(Direction.EAST);
				}
				
				if(x < maze.length - 1){
					MazeNode node = new MazeNode();
					current.setNode(Direction.SOUTH, node);
					node.setNode(Direction.NORTH, current);
				}
				

				current = current.getNode(Direction.EAST);
			}
			nextCol = nextRow.getNode(Direction.EAST);
			nextRow = nextRow.getNode(Direction.SOUTH);
			current = nextRow;
		}
		return result;
	}

	
}
