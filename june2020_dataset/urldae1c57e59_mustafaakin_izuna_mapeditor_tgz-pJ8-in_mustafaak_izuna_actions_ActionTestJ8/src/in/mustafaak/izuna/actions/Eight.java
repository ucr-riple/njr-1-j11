package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.WavePath;

import java.util.ArrayList;

public class Eight extends Action {
	public Eight(int r, int duration) {
		Action a1 = new Circle(r, 0, 2, true, duration / 4);
		Action a2 = new Circle(r, 0, 4, false, duration / 2);
		Action a3 = new Circle(r, 2, 2, true, duration / 4);
		addAction(a1,a2,a3);
	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		// TODO Auto-generated method stub
		return null;
	}
}
