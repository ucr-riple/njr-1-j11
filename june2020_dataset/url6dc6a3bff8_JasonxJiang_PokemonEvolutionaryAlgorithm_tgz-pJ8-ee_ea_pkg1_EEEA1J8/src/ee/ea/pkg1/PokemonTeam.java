/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1;

import ee.ea.pkg1.types.Type;
import ee.ea.pkg1.pokemon.DeoxysA;
import ee.ea.pkg1.pokemon.GiratinaO;
import ee.ea.pkg1.pokemon.Groundon;
import ee.ea.pkg1.pokemon.Kyogre;
import ee.ea.pkg1.pokemon.KyuremW;
import ee.ea.pkg1.pokemon.Mewtwo;
import ee.ea.pkg1.pokemon.Rayquaza;
import ee.ea.pkg1.pokemon.ShayminS;
import ee.ea.pkg1.pokemon.Victini;
import ee.ea.pkg1.types.Bug;
import ee.ea.pkg1.types.Dark;
import ee.ea.pkg1.types.Dragon;
import ee.ea.pkg1.types.Electric;
import ee.ea.pkg1.types.Fighting;
import ee.ea.pkg1.types.Fire;
import ee.ea.pkg1.types.Flying;
import ee.ea.pkg1.types.Ghost;
import ee.ea.pkg1.types.Grass;
import ee.ea.pkg1.types.Ground;
import ee.ea.pkg1.types.Ice;
import ee.ea.pkg1.types.None;
import ee.ea.pkg1.types.Normal;
import ee.ea.pkg1.types.Poison;
import ee.ea.pkg1.types.Psychic;
import ee.ea.pkg1.types.Rock;
import ee.ea.pkg1.types.Steel;
import ee.ea.pkg1.types.Water;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Jason
 */
public class PokemonTeam {
    private ArrayList<Pokemon> team = new ArrayList<Pokemon>();
    private static HashMap<String, Pokemon> pokeLibrary;
    private ArrayList<String> pokeKeys = new ArrayList<String>();
    // NOTE the values are the team's offensive potential and assumes the team is attacking not another provided team parameter
    private int moveResistances;
    private int moveNeutrals; // most likely will not be using this feature
    private int moveSuperEffectives;
    private int moveImmunities;
    private int fitnessRank;
    private double fitness;
    private ArrayList<String> circumvent = new ArrayList<String>();
    public PokemonTeam()
    {
        pokeKeys.add("victini");
        pokeKeys.add("groundon");
        pokeKeys.add("deoxys a");
        pokeKeys.add("giratina o");
        pokeKeys.add("kyogre");
        pokeKeys.add("kyurem w");
        pokeKeys.add("mewtwo");
        pokeKeys.add("rayquaza");
        pokeKeys.add("shaymin s");
        moveResistances =0;
        moveNeutrals = 0;
        moveSuperEffectives = 0;
        moveImmunities =0;
        fitness = 0.0;
        fitnessRank = 0;
        for (int i = 0; i<6; i++)
        {
            
            int randomPokeKey = (int) (Math.random() * pokeKeys.size());
            String currentKey = pokeKeys.get(randomPokeKey);
            while (circumvent.contains(currentKey))
            {
                int randomIndex = (int) (Math.random() * pokeKeys.size());
                currentKey = pokeKeys.get(randomIndex);
            }
            Pokemon member = PokemonTeam.pokeLibrary().get(currentKey);
            team.add(member);
            circumvent.add(currentKey);
            
        }
    }
    
    public Pokemon getMember(int index)
    {
        return team.get(index);
    }
    
    
    public int getMoveResistances()
    {
        return moveResistances;
    }
    
    public int getMoveNeutrals()
    {
        return moveNeutrals;
    }
    
    public int getMoveSuperEffectives()
    {
        return moveSuperEffectives;
    }
    
    public int getMoveImmunities()
    {
        return moveImmunities;
    }
    
    public double getFitness()
    {
        return fitness;
    }
    
    public int getRankFitness()
    {
        return fitnessRank;
    }
    
    public ArrayList<Pokemon> getTeam()
    {
        return team;
    }
    
    public void modifyMoveResistances(int added)
    {
        moveResistances += added;
    }
    
    public void modifyMoveNeutrals(int added)
    {
        moveNeutrals += added;
    }
            
    public void modifyMoveSuperEffectives(int added)
    {
        moveSuperEffectives += added;
    }
    
    public void modifyMoveImmunities(int added)
    {
        moveImmunities += added;
    }
    
    public void modifyFitness (double changed)
    {
        fitness = changed;
    }
    
