package in.mustafaak.izuna.waves.set;

import in.mustafaak.izuna.actions.*;
import in.mustafaak.izuna.meta.WaveInfo;
import in.mustafaak.izuna.waves.CircularInvade;
import in.mustafaak.izuna.waves.Invade;
import in.mustafaak.izuna.waves.RowColEnter;

public class C {
	public static WaveInfo w1() {
		return new CircularInvade(2, -200, -200, 500, 500, 150, "c1", 4000);
	}
	
	public static WaveInfo w2() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_UP, 0, 50, 1, 5, 130, 0, "a1", 2000);
		Action.wiggle(w, 100, 0, 2000);
		return w;
	}
	
	public static WaveInfo w3() {
		WaveInfo w = new Invade(4, -200, -200, 500, 700, 100, 600, 400, 200, "b1", 4000);
		Action.wiggle(w, 100, 0, 2000);
		return w;
	}

	public static WaveInfo w4() {
		return new CircularInvade(3, -200, -200, 500, 500, 150, "c2", 4000);
	}
	
	public static WaveInfo w5() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_UP, 100, 50, 1, 5, 100, 0, "a2", 2000);
		Action.wiggle(w, 0, 200, 4000);
		return w;
	}
	
	public static WaveInfo w6() {
		WaveInfo w = new Invade(4, -200, -200, 500, 700, 150, 650, 400, 200, "b2", 4000);
		Action.wiggle(w, 100, 0, 2000);
		return w;
	}
	
	public static WaveInfo w7() {
		WaveInfo w1 = new RowColEnter(RowColEnter.FROM_UP, 150, 150, 1, 4, 130, 0, "a1", 2000);
		WaveInfo w2 = new RowColEnter(RowColEnter.FROM_LEFT, 50, 250, 2, 1, 0, 150, "a1", 2000);
		WaveInfo w3 = new RowColEnter(RowColEnter.FROM_RIGHT, 600, 250, 2, 1, 0, 150, "a1", 2000);

		Action.wiggle(w1, 0, 100, 3000);
		Action.wiggle(w2, 100, 100, 3000);
		Action.wiggle(w3, -100, 100, 3000);	
		
		return WaveInfo.combine(w1,w2,w3);
	}

	
	public static WaveInfo w8() {
		return new CircularInvade(5, -200, -200, 450, 200, 250, "c1", 9000);
	}

	public static WaveInfo w9(){
		WaveInfo w = new WaveInfo();
		
		
		Start s1 = new Start(0,-100);
		Start s2 = new Start(200,-100);
		Start s3 = new Start(400,-100);
		Move m = new Move(0, 300, 2000);
		Loop l = new Loop();
		Rectangle r = new Rectangle(250, 300, true, 1500);
		
		w.putEnemy("a3", s1,m,l,r);
		w.putEnemy("a3", s2,m,l,r);
		w.putEnemy("a3", s3,m,l,r);
		
		
		return w;
	}
	
	public static WaveInfo w10() {
		WaveInfo w = new WaveInfo();

		Action s = new Start(200, -600);
		Action m = new Move(0, 900, 6000);

		w.putEnemy("boss", s, m);
		Action.wiggle(w, 100, 0, 4000);

		Start s2 = new Start(-100, 200);
		Start s3 = new Start(-150, 300);
		
		Action m2 = new Move(150, 0, 1000);
		Rectangle r = new Rectangle(600, 1, true, 1000);
		w.putEnemy("a3", s2, new Delay(5000), m2, new Loop(), r);
		w.putEnemy("a3", s3, new Delay(5000), m2, new Loop(), r);

		return w;
	}
	
}
