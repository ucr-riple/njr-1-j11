package Game;
import java.util.Random;

import javax.swing.*;

import AMath.Calc;
import GUI.*;
import Sentiens.Clan;

public class AGPmain {
	private static int initpop = 10000;
//	public static Random rand = new Random();
	public static Random rand = new Random(0);
	public static GUImain mainGUI;
	public static Realm TheRealm;
	private static Realm lastRealm;
	
	
	//these are for shire naming... so they dont get new names every time u reload
	public static final int shireFPLen = 43;
	public static final int shireLPLen = 73;
	public static final int[] SR = Calc.randomOrder(shireFPLen*shireLPLen);
	/**
	 * @param args
	 */
	
	public static boolean isGoing = false;
	public static boolean AUTOPILOT = true;

	public static void turnOffAutopilot() {AUTOPILOT = false;}
	public static void turnOnAutopilot() {AUTOPILOT = true;}
	
	public void init() {
		
//		this.setBackground(Color.gray);
	}

	public static void setRealm(Realm r) {lastRealm = TheRealm; TheRealm = r;}
	public static void setLastRealm() {Realm tmp = TheRealm; TheRealm = lastRealm; lastRealm = tmp;}
	
	public void start() {
		

		setLookAndFeel();
		
		long start = System.nanoTime();  
		
		mainGUI = new GUImain("AGP");
		mainGUI.initializeMD();
		TheRealm = Realm.makeRealm(getShiresX(), getShiresY(), initpop);
		TheRealm.setupDefs();
		TheRealm.doCensus();
		//mainGUI.initializeTD(TheRealm.getShireData());
		mainGUI.initializeGM();
		mainGUI.initializeSM();
		Clan avatar = TheRealm.getClan(0);
		mainGUI.initializeAC(avatar);
		mainGUI.GM.loadClan(avatar);
		AGPmain.mainGUI.SM.loadShire(avatar.myShire());
		mainGUI.GM.setState();
		mainGUI.SM.setState();
		mainGUI.SM.setState("POPULATION", PopupShire.POPULATION);
		

		//TheRealm.go();

		
		long elapsedTime = System.nanoTime() - start;

		System.out.println("num shires: " + TheRealm.shires.length);
		System.out.println("Elapsedtime: " + (double)elapsedTime / 1000000000 + " seconds");
		
	}
	
	public static void pause() {
		mainGUI.AC.showPlayButton();
		isGoing = false;
	}
	public static void play() {
		mainGUI.AC.showPauseButton();
		isGoing = true;
		Thread playThread = new Thread() {
			@Override
			public void run() {AGPmain.TheRealm.run();}
		};
		playThread.start();
	}
	
//	public static void pauseOLD() {
//		if(Thread.currentThread().getName().equals(TheRealm.getName())) {
//			System.out.println("paused " + Thread.currentThread().getName());
//			try {
//				System.out.println("sleep start");
//				Thread.sleep(100000000);
//				throw new IllegalStateException("never unpaused!");
//			} catch (InterruptedException e) {
//				System.out.println("interrupted");
//				Thread.interrupted();
//			}
//		}
//		else {
//			mainGUI.AC.showPlayButton();
//			isGoing = false;
//		}
//	}
//	public static void playOLD() {
//		if (isGoing) {TheRealm.interrupt();}
//		mainGUI.AC.showPauseButton();
//		isGoing = true;
//	}

	public static int getShiresX() {return mainGUI != null ? mainGUI.MD.getTCols() / 2 : 100;}
	public static int getShiresY() {return mainGUI.MD.getTRows() / 2;}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	public static Clan getAvatar() {
		return mainGUI != null ? mainGUI.AC.getAvatar() : null;
	}
	
}


