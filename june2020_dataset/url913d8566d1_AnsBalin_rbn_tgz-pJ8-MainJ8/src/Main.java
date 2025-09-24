
import java.util.*;
import java.io.*;
import java.sql.Time;

public class Main {
  
  private ArrayList<Network> library = new ArrayList<Network>();
  private ArrayList<Molecule> bucket = new ArrayList<Molecule>();
  private ArrayList<Reaction> reactions = new ArrayList<Reaction>();
  private int numReactions;
  private static int catalysisProductIndex=-1;
  private static boolean foundCatalysisProduct  = false;
  public static double temperature = 1;
  private static boolean reuse = false;
  private static String path = "/data/library/";
  private static String suffix = "";
  
  
  private int product;
  
  // Constructor adds null network to library, with index 0
  public Main(int numSpecies, int numNodes, int numBondingSites, int numMolecules){
    
    System.out.printf("\n\tPopulating library...\n");
    populateLibrary(numSpecies, numNodes, numBondingSites);
    System.out.printf("\n\tLibrary successfully populated with:\n\t\t%d Species\n\t\t%d nodes each\n\t\t%d bonding sites each", numSpecies, numNodes, numBondingSites);
    
    System.out.printf("\n\n\tPopulating world...\n");
    populateWorld(numMolecules, numSpecies);
    System.out.printf("\n\tWorld successfully populated with %d atoms.\n", numMolecules);
    
    numReactions=0;
    stats();
    
  }
  
  public Main(int numSpecies, int A, int B, int C, int numMolecules, double concentration){
    
    ArrayList<Network> temp = new ArrayList<Network>();
    
    for(int i=0; i<numSpecies; i++){
      
      DataOutput in = new DataOutput(path+i+".txt");
      try{
        temp.add(in.readFile());
        temp.get(0).setPop(0);
      }catch(IOException e){
        e.printStackTrace();
      }catch(ClassNotFoundException e){
        e.printStackTrace();
      }
      
    }
    library.add(temp.get(0));
    library.add(temp.get(A));
    library.add(temp.get(B));
    library.add(temp.get(C));
    
    Molecule nullMolecule = new Molecule();
    bucket.add(nullMolecule);

    for(int i =1; i < (int) (numMolecules*(1 - concentration)); i+=2){
      
      bucket.add(new Molecule(1, nullMolecule, nullMolecule));
      library.get(1).incrPop();
      bucket.add(new Molecule(2, nullMolecule, nullMolecule));
      library.get(2).incrPop();
      
      bucket.get(i).calculateMoleculeID(0,0, new int[0]);
      bucket.get(i+1).calculateMoleculeID(0,0, new int[0]);
      
      product = 0;
    
    }
  
    for(int i = (int)(numMolecules*(1 - concentration)); i<numMolecules; i++){
    
      bucket.add(new Molecule(3, nullMolecule, nullMolecule)); 
      library.get(3).incrPop();
      bucket.get(i).calculateMoleculeID(0,0, new int[0]);
      
    }
    
  }
  
  public int getCycleLength(int index){return library.get( bucket.get(index).getID() ).getCycleLength();}
  public double getActivity(int index){return library.get( bucket.get(index).getID() ).getActivity();}
  public int getPopulation(){return bucket.size();}
  public int getNumSpecies(){return library.size();}
  public int getNumReactions(){return numReactions;}
  public ArrayList<Reaction> getReactions(){return reactions;}
  public ArrayList<Network> getLibrary(){return library;}
  public ArrayList<Molecule> getBucket(){return bucket;}
  
  public void populationDistribution(){
    
    int[][] species = new int[library.size()][2];
    for(int i=0; i<bucket.size(); i++){
      
      Molecule M = bucket.get(i);
      species[M.getID()][0] = M.getID();
      species[M.getID()][1]++;
    }
    int k;
    for(int j=0; j<library.size(); j++){
      
      int a = species[j][1];
      k=j;
      while(k>0 && species[k-1][1] > a){
        species[k][1] = species[k-1][1];
        k--;
      }
      species[k][1]=a;
    }
    for(int j=0; j<library.size(); j++){
      
      System.out.printf("%d\n", species[j][1]);
    }
  }
  
