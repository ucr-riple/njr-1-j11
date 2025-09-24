import java.io.*;
import java.util.*;

public class Network implements Serializable{
  
  private int numNodes;
  private int NetworkID;
  private int population;
  private int numBondingSites;
  private ArrayList<Node> nodes = new ArrayList<Node>();
  private int[] bondingSites;
  
  private int state;  
  private double activity;
  private int cycleLength;
  
  private Molecule mol;
  
  public Network(){
    
    numNodes=0;
    NetworkID=0;
    numBondingSites=0;
    nodes = null;
    bondingSites = new int[0];
    
  }
  
  // Constructor for a NON-bonding RBN
  // If numNodes = 0, constructs a NULL NETWORK
  public Network(int numNodes){
    
    this.numNodes = numNodes;
    this.NetworkID = 0;
    this.bondingSites = new int[1];
    
    for(int i=0;i<numNodes;i++){
      
      nodes.add(new Node(numNodes, false)); //currently set to create non bonding RBNs
    }
    
    calculateCycleLength();
    calculateActivity();
  }
  
  // Constructor for a bonding RBN: Same as above, but nodes are ordered by connectivity
  // and most highly ordered nodes are designated as bonding sites
  public Network(int numNodes, int NetworkID, int numBondingSites){
    
    this.numNodes = numNodes;
    this.NetworkID = NetworkID;
    this.numBondingSites = numBondingSites;
    bondingSites = new int[numBondingSites];
    population = 0;
   
    
    //System.out.printf("\n\nAdding nodes...\n");
    for(int i=0;i<numNodes;i++){
      
      nodes.add(new Node(numNodes, false));
    }
    //System.out.printf("Nodes successfully added.\n\n");
    
    //ASSUME Java initialises array to 0's
    int[] arr = new int[numNodes];
    int index;
    
    //Counts # of outputs of each node
    //System.out.printf("Counting # outputs per node...\n");
    for(int i=0; i<numNodes; i++){
      
      for(int j=0; j<2; j++){
        index = nodes.get(i).getInputs()[j];
        arr[index]++;
      }
    }
    
    // Tracks n = numBondingSites largest in single pass
    boolean inserted;
    //int[] arr_indeces = new int[numBondingSites]
    for(int i=0; i<numNodes; i++){
      inserted = false;
      int j = 0;
      while(j<numBondingSites && !inserted){
        if( arr[i]>=arr[bondingSites[j]] || ((j==1) && (bondingSites[j]==0)) ){
          inserted=true;
          for( int k=numBondingSites-1; k>j; k-- ){
            if(bondingSites[k]!=bondingSites[k-1]){
              bondingSites[k]=bondingSites[k-1];
            }
          }          
          bondingSites[j] = i;
        }
        j++;
      }
    }
    //System.out.printf("\n");
    if(bondingSites[0]==0 && bondingSites[1]==0){System.out.printf("Error...\n");}
    for(int j=0; j<numBondingSites; j++){
      
      nodes.get(bondingSites[j]).setBondingSite(true);
      nodes.get(bondingSites[j]).setFilled(false);
    }
    
    calculateCycleLength();
    calculateActivity();
    
  }
  
  public Network(ArrayList<Node> nodes, int[] bondingSites){
    
    this.nodes = nodes;
    numNodes = nodes.size();
    
    this.bondingSites = Arrays.copyOf(bondingSites, bondingSites.length);
    numBondingSites = bondingSites.length;
    
    calculateCycleLength();
    calculateActivity();
  }
  
  public ArrayList<Node> copyNodes(){
    ArrayList<Node> copy = new ArrayList<Node>();
    for(int i=0; i<numNodes; i++){
      
      copy.add(new Node(nodes.get(i)));
    }
    return copy;
  }
  
  public void setState(int state){
    
    this.state = state;
  }
  
  public void setPop(int population){
    this.population = population;
  }
  
  
  public void incrPop(){
  
    population++;
    
  }
  
  public void decrPop(){
    population--;
  }
  
  public void setMol(Molecule mol){
    this.mol = new Molecule(mol);
  }
  
  public Molecule getMol(){
    
    return mol;
  
  }
  
  public int getNetworkID(){
    return NetworkID;
  }
  
  public int getNumNodes(){
    
    return numNodes;
  }
  
  public ArrayList<Node> getNodes(){
    
    return nodes;
  }
  
  public int getCycleLength(){
    
    return cycleLength;
  }
  
  public int getState(){
    
    return state;
  }
  
  public double getActivity(){
    
    return activity;
  }
  
  public int getNumBondingSites(){
    
    return numBondingSites;
  }
  
  public int[] getBondingSites(){
    
    return bondingSites;
  }
  
