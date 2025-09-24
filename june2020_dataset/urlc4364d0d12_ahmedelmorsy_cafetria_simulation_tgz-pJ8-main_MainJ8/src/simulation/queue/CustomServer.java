package simulation.queue;

import simulation.global.Event;
import simulation.global.EventsQueue;
import simulation.global.SimulationClk;
import simulation.global.Statistics;
import simulation.queue.QueueSystem.QueueEntry;

public class CustomServer extends Server {

    private int finishTime;
    
    public CustomServer(String name) {
        super(name);
    }
    
    @Override
    public boolean isBusy() {
        if (SimulationClk.clock > finishTime) return false;
        return true;
    }

    @Override
    public void serve(final Customer customer, final Event e) {
        int serviceTime = customer.getAccumlatedTime();
        finishTime = SimulationClk.clock + serviceTime;
        EventsQueue.enqueue(finishTime, new Event() {
            
            @Override
            public void execute() {
                e.execute();
                QueueEntry next;
                if ((next = getQueueSystem().dequeue()) != null){
                    Statistics.trace.log("[" + SimulationClk.clock + "][" + getName() +"][Service]" + next.customer.getId());
                    serve(next.customer, next.afterService);
                    Statistics.CustomerQuitQueue(next.customer, getName());
                } else {
                    finishTime = -1;
                }
            }

            @Override
            public String getDescription() {
                return "Cashier event for customer" + customer.getId();
            }
        });
    }
}
