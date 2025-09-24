package MazeGame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import MazeRoomLogic.MazeEnums.Direction;
import MazeRoomLogic.MazeNode;
import java.awt.Color;

public class Board extends JPanel implements ActionListener{
	
	private Timer timer;
	
	private Map m;
	private Player p;
	
	private static ImageIcon keyIcon = new ImageIcon("key.png");
	
	
	public Board(){
		setBackground(new Color(95, 158, 160));
		m=new Map();
		p=Player.getInstance();
		p.setStartLocation(m.getStartY(),m.getStartX());
		
		addKeyListener(new Al());
		setFocusable(true);
		
		timer = new Timer(25, this);
		timer.start();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		
	}

	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		for(int i = 0; i < p.getKeys(); i++){
			g.drawImage(keyIcon.getImage(),(i*32)+5,(int)(m.MAZE_SIZE+.2)*32,null);
		}
		
		g.drawString("Score: " + p.getPoints(), 5, (int)((m.MAZE_SIZE+1.5)*32));
		for(int i=0; i<m.MAZE_SIZE; i++){
			for(int j=0; j<m.MAZE_SIZE; j++){
				if(m.getMapTileType(j, i).equals(MazeRoomLogic.MazeEnums.RoomType.PATH)){
					g.drawImage(m.getRoomImage(), j*32, i*32, null);
				}
				
				if(m.getMapTileType(j, i).equals(MazeRoomLogic.MazeEnums.RoomType.WALL)){
					g.drawImage(m.getWallImage(), j*32, i*32, null);
				}
				
				if(m.getMapTileType(j, i).equals(MazeRoomLogic.MazeEnums.RoomType.DOOR)){
					g.drawImage(m.getDoorImage(), j*32, i*32, null);
				}
				
				if(m.getMapTileType(j, i).equals(MazeRoomLogic.MazeEnums.RoomType.EXIT)){
					g.drawImage(m.getExitImage(), j*32, i*32, null);
				}
				
				if(m.getMapTileType(j, i).equals(MazeRoomLogic.MazeEnums.RoomType.START)){
					g.drawImage(m.getStartImage(), j*32, i*32, null);
				}
			}
		}
		
		g.drawImage(p.getPlayerImage(), p.getTileX()*32, p.getTileY()*32, null);
	}
	
	//This is where we merge our room behavior code.
	public class Al extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			final int keycode=e.getKeyCode();
			
			Thread t = new Thread(new Runnable() {
				public void run(){
					if(keycode==KeyEvent.VK_W){
						p.setDirection(Direction.NORTH);
						if(m.tryMovePlayer(Direction.NORTH))
							p.move(0, -1);
					}
					if(keycode==KeyEvent.VK_S){
						p.setDirection(Direction.SOUTH);
						if(m.tryMovePlayer(Direction.SOUTH))
							p.move(0, 1);
					}
					if(keycode==KeyEvent.VK_A){
						p.setDirection(Direction.WEST);
						if(m.tryMovePlayer(Direction.WEST))
							p.move(-1, 0);
					}
					if(keycode==KeyEvent.VK_D){
						p.setDirection(Direction.EAST);
						if(m.tryMovePlayer(Direction.EAST))
							p.move(1, 0);				
					}
					if(keycode==KeyEvent.VK_K){
						p.addKey();
						System.out.println("Key cheated in. Player has: " + p.getKeys());
					}
					if(keycode==KeyEvent.VK_H){
						p.increaseHealth();
						System.out.println("Player gained health!");
					}
					
				}
			});
			t.start();
			
		}
		
		public void keyReleased(KeyEvent e){
			
		}
		
		public void keyTyped(KeyEvent e){
			
		}
	}
}