  public int getPop(){
    return population;
  }
  
  
  
  public void printMatrixString(){
    
    int[][] networkMatrix; //int[INPUT][OUTPUT]
    
    networkMatrix = new int[numNodes][numNodes];
    
    //initialise
    for(int i=0; i<numNodes; i++){
      for(int j=0; j<numNodes; j++){
        networkMatrix[i][i] = 0;
      }
    }
    Node node;
    for(int i=0; i<numNodes; i++){
      node = getNodes().get(i);
      int k = node.getBondingSite() ? 1:0;
      while(k<2){
        networkMatrix[i][node.getInputs()[k]] = 1;
        k++;
      }
    }
    
    for(int i=0; i<numNodes; i++){
      System.out.printf("\t%d", i);
    }
    System.out.printf("\n");
    for(int i=0; i<numNodes; i++){
      System.out.printf("%d", i);
      for(int j=0; j<numNodes; j++){
        
        System.out.printf("\t"+((networkMatrix[i][j]==1)? "█":" "));
        
      }
      System.out.printf("\n");
    }
    
  }
  
  public String toString(){
    
    return "";
  }
  
  public void calculateState(){
    
    boolean[] arr = new boolean[numNodes];
    int tempState=0;
    
    for(int i=0;i<numNodes;i++){
      arr[i] = nodes.get(i).getState();
    }
    
    for(int i=0;i<numNodes;i++){
      tempState = tempState << 1;
      tempState += (arr[i]? 1:0);
    }
    state = tempState;
    
  }
  
  public void calculateCycleLength(){
    
    boolean notFound = true;
    int currentState;
    int threshhold = 10;
    
    while(notFound){
      
      for(int i=0;i<threshhold;i++){
        update();
        
      }
      
      currentState = getState();
      
      for(int i=0;i<threshhold;i++){
        update();
        
        if (currentState == getState()){
          notFound = false;
          cycleLength=i+1;
          break;
        }
      }
      threshhold *= 2;
      
    }
    
    
  }
  
  //Must be called after cycle length is calculated on any instance of Network
  public void calculateActivity(){
    
    int counter=0;
    for(int i=0; i<cycleLength; i++){
      counter += update(0);
    }
    activity = (double)counter/cycleLength;
  }
  
  //Normal updating method
  public void update(){
    //System.out.printf("\n");
    for(int i=0; i<numNodes; i++){
      
      Node currentNode;
      int[] inputs;           //Should this/these declaration(s) go outside loop??
      
      currentNode = nodes.get(i);
      inputs = currentNode.getInputs();
      
      // !!!!! CHECK LOGIC HERE
      
      //System.out.printf("%d\t%d\t%d\n", inputs[0], nodes.size(), numNodes);
      /*int y = ((nodes.get(inputs[0]).getState() && !currentNode.getBondingSite()) ?
               2 : (currentNode.getFilled() ? 1:0))
      + (nodes.get(inputs[1]).getState() ? 1:0);*/
      int x = currentNode.getFunction();
      
      int y = ((nodes.get(inputs[0]).getState())? 2:0) + ((nodes.get(inputs[1]).getState())? 1:0);
      
      //System.out.printf("[%d, %d]\n", x, y);
      currentNode.setNextState(Global.lookUp[x][y]);
      
    }
    
    for(int i=0; i<numNodes; i++){
      Node currentNode;
      
      currentNode = nodes.get(i);
      currentNode.setState(currentNode.getNextState());
      //System.out.printf("%b\t", currentNode.getState());
    }
    //System.out.printf("\n");
    calculateState();
    //System.out.printf("%d\t", state);
    
  }
  
