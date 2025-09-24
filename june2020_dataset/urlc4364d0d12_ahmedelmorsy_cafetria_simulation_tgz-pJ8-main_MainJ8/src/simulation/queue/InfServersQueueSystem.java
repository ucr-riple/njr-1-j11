package simulation.queue;

import simulation.global.Event;
import simulation.global.SimulationClk;
import simulation.global.Statistics;

public class InfServersQueueSystem extends QueueSystem {

    public InfServersQueueSystem(String name, Server server, int queueLen) {
        super(name,server, queueLen);
    }

    @Override
    public boolean enqueue(Customer customer, Event afterService) {
        Statistics.trace.log("[" + SimulationClk.clock + "][" + this.name +"][Service]" + customer.getId());
        this.server.serve(customer, afterService);
        return true;
    }
}
