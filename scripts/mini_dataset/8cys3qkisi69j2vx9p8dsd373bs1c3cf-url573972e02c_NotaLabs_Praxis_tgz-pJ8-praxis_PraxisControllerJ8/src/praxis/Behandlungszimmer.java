package praxis;



/**
 *
 * @author Tim
 * 
 */
public class Behandlungszimmer {
    
    Patient patient;

    public Behandlungszimmer() {
        
    }
    
    public void behandeln(Patient ppatient){
            patient = ppatient;
    
    }
    
    public void fertig(){
    patient = null;    
    
    }
    
    
}
