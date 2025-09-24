public class SeifertCircle implements Comparable<SeifertCircle>{
    
    private int minEdge;
    private String code;
    private BasisElement vplus;
    private BasisElement vminus;
    
    public SeifertCircle(String c){
        this.code = c;
        this.vplus = new Vplus(c);
        this.vminus = new Vminus(c);
        
        this.minEdge=findMinEdge();
    }
    
    public int compareTo(SeifertCircle other){
        if(this.code.equals(other.getCode()))
            return 0;
        else
            return 1;
        
    }
    
     public int compareName(SeifertCircle other){
        if(this.minEdge==other.getMinEdge())
            return 0;
        else
            return 1;
        
    }
     
     public int findMinEdge(){//find min number in the circle number
         
         if(this.code.length()==0) return 0;
         
         else{
             int min = Character.getNumericValue(this.code.charAt(0));
             for(int i=1; i<this.code.length(); i++){
                 if(min>Character.getNumericValue(this.code.charAt(i)))
                     min=Character.getNumericValue(this.code.charAt(i));  
             }
             
             return min;
         }
         
     }
    
    public BasisElement getVplus(){ return this.vplus;}
    public BasisElement getVminus(){ return this.vminus;}
    
    public String getCode(){ return this.code;}
    public int getMinEdge(){ return this.minEdge;}
        
    
    
    
    
}