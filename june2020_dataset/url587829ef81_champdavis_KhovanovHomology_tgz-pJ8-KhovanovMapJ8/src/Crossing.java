public class Crossing{
    private int[] overstrand;
    private int[] understrand;
    private boolean posneg; //1 for positive crossing
    
    
    public Crossing(int a, int b, int x, int y, boolean z){
        overstrand=new int[] {a,b};
        understrand=new int[] {x,y};
        posneg=z;
    }
    
    public int get1st(){ return overstrand[0];}
    public int get2nd(){ return overstrand[1];}
    public int get3rd(){ return understrand[0];}  
    public int get4th(){ return understrand[1];}
    
    public int getMaxVertex(){ return Math.max(Math.max(overstrand[0],overstrand[1]),Math.max(understrand[0],understrand[1])); }  //returns max vertex at the crossing
    public int getMinVertex(){ return Math.min(Math.min(overstrand[0],overstrand[1]),Math.min(understrand[0],understrand[1])); }  //returns min vertex at the crossing
    
    public void swapOverstrand(){
        int temp = this.overstrand[0];
        this.overstrand[0] = this.overstrand[1];
        this.overstrand[1] = temp;
    }
    
    public void swapUnderstrand(){
        int temp = this.understrand[0];
        this.understrand[0] = this.understrand[1];
        this.understrand[1] = temp;
    }
    
    public void swapSign(){
        this.posneg = !this.posneg;
        
    }
        
        
    public boolean getSign(){ return this.posneg;}
    
    public Smoothing getZeroSmoothing(){
        if(this.posneg) //if positive crossing
            return new Smoothing(this.overstrand[0], this.understrand[1], this.understrand[0], this.overstrand[1], false); //outerToOuterInnerToInner
        else
            return new Smoothing(this.overstrand[0], this.understrand[0], this.overstrand[1], this.understrand[1], true); //outerToInnerInnerToOuter
    }
    
    public Smoothing getOneSmoothing(){
        if(this.posneg)
            return new Smoothing(this.overstrand[0], this.understrand[0], this.overstrand[1], this.understrand[1], true); //outerToInnerInnerToOuter
        else
            return new Smoothing(this.overstrand[0], this.understrand[1], this.understrand[0], this.overstrand[1], false); //outerToOuterInnerToInner
        
    }
    
   /* public Smoothing outerToInnerInnerToOuter(){
        return new Smoothing(this.overstrand[0], this.understrand[0], this.overstrand[1], this.understrand[1]); //outerToInnerInnerToOuter
    }
    
    public Smoothing outerToOuterInnerToInner(){
        return new Smoothing(this.overstrand[0], this.understrand[1], this.overstrand[1], this.understrand[0]); //outerToOuterInnerToInner
    }*/
    
    /*public void reduce(Smoothing s)
    {
        int high1=Math.max(s.a1,s.a2);
        int low1=Math.min(s.a1,s.a2);
        int high2=Math.max(s.b1,s.b2);
        int low2=Math.min(s.b1,s.b2);
        //System.out.println(high1+", "+low1+", "+high2+", "+low2);
        
        if(overstrand[0]==high1){overstrand[0]=low1;}
        if(overstrand[1]==high1){overstrand[1]=low1;}
        if(understrand[0]==high1){understrand[0]=low1;}
        if(understrand[1]==high1){understrand[1]=low1;}
        
        if(overstrand[0]==high2){overstrand[0]=low2;}
        if(overstrand[1]==high2){overstrand[1]=low2;}
        if(understrand[0]==high2){understrand[0]=low2;}
        if(understrand[1]==high2){understrand[1]=low2;}
    }*/
    
    public String toString(){
        return overstrand[0]+""+overstrand[1]+" "+understrand[0]+""+understrand[1];
    }
   
    
    
    
}