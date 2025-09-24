import java.util.*;

public class SplitMap implements DMap{
    
    private KhovanovMapNode fromNode;
    private KhovanovMapNode toNode;
    private SeifertCircle fromCircle;
    private ArrayList<SeifertCircle> toCircles;
    
    public SplitMap(KhovanovMapNode f, KhovanovMapNode t, SeifertCircle fc, ArrayList<SeifertCircle> tc){
        this.fromNode = f;
        this.toNode = t;
        this.fromCircle = fc;
        this.toCircles = tc; 
        //System.out.println(tc.get(0).getCode());
        addTensorArrows();
        System.out.println(toString());
    }
    
    public KhovanovMapNode getToNode(){
        return this.toNode;
    }
    
    public void addTensorArrows(){
        Tensor[] from = this.fromNode.getBasis();
        Tensor[] to = this.toNode.getBasis();
        
        /*   V+ |--> V+ V-
         *   V+ |--> V- V+
         *   V- |--> V- V-
         */  
        BasisElement fromVminus = this.fromCircle.getVminus();
        BasisElement fromVplus = this.fromCircle.getVplus();
        
        BasisElement toCircOneVminus = this.toCircles.get(0).getVminus();
        BasisElement toCircOneVplus = this.toCircles.get(0).getVplus();
        BasisElement toCircTwoVminus = this.toCircles.get(1).getVminus();
        BasisElement toCircTwoVplus = this.toCircles.get(1).getVplus();
        
        Arrow theArrow;
        
        for(Tensor f:from){
            if(f.contains(fromVplus)){          
                for(Tensor t:to){
                    
                    if(t.isIdentityMinusOne(f,fromVplus)){
                        //System.out.println("OMG: "+t.toString());

                        if((t.contains(toCircOneVplus) && t.contains(toCircTwoVminus)) || (t.contains(toCircOneVminus) && t.contains(toCircTwoVplus))){
                            theArrow = new Arrow(f,t);
                            f.addOutArrow(theArrow);
                            t.addInArrow(theArrow);  
                        }
                    }
                }        
            }
            
            else if(f.contains(fromVminus)){          
                for(Tensor t:to){
                    if(t.isIdentityMinusOne(f,fromVminus)){
                        if((t.contains(toCircOneVminus) && t.contains(toCircTwoVminus))){
                            theArrow = new Arrow(f,t);
                            f.addOutArrow(theArrow);
                            t.addInArrow(theArrow);  
                        }
                    }
                }        
            }  
        }
    }
    
    public String toString(){
        String s = "SPLIT ["+toCircles.get(0).getMinEdge()+toCircles.get(1).getMinEdge()+"] From "+fromNode.getBinCode()+" to "+toNode.getBinCode();
        return s;
    }
    
    
    
    
    
    
    
}