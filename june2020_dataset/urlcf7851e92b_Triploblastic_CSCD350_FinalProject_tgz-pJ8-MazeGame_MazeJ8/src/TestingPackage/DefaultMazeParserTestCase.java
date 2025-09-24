package TestingPackage;

import MazeGeneration.EnumMaze;
import MazeRoomLogic.DefaultMazeParser;
import MazeRoomLogic.MazeEnums.RoomType;
import junit.framework.TestCase;

public class DefaultMazeParserTestCase extends TestCase {

	DefaultMazeParser result = new DefaultMazeParser();

	EnumMaze test;
	
	public void setUp(){
		test = new EnumMaze(7);
	}
	
	public void testParseMaze(){
		RoomType[][] m = test.getMaze();
		
		result.parseMaze(m);
		assertNotNull(result);
	}

}
