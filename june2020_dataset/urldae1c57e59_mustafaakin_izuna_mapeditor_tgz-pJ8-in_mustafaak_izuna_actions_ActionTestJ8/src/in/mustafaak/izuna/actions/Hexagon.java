package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.WavePath;

import java.util.ArrayList;

public class Hexagon extends Action {
	public Hexagon(int w, int h, boolean leftToRight, int duration) {
		float ratio = (float) h / (w + h);
		int hduration = (int) (ratio * duration * 2);
		int wduration = (int) ((1 - ratio) * duration * 2);

		int side = (int) (Math.sqrt(3) / 2.0 * h);
		Move left = new Move(w, 0, wduration);

		Move down1 = new Move(side, h / 2, hduration);
		Move down2 = new Move(-side, h / 2, hduration);

		Move right = new Move(-w, 0, wduration);

		Move up1 = new Move(-side, -h / 2, hduration);
		Move up2 = new Move(side, -h / 2, hduration);

		if (leftToRight) {
			addAction(left, down1, down2, right, up1, up2);
		} else {
			addAction(right, down2, down1, left, up2, up1);
		}
	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		return null;
	}

}
