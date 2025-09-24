package in.mustafaak.izuna.waves.set;


import in.mustafaak.izuna.actions.*;
import in.mustafaak.izuna.meta.WaveInfo;
import in.mustafaak.izuna.waves.CircularInvade;
import in.mustafaak.izuna.waves.Invade;
import in.mustafaak.izuna.waves.RowColEnter;

public class B {
	public static WaveInfo w1() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_UP, 100, 200, 1, 4, 150, 0, "b1", 2000);
		Action.wiggle(w, 100, 0, 2000);
		return w;
	}

	public static WaveInfo w2() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_LEFT, 100, 50, 1, 4, 150, 0, "b1", 2000);
		Action.wiggle(w, 150, 0, 2000);
		return w;
	}

	public static WaveInfo w3() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_RIGHT, 100, 50, 1, 4, 150, 0, "b1", 2000);
		Action.wiggle(w, -150, 0, 2000);
		return w;
	}

	public static WaveInfo w4() {
		WaveInfo w = new Invade(4, -200, -200, 500, 700, 100, 600, 400, 200, "a2", 4000);
		return w;
	}

	public static WaveInfo w5() {
		WaveInfo w = new WaveInfo();

		Start s = new Start(-200, 100);
		Loop l = new Loop();
		CrossAndUp c = new CrossAndUp(200, 300, 3000);
		for (int i = 0; i < 4; i++) {
			w.putEnemy("a2", s, l, new Delay(i * 1500), c, c, c, c, c);

		}
		return w;
	}

	public static WaveInfo w6() {
		WaveInfo w = new WaveInfo();

		Start s = new Start(720 + 100, 100);
		Loop l = new Loop();
		CrossAndUp c = new CrossAndUp(-200, 300, 3000);
		for (int i = 0; i < 4; i++) {
			w.putEnemy("a2", s, l, new Delay(i * 1500), c, c, c, c, c);
		}

		return w;

	}

	public static WaveInfo w7() {
		WaveInfo w = new Invade(6, -200, -200, 500, 700, 100, 600, 400, 200, "a2", 4000);
		Action.wiggle(w, -50, 50, 1000);
		return w;
	}

	public static WaveInfo w8() {

		WaveInfo l = new RowColEnter(RowColEnter.FROM_LEFT, 50, 250, 3, 1, 150, 150, "b1", 2000);
		WaveInfo r = new RowColEnter(RowColEnter.FROM_RIGHT, 550, 250, 3, 1, 150, 150, "b1", 2000);

		Action.wiggle(l, 100, -100, 2000);
		Action.wiggle(r, -100, 100, 2000);
		return WaveInfo.combine(l, r);

	}

	public static WaveInfo w9() {
		WaveInfo u1 = new RowColEnter(RowColEnter.FROM_UP, 150, 150, 1, 4, 150, 150, "b1", 2000);
		WaveInfo u2 = new RowColEnter(RowColEnter.FROM_UP, 50, 350, 1, 4, 150, 150, "a1", 2000);

		Action.wiggle(u1, -100, 0, 2000);
		Action.wiggle(u2, 50, 0, 2000);

		return WaveInfo.combine(u1, u2);
	}

	public static WaveInfo w10() {
		WaveInfo w = new WaveInfo();

		Action s = new Start(200, -600);
		Action m = new Move(0, 900, 6000);

		w.putEnemy("boss", s, m);
		Action.wiggle(w, 100, 0, 4000);

		Start s2 = new Start(-100, 200);
		Action m2 = new Move(150, 0, 1000);
		Rectangle r = new Rectangle(600, 1, true, 1000);
		w.putEnemy("a3", s2, new Delay(5000), m2, new Loop(), r);

		return w;
	}
}
