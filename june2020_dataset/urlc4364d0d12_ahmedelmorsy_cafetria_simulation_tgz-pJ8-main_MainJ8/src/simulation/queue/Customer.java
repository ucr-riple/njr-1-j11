package simulation.queue;


public class Customer {
    
    private static int idGen = 1;
    
    private int id;
    private int accumlatedTime;
    private int type;
    
    public Customer() {
        this.id = idGen++;
        this.accumlatedTime = 0;
    }
    
    public void accumlateTime(int time) {
        this.accumlatedTime += time;
    }
    
    public int getAccumlatedTime() {
        return this.accumlatedTime;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setType(int route) {
        this.type = route;
    }
    
    public int getType() {
        return this.type;
    }
}
