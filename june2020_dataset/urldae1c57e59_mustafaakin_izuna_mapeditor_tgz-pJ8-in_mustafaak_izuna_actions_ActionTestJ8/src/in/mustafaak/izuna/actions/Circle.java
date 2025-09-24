package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.WavePath;

import java.util.ArrayList;

public class Circle extends Action {

	public Circle(int r, int quarterStart, int length, boolean clockWise , int duration) {
		MoveQ[] qs = new MoveQ[4];
		duration = duration / length;
		if ( clockWise){
			qs[0] = new MoveQ(r, 0, r, r, duration);
			qs[1] = new MoveQ(0, r, -r, r, duration);
			qs[2] = new MoveQ(-r, 0, -r, -r, duration);
			qs[3] = new MoveQ(0, -r, r, -r, duration);
		} else {
			qs[0] = new MoveQ(-r, 0, -r, r, duration);
			qs[1] = new MoveQ(0, r, r, r, duration);
			qs[2] = new MoveQ(r, 0, r, -r, duration);
			qs[3] = new MoveQ(0, -r, -r, -r, duration);			
		}
		
		for(int i = quarterStart; i < quarterStart + length; i++){
			addAction(qs[i % 4]);
		}
	}

	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		return null;
	}
}
