package in.mustafaak.izuna.actions;

import in.mustafaak.izuna.meta.WavePath;

import java.util.ArrayList;

public class Bounce extends Action{
	public Bounce(int length, boolean horizontal, int duration){
		int dx, dy; 
		if ( horizontal){
			dx = length; dy = 0;
		} else {
			dy = length; dx = 0;
		}
		Move m1 = new Move(dx, dy, duration / 2);
		Move m2 = new Move(-dx,-dy, duration / 2);
		addAction(m1,m2);
	}
	
	@Override
	public ArrayList<WavePath> getPath(WavePath prev) {
		return null;
	}
}
