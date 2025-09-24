/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1;

import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class EEEA1 {

    /**
     * @param args the command line arguments
     */
    
    public static double fitnessTestO(PokemonTeam sampleMember) {
        sampleMember.modifyPairings();
        double offense = 0.0;
        int sampleSE = sampleMember.getMoveSuperEffectives();
        int sampleR = sampleMember.getMoveResistances();
        int sampleI = sampleMember.getMoveImmunities();
        double immuneO = 0.0;
        if (sampleI == 0) {
            immuneO = (double) (2 + sampleI) / (sampleI + 1);
        }

        if (sampleI > 0) {
            immuneO = (double) (2 + sampleI) / sampleI;
        }
        offense = (double) (sampleSE - sampleR); //- (2+ sampleI/ (double) sampleI ));
        sampleMember.reset();
        return offense - immuneO;

    }
    
    public static double fitnessTestD(PokemonTeam sampleMember, Population generationFoes)
    {
        double defense = 0.0;
        int foeSE =0;
        int foeR = 0;
        int foeI =0;
        double immuneD = 0.0;
        for (int i =0; i<generationFoes.getPopulation().size(); i++)
        {
            PokemonTeam currentFoe = generationFoes.getPopulation().get(i);
            currentFoe.modifyPairings(sampleMember);
            foeSE += currentFoe.getMoveSuperEffectives();
            foeR += currentFoe.getMoveResistances();
            foeI += currentFoe.getMoveImmunities();
            currentFoe.reset();
        }
        
        if (foeI == 0) {
            immuneD = (double) (2 + foeI) / (foeI + 1);
        }
       

        if (foeI > 0) {
            immuneD = (double) (2 + foeI) / foeI;
        }

        defense = (double) (foeR - foeSE);// + (2 + foeI/ (double) foeI ));
        return (defense  + immuneD) * .028;
    }
    
    public static double fitnessTestAll(double o, double d)
    {
        return o + d;
    }
    

    
    
    public static void main(String[] args) {
 
        Population first = new Population(20);
        double peakFitness = -10.0;
        int peakGen =1;
        for (int a =0; a<100; a++)
        {
            //generates comparision teams
            Population generationFoes = new Population(10);
            
            
            //tests population members against all the comparision teams
            for (int i = 0; i < first.getPopulation().size(); i++) {
                PokemonTeam current = first.getPopulation().get(i);
                double o = EEEA1.fitnessTestO(current);
                double d = EEEA1.fitnessTestD(current, generationFoes);
                current.modifyFitness(EEEA1.fitnessTestAll(o, d));
                //current.modifyFitness(o);

            }

            first.sort();

            int counter = a +1;
            PokemonTeam member = first.getPopulation().get(19);
            double fitness =  member.getFitness();
            if (counter%10==0 || counter==1)
            {
                System.out.println("Generation " + counter + ":" + fitness);
            }
            //System.out.println("Generation " + counter+ ":" + fitness);
            if (fitness>peakFitness)
            {
                peakFitness = fitness;
                peakGen = counter;
            }
           
            PokemonTeam member2 = first.getPopulation().get(18);
        
            first.cutWeak2();
            first.assignFitnessRank();
            for (int x = 0; x < 10; x = x + 2) {
                PokemonTeam p1 = first.getPopulation().get(x);
                PokemonTeam p2 = first.getPopulation().get(x + 1);
                first.reproduce(p1, p2, p1.getRankFitness());
            }
            first.clearPop2();
            first.addInChildren();
            first.createNextGen();
            //first.mutate();
        }

    System.out.println("Max fitness at Generation:" + peakGen + " fitness:" + peakFitness);


            
            
            
        }
        
 }