  //updating method that returns activity of that iteration
  public int update(int counter){
    
    for(int i=0; i<numNodes; i++){
      
      Node currentNode;
      int[] inputs;
      
      currentNode = nodes.get(i);
      inputs = currentNode.getInputs();
      
      //CHECK LOGIC HERE
      int y = ((nodes.get(inputs[0]).getState())? 2:0) + ((nodes.get(inputs[1]).getState())? 1:0);
      /*int y = ((nodes.get(inputs[0]).getState() && !currentNode.getBondingSite()) ? 2:0)
      + (nodes.get(inputs[1]).getState() ? 1:0);*/
      int x = currentNode.getFunction();
      
      //System.out.printf("[%d, %d]\n", x, y);
      currentNode.setNextState(Global.lookUp[x][y]);
      
      // if (doesnt work) try "currentNode.getState() != currentNode.getNextState()"
      if((currentNode.getState() || currentNode.getNextState()) &&
         !(currentNode.getState() && currentNode.getNextState())){
        
        counter++;  
      }
      
      
    }
    
    for(int i=0; i<numNodes; i++){
      Node currentNode;
      
      currentNode = nodes.get(i);
      currentNode.setState(currentNode.getNextState());
      //System.out.printf("%b\t", currentNode.getState());
    }
    //System.out.printf("\n");
    calculateState();
    //System.out.printf("%d\t", state);
    return counter;
    
  }
  

  
  public Network bond(Network B, int bondingSiteA, int bondingSiteB){
    
    Network C;
    
    ArrayList<Node> nodesA = this.copyNodes();
    ArrayList<Node> nodesB = B.copyNodes();
    ArrayList<Node> nodesC = new ArrayList<Node>();
    
    
    int numNodesA = this.numNodes;
    int numNodesB = B.getNumNodes();
    int numNodesC = numNodesA + numNodesB;
    
    int numBondingSitesA = this.numBondingSites;
    int numBondingSitesB = B.getNumBondingSites();
    int numBondingSitesC = numBondingSitesA + numBondingSitesB - 2;
    
    int[] bondingSitesA = Arrays.copyOf(this.bondingSites, numBondingSites);
    int[] bondingSitesB = Arrays.copyOf(B.getBondingSites(), B.getNumBondingSites());
    int[] bondingSitesC = new int[numBondingSitesC];
    
    for(int i=0; i<numNodesA; i++){
      
      nodesC.add(new Node(nodesA.get(i)));
    }
    for(int i=0; i<numNodesB; i++){
      int[] temp;
      nodesC.add(new Node(nodesB.get(i)));
      temp = Arrays.copyOf(nodesC.get(i+numNodesA).getInputs(), nodesC.get(i+numNodesA).getInputs().length);
      for(int j=0; j<temp.length; j++){
        temp[j] += numNodesA;
      }
      nodesC.get(i+numNodesA).setInputs(temp);
    }
    
    nodesC.get(bondingSiteA).setBondingInput(numNodesA + bondingSiteB);
    nodesC.get(numNodesA + bondingSiteB).setBondingInput(bondingSiteA);
    
    nodesC.get(bondingSiteA).setBondingSite(false);
    nodesC.get(numNodesA + bondingSiteB).setBondingSite(false);
    
    int j=0;
    for(int i=0; i<numBondingSitesA; i++){
      
      if(bondingSitesA[i] != bondingSiteA){
        bondingSitesC[j] = bondingSitesA[i];
        j++;
      }
    }
    for(int i=0; i<numBondingSitesB; i++){
      
      if(bondingSitesB[i] != bondingSiteB){
        //System.out.printf("J:  %d\n#A:   %d\n#B:   %d\n#C:   %d\n", j,numBondingSitesA,numBondingSitesB, numBondingSitesC);
        bondingSitesC[j] = bondingSitesB[i]+numNodesA;
        j++;
      }
    }
    
    C = new Network(nodesC, bondingSitesC);
    C.calculateCycleLength();
    C.calculateActivity();
    return C;
    
  }
  
  public void trajectory(int time){
    
    int activity;
    for(int i=0; i<time; i++){
      for(int j=0;j<numNodes;j++){
        System.out.printf(""+(nodes.get(j).getState()? " ":"█") );
      }
      activity = 0;
      activity = update(activity);
      System.out.printf("\t%d\t%d\n", activity, state);
    }
  }
  
  // Topological comparison of two networks. Returns true only if the two networks
  // are topoligially equivalent.
  public boolean equals(Network A){
    
    boolean result = true;
    if(numNodes == A.getNumNodes()){
      
      for(int i=0; i<numNodes; i++){
        
        if(!Arrays.equals(nodes.get(i).getInputs(), A.getNodes().get(i).getInputs())){
          result = false;
        }
      }
    }
    else{
      result = false;
    }
    
    return result;
  }
  
