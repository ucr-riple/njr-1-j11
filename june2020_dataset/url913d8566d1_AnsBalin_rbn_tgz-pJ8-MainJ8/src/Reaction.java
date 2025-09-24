import java.util.*;

public class Reaction{

  private int[] reactants;
  private int[] products;
  private int count;
  private String ID;
  private int IntID;
  private int type;
  
  private ArrayList<Molecule> molReactants, molProducts;
  
  public Reaction(Reaction R){
  
    this.reactants = Arrays.copyOf(R.getReactants(), R.getReactants().length);
    this.products = Arrays.copyOf(R.getProducts(), R.getProducts().length);
    this.count=R.getCount();
    this.ID = R.getID();
    this.IntID = R.getIntID();
  
  }
  
  public Reaction(){
  
  }
  
  public Reaction(int[] reactants, int[] products){
  
    this.reactants = reactants;
    this.products = products;
    
    Arrays.sort(reactants);
    Arrays.sort(products);
    
    
    String str = "";
    
    for(int i=0; i<this.reactants.length; i++){
      str = str+this.reactants[i]+" ";
    }
    str = str+"--> ";
    
    for(int i=0; i<this.products.length; i++){
      str = str+this.products[i]+" ";
    }
    
    ID = str;
    count=0;
  
  }
  
  public Reaction(int[] reactants, int[] products, Main m){
    
    this.reactants = reactants;
    this.products = products;
    
    Arrays.sort(reactants);
    Arrays.sort(products);
    
    
    String str = "";
    
    for(int i=0; i<this.reactants.length; i++){
      str = str+m.getLibrary().get(this.reactants[i]).getMol().getStringID();
      if(i<this.reactants.length-1){
        str += " + ";
      }
    }
    str = str+"--> ";
    
    for(int i=0; i<this.products.length; i++){
      str = str+m.getLibrary().get(this.products[i]).getMol().getStringID();
      if(i<this.products.length-1){
        str += " + ";
      }
    }
    
    ID = str;
    count=0;
    
  }
  
  public Reaction(ArrayList<Molecule> reactants, ArrayList<Molecule> products){
  
    this.reactants = new int[reactants.size()];
    this.products = new int[products.size()];
    
    molReactants = reactants;
    molProducts = products;
    
    for(int i=0; i<this.reactants.length; i++){
      
      this.reactants[i] = reactants.get(i).getID();
    }
    
    for(int i=0; i<this.products.length; i++){
      
      this.products[i] = products.get(i).getID();
    }
    
    Arrays.sort(this.reactants);
    Arrays.sort(this.products);
    
    String str = "";
    
    for(int i=0; i<this.reactants.length; i++){
      str = str+this.reactants[i]+" ";
    }
    str = str+"-> ";
    
    for(int i=0; i<this.products.length; i++){
      str = str+this.products[i]+" ";
    }
    
    ID = str;
    count=0;
  
  
  }
  
  public Reaction(ArrayList<Molecule> reactants, ArrayList<Molecule> products, Main m){
    
    this.reactants = new int[reactants.size()];
    this.products = new int[products.size()];
    
    molReactants = reactants;
    molProducts = products;
    
    for(int i=0; i<this.reactants.length; i++){
      
      this.reactants[i] = reactants.get(i).getID();
      
    }
    
    for(int i=0; i<this.products.length; i++){
      
      this.products[i] = products.get(i).getID();
    }
    
    Arrays.sort(this.reactants);
    Arrays.sort(this.products);
    
    String str = "";
    
    for(int i=0; i<this.reactants.length; i++){
      str = str+m.getLibrary().get(this.reactants[i]).getMol().getStringID();
      if(i<this.reactants.length-1){
        str += " + ";
      }
    }
    str = str+"--> ";
    
    for(int i=0; i<this.products.length; i++){
      str = str+m.getLibrary().get(this.products[i]).getMol().getStringID();
      if(i<this.products.length-1){
        str += " + ";
      }
    }
    
    ID = str;
    count=0;
    
  }

  
  public int getCount(){
    return count;
  }
  
  public int[] getReactants(){
    return reactants;
  }
  
  public int[] getProducts(){
    return products;
  }
  
  public ArrayList<Molecule> getMolReactants(){
    return molReactants;
  }
  
  public ArrayList<Molecule> getMolProducts(){
    return molProducts;
  }
  
  public String getID(){
    return ID;
  }
  
  public int getIntID(){
    return IntID;
  }
  
  
  public void setIntID(int i){
    IntID = i;
  }
  
  public void incrCount(){
    count++;
  }
  
  public boolean equals(Reaction R){
  
    boolean result = (reactants.length==R.getReactants().length) && 
                     (products.length==R.getProducts().length);
    int i=0;
    boolean finish=false;
    
    while(result && !finish){
      
      if(i<reactants.length){
        
        result = (reactants[i] == R.getReactants()[i]);
        finish=false;
      }
      else{finish=true;}
      
      if(i<products.length){
      
        result &= (products[i] == R.getProducts()[i]);
        finish=false;
      }
      else{finish &= true;}
      
      i++;
      
    }
    
    return result;
    
  }
  
  public void printReaction(){
  
    System.out.printf(""+molReactants.get(0).toStringf()+" + "+molReactants.get(1).toStringf()+" --> ");
    
    System.out.printf(""+molProducts.get(0).toStringf());
    for(int i=1; i< molProducts.size(); i++){
    
      System.out.printf(" + "+molProducts.get(i).toStringf());
    }
  
  
    System.out.printf("\n");
  }
  
  
  public static void main(String args[]){
    
    int[] reactantsA = {43, 18};
    int[] productsA = {4, 52, 1};
    
    int[] reactantsB = {12, 18};
    int[] productsB = {6, 1, 52};
    
    int[] reactantsC = {18, 43};
    int[] productsC = {52, 4, 1};
    
    int[] reactantsD = {18, 43};
    int[] productsD = {52, 4, 1, 4};
    
    Reaction A = new Reaction(reactantsA, productsA);
    Reaction B = new Reaction(reactantsB, productsB);
    Reaction C = new Reaction(reactantsC, productsC);
    Reaction D = new Reaction(reactantsD, productsD);
    
    boolean AequalsB = A.equals(B);
    boolean AequalsC = A.equals(C);
    boolean CequalsA = C.equals(A);
    boolean AequalsD = A.equals(D);
    
    if(AequalsB){
      System.out.printf("A equals B\n");
    }
    
    if(AequalsC){
      System.out.printf("A equals C\n");
    }
    
    if(CequalsA){
      System.out.printf("C equals A\n");
    }
    if(AequalsD){
      System.out.printf("A equals D\n");
    }
    
    System.out.printf(""+A.getID()+"\n");
    System.out.printf(""+B.getID()+"\n");
    System.out.printf(""+C.getID()+"\n");
    System.out.printf(""+D.getID()+"\n");
    
  }
  



}






