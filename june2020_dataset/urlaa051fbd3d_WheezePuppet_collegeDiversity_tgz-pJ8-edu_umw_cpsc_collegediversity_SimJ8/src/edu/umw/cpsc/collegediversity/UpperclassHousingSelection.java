package edu.umw.cpsc.collegediversity;

import java.util.*;
import sim.util.*;
import ec.util.*;


/**
 * A Singleton class that can {@link #assign} a set of {@link
 * Student}s to their non-freshmen dorms. The students in question are all
 * those <i>except</i> incoming freshmen. 
 * @see FreshmanHousingSelection
 */
public class UpperclassHousingSelection{

    private Bag dorms;
    private static UpperclassHousingSelection theInstance;

    private UpperclassHousingSelection(){

        dorms = new Bag();

        //actually mixed-year but for simplicity I'm keeping it 
        // upperclassman only for now
        Dorm Arrington = new Dorm("Arrington", false, false, 74);

        Dorm Ball = new Dorm("Ball", true, false, 53);
        Dorm Custis = new Dorm("Custis", false, false, 21);
        Dorm EagleLanding = new Dorm ("Eagle Landing", false, false, 312);
        Dorm Framar = new Dorm("Framar", false, false, 11);
        Dorm Madison = new Dorm("Madison", false, false, 21);
        Dorm Marshall = new Dorm("Marshall", false, false, 74); 
        Dorm Mason = new Dorm("Mason", false, false, 93); 
        Dorm Westmoreland = new Dorm("Westmoreland", false, false, 56); 

        //Willard houses students in single rooms which is not accounted for yet
        Dorm Willard = new Dorm("Willard", false, false, 91); 

        //100 of the rooms in the aparments are singles which is not 
        // accounted for yet
        Dorm Apartments = new Dorm("Apartments", false, false, 381); 

        //for extraneous students who commute or live off campus
        Dorm Offcampus = new Dorm ("Offcampus", false, false, 3500); 

        dorms.add(Ball);
        dorms.add(Arrington);
        dorms.add(Custis);
        dorms.add(Framar);
        dorms.add(Madison);
        dorms.add(Mason);
        dorms.add(Westmoreland);
        dorms.add(Willard);
        dorms.add(Apartments);
        dorms.add(EagleLanding);
        dorms.add(Offcampus);
    }

    /**
     * Singleton pattern.
     */
    public static UpperclassHousingSelection instance(){
        if(theInstance==null){
            theInstance=new UpperclassHousingSelection();
        }
        return theInstance;
    }


    /** 
     * Assign the set of students (presumably non-incoming-freshmen, but
     * this is not checked) passed to their dorm rooms, in a way that does 
     * not take race into account. (compare {@link #assignByRace}.) As a 
     * precursor (side effect) to this, any students already existing in 
     * upperclass dorms will be removed.
     */
    public void assign(Bag students){

        emptyDorms();

        // Purge all freshmen. (We're not assigning those.)
        Bag upperclassmen = null;
        try { 
            upperclassmen = (Bag) students.clone(); 
        } catch (Exception e) { 
            e.printStackTrace(); 
            System.exit(1); 
        }
        for(int x=upperclassmen.size()-1; x>=0; x--){
            Student s = (Student) upperclassmen.get(x);
            if (s.getGrade() < 2) {
                upperclassmen.remove(s);
            }
        }
        
        for(int x=0;x<upperclassmen.size();x++){
            Student s = (Student) upperclassmen.get(x);
            if (s.getGrade() < 2) {
                // We're only assigning upperclassmen here.
                continue;
            }
            s.leaveRoom();
            for(int y=0; y<dorms.size();y++){
                if(s.hasRoom()){break;}
                Dorm d = (Dorm) dorms.get(y);
                if(d.isFull()){
                    continue;
                }
                if(d.isFemaleOnly() && s.getGender()==Student.Gender.MALE){
                    continue;
                }
                int i = 0;
                while(i<d.getNumRooms()){
                    if(s.hasRoom()){break;}
                    try{
                        d.getRoomByIndex(i).addResident(s);
                    }catch(IllegalArgumentException e){//System.out.println(e);
                    }
                    i++;
                }
            }
        }

        //Error testing to see if there are upperclassmen who don't have a 
        //  room/dorms that aren't full
        // int q=0;
        for(int x=0;x<upperclassmen.size();x++){
            Student s = (Student) upperclassmen.get(x);
            if(!s.hasRoom()){
                System.out.println(s + " has no upperclass dorm room!");
            }
        }
    }

