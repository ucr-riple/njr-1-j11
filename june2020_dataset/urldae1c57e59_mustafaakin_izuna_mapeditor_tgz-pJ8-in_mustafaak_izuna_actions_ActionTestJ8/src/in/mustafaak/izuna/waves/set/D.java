package in.mustafaak.izuna.waves.set;

import in.mustafaak.izuna.meta.WaveInfo;
import in.mustafaak.izuna.actions.*;
import in.mustafaak.izuna.waves.CircularInvade;
import in.mustafaak.izuna.waves.Invade;
import in.mustafaak.izuna.waves.RowColEnter;

public class D {

	public static WaveInfo w1() {
		WaveInfo w = new WaveInfo();

		Action s1 = new Start(-100, 400);
		Action s2 = new Start(720, 400);
		Action m1 = new Move(200, 0, 1000);
		Action m2 = new Move(-200, 0, 1000);
		Action c1 = new Circle(200, 3, 2, true, 2000);
		Action c2 = new Circle(200, 3, 2, false, 2000);

		w.putEnemy("a2", s1, m1, c1);
		w.putEnemy("a2", s2, m2, c2);
		Action.wiggle(w, 0, 100, 2000);
		return w;
	}

	public static WaveInfo w2() {
		WaveInfo w = new WaveInfo();

		Action s1 = new Start(-100, 400);
		Action m1 = new Move(200, 0, 1000);
		Action c1 = new Circle(100, 3, 2, true, 1500);

		for (int i = 0; i < 5; i++) {
			w.putEnemy("a2", s1, new Loop(), new Delay(i * 700), c1, c1, c1, c1);
		}
		return w;
	}

	public static WaveInfo w3() {
		WaveInfo w = new WaveInfo();

		Action s1 = new Start(720, 400);
		Action c1 = new Circle(100, 3, 2, false, 1500);

		for (int i = 0; i < 5; i++) {
			w.putEnemy("a2", s1, new Loop(), new Delay(i * 700), c1, c1, c1, c1);
		}
		return w;
	}

	public static WaveInfo w4() {
		WaveInfo w = new WaveInfo();

		Action s1 = new Start(-100, 400);
		Action c1 = new Circle(100, 3, 2, true, 1500);
		Action c2 = new Circle(100, 1, 2, false, 1500);

		for (int i = 0; i < 5; i++) {
			w.putEnemy("a2", s1, new Loop(), new Delay(i * 700), c1, c2, c1, c2);
		}
		return w;
	}

	public static WaveInfo w5() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_UP, 0, 50, 1, 4, 180, 0, "c3", 2000);
		Action.wiggle(w, 0, 400, 4000);
		return w;
	}

	public static WaveInfo w6() {
		WaveInfo w = new WaveInfo();

		Action s1 = new Start(50, -100);
		Action s2 = new Start(200, -100);
		Action s3 = new Start(350, -100);
		Action s4 = new Start(500, -100);
		Action m = new Move(0, 300, 2000);
		Action l = new Loop();
		Action e = new Eight(100, 5000);

		w.putEnemy("a2", s1, m, l, e);
		w.putEnemy("a1", s2, m, l, new Delay(500), e);
		w.putEnemy("a2", s3, m, l, e);
		w.putEnemy("a1", s4, m, l, new Delay(500), e);

		return w;
	}

	public static WaveInfo w7() {
		WaveInfo w = new WaveInfo();

		Action s1 = new Start(-200, 50);
		Action s2 = new Start(-250, 150);
		Action s3 = new Start(-300, 250);
		Action s4 = new Start(-350, 350);
		Action s5 = new Start(-400, 450);
		Move m = new Move(1200, 0, 5000);
		Move m2 = new Move(-1200, 0, 4500);
		Loop l = new Loop();

		w.putEnemy("a1", s1, l, m, m2);
		w.putEnemy("a2", s2, l, m, m2);
		w.putEnemy("a1", s3, l, m, m2);
		w.putEnemy("a2", s4, l, m, m2);
		w.putEnemy("a1", s5, l, m, m2);

		return w;
	}

	public static WaveInfo w8() {
		WaveInfo w = new WaveInfo();

		Action s1 = new Start(-200, 50);
		Action s2 = new Start(800, 150);

		Move m1 = new Move(1200, 0, 3000);
		Move m2 = new Move(-1200, 0, 3000);

		Move down = new Move(0, 200, 50);

		Loop l = new Loop();

		w.putEnemy("a1", s1, l, m1, down, m2, down, m1, down, m2, down);
		w.putEnemy("a2", s2, l, m2, down, m1, down, m2, down, m1, down);

		return w;
	}

	public static WaveInfo w9() {
		WaveInfo w = new RowColEnter(RowColEnter.FROM_UP, 0, 50, 1, 4, 180, 0, "c3", 2000);
		Action.wiggle(w, 0, 400, 4000);

		Action s1 = new Start(-200, 550);
		Action s2 = new Start(800, 650);
		Move m1 = new Move(1200, 0, 5000);
		Move m2 = new Move(-1200, 0, 4500);
		Loop l = new Loop();

		w.putEnemy("a1", s1, l, m1);
		w.putEnemy("a2", s2, l, m2);

		return w;
	}

	public static WaveInfo w10() {
		WaveInfo w = new WaveInfo();
		Loop l = new Loop();

		Action sB = new Start(-500, 0);
		Action mB1 = new Move(600, 0, 4000);
		Action mB = new Rectangle(400, 1, true, 1000);

		w.putEnemy("boss", sB, mB1, l, mB);

		Action s1 = new Start(-200, 550);
		Action s2 = new Start(800, 650);
		Move m1 = new Move(1200, 0, 5000);
		Move m2 = new Move(-1200, 0, 4500);

		w.putEnemy("a1", s1, l, m1);
		w.putEnemy("a2", s2, l, m2);

		return w;

	}
}
