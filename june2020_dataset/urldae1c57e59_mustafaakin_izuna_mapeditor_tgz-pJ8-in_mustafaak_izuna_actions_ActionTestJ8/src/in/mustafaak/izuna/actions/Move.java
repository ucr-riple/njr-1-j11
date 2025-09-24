package in.mustafaak.izuna.actions;

import java.util.ArrayList;

import in.mustafaak.izuna.meta.WavePath;

public class Move extends Action {
	int dx, dy, duration;

	public Move(int dx, int dy, int duration) {
		this.dx = dx;
		this.dy = dy;
		this.duration = duration;
	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		ArrayList<WavePath> paths = new ArrayList<>();

		WavePath w = new WavePath();
		w.setStartX(prev.getEndX());
		w.setStartY(prev.getEndY());
		w.setType("linear");
		w.setEndX(prev.getEndX() + dx);
		w.setEndY(prev.getEndY() + dy);

		w.setDuration(duration);
		paths.add(w);
		return paths;
	}

}
