/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praxis;

import java.util.Random;

/**
 *
 * @author Tim
 * Diese Klasse generiert zufällige Namen für Patienten
 */
public class namegenerator {
    
    private String[] vornamen = {"Hans","Peter","Elias","Daniel","Luca","Jan","Andreas","Emil","Adrian","Finn","Peter","Johannes","Phillip","Barack","John","Robin","Emily","Melina","Marie","Nina","Johanna","Oonagh","Anna","Emma","Mia"};
    private String[] nachnamen = {"Lannister","Targaryen","Snow","Baelish","Tyrell","Seaworth","Baratheon","Stark","Tarly","Gontrum","Beuse","Hecker","Bauer","Westenhoff","Löbig","Voß","Böckmann","Aubke","Beckerbauer","Meinholz","Kuryman","Schlegemeister","Mesterhaka","Onpfa","Kapfherer","Degtelkimire","Weserfla"};

    public namegenerator() {
        
    }
    
    public String gibname(){
        
        String name = vornamen[new Random().nextInt(vornamen.length)] + " " +nachnamen[new Random().nextInt(nachnamen.length)] ;
        return name;
    }
    
    
    
}
