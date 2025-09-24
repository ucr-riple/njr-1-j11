package manager;

import java.net.URL;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.Timer;

import DeviceGraphics.DeviceGraphics;
import agent.Agent;

/**
 * Handles the music and sound effects for the Factory Production Manager.
 * 
 * @author Daniel Paje
 */
public class FPMMusicAgent extends Agent {

	// Background music - Goldenrod City
	private Clip music, pokeflute, recovery, completed, messageTone;

	private final Semaphore musicSem = new Semaphore(1, true);

	private final FactoryProductionManager fpm;

	private final String name;

	URL url = this.getClass().getClassLoader().getResource("audio/goldenrod.wav");
	URL fluteURL = this.getClass().getClassLoader().getResource("audio/pokeflute.wav");
	URL recoveryURL = this.getClass().getClassLoader().getResource("audio/recovery.wav");
	URL completedURL = this.getClass().getClassLoader().getResource("audio/item_get.wav");
	URL messageToneURL = this.getClass().getClassLoader().getResource("audio/ping.wav");

	private boolean startCompleted;
	private boolean startFlute;
	private boolean startRecovery;
	private boolean startMessageTone;
	// Create a new timer
	private Timer timer;
	private final java.util.Timer musicTimer = new java.util.Timer();

	public FPMMusicAgent(FactoryProductionManager fpm) {
		super();
		this.name = "DJ Mary";
		this.fpm = fpm;
		startCompleted = false;
		startFlute = false;
		startRecovery = false;
		startMessageTone = false;
		init();
		this.startMusic();
	}

	// Load music
	public void init() {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			music = AudioSystem.getClip();
			music.open(audioIn);

		} catch (LineUnavailableException e) {
			print("Sorry, I can't load the music in Java 1.6 :(");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AudioInputStream pokeAudioIn = AudioSystem.getAudioInputStream(fluteURL);
			pokeflute = AudioSystem.getClip();
			pokeflute.open(pokeAudioIn);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AudioInputStream recoverAudioIn = AudioSystem.getAudioInputStream(recoveryURL);
			recovery = AudioSystem.getClip();
			recovery.open(recoverAudioIn);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(completedURL);
			completed = AudioSystem.getClip();
			completed.open(audioIn);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(messageToneURL);
			messageTone = AudioSystem.getClip();
			messageTone.open(audioIn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Message reception
	 */
	public void msgStartCompleted() {
		startCompleted = true;
		stateChanged();
	}

	public void msgStartPokeflute() {
		startFlute = true;
		stateChanged();

	}

	public void msgStartRecovery() {
		startRecovery = true;
		stateChanged();
	}

	public void msgStartMessageTone() {
		startMessageTone = true;
		stateChanged();
	}

	/*
	 * Scheduler (non-Javadoc)
	 * 
	 * @see agent.Agent#pickAndExecuteAnAction()
	 */
	@Override
	public boolean pickAndExecuteAnAction() {
		print("In my scheduler");
		if (!sfxActive()) {
			stopSFX();
			music.start();
		}
		if (startFlute && !sfxActive()) {
			startFlute = false;
			startPokeflute();
			return true;
		}

		if (startCompleted && !sfxActive()) {
			startCompleted = false;
			startCompleted();
			return true;
		}

		if (startRecovery && !sfxActive()) {
			startRecovery = false;
			startRecovery();
			return true;
		}

		if (startMessageTone) {
			startMessageTone = false;
			startMessageTone();
			return true;
		}
		return false;
	}

	/*
	 * Actions
	 */
	public void startMusic() {
		print("Playing music");
		// stopCompleted();
		// stopPokeflute();
		// stopRecovery();

		if (music != null) {
			//if (!music.isActive() && !sfxActive()) {
				music.loop(Clip.LOOP_CONTINUOUSLY);
				//music.start);
			//}
		}
		stateChanged();
	}

	public void stopSFX() {
		pokeflute.stop();
		pokeflute.flush();
		completed.stop();
		completed.flush();
		recovery.stop();
		recovery.flush();
	}

	public boolean sfxActive() {
		return pokeflute.isActive() || completed.isActive() || recovery.isActive();
	}

	public void startPokeflute() {
		print("Playing pokeflute");
		if (pokeflute != null) {
			try {
				musicSem.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			music.stop();
			music.flush();
			// stopCompleted();
			// stopRecovery();

			System.out.println("plays flute"); // !!! EXTREMELY IMPORTANT

			stopSFX();
			// pokeflute.stop();
			pokeflute.setFramePosition(0);
			pokeflute.start();
			musicTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					startMusic();
				}
			}, 4224);

			// while (pokeflute.isRunning()) {
			// ;
			// }
			musicSem.release();
			//startMusic();
		}
		stateChanged();
	}

	public void startRecovery() {
		print("Playing recovery");
		if (recovery != null) {
			try {
				musicSem.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			music.stop();
			music.flush();
			// stopPokeflute();
			// stopCompleted();
			System.out.println("plays recovery"); // !!! EXTREMELY IMPORTANT

			stopSFX();
			// recovery.stop();
			recovery.setFramePosition(0);
			recovery.start();
			musicTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					startMusic();
				}
			}, 2000);

			musicSem.release();
			//startMusic();
		}
		stateChanged();
	}

	public void startCompleted() {
		print("Playing completed");
		if (completed != null) {
			try {
				musicSem.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			music.stop();
			music.flush();
			// stopPokeflute();
			// stopRecovery();

			System.out.println("plays completed"); // !!! EXTREMELY IMPORTANT

			stopSFX();
			// completed.stop();
			completed.setFramePosition(0);
			completed.start();
			musicTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					startMusic();
				}
			}, 2000);
			musicSem.release();
//			fpm.setConveyorExitTrue();
			//startMusic();
		}
		stateChanged();
	}

	public void startMessageTone() {
		print("Playing message tone");
		if (messageTone != null) {
			try {
				musicSem.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("plays message tone"); // !!! EXTREMELY IMPORTANT

			messageTone.setFramePosition(0);
			messageTone.start();

			// while (completed.isRunning()) {
			// ;
			// }
			musicSem.release();
		}
		stateChanged();
	}

	@Override
	public void setGraphicalRepresentation(DeviceGraphics dg) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return this.name;
	}
}
