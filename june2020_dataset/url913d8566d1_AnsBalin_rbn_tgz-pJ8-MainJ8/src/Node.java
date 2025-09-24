import java.util.*;
import java.io.*;

public class Node implements Serializable{
  
  private int[] inputs;
  private boolean state;
  private boolean bondingSite;
  private boolean isFilled;
  private int function;
  private boolean nextState;
  
  
  public Node(int n, boolean bonds){
    inputs = new int[2];
    int i;
    bondingSite = bonds;
    
    
    
    for(i=0;i<2;i++){
      inputs[i] = (int)(Math.random()*n);
    }
    
    while(inputs[1]==inputs[0]){
      inputs[1] = (int)(Math.random()*n);
    }
    
    double randState = Math.random();
    if(randState<0.5)   state = false;
    else                state = true;
    
    nextState = false; //initialised value, doesn't matter
    
    function = (int)(Math.random()*16);
    
  }
  
  public Node(Node N){
    
    this.inputs = Arrays.copyOf(N.getInputs(), N.getInputs().length);
    this.state = N.getState();
    this.bondingSite = N.getBondingSite();
    this.isFilled = N.getFilled();
    this.function = N.getFunction();
    this.nextState = N.getNextState();
  }
  
  public int[] getInputs(){
    
    return inputs;
  }
  
  public boolean getState(){
    
    return state;
  }
  
  public boolean getBondingSite(){
    
    return bondingSite;
  }
  
  public boolean getFilled(){
    
    return isFilled;
  }
  
  
  public boolean getNextState(){
    
    return nextState;
  }
  
  public int getFunction(){
    
    return function;
  }
  
  
  public void setInputs(int[] newInputs){
    //
    // !! Put in validation to check newInputs.length == inputs.length
    //
    
    int i;
    for(i=0;i<inputs.length;i++){
      inputs[i] = newInputs[i];
    }
  }
  
  public void setBondingInput(int bondingInput){
    
    inputs[0] = bondingInput;
  }
  
  public void setState(boolean newState){
    
    state = newState;
  }
  
  public void setBondingSite(boolean bondingSite){
    
    this.bondingSite = bondingSite;
  }
  
  public void setFilled(boolean isFilled){
    
    this.isFilled = isFilled;
  }
  
  public void setNextState(boolean newNextState){
    
    nextState = newNextState;
  }
  
  public void setFunction(int newFunction){
    
    if(newFunction >= 0 && newFunction < 16)    function = newFunction;
    else ;//put error here
  }
  
  public static void main(String args[]){
    
    Node myNode = new Node(10, false);
    int[] myInputs = myNode.getInputs();
    boolean myState = myNode.getState();
    int myFunction = myNode.getFunction();
    int i;
    
    System.out.printf("[%d, %d]\n", myInputs[0], myInputs[1]);
    System.out.printf("%b\n", myState);
    System.out.printf("%d\n\n", myFunction);
  }
}