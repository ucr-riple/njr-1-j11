package praxis;

import java.util.PriorityQueue;

/**
 *
 * @author Tim
 * Diese Klasse repräsentiert das Wartezimmer, sie verwaltet Patienten in einer PriorityQueue die basierend auf der in Klasse prioritaet
 * programierten Regeln Patienten-Objekte bei denen privatpatient auf true steht bevorzugt und an den Anfang der Schlange, aber nie vor einen anderen Privatpatienten
 * der schon in der Schlange ist, stellt. 
 */
public class Wartezimmer {
    PriorityQueue<Patient> wzimmer;
    prioritaet sort = new prioritaet();

    public Wartezimmer() {
        wzimmer = new PriorityQueue<>(sort);
    }
    
    //Ein Pateint wird der Queque hinzugefügt
    public void pluspatient(Patient ppatient){
        wzimmer.add(ppatient);
        System.out.println("Patient " + ppatient.getName() + " sitzt jetzt im Wartezimmer");
    }
    
    //Gibt den Patienten aus der jetzt an der Reihe ist und entfernt ihn aus der Queue
    public Patient nextplease(){
        if(wzimmer.isEmpty()){
         return null;   
        }else{
         return wzimmer.poll();
         
        }
        }
     

    
}

    
    
    
    

