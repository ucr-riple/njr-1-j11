package in.mustafaak.izuna.waves;

import java.util.ArrayList;

import in.mustafaak.izuna.actions.Action;
import in.mustafaak.izuna.actions.Circle;
import in.mustafaak.izuna.actions.Delay;
import in.mustafaak.izuna.actions.Loop;
import in.mustafaak.izuna.actions.Move;
import in.mustafaak.izuna.actions.Start;
import in.mustafaak.izuna.meta.WaveEnemy;
import in.mustafaak.izuna.meta.WaveInfo;

public class CircularInvade extends WaveInfo {
	public CircularInvade(int count, int startX, int startY, int dX, int dY, int r, String ship, int duration) {
		setEnemies(new ArrayList<WaveEnemy>());
		
		double way = Math.sqrt(dX * dX + dY * dY) + 2 * Math.PI * r;
		double ratio = (2 * Math.PI * r / way);
		int circleTime = (int)(ratio * duration);
		int tDelay = circleTime / count;
		
		for (int i = 0; i < count; i++) {
			Action a0 = new Start(startX, startY);
			Action a1 = new Delay(i * tDelay);
			Action a2 = new Move(dX, dY, duration - circleTime);
			Action a3 = new Loop();
			Action a4 = new Circle(r, 0, 4, true, circleTime);
			putEnemy(ship, a0, a1, a2, a3, a4);
		}
	}
}