  // If generateNew = 0     Generate new species
  // If generateNew != 0    Use species from previous run
  public void populateLibrary(int numSpecies, int numNodes, int numBondingSites){
    
    Molecule nullMolecule = new Molecule();
    
    if(!reuse){
      library.add(new Network(0));
      for(int i=1; i<numSpecies; i++){
        library.add(new Network(numNodes, i, numBondingSites));
        library.get(i).setMol(new Molecule(i, nullMolecule, nullMolecule));
        progress("Adding...", i, numSpecies);
        
      
      }
      progress("Adding...", numSpecies, numSpecies);
    
      for(int i=0; i<numSpecies; i++){
        DataOutput out = new DataOutput("/data/library/"+i+".txt");
        progress("Saving...", i, numSpecies);
        
        try{
          out.writeToFile(library.get(i));
        }catch(IOException e){System.out.println("Could not write file.");}
      }
      progress("Saving...", numSpecies, numSpecies);
      
    }
    else{
      
      for(int i=0; i<numSpecies; i++){
        progress("Retrieving...", i, numSpecies);
        DataOutput in = new DataOutput(path+i+".txt");
        try{
          library.add(in.readFile());
          library.get(0).setPop(0);
        }catch(IOException e){
          e.printStackTrace();
        }catch(ClassNotFoundException e){
          e.printStackTrace();
        }
        
      }
      progress("Retrieving...", numSpecies, numSpecies);
      
    }
    
    
  }
  
  public void populateWorld(int numMolecules, int numSpecies){
    
    int rand;
    
    Molecule nullMolecule = new Molecule();
    bucket.add(nullMolecule);
    
    for(int i=1; i<numMolecules; i++){
      progress("Spawning...", i, numMolecules);
      rand = (int) (Math.random()*(double)(numSpecies-1)) + 1;
      bucket.add(new Molecule(rand , nullMolecule, nullMolecule));
      bucket.get(i).calculateMoleculeID(0,0, new int[0]);
      bucket.get(i).setState(library.get(rand).getState());
      //String str = bucket.get(i).toString();
      //System.out.printf(str);
    }
    
    progress("Spawning...", numMolecules, numMolecules);
  }
  
  public void stats(){
    
    int[] cycleLengths = new int[bucket.size()];
    double[] activities = new double[bucket.size()];
    
    double cycleLengthMean, activityMean;
    int cycleLengthMedian, cycleLengthUQ, cycleLengthLQ;
    double activityMedian, activityUQ, activityLQ, activityVar, activityStdDev, cycleLengthVar, cycleLengthStdDev;
    System.out.printf("\n\t==========================================");
    System.out.printf("\n\t                STATISTICS   ");
    System.out.printf("\n\t==========================================\n");
    System.out.printf("\n\tCalculating averages...\n");
    cycleLengthMean=0;
    activityMean=0;
    for(int i=0; i<bucket.size(); i++){
      
      cycleLengths[i] = getCycleLength(i);
      cycleLengthMean += (double)cycleLengths[i];
      
      activities[i] = getActivity(i);
      activityMean += activities[i];
    }
    
    cycleLengthMean = cycleLengthMean/bucket.size();
    activityMean = activityMean/bucket.size();
    
    System.out.printf("\tSorting arrays...\n");
    Arrays.sort(cycleLengths);
    Arrays.sort(activities);
    
    cycleLengthLQ = cycleLengths[(int) bucket.size()/4];
    cycleLengthMedian = cycleLengths[(int) bucket.size()/2];
    cycleLengthUQ = cycleLengths[(int) 3*bucket.size()/4];
    
    activityLQ = activities[(int) bucket.size()/4];
    activityMedian = activities[(int) bucket.size()/2];
    activityUQ = activities[(int) 3*bucket.size()/4];
    
    
    System.out.printf("\tCalculating variance...");
    
    activityVar=0;
    cycleLengthVar=0;
    cycleLengthStdDev=0;
    activityStdDev=0;
    for(int i=0; i<bucket.size(); i++){
      
      activityVar += (getActivity(i) - activityMean)*(getActivity(i) - activityMean);
      cycleLengthVar += ((double)getCycleLength(i) - cycleLengthMean)*((double)getCycleLength(i) - cycleLengthMean);
    }
    
    activityVar = activityVar/(bucket.size()-1);
    cycleLengthVar = cycleLengthVar/(bucket.size()-1);
    
    activityStdDev = Math.sqrt(activityVar);
    cycleLengthStdDev = Math.sqrt(cycleLengthVar);
    
    
    
    System.out.printf("\n\n\t\t\tCyclelength\tActivity\n");
    System.out.printf("\t==========================================\n");
    System.out.printf("\tMean\t\t%.2f\t\t%.2f\n", cycleLengthMean, activityMean);
    System.out.printf("\tMedian\t\t%d\t\t%.2f\n", cycleLengthMedian, activityMedian);
    System.out.printf("\tUpper Q\t\t%d\t\t%.2f\n", cycleLengthUQ, activityUQ);
    System.out.printf("\tLower Q\t\t%d\t\t%.2f\n", cycleLengthLQ, activityLQ);
    System.out.printf("\tVariance\t%.2f\t\t%.2f\n", cycleLengthVar, activityVar);
    System.out.printf("\tStd. Dev.\t%.2f\t\t%.2f\n\n", cycleLengthStdDev, activityStdDev);
    
    
  }
  
