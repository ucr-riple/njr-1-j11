package in.mustafaak.izuna.waves.set;

import java.util.ArrayList;
import java.util.List;

import in.mustafaak.izuna.meta.WaveEnemy;
import in.mustafaak.izuna.meta.WaveInfo;
import in.mustafaak.izuna.meta.WavePath;
import in.mustafaak.izuna.waves.RowColEnter;
import in.mustafaak.izuna.actions.*;

public class A {
	public static WaveInfo w1() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_UP, 100, 200, 1, 4, 150, 0, "a1", 2000);
		Action.wiggle(w, 100, 0, 2000);
		return w;
	}

	public static WaveInfo w2() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_LEFT, 100, 50, 1, 4, 150, 0, "a1", 2000);
		Action.wiggle(w, 150, 0, 2000);
		return w;
	}

	public static WaveInfo w3() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_RIGHT, 100, 50, 1, 4, 150, 0, "a1", 2000);
		Action.wiggle(w, 150, 0, 2000);
		return w;
	}

	public static WaveInfo w4() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_UP, 100, 50, 2, 4, 150, 150, "a1", 2000);
		Action.wiggle(w, 0, 150, 2000);
		return w;
	}

	public static WaveInfo w5() {
		WaveInfo w1 = new WaveInfo();
		w1.setEnemies(new ArrayList<WaveEnemy>());

		Action s1 = new Start(-200, -200);
		Action m1 = new Move(250, 600, 2000);
		Action p1 = new Move(0, -300, 1000);
		w1.putEnemy("a1", s1, m1, p1);

		Action s2 = new Start(920, -200);
		Action m2 = new Move(-350, 600, 2000);
		Action p2 = new Move(0, -300, 1000);
		w1.putEnemy("a1", s2, m2, p2);

		WaveInfo w2 = new RowColEnter(RowColEnter.FROM_UP, 175, 100, 1, 3, 135, 0, "a1", 3000);

		Action.wiggle(w2, 0, 100, 2000);
		Action.wiggle(w1, 0, -50, 2000);

		return WaveInfo.combine(w1, w2);
	}

	public static WaveInfo w6() {
		WaveInfo u = new RowColEnter(RowColEnter.FROM_UP, 150, 150, 1, 3, 150, 150, "a1", 2000);
		WaveInfo l = new RowColEnter(RowColEnter.FROM_LEFT, 50, 250, 3, 1, 150, 150, "a1", 2000);
		WaveInfo r = new RowColEnter(RowColEnter.FROM_RIGHT, 550, 250, 3, 1, 150, 150, "a1", 2000);

		Action.wiggle(u, 0, 100, 4000);
		Action.wiggle(l, 100, 0, 2000);

		Action.wiggle(r, -100, 0, 2000);
		return WaveInfo.combine(u, l, r);
	}

	public static WaveInfo w7() {
		WaveInfo w = new WaveInfo();

		for (int i = 0; i < 4; i++) {
			Action s = new Start(-200, -200);
			Action l = new Loop();
			Action p = new Delay(i * 1000);
			Action m = new Move(250, 500, 2000);
			Action z1 = new ZigZag(250, 400, true, 2000);
			Action z2 = new ZigZag(250, 400, true, 2000);
			Action z3 = new ZigZag(250, 400, true, 2000);
			w.putEnemy("a1", s, l, p, m, z1, z2, z3);
		}
		return w;

	}

	public static WaveInfo w8() {
		WaveInfo w = new WaveInfo();

		Action s = new Start(-150, 200);
		Action m0 = new Move(200,0, 500);
		Action l = new Loop();
		Action m1 = new Move(600, 0, 4000);
		Action m2 = new Move(-600, 0, 4000);
		w.putEnemy("a1", s, m0, l, m1, m2);
		

		Action s2 = new Start(720 + 100, 300);
		Action m3 = new Move(-200,0, 500);
		w.putEnemy("a1", s2, m3, l, m2, m1);

		
		
		return w;
	}
	
	public static WaveInfo w9(){
		WaveInfo w = new WaveInfo();

		Action s = new Start(-150, 200);
		Action m0 = new Move(200,0, 500);
		Action l = new Loop();
		Action m1 = new Move(300, 0, 2000);
		Action m2 = new Move(-300, 0, 2000);
		w.putEnemy("a1", s, m0, l, m1, m2);
		

		Action s2 = new Start(720 + 100, 300);
		Action m3 = new Move(-200,0, 500);
		w.putEnemy("a1", s2, m3, l, m2, m1);
		
		Action s4 = new Start(720 + 130, 400);
		Action s3 = new Start(-120, 100);
		
		
		w.putEnemy("a1", s3, m0, l, m1, m2);
		w.putEnemy("a1", s4, m3, l, m2, m1);
		
		
		return w;
	}
	
	public static WaveInfo w10(){
		WaveInfo w = new WaveInfo();
		
		Action s = new Start(200, -600);
		Action m = new Move(0,900, 6000);
		
		w.putEnemy("boss", s,m);
		Action.wiggle(w, 100, 0, 4000);
		
		
		return w;
	}
}
