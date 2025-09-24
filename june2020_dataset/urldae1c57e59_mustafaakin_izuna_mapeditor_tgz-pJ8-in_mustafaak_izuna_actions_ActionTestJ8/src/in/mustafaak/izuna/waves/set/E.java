package in.mustafaak.izuna.waves.set;

import in.mustafaak.izuna.meta.WaveInfo;
import in.mustafaak.izuna.actions.*;
import in.mustafaak.izuna.waves.CircularInvade;
import in.mustafaak.izuna.waves.Invade;
import in.mustafaak.izuna.waves.RowColEnter;

public class E {
	static Action l = new Loop();

	public static WaveInfo w1() {
		WaveInfo w = new WaveInfo();
		Action s = new Start(-200, 100);
		Action m = new Move(300, 300, 2000);
		Action z = new ZigZag(200, 500, true, 1000);

		for (int i = 0; i < 5; i++) {
			w.putEnemy("a4", s, l, new Delay(i * 500 + 1), m, z, z, z);
		}
		return w;
	}

	public static WaveInfo w2() {
		WaveInfo w = new WaveInfo();
		Action s1 = new Start(-200, 100);
		Action s2 = new Start(-200, 250);
		Action m = new Move(300, 300, 2000);
		Action z = new ZigZag(200, 500, true, 1000);

		for (int i = 0; i < 2; i++) {
			w.putEnemy("a4", s1, l, new Delay(i * 1000 + 1), m, z, z, z);
			w.putEnemy("a4", s2, l, new Delay(i * 1000 + 1), m, z, z, z);
		}

		return w;
	}

	public static WaveInfo w3() {
		WaveInfo w = new WaveInfo();
		Action s1 = new Start(-200, 100);
		Action m = new Move(300, 0, 1000);
		Action r = new Rectangle(400, 200, true, 1000);

		for (int i = 0; i < 6; i++) {
			w.putEnemy(i % 2 == 0 ? "a3" : "a4", s1, new Delay(i * 500), m, l, r);
		}

		return w;
	}

	public static WaveInfo w4() {
		WaveInfo w1 = new CircularInvade(3, -250, -200, 400, 400, 100, "a3", 5000);
		WaveInfo w2 = new CircularInvade(3, 900, -200, -400, 400, 100, "a4", 5000);

		return WaveInfo.combine(w1, w2);
	}

	public static WaveInfo w5() {
		WaveInfo w1 = new CircularInvade(3, -250, -200, 400, 500, 70, "a3", 5000);
		WaveInfo w2 = new CircularInvade(3, 900, -200, -400, 500, 70, "a3", 5000);

		WaveInfo w3 = new RowColEnter(RowColEnter.FROM_UP, 150, 50, 1, 3, 150, 0, "a4", 2500);
		Action.wiggle(w3, 0, 50, 2000);
		return WaveInfo.combine(w1, w2, w3);
	}

	public static WaveInfo w6() {
		WaveInfo w1 = new RowColEnter(RowColEnter.FROM_UP, 25, 50, 1, 5, 150, 0, "a1", 2500);
		WaveInfo w2 = new RowColEnter(RowColEnter.FROM_UP, 100, 250, 1, 4, 150, 0, "a2", 2500);
		WaveInfo w3 = new RowColEnter(RowColEnter.FROM_UP, 150, 450, 1, 3, 150, 0, "a3", 2500);
		Action.wiggle(w1, 0, -50, 2000);
		Action.wiggle(w2, 0, 50, 2000);
		Action.wiggle(w3, 0, 100, 3000);

		return WaveInfo.combine(w1, w2, w3);
	}

	public static WaveInfo w7() {
		WaveInfo w1 = new RowColEnter(RowColEnter.FROM_UP, 150, 150, 1, 3, 150, 0, "a1", 2500);
		WaveInfo w2 = new RowColEnter(RowColEnter.FROM_RIGHT, 100, 250, 1, 4, 150, 0, "a2", 2500);
		WaveInfo w3 = new RowColEnter(RowColEnter.FROM_LEFT, 25, 400, 1, 5, 150, 0, "a3", 2500);
		Action.wiggle(w1, 0, -50, 1000);
		Action.wiggle(w2, 0, 50, 1000);
		Action.wiggle(w3, 0, 100, 1500);

		return WaveInfo.combine(w1, w2, w3);

	}

	public static WaveInfo w8() {
		WaveInfo w1 = new CircularInvade(5, -200, -200, 500, 400, 150, "a4", 5000);
		WaveInfo w2 = new RowColEnter(RowColEnter.FROM_LEFT, 10, 50, 3, 1, 0, 200, "b4", 2500);
		WaveInfo w3 = new RowColEnter(RowColEnter.FROM_RIGHT, 570, 50, 3, 1, 0, 200, "b4", 2500);
		Action.wiggle(w2, 0, 200, 3500);
		Action.wiggle(w3, 0, 200, 3500);

		return WaveInfo.combine(w1, w2, w3);

	}

	public static WaveInfo w9() {
		WaveInfo w1 = new CircularInvade(5, -200, -200, 300, 400, 100, "a3", 5000);
		WaveInfo w2 = new CircularInvade(7, 720 / 2 - 50, -200, 0, 400, 150, "a4", 5000);
		WaveInfo w3 = new CircularInvade(5, 820, -200, -300, 400, 100, "a3", 5000);

		return WaveInfo.combine(w1, w2, w3);
	}

	public static WaveInfo w10() {
		WaveInfo w1 = new CircularInvade(3, -200, -200, 300, 400, 100, "a2", 5000);
		WaveInfo w3 = new CircularInvade(3, 820, -200, -300, 400, 100, "a1", 5000);

		WaveInfo w = new CircularInvade(7, 720 / 2 - 50, -200, 0, 400, 150, "a4", 5000);

		Action s = new Start(-500, 400);
		Move m1 = new Move(900, 0, 4000);
		Move m2 = new Move(-500, 0, 3000);
		Move m3 = new Move(500, 0, 3000);

		w.putEnemy("boss", s, m1, l, m2, m3);

		return WaveInfo.combine(w1,w,w3);

	}
}
