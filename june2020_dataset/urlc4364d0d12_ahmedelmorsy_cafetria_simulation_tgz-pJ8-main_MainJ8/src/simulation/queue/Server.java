package simulation.queue;

import simulation.global.Event;

/**
 * Queue System Server
 * @author Ahmed Elmorsy Khalifa
 *
 */
public abstract class Server {
    
    private QueueSystem qs;
    
    private String name;
    
    public Server(String name) {
        this.name = name;
    }
    
    /**
     * check if server busy serving a customer
     * @return true if server is busy serving a customer and false otherwise.
     */
    public abstract boolean isBusy();

    /**
     * serve a customer; 
     * @param customer
     */
    protected abstract void serve(Customer customer, final Event e);

    /**
     * set Queue system to be able to pull customers from queue when idle
     * @param queueSystem the queue system in which server is working
     */
    protected void setQueueSystem(QueueSystem qs){
        this.qs = qs;
    }
    
    protected QueueSystem getQueueSystem(){
        return this.qs;
    }
    
    protected String getName(){
        return this.name;
    }
    
}
