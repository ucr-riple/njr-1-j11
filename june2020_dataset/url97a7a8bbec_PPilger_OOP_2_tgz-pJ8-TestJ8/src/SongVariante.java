/**
 * Repraesentiert einen Song in einer bestimmten Variante
 * 
 * @author Peter Pilgerstorfer
 * 
 */
public class SongVariante {
	private Song song;
	private Variante variante;

	public SongVariante(Song song, Variante variante) {
		this.song = song;
		this.variante = variante;
	}

	public Song getSong() {
		return song;
	}

	public Variante getVariante() {
		return variante;
	}

	public String toString() {
		return song + " (" + variante + ")";
	}
}
