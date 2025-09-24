package drivenow.interfaces;

/**
 * 
 * Interface fuer die Verwaltung von Drivenow.
 * 
 * Dieses Interface darf nicht veraendert werden.
 * 
 * @author daniel
 */
public interface IDrivenow {

	/**
	 * Diese Methode liefert Ihre Gruppennummer (inkl. Tutoriumsnummer) in der
	 * Form "Txx-yy" zurueck.
	 * 
	 * @return Ihre Gruppennummer (inkl. Tutoriumsnummer) in der Form "Txx-yy"
	 */
	public String getGruppenNummer();

	/**
	 * Diese Methode liefert das Objekt der FahrlehrerinBoundary.
	 * 
	 * @return Die IFahrlehrerinBoundary Ihrer Implementierung
	 */
	public IFahrlehrerinBoundary getFahrlehrerinBoundary();

	/**
	 * Diese Methode liefert das Objekt der SekretaerBoundary.
	 * 
	 * @return Die ISekretaerBoundary Ihrer Implementierung
	 */
	public ISekretaerBoundary getSekretaerBoundary();

	/**
	 * Bevor die ersten Daten in das System uebertragen werden, wird diese
	 * Methode ausgefuehrt um das System in einen startbereiten zustand zu
	 * versetzen.
	 */
	public void initialisieren();

	/**
	 * Diese Methode wird vor dem Beenden der Applikation ausgefuehrt. Bei
	 * erneutem Initialisieren duerfen keine Daten eines vorherigen Durchlaufs
	 * mehr vorhanden sein.
	 */
	public void shutDown();

}
