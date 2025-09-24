package in.mustafaak.izuna.actions;

import java.util.ArrayList;

import in.mustafaak.izuna.meta.WavePath;

public class Rectangle extends Action {

	public Rectangle(int w, int h, boolean clockWise, int duration) {
		float ratio = (float) h / (w + h);
		int hduration = (int) (ratio * duration * 2);
		int wduration = (int) ((1 - ratio) * duration * 2);

		Move down = new Move(0, h, hduration);
		Move left = new Move(w, 0, wduration);
		Move up = new Move(0, -h, hduration);
		Move right = new Move(-w, 0, wduration);
		if (!clockWise) {
			addAction(right, down, left, up);
		} else {
			addAction(down, left, up, right);
		}

	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		return null;
	}

}