  public int[] molecularSizeDistrb(){
    
    int maxSize=0;
    
    for (int i=0; i<bucket.size(); i++){
      maxSize = (bucket.get(i).getSize() > maxSize)? bucket.get(i).getSize() : maxSize;
    }
    
    int[] sizeDistrb = new int[maxSize+1];
  
    for (int i=1; i<bucket.size(); i++){
    
      sizeDistrb[bucket.get(i).getSize()] += bucket.get(i).getSize();
      
    }
    
    return sizeDistrb;
    
  }
  
  public int[] cycleLengthDistrb(){
    
    int maxCL=0;
    Network A;
    
    for(int i=0; i<bucket.size(); i++){
      A = library.get( bucket.get(i).getID() );
      maxCL = (A.getCycleLength() > maxCL)? A.getCycleLength() : maxCL;
    }
    
    int[] CLDistrb = new int[maxCL+1];
    
    for(int i=0; i<bucket.size(); i++){
      A = library.get( bucket.get(i).getID() );
      
      CLDistrb[A.getCycleLength()]++;
      
    }
    
    return CLDistrb;
    
  }
  
  public void reactionAnalysis(){
    //System.out.printf("%d\n", reactions.size());
    for(int i=0; i<reactions.size(); i++){
      if(reactions.get(i).getCount() > 1){
        System.out.printf("%d\t", reactions.get(i).getCount());
        System.out.printf(""+reactionToString(reactions.get(i))+"\n");
      }
    }  
  }
  
  // selects n random molecules
  public ArrayList<Molecule> selectRandMols(int n){
    ArrayList<Molecule> randMols = new ArrayList<Molecule>();
    int max = bucket.size();
    int prevRand = 0;
    //System.out.printf("Max: %d\n", max);
    int rand;
    for(int i=0; i<n; i++){
      rand =  (int) (Math.random()*(double)max);
      while(rand==0 || rand==prevRand){rand =  (int) (Math.random()*(double)max);}
      randMols.add(bucket.get(rand));
      //System.out.printf("%d\t%d\n", rand, randMols.get(i).getID());
      prevRand = rand;
    }
    
    return randMols;
    
  }
  
  public ArrayList<Network> moleculeArrToNetworkArr(ArrayList<Molecule> molecules){
    
    ArrayList<Network> networks = new ArrayList<Network>();
    
    for(int i=0; i<molecules.size(); i++){
      
      networks.add(retrieve(molecules.get(i)));
    }
    return networks;
  }
  
  
  //Checks to see if Network A is in the library and if it isn't, add's it
  //returns index of where Network A is in the library if/when added
  public int libraryUpdate(Network A, Molecule Asub, Molecule Bsub){
    int index=-1;
    int i = 0;
    while (index==-1 && i<library.size()){
      
      index = (A.equals(library.get(i))) ? i : -1;
      i++;
    }
    
    if(index == -1){
      library.add(A);
      index = library.size()-1;
      library.get(index).setMol(new Molecule( index, Asub, Bsub ) );
    }
    
    return index;
  }
  
  public int reactionsUpdate(Reaction A){
  
    int index=-1;
    int i=0;
    
    while( index==-1 && i<reactions.size() ){
    
      index = ( A.getID().equals( reactions.get(i).getID() ) )? i:-1;
      i++;
      
    }
    
    if(index==-1){
      reactions.add(A);
      index = reactions.size()-1;
    }
    
    A.setIntID(index);
    
    return index;
    
    
  }
  
  public Network retrieve(int NetworkID){
    
    return library.get(NetworkID);
  }
  
  public Network retrieve(Molecule A){
    //System.out.printf("...%d\n", A.getID());
    return retrieve( A.getID() );
  }
  
  public Network bond(Molecule Asub, Molecule Bsub, int bondingSite1, int bondingSite2){
    
    Network netAsub, netBsub, netResult;
    netAsub = retrieve(Asub);
    netBsub = retrieve(Bsub);
    
    netResult = netAsub.bond(netBsub, bondingSite1, bondingSite2);
    return netResult;
    
  }
  
