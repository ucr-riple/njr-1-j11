import java.util.*;

public class MergeMap implements DMap{
       
    private KhovanovMapNode fromNode;
    private KhovanovMapNode toNode;
    private ArrayList<SeifertCircle> fromCircles;
    private SeifertCircle toCircle;

    public MergeMap(KhovanovMapNode f, KhovanovMapNode t, ArrayList<SeifertCircle> fc, SeifertCircle tc){
        this.fromNode = f;
        this.toNode = t;
        this.fromCircles = fc;
        this.toCircle = tc;  
        
        addTensorArrows();
        System.out.println(toString());
    }
    
    public KhovanovMapNode getToNode(){
        return this.toNode;
    }
    
    public void addTensorArrows(){
        Tensor[] from = this.fromNode.getBasis();
        Tensor[] to = this.toNode.getBasis();
        
        /*   V+V+ |--> V+
         *   V+V- |--> V-
         *   V-V+ |--> V-
         *   V-V- |--> 0
         */  
        BasisElement toVminus = this.toCircle.getVminus();
        BasisElement toVplus = this.toCircle.getVplus();
        
        BasisElement fromCircOneVminus = this.fromCircles.get(0).getVminus();
        BasisElement fromCircOneVplus = this.fromCircles.get(0).getVplus();
        BasisElement fromCircTwoVminus = this.fromCircles.get(1).getVminus();
        BasisElement fromCircTwoVplus = this.fromCircles.get(1).getVplus();
        
        Arrow theArrow;
        
        for(Tensor f:from){
            if(f.contains(fromCircOneVplus) && f.contains(fromCircTwoVplus)){          
                for(Tensor t:to){
                    
                    if(f.isIdentityMinusOne(t,toVplus)){

                        if(t.contains(toVplus)){
                            theArrow = new Arrow(f,t);
                            f.addOutArrow(theArrow);
                            t.addInArrow(theArrow);  
                        }
                    }
                }        
            }
            
            else if((f.contains(fromCircOneVplus) && f.contains(fromCircTwoVminus)) || (f.contains(fromCircOneVminus) && f.contains(fromCircTwoVplus))){          
                for(Tensor t:to){
                    if(f.isIdentityMinusOne(t,toVminus)){
                        if(t.contains(toVminus)){
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
        String s = "MERGE ["+fromCircles.get(0).getMinEdge()+fromCircles.get(1).getMinEdge()+"] From "+fromNode.getBinCode()+" to "+toNode.getBinCode();
        return s;
    }
    
}