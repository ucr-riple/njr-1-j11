import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Terminvorschlag implements Serializable {
	private static final long serialVersionUID = 1L;

	private Termin termin;
	private Termine target;
	private List<Mitglied> offen;

	public Terminvorschlag(Termin termin, Termine target) {
		this.termin = termin;
		this.target = target;
		this.offen = new ArrayList<Mitglied>(termin.getTeilnehmer());
	}

	public Termin getTermin() {
		return termin;
	}

	public void accept(Mitglied mitglied) {
		offen.remove(mitglied);
		if (accepted()) {
			target.add(this);
		}
	}

	public void decline(Mitglied mitglied, String nachricht) {
		if (termin.getTeilnehmer().contains(mitglied)) {
			for (Mitglied m : termin.getTeilnehmer()) {
				m.revidiere(this);
				if(m != mitglied) {
					m.sende(mitglied + ": " + nachricht + " - " + termin);
				}
			}
		}
	}

	public boolean accepted() {
		return offen.isEmpty();
	}

	public String toString() {
		return termin.toString();
	}

	public String toDetailString() {
		return termin.toDetailString() + ", Ausstehend: " + offen;
	}
}
