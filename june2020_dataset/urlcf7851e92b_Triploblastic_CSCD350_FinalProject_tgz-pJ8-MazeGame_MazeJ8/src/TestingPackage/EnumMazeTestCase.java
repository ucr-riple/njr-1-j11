package TestingPackage;

import java.util.Arrays;

import MazeGeneration.EnumMaze;
import MazeRoomLogic.MazeEnums.RoomType;
import junit.framework.TestCase;

//did not test getTrace or toString

public class EnumMazeTestCase extends TestCase {
	
	EnumMaze test;
	public void setUp(){
		test = new EnumMaze(7);
	}
	
	public void testEnumMaze() {
		RoomType[][] a = test.getMaze();

		for(int i = 0; i < 7; i++){
			int length = a[i].length;
			assertEquals(7, length);
		}
	}

	public void testGetMaze() {
		RoomType[][] a = test.getMaze();

		for(int i = 0; i < 7; i++){
			int length = a[i].length;
			assertEquals(7, length);
		}
	}

	public void testGetSize() {
		assertEquals(7, test.getSize());
	}

	public void testGetStart() {
		RoomType[][] a = test.getMaze();
		int[] rc = new int[2];
		for(int r = 0; r < a.length; r++){
			for(int c = 0; c < a.length; c++){
				if(a[r][c] == RoomType.START){
					//RoomType w = a[r][c];
					rc[0] = r; rc[1] = c;
					assertEquals(Arrays.toString(rc), Arrays.toString(test.getStart()));
				}
			}	
		}
	}

	public void testGetExit() {
		RoomType[][] a = test.getMaze();
		int[] rc = new int[2];
		for(int r = 0; r < a.length; r++){
			for(int c = 0; c < a.length; c++){
				if(a[r][c] == RoomType.EXIT){
					//RoomType w = a[r][c];
					rc[0] = r; rc[1] = c;
					assertEquals(Arrays.toString(rc), Arrays.toString(test.getExit()));
				}
			}	
		}
	}
}