    /** 
     * Assign the set of students (presumably non-incoming-freshmen, but 
     * this is not checked) students passed to their dorm rooms, in a way 
     * that <i>does</i> take race into account. (compare {@link #assign}.)
     * Minorities will have a higher probability of rooming with other
     * minorities than they would otherwise have had. As a precursor (side 
     * effect) to this, any students already existing in upperclass dorms
     * will be removed.
     */
    public void assignByRace(Bag upperclassmen) {
        emptyDorms();
        boolean foundRoomie;
        for(int x=0;x<upperclassmen.size();x++){
            foundRoomie=false;
            Student s = (Student) upperclassmen.get(x);
            //need to leave current room so that they can get a new room
            s.leaveRoom();
            if(s.getRace() == Student.Race.MINORITY){
                double rollDice=Sim.instance().random.nextDouble();
                if(rollDice<Sim.PROB_DUAL_MINORITY){
                    for(int m=0;m<upperclassmen.size();m++){
                        Student r = (Student) upperclassmen.get(m);
                        // if the potential roommate is the same gender, is a 
                        //  minority, and has no room
                        if(r.getId()==s.getId()){continue;}
                        if(r.getRace()==Student.Race.MINORITY &&
                            r.getGender()==s.getGender() &&
                            !(r.hasRoom())&&!(s.hasRoom())){
                            //find a room that is completely empty
                            foundRoomie=true;
                            for(int y=0; y<dorms.size();y++){
                                Dorm d = (Dorm) dorms.get(y);
                                if(d.isFull()){
                                    continue;
                                }
                                if(d.isFemaleOnly() && 
                                    s.getGender()==Student.Gender.MALE){
                                    continue;
                                }
                                int i = 0;
                                while(i<d.getNumRooms()){
                                    if(s.hasRoom()&&r.hasRoom()){break;}
                                    try{
                                        if(d.getRoomByIndex(i).isEmpty()){
                                            try{d.getRoomByIndex(i).addResidents(s,r);}
                                                catch(IllegalArgumentException 
                                                e ){System.out.println(e);}
                                        }
                                    }catch(IllegalArgumentException e){
                                    }
                                    i++;
                                }
                            }
                            if(!s.hasRoom()){
                                //no empty room has been found to put these 
                                //students in; just assign them normally
                                assignStudent(s);
                            }
                            if(!r.hasRoom()){
                                assignStudent(r);
                            }
                        }
                    }
                    if(foundRoomie==false){
                        //no same-minority, same-sex, roomless roommate found
                        assignStudent(s);
                    }
                }else{
                    //dice roll not under PROB_DUAL_MINORITY
                    assignStudent(s);
                }

            }else{
                //not a minority, assign normally
                assignStudent(s);
            }
        }

    }


    private void assignStudent(Student s) {
        for(int y=0; y<dorms.size();y++){
            if(s.hasRoom()){break;}
            Dorm d = (Dorm) dorms.get(y);
            if(d.isFull()){
                continue;
            }
            if(d.isFemaleOnly() && s.getGender()==Student.Gender.MALE){
                continue;
            }
            int i = 0;
            while(i<d.getNumRooms()){
                if(s.hasRoom()){break;}
                try{
                    d.getRoomByIndex(i).addResident(s);
                }catch(IllegalArgumentException e){
                }
                i++;

            }
        }
    }

    private void emptyDorms(){
        for(int y=0; y<dorms.size();y++){
            Dorm d = (Dorm) dorms.get(y);
            d.emptyAllRooms();
        }
    }

}
