package in.mustafaak.izuna.actions;

import java.util.ArrayList;

import in.mustafaak.izuna.meta.WavePath;

public class Start extends Action {

	public Start(int x, int y) {
		endX = x;
		endY = y;
	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		if ( prev != null){
			throw new IllegalArgumentException("Start path should not have any prev nodes");
		}
		ArrayList<WavePath> paths = new ArrayList<>();
		
		WavePath w = new WavePath();
		w.setStartX(endX - 1);
		w.setStartY(endY - 1);
		w.setType("linear");
		w.setEndX(endX);
		w.setEndY(endY);
		
		w.setDuration(1);
		paths.add(w);		
		return paths;
	}
}
