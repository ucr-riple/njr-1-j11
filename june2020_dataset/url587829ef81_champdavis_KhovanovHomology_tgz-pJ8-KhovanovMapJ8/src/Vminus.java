public class Vminus implements BasisElement{
    
    private String circleNumber;
    private int name;
    private boolean sign;
    
    public Vminus(String cn){
        this.circleNumber = cn;
        this.name = findName();
        this.sign = false; //-
        
    }
    
    public int findName(){//find min number in the circle number
        
        if(circleNumber.length()==0) return 0;
        
        else{
            int min = Character.getNumericValue(this.circleNumber.charAt(0));
            for(int i=1; i<this.circleNumber.length(); i++){
                if(min>Character.getNumericValue(this.circleNumber.charAt(i)))
                    min=Character.getNumericValue(this.circleNumber.charAt(i));  
            }
            
            return min;
        }
        
    }
    public boolean getSign(){
        return this.sign;
    }
    
    public String getCode(){
        return this.circleNumber;
    }
    
    public int getName(){
        return this.name;
    }
    
    @Override
    public boolean equals(BasisElement v){
        
        return this.name==v.getName() && this.sign==v.getSign();
    }
    
    public String toString(){
        return "V"+this.name+"-";
        
        
    }
    
    
}