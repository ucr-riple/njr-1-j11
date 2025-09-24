package input;

import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import modes.GameMode;
import modes.TBGameMode;

import com.golden.gamedev.engine.BaseInput;
/**
 * Abstract super class that input engines for
 * any Turn-Based game should extend and implement
 * three methods
 * @author prithvi
 *
 */
public abstract class TBInputEngine {
	
	/*
	 * On instantiation:
	 * Creates new eventList
	 * Sets GTGE's BaseInput object as its own
	 * Loads the arraylist of UserEvents
	 */
	public TBInputEngine(BaseInput b, TBGameMode g) {
		setEventList(new ArrayList<UserEvents>());
		setBsInput(b);
		loadEvents(g);
	}

	/**
	 * Field for holding list of UserEvents
	 */
	private ArrayList<UserEvents> eventList;
	/**
	 * Field for GTGE's BaseInput object
	 */
	private BaseInput bsInput;
	
	/**
	 * Abstract method to be implemented for a specific game
	 * to set default controls
	 * @param map
	 */
	public abstract void createDefaultKeyBindingsMap(HashMap<String, Integer> map);
	
	/**
	 * Abstract method determined by specific game
	 * for deciding on the name of the Key Binding
	 * file to save to
	 * @return
	 */
	public abstract String getFileName();
	
	/**
	 * Length of time that determines how
	 * often to check if a file changed
	 * @return
	 */
	public abstract int getFileCheckTime();

	/**
	 * Create FileWatcher to listen for file changes
	 * Load key bindings depending on existence of file
	 * NOTE: If file exists, must exist in correct format
	 * @param g
	 */
	protected void loadEvents(TBGameMode g) {
		File file=new File(getFileName());
		TimerTask task = new FileWatcher(file, g);
		Timer timer = new Timer();
	    timer.schedule(task , new Date(), getFileCheckTime());
	    if (!file.exists()) {
	    	loadDefaultKeyBindings(g, file);
	    }else{
	    	loadKeyBindingsFromFile(g, file);
	    }
	}

	/**
	 * Reads from key bindings file and instantiates every class
	 * with appropriate key
	 * Assumes file is in correct format (does no error checking)
	 * @param g
	 * @param file
	 * Reference: http://stackoverflow.com/questions/3671649/java-newinstance-of-class-thas-has-no-default-constructor
	 */
	protected void loadKeyBindingsFromFile(TBGameMode g, File file) {
		getEventList().clear();
		try{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while ((line = br.readLine()) != null)   {
				String[] parts = line.split(" ");
				Class<?> event = Class.forName(parts[0]);
				Constructor<UserEvents> ctor = (Constructor<UserEvents>) event.getConstructor(TBGameMode.class, int.class);
				getEventList().add(ctor.newInstance(g, Integer.parseInt(parts[1])));
			}
			in.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * If no file exists, instantiates events based on 
	 * user-defined database of key bindings.
	 * Writes these key bindings to file.
	 * @param g
	 * @param file
	 * Reference: http://stackoverflow.com/questions/3671649/java-newinstance-of-class-thas-has-no-default-constructor
	 */
	private void loadDefaultKeyBindings(TBGameMode g, File file) {
		HashMap<String, Integer> keyBindingMap = new HashMap<String, Integer>();
		createDefaultKeyBindingsMap(keyBindingMap);
		for (String key : keyBindingMap.keySet()) {
			Class<?> event;
			try {
				event = Class.forName(key);
				Constructor<UserEvents> ctor = (Constructor<UserEvents>) event.getConstructor(TBGameMode.class, int.class);
				getEventList().add(ctor.newInstance(g, keyBindingMap.get(key)));
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		try{
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			for (UserEvents event : getEventList()) {
				out.write(event.toString()+"\n");
			}
			out.close();
		}catch (Exception e){
			  e.printStackTrace();
		}
	}

	/**
	 * Method by which GTGE calls inputEngine
	 * @param arg0
	 */
	public void update(long arg0) {
		pollEvents();
	}

	/**
	 * On every update() call,
	 * this method runs through the event list
	 * to check if a particular input has occurred
	 */
	private void pollEvents() {
		for (UserEvents event : getEventList()) {
			if (isAnEvent(event)) {
				event.performEvent(getBsInput().getMouseX(), getBsInput().getMouseY());
			}
		}
	}

	/**
	 * Checks to see if an event is a particular event based on
	 * Key presses
	 * Mouse clicks
	 * Mouse movements
	 * @param event
	 * @return
	 */
	private boolean isAnEvent(UserEvents event) {
		return event.isLinkedToEvent(getBsInput().getKeyPressed()) || 
			   event.isLinkedToEvent(getBsInput().getMousePressed()) || 
			   (getBsInput().isMouseExists() && event.isLinkedToEvent(MouseEvent.MOUSE_MOVED));
	}
	
	/*
	 * Various setters and getters below
	 * for children of this class
	 */
	public void setEventList(ArrayList<UserEvents> eventList) {
		this.eventList = eventList;
	}
	
	public ArrayList<UserEvents> getEventList() {
		return eventList;
	}
	
	public void setBsInput(BaseInput bsInput) {
		this.bsInput = bsInput;
	}
	
	public BaseInput getBsInput() {
		return bsInput;
	}

	/**
	 * Private class for use by TBInputEngine to check when
	 * the key bindings file has changed;
	 * On change, loads key bindings again.
	 * @author prithvi
	 * Reference: http://www.rgagnon.com/javadetails/java-0490.html
	 */
	private class FileWatcher extends TimerTask {
		private long timeStamp;
		private File file;
		private TBGameMode g;

		  private FileWatcher( File file, TBGameMode g ) {
		    this.file = file;
		    this.timeStamp = file.lastModified();
		    this.g = g;
		  }

		  public final void run() {
		    long timeStamp = file.lastModified();

		    if( this.timeStamp != timeStamp ) {
		      this.timeStamp = timeStamp;
		      onChange(g, file);
		    }
		  }

		private void onChange(TBGameMode g, File file) {
			loadKeyBindingsFromFile(g, file);
		}
	}

}
