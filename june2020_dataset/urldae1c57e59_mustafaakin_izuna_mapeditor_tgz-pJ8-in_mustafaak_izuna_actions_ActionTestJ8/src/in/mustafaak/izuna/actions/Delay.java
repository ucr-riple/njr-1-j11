package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.WavePath;

import java.util.ArrayList;

public class Delay extends Action {
	int duration;

	public Delay(int duration) {
		this.duration = duration;
	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		ArrayList<WavePath> paths = new ArrayList<>();

		WavePath w = new WavePath();
		w.setStartX(prev.getEndX() - 1);
		w.setStartY(prev.getEndY() - 1);
		w.setType("linear");
		w.setEndX(prev.getEndX());
		w.setEndY(prev.getEndY());

		w.setDuration(duration);
		paths.add(w);
		return paths;
	}
}
