package ori;

import java.io.PrintWriter;
import ori.mas.actors.*;
import ori.mas.core.*;
import ori.mas.fsm.*;
import ori.mas.fsm.states.*;
import ori.mas.sensors.*;
import ori.ogapi.geometry.Circle;
import ori.ogapi.geometry.LinkedListSurface;
import ori.ogapi.geometry.Point;
import ori.ogapi.report.PrintReporter;

public class Main {

	public static void main(String args[]) {

		PrintReporter reporter = new PrintReporter(System.out);

		World w;
		Heart h;
		Agent a1,a2;
		Body b1,b2;
		Mind m1;
		StateMachineMind m2;

		h = new DefaultHeart();

		w = new World(h,new AdaptedScene(new LinkedListSurface<Body>()));

		for (int i = 0 ; i < 20 ; i++) {
		b1 = new Body();
		b1.set(Properties.HEALTH,new Integer(1000));
		b1.set(Properties.HEALTH_MIN,new Integer(0));
		b1.set(Properties.HEALTH_MAX,new Integer(1000));
		b1.set(Properties.FEED,new Integer(100));
		b1.set(Properties.FEED_MIN,new Integer(0));
		b1.set(Properties.FEED_MAX,new Integer(100));
		b1.translate(new Point(10*i,10*i));
		b1.addActor(new MovementActor(b1,5));
		b1.addSensor(new ShapeSensor(b1,new Circle(0,0,150)));
		m1 = new StateMachineMind(new PatrolState());
		a1 = new Agent(m1,b1);
		w.add(a1);
		}

		b2 = new Body();
		b2.set(Properties.HEALTH,new Integer(1000));
		b2.set(Properties.HEALTH_MIN,new Integer(0));
		b2.set(Properties.HEALTH_MAX,new Integer(1000));
		b2.set(Properties.FEED,new Integer(100));
		b2.set(Properties.FEED_MIN,new Integer(0));
		b2.set(Properties.FEED_MAX,new Integer(100));
		b2.translate(new Point(100,120));
		b2.addActor(new MovementActor(b2,20));
		b2.addActor(new EatActor(b2,10));
		b2.addSensor(new ShapeSensor(b2,new Circle(0,0,100)));

		m2 = new StateMachineMind(new PredateState());

		a2 = new Agent(m2,b2);
		w.add(a2);

		/*
		reporter.newSection("init");
		reporter.report(w);
		for (int i = 0 ; i < 5  ; i++) {
			System.out.println("tick");
		//	reporter.newSection("main loop" + i);
			w.tick();
		//	reporter.report(w);
			for (Agent a : w) {
				System.out.println(a.body().center());
			}
		}
*/
		try {
	//	PrintWriter p1 = new PrintWriter("agent1.res");
	//	PrintWriter p2 = new PrintWriter("agent2.res");
	//	
		final int nbTicks = 100;
		for (int tick = 0 ; tick < nbTicks ; tick++)  {
			System.out.println("tick = "+tick);
			w.tick();
			int k = 0;
			for (Agent a : w) {
				k++;
				System.out.println(a.hashCode()+" "+a.body().center().x+" "+a.body().center().y);
			}
			System.out.println("m2 = "+m2.current());
			System.out.println("k = "+k);
////		for (int y = -20 ; y < 20 ; y++) {
////			for (int x = -20 ; x < 20 ; x++) {
////				char d1 = '.';
////				char d2 = '.';
////				int realX = x * 20;
////				int realY = y * 20;
////				int realXend = (x+1)*20;
////				int realYend = (y+1)*20;
////				realXend--;
////				realYend--;
////				Rectangle zone = new Rectangle(new Point(realX,realY),new Point(realXend,realYend));
////				Scene tile = w.scene().getPartlyIn(zone);
////				if (tile.contains(b1))
////					d1 = '1';
////				if (tile.contains(b2))
////					d2 = '2';
////		//		System.out.print(d1);
////		//		System.out.print(d2);
////			}
////		//	System.out.println(" ");
////		}
		}
		}
		catch (Exception e) {
			System.out.println("exception");
			e.printStackTrace();
		}

	}

};

