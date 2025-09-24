import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Programm {
	private File file;
	private List<Band> bands;

	/**
	 * Erzeugt ein neues Programm und laedt den letzten Zustand aus der Sicherungsdatei.
	 */
	public Programm() {
		this(true);
	}

	/**
	 * Erzeugt ein neues Programm
	 * @param load true, wenn der letzte Zustand aus der Sicherung geladen werden soll.
	 */
	public Programm(boolean load) {
		String filename = "bands.dat";
		file = new File(filename);
		
		if (load) {
			if (file.exists()) {
				read();
			}

			if (this.bands == null) {
				this.bands = new ArrayList<Band>();
			}
		} else {
			this.bands = new ArrayList<Band>();
		}
	}

	public void addBand(Band band) {
		bands.add(band);
	}

	public Band getBand(String name) {
		for (Band band : bands) {
			if (band.getName().equalsIgnoreCase(name)) {
				return band;
			}
		}
		return null;
	}

	/**
	 * Speichert den aktuellen Programmzustand.
	 */
	public void quit() {
		ObjectOutputStream dos = null;
		try {
			dos = new ObjectOutputStream(new FileOutputStream(file));
			dos.writeObject(bands);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void read() {
		ObjectInputStream ois = null;

		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			this.bands = (List<Band>) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Ungueltige Datei " + file.getName());
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
