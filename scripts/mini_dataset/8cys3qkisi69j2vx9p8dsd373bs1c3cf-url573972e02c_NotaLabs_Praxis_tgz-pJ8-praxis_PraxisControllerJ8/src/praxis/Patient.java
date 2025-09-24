package praxis;


import java.util.Random;


/**
 *
 * @author Tim
 * Diese Klasse repräsentiert einen einzelnen Patienten
 * Ein Patient kann einen Namen haben und privatversichert sein
 */
public class Patient {
   
   private String name;
   private boolean privatpatient;

    public Patient() {
        namegenerator nameg = new namegenerator();
        name = nameg.gibname();
        privatpatient = new Random().nextInt(8)==0;
        System.out.println("Patient erstellt // Name: "+ name + " Privatpatient: "+ privatpatient);
    }
    
    public Patient(String pname,boolean pprivat){
        name = pname;
        privatpatient = pprivat;
        
    }
    
    void ausgabe() {
        System.out.println("Patientendaten// Name: "+ name + " Privatpatient: "+ privatpatient);
    }
    
    public String getName() {
        return name;
    }

    public boolean isPrivatpatient() {
        return privatpatient;
    }

    
   
   
}
