public class DotRow{
    
    private Tensor[] dots;
    private int count;
    
    public DotRow(int size){
        this.dots = new Tensor[size];
        this.count = 0;
    }
    
    public void add(Tensor t){
        this.dots[this.count++] = t;
    }
    
    public Tensor[] getDots(){
        return this.dots;
    }
    
    public int numDotsRemaining(){
        int count=0;
        for(Tensor t: this.dots){
            if(!t.isDeleted())
                count++;
        }
        
        return count;
        
        
    }
    
    
    
    
}