  public String reactionToString(Reaction R){
    
    String str = "";
    
    for(int i=0; i<R.getReactants().length; i++){
      str = str+R.getMolReactants().get(i).toStringf()+" ";
      if(i!=R.getReactants().length-1){
        str = str+"+ ";
      }
    }
    str=str+"---> ";
    for(int i=0; i<R.getProducts().length; i++){
      str = str+R.getMolProducts().get(i).toStringf()+"  ";
      if(i!=R.getProducts().length-1){
        str = str+"+ ";
      }
    }
    return str;
  
  }
  
  public void formBond(Molecule A, Molecule B, Molecule Asub, Molecule Bsub, int bondingSite1, int bondingSite2){
    
    
    
    A.calculateMoleculeID(0,0,new int[0]);
    B.calculateMoleculeID(0,0, new int[0]);
    ArrayList<Molecule> molsA = A.split(Asub.getMolID());
    A.unFlagAll();
    ArrayList<Molecule> molsB = B.split(Bsub.getMolID());
    B.unFlagAll();
    if(molsA.size()>0){
      //System.out.printf("!!!!!!!!!!!! %d\n", molsA.size());
      
    }
    //System.out.printf(""+Arrays.toString(Asub.getMolID())+"\n");
    
    ArrayList<Network> after = new ArrayList<Network>();
    
    after.addAll( moleculeArrToNetworkArr(molsA) );
    after.addAll( moleculeArrToNetworkArr(molsB) );
    after.add( bond(Asub, Bsub, bondingSite1, bondingSite2) );

    
    numReactions++;
    ArrayList<Molecule> reactants = new ArrayList<Molecule>();
    ArrayList<Molecule> products = new ArrayList<Molecule>();
    
    reactants.add(A);
    reactants.add(B);
    //
    //System.out.printf("\n\t"+A.toStringf()+" + "+B.toStringf()+" --> ");
    bucket.remove(A);
    bucket.remove(B);
    library.get(A.getID()).decrPop();
    library.get(B.getID()).decrPop();
    
    
    
    for(int i=0; i<molsA.size(); i++){
      
      bucket.add(molsA.get(i));
      products.add(molsA.get(i));
      library.get(molsA.get(i).getID()).incrPop();
     // System.out.printf(""+molsA.get(i).toStringf()+" + ");
    }
    
    for(int i=0; i<molsB.size(); i++){
        
        bucket.add(molsB.get(i));
        products.add(molsB.get(i));
        library.get(molsB.get(i).getID()).incrPop();
       // System.out.printf(""+molsB.get(i).toStringf()+" + ");
      }
    
    
    
    int index = libraryUpdate(after.get(after.size()-1), Asub, Bsub);
    bucket.add(new Molecule(index, Asub, Bsub));
    library.get(index).incrPop();
    bucket.get(bucket.size()-1).setSize(Asub.getSize()+Bsub.getSize());
    
    products.add(bucket.get(bucket.size()-1));
    
    checkCatalysis(reactants, products);
    
    Reaction R = new Reaction(reactants, products);
    int reactionIndex = reactionsUpdate(R);
    reactions.get(reactionIndex).incrCount();
    

    //System.out.printf(""+bucket.get(bucket.size()-1).toStringf()+"\n");

    
  }
  
