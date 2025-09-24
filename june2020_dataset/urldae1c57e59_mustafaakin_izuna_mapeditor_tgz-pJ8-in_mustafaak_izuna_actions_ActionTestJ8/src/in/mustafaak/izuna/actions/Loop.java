package in.mustafaak.izuna.actions;

import java.util.ArrayList;

import in.mustafaak.izuna.meta.WavePath;

public class Loop extends Action {

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		ArrayList<WavePath> paths = new ArrayList<>();

		WavePath w = new WavePath();
		w.setStartX(prev.getEndX() - 1);
		w.setStartY(prev.getEndY() - 1);
		w.setType("loop linear");
		w.setEndX(prev.getEndX() - 1);
		w.setEndY(prev.getEndY());

		w.setDuration(1);
		paths.add(w);
		return paths;
	}
}
