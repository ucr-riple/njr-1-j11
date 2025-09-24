package in.mustafaak.izuna.waves;

import in.mustafaak.izuna.actions.Start;
import in.mustafaak.izuna.meta.WaveEnemy;
import in.mustafaak.izuna.meta.WaveInfo;

import java.awt.Point;
import java.util.ArrayList;
import in.mustafaak.izuna.actions.*;

public class RowColEnter extends WaveInfo {
	public final static int FROM_UP = 0;
	public final static int FROM_LEFT = 1;
	public final static int FROM_RIGHT = 2;
	
	public RowColEnter(int from, int offsetX, int offsetY, int row, int col, int spacingW, int spacingH, String ship, int duration) {
		setEnemies(new ArrayList<WaveEnemy>());
		int tDelay = 200;
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col; j++){
				Action start, delay, move;
				if ( from == FROM_UP){
					start = new Start(offsetX + spacingW * j, -300);
	 				delay = new Delay((row-i) * tDelay);
	 				move = new Move(0, 300 + offsetY + spacingH * i, duration);	 				
				} else if ( from == FROM_RIGHT) { 
	 				start = new Start(900, offsetY + spacingH * i);
	 				delay = new Delay((col-j) * tDelay);
	 				move = new Move(-900 + offsetX + spacingW * (col-j-1), 0, duration);	 									
				} else { // from left
	 				start = new Start(-300, offsetY + spacingH * i);
	 				delay = new Delay((col-j) * tDelay);
	 				move = new Move(300 + offsetX + spacingW * j, 0, duration);	 														
				} 
				putEnemy(ship, start,delay,move);
			}
		}
	}


}