    public void modifyPairings()
    {
        String moveKey="";
        ArrayList<Type> all = new ArrayList<Type>();
        Bug bug = new Bug();
        all.add(bug);
        all.add(new Dark());
        all.add(new Dragon());
        all.add(new Electric());
        all.add(new Fighting());
        all.add(new Fire());
        all.add(new Flying());
        all.add(new Ghost());
        all.add(new Grass());
        all.add(new Ground());
        all.add(new Ice());
        all.add(new Normal());
        all.add(new Poison());
        all.add(new Psychic());
        all.add(new Rock());
        all.add(new Steel());
        all.add(new Water());
        None none = new None();
        for (int i =0; i< 6; i++)
        {
            Pokemon sampleMember = team.get(i);
            for (int x=0; x<4; x++)
            {
                moveKey = sampleMember.getCircumvent().get(x);
                for (int y =0; y<all.size(); y++)
                {
                    Type comparedTo1 = all.get(y);
                    Type comparedTo2 = none;
                    if (sampleMember.getMove(moveKey) instanceof SpecifiedPhysical) {
                        SpecifiedPhysical popMove = (SpecifiedPhysical) sampleMember.getMove(moveKey);
                        int popMoveR = popMove.getType().resistedCounter(comparedTo1, comparedTo2);
                        int popMoveSE = popMove.getType().superEffectiveCounter(comparedTo1, comparedTo2);
                        int popMoveI = popMove.getType().immuneCounter(comparedTo1, comparedTo2);
                        moveSuperEffectives += popMoveSE;
                        moveResistances += popMoveR;
                        moveImmunities += popMoveI;
                    }
                    
                    if (sampleMember.getMove(moveKey) instanceof SpecifiedSpecial) {
                        SpecifiedSpecial popMove = (SpecifiedSpecial) sampleMember.getMove(moveKey);
                        int popMoveR = popMove.getType().resistedCounter(comparedTo1, comparedTo2);
                        int popMoveSE = popMove.getType().superEffectiveCounter(comparedTo1, comparedTo2);
                        int popMoveI = popMove.getType().immuneCounter(comparedTo1, comparedTo2);
                        moveSuperEffectives += popMoveSE;
                        moveResistances += popMoveR;
                        moveImmunities += popMoveI;
                    }
                    
                }
            }
            
        }
    }
    
    public void modifyPairings(PokemonTeam other)
    {
        String moveKey = "";
        for (int i = 0; i < 6; i++) {
            Pokemon sampleMember = team.get(i);

            for (int y = 0; y < 6; y++) {
                Pokemon comparedTo = other.getTeam().get(y);

                for (int x = 0; x < 4; x++) {
                    // Reemember to include netural hits very important. Although there is no array
                    // logically if immune resisted or super effective are not an option only neutral is left so it is possible to include neutral counters
                    moveKey = sampleMember.getCircumvent().get(x); //this returns a string that can be used to access a move within the hashmap of a pokemon's move
                    Type comparedTo1 = comparedTo.getType1(); //this will get the primary type of the pokemon NOT THE STRING
                    Type comparedTo2 = comparedTo.getType2(); // this will get the secondary type of the pokemon NOT THE STRING 
                     //this calls upon the move and assings it a name for easy access
                    if (sampleMember.getMove(moveKey) instanceof SpecifiedPhysical)
                    {
                        SpecifiedPhysical popMove = (SpecifiedPhysical) sampleMember.getMove(moveKey);
                        int popMoveR = popMove.getType().resistedCounter(comparedTo1, comparedTo2);
                        int popMoveSE = popMove.getType().superEffectiveCounter(comparedTo1, comparedTo2);
                        int popMoveI = popMove.getType().immuneCounter(comparedTo1, comparedTo2);
                        moveSuperEffectives += popMoveSE;
                        moveResistances += popMoveR;
                        moveImmunities += popMoveI;
                    }
                    
                    if (sampleMember.getMove(moveKey) instanceof SpecifiedSpecial)
                    {
                        SpecifiedSpecial popMove = (SpecifiedSpecial) sampleMember.getMove(moveKey);
                        int popMoveR = popMove.getType().resistedCounter(comparedTo1, comparedTo2);
                        int popMoveSE = popMove.getType().superEffectiveCounter(comparedTo1, comparedTo2);
                        int popMoveI = popMove.getType().immuneCounter(comparedTo1, comparedTo2);
                        moveSuperEffectives += popMoveSE;
                        moveResistances += popMoveR;
                        moveImmunities += popMoveI;
                    }
                    
                   
                }
            }
        }
    }   
    
    
    
    public void reset()
    {
        moveSuperEffectives =0;
        moveResistances =0;
        moveNeutrals =0;
        moveImmunities =0;
    }
    
    public void modifyFitnessRank(int modify)
    {
        fitnessRank = modify;
    }
    
    public void removeAll()
    {
        for (int i =0; i<team.size(); i ++)
        {
            team.remove(0);
        }
    }
    
    public void removeAll2()
    {
        while(team.size()>0)
        {
            team.remove(0);
        }
    }
    
    public static HashMap<String, Pokemon> pokeLibrary()
    {
        if(pokeLibrary == null)
        {
            Pokemon p1 = new Victini();
            Pokemon p2 = new Groundon();
            Pokemon p3 = new DeoxysA();
            Pokemon p4 = new GiratinaO();
            Pokemon p5 = new Kyogre();
            Pokemon p6 = new KyuremW();
            Pokemon p7 = new Mewtwo();
            Pokemon p8 = new Rayquaza();
            Pokemon p9 = new ShayminS();
            pokeLibrary = new HashMap<String, Pokemon>();
            pokeLibrary.put("victini", p1);
            pokeLibrary.put("groundon", p2);
            pokeLibrary.put("deoxys a", p3);
            pokeLibrary.put("giratina o", p4);
            pokeLibrary.put("kyogre", p5);
            pokeLibrary.put("kyurem w", p6);
            pokeLibrary.put("mewtwo", p7);
            pokeLibrary.put("rayquaza", p8);
            pokeLibrary.put("shaymin s", p9);
                    
        }
        
        return pokeLibrary;
        
    }
}


