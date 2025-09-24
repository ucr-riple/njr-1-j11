package drivenow.boundaries;

import drivenow.Systemereignis;
import drivenow.interfaces.IFahrlehrerinBoundary;
import drivenow.controls.*;

public class FahrlehrerinBoundary implements IFahrlehrerinBoundary {

	private Ressourcenverwaltung ressourcenverwaltung;
	private Unterrichtsverwaltung unterrichtsverwaltung;
	
	public FahrlehrerinBoundary() {
		ressourcenverwaltung = Ressourcenverwaltung.getInstance();
		unterrichtsverwaltung = Unterrichtsverwaltung.getInstance();
	}
	
	@Override
	public Systemereignis einloggenUndUnterrichtsstundenZurueckgeben(
			String name, String kennzeichen) {
		return ressourcenverwaltung.fahrlehrerin_einloggene_und_unterrichtsstunden_zurueckgeben(name, kennzeichen);
	}

	@Override
	public String unterrichtsstundenZurueckgeben() {
		return unterrichtsverwaltung.unterrichtsstunden_zurueckgeben().getText();
	}

}