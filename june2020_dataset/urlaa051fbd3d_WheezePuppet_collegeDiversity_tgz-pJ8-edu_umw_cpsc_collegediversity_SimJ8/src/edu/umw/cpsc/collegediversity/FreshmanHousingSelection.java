package edu.umw.cpsc.collegediversity;

import java.util.*;
import sim.util.*;
import ec.util.*;

/**
 * A Singleton class that can {@link #assign} a set of freshman {@link
 * Student}s to their freshman dorms. 
 * @see UpperclassHousingSelection
 */
public class FreshmanHousingSelection{

    private static FreshmanHousingSelection theInstance;
    private Bag dorms;

    private FreshmanHousingSelection(){

        dorms = new Bag();

        Dorm Alvey = new Dorm("Alvey", false, true, 73);
        Dorm Bushnell = new Dorm("Bushnell", false, true, 76);
        Dorm Jefferson = new Dorm("Jefferson", false, true, 96);
        Dorm Randolph = new Dorm("Randolph", false, true, 93);
        Dorm Russell = new Dorm("Russell", false, true, 87);

        //(actually mixed-year but for simplicity I'm keeping it freshman 
        // only for now)
        Dorm Virginia = new Dorm("Virginia", true, true, 92); 

        dorms.add(Virginia);
        dorms.add(Alvey);
        dorms.add(Bushnell);
        dorms.add(Jefferson);
        dorms.add(Randolph);
        dorms.add(Russell);
    }

    /**
     * Singleton pattern.
     */
    public static synchronized FreshmanHousingSelection instance(){
        if(theInstance==null){
            theInstance=new FreshmanHousingSelection();
        }
        return theInstance;
    }

    /** 
     * Assign the set of (presumably freshman, but this is not checked)
     * students passed to their freshman dorm rooms, in a way that does not
     * take race into account. (compare {@link #assignByRace}.) As a precursor
     * (side effect) to this, any freshmen already existing in freshmen dorms
     * will be removed.
     */
    public void assign(Bag newFreshmen) {
        emptyDorms();
        for(int x=0;x<newFreshmen.size();x++){
            Student s = (Student) newFreshmen.get(x);
            for(int y=0; y<dorms.size();y++){
                if(s.hasRoom()){break;}
                Dorm d = (Dorm) dorms.get(y);
                if(d.isFull()){
                    continue;
                }
                if(d.isFemaleOnly() && s.getGender()==Student.Gender.FEMALE){
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

        //Error testing to see if there are freshmen who don't have a 
        //  room/dorms that aren't full
        for(int x=0;x<newFreshmen.size();x++){
            Student s = (Student) newFreshmen.get(x);
            if(!s.hasRoom()){
                System.out.println(s + " has no freshman dorm room!");
            }
        }
    }


    /** 
     * Assign the set of (presumably freshman, but this is not checked)
     * students passed to their freshman dorm rooms, in a way that
     * <i>does</i> take race into account. (compare {@link #assign}.)
     * Minorities will have a higher probability of rooming with other
     * minorities than they would otherwise have had. As a precursor (side 
     * effect) to this, any freshmen already existing in freshmen dorms
     * will be removed.
     */
    public void assignByRace(Bag newFreshmen) {
        emptyDorms();
        for(int x=0;x<newFreshmen.size();x++){
            boolean foundRoomie=false;
            Student s = (Student) newFreshmen.get(x);
            //System.out.println("Assigning student "+s.getId());
            if(s.hasRoom()){continue;}
            if(s.getRace() == Student.Race.MINORITY){
                double rollDice=Sim.instance().random.nextDouble();
                if(rollDice<Sim.PROB_DUAL_MINORITY){
                    for(int m=0;m<newFreshmen.size();m++){
                        Student r = (Student) newFreshmen.get(m);
                        if(r.getId()==s.getId()){continue;}
                        // if the potential roommate is the same gender, is a 
                        // minority, and has no room
                        if(r.getRace() == Student.Race.MINORITY && 
                            r.getGender()==s.getGender() && 
                            !(r.hasRoom()) && !(s.hasRoom())){

                            //find a room that is completely empty
                            foundRoomie=true;
                            for(int y=0; y<dorms.size();y++){
                                Dorm d = (Dorm) dorms.get(y);
                                if(d.isFull()){
                                    continue;
                                }
                                if(d.isFemaleOnly() && 
                                    s.getGender()== Student.Gender.MALE){
                                    continue;
                                }
                                int i = 0;
                                while(i<d.getNumRooms()){
                                    if(s.hasRoom() && r.hasRoom()){break;}
                                    try{
                                        if(d.getRoomByIndex(i).isEmpty()){
                                            try{d.getRoomByIndex(i).addResidents(s,r);}
                                            catch(IllegalArgumentException e)
                                                {System.out.println(e);}
                                        }
                                    }catch(IllegalArgumentException e){
                                        e.printStackTrace();
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
        // for(int x=0;x<newFreshmen.size();x++){
        // Student s = (Student) newFreshmen.get(x);
        // if(!(s.hasRoom())){
        //System.out.println("After assigning, student "+s.getId()+" has no dorm");
        // }
        // }

    }

    private void assignStudent(Student s) {
        for(int y=0; y<dorms.size();y++){
            if(s.hasRoom()){break;}
            Dorm d = (Dorm) dorms.get(y);
            if(d.isFull()){
                //System.out.println(d.name+" is full!");
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


    private void emptyDorms(){
        for(int y=0; y<dorms.size();y++){
            Dorm d = (Dorm) dorms.get(y);
            d.emptyAllRooms();
        }
    }


}
