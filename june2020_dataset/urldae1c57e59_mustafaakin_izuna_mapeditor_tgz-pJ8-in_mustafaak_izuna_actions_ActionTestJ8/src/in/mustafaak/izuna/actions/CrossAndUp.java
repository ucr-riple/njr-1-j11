package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.WavePath;

import java.util.ArrayList;

public class CrossAndUp extends Action {
	public CrossAndUp(int w, int h, int duration) {
		double total = Math.sqrt(w*w + h*h) + h;
		double ratio = h / total;
		
		Move cross = new Move(w, h,(int)( ( 1- ratio)* duration));
		Move up = new Move(0, -h, (int) (ratio * duration));
		addAction(cross, up);
	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		return null;
	}

}