  public static void main(String args[]){
    
    Network newNetwork, A, B, C;
    
    int arg = Integer.parseInt(args[0]);
    switch(arg){
        
      case 0:
        float[] arr = {0, 0, 0, 0, 0};
        float mean=0;
        int num = 1000000;
        for(int i=0; i<num; i++){
          
          newNetwork = new Network(10);
          newNetwork.calculateCycleLength();
          
          //System.out.printf("%d\n", (int)Math.log(newNetwork.getCycleLength()));
          arr[(int)Math.log10(newNetwork.getCycleLength())]++;
          mean += newNetwork.getCycleLength();
        }
        
        mean = mean/num;
        System.out.printf("\n\tCyclelength\t%% RBNs\t\n");
        System.out.printf("\t=======================\n");
        for(int i=0; i<4; i++){
          System.out.printf("\t>%d\t\t%.2f%%\t\n", (int)Math.pow(10, i), 100*arr[i]/num);
        }
        System.out.printf("\n");
        
        System.out.printf("\t# Nodes\t\tMean\n");
        System.out.printf("\t=======================\n");
        for(int i=10;i<101;i+=10){
          mean = 0;
          num = 100000;
          for(int j=0; j<num; j++){
            
            newNetwork = new Network(i);
            newNetwork.calculateCycleLength();
            mean += newNetwork.getCycleLength();
          }
          mean = mean/num;
          System.out.printf("\t%d\t\t%.2f\n", i, mean);
        }
        
        System.out.printf("\n");
        
        break;
      case 1: //Test trajectory()
        newNetwork = new Network(100);
        newNetwork.trajectory(200);
        break;
        
      case 2: //Test Activity
        newNetwork= new Network(100,0,2);
        newNetwork.calculateCycleLength();
        newNetwork.calculateActivity();
        //System.out.printf("A has a cycle length of %d and activity %f\n", newNetwork.getCycleLength(), newNetwork.getActivity());
        double counter1 = 0;
        double counter2 = 0;
        int N = 100000;
        System.out.printf("\n\n\tNodes\tMean cycle\tMean activity\n");
        System.out.printf("\t=====================================\n");
        for(int j=10; j<101; j+=10){
          
          for(int i=0; i<N; i++){
            newNetwork = new Network(j,0,2);
            newNetwork.calculateCycleLength();
            newNetwork.calculateActivity();
            counter1 += (double) newNetwork.getCycleLength();
            counter2 += newNetwork.getActivity();
          }
          counter1 = counter1/(double)N;
          counter2 = counter2/(double)N;
          System.out.printf("\t%d\t%f\t%f\n", j, counter1, counter2);
          
          
        }
        System.out.printf("\n");
        
        break;
        
      case 3:
        int[] bondingSitesA, bondingSitesB, bondingSitesC;
        for(int i=0; i<1; i++){
          A = new Network(10, 0, 2);
          B = new Network(10, 1, 2);
          
          //System.out.printf("A and B initialised.\n");
          
          A.calculateCycleLength();
          A.calculateActivity();
          A.printMatrixString();
          bondingSitesA = A.getBondingSites();
          System.out.printf("A has a cycle length of %d and activity %f\n", A.getCycleLength(), A.getActivity());
          
          
          B.calculateCycleLength();
          B.calculateActivity();
          B.printMatrixString();
          bondingSitesB = B.getBondingSites();
          System.out.printf("B has a cycle length of %d and activity %f\n", B.getCycleLength(), B.getActivity());
          
          C = A.bond(B, A.bondingSites[0], B.bondingSites[0]);
          
          C.calculateCycleLength();
          C.calculateActivity();
          C.printMatrixString();
          bondingSitesC = C.getBondingSites();
          System.out.printf("C has a cycle length of %d and activity %f\n", C.getCycleLength(), C.getActivity());
          
          System.out.printf("\nBonding Sites:\nA:\t%d\t%d\nB:\t%d\t%d\nC:\t%d\t%d\n", bondingSitesA[0], bondingSitesA[1],
                            bondingSitesB[0], bondingSitesB[1],
                            bondingSitesC[0], bondingSitesC[1]);
          for(int j=0; j<10; j++){
            System.out.printf("ASDF:\t%d\t%d\n", A.getNodes().get(j).getInputs()[0], A.getNodes().get(j).getInputs()[1]);
          }
        }
        
        break;
        
        
        
      case 4:
        for(int i=0; i<10000; i++){
          A = new Network(20, 0, 2);
          B = new Network(20, 0, 2);
          
          A.calculateCycleLength();
          B.calculateCycleLength();
          A.calculateActivity();
          B.calculateActivity();
          
          C = A.bond(B, A.bondingSites[0], B.bondingSites[0]);
          
          C.calculateCycleLength();
          C.calculateActivity();
          
          System.out.printf("%d\t%d\t%d\t%f\t%f\t%f\n", A.getCycleLength(), B.getCycleLength(), C.getCycleLength(),
                            A.getActivity(), B.getActivity(), C.getActivity());
        }
        
        break;
        
      case 5:
        
        DataOutput dat =  new DataOutput("/data/ActCyCorrelation.txt", true);
        
        for(int i=0; i<1000000; i++){
          if(i%100 == 0){System.out.printf("%d\n", i);}
          Network X = new Network(10);
          double act = X.getActivity();
          int cy = X.getCycleLength();
          String str = ""+act+"\t"+cy;
          try{
            dat.writeToFile(str);
          }catch(IOException e){}
          
        }
        
        
    }
    
    
    
  }
  
}