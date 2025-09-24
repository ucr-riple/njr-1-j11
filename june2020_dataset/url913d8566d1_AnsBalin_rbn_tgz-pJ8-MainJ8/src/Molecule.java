import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class Molecule implements Serializable{
  
  boolean flag;
  int NetworkID;
  int[] MoleculeID;
  int[] bondingSites;
  ArrayList<Molecule> children;
  int state;
  int size;
  String StringID;
  
  private int incr;
  
  // Creates an instance of the NULL molecule
  public Molecule(){
    
    flag = false;
    NetworkID = 0;
    children = null;
    MoleculeID = new int[0];
    bondingSites = new int[0];
    state = 0;
    StringID = toStringf();
  }
  
  public Molecule(int NetworkID, Molecule M, Molecule N){
    
    flag = false;
    this.NetworkID = NetworkID;
    children = new ArrayList<Molecule>(2);
    children.add(M);
    children.add(N);
    MoleculeID = new int[0];
    bondingSites = new int[0];
    state = 0;
    if((M.getChildren()==null) || (N.getChildren()==null)){
      size = 1;
    }
    else{
      size = M.getSize() + N.getSize();
    }
    StringID = toStringf();
    
  }
  
  public Molecule(Molecule M){
    
    flag = false;
    NetworkID = M.getID();
    MoleculeID = new int[0];
    bondingSites = Arrays.copyOf(M.getBondingSites(), M.getBondingSites().length);
    
    ArrayList<Molecule> childrenCopy = new ArrayList<Molecule>();
    
    if(M.getChildren()!=null){
      for (int i=0; i<M.getChildren().size(); i++){
        
        childrenCopy.add(new Molecule(M.getChildren().get(i)));
      }    
    }
    else{
      childrenCopy = null;
    }
    
    children = childrenCopy;
    state = M.getState();
    size = M.getSize();
    
  }
  
  public void initMoleculeID(int size){
    
    MoleculeID = new int[size];
  }
  
  public void calculateMoleculeID(int level, int val, int[] parentMolID){
    
    initMoleculeID(level);
    
    if(level>1){
      for(int i=0; i<level-1; i++){
        
        setMoleculeID(i, parentMolID[i]);
      }
      
    }
    if(level!=0){
      setMoleculeID(level-1, val); // Set child[0].moleculeID[last]=0
    }
    
    if( ! ((children.get(0).getID()==0)||(children.get(1).getID()==0)) ){
      
      
      
      children.get(0).calculateMoleculeID(level+1, 0, MoleculeID);
      children.get(1).calculateMoleculeID(level+1, 1, MoleculeID);
      
    }
    
  }
  
  public boolean getFlag(){
    
    return flag;
  }
  
  public ArrayList<Molecule> getChildren(){
    return children;
  }
  
  public Molecule getChildren(int i){
    
    return children.get(i);
  
  }
  
  public int getID(){
    
    return NetworkID;
  }
  
  
  
  public int[] getMolID(){
    
    return MoleculeID;
  }
  
  public int[] getBondingSites(){
    
    return bondingSites;
  }
  
  public int getState(){
  
    return state;
  }
  
  public int getSize(){
    return size;
  }
  
  public String getStringID(){
    return StringID;
  }

  public void setID(int NetworkID){
    
    this.NetworkID = NetworkID;
  }
  
  public void setMoleculeID(int i, int val){
    
    MoleculeID[i] = val;
  }
  
  
  public void setChildren(ArrayList<Molecule> children){
    
    this.children = children;
  }
  
  public void setSize(int size){
  
    this.size = size;
  }
  
  // Flags the molecule it's called on
  public void flag(){
    
    flag = true;
  }
  public void unFlag(){
    
    flag=false;
  }
  public ArrayList<Molecule> sortIntoArray(ArrayList<Molecule> arrList){
    
    arrList.add(this);
    if( !((children.get(0).getID() == 0) || (children.get(1).getID()==0)) ){
      children.get(0).sortIntoArray(arrList);
      children.get(1).sortIntoArray(arrList);
      
    }
    
    return arrList;
  }
  
  public void setState(int state){
  
    this.state = state;
  }
  
  //Flags all the children of the Molecule it's called on
  public void flagChildren(){
    
    if(children!=null){
      children.get(0).flag();
      //System.out.println("\tChild flagged: "+children.get(0).toStringf());
      children.get(1).flag();
      //System.out.println("\tChild flagged: "+children.get(1).toStringf());
      children.get(0).flagChildren();
      children.get(1).flagChildren();
      
    }
  }
  
  //Works down tree from root, flagging all the ancestors of the split point
  //MoleculeID must be the ID of the split point
  //when function is called in main, size = MoleculeID.length and val = 0
  public void flagAncestors(int[] MoleculeID, int size, int val){
    
    
    if ((size!=0)){
      val = MoleculeID[MoleculeID.length-size];
      this.flag();
      //System.out.printf(""+toStringf()+" flagged\n");
      if(children!=null){
        children.get(val).flagAncestors(MoleculeID, size-1, val);
      }
      
    }
  }       
  
  // Searches from root downwards to find highest order unflagged molecules and adds
  // them to ArrayList unflagged.
  public void findUnflaggedChildren(ArrayList<Molecule> unflagged){
    
    if(children!=null){
      if(!children.get(0).getFlag()){
        unflagged.add(children.get(0));
        children.get(0).flag();
        children.get(0).flagChildren();
      }
      if(!children.get(1).getFlag()){
        unflagged.add(children.get(1));
        children.get(1).flag();
        children.get(1).flagChildren();
      }
      children.get(0).findUnflaggedChildren(unflagged);
      children.get(1).findUnflaggedChildren(unflagged);
      
    }
    
  }
  
  public void unFlagAll(){
    unFlag();
    
    if(children!=null){
      
      children.get(0).unFlagAll();
      children.get(1).unFlagAll();
    }
    
  }
  
  
  
  // Makes Molecule selected point to a random molecule in tree
  // by performing resevoir sampling: iterate through tree selecting
  // the nth node with a probability of 1/n
  public int[] selectRandom(){
    calculateMoleculeID(0,0, new int[0]);
    ArrayList<Molecule> arr = new ArrayList<Molecule>();
    Molecule selected = new Molecule();
    int n;
    double rand;
    
    //Sorts THIS molecule into an array
    arr = sortIntoArray(arr);
    
    for(int i=0; i<arr.size(); i++){
      //System.out.printf("%d\t", arr.get(i).getID());
      n=i+1;
      selected = (Math.random() <= (double)1/(double)n) ? arr.get(i) : selected;
      //System.out.printf(""+Arrays.toString(arr.get(i).getMolID())+"\n");
  
    }
    //System.out.printf("\n");
    
    return selected.getMolID();
  
  }
  
  public Molecule getFromMoleculeID(int[] MoleculeID, int size, int val){
    
    if(this.getID()==0){
      return this;
    }
    
    if (size!=0){
      //System.out.printf(""+this.toStringf()+"\t\t\t");
      val = MoleculeID[MoleculeID.length-size];
      //System.out.printf("%d\t%d\t%d\n", val, MoleculeID.length, size);
      return children.get(val).getFromMoleculeID(MoleculeID, size-1, val);
    }
    
    
    else {
      return this;
    }
    
  }
  
  public ArrayList<Molecule> split(int[] MoleculeID){
    
    
    ArrayList<Molecule> fragments = new ArrayList<Molecule>();
    
    int len = (Arrays.equals(MoleculeID, new int[0])) ? 0 : MoleculeID.length;
    
    Molecule splitPoint = getFromMoleculeID(MoleculeID, MoleculeID.length, 0);
    //System.out.println("Split point is at "+Arrays.toString(MoleculeID));
    //System.out.println("Molecule at split point: "+splitPoint.toStringf());
    splitPoint.flag();
    splitPoint.flagChildren();
    flagAncestors(MoleculeID, MoleculeID.length, 0);
    findUnflaggedChildren(fragments);
    
    
    return fragments;
    
  }
  
  public String toString(){
    
    if(NetworkID == 0){
      String nullString = "0";
      return nullString;
    }
    if(((children.get(0).getChildren()==null) ) ) {
      
      return Global.chars[NetworkID];
    }
    
    else {
      
      String s1 = children.get(0).toString();
      String s2 = children.get(1).toString();
      
      String result = "["+s1+s2+"]";
      return result;
      
    }
    
    
  }
  
  public String toStringf(){
    
    //System.out.printf("\tMarker 1\n");
    String str1 = toString();
    String str2;
    //System.out.println("\tMarker 2\tstr1 = "+str1);
    int len = str1.length();
    //System.out.printf("\tMarker 3\tlen = %d\n", len);
    if (len>3){
      //  System.out.printf("\tMarker 4\n");
      str2 = str1.substring(1, (len-1));
    }
    else{
      str2 = str1;
    }
    return str2;
  }
  
  public static void main(String args[]){
    
    ArrayList<Molecule> children;
    
    Molecule X = new Molecule();
    
    Molecule A = new Molecule(1, X, X);
    Molecule B = new Molecule(2, X, X);
    Molecule C = new Molecule(3, X, X);
    Molecule D = new Molecule(4, new Molecule(A), new Molecule(B));
    Molecule E = new Molecule(5, new Molecule(D), new Molecule(C));
    Molecule F = new Molecule(6, new Molecule(B), new Molecule(C));
    Molecule G = new Molecule(7, new Molecule(E), new Molecule(F));
    Molecule H = new Molecule(8, new Molecule(G), new Molecule(B));
    Molecule I = new Molecule(9, new Molecule(H), new Molecule(F));
    
    children = A.getChildren();
    System.out.printf("Children of A are: "+Global.chars[children.get(0).getID()]+" and "
                      +Global.chars[children.get(1).getID()]+"\n");
    children = D.getChildren();
    System.out.printf("Children of D are: "+Global.chars[children.get(0).getID()]+" and "
                      +Global.chars[children.get(1).getID()]+"\n");        
    String str1 = E.toStringf();
    String str2 = F.toStringf();
    String str3 = G.toStringf();
    String str4 = I.toStringf();
    System.out.println("E is "+str1);
    System.out.println("F is "+str2);
    System.out.println("G is "+str3);
    System.out.println("I is "+str4);
    
    
    System.out.printf("Calculating MoleculeID for I...\n");
    I.calculateMoleculeID(0,0, new int[0]);
    
    
    
    ArrayList<Molecule> arrI = new ArrayList<Molecule>();
    arrI = I.sortIntoArray(arrI);
    
    int[] rand = I.selectRandom();
    System.out.printf("Random element: "+Arrays.toString(rand)+"\n");
    
    for(int i=0; i<arrI.size(); i++){
      System.out.printf("|| %d\n", arrI.get(i).getID());
    }
    
    int[] arr = {0,0,0};
    System.out.printf("Getting from MoleculeID...\n");
    Molecule P = I.getFromMoleculeID(arr, arr.length, 0);
    
    if(P == null){
      System.out.printf("P IS NULL LOL\n");
    }
    
    System.out.printf("Converting to String...\n");
    String strP = P.toStringf();
    System.out.println("P is: "+strP);
    
    System.out.println("Splitting I at P...");
    ArrayList<Molecule> fragments = I.split(arr);
    //String frag1 = fragments.get(0).toStringf();
    //String frag2 = fragments.get(1).toStringf();
    int fragSize = fragments.size();
    //System.out.println("Fragments returned are: "+frag1+" and "+frag2);
    System.out.printf("Number of fragments: %d\n", fragSize);
    
    
  }
}