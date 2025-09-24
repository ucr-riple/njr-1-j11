package praxis;

import java.util.Comparator;

/**
 *
 * @author Tim
 * 
 * Diese Klasse wird zum vergleichen von Patienten genutzt um sie, je nachdem ob sie Privat versichert sind oder nicht
 * zu bevorzugen. #wieinecht
 */
public class prioritaet implements Comparator<Patient>{

    

    @Override
    public int compare(Patient o1, Patient o2) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if(o1.isPrivatpatient() == true && o2.isPrivatpatient() == false){
            return -1;
        }
        if(o1.isPrivatpatient() == false && o2.isPrivatpatient() == true){
            return +1;
        }
        if(o1.isPrivatpatient() == true && o2.isPrivatpatient() == true){
            return 0;
        }
        if(o1.isPrivatpatient() == false && o2.isPrivatpatient() == false){
            return 0;
        }else{
            return 0;
        }
        
    }
    
}
