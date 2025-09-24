package in.mustafaak.izuna.waves;

import java.util.ArrayList;

import in.mustafaak.izuna.actions.Action;
import in.mustafaak.izuna.actions.Circle;
import in.mustafaak.izuna.actions.Delay;
import in.mustafaak.izuna.actions.Move;
import in.mustafaak.izuna.actions.Start;
import in.mustafaak.izuna.meta.WaveEnemy;
import in.mustafaak.izuna.meta.WaveInfo;

public class Invade extends WaveInfo {
	public Invade(int count, int startX, int startY, int dX, int dY, int spacing, int lineX, int lineY, int r,
			String ship, int duration) {

		setEnemies(new ArrayList<WaveEnemy>());
		int tDelay = 400;

		double way1 = Math.sqrt(dX * dX + dY * dY);
		double way2 = Math.PI * r;

		for (int i = 0; i < count; i++) {
			int lastX = -lineX + spacing * i;
			double way3 = Math.sqrt(lastX * lastX + lineY * lineY);
			double way = way1 + way2 + way3;
			int duration1 = (int) (duration * way1 / way);
			int duration2 = (int) (duration * way2 / way);
			int duration3 = (int) (duration * way3 / way);

			Action a = new Start(startX, startY);
			Action a0 = new Delay(tDelay * i);
			Action a1 = new Move(dX, dY, duration1);
			Action a2;
			if (startX > 0) { // meaning coming from right
				a2 = new Circle(r, 1 , 2, true, duration2);
			} else {
				a2 = new Circle(r, 1, 2, false, duration2);
			}
			Action a3 = new Move(-lineX + spacing * i, -lineY, duration3);
			putEnemy(ship, a, a0, a1, a2, a3);
		}

	}

}
