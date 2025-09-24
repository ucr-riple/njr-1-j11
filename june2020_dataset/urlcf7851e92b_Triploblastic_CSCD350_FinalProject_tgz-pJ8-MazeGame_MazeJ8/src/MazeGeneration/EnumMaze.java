package MazeGeneration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import MazeRoomLogic.MazeEnums.RoomType;

/* Richard Sipes 
 * CSCD350
 * Generates a 2D maze with RoomType enums: START, EXIT, DOOR, PATH. 
 * Includes start/exit coordinates and a ArrayStack of trace coordinates.
 */

public class EnumMaze {
	private int size;
	private int[] start, exit;
	private RoomType[][] maze;
	private ArrayStack<int[]> trace = new ArrayStack();
	
	public EnumMaze(int num) {
		if (num % 2 == 0) 
			throw new IllegalArgumentException("Maze size must be an odd number.");
		size = num;
		do {
			randomPath(maze = initMaze(size), start = randomEdgeLoc(size), trace);
			maze[start[0]][start[1]] = RoomType.START;
			exit = trace.peek();
			maze[exit[0]][exit[1]] = RoomType.EXIT;
		} while (start[0] == exit[0] && start[1] == exit[1]); //generate mazes until start != exit (usually on first try)
	}
	
	/*getters*/
	public RoomType[][] getMaze() {return this.maze;}
	public int getSize() {return this.size;}
	public int[] getStart() {return this.start;}
	public int[] getExit() {return this.exit;}
	public ArrayStack<int[]> getTrace() {return this.trace;}
	
	
	private static void randomPath(RoomType[][] maze, int[] start, ArrayStack<int[]> trace) {
		recursion(start[0],start[1], maze, trace);	
	}
	
	/*First-depth-search recursive method modified from  http://www.migapro.com/depth-first-search/ */
	/*advances the path 2 squares & pushes coordinates to the trace stack*/
	/*doors randomly placed before backtracking*/
	private static void recursion(int r, int c, RoomType[][] maze, ArrayStack<int[]> trace) { 
		// 4 random directions
	     Integer[] randDirs = generateRandomDirections();
	     // Examine each direction
	     for (int i = 0; i < randDirs.length; i++) {
	    	 double potentialDoor = Math.random();
	         
	    	 switch(randDirs[i]){
	         case 1: // Up
	             //bounds check
	             if (r - 2 <= 0)
	                 continue;
	             if (maze[r - 2][c] != RoomType.PATH) {
	            	 int[] coord2 = {r-2,c}; int[] coord1 = {r-1,c};
	            	 trace.push(coord1); trace.push(coord2);
	            	 maze[r-2][c] = RoomType.PATH;
	                 maze[r-1][c] = RoomType.PATH;
	                 recursion(r - 2, c, maze, trace);
	             } else if (potentialDoor < 0.1)
	            	 maze[r-1][c] = RoomType.DOOR;
	             break;
	         case 2: // Right
	             //bounds check
	             if (c + 2 >= maze.length - 1)
	                 continue;
	             if (maze[r][c + 2] != RoomType.PATH) {
	            	 int[] coord2 = {r,c+2}; int[] coord1 = {r,c+1};
	            	 trace.push(coord1); trace.push(coord2);
	                 maze[r][c + 2] = RoomType.PATH;
	                 maze[r][c + 1] = RoomType.PATH;
	                 recursion(r, c + 2, maze, trace);
	             } else if (potentialDoor < 0.1)
	            	 maze[r][c+1] = RoomType.DOOR;
	             break;
	         case 3: // Down
	             // bounds check
	             if (r + 2 >= maze.length - 1) 
	                 continue;
	             if (maze[r + 2][c] != RoomType.PATH) {
	            	 int[] coord2 = {r+2,c}; int[] coord1 = {r+1,c};
	            	 trace.push(coord1); trace.push(coord2);
	                 maze[r+2][c] = RoomType.PATH;
	                 maze[r+1][c] = RoomType.PATH;
	                 recursion(r + 2, c, maze, trace);
	             } else if (potentialDoor < 0.1)
	            	 maze[r+1][c] = RoomType.DOOR;
	             break;
	         case 4: // Left
	             // bounds check
	             if (c - 2 <= 0)
	                 continue;
	             if (maze[r][c - 2] != RoomType.PATH) {
	            	 int[] coord2 = {r,c-2}; int[] coord1 = {r,c-1};
	            	 trace.push(coord1); trace.push(coord2);
	                 maze[r][c - 2] = RoomType.PATH;
	                 maze[r][c - 1] = RoomType.PATH;
	                 recursion(r, c - 2, maze, trace);
	             } else if (potentialDoor < 0.1)
	            	 maze[r][c-1] = RoomType.DOOR;
	             break;
	         }
	     }
	 }//recursion()
	 
	 private static Integer[] generateRandomDirections() {
	      ArrayList<Integer> randoms = new ArrayList<Integer>();
	      for (int i = 0; i < 4; i++)
	           randoms.add(i + 1);
	      Collections.shuffle(randoms);
	 
	     return randoms.toArray(new Integer[4]);
	 }
	
	 /*Generates random START location along the edge of the maze*/
	private static int[] randomEdgeLoc(int size) {
		int r, c;
		Random rand = new Random();
		int num = rand.nextInt(2);
		if (num == 0) { //random vertical edge coordinate
			r = rand.nextInt(size - 3) + 1;
			while (r % 2 == 0) 
				r = rand.nextInt(size - 3) + 1;// somewhere b/t row(1) to row(size-2)
			c = rand.nextInt(1) + 1;
			num = rand.nextInt(2);
			if (num == 0)
				c = 1;//left side
			else
				c = size - 2;//right side
		} else { //random horizontal edge coordinate
			c = rand.nextInt(size - 3) + 1;
			while(c % 2 == 0)
				c = rand.nextInt(size - 3) + 1;// somewhere b/t column(1) to column(size-2)
			num = rand.nextInt(2);
			if (num == 0)
				r = 1;//upside
			else
				r = size - 2;//downside
		}
		int[] edgeLoc = {r,c};
		return edgeLoc;	
	}//randomEdgeLoc()
	
	/*Fills 2D array with enum RoomType.WALL*/
	private static RoomType[][] initMaze(int size) {
		RoomType[][] maze = new RoomType[size][size];
		for (RoomType[] row : maze) {
			for (int i = 0; i < size; i++)
				row[i] = RoomType.WALL;
		}
		return maze;
	}

	public String toString() {
		String str = "";
		for(Object[] row : this.getMaze()) {
			for (Object col : row) 
				str += " " + col;
			str += "\n";
		}
		return str + "\n\n";
	}//toString()
	
}//EnumMaze.class
	