  public void checkCatalysis( ArrayList<Molecule> reactants, ArrayList<Molecule> products ){
    boolean containsMon, containsDi;
    
    containsMon=false;
    containsDi=false;
    int ABIndex, CIndex, ACIndex;
    double A1, A2, A3;
    ABIndex=0;
    CIndex=0;
    ACIndex=0;
    
    // Check reaction for form: AC + B --> AB + C
    if( (products.size() == 2) && (products.get(0).getID() != reactants.get(0).getID()) ){
      
      for(int i=0; i<products.size(); i++){
        if(products.get(i).getSize()==1){containsMon=true; CIndex=i;}
        if(products.get(i).getSize()==2){containsDi=true; ABIndex = i;}
        if(reactants.get(i).getSize()==2){ACIndex = i;}
      }
      if(containsMon && containsDi){
        
        Molecule AB = new Molecule(products.get(ABIndex));
        Molecule A = new Molecule(AB.getChildren(0));
        Molecule B = new Molecule(AB.getChildren(1));
        Molecule C = new Molecule(products.get(CIndex));
        Molecule AC = new Molecule(reactants.get(ACIndex));
        
        // Get activation energy level A1 for reaction A + B -> AB
        double cAB = (double) retrieve(AB).getCycleLength();
        double aAB = retrieve(AB).getActivity();
        double cA = (double) retrieve(A).getCycleLength();
        double aA = retrieve(A).getActivity();
        double cB = (double) retrieve(B).getCycleLength();
        double aB = retrieve(B).getActivity();
        
        A1 = (cAB + cA + cB)/2 + (aAB + aA + aB)/3 - (cA + cB);
        
        
        // Get compound activation energy level A2+A3 for reactions A + C -> AC and AC + B -> AB + C
        double cC = (double) retrieve(C).getCycleLength();
        double aC = retrieve(C).getActivity();
        double cAC = (double) retrieve(AC).getCycleLength();
        double aAC = retrieve(AC).getActivity();
        
        A2 = (cAC + cA + cC)/2 + (aAC + aA + aC)/3 - (cA + cC);
        A3 = (cAC + cB + cAB + cC)/2 + (aAC + aB + aAB + aC)/4 - (cAC + cB);
        
        if((A2+A3 < A1) && (aC !=0)){
          
          
          if( !(foundCatalysisProduct) ){
            catalysisProductIndex = AB.getID();
            foundCatalysisProduct = true;
            
          }
          
          /*System.out.printf("\n\tMolecule %d catalyses the reaction %d + %d --> %d\n", C.getID(), A.getID(), B.getID(), AB.getID());
          System.out.printf("\t\tCyLen.\tActiv.\n");
          System.out.printf("\tA\t%.2f\t%.2f\n", cA, aA);
          System.out.printf("\tB\t%.2f\t%.2f\n", cB, aB);
          System.out.printf("\tC\t%.2f\t%.2f\n", cC, aC);
          System.out.printf("\tAB\t%.2f\t%.2f\n", cAB, aAB);
          System.out.printf("\tAC\t%.2f\t%.2f\n", cAC, aAC);
          System.out.printf("\tA1: %f\n", A1);
          System.out.printf("\tA2+A3: %f\n", A2+A3);*/
          
        
        }
        
        
      }
      
    
    
    
    }
  
  
  }
  
