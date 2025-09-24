package praxis;

/**
 *
 * @author Tim
 */
public class PraxisController {

    /**
     * @param args the command line arguments
     * Generiert 30 Patienten und fügt sie einzeln der Warteschlange hinzu um zu testen ob Privatpatienten bevorzugt werden und die programierte Regel funktioniert
     */
    public static void main(String[] args) {
        Wartezimmer zimmer = new Wartezimmer();
        for(int i = 0;i<30;i++){
        Patient pt = new Patient();
        zimmer.pluspatient(pt);
        }
        
        for(int i = 0;i<30;i++){
        zimmer.nextplease().ausgabe();
        }
        
        
        
    }
    
}
