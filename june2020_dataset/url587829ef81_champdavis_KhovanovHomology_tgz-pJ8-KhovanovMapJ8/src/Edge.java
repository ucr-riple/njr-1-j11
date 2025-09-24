public class Edge{
    
    private Vertex from;
    private Vertex to;
    
    public Edge(Vertex f, Vertex t){
        this.from = f;
        this.to = t;
    }
    
    public void swap(){
        Vertex temp = this.from;
        this.from = this.to;
        this.to = temp;
    }
     
}