  public void collide(Molecule A, Molecule B, boolean bspar){
  
    ArrayList<Network> before = new ArrayList<Network>();
    ArrayList<Molecule> arrayA = new ArrayList<Molecule>();
    ArrayList<Molecule> arrayB = new ArrayList<Molecule>();
        
    boolean found=false;
    Molecule Asub, Bsub;
    Network net1, net2;
    
    double[][] P;
    int[] bondingSites1, bondingSites2;
    int bondingSite1, bondingSite2;
    double avActivity, avCycleLengthBefore, avCycleLengthAfter, avActivityAfter, Z;
    
    bondingSite1=0;
    bondingSite2=0;
    Z=0;
    
    
    A.sortIntoArray(arrayA);
    B.sortIntoArray(arrayB);
    
    P = new double[arrayA.size()][arrayB.size()];
    
    before.add(retrieve(A));
    before.add(retrieve(B));
    //System.out.printf("!!! %d\n", before.size());
    
    if(bspar){}
    
    else{
      
      for(int i=0; i<arrayA.size(); i++){
      
        for(int j=0; j<arrayB.size(); j++){
          
          
          ArrayList<Molecule> molsA = new ArrayList<Molecule>();
          ArrayList<Molecule> molsB = new ArrayList<Molecule>();
          
          ArrayList<Network> after = new ArrayList<Network>();
          
          avCycleLengthAfter=0;
          avCycleLengthBefore=0;
          avActivityAfter=0;
          
        
          //Asub = new Molecule(arrayA.get(i));
          //Bsub = new Molecule(arrayB.get(j));
          Asub = arrayA.get(i);
          Bsub = arrayB.get(j);
          
          net1 = retrieve(Asub);
          net2 = retrieve(Bsub);
          
          net1.setState(Asub.getState());
          net2.setState(Bsub.getState());
          
          bondingSites1 = net1.getBondingSites();
          bondingSites2 = net2.getBondingSites();
          
          //
          // Ordered choosing of bonding site.
          //
          
          //System.out.printf("%d\t%d\n", bondingSites1.length, bondingSites2.length);
          
          for(int k = bondingSites1.length-1; k>=0; k--){
            //System.out.printf("%d\n", k);
            bondingSite1 = net1.getNodes().get(bondingSites1[k]).getFilled()? bondingSite1 : bondingSites1[k];
          }
          
          for(int k = bondingSites2.length-1; k>=0; k--){
            //System.out.printf("%d\n", k);
            bondingSite2 = net2.getNodes().get(bondingSites2[k]).getFilled()? bondingSite2 : bondingSites2[k];
            
          }
          
          
          molsA = A.split(arrayA.get(i).getMolID());
          A.unFlagAll();
          molsB = B.split(arrayB.get(j).getMolID());
          B.unFlagAll();
          
          
          after.addAll( moleculeArrToNetworkArr(molsA) );
          after.addAll( moleculeArrToNetworkArr(molsB) );
          after.add( bond(Asub, Bsub, bondingSite1, bondingSite2) );
          
          avActivity = 0;
          
          for(int k=0; k< before.size(); k++){
            
            avCycleLengthBefore += (double) before.get(k).getCycleLength();
            avActivity += before.get(k).getActivity();
            
          }
          
          for(int k=0; k<after.size(); k++){
            
            avCycleLengthAfter += (double) after.get(k).getCycleLength();
            avActivity += after.get(k).getActivity();
            
          }
          
          //avCycleLengthAfter = avCycleLengthAfter/after.size();
          //avCycleLengthBefore = avCycleLengthBefore/before.size();
          avActivity = avActivity/(before.size()+after.size());
          
          double exponent = ((avCycleLengthAfter + avCycleLengthBefore)/2 + avActivity)
          - avCycleLengthBefore;
          
          //System.out.printf("%f\t%f\t%f\n", avCycleLengthBefore, exponent+avCycleLengthBefore, avCycleLengthAfter);
          
          Z += Math.exp(-exponent/temperature);
          P[i][j] = Math.exp(-exponent/temperature);
          
        
        }
        
        
      }
    
      /*for(int i=0; (i<arrayA.size())&&(!found); i++){
        for(int j=0; (j<arrayB.size())&&(!found); j++){
          System.out.printf("%.3f\t", P[i][j]/(Z+1));
        }
        System.out.printf("\n");
      }
      System.out.printf("\n");*/

      
      
      double rand = Math.random();
      
      
      for(int i=0; (i<arrayA.size())&&(!found); i++){
        for(int j=0; (j<arrayB.size())&&(!found); j++){
          if(P[i][j]/(Z+1)>rand){
            //System.out.printf("\nDone!! [%d, %d]\n",i,j);
            //Asub = new Molecule(arrayA.get(i));
            //Bsub = new Molecule(arrayB.get(j));
            Asub = arrayA.get(i);
            Bsub = arrayB.get(j);
            net1 = retrieve(Asub);
            net2 = retrieve(Bsub);
            
            net1.setState(Asub.getState());
            net2.setState(Bsub.getState());
            
            bondingSites1 = net1.getBondingSites();
            bondingSites2 = net2.getBondingSites();
            
            //
            // Ordered choosing of bonding site.
            //
            
            //System.out.printf("%d\t%d\n", bondingSites1.length, bondingSites2.length);
            
            for(int k = bondingSites1.length-1; k>=0; k--){
              //System.out.printf("%d\n", k);
              bondingSite1 = net1.getNodes().get(bondingSites1[k]).getFilled()? bondingSite1 : bondingSites1[k];
            }
            
            for(int k = bondingSites2.length-1; k>=0; k--){
              //System.out.printf("%d\n", k);
              bondingSite2 = net2.getNodes().get(bondingSites2[k]).getFilled()? bondingSite2 : bondingSites2[k];
              
            }
            
            //System.out.printf("%d\t%d\t%d\t%d\n", arrayA.get(i).getSize(),arrayB.get(j).getSize(),bondingSite1,bondingSite2);
            formBond(A, B, Asub, Bsub, bondingSite1, bondingSite2);
            
            found = true;
          
          }
        }
      }
      
    }
  
  
  }
  
