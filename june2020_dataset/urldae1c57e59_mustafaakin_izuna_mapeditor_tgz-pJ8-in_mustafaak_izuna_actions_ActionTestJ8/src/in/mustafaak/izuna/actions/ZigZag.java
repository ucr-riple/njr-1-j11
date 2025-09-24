package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.WavePath;

import java.util.ArrayList;

public class ZigZag extends Action {

	public ZigZag(int w, int h, boolean toRight, int duration){
		if (toRight) {
			Move up = new Move(w/2, -h/2, duration);
			Move down = new Move(w/2, h/2, duration);			
			addAction(up,down);
		} else {
			Move up = new Move(-w/2, -h/2, duration);
			Move down = new Move(-w/2, h/2, duration);
			
			addAction(down, up);
			
		}

		
	}
	
	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		return null;
	}

}
