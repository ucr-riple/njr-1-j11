package User_Interface;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * A basic MediaPlayer that starts and stops songs.
 * The different methods are called to play different songs
 * depending on where in the game the player is:
 *
 * > welcomeScreen - played when the user is in the main menus
 * > mainTheme - played when the player first joins the game
 * > winningTheme - played when a player wins the game
 *
 * All credit for the music to HalcyonicFalconX at The Open Game
 * Art Bundle, http://open.commonly.cc/
 *
 * @author Josh Brake, 300274198, brakejosh
 */
public class MediaPlayer {

	private final static String mainTheme = "Sounds/An Adventure Awaits.wav";
	private final static String welcomeScreen = "Sounds/The Frozen Journey.wav";
	private final static String winningTheme = "Sounds/Rainbows and Unicorns.wav";
	private static AudioInputStream ais;
	private static Clip clip;

	public MediaPlayer() { }

	public static void playWelcome() {
		try {
			ais = AudioSystem
					.getAudioInputStream(new File(welcomeScreen));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playMain() {
		try {
			ais = AudioSystem
					.getAudioInputStream(new File(mainTheme));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playWinning() {
		try {
			ais = AudioSystem
					.getAudioInputStream(new File(winningTheme));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops the current song playing, should be called before
	 * starting a new song so no two songs play at once.
	 */
	public static void stopCurrent() {
		clip.stop();
	}
}
