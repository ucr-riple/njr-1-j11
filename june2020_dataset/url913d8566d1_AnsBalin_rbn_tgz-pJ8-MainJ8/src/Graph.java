import java.util.*;

public class Graph{
    
  public static final int _REACTION_ = Integer.MIN_VALUE;
  public static final int _MOLECULE_ = Integer.MIN_VALUE + 1;
  public static final int _NULL_ = Integer.MIN_VALUE + 2;
  public static final int WHITE = 0;
  public static final int BLACK = -1;
  
  
  public class GraphNode{
    
    private Object contents;
    private int type;
    private int ID;
    private int nodeIndex;
    private String StringID;
    private ArrayList children;
    private ArrayList parents;
    private int colour; // -1: black, 0: white, 1+:gray
    
    public GraphNode(){
      contents = null;
      ID=-2;
      StringID="Null";
      children = null;
      parents = null;
      type = _NULL_;
    }
    
    public GraphNode(Molecule o, int nodeIndex){
      
      contents = new Molecule(o);
      type = _MOLECULE_;
      children = new ArrayList<GraphNode>();
      parents = new ArrayList<GraphNode>();
      colour = 0;
      ID = o.getID();
      StringID = o.getStringID();
      this.nodeIndex = nodeIndex;
      
    }
    public GraphNode(Reaction o, int nodeIndex){
      
      contents = new Reaction(o);
      type = _REACTION_;
      children = new ArrayList<GraphNode>();
      parents = new ArrayList<GraphNode>();
      colour = 0;
      ID = o.getIntID();
      StringID = o.getID();
      this.nodeIndex = nodeIndex;
      
    }
    
    public int getColour(){
      return colour;
    }
    
    public int getID(){
      return ID;
    }
    public int getNodeIndex(){
      return nodeIndex;
    }
    public int getType(){
      return type;
    }
    
    public void print(){
    
      System.out.println(""+StringID);
    }
    
    public ArrayList<GraphNode> getChildren(){
      return children;
    }
    
    public void colour(int i){
    
      colour=i;
      
    }
    
    public void addChild(GraphNode GNChild){
      
      children.add(GNChild);
      
    }
    
    public void addParent(GraphNode GNParent){
    
      parents.add(GNParent);
    }
    
    //Forms directed edge from this to GN
    public void connect(GraphNode GN){
      
      addChild(GN);
      GN.addParent(this);
      
    }
  } 
  
  private ArrayList<GraphNode> graphNodes;
  private ArrayList<int[]> cycles;
  private int numCycles;
  private int visited=0;
  private ArrayList<GraphNode> buffer;
  
  public Graph(){
    
    graphNodes = new ArrayList<GraphNode>();
    graphNodes.add(new GraphNode());
    numCycles = 0;
    buffer = new ArrayList<GraphNode>();
    cycles = new ArrayList<int[]>();
  }
  
  public int checkReactionID(int IDCheck){
    int index=-1;
    for(int i=0; i<graphNodes.size(); i++){
      if( (graphNodes.get(i).getID()==IDCheck) && (graphNodes.get(i).getType() == _REACTION_) ){
        index=i;
      
      }
    }
    return index;
  }
  
  public int checkMoleculeID(int IDCheck){
    int index=-1;
    for(int i=0; i<graphNodes.size(); i++){
      if( (graphNodes.get(i).getID()==IDCheck) && (graphNodes.get(i).getType() == _MOLECULE_) ){
        index=i;
        
      }
    }
    return index;
  }
  
  public int getNumCycles(){
    return numCycles;
  }
  
  public ArrayList<GraphNode> getGraphNodes(){return graphNodes;}
  public ArrayList<int[]> getCycles(){return cycles;}
  
  public void add(Reaction R, Main m){
    
    int IDCheck;
    int index = checkReactionID(R.getIntID());  // index = index of R in Graph. checks if R is in Graph, if it is, returns index.
   
    if(index==-1){                               // ie. if R isnt in Graph
      index=graphNodes.size();
      graphNodes.add(new GraphNode(new Reaction(R), index));  // NEW INSTANCE OF R IS ADDED
      //R.printReaction();
    }
    
    for(int i=0; i<R.getReactants().length; i++){
      
      IDCheck = R.getReactants()[i];
      int reactantIndex = checkMoleculeID(IDCheck);   // Checks to see if reactant is already in graph.
      
      if(reactantIndex==-1){    // ie. if reactant isnt in graph
        reactantIndex = graphNodes.size();
        graphNodes.add(new GraphNode(new Molecule(m.getLibrary().get(IDCheck).getMol()), reactantIndex));   // NEW INSTANCE OF MOLECULE IS ADDEDx
      }
      
      //System.out.printf("(%d) is a parent of (%d)\n", reactantIndex, index);
      graphNodes.get(reactantIndex).connect(graphNodes.get(index));      //Makes directed edge
      
    }
    
    for(int i=0; i<R.getProducts().length; i++){
      
      IDCheck = R.getProducts()[i];
      int productIndex = checkMoleculeID(IDCheck);
      
      if(productIndex==-1){
        productIndex = graphNodes.size();
        graphNodes.add(new GraphNode(new Molecule(m.getLibrary().get(IDCheck).getMol()), productIndex));
      }
      //if(index==1){System.out.printf("\n(1) has another child` ie %d child!!!!\n", R.getProducts().length);}
      //if(index==0){System.out.printf("!!!!!!!!\n");}
      //System.out.printf("(%d) is a parent of (%d)\n", index, productIndex);
      graphNodes.get(index).connect(graphNodes.get(productIndex));
      
    }
  
  }
  
