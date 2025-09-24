public class Vertex{
    
    private int numberOfVertex;
    private Vertex from;
    private Vertex to;
    private boolean visited;
    
    
    public Vertex(){
        this.numberOfVertex = 0; 
    }
    
    public Vertex(int n){
        this.numberOfVertex = n;  
    }
    
    public Vertex(int n, Vertex f, Vertex t){
        this.from = f;
        this.to = t;
        this.numberOfVertex = n;  
    }
    
    public void setPointers(Vertex f, Vertex t){
        this.from = f;
        this.to = t;
    }
    
    public int getNum(){ return this.numberOfVertex;}
    public Vertex getFrom(){ return this.from;}
    public Vertex getTo(){ return this.to;}
    public boolean wasVisited(){ return this.visited;}
    
    public void setFrom(Vertex f){ this.from=f;}
    public void setTo(Vertex t){ this.to = t;}
    public void visit(){ this.visited = true;}
    
    public String toString(){
        String s = ""+this.numberOfVertex;
        if(this.from!=null && this.to!=null){
            return "From "+this.from.getNum() + " To " + s + " To " + this.to.getNum();
        }
        else return s;
           
          

    }
    
    
    
    
}