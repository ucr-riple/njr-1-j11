package in.mustafaak.izuna.actions;

import java.util.ArrayList;

import in.mustafaak.izuna.meta.WavePath;

public class MoveQ extends Action {
	int dx1,dx2,dy1,dy2, duration;

	public MoveQ(int dx1,int dy1, int dx2, int dy2, int duration) {
		this.dx1 = dx1;
		this.dy1 = dy1;
		this.dx2 = dx2;
		this.dy2 = dy2;		
		this.duration = duration;
	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		ArrayList<WavePath> paths = new ArrayList<>();

		WavePath w = new WavePath();

		w.setType("quadratic");
		w.setStartX(prev.getEndX());
		w.setStartY(prev.getEndY());
		
		w.setMidX(w.getStartX() + dx1);
		w.setMidY(w.getStartY() + dy1);
		
		w.setEndX(w.getStartX() + dx2);
		w.setEndY(w.getStartY()  + dy2);

		w.setDuration(duration);
		paths.add(w);
		return paths;
	}

}
