package simulation.queue;

import java.util.Random;

import simulation.global.Event;
import simulation.global.EventsQueue;
import simulation.global.SimulationClk;
import simulation.global.Statistics;
import simulation.queue.QueueSystem.QueueEntry;

public class UniformServer extends Server{

    private int serviceTimeMin;
    private int serviceTimeMax;
    private int accumelatedTimeMin;
    private int accumelatedTimeMax;
    private int finishTime;
    private Random stGen;
    private Random actGen;
    
    
    public UniformServer(String name, int stMin, int stMax, int actMin, int actMax, int emp) {
        super(name);
        this.serviceTimeMin = stMin / emp;
        this.serviceTimeMax = stMax / emp;
        this.accumelatedTimeMin = actMin;
        this.accumelatedTimeMax = actMax;
        this.finishTime = -1;
        stGen = new Random();
        actGen = new Random();
    }

    @Override
    public boolean isBusy() {
        if (SimulationClk.clock > finishTime) return false;
        return true;
    }

    @Override
    protected void serve(Customer customer, final Event e) {
        int serviceTime = stGen.nextInt(serviceTimeMax - serviceTimeMin) + serviceTimeMin;
        finishTime = SimulationClk.clock + serviceTime;
        int accTime = actGen.nextInt(accumelatedTimeMax - accumelatedTimeMin) + accumelatedTimeMin;
        customer.accumlateTime(accTime);
        EventsQueue.enqueue(finishTime, new Event() {
            
            @Override
            public void execute() {
                e.execute();
                QueueEntry next;
                if ((next = getQueueSystem().dequeue()) != null){
                    Statistics.console.log("Customer " + next.customer.getId() + " left queue and is going to be served");
                    Statistics.trace.log("[" + SimulationClk.clock + "][" + getName() +"][Service]" + next.customer.getId());
                    Statistics.CustomerQuitQueue(next.customer, getName());
                    serve(next.customer, next.afterService);
                } else {
                    finishTime = -1;
                }
            }

            @Override
            public String getDescription() {
                return "Customer uniform server event";
            }
        });
    }
}