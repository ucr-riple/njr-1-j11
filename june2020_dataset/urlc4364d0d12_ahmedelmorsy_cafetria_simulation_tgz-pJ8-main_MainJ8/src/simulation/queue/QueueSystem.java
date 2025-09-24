package simulation.queue;

import java.util.LinkedList;

import simulation.global.Event;
import simulation.global.SimulationClk;
import simulation.global.Statistics;


/**
 * Represent a queue system with queue and server to serve customers.
 * @author Ahmed Elmorsy Khalifa
 *
 */
public class QueueSystem {
    
    /**
     * Queue where customers wait for service.
     */
    private LinkedList<QueueEntry> queue;
    /**
     * Server that serve customers requests.
     */
    protected Server server;
    /**
     * maximum Queue length
     */
    private int maxQLen;
    
    protected String name;
    
    /**
     * Construct new queue system with the server given
     * @param server the server of the new queue system
     * @param queueLen maximum queue length, -1 for unlimited queues
     */
    public QueueSystem(String name, Server server, int queueLen) {
        this.name = name;
        this.server = server;
        this.queue = new LinkedList<QueueSystem.QueueEntry>();
        this.maxQLen = queueLen;
        server.setQueueSystem(this);
    }
    
    /**
     * Enqueue a new customer in system.
     * @param customer customer entering system
     * @return true if it is enqueued successfully, false If queue is empty in limited
     * queue length systems.
     */
    public boolean enqueue(Customer customer, Event afterService) {
        if (queue.size() == maxQLen) return false;
        Statistics.CustomerEnteredQueue(customer, this.name);
        if (server.isBusy()) {
            Statistics.console.log(this.name + " is busy and customer " + customer.getId() + " has to wait");
            Statistics.trace.log("[" + SimulationClk.clock + "][" + this.name +"][Enqueue]" + customer.getId());
            QueueEntry entry = new QueueEntry();
            entry.customer = customer;
            entry.afterService = afterService;
            queue.addLast(entry);
            return true;
        }
        Statistics.console.log("["+ this.name + "] customer " + customer.getId() + " is going to be served");
        Statistics.trace.log("[" + SimulationClk.clock + "][" + this.name +"][Service]" + customer.getId());
        Statistics.CustomerQuitQueue(customer, this.name);
        server.serve(customer, afterService);
        return true;
    }
    
    public int getQueueLength() {
        if (server.isBusy()) return this.queue.size()+1; 
        return this.queue.size();
    }
    
    protected QueueEntry dequeue() {
        if (queue.size() > 0) return queue.removeFirst();
        return null;
    }
    
    protected class QueueEntry {
        public Customer customer;
        public Event afterService;
    }
}
