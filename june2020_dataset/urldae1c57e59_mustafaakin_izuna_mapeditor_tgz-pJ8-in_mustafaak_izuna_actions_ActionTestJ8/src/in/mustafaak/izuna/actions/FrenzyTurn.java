package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.WavePath;

import java.util.ArrayList;

public class FrenzyTurn extends Action {
	public FrenzyTurn(int r, int steps, int duration) {
		Action a1 = new Loop();
		actions.add(a1);
		int stepSize = r / steps;
		int durationStep = duration / steps;
		for (int i = 0; i < steps; i++) {
			Action a = new Circle(r - stepSize * i, 0, 4, true, duration - (int)(durationStep * Math.sqrt(i)));
			actions.add(a);
		}
		for (int i = steps - 1; i > 0; i--) {
			Action a = new Circle(r - stepSize * i, 0, 4, false, duration - (int)(durationStep * Math.sqrt(i)));
			actions.add(a);
		}
	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		return null;
	}

}
