
package dijkstra;

/**
 * Edge which are the interface (link) between the routers.
 */
public class Edge  {
  private String id; 
  private FibonacciHeapNode source;
  private FibonacciHeapNode destination;
  private int weight; 
  
  public Edge(String id, FibonacciHeapNode source, FibonacciHeapNode destination, int weight) {
    this.id = id;
    this.source = source;
    this.destination = destination;
    this.weight = weight;
  }
  
  public String getId() {
    return id;
  }
  public FibonacciHeapNode getDestination() {
    return destination;
  }

  public FibonacciHeapNode getSource() {
    return source;
  }
  public int getWeight() {
    return weight;
  }
  
  public void setWeight(int w){
      this.weight = w;
  }
  
  @Override
  public String toString() {
    return source + " " + destination;
  }
  
  
} 