  public void bufferSave(GraphNode child){

    int i=0;
    while(!buffer.get(i).equals(child)){
      i++;
    }
    
    int k=0;
    int[] temp = new int[buffer.size() - i];
    while(i<buffer.size()){
    
      temp[k] = buffer.get(i).getNodeIndex();
      k++;
      i++;
    }
    cycles.add(temp);
    
  }
  
  // Performs a depth-first-search of this graph
  public void dfs(){
    int depth;
    for(int i=0; i<graphNodes.size(); i++){
      if((graphNodes.get(i).getColour()==0)&&(graphNodes.get(i).getChildren()!=null)){
        depth = 1;
        buffer.clear();
        dfsVisit(graphNodes.get(i), depth);
      }
    }
  }
  
  
  
  public void dfsVisit(GraphNode GN, int depth){
    GN.colour(depth);
    buffer.add(GN);
    ArrayList<GraphNode> children = GN.getChildren();
    
    for(int i=0; i<children.size(); i++){
      
      if((children.get(i).getColour()==0)&&(children.get(i).getChildren()!=null)){
         dfsVisit(children.get(i), depth+1);
      }
      
      else if(children.get(i).getColour()>0){
        numCycles++;
        bufferSave(children.get(i));
        System.out.printf("%d\n", GN.getColour()- children.get(i).getColour()+1);
        
      }
      
      else{} 
    
    }
    buffer.remove(GN);
    GN.colour(-1);
    visited++;
    
  }  
  
  public boolean filter(Reaction R, int option, int arg ){
    
    boolean result = false;
    
    switch(option){
    
      case 0:
        if(R.getCount() > arg){
          result = true;
        }
        break;
    
    
    
    }
    
    return result;
  
  }
 
  public void printCycle(int cycleIndex, Main m){
    int[] temp = cycles.get(cycleIndex);
    
    System.out.printf("\t Cycle %d:\n", cycleIndex);
    for(int i=0; i<temp.length; i++){
    
      if(graphNodes.get(temp[i]).getType()== _REACTION_){
        System.out.printf("\t");
        m.getReactions().get( graphNodes.get(temp[i]).getID() ).printReaction();
      
      }
    
    }
    System.out.printf("\n");
  
  }
  
  public boolean contains(int[] cycle, int size, Main m){
  
    boolean result = false;
    
    for(int i=0; i<cycle.length; i++){
    
      if(graphNodes.get(cycle[i]).getType()==_MOLECULE_){
      
        if( m.getLibrary().get( graphNodes.get(cycle[i]).getID() ).getNumNodes() > size*10 ){
          
          result = true;
        
        }
      
      }
    
    }
    return result;
  
  }
  
  public static void main(String args[]){
    
    int pop = 5000;
    Main m = new Main(50, 10, 2, pop);
    m.getReactions().add(new Reaction());
    m.temperature = 1;
    
    for(int i=0; i<100*pop; i++){
      if(i%100==0){
       m.progress("Running...", i, 100*pop);
      }
      
      if(m.getBucket().size()==2){
        break;
      }

      ArrayList<Molecule> reactants, unstable;
      reactants = m.selectRandMols(2);
      
      Molecule A, B;
      
      A = reactants.get(0);
      B = reactants.get(1);
      
      if(! (A.getID()==0 || B.getID() == 0)){
        m.collide(A, B, false);
      }
      
      /*for(int j=0; j<m.getBucket().size(); j++){
        m.getLibrary().get( m.getBucket().get(j).getID() ).setMol(new Molecule(  m.getBucket().get(j) ) );
      }*/
      
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

    }
    
    m.progress("Running...", 10000,10000);
    
    System.out.printf("\n");
    Graph g = new Graph();
    System.out.printf("# Reactions = %d", m.getReactions().size());
    for(int i=1; i<m.getReactions().size(); i++){
      //if(i%10==0){m.progress("Graph build", i, m.getReactions().size());}
      Reaction X = new Reaction();
     
      if(g.filter(m.getReactions().get(i), 0, 1)){
        g.add(m.getReactions().get(i), m);
      }
      
    }

    //m.progress("Graph build", m.getReactions().size(), m.getReactions().size());
    System.out.printf("\n");
    System.out.printf("\tPerforming DFS...\n");
    g.dfs();
    System.out.printf("\tNumber of cycles: %d\n", g.getNumCycles());
    
    for(int i=0; i<g.getCycles().size(); i++){
      
      if(g.contains(g.getCycles().get(i), 1, m)){
        g.printCycle(i, m);
      }
      
    
    }
    
  }

}