  public void breakUp(Molecule A){
    
    Molecule Asub;
    Network networkA;
    ArrayList<Molecule> fragmentsMol = new ArrayList<Molecule>();
    ArrayList<Network> fragments = new ArrayList<Network>();
    
    int[] bondingSites;
    int bondingSite;
    
    int[] AsubMolID;
    
    bondingSite=0;
    
    AsubMolID = A.selectRandom();
    
    Asub = new Molecule(A.getFromMoleculeID(AsubMolID, AsubMolID.length, 0));
    
    fragmentsMol = A.split(AsubMolID);
    fragmentsMol.add(Asub);
    fragments = moleculeArrToNetworkArr(fragmentsMol);
    
    A.unFlagAll();
    
    networkA = library.get(A.getID());
    networkA.setState(A.getState());
    
    double avActivity = networkA.getActivity();
    double avCycleLengthBefore = networkA.getCycleLength();
    double avCycleLengthAfter = 0;
    double P = 0;
    
    for(int i=0; i<fragments.size(); i++){
      
      avActivity += (double) fragments.get(i).getActivity();
      avCycleLengthAfter += (double) fragments.get(i).getCycleLength();
      
    }
    
    avActivity = avActivity/(fragments.size()+1);
    //avCycleLengthAfter = avCycleLengthAfter/fragments.size();
    
    
    double exponent = ((avCycleLengthAfter + avCycleLengthBefore)/2 + avActivity)
    - avCycleLengthBefore;
    
    P = Math.exp(-exponent/temperature);
    
    /*if(avActivityAfter < ActivityBefore){
      
      P = 1;
      
    }
    else{
      
      //P=0;
      //P = exp[(int)avCycleLengthAfter-(int)avCycleLengthBefore];
      P = Math.exp( ((ActivityBefore-avActivityAfter)/ (1*temperature) ) );
      //P=1;
    }*/
    
    boolean bondBroken = (Math.random()<P)? true:false;
    
    if(bondBroken){
      //System.out.printf("\t"+A.toStringf()+" ---> %d\n", fragments.size());
      numReactions++;
      ArrayList<Molecule> reactants = new ArrayList<Molecule>();
      ArrayList<Molecule> products = new ArrayList<Molecule>();
      
      reactants.add(A);
      bucket.remove(A);
      library.get(A.getID()).decrPop();
      
      for(int i=0; i<fragmentsMol.size(); i++){
        
        bucket.add(fragmentsMol.get(i));
        products.add(fragmentsMol.get(i));
        library.get(fragmentsMol.get(i).getID()).incrPop();
        
      }
      
      //products.add(bucket.get(bucket.size()-1));
      //library.get(bucket.get(bucket.size()-1).getID()).incrPop();
      
      
      //Reaction R = new Reaction(reactants, products, this);
      //int reactionIndex = reactionsUpdate(R);
      //reactions.get(reactionIndex).incrCount();
      
    
    }
  
  }
  
  public static void progress(String process, int i, int N){
    
    double k=0;
    
    System.out.printf("\r");
    String outputString;
    
    outputString = "\t";
    for(k=0; k<=(double)i; k+=(double)N/50){
      
      outputString+="█";
      
    }
    while(k<=N){
      
      outputString+="-";
      k+=(double)N/50;
    }
    System.out.printf("%20s "+outputString+" %d%%", process, (int)(100*((double)i/(double)N)));
    
    
  }

