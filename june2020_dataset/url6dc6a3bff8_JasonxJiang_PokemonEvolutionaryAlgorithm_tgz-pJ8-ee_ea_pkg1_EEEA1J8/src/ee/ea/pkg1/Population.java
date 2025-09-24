/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1;

import ee.ea.pkg1.pokemon.DeoxysA;
import ee.ea.pkg1.pokemon.GiratinaO;
import ee.ea.pkg1.pokemon.Groundon;
import ee.ea.pkg1.pokemon.Kyogre;
import ee.ea.pkg1.pokemon.KyuremW;
import ee.ea.pkg1.pokemon.Mewtwo;
import ee.ea.pkg1.pokemon.Rayquaza;
import ee.ea.pkg1.pokemon.ShayminS;
import ee.ea.pkg1.pokemon.Victini;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class Population {
    
    private ArrayList<PokemonTeam> population = new ArrayList<PokemonTeam>();
    private ArrayList<PokemonTeam> children = new ArrayList<PokemonTeam>();
    
    public Population(int starting)
    {
        for (int i =0; i< starting; i++)
        {
            PokemonTeam addition = new PokemonTeam();
            population.add(addition);
        }
    }
    
    public ArrayList<PokemonTeam> getPopulation()
    {
        return population;
    }
    
    public void makeEven() // assumes team is already sorted based on fitness
    {
        if (population.size()%2 ==1)
        {
            population.remove(0);
        }
    }
    
 
    
    public void setNew(Population nextGen)
    {
        population = nextGen.getPopulation();
    }
    
    /* DO NOT USE THIS FUNCTION LOOK BACK ON THIS AND UNDERSTAND WHY DOESN'T IT WORK
    public void sortMembers() {
        for (int i = 0; i < population.size() - 1; i++) {
            double indexFit = population.get(i).getFitness();
            for (int x = i + 1; x < population.size(); x++) {
                double nextPosFit = population.get(x).getFitness();
                if (nextPosFit < indexFit) {
                    PokemonTeam placeholder = population.get(i);
                    population.set(i, population.get(x));
                    population.set(x, placeholder);
                }
            }
        }
    }
    */
    public void sort()
    {
        for (int i =0; i< population.size()-1; i++)
        {
            int min = i;
            for (int x = i +1; x<population.size(); x++)
            {
                if (population.get(x).getFitness() < population.get(min).getFitness())
                {
                    PokemonTeam temp = population.get(i);
                    population.set(i, population.get(x));
                    population.set(x, temp);
                }
            }
        }
    }
    /*void selectionSort(int[] ar){
   for (int i = 0; i ‹ ar.length-1; i++)
   {
      int min = i;
      for (int j = i+1; j ‹ ar.length; j++)
            if (ar[j] ‹ ar[min]) min = j;
      int temp = ar[i];
      ar[i] = ar[min];
      ar[min] = temp;
} }*/
    //This one does not work for some reason
    public void cutWeak() // MUST CALL FUNCTION TO ORGANIZE THE TEAMS FIRST and must cut teams down to even first
    {
        for (int i =0; i<population.size()/2; i++)
        {
            population.remove(0);
        }
    }
    
    public void cutWeak2()
    {
        while(population.size()>10)
        {
            population.remove(0);
        }
    }
    
    public void mutate()
    {
        double mutateRate =.1;
        
        ArrayList<Pokemon> mutated = new ArrayList<Pokemon>();
        mutated.add(new DeoxysA());
        mutated.add(new GiratinaO());
        mutated.add(new Groundon());
        mutated.add(new Kyogre());
        mutated.add(new KyuremW());
        mutated.add(new Mewtwo());
        mutated.add(new Rayquaza());
        mutated.add(new ShayminS());
        mutated.add(new Victini());
        for (int i =0; i<20; i++)
        {
            
            double random = Math.random();
            if (random < mutateRate) {
                PokemonTeam member = population.get(i);
                int randomIndex = (int) (Math.random() * 6);
                int randomMutated = (int) (Math.random() * mutated.size());
                member.getTeam().remove(randomIndex);
                member.getTeam().add(mutated.get(randomMutated));

            }
        }
        
    }
    
    public void reproduce0(PokemonTeam parent1, PokemonTeam parent2, int rankFit)
    {
        for (int i =0; i<rankFit; i++)
        {
            PokemonTeam child = new PokemonTeam();
            child.removeAll2();
            for (int x=0; x<6; x++)
            {
                int random = (int) (Math.random() *2);
                if (random==0)
                {
                    child.getTeam().add(parent1.getTeam().get(x));
                }
                
                if (random==1)
                {
                    child.getTeam().add(parent2.getTeam().get(x));
                }
            }
            children.add(child);
        }
    }
    
    // Makes Children proportional to rank fit
    public void reproduce(PokemonTeam parent1, PokemonTeam parent2, int rankFit)
    {

        ArrayList<Pokemon> totalPokes = new ArrayList<Pokemon>();
        for (int y =0; y< rankFit; y ++)
        {
            PokemonTeam child = new PokemonTeam();
            child.removeAll2();
            ArrayList<String> circumvent = new ArrayList<String>();
            ArrayList<String> pokeKeys = new ArrayList<String>();
            for (int i = 0; i < 6; i++) {
                totalPokes.add(parent1.getMember(i));
                totalPokes.add(parent2.getMember(i));
                pokeKeys.add(parent1.getMember(i).showName());
                pokeKeys.add(parent2.getMember(i).showName());

            }


            for (int x = 0; x < 6; x++) {
                int randomPokeKey = (int) (Math.random() * pokeKeys.size());
                String currentKey = pokeKeys.get(randomPokeKey);
                while (circumvent.contains(currentKey)) {
                    randomPokeKey = (int) (Math.random() * pokeKeys.size());
                    currentKey = pokeKeys.get(randomPokeKey);
                }
                Pokemon member = totalPokes.get(randomPokeKey);
                circumvent.add(currentKey);
                child.getTeam().add(member);
            }

            children.add(child);
        }
  
    }
    
    public ArrayList<PokemonTeam> getCMember()
    {
        return children;
    }
    
    public void assignFitnessRank() //assumes 10 fit teams left out of 20
    {
        for (int i = 0; i< 10; i++)
        {
            if (i>=0 && i<=3)
            {
                population.get(i).modifyFitnessRank(1);
            }
            
            if (i>3 || i<=7)
            {
                population.get(i).modifyFitnessRank(2);
            }
            
            if (i>7)
            {
                population.get(i).modifyFitnessRank(2);
            }
        }
    }
    
    public void clearPop()
    {
        for (int i =0; i< population.size(); i++)
        {
            population.remove(0);
        }
    }
    
    public void clearPop2()
    {
        while (population.size()>0)
        {
            population.remove(0);
        }
    }
    
    public void addInChildren()
    {
        for (int i =0; i<children.size(); i ++)
        {
            population.add(children.get(i));
        }
    }
    
    public void createNextGen()
    {
       while (population.size()<20)
       {
           PokemonTeam newTeam = new PokemonTeam();
           population.add(newTeam);
       }
    }
}

