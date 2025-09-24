import java.util.ArrayList;
import java.util.List;

/**
 * Eine Sammlung von Songs.
 * 
 * @author Peter Pilgerstorfer
 * 
 */
public class Songs extends Selection<Song> {
	private static final long serialVersionUID = 1L;

	public Songs() {
	}

	/**
	 * Erstelle eine neue Song Sammlung die auf den selben Daten wie
	 * <code>base</code> arbeitet. Es sind jedoch nur Elemente sichtbar, die von
	 * den Selektoren selektiert werden.
	 * 
	 * @param base
	 * @param selectors
	 */
	private Songs(Songs base, List<Selector<Song>> selectors) {
		super(base, selectors);
	}

	/**
	 * Liefert eine Selektion der in diesem Objekt gespeicherten Songs. Mit den
	 * uebergebenen Selektoren kann bestimmt werden, welche Songs selektiert
	 * werden. Aenderungen in der zurueckgegebenen Selektion wirken sich direkt
	 * auf das Original aus.
	 * 
	 * @param selectors
	 * @return
	 */
	public Songs select(List<Selector<Song>> selectors) {
		return new Songs(this, selectors);
	}

	public List<SongVariante> getSongVarianten() {
		return getSongVarianten(new ArrayList<Selector<Variante>>());
	}

	public List<SongVariante> getSongVarianten(
			List<Selector<Variante>> selectors) {
		List<SongVariante> songVarianten = new ArrayList<SongVariante>();
		for (Song song : this) {
			for (Variante variante : song.getVarianten()) {
				if (select(variante, selectors)) {
					songVarianten.add(new SongVariante(song, variante));
				}
			}
		}
		return songVarianten;
	}

	/**
	 * @param variante
	 * @return true, wenn alle Selektoren die Variante selektieren, false
	 *         anderenfalls.
	 */
	private boolean select(Variante variante, List<Selector<Variante>> selectors) {
		for (Selector<Variante> selector : selectors) {
			if (!selector.select(variante)) {
				return false;
			}
		}
		return true;
	}
}
