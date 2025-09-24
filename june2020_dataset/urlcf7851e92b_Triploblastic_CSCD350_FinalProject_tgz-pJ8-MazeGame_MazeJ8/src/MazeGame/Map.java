package MazeGame;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;

import MazeGeneration.EnumMaze;
import MazeRoomLogic.DefaultMazeParser;
import MazeRoomLogic.MazeEnums.Direction;
import MazeRoomLogic.MazeNode;

public class Map {

	private Scanner fin;
	private String fileName="map.txt";
	//private String Map[]=new String[14];
	private MazeRoomLogic.MazeEnums.RoomType[][] Map = new MazeRoomLogic.MazeEnums.RoomType[MAZE_SIZE][MAZE_SIZE];
	private EnumMaze mazeGen;
	private Image room, wall, start, exit, door;
	private MazeNode currentMazeNode;
	
	public static final int MAZE_SIZE = 15;
	
	
	public Map(){
		
		ImageIcon img=new ImageIcon("grass.png");
		room=img.getImage();
		
		img=new ImageIcon("Stone_Tile.gif");
		wall=img.getImage();
		
		img = new ImageIcon("gate1.gif");
		door=img.getImage();
		
		img = new ImageIcon("exit.png");
		exit = img.getImage();
		
		img = new ImageIcon("start.png");
		start = img.getImage();
		
		mazeGen = new EnumMaze(MAZE_SIZE);
		Map = mazeGen.getMaze();
		currentMazeNode = DefaultMazeParser.parseMaze(Map);
		/*
		openFile();
		readFile();
		closeFile();
		*/
	}
	
	public Image getRoomImage(){
		return room;
	}
	
	public Image getWallImage(){
		return wall;
	}
	
	public Image getDoorImage(){
		return door;
	}
	
	public Image getExitImage(){
		return exit;
	}
	
	public Image getStartImage(){
		return start;
	}
	
	public int getStartX(){
		return mazeGen.getStart()[0];
	}
	
	public int getStartY(){
		return mazeGen.getStart()[1];
	}
	
	public MazeRoomLogic.MazeEnums.RoomType getMapTileType(int x, int y){
		return Map[y][x];
	}

	public boolean tryMovePlayer(Direction direction){
		MazeNode tempNode = currentMazeNode;
		currentMazeNode = currentMazeNode.movePlayer(direction);
		return !(currentMazeNode == tempNode);
	}
	/*
	private void readFile() {
		while(fin.hasNext()){
			for(int i=0; i<MAZE_SIZE; i++){
				Map[i]=fin.next();
			}
		}
	}

	private void openFile() {
		try {
			fin = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void closeFile() {
		fin.close();
	}
	*/
}