  public static void main(String args[]){
    
    boolean scriptTemp=false;
    
    Date d = new Date();
    Time t1 = new Time(d.getHours(), d.getMinutes(), d.getSeconds());
    long t2 = d.getTime();
    
    ArrayList<Molecule> reactants = new ArrayList<Molecule>();
    ArrayList<Molecule> unstable = new ArrayList<Molecule>();
    ArrayList<DataOutput> data = new ArrayList<DataOutput>();
    
    int[] arr = new int[2];
    int[] CLDistrb10 = new int[0];
    int[] CLDistrb100 = new int[0];
    
    ////////////
    //DEFAULTS//
    ////////////
    int initPop = 5000;
    int numSpecies = 50;
    reuse = false;      // Will generate new RBNs on each run
    path = "/data/library/";   //Default path to load RBNs from
    
    //
    // Command-line arguments
    //
    System.out.printf("\n\n\tInterpreting input arguments...\n\n");
    for(int i=0; i<args.length; i++){
      String str = args[i];
      if(str.startsWith("temp=")){
        temperature = Double.parseDouble(str.substring(5));
        if(scriptTemp){
          temperature = temperature/10;
        }
        System.out.printf("\t\tTemperature = %.2f\n",temperature);
      }
      
      else if(str.startsWith("initPop=")){
        initPop = Integer.parseInt(str.substring(8));
        System.out.printf("\t\tInitial population = %d\n",initPop);
      }
      else if(str.startsWith("scriptTemp")){
        scriptTemp=true;
        System.out.printf("\t\tInitial population = %d\n",initPop);
      }
      else if(str.startsWith("numSpecies=")){
        numSpecies = Integer.parseInt(str.substring(11));
        System.out.printf("\t\tInitial # of species = %d\n", numSpecies );
      }
      else if (str.startsWith("fileSuffix=")){
        suffix=str.substring(11);
        System.out.printf("\t\tSaving files with suffix"+suffix);
      }
      
      else if(str.startsWith("reuse")){
      
        if(str.length()>5){
          path = str.substring(5);
          reuse = true;
          System.out.printf("\t\tRe-using previous RBNs saved in path "+path+"\n");
          
        }
        
        else{
          reuse = true;
          System.out.printf("\t\tRe-using previous RBNs saved in path "+path+"\n");
        }
      }
    }
    
    data.add( new DataOutput("/data/numReactionsPop"+suffix+".txt", true) );
    data.add( new DataOutput("/data/molecularSizeDistrb"+suffix+".txt", true) );
    data.add( new DataOutput("/data/librarySize"+suffix+".txt", true) );
    data.add( new DataOutput("/data/tempSizeDistrb_"+suffix+".txt", true) );
    data.add( new DataOutput("/data/cyclelengths"+suffix+".txt", true) );
    data.add( new DataOutput("/data/catalysis_"+suffix+".txt", true));
    data.add( new DataOutput("/data/experiment2/"+suffix+".txt", true));
 
    for(int i=0; i<data.size(); i++){
      try{
        data.get(i).clearFile();
      }catch(IOException e){
        System.out.println("Could not clear file "+data.get(i).getPath()+"\n");
      }  
    }
    
    Main m = new Main(numSpecies, 10, 2, initPop);
    //Main m = new Main(numSpecies, 13, 3, 14, 100000, Double.parseDouble(suffix)/100);
    m.getReactions().add(new Reaction());
    
    //m.populationDistribution();
    int temp=0;
    
    
    for(int i=0; i<10*initPop; i++){
      //System.out.printf("bucket size %d\n", m.getNumSpecies());
      
      //temperature = 1 + 1*Math.sin( 0.00005*i );
      
      if(i%10==0){
        int[] sizeDistrb = m.molecularSizeDistrb();
        double[] tempSizeDistrb = new double[sizeDistrb.length+1];
        tempSizeDistrb[0]=temperature;
        for(int k=0;k<sizeDistrb.length; k++){
          
          tempSizeDistrb[k+1]= (double)sizeDistrb[k];
        
        }
        m.progress("Colliding...", i, 10*initPop);
        System.out.printf("\t%.2f", m.temperature);
        //System.out.printf("%d\t%d\n", i, m.getLibrary().size());
        
        double tempCL=0;
        for(int k=0; k<m.getPopulation(); k++){
        
          Molecule M = m.getBucket().get(k);
          tempCL += m.retrieve(M).getCycleLength()*M.getSize();
          
          
        }
        
        double[] CLarr = new double[2];
        CLarr[0] = i;
        CLarr[1] = tempCL;
        
        
        
        String catStr = ""+((double)m.getLibrary().get(1).getPop()/(double)m.getPopulation())+"\t"
                  +((double)m.getLibrary().get(2).getPop()/(double)m.getPopulation());
        
        if(catalysisProductIndex != -1){
          catStr += "\t"+((double)m.getLibrary().get(catalysisProductIndex).getPop()/(double)m.getPopulation())+"\n";
        }
        else{
          catStr += "\n";
        }
        
        String librarySize = ""+i+"\t"+m.getLibrary().size();
        
        arr[0] = m.getNumReactions()-temp;
        arr[1] = m.getPopulation();
        //System.out.printf("%d\t%d\t%d\n",i, m.getNumReactions()-temp, m.getPopulation());
        temp=m.getNumReactions();
        try{
          data.get(5).writeToFile( catStr );
          data.get(4).writeToFile( CLarr );
          data.get(3).writeToFile( tempSizeDistrb );
          data.get(2).writeToFile( librarySize );
          data.get(1).writeToFile( sizeDistrb );
          data.get(0).writeToFile( arr );
        }
        catch(IOException e){
          System.out.println("Could not write to file "+data.get(0).getPath());
        }
        
        
        if(temperature <0.001){
          temperature = 0.001;
        }
        
        
      }
      
      reactants = m.selectRandMols(2);
      
      Molecule A, B;
      
      A = reactants.get(0);
      B = reactants.get(1);
      
      if(! (A.getID()==0 || B.getID() == 0)){
        m.collide(A, B, false);
      }
      
      
      
      unstable = m.selectRandMols(2);
      
      A = unstable.get(0);
      B = unstable.get(1);
      
      //System.out.printf("\n\n|||||\t%d\n", m.getPopulation());
      if( (A.getID()!=0) && (A.getSize()!=1) ){
        m.breakUp(A);
      }
      if( (B.getID()!=0) && (B.getSize()!=1) ){
        m.breakUp(B);
      }
      
      //System.out.printf("|||||\t%d\n", m.getPopulation());
      
      
      
    }
    
    m.progress("Colliding...", 100*initPop, 100*initPop);
  
    //m.reactionAnalysis();
    try{
      data.get(6).writeToFile( m.cycleLengthDistrb() );
    }catch(IOException e){}
    //m.populationDistribution();
    //m.molecularSizeDistrb();
    
    
    DataOutput log = new DataOutput("log.txt", true);
    Date b = new Date();
    long t3 = b.getTime();
    try{
      
      log.writeToFile( String.format("["+t1.toString()+"]; "+(t3-t2)+"ms; temperature %.2f; fileSuffix %s", m.temperature, suffix) );

    
    }catch(IOException e){System.out.printf("couldnt write to log.txt\n");}
    
    
  }
  
}












